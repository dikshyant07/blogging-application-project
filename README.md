# Blogging Application (Spring Boot)

This is a simple blogging platform built using Spring Boot and JPA.  
It allows users to create posts, categorize them, add tags, comment, and like posts.  
The goal of this project is to learn how to design clean entities and relationships while building a real-world RESTful
backend.

---

## Tech Stack

| Layer      | Technology    |
|------------|---------------|
| Backend    | Spring Boot   |
| Database   | PostgreSQL    |
| ORM        | JPA           |
| Build Tool | Maven         |
| JDK        | Java 21+      |
| IDE        | IntelliJ IDEA |

---
---

## Entities

### 1. User

Represents an author or reader of the platform.

### 2. Post

Contains title, content, status, and relationships with comments, tags, likes, category, and user.

### 3. Comment

Represents a user’s comment on a specific post.

### 4. Like

Represents a user's like on a post.

### 5. Category

Groups multiple posts together (e.g., Technology, Travel).

### 6. Tag

Used for labeling and searching posts (e.g., “Spring Boot”, “Java”).

---
## Entity Relationship Diagram (ERD)
![ER Diagram](erd.png)
