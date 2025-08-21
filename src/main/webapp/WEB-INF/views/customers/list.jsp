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
  <style>
    .navbar-brand {
      font-weight: 600;
    }
    .card {
      box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
      border: 1px solid rgba(0, 0, 0, 0.125);
    }
    .table th {
      font-weight: 600;
      border-top: none;
    }
    .btn-group .btn {
      border-radius: 0.375rem;
      margin-right: 0.25rem;
    }
    .btn-group .btn:last-child {
      margin-right: 0;
    }
    .badge {
      font-size: 0.75em;
    }
    .empty-state {
      padding: 4rem 2rem;
    }
    .empty-state i {
      opacity: 0.5;
    }
    .customer-avatar {
      width: 32px;
      height: 32px;
      font-size: 12px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    }
    .account-number {
      font-family: 'Courier New', monospace;
      font-weight: 600;
      color: #495057;
    }
    .search-card {
      background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
    }
    .stats-card {
      background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
    }
  </style>
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
  <div class="container">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">
      <i class="fas fa-graduation-cap me-2"></i>Pahana Education
    </a>
    <div class="navbar-nav ms-auto">
      <span class="navbar-text me-3">
        <i class="fas fa-user-circle me-1"></i>
        Hello, <strong>${sessionScope.currentUser.fullName}</strong>!
        <span class="badge bg-primary ms-2">${sessionScope.userRole}</span>
      </span>
      <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">
        <i class="fas fa-sign-out-alt me-1"></i>Logout
      </a>
    </div>
  </div>
</nav>

