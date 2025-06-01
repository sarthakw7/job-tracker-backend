# Job Application Tracker Backend

This is a backend API built with Spring Boot to help users track their job applications. It's designed to be the server-side for a future frontend application.

## Features

*   Track job applications, including details, stages, contacts, notes, and documents.
*   User authentication and secure API access with JWT.
*   Built with a standard RESTful architecture and data validation.

## Tech Stack

*   **Backend:** Spring Boot (Java)
*   **Database:** MySQL
*   **ORM:** Spring Data JPA, Hibernate
*   **Security:** Spring Security, JWT
*   **Containerization:** Docker, Docker Compose
*   **API Docs:** Swagger UI

## Setup and Running with Docker Compose

1.  **Clone the repo:**
    ```bash
    git clone https://github.com/sarthakw7/job-tracker-backend/
    cd job-tracker-backend
    ```
2.  **Configure Database Credentials:** Update the `MYSQL_USER`, `MYSQL_PASSWORD`, and `MYSQL_ROOT_PASSWORD` in `docker-compose.yml` to your desired values. Ensure the `SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD` under the `app` service match the database user.
3.  **Build and run:**
    ```bash
    docker-compose up --build
    ```
    This will set up the database and start the Spring Boot application, ensuring the app waits for the database to be ready.

## Accessing the API

The API runs on `http://localhost:8080`.

*   **Swagger UI (API Docs):** `http://localhost:8080/swagger-ui.html`
*   **Authentication:** Use `/api/auth/signup` to create a user and `/api/auth/signin` to get a JWT.
*   **Authenticated Endpoints:** Include the JWT in the `Authorization: Bearer <token>` header for protected routes.

## Future Ideas

*   Add a frontend interface.
*   Implement email notifications.
*   Enable file uploads.

---

