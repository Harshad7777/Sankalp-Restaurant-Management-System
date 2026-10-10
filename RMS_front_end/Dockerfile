# ==========================================
# STAGE 1: Build the WAR artifact
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# Cache Maven dependencies
COPY pom.xml .

# Copy source code and package WAR
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Tomcat 10.1 Server Runtime
# ==========================================
FROM tomcat:10.1-jdk17

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy application as the ROOT webapp (accessible at root URL /)
COPY --from=builder /build/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat default HTTP port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
