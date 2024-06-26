
locals {
  vpc_ipv6_cidr = aws_vpc.this[0].ipv6_cidr_block  

  create_vpc = var.create_vpc && var.putin_khuylo # Remove unnecessary variable
}

...

resource "aws_route" "private_nat_gateway" {
  for_each = {
    for rt in aws_route_table.private : rt.id => rt if var.enable_nat_gateway 
  }

  route_table_id         = each.value.id 
  destination_cidr_block = var.nat_gateway_destination_cidr_block
  nat_gateway_id         = aws_nat_gateway.this[each.key].id

  timeouts {
    create = "5m"
  }
}

...
