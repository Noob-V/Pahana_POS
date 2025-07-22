package com.pahanaedu.services.impl;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.impl.BillDAOImpl;
import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;
import com.pahanaedu.entities.CartItem;
import com.pahanaedu.services.BillService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

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
}