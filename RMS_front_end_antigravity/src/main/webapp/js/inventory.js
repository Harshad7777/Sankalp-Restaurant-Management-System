/**
 * =========================================================
 * SANKALP RESTAURANT MANAGEMENT SYSTEM
 * INVENTORY CONTROLLER JAVASCRIPT
 * Real-time stock tracking, filtering, modals, and adjustments
 * =========================================================
 */

"use strict";

const BACKEND_BASE_URL = window.location.origin.includes(":8081") 
    ? "http://localhost:8080" 
    : (window.location.origin + "/RMS_backend");

const INVENTORY_API = `${BACKEND_BASE_URL}/api/inventory`;

let currentPage = 0;
const pageSize = 10;
let currentSearch = "";
let currentCategory = "ALL";
let currentStatus = "ALL";
let currentSortBy = "item_name";
let currentSortDirection = "ASC";
let searchDebounceTimer = null;

let currentAdjustItem = null;

// =========================================================
// INITIALIZE
// =========================================================

document.addEventListener("DOMContentLoaded", function () {
    console.log("Sankalp Inventory JS Loaded");

    // Verify Authentication
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.jsp";
        return;
    }

    // Role-based Access Check
    const role = localStorage.getItem("role");
    console.log("Current User Role:", role);

    initSearchInput();
    loadInventorySummary();
    loadCategoriesDropdown();
    loadInventoryTable();
});

function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Accept": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("token")}`
    };
}

// =========================================================
// LOAD KPI SUMMARY METRICS
// =========================================================

async function loadInventorySummary() {
    try {
        const response = await fetch(`${INVENTORY_API}/summary`, {
            headers: getAuthHeaders()
        });

        if (!response.ok) throw new Error("Failed to load inventory summary");

        const data = await response.json();

        document.getElementById("kpiTotalItems").textContent = data.totalItems || 0;
        document.getElementById("kpiInStock").textContent = data.inStockItems || 0;
        document.getElementById("kpiLowStock").textContent = data.lowStockItems || 0;
        document.getElementById("kpiOutOfStock").textContent = data.outOfStockItems || 0;

        const val = Number(data.totalInventoryValue || 0).toLocaleString("en-IN", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });
        document.getElementById("kpiTotalValue").textContent = `₹${val}`;

    } catch (error) {
        console.error("Error loading summary:", error);
    }
}

// =========================================================
// LOAD CATEGORIES DROPDOWN
// =========================================================

async function loadCategoriesDropdown() {
    try {
        const response = await fetch(`${INVENTORY_API}/categories`, {
            headers: getAuthHeaders()
        });

        if (!response.ok) return;

        const categories = await response.json();
        const select = document.getElementById("categoryFilter");
        if (!select) return;

        select.innerHTML = '<option value="ALL">All Categories</option>';
        categories.forEach(cat => {
            if (cat && cat.trim()) {
                select.innerHTML += `<option value="${escapeHtml(cat)}">${escapeHtml(cat)}</option>`;
            }
        });

    } catch (error) {
        console.warn("Could not load categories list:", error);
    }
}

// =========================================================
// LOAD INVENTORY DATA TABLE
// =========================================================

async function loadInventoryTable() {
    const tbody = document.getElementById("inventoryTableBody");
    if (!tbody) return;

    try {
        const params = new URLSearchParams({
            page: currentPage,
            size: pageSize,
            sortBy: currentSortBy,
            sortDirection: currentSortDirection,
            category: currentCategory,
            status: currentStatus,
            search: currentSearch
        });

        const response = await fetch(`${INVENTORY_API}/page?${params.toString()}`, {
            headers: getAuthHeaders()
        });

        if (!response.ok) {
            throw new Error(`Server returned HTTP ${response.status}`);
        }

        const pageData = await response.json();
        renderTableRows(pageData.content || []);
        renderPagination(pageData);

    } catch (error) {
        console.error("Error loading inventory items:", error);
        tbody.innerHTML = `
            <tr>
                <td colspan="8" class="text-center py-4 text-danger">
                    <i class="fa-solid fa-triangle-exclamation me-2"></i> Unable to load inventory records. Please check backend connection.
                </td>
            </tr>
        `;
    }
}

