/**
 * =========================================================
 * SANKALP RESTAURANT MANAGEMENT SYSTEM - SETTINGS JS
 * =========================================================
 */

const BACKEND_URL = window.location.origin.includes(":8081")
    ? "http://localhost:8080"
    : (window.location.origin + "/RMS_backend");
const SETTINGS_API = "/api/settings";

const DEFAULT_SETTINGS = {
    restaurantName: "Sankalp Restaurant",
    tagline: "Authentic Flavors & Fine Dining Experience",
    phone: "+91 98765 43210",
    email: "contact@sankalprestaurant.com",
    website: "https://www.sankalprestaurant.com",
    currencySymbol: "₹",
    address: "Opp. City Mall, Main Ring Road, Ahmedabad, Gujarat 380015",
    openingTime: "10:00",
    closingTime: "23:00",

    gstRate: 5.0,
    serviceChargeRate: 0.0,
    gstinNumber: "24ABCDE1234F1Z5",
    fssaiNumber: "10019021004567",
    invoicePrefix: "INV-",
    defaultDiscount: 20.0,
    billFooterNote: "Thank you for dining with Sankalp! Have a wonderful day.",
    enableGst: true,

    prepTime: 20,
    stockThreshold: 5,
    kitchenSound: true,
    autoReleaseTable: true,
    autoAcceptOrders: false,

    themeColor: "gold",
    tableIndicators: true,
    dailySalesEmail: true,
    lowStockEmail: true,
    logoPath: "/images/logo1.png"
};

document.addEventListener("DOMContentLoaded", function () {
    console.log("Sankalp Settings JS Initialized");
    initSettingsTabs();
    initLogoOptions();
    loadAllSettings();
    initSettingsForms();
    initSystemActions();
});

/* =========================================================
   TAB SWITCHING
========================================================= */

function initSettingsTabs() {
    const tabButtons = document.querySelectorAll(".settings-tab-btn");
    const panes = document.querySelectorAll(".settings-pane");

    tabButtons.forEach(button => {
        button.addEventListener("click", function () {
            const targetTab = this.getAttribute("data-tab");

            tabButtons.forEach(btn => btn.classList.remove("active"));
            panes.forEach(pane => pane.classList.remove("active"));

            this.classList.add("active");
            const activePane = document.getElementById(targetTab);
            if (activePane) {
                activePane.classList.add("active");
            }
        });
    });
}

/* =========================================================
   LOGO SWITCHER & FILE UPLOADER
========================================================= */

function initLogoOptions() {
    const logoOptions = document.querySelectorAll(".logo-option");
    const logoPreview = document.getElementById("settingsLogoPreview");
    const logoPathInput = document.getElementById("logoPath");
    const sidebarLogo = document.getElementById("sidebarLogoImg");
    const fileInput = document.getElementById("logoFileInput");
    const uploadBtn = document.getElementById("triggerLogoUploadBtn");
    const loader = document.getElementById("logoLoadingOverlay");

    // File Upload Trigger
    if (uploadBtn && fileInput) {
        uploadBtn.addEventListener("click", function () {
            fileInput.click();
        });

        fileInput.addEventListener("change", async function () {
            const file = this.files && this.files[0];
            if (!file) return;

            // Instant Preview via FileReader
            const reader = new FileReader();
            reader.onload = function (e) {
                const previewSrc = e.target.result;
                if (logoPreview) logoPreview.src = previewSrc;
                if (sidebarLogo) sidebarLogo.src = previewSrc;
            };
            reader.readAsDataURL(file);

            // Upload to Backend
            if (loader) loader.style.display = "flex";
            if (uploadBtn) {
                uploadBtn.disabled = true;
                uploadBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Uploading...';
            }

            try {
                const formData = new FormData();
                formData.append("file", file);

                const token = localStorage.getItem("token");
                const headers = {};
                if (token) headers["Authorization"] = "Bearer " + token;

                const response = await fetch(SETTINGS_API + "/upload-logo", {
                    method: "POST",
                    headers: headers,
                    body: formData
                });

                const result = await response.json();

                if (response.ok && result.logoUrl) {
                    const fullUrl = BACKEND_URL + result.logoUrl;
                    if (logoPathInput) logoPathInput.value = fullUrl;

                    // Uncheck preset radios
                    const logoRadios = document.querySelectorAll("input[name='logoChoice']");
                    logoRadios.forEach(r => {
                        r.checked = false;
                        r.closest(".logo-option")?.classList.remove("active");
                    });

                    // Update stored settings
                    const stored = JSON.parse(localStorage.getItem("sankalp_restaurant_settings") || "{}");
                    stored.logoPath = fullUrl;
                    localStorage.setItem("sankalp_restaurant_settings", JSON.stringify(stored));

                    showSettingsToast("Logo uploaded and saved successfully!", "success");
                } else {
                    throw new Error(result.message || "Upload failed");
                }
            } catch (err) {
                console.warn("Backend logo upload error:", err);
                showSettingsToast("Logo previewed locally.", "success");
            } finally {
                if (loader) loader.style.display = "none";
                if (uploadBtn) {
                    uploadBtn.disabled = false;
                    uploadBtn.innerHTML = '<i class="fa-solid fa-cloud-arrow-up"></i> Upload Logo from Computer';
                }
            }
        });
    }

    // Preset Options Switcher
    logoOptions.forEach(option => {
        option.addEventListener("click", function () {
            logoOptions.forEach(opt => opt.classList.remove("active"));
            this.classList.add("active");

            const radio = this.querySelector("input[type='radio']");
            if (radio) radio.checked = true;

            const imgSrc = this.getAttribute("data-src");
            const imgVal = radio ? radio.value : "/images/logo1.png";

            if (logoPreview && imgSrc) {
                logoPreview.src = imgSrc;
            }
            if (logoPathInput) {
                logoPathInput.value = imgVal;
            }
            if (sidebarLogo && imgSrc) {
                sidebarLogo.src = imgSrc;
            }
        });
    });
}

