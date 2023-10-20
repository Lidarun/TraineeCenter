FROM alpine:latest

RUN apk add openjdk17-jdk

COPY target/training-center-v1.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]