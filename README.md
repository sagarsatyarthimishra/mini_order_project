# Mini Order-Product Microservices

This project is a microservices-based system for managing products and orders, built with Spring Boot, MongoDB, Redis, and Docker Compose.  
It demonstrates best practices such as layered architecture, RESTful APIs, inter-service communication, Swagger documentation, and unit testing.

---

## Table of Contents

- [Project Structure](#project-structure)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup Instructions](#setup-instructions)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Folder Structure](#folder-structure)
- [Notes](#notes)

---

## Project Structure

- **product-service**: Manages products and their stock.
- **order-service**: Handles order creation and queries, and communicates with product-service to update stock.
- **MongoDB**: Stores product and order data.
- **Redis**: (Optional) Used for caching in order-service.
- **docker-compose.yml**: Orchestrates all services.

---

## Features

- **Layered Architecture**: Controller → Service → Repository
- **RESTful APIs**: For products and orders
- **Inter-Service Communication**: Order-service updates product stock via product-service API
- **Swagger UI**: Interactive API documentation for both services
- **Unit Tests**: For business logic
- **Dockerized**: Easy setup and deployment

---

## Tech Stack

- Java 21, Spring Boot 3.x
- MongoDB
- Redis
- Docker & Docker Compose
- Maven
- Lombok
- Swagger (springdoc-openapi)

---

## Setup Instructions

### 1. Clone the Repository

git clone https://github.com/sagarsatyarthimishra/mini_order_project
cd mini-order-product


### 2. Build the Services

Build both microservices (from project root):

cd product-service
./mvnw clean package
cd ../order-service
./mvnw clean package
cd ..

*Or use `mvn clean package` if Maven is installed globally.*

### 3. Start All Services with Docker Compose

docker-compose up --build

This will start:
- MongoDB (port 27017)
- Redis (port 6379)
- Product Service (port 8081)
- Order Service (port 8082)

### 4. Access the Services

- **Product Service:** [http://localhost:8081](http://localhost:8081)
- **Order Service:** [http://localhost:8082](http://localhost:8082)
- **Swagger UI (Product):** [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- **Swagger UI (Order):** [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

---

## API Documentation

Both services expose Swagger UI for easy API exploration.

### Product Service

- `POST /products` — Add a new product
- `GET /products` — List all products
- `GET /products/{id}` — Get product by ID
- `PUT /products/{id}/decrease-stock?quantity={qty}` — Decrease product stock

### Order Service

- `POST /orders` — Place a new order (automatically decreases product stock)
- `GET /orders` — List all orders

---

## Testing

- Unit tests are located in each service under `src/test/java/...`
- To run tests:
  cd product-service
  ./mvnw test
  cd ../order-service
  ./mvnw test

---

## Folder Structure

```
mini-order-product/
│
├── docker-compose.yml
├── product-service/
│   ├── src/
│   │   ├── main/java/com/example/productservice/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   └── model/
│   │   └── test/java/com/example/productservice/
│   ├── pom.xml
│   └── Dockerfile
│
├── order-service/
│   ├── src/
│   │   ├── main/java/com/example/orderservice/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   └── model/
│   │   └── test/java/com/example/orderservice/
│   ├── pom.xml
│   └── Dockerfile
│
└── ...
```

---

## Notes

- **Inter-service communication**: Order service uses `WebClient` to call product service for stock updates.
- **Environment variables**: Database and service URLs are configured in `docker-compose.yml`.
- **Lombok**: Used for reducing boilerplate code in models.
- **Error Handling**: Basic error handling is implemented; for production, enhance as needed.

---

**Feel free to reach out if you have any questions or need

