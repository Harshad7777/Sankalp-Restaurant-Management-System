package com.restaurant.service.payment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaytmPaymentService {

    @Value("${paytm.merchant.id:SANKALP_MID_TEST}")
    private String merchantId;

    @Value("${paytm.merchant.key:SANKALP_KEY_TEST}")
    private String merchantKey;

    public Map<String, Object> initiateTransaction(int billId, BigDecimal amount) {
        String orderId = "PAYTM_BILL_" + billId + "_" + System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("merchantId", merchantId);
        response.put("orderId", orderId);
        response.put("txnAmount", amount.toString());
        response.put("callbackUrl", "/api/payment/verify");
        response.put("checksum", "mock_paytm_checksum_" + System.currentTimeMillis());
        response.put("status", "SUCCESS");
        return response;
    }

    public boolean verifyChecksum(String orderId, String txnId, String checksum) {
        return checksum != null && !checksum.isBlank();
    }
}
