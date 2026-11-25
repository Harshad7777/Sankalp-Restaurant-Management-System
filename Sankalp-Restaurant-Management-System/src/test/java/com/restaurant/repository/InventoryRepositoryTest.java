package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

import com.restaurant.model.Inventory;

@ExtendWith(MockitoExtension.class)
class InventoryRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private InventoryRepository inventoryRepository;

    @Test
    @DisplayName("Should find inventory by ID")
    void testFindById_Found() {
        Inventory item = new Inventory();
        item.setInventoryId(1);
        item.setItemName("Flour");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1), any()))
                .thenReturn(Collections.singletonList(item));

        Inventory result = inventoryRepository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getInventoryId());
    }

    @Test
    @DisplayName("Should return null when inventory not found by ID")
    void testFindById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(99), any()))
                .thenReturn(Collections.emptyList());

        Inventory result = inventoryRepository.findById(99);
        assertNull(result);
    }

    @Test
    @DisplayName("Should find all inventory")
    void testFindAll() {
        Inventory item = new Inventory();
        item.setInventoryId(1);
        item.setItemName("Sugar");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any()))
                .thenReturn(Collections.singletonList(item));

        List<Inventory> list = inventoryRepository.findAll();
        assertEquals(1, list.size());
        assertEquals("Sugar", list.get(0).getItemName());
    }

    @Test
    @DisplayName("Should delete inventory")
    void testDelete() {
        when(jdbcTemplate.update(anyString(), eq(1), any()))
                .thenReturn(1);

        int result = inventoryRepository.delete(1);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should check exists by name")
    void testExistsByName() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("Flour"), any()))
                .thenReturn(1);

        assertTrue(inventoryRepository.existsByName("Flour"));
    }
}
