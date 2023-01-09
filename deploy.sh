#!/bin/sh
docker build -t demo . --build-arg SPRING_CONFIG_NAME="${SPRING_CONFIG_NAME}"
if [ "$(docker container ls --filter name="${SPRING_CONFIG_NAME}"_mongo -q)" ]; then
  echo "Container ${SPRING_CONFIG_NAME}_mongo is running"
else
  # Run the container using docker-compose
  docker-compose -f "$(pwd)/mongo/${SPRING_CONFIG_NAME}/docker-compose.yml" up -d
fi

# Check if nginx is running
if [ "$(docker container ls --filter name=nginx -q)" ]; then
  # Check which app (blue or green) is currently running
  if [ "$(docker container ls -a --filter name=demo_"${SPRING_CONFIG_NAME}"_app_blue -q)" ]; then
    # Run the green app and stop the blue app
    docker-compose -f docker-compose.yml up -d -e "${SPRING_CONFIG_NAME}"--no-recreate
    docker container rm -f demo_"${SPRING_CONFIG_NAME}"_app_blue
    echo "New version in ${SPRING_CONFIG_NAME} env is demo app green"
  elif [ "$(docker container ls -a --filter name=demo_"${SPRING_CONFIG_NAME}"_app_green -q)" ]; then
    # Run the blue app and stop the green app
    docker-compose -f docker-compose.yml up -d -e "${SPRING_CONFIG_NAME}" --no-recreate
    docker container rm -f demo_"${SPRING_CONFIG_NAME}"_app_green
    echo "New version in ${SPRING_CONFIG_NAME} env is demo app blue"

  else
    # Start nginx and stop the local_demo_blue app
    docker-compose -f docker-compose.yml up -d -e "${SPRING_CONFIG_NAME}" --no-recreate
    docker container rm -f demo_"${SPRING_CONFIG_NAME}"_app_green
    echo "New version in ${SPRING_CONFIG_NAME} env is demo app blue"
  fi
fi
