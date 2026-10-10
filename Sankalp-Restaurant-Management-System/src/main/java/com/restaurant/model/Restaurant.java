package com.restaurant.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Restaurant entity in the Restaurant Management System.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    private Integer restaurantId;
    private String restaurantName;
    private String tagline;
    private String phone;
    private String email;
    private String website;
    private String address;
    private String currencySymbol;
    private String openingTime;
    private String closingTime;
    private BigDecimal gstRate;
    private BigDecimal serviceChargeRate;
    private String gstinNumber;
    private String fssaiNumber;
    private String invoicePrefix;
    private BigDecimal defaultDiscount;
    private String billFooterNote;
    private Boolean enableGst;
    private Integer prepTime;
    private Integer stockThreshold;
    private Boolean kitchenSound;
    private Boolean autoReleaseTable;
    private Boolean autoAcceptOrders;
    private String themeColor;
    private Boolean tableIndicators;
    private Boolean dailySalesEmail;
    private Boolean lowStockEmail;
    private String logoPath;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
