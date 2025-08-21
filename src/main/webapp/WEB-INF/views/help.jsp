<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Help & Documentation - PahanaEdu</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
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
    .help-section {
      background: white;
      border-radius: 15px;
      padding: 25px;
      margin-bottom: 20px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.08);
      border: none;
    }
    .help-nav {
      position: sticky;
      top: 20px;
    }
    .help-nav .nav-link {
      color: #6c757d;
      padding: 8px 15px;
      border-left: 3px solid transparent;
      transition: all 0.3s;
    }
    .help-nav .nav-link:hover,
    .help-nav .nav-link.active {
      color: #3498db;
      background-color: rgba(52, 152, 219, 0.1);
      border-left-color: #3498db;
    }
    .feature-card {
      background: #f8f9fa;
      border-left: 4px solid #3498db;
      padding: 15px;
      margin-bottom: 15px;
      border-radius: 5px;
    }
    .step-number {
      width: 30px;
      height: 30px;
      background: #3498db;
      color: white;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: bold;
      margin-right: 15px;
    }
    .keyboard-key {
      display: inline-block;
      padding: 2px 8px;
      background: #f8f9fa;
      border: 1px solid #dee2e6;
      border-radius: 4px;
      font-family: monospace;
      font-size: 0.9em;
    }
    .code-block {
      background: #f8f9fa;
      border-left: 3px solid #28a745;
      padding: 10px 15px;
      border-radius: 5px;
      font-family: monospace;
      margin: 10px 0;
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
          <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
            <i class="fas fa-home me-2"></i> Dashboard
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/books">
            <i class="fas fa-book me-2"></i> Books Management
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/customers">
            <i class="fas fa-users me-2"></i> Customer Management
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/staff">
            <i class="fas fa-users me-2"></i> Staff Management
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/billing/pos">
            <i class="fas fa-cash-register me-2"></i> Point of Sale System
          </a>
          <a class="nav-link" href="${pageContext.request.contextPath}/admin/analytics">
            <i class="fas fa-chart-bar me-2"></i> Analytics
          </a>
          <a class="nav-link active" href="${pageContext.request.contextPath}/help">
            <i class="fas fa-question-circle me-2"></i> Help & Support
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
            <h2><i class="fas fa-question-circle text-primary me-2"></i>Help & Documentation</h2>
            <p class="text-muted mb-0">Complete guide to using the PahanaEdu POS system</p>
          </div>
          <div>
            <button class="btn btn-primary" onclick="window.print()">
              <i class="fas fa-print me-2"></i>Print Guide
            </button>
          </div>
        </div>

        <div class="row">
          <!-- Help Navigation -->
          <div class="col-lg-3">
            <div class="help-nav">
              <div class="card">
                <div class="card-header">
                  <h6 class="mb-0"><i class="fas fa-list me-2"></i>Quick Navigation</h6>
                </div>
                <div class="card-body p-0">
                  <nav class="nav flex-column">
                    <a class="nav-link active" href="#getting-started">Getting Started</a>
                    <a class="nav-link" href="#dashboard-overview">Dashboard Overview</a>
                    <a class="nav-link" href="#book-management">Book Management</a>
                    <a class="nav-link" href="#pos-system">POS System</a>
                    <a class="nav-link" href="#customer-management">Customer Management</a>
                    <a class="nav-link" href="#staff-management">Staff Management</a>
                    <a class="nav-link" href="#reports-analytics">Reports & Analytics</a>
                    <a class="nav-link" href="#troubleshooting">Troubleshooting</a>
                    <a class="nav-link" href="#keyboard-shortcuts">Keyboard Shortcuts</a>
                  </nav>
                </div>
              </div>
            </div>
          </div>

          <!-- Help Content -->
          <div class="col-lg-9">
            <!-- Getting Started -->
            <section id="getting-started" class="help-section">
              <h3><i class="fas fa-play-circle text-success me-2"></i>Getting Started</h3>
              <p class="lead">Welcome to PahanaEdu POS! This guide will help you get started with the system.</p>

              <h5>System Requirements</h5>
              <ul>
                <li>Modern web browser (Chrome, Firefox, Safari, Edge)</li>
                <li>Internet connection for real-time updates</li>
                <li>Screen resolution minimum 1024x768</li>
              </ul>

              <h5>First Time Setup</h5>
              <div class="row">
                <div class="col-md-6">
                  <div class="d-flex align-items-center mb-3">
                    <div class="step-number">1</div>
                    <div>
                      <strong>Login to System</strong><br>
                      <small class="text-muted">Use your provided credentials</small>
                    </div>
                  </div>
                  <div class="d-flex align-items-center mb-3">
                    <div class="step-number">2</div>
                    <div>
                      <strong>Explore Dashboard</strong><br>
                      <small class="text-muted">Familiarize with the interface</small>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="d-flex align-items-center mb-3">
                    <div class="step-number">3</div>
                    <div>
                      <strong>Set Up Inventory</strong><br>
                      <small class="text-muted">Add books and categories</small>
                    </div>
                  </div>
                  <div class="d-flex align-items-center mb-3">
                    <div class="step-number">4</div>
                    <div>
                      <strong>Start Selling</strong><br>
                      <small class="text-muted">Process your first transaction</small>
                    </div>
                  </div>
                </div>
              </div>
            </section>

            <!-- Dashboard Overview -->
            <section id="dashboard-overview" class="help-section">
              <h3><i class="fas fa-tachometer-alt text-primary me-2"></i>Dashboard Overview</h3>
              <p>The dashboard provides a comprehensive view of your business performance.</p>

              <div class="feature-card">
                <h6><i class="fas fa-chart-line me-2"></i>Statistics Cards</h6>
                <p class="mb-1">View real-time data including:</p>
                <ul class="mb-0">
                  <li>Today's revenue and bill count</li>
                  <li>Monthly revenue and transactions</li>
                  <li>Total books in stock</li>
                  <li>Low stock alerts</li>
                </ul>
              </div>

              <div class="feature-card">
                <h6><i class="fas fa-chart-bar me-2"></i>Revenue Chart</h6>
                <p class="mb-0">Track your daily revenue trends over the last 7 days. Hover over data points for detailed information.</p>
              </div>

              <div class="feature-card">
                <h6><i class="fas fa-trophy me-2"></i>Top Selling Books</h6>
                <p class="mb-0">Monitor which books are performing best to optimize your inventory.</p>
              </div>
            </section>

            <!-- Book Management -->
            <section id="book-management" class="help-section">
              <h3><i class="fas fa-book text-info me-2"></i>Book Management</h3>

              <h5>Adding New Books</h5>
              <div class="d-flex align-items-start mb-3">
                <div class="step-number">1</div>
                <div>
                  <p><strong>Navigate to Books section</strong><br>
                    Click on "Books Management" in the sidebar or use the quick action card.</p>
                </div>
              </div>
              <div class="d-flex align-items-start mb-3">
                <div class="step-number">2</div>
                <div>
                  <p><strong>Click "Add New Book"</strong><br>
                    Fill in the required information: Title, Author, ISBN, Price, Quantity, etc.</p>
                </div>
              </div>
              <div class="d-flex align-items-start mb-3">
                <div class="step-number">3</div>
                <div>
                  <p><strong>Set Category</strong><br>
                    Select an appropriate category or create a new one.</p>
                </div>
              </div>

              <h5>Bulk Import from Google Books</h5>
              <p>You can import book data directly from Google Books API:</p>
              <ul>
                <li>Go to Books → Import from Google</li>
                <li>Search by title, author, or ISBN</li>
                <li>Select books and import with one click</li>
              </ul>

              <div class="alert alert-info">
                <i class="fas fa-lightbulb me-2"></i>
                <strong>Tip:</strong> Use the barcode scanner feature for quick ISBN entry when adding physical books.
              </div>
            </section>

            <!-- POS System -->
            <section id="pos-system" class="help-section">
              <h3><i class="fas fa-cash-register text-success me-2"></i>Point of Sale System</h3>

              <h5>Processing a Sale</h5>
              <div class="row">
                <div class="col-md-6">
                  <div class="d-flex align-items-start mb-3">
                    <div class="step-number">1</div>
                    <div>
                      <strong>Access POS</strong><br>
                      <small>Click "Point of Sale System" or use <span class="keyboard-key">Ctrl + P</span></small>
                    </div>
                  </div>
                  <div class="d-flex align-items-start mb-3">
                    <div class="step-number">2</div>
                    <div>
                      <strong>Add Items</strong><br>
                      <small>Search and add books to cart</small>
                    </div>
                  </div>
                  <div class="d-flex align-items-start mb-3">
                    <div class="step-number">3</div>
                    <div>
                      <strong>Customer Info</strong><br>
                      <small>Add customer or process as walk-in</small>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="d-flex align-items-start mb-3">
                    <div class="step-number">4</div>
                    <div>
                      <strong>Apply Discounts</strong><br>
                      <small>If applicable, apply customer discounts</small>
                    </div>
                  </div>
                  <div class="d-flex align-items-start mb-3">
                    <div class="step-number">5</div>
                    <div>
                      <strong>Process Payment</strong><br>
                      <small>Complete transaction and print receipt</small>
                    </div>
                  </div>
                </div>
              </div>

              <div class="feature-card">
                <h6><i class="fas fa-search me-2"></i>Quick Search Features</h6>
                <ul class="mb-0">
                  <li>Search by book title, author, or ISBN</li>
                  <li>Use barcode scanner for instant lookup</li>
                  <li>Filter by category for faster browsing</li>
                </ul>
              </div>
            </section>

            <!-- Customer Management -->
            <section id="customer-management" class="help-section">
              <h3><i class="fas fa-users text-warning me-2"></i>Customer Management</h3>

              <h5>Adding New Customers</h5>
              <p>Registered customers can receive benefits like purchase history tracking and loyalty discounts.</p>

              <div class="code-block">
                Required Information:
                • Full Name
                • Email Address
                • Phone Number
                • Account Number (auto-generated)
              </div>

              <h5>Customer Benefits</h5>
              <ul>
                <li>Purchase history tracking</li>
                <li>Loyalty points accumulation</li>
                <li>Personalized recommendations</li>
                <li>Special discount eligibility</li>
              </ul>
            </section>

            <!-- Staff Management -->
            <section id="staff-management" class="help-section">
              <h3><i class="fas fa-users-cog text-secondary me-2"></i>Staff Management</h3>
              <div class="alert alert-warning">
                <i class="fas fa-lock me-2"></i>
                <strong>Admin Only:</strong> This section is only available to administrators.
              </div>

              <h5>User Roles</h5>
              <div class="row">
                <div class="col-md-6">
                  <div class="feature-card">
                    <h6><i class="fas fa-crown me-2"></i>Administrator</h6>
                    <ul class="mb-0 small">
                      <li>Full system access</li>
                      <li>User management</li>
                      <li>System configuration</li>
                      <li>Advanced reports</li>
                    </ul>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="feature-card">
                    <h6><i class="fas fa-user me-2"></i>Staff/Cashier</h6>
                    <ul class="mb-0 small">
                      <li>POS operations</li>
                      <li>Customer management</li>
                      <li>Basic inventory view</li>
                      <li>Sales reports</li>
                    </ul>
                  </div>
                </div>
              </div>
            </section>

            <!-- Reports & Analytics -->
            <section id="reports-analytics" class="help-section">
              <h3><i class="fas fa-chart-bar text-info me-2"></i>Reports & Analytics</h3>

              <h5>Available Reports</h5>
              <div class="row">
                <div class="col-md-4 mb-3">
                  <div class="feature-card">
                    <h6>Sales Reports</h6>
                    <small>Daily, weekly, monthly sales data</small>
                  </div>
                </div>
                <div class="col-md-4 mb-3">
                  <div class="feature-card">
                    <h6>Inventory Reports</h6>
                    <small>Stock levels, low stock alerts</small>
                  </div>
                </div>
                <div class="col-md-4 mb-3">
                  <div class="feature-card">
                    <h6>Customer Reports</h6>
                    <small>Customer activity and preferences</small>
                  </div>
                </div>
              </div>
            </section>

            <!-- Troubleshooting -->
            <section id="troubleshooting" class="help-section">
              <h3><i class="fas fa-tools text-danger me-2"></i>Troubleshooting</h3>

              <h5>Common Issues</h5>
              <div class="accordion" id="troubleshootingAccordion">
                <div class="accordion-item">
                  <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#issue1">
                      Login Issues
                    </button>
                  </h2>
                  <div id="issue1" class="accordion-collapse collapse" data-bs-parent="#troubleshootingAccordion">
                    <div class="accordion-body">
                      <ul>
                        <li>Verify your username and password</li>
                        <li>Check caps lock status</li>
                        <li>Clear browser cache and cookies</li>
                        <li>Contact administrator for password reset</li>
                      </ul>
                    </div>
                  </div>
                </div>

                <div class="accordion-item">
                  <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#issue2">
                      Slow Performance
                    </button>
                  </h2>
                  <div id="issue2" class="accordion-collapse collapse" data-bs-parent="#troubleshootingAccordion">
                    <div class="accordion-body">
                      <ul>
                        <li>Check internet connection speed</li>
                        <li>Close unnecessary browser tabs</li>
                        <li>Refresh the page (<span class="keyboard-key">F5</span>)</li>
                        <li>Try using a different browser</li>
                      </ul>
                    </div>
                  </div>
                </div>

                <div class="accordion-item">
                  <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#issue3">
                      Printing Problems
                    </button>
                  </h2>
                  <div id="issue3" class="accordion-collapse collapse" data-bs-parent="#troubleshootingAccordion">
                    <div class="accordion-body">
                      <ul>
                        <li>Check printer connection and power</li>
                        <li>Verify paper and ink levels</li>
                        <li>Test with browser print function</li>
                        <li>Check printer settings in browser</li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </section>

            <!-- Keyboard Shortcuts -->
            <section id="keyboard-shortcuts" class="help-section">
              <h3><i class="fas fa-keyboard text-primary me-2"></i>Keyboard Shortcuts</h3>

              <div class="row">
                <div class="col-md-6">
                  <h6>Navigation</h6>
                  <table class="table table-sm">
                    <tbody>
                    <tr>
                      <td><span class="keyboard-key">Ctrl + H</span></td>
                      <td>Go to Dashboard</td>
                    </tr>
                    <tr>
                      <td><span class="keyboard-key">Ctrl + P</span></td>
                      <td>Open POS System</td>
                    </tr>
                    <tr>
                      <td><span class="keyboard-key">Ctrl + B</span></td>
                      <td>Books Management</td>
                    </tr>
                    <tr>
                      <td><span class="keyboard-key">Ctrl + U</span></td>
                      <td>Customer Management</td>
                    </tr>
                    </tbody>
                  </table>
                </div>
                <div class="col-md-6">
                  <h6>POS System</h6>
                  <table class="table table-sm">
                    <tbody>
                    <tr>
                      <td><span class="keyboard-key">F1</span></td>
                      <td>Search Products</td>
                    </tr>
                    <tr>
                      <td><span class="keyboard-key">F9</span></td>
                      <td>Process Payment</td>
                    </tr>
                    <tr>
                      <td><span class="keyboard-key">Esc</span></td>
                      <td>Clear Cart</td>
                    </tr>
                    <tr>
                      <td><span class="keyboard-key">Ctrl + Print</span></td>
                      <td>Print Receipt</td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </section>

            <!-- Contact Support -->
            <section class="help-section">
              <h3><i class="fas fa-headset text-success me-2"></i>Contact Support</h3>
              <div class="row">
                <div class="col-md-4">
                  <div class="text-center">
                    <i class="fas fa-envelope fa-2x text-primary mb-2"></i>
                    <h6>Email Support</h6>
                    <p class="text-muted">support@pahanaedu.com</p>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="text-center">
                    <i class="fas fa-phone fa-2x text-success mb-2"></i>
                    <h6>Phone Support</h6>
                    <p class="text-muted">+1 (555) 123-4567</p>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="text-center">
                    <i class="fas fa-clock fa-2x text-warning mb-2"></i>
                    <h6>Business Hours</h6>
                    <p class="text-muted">Mon-Fri 9AM-6PM</p>
                  </div>
                </div>
              </div>
            </section>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Smooth scrolling for navigation links
  document.querySelectorAll('.help-nav a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();

      // Remove active class from all nav links
      document.querySelectorAll('.help-nav .nav-link').forEach(link => {
        link.classList.remove('active');
      });

      // Add active class to clicked link
      this.classList.add('active');

      // Smooth scroll to target
      const target = document.querySelector(this.getAttribute('href'));
      target.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    });
  });

  // Update active navigation based on scroll position
  window.addEventListener('scroll', function() {
    const sections = document.querySelectorAll('section[id]');
    const navLinks = document.querySelectorAll('.help-nav .nav-link');

    let current = '';
    sections.forEach(section => {
      const sectionTop = section.offsetTop;
      const sectionHeight = section.clientHeight;
      if (scrollY >= (sectionTop - 100)) {
        current = section.getAttribute('id');
      }
    });

    navLinks.forEach(link => {
      link.classList.remove('active');
      if (link.getAttribute('href') === '#' + current) {
        link.classList.add('active');
      }
    });
  });

  // Keyboard shortcuts
  document.addEventListener('keydown', function(e) {
    if (e.ctrlKey) {
      switch(e.key) {
        case 'h':
          e.preventDefault();
          window.location.href = '${pageContext.request.contextPath}/dashboard';
          break;
        case 'p':
          e.preventDefault();
          window.location.href = '${pageContext.request.contextPath}/billing/pos';
          break;
        case 'b':
          e.preventDefault();
          window.location.href = '${pageContext.request.contextPath}/books';
          break;
        case 'u':
          e.preventDefault();
          window.location.href = '${pageContext.request.contextPath}/customers';
          break;
      }
    }
  });
</script>
</body>
</html>