# RAG Chat Storage Microservice

A production ready backend microservice to store and manage chat histories for a RAG based chatbot system. Built with Java Spring Boot and PostgreSQL.

## Features

- **Session Management**: Create, rename, delete, and favorite chat sessions.
- **Message Storage**: Store chat messages with sender, content, and context.
- **Security**: API Key authentication for all endpoints.
- **Rate Limiting**: Distributed sliding window rate limiting using Redis.
- **Observability**: Health and metrics endpoints via Spring Boot Actuator.
- **Dockerized**: Easy setup with Docker Compose (App, Postgres, Redis, pgAdmin).
- **Documentation**: Integrated Swagger/OpenAPI documentation.

## Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.2.3
- **Database**: PostgreSQL, Redis
- **Security**: Custom API Key Filter
- **Rate Limiting**: Sliding Window using Redis
- **Documentation**: Springdoc OpenAPI (Swagger)

## Getting Started

### Prerequisites

- Docker and Docker Compose
- Java 17
- Maven

### Setup & Run

1.  **Clone the repository**
2.  **Configure Environment Variables**
    Copy `.env.example` to `.env` and update the values if needed.
    ```bash
    cp .env.example .env
    ```
3. **Build the Application using Maven**
   ```bash
   mvn clean package -DskipTests
   ```
4.  **Run with Docker Compose**
    ```bash
    docker-compose up --build
    ```
    The application will start at `http://localhost:8080`.

### API Documentation

Once the application is running, you can access the Swagger UI at:
`http://localhost:8080/swagger-ui.html`

### API Endpoints

| Method   | Endpoint                             | Description                 |
|:---------|:-------------------------------------|:----------------------------|
| `POST`   | `/api/sessions`                      | Create a new chat session   |
| `GET`    | `/api/sessions/user/{userId}`        | Get all sessions for a user |
| `GET`    | `/api/sessions/{sessionId}`          | Get a specific session      |
| `PATCH`  | `/api/sessions/{sessionId}/rename`   | Rename a session            |
| `PATCH`  | `/api/sessions/{sessionId}/favorite` | Toggle favorite status      |
| `DELETE` | `/api/sessions/{sessionId}`          | Delete a session            |
| `POST`   | `/api/sessions/{sessionId}/messages` | Add a message to a session  |
| `GET`    | `/api/sessions/{sessionId}/messages` | Get messages (paginated)    |

### Authentication

All API requests must include the `X-API-KEY` header with the configured API key.


## Database Schema

### `chat_sessions`
| Column        | Type      | Description                               |
|:--------------|:----------|:------------------------------------------|
| `id`          | UUID (PK) | Unique session ID                         |
| `user_id`     | VARCHAR   | ID of the user who owns the session       |
| `title`       | VARCHAR   | Title of the chat session                 |
| `is_favorite` | BOOLEAN   | Whether the session is marked as favorite |
| `created_at`  | TIMESTAMP | Creation timestamp                        |
| `updated_at`  | TIMESTAMP | Last update timestamp                     |

### `chat_messages`
| Column       | Type      | Description                  |
|:-------------|:----------|:-----------------------------|
| `id`         | UUID (PK) | Unique message ID            |
| `session_id` | UUID (FK) | Reference to `chat_sessions` |
| `sender`     | VARCHAR   | Sender type (`USER` or `AI`) |
| `content`    | TEXT      | The message content          |
| `context`    | TEXT      | Optional context (JSON/Text) |
| `timestamp`  | TIMESTAMP | Message timestamp            |


### Database Management

pgAdmin is included in the Docker Compose setup at `http://localhost:5050`.
- **Email**: check `.env` file
- **Password**: check `.env` file

**To connect to the database:**
1. Login to pgAdmin
2. Right click on **servers** > **server...**
3. **General** tab: Name it `RagChatDB`
4. **Connection** tab:
   - **Host name**: `db`
   - **Port**: `5432`
   - **Maintenance database**: `ragchat`
   - **Username**: check `.env` file
   - **Password**: check `.env` file
5. Click **save**

### Running Tests
```bash
mvn test
```
