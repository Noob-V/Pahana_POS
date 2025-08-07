package com.pahanaedu.services.impl;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.impl.BillDAOImpl;
import com.pahanaedu.dto.BillDTO;
import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;
import com.pahanaedu.entities.CartItem;
import com.pahanaedu.mapper.BillMapper;
import com.pahanaedu.services.BillService;
import com.pahanaedu.patterns.builder.BillBuilder;
import com.pahanaedu.patterns.strategy.DiscountStrategy;
import com.pahanaedu.patterns.strategy.PercentageDiscountStrategy;
import com.pahanaedu.patterns.strategy.FixedDiscountStrategy;
import com.pahanaedu.patterns.strategy.NoDiscountStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class BillServiceImpl implements BillService {
    private final BillDAO billDAO;

    public BillServiceImpl() {
        this.billDAO = new BillDAOImpl();
    }

    @Override
    public List<Bill> getRecentBills(int limit) {
        List<Bill> allBills = billDAO.findAll();
        if (allBills.size() > limit) {
            return allBills.subList(0, limit);
        }
        return allBills;
    }

    @Override
    public List<Bill> getBillsByDateRange(LocalDate start, LocalDate end) {
        return billDAO.findByDateRange(start, end);
    }

    @Override
    public List<Bill> getBillsByDate(LocalDate date) {
        return billDAO.findByDate(date);
    }

    @Override
    public Optional<Bill> findById(String billId) {
        return billDAO.findById(billId);
    }

    @Override
    public List<Bill> findAll() {
        return billDAO.findAll();
    }

    @Override
    public Bill save(Bill bill) {
        return billDAO.save(bill);
    }

    @Override
    public Bill saveBillWithItems(Bill bill, List<CartItem> items) {
        // Convert cart items to bill items
        List<BillItem> billItems = new ArrayList<>();
        for (CartItem cartItem : items) {
            BillItem billItem = new BillItem();
            billItem.setIsbn(cartItem.getIsbn());
            billItem.setBookTitle(cartItem.getTitle());
            billItem.setQuantity(cartItem.getQuantity());
            billItem.setUnitPrice(cartItem.getPrice());
            billItem.setTotalPrice(cartItem.getTotal());
            billItem.setDiscountAmount(BigDecimal.ZERO);
            billItems.add(billItem);
        }

        bill.setBillItems(billItems);
        return billDAO.save(bill);
    }

    @Override
    public boolean deleteById(String billId) {
        return billDAO.deleteById(billId);
    }

    @Override
    public Optional<BillDTO> findDTOById(String billId) {
        return findById(billId).map(BillMapper::toDTO);
    }

    @Override
    public List<BillDTO> findAllDTOs() {
        return findAll().stream()
                .map(BillMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillDTO> getRecentBillDTOs(int limit) {
        return getRecentBills(limit).stream()
                .map(BillMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillDTO> getBillDTOsByDateRange(LocalDate start, LocalDate end) {
        return getBillsByDateRange(start, end).stream()
                .map(BillMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillDTO> getBillDTOsByDate(LocalDate date) {
        return getBillsByDate(date).stream()
                .map(BillMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BillDTO saveDTO(BillDTO billDTO) {
        Bill bill = BillMapper.toEntity(billDTO);
        Bill savedBill = billDAO.save(bill);
        return BillMapper.toDTO(savedBill);
    }

    // Add to BillServiceImpl.java
    @Override
    public Bill applyDiscount(Bill bill, String discountType, BigDecimal discountValue) {
        DiscountStrategy<Bill> strategy = createDiscountStrategy(discountType, discountValue);
        BigDecimal discountAmount = strategy.calculateDiscount(bill);

        // Use your existing BillBuilder to create updated bill
        Bill updatedBill = new BillBuilder()
                .billId(bill.getBillId())
                .customerAccountNumber(bill.getCustomerAccountNumber())
                .userId(bill.getUserId())
                .billDate(bill.getBillDate())
                .subtotal(bill.getSubtotal())
                .taxAmount(bill.getTaxAmount())
                .discountAmount(discountAmount)
                .totalAmount(calculateFinalTotal(bill.getSubtotal(), bill.getTaxAmount(), discountAmount))
                .paymentMethod(bill.getPaymentMethod())
                .paymentStatus(bill.getPaymentStatus())
                .notes(bill.getNotes())
                .billItems(bill.getBillItems())
                .build();

        return billDAO.save(updatedBill);
    }

    @Override
    public Bill applyDiscount(String billId, String discountType, BigDecimal discountValue) {
        Optional<Bill> billOpt = findById(billId);
        if (billOpt.isPresent()) {
            return applyDiscount(billOpt.get(), discountType, discountValue);
        }
        throw new RuntimeException("Bill not found: " + billId);
    }

    private DiscountStrategy<Bill> createDiscountStrategy(String discountType, BigDecimal discountValue) {
        switch (discountType.toLowerCase()) {
            case "percentage":
                return new PercentageDiscountStrategy(discountValue.divide(new BigDecimal("100")));
            case "fixed":
                return new FixedDiscountStrategy(discountValue);
            case "none":
            default:
                return new NoDiscountStrategy();
        }
    }

    private BigDecimal calculateFinalTotal(BigDecimal subtotal, BigDecimal taxAmount, BigDecimal discountAmount) {
        BigDecimal total = subtotal != null ? subtotal : BigDecimal.ZERO;
        if (taxAmount != null) total = total.add(taxAmount);
        if (discountAmount != null) total = total.subtract(discountAmount);
        return total;
    }
}