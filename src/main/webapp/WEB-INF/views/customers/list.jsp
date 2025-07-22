<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Customer Management - Pahana Education</title>
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
      <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">
        <i class="fas fa-sign-out-alt"></i> Logout
      </a>
    </div>
  </div>
</nav>

<div class="container mt-4">
  <!-- Header -->
  <!-- Header -->
  <div class="d-flex justify-content-between align-items-center mb-4">
    <div>
      <h2><i class="fas fa-users"></i> Customer Management</h2>
      <p class="text-muted mb-0">Manage your customer database</p>
    </div>
    <div>
      <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline-secondary me-2">
        <i class="fas fa-tachometer-alt"></i> Dashboard
      </a>
      <a href="${pageContext.request.contextPath}/customers/add" class="btn btn-primary">
        <i class="fas fa-plus"></i> Add Customer
      </a>
    </div>
  </div>

  <!-- Alerts -->
  <c:if test="${param.message == 'customer-added'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      Customer added successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  <c:if test="${param.message == 'customer-updated'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      Customer updated successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  <c:if test="${param.message == 'customer-deleted'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      Customer deleted successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  <c:if test="${param.error != null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      Error: ${param.error}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <!-- Search and Filters -->
  <div class="card mb-4">
    <div class="card-header">
      <h5 class="mb-0"><i class="fas fa-search"></i> Search Customers</h5>
    </div>
    <div class="card-body">
      <form method="get" action="${pageContext.request.contextPath}/customers/search">
        <div class="row">
          <div class="col-md-8">
            <input type="text" class="form-control" name="q" placeholder="Search by customer name..."
                   value="${searchQuery}">
          </div>
          <div class="col-md-4">
            <button type="submit" class="btn btn-outline-primary me-2">
              <i class="fas fa-search"></i> Search
            </button>
            <a href="${pageContext.request.contextPath}/customers" class="btn btn-outline-secondary">
              <i class="fas fa-times"></i> Clear
            </a>
          </div>
        </div>
      </form>
    </div>
  </div>

  <!-- Customer List -->
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5 class="mb-0">
        <i class="fas fa-list"></i>
        <c:choose>
          <c:when test="${not empty searchQuery}">
            Search Results for "${searchQuery}"
          </c:when>
          <c:otherwise>
            All Customers
          </c:otherwise>
        </c:choose>
      </h5>
      <span class="badge bg-info">${customers.size()} customers</span>
    </div>
    <div class="card-body">
      <c:choose>
        <c:when test="${not empty customers}">
          <div class="table-responsive">
            <table class="table table-striped table-hover">
              <thead class="table-dark">
              <tr>
                <th>Account Number</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Status</th>
                <th>Created Date</th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="customer" items="${customers}">
                <tr>
                  <td>
                    <strong>${customer.accountNumber}</strong>
                  </td>
                  <td>${customer.name}</td>
                  <td>
                    <c:choose>
                      <c:when test="${not empty customer.email}">
                        <a href="mailto:${customer.email}">${customer.email}</a>
                      </c:when>
                      <c:otherwise>
                        <span class="text-muted">-</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${not empty customer.phoneNumber}">
                        ${customer.phoneNumber}
                      </c:when>
                      <c:otherwise>
                        <span class="text-muted">-</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${customer.active}">
                        <span class="badge bg-success">Active</span>
                      </c:when>
                      <c:otherwise>
                        <span class="badge bg-secondary">Inactive</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <fmt:formatDate value="${customer.createdDateAsDate}" pattern="MMM dd, yyyy"/>
                  </td>
                  <td>
                    <div class="btn-group" role="group">
                      <a href="${pageContext.request.contextPath}/customers/bills?accountNumber=${customer.accountNumber}"
                         class="btn btn-sm btn-outline-info" title="View Bills">
                        <i class="fas fa-receipt"></i>
                      </a>
                      <a href="${pageContext.request.contextPath}/customers/edit?accountNumber=${customer.accountNumber}"
                         class="btn btn-sm btn-outline-primary" title="Edit">
                        <i class="fas fa-edit"></i>
                      </a>
                      <a href="${pageContext.request.contextPath}/customers/delete?accountNumber=${customer.accountNumber}"
                         class="btn btn-sm btn-outline-danger" title="Delete"
                         onclick="return confirm('Are you sure you want to delete this customer?')">
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
          <div class="text-center text-muted py-5">
            <i class="fas fa-users fa-3x mb-3"></i>
            <h5>
              <c:choose>
                <c:when test="${not empty searchQuery}">
                  No customers found matching "${searchQuery}"
                </c:when>
                <c:otherwise>
                  No customers found
                </c:otherwise>
              </c:choose>
            </h5>
            <p>
              <c:choose>
                <c:when test="${not empty searchQuery}">
                  Try a different search term or
                  <a href="${pageContext.request.contextPath}/customers">view all customers</a>
                </c:when>
                <c:otherwise>
                  <a href="${pageContext.request.contextPath}/customers/add">Add your first customer</a>
                </c:otherwise>
              </c:choose>
            </p>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>