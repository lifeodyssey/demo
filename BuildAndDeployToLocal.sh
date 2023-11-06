#!/bin/sh

echo "Building book-svc"
./gradlew book-svc:clean
./gradlew book-svc:build

echo "Building bff"
./gradlew bff:clean
./gradlew bff:build

echo "Deploying new version service in local"

docker-compose -f docker-compose.yml up -d   --build

