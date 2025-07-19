// src/main/java/com/pahanaedu/dao/UserDAO.java
package com.pahanaedu.dao;

import com.pahanaedu.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Integer id);
    List<User> findAll();
    User save(User user);
    User update(User user);
    boolean deleteById(Integer id);
    boolean updateLastLogin(String username);
}