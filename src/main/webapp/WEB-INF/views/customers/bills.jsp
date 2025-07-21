<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Customer Bills - Pahana Education</title>
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
  <div class="d-flex justify-content-between align-items-center mb-4">
    <div>
      <h2><i class="fas fa-receipt"></i> Customer Bills</h2>
      <p class="text-muted mb-0">Bill history for ${customer.name}</p>
    </div>
    <div>
      <a href="${pageContext.request.contextPath}/customers/edit?accountNumber=${customer.accountNumber}"
         class="btn btn-primary me-2">
        <i class="fas fa-edit"></i> Edit Customer
      </a>
      <a href="${pageContext.request.contextPath}/customers" class="btn btn-secondary">
        <i class="fas fa-arrow-left"></i> Back to Customers
      </a>
    </div>
  </div>

  <!-- Customer Info Card -->
  <div class="card mb-4">
    <div class="card-header">
      <h5 class="mb-0"><i class="fas fa-user"></i> Customer Information</h5>
    </div>
    <div class="card-body">
      <div class="row">
        <div class="col-md-3">
          <strong>Account Number:</strong><br>
          <span class="text-primary">${customer.accountNumber}</span>
        </div>
        <div class="col-md-3">
          <strong>Name:</strong><br>
          ${customer.name}
        </div>
        <div class="col-md-3">
          <strong>Email:</strong><br>
          ${customer.email}
        </div>
        <div class="col-md-3">
          <strong>Phone:</strong><br>
          ${customer.phoneNumber}
        </div>
      </div>
    </div>
  </div>

  <!-- Bills List -->
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5 class="mb-0"><i class="fas fa-list"></i> Bill History</h5>
      <span class="badge bg-primary">${bills.size()} Bills</span>
    </div>
    <div class="card-body">
      <c:choose>
        <c:when test="${not empty bills}">
          <div class="table-responsive">
            <table class="table table-striped table-hover">
              <thead class="table-dark">
              <tr>
                <th>Bill ID</th>
                <th>Date</th>
                <th>Total Amount</th>
                <th>Payment Method</th>
                <th>Payment Status</th>
                <th>Staff</th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="bill" items="${bills}">
                <tr>
                  <td>
                    <code>${bill.billId}</code>
                  </td>
                  <td>
                    <fmt:formatDate value="${bill.billDate}" pattern="MMM dd, yyyy"/>
                    <br>
                    <small class="text-muted">
                      <fmt:formatDate value="${bill.billDate}" pattern="HH:mm"/>
                    </small>
                  </td>
                  <td>
                                        <span class="fw-bold text-success">
                                            Rs. <fmt:formatNumber value="${bill.totalAmount}" pattern="#,##0.00"/>
                                        </span>
                  </td>
                  <td>
                    <span class="badge bg-secondary">${bill.paymentMethod}</span>
                  </td>
                  <td>
                                        <span class="badge ${bill.paymentStatus == 'PAID' ? 'bg-success' : 'bg-warning'}">
                                            ${bill.paymentStatus}
                                        </span>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${not empty bill.userName}">
                        ${bill.userName}
                      </c:when>
                      <c:otherwise>
                        <span class="text-muted">Unknown</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <a href="${pageContext.request.contextPath}/billing/receipt?billId=${bill.billId}"
                       class="btn btn-sm btn-outline-primary" title="View Receipt">
                      <i class="fas fa-eye"></i>
                    </a>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>

          <!-- Summary -->
          <div class="row mt-4">
            <div class="col-md-12">
              <div class="card bg-light">
                <div class="card-body">
                  <div class="row text-center">
                    <div class="col-md-4">
                      <h5 class="text-primary">${bills.size()}</h5>
                      <small class="text-muted">Total Bills</small>
                    </div>
                    <div class="col-md-4">
                      <h5 class="text-success">
                        Rs. <fmt:formatNumber value="${bills.stream().mapToDouble(bill -> bill.totalAmount.doubleValue()).sum()}" pattern="#,##0.00"/>
                      </h5>
                      <small class="text-muted">Total Amount</small>
                    </div>
                    <div class="col-md-4">
                      <h5 class="text-info">
                        <c:choose>
                          <c:when test="${not empty bills}">
                            <fmt:formatDate value="${bills.get(0).billDate}" pattern="MMM dd, yyyy"/>
                          </c:when>
                          <c:otherwise>
                            N/A
                          </c:otherwise>
                        </c:choose>
                      </h5>
                      <small class="text-muted">Last Purchase</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <div class="text-center py-5">
            <i class="fas fa-receipt fa-3x text-muted mb-3"></i>
            <h5>No Bills Found</h5>
            <p class="text-muted">This customer hasn't made any purchases yet.</p>
            <a href="${pageContext.request.contextPath}/billing" class="btn btn-primary">
              <i class="fas fa-plus"></i> Create New Bill
            </a>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>