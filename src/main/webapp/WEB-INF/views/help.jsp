<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard Help - PahanaEdu</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/aos@2.3.4/dist/aos.css">
  <style>
    :root {
      --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      --secondary-gradient: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
      --success-gradient: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
      --warning-gradient: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
    }

    body {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      min-height: 100vh;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .help-container {
      background: rgba(255, 255, 255, 0.95);
      backdrop-filter: blur(10px);
      border-radius: 20px;
      box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
      margin: 20px auto;
      max-width: 1200px;
    }

    .help-header {
      background: var(--primary-gradient);
      color: white;
      padding: 40px;
      border-radius: 20px 20px 0 0;
      text-align: center;
      position: relative;
      overflow: hidden;
    }

    .help-header::before {
      content: '';
      position: absolute;
      top: -50%;
      left: -50%;
      width: 200%;
      height: 200%;
      background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 50%);
      animation: float 6s ease-in-out infinite;
    }

    @keyframes float {
      0%, 100% { transform: translateY(0px) rotate(0deg); }
      50% { transform: translateY(-20px) rotate(180deg); }
    }

    .feature-card {
      background: white;
      border-radius: 15px;
      padding: 30px;
      margin: 20px 0;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;
      border: 2px solid transparent;
      position: relative;
      overflow: hidden;
    }

    .feature-card::before {
      content: '';
      position: absolute;
      top: 0;
      left: -100%;
      width: 100%;
      height: 100%;
      background: linear-gradient(90deg, transparent, rgba(255,255,255,0.4), transparent);
      transition: left 0.5s;
    }

    .feature-card:hover::before {
      left: 100%;
    }

    .feature-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
      border-color: #667eea;
    }

    .feature-icon {
      width: 80px;
      height: 80px;
      border-radius: 20px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 30px;
      color: white;
      margin-bottom: 20px;
      position: relative;
    }

    .feature-icon.primary { background: var(--primary-gradient); }
    .feature-icon.success { background: var(--success-gradient); }
    .feature-icon.warning { background: var(--warning-gradient); }
    .feature-icon.secondary { background: var(--secondary-gradient); }

    .step-indicator {
      display: flex;
      justify-content: center;
      margin: 40px 0;
    }

    .step {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      background: #e9ecef;
      display: flex;
      align-items: center;
      justify-content: center;
      margin: 0 10px;
      position: relative;
      transition: all 0.3s ease;
      cursor: pointer;
    }

    .step.active {
      background: var(--primary-gradient);
      color: white;
      transform: scale(1.2);
    }

    .step::after {
      content: '';
      position: absolute;
      width: 30px;
      height: 2px;
      background: #e9ecef;
      right: -40px;
      top: 50%;
      transform: translateY(-50%);
    }

    .step:last-child::after {
      display: none;
    }

    .step.active::after {
      background: var(--primary-gradient);
    }

    .tutorial-section {
      display: none;
      padding: 30px;
    }

    .tutorial-section.active {
      display: block;
      animation: fadeInUp 0.5s ease;
    }

    @keyframes fadeInUp {
      from {
        opacity: 0;
        transform: translateY(30px);
      }
      to {
        opacity: 1;
        transform: translateY(0);
      }
    }

    .demo-dashboard {
      background: #f8f9fa;
      border-radius: 10px;
      padding: 20px;
      margin: 20px 0;
      position: relative;
    }

    .demo-card {
      background: white;
      border-radius: 10px;
      padding: 15px;
      margin: 10px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      display: inline-block;
      min-width: 200px;
    }

    .pulse-dot {
      width: 12px;
      height: 12px;
      background: #ff4757;
      border-radius: 50%;
      display: inline-block;
      animation: pulse 2s infinite;
      margin-right: 8px;
    }

    @keyframes pulse {
      0% { transform: scale(1); opacity: 1; }
      50% { transform: scale(1.5); opacity: 0.7; }
      100% { transform: scale(1); opacity: 1; }
    }

    .tooltip-demo {
      position: relative;
      display: inline-block;
    }

    .tooltip-content {
      visibility: hidden;
      width: 200px;
      background-color: #333;
      color: white;
      text-align: center;
      border-radius: 6px;
      padding: 8px;
      position: absolute;
      z-index: 1;
      bottom: 125%;
      left: 50%;
      margin-left: -100px;
      opacity: 0;
      transition: opacity 0.3s;
    }

    .tooltip-demo:hover .tooltip-content {
      visibility: visible;
      opacity: 1;
    }

    .back-btn {
      position: fixed;
      top: 20px;
      left: 20px;
      z-index: 1000;
      background: var(--primary-gradient);
      color: white;
      border: none;
      padding: 12px 20px;
      border-radius: 25px;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
      transition: all 0.3s ease;
    }

    .back-btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 25px rgba(0, 0, 0, 0.3);
      color: white;
    }

    .search-help {
      position: relative;
      margin: 20px 0;
    }

    .search-help input {
      border-radius: 25px;
      padding: 15px 50px 15px 20px;
      border: 2px solid #e9ecef;
      width: 100%;
      transition: all 0.3s ease;
    }

    .search-help input:focus {
      border-color: #667eea;
      box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
    }

    .search-help .search-icon {
      position: absolute;
      right: 15px;
      top: 50%;
      transform: translateY(-50%);
      color: #667eea;
    }
  </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/admin/dashboard" class="btn back-btn">
  <i class="fas fa-arrow-left me-2"></i>Back to Dashboard
