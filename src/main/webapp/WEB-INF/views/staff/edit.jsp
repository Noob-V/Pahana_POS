<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Staff - Pahana Education</title>
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
      <a href="${pageContext.request.contextPath}/staff" class="btn btn-outline-light me-2">
        <i class="fas fa-arrow-left"></i> Back to Staff
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
          <h4 class="mb-0"><i class="fas fa-user-edit"></i> Edit Staff Member</h4>
        </div>
        <div class="card-body">
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
              <i class="fas fa-exclamation-circle"></i> ${errorMessage}
            </div>
          </c:if>

          <div class="alert alert-info">
            <strong>Staff ID:</strong> ${staff.id} |
            <strong>Member Since:</strong>
            <fmt:formatDate value="${staff.createdDateAsDate}" pattern="MMM dd, yyyy"/>
          </div>

          <form method="POST" action="${pageContext.request.contextPath}/staff/edit">
            <input type="hidden" name="id" value="${staff.id}">
            <input type="hidden" name="username" value="${staff.username}">

            <div class="mb-3">
              <label for="username" class="form-label">Username</label>
              <input type="text" class="form-control" id="username" value="${staff.username}" disabled>
              <div class="form-text">Username cannot be changed</div>
            </div>

            <div class="mb-3">
              <label for="fullName" class="form-label">Full Name <span class="text-danger">*</span></label>
              <input type="text" class="form-control" id="fullName" name="fullName"
                     value="${staff.fullName}" required>
            </div>

            <div class="mb-3">
              <label for="email" class="form-label">Email Address <span class="text-danger">*</span></label>
              <input type="email" class="form-control" id="email" name="email"
                     value="${staff.email}" required>
            </div>

            <div class="mb-3">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" id="active" name="active"
                       value="true" ${staff.active ? 'checked' : ''}>
                <label class="form-check-label" for="active">
                  Active Staff
                </label>
                <div class="form-text">
                  <c:choose>
                    <c:when test="${staff.active}">
                      Staff member is currently active
                    </c:when>
                    <c:otherwise>
                      Staff member is inactive - check this box to reactivate
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
            </div>

            <div class="d-flex justify-content-between">
              <a href="${pageContext.request.contextPath}/staff" class="btn btn-secondary">
                <i class="fas fa-times"></i> Cancel
              </a>
              <button type="submit" class="btn btn-primary">
                <i class="fas fa-save"></i> Update Staff
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