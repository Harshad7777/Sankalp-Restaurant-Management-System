package com.restaurant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.Bill;
import com.restaurant.model.OrderDetails;
import com.restaurant.model.Orders;
import com.restaurant.service.BillService;
import com.restaurant.service.OrderService;
import com.restaurant.service.printer.EscPosPrintService;

@RestController
@RequestMapping({"/printer", "/api/printer"})
@CrossOrigin(origins = "*")
public class PrinterController {

    private final EscPosPrintService printService;
    private final OrderService orderService;
    private final BillService billService;

    public PrinterController(EscPosPrintService printService, OrderService orderService, BillService billService) {
        this.printService = printService;
        this.orderService = orderService;
        this.billService = billService;
    }

    @PostMapping("/print-kot/{orderId}")
    public ResponseEntity<?> printKOT(
            @PathVariable Integer orderId,
            @RequestParam(required = false) String printerIp,
            @RequestParam(defaultValue = "9100") int printerPort) {
        try {
            Orders order = orderService.getOrderById(orderId);
            if (order == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Order not found"));
            }
            List<OrderDetails> details = orderService.getOrderDetails(orderId);

            byte[] escposBytes = printService.generateKOTBytes(order, details, String.valueOf(order.getTableId()));
            String base64Payload = printService.encodeBase64(escposBytes);

            boolean networkSent = false;
            if (printerIp != null && !printerIp.isBlank()) {
                networkSent = printService.sendBytesToNetworkPrinter(printerIp, printerPort, escposBytes);
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "orderId", orderId,
                    "networkPrinterSent", networkSent,
                    "escposBase64", base64Payload
            ));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", ex.getMessage()));
        }
    }

    @PostMapping("/print-bill/{billId}")
    public ResponseEntity<?> printBill(
            @PathVariable Integer billId,
            @RequestParam(required = false) String printerIp,
            @RequestParam(defaultValue = "9100") int printerPort) {
        try {
            Bill bill = billService.getBillById(billId);
            if (bill == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Bill not found"));
            }
            Orders order = orderService.getOrderById(bill.getOrderId());
            List<OrderDetails> details = orderService.getOrderDetails(bill.getOrderId());

            byte[] escposBytes = printService.generateBillBytes(bill, order, details, "SANKALP RESTAURANT");
            String base64Payload = printService.encodeBase64(escposBytes);

            boolean networkSent = false;
            if (printerIp != null && !printerIp.isBlank()) {
                networkSent = printService.sendBytesToNetworkPrinter(printerIp, printerPort, escposBytes);
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "billId", billId,
                    "networkPrinterSent", networkSent,
                    "escposBase64", base64Payload
            ));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", ex.getMessage()));
        }
    }
}
