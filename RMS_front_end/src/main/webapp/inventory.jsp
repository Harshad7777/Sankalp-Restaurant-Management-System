<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
%>

<%@ include file="includes/header.jsp" %>
<%@ include file="includes/sidebar.jsp" %>

<!-- MAIN CONTENT -->
<div class="main-content">
    <main class="inventory-page">

        <!-- PAGE TITLE & ACTIONS -->
        <div class="inventory-page-header">
            <div>
                <h2 class="page-title">
                    <i class="fa-solid fa-boxes-stacked text-gold"></i> Inventory &amp; Stock Control
                </h2>
                <p class="page-subtitle">Track raw materials, monitor low-stock thresholds, and log stock movements</p>
            </div>
            <div class="header-actions">
                <button type="button" class="btn-inventory btn-inventory-refresh" onclick="loadInventorySummary(); loadInventoryTable();">
                    <i class="fa-solid fa-arrows-rotate"></i> Refresh
                </button>
                <button type="button" class="btn-inventory btn-inventory-csv" onclick="exportInventoryCSV()">
                    <i class="fa-solid fa-file-excel"></i> Export Excel/CSV
                </button>
                <button type="button" class="btn-inventory btn-inventory-pdf" onclick="exportInventoryPDF()">
                    <i class="fa-solid fa-file-pdf"></i> Print / PDF Report
                </button>
                <button type="button" class="btn-inventory btn-inventory-gold" onclick="openAddItemModal()">
                    <i class="fa-solid fa-plus"></i> Add Stock Item
                </button>
            </div>
        </div>

        <!-- 5-COLUMN KPI SUMMARY CARDS -->
        <div class="inventory-stats-grid">
            <div class="kpi-card">
                <div class="kpi-icon-box icon-blue">
                    <i class="fa-solid fa-cubes"></i>
                </div>
                <div class="kpi-details">
                    <span class="kpi-label">Total Stock Items</span>
                    <h3 class="kpi-value" id="kpiTotalItems">0</h3>
                </div>
            </div>

            <div class="kpi-card kpi-in-stock">
                <div class="kpi-icon-box icon-green">
                    <i class="fa-solid fa-circle-check"></i>
                </div>
                <div class="kpi-details">
                    <span class="kpi-label">In-Stock Healthy</span>
                    <h3 class="kpi-value text-success" id="kpiInStock">0</h3>
                </div>
            </div>

            <div class="kpi-card kpi-low-stock">
                <div class="kpi-icon-box icon-amber">
                    <i class="fa-solid fa-triangle-exclamation"></i>
                </div>
                <div class="kpi-details">
                    <span class="kpi-label">Low Stock Alerts</span>
                    <h3 class="kpi-value text-amber" id="kpiLowStock">0</h3>
                </div>
            </div>

            <div class="kpi-card kpi-out-of-stock">
                <div class="kpi-icon-box icon-red">
                    <i class="fa-solid fa-box-open"></i>
                </div>
                <div class="kpi-details">
                    <span class="kpi-label">Out of Stock</span>
                    <h3 class="kpi-value text-red" id="kpiOutOfStock">0</h3>
                </div>
            </div>

            <div class="kpi-card kpi-valuation">
                <div class="kpi-icon-box icon-gold">
                    <i class="fa-solid fa-sack-dollar"></i>
                </div>
                <div class="kpi-details">
                    <span class="kpi-label">Total Stock Valuation</span>
                    <h3 class="kpi-value text-gold" id="kpiTotalValue">₹0.00</h3>
                </div>
            </div>
        </div>

        <!-- CONTROLS & FILTER TOOLBAR -->
        <div class="inventory-toolbar-card">
            <div class="inventory-toolbar">
                
                <!-- Search Box -->
                <div class="search-input-wrap">
                    <i class="fa-solid fa-magnifying-glass search-icon"></i>
                    <input type="text" id="inventorySearch" class="inventory-search-input" placeholder="Search by item name or supplier...">
                    <button type="button" id="clearSearchBtn" class="btn-clear" style="display:none;" onclick="clearSearch()">
                        <i class="fa-solid fa-xmark"></i>
                    </button>
                </div>

                <!-- Category Filter -->
                <select id="categoryFilter" class="inventory-select" onchange="handleFilterChange()">
                    <option value="ALL">All Categories</option>
                </select>

                <!-- Status Filter Pills -->
                <div class="status-pills-wrap">
                    <button type="button" class="status-pill active" data-status="ALL" onclick="filterByStatus('ALL', this)">All</button>
                    <button type="button" class="status-pill pill-in-stock" data-status="IN_STOCK" onclick="filterByStatus('IN_STOCK', this)">In Stock</button>
                    <button type="button" class="status-pill pill-low-stock" data-status="LOW_STOCK" onclick="filterByStatus('LOW_STOCK', this)">Low Stock</button>
                    <button type="button" class="status-pill pill-out-of-stock" data-status="OUT_OF_STOCK" onclick="filterByStatus('OUT_OF_STOCK', this)">Out of Stock</button>
                </div>

                <!-- Sort By -->
                <select id="sortByFilter" class="inventory-select" onchange="handleFilterChange()">
                    <option value="item_name:ASC">Name (A-Z)</option>
                    <option value="item_name:DESC">Name (Z-A)</option>
                    <option value="quantity:ASC">Quantity (Low-High)</option>
                    <option value="quantity:DESC">Quantity (High-Low)</option>
                    <option value="status:ASC">Status</option>
                </select>

            </div>
        </div>

        <!-- INVENTORY DATA TABLE CARD -->
        <div class="inventory-table-card">
            <div class="table-responsive">
                <table class="inventory-table">
                    <thead>
                        <tr>
                            <th>Item &amp; Category</th>
                            <th>Stock Level</th>
                            <th>Min Threshold</th>
                            <th>Cost / Unit</th>
                            <th>Total Value</th>
                            <th>Supplier</th>
                            <th>Status</th>
                            <th style="text-align: right; padding-right: 20px;">Actions</th>
                        </tr>
                    </thead>
                    <tbody id="inventoryTableBody">
                        <tr>
                            <td colspan="8" style="text-align: center; padding: 30px; color: #64748b;">
                                <i class="fa-solid fa-spinner fa-spin text-gold" style="margin-right: 8px;"></i>
                                Loading inventory records...
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- TABLE FOOTER & PAGINATION -->
            <div class="inventory-table-footer">
                <div style="font-size: 12px; color: #64748b;" id="paginationInfo">
                    Showing 0 to 0 of 0 entries
                </div>
                <div class="pagination-controls" id="paginationControls">
                    <!-- Rendered by JS -->
                </div>
            </div>
        </div>

    </main>
