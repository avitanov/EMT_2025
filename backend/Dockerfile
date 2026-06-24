# syntax=docker/dockerfile:1.7@sha256:a57df69d0ea827fb7266491f2813635de6f17269be881f696fbfdf2d83dda33e

FROM maven:3.9.10-eclipse-temurin-17-alpine@sha256:a38a48ef543af62f2a15c0c3445cea7ccf67555d3fe2ed404082c78eb3ea8f55 AS build

WORKDIR /workspace

COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn -B dependency:go-offline

COPY src/ src/
RUN --mount=type=cache,target=/root/.m2 mvn -B package -DskipTests

FROM eclipse-temurin:17-jre-alpine@sha256:268ed4534cb05c6ebbec22c750c2435183692d399757bf78e103e980ec5208ae

WORKDIR /app

RUN addgroup -S spring && adduser -S -G spring spring

COPY --from=build --chown=spring:spring /workspace/target/*.jar app.jar

USER spring:spring
ARG SERVER_PORT
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
