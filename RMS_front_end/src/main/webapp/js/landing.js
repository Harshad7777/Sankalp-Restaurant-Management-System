/**
 * =========================================================
 * SANKALP RESTAURANT
 * LANDING PAGE JAVASCRIPT
 * Dynamic Menu & Category Fetching, Search, Filters, and Smooth UX
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

const BACKEND_BASE_URL = (window.API_BASE_URL || (window.location.origin.includes(":8081") ? ("http://" + window.location.hostname + ":8080") : (window.location.origin + "/api")));
const MENU_API = "/api/menu";
const CATEGORY_API = "/api/category";

let allMenuItems = [];
let allCategories = [];
let selectedCategoryId = "all";
let currentSearchQuery = "";

// Default fallback items if backend database is empty
const DEFAULT_MENU_ITEMS = [
    {
        itemId: 101,
        itemName: "Royal Paneer Tikka",
        description: "Charcoal-grilled cottage cheese marinated in Kashmiri saffron & aromatic whole spices.",
        price: 349,
        categoryName: "Starters",
        categoryId: 1,
        image: "https://images.unsplash.com/photo-1567184109191-3783b3218038?auto=format&fit=crop&w=800&q=85"
    },
    {
        itemId: 102,
        itemName: "Hyderabadi Dum Biryani",
        description: "Fragrant aged basmati rice slow-cooked in a sealed clay pot with saffron and herbs.",
        price: 399,
        categoryName: "Rice & Biryani",
        categoryId: 2,
        image: "https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=800&q=85"
    },
    {
        itemId: 103,
        itemName: "Dal Makhani Grand Cru",
        description: "Black lentils slow-simmered for 24 hours with organic churned butter and cream.",
        price: 299,
        categoryName: "Main Course",
        categoryId: 3,
        image: "https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=800&q=85"
    },
    {
        itemId: 104,
        itemName: "Crispy Masala Dosa",
        description: "Golden crisp crepe filled with spiced potato mash, served with trio chutneys and sambar.",
        price: 219,
        categoryName: "South Indian",
        categoryId: 4,
        image: "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85"
    },
    {
        itemId: 105,
        itemName: "Saffron Kesari Kulfi",
        description: "Dense artisanal Indian ice cream infused with pistachios, cardamoms, and saffron flakes.",
        price: 189,
        categoryName: "Desserts",
        categoryId: 5,
        image: "https://images.unsplash.com/photo-1551024506-0bccd828d307?auto=format&fit=crop&w=800&q=85"
    },
    {
        itemId: 106,
        itemName: "Royal Mango Blossom Mocktail",
        description: "Fresh Alphonso mango pulp infused with mint sprigs, lime zest, and sparkling soda.",
        price: 169,
        categoryName: "Beverages",
        categoryId: 6,
        image: "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?auto=format&fit=crop&w=800&q=85"
    }
];

// =========================================================
// DOM READY INITIALIZATION
// =========================================================

document.addEventListener("DOMContentLoaded", function () {
    console.log("======================================");
    console.log("Sankalp Restaurant Landing Page Loaded");
    console.log("======================================");

    initializeNavbar();
    initializeSmoothScroll();
    initializeScrollReveal();
    initializeCounters();
    initializeBackToTop();
    initializeReservation();
    initializeGallery();
    initializeCurrentYear();
    initializeBookingDate();
    initializeLandingBrandSettings();

    // Fetch and render live menu from Backend API
    loadLiveMenuAndCategories();
    initializeMenuSearch();
});

let currentPublicRestaurantId = 1;

async function loadLiveMenuAndCategories(restaurantId) {
    if (restaurantId) {
        currentPublicRestaurantId = restaurantId;
    }
    const reqResId = currentPublicRestaurantId || 1;
    selectedCategoryId = "all";

    try {
        const catApi = CATEGORY_API + "?restaurantId=" + reqResId;
        const menuApi = MENU_API + "?restaurantId=" + reqResId;

        // Fetch Categories
        const catPromise = fetch(catApi, { method: "GET" })
            .then(res => res.ok ? res.json() : [])
            .catch(() => []);

        // Fetch Menu Items
        const menuPromise = fetch(menuApi, { method: "GET" })
            .then(res => res.ok ? res.json() : [])
            .catch(() => []);

        const [categories, menuItems] = await Promise.all([catPromise, menuPromise]);

        allCategories = Array.isArray(categories) && categories.length > 0 ? categories : [];
        allMenuItems = Array.isArray(menuItems) && menuItems.length > 0 ? menuItems : DEFAULT_MENU_ITEMS;

        // Render Category Filter Pills
        renderCategoryTabs(allCategories);

        // Render Menu Cards Grid
        renderMenuCards(allMenuItems);

    } catch (error) {
        console.warn("Backend API not reachable, loading fallback culinary menu:", error);
        allMenuItems = DEFAULT_MENU_ITEMS;
        renderMenuCards(DEFAULT_MENU_ITEMS);
    }
}

function switchPublicRestaurantMenu(restaurantId) {
    console.log("Switching public menu to Restaurant ID:", restaurantId);
    loadLiveMenuAndCategories(restaurantId);
}

// =========================================================
// RENDER CATEGORY TABS
// =========================================================

function renderCategoryTabs(categories) {
    const container = document.getElementById("categoryTabsContainer");
    if (!container) return;

    let html = `
        <button type="button" class="cat-pill ${selectedCategoryId === 'all' ? 'active' : ''}" onclick="filterByCategory('all', this)">
            <i class="fa-solid fa-utensils"></i> All Specialities
        </button>
    `;

    if (Array.isArray(categories) && categories.length > 0) {
        categories.forEach(cat => {
            const id = cat.categoryId;
            const name = cat.categoryName || `Category ${id}`;
            const isActive = String(selectedCategoryId) === String(id);
            html += `
                <button type="button" class="cat-pill ${isActive ? 'active' : ''}" onclick="filterByCategory('${id}', this)">
                    <i class="fa-solid fa-bowl-rice"></i> ${escapeHtml(name)}
                </button>
            `;
        });
    }

    container.innerHTML = html;
}

// =========================================================
// FILTER MENU BY CATEGORY
// =========================================================

function filterByCategory(categoryId, buttonEl) {
    selectedCategoryId = categoryId;

    // Update active tab visual
    const buttons = document.querySelectorAll(".cat-pill");
    buttons.forEach(btn => btn.classList.remove("active"));
    if (buttonEl) buttonEl.classList.add("active");

    applyMenuFilters();
}

// =========================================================
// SEARCH & FILTER COMBINED ENGINE
// =========================================================

function initializeMenuSearch() {
    const searchInput = document.getElementById("menuSearchInput");
    const clearBtn = document.getElementById("clearMenuSearch");

    if (searchInput) {
        searchInput.addEventListener("input", function () {
            currentSearchQuery = this.value.trim().toLowerCase();
            if (clearBtn) {
                clearBtn.style.display = currentSearchQuery ? "inline-flex" : "none";
            }
            applyMenuFilters();
        });
    }

    if (clearBtn) {
        clearBtn.addEventListener("click", function () {
            if (searchInput) searchInput.value = "";
            currentSearchQuery = "";
            clearBtn.style.display = "none";
            applyMenuFilters();
        });
    }
}

function resetMenuFilter() {
    const searchInput = document.getElementById("menuSearchInput");
    if (searchInput) searchInput.value = "";
    currentSearchQuery = "";
    selectedCategoryId = "all";

    const allBtn = document.querySelector('.cat-pill[data-category-id="all"]');
    if (allBtn) {
        document.querySelectorAll(".cat-pill").forEach(b => b.classList.remove("active"));
        allBtn.classList.add("active");
    }

    applyMenuFilters();
}

function applyMenuFilters() {
    let filtered = allMenuItems.filter(item => {
        // Category Check
        let matchesCategory = true;
        if (selectedCategoryId !== "all") {
            matchesCategory = String(item.categoryId) === String(selectedCategoryId);
        }

        // Search Query Check
        let matchesSearch = true;
        if (currentSearchQuery) {
            const name = (item.itemName || "").toLowerCase();
            const desc = (item.description || "").toLowerCase();
            const cat = (item.categoryName || "").toLowerCase();
            matchesSearch = name.includes(currentSearchQuery) || desc.includes(currentSearchQuery) || cat.includes(currentSearchQuery);
        }

        return matchesCategory && matchesSearch;
    });

    renderMenuCards(filtered);
}

// =========================================================
// RENDER MENU CARDS TO DOM
// =========================================================

function renderMenuCards(items) {
    const container = document.getElementById("dynamicMenuContainer");
    const emptyMsg = document.getElementById("noMenuItemsMessage");

    if (!container) return;

    if (!items || items.length === 0) {
        container.innerHTML = "";
        if (emptyMsg) emptyMsg.style.display = "block";
        return;
    }

    if (emptyMsg) emptyMsg.style.display = "none";

    let html = "";
    items.forEach(item => {
        const id = item.itemId;
        const name = escapeHtml(item.itemName || "Signature Dish");
        const desc = escapeHtml(item.description || "Freshly cooked to perfection with traditional herbs & spices.");
        const price = Number(item.price || 0).toLocaleString("en-IN", { minimumFractionDigits: 0 });
        
        function resolveMenuImageSrc(img) {
            const localDefault = "/images/menu/default-food.jpg";
            
            if (!img || typeof img !== "string" || img.trim() === "" || img === "null" || img === "undefined") {
                return localDefault;
            }
            let trimmed = img.trim();
            if (trimmed.startsWith("http://") || trimmed.startsWith("https://") || trimmed.startsWith("data:")) {
                return trimmed;
            }
            if (trimmed.startsWith("/")) {
                trimmed = trimmed.substring(1);
            }
            if (trimmed.startsWith("uploads/")) {
                trimmed = trimmed.substring(8);
            }
            if (trimmed.startsWith("images/")) {
                return "/" + trimmed;
            }
            return "/api/uploads/" + encodeURIComponent(trimmed);
        }

        const imageSrc = resolveMenuImageSrc(item.image);
        const fallbackImg = "/images/menu/default-food.jpg";

        const categoryBadge = item.categoryName ? `<span class="food-badge">${escapeHtml(item.categoryName)}</span>` : '<span class="food-badge">Chef Choice</span>';

        html += `
            <div class="col-md-6 col-lg-4">
                <div class="food-card">
                    <div class="food-image">
                        <img src="${imageSrc}" alt="${name}" loading="lazy" onerror="this.onerror=null; this.src='${fallbackImg}';">
                        ${categoryBadge}
                    </div>
                    <div class="food-content">
                        <div class="food-title">
                            <h4>${name}</h4>
                            <span>₹${price}</span>
                        </div>
                        <p>${desc}</p>
                        <div class="food-actions">
                            <a href="#contact" class="btn-order-dish" onclick="prefillBookingDish('${name}')">
                                <i class="fa-solid fa-utensils"></i> Order / Reserve
                                <i class="fa-solid fa-arrow-right arrow-icon"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        `;
    });

    container.innerHTML = html;
}

function prefillBookingDish(dishName) {
    const specialReq = document.getElementById("specialRequest");
    if (specialReq) {
        specialReq.value = `Interested in trying: ${dishName}`;
    }
}

// =========================================================
// NAVBAR SCROLL
// =========================================================

function initializeNavbar() {
    const navbar = document.getElementById("mainNavbar");
    if (!navbar) return;

    function updateNavbar() {
        if (window.scrollY > 50) {
            navbar.classList.add("scrolled");
        } else {
            navbar.classList.remove("scrolled");
        }
    }

    window.addEventListener("scroll", updateNavbar, { passive: true });
    updateNavbar();
}

// =========================================================
// SMOOTH SCROLL
// =========================================================

function initializeSmoothScroll() {
    const links = document.querySelectorAll('a[href^="#"]');

    links.forEach(function (link) {
        link.addEventListener("click", function (event) {
            const targetId = this.getAttribute("href");
            if (!targetId || targetId === "#") return;

            const target = document.querySelector(targetId);
            if (!target) return;

            event.preventDefault();
            const navbar = document.getElementById("mainNavbar");
            const navbarHeight = navbar ? navbar.offsetHeight : 0;
            const targetPosition = target.getBoundingClientRect().top + window.pageYOffset - navbarHeight;

            window.scrollTo({
                top: targetPosition,
                behavior: "smooth"
            });

            // Close Mobile collapse
            const navbarCollapse = document.getElementById("navbarContent");
            if (navbarCollapse && navbarCollapse.classList.contains("show")) {
                const collapseInstance = bootstrap.Collapse.getInstance(navbarCollapse);
                if (collapseInstance) collapseInstance.hide();
            }
        });
    });
}

// =========================================================
// SCROLL REVEAL OBSERVER
// =========================================================

function initializeScrollReveal() {
    const revealElements = document.querySelectorAll(".reveal");
    if (!revealElements.length) return;

    const observer = new IntersectionObserver(
        function (entries) {
            entries.forEach(function (entry) {
                if (entry.isIntersecting) {
                    entry.target.classList.add("active");
                    observer.unobserve(entry.target);
                }
            });
        },
        { threshold: 0.1 }
    );

    revealElements.forEach(function (element) {
        observer.observe(element);
    });
}

// =========================================================
// COUNTERS ANIMATION
// =========================================================

function initializeCounters() {
    const counters = document.querySelectorAll(".counter");
    if (!counters.length) return;

    let countersStarted = false;
    const statsSection = document.querySelector(".stats-section");
    if (!statsSection) return;

    const observer = new IntersectionObserver(
        function (entries) {
            if (entries[0].isIntersecting && !countersStarted) {
                countersStarted = true;
                counters.forEach(animateCounter);
            }
        },
        { threshold: 0.3 }
    );

    observer.observe(statsSection);
}

function animateCounter(counter) {
    const target = parseInt(counter.getAttribute("data-target"), 10);
    if (isNaN(target)) return;

    let current = 0;
    const duration = 1800;
    const increment = target / (duration / 20);

    const timer = setInterval(function () {
        current += increment;
        if (current >= target) {
            current = target;
            clearInterval(timer);
        }

        if (target >= 1000) {
            counter.textContent = Math.floor(current).toLocaleString() + "+";
        } else {
            counter.textContent = Math.floor(current) + "+";
        }
    }, 20);
}

// =========================================================
// BACK TO TOP
// =========================================================

function initializeBackToTop() {
    const button = document.getElementById("backToTop");
    if (!button) return;

    window.addEventListener("scroll", function () {
        if (window.scrollY > 400) {
            button.classList.add("show");
        } else {
            button.classList.remove("show");
        }
    }, { passive: true });

    button.addEventListener("click", function () {
        window.scrollTo({ top: 0, behavior: "smooth" });
    });
}

// =========================================================
// RESERVATION HANDLER
// =========================================================

function initializeReservation() {
    const form = document.getElementById("reservationForm");
    if (!form) return;

    const submitBtn = form.querySelector('button[type="submit"]');

    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        const nameInput = document.getElementById("guestName");
        const phoneInput = document.getElementById("guestPhone");
        const emailInput = document.getElementById("guestEmail");
        const dateInput = document.getElementById("bookingDate");
        const timeInput = document.getElementById("bookingTime");
        const guestInput = document.getElementById("guestCount");
        const specialInput = document.getElementById("specialRequest");

        const name = nameInput ? nameInput.value.trim() : "";
        const phone = phoneInput ? phoneInput.value.trim() : "";
        const email = emailInput ? emailInput.value.trim() : "";
        const date = dateInput ? dateInput.value : "";
        const time = timeInput ? timeInput.value : "";
        const guests = guestInput ? parseInt(guestInput.value, 10) : 2;
        const special = specialInput ? specialInput.value.trim() : "";

        if (!name || !phone || !date || !time) {
            alert("Please fill in all mandatory reservation fields (Name, Phone, Date, and Time Slot).");
            return;
        }

        const phoneRegex = /^[0-9]{10}$/;
        if (!phoneRegex.test(phone)) {
            alert("Please enter a valid 10-digit mobile phone number.");
            if (phoneInput) phoneInput.focus();
            return;
        }

        const restSelect = document.getElementById("reservationRestaurantId");
        const resId = restSelect ? parseInt(restSelect.value, 10) : 1;

        const payload = {
            restaurantId: isNaN(resId) ? 1 : resId,
            guestName: name,
            guestPhone: phone,
            guestEmail: email,
            bookingDate: date,
            bookingTime: time,
            guestCount: isNaN(guests) ? 2 : guests,
            specialRequest: special
        };

        const originalBtnHtml = submitBtn ? submitBtn.innerHTML : "Confirm Reservation";
        if (submitBtn) {
            submitBtn.disabled = true;
            submitBtn.innerHTML = `<i class="fa-solid fa-spinner fa-spin me-2"></i> Submitting Reservation...`;
        }

        try {
            const reservationApi = "/api/reservation";
            const response = await fetch(reservationApi, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                },
                body: JSON.stringify(payload)
            });

            const data = await response.json();

            if (response.ok && data) {
                // Populate Modal details
                const codeEl = document.getElementById("modalResCode");
                const nameEl = document.getElementById("modalResName");
                const dateTimeEl = document.getElementById("modalResDateTime");
                const guestsEl = document.getElementById("modalResGuests");

                if (codeEl) codeEl.textContent = data.reservationCode || "RES-SUBMITTED";
                if (nameEl) nameEl.textContent = data.guestName || name;
                if (dateTimeEl) dateTimeEl.textContent = `${data.bookingDate || date} @ ${data.bookingTime || time}`;
                if (guestsEl) guestsEl.textContent = `${data.guestCount || guests} Guest(s)`;

                const modalElement = document.getElementById("reservationModal");
                if (modalElement) {
                    const modal = new bootstrap.Modal(modalElement);
                    modal.show();
                }

                form.reset();
                initializeBookingDate();
            } else {
                const errMsg = (data && data.message) ? data.message : "Unable to process table reservation request. Please try again.";
                alert(`Reservation Failed: ${errMsg}`);
            }
        } catch (error) {
            console.error("Error submitting reservation:", error);
            alert("Network or server connection error. Please check your network connection and try again.");
        } finally {
            if (submitBtn) {
                submitBtn.disabled = false;
                submitBtn.innerHTML = originalBtnHtml;
            }
        }
    });
}

// =========================================================
// DATE LIMITS
// =========================================================

function initializeBookingDate() {
    const dateInput = document.getElementById("bookingDate");
    if (!dateInput) return;

    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");

    dateInput.min = `${year}-${month}-${day}`;
}

// =========================================================
// GALLERY PREVIEW
// =========================================================

function initializeGallery() {
    const galleryItems = document.querySelectorAll(".gallery-item");

    galleryItems.forEach(function (item) {
        item.addEventListener("click", function () {
            const image = this.querySelector("img");
            if (!image) return;
            openImagePreview(image.src, image.alt);
        });
    });
}

function openImagePreview(imageSrc, imageAlt) {
    const existing = document.getElementById("imagePreviewModal");
    if (existing) existing.remove();

    const modalHtml = `
        <div class="modal fade" id="imagePreviewModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered modal-xl">
                <div class="modal-content bg-dark border-0">
                    <div class="modal-header border-0 pb-0">
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body text-center p-3">
                        <img src="${escapeHtml(imageSrc)}" alt="${escapeHtml(imageAlt)}" style="max-width:100%; max-height:80vh; object-fit:contain; border-radius:8px;">
                    </div>
                </div>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML("beforeend", modalHtml);
    const modalElement = document.getElementById("imagePreviewModal");
    const modal = new bootstrap.Modal(modalElement);
    modal.show();

    modalElement.addEventListener("hidden.bs.modal", function () {
        modalElement.remove();
    });
}

function initializeCurrentYear() {
    const yearElement = document.getElementById("currentYear");
    if (yearElement) yearElement.textContent = new Date().getFullYear();
}

function escapeHtml(value) {
    if (!value) return "";
    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

// =========================================================
// BRAND IDENTITY & SETTINGS SYNC
// =========================================================

async function initializeLandingBrandSettings() {
    // 1. Read cached settings immediately
    try {
        const stored = localStorage.getItem("sankalp_restaurant_settings");
        if (stored) {
            applyLandingBranding(JSON.parse(stored));
        }
    } catch (e) {
        console.warn("Cached settings read error:", e);
    }

    // 2. Fetch fresh settings from backend REST API
    try {
        const res = await fetch("/api/settings");
        if (res.ok) {
            const data = await res.json();
            if (data && typeof data === "object") {
                localStorage.setItem("sankalp_restaurant_settings", JSON.stringify(data));
                applyLandingBranding(data);
            }
        }
    } catch (e) {
        // Backend offline, cached values are retained
    }
}

function applyLandingBranding(settings) {
    if (!settings) return;

    const navLogo = document.getElementById("landingNavLogo");
    const navName = document.getElementById("landingNavName");
    const navSubtitle = document.getElementById("landingNavSubtitle");
    const footerLogo = document.getElementById("landingFooterLogo");
    const footerName = document.getElementById("landingFooterName");
    const copyrightName = document.getElementById("landingCopyrightName");

    const contactAddress = document.getElementById("landingContactAddress");
    const contactHours = document.getElementById("landingContactHours");
    const contactPhone = document.getElementById("landingContactPhone");

    // Format Logo Path
    if (settings.logoPath) {
        let fullSrc = settings.logoPath.trim();
        if (!fullSrc.startsWith("http") && !fullSrc.startsWith("data:")) {
            if (fullSrc.startsWith("uploads/")) fullSrc = "/api/" + fullSrc;
            else if (fullSrc.startsWith("/uploads/")) fullSrc = "/api" + fullSrc;
            else if (!fullSrc.startsWith("/")) fullSrc = "/" + fullSrc;
        }

        if (navLogo) navLogo.src = fullSrc;
        if (footerLogo) footerLogo.src = fullSrc;

        const favicon = document.getElementById("dynamicFavicon");
        if (favicon) {
            favicon.href = fullSrc;
        }
    }

    // Restaurant Name
    if (settings.restaurantName) {
        if (navName) navName.textContent = settings.restaurantName;
        if (footerName) footerName.textContent = settings.restaurantName + " Management System";
        if (copyrightName) copyrightName.textContent = settings.restaurantName;
    }

    // Tagline / Subtitle
    if (settings.tagline && navSubtitle) {
        navSubtitle.textContent = settings.tagline.toUpperCase();
    }

    // Address
    if (settings.address && contactAddress) {
        contactAddress.textContent = settings.address;
    }

    // Operating Hours
    if (settings.openingTime && settings.closingTime && contactHours) {
        contactHours.textContent = `Mon – Sun: ${formatTime12Hour(settings.openingTime)} – ${formatTime12Hour(settings.closingTime)}`;
    }

    // Phone
    if (settings.phone && contactPhone) {
        contactPhone.textContent = settings.phone;
    }
}

function formatTime12Hour(timeStr) {
    if (!timeStr) return "";
    const parts = timeStr.split(":");
    if (parts.length < 2) return timeStr;
    let hour = parseInt(parts[0], 10);
    const minute = parts[1];
    const ampm = hour >= 12 ? "PM" : "AM";
    hour = hour % 12;
    if (hour === 0) hour = 12;
    const hourFormatted = hour < 10 ? "0" + hour : hour;
    return `${hourFormatted}:${minute} ${ampm}`;
}