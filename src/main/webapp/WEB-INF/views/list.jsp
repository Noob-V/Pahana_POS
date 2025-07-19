<!-- src/main/webapp/WEB-INF/views/books/list.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Books Management"/>
<jsp:include page="../layout.jsp"/>

<div class="d-flex justify-content-between align-items-center mb-4">
  <h2><i class="fas fa-book"></i> Books Management</h2>
  <div>
    <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary">
      <i class="fas fa-plus"></i> Add Book
    </a>
    <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#googleSearchModal">
      <i class="fab fa-google"></i> Search Google Books
    </button>
  </div>
</div>

<!-- Search Form -->
<div class="row mb-4">
  <div class="col-md-6">
    <form method="get" action="${pageContext.request.contextPath}/books/search" class="d-flex">
      <input type="text" name="q" class="form-control" placeholder="Search books by title or author..."
             value="${searchQuery}" />
      <button type="submit" class="btn btn-outline-primary ms-2">
        <i class="fas fa-search"></i>
      </button>
    </form>
  </div>
  <div class="col-md-6 text-end">
    <c:if test="${not empty searchQuery}">
      <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-secondary">
        <i class="fas fa-times"></i> Clear Search
      </a>
    </c:if>
  </div>
</div>

<!-- Low Stock Alert -->
<c:if test="${not empty lowStockBooks}">
  <div class="alert alert-warning">
    <h5><i class="fas fa-exclamation-triangle"></i> Low Stock Alert</h5>
    <p>The following books are running low on stock:</p>
    <ul class="mb-0">
      <c:forEach var="book" items="${lowStockBooks}">
        <li>${book.title} - Only ${book.quantity} left (Min: ${book.minStockLevel})</li>
      </c:forEach>
    </ul>
  </div>
</c:if>

<!-- Books Table -->
<div class="card">
  <div class="card-body">
    <c:choose>
      <c:when test="${empty books}">
        <div class="text-center py-5">
          <i class="fas fa-book text-muted" style="font-size: 3rem;"></i>
          <h5 class="mt-3 text-muted">No books found</h5>
          <p class="text-muted">
            <c:choose>
              <c:when test="${not empty searchQuery}">
                No books match your search criteria.
              </c:when>
              <c:otherwise>
                Get started by adding your first book or importing from Google Books.
              </c:otherwise>
            </c:choose>
          </p>
        </div>
      </c:when>
      <c:otherwise>
        <div class="table-responsive">
          <table class="table table-hover">
            <thead class="table-primary">
            <tr>
              <th>ISBN</th>
              <th>Title</th>
              <th>Author</th>
              <th>Category</th>
              <th>Price</th>
              <th>Stock</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="book" items="${books}">
              <tr class="${book.quantity <= book.minStockLevel ? 'low-stock' : ''}">
                <td>
                  <code>${book.isbn}</code>
                  <c:if test="${not empty book.googleBookId}">
                    <br><small class="text-muted">
                    <i class="fab fa-google"></i> Google Books
                  </small>
                  </c:if>
                </td>
                <td>
                  <strong>${book.title}</strong>
                  <c:if test="${not empty book.publisher}">
                    <br><small class="text-muted">${book.publisher}</small>
                  </c:if>
                </td>
                <td>${book.author}</td>
                <td>
                  <c:choose>
                    <c:when test="${not empty book.categoryName}">
                      <span class="badge bg-secondary">${book.categoryName}</span>
                    </c:when>
                    <c:otherwise>
                      <span class="text-muted">Uncategorized</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <strong>LKR <fmt:formatNumber value="${book.price}" pattern="#,##0.00"/></strong>
                </td>
                <td>
                                        <span class="badge ${book.quantity <= book.minStockLevel ? 'bg-warning' : 'bg-success'}">
                                            ${book.quantity}
                                        </span>
                  <c:if test="${book.quantity <= book.minStockLevel}">
                    <br><small class="text-warning">Low Stock!</small>
                  </c:if>
                </td>
                <td>
                  <c:choose>
                    <c:when test="${book.active}">
                      <span class="badge bg-success">Active</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge bg-secondary">Inactive</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <div class="btn-group btn-group-sm" role="group">
                    <a href="${pageContext.request.contextPath}/books/edit?isbn=${book.isbn}"
                       class="btn btn-outline-primary" title="Edit">
                      <i class="fas fa-edit"></i>
                    </a>
                    <button type="button" class="btn btn-outline-danger" title="Delete"
                            onclick="confirmDelete('${book.isbn}', '${book.title}')">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</div>

<!-- Google Books Search Modal -->
<div class="modal fade" id="googleSearchModal" tabindex="-1">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">
          <i class="fab fa-google"></i> Search Google Books
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <form id="googleSearchForm">
          <div class="row">
            <div class="col-md-8">
              <input type="text" id="googleQuery" class="form-control"
                     placeholder="Search by title, author, ISBN, or keyword..." required>
            </div>
            <div class="col-md-2">
              <select id="maxResults" class="form-select">
                <option value="10">10 results</option>
                <option value="20">20 results</option>
                <option value="40">40 results</option>
              </select>
            </div>
            <div class="col-md-2">
              <button type="submit" class="btn btn-primary w-100">
                <i class="fas fa-search"></i>
              </button>
            </div>
          </div>
        </form>
        <div id="googleResults" class="mt-4"></div>
      </div>
    </div>
  </div>
</div>

<script>
  function confirmDelete(isbn, title) {
    if (confirm('Are you sure you want to delete "' + title + '"?')) {
      window.location.href = '${pageContext.request.contextPath}/books/delete?isbn=' + encodeURIComponent(isbn);
    }
  }

  document.getElementById('googleSearchForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const query = document.getElementById('googleQuery').value;
    const maxResults = document.getElementById('maxResults').value;
    const resultsDiv = document.getElementById('googleResults');

    resultsDiv.innerHTML = '<div class="text-center"><i class="fas fa-spinner fa-spin"></i> Searching Google Books...</div>';

    fetch('${pageContext.request.contextPath}/books/google-search?q=' + encodeURIComponent(query) + '&maxResults=' + maxResults)
            .then(response => response.text())
            .then(html => {
              resultsDiv.innerHTML = html;
            })
            .catch(error => {
              resultsDiv.innerHTML = '<div class="alert alert-danger">Error searching Google Books: ' + error.message + '</div>';
            });
  });
</script>