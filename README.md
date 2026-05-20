# ResumeAI Backend - Microservices Project

Welcome to the **ResumeAI** backend repository. This project is built using a microservices architecture with Spring Boot, Spring Cloud, and React.

## 🏗 Project Architecture

| Service | Port | Description |
|---------|------|-------------|
| `SERVICE-REGISTRY` | 8761 | Eureka Service Discovery |
| `RESUMEAI-GATEWAY` | 8080 | Spring Cloud Gateway (Entry point) |
| `AUTH-SERVICE` | 8081 | Authentication & User Management |
| `RESUME-SERVICE` | 8082 | Resume CRUD Operations |
| `TEMPLATE-SERVICE` | 8083 | Resume Template Management |
| `RESUME-SECTION-SERVICE` | 8084 | Dynamic Section Management |
| `AICONTENT-SERVICE` | 8085 | AI Generation (OpenAI/Claude/Gemini) |
| `NOTIFICATION-SERVICE` | 8086 | Email & Dashboard Notifications |

---

## 🌿 Git Workflow & Branching Strategy

We follow a professional branching strategy to ensure code quality and safety.

### 1. Branches
- **`main`**: The stable, production-ready branch. Only merge from `dev` after full testing.
- **`dev`**: The integration branch. All service features are merged here for testing.
- **`feature/<service-name>`**: Dedicated branches for working on specific services (e.g., `feature/auth-service`).

### 2. Workflow Steps
1.  **Switch to your service branch**: `git checkout feature/auth-service`
2.  **Make changes and test locally**.
3.  **Commit and Push**:
    ```bash
    git add .
    git commit -m "feat(auth): added jwt validation"
    git push origin feature/auth-service
    ```
4.  **Merge to `dev`**: Once stable, merge your branch into `dev` for integration testing.
5.  **Merge to `main`**: After all services are verified in `dev`, merge `dev` into `main`.

---

## 🔐 Security & Environment Configuration

**IMPORTANT**: Sensitive data (DB passwords, API keys) must **NEVER** be pushed to Git.

We use a "Local Template" pattern:
- **`.env`**: Contains your **REAL** local values for Docker Compose and stays only on your machine.
- **`.env.example`**: Contains safe placeholders and can be shared as a template.

### Setup for new developers:
1. Copy `.env.example` to `.env`.
2. Fill in your real database credentials and API keys in `.env`.
3. Run `docker compose up -d` from the `ResumeAI` folder.

> `docker-compose.yml` is still needed here because it defines the full local stack and reads values from `.env`.

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL
- RabbitMQ (for Notification Service)

### Running the Project
1. Start the **SERVICE-REGISTRY** first.
2. Start the **RESUMEAI-GATEWAY**.
3. Start the individual microservices.
