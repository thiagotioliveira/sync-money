# Sync-Money

## Overview

This is a financial control system, designed to manage bank accounts, categories, financial transactions, payables, and receivables.

This project is a Proof of Concept (PoC) for studying Domain-Driven Design (DDD) using Clean Architecture principles.

## Features

- Java 21+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Liquibase for database versioning
- Maven
- Docker

## Prerequisites

To work with this project, you will need:

- Java 21 or later.

## Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```

2. **Configure the database**:
   Using `test` profile, the PostgreSQL will be provided by Docker with [compose.yaml](compose.yaml)

3. **Build the project**:
   Use Maven to build the project:
   ```bash
   mvn clean install
   ```
   Or use [mvnw](mvnw)

4. **Deploy to an application server**:
   Use `Dockerfile` to generate image.

5. **Access the application**:
   Open a browser and navigate to:
   ```
   http://<server-host>:<server-port>/swagger-ui/index.html
   ```
## Data Model Description

### Organizations

* Represents the company or organization. Basically is the way to manager N accounts.

### Users

* Linked to an organization.
* Can be associated with the transactions they performed (`transactions.user_id`).

### Categories

* Classifications for transactions and payables/receivables.
* Can be of type `CREDIT` or `DEBIT`.
* Can be global (`organization_id = null`) or specific to an organization.

### Banks

* Represent banking institutions associated with an organization.

### Accounts

* Bank accounts belonging to an organization and linked to a bank.
* Have balances

### Payable/Receivable

* Represents financial commitments to pay or receive.
* Associated with an `account`, `category`, and `organization`.
* Can be recurring and paid in installments.

### Transactions

* Financial movements.
* Can be linked to an `account`, `category`, `organization`, `user`, and a `payable/receivable`.

### Account Summary

* Monthly summary of an account.
* Contains balances and monthly totals.

## 🔌 REST Endpoints

| Method  | Endpoint                                                   | Description                                                     | Protected (JWT) |
|---------|------------------------------------------------------------|-----------------------------------------------------------------|-----------------|
| `POST`  | `/api/auth/register`                                       | Register a new user                                             | ❌              |
| `POST`  | `/api/auth/login`                                          | Login                                                           | ❌              |
| `GET`   | `/api/users`                                               | List all users                                                  | ✅              |
| `GET`   | `/api/users/me`                                            | Get user info                                                   | ✅              |
| `POST`  | `/api/banks`                                               | Create a bank                                                   | ✅              |
| `POST`  | `/api/accounts`                                            | Create a account                                                | ✅              |
| `GET`   | `/api/accounts`                                            | List all accounts                                               | ✅              |
| `GET`   | `/api/accounts/{id}`                                       | Get account by id                                               | ✅              |
| `POST`  | `/api/categories`                                          | Create a category                                               | ✅              |
| `GET`   | `/api/categories`                                          | List all categories                                             | ✅              |
| `GET`   | `/api/categories/{id}`                                     | Get category by id                                              | ✅              |
| `GET`   | `/api/account/{accountId}/trasactions/{yearMonth}`         | Get transactions for a account in a month                       | ✅              |
| `PATCH` | `/api/account/{accountId}/trasactions/{transactionId}`     | Update a scheduled transaction                                  | ✅              |
| `POST`  | `/api/account/{accountId}/trasactions/{transactionId}/pay` | Pay a scheduled transaction                                     | ✅              |
| `POST`  | `/api/account/{accountId}/deposit`                         | Create a deposit in a account (creates paid transaction)        | ✅              |
| `POST`  | `/api/account/{accountId}/withdraw`                        | Create a withdraw in a account (creates paid transaction)       | ✅              |
| `POST`  | `/api/account/{accountId}/payables`                        | Create a payable in a account (creates scheduled transaction)   | ✅              |
| `POST`  | `/api/account/{accountId}/receivables`                     | Create a receivable in a account (creates scheduled transaction)| ✅              |

🔒 Legend:

   * ✅ = Requires valid JWT in Authorization: Bearer <token>
   * ❌ = Public endpoint (no authentication required)

🔐 Authentication:

All protected endpoints require a valid JWT token to be included in the request header:

```
   Authorization: Bearer <your-token>
```

Example:

```
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

You receive this token when you log in via the /api/auth/login endpoint.
Use this token to authenticate requests to all endpoints marked as protected in the table above.

## Folder Structure

- `src/main/java`: Contains the main application source code.
- `src/main/resources`: Configuration files and resources.
- `src/test/java`: Unit and integration tests.

## License

This project is licensed under [MIT License](LICENSE).

## Support

If you encounter any issues or have questions, feel free to open an issue in the repository or contact the repository
maintainers.