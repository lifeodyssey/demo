FROM openjdk:17
COPY  /build/libs/book-svc-0.0.2-SNAPSHOT.jar book-svc-0.0.2-SNAPSHOT.jar
COPY /entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
EXPOSE 3000
ENTRYPOINT ./entrypoint.sh
#ENTRYPOINT ["java","-jar","Demo-0.0.2.jar"]