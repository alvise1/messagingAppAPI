# Messaging App API

This is a Spring Boot REST API backend for an instant messaging application. The API supports real-time communication, user management, and secure authentication.

---

## Features

- **User Registration and Authentication**:
    - Secure authentication using JWT.
    - Role-based access control for different user types.

- **Messaging**:
    - Send and receive messages in real-time using WebSockets.
    - Message persistence with PostgreSQL.

- **User Management**:
    - Update profiles.
    - Manage friends and contacts.

- **Notifications**:
    - Real-time notifications for messages and friend requests.

---

## Tech Stack

- **Java**: Spring Boot framework.
- **Database**: PostgreSQL for production, H2 for testing.
- **Messaging**: WebSockets and Kafka for real-time communication and event streaming.
- **Documentation**: Swagger/OpenAPI.
- **Authentication**: JWT-based authentication with Spring Security.

---

## Setup Instructions

### Prerequisites
- Java 21 or higher
- Maven
- PostgreSQL

### Steps to Run Locally

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/messagingAppAPI.git
   cd messagingAppAPI
