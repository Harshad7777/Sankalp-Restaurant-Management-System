"use strict";

/* =========================================================
   SANKALP RMS - TABLE MANAGEMENT
========================================================= */

console.log("======================================");
console.log("Sankalp Table Management Loaded");
console.log("======================================");


/* =========================================================
   CONFIGURATION
========================================================= */

const BACKEND_URL = window.location.origin.includes(":8081")
    ? "http://localhost:8080"
    : (window.location.origin + "/RMS_backend");

const TABLE_API =
    BACKEND_URL + "/api/table";


/* =========================================================
   CONTEXT PATH
========================================================= */

const contextMeta =
    document.querySelector(
        'meta[name="app-context"]'
    );

const CONTEXT_PATH =
    contextMeta
        ? contextMeta.getAttribute("content") || ""
        : "";
 

/* =========================================================
   VARIABLES
========================================================= */

let allTables = [];

let filteredTables = [];

let editingTableId = null;

let deletingTableId = null;

let currentTablePage = 1;

const TABLE_PAGE_SIZE = 8;


/* =========================================================
   TOKEN
========================================================= */

function getToken() {

    return localStorage.getItem("token");

}


/* =========================================================
   HEADERS
========================================================= */

function getHeaders() {

    const token =
        getToken();

    const headers = {

        "Accept":
            "application/json",

        "Content-Type":
            "application/json"

    };

    if (token) {

        headers["Authorization"] =
            "Bearer " + token;

    }

    return headers;

}


/* =========================================================
   PAGE LOAD
========================================================= */

document.addEventListener(
    "DOMContentLoaded",
    function () {

        console.log(
            "Table page initialized"
        );


        if (!getToken()) {

            window.location.href =
                CONTEXT_PATH +
                "/login.jsp";

            return;

        }


        initializeTablePage();

    }
);


/* =========================================================
   INITIALIZE
========================================================= */

function initializeTablePage() {

    setCurrentDate();

    loadAdminInfo();

    setupTableEvents();

    setupReservationEvents();

    loadTables();

    loadReservations();

    // Auto-refresh live tables and reservations every 10 seconds
    if (!window._tableRefreshInterval) {
        window._tableRefreshInterval = setInterval(function () {
            const formSection = document.getElementById("tableFormSection");
            const isFormOpen = formSection && formSection.style.display !== "none" && formSection.classList.contains("active");
            if (!isFormOpen && document.visibilityState === "visible") {
                loadTables();
                loadReservations();
            }
        }, 10000);
    }

}


/* =========================================================
   EVENTS
========================================================= */

function setupTableEvents() {


    /* =====================================================
       ADD
    ===================================================== */

    document
        .getElementById("openAddTableBtn")
        ?.addEventListener(
            "click",
            openAddTableForm
        );


    /* =====================================================
       CLOSE
    ===================================================== */

    document
        .getElementById("closeTableForm")
        ?.addEventListener(
            "click",
            closeTableForm
        );


    document
        .getElementById("cancelTableForm")
        ?.addEventListener(
            "click",
            closeTableForm
        );


    /* =====================================================
       FORM
    ===================================================== */

    document
        .getElementById("tableForm")
        ?.addEventListener(
            "submit",
            function (event) {

                event.preventDefault();

                saveTable();

            }
        );


    /* =====================================================
       SEARCH
    ===================================================== */

    document
        .getElementById("tableSearch")
        ?.addEventListener(
            "input",
            filterTables
        );


    /* =====================================================
       GLOBAL SEARCH
    ===================================================== */

    const globalSearch =
        document.getElementById(
            "globalSearch"
        );

    if (globalSearch) {

        globalSearch.addEventListener(
            "input",
            function () {

                const localSearch =
                    document.getElementById(
                        "tableSearch"
                    );

                if (localSearch) {

                    localSearch.value =
                        globalSearch.value;

                    filterTables();

                }

            }
        );

    }


    /* =====================================================
       STATUS FILTER
    ===================================================== */

    document
        .getElementById("tableStatusFilter")
        ?.addEventListener(
            "change",
            filterTables
        );


    /* =====================================================
       REFRESH
    ===================================================== */

    document
        .getElementById("refreshTables")
        ?.addEventListener(
            "click",
            loadTables
        );


    /* =====================================================
       PAGINATION
    ===================================================== */

    document
        .getElementById("tableFirstPage")
        ?.addEventListener(
            "click",
            function () {

                currentTablePage =
                    1;

                renderTables();

            }
        );


    document
        .getElementById("tablePrevPage")
        ?.addEventListener(
            "click",
            function () {

                if (
                    currentTablePage >
                    1
                ) {

                    currentTablePage--;

                    renderTables();

                }

            }
        );


    document
        .getElementById("tableNextPage")
        ?.addEventListener(
            "click",
            function () {

                const totalPages =
                    getTableTotalPages();

                if (
                    currentTablePage <
                    totalPages
                ) {

                    currentTablePage++;

                    renderTables();

                }

            }
        );


    document
        .getElementById("tableLastPage")
        ?.addEventListener(
            "click",
            function () {

                const totalPages =
                    getTableTotalPages();

                currentTablePage =
                    totalPages || 1;

                renderTables();

            }
        );


    /* =====================================================
       DELETE
    ===================================================== */

    document
        .getElementById("cancelTableDelete")
        ?.addEventListener(
            "click",
            closeDeleteModal
        );


    document
        .getElementById("confirmTableDelete")
        ?.addEventListener(
            "click",
            confirmDelete
        );


    /* =====================================================
       OVERLAY CLICKS
    ===================================================== */

    document
        .getElementById("tablePanelOverlay")
        ?.addEventListener(
            "click",
            function (event) {

                if (
                    event.target ===
                    this
                ) {

                    closeTableForm();

                }

            }
        );


    document
        .getElementById("tableDeleteOverlay")
        ?.addEventListener(
            "click",
            function (event) {

                if (
                    event.target ===
                    this
                ) {

                    closeDeleteModal();

                }

            }
        );


    /* =====================================================
       ESC
    ===================================================== */

    document.addEventListener(
        "keydown",
        function (event) {

            if (
                event.key === "Escape"
            ) {

                closeTableForm();

                closeDeleteModal();

            }

        }
    );

}


