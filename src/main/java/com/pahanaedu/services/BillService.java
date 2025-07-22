package com.pahanaedu.services;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.CartItem;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

public interface BillService {
    Optional<Bill> findById(String billId);
    List<Bill> findAll();
    List<Bill> getRecentBills(int limit);
    List<Bill> getBillsByDateRange(LocalDate start, LocalDate end);
    List<Bill> getBillsByDate(LocalDate date);
    Bill save(Bill bill);
    Bill saveBillWithItems(Bill bill, List<CartItem> items);
    boolean deleteById(String billId);  // Changed from Integer to String
}