function renderTableRows(items) {
    const tbody = document.getElementById("inventoryTableBody");
    if (!tbody) return;

    if (items.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="8" style="text-align: center; padding: 40px; color: #64748b;">
                    <i class="fa-solid fa-boxes-stacked" style="font-size: 28px; color: #cbd5e1; margin-bottom: 8px; display: block;"></i>
                    No inventory records found matching your filters.
                </td>
            </tr>
        `;
        return;
    }

    let html = "";
    items.forEach(item => {
        const id = item.inventoryId;
        const name = escapeHtml(item.itemName);
        const category = escapeHtml(item.category || "General");
        const qty = Number(item.quantity || 0);
        const unit = escapeHtml(item.unit || "unit");
        const minThreshold = Number(item.minThreshold || 0);
        const cost = Number(item.costPerUnit || 0);
        const totalVal = Number(item.totalValue || (qty * cost)).toLocaleString("en-IN", { minimumFractionDigits: 2 });
        const supplier = escapeHtml(item.supplierName || "—");
        const status = item.status || "IN_STOCK";

        // Meter progress
        let percent = minThreshold > 0 ? Math.min(Math.round((qty / (minThreshold * 2.5)) * 100), 100) : 100;
        let meterClass = "healthy";
        let badgeClass = "in-stock";
        let badgeLabel = "In Stock";

        if (qty <= 0) {
            percent = 0;
            meterClass = "danger";
            badgeClass = "out-of-stock";
            badgeLabel = "Out of Stock";
        } else if (qty <= minThreshold) {
            meterClass = "warning";
            badgeClass = "low-stock";
            badgeLabel = "Low Stock";
        }

        html += `
            <tr>
                <td>
                    <div class="item-name-box">
                        <span class="item-title">${name}</span>
                        <span class="item-category-tag">${category}</span>
                    </div>
                </td>
                <td>
                    <div class="stock-meter-wrap">
                        <div class="stock-meter-label">
                            <span style="font-weight: 700; color: ${qty <= minThreshold ? '#dc2626' : '#1e293b'};">${qty} ${unit}</span>
                        </div>
                        <div class="stock-meter-bar">
                            <div class="stock-meter-fill ${meterClass}" style="width: ${percent}%"></div>
                        </div>
                    </div>
                </td>
                <td>
                    <span style="color: #64748b; font-size: 13px;">${minThreshold} ${unit}</span>
                </td>
                <td>
                    <strong style="color: #1e293b;">₹${cost.toFixed(2)}</strong> <span style="font-size: 12px; color: #64748b;">/ ${unit}</span>
                </td>
                <td>
                    <strong style="color: #1e293b; font-size: 13px;">₹${totalVal}</strong>
                </td>
                <td>
                    <div style="font-size: 13px; font-weight: 600; color: #1e293b;">${supplier}</div>
                    ${item.supplierContact ? `<div style="font-size: 11px; color: #64748b;">${escapeHtml(item.supplierContact)}</div>` : ''}
                </td>
                <td>
                    <span class="badge-stock ${badgeClass}">
                        <i class="fa-solid fa-circle" style="font-size: 6px;"></i> ${badgeLabel}
                    </span>
                </td>
                <td style="text-align: right; padding-right: 20px;">
                    <div class="action-btn-group">
                        <button class="action-btn btn-adjust" title="Quick Stock Movement (Restock / Consume)" onclick="openAdjustModal(${id}, '${name.replace(/'/g, "\\'")}', '${category.replace(/'/g, "\\'")}', ${qty}, '${unit}', ${minThreshold})">
                            <i class="fa-solid fa-arrow-right-arrow-left"></i>
                        </button>
                        <button class="action-btn" title="Edit Item Details" onclick="openEditItemModal(${id})">
                            <i class="fa-solid fa-pen-to-square"></i>
                        </button>
                        <button class="action-btn" title="Audit Movement Logs" onclick="openLogsModal(${id}, '${name.replace(/'/g, "\\'")}')">
                            <i class="fa-solid fa-clock-rotate-left"></i>
                        </button>
                        <button class="action-btn btn-delete" title="Delete Item" onclick="deleteItem(${id}, '${name.replace(/'/g, "\\'")}')">
                            <i class="fa-solid fa-trash-can"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });

    tbody.innerHTML = html;
}

