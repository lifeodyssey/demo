FROM openjdk:11
ARG SPRING_CONFIG_NAME
COPY  /build/libs/demo-0.0.2-SNAPSHOT.jar Demo-0.0.2.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/Demo-0.0.2.jar","--spring.profiles.active=${SPRING_CONFIG_NAME}"]