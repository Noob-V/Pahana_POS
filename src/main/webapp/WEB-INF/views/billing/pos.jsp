<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Point of Sale - PahanaEdu</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style>
    :root {
      --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      --secondary-gradient: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
      --success-gradient: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
      --card-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
      --card-hover-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
      --border-radius: 15px;
    }

    * {
      font-family: 'Inter', sans-serif;
    }

    body {
      background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
      min-height: 100vh;
    }

    /* Enhanced Navbar */
    .navbar {
      background: var(--primary-gradient) !important;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      padding: 1rem 0;
    }

    .navbar-brand {
      font-weight: 700;
      font-size: 1.5rem;
      text-decoration: none;
    }

    .navbar-brand:hover {
      transform: scale(1.05);
      transition: all 0.3s ease;
    }

    .nav-link {
      font-weight: 500;
      margin: 0 0.5rem;
      border-radius: 25px;
      padding: 0.5rem 1rem !important;
      transition: all 0.3s ease;
      text-decoration: none;
    }

    .nav-link:hover {
      background: rgba(255, 255, 255, 0.2);
      transform: translateY(-2px);
    }

    /* Beautiful Cards */
    .card {
      border: none;
      border-radius: var(--border-radius);
      box-shadow: var(--card-shadow);
      transition: all 0.3s ease;
      background: rgba(255, 255, 255, 0.95);
      backdrop-filter: blur(10px);
    }

    .card:hover {
      transform: translateY(-2px);
      box-shadow: var(--card-hover-shadow);
    }

    .card-header {
      background: var(--primary-gradient);
      color: white;
      border-radius: var(--border-radius) var(--border-radius) 0 0 !important;
      border: none;
      font-weight: 600;
      padding: 1.5rem;
    }

    /* Book Cards */
    .book-card {
      cursor: pointer;
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
      border-radius: var(--border-radius);
      overflow: hidden;
      background: linear-gradient(145deg, #ffffff 0%, #f8f9ff 100%);
      position: relative;
    }

    .book-card::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      height: 4px;
      background: var(--success-gradient);
      transform: scaleX(0);
      transition: transform 0.3s ease;
    }

    .book-card:hover::before {
      transform: scaleX(1);
    }

    .book-card:hover {
      transform: translateY(-8px) scale(1.02);
      box-shadow: 0 25px 50px rgba(0, 0, 0, 0.15);
    }

    .book-card .card-body {
      padding: 1.5rem;
    }

    .book-card .card-title {
      font-weight: 600;
      color: #2d3748;
      margin-bottom: 0.5rem;
      line-height: 1.3;
    }

    /* Price Styling */
    .price-tag {
      background: var(--success-gradient);
      color: white;
      padding: 0.3rem 0.8rem;
      border-radius: 20px;
      font-weight: 600;
      font-size: 0.9rem;
      display: inline-block;
    }

    /* Stock Badge */
    .stock-badge {
      position: absolute;
      top: 10px;
      right: 10px;
      padding: 0.3rem 0.6rem;
      border-radius: 15px;
      font-size: 0.75rem;
      font-weight: 600;
    }

    /* Enhanced Cart */
    .cart-item {
      border: none;
      background: linear-gradient(145deg, #f8f9ff 0%, #ffffff 100%);
      border-radius: 10px;
      margin-bottom: 1rem;
      padding: 1rem;
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
      transition: all 0.3s ease;
    }

    .cart-item:hover {
      transform: translateX(5px);
      box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
    }

    /* Quantity Controls */
    .quantity-controls {
      background: #f8f9fa;
      border-radius: 25px;
      padding: 0.2rem;
      display: inline-flex;
      align-items: center;
    }

    .quantity-btn {
      width: 30px;
      height: 30px;
      border-radius: 50%;
      border: none;
      background: var(--primary-gradient);
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: all 0.3s ease;
    }

    .quantity-btn:hover {
      transform: scale(1.1);
      box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
    }

    .quantity-input {
      width: 50px;
      border: none;
      background: transparent;
      text-align: center;
      font-weight: 600;
    }

    /* Enhanced Alerts */
    .alert {
      border: none;
      border-radius: var(--border-radius);
      box-shadow: var(--card-shadow);
      backdrop-filter: blur(10px);
    }

    .alert-danger {
      background: linear-gradient(135deg, rgba(220, 53, 69, 0.1) 0%, rgba(255, 193, 203, 0.1) 100%);
      border-left: 4px solid #dc3545;
    }

    .alert-success {
      background: linear-gradient(135deg, rgba(25, 135, 84, 0.1) 0%, rgba(209, 231, 221, 0.1) 100%);
      border-left: 4px solid #198754;
    }

    /* Search Box */
    .search-container {
      position: relative;
      max-width: 400px;
    }

    .search-container input {
      border-radius: 25px;
      border: 2px solid transparent;
      background: rgba(255, 255, 255, 0.9);
      padding: 0.75rem 1rem 0.75rem 3rem;
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;
    }

    .search-container input:focus {
      border-color: #667eea;
      box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
    }

    .search-container .search-icon {
      position: absolute;
      left: 1rem;
      top: 50%;
      transform: translateY(-50%);
      color: #6c757d;
    }

    /* Checkout Button */
    .checkout-btn {
      background: var(--secondary-gradient);
      border: none;
      border-radius: 25px;
      padding: 1rem 2rem;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      transition: all 0.3s ease;
      box-shadow: 0 8px 25px rgba(245, 87, 108, 0.3);
    }

    .checkout-btn:hover {
      transform: translateY(-3px);
      box-shadow: 0 15px 35px rgba(245, 87, 108, 0.4);
    }

    /* Form Styling */
    .form-control, .form-select {
      border-radius: 10px;
      border: 2px solid #e9ecef;
      transition: all 0.3s ease;
    }

    .form-control:focus, .form-select:focus {
      border-color: #667eea;
      box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
    }

    /* Empty Cart */
    .empty-cart {
      text-align: center;
      color: #6c757d;
      padding: 3rem 1rem;
    }

    .empty-cart i {
      opacity: 0.3;
      margin-bottom: 1rem;
    }

    /* Loading Animation */
    @keyframes pulse {
      0% { opacity: 1; }
      50% { opacity: 0.5; }
      100% { opacity: 1; }
    }

    .loading {
      animation: pulse 1.5s infinite;
    }

    /* Mobile Responsive */
    @media (max-width: 768px) {
      .container-fluid {
        padding: 1rem;
      }

      .book-card {
        margin-bottom: 1rem;
      }

      .card-header {
        padding: 1rem;
      }
    }
  </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
      <i class="fas fa-book-open"></i> PahanaEdu POS
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <div class="navbar-nav ms-auto">
        <a class="nav-link" href="${pageContext.request.contextPath}/books">
          <i class="fas fa-book"></i> Books
        </a>
        <a class="nav-link" href="${pageContext.request.contextPath}/customers">
          <i class="fas fa-users"></i> Customers
        </a>
        <a class="nav-link" href="${pageContext.request.contextPath}/billing">
          <i class="fas fa-receipt"></i> Bills
        </a>
      </div>
    </div>
  </div>
</nav>

<div class="container-fluid mt-4">
  <!-- Enhanced Error/Success Messages -->
  <c:if test="${param.error != null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      <div class="d-flex align-items-center">
        <i class="fas fa-exclamation-triangle me-2"></i>
        <div>
          <strong>Oops!</strong>
          <c:choose>
            <c:when test="${param.error == 'insufficient-stock'}">
              Only ${param.available} books available, but ${param.requested} requested.
            </c:when>
            <c:when test="${param.error == 'out-of-stock'}">
              This book is currently out of stock.
            </c:when>
            <c:when test="${param.error == 'book-not-found'}">
              The selected book could not be found.
            </c:when>
            <c:when test="${param.error == 'empty-cart'}">
              Please add items to cart before checkout.
            </c:when>
            <c:otherwise>
              ${param.error}
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <c:if test="${param.success != null}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      <div class="d-flex align-items-center">
        <i class="fas fa-check-circle me-2"></i>
        <div>
          <strong>Success!</strong>
          <c:choose>
            <c:when test="${param.success == 'item-added'}">
              Item added to cart successfully.
            </c:when>
            <c:otherwise>
              ${param.success}
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <div class="row g-4">
    <!-- Enhanced Book Selection Panel -->
    <div class="col-lg-8">
      <div class="card">
        <div class="card-header d-flex flex-column flex-md-row justify-content-between align-items-center">
          <h5 class="mb-2 mb-md-0">
            <i class="fas fa-shopping-bag me-2"></i>Select Books
          </h5>
          <div class="search-container">
            <i class="fas fa-search search-icon"></i>
            <input type="text" class="form-control" id="searchBooks" placeholder="Search books, authors...">
          </div>
        </div>
        <div class="card-body" style="max-height: 70vh; overflow-y: auto;">
          <div class="row g-3">
            <c:forEach var="book" items="${books}">
              <div class="col-xl-4 col-lg-6 col-md-6 book-item">
                <div class="card book-card h-100" onclick="addToCart('${book.isbn}', ${book.quantity})">
                  <c:choose>
                    <c:when test="${book.quantity > 10}">
                      <span class="stock-badge bg-success">In Stock</span>
                    </c:when>
                    <c:when test="${book.quantity > 0}">
                      <span class="stock-badge bg-warning">Low Stock</span>
                    </c:when>
                    <c:otherwise>
                      <span class="stock-badge bg-danger">Out of Stock</span>
                    </c:otherwise>
                  </c:choose>

                  <div class="card-body">
                    <h6 class="card-title">${book.title}</h6>
                    <p class="text-muted small mb-2">by ${book.author}</p>
                    <div class="d-flex justify-content-between align-items-center">
                      <span class="price-tag">
                        <fmt:formatNumber value="${book.price}" type="currency" currencySymbol="$"/>
                      </span>
                      <small class="text-muted">Stock: ${book.quantity}</small>
                    </div>
                  </div>
                </div>
              </div>
            </c:forEach>
          </div>
        </div>
      </div>
    </div>

    <!-- Enhanced Shopping Cart Panel -->
    <div class="col-lg-4">
      <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
          <h5 class="mb-0">
            <i class="fas fa-shopping-cart me-2"></i>Shopping Cart
          </h5>
          <a href="${pageContext.request.contextPath}/billing/pos?clear=true"
             class="btn btn-sm btn-outline-light"
             onclick="return confirm('Clear all items from cart?')">
            <i class="fas fa-trash me-1"></i> Clear
          </a>
        </div>
        <div class="card-body p-3">
          <div id="cart-items" style="min-height: 300px; max-height: 50vh; overflow-y: auto;">
            <c:choose>
              <c:when test="${not empty sessionScope.cart}">
                <c:forEach var="item" items="${sessionScope.cart}">
                  <div class="cart-item" data-isbn="${item.isbn}">
                    <div class="d-flex justify-content-between align-items-start mb-2">
                      <div class="flex-grow-1">
                        <h6 class="mb-1">${item.title}</h6>
                        <small class="text-muted">${item.author}</small>
                      </div>
                      <button type="button" class="btn btn-sm btn-outline-danger"
                              onclick="removeFromCart('${item.isbn}')" title="Remove item">
                        <i class="fas fa-times"></i>
                      </button>
                    </div>

                    <div class="d-flex justify-content-between align-items-center">
                      <div class="quantity-controls">
                        <button type="button" class="quantity-btn"
                                onclick="updateQuantity('${item.isbn}', ${item.quantity - 1})">
                          <i class="fas fa-minus"></i>
                        </button>
                        <input type="number" class="quantity-input" value="${item.quantity}"
                               min="1" onchange="updateQuantity('${item.isbn}', this.value)">
                        <button type="button" class="quantity-btn"
                                onclick="updateQuantity('${item.isbn}', ${item.quantity + 1})">
                          <i class="fas fa-plus"></i>
                        </button>
                      </div>
                      <div class="fw-semibold">
                        <fmt:formatNumber value="${item.total}" type="currency" currencySymbol="$"/>
                      </div>
                    </div>
                  </div>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <div class="empty-cart">
                  <i class="fas fa-shopping-cart fa-4x"></i>
                  <h5 class="mt-3">Your cart is empty</h5>
                  <p class="text-muted">Click on books to add them to your cart</p>
                </div>
              </c:otherwise>
            </c:choose>
          </div>

          <!-- Enhanced Cart Total -->
          <c:if test="${not empty sessionScope.cart}">
            <div class="border-top pt-3 mt-3">
              <div class="row mb-2">
                <div class="col">Subtotal:</div>
                <div class="col-auto">
                  <c:set var="subtotal" value="0"/>
                  <c:forEach var="item" items="${sessionScope.cart}">
                    <c:set var="subtotal" value="${subtotal + item.total}"/>
                  </c:forEach>
                  <fmt:formatNumber value="${subtotal}" type="currency" currencySymbol="$"/>
                </div>
              </div>
              <div class="row mb-2">
                <div class="col">Tax (10%):</div>
                <div class="col-auto">
                  <c:set var="tax" value="${subtotal * 0.1}"/>
                  <fmt:formatNumber value="${tax}" type="currency" currencySymbol="$"/>
                </div>
              </div>
              <div class="row border-top pt-2">
                <div class="col"><strong>Total:</strong></div>
                <div class="col-auto">
                  <strong>
                    <fmt:formatNumber value="${subtotal + tax}" type="currency" currencySymbol="$"/>
                  </strong>
                </div>
              </div>
            </div>

            <!-- Enhanced Checkout Form -->
            <form action="${pageContext.request.contextPath}/billing/checkout" method="post" class="mt-4">
              <div class="mb-3">
                <label for="customerAccountNumber" class="form-label fw-semibold">
                  <i class="fas fa-user me-1"></i> Customer Account (Optional)
                </label>
                <input type="text" class="form-control" id="customerAccountNumber"
                       name="customerAccountNumber" placeholder="Enter customer account number">
              </div>

              <div class="mb-3">
                <label for="paymentMethod" class="form-label fw-semibold">
                  <i class="fas fa-credit-card me-1"></i> Payment Method
                </label>
                <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                  <option value="">Select payment method</option>
                  <option value="CASH">Cash</option>
                  <option value="CARD">Credit/Debit Card</option>
                  <option value="DIGITAL">Digital Payment</option>
                </select>
              </div>

              <div class="mb-4">
                <label for="notes" class="form-label fw-semibold">
                  <i class="fas fa-sticky-note me-1"></i> Notes (Optional)
                </label>
                <textarea class="form-control" id="notes" name="notes" rows="2"
                          placeholder="Add any notes for this order"></textarea>
              </div>

              <button type="submit" class="btn checkout-btn w-100">
                <i class="fas fa-credit-card me-2"></i>
                Complete Purchase
              </button>
            </form>
          </c:if>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Book inventory mapping
  const bookInventory = {};
  <c:forEach var="book" items="${books}">
  bookInventory['${book.isbn}'] = ${book.quantity};
  </c:forEach>

  function addToCart(isbn, availableStock) {
    if (availableStock <= 0) {
      showAlert('This book is out of stock!', 'danger');
      return;
    }

    let currentCartQuantity = 0;
    const cartItem = document.querySelector(`[data-isbn="${isbn}"]`);
    if (cartItem) {
      const quantityInput = cartItem.querySelector('input[type="number"]');
      currentCartQuantity = parseInt(quantityInput.value) || 0;
    }

    if (currentCartQuantity >= availableStock) {
      showAlert(`Cannot add more items. Only ${availableStock} available and you already have ${currentCartQuantity} in cart.`, 'warning');
      return;
    }

    // Add loading state
    const bookCard = event.currentTarget;
    bookCard.classList.add('loading');

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

    const availableStock = bookInventory[isbn];
    if (quantity > availableStock) {
      showAlert(`Only ${availableStock} books available in stock!`, 'danger');
      const cartItem = document.querySelector(`[data-isbn="${isbn}"]`);
      if (cartItem) {
        const quantityInput = cartItem.querySelector('input[type="number"]');
        quantityInput.value = Math.min(quantity - 1, availableStock);
      }
      return;
    }

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

  // Enhanced search functionality
  document.getElementById('searchBooks').addEventListener('input', function() {
    const searchTerm = this.value.toLowerCase();
    const bookItems = document.querySelectorAll('.book-item');
    let visibleCount = 0;

    bookItems.forEach(function(item) {
      const title = item.querySelector('.card-title').textContent.toLowerCase();
      const author = item.querySelector('.text-muted').textContent.toLowerCase();

      if (title.includes(searchTerm) || author.includes(searchTerm)) {
        item.style.display = 'block';
        visibleCount++;
      } else {
        item.style.display = 'none';
      }
    });

    // Show no results message if needed
    const noResults = document.getElementById('no-results');
    if (noResults) noResults.remove();

    if (visibleCount === 0 && searchTerm.length > 0) {
      const container = document.querySelector('.card-body .row');
      const noResultsDiv = document.createElement('div');
      noResultsDiv.id = 'no-results';
      noResultsDiv.className = 'col-12 text-center py-5';
      noResultsDiv.innerHTML = `
        <i class="fas fa-search fa-3x text-muted mb-3"></i>
        <h5>No books found</h5>
        <p class="text-muted">Try searching with different keywords</p>
      `;
      container.appendChild(noResultsDiv);
    }
  });

  function showAlert(message, type) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;

    // Fix the icon selection logic
    let iconClass = 'info-circle';
    if (type === 'danger') {
      iconClass = 'exclamation-triangle';
    } else if (type === 'warning') {
      iconClass = 'exclamation-triangle';
    } else if (type === 'success') {
      iconClass = 'check-circle';
    }

    alertDiv.innerHTML = `
      <i class="fas fa-${iconClass} me-2"></i>
      ${message}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;

    const container = document.querySelector('.container-fluid');
    container.insertBefore(alertDiv, container.firstChild);

    setTimeout(() => {
      alertDiv.remove();
    }, 5000);
  }

  // Auto-dismiss alerts
  document.addEventListener('DOMContentLoaded', function() {
    setTimeout(function() {
      const alerts = document.querySelectorAll('.alert');
      alerts.forEach(function(alert) {
        const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
        bsAlert.close();
      });
    }, 5000);
  });
</script>
</body>
</html>