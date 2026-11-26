<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String currentUri = request.getRequestURI();

    String pageTitle = "Dashboard";
    String pageCss = "dashboard.css";

    if (currentUri != null) {
        if (currentUri.endsWith("dashboard.jsp")) {
            pageTitle = "Dashboard";
            pageCss = "dashboard.css";
        }
        else if (currentUri.endsWith("category.jsp")) {
            pageTitle = "Category Management";
            pageCss = "category.css";
        }
        else if (currentUri.endsWith("order.jsp")) {
            pageTitle = "Order Management";
            pageCss = "order.css";
        }
        else if (currentUri.endsWith("menu.jsp")) {
            pageTitle = "Menu Management";
            pageCss = "menu.css";
        }
        else if (currentUri.endsWith("table.jsp")) {
            pageTitle = "Table Management";
            pageCss = "table.css";
        }
        else if (currentUri.endsWith("floor-plan.jsp")) {
            pageTitle = "Floor Plan Visualizer";
            pageCss = "table.css";
        }
        else if (currentUri.endsWith("customer.jsp")) {
            pageTitle = "Customers";
            pageCss = "customer.css";
        }
        else if (currentUri.endsWith("billing.jsp")) {
            pageTitle = "Billing & Invoices";
            pageCss = "billing.css";
        }
        else if (currentUri.endsWith("reports.jsp")
                || currentUri.endsWith("report.jsp")) {
            pageTitle = "Reports & Analytics";
            pageCss = "reports.css";
        }
        else if (currentUri.endsWith("chef.jsp")
                || currentUri.endsWith("kitchen.jsp")) {
            pageTitle = "Kitchen Dashboard";
            pageCss = "chef.css";
        }
        else if (currentUri.endsWith("inventory.jsp")) {
            pageTitle = "Inventory & Stock Control";
            pageCss = "inventory.css";
        }
        else if (currentUri.endsWith("staff.jsp")) {
            pageTitle = "Staff Management";
            pageCss = "staff.css";
        }
        else if (currentUri.endsWith("profile.jsp")) {
            pageTitle = "My Profile";
            pageCss = "profile.css";
        }
        else if (currentUri.endsWith("settings.jsp")) {
            pageTitle = "Settings";
            pageCss = "settings.css";
        }
    }
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <!-- =====================================================
         META
    ====================================================== -->

    <meta charset="UTF-8">

    <meta
        name="viewport"
        content="width=device-width, initial-scale=1.0">

    <!-- APP CONTEXT -->
    <meta
        name="app-context"
        content="${pageContext.request.contextPath}">


    <!-- =====================================================
         TITLE
    ====================================================== -->

    <title>
        <%= pageTitle %> | Sankalp RMS
    </title>

    <!-- DYNAMIC FAVICON -->
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo1.png" id="dynamicFavicon">

    <!-- BOOTSTRAP 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- =====================================================
         FONT AWESOME
    ====================================================== -->

    <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">


    <!-- =====================================================
         COMMON CSS
    ====================================================== -->

    <link
        rel="stylesheet"
        href="${pageContext.request.contextPath}/css/common.css?v=1.0.0">


    <!-- =====================================================
         PAGE CSS
    ====================================================== -->

    <link
        rel="stylesheet"
        href="${pageContext.request.contextPath}/css/<%= pageCss %>?v=1.0.2">

    <script src="${pageContext.request.contextPath}/js/config.js?v=1.0.0"></script>

</head>


<body>


<!-- =====================================================
     COMMON HEADER
====================================================== -->

