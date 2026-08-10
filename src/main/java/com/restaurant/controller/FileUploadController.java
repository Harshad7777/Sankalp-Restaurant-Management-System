package com.restaurant.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.service.FileUploadService;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin("*")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/test")
    public String test() {
        return "Upload Controller Working";
    }

    @PostMapping
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
}