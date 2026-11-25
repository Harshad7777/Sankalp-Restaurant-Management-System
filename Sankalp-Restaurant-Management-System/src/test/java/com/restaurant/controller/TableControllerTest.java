package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.restaurant.model.Table;
import com.restaurant.service.TableService;

@ExtendWith(MockitoExtension.class)
class TableControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TableService tableService;

    @InjectMocks
    private TableController tableController;

    private ObjectMapper objectMapper;
    private Table testTable;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tableController).build();
        objectMapper = new ObjectMapper();
        testTable = new Table();
        testTable.setTableId(1);
        testTable.setTableNumber("T-01");
        testTable.setCapacity(4);
        testTable.setTableStatus("AVAILABLE");
    }

    @Test
    @DisplayName("Should get all tables")
    void testGetAllTables() throws Exception {
        when(tableService.getAllTables()).thenReturn(List.of(testTable));

        mockMvc.perform(get("/table"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].tableNumber").value("T-01"));
    }

    @Test
    @DisplayName("Should get table by ID - Found")
    void testGetTable_Found() throws Exception {
        when(tableService.getTable(1)).thenReturn(testTable);

        mockMvc.perform(get("/table/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tableId").value(1))
                .andExpect(jsonPath("$.tableNumber").value("T-01"));
    }

    @Test
    @DisplayName("Should return 404 when table not found")
    void testGetTable_NotFound() throws Exception {
        when(tableService.getTable(99)).thenThrow(new RuntimeException("Table not found"));

        mockMvc.perform(get("/table/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should add table successfully")
    void testAddTable_Success() throws Exception {
        when(tableService.addTable(any(Table.class))).thenReturn("Table Added Successfully");

        mockMvc.perform(post("/table")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTable)))
                .andExpect(status().isOk())
                .andExpect(content().string("Table Added Successfully"));
    }

    @Test
    @DisplayName("Should update table successfully")
    void testUpdateTable_Success() throws Exception {
        when(tableService.updateTable(any(Table.class))).thenReturn("Table Updated Successfully");

        mockMvc.perform(put("/table/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTable)))
                .andExpect(status().isOk())
                .andExpect(content().string("Table Updated Successfully"));
    }

    @Test
    @DisplayName("Should delete table successfully")
    void testDeleteTable_Success() throws Exception {
        when(tableService.deleteTable(1)).thenReturn("Table Deleted Successfully");

        mockMvc.perform(delete("/table/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Table Deleted Successfully"));
    }
}
