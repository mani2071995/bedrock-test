terraform
terraform {
  required_version = ">= 1.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.30"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "web" {
  ami           = "ami-0747bdcabd34c712a" 
  instance_type = "t2.micro"

  tags = {
    Name = "Web Server"
  }
}
