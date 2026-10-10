package com.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.restaurant.dto.AggregatorOrderDTO;
import com.restaurant.dto.OrderDTO;
import com.restaurant.model.OrderDetails;

@Service
public class AggregatorIntegrationService {

    private final OrderService orderService;
    private final JdbcTemplate jdbcTemplate;

    public AggregatorIntegrationService(OrderService orderService, JdbcTemplate jdbcTemplate) {
        this.orderService = orderService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public int processIncomingAggregatorOrder(AggregatorOrderDTO dto) {
        // 1. Resolve or create customer by phone number
        int customerId = resolveOrCreateCustomer(dto.getCustomerName(), dto.getCustomerPhone(), dto.getCustomerAddress());

        // 2. Map items by item_id or external_sku
        List<OrderDetails> orderItems = new ArrayList<>();
        if (dto.getItems() != null) {
            for (AggregatorOrderDTO.AggregatorOrderItemDTO itemDTO : dto.getItems()) {
                Integer itemId = itemDTO.getItemId();
                if (itemId == null && itemDTO.getExternalSku() != null) {
                    itemId = findItemIdBySku(itemDTO.getExternalSku());
                }
                if (itemId == null) {
                    itemId = 1; // Fallback to item 1 if not mapped
                }

                OrderDetails detail = new OrderDetails();
                detail.setItemId(itemId);
                detail.setQuantity(itemDTO.getQuantity() != null ? itemDTO.getQuantity() : 1);
                java.math.BigDecimal itemPrice = itemDTO.getPrice() != null ? itemDTO.getPrice() : getMenuPrice(itemId);
                detail.setPrice(itemPrice != null ? itemPrice.doubleValue() : 0.0);
                detail.setKitchenNote(itemDTO.getKitchenNote() != null ? itemDTO.getKitchenNote() : dto.getSpecialInstructions());
                orderItems.add(detail);
            }
        }

        // 3. Create standard order (Default staff = 1, default online table = 1)
        OrderDTO orderDTO = OrderDTO.builder()
                .customerId(customerId)
                .tableId(1)
                .staffId(1)
                .items(orderItems)
                .build();

        int orderId = orderService.createOrder(orderDTO);

        // 4. Update order with channel & external reference
        updateAggregatorDetails(orderId, dto.getChannel(), dto.getExternalOrderId());

        return orderId;
    }

    private int resolveOrCreateCustomer(String name, String phone, String address) {
        if (phone == null || phone.isBlank()) {
            phone = "9999999999";
        }
        if (name == null || name.isBlank()) {
            name = "Online Guest";
        }

        String sqlCheck = "SELECT customer_id FROM customer WHERE mobile = ? LIMIT 1";
        List<Integer> existing = jdbcTemplate.query(sqlCheck, (rs, rowNum) -> rs.getInt("customer_id"), phone);

        if (!existing.isEmpty()) {
            return existing.get(0);
        }

        String sqlInsert = "INSERT INTO customer (restaurant_id, customer_name, mobile, address, status) VALUES (1, ?, ?, ?, 'ACTIVE')";
        jdbcTemplate.update(sqlInsert, name, phone, address != null ? address : "Online Order");

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    private Integer findItemIdBySku(String sku) {
        String sql = "SELECT item_id FROM menu WHERE external_sku = ? LIMIT 1";
        List<Integer> list = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("item_id"), sku);
        return list.isEmpty() ? null : list.get(0);
    }

    private java.math.BigDecimal getMenuPrice(Integer itemId) {
        String sql = "SELECT price FROM menu WHERE item_id = ?";
        List<java.math.BigDecimal> list = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getBigDecimal("price"), itemId);
        return list.isEmpty() ? java.math.BigDecimal.ZERO : list.get(0);
    }

    private void updateAggregatorDetails(int orderId, String channel, String externalRef) {
        String sql = "UPDATE orders SET order_type = 'DELIVERY', aggregator_channel = ?, external_order_ref = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, channel != null ? channel.toUpperCase() : "AGGREGATOR", externalRef, orderId);
    }
}
