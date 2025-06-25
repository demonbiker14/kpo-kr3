# Stage 1: Build the application
FROM maven:3-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml ./
COPY modules/payments/pom.xml modules/payments/
COPY modules/orders/pom.xml modules/orders/

RUN mvn install -DskipTests

# Build the application
COPY . .


WORKDIR /app

