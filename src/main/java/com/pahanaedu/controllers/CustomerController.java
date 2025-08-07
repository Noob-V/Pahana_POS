package com.pahanaedu.controllers;

import com.pahanaedu.dto.CustomerDTO;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.services.CustomerService;
import com.pahanaedu.services.impl.CustomerServiceImpl;
import com.pahanaedu.entities.Bill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CustomerController", urlPatterns = {
        "/customers", "/customers/add", "/customers/edit", "/customers/delete",
        "/customers/search", "/customers/bills", "/customers/quick"
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
            case "/customers/bills":
                showCustomerBills(request, response);
                break;
            case "/customers/quick":
                showQuickCustomerForm(request, response);
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
            case "/customers/quick":
                createQuickCustomer(request, response);
                break;
            default:
                listCustomers(request, response);
                break;
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<CustomerDTO> customers = customerService.findAllDTOs();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/WEB-INF/views/customers/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading customers: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showCustomerBills(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accountNumber = request.getParameter("accountNumber");
            if (accountNumber == null || accountNumber.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/customers?error=invalid-account-number");
                return;
            }

            Optional<Customer> customerOpt = customerService.findByAccountNumber(accountNumber);
            if (!customerOpt.isPresent()) {
                response.sendRedirect(request.getContextPath() + "/customers?error=customer-not-found");
                return;
            }

            List<Bill> bills = customerService.findBillsByCustomer(accountNumber);

            request.setAttribute("customer", customerOpt.get());
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("/WEB-INF/views/customers/bills.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading customer bills: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/customers/add.jsp").forward(request, response);
    }

    private void showQuickCustomerForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/customers/quick.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accountNumber = request.getParameter("accountNumber");
            CustomerDTO customer = customerService.findCustomerDTOByAccountNumber(accountNumber);

            if (customer != null) {
                request.setAttribute("customer", customer);
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
            CustomerDTO customerDTO = createCustomerDTOFromRequest(request);
            customerService.updateDTO(customerDTO);
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
            if (query == null || query.trim().isEmpty()) {
                listCustomers(request, response);
                return;
            }

            List<CustomerDTO> customers = customerService.searchCustomerDTOsByName(query);
            request.setAttribute("customers", customers);
            request.setAttribute("searchQuery", query);
            request.getRequestDispatcher("/WEB-INF/views/customers/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error searching customers: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    // Updated to use CustomerFactory through service
    private Customer createCustomerFromRequest(HttpServletRequest request) {
        String accountNumber = request.getParameter("accountNumber");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        // Build full address from separate components
        StringBuilder fullAddress = new StringBuilder();
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postalCode");

        if (address != null && !address.trim().isEmpty()) {
            fullAddress.append(address);
        }
        if (city != null && !city.trim().isEmpty()) {
            if (fullAddress.length() > 0) fullAddress.append(", ");
            fullAddress.append(city);
        }
        if (postalCode != null && !postalCode.trim().isEmpty()) {
            if (fullAddress.length() > 0) fullAddress.append(" ");
            fullAddress.append(postalCode);
        }

        return customerService.createCustomerFromRequest(
                accountNumber, name, fullAddress.toString(), phone, email
        );
    }

    private CustomerDTO createCustomerDTOFromRequest(HttpServletRequest request) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAccountNumber(request.getParameter("accountNumber"));
        customerDTO.setName(request.getParameter("name"));
        customerDTO.setTelephone(request.getParameter("phone"));
        customerDTO.setAddress(request.getParameter("address"));
        customerDTO.setEmail(request.getParameter("email"));
        customerDTO.setPhoneNumber(request.getParameter("phone"));

        String activeStr = request.getParameter("active");
        if (activeStr != null) {
            customerDTO.setActive(Boolean.parseBoolean(activeStr));
        } else {
            customerDTO.setActive(true);
        }

        return customerDTO;
    }

    private void createQuickCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String customerType = request.getParameter("customerType");
            Customer customer;

            if ("premium".equals(customerType)) {
                customer = customerService.createPremiumCustomer(
                        request.getParameter("name"),
                        request.getParameter("phone"),
                        request.getParameter("email"),
                        request.getParameter("address")
                );
            } else {
                customer = customerService.createSampleCustomer(
                        request.getParameter("name"),
                        request.getParameter("phone"),
                        request.getParameter("email")
                );
            }

            customerService.save(customer);
            response.sendRedirect(request.getContextPath() + "/customers?message=quick-customer-created");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/customers?error=" + e.getMessage());
        }
    }
}