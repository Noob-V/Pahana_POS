package com.pahanaedu.controllers;

import com.pahanaedu.dto.CategoryDTO;
import com.pahanaedu.entities.Category;
import com.pahanaedu.mapper.CategoryMapper;
import com.pahanaedu.services.CategoryService;
import com.pahanaedu.services.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/categories/*")
public class CategoryController extends HttpServlet {
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listCategories(request, response);
                break;
            case "view":
                viewCategory(request, response);
                break;
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCategory(request, response);
                break;
            default:
                listCategories(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                createCategory(request, response);
                break;
            case "update":
                updateCategory(request, response);
                break;
            default:
                listCategories(request, response);
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        request.setAttribute("categories", categoryDTOs);
        request.getRequestDispatcher("/WEB-INF/views/category/list.jsp").forward(request, response);
    }

    private void viewCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Optional<Category> categoryOpt = categoryService.findById(id);

            if (categoryOpt.isPresent()) {
                CategoryDTO categoryDTO = CategoryMapper.toDTO(categoryOpt.get());
                request.setAttribute("category", categoryDTO);
                request.getRequestDispatcher("/WEB-INF/views/category/view.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Category not found");
                listCategories(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid category ID");
            listCategories(request, response);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Optional<Category> categoryOpt = categoryService.findById(id);

            if (categoryOpt.isPresent()) {
                CategoryDTO categoryDTO = CategoryMapper.toDTO(categoryOpt.get());
                request.setAttribute("category", categoryDTO);
                request.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Category not found");
                listCategories(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid category ID");
            listCategories(request, response);
        }
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(request.getParameter("name"));
            categoryDTO.setDescription(request.getParameter("description"));
            categoryDTO.setActive(request.getParameter("active") != null);
            categoryDTO.setCreatedDate(LocalDateTime.now());

            Category category = CategoryMapper.toEntity(categoryDTO);
            categoryService.save(category);

            request.setAttribute("successMessage", "Category created successfully");
            listCategories(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error creating category: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(request, response);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Optional<Category> categoryOpt = categoryService.findById(id);

            if (categoryOpt.isPresent()) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(id);
                categoryDTO.setName(request.getParameter("name"));
                categoryDTO.setDescription(request.getParameter("description"));
                categoryDTO.setActive(request.getParameter("active") != null);
                categoryDTO.setCreatedDate(categoryOpt.get().getCreatedDate());

                Category category = CategoryMapper.toEntity(categoryDTO);
                categoryService.update(category);

                request.setAttribute("successMessage", "Category updated successfully");
                listCategories(request, response);
            } else {
                request.setAttribute("errorMessage", "Category not found");
                listCategories(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid category ID");
            listCategories(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error updating category: " + e.getMessage());
            showEditForm(request, response);
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean deleted = categoryService.deleteById(id);

            if (deleted) {
                request.setAttribute("successMessage", "Category deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete category");
            }

            listCategories(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid category ID");
            listCategories(request, response);
        }
    }
}