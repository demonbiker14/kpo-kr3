# Stage 1: Build the application
FROM maven:3-eclipse-temurin-11 AS build

WORKDIR /app

COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the built JAR/WAR from the build stage
COPY --from=build /app /app

# Expose the application port (if applicable)
EXPOSE 8080

# Command to run the application

ENTRYPOINT ["java","-jar","/app.jar"]