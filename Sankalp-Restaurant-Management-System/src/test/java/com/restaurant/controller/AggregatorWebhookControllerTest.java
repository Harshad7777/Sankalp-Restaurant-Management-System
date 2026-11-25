package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.restaurant.dto.AggregatorOrderDTO;
import com.restaurant.service.AggregatorIntegrationService;

@ExtendWith(MockitoExtension.class)
class AggregatorWebhookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AggregatorIntegrationService service;

    @InjectMocks
    private AggregatorWebhookController webhookController;

    private ObjectMapper objectMapper;
    private AggregatorOrderDTO testOrder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(webhookController).build();
        objectMapper = new ObjectMapper();
        testOrder = new AggregatorOrderDTO();
        testOrder.setExternalOrderId("SWIGGY-9988");
        testOrder.setCustomerName("Sunil Verma");
    }

    @Test
    @DisplayName("Should receive Swiggy webhook order")
    void testReceiveSwiggyWebhook() throws Exception {
        when(service.processIncomingAggregatorOrder(any(AggregatorOrderDTO.class))).thenReturn(205);

        mockMvc.perform(post("/integrations/aggregator/webhook/swiggy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.orderId").value(205))
                .andExpect(jsonPath("$.externalOrderId").value("SWIGGY-9988"));
    }

    @Test
    @DisplayName("Should receive Zomato webhook order")
    void testReceiveZomatoWebhook() throws Exception {
        testOrder.setExternalOrderId("ZOMATO-7766");
        when(service.processIncomingAggregatorOrder(any(AggregatorOrderDTO.class))).thenReturn(206);

        mockMvc.perform(post("/integrations/aggregator/webhook/zomato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.orderId").value(206))
                .andExpect(jsonPath("$.externalOrderId").value("ZOMATO-7766"));
    }
}
