# Auction System API

An auction platform REST API built with Spring Boot, featuring an API-first
design approach for consistent contracts and clean versioning. The system is
secured using JWT and OAuth2.

## Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Architecture](#architecture)
  - [API-First Design](#api-first-design)
  - [Application Layers](#application-layers)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation & Running](#installation--running)
- [API Documentation](#api-documentation)
- [Security](#security)
- [Configuration](#configuration)
- [License](#license)

## Overview

This project provides a robust backend for an auction platform. It exposes a
RESTful API for managing users, items, bids, and auctions. The core philosophy
is "API-first," where the API contract is formally defined before any code is
written, ensuring consistency between the API documentation and its
implementation.

## Key Features

- **RESTful API**: A comprehensive set of endpoints for auction-related
  operations.
- **API-First Design**: Uses OpenAPI 3 to define the API contract,
  automatically generating server-side code stubs.
- **Secure**: Authentication and authorization are handled using Spring
  Security with OAuth2 and JSON Web Tokens (JWT).
- **Data Persistence**: Uses Spring Data JPA with Hibernate for
  object-relational mapping.
- **Database Support**: Configured for both H2 (for development/testing) and
  MySQL (for production).
- **DTO Layer**: Utilizes Data Transfer Objects (DTOs) with MapStruct for clean
  data mapping between layers.
- **Built-in API Docs**: Integrated Swagger UI for interactive API
  documentation and testing.

## Architecture

The application follows a classic layered architecture, common in Spring Boot
applications, with a strong emphasis on the API contract.

### API-First Design

This project is built using an "API-first" approach. The source of truth for
the API is the OpenAPI specification file located at
`src/main/resources/api/openapi.api-description.yaml`.

The build process uses the `openapi-generator-maven-plugin` to:

1. **Generate DTOs**: All model classes (suffixed with `Dto`) are generated
   from the `schemas` defined in the OpenAPI spec.
2. **Generate API Interfaces**: It generates Spring controller interfaces
   (e.g., `UsersApi`) with all the endpoint mappings defined in the `paths`
   section of the spec.

The custom controllers implement these generated interfaces using the
`delegatePattern`. This ensures that the implementation always conforms to the
defined contract. Any changes to the API must be made in the
`openapi.api-description.yaml` file first.

### Application Layers

1. **Controller Layer (`com.auction.system.controller`)**: These classes
   implement the generated `...Api` interfaces. Their primary role is to handle
   incoming HTTP requests, perform validation, and delegate the business logic to
   the service layer.

2. **Service Layer (`com.auction.system.service`)**: This layer contains the
   core business logic of the application. It orchestrates operations,
   interacts with the repository layer, and handles transactions.

3. **Repository Layer (`com.auction.system.repository`)**: This layer is
   responsible for data access. It consists of Spring Data JPA interfaces that
   provide CRUD operations on the database entities.

4. **Domain/Entity Layer (`com.auction.system.entity`)**: These are the JPA
   entities that map to the database tables. They represent the core domain
   objects of the application.

5. **Mappers (`com.auction.system.mapper`)**: These are MapStruct interfaces
   responsible for converting between JPA entities and generated DTOs. This
   decouples the API layer from the data layer.

6. **Exception Handling (`com.auction.system.exception`)**: These are exception
   classes that extend a single centralized `AuctionApplicationException` class
   which itself extends `RuntimeException` class.

   The `com.auction.system.exception.handler.ErrorController` is responsible
   for handling all custom exceptions of the application.

## Technologies Used

- **Framework**: Spring Boot 3
- **Language**: Java 21
- **Build Tool**: Maven
- **Data Persistence**:
  - Spring Data JPA
  - Hibernate
  - H2 Database (for dev)
  - MySQL (for production)
- **API & Documentation**:
  - OpenAPI 3
  - Springdoc (for Swagger UI)
  - OpenAPI Generator
- **Security**:
  - Spring Security
  - OAuth2 Resource Server
  - JSON Web Tokens (JWT)
- **Utilities**:
  - Lombok
  - MapStruct

## Project Structure

```sh
.
├── .mvn/
├── docs/
├── src
│   ├── main
│   │   ├── java/com/auction/system
│   │   │   ├── controller/       # API implementation (delegates)
│   │   │   ├── mapper/           # MapStruct mappers (DTO <-> Entity)
│   │   │   ├── entity/           # JPA Entities
│   │   │   ├── repositorie/      # Spring Data JPA repositories
│   │   │   ├── security/         # Security configuration, JWT utils
│   │   │   └── service/          # Business logic
│   │   └── resources
│   │       ├── api
│   │       │   └── openapi.api-description.yaml # The API contract
│   │       └── application.yml   # Application configuration
│   └── test/                     # Unit and integration tests
├── pom.xml                       # Maven build configuration
└── README.md                     # This file
```

## Getting Started

Follow these instructions to get the project up and running on your local machine.

### Prerequisites

- **Java Development Kit (JDK)**: Version 21 or later.
- **Maven**: Version 3.8 or later.
- **MySQL Server** (Optional): For running with the production profile.

### Installation & Running

1. **Clone the repository:**

   ```sh
   git clone https://github.com/VanshajSaxena/auction-system.git
   cd auction-system
   ```

2. **Build the project:**
   This command will also trigger the `openapi-generator` to create API
   interfaces and DTOs.

   ```sh
   mvn clean install
   ```

3. **Run the application:**
   The application will start using the default `dev` profile, which is
   configured to use an in-memory H2 database.

   ```sh
   mvn spring-boot:run
   ```

   The API server will be running at `http://localhost:8080`.

## API Documentation

Once the application is running, you can access the interactive Swagger UI to
view the API documentation and test the endpoints.

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI Spec (JSON)**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Security

The API is secured using Spring Security and OAuth2. Endpoints require a valid
JWT Bearer token in the `Authorization` header for access. The security
configuration is located in the `com.auction.system.config` package. The
`com.auction.system.filter.JwtAuthenticationFilter` validates the JWT token
signature and extracts user roles and permissions to
enforce access control on different endpoints.

## Configuration

Application settings can be configured in `src/main/resources/application.yml`.
The project is set up with profiles to manage different environments.

- **`dev` (default)**: Uses an in-memory H2 database. The database is reset on
  every application restart.
- **`prod`**: Can be configured to connect to a persistent database like MySQL.
  You can activate it by setting the `spring.profiles.active=prod` property and
  providing the necessary database connection details in `application-prod.yml`.

To run with a specific profile:

```sh
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file
for details.
