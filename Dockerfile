FROM openjdk:11
COPY  /build/libs/demo-0.0.1-SNAPSHOT.jar Demo-0.0.1.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/Demo-0.0.1.jar"]