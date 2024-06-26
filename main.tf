
resource "aws_internet_gateway" "this" {
  count = var.create_igw ? 1 : 0  
  #...
}

resource "aws_nat_gateway" "this" { 
  count = var.enable_nat_gateway ? local.nat_gateway_count : 0
  # ...
}
