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
        if (port === "8081") {
            // Docker Environment (Frontend on 8081, Backend Spring Boot on 8080)
            defaultApiBaseUrl = "http://localhost:8080";
        } else {
            // Local Tomcat Environment
            defaultApiBaseUrl = "http://localhost:8080";
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

})(window);
