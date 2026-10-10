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
    <title>Sankalp Restaurant — Enterprise Account Registration</title>

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,500;0,600;0,700;1,600&family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <!-- Dynamic Favicon -->
    <link rel="icon" type="image/png" href="<%= contextPath %>/images/logo1.png" id="dynamicFavicon">

    <!-- Login & Form CSS (Shared Theme) -->
    <link rel="stylesheet" href="<%= contextPath %>/css/login.css">
    <style>
        .login-modal-card {
            max-width: 1050px;
        }
        .form-panel-inner {
            max-height: 90vh;
            overflow-y: auto;
            padding-right: 8px;
        }
        .form-select-custom {
            width: 100%;
            height: 48px;
            background-color: #0b1120;
            color: #f8fafc;
            border: 1px solid rgba(255, 255, 255, 0.12);
            border-radius: 12px;
            padding: 0 16px 0 46px;
            font-size: 14px;
            outline: none;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .form-select-custom:focus {
            border-color: #d99400;
            box-shadow: 0 0 0 3px rgba(217, 148, 0, 0.2);
        }
    </style>
</head>

<body>

<!-- CINEMATIC BACKGROUND -->
<div class="login-bg-container">
    <div class="login-bg-image" style="background-image: url('<%= contextPath %>/images/login-bg.jpg');"></div>
    <div class="login-bg-overlay"></div>
    <div class="ambient-glow glow-1"></div>
    <div class="ambient-glow glow-2"></div>
</div>

<!-- PAGE WRAPPER -->
<div class="login-page-wrapper">

    <div class="login-modal-card">

        <!-- =====================================================
             LEFT SHOWCASE PANEL
        ====================================================== -->
        <div class="showcase-panel">
            <div class="showcase-content">
                
                <!-- BRAND EMBLEM -->
                <div class="showcase-brand">
                    <div class="brand-emblem-wrap">
                        <img src="<%= contextPath %>/images/logo1.png" alt="Sankalp Logo" class="brand-emblem-img" id="loginShowcaseLogo">
                    </div>
                    <div class="brand-titles">
                        <h2 class="brand-name" id="loginBrandName">SANKALP</h2>
                        <span class="brand-sub" id="loginBrandSub">RESTAURANT & CULINARY SUITE</span>
                    </div>
                </div>

                <div class="showcase-divider"></div>

                <!-- VALUE PROPOSITION -->
                <div class="showcase-hero">
                    <span class="hero-badge">
                        <i class="fa-solid fa-crown"></i> Enterprise Onboarding
                    </span>
                    <h1>
                        Register New <br>
                        <span class="gold-text">Admin & Restaurant</span>
                    </h1>
                    <p class="hero-desc">
                        Register a new Administrator account and initialize an isolated restaurant tenant, or join an existing dining branch instantly.
                    </p>
                </div>

                <!-- FEATURE PILLARS -->
                <div class="feature-pillars">
                    <div class="pillar-item">
                        <div class="pillar-icon">
                            <i class="fa-solid fa-building-circle-check"></i>
                        </div>
                        <div class="pillar-text">
                            <strong>Multi-Tenant Isolation</strong>
                            <small>Dedicated data context & tenant reporting</small>
                        </div>
                    </div>

                    <div class="pillar-item">
                        <div class="pillar-icon">
                            <i class="fa-solid fa-user-gear"></i>
                        </div>
                        <div class="pillar-text">
                            <strong>Role-Based Access</strong>
                            <small>Admin, Chef & Staff workspace delegation</small>
                        </div>
                    </div>

                    <div class="pillar-item">
                        <div class="pillar-icon">
                            <i class="fa-solid fa-shield-halved"></i>
                        </div>
                        <div class="pillar-text">
                            <strong>Instant Activation</strong>
                            <small>Pre-configured menu, tables & billing engine</small>
                        </div>
                    </div>
                </div>

                <!-- FOOTER BADGE -->
                <div class="showcase-footer">
                    <div class="status-indicator">
                        <span class="pulse-dot"></span>
                        <span>System Status: Fully Operational</span>
                    </div>
                    <span class="version-tag">v2.4 Enterprise</span>
                </div>

            </div>
        </div>

        <!-- =====================================================
             RIGHT FORM PANEL
        ====================================================== -->
        <div class="form-panel">
            <div class="form-panel-inner">

                <!-- TOP LOGO & GREETING -->
                <div class="form-header">
                    <div class="mobile-logo-wrap">
                        <img src="<%= contextPath %>/images/logo1.png" alt="Sankalp Logo" class="mobile-logo-img" id="loginMobileLogo">
                    </div>
                    <span class="form-pretitle">ACCOUNT REGISTRATION</span>
                    <h2 class="form-title">Create Profile</h2>
                    <p class="form-subtitle">Enter your details to initialize your account and workspace</p>
                </div>

                <!-- ALERT MESSAGE BANNER -->
                <div id="messageBox" class="alert-banner" style="display: none;">
                    <i class="fa-solid fa-circle-exclamation alert-icon" id="messageIcon"></i>
                    <span class="alert-text" id="messageText"></span>
                </div>

                <!-- REGISTER FORM -->
                <form id="registerForm" autocomplete="off" novalidate>
                    
                    <!-- FULL NAME -->
                    <div class="input-group">
                        <label for="fullName">Full Name <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-id-card field-icon"></i>
                            <input type="text" id="fullName" name="fullName" placeholder="Enter full name" required autofocus>
                        </div>
                    </div>

                    <!-- USERNAME -->
                    <div class="input-group">
                        <label for="username">Username <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-user-shield field-icon"></i>
                            <input type="text" id="username" name="username" placeholder="Choose a username" required>
                        </div>
                    </div>

                    <!-- EMAIL -->
                    <div class="input-group">
                        <label for="email">Email Address <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-envelope field-icon"></i>
                            <input type="email" id="email" name="email" placeholder="Enter email address" required>
                        </div>
                    </div>

                    <!-- PASSWORD -->
                    <div class="input-group">
                        <label for="password">Password <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-lock-keyhole field-icon"></i>
                            <input type="password" id="password" name="password" placeholder="Create password (min 6 chars)" required>
                        </div>
                    </div>

                    <!-- ASSIGN ROLE -->
                    <div class="input-group">
                        <label for="role">Assign Role <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-user-gear field-icon"></i>
                            <select id="role" name="role" class="form-select-custom">
                                <option value="ADMIN" selected>Administrator (Full Access)</option>
                                <option value="STAFF">Staff (Order Desk / Waiter)</option>
                                <option value="CHEF">Chef (Kitchen Display)</option>
                            </select>
                        </div>
                    </div>

                    <!-- RESTAURANT SETUP MODE -->
                    <div class="input-group" id="restaurantModeGroup">
                        <label for="restaurantMode">Restaurant Setup <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-building-user field-icon"></i>
                            <select id="restaurantMode" name="restaurantMode" class="form-select-custom">
                                <option value="NEW">Create New Restaurant</option>
                                <option value="EXISTING">Join Existing Restaurant</option>
                            </select>
                        </div>
                    </div>

                    <!-- NEW RESTAURANT NAME -->
                    <div class="input-group" id="newRestaurantNameGroup">
                        <label for="restaurantName">New Restaurant Name <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-store field-icon"></i>
                            <input type="text" id="restaurantName" name="restaurantName" placeholder="e.g. Royal Fine Dining">
                        </div>
                    </div>

                    <!-- EXISTING RESTAURANT SELECTION -->
                    <div class="input-group" id="existingRestaurantGroup" style="display: none;">
                        <label for="restaurantId">Select Restaurant <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-hotel field-icon"></i>
                            <select id="restaurantId" name="restaurantId" class="form-select-custom">
                                <option value="1">Sankalp Restaurant</option>
                            </select>
                        </div>
                    </div>

                    <!-- SUBMIT BUTTON -->
                    <button type="submit" class="btn-submit-login" id="registerButton" style="margin-top: 20px;">
                        <span id="btnText">Register Account</span>
                        <i class="fa-solid fa-arrow-right-long btn-icon" id="btnIcon"></i>
                    </button>

                    <div style="text-align: center; margin-top: 15px;">
                        <span style="color: #aaa; font-size: 0.9rem;">Already have an account? </span>
                        <a href="login.jsp" style="color: #d4af37; font-weight: 600; text-decoration: none;">Sign In Here</a>
                    </div>

                </form>

                <!-- SECURITY FOOTER -->
                <div class="form-panel-footer">
                    <div class="security-badge">
                        <i class="fa-solid fa-lock"></i>
                        <span>256-Bit SSL Encrypted Session</span>
                    </div>
                    <p class="copyright-text">
                        &copy; 2026 Sankalp Restaurant Management System. All Rights Reserved.<br>
                        <span style="font-size: 0.8rem; opacity: 0.85;">Developed by <strong>Harshad Rakshe</strong></span>
                    </p>
                </div>

            </div>
        </div>

    </div>

</div>

<!-- JAVASCRIPT -->
<script src="<%= contextPath %>/js/config.js"></script>
<script src="<%= contextPath %>/js/register.js"></script>

</body>
</html>