package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.service.TallyExportService;

@ExtendWith(MockitoExtension.class)
class AccountingExportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TallyExportService tallyExportService;

    @InjectMocks
    private AccountingExportController accountingExportController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountingExportController).build();
    }

    @Test
    @DisplayName("Should export Tally XML vouchers")
    void testExportTallyXml() throws Exception {
        String mockXml = "<ENVELOPE><HEADER><TALLYREQUEST>Import Data</TALLYREQUEST></HEADER></ENVELOPE>";
        when(tallyExportService.generateTallySalesXml(anyString(), anyString())).thenReturn(mockXml);

        mockMvc.perform(get("/accounting/tally-xml"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"Tally_Sales_Vouchers.xml\""))
                .andExpect(content().xml(mockXml));
    }

    @Test
    @DisplayName("Should export QuickBooks CSV ledger")
    void testExportQuickBooksCsv() throws Exception {
        String mockCsv = "Date,Transaction Type,Num,Name,Amount\n2026-02-01,Invoice,101,Rahul,500.00\n";
        when(tallyExportService.generateQuickBooksCsv(anyString(), anyString())).thenReturn(mockCsv);

        mockMvc.perform(get("/accounting/quickbooks-csv"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"QuickBooks_Sales_Ledger.csv\""))
                .andExpect(content().string(mockCsv));
    }
}
