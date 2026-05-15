# 📄 Resume Service (Core Resume Management)

The **Resume Service** is the central engine for managing user resumes. It handles the complete lifecycle of a resume—from creation and drafting to public publishing and view tracking.

---

## 📌 Core Features

- **Resume Lifecycle**: Create, update, and manage resume drafts.
- **Publishing System**: Toggle resumes between `Public` and `Private` states.
- **View Analytics**: Tracks the number of times a public resume has been viewed.
- **User-Specific Storage**: Securely retrieves all resumes belonging to a specific authenticated user.
- **Template Integration**: Associates resumes with specific designs from the Template Service.

---

## ⚙️ Technical Specifications

- **Framework:** Spring Boot 3.x
- **Persistence:** Spring Data JPA + MySQL
- **Port:** `8082`
- **Data Transfer:** Uses `ResumeDTO` for optimized API responses.

---

## 🛣️ API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/resumes` | Create a new resume |
| `GET` | `/api/resumes/{id}` | Retrieve a specific resume by ID |
| `PUT` | `/api/resumes/{id}` | Update existing resume data |
| `DELETE` | `/api/resumes/{id}` | Remove a resume |
| `GET` | `/api/resumes/user/{userId}` | List all resumes for a specific user |
| `PATCH` | `/api/resumes/{id}/publish` | Make a resume public |
| `PATCH` | `/api/resumes/{id}/unpublish` | Set a resume back to private |
| `GET` | `/api/resumes/public` | Retrieve all publicly shared resumes |
| `PATCH` | `/api/resumes/{id}/view` | Increment the view count for a resume |
| `GET` | `/api/resumes/template/{templateId}` | Filter resumes by template ID |

---

## 🚀 How to Run

1. **Pre-requisites:**
   - MySQL database with `resume_builder` schema.
   - **Service Registry** and **API Gateway** should be running.
2. **Setup:**
   - Copy `src/main/resources/application-local.properties` to `application.properties`.
   - Configure your database connection details.
3. **Run:**
   ```bash
   mvn spring-boot:run
   ```

---

## 📂 Project Structure

- `ResumeController.java`: REST API layer.
- `ResumeServiceImpl.java`: Core business logic and database interactions.
- `Resume.java`: Database entity representing a resume document.
- `ResumeMapper.java`: Utility for converting between Entities and DTOs.
