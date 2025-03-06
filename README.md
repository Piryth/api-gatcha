# Gatcha Game API Project

## Description
This project involves setting up a Gatcha-type game using a microservices architecture. The backend is built with **Spring Boot**, and the frontend is developed using **Vite**. The entire project is containerized with **Docker** and utilizes a **MongoDB** database.

### Microservices Architecture
The project consists of the following microservices:
- **Discovery Server**: Service registry for managing microservices.
- **API Gateway**: Centralized entry point for routing requests.
- **Authentication API**: Manages user authentication and authorization.
- **Player API**: Handles player-related data and monsters.
- **Invocation API**: Manages gatcha summons.
- **Monster API**: Stores and retrieves monster data.
- **Combat API**: Handles battle mechanics and combat logic.
- 
## Prerequisites
Before getting started, make sure you have installed the following:
- [Docker](https://www.docker.com/get-started)
- [Git](https://git-scm.com/)
- [Java 23](https://www.oracle.com/fr/java/technologies/downloads/)

## Installation
1. **Clone the Git repository**
   ```sh
   git clone https://github.com/your-repository/gatcha-game.git
   cd gatcha-game
   ```

2. **Environment configuration**
    - Copy the `.env.example` file to `.env` and modify the variables if necessary:
      ```sh
      cp .env.example .env
      ```
    - Ensure that ports and credentials are correctly set according to your environment.

3. **Start the project with Docker**
   ```sh
   docker compose -f "docker-compose.yml" up -d
   ```
   This command will:
    - Build and start the Docker containers
    - Start all microservices (backend and frontend)
    - Start the MongoDB database

4. **Verify proper functioning**
    - Access the Prometheus monitoring server at [http://localhost:9090](http://localhost:9090)
    - Access the Grafana dashboard at [http://localhost:3000](http://localhost:3000)
    - Access the frontend at [http://localhost:5173](http://localhost:5173) (depending on your configuration)
    - Access the API Gateway at [http://localhost:8888](http://localhost:8888)

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
│   ├── prometheus.yml
├── docker-compose.yml
├── .gitignore
├── README.md
└── pom.xml

19 directories, 22 files

```

## Database
- MongoDB is used as the main database.
- The MongoDB database is seeded with players and invocations on startup.
- MongoDB collections can be initialized with JSON files available in `db/`.
- MongoDB is used as the main database.
- MongoDB collections can be initialized with JSON files available in `db/`.

## Authors
- [@CHAMPEIX_Cédric](https://github.com/cedric-champeix)
- [@DELASSUS_Félix](https://github.com/DelassusFelix)
- [@ENDIGNOUS_Arnaud](https://github.com/Piryth)
- [@VILLET_Téo](https://github.com/teovlt)
