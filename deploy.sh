#!/bin/sh
APP_ENV=$1
MONGODB_URI=$2

# Function to check the status of the MongoDB container
check_mongo() {
  container_name="${APP_ENV}_mongo"
  if docker ps | grep -q "$container_name" -q; then
    echo "running"
  else
    echo "not running"
  fi
}

# Function to start the MongoDB container
start_mongo() {
  compose_file="$(pwd)/mongo/${APP_ENV}/docker-compose.yml"
  docker-compose -f "$compose_file" up -d
}

# Function to check the status of the nginx container
check_nginx() {
  container_name="${APP_ENV}_nginx"
  if docker ps | grep -q "$container_name" ; then
    echo "running"
  else
    echo "not running"
  fi
}
replace_version() {
  sed "s/CURRENT_VERSION/$new_app/g" nginx/"${APP_ENV}"/original.conf >nginx/"${APP_ENV}"/default.conf
}

redirect_traffic() {
  echo "Redirect traffic"
  replace_version
  docker exec -i "${APP_ENV}_nginx" service nginx reload
  exitcode=$?
  return $exitcode
}
# Function to start the nginx container
start_nginx() {
  compose_file="$(pwd)/nginx/${APP_ENV}/docker-compose.yml"
  docker-compose -f "$compose_file" up -d
}

# Function to check which demo app container is running
get_current_app() {
  container_name_blue="demo_${APP_ENV}_app_blue"
  container_name_green="demo_${APP_ENV}_app_green"
  if docker ps -a --format '{{.Names}}' | grep -q "$container_name_blue"; then
    echo "blue"
  elif docker ps -a --format '{{.Names}}' | grep -q "^$container_name_green"; then
    echo "green"
  else
    echo "no"
  fi
}

get_new_app() {
  if [[ $1 == "green" ]]; then
    echo "blue"
  elif [[ $1 == "blue" ]]; then
    echo "green"
  else
    echo "blue"
  fi
}

# Function to start the new demo app container and stop the old one
start_new_app() {
  current_app=$(get_current_app)
  echo "Current app: $current_app"
  new_app=$(get_new_app "$current_app")
  echo "Starting new $new_app app"
  # Start the green container and stop the blue one
  docker run \
    --env MONGODB_URI="$MONGODB_URI" \
    --name="demo_${APP_ENV}_app_$new_app" \
    --network="${APP_ENV}_application" \
    -d demo:latest
  if [[ "$current_app" != "no" ]]; then
    echo "Stopping"
    docker stop "demo_${APP_ENV}_app_$current_app"
    echo "Removing"
    docker rm "demo_${APP_ENV}_app_$current_app"
  fi
}


# Check the status of the MongoDB container
mongo_status=$(check_mongo)
# If the MongoDB container is not running, start it
if [[ "$mongo_status" == "running" ]] ; then
  echo "MongoDB is running"
else
  start_mongo
fi


# Check the status of the demo app container and start the new one
app_status=$(get_current_app)
new_app=$(get_new_app "$current_app")
start_new_app
echo "$new_app is running"

## Check the status of the nginx container
nginx_status=$(check_nginx)
# If the nginx container is not running, start it
if [[ "$nginx_status" == "running" ]]; then
  echo "nginx is running"
else
  start_nginx
fi
redirect_traffic
