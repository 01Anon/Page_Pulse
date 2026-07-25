# 🔍 Page Pulse — URL Auditing Tool

A lightweight web application that audits any URL and generates a structured SEO & health report. Enter a URL and get instant insights about HTTP status, response time, page structure, and content metrics.

## 🏗️ Architecture

```
┌──────────────────────┐        HTTP POST         ┌──────────────────────────┐
│                      │  ───────────────────────► │                         │
│   Frontend (Vanilla) │      /api/audit           │  Backend (Spring Boot)  │
│   HTML + CSS + JS    │  ◄─────────────────────── │  Java 17 + Jsoup        │
│                      │       JSON Report         │                         │
└──────────────────────┘                           └────────┬─────────────────┘
                                                            │
                                                            │  Fetches & Parses
                                                            ▼
                                                   ┌─────────────────┐
                                                   │  Target Website │
                                                   └─────────────────┘
```

## 🧰 Tech Stack

| Layer    | Technology                        | Why                                                  |
|----------|-----------------------------------|------------------------------------------------------|
| Backend  | Java 17 + Spring Boot 3           | Type-safe, enterprise-grade, structured error handling|
| Parsing  | Jsoup                             | Purpose-built HTML parser for Java, handles malformed HTML gracefully |
| Frontend | Vanilla HTML/CSS/JS               | Zero dependencies, fast load, demonstrates core web fundamentals |
| Design   | Skeuomorphism + CSS Animations    | Realistic depth, embossed surfaces — unique and premium without any CSS framework |

## 📦 Project Structure

```
Digital_Heros/
├── README.md
├── .gitignore
│
├── backend/                          # Spring Boot Application
│   ├── pom.xml                       # Maven build config + dependencies
│   └── src/
│       └── main/
│           └── java/
│               └── com/pagepulse/
│                   ├── PagePulseApplication.java    # Entry point
│                   ├── controller/
│                   │   └── AuditController.java     # REST endpoint
│                   ├── service/
│                   │   └── AuditService.java        # Core scraping logic
│                   ├── dto/
│                   │   ├── AuditRequest.java         # Input DTO
│                   │   └── AuditResponse.java        # Output DTO
│                   └── exception/
│                       └── GlobalExceptionHandler.java  # Centralized error handling
│
└── frontend/                         # Static Frontend
    ├── index.html                    # Page structure
    ├── style.css                     # Skeuomorphic design system
    └── app.js                        # API calls + DOM rendering
```

## 🚀 How to Run

### Backend
```bash
cd backend
mvn spring-boot:run
```
The API will be available at `http://localhost:8080`

### Frontend
Open `frontend/index.html` in your browser, or use a Live Server extension.

## 📡 API Reference

### `POST /api/audit`

**Request Body:**
```json
{
  "url": "https://example.com"
}
```

**Success Response (200):**
```json
{
  "error": false,
  "statusCode": 200,
  "responseTimeMs": 342,
  "pageTitle": "Example Domain",
  "metaDescription": "This domain is for use in illustrative examples.",
  "h1Count": 1,
  "imagesMissingAlt": 0,
  "wordCount": 28
}
```

**Error Responses:**
| Status | Scenario                    |
|--------|-----------------------------|
| 400    | Invalid or malformed URL    |
| 504    | Connection/read timeout     |
| 422    | Non-HTML content type       |
| 500    | Unexpected server error     |


## 🧠 Design Decisions & Reasoning

1. **Separation of Concerns (DTOs vs Domain logic):** 
   * **Decision:** We used structured `AuditRequest` and `AuditResponse` Data Transfer Objects instead of passing raw JSON Maps or internal entities to the controller.
   * **Reasoning:** This prevents over-posting, provides a crystal-clear API contract for the frontend, and allows the use of Java Bean Validation (`@NotBlank`, `@Pattern`) right at the controller layer before hitting business logic.

2. **Asynchronous/Stateless Parsing with Jsoup:**
   * **Decision:** We chose Jsoup instead of a headless browser (like Selenium or Puppeteer).
   * **Reasoning:** Jsoup is significantly faster and uses a fraction of the memory. Since SEO metrics (H1s, Meta tags, Alt attributes) rely on the raw HTML DOM structure rather than dynamically rendered JavaScript content, a headless browser would have added unnecessary overhead and complexity.

3. **Skeuomorphic Frontend Design over Frameworks:**
   * **Decision:** We built the UI using Vanilla HTML/CSS/JS with a Skeuomorphic design (realistic depth, shadows, gradients) rather than using React or Tailwind CSS.
   * **Reasoning:** For a single-page utility tool, a React build step is overkill. A Skeuomorphic aesthetic stands out distinctly against the common flat/material designs, demonstrating deep mastery of CSS geometry and styling without relying on pre-built utility frameworks.

## 📝 License

Built for Digital Heroes Training Task.
