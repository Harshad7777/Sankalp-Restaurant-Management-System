<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Sankalp Restaurant — Authentic culinary excellence, handcrafted recipes, fine dining ambience, and memorable dining experiences.">
    <title>Sankalp Restaurant | Luxury Fine Dining & Authentic Cuisine</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome 6 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,500;0,600;0,700;1,600;1,700&family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">

    <!-- Dynamic Favicon -->
    <link rel="icon" type="image/png" href="<%= contextPath %>/images/logo1.png" id="dynamicFavicon">

    <!-- Landing CSS -->
    <link rel="stylesheet" href="<%= contextPath %>/css/landing.css">
</head>

<body>

<!-- =========================================================
     NAVBAR
========================================================= -->
<nav class="navbar navbar-expand-lg fixed-top" id="mainNavbar">
    <div class="container">
        
        <!-- BRAND LOGO -->
        <a class="navbar-brand" href="#home">
            <div class="brand-emblem-wrap">
                <img src="<%= contextPath %>/images/logo1.png" alt="Sankalp Restaurant" class="brand-emblem-img" id="landingNavLogo">
            </div>
            <div class="brand-text">
                <span class="brand-name" id="landingNavName">Sankalp</span>
                <span class="brand-subtitle" id="landingNavSubtitle">FINE DINING & CULINARY SUITE</span>
            </div>
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav ms-auto align-items-lg-center">
                <li class="nav-item"><a class="nav-link active" href="#home">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="#about">About</a></li>
                <li class="nav-item"><a class="nav-link" href="#menu">Live Menu</a></li>
                <li class="nav-item"><a class="nav-link" href="#why-us">Experience</a></li>
                <li class="nav-item"><a class="nav-link" href="#gallery">Gallery</a></li>
                <li class="nav-item"><a class="nav-link" href="#contact">Reservations</a></li>
                <li class="nav-item ms-lg-3">
                    <a href="<%= contextPath %>/login.jsp" class="nav-login">
                        <i class="fa-solid fa-user-shield"></i> Staff Portal
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- =========================================================
     HERO SECTION
========================================================= -->
<section id="home" class="hero-section" style="background-image: url('<%= contextPath %>/images/login-bg.jpg');">
    <div class="hero-overlay"></div>
    <div class="container hero-container">
        <div class="row align-items-center min-vh-100">
            <div class="col-lg-8">
                <div class="hero-content">
                    
                    <div class="hero-badge-wrap">
                        <span class="hero-small-title">
                            <i class="fa-solid fa-crown"></i> Authentic Indian & Global Gourmet
                        </span>
                    </div>

                    <h1>
                        Where Royal Heritage Meets <br>
                        <span class="gold-text">Modern Gastronomy</span>
                    </h1>

                    <p class="hero-description">
                        Indulge in an exquisite culinary journey crafted with heirloom spices, master chef precision, and the warmth of timeless Indian hospitality.
                    </p>

                    <div class="hero-buttons">
                        <a href="#menu" class="btn-primary-custom">
                            <i class="fa-solid fa-book-open"></i> Explore Live Menu
                        </a>
                        <a href="#contact" class="btn-outline-custom">
                            <i class="fa-regular fa-calendar-check"></i> Reserve a Table
                        </a>
                    </div>

                    <div class="hero-stats-pills">
                        <div class="hero-pill">
                            <i class="fa-solid fa-star text-gold"></i>
                            <div>
                                <strong>4.9 / 5</strong>
                                <small>Over 12,000+ Reviews</small>
                            </div>
                        </div>
                        <div class="hero-pill">
                            <i class="fa-solid fa-award text-gold"></i>
                            <div>
                                <strong>15+ Years</strong>
                                <small>Culinary Excellence</small>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <a href="#about" class="scroll-down">
        <span>Discover More</span>
        <i class="fa-solid fa-chevron-down"></i>
    </a>
</section>

<!-- =========================================================
     ABOUT SECTION
