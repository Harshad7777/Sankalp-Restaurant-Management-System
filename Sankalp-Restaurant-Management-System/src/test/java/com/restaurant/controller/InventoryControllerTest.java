package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import com.restaurant.dto.InventoryRequestDTO;
import com.restaurant.dto.InventorySummaryDTO;
import com.restaurant.model.Inventory;
import com.restaurant.service.InventoryService;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private ObjectMapper objectMapper;
    private Inventory testItem;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
        objectMapper = new ObjectMapper();
        testItem = new Inventory();
        testItem.setInventoryId(1);
        testItem.setItemName("Basmati Rice");
        testItem.setCategory("Grains");
        testItem.setQuantity(50.0);
        testItem.setUnit("KG");
    }

    @Test
    @DisplayName("Should get inventory summary")
    void testGetSummary() throws Exception {
        InventorySummaryDTO summary = new InventorySummaryDTO();
        summary.setTotalItems(15);
        summary.setLowStockItems(2);

        when(inventoryService.getInventorySummary()).thenReturn(summary);

        mockMvc.perform(get("/inventory/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(15))
                .andExpect(jsonPath("$.lowStockItems").value(2));
    }

    @Test
    @DisplayName("Should get all inventory items")
    void testGetAll() throws Exception {
        when(inventoryService.getAllInventory()).thenReturn(List.of(testItem));

        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].itemName").value("Basmati Rice"));
    }

    @Test
    @DisplayName("Should get item by ID")
    void testGetById() throws Exception {
        when(inventoryService.getInventoryById(1)).thenReturn(testItem);

        mockMvc.perform(get("/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(1))
                .andExpect(jsonPath("$.itemName").value("Basmati Rice"));
    }

    @Test
    @DisplayName("Should delete inventory item")
    void testDelete() throws Exception {
        when(inventoryService.deleteInventory(1)).thenReturn("Inventory item deleted successfully");

        mockMvc.perform(delete("/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Inventory item deleted successfully"));
    }
}
