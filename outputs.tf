
output "vpc_ipv6_cidr_block" {
  description = "The IPv6 CIDR block" 
  value       = try(aws_vpc.this[0].ipv6_cidr_block, null)
}
