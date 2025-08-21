<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Error - Pahana Education</title>
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
      <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline-light">
        <i class="fas fa-home"></i> Dashboard
      </a>
    </div>
  </div>
</nav>

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card border-danger">
        <div class="card-header bg-danger text-white">
          <h4 class="mb-0"><i class="fas fa-exclamation-triangle"></i> Error</h4>
        </div>
        <div class="card-body">
          <div class="text-center mb-4">
            <i class="fas fa-exclamation-circle fa-5x text-danger mb-3"></i>
            <h3>Oops! Something went wrong</h3>
          </div>

          <c:choose>
            <c:when test="${not empty errorMessage}">
              <div class="alert alert-danger">
                <strong>Error Details:</strong><br>
                  ${errorMessage}
              </div>
            </c:when>
            <c:otherwise>
              <div class="alert alert-warning">
                An unexpected error occurred. Please try again or contact support if the problem persists.
              </div>
            </c:otherwise>
          </c:choose>

          <div class="text-center">
            <a href="javascript:history.back()" class="btn btn-secondary me-2">
              <i class="fas fa-arrow-left"></i> Go Back
            </a>
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">
              <i class="fas fa-home"></i> Go to Dashboard
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>