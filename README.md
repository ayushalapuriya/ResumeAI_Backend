# 🧠 AI Content Service (Resume Intelligence Layer)

The **AI Content Service** provides the intelligence behind ResumeAI. It leverages **Google Gemini AI** to help users optimize their resumes, generate professional content, and check for ATS compatibility.

---

## 📌 Intelligent Features

- **Smart Summary**: Generates professional 3-4 line summaries based on resume and job description.
- **Experience Optimization**: Converts raw work experience into high-impact bullet points using action verbs.
- **ATS Compatibility Checker**: Analyzes resumes against ATS algorithms, providing a score (0-100) and actionable feedback on strengths and weaknesses.
- **Job-Specific Tailoring**: Re-writes resume content to better align with a specific job description.
- **Skill Suggestion**: Extracts and suggests the top 10 relevant skills from a job posting.
- **Cover Letter Generation**: Creates customized, professional cover letters (150-200 words).
- **Quota Management**: Implements a daily limit (50 requests/day) to prevent API abuse.

---

## ⚙️ Technical Specifications

- **Framework:** Spring Boot 3.x
- **AI Model:** Google Gemini Pro (via WebClient)
- **Port:** `8085`
- **History Tracking:** Persistence layer to store and retrieve past AI requests.

---

## 🛣️ API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/ai/summary` | Generate a smart resume summary |
| `GET` | `/ai/history/{userId}` | Retrieve all past AI requests for a user |
| `GET` | `/ai/quota/{userId}` | Check remaining daily AI generation quota |

### Internal Service Capabilities (Available via Feign/Service calls):
- `generateBulletPoints`: Experience -> Bullet points.
- `generateCoverLetter`: Resume + JD -> Cover Letter.
- `checkAtsCompatibility`: Resume -> Score + Feedback.
- `suggestSkills`: JD -> Skill List.
- `tailorResumeForJob`: Professional content alignment.

---

## 🚀 How to Run

1. **Pre-requisites:**
   - **Gemini API Key**: You must have a valid API key from Google AI Studio.
   - **Service Registry**: Should be running.
2. **Setup:**
   - Copy `src/main/resources/application-local.yml` to `application.yml`.
   - Enter your `GEMINI_API_KEY` in the configuration.
3. **Run:**
   ```bash
   mvn spring-boot:run
   ```

---

## 📂 Project Structure

- `AiResource.java`: Public endpoints for frontend interaction.
- `AiServiceImpl.java`: Core logic for prompt engineering and AI processing.
- `GeminiClient.java`: Reactive WebClient implementation for communicating with Google Gemini.
- `AiRequestRepository.java`: Quota and history tracking logic.
