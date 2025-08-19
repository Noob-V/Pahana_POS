package com.pahanaedu.services.impl;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.BookDAO;
import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.impl.BillDAOImpl;
import com.pahanaedu.dao.impl.BookDAOImpl;
import com.pahanaedu.dao.impl.CustomerDAOImpl;
import com.pahanaedu.dto.BillDTO;
import com.pahanaedu.dto.BookDTO;
import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.Book;
import com.pahanaedu.entities.BillItem;
import com.pahanaedu.mapper.BillMapper;
import com.pahanaedu.mapper.BookMapper;
import com.pahanaedu.services.DashboardService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardServiceImpl implements DashboardService {

    private final BillDAO billDAO;
    private final BookDAO bookDAO;
    private final CustomerDAO customerDAO;

    public DashboardServiceImpl() {
        // Direct instantiation instead of factory pattern
        this.billDAO = new BillDAOImpl();
        this.bookDAO = new BookDAOImpl();
        this.customerDAO = new CustomerDAOImpl();
    }

    @Override
    public BigDecimal getTotalRevenueToday() {
        try {
            LocalDate today = LocalDate.now();
            List<Bill> todayBills = billDAO.findByDate(today);
            return todayBills.stream()
                    .filter(bill -> bill != null && "PAID".equalsIgnoreCase(bill.getPaymentStatus()))
                    .map(Bill::getTotalAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal getTotalRevenueThisMonth() {
        try {
            LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
            LocalDate endOfMonth = LocalDate.now();
            List<Bill> monthBills = billDAO.findByDateRange(startOfMonth, endOfMonth);
            return monthBills.stream()
                    .filter(bill -> bill != null && "PAID".equalsIgnoreCase(bill.getPaymentStatus()))
                    .map(Bill::getTotalAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal getTotalRevenueThisYear() {
        try {
            LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
            LocalDate endOfYear = LocalDate.now();
            List<Bill> yearBills = billDAO.findByDateRange(startOfYear, endOfYear);
            return yearBills.stream()
                    .filter(bill -> bill != null && "PAID".equalsIgnoreCase(bill.getPaymentStatus()))
                    .map(Bill::getTotalAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public int getTotalBillsToday() {
        try {
            LocalDate today = LocalDate.now();
            List<Bill> todayBills = billDAO.findByDate(today);
            return todayBills != null ? todayBills.size() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getTotalBillsThisMonth() {
        try {
            LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
            LocalDate endOfMonth = LocalDate.now();
            List<Bill> monthBills = billDAO.findByDateRange(startOfMonth, endOfMonth);
            return monthBills != null ? monthBills.size() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getTotalCustomersCount() {
        try {
            List<com.pahanaedu.entities.Customer> customers = customerDAO.findAll();
            return customers != null ? (int) customers.stream().filter(c -> c != null && c.isActive()).count() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getTotalBooksInStock() {
        try {
            List<Book> allBooks = bookDAO.findAll();
            return allBooks.stream()
                    .filter(book -> book != null && book.isActive() && book.getQuantity() != null)
                    .mapToInt(Book::getQuantity)
                    .sum();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<BookDTO> getLowStockBooks() {
        try {
            List<Book> lowStockBooks = bookDAO.findLowStockBooks();
            if (lowStockBooks == null) {
                return new ArrayList<>();
            }
            return lowStockBooks.stream()
                    .filter(book -> book != null && book.isActive())
                    .map(BookMapper::toDTO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int getLowStockBooksCount() {
        try {
            List<Book> lowStockBooks = bookDAO.findLowStockBooks();
            return lowStockBooks != null ? (int) lowStockBooks.stream().filter(book -> book != null && book.isActive()).count() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<BillDTO> getRecentBills(int limit) {
        try {
            List<Bill> allBills = billDAO.findAll();
            if (allBills == null) {
                return new ArrayList<>();
            }
            return allBills.stream()
                    .filter(Objects::nonNull)
                    .filter(bill -> bill.getBillDate() != null)
                    .sorted((b1, b2) -> b2.getBillDate().compareTo(b1.getBillDate()))
                    .limit(Math.max(1, limit))
                    .map(BillMapper::toDTO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<BookDTO> getTopSellingBooks(int limit) {
        try {
            LocalDate thirtyDaysAgo = LocalDate.now().minus(30, ChronoUnit.DAYS);
            LocalDate today = LocalDate.now();
            List<Bill> recentBills = billDAO.findByDateRange(thirtyDaysAgo, today);

            if (recentBills == null || recentBills.isEmpty()) {
                return new ArrayList<>();
            }

            Map<String, Integer> bookSalesCount = new HashMap<>();

            for (Bill bill : recentBills) {
                if (bill == null || bill.getBillId() == null) continue;

                try {
                    List<BillItem> items = billDAO.findBillItemsByBillId(bill.getBillId());
                    for (BillItem item : items) {
                        if (item != null && item.getIsbn() != null) {
                            String isbn = item.getIsbn(); // Use getIsbn() instead of getBookIsbn()
                            bookSalesCount.put(isbn, bookSalesCount.getOrDefault(isbn, 0) + item.getQuantity());
                        }
                    }
                } catch (Exception e) {
                    // Skip this bill if there's an error
                }
            }

            return bookSalesCount.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(Math.max(1, limit))
                    .map(entry -> {
                        try {
                            Optional<Book> bookOpt = bookDAO.findByIsbn(entry.getKey());
                            return bookOpt.map(BookMapper::toDTO).orElse(null);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Map<String, BigDecimal> getWeeklyRevenue() {
        Map<String, BigDecimal> weeklyRevenue = new LinkedHashMap<>();
        try {
            LocalDate today = LocalDate.now();

            for (int i = 6; i >= 0; i--) {
                LocalDate date = today.minus(i, ChronoUnit.DAYS);
                try {
                    List<Bill> dayBills = billDAO.findByDate(date);
                    BigDecimal dayRevenue = dayBills.stream()
                            .filter(bill -> bill != null && "PAID".equalsIgnoreCase(bill.getPaymentStatus()))
                            .map(Bill::getTotalAmount)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    weeklyRevenue.put(date.toString(), dayRevenue);
                } catch (Exception e) {
                    weeklyRevenue.put(date.toString(), BigDecimal.ZERO);
                }
            }
        } catch (Exception e) {
            // Return empty map on error
        }
        return weeklyRevenue;
    }

    @Override
    public Map<String, Integer> getMonthlySalesCount() {
        Map<String, Integer> monthlySales = new LinkedHashMap<>();
        try {
            LocalDate today = LocalDate.now();

            for (int i = 11; i >= 0; i--) {
                LocalDate monthStart = today.minus(i, ChronoUnit.MONTHS).withDayOfMonth(1);
                LocalDate monthEnd = monthStart.plusMonths(1).minus(1, ChronoUnit.DAYS);

                try {
                    List<Bill> monthBills = billDAO.findByDateRange(monthStart, monthEnd);
                    String monthKey = monthStart.getMonth().toString() + " " + monthStart.getYear();
                    monthlySales.put(monthKey, monthBills != null ? monthBills.size() : 0);
                } catch (Exception e) {
                    String monthKey = monthStart.getMonth().toString() + " " + monthStart.getYear();
                    monthlySales.put(monthKey, 0);
                }
            }
        } catch (Exception e) {
            // Return empty map on error
        }
        return monthlySales;
    }

    @Override
    public Map<String, BigDecimal> getRevenueByPaymentMethod() {
        try {
            LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
            LocalDate endOfMonth = LocalDate.now();
            List<Bill> monthBills = billDAO.findByDateRange(startOfMonth, endOfMonth);

            if (monthBills == null) {
                return new HashMap<>();
            }

            return monthBills.stream()
                    .filter(bill -> bill != null && "PAID".equalsIgnoreCase(bill.getPaymentStatus()))
                    .filter(bill -> bill.getPaymentMethod() != null && bill.getTotalAmount() != null)
                    .collect(Collectors.groupingBy(
                            Bill::getPaymentMethod,
                            Collectors.reducing(BigDecimal.ZERO, Bill::getTotalAmount, BigDecimal::add)
                    ));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("todayRevenue", getTotalRevenueToday());
        stats.put("monthRevenue", getTotalRevenueThisMonth());
        stats.put("yearRevenue", getTotalRevenueThisYear());
        stats.put("todayBills", getTotalBillsToday());
        stats.put("monthBills", getTotalBillsThisMonth());
        stats.put("totalCustomers", getTotalCustomersCount());
        stats.put("totalBooksInStock", getTotalBooksInStock());
        stats.put("lowStockBooksCount", getLowStockBooksCount());

        return stats;
    }
}