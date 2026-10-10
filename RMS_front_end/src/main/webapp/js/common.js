"use strict";

/* =====================================================
   SANKALP RMS
   COMMON JS
===================================================== */

console.log("======================================");
console.log("Sankalp Common JS Loaded");
console.log("======================================");


document.addEventListener(
    "DOMContentLoaded",
    function () {

        const role = getCurrentRole();

        console.log(
            "Logged-in role:",
            role
        );

        applyRoleBasedSidebar(role);

        updateDateTime();

        startLiveClock();

        loadAdmin();

        setupSidebar();

        setupProfile();

        setupNotifications();

        setupGlobalSearch();

        setupLogout();

        setActiveSidebarLink();

        syncGlobalBrandLogo();

        setupThemeToggle();

    }
);

/* =====================================================
   GLOBAL BRAND LOGO SYNC
===================================================== */

const RMS_GLOBAL_BACKEND_URL = (typeof window.API_BASE_URL !== "undefined" && window.API_BASE_URL)
    ? window.API_BASE_URL
    : (window.location.origin.includes(":8081") 
        ? "http://localhost:8080" 
        : (window.location.origin + "/RMS_backend"));

async function syncGlobalBrandLogo() {
    const sidebarLogo = document.getElementById("sidebarLogoImg");

    // 1. Local storage instant apply
    try {
        const storedName = localStorage.getItem("restaurantName");
        if (storedName) applyRestaurantName(storedName);

        const stored = localStorage.getItem("sankalp_restaurant_settings");
        if (stored) {
            const settings = JSON.parse(stored);
            if (settings && settings.logoPath) {
                applySidebarLogo(sidebarLogo, settings.logoPath);
            }
            if (settings && settings.restaurantName) {
                applyRestaurantName(settings.restaurantName);
            }
        }
    } catch (e) {}

    // 2. Fetch fresh restaurant details for authenticated user
    try {
        const token = localStorage.getItem("token");
        if (token) {
            const headers = { "Authorization": "Bearer " + token };
            const currentUrl = (typeof window.getApiUrl === "function") ? window.getApiUrl("/restaurant/current") : "/api/restaurant/current";
            const res = await fetch(currentUrl, { headers });
            if (res.ok) {
                const restaurant = await res.json();
                if (restaurant && restaurant.restaurantName) {
                    localStorage.setItem("restaurantName", restaurant.restaurantName);
                    applyRestaurantName(restaurant.restaurantName);
                }
            }
        }
    } catch (e) {}

    // 3. Fetch fresh settings from backend API with tenant authentication
    try {
        const token = localStorage.getItem("token");
        const headers = token ? { "Authorization": "Bearer " + token } : {};
        const settingsUrl = (typeof window.getApiUrl === "function") ? window.getApiUrl("/settings") : "/api/settings";
        const res = await fetch(settingsUrl, { headers });
        if (res.ok) {
            const settings = await res.json();
            if (settings) {
                localStorage.setItem("sankalp_restaurant_settings", JSON.stringify(settings));
                if (settings.logoPath) applySidebarLogo(sidebarLogo, settings.logoPath);
                if (settings.restaurantName) {
                    localStorage.setItem("restaurantName", settings.restaurantName);
                    applyRestaurantName(settings.restaurantName);
                }
            }
        }
    } catch (e) {}
}

function applyRestaurantName(name) {
    if (!name) return;
    const brandEls = document.querySelectorAll(".brand-name, .restaurant-brand-name, #headerRestaurantName, #sidebarRestaurantName, #sidebarBottomTitle");
    brandEls.forEach(el => {
        el.textContent = name;
    });
}