========================================================= -->
<section id="about" class="about-section section-padding">
    <div class="container">
        <div class="row align-items-center g-5">
            <div class="col-lg-6 reveal">
                <div class="about-image-wrapper">
                    <img src="<%= contextPath %>/images/login-bg.jpg" alt="Sankalp Fine Dining Interior" class="about-image">
                    <div class="experience-card">
                        <strong>15+</strong>
                        <span>Years of<br>Royal Heritage</span>
                    </div>
                </div>
            </div>

            <div class="col-lg-6 reveal">
                <div class="section-heading text-start">
                    <span class="section-label">OUR HERITAGE</span>
                    <h2>Every Recipe Tells a <span class="gold-text">Story of Passion</span></h2>
                </div>

                <p class="about-text">
                    Founded on the philosophy of authentic taste and hospitality, <strong>Sankalp Restaurant</strong> brings together centuries-old culinary traditions with modern artistry.
                </p>

                <p class="about-text">
                    From slow-simmered gravies and sizzling charcoal grills to delicate handcrafted desserts, each creation is meticulously prepared using farm-fresh organic produce and handpicked spices.
                </p>

                <div class="about-features">
                    <div class="about-feature">
                        <div class="feature-icon"><i class="fa-solid fa-seedling"></i></div>
                        <div>
                            <h5>Farm-Fresh Ingredients</h5>
                            <p>Organic herbs & 100% natural cold-pressed spices.</p>
                        </div>
                    </div>

                    <div class="about-feature">
                        <div class="feature-icon"><i class="fa-solid fa-hat-chef"></i></div>
                        <div>
                            <h5>Master Chefs</h5>
                            <p>Culinary artisans trained in authentic regional specialties.</p>
                        </div>
                    </div>
                </div>

                <a href="#menu" class="btn-primary-custom mt-3">
                    View Complete Menu <i class="fa-solid fa-arrow-right"></i>
                </a>
            </div>
        </div>
    </div>
</section>

<!-- =========================================================
     LIVE MENU SECTION (DYNAMICALLY FETCHED FROM BACKEND API)
