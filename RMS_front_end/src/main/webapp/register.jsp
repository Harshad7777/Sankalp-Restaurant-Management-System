<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sankalp Restaurant - Register</title>

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500;600;700&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <!-- Login CSS (Shared Theme) -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>

<body>

<!-- BACKGROUND -->
<div class="background">
    <div class="overlay"></div>
    <div class="circle circle-1"></div>
    <div class="circle circle-2"></div>
    <div class="circle circle-3"></div>
</div>

<!-- MAIN CONTAINER -->
<div class="page-container">
    <div class="login-wrapper">

        <!-- LEFT PANEL -->
        <div class="left-panel">
            <div class="left-content">
                <!-- Brand -->
                <div class="brand">
                    <div class="brand-logo">
                        <i class="fa-solid fa-utensils"></i>
                    </div>
                    <div class="brand-text">
                        <h2>SANKALP</h2>
                        <span>RESTAURANT</span>
                    </div>
                </div>

                <div class="gold-line"></div>

                <p class="small-heading">JOIN US</p>
                <h1>Create <span>Account</span></h1>
                <p class="description">
                    Register a new staff, chef, or manager account to start streamlining your restaurant operations.
                </p>

                <div class="features">
                    <div class="feature">
                        <div class="feature-icon"><i class="fa-solid fa-shield-halved"></i></div>
                        <div>
                            <strong>Role-Based Security</strong>
                            <small>Granular access control</small>
                        </div>
                    </div>
                    <div class="feature">
                        <div class="feature-icon"><i class="fa-solid fa-bolt"></i></div>
                        <div>
                            <strong>Instant Activation</strong>
                            <small>Ready to manage orders & tables</small>
                        </div>
                    </div>
                </div>

                <div class="left-bottom">
                    <i class="fa-solid fa-circle-check"></i>
                    Powerful • Simple • Secure
                </div>
            </div>
        </div>

        <!-- RIGHT PANEL -->
        <div class="right-panel">
            <div class="login-card">

                <div class="logo-container">
                    <img src="${pageContext.request.contextPath}/images/logo.png" alt="Sankalp Restaurant Logo">
                </div>

                <div class="login-heading">
                    <span>GET STARTED</span>
                    <h2>Register Account</h2>
                    <p>Enter your details to create a new profile</p>
                </div>

                <div id="message" class="message">
                    <i class="fa-solid fa-circle-exclamation"></i>
                    <span></span>
                </div>

                <!-- REGISTER FORM -->
                <form id="registerForm" autocomplete="off">

                    <!-- Full Name -->
                    <div class="form-group">
                        <label for="fullName">Full Name</label>
                        <div class="input-container">
                            <i class="fa-solid fa-id-card input-icon"></i>
                            <input type="text" id="fullName" placeholder="Enter full name" required>
                        </div>
                    </div>

                    <!-- Username -->
                    <div class="form-group">
                        <label for="username">Username</label>
                        <div class="input-container">
                            <i class="fa-regular fa-user input-icon"></i>
                            <input type="text" id="username" placeholder="Choose a username" required>
                        </div>
                    </div>

                    <!-- Email -->
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <div class="input-container">
                            <i class="fa-regular fa-envelope input-icon"></i>
                            <input type="email" id="email" placeholder="Enter email address" required>
                        </div>
                    </div>

                    <!-- Password -->
                    <div class="form-group">
                        <label for="password">Password</label>
                        <div class="input-container">
                            <i class="fa-solid fa-lock input-icon"></i>
                            <input type="password" id="password" placeholder="Create a password (min 6 chars)" required>
                        </div>
                    </div>

                    <!-- Role Selection -->
                    <div class="form-group">
                        <label for="role">Assign Role</label>
                        <div class="input-container">
                            <i class="fa-solid fa-user-gear input-icon"></i>
                            <select id="role" class="form-select" style="width: 100%; height: 50px; background: #161616; color: #fff; border: 1px solid #333; border-radius: 12px; padding: 0 16px 0 44px; font-size: 14px; outline: none;">
                                <option value="STAFF" style="background:#161616; color:#fff;">Staff (Waiter / Order Desk)</option>
                                <option value="CHEF" style="background:#161616; color:#fff;">Chef (Kitchen Dashboard)</option>
                                <option value="ADMIN" style="background:#161616; color:#fff;">Administrator (Full Access)</option>
                            </select>
                        </div>
                    </div>

                    <button type="submit" class="submit-btn" style="margin-top: 10px;">
                        <span>Register Now</span>
                        <i class="fa-solid fa-arrow-right"></i>
                    </button>

                </form>

                <div class="register-container">
                    <p>Already have an account? <a href="login.jsp">Sign In</a></p>
                </div>

            </div>
        </div>

    </div>
</div>

<script src="${pageContext.request.contextPath}/js/register.js"></script>

</body>
</html>