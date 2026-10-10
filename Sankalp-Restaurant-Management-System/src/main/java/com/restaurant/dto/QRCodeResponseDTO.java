package com.restaurant.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for generated UPI QR Code responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QRCodeResponseDTO {

    private Integer billId;
    private String billNo;
    private BigDecimal grandTotal;
    private String upiString;
    private String qrCodeBase64;
    private String gateway;
}
