package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.model.RestaurantSetting;
import com.restaurant.repository.SettingRepository;

@ExtendWith(MockitoExtension.class)
class SettingServiceTest {

    @Mock
    private SettingRepository repository;

    @InjectMocks
    private SettingService settingService;

    private RestaurantSetting testSetting;

    @BeforeEach
    void setUp() {
        testSetting = new RestaurantSetting();
        testSetting.setId(1);
        testSetting.setRestaurantName("Sankalp RMS");
        testSetting.setGstRate(5.0);
    }

    @Test
    @DisplayName("Should get settings")
    void testGetSettings() {
        when(repository.getSettings()).thenReturn(testSetting);

        RestaurantSetting result = settingService.getSettings();
        assertNotNull(result);
        assertEquals("Sankalp RMS", result.getRestaurantName());
    }

    @Test
    @DisplayName("Should update settings successfully")
    void testUpdateSettings_Success() {
        when(repository.updateSettings(any(RestaurantSetting.class))).thenReturn(1);

        String result = settingService.updateSettings(testSetting);
        assertEquals("Settings updated successfully", result);
    }

    @Test
    @DisplayName("Should reject update if settings is null")
    void testUpdateSettings_NullSettings() {
        String result = settingService.updateSettings(null);
        assertEquals("Settings payload cannot be empty", result);
    }

    @Test
    @DisplayName("Should reject update if restaurant name is empty")
    void testUpdateSettings_EmptyName() {
        testSetting.setRestaurantName(" ");
        String result = settingService.updateSettings(testSetting);
        assertEquals("Restaurant name is required", result);
    }

    @Test
    @DisplayName("Should reject update if GST rate is negative")
    void testUpdateSettings_NegativeGst() {
        testSetting.setGstRate(-1.0);
        String result = settingService.updateSettings(testSetting);
        assertEquals("GST rate cannot be negative", result);
    }
}
