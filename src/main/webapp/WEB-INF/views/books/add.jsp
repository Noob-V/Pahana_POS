<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add New Book - Pahana Education</title>
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
      <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-light me-2">
        <i class="fas fa-arrow-left"></i> Back to Books
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
          <h4 class="mb-0"><i class="fas fa-plus"></i> Add New Book</h4>
        </div>
        <div class="card-body">
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
              <i class="fas fa-exclamation-circle"></i> ${errorMessage}
            </div>
          </c:if>

          <form method="POST" action="${pageContext.request.contextPath}/books/add">
            <div class="row">
              <div class="col-md-6">
                <div class="mb-3">
                  <label for="isbn" class="form-label">ISBN <span class="text-danger">*</span></label>
                  <input type="text" class="form-control" id="isbn" name="isbn" required>
                  <div class="form-text">Enter 10 or 13 digit ISBN</div>
                </div>
              </div>
              <div class="col-md-6">
                <div class="mb-3">
                  <label for="categoryId" class="form-label">Category</label>
                  <select class="form-select" id="categoryId" name="categoryId">
                    <option value="">Select Category</option>
                    <c:forEach var="category" items="${categories}">
                      <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="title" class="form-label">Title <span class="text-danger">*</span></label>
              <input type="text" class="form-control" id="title" name="title" required>
            </div>

            <div class="row">
              <div class="col-md-6">
                <div class="mb-3">
                  <label for="author" class="form-label">Author <span class="text-danger">*</span></label>
                  <input type="text" class="form-control" id="author" name="author" required>
                </div>
              </div>
              <div class="col-md-6">
                <div class="mb-3">
                  <label for="publisher" class="form-label">Publisher</label>
                  <input type="text" class="form-control" id="publisher" name="publisher">
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="description" class="form-label">Description</label>
              <textarea class="form-control" id="description" name="description" rows="3"></textarea>
            </div>

            <div class="mb-3">
              <label for="imageUrl" class="form-label">Book Cover Image URL</label>
              <input type="url" class="form-control" id="imageUrl" name="imageUrl"
                     placeholder="https://example.com/book-cover.jpg">
              <div class="form-text">Optional: URL to book cover image</div>
            </div>

            <div class="row">
              <div class="col-md-4">
                <div class="mb-3">
                  <label for="price" class="form-label">Price ($)</label>
                  <input type="number" class="form-control" id="price" name="price" step="0.01" min="0">
                </div>
              </div>
              <div class="col-md-4">
                <div class="mb-3">
                  <label for="quantity" class="form-label">Quantity <span class="text-danger">*</span></label>
                  <input type="number" class="form-control" id="quantity" name="quantity" required min="0" value="1">
                </div>
              </div>
              <div class="col-md-4">
                <div class="mb-3">
                  <label for="minStockLevel" class="form-label">Min Stock Level</label>
                  <input type="number" class="form-control" id="minStockLevel" name="minStockLevel" min="0" value="5">
                  <div class="form-text">Alert when stock falls below this level</div>
                </div>
              </div>
            </div>

            <div class="mb-3">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" id="active" name="active" value="true" checked>
                <label class="form-check-label" for="active">
                  Active (available for circulation)
                </label>
              </div>
            </div>

            <div class="d-flex justify-content-between">
              <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">
                <i class="fas fa-times"></i> Cancel
              </a>
              <button type="submit" class="btn btn-primary">
                <i class="fas fa-save"></i> Add Book
              </button>
            </div>
          </form>
        </div>
      </div>

      <!-- Quick Import Option -->
      <div class="card mt-4">
        <div class="card-header bg-success text-white">
          <h5 class="mb-0"><i class="fas fa-download"></i> Or Import from Google Books</h5>
        </div>
        <div class="card-body">
          <p class="mb-3">Already have an ISBN? Search and import book details automatically from Google Books.</p>
          <a href="${pageContext.request.contextPath}/books/google-search" class="btn btn-success">
            <i class="fas fa-search"></i> Search Google Books
          </a>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>