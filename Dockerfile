# Use OpenJDK base image
FROM openjdk:17-jdk-alpine

# Set environment variables
ENV APP_HOME=/app

# Create app directory
WORKDIR $APP_HOME

# Copy built JAR into the container
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
