# 🎨 Template Service (Resume Design Management)

The **Template Service** is responsible for managing the visual styles and layouts available for resumes. It allows admins to create and categorize templates and tracks which designs are most popular among users.

---

## 📌 Core Features

- **Template Inventory**: Create and maintain a library of resume designs (HTML/CSS-based).
- **Categorization**: Group templates by style (e.g., *Professional*, *Creative*, *Minimalist*, *Executive*).
- **Usage Analytics**: Monitors how many times each template is used to determine popularity.
- **Lifecycle Control**: Easily activate or deactivate templates for the frontend.
- **Popularity Metrics**: Provides a list of trending templates based on real user data.

---

## ⚙️ Technical Specifications

- **Framework:** Spring Boot 3.x
- **Persistence:** Spring Data JPA + MySQL
- **Port:** `8083`
- **Feature Layer:** Dynamic category-based retrieval.

---

## 🛣️ API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/templates` | Create a new resume template |
| `GET` | `/api/templates/{id}` | Retrieve template details and code by ID |
| `GET` | `/api/templates` | List all available active templates |
| `GET` | `/api/templates/category/{category}` | Filter templates by specific style category |
| `PUT` | `/api/templates/{id}` | Update template code, CSS, or metadata |
| `DELETE` | `/api/templates/{id}` | Mark a template as inactive |
| `PUT` | `/api/templates/{id}/usage` | Record a new use of a template |
| `GET` | `/api/templates/popular` | Retrieve a list of top-performing templates |

---

## 🚀 How to Run

1. **Pre-requisites:**
   - MySQL database with `resume_builder` schema.
   - **Service Registry** should be running.
2. **Setup:**
   - Copy `src/main/resources/application-local.properties` to `application.properties`.
   - Ensure your database connection and Eureka settings are correct.
3. **Run:**
   ```bash
   mvn spring-boot:run
   ```

---

## 📂 Project Structure

- `TemplateController.java`: Management and retrieval API.
- `TemplateServiceImpl.java`: Logic for usage tracking and popularity ranking.
- `ResumeTemplate.java`: Entity containing the HTML/CSS and metadata for a design.
- `TemplateRepository.java`: Custom queries for active and popular templates.
