
resource "aws_flow_log" "this" {
  for_each = local.enable_flow_log ? { "flow_log" = "1" } : {}
  
  #...
}

resource "aws_cloudwatch_log_group" "flow_log" {
  for_each = local.create_flow_log_cloudwatch_log_group ? { "flow_log" = "1" } : {}

  #...
}

resource "aws_iam_role" "vpc_flow_log_cloudwatch" {
  for_each = local.create_flow_log_cloudwatch_iam_role ? { "role" = "1" } : {}

  #...
}