function renderPagination(pageData) {
    const info = document.getElementById("paginationInfo");
    const controls = document.getElementById("paginationControls");
    if (!info || !controls) return;

    const totalElements = pageData.totalElements || 0;
    const totalPages = pageData.totalPages || 0;
    const current = pageData.page || 0;

    const start = totalElements === 0 ? 0 : current * pageSize + 1;
    const end = Math.min((current + 1) * pageSize, totalElements);

    info.textContent = `Showing ${start} to ${end} of ${totalElements} entries`;

    if (totalPages <= 1) {
        controls.innerHTML = "";
        return;
    }

    let html = `
        <button class="page-btn" ${current === 0 ? 'disabled' : ''} onclick="goToPage(${current - 1})">
            <i class="fa-solid fa-chevron-left"></i>
        </button>
    `;

    for (let i = 0; i < totalPages; i++) {
        if (i === 0 || i === totalPages - 1 || (i >= current - 1 && i <= current + 1)) {
            html += `
                <button class="page-btn ${i === current ? 'active' : ''}" onclick="goToPage(${i})">${i + 1}</button>
            `;
        } else if (i === current - 2 || i === current + 2) {
            html += `<span class="px-1 text-muted">...</span>`;
        }
    }

    html += `
        <button class="page-btn" ${current >= totalPages - 1 ? 'disabled' : ''} onclick="goToPage(${current + 1})">
            <i class="fa-solid fa-chevron-right"></i>
        </button>
    `;

    controls.innerHTML = html;
}

function goToPage(page) {
    currentPage = page;
    loadInventoryTable();
}

// =========================================================
// SEARCH & FILTER EVENTS
// =========================================================

function initSearchInput() {
    const searchInput = document.getElementById("inventorySearch");
    const clearBtn = document.getElementById("clearSearchBtn");

    if (searchInput) {
        searchInput.addEventListener("input", function () {
            const val = this.value.trim();
            if (clearBtn) clearBtn.style.display = val ? "inline-block" : "none";

            clearTimeout(searchDebounceTimer);
            searchDebounceTimer = setTimeout(() => {
                currentSearch = val;
                currentPage = 0;
                loadInventoryTable();
            }, 300);
        });
    }
}

function clearSearch() {
    const searchInput = document.getElementById("inventorySearch");
    const clearBtn = document.getElementById("clearSearchBtn");
    if (searchInput) searchInput.value = "";
    if (clearBtn) clearBtn.style.display = "none";
    currentSearch = "";
    currentPage = 0;
    loadInventoryTable();
}

function handleFilterChange() {
    const catSelect = document.getElementById("categoryFilter");
    const sortSelect = document.getElementById("sortByFilter");

    if (catSelect) currentCategory = catSelect.value;
    if (sortSelect) {
        const parts = sortSelect.value.split(":");
        currentSortBy = parts[0];
        currentSortDirection = parts[1] || "ASC";
    }

    currentPage = 0;
    loadInventoryTable();
}

function filterByStatus(status, btn) {
    currentStatus = status;
    currentPage = 0;

    const pills = document.querySelectorAll(".status-pill");
    pills.forEach(p => p.classList.remove("active"));
    if (btn) btn.classList.add("active");

    loadInventoryTable();
}

// =========================================================
// MODAL CONTROLLERS (STANDALONE)
// =========================================================

