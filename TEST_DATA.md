# API Test Data

Use this data to test the API endpoints via Swagger UI or Postman.

## Authentication
**Header:** `X-API-KEY`
**Value:** Check `.env` file

---

## 1. Create Session
**Endpoint:** `POST /api/sessions`

**Request Body:**
```json
{
  "userId": "user-123",
  "title": "Physics Homework Help"
}
```
_*Save the sessionId*_

**Request Body (Another User):**
```json
{
  "userId": "user-456",
  "title": "Recipe Ideas"
}
```

---

## 2. Add Messages
**Endpoint:** `POST /api/sessions/{sessionId}/messages`

**User Message:**
```json
{
  "sender": "USER",
  "content": "Explain Newton's second law.",
  "context": "{\"subject\": \"physics\", \"level\": \"high-school\"}"
}
```

**AI Response:**
```json
{
  "sender": "AI",
  "content": "Newton's second law states that Force equals Mass times Acceleration (F=ma).",
  "context": "{\"source\": \"textbook-p42\", \"confidence\": 0.98}"
}
```

**Follow-up Question:**
```json
{
  "sender": "USER",
  "content": "What about the third law?",
  "context": null
}
```

---

## 3. Rename Session
**Endpoint:** `PATCH /api/sessions/{sessionId}/rename`

**Request Body:**
```json
{
  "newTitle": "Newton's Laws Discussion"
}
```

---

## 4. Toggle Favorite
**Endpoint:** `PATCH /api/sessions/{sessionId}/favorite`
*(No Request Body Required)*

---

## 5. Get User Sessions
**Endpoint:** `GET /api/sessions/user/{userId}`
**Param:** `userId` = `user-123`

---

## 6. Get Session Messages
**Endpoint:** `GET /api/sessions/{sessionId}/messages`
**Object:**
```json
{
  "page": 0,
  "size": 10
}
```
