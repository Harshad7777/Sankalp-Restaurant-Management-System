package com.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantSetting {

    private Integer id = 1;
    private Integer restaurantId = 1;
    private String restaurantName = "Sankalp Restaurant";
    private String tagline = "Authentic Flavors & Fine Dining Experience";
    private String phone = "+91 98765 43210";
    private String email = "contact@sankalprestaurant.com";
    private String website = "https://www.sankalprestaurant.com";
    private String address = "Opp. City Mall, Main Ring Road, Ahmedabad, Gujarat 380015";
    private String currencySymbol = "₹";
    private String openingTime = "10:00";
    private String closingTime = "23:00";

    private Double gstRate = 5.00;
    private Double serviceChargeRate = 0.00;
    private String gstinNumber = "24ABCDE1234F1Z5";
    private String fssaiNumber = "10019021004567";
    private String invoicePrefix = "INV-";
    private Double defaultDiscount = 20.00;
    private String billFooterNote = "Thank you for dining with Sankalp! Have a wonderful day.";
    private Boolean enableGst = true;

    private Integer prepTime = 20;
    private Integer stockThreshold = 5;
    private Boolean kitchenSound = true;
    private Boolean autoReleaseTable = true;
    private Boolean autoAcceptOrders = false;

    private String themeColor = "gold";
    private Boolean tableIndicators = true;
    private Boolean dailySalesEmail = true;
    private Boolean lowStockEmail = true;
    private String logoPath = "/images/logo.png";

    public RestaurantSetting() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public Double getGstRate() {
        return gstRate;
    }

    public void setGstRate(Double gstRate) {
        this.gstRate = gstRate;
    }

    public Double getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(Double serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public String getGstinNumber() {
        return gstinNumber;
    }

    public void setGstinNumber(String gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public String getFssaiNumber() {
        return fssaiNumber;
    }

    public void setFssaiNumber(String fssaiNumber) {
        this.fssaiNumber = fssaiNumber;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public Double getDefaultDiscount() {
        return defaultDiscount;
    }

    public void setDefaultDiscount(Double defaultDiscount) {
        this.defaultDiscount = defaultDiscount;
    }

    public String getBillFooterNote() {
        return billFooterNote;
    }

    public void setBillFooterNote(String billFooterNote) {
        this.billFooterNote = billFooterNote;
    }

    public Boolean getEnableGst() {
        return enableGst;
    }

    public void setEnableGst(Boolean enableGst) {
        this.enableGst = enableGst;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getStockThreshold() {
        return stockThreshold;
    }

    public void setStockThreshold(Integer stockThreshold) {
        this.stockThreshold = stockThreshold;
    }

    public Boolean getKitchenSound() {
        return kitchenSound;
    }

    public void setKitchenSound(Boolean kitchenSound) {
        this.kitchenSound = kitchenSound;
    }

    public Boolean getAutoReleaseTable() {
        return autoReleaseTable;
    }

    public void setAutoReleaseTable(Boolean autoReleaseTable) {
        this.autoReleaseTable = autoReleaseTable;
    }

    public Boolean getAutoAcceptOrders() {
        return autoAcceptOrders;
    }

    public void setAutoAcceptOrders(Boolean autoAcceptOrders) {
        this.autoAcceptOrders = autoAcceptOrders;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public Boolean getTableIndicators() {
        return tableIndicators;
    }

    public void setTableIndicators(Boolean tableIndicators) {
        this.tableIndicators = tableIndicators;
    }

    public Boolean getDailySalesEmail() {
        return dailySalesEmail;
    }

    public void setDailySalesEmail(Boolean dailySalesEmail) {
        this.dailySalesEmail = dailySalesEmail;
    }

    public Boolean getLowStockEmail() {
        return lowStockEmail;
    }

    public void setLowStockEmail(Boolean lowStockEmail) {
        this.lowStockEmail = lowStockEmail;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
