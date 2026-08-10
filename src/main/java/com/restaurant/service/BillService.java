package com.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.dto.BillDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.Bill;
import com.restaurant.repository.BillRepository;

@Service
public class BillService {

    private final BillRepository repository;


    public BillService(
            BillRepository repository) {

        this.repository = repository;
    }


    // =====================================================
    // CREATE BILL
    // =====================================================

    public int createBill(
            BillDTO dto) {

        return repository.createBill(dto);
    }


    // =====================================================
    // GET ALL BILLS
    // =====================================================

    public List<Bill> getAllBills() {

        return repository.getAllBills();
    }


    // =====================================================
    // GET BILLS - PAGINATED
    // =====================================================

    public PageResponse<Bill> getBillsPage(int page, int size) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);
        int offset = safePage * safeSize;

        List<Bill> content =
                repository.getBillsPage(safeSize, offset);

        long total = repository.countBills();

        return new PageResponse<>(content, safePage, safeSize, total);
    }


    // =====================================================
    // GET BILL BY BILL ID
    // =====================================================

    public Bill getBillById(
            int id) {

        return repository.getBillById(id);
    }


    // =====================================================
    // GET BILL BY ORDER ID
    // =====================================================

    public Bill getBillByOrderId(
            int orderId) {

        return repository.getBillByOrderId(
                orderId
        );
    }


    // =====================================================
    // UPDATE PAYMENT
    // =====================================================

    public String updatePayment(
            int billId,
            String paymentMethod) {

        int result =
                repository.updatePayment(
                        billId,
                        paymentMethod
                );


        if (result > 0) {

            return "Payment Updated Successfully";
        }


        return "Payment Update Failed";
    }


    // =====================================================
    // TODAY BILLS
    // =====================================================

    public List<Bill> getTodayBills() {

        return repository.getTodayBills();
    }

}