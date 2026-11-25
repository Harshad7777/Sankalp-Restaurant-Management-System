package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
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
import com.restaurant.dto.BillDTO;
import com.restaurant.dto.PageRequestDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.Bill;
import com.restaurant.service.BillService;

@ExtendWith(MockitoExtension.class)
class BillControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BillService billService;

    @InjectMocks
    private BillController billController;

    private ObjectMapper objectMapper;
    private Bill testBill;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(billController).build();
        objectMapper = new ObjectMapper();
        testBill = new Bill();
        testBill.setBillId(1);
        testBill.setBillNo("BILL-2026-001");
        testBill.setOrderId(10);
        testBill.setSubtotal(500.0);
        testBill.setGrandTotal(525.0);
        testBill.setPaymentMethod("CASH");
        testBill.setPaymentStatus("PAID");
    }

    @Test
    @DisplayName("Should create bill successfully")
    void testCreateBill_Success() throws Exception {
        BillDTO dto = new BillDTO();
        dto.setOrderId(10);
        dto.setPaymentMethod("CASH");

        when(billService.createBill(any(BillDTO.class))).thenReturn(1);
        when(billService.getBillById(1)).thenReturn(testBill);

        mockMvc.perform(post("/bill")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.billId").value(1))
                .andExpect(jsonPath("$.billNo").value("BILL-2026-001"));
    }

    @Test
    @DisplayName("Should return 400 when create bill gets IllegalArgumentException")
    void testCreateBill_IllegalArgumentException() throws Exception {
        BillDTO dto = new BillDTO();
        dto.setOrderId(10);

        when(billService.createBill(any(BillDTO.class))).thenThrow(new IllegalArgumentException("Invalid Order ID"));

        mockMvc.perform(post("/bill")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get all bills")
    void testGetAllBills() throws Exception {
        when(billService.getAllBills()).thenReturn(List.of(testBill));

        mockMvc.perform(get("/bill"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].billNo").value("BILL-2026-001"));
    }

    @Test
    @DisplayName("Should get today bills")
    void testGetTodayBills() throws Exception {
        when(billService.getTodayBills()).thenReturn(List.of(testBill));

        mockMvc.perform(get("/bill/today"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Should get bill by ID - Found")
    void testGetBillById_Found() throws Exception {
        when(billService.getBillById(1)).thenReturn(testBill);

        mockMvc.perform(get("/bill/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.billId").value(1));
    }

    @Test
    @DisplayName("Should get paginated bills")
    void testGetBillsPage() throws Exception {
        PageResponse<Bill> response = PageResponse.<Bill>builder()
                .content(List.of(testBill))
                .page(0)
                .size(10)
                .totalElements(1)
                .totalPages(1)
                .build();

        when(billService.getBillsFilteredPage(any(PageRequestDTO.class))).thenReturn(response);

        mockMvc.perform(get("/bill/page")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
