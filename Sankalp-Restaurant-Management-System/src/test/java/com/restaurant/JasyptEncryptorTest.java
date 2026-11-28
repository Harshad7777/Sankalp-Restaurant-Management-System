package com.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;

class JasyptEncryptorTest {

    private PooledPBEStringEncryptor buildEncryptor(String secretKey) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(secretKey);
        config.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGenerator(new RandomIvGenerator());
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

    @Test
    void testStrongEncryptionRoundTripWithoutLeakingSecrets() {
        String ephemeralTestKey = "test-only-" + UUID.randomUUID();
        String sampleValue = "sample-credential-" + UUID.randomUUID();

        PooledPBEStringEncryptor encryptor = buildEncryptor(ephemeralTestKey);
        String encrypted = encryptor.encrypt(sampleValue);

        assertNotNull(encrypted);
        assertFalse(encrypted.isBlank());
        assertNotEquals(sampleValue, encrypted);
        assertEquals(sampleValue, encryptor.decrypt(encrypted));
    }

    @Test
    void testDecryptionWithWrongKeyFails() {
        PooledPBEStringEncryptor encryptor = buildEncryptor("key-one-" + UUID.randomUUID());
        PooledPBEStringEncryptor wrongEncryptor = buildEncryptor("key-two-" + UUID.randomUUID());

        String encrypted = encryptor.encrypt("sensitive-payload");

        assertThrows(EncryptionOperationNotPossibleException.class, () -> wrongEncryptor.decrypt(encrypted));
    }
}
