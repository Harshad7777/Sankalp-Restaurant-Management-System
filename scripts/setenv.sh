#!/bin/sh
# =====================================================
# Apache Tomcat Environment Configuration Template
# Copy to: apache-tomcat-10.x/bin/setenv.sh (do not commit real secrets)
# =====================================================

export DB_URL='jdbc:mysql://localhost:3306/smprowor_RMS?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true'
export DB_USERNAME='CHANGE_ME_DB_USER'
export DB_PASSWORD='CHANGE_ME_DB_PASSWORD'
export DB_POOL_SIZE='15'
export DB_MIN_IDLE='3'

export JASYPT_ENCRYPTOR_PASSWORD='CHANGE_ME_JASYPT_KEY'
export JWT_SECRET='CHANGE_ME_JWT_SECRET_AT_LEAST_32_BYTES_LONG'

export SERVER_SERVLET_CONTEXT_PATH='/api'
export APP_FRONTEND_URL='https://your-domain.example.com'
export FILE_UPLOAD_DIR='/var/lib/rms/uploads'
export LOG_DIR='/var/log/rms'

export LOG_LEVEL_ROOT='INFO'
export LOG_LEVEL_APP='INFO'
export LOG_LEVEL_JDBC='WARN'
export SWAGGER_ENABLED='false'

export CATALINA_OPTS="$CATALINA_OPTS -server -Xms256m -Xmx512m -Djava.awt.headless=true -Dfile.encoding=UTF-8"