========================================================= -->
<section id="menu" class="menu-section section-padding">
    <div class="container">
        
        <!-- SECTION HEADER -->
        <div class="section-heading text-center reveal">
            <span class="section-label">CHEF'S SELECTION</span>
            <h2>Explore Our <span class="gold-text">Live Menu</span></h2>
            <p>Freshly prepared culinary masterpieces directly synced from our kitchen menu.</p>
        </div>

        <!-- PUBLIC RESTAURANT OUTLET SELECTOR -->
        <div class="d-flex justify-content-center mb-4 reveal">
            <div class="outlet-selector-box d-flex align-items-center justify-content-center flex-wrap">
                <label for="publicRestaurantSelect" class="me-2 text-warning fw-bold text-nowrap mb-0" style="font-size: 0.95rem;">
                    <i class="fa-solid fa-store me-1"></i> Select Restaurant Outlet:
                </label>
                <select id="publicRestaurantSelect" class="form-select d-inline-block w-auto border-0 text-white bg-dark shadow-none fw-semibold" style="cursor: pointer; font-size: 0.95rem; border-radius: 20px; max-width: 100%;" onchange="switchPublicRestaurantMenu(this.value)">
                    <option value="1" selected>🏛️ Sankalp Restaurant (South & North Indian)</option>
                    <option value="2">🍕 ABC Restaurant (Burgers, Pizza & Fast Food)</option>
                    <option value="3">🍷 Royal Fine Dining (Gourmet Steaks & Seafood)</option>
                </select>
            </div>
        </div>

        <!-- SEARCH & CATEGORY FILTER BAR -->
        <div class="menu-controls reveal">
            
            <!-- SEARCH INPUT -->
            <div class="menu-search-wrap">
                <i class="fa-solid fa-magnifying-glass search-icon"></i>
                <input type="text" id="menuSearchInput" placeholder="Search dishes (e.g. Paneer, Biryani, Dosa, Mocktail)..." autocomplete="off">
                <button type="button" id="clearMenuSearch" class="btn-clear-search" style="display: none;">
                    <i class="fa-solid fa-xmark"></i>
                </button>
            </div>

            <!-- DYNAMIC CATEGORY TABS -->
            <div class="category-tabs-container" id="categoryTabsContainer">
                <button type="button" class="cat-pill active" data-category-id="all">
                    <i class="fa-solid fa-utensils"></i> All Specialities
                </button>
                <!-- Dynamic categories loaded via JS -->
            </div>

        </div>

        <!-- DYNAMIC MENU CARDS GRID -->
        <div class="row g-4 mt-2" id="dynamicMenuContainer">
            
            <!-- SKELETON PLACEHOLDER -->
            <div class="col-md-6 col-lg-4">
                <div class="food-card skeleton-card">
                    <div class="skeleton-img"></div>
                    <div class="food-content">
                        <div class="skeleton-line" style="width: 70%; height: 20px;"></div>
                        <div class="skeleton-line" style="width: 90%; height: 14px; margin-top: 10px;"></div>
                        <div class="skeleton-line" style="width: 50%; height: 14px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-4">
                <div class="food-card skeleton-card">
                    <div class="skeleton-img"></div>
                    <div class="food-content">
                        <div class="skeleton-line" style="width: 70%; height: 20px;"></div>
                        <div class="skeleton-line" style="width: 90%; height: 14px; margin-top: 10px;"></div>
                        <div class="skeleton-line" style="width: 50%; height: 14px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-4">
                <div class="food-card skeleton-card">
                    <div class="skeleton-img"></div>
                    <div class="food-content">
                        <div class="skeleton-line" style="width: 70%; height: 20px;"></div>
                        <div class="skeleton-line" style="width: 90%; height: 14px; margin-top: 10px;"></div>
                        <div class="skeleton-line" style="width: 50%; height: 14px;"></div>
                    </div>
                </div>
            </div>

        </div>

        <!-- EMPTY STATE (SEARCH NOT FOUND) -->
        <div id="noMenuItemsMessage" class="no-menu-found" style="display: none;">
            <i class="fa-solid fa-bowl-food"></i>
            <h4>No culinary items match your search</h4>
            <p>Try searching for a different keyword or explore another category.</p>
            <button type="button" class="btn-outline-custom mt-2" onclick="resetMenuFilter()">Reset Filters</button>
        </div>

    </div>
</section>

<!-- =========================================================
     WHY US / STATS
========================================================= -->
<section id="why-us" class="stats-section">
    <div class="container">
        <div class="row text-center g-4">
            <div class="col-6 col-lg-3">
                <div class="stat-item">
                    <i class="fa-solid fa-bowl-food"></i>
                    <h3 class="counter" data-target="85">0</h3>
                    <p>Artisanal Dishes</p>
                </div>
            </div>
            <div class="col-6 col-lg-3">
                <div class="stat-item">
                    <i class="fa-solid fa-users"></i>
                    <h3 class="counter" data-target="15000">0</h3>
                    <p>Delighted Guests</p>
                </div>
            </div>
            <div class="col-6 col-lg-3">
                <div class="stat-item">
                    <i class="fa-solid fa-award"></i>
                    <h3 class="counter" data-target="15">0</h3>
                    <p>Years of Legacy</p>
                </div>
            </div>
            <div class="col-6 col-lg-3">
                <div class="stat-item">
                    <i class="fa-solid fa-star"></i>
                    <h3 class="counter" data-target="5">0</h3>
                    <p>Star Quality Rating</p>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- =========================================================
     GALLERY
