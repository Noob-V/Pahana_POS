<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Pahana Education Bookshop</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <style>
    .hero-section {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      padding: 100px 0;
    }
    .book-card {
      transition: transform 0.3s;
      height: 100%;
    }
    .book-card:hover {
      transform: translateY(-5px);
    }
    .book-image {
      height: 250px;
      object-fit: cover;
      background-color: #f8f9fa;
    }
    .stats-card {
      background: linear-gradient(135deg, #667eea, #764ba2);
      color: white;
      border-radius: 15px;
    }
  </style>
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
  <div class="container">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
      <i class="fas fa-book"></i> Pahana Education
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link active" href="${pageContext.request.contextPath}/">Home</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="categoriesDropdown" role="button" data-bs-toggle="dropdown">
            Categories
          </a>
          <ul class="dropdown-menu">
            <c:forEach var="category" items="${categories}">
              <li><a class="dropdown-item" href="#category-${category.id}">${category.name}</a></li>
            </c:forEach>
          </ul>
        </li>
      </ul>
      <div class="navbar-nav">
        <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-light me-2">
          <i class="fas fa-sign-in-alt"></i> Admin Login
        </a>
      </div>
    </div>
  </div>
</nav>

<!-- Hero Section -->
<section class="hero-section text-center">
  <div class="container">
    <h1 class="display-4 fw-bold mb-4">Welcome to Pahana Education Bookshop</h1>
    <p class="lead mb-4">Discover knowledge, inspire learning, transform lives</p>
    <div class="row justify-content-center">
      <div class="col-md-3 col-sm-6 mb-3">
        <div class="stats-card p-4 text-center">
          <i class="fas fa-book fa-2x mb-2"></i>
          <h3>${totalBooks}</h3>
          <p class="mb-0">Books Available</p>
        </div>
      </div>
      <div class="col-md-3 col-sm-6 mb-3">
        <div class="stats-card p-4 text-center">
          <i class="fas fa-list fa-2x mb-2"></i>
          <h3>${totalCategories}</h3>
          <p class="mb-0">Categories</p>
        </div>
      </div>
    </div>
  </div>
</section>

<!-- Recent Books Section -->
<section class="py-5">
  <div class="container">
    <h2 class="text-center mb-5">Featured Books</h2>

    <c:if test="${not empty recentBooks}">
      <div class="row">
        <c:forEach var="book" items="${recentBooks}" varStatus="status">
          <div class="col-lg-4 col-md-6 mb-4">
            <div class="card book-card shadow">
              <div class="text-center p-3" style="background-color: #f8f9fa;">
                <c:choose>
                  <c:when test="${not empty book.imageUrl}">
                    <img src="${book.imageUrl}" alt="${book.title}"
                         class="img-fluid book-image" style="max-height: 200px; max-width: 150px;">
                  </c:when>
                  <c:otherwise>
                    <div class="d-flex align-items-center justify-content-center book-image">
                      <i class="fas fa-book fa-3x text-muted"></i>
                    </div>
                  </c:otherwise>
                </c:choose>
              </div>
              <div class="card-body">
                <h5 class="card-title">${book.title}</h5>
                <p class="card-text">
                  <small class="text-muted">by ${book.author}</small><br>
                  <c:if test="${not empty book.publisher}">
                    <small class="text-muted">Published by ${book.publisher}</small><br>
                  </c:if>
                  <c:if test="${not empty book.categoryName}">
                    <span class="badge bg-primary">${book.categoryName}</span><br>
                  </c:if>
                </p>
                <c:if test="${not empty book.description}">
                  <p class="card-text">
                    <small>
                        ${book.description.length() > 100 ?
                                book.description.substring(0, 100).concat("...") :
                                book.description}
                    </small>
                  </p>
                </c:if>
                <div class="d-flex justify-content-between align-items-center">
                  <c:if test="${book.price != null && book.price > 0}">
                    <h6 class="text-success mb-0">
                      <fmt:formatNumber value="${book.price}" type="currency" currencySymbol="$"/>
                    </h6>
                  </c:if>
                  <c:choose>
                    <c:when test="${book.quantity > 0}">
                      <span class="badge bg-success">In Stock (${book.quantity})</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge bg-danger">Out of Stock</span>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:if>

    <c:if test="${empty recentBooks}">
      <div class="text-center py-5">
        <i class="fas fa-book fa-3x text-muted mb-3"></i>
        <h4>No Books Available</h4>
        <p class="text-muted">Check back soon for new arrivals!</p>
      </div>
    </c:if>
  </div>
</section>

<!-- Categories Section -->
<c:if test="${not empty categories}">
  <section class="py-5 bg-light">
    <div class="container">
      <h2 class="text-center mb-5">Browse by Categories</h2>
      <div class="row">
        <c:forEach var="category" items="${categories}">
          <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
            <div class="card h-100 text-center">
              <div class="card-body">
                <i class="fas fa-folder fa-3x text-primary mb-3"></i>
                <h5 class="card-title">${category.name}</h5>
                <c:if test="${not empty category.description}">
                  <p class="card-text text-muted">${category.description}</p>
                </c:if>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>
  </section>
</c:if>

<!-- Low Stock Alert (Admin Preview) -->
<c:if test="${not empty lowStockBooks}">
  <section class="py-3 bg-warning">
    <div class="container">
      <div class="row align-items-center">
        <div class="col-md-8">
          <h6 class="mb-0">
            <i class="fas fa-exclamation-triangle"></i>
              ${lowStockCount} books are running low on stock
          </h6>
        </div>
        <div class="col-md-4 text-end">
          <a href="${pageContext.request.contextPath}/login" class="btn btn-dark btn-sm">
            Admin Login to Manage
          </a>
        </div>
      </div>
    </div>
  </section>
</c:if>

<!-- Footer -->
<footer class="bg-dark text-light py-4 mt-5">
  <div class="container">
    <div class="row">
      <div class="col-md-6">
        <h5><i class="fas fa-book"></i> Pahana Education Bookshop</h5>
        <p class="mb-0">Your trusted partner in education and learning.</p>
      </div>
      <div class="col-md-6 text-md-end">
        <p class="mb-0">
          <i class="fas fa-envelope"></i> info@pahanaedu.com |
          <i class="fas fa-phone"></i> +1 (555) 123-4567
        </p>
      </div>
    </div>
    <hr class="my-3">
    <div class="text-center">
      <small>&copy; 2024 Pahana Education Bookshop. All rights reserved.</small>
    </div>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>