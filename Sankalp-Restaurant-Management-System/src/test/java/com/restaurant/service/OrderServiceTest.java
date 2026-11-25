package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.PageRequestDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.OrderDetails;
import com.restaurant.model.Orders;
import com.restaurant.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private OrderService orderService;

    private Orders testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Orders();
        testOrder.setOrderId(1);
        testOrder.setOrderNo("ORD-1001");
        testOrder.setCustomerId(10);
        testOrder.setTableId(2);
        testOrder.setOrderStatus("PENDING");
        testOrder.setTotalAmount(450.0);
    }

    @Test
    @DisplayName("Should create order successfully")
    void testCreateOrder() {
        OrderDTO dto = new OrderDTO();
        dto.setCustomerId(10);
        dto.setTableId(2);

        when(repository.createOrder(dto)).thenReturn(101);

        int orderId = orderService.createOrder(dto);
        assertEquals(101, orderId);
        verify(repository).createOrder(dto);
    }

    @Test
    @DisplayName("Should get all orders")
    void testGetAllOrders() {
        when(repository.getAllOrders()).thenReturn(List.of(testOrder));

        List<Orders> list = orderService.getAllOrders();
        assertEquals(1, list.size());
        assertEquals("ORD-1001", list.get(0).getOrderNo());
    }

    @Test
    @DisplayName("Should get order by ID")
    void testGetOrderById() {
        when(repository.getOrderById(1)).thenReturn(testOrder);

        Orders order = orderService.getOrderById(1);
        assertNotNull(order);
        assertEquals(1, order.getOrderId());
    }

    @Test
    @DisplayName("Should get order details")
    void testGetOrderDetails() {
        OrderDetails detail = new OrderDetails();
        detail.setOrderDetailId(5);
        detail.setItemId(10);
        detail.setQuantity(2);

        when(repository.getOrderDetails(1)).thenReturn(List.of(detail));

        List<OrderDetails> details = orderService.getOrderDetails(1);
        assertEquals(1, details.size());
        assertEquals(5, details.get(0).getOrderDetailId());
    }

    @Test
    @DisplayName("Should update order status successfully")
    void testUpdateStatus_Success() {
        when(repository.updateStatus(1, "PREPARING")).thenReturn(1);

        String result = orderService.updateStatus(1, "PREPARING");
        assertEquals("Order Status Updated Successfully", result);
    }

    @Test
    @DisplayName("Should return Order Not Found when updating status of non-existent order")
    void testUpdateStatus_NotFound() {
        when(repository.updateStatus(99, "PREPARING")).thenReturn(0);

        String result = orderService.updateStatus(99, "PREPARING");
        assertEquals("Order Not Found", result);
    }

    @Test
    @DisplayName("Should cancel order successfully")
    void testCancelOrder_Success() {
        when(repository.cancelOrder(1)).thenReturn(1);

        String result = orderService.cancelOrder(1);
        assertEquals("Order Cancelled Successfully", result);
    }

    @Test
    @DisplayName("Should get paginated and filtered orders")
    void testGetOrdersFilteredPage() {
        PageRequestDTO req = PageRequestDTO.builder().page(0).size(10).build();
        when(repository.getOrdersFilteredPage(req)).thenReturn(List.of(testOrder));
        when(repository.countOrdersFiltered(req)).thenReturn(1L);

        PageResponse<Orders> response = orderService.getOrdersFilteredPage(req);
        assertEquals(1, response.getContent().size());
        assertEquals(1L, response.getTotalElements());
    }
}
