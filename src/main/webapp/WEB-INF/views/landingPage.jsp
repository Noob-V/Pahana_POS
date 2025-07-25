<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Pahana Education Bookshop</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <style>
    :root {
      --primary-color: #FF9800;
      --secondary-color: #FFC107;
      --accent-color: #FF5722;
      --dark-color: #333333;
      --light-color: #FAFAFA;
    }

    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      color: var(--dark-color);
      overflow-x: hidden;
    }

    .navbar {
      background-color: transparent;
      transition: all 0.4s;
      padding: 1rem 2rem;
    }

    .navbar.scrolled {
      background-color: white;
      box-shadow: 0 3px 10px rgba(0,0,0,0.15);
      padding: 0.8rem 2rem;
    }

    .navbar-brand {
      font-weight: 700;
      color: var(--primary-color);
      font-size: 1.5rem;
    }

    .navbar-nav .nav-link {
      color: var(--dark-color);
      font-weight: 500;
      margin: 0 10px;
      transition: all 0.3s;
    }

    .navbar-nav .nav-link:hover {
      color: var(--primary-color);
      transform: translateY(-2px);
    }

    .hero-section {
      background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
      min-height: 100vh;
      display: flex;
      align-items: center;
      position: relative;
      overflow: hidden;
      padding: 80px 0;
    }

    .hero-content {
      position: relative;
      z-index: 1;
    }

    .hero-title {
      font-size: 3.5rem;
      font-weight: 700;
      margin-bottom: 1.5rem;
      color: white;
    }

    .hero-text {
      font-size: 1.2rem;
      margin-bottom: 2rem;
      color: rgba(255,255,255,0.9);
    }

    .btn-hero {
      background-color: white;
      color: var(--primary-color);
      border: none;
      font-weight: 600;
      padding: 12px 30px;
      border-radius: 50px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.1);
      transition: all 0.3s;
    }

    .btn-hero:hover {
      transform: translateY(-3px);
      box-shadow: 0 8px 25px rgba(0,0,0,0.15);
      background-color: #f8f8f8;
    }

    .section {
      padding: 100px 0;
    }

    .section-title {
      font-size: 2.5rem;
      font-weight: 700;
      margin-bottom: 1rem;
      color: var(--dark-color);
    }

    .section-subtitle {
      font-size: 1.1rem;
      color: #666;
      margin-bottom: 3rem;
    }

    .book-card {
      border: none;
      border-radius: 10px;
      overflow: hidden;
      transition: all 0.3s;
      height: 100%;
      box-shadow: 0 5px 15px rgba(0,0,0,0.08);
    }

    .book-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 25px rgba(0,0,0,0.15);
    }

    .book-img-container {
      height: 250px;
      overflow: hidden;
      display: flex;
      align-items: center;
      justify-content: center;
      background-color: #f5f5f5;
    }

    .book-img {
      height: 100%;
      object-fit: contain;
    }

    .book-title {
      font-weight: 600;
      margin-top: 1rem;
      font-size: 1.1rem;
      line-height: 1.4;
    }

    .book-author {
      color: #666;
      font-size: 0.9rem;
    }

    .carousel-control-next,
    .carousel-control-prev {
      width: 5%;
      opacity: 0.7;
    }

    .about-section {
      background: linear-gradient(135deg, #f8f9fa, #e9ecef);
    }

    .stats-section {
      background-color: var(--primary-color);
      color: white;
    }

    .counter {
      font-size: 3rem;
      font-weight: 700;
      margin-bottom: 1rem;
    }

    .footer {
      background-color: var(--dark-color);
      color: white;
      padding: 60px 0 30px;
    }

    .footer-logo {
      font-size: 1.8rem;
      font-weight: 700;
      margin-bottom: 1rem;
    }

    .footer-text {
      color: rgba(255,255,255,0.7);
      margin-bottom: 1.5rem;
    }

    .footer-links li {
      margin-bottom: 0.8rem;
    }

    .footer-links a {
      color: rgba(255,255,255,0.7);
      text-decoration: none;
      transition: all 0.3s;
    }

    .footer-links a:hover {
      color: white;
      transform: translateX(5px);
    }

    .social-links a {
      display: inline-block;
      height: 40px;
      width: 40px;
      background-color: rgba(255,255,255,0.1);
      border-radius: 50%;
      margin-right: 10px;
      line-height: 40px;
      text-align: center;
      color: white;
      transition: all 0.3s;
    }

    .social-links a:hover {
      background-color: var(--primary-color);
      transform: translateY(-3px);
    }

    .fade-in {
      opacity: 0;
      transform: translateY(30px);
      transition: opacity 1s ease, transform 1s ease;
    }

    .fade-in.visible {
      opacity: 1;
      transform: translateY(0);
    }

    /* Search Results Styling */
    #searchResults {
      min-height: 400px;
    }

    .search-result-count {
      font-weight: 600;
      margin-bottom: 2rem;
    }

    .search-no-results {
      text-align: center;
      padding: 3rem;
    }

    .search-icon {
      font-size: 4rem;
      color: #ddd;
      margin-bottom: 1.5rem;
    }
  </style>
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg fixed-top">
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
          <a class="nav-link" href="#about">About Us</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#contact">Contact</a>
        </li>
        <li class="nav-item ms-3">
          <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-warning">Login</a>
        </li>
        <li class="nav-item ms-2">
          <a href="${pageContext.request.contextPath}/register" class="btn btn-warning">Register</a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<!-- Hero Section -->
