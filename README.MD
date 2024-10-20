# Saga Pattern Example

This project is a simplified example of implementing the Saga pattern to handle orders. It demonstrates how to manage distributed transactions across multiple services in a microservices architecture.

## Overview

The Saga pattern is a design pattern used to manage data consistency across multiple services without using distributed transactions. This example project includes the following components:

- **Order Service**: Manages orders and coordinates with other services.
- **Product Service**: Manages products and their availability.
- **User Service**: Manages user information.
- **Payment Service**: Handles payment processing.

## Features

- **Order Creation**: Create new orders and ensure all related services (product, user, payment) are consistent.
- **Order Compensation**: Compensate (rollback) orders if any step in the process fails.
- **Global Exception Handling**: Handle exceptions globally using `@ControllerAdvice`.

## Technologies

- **Java**
- **Spring Boot**
- **Maven**

## Getting Started

1. **Clone the repository**:
    ```sh
    git clone https://github.com/your-repo/saga-pattern-example.git
    cd saga-pattern-example
    ```

2. **Build the project**:
    ```sh
    mvn clean install
    ```

3. **Run the application**:
    ```sh
    mvn spring-boot:run
    ```

## Usage

- **Create a Product**:
    ```sh
    POST /api/products
    {
        "name": "Product A",
        "price": 100.0,
        "type": "ELECTRONICS"
    }
    ```

- **Create an Order**:
    ```sh
    POST /api/orders
    {
        "userId": "user-uuid",
        "productId": "product-uuid"
    }
    ```

- **Compensate an Order**:
    ```sh
    DELETE /api/orders/{orderId}
    ```

## License
@Copyright Dariusz Chmiela.