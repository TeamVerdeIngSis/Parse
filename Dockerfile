# syntax=docker/dockerfile:1
# Dockerfile for Kotlin Spring Boot Project using Multi-Stage Build
FROM gradle:8.10-jdk21 AS builder
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN --mount=type=secret,id=github_token,env=GITHUB_TOKEN,required \
    --mount=type=secret,id=github_username,env=GITHUB_USERNAME,required \
    gradle bootJar

# Second stage: Create a lightweight image for running the application
FROM eclipse-temurin:21-jdk
COPY --from=builder /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]
