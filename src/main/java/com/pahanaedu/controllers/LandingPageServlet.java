package com.pahanaedu.controllers;

import com.pahanaedu.entities.Book;
import com.pahanaedu.entities.Category;
import com.pahanaedu.services.BookService;
import com.pahanaedu.services.CategoryService;
import com.pahanaedu.services.impl.BookServiceImpl;
import com.pahanaedu.services.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LandingPageServlet", urlPatterns = {"/", "/home", "/landing"})
public class LandingPageServlet extends HttpServlet {
    private BookService bookService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        bookService = new BookServiceImpl();
        categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get recent books (limit to 6 for display)
            List<Book> recentBooks = bookService.findAll().stream()
                    .limit(6)
                    .collect(java.util.stream.Collectors.toList());

            // Get low stock books (limit to 5)
            List<Book> lowStockBooks = bookService.getLowStockBooks(5);

            // Get categories
            List<Category> categories = categoryService.findActive();

            // Get total counts for statistics
            int totalBooks = bookService.findAll().size();
            int totalCategories = categories.size();
            int lowStockCount = bookService.findLowStockBooks().size();

            // Set attributes for JSP
            request.setAttribute("recentBooks", recentBooks);
            request.setAttribute("lowStockBooks", lowStockBooks);
            request.setAttribute("categories", categories);
            request.setAttribute("totalBooks", totalBooks);
            request.setAttribute("totalCategories", totalCategories);
            request.setAttribute("lowStockCount", lowStockCount);

            // Forward to landing page
            request.getRequestDispatcher("/WEB-INF/views/landingPage.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}