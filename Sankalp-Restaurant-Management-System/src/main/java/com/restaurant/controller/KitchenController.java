package com.restaurant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.service.KitchenService;

@RestController
@RequestMapping("/kitchen")
@CrossOrigin(origins = "*")
public class KitchenController {

    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }


    // =========================================================
    // GET ALL KITCHEN ORDERS
    // =========================================================

    @GetMapping
    public ResponseEntity<?> getKitchenOrders() {

        try {

            List<Map<String, Object>> orders =
                    kitchenService.getKitchenOrders();

            return ResponseEntity.ok(orders);

        } catch (Exception e) {

            e.printStackTrace();

            Map<String, Object> response =
                    new HashMap<>();

            response.put(
                    "success",
                    false
            );

            response.put(
                    "message",
                    e.getMessage() != null
                            ? e.getMessage()
                            : "Unable to load kitchen orders."
            );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }


    // =========================================================
    // GET SINGLE KITCHEN ORDER
    // =========================================================

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getKitchenOrder(
            @PathVariable int orderId) {

        try {

            List<Map<String, Object>> order =
                    kitchenService.getKitchenOrderById(orderId);

            if (order.isEmpty()) {

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(
                                Map.of(
                                        "success",
                                        false,

                                        "message",
                                        "Order not found."
                                )
                        );
            }

            return ResponseEntity.ok(order);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Map.of(
                                    "success",
                                    false,

                                    "message",
                                    e.getMessage() != null
                                            ? e.getMessage()
                                            : "Unable to load order."
                            )
                    );
        }
    }


    // =========================================================
    // UPDATE KITCHEN DETAIL STATUS
    // =========================================================
    //
    // PUT /api/kitchen/detail/{detailId}
    //
    // Request:
    //
    // {
    //     "status": "ACCEPTED",
    //     "chefId": 17
    // }
    //
    // =========================================================

    @PutMapping("/detail/{detailId}")
    public ResponseEntity<?> updateKitchenStatus(
            @PathVariable int detailId,
            @RequestBody Map<String, Object> request) {

        try {

            // -------------------------------------------------
            // REQUEST BODY VALIDATION
            // -------------------------------------------------

            if (request == null || request.isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                Map.of(
                                        "success",
                                        false,

                                        "message",
                                        "Request body is required."
                                )
                        );
            }


            // -------------------------------------------------
            // GET STATUS
            // -------------------------------------------------

            String status = null;

            if (request.get("status") != null) {

                status =
                        request.get("status")
                                .toString()
                                .trim()
                                .toUpperCase();
            }


            // -------------------------------------------------
            // STATUS REQUIRED
            // -------------------------------------------------

            if (status == null || status.isBlank()) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                Map.of(
                                        "success",
                                        false,

                                        "message",
                                        "Kitchen status is required."
                                )
                        );
            }


            // -------------------------------------------------
            // VALID KITCHEN STATUSES
            // -------------------------------------------------

            List<String> allowedStatuses =
                    List.of(
                            "NEW",
                            "ACCEPTED",
                            "PREPARING",
                            "READY",
                            "SERVED"
                    );


            if (!allowedStatuses.contains(status)) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                Map.of(
                                        "success",
                                        false,

                                        "message",
                                        "Invalid kitchen status: "
                                                + status
                                )
                        );
            }


            // -------------------------------------------------
            // CHEF ID
            // -------------------------------------------------

            Integer chefId = null;

            if (request.get("chefId") != null) {

                String chefIdValue =
                        request.get("chefId")
                                .toString()
                                .trim();

                if (!chefIdValue.isEmpty()) {

                    chefId =
                            Integer.valueOf(
                                    chefIdValue
                            );
                }
            }


            // -------------------------------------------------
            // DETAIL EXISTS
            // -------------------------------------------------

            if (!kitchenService.detailExists(detailId)) {

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(
                                Map.of(
                                        "success",
                                        false,

                                        "message",
                                        "Order detail not found: "
                                                + detailId
                                )
                        );
            }


            // -------------------------------------------------
            // UPDATE
            // -------------------------------------------------

            kitchenService.updateKitchenStatus(
                    detailId,
                    status,
                    chefId
            );


            // -------------------------------------------------
            // SUCCESS RESPONSE
            // -------------------------------------------------

            Map<String, Object> response =
                    new HashMap<>();

            response.put(
                    "success",
                    true
            );

            response.put(
                    "message",
                    "Kitchen status updated successfully."
            );

            response.put(
                    "detailId",
                    detailId
            );

            response.put(
                    "status",
                    status
            );

            response.put(
                    "chefId",
                    chefId
            );

            return ResponseEntity.ok(response);

        }

        // =====================================================
        // INVALID CHEF ID
        // =====================================================

        catch (NumberFormatException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "success",
                                    false,

                                    "message",
                                    "Invalid chefId."
                            )
                    );
        }

        // =====================================================
        // GENERAL ERROR
        // =====================================================

        catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Map.of(
                                    "success",
                                    false,

                                    "message",
                                    e.getMessage() != null
                                            ? e.getMessage()
                                            : "Unable to update kitchen status."
                            )
                    );
        }
    }
}