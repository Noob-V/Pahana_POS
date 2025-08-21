<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Dashboard - PahanaEdu</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/chart.js@3.9.1/dist/chart.min.css">
  <style>
    .sidebar {
      min-height: 100vh;
      background: linear-gradient(180deg, #2c3e50 0%, #3498db 100%);
    }
    .sidebar .nav-link {
      color: rgba(255,255,255,0.8);
      padding: 12px 20px;
      border-radius: 8px;
      margin: 4px 0;
      transition: all 0.3s;
    }
    .sidebar .nav-link:hover,
    .sidebar .nav-link.active {
      color: white;
      background-color: rgba(255,255,255,0.1);
    }
    .stat-card {
      background: white;
      border-radius: 15px;
      padding: 25px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.08);
      border: none;
      transition: transform 0.3s;
    }
    .stat-card:hover {
      transform: translateY(-5px);
    }
    .stat-icon {
      width: 60px;
      height: 60px;
      border-radius: 15px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
      color: white;
    }
    .chart-container {
      position: relative;
      height: 300px;
    }
  </style>
</head>
<body class="bg-light">

<div class="container-fluid">
  <div class="row">
    <!-- Sidebar -->
    <div class="col-md-2 sidebar p-0">
      <div class="p-3">
        <h4 class="text-white mb-4">
          <i class="fas fa-tachometer-alt me-2"></i>
          Admin Panel
        </h4>
        <nav class="nav flex-column">
          <a class="nav-link active" href="${pageContext.request.contextPath}/admin/dashboard">
            <i class="fas fa-home me-2"></i> Dashboard
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/books">
            <i class="fas fa-book me-2"></i> Books Management
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/customers">
            <i class="fas fa-users me-2"></i> Customers
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/staff">
            <i class="fas fa-user-tie me-2"></i> Staff Management
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/billing/pos">
            <i class="fas fa-cash-register me-2"></i> Point of Sale
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/admin/analytics">
            <i class="fas fa-chart-bar me-2"></i> Analytics
          </a>
          <hr class="text-white">
          <div class="dropdown">
            <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
              <i class="fas fa-chart-line me-2"></i> Reports
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/reports/sales">Sales Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/reports/inventory">Inventory Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/reports/customers">Customer Report</a></li>
            </ul>
          </div>
          <a class="nav-link" href="${pageContext.request.contextPath}/admin/system/settings">
            <i class="fas fa-cog me-2"></i> System Settings
          </a>
          <hr class="text-white">
          <a class="nav-link" href="${pageContext.request.contextPath}/logout">
            <i class="fas fa-sign-out-alt me-2"></i> Logout
          </a>
        </nav>
      </div>
    </div>

    <!-- Main Content -->
    <div class="col-md-10">
      <div class="p-4">
        <!-- Header -->
        <div class="d-flex justify-content-between align-items-center mb-4">
          <div>
            <h2>Admin Dashboard</h2>
            <p class="text-muted mb-0">Welcome back! Here's what's happening with your store.</p>
          </div>
          <div class="text-end">
            <span class="text-muted">Last updated: </span>
            <span class="fw-bold"><fmt:formatDate value="<%= new java.util.Date() %>" pattern="MMM dd, yyyy HH:mm"/></span>
          </div>
        </div>

        <!-- Statistics Cards -->
        <div class="row mb-4">
          <div class="col-xl-3 col-md-6 mb-4">
            <div class="stat-card">
              <div class="d-flex align-items-center">
                <div class="stat-icon bg-primary">
                  <i class="fas fa-dollar-sign"></i>
                </div>
                <div class="ms-3 flex-grow-1">
                  <div class="fw-bold text-primary">Today's Sales</div>
                  <div class="h4 mb-0">$<fmt:formatNumber value="${stats.todaySales}" pattern="#,##0.00"/></div>
                  <small class="text-success"><i class="fas fa-arrow-up"></i> +12.5% from yesterday</small>
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-3 col-md-6 mb-4">
            <div class="stat-card">
              <div class="d-flex align-items-center">
                <div class="stat-icon bg-success">
                  <i class="fas fa-shopping-cart"></i>
                </div>
                <div class="ms-3 flex-grow-1">
                  <div class="fw-bold text-success">Orders Today</div>
                  <div class="h4 mb-0">${stats.ordersToday}</div>
                  <small class="text-success"><i class="fas fa-arrow-up"></i> +8.2% from yesterday</small>
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-3 col-md-6 mb-4">
            <div class="stat-card">
              <div class="d-flex align-items-center">
                <div class="stat-icon bg-info">
                  <i class="fas fa-book"></i>
                </div>
                <div class="ms-3 flex-grow-1">
                  <div class="fw-bold text-info">Total Books</div>
                  <div class="h4 mb-0">${stats.totalBooks}</div>
                  <small class="text-muted">Total Inventory: ${stats.totalInventory}</small>
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-3 col-md-6 mb-4">
            <div class="stat-card">
              <div class="d-flex align-items-center">
                <div class="stat-icon bg-warning">
                  <i class="fas fa-exclamation-triangle"></i>
                </div>
                <div class="ms-3 flex-grow-1">
                  <div class="fw-bold text-warning">Low Stock</div>
                  <div class="h4 mb-0">${stats.lowStockCount}</div>
                  <small class="text-danger">Requires attention</small>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Charts and Tables Row -->
        <div class="row">
          <!-- Sales Chart -->
          <div class="col-lg-8 mb-4">
            <div class="card">
              <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Sales Overview (Last 7 Days)</h5>
                <div class="dropdown">
                  <button class="btn btn-outline-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown">
                    Last 7 days
                  </button>
                  <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="#">Last 7 days</a></li>
                    <li><a class="dropdown-item" href="#">Last 30 days</a></li>
                    <li><a class="dropdown-item" href="#">Last 3 months</a></li>
                  </ul>
                </div>
              </div>
              <div class="card-body">
                <div class="chart-container">
                  <canvas id="salesChart"></canvas>
                </div>
              </div>
            </div>
          </div>

          <!-- Quick Actions -->
          <div class="col-lg-4 mb-4">
            <div class="card">
              <div class="card-header">
                <h5 class="mb-0">Quick Actions</h5>
              </div>
              <div class="card-body">
                <div class="d-grid gap-2">
                  <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary">
                    <i class="fas fa-plus me-2"></i>Add New Book
                  </a>
                  <a href="${pageContext.request.contextPath}/customers/add" class="btn btn-success">
                    <i class="fas fa-user-plus me-2"></i>Add Customer
                  </a>
                  <a href="${pageContext.request.contextPath}/staff/add" class="btn btn-primary">
                    <i class="fas fa-plus me-2"></i> Add Staff
                  </a>
                  <a href="${pageContext.request.contextPath}/billing/pos" class="btn btn-info">
                    <i class="fas fa-cash-register me-2"></i>New Sale
                  </a>
                  <a href="${pageContext.request.contextPath}/admin/reports/inventory" class="btn btn-warning">
                    <i class="fas fa-clipboard-list me-2"></i>Inventory Report
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Recent Activity and Low Stock -->
        <div class="row">
          <!-- Recent Sales -->
          <div class="col-lg-8 mb-4">
            <div class="card">
              <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Recent Sales</h5>
                <a href="${pageContext.request.contextPath}/admin/reports/sales" class="btn btn-outline-primary btn-sm">
                  View All
                </a>
              </div>
              <div class="card-body">
                <c:choose>
                  <c:when test="${not empty recentBills}">
                    <div class="table-responsive">
                      <table class="table table-hover">
                        <thead>
                        <tr>
                          <th>Bill #</th>
                          <th>Customer</th>
                          <th>Amount</th>
                          <th>Date</th>
                          <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="bill" items="${recentBills}">
                          <tr>
                            <td><span class="badge bg-light text-dark">#${bill.billNumber}</span></td>
                            <td>${not empty bill.customerAccountNumber ? bill.customerAccountNumber : 'Walk-in'}</td>
                            <td class="fw-bold">$<fmt:formatNumber value="${bill.totalAmount}" pattern="#,##0.00"/></td>
                            <td><fmt:formatDate value="${bill.createdAt}" pattern="MMM dd, HH:mm"/></td>
                            <td><span class="badge bg-success">Completed</span></td>
                          </tr>
                        </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="text-center py-4">
                      <i class="fas fa-receipt fa-3x text-muted mb-3"></i>
                      <p class="text-muted">No recent sales found</p>
                    </div>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
          </div>

          <!-- Low Stock Alert -->
          <div class="col-lg-4 mb-4">
            <div class="card">
              <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="mb-0 text-warning">
                  <i class="fas fa-exclamation-triangle me-2"></i>Low Stock Alert
                </h5>
                <span class="badge bg-warning">${stats.lowStockCount}</span>
              </div>
              <div class="card-body" style="max-height: 300px; overflow-y: auto;">
                <c:choose>
                  <c:when test="${not empty lowStockBooks}">
                    <c:forEach var="book" items="${lowStockBooks}">
                      <div class="d-flex justify-content-between align-items-center mb-3 p-2 bg-light rounded">
                        <div>
                          <div class="fw-bold small">${book.title}</div>
                          <small class="text-muted">${book.author}</small>
                        </div>
                        <span class="badge ${book.quantity == 0 ? 'bg-danger' : 'bg-warning'}">${book.quantity}</span>
                      </div>
                    </c:forEach>
                  </c:when>
                  <c:otherwise>
                    <div class="text-center py-3">
                      <i class="fas fa-check-circle fa-2x text-success mb-2"></i>
                      <p class="text-muted small mb-0">All books are well stocked!</p>
                    </div>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
  // Sales Chart
  const ctx = document.getElementById('salesChart').getContext('2d');
  const salesChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: ['6 days ago', '5 days ago', '4 days ago', '3 days ago', '2 days ago', 'Yesterday', 'Today'],
      datasets: [{
        label: 'Sales ($)',
        data: [1200, 1900, 800, 1500, 2000, 1800, ${stats.todaySales}],
        borderColor: '#3498db',
        backgroundColor: 'rgba(52, 152, 219, 0.1)',
        borderWidth: 3,
        fill: true,
        tension: 0.4
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          grid: {
            color: 'rgba(0,0,0,0.1)'
          }
        },
        x: {
          grid: {
            display: false
          }
        }
      }
    }
  });

  // Auto refresh data every 5 minutes
  setInterval(function() {
    location.reload();
  }, 300000);
</script>
</body>
</html>