function applyLogoToUI(logoPath) {
    if (!logoPath) return;

    const logoPreview = document.getElementById("settingsLogoPreview");
    const sidebarLogo = document.getElementById("sidebarLogoImg");
    const logoPathInput = document.getElementById("logoPath");

    let fullSrc = logoPath.trim();
    if (!fullSrc.startsWith("http") && !fullSrc.startsWith("data:")) {
        if (fullSrc.startsWith("uploads/")) fullSrc = "/api/" + fullSrc;
        else if (fullSrc.startsWith("/uploads/")) fullSrc = "/api" + fullSrc;
        else if (!fullSrc.startsWith("/")) fullSrc = "/" + fullSrc;
    }

    if (logoPreview) logoPreview.src = fullSrc;
    if (sidebarLogo) sidebarLogo.src = fullSrc;
    if (logoPathInput) logoPathInput.value = logoPath;

    const logoRadios = document.querySelectorAll("input[name='logoChoice']");
    let matched = false;
    logoRadios.forEach(radio => {
        if (radio.value === logoPath) {
            radio.checked = true;
            radio.closest(".logo-option")?.classList.add("active");
            matched = true;
        } else {
            radio.closest(".logo-option")?.classList.remove("active");
        }
    });

    if (!matched) {
        logoRadios.forEach(radio => {
            radio.checked = false;
            radio.closest(".logo-option")?.classList.remove("active");
        });
    }
}

/* =========================================================
   LOAD SETTINGS (BACKEND + LOCAL FALLBACK)
========================================================= */

async function loadAllSettings() {
    let settings = Object.assign({}, DEFAULT_SETTINGS);

    try {
        const token = localStorage.getItem("token");
        const headers = { "Content-Type": "application/json" };
        if (token) headers["Authorization"] = "Bearer " + token;

        const response = await fetch(SETTINGS_API, {
            method: "GET",
            headers: headers
        });

        if (response.ok) {
            const data = await response.json();
            if (data && typeof data === "object") {
                settings = Object.assign({}, DEFAULT_SETTINGS, data);
                localStorage.setItem("sankalp_restaurant_settings", JSON.stringify(settings));
                console.log("Settings successfully loaded from Backend API");
            }
        } else {
            throw new Error("Backend settings endpoint returned " + response.status);
        }
    } catch (err) {
        console.warn("Backend unavailable, loading cached settings:", err);
        try {
            const stored = localStorage.getItem("sankalp_restaurant_settings");
            if (stored) {
                settings = Object.assign({}, DEFAULT_SETTINGS, JSON.parse(stored));
            }
        } catch (e) {
            console.error("Cache read error:", e);
        }
    }

    populateFormWithSettings(settings);
}

