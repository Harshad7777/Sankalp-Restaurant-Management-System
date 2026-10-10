/**
 * =========================================================
 * SANKALP RESTAURANT MANAGEMENT SYSTEM
 * MODERN ENTERPRISE LOGIN JS
 * Authentication, demo filling, caps lock warning, and redirects
 * =========================================================
 */

"use strict";

// Universal App UI Toast Notification System
window.showToast = function (title, message = "", type = "success") {
    if (!message && title) {
        message = title;
        title = type === "error" ? "Error" : (type === "warning" ? "Warning" : "Notice");
    }

    let toastContainer = document.getElementById("globalToast");
    if (!toastContainer) {
        toastContainer = document.createElement("div");
        toastContainer.id = "globalToast";
        toastContainer.className = "global-toast-container";
        toastContainer.innerHTML = `
            <div class="global-toast-card" id="globalToastCard">
                <i class="fa-solid fa-circle-check toast-icon" id="globalToastIcon"></i>
                <div class="toast-content">
                    <strong id="globalToastTitle">Notification</strong>
                    <span id="globalToastMessage">Operation successful</span>
                </div>
            </div>
        `;
        document.body.appendChild(toastContainer);
    }

    const card = document.getElementById("globalToastCard");
    const icon = document.getElementById("globalToastIcon");
    const titleEl = document.getElementById("globalToastTitle");
    const msgEl = document.getElementById("globalToastMessage");

    if (!card) return;

    card.className = "global-toast-card";
    if (type === "success") {
        card.classList.add("toast-success");
        if (icon) icon.className = "fa-solid fa-circle-check toast-icon";
    } else if (type === "error") {
        card.classList.add("toast-error");
        if (icon) icon.className = "fa-solid fa-circle-exclamation toast-icon";
    } else if (type === "warning") {
        card.classList.add("toast-warning");
        if (icon) icon.className = "fa-solid fa-triangle-exclamation toast-icon";
    } else {
        card.classList.add("toast-info");
        if (icon) icon.className = "fa-solid fa-circle-info toast-icon";
    }

    if (titleEl) titleEl.textContent = title || "Notification";
    if (msgEl) {
        msgEl.textContent = message || "";
        msgEl.style.display = message ? "block" : "none";
    }

    card.classList.add("show");

    if (window._toastTimeout) clearTimeout(window._toastTimeout);
    window._toastTimeout = setTimeout(() => {
        card.classList.remove("show");
    }, 4000);
};

// Safe override of window.alert to prevent ugly browser popups
window.alert = function (message) {
    console.warn("Intercepted browser alert:", message);
    const msgStr = String(message || "");
    const isError = /error|fail|invalid|cannot|unable|denied|wrong/i.test(msgStr);
    const isSuccess = /success|created|saved|updated|done|completed/i.test(msgStr);
    const type = isError ? "error" : (isSuccess ? "success" : "info");
    const title = isError ? "Alert" : (isSuccess ? "Success" : "Information");
    window.showToast(title, msgStr, type);
};

const BACKEND_BASE_URL = (typeof window.API_BASE_URL !== "undefined" && window.API_BASE_URL)
    ? window.API_BASE_URL
    : (window.location.origin.includes(":8081") 
        ? "http://localhost:8080/RMS_backend" 
        : (window.location.origin.endsWith("/RMS_backend") ? window.location.origin : (window.location.origin + "/RMS_backend")));

function getLoginApiUrl() {
    if (typeof window.getApiUrl === "function") {
        return window.getApiUrl("/auth/login");
    }
    return (window.API_BASE_URL || "/api") + "/auth/login";
}

const LOGIN_API_URL = "/api/auth/login";

document.addEventListener("DOMContentLoaded", function () {
    initLoginForm();
    initPasswordEyeToggle();
    initCapsLockWarning();
    initForgotPassword();
    initBrandIdentity();
});

// =========================================================
// BRAND IDENTITY & LOGO LOADER
// =========================================================

async function initBrandIdentity() {
    // 1. Try reading from cached settings
    try {
        const stored = localStorage.getItem("sankalp_restaurant_settings");
        if (stored) {
            const settings = JSON.parse(stored);
            applyLoginBranding(settings);
        }
    } catch (e) {
        console.warn("Cached settings read error:", e);
    }

    // 2. Fetch fresh settings from backend API
    try {
        const settingsUrl = (typeof window.getApiUrl === "function") ? window.getApiUrl("/settings") : "/api/settings";
        const res = await fetch(settingsUrl);
        if (res.ok) {
            const data = await res.json();
            if (data && typeof data === "object") {
                localStorage.setItem("sankalp_restaurant_settings", JSON.stringify(data));
                applyLoginBranding(data);
            }
        }
    } catch (err) {
        // Backend offline or unreachable, local cached branding is used
    }
}