<section class="hero-section" id="home">
  <div class="container">
    <div class="row align-items-center">
      <div class="col-lg-6 hero-content">
        <h1 class="hero-title">Discover Your Next Favorite Book</h1>
        <p class="hero-text">
          Explore our vast collection of books spanning all genres and interests.
          From bestsellers to hidden gems, find the perfect read for every occasion.
        </p>
        <form id="bookSearchForm" class="mb-4">
          <div class="input-group">
            <input type="text" class="form-control form-control-lg" id="bookSearch"
                   placeholder="Search by title, author, or ISBN...">
            <button class="btn btn-lg btn-hero" type="submit">
              <i class="fas fa-search"></i> Search
            </button>
          </div>
        </form>
        <div class="d-flex mt-4">
          <a href="#books" class="btn btn-hero me-3">
            <i class="fas fa-book me-2"></i>Browse Books
          </a>
          <a href="#about" class="btn btn-outline-light">
            Learn More
          </a>
        </div>
      </div>
      <div class="col-lg-6">
        <img src="https://images.unsplash.com/photo-1507842217343-583bb7270b66?ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=80"
             alt="Books" class="img-fluid rounded shadow">
      </div>
    </div>
  </div>
</section>

<!-- Popular Books Section -->
<section class="section" id="books">
  <div class="container">
    <div class="text-center">
      <h2 class="section-title">Popular Books</h2>
      <p class="section-subtitle">Discover bestsellers and critically acclaimed titles</p>
    </div>

    <div id="booksCarousel" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <!-- Carousel items will be added dynamically -->
        <div class="carousel-item active">
          <div class="row">
            <div class="col-md-3">
              <div class="card book-card">
                <div class="book-img-container">
                  <img src="https://covers.openlibrary.org/b/id/8231990-L.jpg" class="book-img" alt="Book cover">
                </div>
                <div class="card-body">
                  <h5 class="book-title">To Kill a Mockingbird</h5>
                  <p class="book-author">Harper Lee</p>
                </div>
              </div>
            </div>
            <div class="col-md-3">
              <div class="card book-card">
                <div class="book-img-container">
                  <img src="https://covers.openlibrary.org/b/id/12010275-L.jpg" class="book-img" alt="Book cover">
                </div>
                <div class="card-body">
                  <h5 class="book-title">1984</h5>
                  <p class="book-author">George Orwell</p>
                </div>
              </div>
            </div>
            <div class="col-md-3">
              <div class="card book-card">
                <div class="book-img-container">
                  <img src="https://covers.openlibrary.org/b/id/7895280-L.jpg" class="book-img" alt="Book cover">
                </div>
                <div class="card-body">
                  <h5 class="book-title">Pride and Prejudice</h5>
                  <p class="book-author">Jane Austen</p>
                </div>
              </div>
            </div>
            <div class="col-md-3">
              <div class="card book-card">
                <div class="book-img-container">
                  <img src="https://covers.openlibrary.org/b/id/12008084-L.jpg" class="book-img" alt="Book cover">
                </div>
                <div class="card-body">
                  <h5 class="book-title">The Great Gatsby</h5>
                  <p class="book-author">F. Scott Fitzgerald</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#booksCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#booksCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
  </div>
