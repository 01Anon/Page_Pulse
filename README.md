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

## 📊 Scoring Criteria

| Criterion                     | Weight |
|-------------------------------|--------|
| Correctness and Error Handling| 40     |
| Code Quality and Structure    | 35     |
| API Design                    | 25     |

## 📝 License

Built for Digital Heroes Training Task.
