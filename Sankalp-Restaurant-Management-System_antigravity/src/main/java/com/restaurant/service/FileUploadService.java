package com.restaurant.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${file.upload-dir}")
    private String uploadDir;

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

        // -------------------------------------------------
        // BUILD A SAFE, NON-GUESSABLE FILENAME
        // -------------------------------------------------
        // Never trust the client-supplied original filename directly:
        // it can contain path separators ("../../etc/passwd") or
        // executable extensions. We derive only a whitelisted
        // extension from it and generate the rest ourselves.
        // -------------------------------------------------

        String extension = extractSafeExtension(
                file.getOriginalFilename(), contentType);

        String fileName = UUID.randomUUID() + extension;

        Path uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();

        Path targetPath = uploadRoot.resolve(fileName).normalize();

        // Defense in depth: confirm the resolved path is still inside
        // the upload directory before writing anything to disk.
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

        String ext = switch (contentType.toLowerCase()) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> "";
        };

        return ext;
    }
}
