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

_(List any software or tools that need to be installed before running the application, e.g., Java JDK, Maven, specific database.)_

- Java JDK (version)
- Apache Maven (version)
- (Any other dependencies like a database server)

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

_(Details on how to configure the application, e.g., database connection strings in `application.properties`.)_

### Running the Application

```bash
./mvnw spring-boot:run
```

The application will be accessible at `http://localhost:8080` (or as configured).

## API Endpoints

_(A detailed list of the API endpoints, including HTTP methods, request/response formats, and example usage. This can be extensive, so consider linking to a separate API documentation file if needed.)_

**Example:**

- **GET /api/items**: Get a list of all auction items.
- **POST /api/items**: Create a new auction item.

  - Request Body:

    ```json
    {
      "name": "Vintage Watch",
      "description": "A rare vintage watch from the 1950s.",
      "startingPrice": 100.0
    }
    ```

- **GET /api/items/{id}**: Get details of a specific auction item.
- **POST /api/bids**: Place a bid on an item.

  - Request Body:

    ```json
    {
      "itemId": 1,
      "userId": 123,
      "amount": 150.0
    }
    ```

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
