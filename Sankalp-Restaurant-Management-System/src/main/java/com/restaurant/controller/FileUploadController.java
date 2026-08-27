package com.restaurant.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.service.FileUploadService;

@RestController
@CrossOrigin("*")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Value("${file.upload-dir}")
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
            Path uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadRoot.resolve(fileName).normalize();

            if (!filePath.startsWith(uploadRoot) || !Files.exists(filePath) || !Files.isReadable(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] bytes = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "image/png";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName().toString() + "\"")
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}