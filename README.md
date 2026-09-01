
# MioBook: A Bookstore Web Application

This repository contains projects for the Internet Engineering course at the University of Tehran. 

MioBook is a full-stack online bookstore application developed incrementally across multiple phases, covering domain logic, backend development, frontend integration, databases, security, containerization, and orchestration.


## Live Demo

The website is publicly accessible at [**miobook.up.railway.app**](https://miobook.up.railway.app).

To explore the application with pre-populated data, use one of the following accounts:

| Role     | Username | Password |
| -------- | -------- | -------- |
| Customer | `user`   | `1234`   |
| Manager  | `admin`  | `1234`   |

You can also create a new account by signing up.

## How to Run

Alternatively, you can run the project locally using Docker Compose.

### Using Docker Hub Images

Clone the repository and generate the required secrets:

```bash
git clone https://github.com/MehdiJmlkh/MioBook.git
cd MioBook

chmod +x generate-secrets.sh
./generate-secrets.sh
```

Then start the services using the images from Docker Hub:

```bash
docker compose up --pull always
```

### Building the Images Locally

If you prefer to build the images locally, run:

```bash
docker compose up --build
```

Once the services are running, open http://localhost to access the application.

> **Note:** If you run the project locally, Google login is not available because the Google OAuth client secret is not provided.


## Technologies

- **Backend:** Java, Spring Boot, Spring Security, JPA/Hibernate
- **Frontend:** React, HTML, CSS, Bootstrap
- **Databases:** MySQL, Redis
- **DevOps & Deployment:** Docker, Docker Compose, Kubernetes, Railway


## Phases

### P1: Domain & Static Frontend

The core bookstore domain logic is implemented using Spring Boot following the MVC architectural pattern.<br>
Unit tests are written with JUnit to verify the service layer.<br>
Static frontend pages are built using HTML and CSS based on the provided Figma design.<br>
The pages are fully responsive, supporting mobile devices (from 320px) to desktop screens.


### P2: RESTful Backend

New features are added and the backend is completed with RESTful HTTP APIs using Spring Boot.<br>
Unit test coverage is expanded for the service layer.

### P3: React Frontend

The frontend is rebuilt using React to closely match the provided Figma design.<br>
New and updated pages are integrated into the application.<br>
Data fetching and server-state management are implemented using React Query.<br>
Client-side navigation and routing are implemented using React Router.

### P4: Database & ORM

A relational database schema is designed to support persistent data storage using MySQL.<br>
MySQL is integrated with the application using Hibernate ORM and JPA annotations.<br>
Database access is implemented through Spring Data JPA repositories.<br>
Composable and dynamic queries are built using the Spring Data JPA Specification API.<br>
Filtering and pagination are handled at the database level to improve query efficiency.


### P5: Redis Sessions

Redis is used as a NoSQL database for user session management.<br>
A unique session token is generated on login and stored in Redis with the user's identity.<br>
Protected endpoints authenticate requests by validating session tokens against Redis.


### P6: Authentication & Security

User passwords are hashed before being stored in the database.<br>
Redis sessions are replaced with stateless authentication and authorization using JSON Web Tokens.<br>
Access tokens are stored in LocalStorage and sent with requests using the Bearer token.<br>
Refresh tokens are stored in cookies to renew expired access tokens.<br>
Google Sign-In is integrated to support OAuth authorization and OpenID Connect authentication.


### P7: Docker

The back-end and front-end applications are containerized using Dockerfiles.<br>
Nginx serves the front-end and acts as a reverse proxy, forwarding API requests to the back-end.<br>
The resulting Docker images are built and pushed to Docker Hub.<br>
Docker Compose is used to run the complete application stack, including the MySQL database.<br>
Environment variables, secrets, and service health checks are configured through Docker Compose.


### P8: Kubernetes

A multi-node local Kubernetes cluster is created using Kind and managed with kubectl.<br>
ConfigMaps and secrets are used to manage application configuration and sensitive credentials.<br>
Deployments and Services are configured for the front-end, back-end, and MySQL database.<br>
Resource limits and health checks are defined for the deployed services.<br>
The MySQL database is deployed with persistent storage using a persistent volume claim.<br>
The front-end application is exposed locally using kubectl port-forwarding.


### P9: Deployment

The front-end, back-end, and MySQL database are deployed to Railway using Docker images.<br>
Production environment variables and secrets are configured through Railway.


> For detailed descriptions and requirements of each phase, see the corresponding documents in the [`docs`](docs/) directory.
