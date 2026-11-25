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

import com.restaurant.model.Table;

@ExtendWith(MockitoExtension.class)
class TableRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TableRepository tableRepository;

    @Test
    @DisplayName("Should find all tables")
    void testFindAll() {
        Table table = new Table();
        table.setTableId(1);
        table.setTableNumber("T-01");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any()))
                .thenReturn(Collections.singletonList(table));

        List<Table> list = tableRepository.findAll();
        assertEquals(1, list.size());
        assertEquals("T-01", list.get(0).getTableNumber());
    }

    @Test
    @DisplayName("Should find table by ID")
    void testFindById_Found() {
        Table table = new Table();
        table.setTableId(1);
        table.setTableNumber("T-01");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1), any()))
                .thenReturn(Collections.singletonList(table));

        Table result = tableRepository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getTableId());
    }

    @Test
    @DisplayName("Should delete table")
    void testDelete() {
        when(jdbcTemplate.update(anyString(), eq(1), any()))
                .thenReturn(1);

        int result = tableRepository.delete(1);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should check exists by table number")
    void testExistsByTableNumber() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("T-01"), any()))
                .thenReturn(1);

        assertTrue(tableRepository.existsByTableNumber("T-01"));
    }
}