function applyLoginBranding(settings) {
    if (!settings) return;

    const showcaseLogo = document.getElementById("loginShowcaseLogo");
    const mobileLogo = document.getElementById("loginMobileLogo");
    const brandName = document.getElementById("loginBrandName");
    const brandSub = document.getElementById("loginBrandSub");

    const logoPath = settings.logoPath;
    if (logoPath) {
        let fullSrc = logoPath.trim();
        if (!fullSrc.startsWith("http") && !fullSrc.startsWith("data:")) {
            if (fullSrc.startsWith("uploads/")) fullSrc = "/api/" + fullSrc;
            else if (fullSrc.startsWith("/uploads/")) fullSrc = "/api" + fullSrc;
            else if (!fullSrc.startsWith("/")) fullSrc = "/" + fullSrc;
        }

        if (showcaseLogo) showcaseLogo.src = fullSrc;
        if (mobileLogo) mobileLogo.src = fullSrc;

        const favicon = document.getElementById("dynamicFavicon");
        if (favicon) {
            favicon.href = fullSrc;
        }
    }

    if (settings.restaurantName && brandName) {
        brandName.textContent = settings.restaurantName.toUpperCase();
    }
    if (settings.tagline && brandSub) {
        brandSub.textContent = settings.tagline.toUpperCase();
    }
}

// =========================================================
// QUICK ROLE DEMO FILL
// =========================================================

function fillCredentials(username, password, role) {
    const userField = document.getElementById("username");
    const passField = document.getElementById("password");

    if (userField) userField.value = username;
    if (passField) passField.value = password;

    hideMessage();

    // Visual button bounce
    const buttons = document.querySelectorAll(".btn-demo");
    buttons.forEach(btn => btn.style.transform = "");
    
    const activeBtn = document.querySelector(`.btn-demo.${role.toLowerCase()}`);
    if (activeBtn) {
        activeBtn.style.transform = "scale(0.96)";
        setTimeout(() => { activeBtn.style.transform = ""; }, 150);
    }
}

// =========================================================
// PASSWORD TOGGLE
// =========================================================

function initPasswordEyeToggle() {
    const toggleBtn = document.getElementById("togglePasswordBtn");
    const passInput = document.getElementById("password");

    if (toggleBtn && passInput) {
        toggleBtn.addEventListener("click", function () {
            const icon = toggleBtn.querySelector("i");
            if (passInput.type === "password") {
                passInput.type = "text";
                if (icon) {
                    icon.classList.remove("fa-eye");
                    icon.classList.add("fa-eye-slash");
                }
            } else {
                passInput.type = "password";
                if (icon) {
                    icon.classList.remove("fa-eye-slash");
                    icon.classList.add("fa-eye");
                }
            }
        });
    }
}

// =========================================================
// CAPS LOCK WARNING
// =========================================================

function initCapsLockWarning() {
    const passInput = document.getElementById("password");
    const capsWarning = document.getElementById("capsLockWarning");

    if (passInput && capsWarning) {
        ["keydown", "keyup"].forEach(eventType => {
            passInput.addEventListener(eventType, function (e) {
                if (e.getModifierState && e.getModifierState("CapsLock")) {
                    capsWarning.style.display = "flex";
                } else {
                    capsWarning.style.display = "none";
                }
            });
        });
    }
}

// =========================================================
// FORGOT PASSWORD MODAL & OTP FLOW
// =========================================================