function populateFormWithSettings(settings) {
    // 1. General Tab
    setInputValue("restaurantName", settings.restaurantName || settings.name);
    setInputValue("restaurantTagline", settings.tagline || settings.restaurantTagline);
    setInputValue("restaurantPhone", settings.phone || settings.restaurantPhone);
    setInputValue("restaurantEmail", settings.email || settings.restaurantEmail);
    setInputValue("restaurantWebsite", settings.website || settings.restaurantWebsite);
    setInputValue("currencySymbol", settings.currencySymbol || "₹");
    setInputValue("restaurantAddress", settings.address || settings.restaurantAddress);
    setInputValue("openingTime", settings.openingTime || "10:00");
    setInputValue("closingTime", settings.closingTime || "23:00");

    // Logo
    applyLogoToUI(settings.logoPath || "/images/logo1.png");

    // 2. Billing Tab
    setInputValue("gstRate", settings.gstRate ?? 5.0);
    setInputValue("serviceChargeRate", settings.serviceChargeRate ?? 0.0);
    setInputValue("gstinNumber", settings.gstinNumber || "");
    setInputValue("fssaiNumber", settings.fssaiNumber || "");
    setInputValue("invoicePrefix", settings.invoicePrefix || "INV-");
    setInputValue("defaultDiscount", settings.defaultDiscount ?? 20);
    setInputValue("billFooterNote", settings.billFooterNote || "");
    setCheckedValue("enableGstToggle", settings.enableGst !== false);

    // 3. Kitchen Tab
    setInputValue("prepTime", settings.prepTime ?? 20);
    setInputValue("stockThreshold", settings.stockThreshold ?? 5);
    setCheckedValue("kitchenSoundToggle", settings.kitchenSound !== false);
    setCheckedValue("autoReleaseTableToggle", settings.autoReleaseTable !== false);
    setCheckedValue("autoAcceptOrdersToggle", Boolean(settings.autoAcceptOrders));

    // 4. Appearance Tab
    const themeRadios = document.querySelectorAll("input[name='themeColor']");
    themeRadios.forEach(radio => {
        if (radio.value === (settings.themeColor || "gold")) {
            radio.checked = true;
            radio.closest(".theme-option")?.classList.add("active");
        } else {
            radio.closest(".theme-option")?.classList.remove("active");
        }
    });

    setCheckedValue("tableIndicatorsToggle", settings.tableIndicators !== false);
    setCheckedValue("dailySalesEmailToggle", settings.dailySalesEmail !== false);
    setCheckedValue("lowStockEmailToggle", settings.lowStockEmail !== false);
}

/* =========================================================
   COLLECT & SAVE SETTINGS
========================================================= */

function collectCurrentSettings() {
    const themeRadio = document.querySelector("input[name='themeColor']:checked");
    const logoRadio = document.querySelector("input[name='logoChoice']:checked");
    const logoPath = logoRadio ? logoRadio.value : getInputValue("logoPath") || "/images/logo1.png";

    return {
        id: 1,
        restaurantName: getInputValue("restaurantName") || "Sankalp Restaurant",
        tagline: getInputValue("restaurantTagline"),
        phone: getInputValue("restaurantPhone"),
        email: getInputValue("restaurantEmail"),
        website: getInputValue("restaurantWebsite"),
        currencySymbol: getInputValue("currencySymbol") || "₹",
        address: getInputValue("restaurantAddress"),
        openingTime: getInputValue("openingTime") || "10:00",
        closingTime: getInputValue("closingTime") || "23:00",

        gstRate: parseFloat(getInputValue("gstRate")) || 0.0,
        serviceChargeRate: parseFloat(getInputValue("serviceChargeRate")) || 0.0,
        gstinNumber: getInputValue("gstinNumber"),
        fssaiNumber: getInputValue("fssaiNumber"),
        invoicePrefix: getInputValue("invoicePrefix") || "INV-",
        defaultDiscount: parseFloat(getInputValue("defaultDiscount")) || 0.0,
        billFooterNote: getInputValue("billFooterNote"),
        enableGst: getCheckedValue("enableGstToggle"),

        prepTime: parseInt(getInputValue("prepTime")) || 20,
        stockThreshold: parseInt(getInputValue("stockThreshold")) || 5,
        kitchenSound: getCheckedValue("kitchenSoundToggle"),
        autoReleaseTable: getCheckedValue("autoReleaseTableToggle"),
        autoAcceptOrders: getCheckedValue("autoAcceptOrdersToggle"),

        themeColor: themeRadio ? themeRadio.value : "gold",
        tableIndicators: getCheckedValue("tableIndicatorsToggle"),
        dailySalesEmail: getCheckedValue("dailySalesEmailToggle"),
        lowStockEmail: getCheckedValue("lowStockEmailToggle"),
        logoPath: logoPath
    };
}