function showModal(id) {
    const modal = document.getElementById(id);
    if (!modal) return;
    modal.classList.add("show");
    modal.style.display = "flex";
    document.body.classList.add("modal-open");
}

function hideModal(id) {
    const modal = document.getElementById(id);
    if (!modal) return;
    modal.classList.remove("show");
    modal.style.display = "none";
    document.body.classList.remove("modal-open");
}

window.showModal = showModal;
window.hideModal = hideModal;

// =========================================================
// ADD & EDIT MODAL
// =========================================================

function openAddItemModal() {
    const form = document.getElementById("itemForm");
    if (form) form.reset();
    document.getElementById("itemId").value = "";
    document.getElementById("itemModalTitle").innerHTML = '<i class="fa-solid fa-box text-gold" style="margin-right: 8px;"></i> Add Stock Item';
    document.getElementById("saveItemBtn").innerHTML = '<i class="fa-solid fa-floppy-disk" style="margin-right: 6px;"></i> Save Item';

    showModal("itemModal");
}

async function openEditItemModal(id) {
    try {
        const response = await fetch(`${INVENTORY_API}/${id}`, {
            headers: getAuthHeaders()
        });

        if (!response.ok) throw new Error("Could not fetch item details");

        const item = await response.json();

        document.getElementById("itemId").value = item.inventoryId;
        document.getElementById("itemName").value = item.itemName || "";
        document.getElementById("itemCategory").value = item.category || "";
        document.getElementById("itemUnit").value = item.unit || "kg";
        document.getElementById("itemQuantity").value = item.quantity;
        document.getElementById("itemMinThreshold").value = item.minThreshold;
        document.getElementById("itemCostPerUnit").value = item.costPerUnit;
        document.getElementById("itemSupplierName").value = item.supplierName || "";
        document.getElementById("itemSupplierContact").value = item.supplierContact || "";

        document.getElementById("itemModalTitle").innerHTML = '<i class="fa-solid fa-pen-to-square text-gold" style="margin-right: 8px;"></i> Edit Stock Item';
        document.getElementById("saveItemBtn").innerHTML = '<i class="fa-solid fa-check" style="margin-right: 6px;"></i> Update Item';

        showModal("itemModal");

    } catch (error) {
        showToast("Inventory Error", "Failed to load item for editing: " + error.message, "error");
    }
}

async function saveItem(e) {
    e.preventDefault();

    const id = document.getElementById("itemId").value;
    const isEdit = id && id.trim() !== "";

    const payload = {
        itemName: document.getElementById("itemName").value.trim(),
        category: document.getElementById("itemCategory").value.trim(),
        unit: document.getElementById("itemUnit").value,
        quantity: parseFloat(document.getElementById("itemQuantity").value),
        minThreshold: parseFloat(document.getElementById("itemMinThreshold").value),
        costPerUnit: parseFloat(document.getElementById("itemCostPerUnit").value),
        supplierName: document.getElementById("itemSupplierName").value.trim(),
        supplierContact: document.getElementById("itemSupplierContact").value.trim()
    };

    try {
        const url = isEdit ? `${INVENTORY_API}/${id}` : INVENTORY_API;
        const method = isEdit ? "PUT" : "POST";

        const response = await fetch(url, {
            method: method,
            headers: getAuthHeaders(),
            body: JSON.stringify(payload)
        });

        const resultText = await response.text();

        if (!response.ok) {
            let errorMsg = resultText;
            try {
                const parsed = JSON.parse(resultText);
                errorMsg = parsed.message || resultText;
            } catch (err) {}
            throw new Error(errorMsg);
        }

        // Close modal
        hideModal("itemModal");

        showToast("Success", isEdit ? "Item updated successfully" : "Item added to inventory", "success");
        loadInventorySummary();
        loadCategoriesDropdown();
        loadInventoryTable();

    } catch (error) {
        showToast("Save Failed", error.message, "error");
    }
}

// =========================================================
// QUICK STOCK ADJUSTMENT
// =========================================================

