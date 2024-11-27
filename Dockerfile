# syntax=docker/dockerfile:1

# Etapa 1: Construcción de la aplicación
FROM --platform=linux/amd64 gradle:8.10-jdk21 AS build
LABEL org.opencontainers.image.source="https://github.com/teamverdeingsis/parse"

# Build arguments para pasar credenciales
ARG USERNAME
ARG PAT_TOKEN
ENV GITHUB_ACTOR=$USERNAME
ENV GITHUB_TOKEN=$PAT_TOKEN

# Copia del código fuente y construcción
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

# Etapa 2: Imagen ligera para la ejecución
FROM --platform=linux/amd64 eclipse-temurin:21-jdk
EXPOSE 8081
RUN mkdir /app

# Copiar el artefacto generado
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

# Configuración de New Relic (opcional)
COPY ./newrelic/newrelic.jar /app/newrelic.jar
COPY ./newrelic/newrelic.yml /app/newrelic.yml

# Comando de entrada para iniciar la aplicación
ENTRYPOINT ["java", "-javaagent:/app/newrelic.jar", "-jar", "-Dspring.profiles.active=production", "/app/spring-boot-application.jar"]
