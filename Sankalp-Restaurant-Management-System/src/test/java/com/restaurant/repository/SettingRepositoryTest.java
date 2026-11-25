package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.restaurant.model.RestaurantSetting;

@ExtendWith(MockitoExtension.class)
class SettingRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SettingRepository settingRepository;

    @Test
    @DisplayName("Should get settings")
    void testGetSettings() {
        RestaurantSetting setting = new RestaurantSetting();
        setting.setId(1);
        setting.setRestaurantName("Sankalp RMS");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any()))
                .thenReturn(Collections.singletonList(setting));

        RestaurantSetting result = settingRepository.getSettings();
        assertNotNull(result);
        assertEquals("Sankalp RMS", result.getRestaurantName());
    }

    @Test
    @DisplayName("Should update settings")
    void testUpdateSettings() {
        RestaurantSetting setting = new RestaurantSetting();
        setting.setRestaurantName("Sankalp RMS");
        setting.setGstRate(5.0);

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(1);

        int result = settingRepository.updateSettings(setting);
        assertEquals(1, result);
    }
}
