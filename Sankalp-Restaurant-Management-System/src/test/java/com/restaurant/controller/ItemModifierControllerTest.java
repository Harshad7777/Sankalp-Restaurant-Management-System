package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import com.restaurant.model.ItemModifier;
import com.restaurant.model.ModifierOption;
import com.restaurant.service.ItemModifierService;

@ExtendWith(MockitoExtension.class)
class ItemModifierControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemModifierService modifierService;

    @InjectMocks
    private ItemModifierController modifierController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(modifierController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should get modifiers by item ID")
    void testGetModifiersByItemId() throws Exception {
        ItemModifier modifier = new ItemModifier();
        modifier.setModifierId(1);
        modifier.setItemId(10);
        modifier.setName("Spice Level");

        when(modifierService.getModifiersByItemId(10)).thenReturn(List.of(modifier));

        mockMvc.perform(get("/modifiers/item/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Spice Level"));
    }

    @Test
    @DisplayName("Should create modifier")
    void testCreateModifier() throws Exception {
        ItemModifier modifier = new ItemModifier();
        modifier.setItemId(10);
        modifier.setName("Extra Cheese");

        when(modifierService.saveModifier(any(ItemModifier.class))).thenReturn(5);

        mockMvc.perform(post("/modifiers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifier)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.modifierId").value(5));
    }

    @Test
    @DisplayName("Should create modifier option")
    void testCreateOption() throws Exception {
        ModifierOption option = new ModifierOption();
        option.setName("Cheddar");

        when(modifierService.saveOption(eq(5), any(ModifierOption.class))).thenReturn(12);

        mockMvc.perform(post("/modifiers/5/option")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(option)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.optionId").value(12));
    }

    @Test
    @DisplayName("Should delete modifier")
    void testDeleteModifier() throws Exception {
        when(modifierService.deleteModifier(5)).thenReturn(true);

        mockMvc.perform(delete("/modifiers/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
