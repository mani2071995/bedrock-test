
locals {
  redshift_route_table_ids = aws_route_table.redshift[*].id
  public_route_table_ids = aws_route_table.public[*].id 
  private_route_table_ids = aws_route_table.private[*].id
}
