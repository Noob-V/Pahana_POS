<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Import from Google Books - Pahana Education</title>
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
  <div class="card">
    <div class="card-header bg-success text-white">
      <h4 class="mb-0"><i class="fas fa-download"></i> Import Books from Google Books</h4>
    </div>
    <div class="card-body">
      <!-- Search Form -->
      <form method="GET" action="${pageContext.request.contextPath}/books/google-search" class="mb-4">
        <div class="row">
          <div class="col-md-8">
            <input type="text" class="form-control" name="q" placeholder="Search by title, author, or ISBN..."
                   value="${searchQuery}" required>
          </div>
          <div class="col-md-2">
            <select class="form-select" name="maxResults">
              <option value="10">10 results</option>
              <option value="20">20 results</option>
              <option value="40">40 results</option>
            </select>
          </div>
          <div class="col-md-2">
            <button type="submit" class="btn btn-success w-100">
              <i class="fas fa-search"></i> Search
            </button>
          </div>
        </div>
      </form>

      <!-- Search Results with Images -->
      <c:if test="${not empty googleBooks}">
        <h5>Search Results for "${searchQuery}":</h5>
        <div class="row">
          <c:forEach var="book" items="${googleBooks}">
            <div class="col-md-6 col-lg-4 mb-4">
              <div class="card h-100">
                <!-- Book Cover Image -->
                <div class="text-center p-3" style="background-color: #f8f9fa;">
                  <c:choose>
                    <c:when test="${not empty book.imageUrl}">
                      <img src="${book.imageUrl}" alt="${book.title}"
                           class="img-fluid" style="max-height: 200px; max-width: 150px;">
                    </c:when>
                    <c:otherwise>
                      <div class="d-flex align-items-center justify-content-center"
                           style="height: 200px; background-color: #e9ecef; border-radius: 4px;">
                        <i class="fas fa-book fa-3x text-muted"></i>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>

                <div class="card-body">
                  <h6 class="card-title">${book.title}</h6>
                  <p class="card-text">
                    <small class="text-muted">by ${book.author}</small><br>
                    <c:if test="${not empty book.publisher}">
                      <small class="text-muted">Published by ${book.publisher}</small><br>
                    </c:if>
                    <c:if test="${not empty book.isbn}">
                      <small class="font-monospace">ISBN: ${book.isbn}</small><br>
                    </c:if>
                  </p>
                  <c:if test="${not empty book.description}">
                    <p class="card-text">
                      <small>${book.description.length() > 100 ?
                              book.description.substring(0, 100).concat("...") :
                              book.description}
                      </small>
                    </p>
                  </c:if>
                </div>

                <div class="card-footer">
                  <form method="POST" action="${pageContext.request.contextPath}/books/import">
                    <input type="hidden" name="googleId" value="${book.googleBookId}">
                    <input type="hidden" name="isbn" value="${book.isbn}">
                    <input type="hidden" name="title" value="${book.title}">
                    <input type="hidden" name="author" value="${book.author}">
                    <input type="hidden" name="publisher" value="${book.publisher}">
                    <input type="hidden" name="description" value="${book.description}">
                    <input type="hidden" name="price" value="${book.price}">
                    <input type="hidden" name="imageUrl" value="${book.imageUrl}">

                    <div class="row mb-2">
                      <div class="col-6">
                        <input type="number" class="form-control form-control-sm"
                               name="quantity" placeholder="Quantity" value="1" min="1" required>
                      </div>
                      <div class="col-6">
                        <select class="form-select form-select-sm" name="categoryId">
                          <option value="">Category</option>
                          <c:forEach var="category" items="${categories}">
                            <option value="${category.id}">${category.name}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>

                    <button type="submit" class="btn btn-success btn-sm w-100">
                      <i class="fas fa-download"></i> Import Book
                    </button>
                  </form>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
      </c:if>

      <c:if test="${not empty searchQuery && empty googleBooks}">
        <div class="text-center py-5">
          <i class="fas fa-search fa-3x text-muted mb-3"></i>
          <h5>No books found</h5>
          <p class="text-muted">No books found for "${searchQuery}". Try different search terms.</p>
        </div>
      </c:if>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>