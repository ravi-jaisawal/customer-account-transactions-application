# === Stage 1: Build the app ===
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# === Stage 2: Run the app ===
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=builder /app/target/customer-account-transactions-application-1.0.0.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
