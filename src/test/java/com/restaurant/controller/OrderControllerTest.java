package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.dto.OrderDTO;
import com.restaurant.model.OrderDetails;
import com.restaurant.model.Orders;
import com.restaurant.service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testCreateOrder() throws Exception {
        OrderDTO dto = new OrderDTO();
        dto.setCustomerId(1);
        dto.setTableId(5);
        dto.setStaffId(2);

        OrderDetails detail = new OrderDetails();
        detail.setItemId(10);
        detail.setQuantity(2);
        dto.setItems(Collections.singletonList(detail));

        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(101);

        mockMvc.perform(post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Order Created Successfully"))
                .andExpect(jsonPath("$.orderId").value(101));
    }

    @Test
    void testGetAllOrders() throws Exception {
        Orders order = new Orders();
        order.setOrderId(101);
        order.setCustomerId(1);
        order.setOrderStatus("PENDING");

        List<Orders> list = Collections.singletonList(order);
        when(orderService.getAllOrders()).thenReturn(list);

        mockMvc.perform(get("/api/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].orderId").value(101));
    }

    @Test
    void testUpdateStatus_Success() throws Exception {
        when(orderService.updateStatus(101, "COMPLETED"))
                .thenReturn("Order Status Updated Successfully");

        mockMvc.perform(put("/api/order/status/101")
                .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order Status Updated Successfully"));
    }

    @Test
    void testUpdateStatus_Failure() throws Exception {
        when(orderService.updateStatus(101, "INVALID"))
                .thenReturn("Failed to update status");

        mockMvc.perform(put("/api/order/status/101")
                .param("status", "INVALID"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to update status"));
    }
}
