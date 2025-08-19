<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add Staff Member - Pahana Education</title>
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
    .form-label {
      font-weight: 600;
      color: #495057;
    }
    .required {
      color: #dc3545;
    }
    .form-control:focus, .form-select:focus {
      border-color: #86b7fe;
      box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
    }
    .btn-primary {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
    }
    .btn-primary:hover {
      background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
      transform: translateY(-1px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.15);
    }
    .input-group-text {
      background-color: #f8f9fa;
      border-color: #dee2e6;
      color: #6c757d;
    }
    .password-strength {
      height: 4px;
      transition: all 0.3s ease;
    }
    .form-hint {
      font-size: 0.875rem;
      color: #6c757d;
    }
    .header-gradient {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
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

<div class="container py-4">
  <!-- Header Section -->
  <div class="row mb-4">
    <div class="col-12">
      <div class="d-flex justify-content-between align-items-center">
        <div>
          <h1 class="h3 mb-1">
            <i class="fas fa-user-plus text-primary me-2"></i>Add Staff Member
          </h1>
          <p class="text-muted mb-0">Create a new staff account with login credentials</p>
        </div>
        <a href="${pageContext.request.contextPath}/staff" class="btn btn-outline-secondary">
          <i class="fas fa-arrow-left me-1"></i>Back to Staff List
        </a>
      </div>
    </div>
  </div>

  <!-- Error Display -->
  <c:if test="${not empty errorMessage}">
    <div class="row mb-4">
      <div class="col-12">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
          <i class="fas fa-exclamation-triangle me-2"></i>
          <strong>Error:</strong> ${errorMessage}
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </div>
    </div>
  </c:if>

  <!-- Form Section -->
  <div class="row justify-content-center">
    <div class="col-xl-8 col-lg-10">
      <div class="card">
        <div class="card-header header-gradient">
          <h5 class="card-title mb-0">
            <i class="fas fa-user-cog me-2"></i>Staff Information
          </h5>
        </div>
        <form method="post" action="${pageContext.request.contextPath}/staff/add" novalidate>
          <div class="card-body">
            <!-- Personal Information Section -->
            <div class="row mb-4">
              <div class="col-12">
                <h6 class="text-primary mb-3">
                  <i class="fas fa-user me-2"></i>Personal Information
                </h6>
              </div>
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="fullName" class="form-label">
                  Full Name <span class="required">*</span>
                </label>
                <div class="input-group">
                  <span class="input-group-text">
                    <i class="fas fa-user"></i>
                  </span>
                  <input type="text" class="form-control" id="fullName" name="fullName" required>
                </div>
                <div class="form-hint">Enter the staff member's complete name</div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="email" class="form-label">
                  Email Address <span class="required">*</span>
                </label>
                <div class="input-group">
                  <span class="input-group-text">
                    <i class="fas fa-envelope"></i>
                  </span>
                  <input type="email" class="form-control" id="email" name="email" required>
                </div>
                <div class="form-hint">This will be used for login and notifications</div>
              </div>
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="phone" class="form-label">
                  Phone Number
                </label>
                <div class="input-group">
                  <span class="input-group-text">
                    <i class="fas fa-phone"></i>
                  </span>
                  <input type="tel" class="form-control" id="phone" name="phone">
                </div>
                <div class="form-hint">Contact number for staff member</div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="role" class="form-label">
                  Role <span class="required">*</span>
                </label>
                <div class="input-group">
                  <span class="input-group-text">
                    <i class="fas fa-user-tag"></i>
                  </span>
                  <select class="form-select" id="role" name="role" required>
                    <option value="">Select Role</option>
                    <option value="STAFF">Staff - Can view and manage customers</option>
                    <option value="ADMIN">Admin - Full system access</option>
                  </select>
                </div>
                <div class="form-hint">Choose appropriate access level</div>
              </div>
            </div>

            <!-- Account Credentials Section -->
            <div class="row mb-4 mt-4">
              <div class="col-12">
                <hr>
                <h6 class="text-primary mb-3">
                  <i class="fas fa-key me-2"></i>Account Credentials
                </h6>
              </div>
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="username" class="form-label">
                  Username <span class="required">*</span>
                </label>
                <div class="input-group">
                  <span class="input-group-text">
                    <i class="fas fa-at"></i>
                  </span>
                  <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="form-hint">Unique identifier for login</div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="password" class="form-label">
                  Password <span class="required">*</span>
                </label>
                <div class="input-group">
                  <span class="input-group-text">
                    <i class="fas fa-lock"></i>
                  </span>
                  <input type="password" class="form-control" id="password" name="password" required>
                  <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                    <i class="fas fa-eye"></i>
                  </button>
                </div>
                <div class="password-strength" id="passwordStrength"></div>
                <div class="form-hint">Minimum 8 characters with mixed case and numbers</div>
              </div>
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="confirmPassword" class="form-label">
                  Confirm Password <span class="required">*</span>
                </label>
                <div class="input-group">
                  <span class="input-group-text" id="passwordMatch">
                    <i class="fas fa-times text-danger"></i>
                  </span>
                  <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                </div>
                <div class="form-hint">Re-enter the password to confirm</div>
              </div>
            </div>

            <!-- Account Settings Section -->
            <div class="row mb-4 mt-4">
              <div class="col-12">
                <hr>
                <h6 class="text-primary mb-3">
                  <i class="fas fa-cog me-2"></i>Account Settings
                </h6>
              </div>
            </div>

            <div class="row">
              <div class="col-md-6 mb-3">
                <div class="form-check form-switch">
                  <input class="form-check-input" type="checkbox" id="active" name="active" checked>
                  <label class="form-check-label" for="active">
                    <strong>Active Account</strong>
                  </label>
                </div>
                <div class="form-hint">Enable login access for this staff member</div>
              </div>
            </div>
          </div>

          <div class="card-footer bg-light">
            <div class="d-flex justify-content-end gap-2">
              <a href="${pageContext.request.contextPath}/staff" class="btn btn-outline-secondary">
                <i class="fas fa-times me-1"></i>Cancel
              </a>
              <button type="submit" class="btn btn-primary">
                <i class="fas fa-save me-1"></i>Create Staff Account
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Password visibility toggle
  document.getElementById('togglePassword').addEventListener('click', function() {
    const password = document.getElementById('password');
    const icon = this.querySelector('i');

    if (password.type === 'password') {
      password.type = 'text';
      icon.className = 'fas fa-eye-slash';
    } else {
      password.type = 'password';
      icon.className = 'fas fa-eye';
    }
  });

  // Password strength indicator
  document.getElementById('password').addEventListener('input', function() {
    const password = this.value;
    const strengthBar = document.getElementById('passwordStrength');

    let strength = 0;
    if (password.length >= 8) strength++;
    if (password.match(/[a-z]/)) strength++;
    if (password.match(/[A-Z]/)) strength++;
    if (password.match(/[0-9]/)) strength++;
    if (password.match(/[^a-zA-Z0-9]/)) strength++;

    const colors = ['', '#dc3545', '#fd7e14', '#ffc107', '#198754', '#20c997'];
    const widths = ['0%', '20%', '40%', '60%', '80%', '100%'];

    strengthBar.style.backgroundColor = colors[strength];
    strengthBar.style.width = widths[strength];
  });

  // Password confirmation check
  function checkPasswordMatch() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const matchIcon = document.getElementById('passwordMatch').querySelector('i');

    if (confirmPassword === '') {
      matchIcon.className = 'fas fa-times text-danger';
    } else if (password === confirmPassword) {
      matchIcon.className = 'fas fa-check text-success';
    } else {
      matchIcon.className = 'fas fa-times text-danger';
    }
  }

  document.getElementById('password').addEventListener('input', checkPasswordMatch);
  document.getElementById('confirmPassword').addEventListener('input', checkPasswordMatch);

  // Form validation
  (function() {
    'use strict';
    window.addEventListener('load', function() {
      const forms = document.getElementsByTagName('form');
      Array.prototype.filter.call(forms, function(form) {
        form.addEventListener('submit', function(event) {
          if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
          }
          form.classList.add('was-validated');
        }, false);
      });
    }, false);
  })();
</script>
</body>
</html>