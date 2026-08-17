package com.restaurant;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

public class JasyptEncryptorTest {

    @Test
    public void generateEncryptedDatabasePassword() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("SankalpRMSSecretKey2026!");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String rawDbPassword = "root";
        String encrypted = encryptor.encrypt(rawDbPassword);
        System.out.println("==================================================");
        System.out.println("RAW DATABASE PASSWORD: " + rawDbPassword);
        System.out.println("ENCRYPTED VALUE FOR application.properties: ENC(" + encrypted + ")");
        System.out.println("DECRYPTED VERIFICATION: " + encryptor.decrypt(encrypted));
        System.out.println("==================================================");
    }
}
