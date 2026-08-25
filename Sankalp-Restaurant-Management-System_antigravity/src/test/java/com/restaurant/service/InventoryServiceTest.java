package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.dto.InventoryRequestDTO;
import com.restaurant.dto.InventorySummaryDTO;
import com.restaurant.dto.StockAdjustmentDTO;
import com.restaurant.exception.DuplicateResourceException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.exception.ValidationException;
import com.restaurant.model.Inventory;
import com.restaurant.repository.InventoryRepository;
import com.restaurant.service.impl.InventoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository repository;

    @InjectMocks
    private InventoryServiceImpl service;

    private Inventory sampleItem;
    private InventoryRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        sampleItem = Inventory.builder()
                .inventoryId(1)
                .itemName("Fresh Paneer")
                .category("Dairy")
                .quantity(20.0)
                .unit("kg")
                .minThreshold(5.0)
                .costPerUnit(320.0)
                .status("IN_STOCK")
                .build();

        requestDTO = InventoryRequestDTO.builder()
                .itemName("Fresh Paneer")
                .category("Dairy")
                .quantity(20.0)
                .unit("kg")
                .minThreshold(5.0)
                .costPerUnit(320.0)
                .build();
    }

    @Test
    void testGetAllInventory() {
        when(repository.findAll()).thenReturn(List.of(sampleItem));

        List<Inventory> list = service.getAllInventory();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Fresh Paneer", list.get(0).getItemName());
    }

    @Test
    void testGetInventoryById_Success() {
        when(repository.findById(1)).thenReturn(sampleItem);

        Inventory result = service.getInventoryById(1);

        assertNotNull(result);
        assertEquals(1, result.getInventoryId());
    }

    @Test
    void testGetInventoryById_NotFound() {
        when(repository.findById(999)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.getInventoryById(999));
    }

    @Test
    void testAddInventory_Success() {
        when(repository.existsByName("Fresh Paneer")).thenReturn(false);
        when(repository.save(any(Inventory.class))).thenReturn(1);

        String response = service.addInventory(requestDTO);

        assertEquals("Inventory item added successfully", response);
        verify(repository).save(any(Inventory.class));
    }

    @Test
    void testAddInventory_DuplicateName_ThrowsException() {
        when(repository.existsByName("Fresh Paneer")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.addInventory(requestDTO));
    }

    @Test
    void testUpdateInventory_Success() {
        when(repository.findById(1)).thenReturn(sampleItem);
        when(repository.existsByNameAndNotId("Fresh Paneer", 1)).thenReturn(false);
        when(repository.update(any(Inventory.class))).thenReturn(1);

        String response = service.updateInventory(1, requestDTO);

        assertEquals("Inventory item updated successfully", response);
        verify(repository).update(any(Inventory.class));
    }

    @Test
    void testDeleteInventory_Success() {
        when(repository.findById(1)).thenReturn(sampleItem);
        when(repository.delete(1)).thenReturn(1);

        String response = service.deleteInventory(1);

        assertEquals("Inventory item deleted successfully", response);
    }

    @Test
    void testAdjustStock_Restock_Success() {
        when(repository.findById(1)).thenReturn(sampleItem);

        StockAdjustmentDTO adjustDTO = StockAdjustmentDTO.builder()
                .quantity(10.0)
                .actionType("RESTOCK")
                .reason("Weekly fresh delivery")
                .performedBy("Chef Vikas")
                .build();

        String response = service.adjustStock(1, adjustDTO);

        assertTrue(response.contains("30.0"));
        verify(repository).updateStockAndStatus(eq(1), eq(30.0), eq("IN_STOCK"), eq("RESTOCK"));
        verify(repository).saveLog(any());
    }

    @Test
    void testAdjustStock_Consume_Success() {
        when(repository.findById(1)).thenReturn(sampleItem);

        StockAdjustmentDTO adjustDTO = StockAdjustmentDTO.builder()
                .quantity(16.0)
                .actionType("CONSUME")
                .reason("Lunch buffet prep")
                .performedBy("Chef Vikas")
                .build();

        String response = service.adjustStock(1, adjustDTO);

        assertTrue(response.contains("4.0"));
        // 4.0 <= minThreshold(5.0), so status should be LOW_STOCK
        verify(repository).updateStockAndStatus(eq(1), eq(4.0), eq("LOW_STOCK"), eq("CONSUME"));
    }

    @Test
    void testAdjustStock_Consume_InsufficientStock_ThrowsException() {
        when(repository.findById(1)).thenReturn(sampleItem); // Current stock is 20.0

        StockAdjustmentDTO adjustDTO = StockAdjustmentDTO.builder()
                .quantity(25.0)
                .actionType("CONSUME")
                .build();

        assertThrows(ValidationException.class, () -> service.adjustStock(1, adjustDTO));
    }

    @Test
    void testGetInventorySummary() {
        InventorySummaryDTO mockSummary = InventorySummaryDTO.builder()
                .totalItems(10)
                .inStockItems(8)
                .lowStockItems(1)
                .outOfStockItems(1)
                .totalInventoryValue(45000.0)
                .build();

        when(repository.getSummary()).thenReturn(mockSummary);

        InventorySummaryDTO summary = service.getInventorySummary();

        assertNotNull(summary);
        assertEquals(10, summary.getTotalItems());
        assertEquals(45000.0, summary.getTotalInventoryValue());
    }
}
