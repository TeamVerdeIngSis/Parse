# syntax=docker/dockerfile:1
# Dockerfile for Kotlin Spring Boot Project using Multi-Stage Build

# Stage 1: Build the application
FROM gradle:8.10-jdk21 AS builder
COPY . /home/gradle/src
WORKDIR /home/gradle/src

# Pasa las variables de entorno USERNAME y PAT_TOKEN como argumentos de compilación
ARG USERNAME
ARG PAT_TOKEN

# Configura las credenciales para acceder al repositorio privado de Maven
RUN echo "machine maven.pkg.github.com login $USERNAME password $PAT_TOKEN" > ~/.netrc && \
    gradle build

# Stage 2: Create a lightweight image for running the application
FROM eclipse-temurin:21-jdk
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar

# Optional: New Relic setup for monitoring
COPY ./newrelic/newrelic.jar /app/newrelic.jar
COPY ./newrelic/newrelic.yml /app/newrelic.yml

ENTRYPOINT ["java", "-javaagent:/app/newrelic.jar", "-jar", "/app/app.jar"]
EXPOSE 8081