async function saveSettings(message = "Settings saved successfully!") {
    const settings = collectCurrentSettings();

    // Cache locally immediately
    localStorage.setItem("sankalp_restaurant_settings", JSON.stringify(settings));
    applyLogoToUI(settings.logoPath);

    try {
        const token = localStorage.getItem("token");
        const headers = { "Content-Type": "application/json" };
        if (token) headers["Authorization"] = "Bearer " + token;

        const response = await fetch(SETTINGS_API, {
            method: "PUT",
            headers: headers,
            body: JSON.stringify(settings)
        });

        if (response.ok) {
            showSettingsToast(message, "success");
        } else {
            console.warn("Backend save responded with:", response.status);
            showSettingsToast("Settings saved locally.", "success");
        }
    } catch (e) {
        console.warn("Backend offline, saved locally:", e);
        showSettingsToast("Settings saved locally.", "success");
    }
}

/* =========================================================
   FORM SUBMISSIONS & LISTENERS
========================================================= */

function initSettingsForms() {
    const forms = [
        "generalSettingsForm",
        "billingSettingsForm",
        "kitchenSettingsForm",
        "appearanceSettingsForm"
    ];

    forms.forEach(formId => {
        const form = document.getElementById(formId);
        if (form) {
            form.addEventListener("submit", function (e) {
                e.preventDefault();
                saveSettings("Section settings saved successfully!");
            });
        }
    });

    const saveAllBtn = document.getElementById("saveAllSettingsBtn");
    if (saveAllBtn) {
        saveAllBtn.addEventListener("click", function () {
            saveSettings("All configuration settings saved successfully!");
        });
    }

    const resetBtn = document.getElementById("resetDefaultsBtn");
    if (resetBtn) {
        resetBtn.addEventListener("click", async function () {
            if (confirm("Are you sure you want to reset all settings to default values?")) {
                localStorage.removeItem("sankalp_restaurant_settings");
                try {
                    const token = localStorage.getItem("token");
                    const headers = { "Content-Type": "application/json" };
                    if (token) headers["Authorization"] = "Bearer " + token;

                    await fetch(SETTINGS_API + "/reset", {
                        method: "POST",
                        headers: headers
                    });
                } catch (e) {
                    console.warn("Backend reset error:", e);
                }
                loadAllSettings();
                showSettingsToast("All settings reset to default values.", "success");
            }
        });
    }

    const themeOptions = document.querySelectorAll(".theme-option");
    themeOptions.forEach(option => {
        option.addEventListener("click", function () {
            themeOptions.forEach(opt => opt.classList.remove("active"));
            this.classList.add("active");
            const radio = this.querySelector("input[type='radio']");
            if (radio) radio.checked = true;
        });
    });
}

/* =========================================================
   SYSTEM ACTIONS
========================================================= */

function initSystemActions() {
    const exportBtn = document.getElementById("exportBackupBtn");
    if (exportBtn) {
        exportBtn.addEventListener("click", function () {
            const currentData = {
                settings: collectCurrentSettings(),
                exportDate: new Date().toISOString(),
                version: "1.0.0",
                system: "Sankalp Restaurant Management System"
            };

            const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(currentData, null, 2));
            const downloadAnchor = document.createElement("a");
            downloadAnchor.setAttribute("href", dataStr);
            downloadAnchor.setAttribute("download", "sankalp_rms_backup_" + new Date().toISOString().slice(0, 10) + ".json");
            document.body.appendChild(downloadAnchor);
            downloadAnchor.click();
            downloadAnchor.remove();

            showSettingsToast("Configuration backup exported successfully.", "success");
        });
    }

    const clearCacheBtn = document.getElementById("clearCacheBtn");
    if (clearCacheBtn) {
        clearCacheBtn.addEventListener("click", function () {
            if (confirm("Clear local frontend temporary cache and reload data?")) {
                sessionStorage.clear();
                showSettingsToast("Frontend cache cleared. Refreshing...", "success");
                setTimeout(() => window.location.reload(), 1000);
            }
        });
    }
}

/* =========================================================
   HELPERS
========================================================= */

function getInputValue(id) {
    const el = document.getElementById(id);
    return el ? el.value.trim() : "";
}

function setInputValue(id, val) {
    const el = document.getElementById(id);
    if (el && val !== undefined && val !== null) {
        el.value = val;
    }
}

function getCheckedValue(id) {
    const el = document.getElementById(id);
    return el ? el.checked : false;
}

function setCheckedValue(id, checked) {
    const el = document.getElementById(id);
    if (el) {
        el.checked = Boolean(checked);
    }
}

function showSettingsToast(msg, type = "success") {
    const toast = document.getElementById("settingsToast");
    const text = document.getElementById("settingsToastText");

    if (!toast) return;

    if (text) text.innerText = msg;

    toast.classList.remove("error", "show");
    if (type === "error") toast.classList.add("error");
    toast.classList.add("show");

    setTimeout(() => {
        toast.classList.remove("show");
    }, 3500);
}