/* =========================================================
   LOAD TABLES
========================================================= */

async function loadTables() {

    const tbody =
        document.getElementById(
            "tableDataBody"
        );


    if (!tbody) {

        return;

    }


    tbody.innerHTML = `

        <tr>

            <td
                colspan="6"
                class="table-loading">

                <i class="fa-solid fa-spinner fa-spin"></i>

                Loading tables...

            </td>

        </tr>

    `;


    try {

        const response =
            await fetch(
                TABLE_API,
                {

                    method:
                        "GET",

                    headers:
                        getHeaders()

                }
            );


        console.log(
            "Table HTTP Status:",
            response.status
        );


        if (
            response.status ===
                401 ||
            response.status ===
                403
        ) {

            handleUnauthorized();

            return;

        }


        if (!response.ok) {

            const text =
                await response.text();

            console.error(
                "TABLE API ERROR:",
                text
            );

            throw new Error(
                "Unable to load tables. HTTP " +
                response.status
            );

        }


        const data =
            await response.json();


        allTables =
            Array.isArray(data)
                ? data
                : [];


        filteredTables =
            [...allTables];


        currentTablePage =
            1;


        renderTables();

        updateTableStats();

    }
    catch (error) {

        console.error(
            "LOAD TABLE ERROR:",
            error
        );


        tbody.innerHTML = `

            <tr>

                <td
                    colspan="6"
                    class="table-empty">

                    <i class="fa-solid fa-triangle-exclamation"></i>

                    Failed to load tables.

                </td>

            </tr>

        `;


        updateTableStats();

    }

}


/* =========================================================
   FILTER
========================================================= */

function filterTables() {

    const searchInput =
        document.getElementById(
            "tableSearch"
        );

    const statusFilter =
        document.getElementById(
            "tableStatusFilter"
        );


    const searchText =
        searchInput
            ? searchInput.value
                .trim()
                .toLowerCase()
            : "";


    const statusValue =
        statusFilter
            ? statusFilter.value
            : "ALL";


    filteredTables =
        allTables.filter(
            function (table) {

                const tableNumber =
                    String(
                        getTableNumber(
                            table
                        )
                    ).toLowerCase();


                const location =
                    String(
                        getTableLocation(
                            table
                        )
                    ).toLowerCase();


                const id =
                    String(
                        getTableId(
                            table
                        )
                    );


                const status =
                    getTableStatus(
                        table
                    );


                const capacity =
                    String(
                        getTableCapacity(
                            table
                        )
                    );


                const matchesSearch =
                    tableNumber.includes(
                        searchText
                    ) ||
                    location.includes(
                        searchText
                    ) ||
                    id.includes(
                        searchText
                    ) ||
                    capacity.includes(
                        searchText
                    );


                const matchesStatus =
                    statusValue ===
                        "ALL" ||
                    status ===
                        statusValue;


                return (
                    matchesSearch &&
                    matchesStatus
                );

            }
        );


    currentTablePage =
        1;


    renderTables();

}


/* =========================================================
   RENDER
========================================================= */