</a>

<div class="container-fluid">
  <div class="help-container">
    <!-- Header -->
    <div class="help-header" data-aos="fade-down">
      <h1 class="display-4 mb-3">
        <i class="fas fa-question-circle me-3"></i>
        Dashboard Help & Guide
      </h1>
      <p class="lead mb-0">Master your PahanaEdu admin dashboard with our interactive guide</p>
    </div>

    <!-- Search Help -->
    <div class="p-4">
      <div class="search-help" data-aos="fade-up">
        <input type="text" id="helpSearch" placeholder="Search for help topics..." class="form-control">
        <i class="fas fa-search search-icon"></i>
      </div>
    </div>

    <!-- Step Indicator -->
    <div class="step-indicator" data-aos="fade-up" data-aos-delay="100">
      <div class="step active" data-step="1">1</div>
      <div class="step" data-step="2">2</div>
      <div class="step" data-step="3">3</div>
      <div class="step" data-step="4">4</div>
      <div class="step" data-step="5">5</div>
    </div>

    <!-- Tutorial Sections -->
    <div id="tutorial-content">
      <!-- Step 1: Overview -->
      <div class="tutorial-section active" id="step-1">
        <div class="row">
          <div class="col-md-6" data-aos="fade-right">
            <h2 class="mb-4">
              <i class="fas fa-eye text-primary me-2"></i>
              Dashboard Overview
            </h2>
            <p class="lead">Welcome to your command center! Your dashboard provides a comprehensive view of your bookstore's performance.</p>

            <div class="feature-card">
              <div class="feature-icon primary">
                <i class="fas fa-tachometer-alt"></i>
              </div>
              <h4>Real-time Statistics</h4>
              <p>Monitor today's sales, orders, inventory levels, and low stock alerts in real-time.</p>
              <ul class="list-unstyled">
                <li><i class="fas fa-check text-success me-2"></i>Live sales tracking</li>
                <li><i class="fas fa-check text-success me-2"></i>Order monitoring</li>
                <li><i class="fas fa-check text-success me-2"></i>Inventory insights</li>
              </ul>
            </div>
          </div>
          <div class="col-md-6" data-aos="fade-left">
            <div class="demo-dashboard">
              <h5 class="mb-3">Dashboard Preview</h5>
              <div class="row">
                <div class="col-6">
                  <div class="demo-card">
                    <div class="tooltip-demo">
                      <span class="pulse-dot"></span>Today's Sales
                      <div class="tooltip-content">Real-time sales data updates every minute</div>
                    </div>
                    <h4 class="text-primary">$2,450.00</h4>
                  </div>
                </div>
                <div class="col-6">
                  <div class="demo-card">
                    <div class="tooltip-demo">
                      <span class="pulse-dot"></span>Orders Today
                      <div class="tooltip-content">Track daily order volume</div>
                    </div>
                    <h4 class="text-success">42</h4>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Step 2: Navigation -->
      <div class="tutorial-section" id="step-2">
        <div class="row">
          <div class="col-md-8" data-aos="fade-up">
            <h2 class="mb-4">
              <i class="fas fa-compass text-success me-2"></i>
              Navigation & Sidebar
            </h2>
            <p class="lead">Master the navigation to access all features efficiently.</p>

            <div class="row">
              <div class="col-md-6">
                <div class="feature-card">
                  <div class="feature-icon success">
                    <i class="fas fa-book"></i>
                  </div>
                  <h5>Books Management</h5>
                  <p>Add, edit, and manage your book inventory with ease.</p>
                </div>
              </div>
              <div class="col-md-6">
                <div class="feature-card">
                  <div class="feature-icon warning">
                    <i class="fas fa-users"></i>
                  </div>
                  <h5>Customer Management</h5>
                  <p>Track customer information and purchase history.</p>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-4" data-aos="fade-left">
            <div class="card">
              <div class="card-body">
                <h6 class="card-title">Quick Tip</h6>
                <p class="card-text">Use the sidebar for quick navigation. Active sections are highlighted in blue.</p>
                <div class="alert alert-info">
                  <i class="fas fa-lightbulb me-2"></i>
                  Pro tip: Bookmark frequently used pages!
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Step 3: Statistics Cards -->
      <div class="tutorial-section" id="step-3">
        <div data-aos="fade-up">
          <h2 class="mb-4">
            <i class="fas fa-chart-bar text-warning me-2"></i>
            Understanding Statistics Cards
          </h2>
          <p class="lead">Learn how to interpret your key performance indicators.</p>

          <div class="row">
            <div class="col-lg-3 col-md-6 mb-4">
              <div class="feature-card">
                <div class="feature-icon primary">
                  <i class="fas fa-dollar-sign"></i>
                </div>
                <h5>Today's Sales</h5>
                <p>Total revenue generated today</p>
                <small class="text-success">
                  <i class="fas fa-arrow-up"></i> Growth indicator
                </small>
              </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-4">
              <div class="feature-card">
                <div class="feature-icon success">
                  <i class="fas fa-shopping-cart"></i>
                </div>
                <h5>Orders Today</h5>
                <p>Number of orders processed</p>
                <small class="text-success">
                  <i class="fas fa-arrow-up"></i> Trend comparison
                </small>
              </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-4">
              <div class="feature-card">
                <div class="feature-icon warning">
                  <i class="fas fa-book"></i>
                </div>
                <h5>Total Books</h5>
                <p>Current inventory count</p>
                <small class="text-muted">Stock levels</small>
              </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-4">
              <div class="feature-card">
                <div class="feature-icon secondary">
                  <i class="fas fa-exclamation-triangle"></i>
                </div>
                <h5>Low Stock Alert</h5>
                <p>Items requiring attention</p>
                <small class="text-danger">Action needed</small>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Step 4: Charts & Analytics -->
      <div class="tutorial-section" id="step-4">
        <div data-aos="fade-up">
          <h2 class="mb-4">
            <i class="fas fa-chart-line text-info me-2"></i>
            Charts & Analytics
          </h2>
          <p class="lead">Visualize your business performance with interactive charts.</p>

          <div class="row">
            <div class="col-md-8">
              <div class="feature-card">
                <h5>Sales Overview Chart</h5>
                <p>Track your sales trends over the last 7 days with our interactive line chart.</p>
                <div class="demo-dashboard">
                  <canvas id="demoChart" width="400" height="200"></canvas>
                </div>
                <ul class="mt-3">
                  <li>Hover over data points for detailed information</li>
                  <li>Use the dropdown to change time periods</li>
                  <li>Identify trends and patterns in your sales</li>
                </ul>
              </div>
            </div>
            <div class="col-md-4">
              <div class="feature-card">
                <div class="feature-icon primary">
                  <i class="fas fa-filter"></i>
                </div>
                <h5>Filter Options</h5>
                <p>Customize your view:</p>
                <ul>
                  <li>Last 7 days</li>
                  <li>Last 30 days</li>
                  <li>Last 3 months</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Step 5: Quick Actions -->
      <div class="tutorial-section" id="step-5">
        <div data-aos="fade-up">
          <h2 class="mb-4">
            <i class="fas fa-bolt text-warning me-2"></i>
            Quick Actions & Tips
          </h2>
          <p class="lead">Maximize your productivity with these quick actions and pro tips.</p>

          <div class="row">
            <div class="col-md-6">
              <div class="feature-card">
                <h5>Quick Actions Panel</h5>
                <div class="d-grid gap-2">
                  <button class="btn btn-primary">
                    <i class="fas fa-plus me-2"></i>Add New Book
                  </button>
                  <button class="btn btn-success">
                    <i class="fas fa-user-plus me-2"></i>Add Customer
                  </button>
                  <button class="btn btn-info">
                    <i class="fas fa-cash-register me-2"></i>New Sale
                  </button>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="feature-card">
                <h5>Pro Tips</h5>
                <ul class="list-unstyled">
                  <li class="mb-2">
                    <i class="fas fa-lightbulb text-warning me-2"></i>
                    Dashboard auto-refreshes every 5 minutes
                  </li>
                  <li class="mb-2">
                    <i class="fas fa-lightbulb text-warning me-2"></i>
                    Click on low stock items for quick reorder
                  </li>
                  <li class="mb-2">
                    <i class="fas fa-lightbulb text-warning me-2"></i>
                    Use keyboard shortcuts: Ctrl+N for new sale
                  </li>
                  <li class="mb-2">
                    <i class="fas fa-lightbulb text-warning me-2"></i>
                    Export reports for detailed analysis
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Help Footer -->
    <div class="text-center p-4" data-aos="fade-up">
      <h4>Need More Help?</h4>
      <p class="text-muted">Contact our support team or check out our documentation</p>
      <div class="btn-group" role="group">
        <a href="#" class="btn btn-outline-primary">
          <i class="fas fa-envelope me-2"></i>Contact Support
        </a>
        <a href="#" class="btn btn-outline-secondary">
          <i class="fas fa-book me-2"></i>Documentation
        </a>
        <a href="#" class="btn btn-outline-info">
          <i class="fas fa-video me-2"></i>Video Tutorials
        </a>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/aos@2.3.4/dist/aos.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
  // Initialize AOS
  AOS.init({
    duration: 800,
    once: true
  });

  // Step navigation
  document.querySelectorAll('.step').forEach(step => {
    step.addEventListener('click', function() {
      const stepNumber = this.dataset.step;

      // Update active step
      document.querySelectorAll('.step').forEach(s => s.classList.remove('active'));
      this.classList.add('active');

      // Show corresponding tutorial section
      document.querySelectorAll('.tutorial-section').forEach(section => {
        section.classList.remove('active');
      });
      document.getElementById(`step-${stepNumber}`).classList.add('active');
    });
  });

  // Search functionality
  document.getElementById('helpSearch').addEventListener('input', function() {
    const searchTerm = this.value.toLowerCase();
    const sections = document.querySelectorAll('.feature-card');

    sections.forEach(section => {
      const text = section.textContent.toLowerCase();
      const shouldShow = text.includes(searchTerm);
      section.style.opacity = shouldShow ? '1' : '0.3';
      section.style.transform = shouldShow ? 'scale(1)' : 'scale(0.95)';
    });
  });

  // Demo chart
  const ctx = document.getElementById('demoChart');
  if (ctx) {
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        datasets: [{
          label: 'Sales',
          data: [1200, 1900, 800, 1500, 2000, 1800, 2400],
          borderColor: '#667eea',
          backgroundColor: 'rgba(102, 126, 234, 0.1)',
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
            beginAtZero: true
          }
        }
      }
    });
  }

  // Auto-advance tutorial (optional)
  let currentStep = 1;
  const totalSteps = 5;

  function autoAdvance() {
    if (currentStep < totalSteps) {
      currentStep++;
      document.querySelector(`[data-step="${currentStep}"]`).click();
      setTimeout(autoAdvance, 10000); // Advance every 10 seconds
    }
  }

  // Uncomment to enable auto-advance
  // setTimeout(autoAdvance, 10000);

  // Keyboard shortcuts
  document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
      window.location.href = '${pageContext.request.contextPath}/admin/dashboard';
    }

    if (e.key >= '1' && e.key <= '5') {
      document.querySelector(`[data-step="${e.key}"]`).click();
    }
  });
</script>
</body>
</html>