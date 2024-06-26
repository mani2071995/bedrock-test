
resource "aws_flow_log" "this" {
  count = var.create_vpc && var.enable_flow_log ? 1 : 0

  #...
}
