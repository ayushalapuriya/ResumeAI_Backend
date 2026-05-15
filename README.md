# 🚪 ResumeAI API Gateway

The **API Gateway** is the single entry point for all client requests in the ResumeAI ecosystem. Built with **Spring Cloud Gateway**, it handles intelligent routing, security filtering, and cross-cutting concerns like authentication and rate limiting.

---

## 📌 Key Responsibilities

- **Intelligent Routing**: Automatically routes requests to the correct microservice based on URL patterns.
- **Security & JWT Validation**: Uses a custom `AuthFilter` to intercept requests and validate JWT tokens before they reach the backend services.
- **Load Balancing**: Works with Eureka to distribute traffic across multiple service instances.
- **Protocol Translation**: Standardizes request/response formats between the client and microservices.

---

## ⚙️ Technical Specifications

- **Framework:** Spring Boot 3.x + Spring Cloud Gateway
- **Security:** Custom JWT Filter (`AuthFilter.java`)
- **Default Port:** `8080` (Standard Gateway Port)
- **Token Handling:** Extracts and validates `Authorization: Bearer <token>` headers.

---

## 🛡️ Security Implementation

The Gateway implements a custom `AuthFilter` that:
1. Intercepts incoming HTTP requests.
2. Checks for a valid JWT in the `Authorization` header.
3. Uses `JwtUtil` to verify the signature and expiration.
4. Rejects unauthorized requests with a `401 Unauthorized` status before they even hit your microservices.

---

## 🛤️ Routing Configuration

Routes are defined in `application.yml`. Example:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: auth-service
          uri: lb://AUTH-SERVICE
          predicates:
            - Path=/auth/**
```

---

## 🚀 How to Run

1. **Pre-requisites:**
   - Ensure the **Service Registry (Eureka)** is already running.
2. **Setup:**
   - Copy `src/main/resources/application-local.yml` to `application.yml`.
   - Ensure the `AUTH_SERVICE_URL` or Eureka client settings are correct.
3. **Run:**
   ```bash
   mvn spring-boot:run
   ```

---

## 📂 Project Structure

- `filter/AuthFilter.java`: The core security logic.
- `utils/JwtUtil.java`: Token validation and parsing.
- `application.yml`: Route and filter configurations.
