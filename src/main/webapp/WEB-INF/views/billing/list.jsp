<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bills - PahanaEdu POS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <nav class="col-md-2 d-md-block bg-light sidebar">
            <div class="position-sticky pt-3">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
                            <i class="fas fa-tachometer-alt"></i> Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/books">
                            <i class="fas fa-book"></i> Books
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/billing">
                            <i class="fas fa-receipt"></i> Billing
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/customers">
                            <i class="fas fa-users"></i> Customers
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Main content -->
        <main class="col-md-10 ms-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Bills</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <a href="${pageContext.request.contextPath}/billing/pos" class="btn btn-primary">
                        <i class="fas fa-plus"></i> New Sale
                    </a>
                </div>
            </div>

            <!-- Alert Messages -->
            <c:if test="${param.message != null}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <c:choose>
                        <c:when test="${param.message == 'bill-created'}">Bill created successfully!</c:when>
                        <c:otherwise>Operation completed successfully!</c:otherwise>
                    </c:choose>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <c:if test="${param.error != null}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <c:choose>
                        <c:when test="${param.error == 'receipt-not-found'}">Receipt not found!</c:when>
                        <c:when test="${param.error == 'discount-failed'}">Failed to apply discount!</c:when>
                        <c:otherwise>An error occurred!</c:otherwise>
                    </c:choose>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <!-- Bills Table -->
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Recent Bills</h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty bills}">
                            <div class="text-center py-4">
                                <i class="fas fa-receipt fa-3x text-muted mb-3"></i>
                                <h5 class="text-muted">No bills found</h5>
                                <p class="text-muted">Start by creating your first sale.</p>
                                <a href="${pageContext.request.contextPath}/billing/pos" class="btn btn-primary">
                                    <i class="fas fa-plus"></i> Create New Sale
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead class="table-dark">
                                    <tr>
                                        <th>Bill #</th>
                                        <th>Customer</th>
                                        <th>Date</th>
                                        <th>Items</th>
                                        <th>Subtotal</th>
                                        <th>Tax</th>
                                        <th>Discount</th>
                                        <th>Total</th>
                                        <th>Payment</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bill" items="${bills}">
                                        <tr>
                                            <td>
                                                <strong>${bill.billNumber}</strong>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty bill.customerName}">
                                                        ${bill.customerName}
                                                        <br><small class="text-muted">${bill.customerAccountNumber}</small>
                                                    </c:when>
                                                    <c:when test="${not empty bill.customerAccountNumber}">
                                                        ${bill.customerAccountNumber}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">Walk-in</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${bill.billDate}" pattern="MMM dd, yyyy"/>
                                                <br><small class="text-muted">
                                                <fmt:formatDate value="${bill.billDate}" pattern="HH:mm"/>
                                            </small>
                                            </td>
                                            <td>
                                                        <span class="badge bg-info">
                                                            ${not empty bill.billItems ? bill.billItems.size() : 0} items
                                                        </span>
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${bill.subtotal}" type="currency" currencySymbol="$"/>
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${bill.taxAmount}" type="currency" currencySymbol="$"/>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${bill.discountAmount > 0}">
                                                                <span class="text-success">
                                                                    -<fmt:formatNumber value="${bill.discountAmount}" type="currency" currencySymbol="$"/>
                                                                </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <strong>
                                                    <fmt:formatNumber value="${bill.totalAmount}" type="currency" currencySymbol="$"/>
                                                </strong>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty bill.paymentMethod}">
                                                        <span class="badge bg-secondary">${bill.paymentMethod}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${bill.paymentStatus == 'PAID'}">
                                                        <span class="badge bg-success">Paid</span>
                                                    </c:when>
                                                    <c:when test="${bill.paymentStatus == 'PENDING'}">
                                                        <span class="badge bg-warning">Pending</span>
                                                    </c:when>
                                                    <c:when test="${bill.paymentStatus == 'CANCELLED'}">
                                                        <span class="badge bg-danger">Cancelled</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">Unknown</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="btn-group btn-group-sm">
                                                    <a href="${pageContext.request.contextPath}/billing/receipt?billId=${bill.billId}"
                                                       class="btn btn-outline-primary btn-sm" title="View Receipt">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/billing/receipt?billId=${bill.billId}&print=true"
                                                       class="btn btn-outline-secondary btn-sm" title="Print Receipt">
                                                        <i class="fas fa-print"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>