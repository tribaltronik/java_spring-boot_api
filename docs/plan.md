# Development Plan

This document outlines the steps to develop the PoC API based on the specifications in `spec.md`.

## 1. Project Setup

*   [x] Create the project structure:
    *   [x] Create `src/main/java` and `src/test/java` directories.
    *   [x] Create a `pom.xml` file with the necessary Spring Boot dependencies (Spring Web, Spring Data JPA, Spring Security, JWT library, H2 Database, etc.).
    *   [x] Create the main application class.

## 2. Data Model

*   [x] Create the `Product` entity class in `src/main/java/com/example/pocapi/model`.
    *   [x] Define the fields: `id`, `name`, `description`, `price`.
    *   [x] Add JPA annotations.

## 3. Repository

*   [x] Create the `ProductRepository` interface in `src/main/java/com/example/pocapi/repository`.
    *   [x] Extend `JpaRepository`.

## 4. Service

*   [x] Create the `ProductService` interface and its implementation in `src/main/java/com/example/pocapi/service`.
    *   [x] Implement the business logic for CRUD operations on products.

## 5. Controller

*   [x] Create the `ProductController` class in `src/main/java/com/example/pocapi/controller`.
    *   [x] Implement the REST endpoints for `GET`, `POST`, `PUT`, `DELETE` on `/products`.
    *   [x] Use DTOs (Data Transfer Objects) for request and response bodies.

## 6. Security

*   [x] Configure Spring Security.
*   [x] Implement JWT generation and validation.
*   [x] Create a `User` entity and `UserRepository`.
*   [x] Create an authentication endpoint (`/auth/login`).
*   [x] Secure the `ProductController` endpoints.

## 7. Error Handling

*   [x] Create a global exception handler using `@ControllerAdvice`.
*   [x] Define custom exception classes for specific error scenarios.

## 8. Testing

*   [x] Write unit tests for the `ProductService`.
*   [x] Write integration tests for the `ProductController`.

## 9. Dockerization

*   [x] Create a `Dockerfile` to build the application image.
*   [x] Create a `docker-compose.yml` file to run the application and a database (e.g., PostgreSQL).

## 10. Documentation

*   [x] Update the `openapi.yaml` file with the API specification.

## 11. Swagger UI

*   [x] Add Springdoc OpenAPI dependency to `pom.xml`.
*   [x] Configure Swagger UI endpoint in `application.properties`.
*   [x] Update SecurityConfig to permit Swagger UI endpoints.
