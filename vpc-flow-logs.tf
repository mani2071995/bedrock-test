Here are some suggested fixes for the issues in the provided code:
1. Enable linting and formatting to catch issues early. For Terraform, tools like `tflint` and `terraform fmt` can help.
2. Use Terraform 0.12+ features:
- Use for_each instead of count where possible
- Create modules for reusable resources like IAM roles and policies
4. Use Terraform built-in functions: 
- Remove wildcard permissions if possible
- Add assertions to test outcomes in the Terraform
- Use consistent spacing and formatting 
Let me know if any part needs more clarification!