function renderTables() {

    const tbody =
        document.getElementById(
            "tableDataBody"
        );


    if (!tbody) {

        return;

    }


    const totalPages =
        getTableTotalPages();


    if (
        totalPages > 0 &&
        currentTablePage >
            totalPages
    ) {

        currentTablePage =
            totalPages;

    }


    const startIndex =
        (
            currentTablePage -
            1
        ) *
        TABLE_PAGE_SIZE;


    const pageTables =
        filteredTables.slice(
            startIndex,
            startIndex +
                TABLE_PAGE_SIZE
        );


    if (
        pageTables.length ===
        0
    ) {

        tbody.innerHTML = `

            <tr>

                <td
                    colspan="6"
                    class="table-empty">

                    <i class="fa-solid fa-chair"></i>

                    No tables found.

                </td>

            </tr>

        `;

        updateTablePagination(
            totalPages
        );

        return;

    }


    let rows = "";


    pageTables.forEach(
        function (table) {

            const id =
                getTableId(
                    table
                );


            const number =
                getTableNumber(
                    table
                );


            const capacity =
                getTableCapacity(
                    table
                );


            const location =
                getTableLocation(
                    table
                );


            const status =
                getTableStatus(
                    table
                );


            const statusClass =
                getStatusClass(
                    status
                );


            rows += `

                <tr>

                    <td>

                        ${escapeHTML(id)}

                    </td>


                    <td>

                        <div
                            class="table-number-cell">

                            <div
                                class="table-number-icon">

                                <i class="fa-solid fa-chair"></i>

                            </div>

                            <strong>

                                Table
                                ${escapeHTML(number)}

                            </strong>

                        </div>

                    </td>


                    <td>

                        <span
                            class="capacity-cell">

                            <i class="fa-solid fa-users"></i>

                            ${escapeHTML(capacity)}

                        </span>

                    </td>


                    <td>

                        <span
                            class="location-cell">

                            ${escapeHTML(
                                location ||
                                "Main Hall"
                            )}

                        </span>

                    </td>


                    <td>

                        <span
                            class="table-status
                            ${statusClass}">

                            ${escapeHTML(status)}

                        </span>

                    </td>


                    <td>

                        <div
                            class="table-actions">

                            <button
                                type="button"
                                class="table-action-btn
                                       table-edit-btn"
                                title="Edit"
                                onclick="editTable(${Number(id)})">

                                <i
                                    class="fa-solid fa-pen"></i>

                            </button>


                            <button
                                type="button"
                                class="table-action-btn
                                       table-delete-btn"
                                title="Delete"
                                onclick="openDeleteModal(${Number(id)})">

                                <i
                                    class="fa-solid fa-trash"></i>

                            </button>

                        </div>

                    </td>

                </tr>

            `;

        }
    );


    tbody.innerHTML =
        rows;


    updateTablePagination(
        totalPages
    );

}


/* =========================================================
   TOTAL PAGES
========================================================= */

function getTableTotalPages() {

    return Math.ceil(
        filteredTables.length /
        TABLE_PAGE_SIZE
    );

}


/* =========================================================
   PAGINATION
========================================================= */

function updateTablePagination(
    totalPages
) {

    const current =
        document.getElementById(
            "tableCurrentPage"
        );

    const first =
        document.getElementById(
            "tableFirstPage"
        );

    const prev =
        document.getElementById(
            "tablePrevPage"
        );

    const next =
        document.getElementById(
            "tableNextPage"
        );

    const last =
        document.getElementById(
            "tableLastPage"
        );


    if (current) {

        current.innerText =
            currentTablePage;

    }


    if (first) {

        first.disabled =
            currentTablePage <= 1 ||
            totalPages === 0;

    }


    if (prev) {

        prev.disabled =
            currentTablePage <= 1 ||
            totalPages === 0;

    }


    if (next) {

        next.disabled =
            totalPages === 0 ||
            currentTablePage >=
                totalPages;

    }


    if (last) {

        last.disabled =
            totalPages === 0 ||
            currentTablePage >=
                totalPages;

    }


    const total =
        filteredTables.length;


    const start =
        total === 0
            ? 0
            : (
                (
                    currentTablePage -
                    1
                ) *
                TABLE_PAGE_SIZE
            ) + 1;


    const end =
        Math.min(
            currentTablePage *
                TABLE_PAGE_SIZE,
            total
        );


    const text =
        document.getElementById(
            "tableShowingText"
        );


    if (text) {

        text.innerText =
            total === 0
                ? "Showing 0 tables"
                : `Showing ${start} to ${end} of ${total} tables`;

    }

}


