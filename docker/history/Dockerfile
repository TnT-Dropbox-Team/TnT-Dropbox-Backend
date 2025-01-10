FROM openjdk:17-jdk-slim
WORKDIR /app
COPY /target/tntdropbox-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=history,docker", "-jar", "app.jar"]