function applySidebarLogo(imgEl, logoPath) {
    if (!logoPath) return;
    let fullSrc = logoPath.trim();
    if (!fullSrc.startsWith("http") && !fullSrc.startsWith("data:")) {
        if (fullSrc.startsWith("uploads/")) fullSrc = "/api/" + fullSrc;
        else if (fullSrc.startsWith("/uploads/")) fullSrc = "/api" + fullSrc;
        else if (!fullSrc.startsWith("/")) fullSrc = "/" + fullSrc;
    }
    if (imgEl) {
        imgEl.src = fullSrc;
    }
    const favicon = document.getElementById("dynamicFavicon");
    if (favicon) {
        favicon.href = fullSrc;
    }
}


/* =====================================================
   GET ROLE
===================================================== */

function getCurrentRole() {

    let role =
        localStorage.getItem("role");


    if (!role) {

        role =
            localStorage.getItem("userRole");

    }


    if (!role) {

        role =
            localStorage.getItem("chefRole");

    }


    return String(
        role || ""
    )
        .trim()
        .toUpperCase();

}


/* =====================================================
   ROLE BASED SIDEBAR
===================================================== */

function applyRoleBasedSidebar(role) {

    const sidebar =
        document.getElementById(
            "adminSidebar"
        );


    if (!sidebar) {

        console.error(
            "adminSidebar not found"
        );

        return;

    }


    const elements =
        sidebar.querySelectorAll(
            "[data-roles]"
        );


    elements.forEach(
        function (element) {

            const roles =
                String(
                    element.getAttribute(
                        "data-roles"
                    ) || ""
                )
                    .trim()
                    .toUpperCase()
                    .split(/\s+/);


            if (
                roles.includes(role)
            ) {

                element.style.display = "";

            }
            else {

                element.style.display =
                    "none";

            }

        }
    );


    /* =================================================
       CHEF SPECIAL MODE
    ================================================= */

    if (role === "CHEF") {

        console.log(
            "CHEF MODE ENABLED"
        );


        /* ------------------------------------------------
           Hide all normal navigation links
        ------------------------------------------------ */

        const links =
            sidebar.querySelectorAll(
                ".sidebar-link"
            );


        links.forEach(
            function (link) {

                const page =
                    link.getAttribute(
                        "data-page"
                    );


                if (
                    page === "chef" ||
                    page === "profile" ||
                    link.id === "sidebarLogout"
                ) {

                    link.style.display =
                        "flex";

                }
                else {

                    link.style.display =
                        "none";

                }

            }
        );


        /* ------------------------------------------------
           Hide all section titles
        ------------------------------------------------ */

        const sections =
            sidebar.querySelectorAll(
                ".nav-section-title"
            );


        sections.forEach(
            function (section) {

                section.style.display =
                    "none";

            }
        );


        /* ------------------------------------------------
           Remove admin bottom card
        ------------------------------------------------ */

        const bottomCard =
            document.getElementById(
                "sidebarBottomCard"
            );


        if (bottomCard) {

            bottomCard.style.display =
                "none";

        }


        /* ------------------------------------------------
           Add KITCHEN title
        ------------------------------------------------ */

        const nav =
            sidebar.querySelector(
                ".sidebar-nav"
            );


        const kitchenLink =
            sidebar.querySelector(
                '[data-page="chef"]'
            );


        if (
            nav &&
            kitchenLink &&
            !document.getElementById(
                "chefOnlySection"
            )
        ) {

            const title =
                document.createElement(
                    "div"
                );


            title.id =
                "chefOnlySection";


            title.className =
                "nav-section-title";


            title.style.display =
                "block";


            title.textContent =
                "KITCHEN";


            nav.insertBefore(
                title,
                kitchenLink
            );

        }


        /* ------------------------------------------------
           Add ACCOUNT section before logout
        ------------------------------------------------ */

        const logoutLink =
            document.getElementById(
                "sidebarLogout"
            );


        if (
            nav &&
            logoutLink &&
            !document.getElementById(
                "chefAccountSection"
            )
        ) {

            const title =
                document.createElement(
                    "div"
                );


            title.id =
                "chefAccountSection";


            title.className =
                "nav-section-title";


            title.style.display =
                "block";


            title.textContent =
                "ACCOUNT";


            nav.insertBefore(
                title,
                logoutLink
            );

        }

    }


    /* =================================================
       STAFF MODE
    ================================================= */

    if (role === "STAFF") {

        console.log(
            "STAFF MODE ENABLED"
        );


        const bottomCard =
            document.getElementById(
                "sidebarBottomCard"
            );


        if (bottomCard) {

            bottomCard.style.display =
                "none";

        }

    }


    /* =================================================
       ADMIN MODE
    ================================================= */

    if (role === "ADMIN") {

        console.log(
            "ADMIN MODE ENABLED"
        );

    }

}


