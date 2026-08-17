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
    private final com.restaurant.repository.OrderRepository orderRepository;
    private final com.restaurant.repository.CustomerRepository customerRepository;
    private final com.restaurant.service.mail.JmsMailProducer mailProducer;

    public BillService(
            BillRepository repository,
            com.restaurant.repository.OrderRepository orderRepository,
            com.restaurant.repository.CustomerRepository customerRepository,
            com.restaurant.service.mail.JmsMailProducer mailProducer) {

        this.repository = repository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.mailProducer = mailProducer;
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

        int billId = repository.createBill(dto);

        // Asynchronously enqueue invoice receipt email via JMS
        try {
            Bill bill = repository.getBillById(billId);
            if (bill != null) {
                com.restaurant.model.Orders order = orderRepository.getOrderById(bill.getOrderId());
                if (order != null && order.getCustomerId() != null) {
                    com.restaurant.model.Customer customer = customerRepository.findById(order.getCustomerId());
                    if (customer != null && customer.getEmail() != null && !customer.getEmail().isBlank()) {
                        mailProducer.sendBillInvoiceEmail(
                                customer.getEmail(),
                                customer.getCustomerName(),
                                bill.getBillNo(),
                                bill.getSubtotal(),
                                bill.getGst(),
                                bill.getDiscount(),
                                bill.getGrandTotal(),
                                bill.getPaymentMethod()
                        );
                    }
                }
            }
        } catch (Exception e) {
            // Non-blocking: failures in mailing do not break billing transaction
        }

        return billId;
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

    public PageResponse<Bill> getBillsFilteredPage(com.restaurant.dto.PageRequestDTO req) {
        List<Bill> content = repository.getBillsFilteredPage(req);
        long total = repository.countBillsFiltered(req);
        return new PageResponse<>(content, req.getPage(), req.getSize(), total);
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