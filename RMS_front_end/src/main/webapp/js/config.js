/**
 * =========================================================
 * SANKALP RESTAURANT MANAGEMENT SYSTEM
 * GLOBAL CONFIGURATION (config.js)
 * =========================================================
 * Centralized API Base URL & Environment Config.
 * Change `API_BASE_URL` here to re-point all frontend API requests.
 */

(function (window) {
    "use strict";

    const origin = window.location.origin;
    const hostname = window.location.hostname;
    const port = window.location.port;

    // Detect Environment & Set Default Backend API URL
    let defaultApiBaseUrl = "";

    if (hostname === "localhost" || hostname === "127.0.0.1") {
        if (port === "8081" || port === "8080") {
            // Check if Tomcat webapp /api is available on current origin, otherwise fallback to http://localhost:8080
            defaultApiBaseUrl = origin + "/api";
        } else {
            // Local Tomcat Environment (API deployed as api.war on same Tomcat server)
            defaultApiBaseUrl = origin + "/api";
        }
    } else {
        // Production Server (cPanel / Apache Tomcat Deployment)
        // If API is deployed as api.war -> https://yourdomain.com/api
        // If API is deployed as RMS_backend.war -> https://yourdomain.com/RMS_backend
        if (window.location.pathname.startsWith("/RMS_frontend")) {
            defaultApiBaseUrl = origin + "/RMS_backend";
        } else {
            defaultApiBaseUrl = origin + "/api";
        }
    }

    const CONFIG = {
        // Base API URL for all REST API endpoints
        API_BASE_URL: window.CUSTOM_API_BASE_URL || defaultApiBaseUrl,
        APP_NAME: "Sankalp Restaurant Management System",
        VERSION: "1.0.0"
    };

    // Expose globally on window object
    window.CONFIG = CONFIG;
    window.API_BASE_URL = CONFIG.API_BASE_URL;
    window.BACKEND_BASE_URL = CONFIG.API_BASE_URL;

    /**
     * Helper to safely construct full API URLs without duplicate or missing '/api' path segments.
     * Works across Docker, standalone Tomcat (cPanel/Mochahost), and local dev.
     */
    window.getApiUrl = function (path) {
        if (!path) return window.API_BASE_URL;
        const base = window.API_BASE_URL || defaultApiBaseUrl || "/api";
        const cleanBase = base.endsWith("/") ? base.slice(0, -1) : base;
        const cleanPath = path.startsWith("/") ? path : "/" + path;

        // If base ends with /api and path starts with /api/, strip one /api
        if (cleanBase.endsWith("/api") && cleanPath.startsWith("/api/")) {
            return cleanBase + cleanPath.substring(4);
        }
        // If base does not end with /api or /RMS_backend and path doesn't start with /api/, add /api
        if (!cleanBase.endsWith("/api") && !cleanBase.endsWith("/RMS_backend") && !cleanPath.startsWith("/api/")) {
            return cleanBase + "/api" + cleanPath;
        }
        return cleanBase + cleanPath;
    };

})(window);