/* =========================================================
   STATS
========================================================= */

function updateTableStats() {

    let available = 0;
    let occupied = 0;
    let reserved = 0;


    allTables.forEach(
        function (table) {

            const status =
                getTableStatus(
                    table
                );


            if (
                status ===
                "AVAILABLE"
            ) {

                available++;

            }

            if (
                status ===
                "OCCUPIED"
            ) {

                occupied++;

            }

            if (
                status ===
                "RESERVED"
            ) {

                reserved++;

            }

        }
    );


    setText(
        "totalTableCount",
        allTables.length
    );

    setText(
        "availableTableCount",
        available
    );

    setText(
        "occupiedTableCount",
        occupied
    );

    setText(
        "reservedTableCount",
        reserved
    );

}


/* =========================================================
   OPEN ADD FORM
========================================================= */

function openAddTableForm() {

    editingTableId =
        null;


    clearTableForm();


    setText(
        "tableFormTitle",
        "Add New Table"
    );


    openTablePanel();

}


/* =========================================================
   EDIT TABLE
========================================================= */

async function editTable(
    id
) {

    if (!id) {

        return;

    }


    try {

        const response =
            await fetch(
                TABLE_API +
                "/" +
                encodeURIComponent(
                    id
                ),
                {

                    method:
                        "GET",

                    headers:
                        getHeaders()

                }
            );


        if (
            response.status ===
                401 ||
            response.status ===
                403
        ) {

            handleUnauthorized();

            return;

        }


        if (!response.ok) {

            throw new Error(
                "Unable to load table."
            );

        }


        const table =
            await response.json();


        editingTableId =
            getTableId(
                table
            );


        setValue(
            "tableId",
            editingTableId
        );


        setValue(
            "tableNumber",
            getTableNumber(
                table
            )
        );


        setValue(
            "tableCapacity",
            getTableCapacity(
                table
            )
        );


        setValue(
            "tableLocation",
            getTableLocation(
                table
            )
        );


        setValue(
            "tableStatus",
            getTableStatus(
                table
            )
        );


        setText(
            "tableFormTitle",
            "Edit Table"
        );


        openTablePanel();

    }
    catch (error) {

        console.error(
            "EDIT TABLE ERROR:",
            error
        );


        showToast(
            error.message ||
            "Unable to load table.",
            "error"
        );

    }

}


/* =========================================================
   OPEN PANEL (SPLIT LAYOUT)
========================================================= */

function openTablePanel() {

    const layout =
        document.getElementById(
            "tableLayout"
        );

    const panel =
        document.getElementById(
            "tableFormPanel"
        );

    if (layout) {

        layout.classList.add(
            "form-open"
        );

    }

    if (panel) {

        panel.classList.add(
            "show"
        );

        panel.setAttribute(
            "aria-hidden",
            "false"
        );

    }

    setTimeout(
        function () {

            document
                .getElementById(
                    "tableNumber"
                )
                ?.focus();

        },
        100
    );

}


/* =========================================================
   CLOSE PANEL
========================================================= */

function closeTableForm() {

    const layout =
        document.getElementById(
            "tableLayout"
        );

    const panel =
        document.getElementById(
            "tableFormPanel"
        );

    if (layout) {

        layout.classList.remove(
            "form-open"
        );

    }

    if (panel) {

        panel.classList.remove(
            "show"
        );

        panel.setAttribute(
            "aria-hidden",
            "true"
        );

    }

    editingTableId =
        null;

    clearTableForm();

}


/* =========================================================
   CLEAR FORM
========================================================= */

function clearTableForm() {

    const form =
        document.getElementById(
            "tableForm"
        );


    if (form) {

        form.reset();

    }


    setValue(
        "tableId",
        ""
    );


    setValue(
        "tableStatus",
        "AVAILABLE"
    );

}


/* =========================================================
   SAVE / UPDATE
========================================================= */

