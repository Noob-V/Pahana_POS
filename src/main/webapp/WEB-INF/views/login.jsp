<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login - Pahana Education Bookshop</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <style>
    body {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      min-height: 100vh;
      display: flex;
      align-items: center;
    }
    .login-card {
      backdrop-filter: blur(10px);
      background: rgba(255, 255, 255, 0.95);
      border-radius: 15px;
      box-shadow: 0 8px 32px rgba(31, 38, 135, 0.37);
    }
    .brand-logo {
      color: #667eea;
      font-size: 3rem;
      margin-bottom: 1rem;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="row justify-content-center">
    <div class="col-md-6 col-lg-4">
      <div class="card login-card">
        <div class="card-body p-5">
          <div class="text-center mb-4">
            <i class="fas fa-book brand-logo"></i>
            <h2 class="fw-bold text-primary">Pahana Education</h2>
            <p class="text-muted">Bookshop Management System</p>
          </div>

          <c:if test="${not empty param.message}">
            <div class="alert alert-info">
              <c:choose>
                <c:when test="${param.message == 'logged-out'}">You have been logged out successfully.</c:when>
                <c:when test="${param.message == 'registration-success'}">Account created successfully! Please login with your credentials.</c:when>
                <c:otherwise>${param.message}</c:otherwise>
              </c:choose>
            </div>
          </c:if>

          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                ${errorMessage}
            </div>
          </c:if>

          <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="mb-3">
              <label for="username" class="form-label">
                <i class="fas fa-user"></i> Username
              </label>
              <input type="text" class="form-control" id="username" name="username"
                     required autofocus placeholder="Enter your username">
            </div>

            <div class="mb-4">
              <label for="password" class="form-label">
                <i class="fas fa-lock"></i> Password
              </label>
              <input type="password" class="form-control" id="password" name="password"
                     required placeholder="Enter your password">
            </div>

            <div class="d-grid">
              <button type="submit" class="btn btn-primary btn-lg">
                <i class="fas fa-sign-in-alt"></i> Login
              </button>
            </div>
          </form>

          <div class="text-center mt-3">
            <p class="text-muted">Don't have an account?</p>
            <a href="${pageContext.request.contextPath}/register" class="btn btn-outline-secondary">
              <i class="fas fa-user-plus"></i> Create Account
            </a>
          </div>

          <hr class="my-4">

          <div class="text-center mt-4">
            <small class="text-muted">
              Demo: admin/admin123 or user/user123
            </small>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>