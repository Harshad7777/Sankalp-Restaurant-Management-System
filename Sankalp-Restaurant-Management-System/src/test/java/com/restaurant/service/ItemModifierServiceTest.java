package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
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

import com.restaurant.model.ItemModifier;
import com.restaurant.model.ModifierOption;

@ExtendWith(MockitoExtension.class)
class ItemModifierServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ItemModifierService modifierService;

    @Test
    @DisplayName("Should save modifier successfully")
    void testSaveModifier() {
        ItemModifier modifier = new ItemModifier();
        modifier.setItemId(10);
        modifier.setName("Size");
        modifier.setIsRequired(true);
        modifier.setMinSelection(1);
        modifier.setMaxSelection(1);

        when(jdbcTemplate.update(anyString(), eq(10), eq("Size"), eq(true), eq(1), eq(1))).thenReturn(1);
        when(jdbcTemplate.queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Integer.class))).thenReturn(42);

        int result = modifierService.saveModifier(modifier);
        assertEquals(42, result);
    }

    @Test
    @DisplayName("Should save option successfully")
    void testSaveOption() {
        ModifierOption option = new ModifierOption();
        option.setName("Large");
        option.setPrice(BigDecimal.valueOf(50.0));
        option.setIsAvailable(true);

        when(jdbcTemplate.update(anyString(), eq(42), eq("Large"), eq(BigDecimal.valueOf(50.0)), eq(true))).thenReturn(1);
        when(jdbcTemplate.queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Integer.class))).thenReturn(101);

        int result = modifierService.saveOption(42, option);
        assertEquals(101, result);
    }

    @Test
    @DisplayName("Should delete modifier and its options")
    void testDeleteModifier() {
        when(jdbcTemplate.update(contains("DELETE FROM modifier_options"), eq(42))).thenReturn(2);
        when(jdbcTemplate.update(contains("DELETE FROM item_modifiers"), eq(42))).thenReturn(1);

        boolean result = modifierService.deleteModifier(42);
        assertTrue(result);
    }
}
