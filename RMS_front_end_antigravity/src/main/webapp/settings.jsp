<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
%>

<!-- =====================================================
     COMMON HEADER
====================================================== -->
<%@ include file="includes/header.jsp" %>

<!-- =====================================================
     COMMON SIDEBAR
====================================================== -->
<%@ include file="includes/sidebar.jsp" %>

<!-- =====================================================
     MAIN CONTENT
====================================================== -->
<div class="main-content">

    <main class="settings-page">

        <div class="settings-container">

            <!-- =================================================
                 PAGE HEADER
            ================================================== -->
            <div class="settings-page-header">
                <div class="settings-title-area">
                    <h1>
                        <i class="fa-solid fa-gear"></i>
                        System & Restaurant Settings
                    </h1>
                    <div class="settings-breadcrumb">
                        <span>Dashboard</span>
                        <i class="fa-solid fa-chevron-right"></i>
                        <span class="active">Settings</span>
                    </div>
                </div>

                <div class="settings-header-actions">
                    <button type="button" class="btn-settings-secondary" id="resetDefaultsBtn">
                        <i class="fa-solid fa-arrow-rotate-left"></i>
                        Reset Defaults
                    </button>
                    <button type="button" class="btn-settings-primary" id="saveAllSettingsBtn">
                        <i class="fa-solid fa-floppy-disk"></i>
                        Save All Changes
                    </button>
                </div>
            </div>

            <!-- =================================================
                 SETTINGS WRAPPER (NAV TABS + CONTENT PANES)
            ================================================== -->
            <div class="settings-grid">

                <!-- =============================================
                     SETTINGS SIDEBAR / NAV TABS
                ============================================== -->
                <aside class="settings-nav-card">
                    <div class="settings-nav-header">
                        <h3>Configuration</h3>
                        <span>Configure your restaurant workflow</span>
                    </div>

                    <nav class="settings-tabs" role="tablist">
                        <button type="button" class="settings-tab-btn active" data-tab="generalTab">
                            <div class="tab-icon">
                                <i class="fa-solid fa-store"></i>
                            </div>
                            <div class="tab-info">
                                <strong>General Details</strong>
                                <span>Restaurant name, contact & address</span>
                            </div>
                        </button>

                        <button type="button" class="settings-tab-btn" data-tab="billingTab">
                            <div class="tab-icon">
                                <i class="fa-solid fa-file-invoice-dollar"></i>
                            </div>
                            <div class="tab-info">
                                <strong>Taxes & Billing</strong>
                                <span>GST rates, service charge & receipts</span>
                            </div>
                        </button>

                        <button type="button" class="settings-tab-btn" data-tab="kitchenTab">
                            <div class="tab-icon">
                                <i class="fa-solid fa-kitchen-set"></i>
                            </div>
                            <div class="tab-info">
                                <strong>Kitchen & Orders</strong>
                                <span>Auto-acceptance, sounds & alerts</span>
                            </div>
                        </button>

                        <button type="button" class="settings-tab-btn" data-tab="appearanceTab">
                            <div class="tab-icon">
                                <i class="fa-solid fa-palette"></i>
                            </div>
                            <div class="tab-info">
                                <strong>Appearance & Alerts</strong>
                                <span>Theme styling & notifications</span>
                            </div>
                        </button>

                        <button type="button" class="settings-tab-btn" data-tab="systemTab">
                            <div class="tab-icon">
                                <i class="fa-solid fa-server"></i>
                            </div>
                            <div class="tab-info">
                                <strong>System & Diagnostics</strong>
                                <span>Data backup, cache & server status</span>
                            </div>
                        </button>
                    </nav>

                    <!-- LIVE SYSTEM STATUS CARD -->
                    <div class="settings-system-pill">
                        <div class="system-status-indicator online"></div>
                        <div class="system-status-text">
                            <strong>Backend API: Online</strong>
                            <span>Spring Boot & MySQL Connected</span>
                        </div>
                    </div>
                </aside>

                <!-- =============================================
                     SETTINGS TAB PANES
                ============================================== -->
                <section class="settings-content-card">

                    <!-- =========================================
                         1. GENERAL SETTINGS PANE
                    ========================================== -->
                    <div class="settings-pane active" id="generalTab">
                        <div class="pane-header">
                            <div class="pane-title">
                                <h2><i class="fa-solid fa-store"></i> General Restaurant Information</h2>
                                <p>Basic contact and profile details used on invoices, receipts, and order confirmations.</p>
                            </div>
                            <span class="pane-badge">Core Profile</span>
                        </div>

                        <form id="generalSettingsForm" class="settings-form">
                            <!-- LOGO BRANDING SECTION -->
                            <div class="settings-logo-section">
                                <div class="logo-preview-box">
                                    <img id="settingsLogoPreview" src="<%= contextPath %>/images/logo1.png" alt="Restaurant Logo">
                                    <div class="logo-loading-overlay" id="logoLoadingOverlay" style="display: none;">
                                        <i class="fa-solid fa-spinner fa-spin"></i>
                                    </div>
                                </div>
                                <div class="logo-controls">
                                    <label><strong>Restaurant Brand Logo</strong></label>
                                    <p>Upload a custom logo image (PNG, JPG, SVG, WebP) or choose an official preset badge.</p>
                                    
                                    <div class="logo-upload-actions">
                                        <input type="file" id="logoFileInput" accept="image/png, image/jpeg, image/jpg, image/webp, image/svg+xml" style="display: none;">
                                        <button type="button" class="btn-upload-logo" id="triggerLogoUploadBtn">
                                            <i class="fa-solid fa-cloud-arrow-up"></i>
                                            Upload Logo from Computer
                                        </button>
                                        <span class="upload-hint">Recommended: 250x250 transparent PNG</span>
                                    </div>

                                    <div class="logo-presets-title">Or select a default preset emblem:</div>
                                    <div class="logo-options">
                                        <label class="logo-option active" data-src="<%= contextPath %>/images/logo1.png">
                                            <input type="radio" name="logoChoice" value="/images/logo1.png" checked>
                                            <span>Gold Seal Emblem (logo1.png)</span>
                                        </label>
                                        <label class="logo-option" data-src="<%= contextPath %>/images/logo.png">
                                            <input type="radio" name="logoChoice" value="/images/logo.png">
                                            <span>Sankalp Royal Badge (logo.png)</span>
                                        </label>
                                        <label class="logo-option" data-src="<%= contextPath %>/images/sankalp_logo.jpg">
                                            <input type="radio" name="logoChoice" value="/images/sankalp_logo.jpg">
                                            <span>Full Culinary Logo (sankalp_logo.jpg)</span>
                                        </label>
                                    </div>
                                    <input type="hidden" id="logoPath" value="/images/logo1.png">
                                </div>
                            </div>

                            <div class="form-row-2">
                                <div class="settings-form-group">
                                    <label for="restaurantName">Restaurant Name <span>*</span></label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-utensils"></i>
                                        <input type="text" id="restaurantName" required placeholder="e.g. Sankalp Restaurant">
                                    </div>
                                </div>

                                <div class="settings-form-group">
                                    <label for="restaurantTagline">Tagline / Slogan</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-quote-left"></i>
                                        <input type="text" id="restaurantTagline" placeholder="e.g. Authentic Flavors & Fine Dining">
                                    </div>
                                </div>
                            </div>

                            <div class="form-row-2">
                                <div class="settings-form-group">
                                    <label for="restaurantPhone">Official Phone Number <span>*</span></label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-phone"></i>
                                        <input type="tel" id="restaurantPhone" required placeholder="+91 98765 43210">
                                    </div>
                                </div>

                                <div class="settings-form-group">
                                    <label for="restaurantEmail">Contact Email <span>*</span></label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-envelope"></i>
                                        <input type="email" id="restaurantEmail" required placeholder="contact@sankalprestaurant.com">
                                    </div>
                                </div>
                            </div>

                            <div class="form-row-2">
                                <div class="settings-form-group">
                                    <label for="restaurantWebsite">Website URL</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-globe"></i>
                                        <input type="url" id="restaurantWebsite" placeholder="https://www.sankalprestaurant.com">
                                    </div>
                                </div>

                                <div class="settings-form-group">
                                    <label for="currencySymbol">Currency Symbol</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-indian-rupee-sign"></i>
                                        <select id="currencySymbol">
                                            <option value="₹">₹ (INR - Indian Rupee)</option>
                                            <option value="$">$ (USD - US Dollar)</option>
                                            <option value="€">€ (EUR - Euro)</option>
                                            <option value="£">£ (GBP - British Pound)</option>
                                            <option value="AED">AED (UAE Dirham)</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="settings-form-group">
                                <label for="restaurantAddress">Full Address & Landmark <span>*</span></label>
                                <textarea id="restaurantAddress" rows="3" required placeholder="Complete physical address printed on food bills..."></textarea>
                            </div>

                            <div class="form-row-2">
                                <div class="settings-form-group">
                                    <label for="openingTime">Daily Opening Time</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-clock"></i>
                                        <input type="time" id="openingTime" value="10:00">
                                    </div>
                                </div>

                                <div class="settings-form-group">
                                    <label for="closingTime">Daily Closing Time</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-moon"></i>
                                        <input type="time" id="closingTime" value="23:00">
                                    </div>
                                </div>
                            </div>

                            <div class="pane-footer">
                                <button type="submit" class="btn-save-tab">
                                    <i class="fa-solid fa-check"></i>
                                    Save General Info
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- =========================================
                         2. TAXES & BILLING PANE
                    ========================================== -->
                    <div class="settings-pane" id="billingTab">
                        <div class="pane-header">
                            <div class="pane-title">
                                <h2><i class="fa-solid fa-file-invoice-dollar"></i> Taxes, Charges & Invoice Rules</h2>
                                <p>Set up GST rates, invoice prefixes, discounts, and print headers.</p>
                            </div>
                            <span class="pane-badge">Financial</span>
                        </div>

                        <form id="billingSettingsForm" class="settings-form">
                            <div class="form-row-2">
                                <div class="settings-form-group">
                                    <label for="gstRate">GST / Tax Percentage (%) <span>*</span></label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-percent"></i>
                                        <input type="number" id="gstRate" step="0.1" min="0" max="100" value="5.0" required>
                                    </div>
                                    <small class="helper-text">Applied automatically to subtotal during order and bill calculation.</small>
                                </div>

                                <div class="settings-form-group">
                                    <label for="serviceChargeRate">Service Charge (%)</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-receipt"></i>
                                        <input type="number" id="serviceChargeRate" step="0.1" min="0" max="50" value="0.0">
                                    </div>
                                    <small class="helper-text">Optional dine-in service fee percentage.</small>
                                </div>
                            </div>

                            <div class="form-row-2">
                                <div class="settings-form-group">
                                    <label for="gstinNumber">GSTIN / Tax ID Number</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-id-card"></i>
                                        <input type="text" id="gstinNumber" placeholder="e.g. 24ABCDE1234F1Z5">
                                    </div>
                                </div>

                                <div class="settings-form-group">
                                    <label for="fssaiNumber">FSSAI License Number</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-certificate"></i>
                                        <input type="text" id="fssaiNumber" placeholder="e.g. 10019021004567">
                                    </div>
                                </div>
                            </div>

                            <div class="form-row-2">
                                <div class="settings-form-group">
                                    <label for="invoicePrefix">Invoice Bill Prefix</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-hashtag"></i>
                                        <input type="text" id="invoicePrefix" value="INV-">
                                    </div>
                                </div>

                                <div class="settings-form-group">
                                    <label for="defaultDiscount">Maximum Allowed Cash Discount (%)</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-tag"></i>
                                        <input type="number" id="defaultDiscount" min="0" max="100" value="20">
                                    </div>
                                </div>
                            </div>

                            <div class="settings-form-group">
                                <label for="billFooterNote">Receipt Bill Footer Message</label>
                                <textarea id="billFooterNote" rows="2" placeholder="Thank you for dining with Sankalp! Have a wonderful day."></textarea>
                            </div>

                            <div class="settings-toggle-group">
                                <div class="toggle-info">
                                    <strong>Enable GST on All Dine-in Orders</strong>
                                    <span>Automatically compute 5% GST on order checkout</span>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox" id="enableGstToggle" checked>
                                    <span class="toggle-slider"></span>
                                </label>
                            </div>

                            <div class="pane-footer">
                                <button type="submit" class="btn-save-tab">
                                    <i class="fa-solid fa-check"></i>
                                    Save Billing Rules
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- =========================================
                         3. KITCHEN & WORKFLOW PANE
                    ========================================== -->
                    <div class="settings-pane" id="kitchenTab">
                        <div class="pane-header">
                            <div class="pane-title">
                                <h2><i class="fa-solid fa-kitchen-set"></i> Kitchen & Order Workflow</h2>
                                <p>Control kitchen audio alerts, order prep times, and inventory threshold triggers.</p>
                            </div>
                            <span class="pane-badge">Operations</span>
                        </div>

                        <form id="kitchenSettingsForm" class="settings-form">
                            <div class="form-row-2">
                                <div class="settings-form-group">
                                    <label for="prepTime">Standard Preparation Time (Minutes)</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-stopwatch"></i>
                                        <input type="number" id="prepTime" min="5" max="120" value="20">
                                    </div>
                                </div>

                                <div class="settings-form-group">
                                    <label for="stockThreshold">Low Stock Alert Threshold (Units)</label>
                                    <div class="input-with-icon">
                                        <i class="fa-solid fa-triangle-exclamation"></i>
                                        <input type="number" id="stockThreshold" min="1" max="100" value="5">
                                    </div>
                                </div>
                            </div>

                            <div class="settings-toggle-group">
                                <div class="toggle-info">
                                    <strong>Kitchen Audio Sound Notifications</strong>
                                    <span>Play a sound chime in the Chef dashboard when a new order is received</span>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox" id="kitchenSoundToggle" checked>
                                    <span class="toggle-slider"></span>
                                </label>
                            </div>

                            <div class="settings-toggle-group">
                                <div class="toggle-info">
                                    <strong>Auto-Release Table Status on Bill Payment</strong>
                                    <span>Switch table status from OCCUPIED back to AVAILABLE immediately upon bill settlement</span>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox" id="autoReleaseTableToggle" checked>
                                    <span class="toggle-slider"></span>
                                </label>
                            </div>

                            <div class="settings-toggle-group">
                                <div class="toggle-info">
                                    <strong>Auto-Accept New Dine-in Orders</strong>
                                    <span>Directly transition pending dine-in orders to PREPARING status without manual accept click</span>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox" id="autoAcceptOrdersToggle">
                                    <span class="toggle-slider"></span>
                                </label>
                            </div>

                            <div class="pane-footer">
                                <button type="submit" class="btn-save-tab">
                                    <i class="fa-solid fa-check"></i>
                                    Save Kitchen Workflow
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- =========================================
                         4. APPEARANCE & NOTIFICATIONS PANE
                    ========================================== -->
                    <div class="settings-pane" id="appearanceTab">
                        <div class="pane-header">
                            <div class="pane-title">
                                <h2><i class="fa-solid fa-palette"></i> Appearance & Notification Preferences</h2>
                                <p>Customize the visual accent, UI density, and email dispatch options.</p>
                            </div>
                            <span class="pane-badge">Interface</span>
                        </div>

                        <form id="appearanceSettingsForm" class="settings-form">
                            <div class="settings-form-group">
                                <label>Accent Gold Theme Intensity</label>
                                <div class="theme-picker">
                                    <label class="theme-option active">
                                        <input type="radio" name="themeColor" value="gold" checked>
                                        <span class="theme-swatch gold"></span>
                                        <strong>Sankalp Royal Gold (Default)</strong>
                                    </label>
                                    <label class="theme-option">
                                        <input type="radio" name="themeColor" value="amber">
                                        <span class="theme-swatch amber"></span>
                                        <strong>Warm Amber</strong>
                                    </label>
                                    <label class="theme-option">
                                        <input type="radio" name="themeColor" value="emerald">
                                        <span class="theme-swatch emerald"></span>
                                        <strong>Fine Dine Emerald</strong>
                                    </label>
                                </div>
                            </div>

                            <div class="settings-toggle-group">
                                <div class="toggle-info">
                                    <strong>Show Real-time Table Indicators</strong>
                                    <span>Display live color-coded status badges across the restaurant table grid</span>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox" id="tableIndicatorsToggle" checked>
                                    <span class="toggle-slider"></span>
                                </label>
                            </div>

                            <div class="settings-toggle-group">
                                <div class="toggle-info">
                                    <strong>Daily Sales Email Summary</strong>
                                    <span>Automatically email end-of-day revenue reports to administrator email</span>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox" id="dailySalesEmailToggle" checked>
                                    <span class="toggle-slider"></span>
                                </label>
                            </div>

                            <div class="settings-toggle-group">
                                <div class="toggle-info">
                                    <strong>Low Inventory Stock Email Alerts</strong>
                                    <span>Send an alert to manager when ingredient quantities fall below the threshold</span>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox" id="lowStockEmailToggle" checked>
                                    <span class="toggle-slider"></span>
                                </label>
                            </div>

                            <div class="pane-footer">
                                <button type="submit" class="btn-save-tab">
                                    <i class="fa-solid fa-check"></i>
                                    Save Appearance & Alerts
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- =========================================
                         5. SYSTEM & DIAGNOSTICS PANE
                    ========================================== -->
                    <div class="settings-pane" id="systemTab">
                        <div class="pane-header">
                            <div class="pane-title">
                                <h2><i class="fa-solid fa-server"></i> System Health, Data Backup & Security</h2>
                                <p>Manage database backup, cache resets, and live environment diagnostics.</p>
                            </div>
                            <span class="pane-badge">Maintenance</span>
                        </div>

                        <div class="system-diagnostics-grid">
                            <div class="diag-card">
                                <div class="diag-icon"><i class="fa-solid fa-cubes"></i></div>
                                <div>
                                    <span>Application Framework</span>
                                    <strong>Spring Boot v4.1.0</strong>
                                </div>
                            </div>
                            <div class="diag-card">
                                <div class="diag-icon"><i class="fa-brands fa-java"></i></div>
                                <div>
                                    <span>Runtime Environment</span>
                                    <strong>Java 21 OpenJDK</strong>
                                </div>
                            </div>
                            <div class="diag-card">
                                <div class="diag-icon"><i class="fa-solid fa-database"></i></div>
                                <div>
                                    <span>Database Engine</span>
                                    <strong>MySQL / JDBC Template</strong>
                                </div>
                            </div>
                            <div class="diag-card">
                                <div class="diag-icon"><i class="fa-solid fa-shield-halved"></i></div>
                                <div>
                                    <span>Security Protocol</span>
                                    <strong>JWT Auth (HS256)</strong>
                                </div>
                            </div>
                        </div>

                        <div class="system-maintenance-section">
                            <h3><i class="fa-solid fa-wrench"></i> Administrative Actions</h3>
                            
                            <div class="maintenance-action-row">
                                <div class="action-desc">
                                    <strong>Export Configuration & Data Backup</strong>
                                    <p>Download a complete JSON snapshot of all restaurant configuration, menus, and table presets.</p>
                                </div>
                                <button type="button" class="btn-maint btn-export" id="exportBackupBtn">
                                    <i class="fa-solid fa-download"></i>
                                    Export Backup
                                </button>
                            </div>

                            <div class="maintenance-action-row">
                                <div class="action-desc">
                                    <strong>Clear Local Client Cache</strong>
                                    <p>Clear temporary frontend cache and reload menu and customer listings cleanly.</p>
                                </div>
                                <button type="button" class="btn-maint btn-clear-cache" id="clearCacheBtn">
                                    <i class="fa-solid fa-broom"></i>
                                    Clear Cache
                                </button>
                            </div>

                            <div class="maintenance-action-row">
                                <div class="action-desc">
                                    <strong>Admin Account Security & Password</strong>
                                    <p>Update your master account login credentials and security question.</p>
                                </div>
                                <a href="<%= contextPath %>/profile.jsp" class="btn-maint btn-security">
                                    <i class="fa-solid fa-key"></i>
                                    Security Settings
                                </a>
                            </div>
                        </div>
                    </div>

                </section>

            </div>

            <!-- =================================================
                 PAGE FOOTER
            ================================================== -->
            <div class="settings-footer">
                <span>© 2026 <strong>Sankalp</strong> Restaurant Management System</span>
                <span>Version 1.0.0</span>
            </div>

        </div>

    </main>

</div>

<!-- =====================================================
     TOAST NOTIFICATION
====================================================== -->
<div class="settings-toast" id="settingsToast">
    <i class="fa-solid fa-circle-check"></i>
    <span id="settingsToastText">Settings updated successfully</span>
</div>

<!-- =====================================================
     SETTINGS JS
====================================================== -->
<script src="${pageContext.request.contextPath}/js/settings.js"></script>

<%@ include file="includes/footer.jsp" %>