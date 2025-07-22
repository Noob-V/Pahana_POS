<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Dashboard - Pahana Education</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .stats-card {
            border: none;
            border-radius: 15px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .stats-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        }
        .stats-card .card-body {
            padding: 2rem;
        }
        .stats-icon {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            color: white;
            margin-bottom: 1rem;
        }
        .stats-value {
            font-size: 2.5rem;
            font-weight: bold;
            margin: 0;
            color: #2c3e50;
        }
        .stats-label {
            color: #6c757d;
            font-weight: 500;
            text-transform: uppercase;
            font-size: 0.85rem;
            letter-spacing: 1px;
            margin: 0;
        }
        .bg-gradient-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .bg-gradient-success {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }
        .bg-gradient-info {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }
        .bg-gradient-warning {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
        }
        .bg-gradient-danger {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
        }
        .quick-action-card {
            border: none;
            border-radius: 12px;
            transition: all 0.3s ease;
            cursor: pointer;
        }
        .quick-action-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.12);
        }
        .bills-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 15px;
            color: white;
            padding: 2rem;
            margin-bottom: 2rem;
        }
        .bills-card {
            background: rgba(255,255,255,0.1);
            border: 1px solid rgba(255,255,255,0.2);
            border-radius: 12px;
            backdrop-filter: blur(10px);
        }
        .bills-card:hover {
            background: rgba(255,255,255,0.15);
        }
        .btn-bills {
            background: rgba(255,255,255,0.2);
            border: 1px solid rgba(255,255,255,0.3);
            color: white;
            border-radius: 8px;
            transition: all 0.3s ease;
        }
        .btn-bills:hover {
            background: rgba(255,255,255,0.3);
            color: white;
            transform: translateY(-2px);
        }
    </style>
</head>
<body class="bg-light">
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