/* =====================================================
   ACTIVE SIDEBAR
===================================================== */

function setActiveSidebarLink() {

    const sidebar =
        document.getElementById(
            "adminSidebar"
        );


    if (!sidebar) {
        return;
    }


    const currentPath =
        window.location.pathname
            .toLowerCase();


    const links =
        sidebar.querySelectorAll(
            ".sidebar-link[data-page]"
        );


    links.forEach(
        function (link) {

            const page =
                link.getAttribute(
                    "data-page"
                );


            let active = false;


            switch (page) {

                case "dashboard":

                    active =
                        currentPath.endsWith(
                            "dashboard.jsp"
                        );

                    break;


                case "order":

                    active =
                        currentPath.endsWith(
                            "order.jsp"
                        );

                    break;


                case "table":

                    active =
                        currentPath.endsWith(
                            "table.jsp"
                        );

                    break;


                case "menu":

                    active =
                        currentPath.endsWith(
                            "menu.jsp"
                        );

                    break;


                case "category":

                    active =
                        currentPath.endsWith(
                            "category.jsp"
                        );

                    break;


                case "customer":

                    active =
                        currentPath.endsWith(
                            "customer.jsp"
                        );

                    break;


                case "billing":

                    active =
                        currentPath.endsWith(
                            "billing.jsp"
                        );

                    break;


                case "reports":

                    active =
                        currentPath.endsWith(
                            "reports.jsp"
                        ) ||
                        currentPath.endsWith(
                            "report.jsp"
                        );

                    break;


                case "chef":

                    active =
                        currentPath.endsWith(
                            "chef.jsp"
                        ) ||
                        currentPath.endsWith(
                            "kitchen.jsp"
                        );

                    break;


                case "inventory":

                    active =
                        currentPath.endsWith(
                            "inventory.jsp"
                        );

                    break;


                case "staff":

                    active =
                        currentPath.endsWith(
                            "staff.jsp"
                        );

                    break;


                case "settings":

                    active =
                        currentPath.endsWith(
                            "settings.jsp"
                        );

                    break;

            }


            if (active) {

                link.classList.add(
                    "active"
                );

            }
            else {

                link.classList.remove(
                    "active"
                );

            }

        }
    );

}


/* =====================================================
   DATE + TIME
===================================================== */

function updateDateTime() {

    const dateElement =
        document.getElementById(
            "currentDate"
        );


    const dayElement =
        document.getElementById(
            "currentDay"
        );


    if (!dateElement) {
        return;
    }


    const now =
        new Date();


    dateElement.textContent =
        now.toLocaleDateString(
            "en-IN",
            {
                day: "2-digit",
                month: "long",
                year: "numeric"
            }
        );


    if (dayElement) {

        dayElement.textContent =
            now.toLocaleDateString(
                "en-IN",
                {
                    weekday: "long"
                }
            ) +
            " | " +
            now.toLocaleTimeString(
                "en-IN",
                {
                    hour: "2-digit",
                    minute: "2-digit",
                    second: "2-digit",
                    hour12: true
                }
            );

    }

}


/* =====================================================
   LIVE CLOCK
===================================================== */

