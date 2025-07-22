<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Customer Bills - ${customer.name} - Pahana Education</title>
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
  <!-- Customer Info Header -->
  <div class="card mb-4">
    <div class="card-header bg-primary text-white">
      <h4 class="mb-0">
        <i class="fas fa-user"></i> ${customer.name}
        <span class="badge bg-light text-dark ms-2">${customer.accountNumber}</span>
      </h4>
    </div>
    <div class="card-body">
      <div class="row">
        <div class="col-md-6">
          <p><strong>Email:</strong>
            <c:choose>
              <c:when test="${not empty customer.email}">
                <a href="mailto:${customer.email}">${customer.email}</a>
              </c:when>
              <c:otherwise>
                <span class="text-muted">Not provided</span>
              </c:otherwise>
            </c:choose>
          </p>
          <p><strong>Phone:</strong>
            <c:choose>
              <c:when test="${not empty customer.phoneNumber}">
                ${customer.phoneNumber}
              </c:when>
              <c:otherwise>
                <span class="text-muted">Not provided</span>
              </c:otherwise>
            </c:choose>
          </p>
        </div>
        <div class="col-md-6">
          <p><strong>Status:</strong>
            <c:choose>
              <c:when test="${customer.active}">
                <span class="badge bg-success">Active</span>
              </c:when>
              <c:otherwise>
                <span class="badge bg-secondary">Inactive</span>
              </c:otherwise>
            </c:choose>
          </p>
          <p><strong>Member Since:</strong>
            <fmt:formatDate value="${customer.createdDateAsDate}" pattern="MMM dd, yyyy"/>
          </p>
        </div>
      </div>
    </div>
  </div>

  <!-- Bills List -->
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5 class="mb-0">
        <i class="fas fa-receipt"></i> Purchase History
      </h5>
      <span class="badge bg-info">${bills.size()} bills</span>
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
                <th>Status</th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="bill" items="${bills}">
                <tr>
                  <td>
                    <strong>${bill.billId}</strong>
                  </td>
                  <td>
                    <fmt:formatDate value="${bill.billDateAsDate}" pattern="MMM dd, yyyy HH:mm"/>
                  </td>
                  <td>
                    <strong>$<fmt:formatNumber value="${bill.totalAmount}" pattern="#,##0.00"/></strong>
                  </td>
                  <td>
                    <span class="badge bg-secondary">${bill.paymentMethod}</span>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${bill.paymentStatus == 'PAID'}">
                        <span class="badge bg-success">Paid</span>
                      </c:when>
                      <c:when test="${bill.paymentStatus == 'PENDING'}">
                        <span class="badge bg-warning">Pending</span>
                      </c:when>
                      <c:otherwise>
                        <span class="badge bg-danger">Unpaid</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <a href="${pageContext.request.contextPath}/billing/receipt?billId=${bill.billId}"
                       class="btn btn-sm btn-outline-primary" title="View Receipt">
                      <i class="fas fa-eye"></i> View
                    </a>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>

          <!-- Summary -->
          <div class="row mt-4">
            <div class="col-md-6 offset-md-6">
              <div class="card bg-light">
                <div class="card-body">
                  <h6 class="card-title">Summary</h6>
                  <p class="mb-1">Total Orders: <strong>${bills.size()}</strong></p>
                  <c:set var="totalAmount" value="0" />
                  <c:forEach var="bill" items="${bills}">
                    <c:set var="totalAmount" value="${totalAmount + bill.totalAmount}" />
                  </c:forEach>
                  <p class="mb-0">Total Spent: <strong>$<fmt:formatNumber value="${totalAmount}" pattern="#,##0.00"/></strong></p>
                </div>
              </div>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <div class="text-center text-muted py-5">
            <i class="fas fa-receipt fa-3x mb-3"></i>
            <h5>No Purchase History</h5>
            <p>This customer hasn't made any purchases yet.</p>
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