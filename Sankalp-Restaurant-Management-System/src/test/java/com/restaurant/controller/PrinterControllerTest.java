package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.model.Orders;
import com.restaurant.service.BillService;
import com.restaurant.service.OrderService;
import com.restaurant.service.printer.EscPosPrintService;

@ExtendWith(MockitoExtension.class)
class PrinterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EscPosPrintService printService;

    @Mock
    private OrderService orderService;

    @Mock
    private BillService billService;

    @InjectMocks
    private PrinterController printerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(printerController).build();
    }

    @Test
    @DisplayName("Should generate KOT thermal print payload")
    void testPrintKOT() throws Exception {
        Orders order = new Orders();
        order.setOrderId(10);
        order.setTableId(2);

        when(orderService.getOrderById(10)).thenReturn(order);
        when(orderService.getOrderDetails(10)).thenReturn(Collections.emptyList());
        when(printService.generateKOTBytes(any(), any(), any())).thenReturn(new byte[]{0x1B, 0x40});
        when(printService.encodeBase64(any())).thenReturn("G0A=");

        mockMvc.perform(post("/printer/print-kot/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.orderId").value(10))
                .andExpect(jsonPath("$.escposBase64").value("G0A="));
    }

    @Test
    @DisplayName("Should return bad request when KOT order not found")
    void testPrintKOT_NotFound() throws Exception {
        when(orderService.getOrderById(99)).thenReturn(null);

        mockMvc.perform(post("/printer/print-kot/99"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
