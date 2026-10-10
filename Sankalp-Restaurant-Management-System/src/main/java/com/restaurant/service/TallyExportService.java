package com.restaurant.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TallyExportService {

    private final JdbcTemplate jdbcTemplate;

    public TallyExportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String generateTallySalesXml(String startDate, String endDate) {
        String sql = "SELECT b.*, c.customer_name as customer_name " +
                     "FROM bill b " +
                     "LEFT JOIN orders o ON b.order_id = o.order_id " +
                     "LEFT JOIN customer c ON o.customer_id = c.customer_id " +
                     "WHERE DATE(COALESCE(b.created_at, b.bill_date, NOW())) BETWEEN ? AND ? " +
                     "ORDER BY b.bill_id ASC";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, startDate, endDate);

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<ENVELOPE>\n");
        xml.append("  <HEADER>\n");
        xml.append("    <TALLYREQUEST>Import Data</TALLYREQUEST>\n");
        xml.append("  </HEADER>\n");
        xml.append("  <BODY>\n");
        xml.append("    <IMPORTDATA>\n");
        xml.append("      <REQUESTDESC>\n");
        xml.append("        <REPORTNAME>Vouchers</REPORTNAME>\n");
        xml.append("      </REQUESTDESC>\n");
        xml.append("      <REQUESTDATA>\n");

        for (Map<String, Object> r : rows) {
            Object rawDate = r.get("created_at") != null ? r.get("created_at") : r.get("bill_date");
            String dateStr = rawDate != null ? rawDate.toString().substring(0, 10).replace("-", "") : "20260101";
            String voucherNo = r.get("bill_no") != null ? r.get("bill_no").toString() : ("INV-" + r.get("bill_id"));
            String customerName = r.get("customer_name") != null ? (String) r.get("customer_name") : "Cash Sales";
            double grandTotal = r.get("grand_total") != null ? Double.parseDouble(r.get("grand_total").toString()) : 0.0;
            double taxAmount = r.get("tax_amount") != null ? Double.parseDouble(r.get("tax_amount").toString()) : 0.0;
            double netSales = grandTotal - taxAmount;

            xml.append("        <TALLYMESSAGE xmlns:UDF=\"TallyUDF\">\n");
            xml.append("          <VOUCHER VCHTYPE=\"Sales\" ACTION=\"Create\">\n");
            xml.append("            <DATE>").append(dateStr).append("</DATE>\n");
            xml.append("            <VOUCHERTYPENAME>Sales</VOUCHERTYPENAME>\n");
            xml.append("            <VOUCHERNUMBER>").append(voucherNo).append("</VOUCHERNUMBER>\n");
            xml.append("            <PARTYLEDGERNAME>").append(customerName).append("</PARTYLEDGERNAME>\n");
            xml.append("            <ALLLEDGERENTRIES.LIST>\n");
            xml.append("              <LEDGERNAME>").append(customerName).append("</LEDGERNAME>\n");
            xml.append("              <ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE>\n");
            xml.append("              <AMOUNT>-").append(String.format("%.2f", grandTotal)).append("</AMOUNT>\n");
            xml.append("            </ALLLEDGERENTRIES.LIST>\n");
            xml.append("            <ALLLEDGERENTRIES.LIST>\n");
            xml.append("              <LEDGERNAME>Sales Account</LEDGERNAME>\n");
            xml.append("              <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>\n");
            xml.append("              <AMOUNT>").append(String.format("%.2f", netSales)).append("</AMOUNT>\n");
            xml.append("            </ALLLEDGERENTRIES.LIST>\n");
            xml.append("            <ALLLEDGERENTRIES.LIST>\n");
            xml.append("              <LEDGERNAME>GST Output Tax</LEDGERNAME>\n");
            xml.append("              <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>\n");
            xml.append("              <AMOUNT>").append(String.format("%.2f", taxAmount)).append("</AMOUNT>\n");
            xml.append("            </ALLLEDGERENTRIES.LIST>\n");
            xml.append("          </VOUCHER>\n");
            xml.append("        </TALLYMESSAGE>\n");
        }

        xml.append("      </REQUESTDATA>\n");
        xml.append("    </IMPORTDATA>\n");
        xml.append("  </BODY>\n");
        xml.append("</ENVELOPE>\n");

        return xml.toString();
    }

    public String generateQuickBooksCsv(String startDate, String endDate) {
        String sql = "SELECT b.*, c.customer_name as customer_name " +
                     "FROM bill b " +
                     "LEFT JOIN orders o ON b.order_id = o.order_id " +
                     "LEFT JOIN customer c ON o.customer_id = c.customer_id " +
                     "WHERE DATE(COALESCE(b.created_at, b.bill_date, NOW())) BETWEEN ? AND ? " +
                     "ORDER BY b.bill_id ASC";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, startDate, endDate);

        StringBuilder csv = new StringBuilder();
        csv.append("VoucherNo,Date,Customer,PaymentStatus,PaymentMethod,Subtotal,TaxAmount,GrandTotal\n");

        for (Map<String, Object> r : rows) {
            Object rawDate = r.get("created_at") != null ? r.get("created_at") : r.get("bill_date");
            String dateStr = rawDate != null ? rawDate.toString() : "";
            String voucherNo = r.get("bill_no") != null ? r.get("bill_no").toString() : ("INV-" + r.get("bill_id"));
            String customerName = r.get("customer_name") != null ? (String) r.get("customer_name") : "Cash Customer";
            String status = r.get("payment_status") != null ? (String) r.get("payment_status") : "PAID";
            Object rawMode = r.get("payment_mode") != null ? r.get("payment_mode") : r.get("payment_method");
            String method = rawMode != null ? rawMode.toString() : "CASH";
            double grandTotal = r.get("grand_total") != null ? Double.parseDouble(r.get("grand_total").toString()) : 0.0;
            double taxAmount = r.get("tax_amount") != null ? Double.parseDouble(r.get("tax_amount").toString()) : 0.0;
            double subtotal = grandTotal - taxAmount;

            csv.append(String.format("%s,%s,\"%s\",%s,%s,%.2f,%.2f,%.2f\n",
                    voucherNo, dateStr, customerName, status, method, subtotal, taxAmount, grandTotal));
        }

        return csv.toString();
    }
}
