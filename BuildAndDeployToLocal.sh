#!/bin/sh

# Copy .env.example to .env if .env doesn't exist
if [ ! -f .env ]; then
    echo "Creating .env from .env.example"
    cp .env.example .env
else
    echo ".env file already exists, using existing file."
fi

. .env

echo "Building book-svc"
./gradlew book-svc:clean
./gradlew book-svc:build

# Check if book-svc build was successful
if [ $? -ne 0 ]; then
    echo "book-svc build failed. Exiting."
    exit 1
fi

echo "Building bff"
./gradlew bff:clean
./gradlew bff:build

# Check if bff build was successful
if [ $? -ne 0 ]; then
    echo "bff build failed. Exiting."
    exit 1
fi

echo "Deploying new version service in local"

docker-compose -f docker-compose.yml up -d --build

echo "Deploying new version service in local"

docker-compose -f docker-compose.yml up -d   --build

