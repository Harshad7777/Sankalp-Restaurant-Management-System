package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.model.Table;
import com.restaurant.repository.TableRepository;

@ExtendWith(MockitoExtension.class)
public class TableServiceTest {

    @Mock
    private TableRepository repository;

    @InjectMocks
    private TableService tableService;

    private Table testTable;

    @BeforeEach
    void setUp() {
        testTable = Table.builder()
                .tableId(1)
                .tableNumber("T-01")
                .capacity(4)
                .tableStatus("AVAILABLE")
                .build();
    }

    @Test
    @DisplayName("Should successfully add a table")
    void testAddTable_Success() {
        when(repository.existsByTableNumber("T-01")).thenReturn(false);
        when(repository.save(any(Table.class))).thenReturn(1);

        String result = tableService.addTable(testTable);

        assertEquals("Table Added Successfully", result);
        verify(repository).save(testTable);
    }

    @Test
    @DisplayName("Should reject table if table number already exists")
    void testAddTable_Duplicate() {
        when(repository.existsByTableNumber("T-01")).thenReturn(true);

        String result = tableService.addTable(testTable);

        assertEquals("Table number already exists", result);
        verify(repository, never()).save(any(Table.class));
    }

    @Test
    @DisplayName("Should reject table with invalid capacity")
    void testAddTable_InvalidCapacity() {
        testTable.setCapacity(0);

        String result = tableService.addTable(testTable);

        assertEquals("Capacity must be greater than 0", result);
    }

    @Test
    @DisplayName("Should update table successfully")
    void testUpdateTable_Success() {
        when(repository.findById(1)).thenReturn(testTable);
        when(repository.existsByTableNumberAndNotId("T-01", 1)).thenReturn(false);
        when(repository.update(any(Table.class))).thenReturn(1);

        String result = tableService.updateTable(testTable);

        assertEquals("Table Updated Successfully", result);
        verify(repository).update(testTable);
    }

    @Test
    @DisplayName("Should delete table successfully")
    void testDeleteTable_Success() {
        when(repository.findById(1)).thenReturn(testTable);
        when(repository.delete(1)).thenReturn(1);

        String result = tableService.deleteTable(1);

        assertEquals("Table Deleted Successfully", result);
        verify(repository).delete(1);
    }

    @Test
    @DisplayName("Should get all tables")
    void testGetAllTables() {
        when(repository.findAll()).thenReturn(List.of(testTable));

        List<Table> tables = tableService.getAllTables();

        assertNotNull(tables);
        assertEquals(1, tables.size());
    }
}
