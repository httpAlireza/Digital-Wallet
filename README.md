# Digital Wallet Application

A secure **Digital Wallet REST API** built with **Spring Boot 3** (Java 21), JWT authentication, and PostgreSQL for
persistence. This application allows users to manage wallets, perform deposits, withdrawals, transfers, and track
transactions. The API follows RESTful principles and includes JWT-based authentication and role-based authorization.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Running Locally](#running-locally)
    - [Docker Deployment](#docker-deployment)
- [API Endpoints](#api-endpoints)
    - [Authentication](#authentication-apis)
    - [Wallet Management](#wallet-apis)
- [Database Migrations](#database-migrations)
- [Swagger Documentation](#swagger-documentation)

---

## Features

- User registration and login with JWT authentication.
- Change password functionality.
- Create and manage multiple wallets per user.
- Deposit, withdraw, and transfer funds between wallets.
- Transaction history with filtering and pagination.
- Secure API endpoints with ownership checks (`@PreAuthorize`).
- Flyway-based database migrations.
- Dockerized deployment for easy setup.
- OpenAPI/Swagger documentation for API exploration.

---

## Tech Stack

- **Backend:** Spring Boot 3, Spring Security, Spring Data JPA
- **Database:** PostgreSQL
- **Authentication:** JWT (JSON Web Tokens)
- **ORM:** Hibernate
- **Migrations:** Flyway
- **API Docs:** OpenAPI / Swagger
- **Java Version:** 21
- **Containerized deployment:** Docker

---

## Getting Started

### Prerequisites

- Java 21
- Maven
- PostgreSQL (or any other supported relational database)
- Docker (optional, for containerized deployment)

### Running Locally

1. Clone the repository:

```bash
git clone git@github.com:httpAlireza/Digital-Wallet.git
cd digital-wallet
```

2. Set up PostgreSQL database and configure credentials in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/digitalwallet
    username: walletuser
    password: walletpass
```

a docker-compose file is provided for easy setup of PostgreSQL in local environment.
if you want to use it, run:

```bash
docker-compose up -d
```

3. Build and run the application (`SPRING_PROFILES_ACTIVE` environment variable must be set to `dev` or `prd` as
   needed):

```bash
mvn clean install
mvn spring-boot:run
```

The API will start at `http://localhost:8080`.

---

### Docker Deployment

A `Dockerfile` is provided in the project root. To build and run the Docker container:

1. Build the Docker image:

```bash
mvn clean package
docker build -t digital-wallet .
```

2. Run the container:

```bash
docker run -p 8080:8080 digital-wallet
```

The application will be accessible at `http://localhost:8080`.

---

## API Endpoints

### Authentication APIs

| Method | Path                       | Request Body            | Response       | Description                             |
|--------|----------------------------|-------------------------|----------------|-----------------------------------------|
| POST   | `/v1/auth/signup`          | `SignupRequest`         | `AuthResponse` | Register a new user and receive JWT.    |
| POST   | `/v1/auth/login`           | `LoginRequest`          | `AuthResponse` | Log in and receive JWT.                 |
| POST   | `/v1/auth/change-password` | `ChangePasswordRequest` | `String`       | Change password for authenticated user. |

---

### Wallet APIs

| Method | Path                                  | Request Body            | Response                    | Authorization | Description                                 |
|--------|---------------------------------------|-------------------------|-----------------------------|---------------|---------------------------------------------|
| POST   | `/v1/wallets`                         | `WalletCreationRequest` | `WalletDto`                 | Authenticated | Create a new wallet.                        |
| GET    | `/v1/wallets`                         | -                       | `List<WalletDto>`           | Authenticated | Get all wallets for the authenticated user. |
| GET    | `/v1/wallets/{walletId}`              | -                       | `WalletDto`                 | Owner only    | Get wallet details.                         |
| GET    | `/v1/wallets/{walletId}/transactions` | -                       | `Paginated<TransactionDto>` | Owner only    | Filter wallet transactions.                 |
| POST   | `/v1/wallets/{walletId}/deposit`      | `DepositRequest`        | `String`                    | Owner only    | Deposit money into a wallet.                |
| POST   | `/v1/wallets/{walletId}/withdraw`     | `WithdrawRequest`       | `String`                    | Owner only    | Withdraw money from a wallet.               |
| POST   | `/v1/wallets/{walletId}/transfer`     | `TransferRequest`       | `String`                    | Owner only    | Transfer money to another wallet.           |

---

## Database Migrations

Flyway is used to manage database migrations. Place migration SQL files in:

```
src/main/resources/db/migration
```

Migrations are executed automatically on application startup.

---

## Swagger Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI docs are exposed at:

```
http://localhost:8080/v3/api-docs
```

