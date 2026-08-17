package com.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.restaurant.dto.BillDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.Bill;
import com.restaurant.service.BillService;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin(origins = "*")
public class BillController {

    private final BillService service;

    public BillController(
            BillService service) {

        this.service = service;
    }

    // =====================================================
    // CREATE BILL
    // =====================================================

    @PostMapping
    public ResponseEntity<?> createBill(
            @RequestBody BillDTO dto) {

        try {

            int billId =
                    service.createBill(dto);

            Bill bill =
                    service.getBillById(
                            billId
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(bill);

        } catch (IllegalArgumentException ex) {

            return ResponseEntity
                    .badRequest()
                    .body(ex.getMessage());

        } catch (Exception ex) {

            return ResponseEntity
                    .status(
                            HttpStatus.INTERNAL_SERVER_ERROR
                    )
                    .body(
                            "Failed to create bill: "
                                    + ex.getMessage()
                    );
        }
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<List<Bill>>
    getAllBills() {

        return ResponseEntity.ok(
                service.getAllBills()
        );
    }

    // =====================================================
    // PAGINATION
    // =====================================================

    @GetMapping("/page")
    public ResponseEntity<PageResponse<Bill>>
    getBillsPage(
            @RequestParam(
                    defaultValue = "0"
            ) int page,

            @RequestParam(
                    defaultValue = "10"
            ) int size) {

        return ResponseEntity.ok(
                service.getBillsPage(
                        page,
                        size
                )
        );
    }

    // =====================================================
    // TODAY
    // =====================================================

    @GetMapping("/today")
    public ResponseEntity<List<Bill>>
    getTodayBills() {

        return ResponseEntity.ok(
                service.getTodayBills()
        );
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getBillById(
            @PathVariable int id) {

        try {

            Bill bill =
                    service.getBillById(id);

            if (bill == null) {

                return ResponseEntity
                        .status(
                                HttpStatus.NOT_FOUND
                        )
                        .body(
                                "Bill not found"
                        );
            }

            return ResponseEntity.ok(bill);

        } catch (IllegalArgumentException ex) {

            return ResponseEntity
                    .badRequest()
                    .body(ex.getMessage());
        }
    }

    // =====================================================
    // GET BY ORDER ID
    // =====================================================

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getBillByOrderId(
            @PathVariable int orderId) {

        try {

            Bill bill =
                    service.getBillByOrderId(
                            orderId
                    );

            if (bill == null) {

                return ResponseEntity
                        .status(
                                HttpStatus.NOT_FOUND
                        )
                        .body(
                                "Bill not found"
                        );
            }

            return ResponseEntity.ok(bill);

        } catch (IllegalArgumentException ex) {

            return ResponseEntity
                    .badRequest()
                    .body(ex.getMessage());
        }
    }

    // =====================================================
    // UPDATE PAYMENT
    // =====================================================

    @PutMapping("/{billId}/payment")
    public ResponseEntity<String>
    updatePayment(

            @PathVariable int billId,

            @RequestParam
            String paymentMethod,

            @RequestParam(
                    defaultValue = "PAID"
            )
            String paymentStatus) {

        String result =
                service.updatePayment(
                        billId,
                        paymentMethod,
                        paymentStatus
                );

        if ("Payment Updated Successfully"
                .equals(result)) {

            return ResponseEntity.ok(result);
        }

        return ResponseEntity
                .badRequest()
                .body(result);
    }
}