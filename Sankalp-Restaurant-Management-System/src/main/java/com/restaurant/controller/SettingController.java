package com.restaurant.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.RestaurantSetting;
import com.restaurant.service.SettingService;

@RestController
@RequestMapping("/settings")
@CrossOrigin(origins = "*")
public class SettingController {

    private final SettingService service;

    public SettingController(SettingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<RestaurantSetting> getSettings() {
        return ResponseEntity.ok(service.getSettings());
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateSettings(@RequestBody RestaurantSetting settings) {
        String result = service.updateSettings(settings);
        if ("Settings updated successfully".equals(result)) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", result,
                    "settings", service.getSettings()
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", result
        ));
    }

    @PostMapping(value = "/upload-logo", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadLogo(@org.springframework.web.bind.annotation.RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            String logoUrl = service.uploadLogo(file);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Logo uploaded successfully",
                    "logoUrl", logoUrl,
                    "settings", service.getSettings()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, Object>> resetDefaults() {
        String result = service.resetDefaults();
        if ("Settings reset to defaults successfully".equals(result)) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", result,
                    "settings", service.getSettings()
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", result
        ));
    }
}
