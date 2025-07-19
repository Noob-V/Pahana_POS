// src/main/java/com/pahanaedu/services/BillService.java
package com.pahanaedu.services;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.CartItem;
import java.util.List;
import java.util.Optional;

public interface BillService {
    Optional<Bill> findById(Integer billId);  // Keep as Integer for service layer
    List<Bill> findAll();
    Bill save(Bill bill);
    Bill saveBillWithItems(Bill bill, List<CartItem> items);
    boolean deleteById(Integer billId);      // Keep as Integer for service layer
}