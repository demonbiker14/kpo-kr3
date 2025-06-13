# Stage 1: Build the application
FROM maven:3.8.4-openjdk-11 AS build

WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the built JAR/WAR from the build stage
COPY --from=build /app/target/order-service-1.0-SNAPSHOT.jar /app.jar

# Expose the application port (if applicable)
EXPOSE 8080

# Command to run the application

ENTRYPOINT ["java","-jar","/app.jar"]