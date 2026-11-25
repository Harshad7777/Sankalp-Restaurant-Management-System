package com.restaurant.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Local filesystem implementation of {@link MediaStorageService} for development and standalone Tomcat.
 */
@Service
@ConditionalOnProperty(name = "storage.provider", havingValue = "local", matchIfMissing = true)
public class LocalMediaStorageService implements MediaStorageService {

    private static final Logger logger = LoggerFactory.getLogger(LocalMediaStorageService.class);

    @Value("${file.upload-dir:${user.home}/rms-uploads}")
    private String uploadDir;

    @Override
    public String store(String fileName, byte[] content, String contentType) throws IOException {
        Path uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path targetPath = uploadRoot.resolve(fileName).normalize();

        if (!targetPath.startsWith(uploadRoot)) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        Files.createDirectories(uploadRoot);
        Files.write(targetPath, content);
        logger.info("Stored media locally at: {}", targetPath);
        return fileName;
    }

    @Override
    public byte[] load(String fileName) throws IOException {
        Path uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path filePath = uploadRoot.resolve(fileName).normalize();

        if (!filePath.startsWith(uploadRoot) || !Files.exists(filePath) || !Files.isReadable(filePath)) {
            return null;
        }
        return Files.readAllBytes(filePath);
    }
}
