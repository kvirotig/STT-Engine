# Speech Recognition Service (Spring Boot + DJL Whisper + PostgreSQL)

## 📌 Overview
This project is a **Spring Boot REST API** that converts speech audio into text using **OpenAI Whisper** via the **Deep Java Library (DJL)**.  
The transcription results are stored in a **PostgreSQL** database for later retrieval and analysis.

It supports:
- Local audio file transcription (`.wav`, `.mp3`, etc.)
- Remote audio transcription from an HTTP/HTTPS URL
- Automatic text cleanup
- Persistent storage of transcripts in a database
- Custom exception handling with meaningful HTTP responses

---

## 🏗️ Architecture
**Layers & Components**
- **Controller**: `SpeechToTextController` → Exposes `/api/speech/transcript` endpoint
- **Service**: `SpeechToTextService` → Handles business logic, calls ML model, stores transcripts
- **ML Model Wrapper**: `WhisperModel` → Loads and runs DJL Whisper model
- **Repository**: `TranscriptRepository` → JPA repository for PostgreSQL
- **Entity**: `TranscriptEntity` → Database mapping for transcripts
- **Exception Handling**: `GlobalExceptionHandler` + `SpeechToTextException`

***Flow***:

Client → REST API → Service → DJL Whisper Model → Transcript Cleanup → Database → Response
---

## 🚀 Tech Stack
- **Java 17+**
- **Spring Boot** (Web, Data JPA)
- **PostgreSQL** (with JDBC & Hibernate)
- **Deep Java Library (DJL)** with HuggingFace + PyTorch engine
- **OpenAI Whisper** (small model)
- **Maven**
- **SLF4J** (Logging)

---

## 📂 Project Structure

<img width="481" height="174" alt="image" src="https://github.com/user-attachments/assets/c4c69a33-e25f-4d4d-a31e-55d3474340a9" />

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository
```bash
  git clone  https://github.com/kvirotig/STT-Engine.git
  cd STT-Enine/speechrecognition

2️⃣ Configure Database

  spring.application.name=SpeechRecognition
  spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
  spring.datasource.username=YOUR_USERID
  spring.datasource.password=YOUR_PASSWORD
  spring.jpa.hibernate.ddl-auto=update

  Ensure PostgreSQL is running and accessible.

3️⃣ Download Whisper Model (Optional: local caching)

  By default, the code loads:
     djl://ai.djl.huggingface.pytorch/openai/whisper-small
  
  DJL will automatically download the model.
  
  To use a pre-downloaded model, update WhisperModel to use .optModelUrls("/path/to/model").

### 4️⃣ Build and Run
  mvn clean install
  mvn spring-boot:run

📡 API Usage
  Endpoint
  POST /api/speech/transcript
  
  Request Body
  {
    "filePath": "/absolute/path/to/audio.wav"
  }
  or for remote files:
  {
    "filePath": "https://resources.djl.ai/audios/speech.wav"
  }

Response:
"Hello, this is a transcribed text from the given audio."

📦 Database
A PostgreSQL table transcripts is used with columns:
  id (Primary Key)
  uri (File path or URL)
  transcript (Text)
  piiData (Boolean flag for sensitive info detection)
  createTime (Timestamp)

🔥 Error Handling
Custom exceptions return structured JSON:
{
  "timestamp": "2025-08-08T12:34:56Z",
  "status": 400,
  "error": "Speech TO Text Processing Error",
  "message": "File not found : /invalid/path/audio.wav"
}
 
🛠️ Future Improvements
  Support for multiple languages
  PII detection and masking
  Asynchronous processing for large files
  WebSocket real-time transcription

👤 Author
Gopala Krishna Viroti
Email : kvirotig@gmail.com

