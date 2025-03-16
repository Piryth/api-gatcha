# Gatcha Game API Project

## Description
This project involves setting up a Gatcha-type game using a microservice architecture. The backend is built with **Spring Boot**, and the frontend is developed using **Vite**. The entire project is containerized with **Docker** and utilizes a **MongoDB** database.

### Microservices

The project consists of the following microservices:

- **Discovery Server**: Service registry for managing microservices.
- **API Gateway**: Centralized entry point for routing requests.
- **Authentication API**: Manages user authentication and authorization.
- **Player API**: Handles player-related data and monsters.
- **Invocation API**: Manages gatcha summons.
- **Monster API**: Stores and retrieves monster data.
- **Combat API**: Handles battle mechanics and combat logic.

## Prerequisites
Before getting started, make sure you have installed the following:
- [Docker](https://www.docker.com/get-started)
- [Git](https://git-scm.com/)
- [Java 23](https://www.oracle.com/fr/java/technologies/downloads/)

## Installation

### 1. **Clone the Git repository**

```sh
git clone https://github.com/Piryth/api-gatcha.git
cd api-gatcha
```

### 2. **Generate the public & private keys for local environment**

```sh
# create key pair
openssl genrsa -out keypair.pem 2048

# extract public key
openssl rsa -in keypair.pem -pubout -out ./api-gateway/src/main/ressources/certs/jwt_public.pem

# extract private key
openssl pkcs8 -in keypair.pem -topk8 -nocrypt -inform PEM -outform PEM -out ./api-auth/src/main/ressources/certs/jwt_private.pem
```

**Note 1:** The private key is used by the Authentication API to sign the JWT token, while the public key is used by the API Gateway or any other service that needs it to verify the JWT token.

**Note 2:** When running from docker containers, the keys are generated automatically by the openssl-keygen image. If you want to use new keys, simply rebuild the image.

### 3. **Start the project with Docker**

```sh
docker compose -f "docker-compose.yml" up -d
```

**This command will:**

- Build and start the Docker containers
- Start all microservices (backend and frontend)
- Start the MongoDB database

### 4. **Verify proper functioning**

- Access the frontend at [http://localhost:5173](http://localhost:5173) (depending on your configuration)
- Access the API Gateway at [http://localhost:8888](http://localhost:8888)
- Access the Prometheus monitoring server at [http://localhost:9090](http://localhost:9090)
- Access the Grafana dashboard at [http://localhost:3000](http://localhost:3000)

## Database

- We use MongoDB as the main database for this project.
- It is seeded with players and invocations on startup.
- MongoDB collections can be initialized with JSON files available in `docker-conf/mongodb/seed`.

You can access the MongoDB database using the following credentials:
- **Username:** root
- **Password:** example
- **Port:** 27019

or on this URL: [mongodb://root:example@localhost:27019/](mongodb://root:example@localhost:27019/)

## Endpoint documentation

You can get all the services **swagger documentation** from the gateway at [http://localhost:8888/swagger-ui.html](http://localhost:8888/swagger-ui.html)

## Project Structure
```
/
├── discovery-server/ # Eureka Discovery Server
│   ├── pom.xml
│   └── src/
├── api-gateway/ # API Gateway
│   ├── pom.xml
│   └── src/
├── api-auth/ # Authentication API
│   ├── pom.xml
│   └── src/
├── api-invocation/ # Invocation API
│   ├── pom.xml
│   ├── src/
├── api-monster/ # Monster API
│   ├── pom.xml
│   ├── src/
├── api-player/ # Player API
│   ├── pom.xml
│   ├── src/
├── website/ # Frontend
│   ├── src/
│   ├── components.json
│   ├── index.html
│   ├── package.json
│   └── README.md
├── docker-conf/
│   ├── mongodb/
│   ├── openssl/
│   ├── prometheus.yml
├── docker-compose.yml
├── .gitignore
├── README.md
└── pom.xml
```

## Authors

- [@CHAMPEIX_Cédric](https://github.com/cedric-champeix)
- [@DELASSUS_Félix](https://github.com/DelassusFelix)
- [@ENDIGNOUS_Arnaud](https://github.com/Piryth)
- [@VILLET_Téo](https://github.com/teovlt)
