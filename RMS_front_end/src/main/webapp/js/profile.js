/**
 * =========================================================
 * SANKALP RESTAURANT MANAGEMENT SYSTEM
 * MY PROFILE JAVASCRIPT
 * Full interactive logic, validation, security, and toast UI
 * =========================================================
 */

"use strict";

const BACKEND_BASE_URL = (window.API_BASE_URL || (window.location.origin.includes(":8081") ? ("http://" + window.location.hostname + ":8080") : (window.location.origin + "/api")));
const BASE_API_URL = "/api/users";
const PROFILE_API = `${BASE_API_URL}/profile`;
const CHANGE_PASSWORD_API = `${BASE_API_URL}/profile/change-password`;

let currentProfile = null;

// =========================================================
// INITIALIZATION
// =========================================================

document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "login.jsp";
        return;
    }

    loadProfile();
    initFormListeners();
    initPasswordStrengthChecker();
});

// =========================================================
// TAB SWITCHER
// =========================================================

function switchProfileTab(tabName) {
    const tabDetails = document.getElementById("tabDetails");
    const tabSecurity = document.getElementById("tabSecurity");
    const tabDetailsBtn = document.getElementById("tabDetailsBtn");
    const tabSecurityBtn = document.getElementById("tabSecurityBtn");

    if (tabName === "details") {
        tabDetails.classList.add("active");
        tabSecurity.classList.remove("active");
        tabDetailsBtn.classList.add("active");
        tabSecurityBtn.classList.remove("active");
    } else if (tabName === "security") {
        tabDetails.classList.remove("active");
        tabSecurity.classList.add("active");
        tabDetailsBtn.classList.remove("active");
        tabSecurityBtn.classList.add("active");
    }
}

// =========================================================
// PASSWORD VISIBILITY TOGGLE
// =========================================================

function togglePasswordVisibility(fieldId, btn) {
    const input = document.getElementById(fieldId);
    if (!input) return;

    const icon = btn.querySelector("i");
    if (input.type === "password") {
        input.type = "text";
        if (icon) {
            icon.classList.remove("fa-eye");
            icon.classList.add("fa-eye-slash");
        }
    } else {
        input.type = "password";
        if (icon) {
            icon.classList.remove("fa-eye-slash");
            icon.classList.add("fa-eye");
        }
    }
}

// =========================================================
// LOAD PROFILE DATA
// =========================================================

async function loadProfile() {
    const token = localStorage.getItem("token");

    try {
        const response = await fetch(PROFILE_API, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });

        if (response.status === 401 || response.status === 403) {
            localStorage.clear();
            showToast("Session expired. Redirecting to login...", "warning");
            setTimeout(() => {
                window.location.href = "login.jsp";
            }, 1500);
            return;
        }

        if (!response.ok) {
            throw new Error("Unable to fetch user profile data.");
        }

        const user = await response.json();
        currentProfile = user;
        renderProfile(user);

    } catch (error) {
        console.error("Load Profile Error:", error);
        showToast(error.message || "Failed to load profile", "error");
    }
}

// =========================================================
// RENDER PROFILE TO DOM
// =========================================================

function renderProfile(user) {
    if (!user) return;

    const fullName = user.fullName || "User";
    const username = user.username || "";
    const email = user.email || "";
    const mobile = user.mobile || "";
    const role = (user.role || "STAFF").toUpperCase();
    const status = (user.status || "ACTIVE").toUpperCase();
    const userId = user.userId ? `#${user.userId}` : "#--";

    // 1. Personal Information Form Fields
    setInputValue("fullName", fullName);
    setInputValue("profileUsername", username);
    setInputValue("email", email);
    setInputValue("mobile", mobile);
    setInputValue("userRoleDisplay", role);
    setInputValue("userStatusDisplay", status);

    // 2. Hero Card Info
    setText("heroFullName", fullName);
    setText("heroUsername", `@${username}`);
    setText("heroUserId", userId);
    setText("heroRoleLabel", role);
    setText("heroStatusLabel", status);
    setText("heroRoleText", role);

    // 3. Initials
    const initials = getInitials(fullName);
    setText("heroInitials", initials);

    // 4. Role Pill Styling
    const roleBadge = document.getElementById("heroRoleBadge");
    if (roleBadge) {
        roleBadge.className = "role-pill " + role.toLowerCase();
    }

    // 5. Update Header User Info if present
    setText("adminName", fullName);
    setText("adminRole", role === "ADMIN" ? "Administrator" : role);

    // 6. Cache in LocalStorage
    localStorage.setItem("fullName", fullName);
    localStorage.setItem("username", username);
    localStorage.setItem("role", role);
    if (user.userId) localStorage.setItem("userId", user.userId);
}

