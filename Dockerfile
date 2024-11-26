# syntax=docker/dockerfile:1
# Dockerfile for Kotlin Spring Boot Project using Multi-Stage Build

# First stage: Build the project
FROM gradle:8.10-jdk21 AS builder
COPY . /home/gradle/src
WORKDIR /home/gradle/src

# Use BuildKit secrets for secure authentication
RUN --mount=type=secret,id=PAT_TOKEN,env=GITHUB_TOKEN,required \
    gradle build


# Second stage: Create a lightweight image for running the application
FROM eclipse-temurin:21-jdk
RUN mkdir /app

# Copy the JAR from the build stage
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar

# Copy New Relic agent and configuration (if needed)
COPY ./newrelic/newrelic.jar /app/newrelic.jar
COPY ./newrelic/newrelic.yml /app/newrelic.yml

# Define the entry point
ENTRYPOINT ["java","-javaagent:/app/newrelic.jar","-jar","/app/app.jar"]

# Expose the application port
EXPOSE 8081
