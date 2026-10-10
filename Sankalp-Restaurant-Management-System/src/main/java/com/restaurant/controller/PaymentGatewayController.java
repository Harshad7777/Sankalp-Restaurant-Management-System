package com.restaurant.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.PaymentOrderRequestDTO;
import com.restaurant.dto.PaymentVerificationDTO;
import com.restaurant.dto.QRCodeResponseDTO;
import com.restaurant.model.Bill;
import com.restaurant.service.BillService;
import com.restaurant.service.payment.PaytmPaymentService;
import com.restaurant.service.payment.QRCodeGeneratorService;
import com.restaurant.service.payment.RazorpayPaymentService;
import com.restaurant.service.payment.StripePaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping({"/payment", "/api/payment"})
@CrossOrigin(origins = "*")
public class PaymentGatewayController {

    private final RazorpayPaymentService razorpayService;
    private final StripePaymentService stripeService;
    private final PaytmPaymentService paytmService;
    private final QRCodeGeneratorService qrCodeService;
    private final BillService billService;
    private final JdbcTemplate jdbcTemplate;

    public PaymentGatewayController(
            RazorpayPaymentService razorpayService,
            StripePaymentService stripeService,
            PaytmPaymentService paytmService,
            QRCodeGeneratorService qrCodeService,
            BillService billService,
            JdbcTemplate jdbcTemplate) {
        this.razorpayService = razorpayService;
        this.stripeService = stripeService;
        this.paytmService = paytmService;
        this.qrCodeService = qrCodeService;
        this.billService = billService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createPaymentOrder(@Valid @RequestBody PaymentOrderRequestDTO dto) {
        try {
            Bill bill = billService.getBillById(dto.getBillId());
            if (bill == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Bill not found"));
            }

            BigDecimal amount = dto.getAmount() != null ? dto.getAmount() : BigDecimal.valueOf(bill.getGrandTotal());
            String gateway = dto.getGateway() != null ? dto.getGateway().toUpperCase() : "RAZORPAY";

            Map<String, Object> orderDetails;
            if ("STRIPE".equals(gateway)) {
                orderDetails = stripeService.createPaymentIntent(bill.getBillId(), amount, dto.getCurrency());
            } else if ("PAYTM".equals(gateway)) {
                orderDetails = paytmService.initiateTransaction(bill.getBillId(), amount);
            } else { // Default RAZORPAY
                orderDetails = razorpayService.createOrder(bill.getBillId(), amount, dto.getCurrency());
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "gateway", gateway,
                    "billId", bill.getBillId(),
                    "billNo", bill.getBillNo(),
                    "paymentDetails", orderDetails
            ));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", ex.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationDTO dto) {
        try {
            if (dto.getBillId() == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Bill ID is required"));
            }

            String gateway = dto.getGateway() != null ? dto.getGateway().toUpperCase() : "RAZORPAY";
            boolean isVerified = false;
            String transactionId = "";

            if ("STRIPE".equals(gateway)) {
                isVerified = stripeService.verifyPaymentIntent(dto.getStripePaymentIntentId());
                transactionId = dto.getStripePaymentIntentId();
            } else if ("PAYTM".equals(gateway)) {
                isVerified = paytmService.verifyChecksum(String.valueOf(dto.getBillId()), dto.getPaytmTxnId(), dto.getPaytmChecksum());
                transactionId = dto.getPaytmTxnId();
            } else { // RAZORPAY
                isVerified = razorpayService.verifySignature(dto.getRazorpayOrderId(), dto.getRazorpayPaymentId(), dto.getRazorpaySignature());
                transactionId = dto.getRazorpayPaymentId();
            }

            if (isVerified) {
                // Update bill status to PAID & save transaction details
                String sql = "UPDATE bill SET payment_status = 'PAID', payment_mode = ?, transaction_id = ?, payment_gateway = ? WHERE bill_id = ?";
                jdbcTemplate.update(sql, gateway, transactionId, gateway, dto.getBillId());

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Payment Verified & Bill Updated to PAID",
                        "billId", dto.getBillId(),
                        "transactionId", transactionId
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Payment Signature Verification Failed"
                ));
            }
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", ex.getMessage()));
        }
    }

    @GetMapping("/qr-code/{billId}")
    public ResponseEntity<?> generateTablePaymentQR(@PathVariable Integer billId, @RequestParam(required = false) String vpa) {
        try {
            Bill bill = billService.getBillById(billId);
            if (bill == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Bill not found"));
            }

            BigDecimal grandTotal = BigDecimal.valueOf(bill.getGrandTotal());
            String upiString = qrCodeService.generateUPIString(vpa, "Sankalp Restaurant", grandTotal, bill.getBillNo());
            String base64QR = qrCodeService.generateQRCodeBase64(upiString, 300, 300);

            // Save QR Code URL in bill table
            String sql = "UPDATE bill SET qr_code_url = ? WHERE bill_id = ?";
            jdbcTemplate.update(sql, base64QR, billId);

            QRCodeResponseDTO response = new QRCodeResponseDTO(
                    bill.getBillId(),
                    bill.getBillNo(),
                    grandTotal,
                    upiString,
                    base64QR,
                    "UPI_DYNAMIC_QR"
            );

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", ex.getMessage()));
        }
    }
}
