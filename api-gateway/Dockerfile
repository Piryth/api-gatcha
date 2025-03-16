FROM maven:3.9.9-amazoncorretto-23-alpine AS build
LABEL authors="champeic"

ARG PUBLIC_KEY_PATH

RUN mkdir -p /workspace
WORKDIR /workspace

COPY . .

COPY --from=openssl-keygen:latest ${PUBLIC_KEY_PATH} /workspace/api-gateway/src/main/resources/certs/public.pem

RUN mvn -pl api-gateway clean package -DskipTests

FROM amazoncorretto:23.0.2-alpine3.21

RUN mkdir -p /app
WORKDIR /app

COPY --from=build /workspace/api-gateway/target/*.jar app.jar

EXPOSE 8888/tcp

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "./app.jar"]