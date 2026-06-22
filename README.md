# Dynamic MIS Reporting System

A full-stack dynamic reporting platform for managing and exporting MIS (Management Information System) reports, built with Spring Boot and React.

## Features

- Dynamic SQL-based reports with runtime filters
- Student and course record management
- Pagination support
- CSV and PDF export
- Responsive dashboard UI
- PostgreSQL database with Flyway migrations
- Role-based access with Spring Security

## Tech Stack

### Frontend
- React (JavaScript)
- Vite
- Axios
- CSS

### Backend
- Spring Boot 4.0.6
- Java 21
- Spring Data JPA + Hibernate
- Spring Security
- Flyway (database migrations)
- PostgreSQL
- Lombok
- dotenv-java (environment config)
- OpenCSV / Apache Commons CSV
- iText PDF

## Project Structure

```
dynamic_mis_reporting/
├── backend/          # Spring Boot application
│   ├── src/
│   │   └── main/
│   │       └── java/com/mis/
│   │           ├── course/
│   │           ├── department/
│   │           ├── dynamic_report/
│   │           │   ├── controller/
│   │           │   ├── dto/
│   │           │   ├── entity/
│   │           │   ├── repository/
│   │           │   └── service/
│   │           ├── exception/
│   │           └── student/
│   └── pom.xml
└── frontend/         # React application
    └── src/
```

## Setup & Installation

### Prerequisites
- Java 21+
- Maven
- Node.js
- PostgreSQL

### 1. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE NIC_MIS;
```

### 2. Backend Configuration

Create a `.env` file inside the `backend/` folder:

```env
PORT=8080
DB_URL=jdbc:postgresql://localhost:5432/NIC_MIS
DB_USERNAME=your_pg_username
DB_PASSWORD=your_pg_password
FRONTEND_URL=http://localhost:5173
```

Run the backend:

```bash
cd backend
./mvnw clean spring-boot:run
```

### 3. Frontend Configuration

Create a `.env` file inside the `frontend/` folder:

```env
VITE_API_URL=http://localhost:8080/api
```

Run the frontend:

```bash
cd frontend
npm install
npm run dev
```

The app will be available at `http://localhost:5173`.

## API Endpoints

| Method | Endpoint                  | Description         |
|--------|---------------------------|---------------------|
| GET    | /api/reports/{id}         | Get report config   |
| POST   | /api/students/search      | Search records      |
| POST   | /api/students/export/csv  | Export CSV          |
| POST   | /api/students/export/pdf  | Export PDF          |

## Environment Variables

| Key            | Description                        |
|----------------|------------------------------------|
| PORT           | Backend server port (default 8080) |
| DB_URL         | PostgreSQL JDBC connection URL     |
| DB_USERNAME    | PostgreSQL username                |
| DB_PASSWORD    | PostgreSQL password                |
| FRONTEND_URL   | Frontend origin for CORS           |
| VITE_API_URL   | Backend API base URL for frontend  |

## Acknowledgements

This project was inspired by and initially based on [jitaditya004/reporting-service](https://github.com/jitaditya004/reporting-service). Significant modifications have been made to the codebase including restructuring, bug fixes, and frontend migration from TypeScript to JavaScript.

## Author

[Biprajit-Palit](https://github.com/Biprajit-Palit)
