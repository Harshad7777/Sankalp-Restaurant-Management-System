package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import com.restaurant.service.KitchenService;

@ExtendWith(MockitoExtension.class)
class KitchenControllerTest {

    private MockMvc mockMvc;

    @Mock
    private KitchenService kitchenService;

    @InjectMocks
    private KitchenController kitchenController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(kitchenController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should get all kitchen orders")
    void testGetKitchenOrders() throws Exception {
        Map<String, Object> order = Map.of("orderId", 1, "status", "IN_PROGRESS");
        when(kitchenService.getKitchenOrders()).thenReturn(List.of(order));

        mockMvc.perform(get("/kitchen/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].orderId").value(1));
    }

    @Test
    @DisplayName("Should get single kitchen order - Found")
    void testGetKitchenOrder_Found() throws Exception {
        Map<String, Object> item = Map.of("orderId", 1, "itemName", "Biryani");
        when(kitchenService.getKitchenOrderById(1)).thenReturn(List.of(item));

        mockMvc.perform(get("/kitchen/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].itemName").value("Biryani"));
    }

    @Test
    @DisplayName("Should return 404 when kitchen order not found")
    void testGetKitchenOrder_NotFound() throws Exception {
        when(kitchenService.getKitchenOrderById(99)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/kitchen/order/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
