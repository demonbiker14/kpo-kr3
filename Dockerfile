# Stage 1: Build the application
FROM maven:3-eclipse-temurin-11 AS build

WORKDIR /app

COPY pom.xml .
COPY modules/payments/pom.xml modules/payments/
COPY modules/orders/pom.xml modules/orders/

RUN mvn install -DskipTests

# Build the application
COPY . .
RUN mvn jar:jar

# Stage 2: Create the runtime image
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the built JAR/WAR from the build stage
COPY --from=build /app /app
RUN ls /app/modules/orders

# Expose the application port (if applicable)
EXPOSE 8080

