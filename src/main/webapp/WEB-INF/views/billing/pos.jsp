<!-- src/main/webapp/WEB-INF/views/billing/pos.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Point of Sale"/>
<jsp:include page="../layout.jsp"/>

<div class="row">
  <!-- Left Panel - Product Selection -->
  <div class="col-md-8">
    <h3><i class="fas fa-shopping-cart"></i> Point of Sale</h3>

    <!-- Search Books -->
    <div class="card mb-4">
      <div class="card-header">
        <h6><i class="fas fa-search"></i> Find Books</h6>
      </div>
      <div class="card-body">
        <form id="bookSearchForm" class="row">
          <div class="col-md-8">
            <input type="text" id="bookSearch" class="form-control"
                   placeholder="Search by ISBN, title, or author...">
          </div>
          <div class="col-md-4">
            <button type="submit" class="btn btn-primary w-100">
              <i class="fas fa-search"></i> Search
            </button>
          </div>
        </form>
        <div id="searchResults" class="mt-3"></div>
      </div>
    </div>

    <!-- Quick Access Books -->
    <div class="card">
      <div class="card-header">
        <h6><i class="fas fa-bolt"></i> Quick Access</h6>
      </div>
      <div class="card-body">
        <div class="row">
          <c:forEach var="book" items="${recentBooks}">
            <div class="col-md-6 col-lg-4 mb-3">
              <div class="card book-card" onclick="addToCart('${book.isbn}', '${book.title}', ${book.price})">
                <div class="card-body">
                  <h6 class="card-title text-truncate">${book.title}</h6>
                  <p class="card-text small text-muted">${book.author}</p>
                  <div class="d-flex justify-content-between align-items-center">
                    <span class="fw-bold">$<fmt:formatNumber value="${book.price}" pattern="0.00"/></span>
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

  <!-- Right Panel - Cart & Checkout -->
  <div class="col-md-4">
    <div class="card sticky-top">
      <div class="card-header">
        <h6><i class="fas fa-shopping-cart"></i> Current Sale</h6>
      </div>
      <div class="card-body">
        <!-- Cart Items -->
        <div id="cartItems" class="mb-3">
          <c:choose>
            <c:when test="${empty sessionScope.cart}">
              <div class="text-center text-muted py-4">
                <i class="fas fa-shopping-cart fa-2x mb-2"></i>
                <p>Cart is empty</p>
              </div>
            </c:when>
            <c:otherwise>
              <c:forEach var="item" items="${sessionScope.cart}">
                <div class="d-flex justify-content-between align-items-center mb-2 cart-item">
                  <div class="flex-grow-1">
                    <small class="fw-bold">${item.title}</small><br>
                    <small class="text-muted">
                      $<fmt:formatNumber value="${item.price}" pattern="0.00"/> × ${item.quantity}
                    </small>
                  </div>
                  <div class="text-end">
                    <div class="fw-bold">
                      $<fmt:formatNumber value="${item.price * item.quantity}" pattern="0.00"/>
                    </div>
                    <button class="btn btn-sm btn-outline-danger"
                            onclick="removeFromCart('${item.isbn}')">
                      <i class="fas fa-times"></i>
                    </button>
                  </div>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>

        <!-- Totals -->
        <div class="border-top pt-3">
          <div class="d-flex justify-content-between">
            <span>Subtotal:</span>
            <span id="subtotal">$<fmt:formatNumber value="${cartTotal}" pattern="0.00"/></span>
          </div>
          <div class="d-flex justify-content-between">
            <span>Tax (10%):</span>
            <span id="tax">$<fmt:formatNumber value="${cartTotal * 0.10}" pattern="0.00"/></span>
          </div>
          <hr>
          <div class="d-flex justify-content-between fw-bold h5">
            <span>Total:</span>
            <span id="total">$<fmt:formatNumber value="${cartTotal * 1.10}" pattern="0.00"/></span>
          </div>
        </div>

        <!-- Checkout Form -->
        <c:if test="${not empty sessionScope.cart}">
          <form method="post" action="${pageContext.request.contextPath}/billing/checkout">
            <div class="mb-3">
              <label for="customerSearch" class="form-label">Customer</label>
              <div class="input-group">
                <input type="text" id="customerSearch" class="form-control"
                       placeholder="Search customer...">
                <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#customerModal">
                  <i class="fas fa-plus"></i>
                </button>
              </div>
              <input type="hidden" name="customerAccountNumber" id="customerAccountNumber">
            </div>

            <div class="mb-3">
              <label for="paymentMethod" class="form-label">Payment Method</label>
              <select class="form-select" name="paymentMethod" required>
                <option value="CASH">Cash</option>
                <option value="CARD">Card</option>
                <option value="MOBILE">Mobile Payment</option>
              </select>
            </div>

            <button type="submit" class="btn btn-success w-100">
              <i class="fas fa-credit-card"></i> Complete Sale
            </button>
          </form>
        </c:if>

        <button class="btn btn-outline-danger w-100 mt-2" onclick="clearCart()">
          <i class="fas fa-trash"></i> Clear Cart
        </button>
      </div>
    </div>
  </div>
</div>

<style>
  .book-card {
    cursor: pointer;
    transition: transform 0.2s;
  }
  .book-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  }
  .cart-item {
    padding: 8px;
    border-radius: 4px;
    background-color: #f8f9fa;
  }
</style>

<script>
  function addToCart(isbn, title, price) {
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '${pageContext.request.contextPath}/billing/add-item';
    form.style.display = 'none';

    const isbnInput = document.createElement('input');
    isbnInput.name = 'isbn';
    isbnInput.value = isbn;

    const quantityInput = document.createElement('input');
    quantityInput.name = 'quantity';
    quantityInput.value = '1';

    form.appendChild(isbnInput);
    form.appendChild(quantityInput);
    document.body.appendChild(form);
    form.submit();
  }

  function removeFromCart(isbn) {
    window.location.href = '${pageContext.request.contextPath}/billing/remove-item?isbn=' + encodeURIComponent(isbn);
  }

  function clearCart() {
    if (confirm('Are you sure you want to clear the entire cart?')) {
      window.location.href = '${pageContext.request.contextPath}/billing/new';
    }
  }

  // Book search functionality
  document.getElementById('bookSearchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const query = document.getElementById('bookSearch').value;
    if (!query) return;

    fetch('${pageContext.request.contextPath}/books/search?q=' + encodeURIComponent(query))
            .then(response => response.text())
            .then(html => {
              document.getElementById('searchResults').innerHTML = html;
            })
            .catch(error => {
              console.error('Search error:', error);
            });
  });
</script>