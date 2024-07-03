# Use the official maven/Java 17 image to create a build artifact.
FROM maven:3.8.3-openjdk-17-slim as builder

# Set the working directory in the builder image to /app
WORKDIR /app

# Copy the pom.xml file into the working directory /app in the builder image
COPY pom.xml .

# Download all required dependencies into one layer
RUN mvn dependency:go-offline -B

# Copy the rest of your app's source code into the working directory /app in the builder image
COPY src src

# package our application code
RUN mvn clean package

# Use the official openjdk image as the runtime base image
FROM openjdk:17-jdk-slim

EXPOSE 8090

# Set the working directory in the runtime container to /app
WORKDIR /app

# Copy the jar file from builder image into the working directory /app in the runtime container
COPY --from=builder /app/target/*.jar /app

# execute the application
CMD ["java", "-jar", "/app/trainingmicroservice-0.0.1-SNAPSHOT.jar"]