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

    public int createBill(BillDTO dto) {

        if (dto == null) {

            throw new IllegalArgumentException(
                    "Bill request cannot be null"
            );
        }

        if (dto.getOrderId() <= 0) {

            throw new IllegalArgumentException(
                    "Invalid Order ID"
            );
        }

        if (dto.getDiscount() < 0) {

            throw new IllegalArgumentException(
                    "Discount cannot be negative"
            );
        }

        return repository.createBill(dto);
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<Bill> getAllBills() {

        return repository.getAllBills();
    }

    // =====================================================
    // GET PAGE
    // =====================================================

    public PageResponse<Bill> getBillsPage(
            int page,
            int size) {

        int safePage =
                Math.max(
                        page,
                        0
                );

        int safeSize =
                Math.min(
                        Math.max(size, 1),
                        100
                );

        int offset =
                safePage * safeSize;

        List<Bill> content =
                repository.getBillsPage(
                        safeSize,
                        offset
                );

        long total =
                repository.countBills();

        return new PageResponse<>(
                content,
                safePage,
                safeSize,
                total
        );
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public Bill getBillById(
            int id) {

        if (id <= 0) {

            throw new IllegalArgumentException(
                    "Invalid Bill ID"
            );
        }

        return repository.getBillById(id);
    }

    // =====================================================
    // GET BY ORDER
    // =====================================================

    public Bill getBillByOrderId(
            int orderId) {

        if (orderId <= 0) {

            throw new IllegalArgumentException(
                    "Invalid Order ID"
            );
        }

        return repository.getBillByOrderId(
                orderId
        );
    }

    // =====================================================
    // UPDATE PAYMENT
    // =====================================================

    public String updatePayment(
            int billId,
            String paymentMethod,
            String paymentStatus) {

        if (billId <= 0) {

            return "Invalid Bill ID";
        }

        if (paymentMethod == null ||
                paymentMethod.trim().isEmpty()) {

            return "Payment method is required";
        }

        if (paymentStatus == null ||
                paymentStatus.trim().isEmpty()) {

            return "Payment status is required";
        }

        try {

            int result =
                    repository.updatePayment(
                            billId,
                            paymentMethod,
                            paymentStatus
                    );

            if (result > 0) {

                return "Payment Updated Successfully";
            }

            return "Payment Update Failed";

        } catch (IllegalArgumentException ex) {

            return ex.getMessage();
        }
    }

    // =====================================================
    // TODAY
    // =====================================================

    public List<Bill> getTodayBills() {

        return repository.getTodayBills();
    }
}