<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="includes/header.jsp" %>
<%@ include file="includes/sidebar.jsp" %>

<div class="main-content">


    <!-- =================================================
         TABLE PAGE
    ================================================== -->

    <main class="table-page">


        <!-- =================================================
             PAGE HEADER
        ================================================== -->

        <div class="table-page-header">

            <div class="table-title-area">

                <h1>
                    Table Management
                </h1>

                <div class="table-breadcrumb">

                    <span>
                        Dashboard
                    </span>

                    <i class="fa-solid fa-chevron-right"></i>

                    <span class="active">
                        Tables
                    </span>

                </div>

            </div>


            <button
                type="button"
                class="btn-add-table"
                id="openAddTableBtn">

                <i class="fa-solid fa-plus"></i>

                Add Table

            </button>

        </div>


        <!-- =================================================
             TABLE STATS
        ================================================== -->

        <div class="table-stats">


            <!-- TOTAL -->

            <div class="table-stat-card">

                <div class="table-stat-icon total">

                    <i class="fa-solid fa-chair"></i>

                </div>

                <div>

                    <span>
                        Total Tables
                    </span>

                    <strong id="totalTableCount">
                        0
                    </strong>

                </div>

            </div>


            <!-- AVAILABLE -->

            <div class="table-stat-card">

                <div class="table-stat-icon available">

                    <i class="fa-solid fa-circle-check"></i>

                </div>

                <div>

                    <span>
                        Available
                    </span>

                    <strong id="availableTableCount">
                        0
                    </strong>

                </div>

            </div>


            <!-- OCCUPIED -->

            <div class="table-stat-card">

                <div class="table-stat-icon occupied">

                    <i class="fa-solid fa-user-group"></i>

                </div>

                <div>

                    <span>
                        Occupied
                    </span>

                    <strong id="occupiedTableCount">
                        0
                    </strong>

                </div>

            </div>


            <!-- RESERVED -->

            <div class="table-stat-card">

                <div class="table-stat-icon reserved">

                    <i class="fa-solid fa-calendar-check"></i>

                </div>

                <div>

                    <span>
                        Reserved
                    </span>

                    <strong id="reservedTableCount">
                        0
                    </strong>

                </div>

            </div>

        </div>


        <!-- =================================================
             NAVIGATION TABS: PHYSICAL TABLES vs BOOKING REQUESTS
        ================================================== -->
        <div class="d-flex align-items-center justify-content-between my-3">
            <ul class="nav nav-pills gap-2" id="tableManagementTabs" role="tablist" style="background: rgba(0,0,0,0.04); padding: 5px; border-radius: 10px;">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active btn-sm px-3 py-2 fw-semibold" id="btnTabPhysicalTables" data-bs-toggle="pill" data-bs-target="#tabPhysicalContent" type="button" role="tab">
                        <i class="fa-solid fa-chair me-2"></i> Physical Floor Tables
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link btn-sm px-3 py-2 fw-semibold" id="btnTabBookingRequests" data-bs-toggle="pill" data-bs-target="#tabBookingContent" type="button" role="tab">
                        <i class="fa-solid fa-calendar-check me-2"></i> Online Booking Requests
                        <span class="badge bg-danger ms-2" id="pendingBookingBadge" style="font-size:0.75rem;">0</span>
                    </button>
                </li>
            </ul>
        </div>

        <div class="tab-content" id="tableManagementTabContent">
            
            <!-- TAB 1: PHYSICAL TABLES -->
            <div class="tab-pane fade show active" id="tabPhysicalContent" role="tabpanel">
                <div class="table-layout" id="tableLayout">
                    <!-- =================================================
                         TABLE CARD (LEFT PART)
                    ================================================== -->
                    <section class="table-main-card">
                        <!-- TOOLBAR -->
                        <div class="table-toolbar">
                            <div class="table-search-box">
                                <i class="fa-solid fa-magnifying-glass"></i>
                                <input type="text" id="tableSearch" placeholder="Search tables...">
                            </div>

                            <div class="table-filter-box">
                                <select id="tableStatusFilter">
                                    <option value="ALL">All Status</option>
                                    <option value="AVAILABLE">Available</option>
                                    <option value="OCCUPIED">Occupied</option>
                                    <option value="RESERVED">Reserved</option>
                                    <option value="MAINTENANCE">Maintenance</option>
                                    <option value="INACTIVE">Inactive</option>
                                </select>
                                <i class="fa-solid fa-chevron-down"></i>
                            </div>

                            <button type="button" class="btn-refresh-tables" id="refreshTables">
                                <i class="fa-solid fa-rotate"></i> Refresh
                            </button>
                        </div>

                        <!-- TABLE DATA -->
                        <div class="table-data-wrapper">
                            <table class="restaurant-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Table</th>
                                        <th>Capacity</th>
                                        <th>Location</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody id="tableDataBody">
                                    <tr>
                                        <td colspan="6" class="table-loading">
                                            <i class="fa-solid fa-spinner fa-spin"></i> Loading tables...
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <!-- FOOTER / PAGINATION -->
                        <div class="table-data-footer">
                            <div class="table-showing-text" id="tableShowingText">Showing 0 tables</div>
                            <div class="table-pagination">
                                <button type="button" id="tableFirstPage">First</button>
                                <button type="button" id="tablePrevPage">Prev</button>
                                <button type="button" id="tableCurrentPage" class="active">1</button>
                                <button type="button" id="tableNextPage">Next</button>
                                <button type="button" id="tableLastPage">Last</button>
                            </div>
                        </div>
                    </section>

                    <!-- =================================================
                         ADD / EDIT FORM CARD (RIGHT PART)
                    ================================================== -->
                    <section class="table-form-panel" id="tableFormPanel" aria-hidden="true">
                        <div class="table-form-header">
                            <h2 id="tableFormTitle">Add New Table</h2>
                            <button type="button" id="closeTableForm" aria-label="Close">
                                <i class="fa-solid fa-xmark"></i>
                            </button>
                        </div>

                        <form id="tableForm">
                            <input type="hidden" id="tableId">

                            <div class="table-form-group">
                                <label for="tableNumber">Table Number <span>*</span></label>
                                <input type="text" id="tableNumber" placeholder="Enter table number" maxlength="20" required>
                            </div>

                            <div class="table-form-group">
                                <label for="tableCapacity">Capacity <span>*</span></label>
                                <input type="number" id="tableCapacity" min="1" max="50" placeholder="Enter seating capacity" required>
                            </div>

                            <div class="table-form-group">
                                <label for="tableLocation">Location</label>
                                <input type="text" id="tableLocation" maxlength="100" placeholder="e.g. Ground Floor, Window Side">
                            </div>

                            <div class="table-form-group">
                                <label for="tableStatus">Status <span>*</span></label>
                                <div class="table-select-wrapper">
                                    <select id="tableStatus">
                                        <option value="AVAILABLE">AVAILABLE</option>
                                        <option value="OCCUPIED">OCCUPIED</option>
                                        <option value="RESERVED">RESERVED</option>
                                        <option value="MAINTENANCE">MAINTENANCE</option>
                                        <option value="INACTIVE">INACTIVE</option>
                                    </select>
                                    <i class="fa-solid fa-chevron-down"></i>
                                </div>
                            </div>

                            <div class="table-form-actions">
                                <button type="button" class="btn-table-cancel" id="cancelTableForm">Cancel</button>
                                <button type="submit" class="btn-table-save" id="saveTableBtn">
                                    <i class="fa-solid fa-check"></i> Save Table
                                </button>
                            </div>
                        </form>
                    </section>
                </div>
            </div>

            <!-- TAB 2: ONLINE BOOKING REQUESTS -->
            <div class="tab-pane fade" id="tabBookingContent" role="tabpanel">
                <div class="card border-0 shadow-sm rounded-4 overflow-hidden mb-4" style="background:#fff;">
                    <div class="card-header bg-white py-3 border-bottom d-flex flex-wrap align-items-center justify-content-between gap-3">
                        <div class="d-flex align-items-center gap-2">
                            <div class="p-2 bg-warning-subtle text-warning rounded-3">
                                <i class="fa-solid fa-calendar-days fa-lg"></i>
                            </div>
                            <div>
                                <h5 class="mb-0 fw-bold">Online Table Reservations</h5>
                                <small class="text-muted">Manage table booking requests submitted by customers from the website</small>
                            </div>
                        </div>

                        <div class="d-flex align-items-center gap-2">
                            <select id="resStatusFilter" class="form-select form-select-sm rounded-3" style="width: auto; min-width: 150px;">
                                <option value="ALL">All Statuses</option>
                                <option value="PENDING" selected>Pending Requests</option>
                                <option value="CONFIRMED">Confirmed</option>
                                <option value="COMPLETED">Completed</option>
                                <option value="REJECTED">Rejected</option>
                                <option value="CANCELLED">Cancelled</option>
                            </select>
                            <button type="button" class="btn btn-sm btn-outline-secondary rounded-3" id="refreshReservationsBtn" title="Refresh Reservations">
                                <i class="fa-solid fa-rotate me-1"></i> Refresh
                            </button>
                        </div>
                    </div>

                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0" id="reservationsTable">
                                <thead class="table-light">
                                    <tr>
                                        <th class="ps-3">Code</th>
                                        <th>Guest Name</th>
                                        <th>Phone Number</th>
                                        <th>Date & Time</th>
                                        <th>Guests</th>
                                        <th>Special Request</th>
                                        <th>Assigned Table</th>
                                        <th>Status</th>
                                        <th class="text-end pe-3">Actions</th>
                                    </tr>
                                </thead>
                                <tbody id="reservationsTableBody">
                                    <tr>
                                        <td colspan="9" class="text-center py-4 text-muted">
                                            <i class="fa-solid fa-spinner fa-spin me-2"></i> Loading reservations...
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </div>


        <!-- =================================================
             FOOTER
        ================================================== -->

        <div class="table-footer">

            <span>

                © 2026
                <strong>Sankalp</strong>
                Restaurant Management System

            </span>

            <span>
                Version 1.0.0
            </span>

        </div>

    </main>

</div>


<!-- =====================================================
     DELETE MODAL
====================================================== -->

<div
    class="table-delete-overlay"
    id="tableDeleteOverlay">

    <div class="table-delete-modal">

        <div class="table-delete-icon">

            <i class="fa-solid fa-trash"></i>

        </div>

        <h3>
            Delete Table?
        </h3>

        <p>
            Are you sure you want to delete this table?
            This action cannot be undone.
        </p>

        <div class="table-delete-actions">

            <button
                type="button"
                id="cancelTableDelete">

                Cancel

            </button>

            <button
                type="button"
                id="confirmTableDelete">

                Delete

            </button>

        </div>

    </div>

</div>


<!-- =====================================================
     TOAST
====================================================== -->

<div
    class="table-toast"
    id="tableToast">

    <i class="fa-solid fa-circle-check"></i>

    <span id="tableToastText">
        Success
    </span>

</div>


<!-- =====================================================
     TABLE JS
====================================================== -->

<script
    src="${pageContext.request.contextPath}/js/table.js">
</script>

<%@ include file="includes/footer.jsp" %>