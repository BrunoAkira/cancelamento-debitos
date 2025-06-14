
variable "aws_region" {
  description = "Região da AWS onde será criada a fila"
  type        = string
  default     = "sa-east-1"
}

variable "queue_name" {
  description = "Nome da fila SQS a ser criada"
  type        = string
}

variable "account_id" {
  description = "Número da conta AWS do usuário (somente referência)"
  type        = string
}
