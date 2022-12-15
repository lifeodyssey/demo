#!/bin/sh
docker build -t demo . -q
if [ "$(docker container ls --filter name=deploy_mongo -q)" ]; then
  echo "Container deploy_mongo is running"
else
  # Run the container using docker-compose
  docker-compose -f "$(pwd)/mongo/deploy/docker-compose.yml" up -d
fi

# Check if nginx is running
if [ "$(docker container ls --filter name=nginx -q)" ]; then
  # Check which app (blue or green) is currently running
  if [ "$(docker container ls --filter name=demo_app_blue -q)" ]; then
    # Run the green app and stop the blue app
    docker-compose -f docker-compose.yml up -d --no-recreate
    docker container stop demo_app_blue
    echo "New version is demo app green"
  elif [ "$(docker container ls --filter name=demo_app_green -q)" ]; then
    # Run the blue app and stop the green app
    docker-compose -f docker-compose.yml up -d --no-recreate
    docker container stop demo_app_green
    echo "New version is demo app blue"

  else
    # Start nginx and stop the local_demo_blue app
    docker-compose -f docker-compose.yml up -d --no-recreate
    docker container stop demo_app_green
    echo "New version is demo app blue"
  fi
fi