function startLiveClock() {

    updateDateTime();


    setInterval(
        function () {

            updateDateTime();

        },
        1000
    );

}


/* =====================================================
   LOAD ADMIN / USER
===================================================== */

function loadAdmin() {

    let username =
        localStorage.getItem(
            "username"
        );


    if (!username) {

        username =
            localStorage.getItem(
                "userName"
            );

    }


    const role =
        getCurrentRole();


    const nameElement =
        document.getElementById(
            "adminName"
        );


    const roleElement =
        document.getElementById(
            "adminRole"
        );


    if (
        nameElement &&
        username
    ) {

        nameElement.textContent =
            username;

    }


    if (roleElement) {

        switch (role) {

            case "ADMIN":

                roleElement.textContent =
                    "Administrator";

                break;


            case "STAFF":

                roleElement.textContent =
                    "Staff";

                break;


            case "CHEF":

                roleElement.textContent =
                    "Chef";

                break;


            default:

                roleElement.textContent =
                    "User";

                break;

        }

    }

}


/* =====================================================
   SIDEBAR TOGGLE
===================================================== */

function setupSidebar() {

    const toggle =
        document.getElementById(
            "sidebarToggle"
        );


    const sidebar =
        document.getElementById(
            "adminSidebar"
        );


    const overlay =
        document.getElementById(
            "sidebarOverlay"
        );


    if (
        !toggle ||
        !sidebar
    ) {

        return;
    }


    toggle.addEventListener(
        "click",
        function () {

            sidebar.classList.toggle(
                "open"
            );


            if (overlay) {

                overlay.classList.toggle(
                    "show"
                );

            }

        }
    );


    if (overlay) {

        overlay.addEventListener(
            "click",
            function () {

                sidebar.classList.remove(
                    "open"
                );


                overlay.classList.remove(
                    "show"
                );

            }
        );

    }

}


/* =====================================================
   PROFILE
===================================================== */

function setupProfile() {

    const profile =
        document.getElementById(
            "adminProfile"
        );


    const dropdown =
        document.getElementById(
            "profileDropdown"
        );


    if (
        !profile ||
        !dropdown
    ) {

        return;
    }


    profile.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();


            dropdown.classList.toggle(
                "show"
            );

        }
    );


    dropdown.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();

        }
    );


    document.addEventListener(
        "click",
        function () {

            dropdown.classList.remove(
                "show"
            );

        }
    );

}


/* =====================================================
   LOGOUT
===================================================== */

function setupLogout() {

    const buttons = [

        document.getElementById(
            "sidebarLogout"
        ),

        document.getElementById(
            "headerLogout"
        )

    ];


    buttons.forEach(
        function (button) {

            if (!button) {
                return;
            }


            button.addEventListener(
                "click",
                function (event) {

                    event.preventDefault();


                    console.log(
                        "Logout clicked"
                    );


                    clearLoginData();


                    const meta =
                        document.querySelector(
                            'meta[name="app-context"]'
                        );


                    const contextPath =
                        meta
                            ? meta.getAttribute(
                                "content"
                            ) || ""
                            : "";


                    window.location.href =
                        contextPath +
                        "/login.jsp";

                }
            );

        }
    );

}


/* =====================================================
   CLEAR LOGIN DATA
===================================================== */

function clearLoginData() {
    localStorage.clear();
}


/* =====================================================
   NOTIFICATION DROPDOWN HANDLER
===================================================== */