</div>

<!-- =====================================================
     ADD / EDIT INVENTORY ITEM MODAL
====================================================== -->
<div class="modal" id="itemModal" tabindex="-1" onclick="if(event.target===this) hideModal('itemModal')">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="itemModalTitle">
                    <i class="fa-solid fa-box text-gold"></i> Add Stock Item
                </h5>
                <button type="button" class="modal-close-btn" onclick="hideModal('itemModal')">
                    <i class="fa-solid fa-xmark"></i>
                </button>
            </div>
            <form id="itemForm" onsubmit="saveItem(event)">
                <div class="modal-body">
                    <input type="hidden" id="itemId">

                    <div class="form-group">
                        <label class="form-label required">Item Name</label>
                        <input type="text" id="itemName" class="form-input" placeholder="e.g. Fresh Paneer" required maxlength="100">
                    </div>

                    <div class="form-grid-2">
                        <div class="form-group no-margin">
                            <label class="form-label required">Category</label>
                            <input type="text" id="itemCategory" class="form-input" placeholder="e.g. Dairy, Grains, Spices" required maxlength="50" list="categorySuggestions">
                            <datalist id="categorySuggestions">
                                <option value="Dairy">
                                <option value="Grains">
                                <option value="Vegetables">
                                <option value="Spices">
                                <option value="Beverages">
                                <option value="Cooking Oil">
                                <option value="Meat & Poultry">
                                <option value="Packaging">
                            </datalist>
                        </div>

                        <div class="form-group no-margin">
                            <label class="form-label required">Unit of Measurement</label>
                            <select id="itemUnit" class="form-select" required>
                                <option value="kg">kg (Kilograms)</option>
                                <option value="liters">liters (Liters)</option>
                                <option value="grams">grams (Grams)</option>
                                <option value="packets">packets (Packets)</option>
                                <option value="units">units (Pieces)</option>
                                <option value="boxes">boxes (Boxes)</option>
                                <option value="tins">tins (Tins)</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-grid-2" style="margin-top: 14px;">
                        <div class="form-group no-margin">
                            <label class="form-label required">Current Quantity</label>
                            <input type="number" id="itemQuantity" class="form-input" placeholder="0.00" step="0.01" min="0" required>
                        </div>

                        <div class="form-group no-margin">
                            <label class="form-label required">Min Alert Threshold</label>
                            <input type="number" id="itemMinThreshold" class="form-input" placeholder="5.00" step="0.01" min="0" required>
                            <small class="form-help">Triggers low-stock warning</small>
                        </div>
                    </div>

                    <div class="form-group" style="margin-top: 14px;">
                        <label class="form-label required">Cost Per Unit (₹)</label>
                        <input type="number" id="itemCostPerUnit" class="form-input" placeholder="0.00" step="0.01" min="0" required>
                    </div>

                    <div class="form-grid-2">
                        <div class="form-group no-margin">
                            <label class="form-label">Supplier Name</label>
                            <input type="text" id="itemSupplierName" class="form-input" placeholder="e.g. Amul Dairy" maxlength="100">
                        </div>

                        <div class="form-group no-margin">
                            <label class="form-label">Supplier Contact</label>
                            <input type="text" id="itemSupplierContact" class="form-input" placeholder="e.g. 9876543210" maxlength="50">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn-inventory btn-inventory-refresh" onclick="hideModal('itemModal')">Cancel</button>
                    <button type="submit" class="btn-inventory btn-inventory-gold" id="saveItemBtn">
                        <i class="fa-solid fa-floppy-disk"></i> Save Item
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- =====================================================
     QUICK STOCK ADJUSTMENT (RESTOCK / CONSUME) MODAL
