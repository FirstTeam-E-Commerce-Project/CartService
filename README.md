# Overview

The Cart Service is a core service of the e-commerce microservices project. It handles shopping cart functionalities, including adding products, updating quantities, removing items, and clearing the cart. The service is built using Spring Boot and integrates with other microservices such as User Service, and Order Service.

Features

Add products to the cart

Update product quantity

Remove products from the cart

Clear the cart

Retrieve cart details for a user

Kafka-based event-driven communication

Secure API endpoints using JWT authentication

Database persistence using PostgreSQL

Tech Stack

Backend: Java 17, Spring Boot, Spring Web, Spring Data JPA

Database: PostgreSQL

Message Broker: Apache Kafka

Security: Spring Security, JWT Authentication

Service Discovery: Eureka Server

API Gateway: Spring Cloud Gateway