<div class="container-fluid mt-4">
    <!-- Welcome Header -->
    <div class="row mb-4">
        <div class="col-12">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h2 class="mb-0">
                        <i class="fas fa-tachometer-alt text-primary"></i> Staff Dashboard
                    </h2>
                    <p class="text-muted mb-0">Welcome back, ${sessionScope.currentUser.fullName}</p>
                </div>
                <div class="text-end">
                    <small class="text-muted">
                        <i class="fas fa-calendar-alt"></i>
                        <fmt:formatDate value="${now}" pattern="EEEE, MMMM dd, yyyy"/>
                    </small>
                </div>
            </div>
        </div>
    </div>

    <!-- Bills Generation Section -->
    <div class="bills-section">
        <div class="row align-items-center">
            <div class="col-md-8">
                <h3 class="mb-3">
                    <i class="fas fa-cash-register"></i> Point of Sale System
                </h3>
                <p class="mb-4 opacity-75">
                    Generate bills, manage transactions, and process customer payments efficiently.
                </p>
                <div class="row">
                    <div class="col-sm-6 mb-3">
                        <div class="bills-card card">
                            <div class="card-body text-center">
                                <i class="fas fa-plus-circle fa-2x mb-2"></i>
                                <h6 class="card-title">New Sale</h6>
                                <p class="card-text small opacity-75">Start a new transaction</p>
                                <a href="${pageContext.request.contextPath}/billing/new" class="btn btn-bills btn-sm">
                                    <i class="fas fa-shopping-cart"></i> Start Sale
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 mb-3">
                        <div class="bills-card card">
                            <div class="card-body text-center">
                                <i class="fas fa-receipt fa-2x mb-2"></i>
                                <h6 class="card-title">View Bills</h6>
                                <p class="card-text small opacity-75">Browse transaction history</p>
                                <a href="${pageContext.request.contextPath}/billing" class="btn btn-bills btn-sm">
                                    <i class="fas fa-eye"></i> View All
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4 text-center">
                <div class="d-none d-md-block">
                    <i class="fas fa-cash-register" style="font-size: 120px; opacity: 0.2;"></i>
                </div>
            </div>
        </div>
    </div>

    <!-- Stats Cards -->
    <div class="row mb-4">
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="stats-icon bg-gradient-primary">
                        <i class="fas fa-users"></i>
                    </div>
                    <h3 class="stats-value">${totalCustomers}</h3>
                    <p class="stats-label">Total Customers</p>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="stats-icon bg-gradient-success">
                        <i class="fas fa-book"></i>
                    </div>
                    <h3 class="stats-value">${totalBooks}</h3>
                    <p class="stats-label">Books in Stock</p>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="stats-icon bg-gradient-info">
                        <i class="fas fa-receipt"></i>
                    </div>
                    <h3 class="stats-value">${totalBills}</h3>
                    <p class="stats-label">Total Bills</p>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="stats-icon bg-gradient-warning">
                        <i class="fas fa-dollar-sign"></i>
                    </div>
                    <h3 class="stats-value">$<fmt:formatNumber value="${totalRevenue}" pattern="#,##0.00"/></h3>
                    <p class="stats-label">Total Revenue</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="row mb-4">
        <div class="col-12">
            <h4 class="mb-3">
                <i class="fas fa-bolt text-warning"></i> Quick Actions
            </h4>
        </div>

        <div class="col-lg-3 col-md-6 mb-3">
            <div class="card quick-action-card" onclick="window.location.href='${pageContext.request.contextPath}/customers/add'">
                <div class="card-body text-center">
                    <i class="fas fa-user-plus fa-2x text-primary mb-3"></i>
                    <h6>Add Customer</h6>
                    <p class="text-muted small">Register new customer</p>
                </div>
            </div>
        </div>

        <div class="col-lg-3 col-md-6 mb-3">
            <div class="card quick-action-card" onclick="window.location.href='${pageContext.request.contextPath}/books/add'">
                <div class="card-body text-center">
                    <i class="fas fa-book-medical fa-2x text-success mb-3"></i>
                    <h6>Add Book</h6>
                    <p class="text-muted small">Add new book to inventory</p>
                </div>
            </div>
        </div>

        <div class="col-lg-3 col-md-6 mb-3">
            <div class="card quick-action-card" onclick="window.location.href='${pageContext.request.contextPath}/customers'">
                <div class="card-body text-center">
                    <i class="fas fa-users fa-2x text-info mb-3"></i>
                    <h6>Manage Customers</h6>
                    <p class="text-muted small">View and edit customers</p>
                </div>
            </div>
        </div>

        <div class="col-lg-3 col-md-6 mb-3">
            <div class="card quick-action-card" onclick="window.location.href='${pageContext.request.contextPath}/books'">
                <div class="card-body text-center">
                    <i class="fas fa-warehouse fa-2x text-warning mb-3"></i>
                    <h6>Manage Inventory</h6>
                    <p class="text-muted small">View and edit books</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Recent Activities -->
    <div class="row">
        <div class="col-lg-6 mb-4">
            <div class="card">
                <div class="card-header bg-white">
                    <h5 class="mb-0">
                        <i class="fas fa-clock text-primary"></i> Recent Customers
                    </h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty recentCustomers}">
                            <div class="list-group list-group-flush">
                                <c:forEach var="customer" items="${recentCustomers}">
                                    <div class="list-group-item px-0">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                <h6 class="mb-1">${customer.name}</h6>
                                                <p class="mb-1 text-muted small">${customer.accountNumber}</p>
                                            </div>
                                            <small class="text-muted">
                                                <fmt:formatDate value="${customer.createdDateAsDate}" pattern="MMM dd"/>
                                            </small>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="text-muted text-center py-3">No recent customers</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <div class="col-lg-6 mb-4">
            <div class="card">
                <div class="card-header bg-white">
                    <h5 class="mb-0">
                        <i class="fas fa-chart-line text-success"></i> Recent Bills
                    </h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty recentBills}">
                            <div class="list-group list-group-flush">
                                <c:forEach var="bill" items="${recentBills}">
                                    <div class="list-group-item px-0">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                <h6 class="mb-1">${bill.billId}</h6>
                                                <p class="mb-1 text-muted small">${bill.customerName}</p>
                                            </div>
                                            <div class="text-end">
                                                <small class="fw-bold text-success">
                                                    $<fmt:formatNumber value="${bill.totalAmount}" pattern="#,##0.00"/>
                                                </small><br>
                                                <small class="text-muted">
                                                    <fmt:formatDate value="${bill.billDateAsDate}" pattern="MMM dd"/>
                                                </small>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="text-muted text-center py-3">No recent bills</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>