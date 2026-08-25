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

    <main class="profile-page">

        <div class="profile-page-container">

            <!-- BREADCRUMB / HEADER -->
            <div class="profile-page-header">
                <div class="header-text">
                    <h1>
                        <i class="fa-solid fa-circle-user"></i>
                        My Account Profile
                    </h1>
                    <p>Manage your personal profile details, account security, and credentials.</p>
                </div>
                <div class="header-actions">
                    <span class="badge-status" id="accountStatusBadge">
                        <i class="fa-solid fa-circle-check"></i> <span id="accountStatusText">Active</span>
                    </span>
                </div>
            </div>

            <!-- PROFILE LAYOUT GRID -->
            <div class="profile-grid">

                <!-- LEFT COLUMN: USER HERO CARD & QUICK INFO -->
                <div class="profile-sidebar-col">
                    
                    <!-- USER SUMMARY CARD -->
                    <div class="profile-card hero-card">
                        <div class="hero-cover"></div>
                        <div class="hero-avatar-wrap">
                            <div class="hero-avatar" id="heroAvatar">
                                <span id="heroInitials">U</span>
                            </div>
                            <div class="role-pill" id="heroRoleBadge">
                                <i class="fa-solid fa-user-shield"></i> <span id="heroRoleText">STAFF</span>
                            </div>
                        </div>

                        <div class="hero-info">
                            <h2 id="heroFullName">Loading...</h2>
                            <span class="hero-username" id="heroUsername">@username</span>
                            <p class="hero-bio" id="heroBio">Sankalp Restaurant Team Member</p>
                        </div>

                        <div class="hero-stats">
                            <div class="stat-item">
                                <span class="stat-label">User ID</span>
                                <strong class="stat-value" id="heroUserId">#--</strong>
                            </div>
                            <div class="stat-item">
                                <span class="stat-label">Role</span>
                                <strong class="stat-value" id="heroRoleLabel">STAFF</strong>
                            </div>
                            <div class="stat-item">
                                <span class="stat-label">Status</span>
                                <strong class="stat-value text-success" id="heroStatusLabel">ACTIVE</strong>
                            </div>
                        </div>
                    </div>

                    <!-- QUICK SHORTCUTS CARD -->
                    <div class="profile-card shortcuts-card">
                        <div class="profile-card-header">
                            <h2><i class="fa-solid fa-bolt"></i> Quick Shortcuts</h2>
                        </div>
                        <div class="shortcuts-list">
                            <a href="<%= contextPath %>/dashboard.jsp" class="shortcut-item">
                                <i class="fa-solid fa-chart-line"></i>
                                <span>Dashboard Overview</span>
                                <i class="fa-solid fa-chevron-right arrow"></i>
                            </a>
                            <a href="<%= contextPath %>/order.jsp" class="shortcut-item">
                                <i class="fa-solid fa-cart-shopping"></i>
                                <span>Orders & Tables</span>
                                <i class="fa-solid fa-chevron-right arrow"></i>
                            </a>
                            <a href="<%= contextPath %>/billing.jsp" class="shortcut-item">
                                <i class="fa-solid fa-file-invoice-dollar"></i>
                                <span>Billing & Invoices</span>
                                <i class="fa-solid fa-chevron-right arrow"></i>
                            </a>
                        </div>
                    </div>

                </div>

                <!-- RIGHT COLUMN: PROFILE DETAILS & SECURITY TABS -->
                <div class="profile-main-col">

                    <!-- TAB HEADERS -->
                    <div class="profile-tabs-header">
                        <button type="button" class="tab-btn active" id="tabDetailsBtn" onclick="switchProfileTab('details')">
                            <i class="fa-solid fa-id-card"></i> Personal Information
                        </button>
                        <button type="button" class="tab-btn" id="tabSecurityBtn" onclick="switchProfileTab('security')">
                            <i class="fa-solid fa-shield-halved"></i> Security & Password
                        </button>
                    </div>

                    <!-- TAB 1: PERSONAL INFORMATION -->
                    <div class="profile-tab-content active" id="tabDetails">
                        <div class="profile-card">
                            <div class="profile-card-header details">
                                <div>
                                    <h2><i class="fa-solid fa-user-pen"></i> Personal Information</h2>
                                    <p class="card-subtitle">Update your personal information and contact details</p>
                                </div>
                            </div>
                            <div class="profile-card-body">
                                <form id="profileForm" novalidate>
                                    
                                    <div class="form-row">
                                        <div class="profile-form-group">
                                            <label for="fullName">Full Name <span class="required">*</span></label>
                                            <div class="input-icon-wrap">
                                                <i class="fa-solid fa-user icon"></i>
                                                <input type="text" id="fullName" placeholder="Enter your full name" required autocomplete="name">
                                            </div>
                                            <small class="field-error" id="fullNameError"></small>
                                        </div>

                                        <div class="profile-form-group">
                                            <label for="profileUsername">Username <span class="field-hint">(System ID)</span></label>
                                            <div class="input-icon-wrap">
                                                <i class="fa-solid fa-at icon"></i>
                                                <input type="text" id="profileUsername" readonly disabled title="Username cannot be changed">
                                                <i class="fa-solid fa-lock input-lock-icon" title="Username cannot be changed"></i>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-row">
                                        <div class="profile-form-group">
                                            <label for="email">Email Address</label>
                                            <div class="input-icon-wrap">
                                                <i class="fa-solid fa-envelope icon"></i>
                                                <input type="email" id="email" placeholder="e.g. user@sankalp.com" autocomplete="email">
                                            </div>
                                            <small class="field-error" id="emailError"></small>
                                        </div>

                                        <div class="profile-form-group">
                                            <label for="mobile">Mobile Number</label>
                                            <div class="input-icon-wrap">
                                                <i class="fa-solid fa-phone icon"></i>
                                                <input type="tel" id="mobile" placeholder="10-digit mobile number" maxlength="10" autocomplete="tel">
                                            </div>
                                            <small class="field-error" id="mobileError"></small>
                                        </div>
                                    </div>

                                    <div class="form-row">
                                        <div class="profile-form-group">
                                            <label for="userRoleDisplay">Assigned Role</label>
                                            <div class="input-icon-wrap">
                                                <i class="fa-solid fa-shield-cat icon"></i>
                                                <input type="text" id="userRoleDisplay" readonly disabled>
                                            </div>
                                        </div>

                                        <div class="profile-form-group">
                                            <label for="userStatusDisplay">Account Status</label>
                                            <div class="input-icon-wrap">
                                                <i class="fa-solid fa-badge-check icon"></i>
                                                <input type="text" id="userStatusDisplay" readonly disabled>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-actions">
                                        <button type="submit" class="btn-profile-save" id="btnSaveProfile">
                                            <i class="fa-solid fa-floppy-disk"></i>
                                            <span id="saveProfileBtnText">Save Changes</span>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- TAB 2: SECURITY / CHANGE PASSWORD -->
                    <div class="profile-tab-content" id="tabSecurity">
                        <div class="profile-card">
                            <div class="profile-card-header security">
                                <div>
                                    <h2><i class="fa-solid fa-lock-keyhole"></i> Change Password</h2>
                                    <p class="card-subtitle">Ensure your account is using a secure, strong password</p>
                                </div>
                            </div>
                            <div class="profile-card-body">
                                <form id="passwordForm" novalidate>

                                    <!-- CURRENT PASSWORD -->
                                    <div class="profile-form-group">
                                        <label for="oldPassword">Current Password <span class="required">*</span></label>
                                        <div class="input-icon-wrap">
                                            <i class="fa-solid fa-key icon"></i>
                                            <input type="password" id="oldPassword" placeholder="Enter your current password" required autocomplete="current-password">
                                            <button type="button" class="btn-eye-toggle" onclick="togglePasswordVisibility('oldPassword', this)" title="Show/Hide Password">
                                                <i class="fa-solid fa-eye"></i>
                                            </button>
                                        </div>
                                        <small class="field-error" id="oldPasswordError"></small>
                                    </div>

                                    <!-- NEW PASSWORD -->
                                    <div class="profile-form-group">
                                        <label for="newPassword">New Password <span class="required">*</span></label>
                                        <div class="input-icon-wrap">
                                            <i class="fa-solid fa-shield-halved icon"></i>
                                            <input type="password" id="newPassword" placeholder="Enter new password (min 6 characters)" required autocomplete="new-password">
                                            <button type="button" class="btn-eye-toggle" onclick="togglePasswordVisibility('newPassword', this)" title="Show/Hide Password">
                                                <i class="fa-solid fa-eye"></i>
                                            </button>
                                        </div>
                                        
                                        <!-- STRENGTH METER -->
                                        <div class="password-strength-container" id="strengthContainer">
                                            <div class="strength-bar-bg">
                                                <div class="strength-bar-fill" id="strengthBar"></div>
                                            </div>
                                            <span class="strength-text" id="strengthText">Password strength: -</span>
                                        </div>
                                        <small class="field-error" id="newPasswordError"></small>
                                    </div>

                                    <!-- CONFIRM NEW PASSWORD -->
                                    <div class="profile-form-group">
                                        <label for="confirmPassword">Confirm New Password <span class="required">*</span></label>
                                        <div class="input-icon-wrap">
                                            <i class="fa-solid fa-check-double icon"></i>
                                            <input type="password" id="confirmPassword" placeholder="Re-enter your new password" required autocomplete="new-password">
                                            <button type="button" class="btn-eye-toggle" onclick="togglePasswordVisibility('confirmPassword', this)" title="Show/Hide Password">
                                                <i class="fa-solid fa-eye"></i>
                                            </button>
                                        </div>
                                        <small class="field-error" id="confirmPasswordError"></small>
                                    </div>

                                    <!-- GUIDELINES -->
                                    <div class="password-guidelines">
                                        <strong><i class="fa-solid fa-circle-info"></i> Password Guidelines:</strong>
                                        <ul>
                                            <li id="ruleMinLen"><i class="fa-solid fa-circle-xmark"></i> At least 6 characters long</li>
                                            <li id="ruleLetters"><i class="fa-solid fa-circle-xmark"></i> Contains letters and numbers</li>
                                            <li id="ruleMatch"><i class="fa-solid fa-circle-xmark"></i> New and Confirm passwords match</li>
                                        </ul>
                                    </div>

                                    <div class="form-actions">
                                        <button type="submit" class="btn-password-save" id="btnSavePassword">
                                            <i class="fa-solid fa-key"></i>
                                            <span id="savePasswordBtnText">Update Password</span>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </div>

    </main>

</div>

<!-- SCRIPT -->
<script src="<%= contextPath %>/js/profile.js"></script>

<!-- =====================================================
     COMMON FOOTER
====================================================== -->
<%@ include file="includes/footer.jsp" %>