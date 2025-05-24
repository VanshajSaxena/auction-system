AuctionHub is a **REST API application for an auction platform**, built with
**Spring Boot** and following an **API-first development strategy**. This
approach ensures a consistent API contract, robust versioning, and a clear
separation of concerns within the application.

---

## Project Structure

The project follows a well-organized structure, categorizing components by
their purpose (e.g., security, data, web requests). This makes the codebase
easy to navigate and maintain.

```sh
auction-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── auction/
│   │   │           └── system/             # Main application package
│   │   │               ├── config/         # Spring Security configuration classes
│   │   │               ├── controllers/    # API Controllers (delegates to generated interfaces)
│   │   │               ├── entities/       # JPA entities
│   │   │               ├── exception/      # Custom exception handling
│   │   │               ├── filters/        # Request filters (e.g., JWT authentication filter)
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

---

## Features

AuctionHub offers a comprehensive set of features for managing an online
auction platform:

- **User Registration & Authentication**: Secure user registration and login
  using username or email, with **JWT-based authentication** and robust
  **Spring Security integration** for stateless API access.
- **Auction Item Management**: Full **CRUD** (Create, Read, Update, Delete)
  functionality for auction items, including detailed retrieval and listing of
  all available items.
- **Bidding System**: Allows users to **place bids** on auction items, track
  bids for a given item, and enforces business rules like accepting only higher
  bids and respecting auction deadlines.
- **User Management**: Supports registration of new users, management of user
  profiles, and an admin feature to retrieve a list of all registered users.
- **Exception Handling**: Features custom exception handling for clear,
  user-friendly error messages.
- **API Documentation (OpenAPI)**: Provides an OpenAPI/Swagger specification
  for easy API integration and testing.
- **Security**: Includes password encoding and secure storage, **role-based
  access control** for sensitive endpoints, and JWT validation via request
  filters.
- **Extensible Architecture**: Utilizes a layered structure (Controllers,
  Services, Repositories) for maintainability and uses **MapStruct** for
  DTO/entity mapping.
- **Testing**: Comprehensive **unit and integration tests** cover core
  business logic and services.
- **Configuration & Extensibility**: Externalized configuration via
  `application.yaml` allows for easily switchable database and security
  settings.

---

## Prerequisites

To run AuctionHub, you'll need:

- **Java JDK (21)** or later

---

## Getting Started

Follow these steps to set up and run the AuctionHub application.

### Initialization

1. **Clone the repository**:

   ```bash
   git clone https://github.com/VanshajSaxena/auction-system.git
   cd auction-system
   ```

2. **Build the project**:

   ```bash
   ./mvnw clean install
   ```

   This command leverages the **OpenAPI Generator Maven plugin** to generate
   server stubs from the `openapi.api-description.yaml` file. After stub
   generation, it compiles all sources and installs the artifacts into the
   `target` directory.

### Configuration

The primary configuration file is located at
`src/main/resources/application.yaml`. However, the application uses Spring
Profiles for more flexible configuration. You can create
`application-{profile}.yaml` files for different environments (e.g.,
`application-dev.yaml`, `application-prod.yaml`).

To run with a specific profile:

```bash
./mvn spring-boot:run -Dspring-boot.run.profiles={profile}
```

Here are some key configuration properties you might want to adjust:

- **Database Configuration**: By default, an in-memory H2 database is used for
  development. For production or persistent data, configure a database like
  MySQL:

  ```yaml
  # src/main/resources/application-dev.yaml
  spring:
    datasource:
      url: jdbc:mysql://localhost:3306/auction_system
      username: test
      password: password
    jpa:
      hibernate:
        ddl-auto: update
  ```

  Remember to include the appropriate database driver in your `pom.xml`.

- **JWT Secret and Expiry**: Set your JWT token secret and expiration time (in
  milliseconds) for authentication:

  ```yaml
  # src/main/resources/application-prod.yaml
  jwt:
    secret: 3f8f4debeee3a93af3ee723b9af18ce9c4b57c88987f3040bf553db4a808cb8b
    expiryMs: 900000 # 15 mins
  ```

- **Other Properties**: You can customize other Spring Boot properties as
  needed, such as logging levels or actuator settings.

  ```yaml
  # src/main/resources/application-dev.yaml
  logging.level.org.springframework.security: TRACE
  ```

### Running the Application

To start the application, simply run:

```bash
./mvnw spring-boot:run
```

The application will be accessible at `http://localhost:8080` by default.

---

## API Endpoints

- A detailed API overview, including usage and design guidelines, can be found
  in the project's documentation.
- For the complete contract definition, refer to the API description file:
  `src/main/resources/api/openapi.api-description.yaml`.

---

### Notes

- **Authentication**: Most endpoints require a valid **JWT token** in the
  `Authorization: Bearer <token>` header, with exceptions for registration,
  login, and public auction listings.
- **Validation**: Input data is validated server-side, and errors are
  consistently formatted.
- **OpenAPI**: The OpenAPI specification can be used with Swagger tools to
  generate interactive documentation.

---

## Technologies Used

- **Framework**: Spring Boot
- **Language**: Java 21
- **Build Tool**: Apache Maven
- **Database**: MySQL
- **API Schema**: OpenAPI

---

## License

This project is licensed under the **MIT License**. For more details, see the
[LICENSE](LICENSE) file.
