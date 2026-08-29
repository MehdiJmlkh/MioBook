
# MioBook: A Bookstore Web Application

A full-stack bookstore application developed incrementally across multiple phases, covering domain logic, backend development, frontend integration, databases, security, containerization, and orchestration.


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

Once the services are running, open **http://localhost** to access the application.

> **Note:** If you run the project locally, Google login is not available because the Google OAuth client secret is not provided.


## Technologies

- **Backend:** Java, Spring Boot, Spring Security, JPA/Hibernate
- **Frontend:** React, HTML, CSS, Bootstrap
- **Databases:** MySQL, Redis
- **DevOps & Deployment:** Docker, Docker Compose, Kubernetes, Railway


## Phases

### Phase 1 - Domain & Static Frontend

Implemented the core bookstore domain logic with Spring Boot, built static pages using HTML and CSS, and added unit tests.

### Phase 2 - Completed RESTful Backend

Added new features, completed the RESTful HTTP APIs with Spring Boot, and expanded the unit test coverage.

### Phase 3 - React Frontend

Rebuilt the frontend using React and connected it to the backend APIs, while adding and updating application pages.

### Phase 4 - MySQL & ORM

Introduced persistent data storage with MySQL, designed the relational database schema, and integrated it with the application using ORM.

### Phase 5 - Redis Sessions

Introduced Redis as a NoSQL database for efficient and persistent user session management.

### Phase 6 - Authentication & Security

Implemented authentication, authorization, and essential security measures to protect application resources and user data.

### Phase 7 - Docker

Dockerized the frontend and backend and used Docker Compose to run the complete application stack together with its database.

### Phase 8 - Kubernetes

Orchestrated and managed the containerized application using Kubernetes across a distributed environment.

### Phase 9 - Deployment

Deployed the frontend, backend, and database on Railway.


> For detailed descriptions and requirements of each phase, see the corresponding documents in the [`docs`](docs/) directory.