function setupNotifications() {
    const notifBtn = document.getElementById("notificationBtn");
    const notifDropdown = document.getElementById("notificationDropdown");
    const clearBtn = document.getElementById("clearNotifBtn");
    const notifCount = document.getElementById("notificationCount");

    if (!notifBtn || !notifDropdown) {
        return;
    }

    // Toggle dropdown
    notifBtn.addEventListener("click", function (e) {
        e.stopPropagation();
        const isShown = notifDropdown.classList.contains("show");
        if (isShown) {
            notifDropdown.classList.remove("show");
        } else {
            notifDropdown.classList.add("show");
        }

        // Close profile dropdown if open
        const profileDropdown = document.getElementById("profileDropdown");
        if (profileDropdown) profileDropdown.classList.remove("show");
    });

    notifDropdown.addEventListener("click", function (e) {
        e.stopPropagation();
    });

    // Clear notifications
    if (clearBtn) {
        clearBtn.addEventListener("click", function () {
            const list = document.getElementById("notificationList");
            if (list) {
                list.innerHTML = '<div style="padding: 24px; text-align: center; color: #9ca3af; font-size: 13px;">No unread notifications</div>';
            }
            if (notifCount) {
                notifCount.textContent = "0";
                notifCount.style.display = "none";
            }
        });
    }

    // Close when clicking outside
    document.addEventListener("click", function () {
        if (notifDropdown) notifDropdown.classList.remove("show");
    });
}


/* =====================================================
   GLOBAL SEARCH HANDLER
===================================================== */

function setupGlobalSearch() {
    const searchInput = document.getElementById("globalSearch");
    if (!searchInput) return;

    searchInput.addEventListener("input", function (e) {
        const query = e.target.value.trim().toLowerCase();
        
        // Filter table rows across any active table on page
        const tables = document.querySelectorAll("table tbody");
        tables.forEach(tbody => {
            const rows = tbody.querySelectorAll("tr");
            rows.forEach(row => {
                const text = row.textContent.toLowerCase();
                row.style.display = text.includes(query) ? "" : "none";
            });
        });

        // Filter card items (e.g. food cards, table cards)
        const cards = document.querySelectorAll(".menu-card, .table-card, .kitchen-card, .customer-card, .order-item-card");
        cards.forEach(card => {
            const text = card.textContent.toLowerCase();
            card.style.display = text.includes(query) ? "" : "none";
        });
    });
}


/* =====================================================
   UNIVERSAL TOAST NOTIFICATION SYSTEM
===================================================== */

window.showToast = function (title, message = "", type = "success") {
    // If only one argument is provided
    if (!message && title) {
        if (title.length > 30) {
            message = title;
            title = type === "error" ? "Error" : (type === "warning" ? "Warning" : "Notice");
        }
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


/* =====================================================
   THEME TOGGLE SYSTEM (DARK / LIGHT GLASS)
===================================================== */

function setupThemeToggle() {
    const savedTheme = localStorage.getItem("rms_theme") || "dark";
    applyTheme(savedTheme);

    const toggleBtns = document.querySelectorAll("#themeToggleBtn, .theme-toggle-btn");
    toggleBtns.forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.stopPropagation();
            const current = document.documentElement.getAttribute("data-theme") || "dark";
            const nextTheme = current === "dark" ? "light" : "dark";
            applyTheme(nextTheme);
            localStorage.setItem("rms_theme", nextTheme);
            if (window.showToast) {
                window.showToast("Theme Changed", `Switched to ${nextTheme.toUpperCase()} mode`, "info");
            }
        });
    });
}

function applyTheme(theme) {
    document.documentElement.setAttribute("data-theme", theme);
    document.body.setAttribute("data-theme", theme);

    const icons = document.querySelectorAll("#themeToggleIcon, .theme-toggle-btn i");
    icons.forEach(icon => {
        if (theme === "dark") {
            icon.className = "fa-solid fa-sun";
            icon.style.color = "#fcd34d";
        } else {
            icon.className = "fa-solid fa-moon";
            icon.style.color = "#475569";
        }
    });
}

// Immediate theme execution on script read to prevent flash of wrong theme
(function () {
    const instantTheme = localStorage.getItem("rms_theme") || "dark";
    document.documentElement.setAttribute("data-theme", instantTheme);
})();

/* =====================================================
   FINAL LOG
===================================================== */

console.log(
    "Sankalp Common JS Ready"
);