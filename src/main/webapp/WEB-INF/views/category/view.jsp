<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Category: ${category.name} - Pahana Education</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
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
      <a href="${pageContext.request.contextPath}/categories" class="btn btn-outline-light me-2">
        <i class="fas fa-arrow-left"></i> Back to Categories
      </a>
      <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">
        <i class="fas fa-sign-out-alt"></i> Logout
      </a>
    </div>
  </div>
</nav>

<div class="container mt-4">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card">
        <div class="card-header bg-primary text-white">
          <h4 class="mb-0"><i class="fas fa-folder"></i> Category Details</h4>
        </div>
        <div class="card-body">
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
              <i class="fas fa-exclamation-circle"></i> ${errorMessage}
            </div>
          </c:if>

          <h2 class="mb-4">${category.name}</h2>

          <div class="mb-3 row">
            <label class="col-sm-3 fw-bold">Description:</label>
            <div class="col-sm-9">
              ${category.description}
            </div>
          </div>

          <div class="mb-3 row">
            <label class="col-sm-3 fw-bold">Status:</label>
            <div class="col-sm-9">
                            <span class="badge ${category.active ? 'bg-success' : 'bg-danger'}">
                                <i class="fas ${category.active ? 'fa-check-circle' : 'fa-times-circle'}"></i>
                                ${category.active ? 'Active' : 'Inactive'}
                            </span>
            </div>
          </div>

          <div class="mb-3 row">
            <label class="col-sm-3 fw-bold">Created Date:</label>
            <div class="col-sm-9">
              <fmt:parseDate value="${category.createdDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
              <fmt:formatDate value="${parsedDateTime}" pattern="yyyy-MM-dd HH:mm" />
            </div>
          </div>

          <div class="alert alert-info mt-4">
            <i class="fas fa-info-circle"></i>
            <strong>Note:</strong> This category can be used to organize books and educational materials in the system.
          </div>

          <div class="d-flex justify-content-between mt-4">
            <a href="${pageContext.request.contextPath}/categories" class="btn btn-secondary">
              <i class="fas fa-list"></i> Back to Categories
            </a>
            <div>
              <a href="${pageContext.request.contextPath}/categories?action=edit&id=${category.id}" class="btn btn-primary me-2">
                <i class="fas fa-edit"></i> Edit
              </a>
              <a href="${pageContext.request.contextPath}/categories?action=delete&id=${category.id}"
                 class="btn btn-danger"
                 onclick="return confirm('Are you sure you want to delete this category?')">
                <i class="fas fa-trash"></i> Delete
              </a>
            </div>
          </div>
        </div>
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