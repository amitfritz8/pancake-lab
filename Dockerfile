# Stage 1: Build the application using Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/PancakeLab-1.0-SNAPSHOT.jar /app/PancakeLab.jar

# Command to run the application
CMD ["java", "-jar", "PancakeLab.jar"]
