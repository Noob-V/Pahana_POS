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
            <h2><i class="fas fa-tachometer-alt"></i> Staff Dashboard</h2>
            <p class="text-muted mb-0">Manage your library inventory efficiently</p>
        </div>
    </div>

    <!-- Quick Stats Cards -->
    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card bg-primary text-white h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h4 class="card-title">${totalBooks}</h4>
                            <p class="card-text">Total Books</p>
                        </div>
                        <div class="align-self-center">
                            <i class="fas fa-book fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer bg-primary bg-opacity-25">
                    <a href="${pageContext.request.contextPath}/books" class="text-white text-decoration-none small">
                        <i class="fas fa-arrow-right"></i> View All Books
                    </a>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card bg-success text-white h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h4 class="card-title">${activeBooks}</h4>
                            <p class="card-text">Active Books</p>
                        </div>
                        <div class="align-self-center">
                            <i class="fas fa-check-circle fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer bg-success bg-opacity-25">
                    <a href="${pageContext.request.contextPath}/books" class="text-white text-decoration-none small">
                        <i class="fas fa-arrow-right"></i> Manage Books
                    </a>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card bg-warning text-white h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h4 class="card-title">${lowStockBooks}</h4>
                            <p class="card-text">Low Stock Alert</p>
                        </div>
                        <div class="align-self-center">
                            <i class="fas fa-exclamation-triangle fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer bg-warning bg-opacity-25">
                    <a href="#lowStockSection" class="text-white text-decoration-none small">
                        <i class="fas fa-arrow-down"></i> View Below
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="row mb-4">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0"><i class="fas fa-bolt"></i> Quick Actions</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-3 mb-2">
                            <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary w-100">
                                <i class="fas fa-plus"></i> Add New Book
                            </a>
                        </div>
                        <div class="col-md-3 mb-2">
                            <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-primary w-100">
                                <i class="fas fa-list"></i> View All Books
                            </a>
                        </div>
                        <div class="col-md-3 mb-2">
                            <a href="${pageContext.request.contextPath}/books/search" class="btn btn-outline-secondary w-100">
                                <i class="fas fa-search"></i> Search Books
                            </a>
                        </div>
                        <div class="col-md-3 mb-2">
                            <a href="${pageContext.request.contextPath}/books/google-search" class="btn btn-outline-success w-100">
                                <i class="fas fa-download"></i> Import Books
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Content Sections -->
    <div class="row">
        <!-- Recent Books -->
        <div class="col-md-6">
            <div class="card h-100">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0"><i class="fas fa-clock"></i> Recent Books</h5>
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-sm btn-outline-primary">View All</a>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty recentBooks}">
                            <div class="list-group list-group-flush">
                                <c:forEach var="book" items="${recentBooks}" varStatus="status">
                                    <c:if test="${status.index < 5}">
                                        <div class="list-group-item d-flex justify-content-between align-items-center">
                                            <div>
                                                <h6 class="mb-1">${book.title}</h6>
                                                <small class="text-muted">by ${book.author}</small><br>
                                                <small class="text-muted">Qty: ${book.quantity}</small>
                                            </div>
                                            <span class="badge ${book.quantity > book.minStockLevel ? 'bg-success' : 'bg-warning'} rounded-pill">
                                                    ${book.quantity > book.minStockLevel ? 'In Stock' : 'Low Stock'}
                                            </span>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center text-muted py-4">
                                <i class="fas fa-book fa-2x mb-2"></i>
                                <p>No books available</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Low Stock Alerts -->
        <div class="col-md-6">
            <div class="card h-100">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0" id="lowStockSection">
                        <i class="fas fa-exclamation-triangle text-warning"></i> Stock Alerts
                    </h5>
                    <span class="badge bg-warning">${lowStockBooks}</span>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty lowStockAlerts}">
                            <div class="list-group list-group-flush">
                                <c:forEach var="book" items="${lowStockAlerts}">
                                    <div class="list-group-item d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="mb-1">${book.title}</h6>
                                            <small class="text-muted">by ${book.author}</small><br>
                                            <small class="text-danger">Only ${book.quantity} left (Min: ${book.minStockLevel})</small>
                                        </div>
                                        <a href="${pageContext.request.contextPath}/books/edit?isbn=${book.isbn}"
                                           class="btn btn-sm btn-outline-warning">
                                            <i class="fas fa-edit"></i> Update
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center text-success py-4">
                                <i class="fas fa-check-circle fa-2x mb-2"></i>
                                <p>All books are well stocked!</p>
                            </div>
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