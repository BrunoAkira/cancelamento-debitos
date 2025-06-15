# 💳 Cancelamento de Débitos – API com Java 21, Spring Boot e AWS SQS

Este projeto simula o recebimento de requisições de cancelamento de débito por meio de uma API exposta via API Gateway, respondendo com `OK` e publicando uma mensagem em uma fila SQS da AWS.

---

## ✅ Funcionalidades

- Receber requisição HTTP `POST` com `idTransacao` e `mensagem`.
- Validar se a transação:
    - Existe (mock de repositório simulado em memória).
    - Foi realizada há no máximo 7 dias.
- Retornar `"OK"` se válida.
- Lançar exceções personalizadas para casos inválidos.
- Publicar mensagem no SQS (Amazon Simple Queue Service).

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5**
- **AWS SDK SQS**
- **Maven**
- **Terraform**
- **Arquitetura Hexagonal (Ports & Adapters)**
- **JUnit 5** (testes unitários e integração)
- **Mockito** (mocks para testes)

---

## ▶️ Como executar a aplicação

**⚠️ Atenção:** Antes de executar, edite o arquivo `src/main/resources/application.properties` com a URL real da fila SQS, que será exibida após o `terraform apply`:

```properties
sqs.queue.url=https://sqs.sa-east-1.amazonaws.com/SEU_ACCOUNT_ID/NOME_DA_FILA
```

```bash
mvn spring-boot:run
```

---

### ✅ Exemplo de Requisição

**POST** `http://localhost:8080/api/cancelar`

```json
{
  "idTransacao": "123",
  "mensagem": "Cancelamento de débito indevido"
}
```

**Resposta esperada:**

```
OK
```

---

## 🧪 Testes Automatizados

### 🧩 Testes Unitários
Cobrem o serviço de cancelamento com os seguintes cenários:
- Transação dentro do prazo (deve ser cancelada com sucesso).
- Transação não encontrada (exceção `TransacaoNaoEncontradaException`).
- Transação fora do prazo de 7 dias (exceção `CancelamentoForaDoPrazoException`).

### 🔁 Mock de Repositório
O mock `TransacaoRepositoryFake` simula um repositório de transações. Ele é usado no teste de integração e retorna datas de transações para validar a lógica.

### 🌐 Teste de Integração
Testa o fluxo completo da aplicação, executando uma requisição real para a API `/api/cancelar` e verificando se a resposta e o comportamento estão corretos.

Execute todos os testes com:

```bash
mvn test
```

---

## 📂 Estrutura de Diretórios (Java)

A aplicação segue a **Arquitetura Hexagonal (Ports and Adapters)** com a seguinte organização:

```
cancelamento/src/main/java/br.com.cancelamento
├── adapters/
│   ├── input/                          # Entrada da aplicação (Controller REST)
│   │   ├── CancelamentoController.java
│   │   └── CancelamentoRequest.java
│   └── output/                         # Saída da aplicação (Adapter para publicar no SQS)
│       └── SqsPublicadorAdapter.java
├── application/                        # Casos de uso (regras de negócio)
│   └── CancelamentoService.java
├── domain/
│   └── ports/
│       ├── input/                      # Interface de entrada (Use Case)
│       │   └── CancelamentoUseCase.java
│       └── output/                     # Interface de saída
│           ├── PublicadorEvento.java
│           └── TransacaoRepository.java
├── exceptions/                         # Exceções personalizadas
│   ├── CancelamentoForaDoPrazoException.java
│   └── TransacaoNaoEncontradaException.java
├── integration/                        # Teste de integração
│   └── CancelamentoControllerIntegrationTest.java
├── mocks/                              # Mock de repositório de transações
│   └── TransacaoRepositoryFake.java
├── CancelamentoApplication.java        # Classe principal da aplicação
```

---

## ☁️ Provisionamento com Terraform

1. Edite o arquivo `terraform.tfvars` com seu `account_id` e `queue_name`.

```hcl
account_id = "123456789012"
queue_name = "cancelamento-queue"
aws_region = "sa-east-1"
```

2. Execute:

```bash
terraform init
terraform apply
```

O Terraform cria uma fila SQS e imprime a URL para ser utilizada no `application.properties`.

---

## 📦 Collection Postman

Uma collection `postman_collection.json` atualizada está incluída no projeto. Ela permite testar a API com facilidade usando `idTransacao`.

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