package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@ExtendWith(MockitoExtension.class)
class KitchenRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private KitchenRepository kitchenRepository;

    @Test
    @DisplayName("Should get kitchen orders")
    void testGetKitchenOrders() {
        Map<String, Object> map = Map.of("order_id", 1, "table_no", "5");
        when(jdbcTemplate.queryForList(anyString(), any(Object.class))).thenReturn(List.of(map));

        List<Map<String, Object>> result = kitchenRepository.getKitchenOrders();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should check order exists")
    void testOrderExists() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(1), any()))
                .thenReturn(1);

        assertTrue(kitchenRepository.orderExists(1));
    }

    @Test
    @DisplayName("Should check detail exists")
    void testDetailExists() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(5), any()))
                .thenReturn(1);

        assertTrue(kitchenRepository.detailExists(5));
    }

    @Test
    @DisplayName("Should get order ID from detail ID")
    void testGetOrderIdFromDetail() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(5), any()))
                .thenReturn(List.of(101));

        Integer orderId = kitchenRepository.getOrderIdFromDetail(5);
        assertEquals(101, orderId);
    }
}
