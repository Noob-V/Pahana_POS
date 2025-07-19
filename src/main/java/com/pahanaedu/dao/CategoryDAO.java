// src/main/java/com/pahanaedu/dao/CategoryDAO.java
package com.pahanaedu.dao;

import com.pahanaedu.entities.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    Optional<Category> findById(Integer id);
    List<Category> findAll();
    List<Category> findActive();
    Category save(Category category);
    Category update(Category category);
    boolean deleteById(Integer id);
}