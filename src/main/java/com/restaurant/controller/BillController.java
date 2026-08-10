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

import com.restaurant.dto.BillDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.Bill;
import com.restaurant.service.BillService;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin("*")
public class BillController {

    private final BillService service;


    public BillController(
            BillService service) {

        this.service = service;
    }


    // =====================================================
    // CREATE BILL
    // POST /api/bill/create
    // =====================================================

    @PostMapping("/create")
    public ResponseEntity<?> createBill(
            @RequestBody BillDTO dto) {

        try {

            int billId =
                    service.createBill(dto);


            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "Bill Generated Successfully",

                            "billId",
                            billId
                    )
            );

        }

        catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }


    // =====================================================
    // GET BILL BY BILL ID
    // GET /api/bill/{id}
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getBillById(
            @PathVariable int id) {

        try {

            Bill bill =
                    service.getBillById(id);


            return ResponseEntity.ok(
                    bill
            );

        }

        catch (Exception e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }


    // =====================================================
    // GET BILL BY ORDER ID
    // GET /api/bill/order/{orderId}
    // =====================================================

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getBillByOrderId(
            @PathVariable int orderId) {

        try {

            Bill bill =
                    service.getBillByOrderId(
                            orderId
                    );


            return ResponseEntity.ok(
                    bill
            );

        }

        catch (Exception e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }


    // =====================================================
    // GET ALL BILLS
    // GET /api/bill/all
    // =====================================================

    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAllBills() {

        return ResponseEntity.ok(
                service.getAllBills()
        );
    }


    // =====================================================
    // GET BILLS - PAGINATED
    // GET /api/bill/page?page=0&size=20
    // =====================================================

    @GetMapping("/page")
    public ResponseEntity<PageResponse<Bill>> getBillsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(
                service.getBillsPage(page, size)
        );
    }


    // =====================================================
    // UPDATE PAYMENT
    // PUT /api/bill/payment/{billId}
    // =====================================================

    @PutMapping("/payment/{billId}")
    public ResponseEntity<?> updatePayment(
            @PathVariable int billId,
            @RequestParam String paymentMethod) {

        String result =
                service.updatePayment(
                        billId,
                        paymentMethod
                );


        if (result.equals(
                "Payment Updated Successfully")) {

            return ResponseEntity.ok(
                    result
            );
        }


        return ResponseEntity
                .badRequest()
                .body(result);
    }


    // =====================================================
    // TODAY BILLS
    // GET /api/bill/today
    // =====================================================

    @GetMapping("/today")
    public ResponseEntity<List<Bill>> getTodayBills() {

        return ResponseEntity.ok(
                service.getTodayBills()
        );
    }

}