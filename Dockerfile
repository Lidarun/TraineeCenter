# Stage 1: Build the application with Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn package -DskipTests  # Собираем приложение без запуска тестов
RUN ls -l target  # Проверка наличия jar файла в директории target

# Stage 2: Create the final image and copy the jar file
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/training-center-v1.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]