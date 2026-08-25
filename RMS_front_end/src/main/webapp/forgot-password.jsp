<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
    String backendBaseUrl = contextPath.endsWith("/RMS_backend") ? contextPath : "/RMS_backend";
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sankalp Restaurant — Account Password Recovery</title>

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

    <style>
        .otp-banner-box {
            background: rgba(217, 148, 0, 0.12);
            border: 1px solid rgba(217, 148, 0, 0.3);
            color: #fcd34d;
            padding: 12px 16px;
            border-radius: var(--radius-md);
            font-size: 0.88rem;
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 20px;
        }

        .success-box-hero {
            text-align: center;
            padding: 25px 10px;
        }

        .success-icon-large {
            font-size: 60px;
            color: #10b981;
            margin-bottom: 16px;
            animation: pulseSuccess 1.5s infinite alternate;
        }

        @keyframes pulseSuccess {
            from { transform: scale(1); filter: drop-shadow(0 0 10px rgba(16, 185, 129, 0.4)); }
            to { transform: scale(1.08); filter: drop-shadow(0 0 20px rgba(16, 185, 129, 0.7)); }
        }

        .btn-success-green {
            background: linear-gradient(135deg, #10b981 0%, #059669 100%) !important;
            box-shadow: 0 6px 22px rgba(16, 185, 129, 0.4) !important;
        }

        .btn-success-green:hover {
            box-shadow: 0 10px 28px rgba(16, 185, 129, 0.6) !important;
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
                        <img src="<%= contextPath %>/images/logo1.png" alt="Sankalp Logo" class="brand-emblem-img" id="forgotShowcaseLogo">
                    </div>
                    <div class="brand-titles">
                        <h2 class="brand-name" id="forgotBrandName">SANKALP</h2>
                        <span class="brand-sub" id="forgotBrandSub">RESTAURANT & CULINARY SUITE</span>
                    </div>
                </div>

                <div class="showcase-divider"></div>

                <!-- VALUE PROPOSITION -->
                <div class="showcase-hero">
                    <span class="hero-badge">
                        <i class="fa-solid fa-key"></i> Security Recovery Center
                    </span>
                    <h1>
                        Automated &amp; Secure <br>
                        <span class="gold-text">Account Recovery</span>
                    </h1>
                    <p class="hero-desc">
                        Instantly reset your account credentials using 6-Digit Email OTP verification backed by BCrypt encryption and automated security alerts.
                    </p>
                </div>

                <!-- FEATURE PILLARS -->
                <div class="feature-pillars">
                    <div class="pillar-item">
                        <div class="pillar-icon">
                            <i class="fa-solid fa-envelope-circle-check"></i>
                        </div>
                        <div class="pillar-text">
                            <strong>6-Digit Email OTP Verification</strong>
                            <small>Time-sensitive security passcode sent directly to your Gmail</small>
                        </div>
                    </div>

                    <div class="pillar-item">
                        <div class="pillar-icon">
                            <i class="fa-solid fa-shield-halved"></i>
                        </div>
                        <div class="pillar-text">
                            <strong>BCrypt Hashed Password Protection</strong>
                            <small>Enterprise salted encryption protecting all credentials</small>
                        </div>
                    </div>

                    <div class="pillar-item">
                        <div class="pillar-icon">
                            <i class="fa-solid fa-bell"></i>
                        </div>
                        <div class="pillar-text">
                            <strong>Automated Security Alerts</strong>
                            <small>Instant notification dispatches upon password update</small>
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
                        <img src="<%= contextPath %>/images/logo1.png" alt="Sankalp Logo" class="mobile-logo-img" id="forgotMobileLogo">
                    </div>
                    <span class="form-pretitle">STAFF &amp; ADMIN PORTAL</span>
                    <h2 class="form-title">Password Recovery</h2>
                    <p class="form-subtitle">Reset your security password using 6-Digit Email OTP</p>
                </div>

                <!-- ALERT MESSAGE BANNER -->
                <div id="pageAlertBox" class="alert-banner" style="display: none;">
                    <i class="fa-solid fa-circle-exclamation alert-icon" id="pageAlertIcon"></i>
                    <span class="alert-text" id="pageAlertText"></span>
                </div>

                <!-- STEP 1: REQUEST OTP FORM -->
                <form id="pageRequestOtpForm">
                    <div class="input-group">
                        <label for="pageForgotIdentity">Username or Registered Email <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-user-shield field-icon"></i>
                            <input type="text" id="pageForgotIdentity" name="identity" placeholder="e.g. admin or admin@sankalp.com" required autocomplete="username" autofocus>
                        </div>
                    </div>

                    <button type="submit" class="btn-submit-login mt-4" id="btnPageRequestOtp">
                        <span id="btnPageRequestOtpText">Send 6-Digit Reset OTP</span>
                        <i class="fa-solid fa-paper-plane btn-icon" id="btnPageRequestOtpIcon"></i>
                        <span class="btn-spinner" id="btnPageRequestOtpSpinner" style="display: none;">
                            <i class="fa-solid fa-circle-notch fa-spin"></i>
                        </span>
                    </button>

                    <div class="text-center mt-3">
                        <a href="<%= contextPath %>/login.jsp" style="color: #94a3b8; font-size: 13px; text-decoration: none;">
                            <i class="fa-solid fa-arrow-left me-1"></i> Return to Sign In Page
                        </a>
                    </div>
                </form>

                <!-- STEP 2: VERIFY OTP & RESET PASSWORD FORM -->
                <form id="pageResetPasswordForm" style="display: none;">
                    <div class="otp-banner-box">
                        <i class="fa-solid fa-envelope-circle-check"></i>
                        <span>OTP sent to <strong id="pageSentMaskedEmail">your email</strong> (Valid 10 mins)</span>
                    </div>

                    <div class="input-group">
                        <label for="pageOtpCode">6-Digit Verification OTP <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-shield-halved field-icon" style="color: #f59e0b;"></i>
                            <input type="text" id="pageOtpCode" name="otp" placeholder="• • • • • •" maxlength="6" required autocomplete="off" style="letter-spacing: 6px; font-weight: 700; font-size: 1.1rem; text-align: center;">
                        </div>
                    </div>

                    <div class="input-group">
                        <label for="pageNewPassword">New Security Password <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-lock field-icon"></i>
                            <input type="password" id="pageNewPassword" name="newPassword" placeholder="Enter new password" required autocomplete="new-password">
                        </div>
                    </div>

                    <div class="input-group">
                        <label for="pageConfirmNewPassword">Confirm New Password <span class="required-star">*</span></label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-lock field-icon"></i>
                            <input type="password" id="pageConfirmNewPassword" name="confirmPassword" placeholder="Re-enter new password" required autocomplete="new-password">
                        </div>
                    </div>

                    <button type="submit" class="btn-submit-login btn-success-green mt-4" id="btnPageResetPass">
                        <span id="btnPageResetPassText">Reset Password &amp; Save</span>
                        <i class="fa-solid fa-circle-check btn-icon" id="btnPageResetPassIcon"></i>
                        <span class="btn-spinner" id="btnPageResetPassSpinner" style="display: none;">
                            <i class="fa-solid fa-circle-notch fa-spin"></i>
                        </span>
                    </button>

                    <div class="text-center mt-3">
                        <a href="javascript:void(0)" id="btnPageBackToStep1" style="color: #94a3b8; font-size: 13px; text-decoration: none;">
                            <i class="fa-solid fa-arrow-left me-1"></i> Change Username/Email
                        </a>
                    </div>
                </form>

                <!-- STEP 3: SUCCESS SCREEN WITH REDIRECT BUTTON -->
                <div id="pageSuccessScreen" style="display: none;">
                    <div class="success-box-hero">
                        <i class="fa-solid fa-circle-check success-icon-large"></i>
                        <h3 style="color: #ffffff; font-weight: 700; margin-bottom: 8px;">Password Updated!</h3>
                        <p style="color: #94a3b8; font-size: 0.9rem; margin-bottom: 25px;">Your security password has been reset successfully. Click below to sign in to your dashboard.</p>
                        <a href="<%= contextPath %>/login.jsp" class="btn-submit-login btn-success-green" style="text-decoration: none;">
                            <span>Go to Sign In / Login Page</span>
                            <i class="fa-solid fa-right-to-bracket btn-icon"></i>
                        </a>
                    </div>
                </div>

                <!-- FOOTER -->
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

<script>
    const BACKEND_BASE_URL = window.location.origin.includes(":8081") 
        ? "http://localhost:8080/RMS_backend" 
        : (window.location.origin.endsWith("/RMS_backend") ? window.location.origin : (window.location.origin + "/RMS_backend"));

    document.addEventListener("DOMContentLoaded", function () {
        initBrandBranding();

        const reqForm = document.getElementById("pageRequestOtpForm");
        const resetForm = document.getElementById("pageResetPasswordForm");
        const backBtn = document.getElementById("btnPageBackToStep1");

        // Step 1 Submit
        reqForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            const identity = document.getElementById("pageForgotIdentity").value.trim();
            if (!identity) return;

            setPageOtpBtnLoading(true);
            hidePageAlert();

            try {
                const res = await fetch(BACKEND_BASE_URL + "/api/auth/forgot-password/request-otp", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ identity: identity })
                });
                let data = {};
                try { data = await res.json(); } catch(e) {}

                if (data.success || (res.ok && data.success !== false)) {
                    document.getElementById("pageSentMaskedEmail").textContent = data.maskedEmail || "your email";
                    const otpInput = document.getElementById("pageOtpCode");
                    if (otpInput) {
                        otpInput.value = "";
                        setTimeout(() => otpInput.focus(), 100);
                    }
                    reqForm.style.display = "none";
                    resetForm.style.display = "block";
                    showPageAlert(data.message || "6-Digit OTP sent successfully!", "success");
                } else {
                    showPageAlert(data.message || "Unable to send OTP. Please check your username or registered email address.", "error");
                }
            } catch (err) {
                console.error("Fetch Error:", err);
                showPageAlert("Network connection error. Please verify backend is running.", "error");
            } finally {
                setPageOtpBtnLoading(false);
            }
        });

        // Step 2 Submit
        resetForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            const identity = document.getElementById("pageForgotIdentity").value.trim();
            const otp = document.getElementById("pageOtpCode").value.trim();
            const newPass = document.getElementById("pageNewPassword").value;
            const confirmPass = document.getElementById("pageConfirmNewPassword").value;

            if (newPass !== confirmPass) {
                showPageAlert("New Password and Confirm Password do not match.", "error");
                return;
            }

            setPageResetBtnLoading(true);
            hidePageAlert();

            try {
                const res = await fetch(BACKEND_BASE_URL + "/api/auth/forgot-password/reset-password", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ identity: identity, otp: otp, newPassword: newPass })
                });
                const data = await res.json();

                if (res.ok && data.success) {
                    resetForm.style.display = "none";
                    document.getElementById("pageSuccessScreen").style.display = "block";
                } else {
                    showPageAlert(data.message || "Failed to reset password.", "error");
                }
            } catch (err) {
                showPageAlert("Unable to connect to server.", "error");
            } finally {
                setPageResetBtnLoading(false);
            }
        });

        backBtn.addEventListener("click", function () {
            resetForm.style.display = "none";
            reqForm.style.display = "block";
            hidePageAlert();
        });
    });

    async function initBrandBranding() {
        try {
            const stored = localStorage.getItem("sankalp_restaurant_settings");
            if (stored) applyBranding(JSON.parse(stored));
            const res = await fetch(BACKEND_BASE_URL + "/api/settings");
            if (res.ok) {
                const data = await res.json();
                if (data && typeof data === "object") applyBranding(data);
            }
        } catch (e) {}
    }

    function applyBranding(settings) {
        if (!settings) return;
        const logoPath = settings.logoPath;
        if (logoPath) {
            const contextPrefix = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1)) || "";
            let fullSrc = logoPath;
            if (!logoPath.startsWith("http") && !logoPath.startsWith("data:")) fullSrc = contextPrefix + fullSrc;
            const sc = document.getElementById("forgotShowcaseLogo");
            const mb = document.getElementById("forgotMobileLogo");
            if (sc) sc.src = fullSrc;
            if (mb) mb.src = fullSrc;
        }
        if (settings.restaurantName) {
            const bn = document.getElementById("forgotBrandName");
            if (bn) bn.textContent = settings.restaurantName.toUpperCase();
        }
        if (settings.tagline) {
            const bs = document.getElementById("forgotBrandSub");
            if (bs) bs.textContent = settings.tagline.toUpperCase();
        }
    }

    function showPageAlert(msg, type) {
        const box = document.getElementById("pageAlertBox");
        const text = document.getElementById("pageAlertText");
        const icon = document.getElementById("pageAlertIcon");

        if (!box || !text) return;
        box.className = "alert-banner " + type;
        text.textContent = msg;
        box.style.display = "flex";

        if (icon) {
            if (type === "success") {
                icon.className = "fa-solid fa-circle-check alert-icon";
                icon.style.color = "#10b981";
            } else {
                icon.className = "fa-solid fa-circle-exclamation alert-icon";
                icon.style.color = "#ef4444";
            }
        }
    }

    function hidePageAlert() {
        const box = document.getElementById("pageAlertBox");
        if (box) box.style.display = "none";
    }

    function setPageOtpBtnLoading(isLoading) {
        const btn = document.getElementById("btnPageRequestOtp");
        const txt = document.getElementById("btnPageRequestOtpText");
        const icon = document.getElementById("btnPageRequestOtpIcon");
        const spin = document.getElementById("btnPageRequestOtpSpinner");
        if (btn) btn.disabled = isLoading;
        if (txt) txt.style.display = isLoading ? "none" : "inline";
        if (icon) icon.style.display = isLoading ? "none" : "inline-block";
        if (spin) spin.style.display = isLoading ? "inline-block" : "none";
    }

    function setPageResetBtnLoading(isLoading) {
        const btn = document.getElementById("btnPageResetPass");
        const txt = document.getElementById("btnPageResetPassText");
        const icon = document.getElementById("btnPageResetPassIcon");
        const spin = document.getElementById("btnPageResetPassSpinner");
        if (btn) btn.disabled = isLoading;
        if (txt) txt.style.display = isLoading ? "none" : "inline";
        if (icon) icon.style.display = isLoading ? "none" : "inline-block";
        if (spin) spin.style.display = isLoading ? "inline-block" : "none";
    }
</script>

</body>
</html>
