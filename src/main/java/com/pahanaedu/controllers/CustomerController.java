// src/main/java/com/pahanaedu/controllers/CustomerController.java
package com.pahanaedu.controllers;

import com.pahanaedu.entities.Customer;
import com.pahanaedu.services.CustomerService;
import com.pahanaedu.services.impl.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CustomerController", urlPatterns = {
        "/customers", "/customers/add", "/customers/edit", "/customers/delete", "/customers/search"
})
public class CustomerController extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        super.init();
        customerService = new CustomerServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/customers/add":
                showAddForm(request, response);
                break;
            case "/customers/edit":
                showEditForm(request, response);
                break;
            case "/customers/delete":
                deleteCustomer(request, response);
                break;
            case "/customers/search":
                searchCustomers(request, response);
                break;
            case "/customers":
            default:
                listCustomers(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/customers/add":
                addCustomer(request, response);
                break;
            case "/customers/edit":
                updateCustomer(request, response);
                break;
            default:
                listCustomers(request, response);
                break;
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Customer> customers = customerService.findAll();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/WEB-INF/views/customers/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading customers: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/customers/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accountNumber = request.getParameter("accountNumber");
            Optional<Customer> customerOpt = customerService.findByAccountNumber(accountNumber);

            if (customerOpt.isPresent()) {
                request.setAttribute("customer", customerOpt.get());
                request.getRequestDispatcher("/WEB-INF/views/customers/edit.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/customers?error=customer-not-found");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading customer: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Customer customer = createCustomerFromRequest(request);
            customerService.save(customer);
            response.sendRedirect(request.getContextPath() + "/customers?message=customer-added");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error adding customer: " + e.getMessage());
            showAddForm(request, response);
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Customer customer = createCustomerFromRequest(request);
            customerService.update(customer);
            response.sendRedirect(request.getContextPath() + "/customers?message=customer-updated");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error updating customer: " + e.getMessage());
            showEditForm(request, response);
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String accountNumber = request.getParameter("accountNumber");
            customerService.deleteByAccountNumber(accountNumber);
            response.sendRedirect(request.getContextPath() + "/customers?message=customer-deleted");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/customers?error=" + e.getMessage());
        }
    }

    private void searchCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String query = request.getParameter("q");
            List<Customer> customers = customerService.searchByName(query);

            request.setAttribute("customers", customers);
            request.setAttribute("searchQuery", query);
            request.getRequestDispatcher("/WEB-INF/views/customers/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error searching customers: " + e.getMessage());
            listCustomers(request, response);
        }
    }

    private Customer createCustomerFromRequest(HttpServletRequest request) {
        Customer customer = new Customer();
        customer.setAccountNumber(request.getParameter("accountNumber"));
        customer.setName(request.getParameter("name"));
        customer.setEmail(request.getParameter("email"));

        // Handle phone parameter
        String phone = request.getParameter("phone");
        if (phone != null && !phone.trim().isEmpty()) {
            customer.setContactNumber(phone); // Using setContactNumber instead of setPhone
        }

        // Handle address parameter
        String address = request.getParameter("address");
        if (address != null && !address.trim().isEmpty()) {
            customer.setAddress(address);
        }

        // Handle city parameter - using address field to store city info
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postalCode");

        // Combine address, city, and postal code into a single address field
        StringBuilder fullAddress = new StringBuilder();
        if (address != null && !address.trim().isEmpty()) {
            fullAddress.append(address);
        }
        if (city != null && !city.trim().isEmpty()) {
            if (fullAddress.length() > 0) {
                fullAddress.append(", ");
            }
            fullAddress.append(city);
        }
        if (postalCode != null && !postalCode.trim().isEmpty()) {
            if (fullAddress.length() > 0) {
                fullAddress.append(" ");
            }
            fullAddress.append(postalCode);
        }

        if (fullAddress.length() > 0) {
            customer.setAddress(fullAddress.toString());
        }

        // Set default active status
        customer.setActive(true);

        return customer;
    }
}