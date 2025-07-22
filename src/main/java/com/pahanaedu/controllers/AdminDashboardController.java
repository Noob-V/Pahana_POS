package com.pahanaedu.controllers;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.Book;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.entities.User;
import com.pahanaedu.services.BillService;
import com.pahanaedu.services.BookService;
import com.pahanaedu.services.CustomerService;
import com.pahanaedu.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "AdminDashboardController", urlPatterns = {
        "/admin/dashboard",
        "/admin/analytics",
        "/admin/reports/*",
        "/admin/system/*"
})
public class AdminDashboardController extends HttpServlet {

    private BillService billService;
    private BookService bookService;
    private CustomerService customerService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        try {
            billService = new com.pahanaedu.services.impl.BillServiceImpl();
            bookService = new com.pahanaedu.services.impl.BookServiceImpl();
            customerService = new com.pahanaedu.services.impl.CustomerServiceImpl();
            userService = new com.pahanaedu.services.impl.UserServiceImpl();
        } catch (Exception e) {
            throw new ServletException("Failed to initialize services", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath() + (request.getPathInfo() != null ? request.getPathInfo() : "");

        // Check admin authorization
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        switch (path) {
            case "/admin/dashboard":
                showDashboard(request, response);
                break;
            case "/admin/analytics":
                showAnalytics(request, response);
                break;
            case "/admin/reports/sales":
                generateSalesReport(request, response);
                break;
            case "/admin/reports/inventory":
                generateInventoryReport(request, response);
                break;
            case "/admin/reports/customers":
                generateCustomerReport(request, response);
                break;
            case "/admin/system/settings":
                showSystemSettings(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath() + (request.getPathInfo() != null ? request.getPathInfo() : "");

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        switch (path) {
            case "/admin/system/settings":
                updateSystemSettings(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get dashboard statistics
            Map<String, Object> stats = getDashboardStats();
            request.setAttribute("stats", stats);

            // Get recent activities
            List<Bill> recentBills = billService.getRecentBills(10);
            request.setAttribute("recentBills", recentBills);

            // Get low stock books
            List<Book> lowStockBooks = bookService.getLowStockBooks(10);
            request.setAttribute("lowStockBooks", lowStockBooks);

            // Get top selling books this month
            List<Book> topSellingBooks = getTopSellingBooks(5);
            request.setAttribute("topSellingBooks", topSellingBooks);

            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Failed to load dashboard data");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showAnalytics(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Sales analytics
            Map<String, BigDecimal> salesData = getSalesAnalytics();
            request.setAttribute("salesData", salesData);

            // Customer analytics
            Map<String, Integer> customerData = getCustomerAnalytics();
            request.setAttribute("customerData", customerData);

            // Inventory analytics
            Map<String, Integer> inventoryData = getInventoryAnalytics();
            request.setAttribute("inventoryData", inventoryData);

            request.getRequestDispatcher("/WEB-INF/views/admin/analytics.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Failed to load analytics data");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void generateSalesReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            List<Bill> bills = billService.getBillsByDateRange(
                    startDate != null ? LocalDate.parse(startDate) : LocalDate.now().minusDays(30),
                    endDate != null ? LocalDate.parse(endDate) : LocalDate.now()
            );

            // Calculate report data
            BigDecimal totalRevenue = bills.stream()
                    .map(Bill::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int totalOrders = bills.size();
            BigDecimal averageOrderValue = totalOrders > 0 ?
                    totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, BigDecimal.ROUND_HALF_UP) :
                    BigDecimal.ZERO;

            request.setAttribute("bills", bills);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("averageOrderValue", averageOrderValue);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);

            request.getRequestDispatcher("/WEB-INF/views/admin/reports/sales.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Failed to generate sales report");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void generateInventoryReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Book> allBooks = bookService.findAll();
            List<Book> lowStockBooks = allBooks.stream()
                    .filter(book -> book.getQuantity() < 10)
                    .collect(Collectors.toList());
            List<Book> outOfStockBooks = allBooks.stream()
                    .filter(book -> book.getQuantity() == 0)
                    .collect(Collectors.toList());

            int totalBooks = allBooks.size();
            int totalQuantity = allBooks.stream()
                    .mapToInt(Book::getQuantity)
                    .sum();
            BigDecimal totalInventoryValue = allBooks.stream()
                    .map(book -> book.getPrice().multiply(BigDecimal.valueOf(book.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            request.setAttribute("allBooks", allBooks);
            request.setAttribute("lowStockBooks", lowStockBooks);
            request.setAttribute("outOfStockBooks", outOfStockBooks);
            request.setAttribute("totalBooks", totalBooks);
            request.setAttribute("totalQuantity", totalQuantity);
            request.setAttribute("totalInventoryValue", totalInventoryValue);

            request.getRequestDispatcher("/WEB-INF/views/admin/reports/inventory.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Failed to generate inventory report");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void generateCustomerReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Customer> allCustomers = customerService.findAll();
            List<Customer> activeCustomers = allCustomers.stream()
                    .filter(Customer::isActive)
                    .collect(Collectors.toList());

            // Get customer purchase data
            Map<String, BigDecimal> customerPurchases = new HashMap<>();
            List<Bill> allBills = billService.findAll();

            for (Bill bill : allBills) {
                if (bill.getCustomerAccountNumber() != null) {
                    customerPurchases.merge(bill.getCustomerAccountNumber(),
                            bill.getTotalAmount(), BigDecimal::add);
                }
            }

            request.setAttribute("allCustomers", allCustomers);
            request.setAttribute("activeCustomers", activeCustomers);
            request.setAttribute("customerPurchases", customerPurchases);

            request.getRequestDispatcher("/WEB-INF/views/admin/reports/customers.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Failed to generate customer report");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showSystemSettings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Load current system settings
        request.setAttribute("taxRate", "0.10");
        request.setAttribute("lowStockThreshold", "10");
        request.setAttribute("sessionTimeout", "30");

        request.getRequestDispatcher("/WEB-INF/views/admin/system/settings.jsp").forward(request, response);
    }

    private void updateSystemSettings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Update system settings logic here
            String taxRate = request.getParameter("taxRate");
            String lowStockThreshold = request.getParameter("lowStockThreshold");
            String sessionTimeout = request.getParameter("sessionTimeout");

            // Save settings to database or configuration file
            // Implementation depends on your requirements

            response.sendRedirect(request.getContextPath() + "/admin/system/settings?success=settings-updated");

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/system/settings?error=update-failed");
        }
    }

    private Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        try {
            // Total sales today
            LocalDate today = LocalDate.now();
            List<Bill> todayBills = billService.getBillsByDate(today);
            BigDecimal todaySales = todayBills.stream()
                    .map(Bill::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Total books
            List<Book> allBooks = bookService.findAll();
            int totalBooks = allBooks.size();
            int totalInventory = allBooks.stream().mapToInt(Book::getQuantity).sum();

            // Total customers
            List<Customer> allCustomers = customerService.findAll();
            int totalCustomers = allCustomers.size();

            // Orders today
            int ordersToday = todayBills.size();

            // Low stock count
            int lowStockCount = (int) allBooks.stream()
                    .filter(book -> book.getQuantity() < 10)
                    .count();

            stats.put("todaySales", todaySales);
            stats.put("totalBooks", totalBooks);
            stats.put("totalInventory", totalInventory);
            stats.put("totalCustomers", totalCustomers);
            stats.put("ordersToday", ordersToday);
            stats.put("lowStockCount", lowStockCount);

        } catch (Exception e) {
            // Set default values if there's an error
            stats.put("todaySales", BigDecimal.ZERO);
            stats.put("totalBooks", 0);
            stats.put("totalInventory", 0);
            stats.put("totalCustomers", 0);
            stats.put("ordersToday", 0);
            stats.put("lowStockCount", 0);
        }

        return stats;
    }

    private Map<String, BigDecimal> getSalesAnalytics() {
        Map<String, BigDecimal> salesData = new HashMap<>();

        try {
            LocalDate now = LocalDate.now();

            // Last 7 days sales
            for (int i = 6; i >= 0; i--) {
                LocalDate date = now.minusDays(i);
                List<Bill> dayBills = billService.getBillsByDate(date);
                BigDecimal daySales = dayBills.stream()
                        .map(Bill::getTotalAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                salesData.put(date.toString(), daySales);
            }

        } catch (Exception e) {
            // Return empty data if error occurs
        }

        return salesData;
    }

    private Map<String, Integer> getCustomerAnalytics() {
        Map<String, Integer> customerData = new HashMap<>();

        try {
            List<Customer> customers = customerService.findAll();
            int activeCustomers = (int) customers.stream()
                    .filter(Customer::isActive)
                    .count();
            int inactiveCustomers = customers.size() - activeCustomers;

            customerData.put("active", activeCustomers);
            customerData.put("inactive", inactiveCustomers);

        } catch (Exception e) {
            customerData.put("active", 0);
            customerData.put("inactive", 0);
        }

        return customerData;
    }

    private Map<String, Integer> getInventoryAnalytics() {
        Map<String, Integer> inventoryData = new HashMap<>();

        try {
            List<Book> books = bookService.findAll();
            int inStock = (int) books.stream()
                    .filter(book -> book.getQuantity() > 10)
                    .count();
            int lowStock = (int) books.stream()
                    .filter(book -> book.getQuantity() > 0 && book.getQuantity() <= 10)
                    .count();
            int outOfStock = (int) books.stream()
                    .filter(book -> book.getQuantity() == 0)
                    .count();

            inventoryData.put("inStock", inStock);
            inventoryData.put("lowStock", lowStock);
            inventoryData.put("outOfStock", outOfStock);

        } catch (Exception e) {
            inventoryData.put("inStock", 0);
            inventoryData.put("lowStock", 0);
            inventoryData.put("outOfStock", 0);
        }

        return inventoryData;
    }

    private List<Book> getTopSellingBooks(int limit) {
        // This would require additional logic to track book sales
        // For now, return books with highest sales (you'd need to implement this)
        try {
            return bookService.findAll().stream()
                    .limit(limit)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            return user != null && "ADMIN".equals(user.getRole());
        }
        return false;
    }
}