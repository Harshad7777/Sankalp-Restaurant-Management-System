# ==========================================
# STAGE 1: Build the Spring Boot application
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# Cache Maven dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and package application (skip tests for faster build)
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Lightweight Runtime Container
# ==========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Create directories for logs and file uploads
RUN mkdir -p /app/uploads /app/logs

# Copy WAR from builder stage
COPY --from=builder /build/target/*.war app.war

# Expose Spring Boot port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.war"]
