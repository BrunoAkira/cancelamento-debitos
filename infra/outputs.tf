
output "queue_url" {
  description = "URL da fila SQS criada"
  value       = "https://sqs.${var.aws_region}.amazonaws.com/${var.account_id}/${var.queue_name}"
}