function selectActionType(action) {
    const restock = document.getElementById("actionRestock");
    const consume = document.getElementById("actionConsume");
    const waste = document.getElementById("actionWaste");

    const labelRestock = document.getElementById("labelRestock");
    const labelConsume = document.getElementById("labelConsume");
    const labelWaste = document.getElementById("labelWaste");

    if (labelRestock) labelRestock.classList.remove("selected");
    if (labelConsume) labelConsume.classList.remove("selected");
    if (labelWaste) labelWaste.classList.remove("selected");

    if (action === "RESTOCK" && restock) {
        restock.checked = true;
        if (labelRestock) labelRestock.classList.add("selected");
    } else if (action === "CONSUME" && consume) {
        consume.checked = true;
        if (labelConsume) labelConsume.classList.add("selected");
    } else if (action === "WASTAGE" && waste) {
        waste.checked = true;
        if (labelWaste) labelWaste.classList.add("selected");
    }
    updateAdjustPreview();
}
window.selectActionType = selectActionType;

function openAdjustModal(id, name, category, quantity, unit, minThreshold) {
    currentAdjustItem = { id, name, category, quantity, unit, minThreshold };

    document.getElementById("adjustItemId").value = id;
    document.getElementById("adjustItemName").textContent = name;
    document.getElementById("adjustItemCategory").textContent = category;
    document.getElementById("adjustCurrentStock").textContent = `${quantity} ${unit}`;
    document.getElementById("adjustUnitLabel").textContent = unit;
    document.getElementById("adjustQuantity").value = "";
    document.getElementById("adjustReason").value = "";
    
    selectActionType("RESTOCK");
    updateAdjustPreview();

    showModal("adjustModal");
}

function updateAdjustPreview() {
    if (!currentAdjustItem) return;

    const action = document.querySelector('input[name="adjustAction"]:checked')?.value || "RESTOCK";
    const qtyVal = parseFloat(document.getElementById("adjustQuantity").value) || 0;
    const currentQty = currentAdjustItem.quantity;
    let projected = currentQty;

    if (action === "RESTOCK") {
        projected = currentQty + qtyVal;
    } else if (action === "CONSUME" || action === "WASTAGE") {
        projected = currentQty - qtyVal;
    }

    projected = Math.round(projected * 100) / 100;
    const projectedEl = document.getElementById("adjustProjectedStock");

    if (projected < 0) {
        projectedEl.textContent = `${projected} ${currentAdjustItem.unit} (INSUFFICIENT STOCK!)`;
        projectedEl.style.color = "#dc2626";
    } else {
        projectedEl.textContent = `${projected} ${currentAdjustItem.unit}`;
        projectedEl.style.color = projected <= currentAdjustItem.minThreshold ? "#d97706" : "#059669";
    }
}

async function submitStockAdjustment(e) {
    e.preventDefault();

    if (!currentAdjustItem) return;

    const id = currentAdjustItem.id;
    const actionType = document.querySelector('input[name="adjustAction"]:checked')?.value || "RESTOCK";
    const quantity = parseFloat(document.getElementById("adjustQuantity").value);
    const reason = document.getElementById("adjustReason").value.trim();
    const currentUser = localStorage.getItem("username") || "STAFF";

    if (isNaN(quantity) || quantity <= 0) {
        showToast("Invalid Quantity", "Please enter a valid quantity greater than zero.", "warning");
        return;
    }

    try {
        const response = await fetch(`${INVENTORY_API}/${id}/adjust-stock`, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify({
                quantity: quantity,
                actionType: actionType,
                reason: reason,
                performedBy: currentUser
            })
        });

        const resultText = await response.text();

        if (!response.ok) {
            let errorMsg = resultText;
            try {
                const parsed = JSON.parse(resultText);
                errorMsg = parsed.message || resultText;
            } catch (err) {}
            throw new Error(errorMsg);
        }

        // Hide modal
        hideModal("adjustModal");

        showToast("Stock Adjusted", "Inventory stock updated successfully", "success");
        loadInventorySummary();
        loadInventoryTable();

    } catch (error) {
        showToast("Adjustment Failed", error.message, "error");
    }
}