// =========================================================
// FORM LISTENERS & VALIDATION
// =========================================================

function initFormListeners() {
    // 1. Profile Form Submit
    const profileForm = document.getElementById("profileForm");
    if (profileForm) {
        profileForm.addEventListener("submit", handleProfileUpdate);
    }

    // 2. Password Form Submit
    const passwordForm = document.getElementById("passwordForm");
    if (passwordForm) {
        passwordForm.addEventListener("submit", handlePasswordChange);
    }

    // 3. Live Clear Error on Input
    ["fullName", "email", "mobile"].forEach(id => {
        const el = document.getElementById(id);
        if (el) {
            el.addEventListener("input", () => clearFieldError(id));
        }
    });

    ["oldPassword", "newPassword", "confirmPassword"].forEach(id => {
        const el = document.getElementById(id);
        if (el) {
            el.addEventListener("input", () => clearFieldError(id));
        }
    });
}

// =========================================================
// HANDLE PROFILE UPDATE
// =========================================================

async function handleProfileUpdate(event) {
    event.preventDefault();

    const fullNameInput = document.getElementById("fullName");
    const emailInput = document.getElementById("email");
    const mobileInput = document.getElementById("mobile");

    const fullName = fullNameInput ? fullNameInput.value.trim() : "";
    const email = emailInput ? emailInput.value.trim() : "";
    const mobile = mobileInput ? mobileInput.value.trim() : "";

    // Client-side validation
    let hasError = false;

    if (!fullName) {
        setFieldError("fullName", "Full Name is required.");
        hasError = true;
    } else if (fullName.length < 2) {
        setFieldError("fullName", "Full Name must be at least 2 characters.");
        hasError = true;
    }

    if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        setFieldError("email", "Please enter a valid email address.");
        hasError = true;
    }

    if (mobile && !/^[6-9]\d{9}$/.test(mobile)) {
        setFieldError("mobile", "Please enter a valid 10-digit mobile number.");
        hasError = true;
    }

    if (hasError) return;

    const btn = document.getElementById("btnSaveProfile");
    const btnText = document.getElementById("saveProfileBtnText");
    const token = localStorage.getItem("token");

    // UI Loading State
    if (btn) btn.disabled = true;
    if (btnText) btnText.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Saving...';

    try {
        const response = await fetch(PROFILE_API, {
            method: "PUT",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                fullName: fullName,
                email: email,
                mobile: mobile
            })
        });

        const resultText = await response.text();

        if (response.status === 401 || response.status === 403) {
            localStorage.clear();
            showToast("Session expired. Redirecting to login...", "warning");
            setTimeout(() => { window.location.href = "login.jsp"; }, 1500);
            return;
        }

        if (!response.ok) {
            throw new Error(resultText || "Failed to update profile.");
        }

        showToast("Profile details updated successfully!", "success");

        // Refresh local cache & hero card
        if (currentProfile) {
            currentProfile.fullName = fullName;
            currentProfile.email = email;
            currentProfile.mobile = mobile;
            renderProfile(currentProfile);
        }

    } catch (error) {
        console.error("Profile Update Error:", error);
        showToast(error.message || "Failed to update profile.", "error");
    } finally {
        if (btn) btn.disabled = false;
        if (btnText) btnText.textContent = "Save Changes";
    }
}