====================================================== -->
<div class="modal" id="adjustModal" tabindex="-1" onclick="if(event.target===this) hideModal('adjustModal')">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i class="fa-solid fa-arrow-right-arrow-left text-gold"></i> Stock Movement Adjustment
                </h5>
                <button type="button" class="modal-close-btn" onclick="hideModal('adjustModal')">
                    <i class="fa-solid fa-xmark"></i>
                </button>
            </div>
            <form id="adjustForm" onsubmit="submitStockAdjustment(event)">
                <div class="modal-body">
                    <input type="hidden" id="adjustItemId">
                    
                    <div class="current-stock-banner">
                        <div class="stock-banner-item">
                            <strong id="adjustItemName" style="font-size: 14px; color: #1e293b;">Item Name</strong>
                            <span style="font-size: 12px; color: #64748b;" id="adjustItemCategory">Category</span>
                        </div>
                        <div class="stock-banner-item" style="text-align: right;">
                            <span class="stock-banner-label">Current Stock:</span>
                            <span class="stock-banner-val text-gold" id="adjustCurrentStock">0.00 kg</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="form-label required">Action Type</label>
                        <div class="action-radio-grid">
                            <label class="action-radio-card selected" id="labelRestock" onclick="selectActionType('RESTOCK')">
                                <input type="radio" name="adjustAction" id="actionRestock" value="RESTOCK" checked onchange="updateAdjustPreview()">
                                <span class="action-radio-title" style="color: #059669;"><i class="fa-solid fa-plus"></i> Restock</span>
                            </label>

                            <label class="action-radio-card" id="labelConsume" onclick="selectActionType('CONSUME')">
                                <input type="radio" name="adjustAction" id="actionConsume" value="CONSUME" onchange="updateAdjustPreview()">
                                <span class="action-radio-title" style="color: #0284c7;"><i class="fa-solid fa-minus"></i> Consume</span>
                            </label>

                            <label class="action-radio-card" id="labelWaste" onclick="selectActionType('WASTAGE')">
                                <input type="radio" name="adjustAction" id="actionWaste" value="WASTAGE" onchange="updateAdjustPreview()">
                                <span class="action-radio-title" style="color: #dc2626;"><i class="fa-solid fa-trash-can"></i> Waste</span>
                            </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="form-label required">Quantity (<span id="adjustUnitLabel">unit</span>)</label>
                        <input type="number" id="adjustQuantity" class="form-input" placeholder="Enter quantity" step="0.01" min="0.01" required oninput="updateAdjustPreview()">
                    </div>

                    <div class="form-group">
                        <label class="form-label">Reason / Notes</label>
                        <input type="text" id="adjustReason" class="form-input" placeholder="e.g. Daily kitchen prep, Supplier shipment #104">
                    </div>

                    <div class="current-stock-banner" style="justify-content: center; text-align: center; background: #fffdf7;">
                        <span style="font-size: 12px; color: #64748b; margin-right: 6px;">Projected New Stock: </span>
                        <strong id="adjustProjectedStock" style="color: #1e293b; font-size: 14px;">0.00</strong>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn-inventory btn-inventory-refresh" onclick="hideModal('adjustModal')">Cancel</button>
                    <button type="submit" class="btn-inventory btn-inventory-gold" id="submitAdjustBtn">
                        <i class="fa-solid fa-check"></i> Confirm Adjustment
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- =====================================================
     INVENTORY AUDIT LOGS MODAL
====================================================== -->
<div class="modal" id="logsModal" tabindex="-1" onclick="if(event.target===this) hideModal('logsModal')">
    <div class="modal-dialog modal-dialog-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i class="fa-solid fa-clock-rotate-left text-gold"></i> Stock Movement History: <span id="logsItemTitle" style="color: #e2e8f0; margin-left: 5px;"></span>
                </h5>
                <button type="button" class="modal-close-btn" onclick="hideModal('logsModal')">
                    <i class="fa-solid fa-xmark"></i>
                </button>
            </div>
            <div class="modal-body" style="padding: 0;">
                <div class="table-responsive" style="max-height: 400px;">
                    <table class="inventory-table">
                        <thead>
                            <tr>
                                <th>Date &amp; Time</th>
                                <th>Action</th>
                                <th>Quantity</th>
                                <th>Before &amp; After</th>
                                <th>Reason</th>
                                <th>Performed By</th>
                            </tr>
                        </thead>
                        <tbody id="logsTableBody">
                            <tr>
                                <td colspan="6" style="text-align: center; padding: 24px; color: #64748b;">No logs recorded yet.</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn-inventory btn-inventory-refresh" onclick="hideModal('logsModal')">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- INVENTORY JS SCRIPT -->
<script src="<%= contextPath %>/js/inventory.js"></script>

<%@ include file="includes/footer.jsp" %>

