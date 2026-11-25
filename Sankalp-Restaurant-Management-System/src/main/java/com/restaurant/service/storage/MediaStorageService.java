package com.restaurant.service.storage;

import java.io.IOException;

/**
 * Abstraction for storing and retrieving uploaded media assets (menu images, restaurant logos).
 * Supports local disk storage for development and Google Cloud Storage (GCS) for production.
 */
public interface MediaStorageService {

    /**
     * Stores the given bytes under {@code fileName} and returns the stored object/file name.
     */
    String store(String fileName, byte[] content, String contentType) throws IOException;

    /**
     * Loads the bytes for {@code fileName}, or returns {@code null} if not found.
     */
    byte[] load(String fileName) throws IOException;

    /**
     * Resolves a safe content type for {@code fileName}.
     */
    default String detectContentType(String fileName) {
        if (fileName == null) {
            return "application/octet-stream";
        }
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (lower.endsWith(".png")) {
            return "image/png";
        }
        if (lower.endsWith(".webp")) {
            return "image/webp";
        }
        if (lower.endsWith(".gif")) {
            return "image/gif";
        }
        if (lower.endsWith(".svg")) {
            return "image/svg+xml";
        }
        return "application/octet-stream";
    }
}
