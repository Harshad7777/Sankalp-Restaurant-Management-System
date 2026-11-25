package com.restaurant.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.model.RestaurantSetting;
import com.restaurant.repository.SettingRepository;
import com.restaurant.service.storage.MediaStorageService;

@Service
public class SettingService {

    @Value("${file.upload-dir:${user.home}/rms-uploads}")
    private String uploadDir;

    private final SettingRepository repository;

    @Autowired(required = false)
    private MediaStorageService mediaStorageService;

    public SettingService(SettingRepository repository) {
        this.repository = repository;
    }

    public RestaurantSetting getSettings() {
        return repository.getSettings();
    }

    public String updateSettings(RestaurantSetting settings) {
        if (settings == null) {
            return "Settings payload cannot be empty";
        }
        if (settings.getRestaurantName() == null || settings.getRestaurantName().trim().isEmpty()) {
            return "Restaurant name is required";
        }
        if (settings.getGstRate() != null && settings.getGstRate() < 0) {
            return "GST rate cannot be negative";
        }

        int updated = repository.updateSettings(settings);
        return updated > 0 ? "Settings updated successfully" : "Failed to update settings";
    }

    public String uploadLogo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("No file provided for upload");
        }

        try {
            String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "logo.png";
            String cleanName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String fileName = "logo_" + UUID.randomUUID().toString().substring(0, 8) + "_" + cleanName;

            if (mediaStorageService != null) {
                mediaStorageService.store(fileName, file.getBytes(), file.getContentType());
            } else {
                File folder = new File(uploadDir);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File destination = new File(folder, fileName);
                file.transferTo(destination);
            }

            String logoUrl = "/uploads/" + fileName;

            // Update database setting with new logo path
            RestaurantSetting current = repository.getSettings();
            current.setLogoPath(logoUrl);
            repository.updateSettings(current);

            return logoUrl;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save uploaded logo: " + e.getMessage(), e);
        }
    }

    public String resetDefaults() {
        int result = repository.resetDefaults();
        return result > 0 ? "Settings reset to defaults successfully" : "Failed to reset settings";
    }
}
