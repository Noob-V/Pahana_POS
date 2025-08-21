<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Categories - Pahana Education</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .category-active { color: #198754; }
        .category-inactive { color: #dc3545; }
        .table-actions { min-width: 150px; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">
            <i class="fas fa-graduation-cap"></i> Pahana Education
        </a>
        <div class="navbar-nav ms-auto">
            <span class="navbar-text me-3">
                Hello, ${sessionScope.currentUser.fullName}! (${sessionScope.userRole})
            </span>
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline-light me-2">
                <i class="fas fa-home"></i> Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1><i class="fas fa-list"></i> Book Categories</h1>
        <a href="${pageContext.request.contextPath}/categories?action=new" class="btn btn-primary">
            <i class="fas fa-plus"></i> Add New Category
        </a>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> ${successMessage}
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            <i class="fas fa-exclamation-circle"></i> ${errorMessage}
        </div>
    </c:if>

    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Created Date</th>
                        <th class="table-actions">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>${category.id}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/categories?action=view&id=${category.id}" class="text-decoration-none">
                                    <strong>${category.name}</strong>
                                </a>
                            </td>
                            <td>${category.description}</td>
                            <td>
                                <span class="badge ${category.active ? 'bg-success' : 'bg-danger'}">
                                    <i class="fas ${category.active ? 'fa-check-circle' : 'fa-times-circle'}"></i>
                                    ${category.active ? 'Active' : 'Inactive'}
                                </span>
                            </td>
                            <td>
                                <fmt:parseDate value="${category.createdDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                                <fmt:formatDate value="${parsedDateTime}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/categories?action=edit&id=${category.id}" class="btn btn-sm btn-primary">
                                    <i class="fas fa-edit"></i> Edit
                                </a>
                                <a href="${pageContext.request.contextPath}/categories?action=delete&id=${category.id}"
                                   class="btn btn-sm btn-danger"
                                   onclick="return confirm('Are you sure you want to delete this category?')">
                                    <i class="fas fa-trash"></i> Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty categories}">
                        <tr>
                            <td colspan="6" class="text-center py-4">
                                <i class="fas fa-info-circle text-info"></i> No categories found.
                                <a href="${pageContext.request.contextPath}/categories?action=new">Add your first category</a>.
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Footer - included directly in this JSP -->
<footer class="bg-dark text-white mt-5 py-3">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <p class="mb-0">&copy; 2025 Pahana Education. All rights reserved.</p>
            </div>
            <div class="col-md-6 text-md-end">
                <p class="mb-0">
                    <i class="fas fa-book"></i> Knowledge is power
                </p>
            </div>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>