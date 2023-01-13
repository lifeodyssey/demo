#!/bin/sh
SPRING_CONFIG_NAME=$1
NGINX_PORT=$2
MONGODB_URI=$3

if [ "$(docker container ls --filter name="${SPRING_CONFIG_NAME}"_mongo -q)" ]; then
  echo "Container ${SPRING_CONFIG_NAME}_mongo is running"
else
  # Run the container using docker-compose
  docker-compose -f "$(pwd)/mongo/${SPRING_CONFIG_NAME}/docker-compose.yml" up -d
fi

# Check if nginx is running
if [ "$(docker container ls --filter name="${SPRING_CONFIG_NAME}"_nginx -q)" ]; then
  echo "${SPRING_CONFIG_NAME}_nginx is running"
  # Check which app (blue or green) is currently running
  if [ "$(docker container ls -a --filter name=demo_"${SPRING_CONFIG_NAME}"_app_blue -q)" ]; then
    echo "demo_${SPRING_CONFIG_NAME}_app_blue_nginx is running"
    # Run the green app and stop the blue app
    APP_ENV="${SPRING_CONFIG_NAME}" NGINX_PORT="${NGINX_PORT}" MONGODB_URI="$MONGODB_URI" docker-compose -f docker-compose.yml up -d --no-recreate
    docker container rm -f demo_"${SPRING_CONFIG_NAME}"_app_blue
    echo "New version in ${SPRING_CONFIG_NAME} env is demo app green"
  elif [ "$(docker container ls -a --filter name=demo_"${SPRING_CONFIG_NAME}"_app_green -q)" ]; then
    # Run the blue app and stop the green app
    echo "demo_${SPRING_CONFIG_NAME}_app_green_nginx is running"
    APP_ENV="${SPRING_CONFIG_NAME}" NGINX_PORT="${NGINX_PORT}" MONGODB_URI="$MONGODB_URI" docker-compose -f docker-compose.yml up -d --no-recreate
    docker container rm -f demo_"${SPRING_CONFIG_NAME}"_app_green
    echo "New version in ${SPRING_CONFIG_NAME} env is demo app blue"
  else
    # Start nginx and stop the local_demo_blue app
    APP_ENV="${SPRING_CONFIG_NAME}" NGINX_PORT="${NGINX_PORT}" MONGODB_URI="$MONGODB_URI" docker-compose -f docker-compose.yml up -d --no-recreate
    docker container rm -f demo_"${SPRING_CONFIG_NAME}"_app_green
    echo "New version in ${SPRING_CONFIG_NAME} env is demo app blue"
  fi
fi