async function saveTable() {

    const tableNumber =
        getValue(
            "tableNumber"
        );


    const capacity =
        Number(
            getValue(
                "tableCapacity"
            )
        );


    const location =
        getValue(
            "tableLocation"
        );


    const status =
        getValue(
            "tableStatus"
        );


    if (!tableNumber) {

        showToast(
            "Table number is required.",
            "error"
        );

        return;

    }


    if (
        !capacity ||
        capacity < 1
    ) {

        showToast(
            "Enter a valid table capacity.",
            "error"
        );

        return;

    }


    const payload = {

        tableNumber:
            tableNumber,

        capacity:
            capacity,

        location:
            location,

        tableStatus:
            status || "AVAILABLE",

        status:
            status || "AVAILABLE"

    };


    const button =
        document.getElementById(
            "saveTableBtn"
        );


    if (button) {

        button.disabled =
            true;

        button.innerHTML = `

            <i class="fa-solid fa-spinner fa-spin"></i>

            Saving...

        `;

    }


    try {

        let response;


        if (
            editingTableId !==
                null &&
            editingTableId !==
                undefined
        ) {

            response =
                await fetch(
                    TABLE_API +
                    "/" +
                    encodeURIComponent(
                        editingTableId
                    ),
                    {

                        method:
                            "PUT",

                        headers:
                            getHeaders(),

                        body:
                            JSON.stringify(
                                payload
                            )

                    }
                );

        }
        else {

            response =
                await fetch(
                    TABLE_API,
                    {

                        method:
                            "POST",

                        headers:
                            getHeaders(),

                        body:
                            JSON.stringify(
                                payload
                            )

                    }
                );

        }


        if (
            response.status ===
                401 ||
            response.status ===
                403
        ) {

            handleUnauthorized();

            return;

        }


        const result =
            await parseResponse(
                response
            );


        if (!response.ok) {

            throw new Error(
                getServerMessage(
                    result,
                    "Unable to save table."
                )
            );

        }


        showToast(
            editingTableId !==
                null
                ? "Table updated successfully."
                : "Table added successfully.",
            "success"
        );


        closeTableForm();

        await loadTables();

    }
    catch (error) {

        console.error(
            "SAVE TABLE ERROR:",
            error
        );


        showToast(
            error.message ||
            "Unable to save table.",
            "error"
        );

    }
    finally {

        if (button) {

            button.disabled =
                false;

            button.innerHTML = `

                <i class="fa-solid fa-check"></i>

                Save Table

            `;

        }

    }

}


/* =========================================================
   OPEN DELETE MODAL
========================================================= */

function openDeleteModal(
    id
) {

    deletingTableId =
        Number(id);


    const overlay =
        document.getElementById(
            "tableDeleteOverlay"
        );


    if (overlay) {

        overlay.classList.add(
            "active"
        );

    }

}


/* =========================================================
   CLOSE DELETE MODAL
========================================================= */

function closeDeleteModal() {

    deletingTableId =
        null;


    const overlay =
        document.getElementById(
            "tableDeleteOverlay"
        );


    if (overlay) {

        overlay.classList.remove(
            "active"
        );

    }

}


/* =========================================================
   DELETE
========================================================= */

async function confirmDelete() {

    if (!deletingTableId) {

        return;

    }


    const id =
        deletingTableId;


    const button =
        document.getElementById(
            "confirmTableDelete"
        );


    if (button) {

        button.disabled =
            true;

        button.innerText =
            "Deleting...";

    }


    try {

        const response =
            await fetch(
                TABLE_API +
                "/" +
                encodeURIComponent(
                    id
                ),
                {

                    method:
                        "DELETE",

                    headers:
                        getHeaders()

                }
            );


        if (
            response.status ===
                401 ||
            response.status ===
                403
        ) {

            handleUnauthorized();

            return;

        }


        const result =
            await parseResponse(
                response
            );


        if (!response.ok) {

            throw new Error(
                getServerMessage(
                    result,
                    "Unable to delete table."
                )
            );

        }


        closeDeleteModal();


        showToast(
            "Table deleted successfully.",
            "success"
        );


        await loadTables();

    }
    catch (error) {

        console.error(
            "DELETE TABLE ERROR:",
            error
        );


        showToast(
            error.message ||
            "Unable to delete table.",
            "error"
        );

    }
    finally {

        if (button) {

            button.disabled =
                false;

            button.innerText =
                "Delete";

        }

    }

}


/* =========================================================
   GET TABLE FIELDS
========================================================= */

function getTableId(
    table
) {

    return (
        table.tableId ??
        table.id ??
        ""
    );

}


function getTableNumber(
    table
) {

    return (
        table.tableNumber ??
        table.tableName ??
        table.number ??
        ""
    );

}


function getTableCapacity(
    table
) {

    return (
        table.capacity ??
        table.seatingCapacity ??
        0
    );

}


function getTableLocation(
    table
) {

    return (
        table.location ??
        table.tableLocation ??
        ""
    );

}


function getTableStatus(
    table
) {

    if (!table) {
        return "AVAILABLE";
    }

    return String(
        table.tableStatus ||
        table.table_status ||
        table.status ||
        "AVAILABLE"
    ).toUpperCase();

}


/* =========================================================
   STATUS CLASS
========================================================= */

