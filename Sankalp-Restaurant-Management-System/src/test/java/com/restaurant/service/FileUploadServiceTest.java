package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

class FileUploadServiceTest {

    private FileUploadService fileUploadService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileUploadService = new FileUploadService();
        ReflectionTestUtils.setField(fileUploadService, "uploadDir", tempDir.toString());
    }

    @Test
    @DisplayName("Should successfully upload valid JPEG image")
    void testUploadImage_Success() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "dish.jpg", "image/jpeg", "sample-image-data".getBytes());

        String savedFileName = fileUploadService.uploadImage(file);

        assertNotNull(savedFileName);
        assertTrue(savedFileName.endsWith(".jpg"));
        assertTrue(Files.exists(tempDir.resolve(savedFileName)));
    }

    @Test
    @DisplayName("Should reject empty file upload")
    void testUploadImage_EmptyFile() {
        MockMultipartFile file = new MockMultipartFile("file", "empty.jpg", "image/jpeg", new byte[0]);

        assertThrows(IllegalArgumentException.class, () -> fileUploadService.uploadImage(file));
    }

    @Test
    @DisplayName("Should reject invalid content type")
    void testUploadImage_InvalidContentType() {
        MockMultipartFile file = new MockMultipartFile("file", "script.sh", "text/plain", "echo hello".getBytes());

        assertThrows(IllegalArgumentException.class, () -> fileUploadService.uploadImage(file));
    }
}
