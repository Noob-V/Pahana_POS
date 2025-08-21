📚 Pahana POS - Advanced Bookstore Management System
An enterprise-grade Point of Sale (POS) system engineered with Java EE technologies, delivering a sophisticated solution for modern bookstore operations. This comprehensive platform seamlessly integrates inventory management, intelligent billing systems, customer relationship management, and multi-tiered staff administration in a unified, scalable architecture.
🚀 Features
🌟 Advanced Book Management

Dual Import Methods:

Manual Entry: Complete control with custom book addition featuring ISBN, title, author, pricing, and inventory details
Google Books Integration: Intelligent book import via Google Books API with automatic metadata population


Smart Inventory Control: Real-time stock tracking with automated low-stock alerts
Dynamic Categorization: Hierarchical category management with custom taxonomies
Advanced Search Engine: Multi-parameter search functionality across all book attributes
Inventory Control: Track book stock levels and manage categories
Point of Sale: Complete billing system with discount strategies
Customer Management: Customer registration, profiles, and purchase history
Staff Administration: Multi-role staff management with dashboards
Receipt Generation: Automated receipt printing and email notifications

Technical Highlights

Design Patterns: Implements Builder, Factory, Observer, Singleton, and Strategy patterns
Clean Architecture: Layered architecture with DAO, Service, and Controller layers
Responsive UI: Bootstrap-based frontend with JavaScript interactions
Security: Password encryption and user authentication
Database: MySQL integration with connection pooling

🛠️ Tech Stack

Backend: Java EE (Enterprise Edition)
Frontend: JSP, Bootstrap 5, JavaScript
Database: MySQL
Server: Apache Tomcat
Build Tool: Maven
External APIs: Google Books API

📁 Project Structure
src/main/java/pahanaedu/
├── config/                 # Configuration classes
├── controllers/           # Servlet controllers
├── dao/                  # Data Access Objects
├── dto/                  # Data Transfer Objects
├── entities/             # JPA Entities
├── mapper/               # Entity-DTO mappers
├── patterns/             # Design pattern implementations
├── services/             # Business logic layer
├── utils/                # Utility classes
└── web/                  # Web application entry point

src/main/webapp/
├── WEB-INF/views/        # JSP pages
│   ├── admin/            # Admin dashboard views
│   ├── billing/          # POS and billing views
│   ├── books/            # Book management views
│   ├── category/         # Category management views
│   ├── customers/        # Customer management views
│   └── staff/            # Staff management views
└── web.xml               # Web application descriptor

👥 User Roles

Admin

Full system access
Staff management
System configuration
Advanced reporting
Book management
Customer management 
POS operations
Manage Inventory

Staff

Book management
Customer service
POS operations
Add customers
Generate bills
Manage inventory

💡 Key Design Patterns Used

Factory Pattern: DAO creation and database connections
Builder Pattern: Complex object construction (Bills, Customers)
Observer Pattern: Bill notifications and audit logging
Strategy Pattern: Flexible discount calculations
Singleton Pattern: Configuration and utility classes

📊 Features Overview

Book Management

Add books manually or via Google Books API
Category-based organization
Stock level tracking
Advanced search functionality

Billing System

Real-time POS interface
Multiple discount strategies
Receipt generation
Payment processing

Customer Management

Customer registration and profiles
Purchase history tracking
Billing management

Reporting & Analytics

Sales reports
Inventory reports

🆘 Support

For support and questions:

Check the help documentation in /help

🚀 Future Enhancements

Mobile application
Advanced analytics dashboard
Multi-store support
Integration with payment gateways
Barcode scanning support
Advanced inventory management

**Pahana POS - Streamlining retail operations with modern technology.**
