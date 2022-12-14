#!/bin/sh

MONGO_CONTAINER_EXIST=$(docker ps -q -f name=deploy_mongo)
DOCKER_SWARM_EXIST=$(docker info 2>/dev/null | grep -q "Swarm: active")
SERVICE_EXIST=$(docker service ls | grep -q "book_service")

echo "Deploy Start"
if [ -n "$MONGO_CONTAINER_EXIST" ]; then
  echo "The deploy mongo container is already running"
else
  CURRENT_DIR=$(pwd)
  docker-compose -f "$CURRENT_DIR/mongo/deploy/docker-compose.yml" up -d
  echo "The deploy mongo container is running"
fi

if [ -n "$DOCKER_SWARM_EXIST" ]; then
  echo"The docker swarm is running"
else
  docker swarm init
fi

docker build -t book_demo:latest .
docker tag book_demo:latest book_demo:latest

if [ -n "$SERVICE_EXIST" ]; then
  docker service update \
    book_demo:latest \
    --detach=false \
    --name book_service
else
  docker service create\
    --detach=false \
    --name book_service\
    --publish 3000:3000\
    book_demo:latest
fi
docker swarm leave
echo "Deployment complete"
