package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.dto.PaymentOrderRequestDTO;
import com.restaurant.model.Bill;
import com.restaurant.service.BillService;
import com.restaurant.service.payment.PaytmPaymentService;
import com.restaurant.service.payment.QRCodeGeneratorService;
import com.restaurant.service.payment.RazorpayPaymentService;
import com.restaurant.service.payment.StripePaymentService;

@ExtendWith(MockitoExtension.class)
class PaymentGatewayControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RazorpayPaymentService razorpayService;

    @Mock
    private StripePaymentService stripeService;

    @Mock
    private PaytmPaymentService paytmService;

    @Mock
    private QRCodeGeneratorService qrCodeService;

    @Mock
    private BillService billService;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PaymentGatewayController paymentGatewayController;

    private ObjectMapper objectMapper;
    private Bill testBill;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentGatewayController).build();
        objectMapper = new ObjectMapper();
        testBill = new Bill();
        testBill.setBillId(1);
        testBill.setBillNo("BILL-100");
        testBill.setGrandTotal(350.0);
    }

    @Test
    @DisplayName("Should create Razorpay order successfully")
    void testCreatePaymentOrder_Razorpay() throws Exception {
        PaymentOrderRequestDTO dto = new PaymentOrderRequestDTO();
        dto.setBillId(1);
        dto.setGateway("RAZORPAY");

        when(billService.getBillById(1)).thenReturn(testBill);
        when(razorpayService.createOrder(eq(1), any(BigDecimal.class), any()))
                .thenReturn(Map.of("id", "order_rzp_123", "amount", 35000));

        mockMvc.perform(post("/payment/create-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.gateway").value("RAZORPAY"))
                .andExpect(jsonPath("$.billId").value(1));
    }

    @Test
    @DisplayName("Should generate UPI QR Code for table payment")
    void testGenerateTablePaymentQR() throws Exception {
        when(billService.getBillById(1)).thenReturn(testBill);
        when(qrCodeService.generateUPIString(any(), any(), any(), any())).thenReturn("upi://pay?pa=test@upi");
        when(qrCodeService.generateQRCodeBase64(any(), eq(300), eq(300))).thenReturn("data:image/png;base64,mockqr");

        mockMvc.perform(get("/payment/qr-code/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.billId").value(1))
                .andExpect(jsonPath("$.qrCodeBase64").value("data:image/png;base64,mockqr"));
    }
}
