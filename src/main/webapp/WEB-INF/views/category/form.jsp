<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${category == null ? 'Add New' : 'Edit'} Category - Pahana Education</title>
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
          <h4 class="mb-0">
            <i class="fas fa-${category == null ? 'plus' : 'edit'}"></i>
            ${category == null ? 'Add New' : 'Edit'} Category
          </h4>
        </div>
        <div class="card-body">
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
              <i class="fas fa-exclamation-circle"></i> ${errorMessage}
            </div>
          </c:if>

          <form method="post" action="${pageContext.request.contextPath}/categories">
            <input type="hidden" name="action" value="${category == null ? 'create' : 'update'}">
            <c:if test="${category != null}">
              <input type="hidden" name="id" value="${category.id}">
            </c:if>

            <div class="mb-3">
              <label for="name" class="form-label">Category Name <span class="text-danger">*</span></label>
              <input type="text" id="name" name="name" class="form-control"
                     value="${category != null ? category.name : ''}" required>
              <div class="form-text">Enter a descriptive name for this category</div>
            </div>

            <div class="mb-3">
              <label for="description" class="form-label">Description</label>
              <textarea id="description" name="description" class="form-control" rows="4"
                        placeholder="Provide a detailed description of this category">${category != null ? category.description : ''}</textarea>
              <div class="form-text">A good description helps users understand what belongs in this category</div>
            </div>

            <div class="mb-3">
              <div class="form-check">
                <input type="checkbox" id="active" name="active" class="form-check-input"
                ${category == null || category.active ? 'checked' : ''}>
                <label for="active" class="form-check-label">Active</label>
                <div class="form-text">Active categories appear in searches and can be assigned to books</div>
              </div>
            </div>

            <div class="alert alert-info">
              <i class="fas fa-info-circle"></i>
              <strong>Note:</strong> Categories help organize and classify educational materials in the system.
            </div>

            <div class="d-flex justify-content-between">
              <a href="${pageContext.request.contextPath}/categories" class="btn btn-secondary">
                <i class="fas fa-times"></i> Cancel
              </a>
              <button type="submit" class="btn btn-primary">
                <i class="fas fa-save"></i> ${category == null ? 'Create' : 'Update'} Category
              </button>
            </div>
          </form>
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