# Auction System API

A Spring Boot REST API application designed for a fictional auction system,
implemented with a focus on adhering to best practices.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-layout)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Authentication](#authentication)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Registration & Authentication**

  - Secure user registration and login using username or email.
  - JWT-based authentication for stateless and secure API access.
  - Spring Security integration for robust authentication and authorization.

- **Auction Item Management**

  - Create, read, update, and delete (CRUD) auction items.
  - Retrieve details for individual auction items.
  - List all available auction items.

- **Bidding System**

  - Place bids on auction items.
  - Track and retrieve all bids for a given item.
  - Enforce business rules for bidding (e.g., only higher bids are accepted, auction deadlines).

- **User Management**

  - Register new users and manage user profiles.
  - Retrieve a list of all registered users (admin feature).

- **Exception Handling**

  - Custom exception handling for clear, user-friendly error messages.

- **API Documentation (OpenAPI)**

  - OpenAPI/Swagger specification provided for easy integration and testing.

- **Security**

  - Password encoding and secure storage.
  - Role-based access control for sensitive endpoints.
  - JWT validation via request filters.

- **Extensible Architecture**

  - Layered structure (Controllers, Services, Repositories) for maintainability.
  - Use of MapStruct for DTO/entity mapping.

- **Testing**

  - Unit and integration tests for core business logic and services.

- **Configuration & Extensibility**
  - Externalized configuration via `application.yaml`.
  - Easily switchable database and security settings.

## Project Layout

The project is cleanly organized by purpose—like handling security, data, and
web requests—making it easy to follow and maintain.

```sh
auction-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── auction/
│   │   │           └── auction_system/     # Main application package
│   │   │               ├── config/         # Spring Security configuration classes
│   │   │               ├── controllers/    # API Controllers (delegates to generated interfaces)
│   │   │               ├── entities/       # JPA entities
│   │   │               ├── exception/      # Custom exception handling
│   │   │               ├── filters/        # Request filters (e.g. JWT authentication filter)
│   │   │               ├── mappers/        # MapStruct mappers
│   │   │               ├── repositories/   # Spring Data JPA repositories
│   │   │               ├── security/       # Security related components
│   │   │               ├── services/       # Business logic interfaces
│   │   │               │   └── impl/       # Interface implementations
│   │   │               └── AuctionSystemApplication.java # Spring Boot main class
│   │   └── resources/
│   │       ├── api/
│   │       │   └── openapi.api-spec.yaml   # API description file
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.yaml            # Application properties
│   └── test/                               # Test sources
│       └── java/
│           └── com/
│               └── auction/
│                   └── auction_system/
│                       ├── services/
│                       │   └── impl/       # Service related tests
│                       └── testutils/      # Test utility classes
├── mvnw                                    # Maven wrapper executable (Linux/MacOS)
├── mvnw.cmd                                # Maven wrapper executable (Windows)
└── pom.xml                                 # Maven Project Object Model
```

## Prerequisites

- Java JDK (21)
- Apache Maven (3.9.9)

## Getting Started

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/VanshajSaxena/auction-system.git
   cd auction-system
   ```

2. Build the project:

   ```bash
   ./mvnw clean install
   ```

### Configuration

Before running the application, you may need to configure certain settings to
match your environment and preferences. The main configuration file is located
at:

```
src/main/resources/application.yaml
```

### Key Configuration Properties

- **Database Configuration**

  - By default, the application may use an H2 in-memory database for
    development. You can set up and database server you like below:

    ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/auction_system
        username: test
        password: password
      jpa:
        hibernate:
          ddl-auto: update
    ```

  - Make sure the appropriate database driver is included in your `pom.xml` dependencies.

- **JWT Secret and Expiry**

  - Set your JWT secret key and token expiration (in milliseconds) for authentication:

    ```yaml
    jwt:
      secret: your-very-secure-secret-key
      expiryMs: 86400000 # 24 hours
    ```

> **Note:** Do not commit sensitive information such as passwords or secret
> keys to version control. Use environment variables or a secrets manager for
> production deployments where possible.

- **Other Properties**

  - You can further customize logging, actuator, mail, or any other Spring Boot
    supported properties as required.

```yaml
logging.level.org.springframework.security: TRACE
```

### Running the Application

```bash
./mvnw spring-boot:run
```

The application will be accessible at `http://localhost:8080` (or as configured).

## API Endpoints

The Auction System exposes a set of RESTful endpoints for managing users, auctions, and bids. All endpoints accept and return JSON unless otherwise specified. For full details, refer to the included OpenAPI specification (`src/main/resources/api/openapi.api-spec.yaml`) or use tools like Swagger UI for interactive exploration.

### Authentication

- `POST /api/v1/auth/register`  
  Register a new user.  
  **Request Body:**

  ```json
  {
    "firstname": "Elizabeth"
    "lastname": "Gomez"
    "username": "eligmz",
    "email": "eliza@example.com",
    "password": "yourpassword"
  }
  ```

  **Response:**  
  201 Created or error details.

- `POST /api/v1/auth/login`  
  Authenticate a user and receive a JWT token.  
  **Request Body:**

  ```json
  {
    "username": "eligmz",
    "email": "eliza@example.com",
    "password": "yourpassword"
  }
  ```

  **Response:**

  ```json
  {
    "token": "<JWT token>",
    "expiresIn": 86400000
  }
  ```

---

### Users

- `GET /api/v1/users`  
  Retrieve a list of all registered users.  
  **Authentication:** Required (Admin or elevated role).

---

### Auctions

- `GET /api/v1/auctions`  
  List all auction items.

- `GET /api/v1/auctions/{auctionId}`  
  Get details of a specific auction item.

- `POST /api/v1/auctions`  
  Create a new auction item.  
  **Authentication:** Required
  **Request Body:**

  ```json
  {
    "title": "Vintage Watch",
    "description": "A rare vintage watch from the 1950s.",
    "startingPrice": 100.0,
    "expirationTime": "2025-06-01T12:00:00Z"
  }
  ```

- `PUT /api/v1/auctions/{auctionId}`  
  Update an existing auction item.  
  **Authentication:** Required (Owner or admin)

- `DELETE /api/v1/auctions/{auctionId}`  
  Delete an auction item.  
  **Authentication:** Required (Owner or admin)

---

### Bids

- `GET /api/v1/auctions/{auctionId}/bids`  
  Retrieve all bids for a specific auction item.

- `POST /api/v1/auctions/{auctionId}/bids`  
  Place a bid on an auction item.  
  **Authentication:** Required  
  **Request Body:**

  ```json
  {
    "amount": 150.0
  }
  ```

  **Response:**  
  201 Created if bid is valid; error if bid is lower than current or auction is closed.

---

### Error Handling

- All unsuccessful requests return JSON error messages with appropriate HTTP status codes and details.

---

### Notes

- **Authentication:** Most endpoints (except registration, login, and public auctions listing) require a valid JWT token in the `Authorization: Bearer <token>` header.
- **Validation:** Input data is validated server-side; errors are returned in a consistent format.
- **OpenAPI:** For a complete list of endpoints, parameters, and models, refer to the OpenAPI spec or generate interactive documentation using Swagger tools.

## Database

_(Information about the database schema, migrations (if any), and how to set it up.)_

## Authentication

_(Details about how authentication and authorization are implemented, e.g., Spring Security, JWT, OAuth2.)_

## Technologies Used

- **Framework**: Spring Boot
- **Language**: Java
- **Build Tool**: Apache Maven
- **Database**: (Specify database, e.g., PostgreSQL, MySQL, H2)
- (List other key libraries or frameworks)

## Contributing

_(Guidelines for contributing to the project, if applicable.)_

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## License

This project is licensed under the (Specify License, e.g., MIT License) - see the [LICENSE.md](LICENSE.md) file for details.
