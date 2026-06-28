# Nova AI

Nova AI is a production-quality desktop AI assistant built step-by-step using modern Java standards and Clean Architecture principles.

## 🚀 Technology Stack

* **Language:** Java 21 (LTS)
* **Framework:** Spring Boot 3.3.x
* **Build System:** Maven
* **AI Integration:** Spring AI with OpenAI Support
* **Boilerplate Reduction:** Lombok

---

## 🏗️ Architectural Design

The codebase strictly adheres to **SOLID** principles, separation of concerns, and constructor-based dependency injection.

### Package Structure

```text
com.manohar.nova
├── controller     # REST Controllers & API endpoint mappings
├── service        # Core Business services & vendor abstractions
│   └── impl       # Concrete implementations of services
├── dto            # Data Transfer Objects & Request validations
├── intent         # Request intent analysis & routing layer
│   └── impl       # Intent rule implementation classes
├── tools          # Modular tool interface & execution dispatcher
│   └── impl       # Concrete system tools (e.g. TimeTool)
└── config         # Configuration classes
```

---

## ⚡ Key Modules

### 1. Intent Router
Every incoming request goes through the `IntentService` before calling external APIs. 
* If local keywords (such as `time`, `clock`, `date`, `today`) are detected, the request is flagged as a `TOOL` intent and resolved locally.
* Otherwise, it is flagged as a `CHAT` intent and dispatched to the LLM.
* **Why it exists:** Bypasses expensive and slow network calls to the LLM for simple queries.

### 2. Tool Framework
* **`Tool` Interface:** A standard execution contract (`getName`, `getDescription`, `execute`) implemented by all system actions.
* **`ToolRegistry`:** Startup component that dynamically discovers all Spring beans implementing `Tool` via constructor list-injection and catalogs them.
* **`ToolDispatcher`:** Orchestrates and executes tools by name, returning standardized error fallbacks if a tool is not found.

### 3. AI Service Boundary
* Abstracts direct LLM interaction behind `AIService` and uses Spring AI's `ChatModel` underneath.
* Includes graceful fallback error handling, shielding API clients from raw connection stack traces.

---

## 🛠️ API Documentation

### 1. Chat Endpoints

#### `POST /api/chat`
Resolves user queries by dispatching to the correct local tool or AI provider.

* **Payload:**
  ```json
  {
    "message": "What time is it?"
  }
  ```
* **Response (Routed to local TimeTool):**
  ```json
  {
    "reply": "2026-06-28 09:44:17"
  }
  ```

---

### 2. Test/Tool Endpoints (Temporary)

#### `GET /api/tools`
Lists metadata for all registered system tools.
* **Response:**
  ```json
  [
    {
      "name": "time",
      "description": "Returns the current local date and time."
    }
  ]
  ```

#### `GET /api/tools/time`
Directly executes the `time` tool.
* **Response:**
  ```json
  {
    "result": "2026-06-28 09:44:17"
  }
  ```

---

## 📦 Getting Started

### Prerequisites
* JDK 21
* Maven

### Configuration
Configure your OpenAI API key as an environment variable:
* **Linux/macOS:**
  ```bash
  export OPENAI_API_KEY="your_api_key_here"
  ```
* **Windows (PowerShell):**
  ```powershell
  $env:OPENAI_API_KEY="your_api_key_here"
  ```

### Running the Application
Start the Spring Boot server (port `8081`):
```bash
mvn spring-boot:run
```
