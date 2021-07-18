# syntax=docker/dockerfile:experimental
FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM openjdk:11
WORKDIR /app
COPY --from=build /app/target/*.jar /app/readingisgood-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "readingisgood-app.jar"]