// =========================================================
// HANDLE PASSWORD CHANGE
// =========================================================

async function handlePasswordChange(event) {
    event.preventDefault();

    const oldPasswordInput = document.getElementById("oldPassword");
    const newPasswordInput = document.getElementById("newPassword");
    const confirmPasswordInput = document.getElementById("confirmPassword");

    const oldPassword = oldPasswordInput ? oldPasswordInput.value : "";
    const newPassword = newPasswordInput ? newPasswordInput.value : "";
    const confirmPassword = confirmPasswordInput ? confirmPasswordInput.value : "";

    let hasError = false;

    if (!oldPassword) {
        setFieldError("oldPassword", "Current password is required.");
        hasError = true;
    }

    if (!newPassword) {
        setFieldError("newPassword", "New password is required.");
        hasError = true;
    } else if (newPassword.length < 6) {
        setFieldError("newPassword", "Password must be at least 6 characters.");
        hasError = true;
    }

    if (!confirmPassword) {
        setFieldError("confirmPassword", "Please confirm your new password.");
        hasError = true;
    } else if (newPassword !== confirmPassword) {
        setFieldError("confirmPassword", "New Password and Confirm Password do not match.");
        hasError = true;
    }

    if (hasError) return;

    const btn = document.getElementById("btnSavePassword");
    const btnText = document.getElementById("savePasswordBtnText");
    const token = localStorage.getItem("token");

    if (btn) btn.disabled = true;
    if (btnText) btnText.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Updating...';

    try {
        const response = await fetch(CHANGE_PASSWORD_API, {
            method: "PUT",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                oldPassword: oldPassword,
                newPassword: newPassword,
                confirmPassword: confirmPassword
            })
        });

        const resultText = await response.text();

        if (response.status === 401 || response.status === 403) {
            localStorage.clear();
            showToast("Session expired. Redirecting to login...", "warning");
            setTimeout(() => { window.location.href = "login.jsp"; }, 1500);
            return;
        }

        if (!response.ok) {
            throw new Error(resultText || "Failed to update password.");
        }

        showToast("Password updated successfully! Notification sent to email.", "success");

        // Reset form & strength meter
        document.getElementById("passwordForm").reset();
        resetPasswordStrength();

    } catch (error) {
        console.error("Password Change Error:", error);
        showToast(error.message || "Failed to change password.", "error");
    } finally {
        if (btn) btn.disabled = false;
        if (btnText) btnText.textContent = "Update Password";
    }
}

// =========================================================
// REAL-TIME PASSWORD STRENGTH CHECKER
// =========================================================

function initPasswordStrengthChecker() {
    const newPassInput = document.getElementById("newPassword");
    const confirmPassInput = document.getElementById("confirmPassword");

    if (newPassInput) {
        newPassInput.addEventListener("input", updatePasswordStrength);
    }
    if (confirmPassInput) {
        confirmPassInput.addEventListener("input", updatePasswordMatch);
    }
}

function updatePasswordStrength() {
    const pass = (document.getElementById("newPassword").value || "").trim();
    const bar = document.getElementById("strengthBar");
    const text = document.getElementById("strengthText");

    const ruleMinLen = document.getElementById("ruleMinLen");
    const ruleLetters = document.getElementById("ruleLetters");

    if (!bar || !text) return;

    // Rule 1: min 6 chars
    const hasMinLen = pass.length >= 6;
    toggleRule(ruleMinLen, hasMinLen);

    // Rule 2: letters and digits
    const hasLetters = /[a-zA-Z]/.test(pass);
    const hasNumbers = /\d/.test(pass);
    const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(pass);
    const hasLetterAndNum = hasLetters && hasNumbers;
    toggleRule(ruleLetters, hasLetterAndNum);

    updatePasswordMatch();

    if (pass.length === 0) {
        bar.className = "strength-bar-fill";
        text.textContent = "Password strength: -";
        return;
    }

    let score = 0;
    if (hasMinLen) score++;
    if (hasLetterAndNum) score++;
    if (pass.length >= 8 && hasSpecial) score++;

    if (score === 1) {
        bar.className = "strength-bar-fill weak";
        text.textContent = "Password strength: Weak";
    } else if (score === 2) {
        bar.className = "strength-bar-fill medium";
        text.textContent = "Password strength: Medium";
    } else if (score >= 3) {
        bar.className = "strength-bar-fill strong";
        text.textContent = "Password strength: Strong";
    }
}

