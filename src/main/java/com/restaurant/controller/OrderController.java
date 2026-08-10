package com.restaurant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.KitchenOrderDTO;
import com.restaurant.dto.KitchenStatsDTO;
import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.OrderDetails;
import com.restaurant.model.Orders;
import com.restaurant.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // =====================================================
    // CREATE ORDER
    // =====================================================

    @PostMapping
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO dto) {

        int orderId = service.createOrder(dto);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Order Created Successfully",
                        "orderId", orderId
                )
        );
    }

    // =====================================================
    // GET ALL ORDERS
    // =====================================================

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {

        return ResponseEntity.ok(
                service.getAllOrders()
        );
    }

    // =====================================================
    // GET ORDERS - PAGINATED
    // Non-breaking: existing GET /api/order (unbounded) is untouched.
    // =====================================================

    @GetMapping("/page")
    public ResponseEntity<PageResponse<Orders>> getOrdersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(
                service.getOrdersPage(page, size)
        );
    }

    // =====================================================
    // GET ORDER BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                service.getOrderById(id)
        );
    }

    // =====================================================
    // GET ORDER DETAILS
    // =====================================================

    @GetMapping("/details/{orderId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetails(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                service.getOrderDetails(orderId)
        );
    }

    // =====================================================
    // UPDATE ORDER STATUS
    // =====================================================

    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateStatus(
            @PathVariable Integer id,
            @RequestParam String status) {

        String result = service.updateStatus(
                id,
                status
        );

        if ("Order Status Updated Successfully".equals(result)) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity
                .badRequest()
                .body(result);
    }

    // =====================================================
    // CANCEL ORDER
    // =====================================================

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Integer id) {

        String result = service.cancelOrder(id);

        if ("Order Cancelled Successfully".equals(result)) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity
                .badRequest()
                .body(result);
    }

    // =====================================================
    // CUSTOMER ORDER HISTORY
    // =====================================================

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Orders>> getCustomerOrders(
            @PathVariable Integer customerId) {

        return ResponseEntity.ok(
                service.getCustomerOrders(customerId)
        );
    }

    // =====================================================
    // TODAY'S ORDERS
    // =====================================================

    @GetMapping("/today")
    public ResponseEntity<List<Orders>> getTodayOrders() {

        return ResponseEntity.ok(
                service.getTodayOrders()
        );
    }

    // =====================================================
    // TODAY'S KITCHEN ORDERS
    // =====================================================

    @GetMapping("/kitchen/today")
    public ResponseEntity<List<KitchenOrderDTO>> getTodayKitchenOrders() {

        return ResponseEntity.ok(
                service.getTodayKitchenOrders()
        );
    }

    // =====================================================
    // KITCHEN STATISTICS
    // =====================================================

    @GetMapping("/kitchen/stats")
    public ResponseEntity<KitchenStatsDTO> getKitchenStats() {

        return ResponseEntity.ok(
                service.getKitchenStats()
        );
    }

    // =====================================================
    // GET KITCHEN ORDER DETAIL
    // =====================================================

    @GetMapping("/kitchen/detail/{orderDetailId}")
    public ResponseEntity<KitchenOrderDTO> getKitchenOrderDetail(
            @PathVariable Integer orderDetailId) {

        return ResponseEntity.ok(
                service.getKitchenOrderDetail(
                        orderDetailId
                )
        );
    }

    // =====================================================
    // UPDATE KITCHEN STATUS
    // =====================================================

    @PutMapping("/kitchen/detail/{orderDetailId}/status")
    public ResponseEntity<String> updateKitchenStatus(
            @PathVariable Integer orderDetailId,
            @RequestParam String status,
            @RequestParam Integer chefId) {

        String result = service.updateKitchenStatus(
                orderDetailId,
                status,
                chefId
        );

        if ("Kitchen Status Updated Successfully".equals(result)) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity
                .badRequest()
                .body(result);
    }
}