# Sankalp Restaurant Management System (RMS)

An enterprise 3-tier Restaurant Management System built with Spring Boot, JSP/Servlets (Tomcat), and MySQL, fully containerized with Docker.

---

## 🚀 Quick Start with Docker (Run in 1 Command)

No need to manually install Java, Maven, Apache Tomcat, or MySQL Server!

### Prerequisites:
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running.

---

### Step 1: Start the Entire Application
Open a terminal in this project root folder and run:

```bash
docker compose up --build
```

*(To run in background mode, add `-d`: `docker compose up -d --build`)*

---

### Step 2: Access the Application

| Service | URL | Description |
| :--- | :--- | :--- |
| 🖥️ **Frontend Web UI** | [http://localhost:8081](http://localhost:8081) | JSP/HTML5 Client & Admin Dashboard |
| 📑 **Swagger API Docs** | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) | Interactive OpenAPI REST Testing |
| 🔌 **Backend REST API** | [http://localhost:8080](http://localhost:8080) | Spring Boot REST API |
| 🗄️ **MySQL Database** | `localhost:3307` | User: `root` \| Pass: `root` \| DB: `RMS` |

---

### Step 3: Stop the Application
When you are done, run:
```bash
docker compose down
```

---

## 🏗️ Architecture & Tech Stack

- **Frontend:** JSP, JavaScript, HTML5, CSS3, Bootstrap (Deployed on Apache Tomcat 10.1)
- **Backend:** Spring Boot 3.5.5, Spring Security, JWT Authentication, Spring JDBC, Swagger/OpenAPI (Java 17)
- **Database:** MySQL 8.0 with automatic schema creation and initial data migration
- **Containerization:** Docker Multi-stage builds & Docker Compose