function updatePasswordMatch() {
    const pass = document.getElementById("newPassword").value;
    const confirm = document.getElementById("confirmPassword").value;
    const ruleMatch = document.getElementById("ruleMatch");

    const isMatch = pass.length > 0 && pass === confirm;
    toggleRule(ruleMatch, isMatch);
}

function toggleRule(el, isValid) {
    if (!el) return;
    const icon = el.querySelector("i");
    if (isValid) {
        el.classList.add("valid");
        if (icon) {
            icon.className = "fa-solid fa-circle-check";
        }
    } else {
        el.classList.remove("valid");
        if (icon) {
            icon.className = "fa-solid fa-circle-xmark";
        }
    }
}

function resetPasswordStrength() {
    const bar = document.getElementById("strengthBar");
    const text = document.getElementById("strengthText");
    if (bar) bar.className = "strength-bar-fill";
    if (text) text.textContent = "Password strength: -";

    ["ruleMinLen", "ruleLetters", "ruleMatch"].forEach(id => {
        toggleRule(document.getElementById(id), false);
    });
}

// =========================================================
// TOAST NOTIFICATIONS
// =========================================================

function showToast(message, type = "info") {
    let container = document.getElementById("profileToastContainer");
    if (!container) {
        container = document.createElement("div");
        container.id = "profileToastContainer";
        container.className = "profile-toast-container";
        document.body.appendChild(container);
    }

    const toast = document.createElement("div");
    toast.className = `profile-toast ${type}`;

    let iconClass = "fa-circle-info";
    if (type === "success") iconClass = "fa-circle-check";
    else if (type === "error") iconClass = "fa-circle-exclamation";
    else if (type === "warning") iconClass = "fa-triangle-exclamation";

    toast.innerHTML = `
        <i class="fa-solid ${iconClass}"></i>
        <span class="profile-toast-msg">${escapeHtml(message)}</span>
        <button type="button" class="profile-toast-close" onclick="this.parentElement.remove()">
            <i class="fa-solid fa-xmark"></i>
        </button>
    `;

    container.appendChild(toast);

    setTimeout(() => {
        if (toast.parentElement) {
            toast.style.opacity = "0";
            toast.style.transform = "translateX(40px)";
            toast.style.transition = "all 0.3s ease-out";
            setTimeout(() => toast.remove(), 300);
        }
    }, 4000);
}

// =========================================================
// HELPERS
// =========================================================

function setText(id, text) {
    const el = document.getElementById(id);
    if (el) el.textContent = text;
}

function setInputValue(id, val) {
    const el = document.getElementById(id);
    if (el) el.value = val;
}

function setFieldError(id, msg) {
    const errEl = document.getElementById(id + "Error");
    if (errEl) errEl.textContent = msg;
    const inputEl = document.getElementById(id);
    if (inputEl) inputEl.style.borderColor = "var(--danger)";
}

function clearFieldError(id) {
    const errEl = document.getElementById(id + "Error");
    if (errEl) errEl.textContent = "";
    const inputEl = document.getElementById(id);
    if (inputEl) inputEl.style.borderColor = "";
}

function getInitials(name) {
    if (!name) return "U";
    const parts = name.trim().split(/\s+/);
    if (parts.length >= 2) {
        return (parts[0][0] + parts[1][0]).toUpperCase();
    }
    return parts[0].substring(0, 2).toUpperCase();
}

function escapeHtml(str) {
    if (!str) return "";
    return str
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}