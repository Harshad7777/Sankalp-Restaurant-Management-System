package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.restaurant.model.Bill;

@ExtendWith(MockitoExtension.class)
class BillRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BillRepository billRepository;

    @Test
    @DisplayName("Should get all bills")
    void testGetAllBills() {
        Bill bill = new Bill();
        bill.setBillId(1);
        bill.setBillNo("BILL-001");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any()))
                .thenReturn(Collections.singletonList(bill));

        List<Bill> list = billRepository.getAllBills();
        assertEquals(1, list.size());
        assertEquals("BILL-001", list.get(0).getBillNo());
    }

    @Test
    @DisplayName("Should get today bills")
    void testGetTodayBills() {
        Bill bill = new Bill();
        bill.setBillId(2);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any()))
                .thenReturn(Collections.singletonList(bill));

        List<Bill> list = billRepository.getTodayBills();
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Should get bill by ID - Found")
    void testGetBillById_Found() {
        Bill bill = new Bill();
        bill.setBillId(1);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1), any()))
                .thenReturn(Collections.singletonList(bill));

        Bill result = billRepository.getBillById(1);
        assertNotNull(result);
        assertEquals(1, result.getBillId());
    }

    @Test
    @DisplayName("Should return null when bill not found by ID")
    void testGetBillById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(99), any()))
                .thenReturn(Collections.emptyList());

        Bill result = billRepository.getBillById(99);
        assertNull(result);
    }

    @Test
    @DisplayName("Should update payment status")
    void testUpdatePayment() {
        when(jdbcTemplate.update(anyString(), eq("CASH"), eq("PAID"), eq(1), any()))
                .thenReturn(1);

        int result = billRepository.updatePayment(1, "CASH", "PAID");
        assertEquals(1, result);
    }
}
