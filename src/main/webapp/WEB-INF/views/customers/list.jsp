<!-- src/main/webapp/WEB-INF/views/customers/list.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Customer Management"/>
<jsp:include page="../layout.jsp"/>

<div class="d-flex justify-content-between align-items-center mb-4">
  <h2><i class="fas fa-users"></i> Customer Management</h2>
  <a href="${pageContext.request.contextPath}/customers/add" class="btn btn-primary">
    <i class="fas fa-plus"></i> Add Customer
  </a>
</div>

<!-- Search Form -->
<div class="row mb-4">
  <div class="col-md-6">
    <form method="get" action="${pageContext.request.contextPath}/customers/search" class="d-flex">
      <input type="text" name="q" class="form-control" placeholder="Search customers by name..."
             value="${searchQuery}" />
      <button type="submit" class="btn btn-outline-primary ms-2">
        <i class="fas fa-search"></i>
      </button>
    </form>
  </div>
  <div class="col-md-6 text-end">
    <c:if test="${not empty searchQuery}">
      <a href="${pageContext.request.contextPath}/customers" class="btn btn-outline-secondary">
        <i class="fas fa-times"></i> Clear Search
      </a>
    </c:if>
  </div>
</div>

<!-- Customers Table -->
<div class="card">
  <div class="card-body">
    <c:choose>
      <c:when test="${empty customers}">
        <div class="text-center py-5">
          <i class="fas fa-users text-muted" style="font-size: 3rem;"></i>
          <h5 class="mt-3 text-muted">No customers found</h5>
          <p class="text-muted">Start by adding your first customer.</p>
        </div>
      </c:when>
      <c:otherwise>
        <div class="table-responsive">
          <table class="table table-hover">
            <thead>
            <tr>
              <th>Account Number</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>City</th>
              <th>Created Date</th>
              <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="customer" items="${customers}">
              <tr>
                <td><strong>${customer.accountNumber}</strong></td>
                <td>${customer.name}</td>
                <td>
                  <c:if test="${not empty customer.email}">
                    <a href="mailto:${customer.email}">${customer.email}</a>
                  </c:if>
                </td>
                <td>
                  <c:if test="${not empty customer.phone}">
                    <a href="tel:${customer.phone}">${customer.phone}</a>
                  </c:if>
                </td>
                <td>${customer.city}</td>
                <td>
                  <fmt:formatDate value="${customer.createdDate}" pattern="MMM dd, yyyy"/>
                </td>
                <td>
                  <div class="btn-group btn-group-sm">
                    <a href="${pageContext.request.contextPath}/customers/edit?accountNumber=${customer.accountNumber}"
                       class="btn btn-outline-primary">
                      <i class="fas fa-edit"></i>
                    </a>
                    <button class="btn btn-outline-danger"
                            onclick="confirmDelete('${customer.accountNumber}', '${customer.name}')">
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

<script>
  function confirmDelete(accountNumber, name) {
    if (confirm('Are you sure you want to delete customer "' + name + '"?')) {
      window.location.href = '${pageContext.request.contextPath}/customers/delete?accountNumber=' + encodeURIComponent(accountNumber);
    }
  }
</script>