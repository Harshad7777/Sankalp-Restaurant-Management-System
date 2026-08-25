package com.restaurant.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.repository.KitchenRepository;

@Service
public class KitchenService {

    private final KitchenRepository kitchenRepository;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public KitchenService(
            KitchenRepository kitchenRepository) {

        this.kitchenRepository =
                kitchenRepository;
    }


    // =========================================================
    // GET ALL KITCHEN ORDERS
    // =========================================================

    public List<Map<String, Object>> getKitchenOrders() {

        return kitchenRepository.getKitchenOrders();
    }


    // =========================================================
    // GET SINGLE KITCHEN ORDER
    // =========================================================

    public List<Map<String, Object>> getKitchenOrderById(
            int orderId) {

        return kitchenRepository.getKitchenOrderById(
                orderId
        );
    }


    // =========================================================
    // CHECK DETAIL EXISTS
    // =========================================================

    public boolean detailExists(int detailId) {

        return kitchenRepository.detailExists(
                detailId
        );
    }


    // =========================================================
    // UPDATE KITCHEN STATUS
    // =========================================================

    @Transactional
    public void updateKitchenStatus(
            int detailId,
            String status,
            Integer chefId) {


        // -----------------------------------------------------
        // VALIDATE DETAIL
        // -----------------------------------------------------

        if (!kitchenRepository.detailExists(detailId)) {

            throw new RuntimeException(
                    "Order detail not found: "
                            + detailId
            );
        }


        // -----------------------------------------------------
        // GET ORDER ID
        // -----------------------------------------------------

        Integer orderId =
                kitchenRepository.getOrderIdFromDetail(
                        detailId
                );


        if (orderId == null) {

            throw new RuntimeException(
                    "Order ID not found for detail: "
                            + detailId
            );
        }


        // -----------------------------------------------------
        // NORMALIZE STATUS
        // -----------------------------------------------------

        if (status == null || status.isBlank()) {

            throw new RuntimeException(
                    "Kitchen status is required."
            );
        }


        status =
                status
                        .trim()
                        .toUpperCase();


        // -----------------------------------------------------
        // VALIDATE KITCHEN STATUS
        // -----------------------------------------------------

        List<String> allowedStatuses =
                List.of(
                        "NEW",
                        "ACCEPTED",
                        "PREPARING",
                        "READY",
                        "SERVED"
                );


        if (!allowedStatuses.contains(status)) {

            throw new RuntimeException(
                    "Invalid kitchen status: "
                            + status
            );
        }


        // -----------------------------------------------------
        // UPDATE KITCHEN STATUS
        // -----------------------------------------------------

        int updated =
                kitchenRepository.updateKitchenStatus(
                        detailId,
                        status
                );


        if (updated == 0) {

            throw new RuntimeException(
                    "Kitchen status was not updated."
            );
        }


        // -----------------------------------------------------
        // UPDATE CHEF
        // -----------------------------------------------------
        //
        // The chef takes ownership when working
        // on the kitchen item.
        //
        // -----------------------------------------------------

        if (chefId != null) {

            kitchenRepository.updatePreparedBy(
                    detailId,
                    chefId
            );
        }


        // -----------------------------------------------------
        // SERVED TIME
        // -----------------------------------------------------

        if ("SERVED".equals(status)) {

            kitchenRepository.updateServedAt(
                    detailId
            );

        } else {

            kitchenRepository.clearServedAt(
                    detailId
            );
        }


        // -----------------------------------------------------
        // UPDATE MAIN ORDER STATUS
        // -----------------------------------------------------

        updateMainOrderStatus(orderId);
    }


    // =========================================================
    // UPDATE MAIN ORDER STATUS
    // =========================================================
    //
    // IMPORTANT:
    //
    // kitchen_status supports:
    //
    // NEW
    // ACCEPTED
    // PREPARING
    // READY
    // SERVED
    //
    // orders.order_status supports:
    //
    // NEW
    // PREPARING
    // READY
    // SERVED
    // BILLED
    // CANCELLED
    //
    // Therefore ACCEPTED must NEVER be written to
    // orders.order_status.
    //
    // =========================================================

    private void updateMainOrderStatus(
            int orderId) {


        // -----------------------------------------------------
        // GET ALL KITCHEN STATUSES
        // -----------------------------------------------------

        List<String> statuses =
                kitchenRepository.getKitchenStatuses(
                        orderId
                );


        // -----------------------------------------------------
        // NO DETAILS
        // -----------------------------------------------------

        if (statuses == null ||
                statuses.isEmpty()) {

            kitchenRepository.updateOrderStatus(
                    orderId,
                    "NEW"
            );

            return;
        }


        // -----------------------------------------------------
        // ALL SERVED
        // -----------------------------------------------------

        boolean allServed =
                statuses.stream()
                        .allMatch(
                                s ->
                                        "SERVED"
                                                .equalsIgnoreCase(s)
                        );


        if (allServed) {

            kitchenRepository.updateOrderStatus(
                    orderId,
                    "SERVED"
            );

            return;
        }


        // -----------------------------------------------------
        // ALL READY OR SERVED
        // -----------------------------------------------------

        boolean allReadyOrServed =
                statuses.stream()
                        .allMatch(
                                s ->
                                        "READY"
                                                .equalsIgnoreCase(s)
                                        ||
                                        "SERVED"
                                                .equalsIgnoreCase(s)
                        );


        if (allReadyOrServed) {

            kitchenRepository.updateOrderStatus(
                    orderId,
                    "READY"
            );

            return;
        }


        // -----------------------------------------------------
        // ANY PREPARING
        // -----------------------------------------------------

        boolean anyPreparing =
                statuses.stream()
                        .anyMatch(
                                s ->
                                        "PREPARING"
                                                .equalsIgnoreCase(s)
                        );


        if (anyPreparing) {

            kitchenRepository.updateOrderStatus(
                    orderId,
                    "PREPARING"
            );

            return;
        }


        // -----------------------------------------------------
        // ACCEPTED OR NEW
        // -----------------------------------------------------
        //
        // ACCEPTED is ONLY a kitchen status.
        //
        // Main order remains NEW until preparation starts.
        //
        // -----------------------------------------------------

        kitchenRepository.updateOrderStatus(
                orderId,
                "NEW"
        );
    }
}