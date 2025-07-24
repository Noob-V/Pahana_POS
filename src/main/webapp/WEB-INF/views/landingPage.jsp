<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Pahana Education - Premier Online Bookshop</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: 'Poppins', sans-serif;
      overflow-x: hidden;
    }

    /* Animated Background */
    .hero-section {
      min-height: 100vh;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      position: relative;
      overflow: hidden;
    }

    .floating-shapes {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      overflow: hidden;
      z-index: 1;
    }

    .shape {
      position: absolute;
      background: rgba(255, 255, 255, 0.1);
      animation: float 20s infinite linear;
    }

    .circle {
      border-radius: 50%;
    }

    .square {
      border-radius: 10px;
    }

    @keyframes float {
      0% {
        transform: translateY(100vh) rotate(0deg);
        opacity: 0;
      }
      10% {
        opacity: 1;
      }
      90% {
        opacity: 1;
      }
      100% {
        transform: translateY(-100px) rotate(360deg);
        opacity: 0;
      }
    }

    /* Navigation */
    .navbar {
      background: rgba(255, 255, 255, 0.95) !important;
      backdrop-filter: blur(20px);
      box-shadow: 0 8px 32px rgba(31, 38, 135, 0.37);
      transition: all 0.3s ease;
      z-index: 1000;
    }

    .navbar-brand {
      font-weight: 700;
      font-size: 1.8rem;
      color: #667eea !important;
    }

    .nav-link {
      font-weight: 500;
      transition: all 0.3s ease;
      position: relative;
    }

    .nav-link:hover {
      color: #667eea !important;
      transform: translateY(-2px);
    }

    .nav-link::after {
      content: '';
      position: absolute;
      width: 0;
      height: 2px;
      bottom: -5px;
      left: 50%;
      background: #667eea;
      transition: all 0.3s ease;
      transform: translateX(-50%);
    }

    .nav-link:hover::after {
      width: 100%;
    }

    /* Hero Content */
    .hero-content {
      position: relative;
      z-index: 10;
      padding-top: 120px;
    }

    .hero-title {
      font-size: 3.5rem;
      font-weight: 800;
      color: white;
      margin-bottom: 1.5rem;
      text-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
      animation: fadeInUp 1s ease;
    }

    .hero-subtitle {
      font-size: 1.3rem;
      color: rgba(255, 255, 255, 0.9);
      margin-bottom: 2rem;
      animation: fadeInUp 1s ease 0.3s both;
    }

    @keyframes fadeInUp {
      from {
        opacity: 0;
        transform: translateY(50px);
      }
      to {
        opacity: 1;
        transform: translateY(0);
      }
    }

    /* Search Box */
    .search-container {
      position: relative;
      max-width: 600px;
      margin: 0 auto 3rem;
      animation: fadeInUp 1s ease 0.6s both;
    }

    .search-box {
      border: none;
      border-radius: 50px;
      padding: 20px 25px;
      font-size: 1.1rem;
      box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
      background: rgba(255, 255, 255, 0.95);
      backdrop-filter: blur(20px);
      transition: all 0.3s ease;
    }

    .search-box:focus {
      outline: none;
      box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
      transform: translateY(-5px);
    }

    .search-btn {
      position: absolute;
      right: 8px;
      top: 8px;
      border: none;
      background: linear-gradient(135deg, #667eea, #764ba2);
      color: white;
      border-radius: 50px;
      padding: 12px 25px;
      font-weight: 600;
      transition: all 0.3s ease;
    }

    .search-btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 10px 20px rgba(102, 126, 234, 0.4);
    }

    /* CTA Buttons */
    .cta-buttons {
      animation: fadeInUp 1s ease 0.9s both;
    }

    .btn-custom {
      padding: 15px 35px;
      font-size: 1.1rem;
      font-weight: 600;
      border-radius: 50px;
      margin: 0 10px;
      transition: all 0.3s ease;
      border: none;
      text-decoration: none;
      display: inline-block;
    }

    .btn-primary-custom {
      background: linear-gradient(135deg, #667eea, #764ba2);
      color: white;
      box-shadow: 0 10px 30px rgba(102, 126, 234, 0.4);
    }

    .btn-primary-custom:hover {
      transform: translateY(-5px);
      box-shadow: 0 15px 40px rgba(102, 126, 234, 0.6);
      color: white;
    }

    .btn-outline-custom {
      background: rgba(255, 255, 255, 0.2);
      color: white;
      border: 2px solid rgba(255, 255, 255, 0.3);
      backdrop-filter: blur(20px);
    }

    .btn-outline-custom:hover {
      background: white;
      color: #667eea;
      transform: translateY(-5px);
      box-shadow: 0 15px 40px rgba(255, 255, 255, 0.3);
    }

    /* Sections */
    .section {
      padding: 100px 0;
    }

    .section-title {
      font-size: 2.5rem;
      font-weight: 700;
      text-align: center;
      margin-bottom: 3rem;
      color: #333;
      position: relative;
    }

    .section-title::after {
      content: '';
      position: absolute;
      width: 80px;
      height: 4px;
      background: linear-gradient(135deg, #667eea, #764ba2);
      bottom: -10px;
      left: 50%;
      transform: translateX(-50%);
      border-radius: 2px;
    }

    /* Popular Books */
    .book-card {
      background: white;
      border-radius: 20px;
      padding: 20px;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;
      height: 100%;
      border: none;
    }

    .book-card:hover {
      transform: translateY(-10px);
      box-shadow: 0 20px 50px rgba(0, 0, 0, 0.15);
    }

    .book-image {
      width: 100%;
      height: 200px;
      object-fit: cover;
      border-radius: 15px;
      margin-bottom: 15px;
    }

    .book-title {
      font-size: 1.1rem;
      font-weight: 600;
      color: #333;
      margin-bottom: 8px;
      line-height: 1.3;
    }

    .book-author {
      color: #666;
      font-size: 0.9rem;
      margin-bottom: 10px;
    }

    .book-rating {
      color: #ffc107;
      margin-bottom: 15px;
    }

    /* About Section */
    .about-section {
      background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
    }

    .feature-card {
      background: white;
      padding: 40px 30px;
      border-radius: 20px;
      text-align: center;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;
      height: 100%;
    }

    .feature-card:hover {
      transform: translateY(-10px);
      box-shadow: 0 20px 50px rgba(0, 0, 0, 0.15);
    }

    .feature-icon {
      font-size: 4rem;
      background: linear-gradient(135deg, #667eea, #764ba2);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      margin-bottom: 20px;
    }

    .feature-title {
      font-size: 1.3rem;
      font-weight: 600;
      margin-bottom: 15px;
      color: #333;
    }

    .feature-description {
      color: #666;
      line-height: 1.6;
    }

    /* Stats Section */
    .stats-section {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
    }

    .stat-item {
      text-align: center;
      padding: 20px;
    }

    .stat-number {
      font-size: 3rem;
      font-weight: 800;
      margin-bottom: 10px;
      display: block;
    }

    .stat-label {
      font-size: 1.1rem;
      opacity: 0.9;
    }

    /* Footer */
    .footer {
      background: #2c3e50;
      color: white;
      padding: 60px 0 30px;
    }

    .footer-section {
      margin-bottom: 30px;
    }

    .footer-title {
      font-size: 1.3rem;
      font-weight: 600;
      margin-bottom: 20px;
      color: #ecf0f1;
    }

    .footer-link {
      color: #bdc3c7;
      text-decoration: none;
      transition: color 0.3s ease;
      display: block;
      margin-bottom: 8px;
    }

    .footer-link:hover {
      color: #667eea;
    }

    .social-links {
      display: flex;
      gap: 15px;
    }

    .social-link {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      width: 45px;
      height: 45px;
      background: rgba(255, 255, 255, 0.1);
      color: white;
      border-radius: 50%;
      transition: all 0.3s ease;
      text-decoration: none;
    }

    .social-link:hover {
      background: #667eea;
      color: white;
      transform: translateY(-3px);
    }

    /* Loading Animation */
    .loading {
      display: none;
      text-align: center;
      padding: 50px;
    }

    .spinner {
      width: 50px;
      height: 50px;
      border: 5px solid #f3f3f3;
      border-top: 5px solid #667eea;
      border-radius: 50%;
      animation: spin 1s linear infinite;
      margin: 0 auto 20px;
    }

    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }

    /* Responsive */
    @media (max-width: 768px) {
      .hero-title {
        font-size: 2.5rem;
      }

      .hero-subtitle {
        font-size: 1.1rem;
      }

      .btn-custom {
        padding: 12px 25px;
        font-size: 1rem;
        margin: 5px;
      }

      .search-box {
        padding: 15px 20px;
        font-size: 1rem;
      }
    }

    /* Scroll Animation */
    .animate-on-scroll {
      opacity: 0;
      transform: translateY(50px);
      transition: all 0.8s ease;
    }

    .animate-on-scroll.animated {
      opacity: 1;
      transform: translateY(0);
    }
  </style>
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top">
  <div class="container">
    <a class="navbar-brand" href="#home">
      <i class="fas fa-graduation-cap me-2"></i>Pahana Education
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item">
          <a class="nav-link" href="#home">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#books">Popular Books</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#about">About</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#contact">Contact</a>
        </li>
        <li class="nav-item ms-3">
          <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-primary me-2">
            <i class="fas fa-sign-in-alt"></i> Login
          </a>
        </li>
        <li class="nav-item">
          <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">
            <i class="fas fa-user-plus"></i> Register
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<!-- Hero Section -->
<section class="hero-section" id="home">
  <!-- Animated Background -->
  <div class="floating-shapes">
    <!-- Shapes will be generated by JavaScript -->
  </div>

  <div class="container hero-content">
    <div class="row align-items-center min-vh-100">
      <div class="col-lg-12 text-center">
        <h1 class="hero-title">Welcome to Pahana Education</h1>
        <p class="hero-subtitle">Your Premier Destination for Academic Excellence and Knowledge</p>

        <!-- Search Box -->
        <div class="search-container">
          <form id="bookSearchForm" class="position-relative">
            <input type="text" class="form-control search-box" id="bookSearch"
                   placeholder="Search for books, authors, subjects..." required>
            <button type="submit" class="search-btn">
              <i class="fas fa-search me-2"></i>Search
            </button>
          </form>
        </div>

        <!-- CTA Buttons -->
        <div class="cta-buttons">
          <a href="#books" class="btn-custom btn-primary-custom">
            <i class="fas fa-book me-2"></i>Explore Books
          </a>
          <a href="#about" class="btn-custom btn-outline-custom">
            <i class="fas fa-info-circle me-2"></i>Learn More
          </a>
        </div>
      </div>
    </div>
  </div>
</section>

<!-- Popular Books Section -->
<section class="section" id="books">
  <div class="container">
    <h2 class="section-title animate-on-scroll">Popular Books</h2>
    <div class="row" id="popularBooksContainer">
      <div class="loading">
        <div class="spinner"></div>
        <p>Loading popular books...</p>
      </div>
    </div>
  </div>
</section>

<!-- Search Results Section -->
<section class="section d-none" id="searchResults">
  <div class="container">
    <h2 class="section-title">Search Results</h2>
    <div class="row" id="searchResultsContainer">
      <!-- Search results will be populated here -->
    </div>
  </div>
</section>

<!-- About Section -->
<section class="section about-section" id="about">
  <div class="container">
    <div class="row">
      <div class="col-lg-6 animate-on-scroll">
        <h2 class="section-title text-start">About Pahana Education</h2>
        <p class="lead mb-4">
          Pahana Education is Sri Lanka's premier online bookshop management system, dedicated to revolutionizing how educational institutions and bookstores manage their inventory and serve their customers.
        </p>
        <p class="mb-4">
          Founded with the vision of making quality educational resources accessible to everyone, we combine cutting-edge technology with a deep understanding of the educational landscape to provide comprehensive solutions for book management, sales, and distribution.
        </p>
        <p class="mb-4">
          Our platform integrates seamlessly with Google Books API to provide access to millions of titles, ensuring that students, educators, and book enthusiasts can find exactly what they're looking for. From academic textbooks to recreational reading, we cover every aspect of the literary world.
        </p>
      </div>
      <div class="col-lg-6">
        <div class="row">
          <div class="col-md-6 mb-4">
            <div class="feature-card animate-on-scroll">
              <i class="fas fa-book-open feature-icon"></i>
              <h4 class="feature-title">Vast Collection</h4>
              <p class="feature-description">Access to millions of books through Google Books integration</p>
            </div>
          </div>
          <div class="col-md-6 mb-4">
            <div class="feature-card animate-on-scroll">
              <i class="fas fa-users feature-icon"></i>
              <h4 class="feature-title">User-Friendly</h4>
              <p class="feature-description">Intuitive interface designed for all user levels</p>
            </div>
          </div>
          <div class="col-md-6 mb-4">
            <div class="feature-card animate-on-scroll">
              <i class="fas fa-chart-line feature-icon"></i>
              <h4 class="feature-title">Analytics</h4>
              <p class="feature-description">Comprehensive reporting and inventory management</p>
            </div>
          </div>
          <div class="col-md-6 mb-4">
            <div class="feature-card animate-on-scroll">
              <i class="fas fa-shield-alt feature-icon"></i>
              <h4 class="feature-title">Secure</h4>
              <p class="feature-description">Advanced security measures to protect your data</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>

<!-- Stats Section -->
<section class="section stats-section">
  <div class="container">
    <div class="row">
      <div class="col-md-3 col-sm-6">
        <div class="stat-item animate-on-scroll">
          <span class="stat-number" data-count="50000">0</span>
          <span class="stat-label">Books Available</span>
        </div>
      </div>
      <div class="col-md-3 col-sm-6">
        <div class="stat-item animate-on-scroll">
          <span class="stat-number" data-count="1000">0</span>
          <span class="stat-label">Happy Customers</span>
        </div>
      </div>
      <div class="col-md-3 col-sm-6">
        <div class="stat-item animate-on-scroll">
          <span class="stat-number" data-count="50">0</span>
          <span class="stat-label">Institutions Served</span>
        </div>
      </div>
      <div class="col-md-3 col-sm-6">
        <div class="stat-item animate-on-scroll">
          <span class="stat-number" data-count="99">0</span>
          <span class="stat-label">Satisfaction Rate</span>
        </div>
      </div>
    </div>
  </div>
</section>

<!-- Footer -->
<footer class="footer" id="contact">
  <div class="container">
    <div class="row">
      <div class="col-lg-4 footer-section">
        <h5 class="footer-title">
          <i class="fas fa-graduation-cap me-2"></i>Pahana Education
        </h5>
        <p class="mb-4">Empowering education through innovative book management solutions. Your success is our mission.</p>
        <div class="social-links">
          <a href="#" class="social-link"><i class="fab fa-facebook-f"></i></a>
          <a href="#" class="social-link"><i class="fab fa-twitter"></i></a>
          <a href="#" class="social-link"><i class="fab fa-linkedin-in"></i></a>
          <a href="#" class="social-link"><i class="fab fa-instagram"></i></a>
        </div>
      </div>
      <div class="col-lg-2 footer-section">
        <h5 class="footer-title">Quick Links</h5>
        <a href="#home" class="footer-link">Home</a>
        <a href="#books" class="footer-link">Books</a>
        <a href="#about" class="footer-link">About</a>
        <a href="#contact" class="footer-link">Contact</a>
      </div>
      <div class="col-lg-2 footer-section">
        <h5 class="footer-title">Services</h5>
        <a href="#" class="footer-link">Book Management</a>
        <a href="#" class="footer-link">Inventory Control</a>
        <a href="#" class="footer-link">Sales Analytics</a>
        <a href="#" class="footer-link">API Integration</a>
      </div>
      <div class="col-lg-4 footer-section">
        <h5 class="footer-title">Contact Info</h5>
        <p class="footer-link"><i class="fas fa-map-marker-alt me-2"></i>Colombo, Sri Lanka</p>
        <p class="footer-link"><i class="fas fa-phone me-2"></i>+94 11 234 5678</p>
        <p class="footer-link"><i class="fas fa-envelope me-2"></i>info@pahanaedu.lk</p>
      </div>
    </div>
    <hr class="my-4" style="border-color: #34495e;">
    <div class="row align-items-center">
      <div class="col-md-6">
        <p class="mb-0">&copy; 2024 Pahana Education. All rights reserved.</p>
      </div>
      <div class="col-md-6 text-md-end">
        <p class="mb-0">Built with <i class="fas fa-heart text-danger"></i> in Sri Lanka</p>
      </div>
    </div>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Floating shapes animation
  function createFloatingShapes() {
    const shapesContainer = document.querySelector('.floating-shapes');
    const shapes = ['circle', 'square'];

    setInterval(() => {
      const shape = document.createElement('div');
      const isCircle = Math.random() > 0.5;

      shape.className = `shape ${isCircle ? 'circle' : 'square'}`;
      shape.style.left = Math.random() * 100 + '%';
      shape.style.width = shape.style.height = (Math.random() * 100 + 20) + 'px';
      shape.style.animationDuration = (Math.random() * 10 + 15) + 's';
      shape.style.animationDelay = Math.random() * 5 + 's';

      shapesContainer.appendChild(shape);

      setTimeout(() => {
        shape.remove();
      }, 25000);
    }, 3000);
  }

  // Load popular books from Google Books API
  async function loadPopularBooks() {
    const container = document.getElementById('popularBooksContainer');
    const loading = container.querySelector('.loading');

    try {
      loading.style.display = 'block';

      // Popular search terms for educational books
      const searchTerms = ['programming', 'mathematics', 'science', 'literature', 'history', 'psychology'];
      const randomTerm = searchTerms[Math.floor(Math.random() * searchTerms.length)];

      const response = await fetch(`https://www.googleapis.com/books/v1/volumes?q=${randomTerm}&maxResults=8&orderBy=relevance`);
      const data = await response.json();

      loading.style.display = 'none';

      if (data.items) {
        displayBooks(data.items, container);
      } else {
        container.innerHTML = '<div class="col-12 text-center"><p>No books found</p></div>';
      }
    } catch (error) {
      loading.style.display = 'none';
      container.innerHTML = '<div class="col-12 text-center"><p class="text-danger">Error loading books. Please try again later.</p></div>';
    }
  }

  // Search books function
  async function searchBooks(query) {
    const resultsSection = document.getElementById('searchResults');
    const container = document.getElementById('searchResultsContainer');

    try {
      container.innerHTML = '<div class="col-12 text-center"><div class="spinner"></div><p>Searching...</p></div>';
      resultsSection.classList.remove('d-none');

      const response = await fetch(`https://www.googleapis.com/books/v1/volumes?q=${encodeURIComponent(query)}&maxResults=12`);
      const data = await response.json();

      if (data.items) {
        displayBooks(data.items, container);
        resultsSection.scrollIntoView({ behavior: 'smooth' });
      } else {
        container.innerHTML = '<div class="col-12 text-center"><p>No books found for your search.</p></div>';
      }
    } catch (error) {
      container.innerHTML = '<div class="col-12 text-center"><p class="text-danger">Error searching books. Please try again.</p></div>';
    }
  }

  // Display books function
  function displayBooks(books, container) {
    const booksHTML = books.map(book => {
      const volumeInfo = book.volumeInfo;
      const title = volumeInfo.title || 'No title available';
      const authors = volumeInfo.authors ? volumeInfo.authors.join(', ') : 'Unknown author';
      const thumbnail = volumeInfo.imageLinks ? volumeInfo.imageLinks.thumbnail : 'https://via.placeholder.com/150x200?text=No+Image';
      const description = volumeInfo.description ?
              (volumeInfo.description.length > 100 ? volumeInfo.description.substring(0, 100) + '...' : volumeInfo.description) :
              'No description available';
      const rating = volumeInfo.averageRating || 0;
      const ratingStars = '★'.repeat(Math.floor(rating)) + '☆'.repeat(5 - Math.floor(rating));

      return `
                    <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                        <div class="book-card animate-on-scroll">
                            <img src="${thumbnail}" alt="${title}" class="book-image">
                            <h5 class="book-title">${title}</h5>
                            <p class="book-author">by ${authors}</p>
                            <div class="book-rating">${ratingStars} (${rating || 'N/A'})</div>
                            <p class="text-muted small">${description}</p>
                            <div class="mt-auto">
                                <button class="btn btn-primary btn-sm w-100" onclick="viewBookDetails('${book.id}')">
                                    <i class="fas fa-eye me-1"></i>View Details
                                </button>
                            </div>
                        </div>
                    </div>
                `;
    }).join('');

    container.innerHTML = booksHTML;
    animateOnScroll();
  }

  // View book details function
  function viewBookDetails(bookId) {
    // For now, we'll just show an alert. In a real app, this would open a modal or navigate to a details page
    alert(`Book details for ID: ${bookId}\n\nThis would typically open a detailed view of the book with options to add to cart, read reviews, etc.`);
  }

  // Animate on scroll
  function animateOnScroll() {
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add('animated');
        }
      });
    });

    document.querySelectorAll('.animate-on-scroll').forEach(el => {
      observer.observe(el);
    });
  }

  // Counter animation
  function animateCounters() {
    const counters = document.querySelectorAll('[data-count]');

    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          const counter = entry.target;
          const target = parseInt(counter.getAttribute('data-count'));
          const duration = 2000;
          const step = target / (duration / 16);
          let current = 0;

          const timer = setInterval(() => {
            current += step;
            if (current >= target) {
              counter.textContent = target.toLocaleString();
              clearInterval(timer);
            } else {
              counter.textContent = Math.floor(current).toLocaleString();
            }
          }, 16);

          observer.unobserve(counter);
        }
      });
    });

    counters.forEach(counter => observer.observe(counter));
  }

  // Smooth scrolling for navigation links
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        target.scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        });
      }
    });
  });

  // Search form handler
  document.getElementById('bookSearchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const query = document.getElementById('bookSearch').value.trim();
    if (query) {
      searchBooks(query);
    }
  });

  // Navbar scroll effect
  window.addEventListener('scroll', function() {
    const navbar = document.querySelector('.navbar');
    if (window.scrollY > 50) {
      navbar.style.background = 'rgba(255, 255, 255, 0.98)';
      navbar.style.boxShadow = '0 8px 32px rgba(31, 38, 135, 0.5)';
    } else {
      navbar.style.background = 'rgba(255, 255, 255, 0.95)';
      navbar.style.boxShadow = '0 8px 32px rgba(31, 38, 135, 0.37)';
    }
  });

  // Initialize everything when DOM is loaded
  document.addEventListener('DOMContentLoaded', function() {
    createFloatingShapes();
    loadPopularBooks();
    animateOnScroll();
    animateCounters();
  });
</script>
</body>
</html>