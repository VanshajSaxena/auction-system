# Auction System API

A REST API application for an auction platform, implemented in Spring Boot
using an API-first development strategy to ensure consistent contract design,
robust versioning, and clean separation of concerns.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-layout)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Technologies Used](#technologies-used)
- [License](#license)

## Features

<details>
  <summary><strong>User Registration & Authentication</strong></summary>
  <ul>
    <li>Secure user registration and login using username or email.</li>
    <li>JWT-based authentication for stateless and secure API access.</li>
    <li>Spring Security integration for robust authentication and authorization.</li>
  </ul>
</details>

<details>
  <summary><strong>Auction Item Management</strong></summary>
  <ul>
    <li>Create, read, update, and delete (CRUD) auction items.</li>
    <li>Retrieve details for individual auction items.</li>
    <li>List all available auction items.</li>
  </ul>
</details>

<details>
  <summary><strong>Bidding System</strong></summary>
  <ul>
    <li>Place bids on auction items.</li>
    <li>Track and retrieve all bids for a given item.</li>
    <li>Enforce business rules for bidding (e.g., only higher bids are accepted, auction deadlines).</li>
  </ul>
</details>

<details>
  <summary><strong>User Management</strong></summary>
  <ul>
    <li>Register new users and manage user profiles.</li>
    <li>Retrieve a list of all registered users (admin feature).</li>
  </ul>
</details>

<details>
  <summary><strong>Exception Handling</strong></summary>
  <ul>
    <li>Custom exception handling for clear, user-friendly error messages.</li>
  </ul>
</details>

<details>
  <summary><strong>API Documentation (OpenAPI)</strong></summary>
  <ul>
    <li>OpenAPI/Swagger specification provided for easy integration and testing.</li>
  </ul>
</details>

<details>
  <summary><strong>Security</strong></summary>
  <ul>
    <li>Password encoding and secure storage.</li>
    <li>Role-based access control for sensitive endpoints.</li>
    <li>JWT validation via request filters.</li>
  </ul>
</details>

<details>
  <summary><strong>Extensible Architecture</strong></summary>
  <ul>
    <li>Layered structure (Controllers, Services, Repositories) for maintainability.</li>
    <li>Use of MapStruct for DTO/entity mapping.</li>
  </ul>
</details>

<details>
  <summary><strong>Testing</strong></summary>
  <ul>
    <li>Unit and integration tests for core business logic and services.</li>
  </ul>
</details>

<details>
  <summary><strong>Configuration & Extensibility</strong></summary>
  <ul>
    <li>Externalized configuration via <code>application.yaml</code>.</li>
    <li>Easily switchable database and security settings.</li>
  </ul>
</details>

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
│   │   │           └── system/     # Main application package
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
│   │       │   └── openapi.api-description.yaml # API description file
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.yaml            # Application properties
│   └── test/                               # Test sources
│       └── java/
│           └── com/
│               └── auction/
│                   └── system/
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

### Initialization

1. Clone the repository:

   ```bash
   git clone https://github.com/VanshajSaxena/auction-system.git
   cd auction-system
   ```

2. Build the project:

   ```bash
   ./mvnw clean install
   ```

   This command uses the [OpenAPI
   Generator](https://github.com/OpenAPITools/openapi-generator) Maven plugin
   to generate server stubs from the API description file located at
   [`src/main/resources/api/openapi.api-description.yaml`](./src/main/resources/api/openapi.api-description.yaml).

   After generating the stubs, it compiles both the main and test sources, and
   installs the resulting artifacts.

   The compiled outputs, including the generated sources and packaged
   application files, will be available in the `target` directory.

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
    development. You can set up a database server like this:

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

  - Set your JWT token expiration (in milliseconds) for authentication:

    ```yaml
    jwt:
      expiryMs: 900000 # 15 mins
    ```

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

- You can find an overview of the API [here](./docs/api/README.md).
- You could directly check out the API description file [here](./src/main/resources/api/openapi.api-description.yaml).

---

### Notes

- **Authentication:** Most endpoints (except registration, login, and public auctions listing) require a valid JWT token in the `Authorization: Bearer <token>` header.
- **Validation:** Input data is validated server-side; errors are returned in a consistent format.
- **OpenAPI:** For a complete list of endpoints, parameters, and models, refer to the OpenAPI spec or generate interactive documentation using Swagger tools.

## Technologies Used

- **Framework**: Spring Boot
- **Language**: Java 21
- **Build Tool**: Apache Maven
- **Database**: MySQL
- **API Schema**: OpenAPI

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.
