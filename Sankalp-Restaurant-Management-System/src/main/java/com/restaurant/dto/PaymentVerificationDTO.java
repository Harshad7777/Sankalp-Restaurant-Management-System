package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for payment verification webhook/callback requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentVerificationDTO {

    private Integer billId;
    private String gateway; // RAZORPAY, STRIPE, PAYTM
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    private String stripePaymentIntentId;
    private String paytmTxnId;
    private String paytmChecksum;
}
