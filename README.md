# Flight Booking Management System

A backend Flight Booking Management System built using **Spring Boot**, **Spring Data JPA**, and **PostgreSQL/MySQL**. The application provides REST APIs to manage flights, passengers, and bookings while enforcing business rules and validations.

##  Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- REST APIs

## 📌 Features

### Flight Management
- Create, update, delete, and fetch flights.
- Search flights by source and destination.
- Pagination and sorting for flight listings.

### Passenger Management
- Create, update, delete, and fetch passengers.
- Manage passenger information with validation.

### Booking Management
- Book seats for passengers.
- Update or cancel bookings.
- View bookings by flight or booking ID.
- Pagination and sorting for bookings.

## 🗄️ Entities

- **Flight**
- **Passenger**
- **Booking**

**Relationships**
- One Flight → Many Bookings.
- One Passenger → Many Bookings.
- Each Booking belongs to one Flight and one Passenger.

## ✅ Business Rules

- Booking is allowed only if seats are available.
- Cannot book cancelled or expired flights.
- Available seats decrease after booking confirmation.
- Available seats are restored after booking cancellation.
- Booking amount is calculated on the server.
- Global exception handling for validation and resource errors.

## 📚 Concepts Practiced

- CRUD Operations
- DTO Pattern
- Entity Relationships (`@OneToMany`, `@ManyToOne`)
- Bean Validation
- Pagination & Sorting
- Exception Handling
- REST API Design