========================================================= -->
<section id="gallery" class="gallery-section section-padding">
    <div class="container">
        <div class="section-heading text-center reveal">
            <span class="section-label">VISUAL DELIGHTS</span>
            <h2>Moments at <span class="gold-text">Sankalp</span></h2>
            <p>Glimpse into our ambient fine-dining spaces and artisanal culinary creations.</p>
        </div>

        <div class="row g-4 mt-2">
            <div class="col-md-4 col-sm-6 gallery-item reveal">
                <div class="gallery-img-box">
                    <img src="https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&w=800&q=85" alt="Dining Hall">
                    <div class="gallery-overlay"><i class="fa-solid fa-expand"></i></div>
                </div>
            </div>
            <div class="col-md-4 col-sm-6 gallery-item reveal">
                <div class="gallery-img-box">
                    <img src="https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=800&q=85" alt="Gourmet Dish">
                    <div class="gallery-overlay"><i class="fa-solid fa-expand"></i></div>
                </div>
            </div>
            <div class="col-md-4 col-sm-6 gallery-item reveal">
                <div class="gallery-img-box">
                    <img src="https://images.unsplash.com/photo-1551024506-0bccd828d307?auto=format&fit=crop&w=800&q=85" alt="Dessert Plate">
                    <div class="gallery-overlay"><i class="fa-solid fa-expand"></i></div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- =========================================================
     TABLE RESERVATION FORM
========================================================= -->
<section id="contact" class="reservation-section section-padding">
    <div class="container">
        <div class="reservation-card-wrap reveal">
            <div class="row g-0">
                <div class="col-lg-5 reservation-info-col">
                    <div class="reservation-info-content">
                        <span class="section-label">EXCLUSIVE BOOKING</span>
                        <h3>Reserve Your <br><span class="gold-text">Dining Table</span></h3>
                        <p>Enjoy guaranteed seating and customized dining experiences for you and your guests.</p>
                        
                        <div class="info-list">
                            <div class="info-item">
                                <i class="fa-solid fa-location-dot"></i>
                                <div>
                                    <strong>Location</strong>
                                    <span id="landingContactAddress">Opp. City Mall, Main Ring Road, Ahmedabad, Gujarat 380015</span>
                                </div>
                            </div>
                            <div class="info-item">
                                <i class="fa-solid fa-clock"></i>
                                <div>
                                    <strong>Opening Hours</strong>
                                    <span id="landingContactHours">Mon – Sun: 10:00 AM – 11:00 PM</span>
                                </div>
                            </div>
                            <div class="info-item">
                                <i class="fa-solid fa-phone"></i>
                                <div>
                                    <strong>Phone Inquiries</strong>
                                    <span id="landingContactPhone">+91 98765 43210</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-7 reservation-form-col">
                    <form id="reservationForm" class="reservation-form">
                        <h4 class="form-heading">Table Booking Request</h4>

                        <div class="row g-3">
                            <div class="col-md-12">
                                <label for="reservationRestaurantId" class="fw-bold text-warning">Target Restaurant Outlet *</label>
                                <select id="reservationRestaurantId" required class="form-select bg-dark text-white border-warning fw-semibold">
                                    <option value="1" selected>🏛️ Sankalp Restaurant (South & North Indian)</option>
                                    <option value="2">🍕 ABC Restaurant (Burgers, Pizza & Fast Food)</option>
                                    <option value="3">🍷 Royal Fine Dining (Gourmet Steaks & Seafood)</option>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="guestName">Full Name *</label>
                                <input type="text" id="guestName" placeholder="Your Name" required class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label for="guestPhone">Phone Number *</label>
                                <input type="tel" id="guestPhone" placeholder="10-digit mobile" required class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label for="guestEmail">Email Address (Optional)</label>
                                <input type="email" id="guestEmail" placeholder="name@example.com" class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label for="bookingDate">Date *</label>
                                <input type="date" id="bookingDate" required class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label for="bookingTime">Time Slot *</label>
                                <select id="bookingTime" required class="form-select">
                                    <option value="">Select Time</option>
                                    <option value="12:30 PM">12:30 PM (Lunch)</option>
                                    <option value="01:30 PM">01:30 PM (Lunch)</option>
                                    <option value="07:30 PM">07:30 PM (Dinner)</option>
                                    <option value="08:30 PM">08:30 PM (Dinner)</option>
                                    <option value="09:30 PM">09:30 PM (Dinner)</option>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="guestCount">Guests *</label>
                                <select id="guestCount" required class="form-select">
                                    <option value="2">2 Guests</option>
                                    <option value="4">4 Guests</option>
                                    <option value="6">6 Guests</option>
                                    <option value="8">8+ Guests (Party)</option>
                                </select>
                            </div>
                            <div class="col-12">
                                <label for="specialRequest">Special Dietary / Seating Request</label>
                                <textarea id="specialRequest" rows="3" placeholder="e.g. Window side table, high chair, birthday celebration..." class="form-control"></textarea>
                            </div>
                            <div class="col-12 mt-4">
                                <button type="submit" class="btn-primary-custom w-100">
                                    <i class="fa-solid fa-circle-check"></i> Confirm Reservation
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- =========================================================
     FOOTER
