# 🔔 Notification Service (Communication Hub)

The **Notification Service** handles all user-facing communications in the ResumeAI ecosystem. It supports real-time in-app dashboard notifications, individual email delivery, and global system broadcasts.

---

## 📌 Core Features

- **Multi-Channel Delivery**: Support for `DASHBOARD`, `EMAIL`, and `BOTH` notification channels.
- **Async Email Delivery**: Leverages Spring `@Async` to send emails without blocking the main application thread.
- **System Broadcasts**: Admin-level capability to send global messages to all registered users via both the dashboard and email.
- **Read/Unread Tracking**: Maintains the read status of each notification per user.
- **Cross-Service Integration**: Uses Feign Client to communicate with the **Auth Service** to fetch real recipient email addresses.

---

## ⚙️ Technical Specifications

- **Framework:** Spring Boot 3.x
- **Messaging:** Java Mail Sender (SMTP)
- **Service Communication:** Spring Cloud OpenFeign
- **Port:** `8086`
- **Database:** MySQL (Notification persistence)

---

## 🛣️ API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/notifications/recipient/{id}` | Fetch all personal and global notifications for a user |
| `PUT` | `/notifications/{id}/read` | Mark a specific notification as read |
| `PUT` | `/notifications/recipient/{id}/read-all` | Mark all user notifications as read |
| `GET` | `/notifications/recipient/{id}/unread-count` | Get total count of unread personal + global messages |
| `POST` | `/notifications/broadcast` | (Admin) Send a global message to every user |

---

## 🚀 How to Run

1. **Pre-requisites:**
   - SMTP Server details (e.g., Gmail App Password).
   - **Auth Service** should be running for user email lookups.
2. **Setup:**
   - Copy `src/main/resources/application-local.properties` to `application.properties`.
   - Configure `spring.mail.username` and `spring.mail.password`.
3. **Run:**
   ```bash
   mvn spring-boot:run
   ```

---

## 📂 Project Structure

- `NotificationResource.java`: REST controller for dashboard interactions.
- `NotificationServiceImpl.java`: Logic for mail sending, broadcast management, and async processing.
- `AuthClient.java`: Feign client for fetching user details from the Auth microservice.
- `Notification.java`: Entity mapping for notification storage.
