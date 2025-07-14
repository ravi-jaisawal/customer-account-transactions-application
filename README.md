## 📋 Project Overview

This project simulates a basic financial transaction service. A customer can create an account and perform operations such as purchases, installment purchases, withdrawals, and payments. Each operation is logged as a transaction and tied to a customer account.

---

## 🧑‍💻 Tech Stack

- Java 17
- Spring Boot (if using Java)
- Swagger/OpenAPI for API documentation
- JUnit (Java) or Go's testing package for unit tests

---

## 📦 Features (Phase 1)

### ✅ Accounts

- `POST /accounts`  
  Create a new account using a unique document number.

- `GET /accounts/{accountId}`  
  Retrieve account details by ID.

### ✅ Transactions

- `POST /transactions`  
  Register a transaction against an account with an operation type and amount.

---

## 🧾 Data Structures

### Accounts
| Field         | Type   |
|---------------|--------|
| account_id    | Long   |
| document_number | String |

### Operation Types
| ID | Type                 |
|----|----------------------|
| 1  | PURCHASE             |
| 2  | INSTALLMENT PURCHASE|
| 3  | WITHDRAWAL          |
| 4  | PAYMENT             |

### Transactions
| Field           | Type   |
|----------------|--------|
| transaction_id | Long   |
| account_id     | Long   |
| operation_type_id | Int |
| amount         | Double |
| event_date     | DateTime |

---

## 📂 API Examples

### Create Account
**Request**
```http
POST /accounts
Content-Type: application/json

{
  "document_number": "12345678900"
}

Response
{
  "account_id": 1,
  "document_number": "12345678900"
}

Get Account
Request

GET /accounts/1
Response

{
  "account_id": 1,
  "document_number": "12345678900"
}

Create Transaction

POST /transactions
Content-Type: application/json

{
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 123.45
}

Response
{
  "transaction_id": 1,
  "account_id": 1,
  "operation_type_id": 4,
  "amount": 123.45
}

🧪 Run Tests
If you're using Java with Maven:

./mvnw clean test

Once the app is running, access Swagger UI at:

http://localhost:8080/swagger-ui.html

###Build & Run the Application

##Step 1: Build the Spring Boot application JAR
If using Java:

./mvnw clean package -DskipTests
Ensure the JAR is generated in the target/ directory.

##Step 2: Start the services
docker-compose up --build

This will:

Start a PostgreSQL container on port 5432

Build and run the Spring Boot app on port 8080

##Step 3: Access the application
API Base URL: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui.html

Stop the services

docker-compose down

Add -v if you want to clear database volumes:

docker-compose down -v

