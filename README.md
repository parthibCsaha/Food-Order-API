# Food Ordering App – Backend API

A RESTful backend for a **Food Ordering** application built with Spring Boot, Spring Data JPA, Spring Security (JWT), and PostgreSQL. Users can browse meal types, meals, place orders (with or without login), and view order details.

----

## Features

- **User Management**  
  - **Registration & Login** – Users sign up and obtain a JWT.  
  - **Roles** – New users default to `ROLE_USER`; admins can manage all resources.

- **Meal & Category Browsing**  
  - **Public Endpoints** – Browse meal types and meals by category.  
  - **Admin Endpoints** – Create, update, delete meal types and meals.

- **Order Processing**  
  - **Guest Checkout** – Place an order without logging in.  
  - **Authenticated Orders** – Logged-in users can view their order history.  
  - **Order Items** – Each order contains one or more items with quantity and price.

- **Security**  
  - Stateless JWT authentication.  
  - Protected endpoints require a valid `Authorization: Bearer <token>`.  
  - CORS & CSRF disabled for API clients.

----

## Architecture & Entity Relationships

The project follows a layered architecture:

- **Controllers** – Handle HTTP requests  
- **Services** – Business logic  
- **Repositories** – Spring Data JPA interfaces  
- **Models** – JPA entities  
----
### Entity Relationship Diagram
```mermaidclassDiagram
    class User {
        +Long id
        +String firstName
        +String lastName
        +String username
        +String email
        +String password
        +Role role
        +String phoneNumber
        +String address
        +Boolean deleted
    }
    class FinalOrder {
        +Long id
        +Date date
        +int finalPrice
        +String address
        +String phoneNumber
        +String status
    }
    class OrderItem {
        +Long id
        +String mealName
        +int mealPrice
        +String mealDescription
        +String mealImage
        +String mealImageName
        +String mealTypeName
        +int quantity
    }
    class Meal {
        +Long id
        +String name
        +int price
        +String image
        +String imageName
        +String description
        +Boolean isDeleted
    }
    class MealType {
        +Long id
        +String typeName
        +String image
        +String imageName
        +String description
        +Boolean isDeleted
    }

    User "1" -- "many" FinalOrder : places >
    FinalOrder "1" -- "many" OrderItem : contains >
    Meal "1" -- "many" OrderItem : appears in >
    MealType "1" -- "many" Meal : categorizes >

```
----
## Technologies Used
  - Java 21
  - Spring Boot 3.x
  - Spring Data JPA
  - Spring Security (JWT)
  - PostgreSQL
  - Lombok
  - Maven
