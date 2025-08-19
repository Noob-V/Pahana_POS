package com.pahanaedu.services;

import com.pahanaedu.dto.BillDTO;
import com.pahanaedu.dto.BookDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DashboardService {

    // Financial metrics
    BigDecimal getTotalRevenueToday();
    BigDecimal getTotalRevenueThisMonth();
    BigDecimal getTotalRevenueThisYear();

    // Sales metrics
    int getTotalBillsToday();
    int getTotalBillsThisMonth();
    int getTotalCustomersCount();

    // Inventory metrics
    int getTotalBooksInStock();
    List<BookDTO> getLowStockBooks();
    int getLowStockBooksCount();

    // Recent activity
    List<BillDTO> getRecentBills(int limit);
    List<BookDTO> getTopSellingBooks(int limit);

    // Charts data
    Map<String, BigDecimal> getWeeklyRevenue();
    Map<String, Integer> getMonthlySalesCount();
    Map<String, BigDecimal> getRevenueByPaymentMethod();

    // Quick stats for cards
    Map<String, Object> getDashboardStats();
}