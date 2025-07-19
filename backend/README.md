
# EventScribe Spring Boot Backend

This is the Spring Boot backend for the EventScribe application.

## Setup Instructions

1. Make sure you have Java 17 or higher installed
2. Make sure you have Maven installed
3. Navigate to the backend directory
4. Run `mvn spring-boot:run` to start the application

The backend will start on port 8080.

## Available Endpoints

### Authentication
- POST `/api/auth/register` - Register a new user
- POST `/api/auth/login` - Login an existing user

### Events
- GET `/api/events` - Get all events for the authenticated user
- GET `/api/events/{id}` - Get a specific event
- POST `/api/events` - Create a new event
- PUT `/api/events/{id}` - Update an existing event
- DELETE `/api/events/{id}` - Delete an event

### Chat
- POST `/api/chat` - Send a message to the chat service

### Health Check
- GET `/api/health` - Check if the API is running

## Database

This application uses an in-memory H2 database by default. Data will be lost when the application is restarted.

You can access the H2 console at `http://localhost:8080/api/h2-console` with the following credentials:
- JDBC URL: `jdbc:h2:mem:eventscribedb`
- Username: `sa`
- Password: `password`
