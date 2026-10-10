# =====================================================
# Apache Tomcat Environment Configuration (setenv.sh)
# Place this file in: apache-tomcat-10.x/bin/setenv.sh
# =====================================================

export DB_URL="jdbc:mysql://localhost:3306/smprowor_RMS?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
export DB_USERNAME="smprowor_harshad"
export DB_PASSWORD="N!63TJd_=N-@aA]!"
export JASYPT_ENCRYPTOR_PASSWORD="SankalpRMSSecretKey2026!"
export JWT_SECRET="SankalpRestaurantManagementSystemSecretKey2026SecureJwtTokenForProductionAndTestingPurposeOnly"
export SERVER_SERVLET_CONTEXT_PATH="/api"
