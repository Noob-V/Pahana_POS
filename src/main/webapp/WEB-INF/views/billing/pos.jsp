<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Point of Sale - PahanaEdu</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <style>
    .book-card { cursor: pointer; transition: all 0.2s; }
    .book-card:hover { transform: translateY(-2px); box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
    .cart-item { border-bottom: 1px solid #eee; padding: 10px 0; }
    .cart-item:last-child { border-bottom: none; }
    .quantity-input { width: 60px; }
  </style>
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
      <i class="fas fa-book"></i> PahanaEdu POS
    </a>
    <div class="navbar-nav ms-auto">
      <a class="nav-link" href="${pageContext.request.contextPath}/books">
        <i class="fas fa-books"></i> Books
      </a>
      <a class="nav-link" href="${pageContext.request.contextPath}/customers">
        <i class="fas fa-users"></i> Customers
      </a>
    </div>
  </div>
</nav>

<div class="container-fluid mt-4">
  <!-- Error/Success Messages -->
  <c:if test="${param.error != null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      <c:choose>
        <c:when test="${param.error == 'insufficient-stock'}">
          <strong><i class="fas fa-exclamation-triangle"></i> Insufficient Stock!</strong><br>
          <c:choose>
            <c:when test="${not empty param.message}">
              ${param.message}
            </c:when>
            <c:otherwise>
              Only ${param.available} books available in inventory, but ${param.requested} requested.
            </c:otherwise>
          </c:choose>
        </c:when>
        <c:when test="${param.error == 'out-of-stock'}">
          <strong><i class="fas fa-times-circle"></i> Out of Stock!</strong><br>
          This book is currently out of stock.
        </c:when>
        <c:when test="${param.error == 'book-not-found'}">
          <strong><i class="fas fa-search"></i> Book Not Found!</strong><br>
          The selected book could not be found in the system.
        </c:when>
        <c:when test="${param.error == 'empty-cart'}">
          <strong><i class="fas fa-shopping-cart"></i> Empty Cart!</strong><br>
          Please add items to cart before checkout.
        </c:when>
        <c:otherwise>
          <strong><i class="fas fa-exclamation-circle"></i> Error!</strong><br>
          ${param.error}
        </c:otherwise>
      </c:choose>
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <c:if test="${param.success != null}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      <c:choose>
        <c:when test="${param.success == 'item-added'}">
          <strong>Success!</strong> Item added to cart.
        </c:when>
        <c:otherwise>
          <strong>Success!</strong> ${param.success}
        </c:otherwise>
      </c:choose>
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <div class="row">
    <!-- Book Selection Panel -->
    <div class="col-md-8">
      <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
          <h5 class="mb-0">Select Books</h5>
          <div class="input-group" style="width: 300px;">
            <input type="text" class="form-control" id="searchBooks" placeholder="Search books...">
            <button class="btn btn-outline-secondary" type="button">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </div>
        <div class="card-body" style="max-height: 600px; overflow-y: auto;">
          <div class="row">
            <c:forEach var="book" items="${books}">
              <div class="col-md-4 mb-3 book-item">
                <div class="card book-card h-100" onclick="addToCart('${book.isbn}', ${book.quantity})">
                  <div class="card-body">
                    <h6 class="card-title">${book.title}</h6>
                    <p class="card-text text-muted small">by ${book.author}</p>
                    <p class="card-text">
                      <strong>$<fmt:formatNumber value="${book.price}" pattern="#,##0.00"/></strong>
                    </p>
                    <p class="card-text small">
                      Stock: <span class="badge ${book.quantity > 0 ? 'bg-success' : 'bg-danger'}">
                        ${book.quantity}
                    </span>
                    </p>
                  </div>
                </div>
              </div>
            </c:forEach>
          </div>
        </div>
      </div>
    </div>

    <!-- Shopping Cart Panel -->
    <div class="col-md-4">
      <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
          <h5 class="mb-0">Shopping Cart</h5>
          <a href="${pageContext.request.contextPath}/billing/pos?clear=true" class="btn btn-sm btn-outline-danger">
            <i class="fas fa-trash"></i> Clear
          </a>
        </div>
        <div class="card-body">
          <div id="cart-items" style="min-height: 300px; max-height: 400px; overflow-y: auto;">
            <c:choose>
              <c:when test="${not empty sessionScope.cart}">
                <c:forEach var="item" items="${sessionScope.cart}">
                  <div class="cart-item" data-isbn="${item.isbn}">
                    <div class="d-flex justify-content-between align-items-start">
                      <div class="flex-grow-1">
                        <h6 class="mb-1">${item.title}</h6>
                        <small class="text-muted">${item.author}</small>
                        <br>
                        <small>$<fmt:formatNumber value="${item.price}" pattern="#,##0.00"/> each</small>
                      </div>
                      <button class="btn btn-sm btn-outline-danger"
                              onclick="removeFromCart('${item.isbn}')">
                        <i class="fas fa-times"></i>
                      </button>
                    </div>
                    <div class="d-flex justify-content-between align-items-center mt-2">
                      <div class="d-flex align-items-center">
                        <button class="btn btn-sm btn-outline-secondary"
                                onclick="updateQuantity('${item.isbn}', ${item.quantity - 1})">-</button>
                        <input type="number" class="form-control quantity-input mx-2"
                               value="${item.quantity}" min="1"
                               onchange="updateQuantity('${item.isbn}', this.value)">
                        <button class="btn btn-sm btn-outline-secondary"
                                onclick="updateQuantity('${item.isbn}', ${item.quantity + 1})">+</button>
                      </div>
                      <strong>$<fmt:formatNumber value="${item.total}" pattern="#,##0.00"/></strong>
                    </div>
                  </div>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <div class="text-center text-muted py-4">
                  <i class="fas fa-shopping-cart fa-3x mb-2"></i>
                  <p>Cart is empty</p>
                </div>
              </c:otherwise>
            </c:choose>
          </div>

          <!-- Cart Total -->
          <div class="border-top pt-3 mt-3">
            <div class="d-flex justify-content-between">
              <strong>Total: </strong>
              <strong>$<fmt:formatNumber value="${cartTotal != null ? cartTotal : 0}" pattern="#,##0.00"/></strong>
            </div>
          </div>

          <!-- Checkout Form -->
          <c:if test="${not empty sessionScope.cart}">
            <form action="${pageContext.request.contextPath}/billing/checkout" method="post" class="mt-3">
              <div class="mb-3">
                <label for="customerAccountNumber" class="form-label">Customer Account</label>
                <input type="text" class="form-control" id="customerAccountNumber"
                       name="customerAccountNumber" placeholder="Enter account number">
              </div>
              <div class="mb-3">
                <label for="paymentMethod" class="form-label">Payment Method</label>
                <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                  <option value="CASH">Cash</option>
                  <option value="CARD">Card</option>
                  <option value="MOBILE">Mobile Payment</option>
                </select>
              </div>
              <div class="mb-3">
                <label for="notes" class="form-label">Notes (Optional)</label>
                <textarea class="form-control" id="notes" name="notes" rows="2"></textarea>
              </div>
              <button type="submit" class="btn btn-success w-100">
                <i class="fas fa-credit-card"></i> Checkout
              </button>
            </form>
          </c:if>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Global variable to store book data for validation
  const bookInventory = {};
  <c:forEach var="book" items="${books}">
  bookInventory['${book.isbn}'] = ${book.quantity};
  </c:forEach>

  function addToCart(isbn, availableStock) {
    if (availableStock <= 0) {
      alert('This book is out of stock!');
      return;
    }

    // Check current cart quantity for this book
    let currentCartQuantity = 0;
    const cartItem = document.querySelector(`[data-isbn="${isbn}"]`);
    if (cartItem) {
      const quantityInput = cartItem.querySelector('input[type="number"]');
      currentCartQuantity = parseInt(quantityInput.value) || 0;
    }

    if (currentCartQuantity >= availableStock) {
      alert(`Cannot add more items. Only ${availableStock} available and you already have ${currentCartQuantity} in cart.`);
      return;
    }

    // Submit form to add item
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '${pageContext.request.contextPath}/billing/add-item';

    const isbnInput = document.createElement('input');
    isbnInput.type = 'hidden';
    isbnInput.name = 'isbn';
    isbnInput.value = isbn;

    const quantityInput = document.createElement('input');
    quantityInput.type = 'hidden';
    quantityInput.name = 'quantity';
    quantityInput.value = '1';

    form.appendChild(isbnInput);
    form.appendChild(quantityInput);
    document.body.appendChild(form);
    form.submit();
  }

  function removeFromCart(isbn) {
    if (confirm('Remove this item from cart?')) {
      window.location.href = '${pageContext.request.contextPath}/billing/remove-item?isbn=' + isbn;
    }
  }

  function updateQuantity(isbn, quantity) {
    quantity = parseInt(quantity);

    if (quantity < 1) {
      removeFromCart(isbn);
      return;
    }

    // Validate against available stock
    const availableStock = bookInventory[isbn];
    if (quantity > availableStock) {
      alert(`Only ${availableStock} books available in stock!`);
      // Reset the input field to current value
      const cartItem = document.querySelector(`[data-isbn="${isbn}"]`);
      if (cartItem) {
        const quantityInput = cartItem.querySelector('input[type="number"]');
        quantityInput.value = Math.min(quantity - 1, availableStock);
      }
      return;
    }

    // Submit form to update quantity
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '${pageContext.request.contextPath}/billing/update-quantity';

    const isbnInput = document.createElement('input');
    isbnInput.type = 'hidden';
    isbnInput.name = 'isbn';
    isbnInput.value = isbn;

    const quantityInput = document.createElement('input');
    quantityInput.type = 'hidden';
    quantityInput.name = 'quantity';
    quantityInput.value = quantity;

    form.appendChild(isbnInput);
    form.appendChild(quantityInput);
    document.body.appendChild(form);
    form.submit();
  }

  // Search functionality
  document.getElementById('searchBooks').addEventListener('input', function() {
    const searchTerm = this.value.toLowerCase();
    const bookItems = document.querySelectorAll('.book-item');

    bookItems.forEach(function(item) {
      const title = item.querySelector('.card-title').textContent.toLowerCase();
      const author = item.querySelector('.text-muted').textContent.toLowerCase();

      if (title.includes(searchTerm) || author.includes(searchTerm)) {
        item.style.display = 'block';
      } else {
        item.style.display = 'none';
      }
    });
  });

  // Auto-dismiss alerts after 5 seconds
  document.addEventListener('DOMContentLoaded', function() {
    setTimeout(function() {
      const alerts = document.querySelectorAll('.alert');
      alerts.forEach(function(alert) {
        const bsAlert = new bootstrap.Alert(alert);
        bsAlert.close();
      });
    }, 5000);
  });
</script>
</body>
</html>