</section>

<!-- Search Results Section -->
<section class="section d-none" id="searchResults">
  <div class="container">
    <h2 class="section-title">Search Results</h2>
    <p id="searchResultsCount" class="search-result-count"></p>

    <div id="searchResultsContainer" class="row">
      <!-- Search results will be displayed here -->
    </div>
  </div>
</section>

<!-- About Section -->
<section class="section about-section" id="about">
  <div class="container">
    <div class="row align-items-center">
      <div class="col-lg-6 fade-in">
        <h2 class="section-title">About Pahana Education</h2>
        <p class="mb-4">
          Pahana Education is a premier bookshop dedicated to providing quality educational resources and literature to students,
          teachers, and book enthusiasts alike. Established with a vision to promote knowledge and literacy,
          we've been serving our community for over a decade.
        </p>
        <p class="mb-4">
          At Pahana Education, we believe that books are not just products but gateways to new worlds, ideas, and perspectives.
          Our carefully curated collection spans across all genres, academic disciplines, and age groups,
          ensuring that every reader finds their perfect match.
        </p>
        <p>
          We pride ourselves on offering personalized recommendations, competitive prices,
          and a warm, welcoming environment where you can explore the vast universe of books at your leisure.
        </p>
        <div class="mt-4">
          <a href="#contact" class="btn btn-warning">Contact Us</a>
        </div>
      </div>
      <div class="col-lg-6 fade-in">
        <img src="https://images.unsplash.com/photo-1521587760476-6c12a4b040da?ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=80"
             alt="Bookshop interior" class="img-fluid rounded shadow">
      </div>
    </div>
  </div>
</section>

<!-- Stats Section -->
<section class="section stats-section">
  <div class="container">
    <div class="row text-center">
      <div class="col-md-3 mb-4 mb-md-0">
        <div class="counter" data-target="15000">0</div>
        <div>Books in Stock</div>
      </div>
      <div class="col-md-3 mb-4 mb-md-0">
        <div class="counter" data-target="5000">0</div>
        <div>Happy Customers</div>
      </div>
      <div class="col-md-3 mb-4 mb-md-0">
        <div class="counter" data-target="500">0</div>
        <div>New Releases</div>
      </div>
      <div class="col-md-3">
        <div class="counter" data-target="10">0</div>
        <div>Years of Service</div>
      </div>
    </div>
  </div>
</section>

