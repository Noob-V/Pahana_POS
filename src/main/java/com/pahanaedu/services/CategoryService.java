// src/main/java/com/pahanaedu/services/CategoryService.java
package com.pahanaedu.services;

import com.pahanaedu.entities.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Integer id);
    List<Category> findAll();
    List<Category> findActive();
    Category save(Category category);
    Category update(Category category);
    boolean deleteById(Integer id);
}