package com.pahanaedu.services;

import com.pahanaedu.dto.BillDTO;
import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.CartItem;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.math.BigDecimal;

public interface BillService {
    Optional<Bill> findById(String billId);
    List<Bill> findAll();
    List<Bill> getRecentBills(int limit);
    List<Bill> getBillsByDateRange(LocalDate start, LocalDate end);
    List<Bill> getBillsByDate(LocalDate date);
    Bill save(Bill bill);
    Bill saveBillWithItems(Bill bill, List<CartItem> items);
    boolean deleteById(String billId);
    Bill applyDiscount(Bill bill, String discountType, BigDecimal discountValue);
    Bill applyDiscount(String billId, String discountType, BigDecimal discountValue);

    Optional<BillDTO> findDTOById(String billId);
    List<BillDTO> findAllDTOs();
    List<BillDTO> getRecentBillDTOs(int limit);
    List<BillDTO> getBillDTOsByDateRange(LocalDate start, LocalDate end);
    List<BillDTO> getBillDTOsByDate(LocalDate date);
    BillDTO saveDTO(BillDTO billDTO);
}