import json
import logging
import base64
import os
import boto3
import re
import requests
from urllib.parse import urlparse
from datetime import datetime
from botocore.exceptions import ClientError

# Set up logging
logger = logging.getLogger()
logger.setLevel(logging.INFO)

# Initialize a Boto3 client for Bedrock
bedrock = boto3.client(service_name='bedrock-runtime')

# Fetch the GitHub token from environment variables
GITHUB_TOKEN = os.getenv('GITHUB_TOKEN')
if not GITHUB_TOKEN:
    raise ValueError("GitHub token is not set in the environment variables")

headers = {
    "Authorization": f"token {GITHUB_TOKEN}",
    "Content-Type": "application/json"
}

def fetch_repository_contents(repo_link, branch):
    repo_path = urlparse(repo_link).path.strip('/')
    api_endpoint = f"https://api.github.com/repos/{repo_path}/contents?ref={branch}"
    response = requests.get(api_endpoint, headers=headers)
    response.raise_for_status()
    return {item["path"]: requests.get(item["download_url"], headers=headers).text
            for item in response.json() if item["type"] == "file"}

def analyze_and_remediate_code(code_repo, non_code_exts, folders_exclude):
    def analyze_code(code):
        prompt = f"\n\nHuman: Please analyze the following code:\n\nCode:\n{code}\n\nAssistant:"
        response = bedrock.invoke_model(
            body=json.dumps({
                "prompt": prompt,
                "max_tokens_to_sample": 1000,
                "temperature": 0.5,
                "top_k": 50,
                "top_p": 0.95,
                "stop_sequences": ["\n\nHuman:"]
            }),
            modelId="anthropic.claude-v2:1",
            contentType="application/json",
            accept="*/*"
        )
        analysis = json.loads(response['body'].read()).get('completion', '').strip()
        return re.findall(r"(potential security vulnerability|code style issue|performance issue|code complexity issue|potential bug)", analysis)

    def remediate_code(code, issues):
        prompt = f"\n\nHuman: Please remediate the following code issues:\n\nCode:\n{code}\n\nIssues identified:\n" + "\n".join(f"- {issue}" for issue in issues) + "\n\nAssistant:"
        response = bedrock.invoke_model(
            body=json.dumps({
                "prompt": prompt,
                "max_tokens_to_sample": 2000,
                "temperature": 0.7,
                "top_k": 50,
                "top_p": 0.95,
                "stop_sequences": ["\n\nHuman:"]
            }),
            modelId="anthropic.claude-v2:1",
            contentType="application/json",
            accept="*/*"
        )
        remediation = json.loads(response['body'].read()).get('completion', '').strip()
        code_block_start, code_block_end = remediation.find("```"), remediation.find("```", remediation.find("```") + 3)
        if code_block_start != -1 and code_block_end != -1:
            return remediation[code_block_start + 3:code_block_end], remediation[:code_block_start].strip()
        lines = remediation.split("\n")
        indented_lines = [line for line in lines if line.startswith(" ") or line.startswith("\t")]
        if indented_lines:
            return "\n".join(indented_lines), "\n".join([line for line in lines if not line.startswith(" ") and not line.startswith("\t")]).strip()
        code_keywords = ["def", "class", "import", "if", "for", "while"]
        code_lines = [line for line in lines if any(keyword in line for keyword in code_keywords)]
        if code_lines:
            return "\n".join(code_lines), "\n".join([line for line in lines if not any(keyword in line for keyword in code_keywords)]).strip()
        return "", remediation

    remediations = {}
    summary_of_changes = []
    for file_path, file_content in code_repo.items():
        if any(file_path.endswith(ext) for ext in non_code_exts) or any(file_path.startswith(folder) for folder in folders_exclude):
            logger.info(f"Skipping non-code file or excluded folder: {file_path}")
            continue
        issues = analyze_code(file_content)
        remediated_code, changes_summary = remediate_code(file_content, issues)
        remediations[file_path] = remediated_code
        summary_of_changes.append(f"{file_path} - {', '.join(issues)}. Changes: {changes_summary}")
    return remediations, summary_of_changes

def create_new_branch(event):
    repo_link = event['repository_link']
    remediations = event['remediations']
    base_branch = event['base_branch']
    new_branch_name = event['new_branch_name']

    repo_path = urlparse(repo_link).path.strip('/')
    base_commit = requests.get(f"https://api.github.com/repos/{repo_path}/git/refs/heads/{base_branch}", headers=headers).json()["object"]["sha"]
    if requests.get(f"https://api.github.com/repos/{repo_path}/git/refs/heads/{new_branch_name}", headers=headers).status_code == 200:
        raise ValueError(f"Branch {new_branch_name} already exists.")
    requests.post(f"https://api.github.com/repos/{repo_path}/git/refs", json={"ref": f"refs/heads/{new_branch_name}", "sha": base_commit}, headers=headers)
    blobs = []
    for file_path, remediated_code in remediations.items():
        blob_payload = {
            "content": base64.b64encode(remediated_code.encode()).decode(),
            "encoding": "base64"
        }
        blob_sha = requests.post(f"https://api.github.com/repos/{repo_path}/git/blobs", json=blob_payload, headers=headers).json()["sha"]
        blobs.append({"path": file_path, "mode": "100644", "type": "blob", "sha": blob_sha})
    base_tree_sha = requests.get(f"https://api.github.com/repos/{repo_path}/git/trees/{base_commit}", headers=headers).json()["sha"]
    new_tree_sha = requests.post(f"https://api.github.com/repos/{repo_path}/git/trees", json={"base_tree": base_tree_sha, "tree": blobs}, headers=headers).json()["sha"]
    new_commit_sha = requests.post(f"https://api.github.com/repos/{repo_path}/git/commits", json={"message": "Remediated code", "tree": new_tree_sha, "parents": [base_commit]}, headers=headers).json()["sha"]
    requests.patch(f"https://api.github.com/repos/{repo_path}/git/refs/heads/{new_branch_name}", json={"sha": new_commit_sha}, headers=headers)
    return new_branch_name


def lambda_handler(event, context):
    try:
        repo_link = event['repository_link']
        branch = event.get('branch_name', 'main')
        non_code_exts = event.get('non_code_extensions', ['.md', '.txt', '.gitignore'])
        folders_exclude = event.get('folders_to_exclude', [])
        new_branch_name = event.get('new_branch_name', "remediation-" + datetime.now().strftime("%Y%m%d%H%M%S"))

        code_repo = fetch_repository_contents(repo_link, branch)
        remediations, summary_changes = analyze_and_remediate_code(code_repo, non_code_exts, folders_exclude)

        create_branch_event = {
            'repository_link': repo_link,
            'remediations': remediations,
            'base_branch': branch,
            'new_branch_name': new_branch_name
        }
        
        new_branch_name = create_new_branch(create_branch_event)
        
        return {'message': 'Remediation completed successfully', 'new_branch_name': new_branch_name}
    except Exception as e:
        logger.error("An error occurred during the process: %s", e)
        raise