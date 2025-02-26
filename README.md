# TnT Dropbox

The TnT Dropbox project is designed to address issues related to secure and efficient storage, sharing, and management of files in a digital environment. Users often require a platform that enables seamless collaboration, tracking, and access control while ensuring security and easy access anytime and anywhere. Our solution offers a robust application based on microservices, with features for file storage and management, notifications, activity tracking, and collaboration.

## Architecture Diagram

![Architecture Diagram](assets/shema.drawio.png)

## Microservices Design and REST API Endpoints

- **User Service:**
  - `GET /users` – Returns a list of all user profiles.
  - `GET /users/{id}` – Returns data about a specific user based on ID.
  - `POST /users/register` – Creates a new user profile (registration).
  - `POST /users/login` – Logs the user into the system.
  - `PUT /users/{id}` – Updates an existing user's data based on ID (password change, etc.).
  - `DELETE /users/{id}` – Deletes a user profile based on ID.

- **File Service:**
  - `GET /files/user/{userId}` – Returns a list of all files (of the logged-in user).
  - `GET /files/group/{GrId}` – Returns a list of all files for a specific user group.
  - `GET /files/{id}` – Returns a file based on ID.
  - `POST /files` – Saves a new file.
  - `POST /files/{fileId}/groups/{GrId}` – Saves a file to a specific group.
  - `PUT /files/{id}` – Updates a file.
  - `DELETE /files/{id}` – Deletes a file.

- **Group Service:**
  - `GET /groups` – Returns a list of all groups (of the logged-in user).
  - `GET /groups/{id}` – Returns data about a specific group.
  - `GET /groups/{id}/members` – Returns members of a specific group.
  - `POST /groups` – Creates a group.
  - `PUT /groups/{id}` – Updates group data.
  - `PUT /groups/{id}/add/{userId}` – Adds a new user to the group.
  - `DELETE /groups/{id}` – Deletes a group.
  - `DELETE /groups/{id}/remove/{id}` – Removes a user from the group.

- **Comment Service:**
  - `GET /comments/group/{GrId}` – Returns a list of comments for a specific group.
  - `POST /comments/group/{GrId}` – Adds a comment to a group.
  - `PUT /comments/{id}` – Edits a comment.
  - `DELETE /comments/{id}` – Deletes a comment.

- **Notification Service:**
  - `GET /notifications` – Returns a list of notifications (for the logged-in user).
  - `GET /notifications/{id}` – Returns the content of a notification.
  - `POST /notifications/to/{UserID}` – Sends a notification to a specific user (admin/microservice).
  - `DELETE /notifications` – Deletes all received notifications of a user.
  - `DELETE /notifications/{id}` – Deletes a specific notification.

- **History Service:**
  - `GET /logs/user/{id}` – Returns a list of event logs for a specific user.
  - `GET /logs/{id}` – Details of a specific log entry.
  - `POST /logs` – Creates a new log entry.

## Database Design

![Database Design](assets/ER.png)

## Setup Instructions

### Local Setup

You can start individual microservices by selecting the appropriate profile:

- The **user** microservice starts on port **8081**:
  ```cmd
  mvn spring-boot:run "-Dspring-boot.run.profiles=user"
  ```
- The **file** microservice starts on port **8082**:
  ```cmd
  mvn spring-boot:run "-Dspring-boot.run.profiles=file"
  ```
- The **group** microservice starts on port **8083**:
  ```cmd
  mvn spring-boot:run "-Dspring-boot.run.profiles=group"
  ```
- The **comment** microservice starts on port **8084**:
  ```cmd
  mvn spring-boot:run "-Dspring-boot.run.profiles=comment"
  ```
- The **notification** microservice starts on port **8085**:
  ```cmd
  mvn spring-boot:run "-Dspring-boot.run.profiles=notification"
  ```
- The **history** microservice starts on port **8086**:
  ```cmd
  mvn spring-boot:run "-Dspring-boot.run.profiles=history"
  ```

You can also test-run all services together:

- The test version runs on port **3000**:
  ```cmd
  mvn spring-boot:run "-Dspring-boot.run.profiles=test"
  ```

### Docker Setup

First, run the following command in the root directory:

```cmd
mvn clean install
```

Then navigate to the `docker` directory:

```cmd
cd docker
```

You can manually build and run Docker images separately for each service:

- **user**
  ```cmd
  docker build -t user_service -f user/Dockerfile ..
  docker run -d --name user_service_container -p 8081:8081 user_service
  ```
- **file**
  ```cmd
  docker build -t file_service -f file/Dockerfile ..
  docker run -d --name file_service_container -p 8082:8082 file_service
  ```
- ... similarly for other services and Dockerfiles.

You can also build and run all Docker images at once using:

```cmd
docker compose up --build
```

All Docker images are also available in the Docker Hub [repository](https://hub.docker.com/repository/docker/tcerne/tntdropbox).
