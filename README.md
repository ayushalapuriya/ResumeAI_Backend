# 🛰️ Service Registry (Eureka Server)

The **Service Registry** is the backbone of the ResumeAI microservices architecture. It uses **Netflix Eureka** to provide service discovery, allowing all other microservices to find and communicate with each other dynamically without hardcoded IP addresses.

---

## 📌 Key Features

- **Service Discovery**: Automatically tracks all active microservice instances.
- **Health Monitoring**: Periodically checks the health of registered services.
- **Dynamic Routing Support**: Enables the API Gateway to route requests to the correct service instance.
- **Self-Preservation Mode**: Prevents mass de-registration during network glitches.

---

## ⚙️ Technical Specifications

- **Framework:** Spring Boot 3.x
- **Discovery Tool:** Spring Cloud Netflix Eureka
- **Default Port:** `8761`
- **Dashboard URL:** [http://localhost:8761](http://localhost:8761)

---

## 🛠️ Configuration Details

This service is configured **not** to register with itself:

```yaml
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
server:
  port: 8761
```

---

## 🚀 How to Run

1. **Prepare Environment:**
   - Ensure you have Java 17+ installed.
   - Copy `src/main/resources/application-local.yml` (or properties) to `application.yml` if you need custom overrides.

2. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Verify:**
   - Open your browser and navigate to `http://localhost:8761`. 
   - You should see the **Eureka System Status** dashboard.

---

## 🔗 Integration

All other services (Auth, Resume, etc.) should have the following in their configuration to register here:

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```
