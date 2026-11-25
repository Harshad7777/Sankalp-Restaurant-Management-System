# ==========================================
# STAGE 1: Build Frontend WAR (Java 21)
# ==========================================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Use Google Cloud Maven Central mirror to avoid 429 rate-limits on shared CI/Render builders
RUN mkdir -p /root/.m2 && printf '<settings><mirrors><mirror><id>google-maven-central</id><name>GCS Maven Central mirror</name><url>https://maven-central.storage-download.googleapis.com/maven2</url><mirrorOf>central</mirrorOf></mirror></mirrors></settings>' > /root/.m2/settings.xml

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -B

# ==========================================
# STAGE 2: Tomcat 10.1 JDK 21 Runtime (512MB-Safe for Render Free / Cloud Run)
# ==========================================
FROM tomcat:10.1-jdk21

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy application as ROOT webapp
COPY --from=builder /build/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

ENV PORT=8080
ENV CATALINA_OPTS="-Xms64m -Xmx256m -XX:+UseSerialGC -Djava.awt.headless=true -Dfile.encoding=UTF-8"
EXPOSE 8080

# Bind Tomcat HTTP connector dynamically to $PORT and disable shutdown port noise
CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT}\\\"/g; s/port=\\\"8005\\\"/port=\\\"-1\\\"/g\" /usr/local/tomcat/conf/server.xml && catalina.sh run"]
