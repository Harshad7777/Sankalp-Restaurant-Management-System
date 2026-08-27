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
    <title>Sankalp Restaurant — Enterprise Portal Login</title>

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,500;0,600;0,700;1,600&family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <!-- Dynamic Favicon -->
    <link rel="icon" type="image/png" href="<%= contextPath %>/images/logo1.png" id="dynamicFavicon">

    <!-- Login CSS -->
    <link rel="stylesheet" href="<%= contextPath %>/css/login.css">
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
                        <i class="fa-solid fa-crown"></i> Enterprise RMS
                    </span>
                    <h1>
                        Next-Generation <br>
                        <span class="gold-text">Restaurant Intelligence</span>
                    </h1>
                    <p class="hero-desc">
                        Seamlessly synchronize dining floor orders, live kitchen display systems, GST-compliant billing, and real-time operational analytics.
                    </p>
                </div>

                <!-- FEATURE PILLARS -->
                <div class="feature-pillars">
                    <div class="pillar-item">
                        <div class="pillar-icon">
                            <i class="fa-solid fa-fire-burner"></i>
                        </div>
                        <div class="pillar-text">
                            <strong>Live Kitchen Orchestration</strong>
                            <small>Instant KOT routing & chef ticket dispatch</small>
                        </div>
                    </div>

                    <div class="pillar-item">
                        <div class="pillar-icon">
                            <i class="fa-solid fa-receipt"></i>
                        </div>
                        <div class="pillar-text">
                            <strong>Automated Invoicing & GST</strong>
                            <small>Split billing, discounts & automated customer emails</small>
                        </div>
                    </div>

                    <div class="pillar-item">
                        <div class="pillar-icon">
                            <i class="fa-solid fa-shield-halved"></i>
                        </div>
                        <div class="pillar-text">
                            <strong>Bank-Grade Role Security</strong>
                            <small>BCrypt encryption & encrypted database channels</small>
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
                    <span class="form-pretitle">STAFF & ADMIN PORTAL</span>
                    <h2 class="form-title">Account Sign In</h2>
                    <p class="form-subtitle">Enter your credentials to access your designated workspace</p>
                </div>

                <!-- QUICK DEMO LOGINS -->
                <div class="demo-logins-box">
                    <span class="demo-label"><i class="fa-solid fa-wand-magic-sparkles"></i> Quick Fill Roles:</span>
                    <div class="demo-buttons">
                        <button type="button" class="btn-demo admin" onclick="fillCredentials('admin', 'admin123', 'ADMIN')">
                            <i class="fa-solid fa-shield-halved"></i> Admin
                        </button>
                        <button type="button" class="btn-demo chef" onclick="fillCredentials('chef', 'chef123', 'CHEF')">
                            <i class="fa-solid fa-hat-chef"></i> Chef
                        </button>
                        <button type="button" class="btn-demo staff" onclick="fillCredentials('staff', 'staff123', 'STAFF')">
                            <i class="fa-solid fa-user-tie"></i> Staff
                        </button>
                    </div>
                </div>

                <!-- ALERT MESSAGE BANNER -->
                <div id="messageBox" class="alert-banner" style="display: none;">
                    <i class="fa-solid fa-circle-exclamation alert-icon" id="messageIcon"></i>
                    <span class="alert-text" id="messageText"></span>
                </div>

                <!-- LOGIN FORM -->
                <form id="loginForm" autocomplete="off" novalidate>
                    
                    <!-- USERNAME INPUT -->
                    <div class="input-group">
                        <label for="username">Username or System ID <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-user-shield field-icon"></i>
                            <input type="text" id="username" name="username" placeholder="e.g. admin, chef, or staff" required autocomplete="username" autofocus>
                        </div>
                    </div>

                    <!-- PASSWORD INPUT -->
                    <div class="input-group">
                        <div class="label-row">
                            <label for="password">Security Password <span class="required-star">*</span></label>
                            <a href="<%= contextPath %>/forgot-password.jsp" target="_blank" class="link-forgot" id="forgotPasswordLink" title="Open Password Recovery in New Tab">Forgot?</a>
                        </div>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-lock-keyhole field-icon"></i>
                            <input type="password" id="password" name="password" placeholder="Enter your password" required autocomplete="current-password">
                            <button type="button" class="btn-toggle-eye" id="togglePasswordBtn" title="Toggle password visibility">
                                <i class="fa-regular fa-eye"></i>
                            </button>
                        </div>
                        <div id="capsLockWarning" class="caps-warning" style="display: none;">
                            <i class="fa-solid fa-arrow-up-from-bracket"></i> Caps Lock is ON
                        </div>
                    </div>

                    <!-- REMEMBER ME & SECURITY NOTE -->
                    <div class="form-row-options">
                        <label class="custom-checkbox-container">
                            <input type="checkbox" id="rememberMe">
                            <span class="checkmark"></span>
                            <span class="checkbox-label">Keep me signed in</span>
                        </label>
                    </div>

                    <!-- SUBMIT BUTTON -->
                    <button type="submit" class="btn-submit-login" id="loginButton">
                        <span id="btnText">Sign In to Dashboard</span>
                        <i class="fa-solid fa-arrow-right-long btn-icon" id="btnIcon"></i>
                        <span class="btn-spinner" id="btnSpinner" style="display: none;">
                            <i class="fa-solid fa-circle-notch fa-spin"></i>
                        </span>
                    </button>

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
<script src="<%= contextPath %>/js/login.js"></script>

</body>
</html>