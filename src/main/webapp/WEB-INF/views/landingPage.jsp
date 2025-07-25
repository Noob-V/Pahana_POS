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
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
  <style>
    :root {
      --primary-color: #ff6b35;
      --secondary-color: #004d7a;
      --accent-color: #ffd23f;
      --success-color: #10b981;
      --dark-color: #1a1a2e;
      --light-color: #f8fafc;
      --gradient-primary: linear-gradient(135deg, #ff6b35 0%, #f7931e 50%, #ffd23f 100%);
      --gradient-secondary: linear-gradient(135deg, #004d7a 0%, #0066cc 100%);
      --shadow-sm: 0 1px 2px 0 rgb(0 0 0 / 0.05);
      --shadow-md: 0 4px 6px -1px rgb(0 0 0 / 0.1);
      --shadow-lg: 0 10px 15px -3px rgb(0 0 0 / 0.1);
      --shadow-xl: 0 20px 25px -5px rgb(0 0 0 / 0.1);
    }

    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: 'Inter', sans-serif;
      line-height: 1.6;
      color: var(--dark-color);
      overflow-x: hidden;
    }

    /* Navigation */
    .navbar {
      background: rgba(255, 255, 255, 0.95) !important;
      backdrop-filter: blur(20px);
      box-shadow: var(--shadow-lg);
      transition: all 0.3s ease;
      padding: 1rem 0;
    }

    .navbar-brand {
      font-weight: 700;
      font-size: 1.5rem;
      color: var(--primary-color) !important;
      display: flex;
      align-items: center;
    }

    .nav-link {
      font-weight: 500;
      color: var(--dark-color) !important;
      margin: 0 0.5rem;
      transition: all 0.3s ease;
      position: relative;
    }

    .nav-link:hover {
      color: var(--primary-color) !important;
      transform: translateY(-2px);
    }

    .nav-link::after {
      content: '';
      position: absolute;
      width: 0;
      height: 2px;
      bottom: -5px;
      left: 50%;
      background: var(--primary-color);
      transition: all 0.3s ease;
      transform: translateX(-50%);
    }

    .nav-link:hover::after {
      width: 80%;
    }

    /* Hero Section */
    .hero-section {
      min-height: 100vh;
      background: var(--gradient-primary);
      position: relative;
      overflow: hidden;
      display: flex;
      align-items: center;
    }

    .hero-content {
      position: relative;
      z-index: 10;
      padding: 2rem 0;
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
      font-size: 1.25rem;
      color: rgba(255, 255, 255, 0.9);
      margin-bottom: 2rem;
      animation: fadeInUp 1s ease 0.3s both;
    }

    .search-container {
      max-width: 600px;
      margin: 0 auto 3rem;
      animation: fadeInUp 1s ease 0.6s both;
      position: relative;
    }

    .search-box {
      border: none;
      border-radius: 50px;
      padding: 1rem 1.5rem;
      font-size: 1.1rem;
      box-shadow: var(--shadow-xl);
      background: rgba(255, 255, 255, 0.95);
      backdrop-filter: blur(20px);
      transition: all 0.3s ease;
      width: 100%;
      padding-right: 120px;
    }

    .search-box:focus {
      outline: none;
      box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
      transform: translateY(-2px);
    }

    .search-btn {
      position: absolute;
      right: 8px;
      top: 8px;
      border: none;
      background: var(--gradient-primary);
      color: white;
      border-radius: 50px;
      padding: 12px 25px;
      font-weight: 600;
      transition: all 0.3s ease;
    }

    .search-btn:hover {
      transform: translateY(-2px);
      box-shadow: var(--shadow-lg);
    }

    .cta-buttons {
      animation: fadeInUp 1s ease 0.9s both;
    }

    .btn-custom {
      padding: 1rem 2rem;
      font-size: 1.1rem;
      font-weight: 600;
      border-radius: 50px;
      margin: 0.5rem;
      transition: all 0.3s ease;
      border: none;
      text-decoration: none;
      display: inline-block;
    }

    .btn-primary-custom {
      background: white;
      color: var(--primary-color);
      box-shadow: var(--shadow-lg);
    }

    .btn-primary-custom:hover {
      transform: translateY(-3px);
      box-shadow: var(--shadow-xl);
      color: var(--primary-color);
    }

    .btn-outline-custom {
      background: rgba(255, 255, 255, 0.1);
      color: white;
      border: 2px solid rgba(255, 255, 255, 0.3);
      backdrop-filter: blur(20px);
    }

    .btn-outline-custom:hover {
      background: white;
      color: var(--primary-color);
      transform: translateY(-3px);
    }

    /* Sections */
    .section {
      padding: 5rem 0;
    }

    .section-title {
      font-size: 2.5rem;
      font-weight: 700;
      text-align: center;
      margin-bottom: 3rem;
      color: var(--dark-color);
      position: relative;
    }

    .section-title::after {
      content: '';
      position: absolute;
      width: 80px;
      height: 4px;
      background: var(--gradient-primary);
      bottom: -10px;
      left: 50%;
      transform: translateX(-50%);
      border-radius: 2px;
    }

    /* Book Cards */
    .book-card {
      background: white;
      border-radius: 20px;
      padding: 1.5rem;
      box-shadow: var(--shadow-md);
      transition: all 0.3s ease;
      height: 100%;
      border: none;
      overflow: hidden;
    }

    .book-card:hover {
      transform: translateY(-10px);
      box-shadow: var(--shadow-xl);
    }

    .book-image {
      width: 100%;
      height: 280px;
      object-fit: cover;
      border-radius: 15px;
      margin-bottom: 1rem;
      transition: transform 0.3s ease;
    }

    .book-card:hover .book-image {
      transform: scale(1.05);
    }

    .book-title {
      font-size: 1.1rem;
      font-weight: 600;
      color: var(--dark-color);
      margin-bottom: 0.5rem;
      line-height: 1.3;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }

    .book-author {
      color: #6b7280;
      font-size: 0.9rem;
      margin-bottom: 0.5rem;
    }

    .book-rating {
      color: var(--accent-color);
      margin-bottom: 1rem;
      font-size: 0.9rem;
    }

    .book-description {
      font-size: 0.85rem;
      color: #6b7280;
      line-height: 1.4;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      overflow: hidden;
      margin-bottom: 1rem;
    }

    .book-price {
      font-size: 1.1rem;
      font-weight: 600;
      color: var(--success-color);
      margin-bottom: 1rem;
    }

    /* About Section */
    .about-section {
      background: var(--light-color);
    }

    .feature-card {
      background: white;
      padding: 2.5rem 2rem;
      border-radius: 20px;
      text-align: center;
      box-shadow: var(--shadow-md);
      transition: all 0.3s ease;
      height: 100%;
    }

    .feature-card:hover {
      transform: translateY(-10px);
      box-shadow: var(--shadow-xl);
    }

    .feature-icon {
      font-size: 3rem;
      background: var(--gradient-primary);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      margin-bottom: 1.5rem;
    }

    .feature-title {
      font-size: 1.3rem;
      font-weight: 600;
      margin-bottom: 1rem;
      color: var(--dark-color);
    }

    .feature-description {
      color: #6b7280;
      line-height: 1.6;
    }

    /* Stats Section */
    .stats-section {
      background: var(--gradient-secondary);
      color: white;
    }

    .stat-item {
      text-align: center;
      padding: 2rem 1rem;
    }

    .stat-number {
      font-size: 3rem;
      font-weight: 800;
      margin-bottom: 0.5rem;
      display: block;
    }

    .stat-label {
      font-size: 1.1rem;
      opacity: 0.9;
    }

    /* Loading */
    .loading {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      padding: 3rem;
    }

    .spinner {
      width: 50px;
      height: 50px;
      border: 4px solid #f3f3f3;
      border-top: 4px solid var(--primary-color);
      border-radius: 50%;
      animation: spin 1s linear infinite;
      margin-bottom: 1rem;
    }

    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
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

    /* Footer */
    .footer {
      background: var(--dark-color);
      color: white;
      padding: 4rem 0 2rem;
    }

    .footer-title {
      font-size: 1.2rem;
      font-weight: 600;
      margin-bottom: 1.5rem;
      color: white;
    }

    .footer-link {
      color: #9ca3af;
      text-decoration: none;
      transition: color 0.3s ease;
      display: block;
      margin-bottom: 0.5rem;
    }

    .footer-link:hover {
      color: var(--primary-color);
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
        padding: 0.8rem 1.5rem;
        font-size: 1rem;
      }

      .book-image {
        height: 200px;
      }
    }

    /* Animate on scroll */
    .animate-on-scroll {
      opacity: 0;
      transform: translateY(30px);
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
<nav class="navbar navbar-expand-lg fixed-top">
  <div class="container">
    <a class="navbar-brand" href="#home">
      <i class="fas fa-book-open me-2"></i>Pahana Education
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
          <a class="nav-link" href="#books">Books</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#about">About</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#contact">Contact</a>
        </li>
        <li class="nav-item">
          <a class="nav-link btn btn-outline-primary ms-2" href="${pageContext.request.contextPath}/login">Login</a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<!-- Hero Section -->
<section class="hero-section" id="home">
  <div class="container">
    <div class="row align-items-center">
      <div class="col-lg-12 text-center hero-content">
        <h1 class="hero-title">Welcome to Pahana Education</h1>
        <p class="hero-subtitle">Discover thousands of books and expand your knowledge with our premium collection</p>

        <div class="search-container">
          <form id="bookSearchForm">
            <input type="text" class="search-box" id="bookSearch" placeholder="Search for books, authors, or subjects...">
            <button type="submit" class="search-btn">
              <i class="fas fa-search"></i> Search
            </button>
          </form>
        </div>

        <div class="cta-buttons">
          <a href="#books" class="btn-custom btn-primary-custom">Browse Books</a>
          <a href="#about" class="btn-custom btn-outline-custom">Learn More</a>
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
    </div>
  </div>
</section>

<!-- About Section -->
<section class="section about-section" id="about">
  <div class="container">
    <div class="row align-items-center">
      <div class="col-lg-6 animate-on-scroll">
        <h2 class="section-title text-start">About Pahana Education</h2>
        <p class="lead">Your premier destination for educational books and learning resources.</p>
        <p>At Pahana Education, we believe in the power of knowledge to transform lives. Our extensive collection includes textbooks, reference materials, fiction, non-fiction, and specialized educational content to support learners at every level.</p>
        <ul class="list-unstyled">
          <li><i class="fas fa-check text-success me-2"></i> Over 50,000+ books in our collection</li>
          <li><i class="fas fa-check text-success me-2"></i> Expert-curated educational content</li>
          <li><i class="fas fa-check text-success me-2"></i> Fast and reliable delivery</li>
          <li><i class="fas fa-check text-success me-2"></i> Competitive prices and discounts</li>
        </ul>
      </div>
      <div class="col-lg-6">
        <div class="row">
          <div class="col-md-6 mb-4">
            <div class="feature-card animate-on-scroll">
              <i class="fas fa-book feature-icon"></i>
              <h4 class="feature-title">Vast Collection</h4>
              <p class="feature-description">Browse through thousands of carefully selected books across all subjects and genres.</p>
            </div>
          </div>
          <div class="col-md-6 mb-4">
            <div class="feature-card animate-on-scroll">
              <i class="fas fa-shipping-fast feature-icon"></i>
              <h4 class="feature-title">Fast Delivery</h4>
              <p class="feature-description">Quick and reliable shipping to get your books to you as soon as possible.</p>
            </div>
          </div>
          <div class="col-md-6 mb-4">
            <div class="feature-card animate-on-scroll">
              <i class="fas fa-star feature-icon"></i>
              <h4 class="feature-title">Quality Assured</h4>
              <p class="feature-description">All books are carefully inspected to ensure the highest quality standards.</p>
            </div>
          </div>
          <div class="col-md-6 mb-4">
            <div class="feature-card animate-on-scroll">
              <i class="fas fa-headset feature-icon"></i>
              <h4 class="feature-title">24/7 Support</h4>
              <p class="feature-description">Our dedicated customer service team is here to help you anytime.</p>
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
        <div class="stat-item">
          <span class="stat-number" data-count="50000">0</span>
          <div class="stat-label">Books Available</div>
        </div>
      </div>
      <div class="col-md-3 col-sm-6">
        <div class="stat-item">
          <span class="stat-number" data-count="25000">0</span>
          <div class="stat-label">Happy Customers</div>
        </div>
      </div>
      <div class="col-md-3 col-sm-6">
        <div class="stat-item">
          <span class="stat-number" data-count="100">0</span>
          <div class="stat-label">Categories</div>
        </div>
      </div>
      <div class="col-md-3 col-sm-6">
        <div class="stat-item">
          <span class="stat-number" data-count="5">0</span>
          <div class="stat-label">Years Experience</div>
        </div>
      </div>
    </div>
  </div>
</section>

<!-- Footer -->
<footer class="footer" id="contact">
  <div class="container">
    <div class="row">
      <div class="col-lg-4 mb-4">
        <h5 class="footer-title">Pahana Education</h5>
        <p>Your trusted partner in education and learning. We provide quality books and educational resources to help you achieve your academic goals.</p>
        <div class="social-links">
          <a href="#" class="footer-link d-inline me-3"><i class="fab fa-facebook-f"></i></a>
          <a href="#" class="footer-link d-inline me-3"><i class="fab fa-twitter"></i></a>
          <a href="#" class="footer-link d-inline me-3"><i class="fab fa-instagram"></i></a>
          <a href="#" class="footer-link d-inline"><i class="fab fa-linkedin-in"></i></a>
        </div>
      </div>
      <div class="col-lg-2 mb-4">
        <h5 class="footer-title">Quick Links</h5>
        <a href="#home" class="footer-link">Home</a>
        <a href="#books" class="footer-link">Books</a>
        <a href="#about" class="footer-link">About</a>
        <a href="#contact" class="footer-link">Contact</a>
      </div>
      <div class="col-lg-3 mb-4">
        <h5 class="footer-title">Categories</h5>
        <a href="#" class="footer-link">Textbooks</a>
        <a href="#" class="footer-link">Fiction</a>
        <a href="#" class="footer-link">Non-Fiction</a>
        <a href="#" class="footer-link">Reference</a>
      </div>
      <div class="col-lg-3 mb-4">
        <h5 class="footer-title">Contact Info</h5>
        <p class="footer-link"><i class="fas fa-map-marker-alt me-2"></i> 123 Education Street, Learning City</p>
        <p class="footer-link"><i class="fas fa-phone me-2"></i> +1 (555) 123-4567</p>
        <p class="footer-link"><i class="fas fa-envelope me-2"></i> info@pahanaedu.com</p>
      </div>
    </div>
    <hr class="my-4" style="border-color: #374151;">
    <div class="row align-items-center">
      <div class="col-md-6">
        <p>&copy; 2025 Pahana Education. All rights reserved.</p>
      </div>
      <div class="col-md-6 text-md-end">
        <a href="#" class="footer-link me-3">Privacy Policy</a>
        <a href="#" class="footer-link">Terms of Service</a>
      </div>
    </div>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Configuration
  const API_KEY = 'AIzaSyDhoqdHraaknp7HgKscwjrxY3Ob2xn5Hag';
  const API_BASE_URL = 'https://www.googleapis.com/books/v1/volumes';

  // Sample popular books for fallback
  const SAMPLE_BOOKS = [
    {
      title: "The Great Gatsby",
      author: "F. Scott Fitzgerald",
      image: "https://covers.openlibrary.org/b/isbn/9780743273565-L.jpg",
      description: "A classic American novel set in the Jazz Age, exploring themes of wealth, love, and the American Dream.",
      rating: 4.2,
      price: "$12.99"
    },
    {
      title: "To Kill a Mockingbird",
      author: "Harper Lee",
      image: "https://covers.openlibrary.org/b/isbn/9780061120084-L.jpg",
      description: "A gripping tale of racial injustice and childhood innocence in the American South.",
      rating: 4.5,
      price: "$14.99"
    },
    {
      title: "1984",
      author: "George Orwell",
      image: "https://covers.openlibrary.org/b/isbn/9780451524935-L.jpg",
      description: "A dystopian social science fiction novel about totalitarian control and surveillance.",
      rating: 4.3,
      price: "$13.99"
    },
    {
      title: "Pride and Prejudice",
      author: "Jane Austen",
      image: "https://covers.openlibrary.org/b/isbn/9780141439518-L.jpg",
      description: "A romantic novel dealing with issues of manners, upbringing, and marriage.",
      rating: 4.4,
      price: "$11.99"
    },
    {
      title: "The Catcher in the Rye",
      author: "J.D. Salinger",
      image: "https://covers.openlibrary.org/b/isbn/9780316769174-L.jpg",
      description: "A controversial novel about teenage rebellion and alienation in post-war America.",
      rating: 3.8,
      price: "$12.99"
    },
    {
      title: "Lord of the Flies",
      author: "William Golding",
      image: "https://covers.openlibrary.org/b/isbn/9780571056866-L.jpg",
      description: "A story about a group of British boys stranded on an uninhabited island.",
      rating: 4.0,
      price: "$10.99"
    },
    {
      title: "Harry Potter and the Philosopher's Stone",
      author: "J.K. Rowling",
      image: "https://covers.openlibrary.org/b/isbn/9780747532699-L.jpg",
      description: "The first book in the magical Harry Potter series about a young wizard's adventures.",
      rating: 4.7,
      price: "$15.99"
    },
    {
      title: "The Hobbit",
      author: "J.R.R. Tolkien",
      image: "https://covers.openlibrary.org/b/isbn/9780547928227-L.jpg",
      description: "A fantasy novel about Bilbo Baggins' unexpected journey to reclaim treasure.",
      rating: 4.6,
      price: "$16.99"
    }
  ];

  // Load popular books
  async function loadPopularBooks() {
    const container = document.getElementById('popularBooksContainer');

    try {
      // Try to load from Google Books API
      const response = await fetch(`${API_BASE_URL}?q=bestsellers&maxResults=8&key=${API_KEY}`);

      if (response.ok) {
        const data = await response.json();
        if (data.items && data.items.length > 0) {
          displayBooks(data.items, container);
          return;
        }
      }
    } catch (error) {
      console.log('API failed, using sample books:', error);
    }

    // Fallback to sample books
    displaySampleBooks(container);
  }

  // Display books from API
  function displayBooks(books, container) {
    const booksHTML = books.map(book => {
      const volumeInfo = book.volumeInfo;
      const title = volumeInfo.title || 'Unknown Title';
      const authors = volumeInfo.authors ? volumeInfo.authors.join(', ') : 'Unknown Author';
      const thumbnail = volumeInfo.imageLinks?.thumbnail || volumeInfo.imageLinks?.smallThumbnail || 'https://via.placeholder.com/200x280/e2e8f0/64748b?text=No+Image';
      const description = volumeInfo.description ?
              (volumeInfo.description.length > 150 ? volumeInfo.description.substring(0, 147) + '...' : volumeInfo.description) :
              'No description available';
      const rating = volumeInfo.averageRating || (Math.random() * 2 + 3).toFixed(1);
      const price = volumeInfo.listPrice ?
              `$${volumeInfo.listPrice.amount}` :
              `$${(Math.random() * 20 + 10).toFixed(2)}`;

      return createBookCardHTML(title, authors, thumbnail, description, rating, price);
    }).join('');

    container.innerHTML = booksHTML;
    animateOnScroll();
  }

  // Display sample books as fallback
  function displaySampleBooks(container) {
    const booksHTML = SAMPLE_BOOKS.map(book =>
            createBookCardHTML(book.title, book.author, book.image, book.description, book.rating, book.price)
    ).join('');

    container.innerHTML = booksHTML;
    animateOnScroll();
  }

  // Create book card HTML
  function createBookCardHTML(title, authors, thumbnail, description, rating, price) {
    const stars = '★'.repeat(Math.floor(rating)) + '☆'.repeat(5 - Math.floor(rating));

    return `
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="book-card animate-on-scroll">
                    <img src="${thumbnail}" alt="${title}" class="book-image" onerror="this.src='https://via.placeholder.com/200x280/e2e8f0/64748b?text=No+Image'">
                    <h5 class="book-title">${title}</h5>
                    <p class="book-author">by ${authors}</p>
                    <div class="book-rating">
                        <span class="stars">${stars}</span>
                        <span class="rating-text">(${rating})</span>
                    </div>
                    <p class="book-description">${description}</p>
                    <div class="book-price">${price}</div>
                    <button class="btn btn-primary w-100" onclick="addToCart('${title.replace(/'/g, "\\'")}')">
                        <i class="fas fa-cart-plus me-2"></i>Add to Cart
                    </button>
                </div>
            </div>
        `;
  }

  // Search books
  async function searchBooks(query) {
    const resultsSection = document.getElementById('searchResults');
    const container = document.getElementById('searchResultsContainer');
    const booksSection = document.getElementById('books');

    // Show loading
    container.innerHTML = `
            <div class="loading">
                <div class="spinner"></div>
                <p>Searching for "${query}"...</p>
            </div>
        `;

    resultsSection.classList.remove('d-none');
    booksSection.style.display = 'none';

    try {
      const response = await fetch(`${API_BASE_URL}?q=${encodeURIComponent(query)}&maxResults=12&key=${API_KEY}`);

      if (response.ok) {
        const data = await response.json();
        if (data.items && data.items.length > 0) {
          displayBooks(data.items, container);
        } else {
          container.innerHTML = `
                        <div class="col-12 text-center py-5">
                            <i class="fas fa-search fa-3x text-muted mb-3"></i>
                            <h5>No books found</h5>
                            <p class="text-muted">No books found for "${query}". Try different search terms.</p>
                            <button class="btn btn-primary" onclick="showPopularBooks()">Browse Popular Books</button>
                        </div>
                    `;
        }
      } else {
        throw new Error('Search failed');
      }
    } catch (error) {
      console.error('Search error:', error);
      container.innerHTML = `
                <div class="col-12 text-center py-5">
                    <i class="fas fa-exclamation-triangle fa-3x text-warning mb-3"></i>
                    <h5>Search Error</h5>
                    <p class="text-muted">Unable to search at the moment. Please try again later.</p>
                    <button class="btn btn-primary" onclick="showPopularBooks()">Browse Popular Books</button>
                </div>
            `;
    }
  }

  // Show popular books
  function showPopularBooks() {
    const resultsSection = document.getElementById('searchResults');
    const booksSection = document.getElementById('books');

    resultsSection.classList.add('d-none');
    booksSection.style.display = 'block';

    // Clear search
    document.getElementById('bookSearch').value = '';
  }

  // Add to cart function
  function addToCart(bookTitle) {
    // Simple alert for now - in real app this would add to cart
    alert(`"${bookTitle}" has been added to your cart!`);
  }

  // Animate on scroll
  function animateOnScroll() {
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add('animated');
        }
      });
    }, { threshold: 0.1 });

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
              current = target;
              clearInterval(timer);
            }
            counter.textContent = Math.floor(current).toLocaleString();
          }, 16);

          observer.unobserve(counter);
        }
      });
    });

    counters.forEach(counter => observer.observe(counter));
  }

  // Smooth scrolling
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
    } else {
      navbar.style.background = 'rgba(255, 255, 255, 0.95)';
    }
  });

  // Initialize everything
  document.addEventListener('DOMContentLoaded', function() {
    loadPopularBooks();
    animateOnScroll();
    animateCounters();
  });
</script>
</body>
</html>