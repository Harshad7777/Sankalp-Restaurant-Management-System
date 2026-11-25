package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.repository.KitchenRepository;

@ExtendWith(MockitoExtension.class)
class KitchenServiceTest {

    @Mock
    private KitchenRepository kitchenRepository;

    @InjectMocks
    private KitchenService kitchenService;

    @Test
    @DisplayName("Should return kitchen orders")
    void testGetKitchenOrders() {
        Map<String, Object> order = Map.of("orderId", 101, "tableNo", "T1");
        when(kitchenRepository.getKitchenOrders()).thenReturn(List.of(order));

        List<Map<String, Object>> result = kitchenService.getKitchenOrders();
        assertEquals(1, result.size());
        assertEquals(101, result.get(0).get("orderId"));
    }

    @Test
    @DisplayName("Should return order details by orderId")
    void testGetKitchenOrderById() {
        Map<String, Object> detail = Map.of("detailId", 1, "itemName", "Pizza");
        when(kitchenRepository.getKitchenOrderById(101)).thenReturn(List.of(detail));

        List<Map<String, Object>> result = kitchenService.getKitchenOrderById(101);
        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).get("itemName"));
    }

    @Test
    @DisplayName("Should check if detail exists")
    void testDetailExists() {
        when(kitchenRepository.detailExists(1)).thenReturn(true);
        assertTrue(kitchenService.detailExists(1));
    }
}