<header class="admin-header">


    <!-- =================================================
         LEFT
    ================================================== -->

    <div class="header-left">

        <button
            type="button"
            class="sidebar-toggle"
            id="sidebarToggle"
            aria-label="Toggle sidebar">

            <i class="fa-solid fa-bars"></i>

        </button>


        <h1
            class="header-title"
            id="pageTitle">

            <%= pageTitle %>

        </h1>

    </div>


    <!-- =================================================
         GLOBAL SEARCH
    ================================================== -->

    <div class="header-search">

        <input
            type="text"
            id="globalSearch"
            placeholder="Search here..."
            autocomplete="off">

        <i class="fa-solid fa-magnifying-glass"></i>

    </div>


    <!-- =================================================
         HEADER RIGHT
    ================================================== -->

    <div class="header-right">


        <!-- =================================================
             DATE + LIVE TIME
        ================================================== -->

        <div
            class="header-date"
            id="headerDateTime">

            <i class="fa-regular fa-calendar"></i>

            <div>

                <!-- DATE -->
                <strong id="currentDate">
                    Loading...
                </strong>


                <!-- DAY + TIME -->
                <span id="currentDay">
                    Loading...
                </span>

            </div>

        </div>


        <!-- =================================================
             THEME TOGGLE (DARK / LIGHT)
        ================================================== -->

        <button
            type="button"
            class="theme-toggle-btn"
            id="themeToggleBtn"
            title="Toggle Light / Dark Glass Mode"
            aria-label="Toggle Theme">

            <i class="fa-solid fa-moon" id="themeToggleIcon"></i>

        </button>

        <!-- =================================================
             NOTIFICATION
        ================================================== -->

        <button
            type="button"
            class="notification-btn"
            id="notificationBtn"
            aria-label="Notifications">

            <i class="fa-regular fa-bell"></i>

            <span
                class="notification-count"
                id="notificationCount">

                0

            </span>

        </button>


        <!-- =================================================
             PROFILE
        ================================================== -->

        <div
            class="admin-profile"
            id="adminProfile">

            <div class="profile-avatar">

                <i class="fa-solid fa-user-tie"></i>

            </div>


            <div class="profile-info">

                <strong id="adminName">
                    Admin
                </strong>

                <span id="adminRole">
                    Administrator
                </span>

            </div>


            <i class="fa-solid fa-chevron-down"></i>

        </div>

    </div>

</header>


<!-- =====================================================
     NOTIFICATIONS DROPDOWN
====================================================== -->

<div class="notification-dropdown" id="notificationDropdown">
    <div class="notification-header">
        <h6>Notifications</h6>
        <button type="button" class="btn-clear-notif" id="clearNotifBtn">Clear all</button>
    </div>
    <div class="notification-list" id="notificationList">
        <div class="notification-item unread">
            <div class="notif-icon kitchen"><i class="fa-solid fa-utensils"></i></div>
            <div class="notif-content">
                <strong>Kitchen Alert</strong>
                <p>Order for Table T-02 is ready to serve!</p>
                <small>2 mins ago</small>
            </div>
        </div>
        <div class="notification-item">
            <div class="notif-icon billing"><i class="fa-solid fa-receipt"></i></div>
            <div class="notif-content">
                <strong>Bill Generated</strong>
                <p>Invoice #BILL-1002 marked as PAID (Cash).</p>
                <small>15 mins ago</small>
            </div>
        </div>
    </div>
    <div class="notification-footer">
        <a href="${pageContext.request.contextPath}/order.jsp">View All Activity</a>
    </div>
</div>

<!-- =====================================================
     PROFILE DROPDOWN
====================================================== -->

<div
    class="profile-dropdown"
    id="profileDropdown">

    <!-- PROFILE -->
    <a href="${pageContext.request.contextPath}/profile.jsp">
        <i class="fa-regular fa-user"></i>
        <span>My Profile</span>
    </a>

    <!-- DIVIDER -->
    <div class="dropdown-divider"></div>

    <!-- LOGOUT -->
    <a
        href="${pageContext.request.contextPath}/login.jsp"
        id="headerLogout"
        class="logout-dropdown">
        <i class="fa-solid fa-right-from-bracket"></i>
        <span>Logout</span>
    </a>
</div>