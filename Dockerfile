# syntax=docker/dockerfile:1
FROM gradle:8.10-jdk21 AS builder

ARG GITHUB_ACTOR
ARG GITHUB_TOKEN
ENV GITHUB_ACTOR=$GITHUB_ACTOR
ENV GITHUB_TOKEN=$GITHUB_TOKEN

COPY . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle build

FROM eclipse-temurin:21-jdk
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar
COPY ./newrelic/newrelic.jar /app/newrelic.jar
COPY ./newrelic/newrelic.yml /app/newrelic.yml

ENTRYPOINT ["java","-javaagent:/app/newrelic.jar","-jar","/app/app.jar"]

EXPOSE 8081
