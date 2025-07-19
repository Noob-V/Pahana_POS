// src/main/java/com/pahanaedu/dao/BillDAO.java
package com.pahanaedu.dao;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillDAO {
    Optional<Bill> findById(String billId);
    List<Bill> findAll();
    List<Bill> findByCustomerAccountNumber(String accountNumber);
    List<Bill> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<Bill> findByUserId(Integer userId);
    Bill save(Bill bill);
    boolean deleteById(String billId);
    String generateNextBillId();

    // Bill items methods
    List<BillItem> findBillItemsByBillId(String billId);
    BillItem saveBillItem(BillItem billItem);
    boolean deleteBillItemsByBillId(String billId);

}