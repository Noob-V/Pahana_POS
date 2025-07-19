<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Pahana Education Bookshop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }
        .register-card {
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
        <div class="col-md-6 col-lg-5">
            <div class="card register-card">
                <div class="card-body p-5">
                    <div class="text-center mb-4">
                        <i class="fas fa-book brand-logo"></i>
                        <h2 class="fw-bold text-primary">Create Account</h2>
                        <p class="text-muted">Join Pahana Education Bookshop</p>
                    </div>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">
                                ${errorMessage}
                        </div>
                    </c:if>

                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success">
                                ${successMessage}
                        </div>
                    </c:if>

                    <form method="post" action="${pageContext.request.contextPath}/register" id="registerForm">
                        <div class="mb-3">
                            <label for="fullName" class="form-label">
                                <i class="fas fa-user"></i> Full Name
                            </label>
                            <input type="text" class="form-control" id="fullName" name="fullName"
                                   required placeholder="Enter your full name" value="${param.fullName}">
                        </div>

                        <div class="mb-3">
                            <label for="username" class="form-label">
                                <i class="fas fa-user-circle"></i> Username
                            </label>
                            <input type="text" class="form-control" id="username" name="username"
                                   required placeholder="Choose a username" value="${param.username}">
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">
                                <i class="fas fa-envelope"></i> Email
                            </label>
                            <input type="email" class="form-control" id="email" name="email"
                                   required placeholder="Enter your email" value="${param.email}">
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">
                                <i class="fas fa-lock"></i> Password
                            </label>
                            <input type="password" class="form-control" id="password" name="password"
                                   required placeholder="Create a password" minlength="6">
                            <small class="form-text text-muted">Password must be at least 6 characters long</small>
                        </div>

                        <div class="mb-4">
                            <label for="confirmPassword" class="form-label">
                                <i class="fas fa-lock"></i> Confirm Password
                            </label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                                   required placeholder="Confirm your password">
                            <div class="invalid-feedback">
                                Passwords do not match
                            </div>
                        </div>

                        <div class="d-grid mb-3">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-user-plus"></i> Create Account
                            </button>
                        </div>
                    </form>

                    <div class="text-center">
                        <p class="text-muted">Already have an account?</p>
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-primary">
                            <i class="fas fa-sign-in-alt"></i> Login Here
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Password confirmation validation
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password !== confirmPassword) {
            e.preventDefault();
            document.getElementById('confirmPassword').classList.add('is-invalid');
        } else {
            document.getElementById('confirmPassword').classList.remove('is-invalid');
        }
    });

    // Real-time password matching feedback
    document.getElementById('confirmPassword').addEventListener('input', function() {
        const password = document.getElementById('password').value;
        const confirmPassword = this.value;

        if (confirmPassword && password !== confirmPassword) {
            this.classList.add('is-invalid');
        } else {
            this.classList.remove('is-invalid');
        }
    });
</script>
</body>
</html>