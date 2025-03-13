FROM maven:3.9.9-amazoncorretto-23-alpine AS build
LABEL authors="champeic"

ARG PRIVATE_KEY_PATH
ARG PUBLIC_KEY_PATH

RUN mkdir -p /workspace
WORKDIR /workspace

COPY . .

COPY --from=openssl-keygen:latest ${PRIVATE_KEY_PATH} /workspace/api-auth/src/main/resources/certs/private.pem
COPY --from=openssl-keygen:latest ${PUBLIC_KEY_PATH} /workspace/api-auth/src/main/resources/certs/public.pem

RUN mvn -pl api-auth clean package -DskipTests

FROM amazoncorretto:23.0.2-alpine3.21

RUN mkdir -p /app
WORKDIR /app

COPY --from=build /workspace/api-auth/target/*.jar app.jar

EXPOSE 8080/tcp

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "./app.jar"]