function getStatusClass(
    status
) {

    const value =
        String(
            status
        ).toUpperCase();


    switch (value) {

        case "AVAILABLE":
            return "available";

        case "OCCUPIED":
            return "occupied";

        case "RESERVED":
            return "reserved";

        case "MAINTENANCE":
            return "maintenance";

        case "INACTIVE":
            return "inactive";

        default:
            return "available";

    }

}


/* =========================================================
   PARSE RESPONSE
========================================================= */

async function parseResponse(
    response
) {

    const text =
        await response.text();


    if (!text) {

        return "";

    }


    try {

        return JSON.parse(
            text
        );

    }
    catch (
        error
    ) {

        return text;

    }

}


/* =========================================================
   SERVER MESSAGE
========================================================= */

function getServerMessage(
    result,
    defaultMessage
) {

    if (!result) {

        return defaultMessage;

    }


    if (
        typeof result ===
        "object"
    ) {

        return (
            result.message ||
            result.error ||
            result.details ||
            defaultMessage
        );

    }


    const text =
        String(result)
            .replace(
                /<[^>]*>/g,
                " "
            )
            .replace(
                /\s+/g,
                " "
            )
            .trim();


    return (
        text &&
        text.length < 300
    )
        ? text
        : defaultMessage;

}


/* =========================================================
   DATE
========================================================= */

function setCurrentDate() {

    const dateElement =
        document.getElementById(
            "currentDate"
        );

    const dayElement =
        document.getElementById(
            "currentDay"
        );


    if (
        !dateElement ||
        !dayElement
    ) {

        return;

    }


    const now =
        new Date();


    dateElement.innerText =
        now.toLocaleDateString(
            "en-US",
            {

                month:
                    "long",

                day:
                    "numeric",

                year:
                    "numeric"

            }
        );


    dayElement.innerText =
        now.toLocaleDateString(
            "en-US",
            {

                weekday:
                    "long"

            }
        );

}


/* =========================================================
   ADMIN INFO
========================================================= */

function loadAdminInfo() {

    const adminName =
        document.getElementById(
            "adminName"
        );

    const username =
        localStorage.getItem(
            "username"
        );


    if (
        adminName &&
        username
    ) {

        adminName.innerText =
            username;

    }


    const roleElement =
        document.getElementById(
            "adminRole"
        );

    const role =
        localStorage.getItem(
            "role"
        );


    if (
        roleElement &&
        role
    ) {

        roleElement.innerText =
            role === "ADMIN"
                ? "Administrator"
                : role;

    }

}


/* =========================================================
   TOAST
========================================================= */

function showToast(
    message,
    type = "success"
) {

    const toast =
        document.getElementById(
            "tableToast"
        );

    const text =
        document.getElementById(
            "tableToastText"
        );


    if (!toast) {

        alert(message);

        return;

    }


    if (text) {

        text.innerText =
            message;

    }


    toast.classList.remove(
        "success",
        "error",
        "show"
    );


    toast.classList.add(
        type
    );


    requestAnimationFrame(
        function () {

            toast.classList.add(
                "show"
            );

        }
    );


    setTimeout(
        function () {

            toast.classList.remove(
                "show"
            );

        },
        3000
    );

}


/* =========================================================
   GET VALUE
========================================================= */

function getValue(
    id
) {

    const element =
        document.getElementById(
            id
        );


    if (!element) {

        return "";

    }


    return String(
        element.value ||
        ""
    ).trim();

}


/* =========================================================
   SET VALUE
========================================================= */

function setValue(
    id,
    value
) {

    const element =
        document.getElementById(
            id
        );


    if (element) {

        element.value =
            value ?? "";

    }

}


/* =========================================================
   SET TEXT
========================================================= */

function setText(
    id,
    value
) {

    const element =
        document.getElementById(
            id
        );


    if (element) {

        element.innerText =
            value ?? "";

    }

}


/* =========================================================
   ESCAPE HTML
========================================================= */

function escapeHTML(
    value
) {

    return String(
        value ??
        ""
    )

    .replace(
        /&/g,
        "&amp;"
    )

    .replace(
        /</g,
        "&lt;"
    )

    .replace(
        />/g,
        "&gt;"
    )

    .replace(
        /"/g,
        "&quot;"
    )

    .replace(
        /'/g,
        "&#039;"
    );

}

function escapeHtml(value) {
    return escapeHTML(value);
}


/* =========================================================
   INLINE FUNCTIONS
========================================================= */

window.editTable =
    editTable;

window.openDeleteModal =
    openDeleteModal;


/* =========================================================
   UNAUTHORIZED
========================================================= */

function handleUnauthorized() {

    localStorage.removeItem(
        "token"
    );

    localStorage.removeItem(
        "userId"
    );

    localStorage.removeItem(
        "username"
    );

    localStorage.removeItem(
        "role"
    );

    localStorage.removeItem(
        "chefId"
    );


    window.location.href =
        CONTEXT_PATH +
        "/login.jsp";

}


