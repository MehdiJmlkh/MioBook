

## How to Run

The website is publicly accessible at **[miobook.up.railway.app](https://miobook.up.railway.app)**.

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

> **Note:** If you run the project locally, Google login is not available because the Google OAuth client secret is not provided.

