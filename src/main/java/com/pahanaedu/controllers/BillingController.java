package com.pahanaedu.controllers;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.Book;
import com.pahanaedu.entities.CartItem;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.services.BillService;
import com.pahanaedu.services.BookService;
import com.pahanaedu.services.CustomerService;
import com.pahanaedu.services.impl.BillServiceImpl;
import com.pahanaedu.services.impl.BookServiceImpl;
import com.pahanaedu.services.impl.CustomerServiceImpl;

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
        "/billing", "/billing/new", "/billing/pos", "/billing/add-item",
        "/billing/remove-item", "/billing/update-quantity", "/billing/checkout",
        "/billing/receipt"
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

        try {
            switch (action) {
                case "/billing/new":
                    showPOS(request, response);
                    break;
                case "/billing/pos":
                    showPOS(request, response);
                    break;
                case "/billing/remove-item":
                    removeFromCart(request, response);
                    break;
                case "/billing/receipt":
                    showReceipt(request, response);
                    break;
                case "/billing":
                default:
                    listBills(request, response);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/billing/add-item":
                    addToCart(request, response);
                    break;
                case "/billing/update-quantity":
                    updateCartQuantity(request, response);
                    break;
                case "/billing/checkout":
                    checkout(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/billing");
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void listBills(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Bill> bills = billService.findAll();
        request.setAttribute("bills", bills);
        request.getRequestDispatcher("/WEB-INF/views/billing/list.jsp").forward(request, response);
    }

    private void showPOS(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Clear cart if requested
        HttpSession session = request.getSession();
        if (request.getParameter("clear") != null) {
            session.removeAttribute("cart");
        }

        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);

        // Calculate cart total
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            BigDecimal cartTotal = cart.stream()
                    .map(CartItem::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            request.setAttribute("cartTotal", cartTotal);
        }

        request.getRequestDispatcher("/WEB-INF/views/billing/pos.jsp").forward(request, response);
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Optional<Book> bookOpt = bookService.findByIsbn(isbn);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            HttpSession session = request.getSession();
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

            if (cart == null) {
                cart = new ArrayList<>();
            }

            // Check current cart quantity for this item
            int currentCartQuantity = cart.stream()
                    .filter(item -> item.getIsbn().equals(isbn))
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            // Validate total quantity (cart + new quantity) against available stock
            int totalRequestedQuantity = currentCartQuantity + quantity;

            if (totalRequestedQuantity > book.getQuantity()) {
                // Redirect with error message
                response.sendRedirect(request.getContextPath() + "/billing/pos?error=insufficient-stock&isbn=" + isbn + "&available=" + book.getQuantity() + "&requested=" + totalRequestedQuantity);
                return;
            }

            // Check if item already exists in cart
            Optional<CartItem> existingItem = cart.stream()
                    .filter(item -> item.getIsbn().equals(isbn))
                    .findFirst();

            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                CartItem newItem = new CartItem(isbn, book.getTitle(), book.getAuthor(),
                        book.getPrice(), quantity);
                cart.add(newItem);
            }

            session.setAttribute("cart", cart);
            response.sendRedirect(request.getContextPath() + "/billing/pos?success=item-added");
        } else {
            response.sendRedirect(request.getContextPath() + "/billing/pos?error=book-not-found");
        }
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String isbn = request.getParameter("isbn");
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            cart.removeIf(item -> item.getIsbn().equals(isbn));
            session.setAttribute("cart", cart);
        }

        response.sendRedirect(request.getContextPath() + "/billing/pos");
    }

    private void updateCartQuantity(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String isbn = request.getParameter("isbn");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            Optional<CartItem> itemOpt = cart.stream()
                    .filter(item -> item.getIsbn().equals(isbn))
                    .findFirst();

            if (itemOpt.isPresent()) {
                if (quantity > 0) {
                    // Validate against available stock
                    Optional<Book> bookOpt = bookService.findByIsbn(isbn);
                    if (bookOpt.isPresent()) {
                        Book book = bookOpt.get();
                        if (quantity > book.getQuantity()) {
                            response.sendRedirect(request.getContextPath() + "/billing/pos?error=insufficient-stock&isbn=" + isbn + "&available=" + book.getQuantity() + "&requested=" + quantity);
                            return;
                        }
                    }
                    itemOpt.get().setQuantity(quantity);
                } else {
                    cart.removeIf(item -> item.getIsbn().equals(isbn));
                }
                session.setAttribute("cart", cart);
            }
        }

        response.sendRedirect(request.getContextPath() + "/billing/pos");
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/billing/pos?error=empty-cart");
            return;
        }

        // Get form parameters
        String customerAccountNumber = request.getParameter("customerAccountNumber");
        String paymentMethod = request.getParameter("paymentMethod");
        String notes = request.getParameter("notes");

        // Calculate totals
        BigDecimal subtotal = cart.stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal taxAmount = subtotal.multiply(new BigDecimal("0.10")); // 10% tax
        BigDecimal totalAmount = subtotal.add(taxAmount);

        // Create bill
        Bill bill = new Bill();
        bill.setCustomerAccountNumber(customerAccountNumber);
        bill.setUserId(1); // TODO: Get from session
        bill.setBillDate(LocalDateTime.now());
        bill.setSubtotal(subtotal);
        bill.setTaxAmount(taxAmount);
        bill.setDiscountAmount(BigDecimal.ZERO);
        bill.setTotalAmount(totalAmount);
        bill.setPaymentMethod(paymentMethod);
        bill.setPaymentStatus("PAID");
        bill.setNotes(notes);

        // Save bill with items
        Bill savedBill = billService.saveBillWithItems(bill, cart);

        // Clear cart
        session.removeAttribute("cart");

        // Redirect to receipt
        response.sendRedirect(request.getContextPath() + "/billing/receipt?billId=" + savedBill.getBillId());
    }

    private void showReceipt(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String billId = request.getParameter("billId");
        Optional<Bill> billOpt = billService.findById(billId);

        if (billOpt.isPresent()) {
            Bill bill = billOpt.get();

            // Get customer details if available
            if (bill.getCustomerAccountNumber() != null) {
                Optional<Customer> customerOpt = customerService.findByAccountNumber(bill.getCustomerAccountNumber());
                customerOpt.ifPresent(customer -> request.setAttribute("customer", customer));
            }

            request.setAttribute("bill", bill);
            request.getRequestDispatcher("/WEB-INF/views/billing/receipt.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/billing?error=receipt-not-found");
        }
    }
}