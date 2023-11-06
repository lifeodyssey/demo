FROM openjdk:17

COPY /build/libs/book-svc-0.0.2-SNAPSHOT.jar /app/book-svc.jar
#COPY /entrypoint.sh /app/entrypoint.sh

WORKDIR /app

#RUN chmod +x entrypoint.sh

EXPOSE 3000
CMD ["java", "-jar", "/app/book-svc.jar"]

#ENTRYPOINT ["./entrypoint.sh"]
