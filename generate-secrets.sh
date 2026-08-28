#!/bin/bash

set -e

mkdir -p ./secrets

openssl rand -base64 32 > ./secrets/db_root_password.txt
openssl rand -base64 64 > ./secrets/jwt_secret.txt
touch ./secrets/oauth_google_client_secret.txt

echo "Secrets generated successfully."