<div class="container-fluid py-4">
  <!-- Header Section -->
  <div class="row mb-4">
    <div class="col-12">
      <div class="d-flex justify-content-between align-items-center">
        <div>
          <h1 class="h3 mb-1">
            <i class="fas fa-users text-primary me-2"></i>Customer Management
          </h1>
          <p class="text-muted mb-0">Manage your customer database and billing information</p>
        </div>
        <div class="d-flex gap-2">
          <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline-secondary">
            <i class="fas fa-tachometer-alt me-1"></i>Dashboard
          </a>
          <c:if test="${sessionScope.userRole == 'ADMIN' || sessionScope.userRole == 'STAFF'}">
            <a href="${pageContext.request.contextPath}/customers/add" class="btn btn-primary">
              <i class="fas fa-plus me-1"></i>Add Customer
            </a>
          </c:if>
        </div>
      </div>
    </div>
  </div>

  <!-- Alerts Section -->
  <div class="row mb-4">
    <div class="col-12">
      <c:if test="${param.message == 'customer-added'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="fas fa-check-circle me-2"></i>
          <strong>Success!</strong> Customer added successfully!
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>
      <c:if test="${param.message == 'customer-updated'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="fas fa-check-circle me-2"></i>
          <strong>Success!</strong> Customer updated successfully!
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>
      <c:if test="${param.message == 'customer-deleted'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="fas fa-check-circle me-2"></i>
          <strong>Success!</strong> Customer deactivated successfully!
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>
      <c:if test="${param.message == 'quick-customer-created'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="fas fa-bolt me-2"></i>
          <strong>Success!</strong> Quick customer created successfully!
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>
      <c:if test="${param.error != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
          <i class="fas fa-exclamation-triangle me-2"></i>
          <strong>Error:</strong> ${param.error}
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>
    </div>
  </div>

  <!-- Search and Stats Row -->
  <div class="row mb-4">
    <!-- Search Card -->
    <div class="col-lg-8">
      <div class="card search-card border-0">
        <div class="card-header bg-transparent border-bottom">
          <h5 class="card-title mb-0">
            <i class="fas fa-search text-primary me-2"></i>Search Customers
          </h5>
        </div>
        <div class="card-body">
          <form method="get" action="${pageContext.request.contextPath}/customers/search">
            <div class="row g-2">
              <div class="col-md-8">
                <div class="input-group">
                  <span class="input-group-text bg-white border-end-0">
                    <i class="fas fa-search text-muted"></i>
                  </span>
                  <input type="text" class="form-control border-start-0" name="q"
                         placeholder="Search by account number, customer name, email, phone..."
                         value="${searchQuery}" id="customerSearch">
                </div>
              </div>
              <div class="col-md-4">
                <div class="d-grid gap-2 d-md-flex">
                  <button type="submit" class="btn btn-primary flex-fill">
                    <i class="fas fa-search me-1"></i>Search
                  </button>
                  <a href="${pageContext.request.contextPath}/customers" class="btn btn-outline-secondary">
                    <i class="fas fa-times"></i>
                  </a>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- Stats Card -->
    <div class="col-lg-4">
      <div class="card stats-card border-0 h-100">
        <div class="card-body d-flex align-items-center">
          <div class="me-3">
            <div class="rounded-circle bg-primary text-white d-flex align-items-center justify-content-center"
                 style="width: 60px; height: 60px;">
              <i class="fas fa-users fa-lg"></i>
            </div>
          </div>
          <div>
            <h3 class="mb-1">${customers.size()}</h3>
            <p class="text-muted mb-0">
              <c:choose>
                <c:when test="${not empty searchQuery}">
                  Search Results
                </c:when>
                <c:otherwise>
                  Total Customers
                </c:otherwise>
              </c:choose>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Customer List Section -->
  <div class="row">
    <div class="col-12">
      <div class="card">
        <div class="card-header bg-white border-bottom">
          <div class="d-flex justify-content-between align-items-center">
            <h5 class="card-title mb-0">
              <i class="fas fa-list me-2 text-primary"></i>
              <c:choose>
                <c:when test="${not empty searchQuery}">
                  Search Results for "<em>${searchQuery}</em>"
                </c:when>
                <c:otherwise>
                  Customer Directory
                </c:otherwise>
              </c:choose>
            </h5>
            <div class="d-flex gap-2">
              <c:if test="${sessionScope.userRole == 'ADMIN' || sessionScope.userRole == 'STAFF'}">
                <a href="${pageContext.request.contextPath}/customers/quick" class="btn btn-outline-primary btn-sm">
                  <i class="fas fa-bolt me-1"></i>Quick Add
                </a>
              </c:if>
              <span class="badge bg-info fs-6">${customers.size()} found</span>
            </div>
          </div>
        </div>
        <div class="card-body p-0">
          <c:choose>
            <c:when test="${not empty customers}">
              <div class="table-responsive">
                <table class="table table-hover mb-0">
                  <thead class="table-dark">
                  <tr>
                    <th scope="col" class="ps-4">
                      <i class="fas fa-hashtag me-1"></i>Account
                    </th>
                    <th scope="col">
                      <i class="fas fa-user me-1"></i>Customer
                    </th>
                    <th scope="col">
                      <i class="fas fa-envelope me-1"></i>Email
                    </th>
                    <th scope="col">
                      <i class="fas fa-phone me-1"></i>Phone
                    </th>
                    <th scope="col">
                      <i class="fas fa-toggle-on me-1"></i>Status
                    </th>
                    <th scope="col">
                      <i class="fas fa-calendar-plus me-1"></i>Created
                    </th>
                    <th scope="col" class="pe-4">
                      <i class="fas fa-cogs me-1"></i>Actions
                    </th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="customer" items="${customers}" varStatus="status">
                    <tr>
                      <td class="ps-4">
                        <div class="account-number">${customer.accountNumber}</div>
                      </td>
                      <td>
                        <div class="d-flex align-items-center">
                          <div class="customer-avatar rounded-circle d-flex align-items-center justify-content-center text-white me-3">
                              ${customer.name.substring(0,1).toUpperCase()}
                          </div>
                          <div>
                            <div class="fw-medium">${customer.name}</div>
                            <c:if test="${not empty customer.address}">
                              <small class="text-muted">
                                <i class="fas fa-map-marker-alt me-1"></i>${customer.address}
                              </small>
                            </c:if>
                          </div>
                        </div>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${not empty customer.email}">
                            <a href="mailto:${customer.email}" class="text-decoration-none">
                              <i class="fas fa-envelope-open-text me-1"></i>${customer.email}
                            </a>
                          </c:when>
                          <c:otherwise>
                            <span class="text-muted fst-italic">
                              <i class="fas fa-minus me-1"></i>Not provided
                            </span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${not empty customer.phoneNumber}">
                            <a href="tel:${customer.phoneNumber}" class="text-decoration-none">
                              <i class="fas fa-phone me-1"></i>${customer.phoneNumber}
                            </a>
                          </c:when>
                          <c:otherwise>
                            <span class="text-muted fst-italic">
                              <i class="fas fa-minus me-1"></i>Not provided
                            </span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${customer.active}">
                            <span class="badge bg-success">
                              <i class="fas fa-check me-1"></i>Active
                            </span>
                          </c:when>
                          <c:otherwise>
                            <span class="badge bg-secondary">
                              <i class="fas fa-pause me-1"></i>Inactive
                            </span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <div class="text-muted small">
                          <i class="fas fa-calendar me-1"></i>
                          <fmt:formatDate value="${customer.createdDateAsDate}" pattern="MMM dd, yyyy"/>
                        </div>
                      </td>
                      <td class="pe-4">
                        <div class="btn-group" role="group">
                          <a href="${pageContext.request.contextPath}/customers/bills?accountNumber=${customer.accountNumber}"
                             class="btn btn-sm btn-outline-info"
                             title="View bills and payment history"
                             data-bs-toggle="tooltip">
                            <i class="fas fa-receipt"></i>
                          </a>

                          <c:if test="${sessionScope.userRole == 'ADMIN'}">
                            <a href="${pageContext.request.contextPath}/customers/edit?accountNumber=${customer.accountNumber}"
                               class="btn btn-sm btn-outline-primary"
                               title="Edit customer information"
                               data-bs-toggle="tooltip">
                              <i class="fas fa-edit"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/customers/delete?accountNumber=${customer.accountNumber}"
                               class="btn btn-sm btn-outline-danger"
                               title="Deactivate customer account"
                               data-bs-toggle="tooltip"
                               onclick="return confirm('Are you sure you want to deactivate ${customer.name}? This will hide them from the active customer list.')">
                              <i class="fas fa-user-times"></i>
                            </a>
                          </c:if>
                        </div>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
            </c:when>
            <c:otherwise>
              <div class="text-center text-muted empty-state">
                <i class="fas fa-users fa-4x mb-3"></i>
                <h4 class="mb-3">
                  <c:choose>
                    <c:when test="${not empty searchQuery}">
                      No customers found matching "${searchQuery}"
                    </c:when>
                    <c:otherwise>
                      No customers found
                    </c:otherwise>
                  </c:choose>
                </h4>
                <p class="mb-4">
                  <c:choose>
                    <c:when test="${not empty searchQuery}">
                      Try adjusting your search criteria or browse all customers.
                    </c:when>
                    <c:otherwise>
                      Get started by adding your first customer to the system.
                    </c:otherwise>
                  </c:choose>
                </p>
                <div class="d-flex gap-2 justify-content-center">
                  <c:if test="${not empty searchQuery}">
                    <a href="${pageContext.request.contextPath}/customers" class="btn btn-outline-primary">
                      <i class="fas fa-list me-2"></i>View All Customers
                    </a>
                  </c:if>
                  <c:if test="${sessionScope.userRole == 'ADMIN' || sessionScope.userRole == 'STAFF'}">
                    <a href="${pageContext.request.contextPath}/customers/add" class="btn btn-primary btn-lg">
                      <i class="fas fa-plus me-2"></i>Add First Customer
                    </a>
                  </c:if>
                </div>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
        <c:if test="${not empty customers && customers.size() > 10}">
          <div class="card-footer bg-light text-center">
            <small class="text-muted">
              <i class="fas fa-info-circle me-1"></i>
              Showing ${customers.size()} customer${customers.size() != 1 ? 's' : ''}
            </small>
          </div>
        </c:if>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Real-time search functionality
  document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('customerSearch');
    const tableRows = document.querySelectorAll('tbody tr');
    const totalCount = tableRows.length;
    const resultsCounter = document.querySelector('.badge.bg-info');

    function performSearch() {
      const searchTerm = searchInput.value.toLowerCase().trim();
      let visibleCount = 0;

      tableRows.forEach(function(row) {
        const accountNumber = row.querySelector('.account-number').textContent.toLowerCase();
        const customerName = row.querySelector('.fw-medium').textContent.toLowerCase();
        const emailCell = row.querySelector('td:nth-child(3)');
        const email = emailCell.querySelector('a') ?
                emailCell.querySelector('a').textContent.toLowerCase() : '';
        const phoneCell = row.querySelector('td:nth-child(4)');
        const phone = phoneCell.querySelector('a') ?
                phoneCell.querySelector('a').textContent.toLowerCase() : '';

        const matches = accountNumber.includes(searchTerm) ||
                customerName.includes(searchTerm) ||
                email.includes(searchTerm) ||
                phone.includes(searchTerm);

        if (matches) {
          row.style.display = '';
          visibleCount++;
        } else {
          row.style.display = 'none';
        }
      });

      // Update results counter
      resultsCounter.textContent = `${visibleCount} found`;

      // Handle empty results
      const tableContainer = document.querySelector('.table-responsive');
      const emptyState = document.querySelector('.search-empty-state');

      if (visibleCount === 0 && searchTerm !== '') {
        if (tableContainer) tableContainer.style.display = 'none';
        if (!emptyState) {
          const searchEmptyDiv = document.createElement('div');
          searchEmptyDiv.className = 'text-center text-muted empty-state search-empty-state';
          searchEmptyDiv.innerHTML = `
            <i class="fas fa-search fa-4x mb-3"></i>
            <h4 class="mb-3">No Results Found</h4>
            <p class="mb-4">No customers match your search: "<strong>${searchTerm}</strong>"</p>
            <button class="btn btn-outline-primary" onclick="clearSearch()">
              <i class="fas fa-times me-2"></i>Clear Search
            </button>
          `;
          document.querySelector('.card-body.p-0').appendChild(searchEmptyDiv);
        }
      } else {
        if (tableContainer) tableContainer.style.display = '';
        if (emptyState) emptyState.remove();
      }
    }

    // Clear search function
    window.clearSearch = function() {
      searchInput.value = '';
      performSearch();
      searchInput.focus();
    };

    // Search as user types
    searchInput.addEventListener('input', performSearch);

    // Focus search with Ctrl+F
    document.addEventListener('keydown', function(e) {
      if ((e.ctrlKey || e.metaKey) && e.key === 'f') {
        e.preventDefault();
        searchInput.focus();
      }
    });
  });
</script>
</body>
</html>