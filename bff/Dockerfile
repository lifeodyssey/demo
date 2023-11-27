FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY /build/libs/bff-0.0.2-SNAPSHOT.jar /app/bff.jar
COPY /entrypoint.sh /app/entrypoint.sh

RUN chmod +x /app/entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["/app/entrypoint.sh"]

