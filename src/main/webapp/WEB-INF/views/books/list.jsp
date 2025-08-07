<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>All Books - Pahana Education</title>
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
                Hello, ${sessionScope.currentUser.fullName}!
            </span>
      <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">
        <i class="fas fa-sign-out-alt"></i> Logout
      </a>
    </div>
  </div>
</nav>

<div class="container mt-4">
  <!-- Header -->
  <div class="d-flex justify-content-between align-items-center mb-4">
    <div>
      <h2><i class="fas fa-book"></i> Books Management</h2>
      <p class="text-muted mb-0">Manage your library inventory</p>
    </div>
    <div>
      <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary me-2">
        <i class="fas fa-plus"></i> Add New Book
      </a>
      <a href="${pageContext.request.contextPath}/books/google-search" class="btn btn-success">
        <i class="fas fa-download"></i> Import from Google
      </a>
    </div>
  </div>

  <!-- Search Form -->
  <div class="row mb-4">
    <div class="col-md-8">
      <form method="GET" action="${pageContext.request.contextPath}/books/search" class="d-flex">
        <input type="text" class="form-control me-2" name="q" placeholder="Search books by title, author, or ISBN..."
               value="${searchQuery}" id="searchInput" autocomplete="off">
        <button type="submit" class="btn btn-outline-primary">
          <i class="fas fa-search"></i> Search
        </button>
      </form>
      <div id="searchSuggestions" class="position-relative">
        <ul id="suggestionsList" class="list-group position-absolute w-100 d-none" style="z-index: 1000;"></ul>
      </div>
    </div>
    <div class="col-md-4 text-end">
      <c:if test="${not empty searchQuery}">
        <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-secondary">
          <i class="fas fa-times"></i> Clear Search
        </a>
      </c:if>
    </div>
  </div>

  <!-- Messages -->
  <c:if test="${param.message == 'book-added'}">
    <div class="alert alert-success alert-dismissible fade show">
      <i class="fas fa-check-circle"></i> Book added successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <c:if test="${param.message == 'book-updated'}">
    <div class="alert alert-success alert-dismissible fade show">
      <i class="fas fa-check-circle"></i> Book updated successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <c:if test="${param.message == 'book-deleted'}">
    <div class="alert alert-success alert-dismissible fade show">
      <i class="fas fa-check-circle"></i> Book deleted successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <c:if test="${param.message == 'book-imported'}">
    <div class="alert alert-success alert-dismissible fade show">
      <i class="fas fa-check-circle"></i> Book imported from Google Books successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <c:if test="${not empty param.error}">
    <div class="alert alert-danger alert-dismissible fade show">
      <i class="fas fa-exclamation-circle"></i> Error: ${param.error}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <!-- Stats Cards -->
  <div class="row mb-4">
    <div class="col-md-4">
      <div class="card bg-primary text-white">
        <div class="card-body text-center">
          <h3><i class="fas fa-book"></i> ${books.size()}</h3>
          <p class="mb-0">Total Books</p>
        </div>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card bg-success text-white">
        <div class="card-body text-center">
          <h3><i class="fas fa-check-circle"></i>
            <c:set var="activeCount" value="0" />
            <c:forEach items="${books}" var="book">
              <c:if test="${book.active}">
                <c:set var="activeCount" value="${activeCount + 1}" />
              </c:if>
            </c:forEach>
            ${activeCount}
          </h3>
          <p class="mb-0">Active Books</p>
        </div>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card bg-warning text-white">
        <div class="card-body text-center">
          <h3><i class="fas fa-exclamation-triangle"></i> ${lowStockBooks.size()}</h3>
          <p class="mb-0">Low Stock</p>
        </div>
      </div>
    </div>
  </div>

  <!-- Books Table -->
  <div class="card">
    <div class="card-header">
      <h5 class="mb-0">
        <i class="fas fa-list"></i> Books Inventory
        <c:if test="${not empty searchQuery}">
          - Search Results for "${searchQuery}"
        </c:if>
      </h5>
    </div>
    <div class="card-body">
      <c:choose>
        <c:when test="${not empty books}">
          <div class="table-responsive">
            <table class="table table-hover">
              <thead class="table-dark">
              <tr>
                <th>ISBN</th>
                <th>Title</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="book" items="${books}">
                <tr>
                  <td class="font-monospace">${book.isbn}</td>
                  <td>
                    <div class="d-flex align-items-center">
                      <c:choose>
                        <c:when test="${not empty book.imageUrl}">
                          <img src="${book.imageUrl}" alt="${book.title}"
                               class="me-3 rounded" style="width: 50px; height: 60px; object-fit: cover;">
                        </c:when>
                        <c:otherwise>
                          <div class="me-3 d-flex align-items-center justify-content-center bg-light rounded"
                               style="width: 50px; height: 60px;">
                            <i class="fas fa-book text-muted"></i>
                          </div>
                        </c:otherwise>
                      </c:choose>
                      <div>
                        <strong>${book.title}</strong>
                        <c:if test="${not empty book.categoryName}">
                          <br><small class="text-muted">${book.categoryName}</small>
                        </c:if>
                      </div>
                    </div>
                  </td>
                  <td>${book.author}</td>
                  <td>${book.publisher}</td>
                  <td>
                    <c:if test="${not empty book.price and book.price > 0}">
                      <fmt:formatNumber value="${book.price}" type="currency"/>
                    </c:if>
                    <c:if test="${empty book.price or book.price <= 0}">
                      <span class="text-muted">Not set</span>
                    </c:if>
                  </td>
                  <td>
                    <span class="badge ${book.quantity <= book.minStockLevel ? 'bg-danger' : 'bg-success'}">${book.quantity}</span>
                    <c:if test="${book.quantity <= book.minStockLevel}">
                      <small class="text-danger d-block">Low Stock!</small>
                    </c:if>
                  </td>
                  <td>
        <span class="badge ${book.active ? 'bg-success' : 'bg-secondary'}">
            ${book.active ? 'Active' : 'Inactive'}
        </span>
                  </td>
                  <td>
                    <div class="btn-group btn-group-sm">
                      <a href="${pageContext.request.contextPath}/books/edit?isbn=${book.isbn}"
                         class="btn btn-outline-primary" title="Edit">
                        <i class="fas fa-edit"></i>
                      </a>
                      <a href="${pageContext.request.contextPath}/books/delete?isbn=${book.isbn}"
                         class="btn btn-outline-danger" title="Delete"
                         onclick="return confirm('Are you sure you want to delete this book?')">
                        <i class="fas fa-trash"></i>
                      </a>
                    </div>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
        </c:when>
        <c:otherwise>
          <div class="text-center py-5">
            <i class="fas fa-book fa-3x text-muted mb-3"></i>
            <h5>No books found</h5>
            <p class="text-muted">
              <c:choose>
                <c:when test="${not empty searchQuery}">
                  No books match your search criteria.
                </c:when>
                <c:otherwise>
                  Start by adding your first book to the library.
                </c:otherwise>
              </c:choose>
            </p>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>

  <!-- Low Stock Section -->
  <c:if test="${not empty lowStockBooks}">
    <div class="row mt-4">
      <div class="col-12">
        <div class="card border-warning">
          <div class="card-header bg-warning text-dark">
            <h5 class="mb-0"><i class="fas fa-exclamation-triangle"></i> Low Stock Alert</h5>
          </div>
          <div class="card-body">
            <div class="row">
              <c:forEach var="book" items="${lowStockBooks}">
                <div class="col-md-6 col-lg-4 mb-3">
                  <div class="card border-warning">
                    <div class="card-body">
                      <h6 class="card-title">${book.title}</h6>
                      <p class="card-text text-muted">${book.author}</p>
                      <p class="text-danger mb-2">
                        <strong>Only ${book.quantity} left!</strong>
                        <br><small>Min required: ${book.minStockLevel}</small>
                      </p>
                      <a href="${pageContext.request.contextPath}/books/edit?isbn=${book.isbn}"
                         class="btn btn-sm btn-warning">Update Stock</a>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
        </div>
      </div>
    </div>
  </c:if>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Confirm Delete</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to delete the book "<span id="bookTitle"></span>"?</p>
        <p class="text-muted">This action cannot be undone.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <a href="#" id="confirmDeleteBtn" class="btn btn-danger">Delete</a>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  function confirmDelete(isbn, title) {
    document.getElementById('bookTitle').textContent = title;
    document.getElementById('confirmDeleteBtn').href =
            '${pageContext.request.contextPath}/books/delete?isbn=' + encodeURIComponent(isbn);

    var deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    deleteModal.show();
  }

  // Search suggestions functionality
  document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    const suggestionsList = document.getElementById('suggestionsList');
    let debounceTimer;

    searchInput.addEventListener('input', function() {
      clearTimeout(debounceTimer);
      const query = this.value.trim();

      if (query.length < 2) {
        suggestionsList.classList.add('d-none');
        return;
      }

      debounceTimer = setTimeout(() => {
        fetchSuggestions(query);
      }, 300);
    });

    function fetchSuggestions(query) {
      fetch(`${pageContext.request.contextPath}/books/suggestions?q=` + encodeURIComponent(query))
              .then(response => response.json())
              .then(data => {
                displaySuggestions(data);
              })
              .catch(error => {
                console.error('Error fetching suggestions:', error);
                suggestionsList.classList.add('d-none');
              });
    }

    function displaySuggestions(suggestions) {
      suggestionsList.innerHTML = '';

      if (suggestions.length === 0) {
        suggestionsList.classList.add('d-none');
        return;
      }

      suggestions.forEach(suggestion => {
        const li = document.createElement('li');
        li.className = 'list-group-item list-group-item-action';
        li.style.cursor = 'pointer';
        li.innerHTML = `
                <strong>${suggestion.title}</strong><br>
                <small class="text-muted">by ${suggestion.author}</small>
            `;

        li.addEventListener('click', function() {
          searchInput.value = suggestion.title;
          suggestionsList.classList.add('d-none');
          searchInput.form.submit();
        });

        suggestionsList.appendChild(li);
      });

      suggestionsList.classList.remove('d-none');
    }

    // Hide suggestions when clicking outside
    document.addEventListener('click', function(event) {
      if (!searchInput.contains(event.target) && !suggestionsList.contains(event.target)) {
        suggestionsList.classList.add('d-none');
      }
    });
  });

  // Auto-dismiss alerts after 5 seconds
  setTimeout(function() {
    var alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
      if (alert.classList.contains('show')) {
        var bsAlert = new bootstrap.Alert(alert);
        bsAlert.close();
      }
    });
  }, 5000);
</script>
</body>
</html>