// =========================================================
// AUDIT LOGS MODAL
// =========================================================

async function openLogsModal(id, name) {
    document.getElementById("logsItemTitle").textContent = name;
    const tbody = document.getElementById("logsTableBody");
    tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 24px; color: #64748b;"><i class="fa-solid fa-spinner fa-spin text-gold" style="margin-right: 6px;"></i> Loading audit history...</td></tr>';

    showModal("logsModal");

    try {
        const response = await fetch(`${INVENTORY_API}/${id}/logs`, {
            headers: getAuthHeaders()
        });

        if (!response.ok) throw new Error("Could not fetch logs");

        const logs = await response.json();

        if (!logs || logs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 24px; color: #64748b;">No stock movement logs found for this item.</td></tr>';
            return;
        }

        let html = "";
        logs.forEach(log => {
            let badgeClass = "badge-stock";
            let colorStyle = "background: #f1f5f9; color: #475569; border: 1px solid #cbd5e1;";
            if (log.actionType === "RESTOCK") colorStyle = "background: #ecfdf5; color: #059669; border: 1px solid #a7f3d0;";
            else if (log.actionType === "CONSUME") colorStyle = "background: #f0f9ff; color: #0284c7; border: 1px solid #bae6fd;";
            else if (log.actionType === "WASTAGE") colorStyle = "background: #fef2f2; color: #dc2626; border: 1px solid #fecaca;";

            const dateStr = log.createdAt ? new Date(log.createdAt).toLocaleString("en-IN") : "—";

            html += `
                <tr>
                    <td style="color: #64748b; font-size: 12px;">${dateStr}</td>
                    <td><span class="badge-stock" style="${colorStyle}">${escapeHtml(log.actionType)}</span></td>
                    <td style="font-weight: 700; color: #1e293b;">${log.quantity}</td>
                    <td style="font-size: 13px;">${log.previousQuantity} &rarr; <strong style="color: #1e293b;">${log.newQuantity}</strong></td>
                    <td style="color: #64748b; font-size: 12px;">${escapeHtml(log.reason || '—')}</td>
                    <td style="font-size: 12px; color: #334155;">${escapeHtml(log.performedBy || 'System')}</td>
                </tr>
            `;
        });

        tbody.innerHTML = html;

    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="6" style="text-align: center; padding: 24px; color: #dc2626;">Error: ${error.message}</td></tr>`;
    }
}

// =========================================================
// DELETE ITEM
// =========================================================

async function deleteItem(id, name) {
    if (!confirm(`Are you sure you want to delete '${name}' from inventory?\nThis will also remove all its movement audit logs.`)) {
        return;
    }

    try {
        const response = await fetch(`${INVENTORY_API}/${id}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });

        const resultText = await response.text();

        if (!response.ok) {
            throw new Error(resultText || "Failed to delete item");
        }

        showToast("Item Deleted", `'${name}' removed from inventory`, "success");
        loadInventorySummary();
        loadCategoriesDropdown();
        loadInventoryTable();

    } catch (error) {
        showToast("Delete Failed", error.message, "error");
    }
}

// =========================================================
// ESCAPE HTML UTILITY & CSV HELPER
// =========================================================