========================================================= -->
<footer class="landing-footer">
    <div class="container">
        <div class="row g-4 align-items-center">
            <div class="col-md-6 text-center text-md-start">
                <div class="footer-brand">
                    <img src="<%= contextPath %>/images/logo1.png" alt="Sankalp" class="footer-logo" id="landingFooterLogo">
                    <span id="landingFooterName">Sankalp Restaurant Management System</span>
                </div>
            </div>
            <div class="col-md-6 text-center text-md-end">
                <p class="copyright mb-0">
                    &copy; <span id="currentYear">2026</span> <span id="landingCopyrightName">Sankalp Restaurant</span>. All Rights Reserved.
                </p>
                <p class="developer-credit text-muted mt-1 mb-0" style="font-size: 0.85rem;">
                    Developed with <i class="fa-solid fa-heart text-danger"></i> by <strong>Harshad Rakshe</strong>
                </p>
            </div>
        </div>
    </div>
</footer>

<!-- BACK TO TOP BUTTON -->
<button type="button" id="backToTop" class="btn-back-to-top" title="Back to Top">
    <i class="fa-solid fa-arrow-up"></i>
</button>

<!-- RESERVATION SUCCESS MODAL -->
<div class="modal fade" id="reservationModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content modal-success-content" style="background:#1a1918; color:#fff; border:1px solid rgba(212,175,55,0.3); border-radius:16px;">
            <div class="modal-body text-center p-4">
                <div class="success-icon-box" style="width:70px; height:70px; margin:0 auto; background:rgba(212,175,55,0.15); color:#d4af37; border-radius:50%; display:flex; align-items:center; justify-content:center; font-size:2rem;">
                    <i class="fa-solid fa-circle-check"></i>
                </div>
                <h3 class="mt-3" style="font-family:'Cormorant Garamond',serif; color:#d4af37; font-weight:700;">Reservation Confirmed!</h3>
                <p class="text-muted mb-3" style="color:#aaa !important;">Thank you! Your table booking request has been registered in our system.</p>
                
                <div id="reservationDetailsCard" class="p-3 mb-3 text-start" style="background:rgba(255,255,255,0.05); border-radius:10px; border:1px dashed rgba(212,175,55,0.3); font-size:0.9rem;">
                    <div class="d-flex justify-content-between mb-2">
                        <span style="color:#aaa;">Booking Code:</span>
                        <strong id="modalResCode" style="color:#d4af37; font-size:1rem; letter-spacing:1px;">RES-XXXXX</strong>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span style="color:#aaa;">Guest Name:</span>
                        <span id="modalResName" class="fw-semibold">Guest</span>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span style="color:#aaa;">Date & Time:</span>
                        <span id="modalResDateTime" class="fw-semibold">-</span>
                    </div>
                    <div class="d-flex justify-content-between">
                        <span style="color:#aaa;">Party Size:</span>
                        <span id="modalResGuests" class="fw-semibold">2 Guests</span>
                    </div>
                </div>

                <p style="font-size:0.85rem; color:#888;" class="mb-3">
                    <i class="fa-solid fa-info-circle text-gold me-1"></i> Our restaurant team will contact you at your phone number if table assignment updates are needed.
                </p>

                <button type="button" class="btn-primary-custom w-100 py-2" data-bs-dismiss="modal">
                    Done
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap 5 Bundle JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Landing JS -->
<script src="<%= contextPath %>/js/config.js?v=1.0.0"></script>
<script src="<%= contextPath %>/js/landing.js?v=1.0.0"></script>

</body>
</html>