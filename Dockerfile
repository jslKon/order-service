# First stage: Build the application using Maven
FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline
RUN mvn clean install

# Second stage: Run the application using a smaller JDK base image
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar ./order-service.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar order-service.jar"]