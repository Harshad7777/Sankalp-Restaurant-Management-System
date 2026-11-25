# ==========================================
# STAGE 1: Build Spring Boot WAR (Java 21)
# ==========================================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Cache Maven dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and package application
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Production Runtime (512MB-Safe for Render Free / Cloud Run)
# ==========================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Create non-root user for container security
RUN groupadd -r appgroup && useradd -r -g appgroup appuser \
    && mkdir -p /app/uploads /app/logs /tmp/rms-uploads \
    && chown -R appuser:appgroup /app /tmp/rms-uploads

COPY --from=builder /build/target/api.war /app/app.war
RUN chown appuser:appgroup /app/app.war

USER appuser

ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:+UseSerialGC -Xms128m -Xmx320m -Djava.awt.headless=true -Dfile.encoding=UTF-8"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.war --server.port=${PORT}"]
