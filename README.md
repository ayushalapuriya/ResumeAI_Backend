# 🔐 Auth Service (Authentication & User Management)

The **Auth Service** is the central security authority for the ResumeAI platform. It manages user identity, handles secure authentication via JWT, and maintains user profiles and subscription statuses.

---

## 📌 Core Features

- **User Onboarding**: Secure registration and profile creation.
- **JWT-based Authentication**: Issues signed JSON Web Tokens for stateless communication.
- **Token Lifecycle**: Supports token validation and refresh mechanisms.
- **Profile Management**: Update user details, change passwords, and manage subscriptions.
- **Secure Storage**: Encrypts sensitive user data using industry-standard hashing.

---

## ⚙️ Technical Specifications

- **Framework:** Spring Boot 3.x
- **Security:** Spring Security + JWT (JSON Web Token)
- **Port:** `8081`
- **Database:** MySQL (User and Role persistence)

---

## 🛣️ API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Login and receive a JWT token |
| `GET` | `/auth/validate` | Validate an existing JWT token |
| `POST` | `/auth/refresh` | Refresh an expired session |
| `GET` | `/auth/{id}` | Retrieve user profile by ID |
| `PUT` | `/auth/{id}` | Update user profile information |
| `POST` | `/auth/{id}/change-password` | Securely update password |
| `POST` | `/auth/{id}/subscription` | Update user subscription plan |

---

## 🚀 How to Run

1. **Pre-requisites:**
   - MySQL database running with a schema for `resume_builder`.
   - Eureka Server (Service Registry) should be active.
2. **Setup:**
   - Copy `src/main/resources/application-local.properties` to `application.properties`.
   - Configure your DB credentials and JWT secret key.
3. **Run:**
   ```bash
   mvn spring-boot:run
   ```

---

## 🔗 Security Configuration

The service uses `SecurityConfig.java` to define public and protected routes. By default, `/auth/login` and `/auth/register` are open, while other endpoints require a valid token passed through the API Gateway.
