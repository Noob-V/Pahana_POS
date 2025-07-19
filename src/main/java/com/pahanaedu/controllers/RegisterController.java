package com.pahanaedu.controllers;

import com.pahanaedu.entities.User;
import com.pahanaedu.services.UserService;
import com.pahanaedu.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input
        if (fullName == null || fullName.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Check password length
        if (password.length() < 6) {
            request.setAttribute("errorMessage", "Password must be at least 6 characters long");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        try {
            // Check if username already exists
            Optional<User> existingUser = userService.findByUsername(username);
            if (existingUser.isPresent()) {
                request.setAttribute("errorMessage", "Username already exists. Please choose a different one.");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            // Create new user - UserService will handle password hashing
            User newUser = new User();
            newUser.setFullName(fullName.trim());
            newUser.setUsername(username.trim().toLowerCase());
            newUser.setEmail(email.trim().toLowerCase());
            newUser.setPasswordHash(password); // UserService will hash this
            newUser.setRole("USER"); // Default role
            newUser.setActive(true);
            newUser.setCreatedDate(LocalDateTime.now());

            // Save user to database
            User savedUser = userService.save(newUser);

            if (savedUser != null && savedUser.getId() != null) {
                response.sendRedirect(request.getContextPath() + "/login?message=registration-success");
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed due to server error. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}