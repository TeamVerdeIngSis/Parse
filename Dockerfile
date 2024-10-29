# Dockerfile for Kotlin Spring Boot Project using Multi-Stage Build

# First stage: Build the project
FROM gradle:8.10-jdk21 AS builder

# Copia el código fuente del proyecto
COPY . /home/gradle/src
WORKDIR /home/gradle/src

# Ejecuta la construcción del proyecto
RUN gradle build --no-daemon

# Second stage: Create a lightweight image for running the application
FROM openjdk:21-jdk-slim
COPY --from=builder /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
