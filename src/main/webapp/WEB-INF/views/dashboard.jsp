<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - Pahana Education</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h1>Welcome to Dashboard</h1>
  <p>Hello, ${sessionScope.currentUser.fullName}!</p>
  <p>Role: ${sessionScope.userRole}</p>

  <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">Logout</a>
</div>
</body>
</html>