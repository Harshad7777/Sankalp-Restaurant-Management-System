package com.restaurant.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.service.TallyExportService;

@RestController
@RequestMapping({"/accounting", "/api/accounting"})
@CrossOrigin(origins = "*")
public class AccountingExportController {

    private final TallyExportService tallyExportService;

    public AccountingExportController(TallyExportService tallyExportService) {
        this.tallyExportService = tallyExportService;
    }

    @GetMapping("/tally-xml")
    public ResponseEntity<String> exportTallyXml(
            @RequestParam(defaultValue = "2020-01-01") String startDate,
            @RequestParam(defaultValue = "2099-12-31") String endDate) {
        String xmlContent = tallyExportService.generateTallySalesXml(startDate, endDate);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Tally_Sales_Vouchers.xml\"")
                .contentType(MediaType.APPLICATION_XML)
                .body(xmlContent);
    }

    @GetMapping("/quickbooks-csv")
    public ResponseEntity<String> exportQuickBooksCsv(
            @RequestParam(defaultValue = "2020-01-01") String startDate,
            @RequestParam(defaultValue = "2099-12-31") String endDate) {
        String csvContent = tallyExportService.generateQuickBooksCsv(startDate, endDate);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"QuickBooks_Sales_Ledger.csv\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvContent);
    }
}
