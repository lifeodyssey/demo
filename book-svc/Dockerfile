FROM openjdk:17
COPY  /build/libs/demo-0.0.2-SNAPSHOT.jar Demo-0.0.2.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
EXPOSE 3000
ENTRYPOINT ./entrypoint.sh
#ENTRYPOINT ["java","-jar","Demo-0.0.2.jar"]