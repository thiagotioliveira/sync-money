# Sync-Money

## Overview

This is a financial control system, designed to manage bank accounts, categories, financial transactions, payables, and receivables.

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
   Update the `persistence.xml` file with your database connection details.

3. **Build the project**:
   Use Maven to build the project:
   ```bash
   mvn clean install
   ```

4. **Deploy to an application server**:
   Use `Dockerfile` to generate image.

5. **Access the application**:
   Open a browser and navigate to:
   ```
   http://<server-host>:<server-port>/swagger-ui/index.html
   ```
## 🔌 REST Endpoints

| Method  | Endpoint                                                   | Description                               |
|---------|------------------------------------------------------------|-------------------------------------------|
| `POST`  | `/api/auth/register`                                       | Register a new user                       |
| `POST`  | `/api/auth/login`                                          | Login                                     |
| `GET`   | `/api/users`                                               | List all users                            |
| `GET`   | `/api/users/me`                                            | Get user info                             |
| `POST`  | `/api/banks`                                               | Create a bank                             |
| `POST`  | `/api/accounts`                                            | Create a account                          |
| `GET`   | `/api/accounts`                                            | List all accounts                         |
| `GET`   | `/api/accounts/{id}`                                       | Get account by id                         |
| `POST`  | `/api/categories`                                          | Create a category                         |
| `GET`   | `/api/categories`                                          | List all categories                       |
| `GET`   | `/api/categories/{id}`                                     | Get category by id                        |
| `GET`   | `/api/account/{accountId}/trasactions/{yearMonth}`         | Get transactions for a account in a month |
| `PATCH` | `/api/account/{accountId}/trasactions/{transactionId}`     | Update a scheduled transaction            |
| `POST`  | `/api/account/{accountId}/trasactions/{transactionId}/pay` | Pay a scheduled transaction               |
| `POST`  | `/api/account/{accountId}/deposit`                         | Create a deposit in a account             |
| `POST`  | `/api/account/{accountId}/withdraw`                        | Create a withdraw in a account            |
| `POST`  | `/api/account/{accountId}/payables`                        | Create a payable in a account             |
| `POST`  | `/api/account/{accountId}/receivables`                     | Create a receivable in a account          |

## Folder Structure

- `src/main/java`: Contains the main application source code.
- `src/main/resources`: Configuration files and resources.
- `src/test/java`: Unit and integration tests.

## License

This project is licensed under [MIT License](LICENSE).

## Support

If you encounter any issues or have questions, feel free to open an issue in the repository or contact the repository
maintainers.