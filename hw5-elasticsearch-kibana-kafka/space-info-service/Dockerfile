# Use Maven for building the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory for the build stage
WORKDIR /app

# Copy the pom.xml and src directory from the space-info-service context
COPY space-info-service/pom.xml .
COPY space-info-service/src ./src

# Run Maven to clean and package the application
RUN mvn -B -DskipTests clean package

# Use lightweight OpenJDK image for the runtime environment
FROM openjdk:17-alpine

# Set the working directory for the runtime stage
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar space-info-service.jar

# Expose the port the application will run on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "space-info-service.jar"]
