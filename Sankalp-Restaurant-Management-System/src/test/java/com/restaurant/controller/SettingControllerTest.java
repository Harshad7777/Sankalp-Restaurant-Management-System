package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.model.RestaurantSetting;
import com.restaurant.service.SettingService;

@ExtendWith(MockitoExtension.class)
class SettingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SettingService settingService;

    @InjectMocks
    private SettingController settingController;

    private ObjectMapper objectMapper;
    private RestaurantSetting testSetting;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(settingController).build();
        objectMapper = new ObjectMapper();
        testSetting = new RestaurantSetting();
        testSetting.setId(1);
        testSetting.setRestaurantName("Sankalp RMS");
        testSetting.setGstRate(5.0);
    }

    @Test
    @DisplayName("Should get settings")
    void testGetSettings() throws Exception {
        when(settingService.getSettings()).thenReturn(testSetting);

        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value("Sankalp RMS"))
                .andExpect(jsonPath("$.gstRate").value(5.0));
    }

    @Test
    @DisplayName("Should update settings successfully")
    void testUpdateSettings_Success() throws Exception {
        when(settingService.updateSettings(any(RestaurantSetting.class))).thenReturn("Settings updated successfully");
        when(settingService.getSettings()).thenReturn(testSetting);

        mockMvc.perform(put("/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testSetting)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Settings updated successfully"));
    }

    @Test
    @DisplayName("Should return 400 when settings update fails")
    void testUpdateSettings_Failure() throws Exception {
        when(settingService.updateSettings(any(RestaurantSetting.class))).thenReturn("Restaurant name is required");

        mockMvc.perform(put("/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testSetting)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Restaurant name is required"));
    }
}
