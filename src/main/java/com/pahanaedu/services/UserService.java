// src/main/java/com/pahanaedu/services/UserService.java
package com.pahanaedu.services;

import com.pahanaedu.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String username, String password);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Integer id);
    List<User> findAll();
    User save(User user);
    User update(User user);
    boolean deleteById(Integer id);
    boolean changePassword(String username, String oldPassword, String newPassword);
}