function escapeHtml(str) {
    if (!str) return "";
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

function csvEscape(value) {
    return '"' + String(value ?? "").replace(/"/g, '""') + '"';
}

// =========================================================
// EXPORT INVENTORY TO CSV / EXCEL
// =========================================================

async function exportInventoryCSV() {
    try {
        const response = await fetch(INVENTORY_API, {
            headers: getAuthHeaders()
        });

        if (!response.ok) {
            throw new Error("Failed to fetch inventory records for export.");
        }

        const items = await response.json();

        if (!items || items.length === 0) {
            showToast("Export Notice", "No inventory items found to export.", "warning");
            return;
        }

        const headers = [
            "Item ID",
            "Item Name",
            "Category",
            "Quantity",
            "Unit",
            "Min Alert Threshold",
            "Cost Per Unit (INR)",
            "Total Value (INR)",
            "Supplier Name",
            "Supplier Contact",
            "Stock Status"
        ];

        let csvContent = headers.map(csvEscape).join(",") + "\n";

        items.forEach(item => {
            const qty = Number(item.quantity || 0);
            const cost = Number(item.costPerUnit || 0);
            const total = Number(item.totalValue || (qty * cost));
            const minT = Number(item.minThreshold || 0);

            let status = "IN_STOCK";
            if (qty <= 0) status = "OUT_OF_STOCK";
            else if (qty <= minT) status = "LOW_STOCK";

            const row = [
                item.inventoryId,
                item.itemName,
                item.category || "General",
                qty,
                item.unit || "unit",
                minT,
                cost.toFixed(2),
                total.toFixed(2),
                item.supplierName || "—",
                item.supplierContact || "—",
                status
            ];

            csvContent += row.map(csvEscape).join(",") + "\n";
        });

        const blob = new Blob(["\uFEFF", csvContent], { type: "text/csv;charset=utf-8;" });
        const url = URL.createObjectURL(blob);
        const link = document.createElement("a");
        const today = new Date().toISOString().split("T")[0];

        link.href = url;
        link.download = `Sankalp_Inventory_Stock_Report_${today}.csv`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        URL.revokeObjectURL(url);
        showToast("Export Complete", "Inventory CSV report downloaded", "success");

    } catch (error) {
        console.error("CSV Export error:", error);
        showToast("Export Failed", error.message, "error");
    }
}

// =========================================================
// EXPORT / PRINT INVENTORY PDF REPORT
// =========================================================

async function exportInventoryPDF() {
    try {
        const [itemsRes, summaryRes] = await Promise.all([
            fetch(INVENTORY_API, { headers: getAuthHeaders() }),
            fetch(`${INVENTORY_API}/summary`, { headers: getAuthHeaders() })
        ]);

        if (!itemsRes.ok) throw new Error("Failed to fetch inventory list.");

        const items = await itemsRes.json();
        const summary = summaryRes.ok ? await summaryRes.json() : {};

        if (!items || items.length === 0) {
            showToast("Print Notice", "No inventory records to print.", "warning");
            return;
        }

        const now = new Date();
        const dateStr = now.toLocaleDateString("en-IN", {
            day: "2-digit",
            month: "long",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit",
            hour12: true
        });

        const totalVal = Number(summary.totalInventoryValue || 0).toLocaleString("en-IN", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });

        let tableRowsHtml = "";
        items.forEach((item, index) => {
            const qty = Number(item.quantity || 0);
            const cost = Number(item.costPerUnit || 0);
            const total = Number(item.totalValue || (qty * cost)).toFixed(2);
            const minT = Number(item.minThreshold || 0);

            let statusBadge = `<span style="color:#16a34a; font-weight:600;">In Stock</span>`;
            if (qty <= 0) {
                statusBadge = `<span style="color:#dc2626; font-weight:600;">Out of Stock</span>`;
            } else if (qty <= minT) {
                statusBadge = `<span style="color:#d97706; font-weight:600;">Low Stock</span>`;
            }

            tableRowsHtml += `
                <tr style="border-bottom: 1px solid #e2e8f0; font-size: 13px;">
                    <td style="padding: 8px;">${index + 1}</td>
                    <td style="padding: 8px; font-weight:600;">${escapeHtml(item.itemName)}</td>
                    <td style="padding: 8px;">${escapeHtml(item.category || "General")}</td>
                    <td style="padding: 8px;">${qty} ${escapeHtml(item.unit || "")}</td>
                    <td style="padding: 8px;">${minT} ${escapeHtml(item.unit || "")}</td>
                    <td style="padding: 8px;">₹${cost.toFixed(2)}</td>
                    <td style="padding: 8px; font-weight:600;">₹${total}</td>
                    <td style="padding: 8px;">${escapeHtml(item.supplierName || "—")}</td>
                    <td style="padding: 8px;">${statusBadge}</td>
                </tr>
            `;
        });

        const printWindow = window.open("", "_blank", "width=900,height=700");
        if (!printWindow) {
            showToast("Popup Blocked", "Please allow popups in browser to print/export PDF.", "warning");
            return;
        }

        printWindow.document.write(`
            <!DOCTYPE html>
            <html>
            <head>
                <title>Sankalp RMS - Inventory Valuation Report</title>
                <style>
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 25px; color: #1e293b; }
                    .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #d99400; padding-bottom: 12px; margin-bottom: 20px; }
                    .header h1 { margin: 0; color: #b45309; font-size: 22px; }
                    .header p { margin: 2px 0 0 0; color: #64748b; font-size: 12px; }
                    .kpi-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 12px; margin-bottom: 20px; }
                    .kpi-box { border: 1px solid #e2e8f0; border-radius: 8px; padding: 10px 14px; background: #f8fafc; }
                    .kpi-box .label { font-size: 11px; text-transform: uppercase; color: #64748b; margin-bottom: 4px; }
                    .kpi-box .val { font-size: 18px; font-weight: bold; color: #0f172a; }
                    table { width: 100%; border-collapse: collapse; margin-top: 10px; }
                    th { background-color: #f1f5f9; text-align: left; padding: 9px 8px; font-size: 12px; text-transform: uppercase; color: #475569; border-bottom: 2px solid #cbd5e1; }
                    .footer { margin-top: 30px; border-top: 1px solid #e2e8f0; padding-top: 10px; font-size: 11px; color: #94a3b8; display: flex; justify-content: space-between; }
                    @media print {
                        body { margin: 0; }
                        .no-print { display: none; }
                    }
                </style>
            </head>
            <body>
                <div class="header">
                    <div>
                        <h1>SANKALP RESTAURANT MANAGEMENT SYSTEM</h1>
                        <p>Official Stock Inventory & Valuation Report</p>
                    </div>
                    <div style="text-align: right;">
                        <p><strong>Generated:</strong> ${dateStr}</p>
                        <p><strong>Status:</strong> Active Stock</p>
                    </div>
                </div>

                <div class="kpi-grid">
                    <div class="kpi-box">
                        <div class="label">Total Stock Items</div>
                        <div class="val">${summary.totalItems || items.length}</div>
                    </div>
                    <div class="kpi-box">
                        <div class="label">Healthy In-Stock</div>
                        <div class="val" style="color:#16a34a;">${summary.inStockItems || 0}</div>
                    </div>
                    <div class="kpi-box">
                        <div class="label">Low / Out of Stock</div>
                        <div class="val" style="color:#dc2626;">${(summary.lowStockItems || 0) + (summary.outOfStockItems || 0)}</div>
                    </div>
                    <div class="kpi-box">
                        <div class="label">Total Stock Value</div>
                        <div class="val" style="color:#b45309;">₹${totalVal}</div>
                    </div>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Item Name</th>
                            <th>Category</th>
                            <th>Current Stock</th>
                            <th>Min Threshold</th>
                            <th>Unit Cost</th>
                            <th>Total Value</th>
                            <th>Supplier</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${tableRowsHtml}
                    </tbody>
                </table>

                <div class="footer">
                    <span>Sankalp Restaurant Management System - Confidential Report</span>
                    <span>Page 1 of 1</span>
                </div>

                <script>
                    window.onload = function() {
                        window.print();
                    };
                </script>
            </body>
            </html>
        `);
        printWindow.document.close();

    } catch (error) {
        console.error("PDF Export error:", error);
        showToast("PDF Export Failed", error.message, "error");
    }
}
