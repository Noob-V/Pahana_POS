// src/main/java/com/pahanaedu/services/impl/CategoryServiceImpl.java
package com.pahanaedu.services.impl;

import com.pahanaedu.dao.CategoryDAO;
import com.pahanaedu.dao.impl.CategoryDAOImpl;
import com.pahanaedu.entities.Category;
import com.pahanaedu.services.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryDAO.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public List<Category> findActive() {
        return categoryDAO.findActive();
    }

    @Override
    public Category save(Category category) {
        validateCategory(category);
        return categoryDAO.save(category);
    }

    @Override
    public Category update(Category category) {
        validateCategory(category);
        return categoryDAO.update(category);
    }

    @Override
    public boolean deleteById(Integer id) {
        return categoryDAO.deleteById(id);
    }

    private void validateCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name is required");
        }
    }
}