package com.restaurant.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.service.storage.MediaStorageService;

@Service
public class FileUploadService {

    private static final Logger logger =
            LoggerFactory.getLogger(FileUploadService.class);

    // Only image types the app actually serves back to clients are allowed.
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final long MAX_FILE_SIZE_BYTES = 5L * 1024 * 1024; // 5MB

    @Value("${file.upload-dir:${user.home}/rms-uploads}")
    private String uploadDir;

    @Autowired(required = false)
    private MediaStorageService mediaStorageService;

    public String uploadImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required.");
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new IllegalArgumentException(
                    "File exceeds maximum allowed size of 5MB.");
        }

        String contentType = file.getContentType();

        if (contentType == null
                || !ALLOWED_CONTENT_TYPES.contains(
                        contentType.toLowerCase())) {

            throw new IllegalArgumentException(
                    "Only JPEG, PNG, and WEBP images are allowed.");
        }

        String extension = extractSafeExtension(
                file.getOriginalFilename(), contentType);

        String fileName = UUID.randomUUID() + extension;

        if (mediaStorageService != null) {
            return mediaStorageService.store(fileName, file.getBytes(), contentType);
        }

        Path uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path targetPath = uploadRoot.resolve(fileName).normalize();

        if (!targetPath.startsWith(uploadRoot)) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        Files.createDirectories(uploadRoot);
        Files.write(targetPath, file.getBytes());

        logger.info("Image saved at: {}", targetPath);

        return fileName;
    }

    private String extractSafeExtension(
            String originalFilename,
            String contentType) {

        return switch (contentType.toLowerCase()) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> "";
        };
    }
}
