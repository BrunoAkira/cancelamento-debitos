# 💳 Cancelamento de Débitos – API com Java 21, Spring Boot e AWS SQS

Este projeto simula o recebimento de requisições de cancelamento de débito por meio de uma API exposta via API Gateway, respondendo com `OK` e publicando uma mensagem em uma fila SQS da AWS.

---

## ✅ Funcionalidades

- Receber requisição HTTP `POST` com `número da conta` e `mensagem`.
- Retornar `"OK"` como resposta.
- Publicar mensagem no SQS (Amazon Simple Queue Service).

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5**
- **AWS SDK SQS**
- **Maven**
- **Terraform**
- **Arquitetura Hexagonal (Ports & Adapters)**

---

## ▶️ Como executar a aplicação

**⚠️ Atenção:** Antes de executar, edite o arquivo `src/main/resources/application.properties` com a URL real da fila SQS, que será exibida após o `terraform apply`:

```
sqs.queue.url=https://sqs.sa-east-1.amazonaws.com/SEU_ACCOUNT_ID/NOME_DA_FILA
```


```bash
mvn spring-boot:run
```

### Exemplo de requisição (via Postman ou cURL)

**POST** `http://localhost:8080/api/cancelar`

```json
{
  "numeroConta": "123456",
  "mensagem": "Cancelar débito em duplicidade"
}
```

**Resposta esperada:**

```
OK
```

---

## 📂 Estrutura de Diretórios (Java)

A aplicação segue a **Arquitetura Hexagonal (Ports and Adapters)** com a seguinte organização:

```
cancelamento/src/main/java/br.com.cancelamento
├── adapters/
│   ├── input/                # Entrada da aplicação (Controller REST)
│   │   ├── CancelamentoController.java
│   │   └── CancelamentoRequest.java
│   └── output/               # Saída da aplicação (Adapter para publicar no SQS)
│       └── SqsPublicadorAdapter.java
├── application/              # Casos de uso (implementações de serviços de domínio)
│   └── CancelamentoService.java
├── domain/
│   └── ports/
│       ├── input/            # Interface de entrada (Use Case)
│       │   └── CancelamentoUseCase.java
│       └── output/           # Interface de saída (Port de publicação)
│           └── PublicadorEvento.java
└── CancelamentoApplication.java # Classe principal da aplicação (Spring Boot)
```

---

## ☁️ Provisionamento com Terraform

1. Edite o arquivo `terraform.tfvars` com seu `account_id` e `queue_name`.

```hcl
account_id = "123456789012"
queue_name = "cancelamento-queue"
```

2. Execute:

```bash
terraform init
terraform apply
```

O Terraform cria uma fila SQS e imprime a URL para ser utilizada no código.

---

## 📦 Collection Postman

Uma collection `postman_collection.json` está incluída no projeto. Ela permite testar a API com facilidade.

---

## 📄 .gitignore

O arquivo `.gitignore` serve para **ignorar arquivos** que não devem ser versionados no Git, como:

- Diretórios `target/`
- Arquivos `.class`
- Arquivos de configuração da IDE (ex: `.idea/`, `*.iml`)
- `.terraform/` e `terraform.tfstate`

---

## 📋 Autor

Bruno Akira – 2025

---