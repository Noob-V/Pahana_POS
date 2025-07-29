<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Staff Management - Pahana Education</title>
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
      <h2><i class="fas fa-users-cog"></i> Staff Management</h2>
      <p class="text-muted mb-0">Manage your staff members</p>
    </div>
    <div>
      <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline-secondary me-2">
        <i class="fas fa-tachometer-alt"></i> Dashboard
      </a>
      <a href="${pageContext.request.contextPath}/staff/add" class="btn btn-primary">
        <i class="fas fa-plus"></i> Add Staff
      </a>
    </div>
  </div>


  <!-- Alerts -->
  <c:if test="${param.message == 'staff-added'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      Staff member added successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  <c:if test="${param.message == 'staff-updated'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      Staff member updated successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  <c:if test="${param.message == 'staff-deleted'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      Staff member deleted successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  <c:if test="${param.error != null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      Error: ${param.error}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <!-- Staff List -->
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5 class="mb-0">
        <i class="fas fa-list"></i> All Staff Members
      </h5>
      <span class="badge bg-info">${staffList.size()} staff members</span>
    </div>
    <div class="card-body">
      <c:choose>
        <c:when test="${not empty staffList}">
          <div class="table-responsive">
            <table class="table table-striped table-hover">
              <thead class="table-dark">
              <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Status</th>
                <th>Created Date</th>
                <th>Last Login</th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="staff" items="${staffList}">
                <tr>
                  <td>
                    <strong>${staff.id}</strong>
                  </td>
                  <td>
                    <code>${staff.username}</code>
                  </td>
                  <td>${staff.fullName}</td>
                  <td>
                    <c:choose>
                      <c:when test="${not empty staff.email}">
                        <a href="mailto:${staff.email}">${staff.email}</a>
                      </c:when>
                      <c:otherwise>
                        <span class="text-muted">-</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${staff.active}">
                        <span class="badge bg-success">Active</span>
                      </c:when>
                      <c:otherwise>
                        <span class="badge bg-secondary">Inactive</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <fmt:formatDate value="${staff.createdDateAsDate}" pattern="MMM dd, yyyy"/>
                  </td>
                  <td>
                    <c:choose>
                      <c:when test="${not empty staff.lastLoginAsDate}">
                        <fmt:formatDate value="${staff.lastLoginAsDate}" pattern="MMM dd, HH:mm"/>
                      </c:when>
                      <c:otherwise>
                        <span class="text-muted">Never</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    <div class="btn-group" role="group">
                      <a href="${pageContext.request.contextPath}/staff/edit?username=${staff.username}"
                         class="btn btn-sm btn-outline-primary" title="Edit">
                        <i class="fas fa-edit"></i>
                      </a>
                      <a href="${pageContext.request.contextPath}/staff/delete?username=${staff.username}"
                         class="btn btn-sm btn-outline-danger" title="Delete"
                         onclick="return confirm('Are you sure you want to delete this staff member?')">
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
            <i class="fas fa-users-cog fa-3x mb-3"></i>
            <h5>No staff members found</h5>
            <p>
              <a href="${pageContext.request.contextPath}/staff/add">Add your first staff member</a>
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