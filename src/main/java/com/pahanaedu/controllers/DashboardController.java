package com.pahanaedu.controllers;

import com.pahanaedu.services.DashboardService;
import com.pahanaedu.services.impl.DashboardServiceImpl;
import com.pahanaedu.dto.BillDTO;
import com.pahanaedu.dto.BookDTO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    private DashboardService dashboardService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.dashboardService = new DashboardServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Check user role and redirect accordingly
        String userRole = (String) session.getAttribute("userRole");
        if ("STAFF".equals(userRole)) {
            response.sendRedirect(request.getContextPath() + "/staff/dashboard");
            return;
        }

        try {
            // Get dashboard statistics
            Map<String, Object> dashboardStats = dashboardService.getDashboardStats();
            request.setAttribute("dashboardStats", dashboardStats);

            // Get recent bills (last 5)
            List<BillDTO> recentBills = dashboardService.getRecentBills(5);
            request.setAttribute("recentBills", recentBills);

            // Get low stock books
            List<BookDTO> lowStockBooks = dashboardService.getLowStockBooks();
            request.setAttribute("lowStockBooks", lowStockBooks);

            // Get top selling books (last 5)
            List<BookDTO> topSellingBooks = dashboardService.getTopSellingBooks(5);
            request.setAttribute("topSellingBooks", topSellingBooks);

            // Get chart data as JSON for frontend
            Map<String, BigDecimal> weeklyRevenue = dashboardService.getWeeklyRevenue();
            Map<String, Integer> monthlySales = dashboardService.getMonthlySalesCount();
            Map<String, BigDecimal> revenueByPayment = dashboardService.getRevenueByPaymentMethod();

            Gson gson = new Gson();
            request.setAttribute("weeklyRevenueJson", gson.toJson(weeklyRevenue));
            request.setAttribute("monthlySalesJson", gson.toJson(monthlySales));
            request.setAttribute("revenueByPaymentJson", gson.toJson(revenueByPayment));

        } catch (Exception e) {
            // Log error and set default values
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load dashboard data");
        }

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}