<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Customer - Pahana Education</title>
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
      <a href="${pageContext.request.contextPath}/customers" class="btn btn-outline-light me-2">
        <i class="fas fa-arrow-left"></i> Back to Customers
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
        <div class="card-header">
          <h4 class="mb-0"><i class="fas fa-user-edit"></i> Edit Customer</h4>
        </div>
        <div class="card-body">
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
              <i class="fas fa-exclamation-circle"></i> ${errorMessage}
            </div>
          </c:if>

          <!-- Customer Info Preview -->
          <div class="alert alert-info">
            <div class="row">
              <div class="col-md-6">
                <strong>Account Number:</strong> ${customer.accountNumber}
              </div>
              <div class="col-md-6">
                <strong>Customer Since:</strong>
                <fmt:formatDate value="${customer.createdDate}" pattern="MMM dd, yyyy"/>
              </div>
            </div>
          </div>

          <form method="POST" action="${pageContext.request.contextPath}/customers/edit">
            <input type="hidden" name="accountNumber" value="${customer.accountNumber}">

            <div class="mb-3">
              <label for="name" class="form-label">Full Name <span class="text-danger">*</span></label>
              <input type="text" class="form-control" id="name" name="name"
                     value="${customer.name}" required>
            </div>

            <div class="mb-3">
              <label for="email" class="form-label">Email Address <span class="text-danger">*</span></label>
              <input type="email" class="form-control" id="email" name="email"
                     value="${customer.email}" required>
              <div class="form-text">We'll use this for communication and receipts</div>
            </div>

            <div class="mb-3">
              <label for="phone" class="form-label">Phone Number</label>
              <input type="tel" class="form-control" id="phone" name="phone"
                     value="${customer.phoneNumber}" placeholder="e.g., +1-555-123-4567">
            </div>

            <div class="mb-3">
              <label for="address" class="form-label">Address</label>
              <textarea class="form-control" id="address" name="address" rows="3"
                        placeholder="Street address, city, state, postal code">${customer.address}</textarea>
            </div>

            <div class="mb-3">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" id="active" name="active"
                       value="true" ${customer.active ? 'checked' : ''}>
                <label class="form-check-label" for="active">
                  Active Customer
                </label>
                <div class="form-text">
                  <c:choose>
                    <c:when test="${customer.active}">
                      Customer is currently active and can make purchases
                    </c:when>
                    <c:otherwise>
                      Customer is inactive - check this box to reactivate
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
            </div>

            <div class="d-flex justify-content-between">
              <div>
                <a href="${pageContext.request.contextPath}/customers" class="btn btn-secondary">
                  <i class="fas fa-times"></i> Cancel
                </a>
                <a href="${pageContext.request.contextPath}/customers/bills?accountNumber=${customer.accountNumber}"
                   class="btn btn-info">
                  <i class="fas fa-receipt"></i> View Bills
                </a>
              </div>
              <button type="submit" class="btn btn-primary">
                <i class="fas fa-save"></i> Update Customer
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>