<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Book - Pahana Education</title>
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
                    <h4 class="mb-0"><i class="fas fa-edit"></i> Edit Book</h4>
                </div>
                <div class="card-body">
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">
                            <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                        </div>
                    </c:if>

                    <!-- Book Preview -->
                    <div class="row mb-4">
                        <div class="col-md-3 text-center">
                            <c:choose>
                                <c:when test="${not empty book.imageUrl}">
                                    <img src="${book.imageUrl}" alt="${book.title}"
                                         class="img-fluid" style="max-height: 200px; max-width: 150px;">
                                </c:when>
                                <c:otherwise>
                                    <div class="d-flex align-items-center justify-content-center bg-light"
                                         style="height: 200px; width: 150px; margin: 0 auto;">
                                        <i class="fas fa-book fa-3x text-muted"></i>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col-md-9">
                            <h5>${book.title}</h5>
                            <p class="text-muted mb-1">by ${book.author}</p>
                            <p class="text-muted mb-1">ISBN: ${book.isbn}</p>
                            <c:if test="${not empty book.publisher}">
                                <p class="text-muted">Publisher: ${book.publisher}</p>
                            </c:if>
                        </div>
                    </div>

                    <form method="POST" action="${pageContext.request.contextPath}/books/edit">
                        <input type="hidden" name="isbn" value="${book.isbn}">

                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="isbn" class="form-label">ISBN <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="isbn" name="isbn"
                                           value="${book.isbn}" readonly>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="categoryId" class="form-label">Category</label>
                                    <select class="form-select" id="categoryId" name="categoryId">
                                        <option value="">Select Category</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.id}"
                                                ${category.id == book.categoryId ? 'selected' : ''}>
                                                    ${category.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="title" class="form-label">Title <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="title" name="title"
                                   value="${book.title}" required>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="author" class="form-label">Author <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="author" name="author"
                                           value="${book.author}" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="publisher" class="form-label">Publisher</label>
                                    <input type="text" class="form-control" id="publisher" name="publisher"
                                           value="${book.publisher}">
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description" rows="3">${book.description}</textarea>
                        </div>

                        <div class="mb-3">
                            <label for="imageUrl" class="form-label">Book Cover Image URL</label>
                            <input type="url" class="form-control" id="imageUrl" name="imageUrl"
                                   value="${book.imageUrl}" placeholder="https://example.com/book-cover.jpg">
                            <div class="form-text">Optional: URL to book cover image</div>
                        </div>

                        <div class="row">
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="price" class="form-label">Price ($) <span class="text-danger">*</span></label>
                                    <input type="number" class="form-control" id="price" name="price"
                                           step="0.01" min="0" value="${book.price}" required>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="quantity" class="form-label">Quantity <span class="text-danger">*</span></label>
                                    <input type="number" class="form-control" id="quantity" name="quantity"
                                           required min="0" value="${book.quantity}">
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="minStockLevel" class="form-label">Min Stock Level</label>
                                    <input type="number" class="form-control" id="minStockLevel" name="minStockLevel"
                                           min="0" value="${book.minStockLevel}">
                                    <div class="form-text">Alert when stock falls below this level</div>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="active" name="active"
                                       value="true" ${book.active ? 'checked' : ''}>
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
                                <i class="fas fa-save"></i> Update Book
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