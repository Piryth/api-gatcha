FROM maven:3.9.9-amazoncorretto-23-alpine AS build
LABEL authors="champeic"

RUN mkdir -p /workspace
WORKDIR /workspace

COPY . .

RUN mvn -pl discovery-server clean package -DskipTests

FROM amazoncorretto:23.0.2-alpine3.21

RUN mkdir -p /app
WORKDIR /app

COPY --from=build /workspace/discovery-server/target/*.jar app.jar

EXPOSE 8761/tcp

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "./app.jar"]