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

document.addEventListener("DOMContentLoaded", function () {
    const registerForm = document.getElementById("registerForm");
    const messageBox = document.getElementById("message");
    const roleSelect = document.getElementById("role");
    const restaurantModeSelect = document.getElementById("restaurantMode");
    const newGroup = document.getElementById("newRestaurantNameGroup");
    const existingGroup = document.getElementById("existingRestaurantGroup");
    const existingSelect = document.getElementById("restaurantId");

    // Load available restaurants
    loadExistingRestaurants();

    function updateTenantFieldsVisibility() {
        const mode = restaurantModeSelect ? restaurantModeSelect.value : "NEW";
        if (mode === "NEW") {
            if (newGroup) newGroup.style.display = "block";
            if (existingGroup) existingGroup.style.display = "none";
        } else {
            if (newGroup) newGroup.style.display = "none";
            if (existingGroup) existingGroup.style.display = "block";
        }
    }

    if (restaurantModeSelect) {
        restaurantModeSelect.addEventListener("change", updateTenantFieldsVisibility);
    }
    if (roleSelect) {
        roleSelect.addEventListener("change", function () {
            if (roleSelect.value !== "ADMIN") {
                // Default non-admins to joining existing restaurant
                if (restaurantModeSelect) restaurantModeSelect.value = "EXISTING";
            }
            updateTenantFieldsVisibility();
        });
    }

    updateTenantFieldsVisibility();

    async function loadExistingRestaurants() {
        if (!existingSelect) return;
        try {
            const res = await fetch("/api/restaurants");
            if (res.ok) {
                const list = await res.json();
                if (Array.isArray(list) && list.length > 0) {
                    existingSelect.innerHTML = list.map(r => 
                        `<option value="${r.restaurantId}" style="background:#161616; color:#fff;">${r.restaurantName}</option>`
                    ).join("");
                }
            }
        } catch (e) {
            console.warn("Could not load restaurants list", e);
        }
    }

    function showMessage(msg, isSuccess = false) {
        if (!messageBox) {
            alert(msg);
            return;
        }
        const span = messageBox.querySelector("span");
        if (span) span.textContent = msg;
        messageBox.style.display = "flex";
        if (isSuccess) {
            messageBox.style.background = "rgba(34, 197, 94, 0.15)";
            messageBox.style.borderColor = "rgba(34, 197, 94, 0.4)";
            messageBox.style.color = "#4ade80";
        } else {
            messageBox.style.background = "rgba(239, 68, 68, 0.15)";
            messageBox.style.borderColor = "rgba(239, 68, 68, 0.4)";
            messageBox.style.color = "#f87171";
        }
    }

    if (registerForm) {
        registerForm.addEventListener("submit", async function (e) {
            e.preventDefault();

            const fullName = document.getElementById("fullName").value.trim();
            const username = document.getElementById("username").value.trim();
            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value;
            const role = document.getElementById("role").value;
            const mode = restaurantModeSelect ? restaurantModeSelect.value : "NEW";

            if (!fullName || !username || !email || !password) {
                showMessage("All fields are required.");
                return;
            }

            if (password.length < 6) {
                showMessage("Password must be at least 6 characters.");
                return;
            }

            const user = {
                fullName: fullName,
                username: username,
                email: email,
                password: password,
                role: role
            };

            if (mode === "NEW") {
                const resName = document.getElementById("restaurantName")?.value?.trim();
                if (!resName) {
                    showMessage("Please enter a New Restaurant Name.");
                    return;
                }
                user.restaurantName = resName;
                delete user.restaurantId;
            } else {
                const selectedId = existingSelect ? parseInt(existingSelect.value) : 1;
                user.restaurantId = selectedId;
                delete user.restaurantName;
            }

            try {
                const response = await fetch("/api/users/register", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    },
                    body: JSON.stringify(user)
                });

                const text = await response.text();
                let result;
                try {
                    result = JSON.parse(text);
                } catch {
                    result = { message: text };
                }

                if (response.ok && result.success) {
                    showMessage(result.message || "Registration Successful! Redirecting to login...", true);
                    setTimeout(() => {
                        window.location.href = "login.jsp";
                    }, 1500);
                } else {
                    showMessage(result.message || "Registration failed. Please try again.");
                    console.error("Registration error:", result);
                }

            } catch (error) {
                console.error("Network Error:", error);
                showMessage("Cannot connect to server. Please ensure backend is running.");
            }
        });
    }
});