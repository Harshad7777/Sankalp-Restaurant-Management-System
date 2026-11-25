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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.restaurant.model.OrderDetails;
import com.restaurant.model.Orders;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Should find all orders")
    void testGetAllOrders() {
        Orders order = new Orders();
        order.setOrderId(1);
        order.setOrderNo("ORD-001");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), any()))
                .thenReturn(Collections.singletonList(order));

        List<Orders> orders = orderRepository.getAllOrders();
        assertEquals(1, orders.size());
        assertEquals("ORD-001", orders.get(0).getOrderNo());
    }

    @Test
    @DisplayName("Should find order by ID")
    void testGetOrderById_Found() {
        Orders order = new Orders();
        order.setOrderId(1);
        order.setOrderNo("ORD-001");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(1), any()))
                .thenReturn(Collections.singletonList(order));

        Orders result = orderRepository.getOrderById(1);
        assertNotNull(result);
        assertEquals(1, result.getOrderId());
    }

    @Test
    @DisplayName("Should return null when order not found by ID")
    void testGetOrderById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(99), any()))
                .thenReturn(Collections.emptyList());

        Orders result = orderRepository.getOrderById(99);
        assertNull(result);
    }

    @Test
    @DisplayName("Should get order details for order")
    void testGetOrderDetails() {
        Orders order = new Orders();
        order.setOrderId(1);

        OrderDetails detail = new OrderDetails();
        detail.setOrderDetailId(10);
        detail.setItemId(2);

        when(jdbcTemplate.query(eq(com.restaurant.repository.query.OrderQueries.FIND_ORDER_BY_ID), any(BeanPropertyRowMapper.class), eq(1), any()))
                .thenReturn(Collections.singletonList(order));
        when(jdbcTemplate.query(eq(com.restaurant.repository.query.OrderQueries.FIND_ORDER_DETAILS), any(BeanPropertyRowMapper.class), eq(1)))
                .thenReturn(Collections.singletonList(detail));

        List<OrderDetails> details = orderRepository.getOrderDetails(1);
        assertEquals(1, details.size());
        assertEquals(10, details.get(0).getOrderDetailId());
    }

    @Test
    @DisplayName("Should update order status")
    void testUpdateStatus() {
        when(jdbcTemplate.update(anyString(), eq("COMPLETED"), eq(1), any()))
                .thenReturn(1);

        int result = orderRepository.updateStatus(1, "COMPLETED");
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should cancel order")
    void testCancelOrder() {
        when(jdbcTemplate.update(anyString(), eq(1), any()))
                .thenReturn(1);

        int result = orderRepository.cancelOrder(1);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should count orders")
    void testCountOrders() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class), any()))
                .thenReturn(25L);

        long count = orderRepository.countOrders();
        assertEquals(25L, count);
    }
}
