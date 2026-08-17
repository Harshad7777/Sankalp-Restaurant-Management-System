
package com.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.dto.KitchenOrderDTO;
import com.restaurant.dto.KitchenStatsDTO;
import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.OrderDetails;
import com.restaurant.model.Orders;
import com.restaurant.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    // =====================================================
    // CREATE ORDER
    // =====================================================

    public int createOrder(OrderDTO dto) {
        return repository.createOrder(dto);
    }

    // =====================================================
    // GET ALL ORDERS
    // =====================================================

    public List<Orders> getAllOrders() {
        return repository.getAllOrders();
    }

    // =====================================================
    // GET ORDERS - PAGINATED
    // =====================================================

    public PageResponse<Orders> getOrdersPage(
            int page,
            int size) {

        int safePage = Math.max(page, 0);

        int safeSize =
                Math.min(
                        Math.max(size, 1),
                        100);

        int offset =
                safePage * safeSize;

        List<Orders> content =
                repository.getOrdersPage(
                        safeSize,
                        offset);

        long total =
                repository.countOrders();

        return new PageResponse<>(
                content,
                safePage,
                safeSize,
                total);
    }

    public PageResponse<Orders> getOrdersFilteredPage(com.restaurant.dto.PageRequestDTO req) {
        List<Orders> content = repository.getOrdersFilteredPage(req);
        long total = repository.countOrdersFiltered(req);
        return new PageResponse<>(content, req.getPage(), req.getSize(), total);
    }

    // =====================================================
    // GET ORDER BY ID
    // =====================================================

    public Orders getOrderById(
            Integer orderId) {

        return repository.getOrderById(orderId);
    }

    // =====================================================
    // GET ORDER DETAILS
    // =====================================================

    public List<OrderDetails> getOrderDetails(
            Integer orderId) {

        return repository.getOrderDetails(
                orderId);
    }

    // =====================================================
    // UPDATE ORDER STATUS
    // =====================================================

    public String updateStatus(
            Integer orderId,
            String status) {

        if (orderId == null) {
            return "Order Id is required";
        }

        if (status == null ||
                status.isBlank()) {

            return "Order status is required";
        }

        int result =
                repository.updateStatus(
                        orderId,
                        status);

        if (result > 0) {
            return "Order Status Updated Successfully";
        }

        return "Order Not Found";
    }

    // =====================================================
    // CANCEL ORDER
    // =====================================================

    public String cancelOrder(
            Integer orderId) {

        int result =
                repository.cancelOrder(
                        orderId);

        if (result > 0) {
            return "Order Cancelled Successfully";
        }

        return "Order Not Found";
    }

    // =====================================================
    // CUSTOMER ORDERS
    // =====================================================

    public List<Orders> getCustomerOrders(
            Integer customerId) {

        return repository.getCustomerOrders(
                customerId);
    }

    // =====================================================
    // TODAY'S ORDERS
    // =====================================================

    public List<Orders> getTodayOrders() {

        return repository.getTodayOrders();
    }

    // =====================================================
    // TODAY'S KITCHEN ORDERS
    // =====================================================

    public List<KitchenOrderDTO>
    getTodayKitchenOrders() {

        return repository.getTodayKitchenOrders();
    }

    // =====================================================
    // KITCHEN STATISTICS
    // =====================================================

    public KitchenStatsDTO
    getKitchenStats() {

        return repository.getKitchenStats();
    }

    // =====================================================
    // GET KITCHEN ORDER DETAIL
    // =====================================================

    public KitchenOrderDTO
    getKitchenOrderDetail(
            Integer orderDetailId) {

        return repository.getKitchenOrderDetail(
                orderDetailId);
    }

    // =====================================================
    // UPDATE KITCHEN STATUS
    // =====================================================

    public String updateKitchenStatus(
            Integer orderDetailId,
            String status,
            Integer chefId) {

        if (orderDetailId == null) {
            return "Order Detail ID is required";
        }

        if (status == null ||
                status.trim().isEmpty()) {

            return "Kitchen status is required";
        }

        if (chefId == null) {
            return "Chef ID is required";
        }

        String normalizedStatus =
                status.trim().toUpperCase();

        if (!normalizedStatus.equals("NEW") &&
            !normalizedStatus.equals("ACCEPTED") &&
            !normalizedStatus.equals("PREPARING") &&
            !normalizedStatus.equals("READY") &&
            !normalizedStatus.equals("SERVED") &&
            !normalizedStatus.equals("CANCELLED")) {

            return "Invalid Kitchen Status";
        }

        int result =
                repository.updateKitchenStatus(
                        orderDetailId,
                        normalizedStatus,
                        chefId);

        if (result > 0) {

            return "Kitchen Status Updated Successfully";
        }

        return "Kitchen Order Not Found";
    }
}

