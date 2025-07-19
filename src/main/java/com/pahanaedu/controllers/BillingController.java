package com.pahanaedu.controllers;

import com.pahanaedu.entities.*;
import com.pahanaedu.services.*;
import com.pahanaedu.services.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "BillingController", urlPatterns = {
        "/billing", "/billing/new", "/billing/add-item", "/billing/remove-item",
        "/billing/checkout", "/billing/receipt"
})
public class BillingController extends HttpServlet {
    private BillService billService;
    private BookService bookService;
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        super.init();
        billService = new BillServiceImpl();
        bookService = new BookServiceImpl();
        customerService = new CustomerServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/billing/new":
                startNewBill(request, response);
                break;
            case "/billing/remove-item":
                removeItem(request, response);
                break;
            case "/billing/receipt":
                showReceipt(request, response);
                break;
            case "/billing":
            default:
                showPOS(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/billing/add-item":
                addItemToCart(request, response);
                break;
            case "/billing/checkout":
                processCheckout(request, response);
                break;
            default:
                showPOS(request, response);
                break;
        }
    }

    private void showPOS(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute("cart", cart);
            }

            // Get available books
            List<Book> books = bookService.findAll();
            BigDecimal total = calculateCartTotal(cart);

            request.setAttribute("books", books);
            request.setAttribute("cartTotal", total);
            request.getRequestDispatcher("/WEB-INF/views/billing/pos.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading POS: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/billing/pos.jsp").forward(request, response);
        }
    }

    private void addItemToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute("cart", cart);
            }

            String isbn = request.getParameter("isbn");
            String quantityStr = request.getParameter("quantity");
            int quantity = quantityStr != null ? Integer.parseInt(quantityStr) : 1;

            Optional<Book> bookOpt = bookService.findByIsbn(isbn);
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();

                // Check if item already in cart
                boolean found = false;
                for (CartItem item : cart) {
                    if (item.getIsbn().equals(isbn)) {
                        item.setQuantity(item.getQuantity() + quantity);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    CartItem cartItem = new CartItem(isbn, book.getTitle(), book.getAuthor(),
                            book.getPrice(), quantity);
                    cart.add(cartItem);
                }
            }

            response.sendRedirect(request.getContextPath() + "/billing");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/billing?error=" + e.getMessage());
        }
    }

    private void processCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

            if (cart == null || cart.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/billing?error=empty-cart");
                return;
            }

            String customerAccountNumber = request.getParameter("customerAccountNumber");
            String paymentMethod = request.getParameter("paymentMethod");
            String notes = request.getParameter("notes");

            // Create bill
            Bill bill = new Bill();
            bill.setCustomerAccountNumber(customerAccountNumber);
            bill.setUserId(1); // Get from session in real app
            bill.setSubtotal(calculateCartTotal(cart));
            bill.setTaxAmount(BigDecimal.ZERO);
            bill.setTotalAmount(bill.getSubtotal());
            bill.setPaymentMethod(paymentMethod != null ? paymentMethod : "CASH");
            bill.setNotes(notes);

            // Convert cart items to bill items
            List<BillItem> billItems = new ArrayList<>();
            for (CartItem cartItem : cart) {
                BillItem billItem = new BillItem();
                billItem.setIsbn(cartItem.getIsbn());
                billItem.setBookTitle(cartItem.getTitle());
                billItem.setQuantity(cartItem.getQuantity());
                billItem.setUnitPrice(cartItem.getPrice());
                billItem.setTotalPrice(cartItem.getTotal());
                billItems.add(billItem);
            }
            bill.setBillItems(billItems);

            // Save bill
            Bill savedBill = billService.save(bill);

            // Clear cart
            session.removeAttribute("cart");

            response.sendRedirect(request.getContextPath() + "/billing/receipt?billId=" + savedBill.getBillId());
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/billing?error=" + e.getMessage());
        }
    }

    private void startNewBill(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        response.sendRedirect(request.getContextPath() + "/billing");
    }

    private void removeItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            String isbn = request.getParameter("isbn");
            cart.removeIf(item -> item.getIsbn().equals(isbn));
        }

        response.sendRedirect(request.getContextPath() + "/billing");
    }

    private void showReceipt(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String billIdParam = request.getParameter("billId");
            if (billIdParam != null && !billIdParam.trim().isEmpty()) {
                // Convert String to Integer
                Integer billId = Integer.parseInt(billIdParam);
                Optional<Bill> billOpt = billService.findById(billId);
                if (billOpt.isPresent()) {
                    request.setAttribute("bill", billOpt.get());
                    request.getRequestDispatcher("/WEB-INF/views/billing/receipt.jsp").forward(request, response);
                    return;
                }
            }
            response.sendRedirect(request.getContextPath() + "/billing?error=receipt-not-found");
        } catch (NumberFormatException e) {
            // Handle invalid bill ID format
            response.sendRedirect(request.getContextPath() + "/billing?error=invalid-bill-id");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/billing?error=" + e.getMessage());
        }
    }

    private BigDecimal calculateCartTotal(List<CartItem> cart) {
        return cart.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}