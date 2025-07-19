// src/main/java/com/pahanaedu/services/impl/UserServiceImpl.java
package com.pahanaedu.services.impl;

import com.pahanaedu.dao.UserDAO;
import com.pahanaedu.dao.impl.UserDAOImpl;
import com.pahanaedu.entities.User;
import com.pahanaedu.services.UserService;
import com.pahanaedu.utils.PasswordUtils;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
                userDAO.updateLastLogin(username);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User save(User user) {
        validateUser(user);

        // Check if username already exists
        if (userDAO.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Hash password
        user.setPasswordHash(PasswordUtils.hashPassword(user.getPasswordHash()));
        return userDAO.save(user);
    }

    @Override
    public User update(User user) {
        validateUser(user);
        return userDAO.update(user);
    }

    @Override
    public boolean deleteById(Integer id) {
        return userDAO.deleteById(id);
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordUtils.verifyPassword(oldPassword, user.getPasswordHash())) {
                user.setPasswordHash(PasswordUtils.hashPassword(newPassword));
                userDAO.update(user);
                return true;
            }
        }
        return false;
    }

    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required");
        }
    }
}