<!-- Footer -->
<footer class="footer" id="contact">
  <div class="container">
    <div class="row">
      <div class="col-lg-4 mb-5 mb-lg-0">
        <div class="footer-logo">
          <i class="fas fa-graduation-cap me-2"></i>Pahana Education
        </div>
        <p class="footer-text">
          Your premier destination for books across all genres. Discover, learn, and grow with our extensive collection.
        </p>
        <div class="social-links">
          <a href="#"><i class="fab fa-facebook-f"></i></a>
          <a href="#"><i class="fab fa-twitter"></i></a>
          <a href="#"><i class="fab fa-instagram"></i></a>
          <a href="#"><i class="fab fa-linkedin-in"></i></a>
        </div>
      </div>
      <div class="col-lg-2 col-md-6 mb-5 mb-md-0">
        <h5 class="text-white mb-4">Quick Links</h5>
        <ul class="list-unstyled footer-links">
          <li><a href="#home">Home</a></li>
          <li><a href="#books">Books</a></li>
          <li><a href="#about">About Us</a></li>
          <li><a href="#contact">Contact</a></li>
        </ul>
      </div>
      <div class="col-lg-3 col-md-6 mb-5 mb-lg-0">
        <h5 class="text-white mb-4">Categories</h5>
        <ul class="list-unstyled footer-links">
          <li><a href="#">Fiction</a></li>
          <li><a href="#">Non-Fiction</a></li>
          <li><a href="#">Educational</a></li>
          <li><a href="#">Children's Books</a></li>
          <li><a href="#">Academic</a></li>
        </ul>
      </div>
      <div class="col-lg-3">
        <h5 class="text-white mb-4">Contact Info</h5>
        <ul class="list-unstyled footer-links">
          <li><i class="fas fa-map-marker-alt me-2"></i> 123 Book Street, Reading City</li>
          <li><i class="fas fa-phone me-2"></i> +94 11 2345678</li>
          <li><i class="fas fa-envelope me-2"></i> info@pahanaedu.com</li>
          <li><i class="fas fa-clock me-2"></i> Mon-Sat: 9AM - 6PM</li>
        </ul>
      </div>
    </div>
    <hr class="mt-5 mb-4" style="background-color: rgba(255,255,255,0.1);">
    <div class="text-center">
      <p class="mb-0">© 2023 Pahana Education Bookshop. All Rights Reserved.</p>
    </div>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Navbar scroll effect
  window.addEventListener('scroll', function() {
    const navbar = document.querySelector('.navbar');
    if (window.scrollY > 50) {
      navbar.classList.add('scrolled');
    } else {
      navbar.classList.remove('scrolled');
    }
  });

  // Smooth scrolling for anchor links
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        window.scrollTo({
          top: target.offsetTop - 70,
          behavior: 'smooth'
        });
      }
    });
  });

  // Animation on scroll
  function animateOnScroll() {
    const elements = document.querySelectorAll('.fade-in');
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add('visible');
        }
      });
    }, { threshold: 0.1 });

    elements.forEach(element => {
      observer.observe(element);
    });
  }

  // Counter animation
  function animateCounters() {
    const counters = document.querySelectorAll('.counter');
    const speed = 200;

    counters.forEach(counter => {
      const target = parseInt(counter.getAttribute('data-target'));
      const increment = Math.trunc(target / speed);

      function updateCount() {
        const current = parseInt(counter.innerText);
        if (current < target) {
          counter.innerText = current + increment;
          setTimeout(updateCount, 20);
        } else {
          counter.innerText = target;
        }
      }

      updateCount();
    });
  }

  // Load popular books from Google Books API
  function loadPopularBooks() {
    const apiKey = 'AIzaSyDhoqdHraaknp7HgKscwjrxY3Ob2xn5Hag'; // Using the API key from your .env file
    const popularQueries = [
      'bestsellers',
      'award winning books',
      'classic literature',
      'most popular fiction'
    ];

    // Choose a random query from the list
    const randomQuery = popularQueries[Math.floor(Math.random() * popularQueries.length)];

    fetch(`https://www.googleapis.com/books/v1/volumes?q=${randomQuery}&maxResults=12&key=${apiKey}`)
            .then(response => {
              if (!response.ok) {
                throw new Error('Network response was not ok');
              }
              return response.json();
            })
            .then(data => {
              if (data.items && data.items.length > 0) {
                displayBooksInCarousel(data.items);
              } else {
                // Fall back to sample books if no results
                console.log('No books found, using sample data');
              }
            })
            .catch(error => {
              console.error('Error fetching popular books:', error);
              // The carousel already has sample books as fallback
            });
  }

  // Display books in carousel
  function displayBooksInCarousel(books) {
    const carousel = document.getElementById('booksCarousel');
    const carouselInner = carousel.querySelector('.carousel-inner');
    carouselInner.innerHTML = ''; // Clear existing content

    // Process books in groups of 4 for each carousel slide
    for (let i = 0; i < books.length; i += 4) {
      const slide = document.createElement('div');
      slide.className = i === 0 ? 'carousel-item active' : 'carousel-item';

      const row = document.createElement('div');
      row.className = 'row';

      // Add up to 4 books per slide
      for (let j = i; j < i + 4 && j < books.length; j++) {
        const book = books[j];
        const volumeInfo = book.volumeInfo;

        // Get book details
        const title = volumeInfo.title || 'Unknown Title';
        const authors = volumeInfo.authors ? volumeInfo.authors.join(', ') : 'Unknown Author';
        const thumbnail = volumeInfo.imageLinks && volumeInfo.imageLinks.thumbnail
                ? volumeInfo.imageLinks.thumbnail.replace('http:', 'https:')
                : 'https://via.placeholder.com/150x225?text=No+Cover';

        // Create book card
        const bookHTML = `
          <div class="col-md-3">
            <div class="card book-card">
              <div class="book-img-container">
                <img src="${thumbnail}" class="book-img" alt="${title}">
              </div>
              <div class="card-body">
                <h5 class="book-title">${title}</h5>
                <p class="book-author">${authors}</p>
              </div>
            </div>
          </div>
        `;

        row.innerHTML += bookHTML;
      }

      slide.appendChild(row);
      carouselInner.appendChild(slide);
    }
  }

  // Search books function
  function searchBooks(query) {
    const apiKey = 'AIzaSyDhoqdHraaknp7HgKscwjrxY3Ob2xn5Hag';
    const searchResultsSection = document.getElementById('searchResults');
    const searchResultsContainer = document.getElementById('searchResultsContainer');
    const searchResultsCount = document.getElementById('searchResultsCount');

    // Show loading state
    searchResultsSection.classList.remove('d-none');
    searchResultsContainer.innerHTML = '<div class="col-12 text-center"><div class="spinner-border text-warning" role="status"><span class="visually-hidden">Loading...</span></div></div>';
    searchResultsCount.textContent = `Searching for "${query}"...`;

    // Scroll to search results
    window.scrollTo({
      top: searchResultsSection.offsetTop - 100,
      behavior: 'smooth'
    });

    fetch(`https://www.googleapis.com/books/v1/volumes?q=${query}&maxResults=8&key=${apiKey}`)
            .then(response => {
              if (!response.ok) {
                throw new Error('Network response was not ok');
              }
              return response.json();
            })
            .then(data => {
              if (data.items && data.items.length > 0) {
                const totalResults = data.totalItems || data.items.length;
                searchResultsCount.textContent = `Found ${totalResults} results for "${query}"`;

                // Display search results
                searchResultsContainer.innerHTML = '';
                data.items.forEach(book => {
                  const volumeInfo = book.volumeInfo;
                  const title = volumeInfo.title || 'Unknown Title';
                  const authors = volumeInfo.authors ? volumeInfo.authors.join(', ') : 'Unknown Author';
                  const description = volumeInfo.description
                          ? volumeInfo.description.substring(0, 150) + (volumeInfo.description.length > 150 ? '...' : '')
                          : 'No description available.';
                  const thumbnail = volumeInfo.imageLinks && volumeInfo.imageLinks.thumbnail
                          ? volumeInfo.imageLinks.thumbnail.replace('http:', 'https:')
                          : 'https://via.placeholder.com/150x225?text=No+Cover';

                  const bookCard = document.createElement('div');
                  bookCard.className = 'col-md-6 col-lg-3 mb-4';
                  bookCard.innerHTML = `
              <div class="card book-card h-100">
                <div class="book-img-container">
                  <img src="${thumbnail}" class="book-img" alt="${title}">
                </div>
                <div class="card-body d-flex flex-column">
                  <h5 class="book-title">${title}</h5>
                  <p class="book-author">${authors}</p>
                  <p class="card-text flex-grow-1">${description}</p>
                </div>
              </div>
            `;

                  searchResultsContainer.appendChild(bookCard);
                });
              } else {
                // No results found
                searchResultsCount.textContent = `No results found for "${query}"`;
                searchResultsContainer.innerHTML = `
            <div class="col-12 search-no-results">
              <div class="search-icon"><i class="fas fa-search"></i></div>
              <h4>No books found</h4>
              <p>Try different keywords or check the spelling.</p>
            </div>
          `;
              }
            })
            .catch(error => {
              console.error('Error searching books:', error);
              searchResultsCount.textContent = `Error searching for "${query}"`;
              searchResultsContainer.innerHTML = `
          <div class="col-12 search-no-results">
            <div class="search-icon"><i class="fas fa-exclamation-triangle"></i></div>
            <h4>Something went wrong</h4>
            <p>There was an error processing your search. Please try again later.</p>
          </div>
        `;
            });
  }

  // Search form handler
  document.getElementById('bookSearchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const query = document.getElementById('bookSearch').value.trim();
    if (query) {
      searchBooks(query);
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