function initForgotPassword() {
    const link = document.getElementById("forgotPasswordLink");
    const modalEl = document.getElementById("forgotPasswordModal");
    if (!link) return;

    if (!modalEl) {
        // Standard navigation to forgot-password.jsp
        return;
    }

    const closeBtn = document.getElementById("closeForgotModalBtn");
    link.addEventListener("click", function (e) {
        e.preventDefault();
        
        // Auto fill username from login form if present
        const currentUsername = document.getElementById("username")?.value?.trim() || "";
        const forgotInput = document.getElementById("forgotIdentity");
        if (forgotInput && currentUsername) {
            forgotInput.value = currentUsername;
        }

        // Reset forms to step 1
        showStep1();
        hideModalAlert();

        modalEl.style.display = "flex";
    });

    if (closeBtn) {
        closeBtn.addEventListener("click", function () {
            modalEl.style.display = "none";
        });
    }

    modalEl.addEventListener("click", function (e) {
        if (e.target === modalEl) {
            modalEl.style.display = "none";
        }
    });

    // Step 1 Form Submit (Request OTP)
    const requestForm = document.getElementById("requestOtpForm");
    if (requestForm) {
        requestForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            const identity = document.getElementById("forgotIdentity")?.value?.trim();
            if (!identity) {
                showModalAlert("Please enter your username or email address.", "warning");
                return;
            }

            setOtpBtnLoading(true);
            hideModalAlert();

            try {
                const res = await fetch("/api/auth/forgot-password/request-otp", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ identity: identity })
                });
                const data = await res.json();

                if (res.ok && data.success) {
                    const maskedEmail = data.maskedEmail || "your registered email";
                    const sentEmailSpan = document.getElementById("sentMaskedEmail");
                    if (sentEmailSpan) sentEmailSpan.textContent = maskedEmail;

                    if (data.otp) {
                        const otpInput = document.getElementById("otpCode");
                        if (otpInput) otpInput.value = data.otp;
                    }

                    showStep2();
                    showModalAlert(data.message || "6-Digit OTP sent successfully!", "success");
                } else {
                    showModalAlert(data.message || "Failed to send OTP. Please check your identity.", "danger");
                }
            } catch (err) {
                console.error("OTP Request Error:", err);
                showModalAlert("Unable to connect to server. Please ensure backend is running.", "danger");
            } finally {
                setOtpBtnLoading(false);
            }
        });
    }

    // Step 2 Form Submit (Verify OTP & Reset Password)
    const resetForm = document.getElementById("resetPasswordForm");
    if (resetForm) {
        resetForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            const identity = document.getElementById("forgotIdentity")?.value?.trim();
            const otp = document.getElementById("otpCode")?.value?.trim();
            const newPassword = document.getElementById("newPassword")?.value;
            const confirmPassword = document.getElementById("confirmNewPassword")?.value;

            if (!otp || otp.length !== 6) {
                showModalAlert("Please enter the 6-Digit OTP code sent to your email.", "warning");
                return;
            }
            if (!newPassword) {
                showModalAlert("Please enter your new password.", "warning");
                return;
            }
            if (newPassword !== confirmPassword) {
                showModalAlert("New Password and Confirm Password do not match.", "warning");
                return;
            }

            setResetBtnLoading(true);
            hideModalAlert();

            try {
                const res = await fetch("/api/auth/forgot-password/reset-password", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        identity: identity,
                        otp: otp,
                        newPassword: newPassword
                    })
                });
                const data = await res.json();

                if (res.ok && data.success) {
                    showModalAlert(data.message || "Password reset successfully! Redirecting...", "success");
                    
                    // Auto fill login form with new password & username
                    const userField = document.getElementById("username");
                    const passField = document.getElementById("password");
                    if (userField) userField.value = identity;
                    if (passField) passField.value = newPassword;

                    setTimeout(() => {
                        if (bsModal) bsModal.hide();
                        showMessage("Password reset successfully! Click 'Sign In' to enter dashboard.", "success");
                    }, 2000);
                } else {
                    showModalAlert(data.message || "Failed to reset password. Check your OTP.", "danger");
                }
            } catch (err) {
                console.error("Reset Password Error:", err);
                showModalAlert("Unable to connect to server. Please try again.", "danger");
            } finally {
                setResetBtnLoading(false);
            }
        });
    }

    // Back to Step 1 Button
    const btnBack = document.getElementById("btnBackToStep1");
    if (btnBack) {
        btnBack.addEventListener("click", function () {
            showStep1();
            hideModalAlert();
        });
    }
}

function showStep1() {
    const step1 = document.getElementById("requestOtpForm");
    const step2 = document.getElementById("resetPasswordForm");
    if (step1) step1.style.display = "block";
    if (step2) step2.style.display = "none";
}

function showStep2() {
    const step1 = document.getElementById("requestOtpForm");
    const step2 = document.getElementById("resetPasswordForm");
    if (step1) step1.style.display = "none";
    if (step2) step2.style.display = "block";
}

function showModalAlert(msg, type = "info") {
    const box = document.getElementById("modalAlertBox");
    const text = document.getElementById("modalAlertText");

    if (!box || !text) return;
    box.className = `alert alert-${type} border-0 shadow-sm text-center mb-3`;
    text.textContent = msg;
    box.style.display = "block";
}

function hideModalAlert() {
    const box = document.getElementById("modalAlertBox");
    if (box) box.style.display = "none";
}

function setOtpBtnLoading(isLoading) {
    const txt = document.getElementById("btnRequestOtpText");
    const spin = document.getElementById("btnRequestOtpSpinner");
    const btn = document.getElementById("btnRequestOtp");
    if (btn) btn.disabled = isLoading;
    if (txt) txt.style.display = isLoading ? "none" : "inline-block";
    if (spin) spin.style.display = isLoading ? "inline-block" : "none";
}

