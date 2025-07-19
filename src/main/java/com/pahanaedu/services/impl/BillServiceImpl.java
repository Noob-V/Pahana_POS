// src/main/java/com/pahanaedu/services/impl/BillServiceImpl.java
package com.pahanaedu.services.impl;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.impl.BillDAOImpl;
import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;
import com.pahanaedu.entities.CartItem;
import com.pahanaedu.services.BillService;

import java.util.List;
import java.util.Optional;

public class BillServiceImpl implements BillService {
    private final BillDAO billDAO;

    public BillServiceImpl() {
        this.billDAO = new BillDAOImpl();
    }

    @Override
    public Optional<Bill> findById(Integer billId) {
        return billDAO.findById(billId.toString());
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
        // Save the bill first
        Bill savedBill = billDAO.save(bill);

        // Convert CartItems to BillItems and save
        for (CartItem cartItem : items) {
            BillItem billItem = new BillItem();
            billItem.setBillId(savedBill.getBillId());
            billItem.setIsbn(cartItem.getIsbn());
            billItem.setQuantity(cartItem.getQuantity());
            billItem.setUnitPrice(cartItem.getPrice());
            billItem.setDiscountAmount(java.math.BigDecimal.ZERO);

            billDAO.saveBillItem(billItem);
        }

        return savedBill;
    }

    @Override
    public boolean deleteById(Integer billId) {
        return billDAO.deleteById(billId.toString());
    }
}