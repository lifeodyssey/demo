FROM openjdk:17
ENV SPRING_CONFIG_NAME=deploy
ENV MONGODB_URI="mongodb://local_mongo_username:local_mongo_pwd@localhost:27017/?authSource=admin&tls=false"
COPY  /build/libs/demo-0.0.2-SNAPSHOT.jar Demo-0.0.2.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/Demo-0.0.2.jar","--spring.profiles.active=${SPRING_CONFIG_NAME}"]