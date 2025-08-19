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
            <i class="fas fa-users-cog text-primary me-2"></i>Staff Management
          </h1>
          <p class="text-muted mb-0">Manage your staff members and their access levels</p>
        </div>
        <div class="d-flex gap-2">
          <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline-secondary">
            <i class="fas fa-tachometer-alt me-1"></i>Dashboard
          </a>
          <a href="${pageContext.request.contextPath}/staff/add" class="btn btn-primary">
            <i class="fas fa-plus me-1"></i>Add Staff
          </a>
        </div>
      </div>
    </div>
  </div>

  <!-- Alerts Section -->
  <div class="row mb-4">
    <div class="col-12">
      <c:if test="${param.message == 'staff-added'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="fas fa-check-circle me-2"></i>
          <strong>Success!</strong> Staff member added successfully!
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>
      <c:if test="${param.message == 'staff-updated'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="fas fa-check-circle me-2"></i>
          <strong>Success!</strong> Staff member updated successfully!
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>
      <c:if test="${param.message == 'staff-deleted'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="fas fa-check-circle me-2"></i>
          <strong>Success!</strong> Staff member deleted successfully!
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

  <!-- Staff List Section -->
  <div class="row">
    <div class="col-12">
      <div class="card">
        <div class="card-header bg-white border-bottom">
          <div class="d-flex justify-content-between align-items-center">
            <h5 class="card-title mb-0">
              <i class="fas fa-list me-2 text-primary"></i>Staff Members
            </h5>
            <span class="badge bg-info fs-6">${staffList.size()} total</span>
          </div>
        </div>
        <div class="card-body p-0">
          <c:choose>
            <c:when test="${not empty staffList}">
              <div class="table-responsive">
                <table class="table table-hover mb-0">
                  <thead class="table-dark">
                  <tr>
                    <th scope="col" class="ps-4">
                      <i class="fas fa-hashtag me-1"></i>ID
                    </th>
                    <th scope="col">
                      <i class="fas fa-user me-1"></i>Username
                    </th>
                    <th scope="col">
                      <i class="fas fa-id-card me-1"></i>Full Name
                    </th>
                    <th scope="col">
                      <i class="fas fa-envelope me-1"></i>Email
                    </th>
                    <th scope="col">
                      <i class="fas fa-toggle-on me-1"></i>Status
                    </th>
                    <th scope="col">
                      <i class="fas fa-calendar-plus me-1"></i>Created
                    </th>
                    <th scope="col">
                      <i class="fas fa-clock me-1"></i>Last Login
                    </th>
                    <th scope="col" class="pe-4">
                      <i class="fas fa-cogs me-1"></i>Actions
                    </th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="staff" items="${staffList}" varStatus="status">
                    <tr>
                      <td class="ps-4">
                        <span class="fw-bold text-primary">#${staff.id}</span>
                      </td>
                      <td>
                        <div class="d-flex align-items-center">
                          <div class="avatar bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-2"
                               style="width: 32px; height: 32px; font-size: 12px;">
                              ${staff.username.substring(0,1).toUpperCase()}
                          </div>
                          <code class="bg-light px-2 py-1 rounded">${staff.username}</code>
                        </div>
                      </td>
                      <td>
                        <span class="fw-medium">${staff.fullName}</span>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${not empty staff.email}">
                            <a href="mailto:${staff.email}" class="text-decoration-none">
                              <i class="fas fa-envelope-open-text me-1"></i>${staff.email}
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
                          <c:when test="${staff.active}">
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
                          <fmt:formatDate value="${staff.createdDateAsDate}" pattern="MMM dd, yyyy"/>
                        </div>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${not empty staff.lastLoginAsDate}">
                            <div class="text-success small">
                              <i class="fas fa-sign-in-alt me-1"></i>
                              <fmt:formatDate value="${staff.lastLoginAsDate}" pattern="MMM dd, HH:mm"/>
                            </div>
                          </c:when>
                          <c:otherwise>
                            <span class="text-muted small fst-italic">
                              <i class="fas fa-user-clock me-1"></i>Never logged in
                            </span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td class="pe-4">
                        <div class="btn-group" role="group">
                          <a href="${pageContext.request.contextPath}/staff/edit?username=${staff.username}"
                             class="btn btn-sm btn-outline-primary"
                             title="Edit staff member"
                             data-bs-toggle="tooltip">
                            <i class="fas fa-edit"></i>
                          </a>
                          <a href="${pageContext.request.contextPath}/staff/delete?username=${staff.username}"
                             class="btn btn-sm btn-outline-danger"
                             title="Delete staff member"
                             data-bs-toggle="tooltip"
                             onclick="return confirm('Are you sure you want to delete ${staff.fullName}? This action cannot be undone.')">
                            <i class="fas fa-trash-alt"></i>
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
              <div class="text-center text-muted empty-state">
                <i class="fas fa-users-cog fa-4x mb-3"></i>
                <h4 class="mb-3">No Staff Members Found</h4>
                <p class="mb-4">Get started by adding your first staff member to the system.</p>
                <a href="${pageContext.request.contextPath}/staff/add" class="btn btn-primary btn-lg">
                  <i class="fas fa-plus me-2"></i>Add First Staff Member
                </a>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
        <c:if test="${not empty staffList && staffList.size() > 10}">
          <div class="card-footer bg-light text-center">
            <small class="text-muted">
              Showing ${staffList.size()} staff members
            </small>
          </div>
        </c:if>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Initialize tooltips
  var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
  })
</script>
</body>
</html>