function setResetBtnLoading(isLoading) {
    const txt = document.getElementById("btnResetPassText");
    const spin = document.getElementById("btnResetPassSpinner");
    const btn = document.getElementById("btnResetPass");
    if (btn) btn.disabled = isLoading;
    if (txt) txt.style.display = isLoading ? "none" : "inline-block";
    if (spin) spin.style.display = isLoading ? "inline-block" : "none";
}

// =========================================================
// LOGIN FORM SUBMISSION
// =========================================================

function initLoginForm() {
    const form = document.getElementById("loginForm");
    if (!form) return;

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const usernameInput = document.getElementById("username");
        const passwordInput = document.getElementById("password");
        const btn = document.getElementById("loginButton");
        const btnText = document.getElementById("btnText");
        const btnIcon = document.getElementById("btnIcon");
        const btnSpinner = document.getElementById("btnSpinner");

        const username = usernameInput ? usernameInput.value.trim() : "";
        const password = passwordInput ? passwordInput.value : "";

        if (!username || !password) {
            showMessage("Please enter both username and password.", "error");
            if (!username && usernameInput) usernameInput.focus();
            else if (!password && passwordInput) passwordInput.focus();
            return;
        }

        hideMessage();

        // UI Loading State
        setLoading(true);

        try {
            const response = await fetch(getLoginApiUrl(), {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });

            const responseText = await response.text();
            let responseData = {};

            try {
                responseData = JSON.parse(responseText);
            } catch (err) {
                // If not JSON, use raw text
            }

            if (!response.ok) {
                const errorMsg = responseData.message || responseData.error || responseText || "Invalid username or password.";
                throw new Error(errorMsg);
            }

            if (!responseData.token) {
                throw new Error("Authentication failed: No access token received from server.");
            }

            // Extract User Attributes
            const token = responseData.token;
            const role = String(responseData.role || "STAFF").toUpperCase();
            const userId = responseData.userId;
            const fullName = responseData.fullName || username;

            // Clear Old Session Cache
            localStorage.clear();

            // Save Active Session
            localStorage.setItem("token", token);
            localStorage.setItem("userId", String(userId));
            localStorage.setItem("username", username);
            localStorage.setItem("fullName", fullName);
            localStorage.setItem("role", role);
            if (responseData.restaurantId) {
                localStorage.setItem("restaurantId", String(responseData.restaurantId));
            }
            if (responseData.restaurantName) {
                localStorage.setItem("restaurantName", responseData.restaurantName);
            }

            // Chef Special Cache
            if (role === "CHEF") {
                localStorage.setItem("chefToken", token);
                localStorage.setItem("chefRole", role);
                localStorage.setItem("chefId", String(userId));
            }

            showMessage("Authentication successful! Redirecting to workspace...", "success");

            // Smooth Redirect based on Role
            setTimeout(() => {
                if (role === "CHEF") {
                    window.location.href = "chef.jsp";
                } else {
                    window.location.href = "dashboard.jsp";
                }
            }, 800);

        } catch (error) {
            console.error("Login Error:", error);
            showMessage(error.message || "Unable to connect to authentication server.", "error");
            setLoading(false);
        }
    });

    function setLoading(isLoading) {
        const btn = document.getElementById("loginButton");
        const btnText = document.getElementById("btnText");
        const btnIcon = document.getElementById("btnIcon");
        const btnSpinner = document.getElementById("btnSpinner");

        if (btn) btn.disabled = isLoading;
        if (btnText) btnText.textContent = isLoading ? "Authenticating..." : "Sign In to Dashboard";
        if (btnIcon) btnIcon.style.display = isLoading ? "none" : "inline-block";
        if (btnSpinner) btnSpinner.style.display = isLoading ? "inline-block" : "none";
    }
}

// =========================================================
// MESSAGE BANNER HELPERS
// =========================================================

function showMessage(msg, type = "error") {
    const box = document.getElementById("messageBox");
    const icon = document.getElementById("messageIcon");
    const text = document.getElementById("messageText");

    if (!box || !text) return;

    box.className = `alert-banner ${type}`;
    text.textContent = msg;

    if (icon) {
        if (type === "success") {
            icon.className = "fa-solid fa-circle-check alert-icon";
        } else if (type === "info") {
            icon.className = "fa-solid fa-circle-info alert-icon";
        } else {
            icon.className = "fa-solid fa-circle-exclamation alert-icon";
        }
    }

    box.style.display = "flex";
}

function hideMessage() {
    const box = document.getElementById("messageBox");
    if (box) box.style.display = "none";
}
