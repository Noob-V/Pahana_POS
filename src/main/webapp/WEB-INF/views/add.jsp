<!-- src/main/webapp/WEB-INF/views/books/add.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Add Book"/>
<jsp:include page="../layout.jsp"/>

<div class="row">
  <div class="col-md-8">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2><i class="fas fa-plus"></i> Add New Book</h2>
      <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-secondary">
        <i class="fas fa-arrow-left"></i> Back to Books
      </a>
    </div>

    <div class="card">
      <div class="card-body">
        <form method="post" action="${pageContext.request.contextPath}/books/add">
          <div class="row">
            <div class="col-md-6">
              <div class="mb-3">
                <label for="isbn" class="form-label">ISBN *</label>
                <input type="text" class="form-control" id="isbn" name="isbn" required>
                <div class="form-text">
                  Enter ISBN-10 or ISBN-13.
                  <button type="button" class="btn btn-link btn-sm p-0" onclick="searchByIsbn()">
                    Search Google Books by ISBN
                  </button>
                </div>
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
            <label for="title" class="form-label">Title *</label>
            <input type="text" class="form-control" id="title" name="title" required>
          </div>

          <div class="mb-3">
            <label for="author" class="form-label">Author *</label>
            <input type="text" class="form-control" id="author" name="author" required>
          </div>

          <div class="mb-3">
            <label for="publisher" class="form-label">Publisher</label>
            <input type="text" class="form-control" id="publisher" name="publisher">
          </div>

          <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3"></textarea>
          </div>

          <div class="row">
            <div class="col-md-4">
              <div class="mb-3">
                <label for="price" class="form-label">Price (LKR) *</label>
                <input type="number" class="form-control" id="price" name="price"
                       step="0.01" min="0" required>
              </div>
            </div>
            <div class="col-md-4">
              <div class="mb-3">
                <label for="quantity" class="form-label">Quantity *</label>
                <input type="number" class="form-control" id="quantity" name="quantity"
                       min="0" required>
              </div>
            </div>
            <div class="col-md-4">
              <div class="mb-3">
                <label for="minStockLevel" class="form-label">Min Stock Level *</label>
                <input type="number" class="form-control" id="minStockLevel" name="minStockLevel"
                       min="0" value="5" required>
              </div>
            </div>
          </div>

          <div class="mb-3">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" id="active" name="active"
                     value="true" checked>
              <label class="form-check-label" for="active">
                Active (available for sale)
              </label>
            </div>
          </div>

          <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-save"></i> Save Book
            </button>
            <button type="reset" class="btn btn-outline-secondary">
              <i class="fas fa-undo"></i> Reset
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <div class="col-md-4">
    <div class="card">
      <div class="card-header">
        <h6><i class="fab fa-google"></i> Quick Import from Google Books</h6>
      </div>
      <div class="card-body">
        <div class="mb-3">
          <input type="text" id="quickSearch" class="form-control"
                 placeholder="Search title, author, or ISBN...">
        </div>
        <button class="btn btn-success btn-sm w-100" onclick="quickGoogleSearch()">
          <i class="fas fa-search"></i> Search & Import
        </button>
        <div id="quickResults" class="mt-3"></div>
      </div>
    </div>

    <div class="card mt-3">
      <div class="card-header">
        <h6><i class="fas fa-info-circle"></i> Tips</h6>
      </div>
      <div class="card-body">
        <ul class="small">
          <li>ISBN is required and must be unique</li>
          <li>Use Google Books search to auto-fill book details</li>
          <li>Set appropriate minimum stock levels for alerts</li>
          <li>Inactive books won't appear in sales</li>
        </ul>
      </div>
    </div>
  </div>
</div>
<!-- Complete the form content in add.jsp -->
<form method="post" action="${pageContext.request.contextPath}/books/add">
  <div class="row">
    <div class="col-md-6">
      <div class="mb-3">
        <label for="isbn" class="form-label">ISBN *</label>
        <div class="input-group">
          <input type="text" class="form-control" id="isbn" name="isbn" required>
          <button type="button" class="btn btn-outline-primary" onclick="searchByIsbn()">
            <i class="fas fa-search"></i>
          </button>
        </div>
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
    <label for="title" class="form-label">Title *</label>
    <input type="text" class="form-control" id="title" name="title" required>
  </div>

  <div class="mb-3">
    <label for="author" class="form-label">Author *</label>
    <input type="text" class="form-control" id="author" name="author" required>
  </div>

  <div class="mb-3">
    <label for="publisher" class="form-label">Publisher</label>
    <input type="text" class="form-control" id="publisher" name="publisher">
  </div>

  <div class="mb-3">
    <label for="description" class="form-label">Description</label>
    <textarea class="form-control" id="description" name="description" rows="3"></textarea>
  </div>

  <div class="row">
    <div class="col-md-4">
      <div class="mb-3">
        <label for="price" class="form-label">Price *</label>
        <div class="input-group">
          <span class="input-group-text">$</span>
          <input type="number" class="form-control" id="price" name="price" step="0.01" min="0" required>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="mb-3">
        <label for="quantity" class="form-label">Quantity *</label>
        <input type="number" class="form-control" id="quantity" name="quantity" min="0" required>
      </div>
    </div>
    <div class="col-md-4">
      <div class="mb-3">
        <label for="minStockLevel" class="form-label">Min Stock Level</label>
        <input type="number" class="form-control" id="minStockLevel" name="minStockLevel" min="0" value="5">
      </div>
    </div>
  </div>

  <div class="mb-3">
    <div class="form-check">
      <input class="form-check-input" type="checkbox" id="active" name="active" value="true" checked>
      <label class="form-check-label" for="active">
        Active (available for sale)
      </label>
    </div>
  </div>

  <div class="d-flex gap-2">
    <button type="submit" class="btn btn-primary">
      <i class="fas fa-save"></i> Save Book
    </button>
    <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
  </div>
</form>

<script>
  function searchByIsbn() {
    const isbn = document.getElementById('isbn').value;
    if (!isbn) {
      alert('Please enter an ISBN first');
      return;
    }

    fetch('${pageContext.request.contextPath}/books/google-search?q=isbn:' + encodeURIComponent(isbn) + '&maxResults=1')
            .then(response => response.text())
            .then(html => {
              if (html.includes('No books found')) {
                alert('No book found with this ISBN on Google Books');
              } else {
                document.getElementById('quickResults').innerHTML = html;
              }
            })
            .catch(error => alert('Error searching: ' + error.message));
  }

  function quickGoogleSearch() {
    const query = document.getElementById('quickSearch').value;
    if (!query) {
      alert('Please enter a search term');
      return;
    }

    document.getElementById('quickResults').innerHTML = '<div class="text-center"><i class="fas fa-spinner fa-spin"></i> Searching...</div>';

    fetch('${pageContext.request.contextPath}/books/google-search?q=' + encodeURIComponent(query) + '&maxResults=5')
            .then(response => response.text())
            .then(html => {
              document.getElementById('quickResults').innerHTML = html;
            })
            .catch(error => {
              document.getElementById('quickResults').innerHTML = '<div class="alert alert-danger">Error: ' + error.message + '</div>';
            });
  }
</script>