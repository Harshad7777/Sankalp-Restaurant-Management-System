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

import com.restaurant.dto.BillDTO;
import com.restaurant.model.Bill;
import com.restaurant.model.Customer;
import com.restaurant.model.Orders;
import com.restaurant.repository.BillRepository;
import com.restaurant.repository.CustomerRepository;
import com.restaurant.repository.OrderRepository;
import com.restaurant.service.mail.JmsMailProducer;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private JmsMailProducer mailProducer;

    @InjectMocks
    private BillService billService;

    private BillDTO testBillDto;
    private Bill testBill;
    private Orders testOrder;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testBillDto = BillDTO.builder()
                .orderId(10)
                .discount(50.0)
                .paymentMethod("UPI")
                .paymentStatus("PAID")
                .build();

        testBill = Bill.builder()
                .billId(100)
                .billNo("BILL-000001")
                .orderId(10)
                .subtotal(500.0)
                .discount(50.0)
                .gst(22.5)
                .grandTotal(472.5)
                .paymentMethod("UPI")
                .paymentStatus("PAID")
                .build();

        testOrder = Orders.builder()
                .orderId(10)
                .customerId(5)
                .orderStatus("SERVED")
                .build();

        testCustomer = Customer.builder()
                .customerId(5)
                .customerName("Alice Smith")
                .email("alice@example.com")
                .build();
    }

    @Test
    @DisplayName("Should successfully create a bill and dispatch invoice email")
    void testCreateBill_Success() {
        when(billRepository.createBill(any(BillDTO.class))).thenReturn(100);
        when(billRepository.getBillById(100)).thenReturn(testBill);
        when(orderRepository.getOrderById(10)).thenReturn(testOrder);
        when(customerRepository.findById(5)).thenReturn(testCustomer);

        int billId = billService.createBill(testBillDto);

        assertEquals(100, billId);
        verify(billRepository).createBill(testBillDto);
        verify(mailProducer).sendBillInvoiceEmail(
                eq("alice@example.com"),
                eq("Alice Smith"),
                eq("BILL-000001"),
                eq(500.0),
                eq(22.5),
                eq(50.0),
                eq(472.5),
                eq("UPI"));
    }

    @Test
    @DisplayName("Should retrieve bill by ID")
    void testGetBillById_Success() {
        when(billRepository.getBillById(100)).thenReturn(testBill);

        Bill bill = billService.getBillById(100);

        assertNotNull(bill);
        assertEquals("BILL-000001", bill.getBillNo());
        assertEquals(472.5, bill.getGrandTotal());
    }

    @Test
    @DisplayName("Should update bill payment status")
    void testUpdatePayment_Success() {
        when(billRepository.updatePayment(100, "CARD", "PAID")).thenReturn(1);

        String result = billService.updatePayment(100, "CARD", "PAID");

        assertEquals("Payment Updated Successfully", result);
        verify(billRepository).updatePayment(100, "CARD", "PAID");
    }

    @Test
    @DisplayName("Should retrieve all bills")
    void testGetAllBills() {
        when(billRepository.getAllBills()).thenReturn(List.of(testBill));

        List<Bill> list = billService.getAllBills();

        assertNotNull(list);
        assertEquals(1, list.size());
    }
}
