package com.restaurant.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.service.FileUploadService;
import com.restaurant.service.storage.MediaStorageService;

@RestController
@CrossOrigin("*")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Autowired(required = false)
    private MediaStorageService mediaStorageService;

    @Value("${file.upload-dir:${user.home}/rms-uploads}")
    private String uploadDir;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/upload/test")
    public String test() {
        return "Upload Controller Working";
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        String fileName = fileUploadService.uploadImage(file);

        return ResponseEntity.ok(
                Map.of(
                        "message", "File Uploaded Successfully",
                        "fileName", fileName
                ));
    }

    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String fileName) {
        try {
            byte[] bytes = null;
            String contentType = null;

            if (mediaStorageService != null) {
                bytes = mediaStorageService.load(fileName);
                contentType = mediaStorageService.detectContentType(fileName);
            } else {
                Path uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
                Path filePath = uploadRoot.resolve(fileName).normalize();

                if (filePath.startsWith(uploadRoot) && Files.exists(filePath) && Files.isReadable(filePath)) {
                    bytes = Files.readAllBytes(filePath);
                    contentType = Files.probeContentType(filePath);
                }
            }

            if (bytes == null) {
                return ResponseEntity.notFound().build();
            }

            if (contentType == null) {
                contentType = "image/png";
            }

            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS).cachePublic())
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + Paths.get(fileName).getFileName().toString() + "\"")
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
