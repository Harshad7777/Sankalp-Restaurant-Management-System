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
    // POST /api/order
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
    // GET /api/order
    // =====================================================

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {

        return ResponseEntity.ok(
                service.getAllOrders()
        );
    }

    // =====================================================
    // GET ORDERS - PAGINATED, FILTERED, SORTED
    // GET /api/order/page
    // =====================================================

    @GetMapping("/page")
    public ResponseEntity<PageResponse<Orders>> getOrdersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) Integer tableId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String search) {

        com.restaurant.dto.PageRequestDTO req = com.restaurant.dto.PageRequestDTO.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .status(status)
                .customerId(customerId)
                .tableId(tableId)
                .startDate(startDate)
                .endDate(endDate)
                .search(search)
                .build();

        return ResponseEntity.ok(service.getOrdersFilteredPage(req));
    }

    // =====================================================
    // GET ORDER BY ID
    // GET /api/order/{id}
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
    // GET /api/order/details/{orderId}
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
    // PUT /api/order/status/{id}?status=READY
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
    // PUT /api/order/cancel/{id}
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
    // GET /api/order/customer/{customerId}
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
    // GET /api/order/today
    // =====================================================

    @GetMapping("/today")
    public ResponseEntity<List<Orders>> getTodayOrders() {

        return ResponseEntity.ok(
                service.getTodayOrders()
        );
    }

    // =====================================================
    // TODAY'S KITCHEN ORDERS
    // GET /api/order/kitchen/today
    // =====================================================

    @GetMapping("/kitchen/today")
    public ResponseEntity<List<KitchenOrderDTO>>
    getTodayKitchenOrders() {

        return ResponseEntity.ok(
                service.getTodayKitchenOrders()
        );
    }

    // =====================================================
    // KITCHEN STATISTICS
    // GET /api/order/kitchen/stats
    // =====================================================

    @GetMapping("/kitchen/stats")
    public ResponseEntity<KitchenStatsDTO>
    getKitchenStats() {

        return ResponseEntity.ok(
                service.getKitchenStats()
        );
    }

    // =====================================================
    // GET KITCHEN ORDER DETAIL
    // GET /api/order/kitchen/detail/{orderDetailId}
    // =====================================================

    @GetMapping("/kitchen/detail/{orderDetailId}")
    public ResponseEntity<KitchenOrderDTO>
    getKitchenOrderDetail(
            @PathVariable Integer orderDetailId) {

        return ResponseEntity.ok(
                service.getKitchenOrderDetail(
                        orderDetailId
                )
        );
    }

    // =====================================================
    // UPDATE KITCHEN STATUS
    //
    // PUT
    // /api/order/kitchen/detail/{detailId}/status
    //
    // Example:
    //
    // PUT
    // /api/order/kitchen/detail/54/status
    // ?status=SERVED
    // &chefId=7
    // =====================================================

    @PutMapping("/kitchen/detail/{orderDetailId}/status")
    public ResponseEntity<?> updateKitchenStatus(

            @PathVariable Integer orderDetailId,

            @RequestParam String status,

            @RequestParam(required = false) Integer chefId) {

        try {

            // =================================================
            // VALIDATE CHEF ID
            // =================================================

            if (chefId == null) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                Map.of(
                                        "success", false,
                                        "message",
                                        "Chef ID is required"
                                )
                        );
            }

            // =================================================
            // VALIDATE STATUS
            // =================================================

            if (status == null ||
                status.trim().isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                Map.of(
                                        "success", false,
                                        "message",
                                        "Kitchen status is required"
                                )
                        );
            }

            // =================================================
            // NORMALIZE STATUS
            // =================================================

            String kitchenStatus =
                    status.trim().toUpperCase();

            // =================================================
            // VALID STATUSES
            // =================================================

            if (!kitchenStatus.equals("NEW")
                    && !kitchenStatus.equals("ACCEPTED")
                    && !kitchenStatus.equals("PREPARING")
                    && !kitchenStatus.equals("READY")
                    && !kitchenStatus.equals("SERVED")
                    && !kitchenStatus.equals("CANCELLED")) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                Map.of(
                                        "success", false,
                                        "message",
                                        "Invalid kitchen status: "
                                                + kitchenStatus
                                )
                        );
            }

            // =================================================
            // UPDATE DATABASE
            // =================================================

            String result =
                    service.updateKitchenStatus(
                            orderDetailId,
                            kitchenStatus,
                            chefId
                    );

            // =================================================
            // SUCCESS
            // =================================================

            if ("Kitchen Status Updated Successfully"
                    .equals(result)) {

                return ResponseEntity.ok(
                        Map.of(
                                "success", true,
                                "message", result,
                                "detailId", orderDetailId,
                                "status", kitchenStatus,
                                "chefId", chefId
                        )
                );
            }

            // =================================================
            // SERVICE ERROR
            // =================================================

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "success", false,
                                    "message", result
                            )
                    );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                            Map.of(
                                    "success", false,
                                    "message",
                                    e.getMessage() != null
                                            ? e.getMessage()
                                            : "Kitchen status update failed"
                            )
                    );
        }
    }
}