/* =========================================================
   ONLINE TABLE RESERVATION MANAGEMENT
========================================================= */

const RESERVATION_API = BACKEND_URL + "/api/reservation";
let allReservations = [];

function setupReservationEvents() {
    const filterSelect = document.getElementById("resStatusFilter");
    if (filterSelect) {
        filterSelect.addEventListener("change", function () {
            loadReservations();
        });
    }

    const refreshBtn = document.getElementById("refreshReservationsBtn");
    if (refreshBtn) {
        refreshBtn.addEventListener("click", function () {
            loadReservations();
        });
    }
}

async function loadReservations() {
    const tbody = document.getElementById("reservationsTableBody");
    if (!tbody) return;

    const filterSelect = document.getElementById("resStatusFilter");
    const status = filterSelect ? filterSelect.value : "ALL";

    try {
        const url = status && status !== "ALL" 
            ? `${RESERVATION_API}?status=${encodeURIComponent(status)}`
            : RESERVATION_API;

        const response = await fetch(url, {
            method: "GET",
            headers: getHeaders()
        });

        if (response.status === 401 || response.status === 403) {
            handleUnauthorized();
            return;
        }

        if (!response.ok) {
            throw new Error(`HTTP error ${response.status}`);
        }

        allReservations = await response.json();
        if (!Array.isArray(allReservations)) allReservations = [];

        // Count pending
        const pendingCount = allReservations.filter(r => r.status === "PENDING").length;
        const pendingBadge = document.getElementById("pendingBookingBadge");
        if (pendingBadge) {
            pendingBadge.textContent = pendingCount;
            pendingBadge.style.display = pendingCount > 0 ? "inline-block" : "none";
        }

        renderReservations(allReservations);
    } catch (error) {
        console.error("Error loading reservations:", error);
        if (tbody) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="9" class="text-center py-4 text-danger">
                        <i class="fa-solid fa-triangle-exclamation me-2"></i> Failed to load reservations. Please refresh or check connection.
                    </td>
                </tr>
            `;
        }
    }
}

function renderReservations(reservations) {
    const tbody = document.getElementById("reservationsTableBody");
    if (!tbody) return;

    if (!Array.isArray(reservations) || reservations.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="9" class="text-center py-4 text-muted">
                    <i class="fa-solid fa-calendar-xmark fa-2x mb-2 d-block text-secondary"></i>
                    No table reservations found.
                </td>
            </tr>
        `;
        return;
    }

    let html = "";
    reservations.forEach(r => {
        const id = r.reservationId;
        const code = escapeHtml(r.reservationCode || `RES-${id}`);
        const name = escapeHtml(r.guestName || "Guest");
        const phone = escapeHtml(r.guestPhone || "-");
        const email = escapeHtml(r.guestEmail || "");
        const date = escapeHtml(r.bookingDate || "-");
        const time = escapeHtml(r.bookingTime || "-");
        const guests = r.guestCount || 2;
        const request = escapeHtml(r.specialRequest || "None");
        const status = r.status || "PENDING";
        const tableId = r.tableId;
        const tableNum = escapeHtml(r.tableNumber || "");

        // Table assignment select options from allTables
        let tableOptionsHtml = `<option value="">-- Assign Table --</option>`;
        if (Array.isArray(allTables) && allTables.length > 0) {
            allTables.forEach(t => {
                const selected = String(t.tableId) === String(tableId) ? "selected" : "";
                tableOptionsHtml += `<option value="${t.tableId}" ${selected}>${escapeHtml(t.tableNumber)} (${t.capacity} seats)</option>`;
            });
        }

        // Status badge class
        let statusBadge = `<span class="badge bg-warning text-dark"><i class="fa-solid fa-clock me-1"></i> PENDING</span>`;
        if (status === "CONFIRMED") {
            statusBadge = `<span class="badge bg-success"><i class="fa-solid fa-check-circle me-1"></i> CONFIRMED</span>`;
        } else if (status === "COMPLETED") {
            statusBadge = `<span class="badge bg-info text-dark"><i class="fa-solid fa-circle-check me-1"></i> COMPLETED</span>`;
        } else if (status === "REJECTED") {
            statusBadge = `<span class="badge bg-danger"><i class="fa-solid fa-circle-xmark me-1"></i> REJECTED</span>`;
        } else if (status === "CANCELLED") {
            statusBadge = `<span class="badge bg-secondary"><i class="fa-solid fa-ban me-1"></i> CANCELLED</span>`;
        }

        html += `
            <tr>
                <td class="ps-3"><strong class="text-primary">${code}</strong></td>
                <td><span class="fw-bold">${name}</span></td>
                <td><i class="fa-solid fa-phone text-muted me-1 small"></i>${phone}${email ? `<br><small class="text-muted"><i class="fa-solid fa-envelope me-1"></i>${email}</small>` : ''}</td>
                <td><span class="badge bg-light text-dark border"><i class="fa-solid fa-calendar me-1"></i>${date}</span> <br><small class="text-muted"><i class="fa-solid fa-clock me-1"></i>${time}</small></td>
                <td><span class="fw-bold text-dark"><i class="fa-solid fa-user-group me-1 text-gold"></i>${guests}</span></td>
                <td><small class="text-muted" title="${request}">${request.length > 30 ? request.substring(0, 30) + '...' : request}</small></td>
                <td>
                    <select class="form-select form-select-sm rounded-3" style="min-width:120px;" onchange="assignReservationTable(${id}, this.value)">
                        ${tableOptionsHtml}
                    </select>
                </td>
                <td>${statusBadge}</td>
                <td class="text-end pe-3">
                    <div class="btn-group btn-group-sm">
                        ${status === "PENDING" ? `
                            <button type="button" class="btn btn-sm btn-success" onclick="changeReservationStatus(${id}, 'CONFIRMED')" title="Approve & Confirm">
                                <i class="fa-solid fa-check"></i> Confirm
                            </button>
                            <button type="button" class="btn btn-sm btn-outline-danger" onclick="changeReservationStatus(${id}, 'REJECTED')" title="Reject">
                                <i class="fa-solid fa-xmark"></i>
                            </button>
                        ` : ''}
                        
                        ${status === "CONFIRMED" ? `
                            <button type="button" class="btn btn-sm btn-info text-white" onclick="changeReservationStatus(${id}, 'COMPLETED')" title="Mark Completed">
                                <i class="fa-solid fa-flag-checkered"></i> Complete
                            </button>
                            <button type="button" class="btn btn-sm btn-outline-secondary" onclick="changeReservationStatus(${id}, 'CANCELLED')" title="Cancel">
                                <i class="fa-solid fa-ban"></i>
                            </button>
                        ` : ''}

                        <button type="button" class="btn btn-sm btn-outline-danger ms-1" onclick="deleteReservationItem(${id})" title="Delete Reservation">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });

    tbody.innerHTML = html;
}

async function changeReservationStatus(id, newStatus) {
    try {
        const response = await fetch(`${RESERVATION_API}/${id}/status?status=${encodeURIComponent(newStatus)}`, {
            method: "PUT",
            headers: getHeaders()
        });

        if (!response.ok) {
            const data = await response.json().catch(() => ({}));
            alert(`Error: ${data.message || "Failed to update reservation status"}`);
            return;
        }

        showTableToast(`Reservation status updated to ${newStatus}`);
        loadReservations();
    } catch (e) {
        console.error("Status update error:", e);
        alert("Network error updating status.");
    }
}

async function assignReservationTable(id, tableId) {
    try {
        const payload = tableId ? { tableId: parseInt(tableId, 10) } : {};
        const response = await fetch(`${RESERVATION_API}/${id}/assign-table${tableId ? '?tableId=' + tableId : ''}`, {
            method: "PUT",
            headers: getHeaders(),
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const data = await response.json().catch(() => ({}));
            alert(`Error: ${data.message || "Failed to assign table"}`);
            return;
        }

        showTableToast("Table assigned successfully!");
        loadReservations();
    } catch (e) {
        console.error("Assign table error:", e);
        alert("Network error assigning table.");
    }
}

async function deleteReservationItem(id) {
    if (!confirm("Are you sure you want to delete this reservation record?")) return;

    try {
        const response = await fetch(`${RESERVATION_API}/${id}`, {
            method: "DELETE",
            headers: getHeaders()
        });

        if (!response.ok) {
            const data = await response.json().catch(() => ({}));
            alert(`Error: ${data.message || "Failed to delete reservation"}`);
            return;
        }

        showTableToast("Reservation deleted successfully");
        loadReservations();
    } catch (e) {
        console.error("Delete reservation error:", e);
        alert("Network error deleting reservation.");
    }
}

window.changeReservationStatus = changeReservationStatus;
window.assignReservationTable = assignReservationTable;
window.deleteReservationItem = deleteReservationItem;

/* =========================================================
   FINAL
========================================================= */

console.log(
    "Sankalp RMS Table Management JavaScript Ready with Online Booking Requests Support"
);


console.log(
    "Table API:",
    TABLE_API
);