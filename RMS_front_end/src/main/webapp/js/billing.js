"use strict";

/* =========================================================
   SANKALP RMS
   BILLING DASHBOARD
   COMPLETE CORRECTED VERSION
   ========================================================= */

(function () {

    /* =====================================================
       CONFIGURATION
    ===================================================== */

    console.log("======================================");
    console.log("Sankalp RMS - Billing Dashboard");
    console.log("======================================");


    /* =====================================================
       FRONTEND CONTEXT PATH
    ===================================================== */

    const pathParts =
        window.location.pathname
            .split("/")
            .filter(Boolean);

    const CTX =
        pathParts.length > 0
            ? "/" + pathParts[0]
            : "";


    /* =====================================================
       BACKEND BASE URL
    ===================================================== */

    const BACKEND_BASE_URL =
        (window.API_BASE_URL || (window.location.origin.includes(":8081") ? ("http://" + window.location.hostname + ":8080") : (window.location.origin + "/api")));


    /* =====================================================
       API URLS
    ===================================================== */

    const BILL_API = "/api/bill";
    const ORDER_API = "/api/order";


    /* =====================================================
       GST
    ===================================================== */

    const GST_RATE = 0.05;


    console.log(
        "Frontend Context Path:",
        CTX
    );

    console.log(
        "Backend Base URL:",
        BACKEND_BASE_URL
    );

    console.log(
        "Bill API:",
        BILL_API
    );

    console.log(
        "Order API:",
        ORDER_API
    );

    console.log("======================================");


    /* =====================================================
       STATE
    ===================================================== */

    const state = {

        bills: [],

        filteredBills: [],

        orders: [],

        selectedOrder: null,

        selectedDetails: [],

        page: 0,

        size: 6,

        paymentMethod: "CASH",

        paymentStatus: "PAID",

        discount: 0,

        lastCreatedBill: null

    };


    /* =====================================================
       SHORT SELECTOR
    ===================================================== */

    const $ = (id) =>
        document.getElementById(id);


    /* =====================================================
       TOKEN
    ===================================================== */

    function getToken() {

        return (
            localStorage.getItem("token") ||
            localStorage.getItem("jwtToken") ||
            ""
        );
    }


    /* =====================================================
       HEADERS
    ===================================================== */

    function getHeaders(json = false) {

        const headers = {};

        const token =
            getToken();

        if (token) {

            headers["Authorization"] =
                `Bearer ${token}`;
        }

        if (json) {

            headers["Content-Type"] =
                "application/json";
        }

        return headers;
    }


    /* =====================================================
       API REQUEST
    ===================================================== */

    async function api(
        url,
        options = {}
    ) {

        console.log(
            "API Request:",
            options.method || "GET",
            url
        );

        const response =
            await fetch(
                url,
                {
                    ...options,

                    headers: {
                        ...getHeaders(
                            !!options.body
                        ),

                        ...(options.headers || {})
                    }
                }
            );


        const text =
            await response.text();


        let data = null;


        try {

            data =
                text
                    ? JSON.parse(text)
                    : null;

        } catch {

            data = text;
        }


        if (!response.ok) {

            let message =
                `HTTP ${response.status}`;


            if (typeof data === "string") {

                message = data;

            } else if (data?.message) {

                message = data.message;

            } else if (data?.error) {

                message = data.error;
            }


            throw new Error(
                message
            );
        }


        return data;
    }


    /* =====================================================
       MONEY
    ===================================================== */

    function money(value) {

        const number =
            Number(value || 0);


        return `₹ ${number.toLocaleString(
            "en-IN",
            {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            }
        )}`;
    }


    /* =====================================================
       ESCAPE HTML
    ===================================================== */

    function escapeHtml(value) {

        return String(
            value ?? ""
        )
            .replaceAll(
                "&",
                "&amp;"
            )
            .replaceAll(
                "<",
                "&lt;"
            )
            .replaceAll(
                ">",
                "&gt;"
            )
            .replaceAll(
                '"',
                "&quot;"
            )
            .replaceAll(
                "'",
                "&#039;"
            );
    }


    /* =====================================================
       DATE
    ===================================================== */

    function dateText(value) {

        if (!value) {

            return "-";
        }


        const date =
            new Date(value);


        if (
            Number.isNaN(
                date.getTime()
            )
        ) {

            return String(value);
        }


        return date.toLocaleDateString(
            "en-IN",
            {
                day: "2-digit",
                month: "short",
                year: "numeric"
            }
        );
    }


    /* =====================================================
       TIME
    ===================================================== */

    function timeText(value) {

        if (!value) {

            return "";
        }


        const date =
            new Date(value);


        if (
            Number.isNaN(
                date.getTime()
            )
        ) {

            return "";
        }


        return date.toLocaleTimeString(
            "en-IN",
            {
                hour: "2-digit",
                minute: "2-digit"
            }
        );
    }


    /* =====================================================
       DATE TIME
    ===================================================== */

    function dateTimeText(value) {

        if (!value) {

            return "-";
        }


        const date =
            new Date(value);


        if (
            Number.isNaN(
                date.getTime()
            )
        ) {

            return String(value);
        }


        return date.toLocaleString(
            "en-IN",
            {
                day: "2-digit",
                month: "short",
                year: "numeric",
                hour: "2-digit",
                minute: "2-digit"
            }
        );
    }


    /* =====================================================
       NORMALIZE ARRAY
    ===================================================== */

    function normalizeArray(data) {

        if (Array.isArray(data)) {

            return data;
        }


        if (Array.isArray(data?.content)) {

            return data.content;
        }


        if (Array.isArray(data?.data)) {

            return data.data;
        }


        if (Array.isArray(data?.orders)) {

            return data.orders;
        }


        if (Array.isArray(data?.bills)) {

            return data.bills;
        }


        return [];
    }


    /* =====================================================
       ORDER ID
    ===================================================== */

    function orderIdOf(order) {

        return (
            order?.orderId ??
            order?.id ??
            order?.order_id
        );
    }


    /* =====================================================
       ORDER NUMBER
    ===================================================== */

    function orderNoOf(order) {

        return (
            order?.orderNo ??
            order?.orderNumber ??
            order?.order_no ??
            order?.order_number ??
            `ORD-${orderIdOf(order) ?? "-"}`
        );
    }


    /* =====================================================
       CUSTOMER
    ===================================================== */

    function customerOf(order) {

        return (
            order?.customerName ??
            order?.customer?.customerName ??
            order?.customer?.name ??
            order?.customer_name ??
            "Walk-in Customer"
        );
    }


    /* =====================================================
       TABLE
    ===================================================== */

    function tableOf(order) {

        return (
            order?.tableNumber ??
            order?.tableNo ??
            order?.table?.tableNumber ??
            order?.table_number ??
            (
                order?.tableId
                    ? `T${order.tableId}`
                    : "-"
            )
        );
    }


    /* =====================================================
       ORDER DATE
    ===================================================== */

    function orderDateOf(order) {

        return (
            order?.orderDate ??
            order?.order_date ??
            order?.createdAt ??
            order?.created_at
        );
    }


    /* =====================================================
       ORDER STATUS
    ===================================================== */

    function orderStatusOf(order) {

        return String(
            order?.orderStatus ??
            order?.order_status ??
            order?.status ??
            "NEW"
        ).toUpperCase();
    }


    /* =====================================================
       ITEM NAME
    ===================================================== */

    function itemNameOf(item) {

        return (
            item?.itemName ??
            item?.item_name ??
            item?.menuItemName ??
            item?.menu_item_name ??
            item?.name ??
            "Item"
        );
    }


    /* =====================================================
       QUANTITY
    ===================================================== */

    function quantityOf(item) {

        return Number(
            item?.quantity ??
            item?.qty ??
            1
        );
    }


    /* =====================================================
       PRICE
    ===================================================== */

    function priceOf(item) {

        return Number(
            item?.price ??
            item?.unitPrice ??
            item?.unit_price ??
            0
        );
    }


    /* =====================================================
       ITEM SUBTOTAL
    ===================================================== */

    function subtotalOf(item) {

        if (
            item?.subtotal !== undefined &&
            item?.subtotal !== null
        ) {

            return Number(
                item.subtotal
            );
        }


        if (
            item?.amount !== undefined &&
            item?.amount !== null
        ) {

            return Number(
                item.amount
            );
        }


        return (
            quantityOf(item) *
            priceOf(item)
        );
    }


    /* =====================================================
       TOAST
    ===================================================== */

    function toast(message) {

        const element =
            $("toast");

        const text =
            $("toastText");


        if (!element) {

            console.warn(
                "Toast element not found:",
                message
            );

            return;
        }


        if (text) {

            text.textContent =
                message;

        } else {

            element.textContent =
                message;
        }


        element.classList.add(
            "show"
        );


        clearTimeout(
            window.__billingToastTimer
        );


        window.__billingToastTimer =
            setTimeout(
                () => {

                    element.classList.remove(
                        "show"
                    );

                },
                3000
            );
    }


    /* =====================================================
       LOAD ORDERS
    ===================================================== */

    async function loadOrders() {

        try {

            const data =
                await api(
                    ORDER_API
                );


            state.orders =
                normalizeArray(data);


            populateOrderSelect();


            console.log(
                "Orders loaded:",
                state.orders.length
            );


        } catch (error) {

            console.error(
                "Order loading error:",
                error
            );


            toast(
                "Unable to load orders: " +
                error.message
            );
        }
    }


    /* =====================================================
       POPULATE ORDER SELECT
    ===================================================== */

    function populateOrderSelect() {

        const select =
            $("orderSelect");


        if (!select) {

            console.warn(
                "orderSelect not found"
            );

            return;
        }


        select.innerHTML =
            `
            <option value="">
                Select an order
            </option>
            `;


        state.orders.forEach(
            order => {

                const id =
                    orderIdOf(order);


                if (!id) {

                    return;
                }


                const option =
                    document.createElement(
                        "option"
                    );


                option.value =
                    id;


                option.textContent =
                    `${orderNoOf(order)} - ${customerOf(order)} - ${tableOf(order)}`;


                select.appendChild(
                    option
                );
            }
        );
    }


    /* =====================================================
       CLEAR ORDER PREVIEW
    ===================================================== */

    function clearOrderPreview() {

        state.selectedOrder =
            null;


        state.selectedDetails =
            [];


        if ($("billCustomer")) {

            $("billCustomer").textContent =
                "—";
        }


        if ($("billOrderDate")) {

            $("billOrderDate").textContent =
                "—";
        }


        if ($("billTable")) {

            $("billTable").textContent =
                "—";
        }


        if ($("billOrderStatus")) {

            $("billOrderStatus").textContent =
                "NEW";


            $("billOrderStatus").className =
                "status-badge new";
        }


        if ($("orderItemsBody")) {

            $("orderItemsBody").innerHTML =
                `
                <tr>

                    <td
                        colspan="4"
                        class="empty-items">

                        Select an order

                    </td>

                </tr>
                `;
        }


        if ($("subtotal")) {

            $("subtotal").textContent =
                "₹ 0.00";
        }


        if ($("gst")) {

            $("gst").textContent =
                "₹ 0.00";
        }


        if ($("discount")) {

            $("discount").textContent =
                "₹ 0.00";
        }


        if ($("grandTotal")) {

            $("grandTotal").textContent =
                "₹ 0.00";
        }


        if ($("receivedAmount")) {

            $("receivedAmount").value =
                "";
        }
    }


    /* =====================================================
       LOAD SELECTED ORDER
    ===================================================== */

    async function loadSelectedOrder() {

        const select =
            $("orderSelect");


        if (!select) {

            return;
        }


        const id =
            Number(
                select.value
            );


        if (!id) {

            clearOrderPreview();

            return;
        }


        const order =
            state.orders.find(
                item =>
                    Number(
                        orderIdOf(item)
                    ) === id
            );


        if (!order) {

            toast(
                "Selected order not found"
            );

            return;
        }


        state.selectedOrder =
            order;


        renderOrderSummary(
            order
        );


        let details = [];


        try {

            /*
             * CORRECT CONTROLLER ENDPOINT
             *
             * GET /api/order/details/{orderId}
             */

            const data =
                await api(
                    `${ORDER_API}/details/${id}`
                );


            details =
                normalizeArray(data);


            console.log(
                "Order details loaded:",
                details
            );


        } catch (error) {

            console.error(
                "Order details loading error:",
                error
            );


            toast(
                "Unable to load order details: " +
                error.message
            );


            details = [];
        }


        state.selectedDetails =
            details;


        renderOrderItems(
            details
        );


        calculateTotals();
    }


    /* =====================================================
       LOAD ORDER BUTTON
    ===================================================== */

    async function handleLoadOrder() {

        try {

            await loadSelectedOrder();

        } catch (error) {

            console.error(
                error
            );

            toast(
                error.message
            );
        }
    }


    /* =====================================================
       ORDER SUMMARY
    ===================================================== */

    function renderOrderSummary(
        order
    ) {

        const customer =
            $("billCustomer");

        const date =
            $("billOrderDate");

        const table =
            $("billTable");

        const status =
            $("billOrderStatus");


        if (customer) {

            customer.textContent =
                customerOf(order);
        }


        if (date) {

            date.textContent =
                dateTimeText(
                    orderDateOf(order)
                );
        }


        if (table) {

            table.textContent =
                tableOf(order);
        }


        if (status) {

            const value =
                orderStatusOf(order);


            status.textContent =
                value;


            status.className =
                `status-badge ${value.toLowerCase()}`;
        }
    }


    /* =====================================================
       ORDER ITEMS
    ===================================================== */

    function renderOrderItems(items) {

        const body =
            $("orderItemsBody");


        if (!body) {

            return;
        }


        if (!items.length) {

            body.innerHTML =
                `
                <tr>

                    <td
                        colspan="4"
                        class="empty-items">

                        No order items found

                    </td>

                </tr>
                `;

            return;
        }


        body.innerHTML =
            items
                .map(
                    item => {

                        return `
                        <tr>

                            <td>
                                ${escapeHtml(
                                    itemNameOf(item)
                                )}
                            </td>

                            <td>
                                ${quantityOf(item)}
                            </td>

                            <td>
                                ${money(
                                    priceOf(item)
                                )}
                            </td>

                            <td>
                                ${money(
                                    subtotalOf(item)
                                )}
                            </td>

                        </tr>
                        `;
                    }
                )
                .join("");
    }


    /* =====================================================
       CALCULATE TOTALS
    ===================================================== */

    function calculateTotals() {

        const subtotal =
            state.selectedDetails.reduce(
                (
                    total,
                    item
                ) => {

                    return (
                        total +
                        subtotalOf(item)
                    );

                },
                0
            );


        const discount =
            Math.min(
                subtotal,
                Math.max(
                    0,
                    Number(
                        state.discount || 0
                    )
                )
            );


        const taxable =
            Math.max(
                0,
                subtotal - discount
            );


        const gst =
            Math.round(
                taxable *
                GST_RATE *
                100
            ) / 100;


        const grandTotal =
            Math.round(
                (
                    taxable +
                    gst
                ) * 100
            ) / 100;


        if ($("subtotal")) {

            $("subtotal").textContent =
                money(subtotal);
        }


        if ($("gst")) {

            $("gst").textContent =
                money(gst);
        }


        if ($("discount")) {

            $("discount").textContent =
                money(discount);
        }


        if ($("grandTotal")) {

            $("grandTotal").textContent =
                money(grandTotal);
        }


        if (
            $("receivedAmount") &&
            state.paymentStatus === "PAID"
        ) {

            $("receivedAmount").value =
                grandTotal.toFixed(2);
        }


        return {

            subtotal,

            gst,

            discount,

            grandTotal
        };
    }


    /* =====================================================
       LOAD BILLS
    ===================================================== */

    async function loadBills() {

        try {

            const data =
                await api(
                    BILL_API
                );


            state.bills =
                normalizeArray(data);


            console.log(
                "Bills loaded:",
                state.bills.length
            );


            renderStats();

            renderCharts();

            applyFilters();


        } catch (error) {

            console.error(
                "Billing load error:",
                error
            );


            const body =
                $("billTableBody");


            if (body) {

                body.innerHTML =
                    `
                    <tr>

                        <td
                            colspan="9"
                            class="empty">

                            Unable to load bills:
                            ${escapeHtml(
                                error.message
                            )}

                        </td>

                    </tr>
                    `;
            }


            toast(
                "Unable to load bills: " +
                error.message
            );
        }
    }


    /* =====================================================
       BILL STATUS
    ===================================================== */

    function billStatusOf(bill) {

        return String(
            bill?.paymentStatus ??
            bill?.payment_status ??
            "PENDING"
        ).toUpperCase();
    }


    /* =====================================================
       BILL PAYMENT METHOD
    ===================================================== */

    function paymentMethodOf(bill) {

        return String(
            bill?.paymentMethod ??
            bill?.payment_method ??
            "OTHER"
        ).toUpperCase();
    }


    /* =====================================================
       BILL DATE
    ===================================================== */

    function billDateOf(bill) {

        return (
            bill?.billDate ??
            bill?.bill_date
        );
    }


    /* =====================================================
       BILL TOTAL
    ===================================================== */

    function billTotalOf(bill) {

        return Number(
            bill?.grandTotal ??
            bill?.grand_total ??
            0
        );
    }


    /* =====================================================
       BILL ORDER
    ===================================================== */

    function findOrderForBill(bill) {

        return state.orders.find(
            order =>
                Number(
                    orderIdOf(order)
                ) ===
                Number(
                    bill?.orderId ??
                    bill?.order_id
                )
        );
    }


    /* =====================================================
       RENDER STATS
    ===================================================== */

    function renderStats() {

        const bills =
            state.bills;


        const paid =
            bills.filter(
                bill =>
                    billStatusOf(
                        bill
                    ) === "PAID"
            );


        const pending =
            bills.filter(
                bill =>
                    billStatusOf(
                        bill
                    ) === "PENDING"
            );


        const cancelled =
            bills.filter(
                bill =>
                    billStatusOf(
                        bill
                    ) === "CANCELLED"
            );


        const paidAmount =
            paid.reduce(
                (
                    total,
                    bill
                ) =>
                    total +
                    billTotalOf(
                        bill
                    ),
                0
            );


        const pendingAmount =
            pending.reduce(
                (
                    total,
                    bill
                ) =>
                    total +
                    billTotalOf(
                        bill
                    ),
                0
            );


        if ($("totalBills")) {

            $("totalBills").textContent =
                bills.length;
        }


        if ($("totalRevenue")) {

            $("totalRevenue").textContent =
                money(
                    paidAmount
                );
        }


        if ($("pendingAmount")) {

            $("pendingAmount").textContent =
                money(
                    pendingAmount
                );
        }


        if ($("paidAmount")) {

            $("paidAmount").textContent =
                money(
                    paidAmount
                );
        }


        if ($("paidCount")) {

            $("paidCount").textContent =
                paid.length;
        }


        if ($("pendingCount")) {

            $("pendingCount").textContent =
                pending.length;
        }


        if ($("cancelledCount")) {

            $("cancelledCount").textContent =
                cancelled.length;
        }


        if ($("statusTotal")) {

            $("statusTotal").textContent =
                bills.length;
        }
    }


    /* =====================================================
       FILTER BILLS
    ===================================================== */

    function applyFilters() {

        const query =
            $("billSearch")
                ?.value
                ?.trim()
                ?.toLowerCase() ||
            "";


        const status =
            $("statusFilter")
                ?.value ||
            "ALL";


        const dateFilter =
            $("dateFilter")
                ?.value ||
            "ALL";


        state.filteredBills =
            state.bills.filter(
                bill => {

                    const order =
                        findOrderForBill(
                            bill
                        );


                    const customer =
                        order
                            ? customerOf(order)
                            : "";


                    const orderNo =
                        order
                            ? orderNoOf(order)
                            : `ORD-${
                                bill.orderId ??
                                bill.order_id ??
                                "-"
                            }`;


                    const billNo =
                        bill.billNo ??
                        bill.bill_no ??
                        "";


                    const text =
                        `
                        ${billNo}
                        ${orderNo}
                        ${customer}
                        ${bill.orderId ?? ""}
                        `.toLowerCase();


                    const queryMatch =
                        !query ||
                        text.includes(
                            query
                        );


                    const statusMatch =
                        status === "ALL" ||
                        billStatusOf(
                            bill
                        ) === status;


                    const dateMatch =
                        matchesDateFilter(
                            billDateOf(
                                bill
                            ),
                            dateFilter
                        );


                    return (
                        queryMatch &&
                        statusMatch &&
                        dateMatch
                    );
                }
            );


        state.page =
            0;


        renderBillTable();
    }


    /* =====================================================
       DATE FILTER
    ===================================================== */

    function matchesDateFilter(
        value,
        filter
    ) {

        if (
            !value ||
            filter === "ALL"
        ) {

            return true;
        }


        const billDate =
            new Date(value);


        if (
            Number.isNaN(
                billDate.getTime()
            )
        ) {

            return true;
        }


        const now =
            new Date();


        if (
            filter === "TODAY"
        ) {

            return (
                billDate.toDateString() ===
                now.toDateString()
            );
        }


        if (
            filter === "WEEK"
        ) {

            const start =
                new Date(now);


            const day =
                start.getDay();


            const diff =
                day === 0
                    ? 6
                    : day - 1;


            start.setDate(
                start.getDate() - diff
            );


            start.setHours(
                0,
                0,
                0,
                0
            );


            const end =
                new Date(start);


            end.setDate(
                end.getDate() + 7
            );


            return (
                billDate >= start &&
                billDate < end
            );
        }


        if (
            filter === "MONTH"
        ) {

            return (
                billDate.getMonth()
                    === now.getMonth() &&
                billDate.getFullYear()
                    === now.getFullYear()
            );
        }


        return true;
    }


    /* =====================================================
       RENDER BILL TABLE
    ===================================================== */

    function renderBillTable() {

        const body =
            $("billTableBody");


        if (!body) {

            return;
        }


        const start =
            state.page *
            state.size;


        const rows =
            state.filteredBills.slice(
                start,
                start + state.size
            );


        if (!rows.length) {

            body.innerHTML =
                `
                <tr>

                    <td
                        colspan="9"
                        class="empty">

                        No bills found

                    </td>

                </tr>
                `;

        } else {

            body.innerHTML =
                rows
                    .map(
                        bill => {

                            const billId =
                                bill.billId ??
                                bill.bill_id;


                            const billNo =
                                bill.billNo ??
                                bill.bill_no ??
                                `BILL-${
                                    String(
                                        billId
                                    ).padStart(
                                        6,
                                        "0"
                                    )
                                }`;


                            const order =
                                findOrderForBill(
                                    bill
                                );


                            const customer =
                                order
                                    ? customerOf(order)
                                    : "Customer";


                            const table =
                                order
                                    ? tableOf(order)
                                    : "-";


                            const orderNo =
                                order
                                    ? orderNoOf(order)
                                    : `ORD-${
                                        bill.orderId ??
                                        bill.order_id ??
                                        "-"
                                    }`;


                            const payment =
                                paymentMethodOf(
                                    bill
                                );


                            const status =
                                billStatusOf(
                                    bill
                                );


                            return `
                            <tr>

                                <td>
                                    ${escapeHtml(
                                        billNo
                                    )}
                                </td>


                                <td>
                                    ${escapeHtml(
                                        orderNo
                                    )}
                                </td>


                                <td>
                                    ${escapeHtml(
                                        customer
                                    )}
                                </td>


                                <td>
                                    ${escapeHtml(
                                        table
                                    )}
                                </td>


                                <td>
                                    ${money(
                                        billTotalOf(
                                            bill
                                        )
                                    )}
                                </td>


                                <td>

                                    <span
                                        class="payment-tag ${payment.toLowerCase()}">

                                        ${escapeHtml(
                                            payment
                                        )}

                                    </span>

                                </td>


                                <td>

                                    <span
                                        class="status-tag ${status.toLowerCase()}">

                                        ${escapeHtml(
                                            status
                                        )}

                                    </span>

                                </td>


                                <td>

                                    ${dateText(
                                        billDateOf(
                                            bill
                                        )
                                    )}

                                    <br>

                                    ${timeText(
                                        billDateOf(
                                            bill
                                        )
                                    )}

                                </td>


                                <td>

                                    <button
                                        type="button"
                                        class="action-btn"
                                        title="View Bill"
                                        onclick="window.viewBill(${billId})">

                                        <i class="fa-solid fa-eye"></i>

                                    </button>


                                    <button
                                        type="button"
                                        class="action-btn"
                                        title="Print Bill"
                                        onclick="window.printBillById(${billId})">

                                        <i class="fa-solid fa-print"></i>

                                    </button>

                                </td>

                            </tr>
                            `;
                        }
                    )
                    .join("");
        }


        const total =
            state.filteredBills.length;


        const from =
            total > 0
                ? start + 1
                : 0;


        const to =
            Math.min(
                start + state.size,
                total
            );


        if ($("billCount")) {

            $("billCount").textContent =
                `Showing ${from} to ${to} of ${total} bills`;
        }


        updatePagination();
    }


    /* =====================================================
       UPDATE PAGINATION
    ===================================================== */

    function updatePagination() {

        const totalPages =
            Math.max(
                1,
                Math.ceil(
                    state.filteredBills.length /
                    state.size
                )
            );


        const current =
            state.page + 1;


        const first =
            $("billFirstPage");


        const prev =
            $("billPrevPage");


        const currentButton =
            $("billCurrentPage");


        const next =
            $("billNextPage");


        const last =
            $("billLastPage");


        if (first) {

            first.disabled =
                state.page === 0;
        }


        if (prev) {

            prev.disabled =
                state.page === 0;
        }


        if (next) {

            next.disabled =
                state.page >=
                totalPages - 1;
        }


        if (last) {

            last.disabled =
                state.page >=
                totalPages - 1;
        }


        if (currentButton) {

            currentButton.textContent =
                current;
        }
    }


    /* =====================================================
       GO TO PAGE
    ===================================================== */

    function goToPage(page) {

        const totalPages =
            Math.max(
                1,
                Math.ceil(
                    state.filteredBills.length /
                    state.size
                )
            );


        state.page =
            Math.max(
                0,
                Math.min(
                    page,
                    totalPages - 1
                )
            );


        renderBillTable();
    }


    /* =====================================================
       RENDER CHART SUMMARY
    ===================================================== */

    function renderCharts() {

        const bills =
            state.bills;


        const total =
            bills.length;


        if ($("paymentTotal")) {

            $("paymentTotal").textContent =
                total;
        }


        if ($("statusTotal")) {

            $("statusTotal").textContent =
                total;
        }


        let cash = 0;

        let upi = 0;

        let card = 0;

        let other = 0;


        bills.forEach(
            bill => {

                const amount =
                    billTotalOf(
                        bill
                    );


                const method =
                    paymentMethodOf(
                        bill
                    );


                if (
                    method === "CASH"
                ) {

                    cash += amount;

                } else if (
                    method === "UPI"
                ) {

                    upi += amount;

                } else if (
                    method === "CARD"
                ) {

                    card += amount;

                } else {

                    other += amount;
                }
            }
        );


        if ($("cashAmount")) {

            $("cashAmount").textContent =
                money(cash);
        }


        if ($("upiAmount")) {

            $("upiAmount").textContent =
                money(upi);
        }


        if ($("cardAmount")) {

            $("cardAmount").textContent =
                money(card);
        }


        if ($("otherAmount")) {

            $("otherAmount").textContent =
                money(other);
        }


        const paid =
            bills.filter(
                bill =>
                    billStatusOf(
                        bill
                    ) === "PAID"
            ).length;


        const pending =
            bills.filter(
                bill =>
                    billStatusOf(
                        bill
                    ) === "PENDING"
            ).length;


        const cancelled =
            bills.filter(
                bill =>
                    billStatusOf(
                        bill
                    ) === "CANCELLED"
            ).length;


        if ($("paidCount")) {

            $("paidCount").textContent =
                paid;
        }


        if ($("pendingCount")) {

            $("pendingCount").textContent =
                pending;
        }


        if ($("cancelledCount")) {

            $("cancelledCount").textContent =
                cancelled;
        }
    }


    /* =====================================================
       PAYMENT METHOD
    ===================================================== */

    function setupPaymentMethods() {

        document
            .querySelectorAll(
                ".payment-btn"
            )
            .forEach(
                button => {

                    button.addEventListener(
                        "click",
                        () => {

                            document
                                .querySelectorAll(
                                    ".payment-btn"
                                )
                                .forEach(
                                    item =>
                                        item.classList.remove(
                                            "active"
                                        )
                                );


                            button.classList.add(
                                "active"
                            );


                            state.paymentMethod =
                                String(
                                    button.dataset.method ||
                                    "CASH"
                                ).toUpperCase();


                            console.log(
                                "Payment method:",
                                state.paymentMethod
                            );
                        }
                    );
                }
            );
    }


    /* =====================================================
       PAYMENT STATUS
    ===================================================== */

    function setupPaymentStatus() {

        document
            .querySelectorAll(
                ".payment-status-btn"
            )
            .forEach(
                button => {

                    button.addEventListener(
                        "click",
                        () => {

                            document
                                .querySelectorAll(
                                    ".payment-status-btn"
                                )
                                .forEach(
                                    item =>
                                        item.classList.remove(
                                            "active"
                                        )
                                );


                            button.classList.add(
                                "active"
                            );


                            state.paymentStatus =
                                String(
                                    button.dataset.status ||
                                    "PAID"
                                ).toUpperCase();


                            const totals =
                                calculateTotals();


                            if (
                                $("receivedAmount")
                            ) {

                                if (
                                    state.paymentStatus ===
                                    "PAID"
                                ) {

                                    $("receivedAmount").value =
                                        totals.grandTotal
                                            .toFixed(2);

                                } else {

                                    $("receivedAmount").value =
                                        "0.00";
                                }
                            }


                            console.log(
                                "Payment status:",
                                state.paymentStatus
                            );
                        }
                    );
                }
            );
    }


    /* =====================================================
       GENERATE BILL
    ===================================================== */

    async function generateBill() {

        if (!state.selectedOrder) {

            toast(
                "Please select an order"
            );

            return;
        }


        if (
            !state.selectedDetails.length
        ) {

            toast(
                "Selected order has no items"
            );

            return;
        }


        const totals =
            calculateTotals();


        const received =
            Number(
                $("receivedAmount")
                    ?.value || 0
            );


        if (
            state.paymentStatus === "PAID" &&
            received < totals.grandTotal
        ) {

            toast(
                "Received amount is less than bill total"
            );

            return;
        }


        const orderId =
            Number(
                orderIdOf(
                    state.selectedOrder
                )
            );


        const payload = {

            orderId,

            subtotal:
                totals.subtotal,

            gst:
                totals.gst,

            discount:
                totals.discount,

            grandTotal:
                totals.grandTotal,

            paymentMethod:
                state.paymentMethod,

            paymentStatus:
                state.paymentStatus
        };


        console.log(
            "Creating bill:",
            payload
        );


        const button =
            $("generateBillBtn");


        try {

            if (button) {

                button.disabled =
                    true;


                button.innerHTML =
                    `
                    <i class="fa-solid fa-spinner fa-spin"></i>
                    Generating...
                    `;
            }


            const bill =
                await api(
                    BILL_API,
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(
                                payload
                            )
                    }
                );


            state.lastCreatedBill =
                bill;


            console.log(
                "Bill created:",
                bill
            );


            toast(
                "Bill generated successfully"
            );


            await loadBills();


            clearForm();


        } catch (error) {

            console.error(
                "Generate bill error:",
                error
            );


            toast(
                error.message
            );


        } finally {

            if (button) {

                button.disabled =
                    false;


                button.innerHTML =
                    `
                    <i class="fa-solid fa-file-invoice"></i>
                    Generate Bill
                    `;
            }
        }
    }


    /* =====================================================
       CLOSE BILL DETAILS MODAL
    ===================================================== */

    function closeBillModal() {

        const modal =
            $("billDetailsModal");


        if (!modal) {

            return;
        }


        modal.classList.remove(
            "show"
        );


        document.body.style.overflow =
            "";
    }


    /* =====================================================
       VIEW BILL - CUSTOM MODAL
    ===================================================== */

    window.viewBill =
        async function (
            billId
        ) {

            try {

                const bill =
                    await api(
                        `${BILL_API}/${billId}`
                    );


                const billNo =
                    bill.billNo ??
                    bill.bill_no ??
                    `BILL-${
                        String(
                            billId
                        ).padStart(
                            6,
                            "0"
                        )
                    }`;


                const orderId =
                    bill.orderId ??
                    bill.order_id ??
                    "-";


                const subtotal =
                    Number(
                        bill.subtotal ?? 0
                    );


                const gst =
                    Number(
                        bill.gst ?? 0
                    );


                const discount =
                    Number(
                        bill.discount ?? 0
                    );


                const grandTotal =
                    Number(
                        bill.grandTotal ??
                        bill.grand_total ??
                        0
                    );


                const paymentMethod =
                    String(
                        bill.paymentMethod ??
                        bill.payment_method ??
                        "-"
                    ).toUpperCase();


                const paymentStatus =
                    String(
                        bill.paymentStatus ??
                        bill.payment_status ??
                        "PENDING"
                    ).toUpperCase();


                const billDate =
                    bill.billDate ??
                    bill.bill_date;


                const modal =
                    $("billDetailsModal");


                if (!modal) {

                    console.error(
                        "Bill Details Modal not found in billing.jsp"
                    );


                    toast(
                        "Bill details modal not found"
                    );


                    return;
                }


                /* -----------------------------------------
                   MODAL DATA
                ----------------------------------------- */

                if ($("modalBillNo")) {

                    $("modalBillNo").textContent =
                        billNo;
                }


                if ($("modalOrderId")) {

                    $("modalOrderId").textContent =
                        orderId;
                }


                if ($("modalBillDate")) {

                    $("modalBillDate").textContent =
                        dateTimeText(
                            billDate
                        );
                }


                if ($("modalPaymentMethod")) {

                    $("modalPaymentMethod").textContent =
                        paymentMethod;
                }


                if ($("modalPaymentStatus")) {

                    $("modalPaymentStatus").textContent =
                        paymentStatus;
                }


                if ($("modalSubtotal")) {

                    $("modalSubtotal").textContent =
                        money(
                            subtotal
                        );
                }


                if ($("modalGst")) {

                    $("modalGst").textContent =
                        money(
                            gst
                        );
                }


                if ($("modalDiscount")) {

                    $("modalDiscount").textContent =
                        money(
                            discount
                        );
                }


                if ($("modalGrandTotal")) {

                    $("modalGrandTotal").textContent =
                        money(
                            grandTotal
                        );
                }


                if ($("modalPayment")) {

                    $("modalPayment").textContent =
                        paymentMethod;
                }


                if ($("modalStatus")) {

                    $("modalStatus").textContent =
                        paymentStatus;
                }


                /* -----------------------------------------
                   STATUS CLASSES
                ----------------------------------------- */

                const statusClass =
                    paymentStatus.toLowerCase();


                if ($("modalPaymentStatus")) {

                    $("modalPaymentStatus").className =
                        `modal-status ${statusClass}`;
                }


                if ($("modalStatus")) {

                    $("modalStatus").className =
                        statusClass;
                }


                /* -----------------------------------------
                   STORE BILL
                ----------------------------------------- */

                state.lastCreatedBill =
                    bill;


                /* -----------------------------------------
                   SHOW MODAL
                ----------------------------------------- */

                modal.classList.add(
                    "show"
                );


                document.body.style.overflow =
                    "hidden";


                console.log(
                    "Bill modal opened:",
                    billNo
                );


            } catch (error) {

                console.error(
                    "View bill error:",
                    error
                );


                toast(
                    error.message
                );
            }
        };


    /* =====================================================
       PRINT BILL BY ID
    ===================================================== */

    window.printBillById =
        async function (
            billId
        ) {

            try {

                const bill =
                    await api(
                        `${BILL_API}/${billId}`
                    );


                state.lastCreatedBill =
                    bill;


                printBill(
                    bill
                );


            } catch (error) {

                console.error(
                    "Print bill error:",
                    error
                );


                toast(
                    error.message
                );
            }
        };


    /* =====================================================
       PRINT BILL
    ===================================================== */

    function printBill(
        bill
    ) {

        const billNo =
            bill.billNo ??
            bill.bill_no ??
            "-";


        const orderId =
            bill.orderId ??
            bill.order_id ??
            "-";


        const subtotal =
            Number(
                bill.subtotal ?? 0
            );


        const gst =
            Number(
                bill.gst ?? 0
            );


        const discount =
            Number(
                bill.discount ?? 0
            );


        const grandTotal =
            Number(
                bill.grandTotal ??
                bill.grand_total ??
                0
            );


        const paymentMethod =
            bill.paymentMethod ??
            bill.payment_method ??
            "-";


        const paymentStatus =
            bill.paymentStatus ??
            bill.payment_status ??
            "-";


        const billDate =
            bill.billDate ??
            bill.bill_date;

        let settings = {};
        try {
            const stored = localStorage.getItem("sankalp_restaurant_settings");
            if (stored) settings = JSON.parse(stored);
        } catch (e) {}

        const restaurantName = settings.restaurantName || "Sankalp Restaurant";
        const tagline = settings.tagline || "Authentic Flavors & Fine Dining Experience";
        const address = settings.address || "Opp. City Mall, Main Ring Road, Ahmedabad, Gujarat 380015";
        const phone = settings.phone || "+91 98765 43210";
        const email = settings.email || "contact@sankalprestaurant.com";
        const gstin = settings.gstinNumber ? `GSTIN: ${escapeHtml(settings.gstinNumber)}` : "";
        const fssai = settings.fssaiNumber ? `FSSAI Lic: ${escapeHtml(settings.fssaiNumber)}` : "";
        const gstRate = settings.gstRate !== undefined ? settings.gstRate : 5.0;
        const footerNote = settings.billFooterNote || "Thank you for dining with Sankalp! Have a wonderful day.";
        const currencySymbol = settings.currencySymbol || "₹";

        let logoSrc = (settings.logoPath || "/images/logo1.png").trim();
        if (!logoSrc.startsWith("http") && !logoSrc.startsWith("data:")) {
            if (logoSrc.startsWith("uploads/")) logoSrc = "/api/" + logoSrc;
            else if (logoSrc.startsWith("/uploads/")) logoSrc = "/api" + logoSrc;
            else if (!logoSrc.startsWith("/")) logoSrc = "/" + logoSrc;
        }

        const printWindow =
            window.open(
                "",
                "_blank",
                "width=700,height=850"
            );

        if (!printWindow) {
            toast(
                "Please allow popups to print the bill"
            );
            return;
        }

        printWindow.document.write(
            `
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Receipt - ${escapeHtml(billNo)} | ${escapeHtml(restaurantName)}</title>
                <style>
                    * { box-sizing: border-box; }
                    body {
                        font-family: 'Segoe UI', Arial, sans-serif;
                        padding: 30px;
                        color: #1e293b;
                        background: #fff;
                    }
                    .invoice {
                        max-width: 600px;
                        margin: auto;
                        border: 1px solid #e2e8f0;
                        padding: 24px;
                        border-radius: 10px;
                    }
                    .header {
                        text-align: center;
                        margin-bottom: 16px;
                    }
                    .header img {
                        max-height: 65px;
                        max-width: 140px;
                        object-fit: contain;
                        margin-bottom: 8px;
                    }
                    .header h1 {
                        margin: 0 0 4px 0;
                        color: #b45309;
                        font-size: 22px;
                        font-weight: 800;
                        letter-spacing: 0.5px;
                    }
                    .header .tagline {
                        font-size: 12px;
                        color: #64748b;
                        font-style: italic;
                        margin-bottom: 4px;
                    }
                    .header .contact-meta {
                        font-size: 11px;
                        color: #475569;
                        line-height: 1.4;
                    }
                    .tax-badges {
                        display: flex;
                        justify-content: center;
                        gap: 12px;
                        margin-top: 6px;
                        font-size: 11px;
                        font-weight: 600;
                        color: #334155;
                    }
                    hr {
                        border: 0;
                        border-top: 1px dashed #cbd5e1;
                        margin: 14px 0;
                    }
                    .meta-grid {
                        display: grid;
                        grid-template-columns: 1fr 1fr;
                        gap: 8px;
                        font-size: 12px;
                        background: #f8fafc;
                        padding: 10px 14px;
                        border-radius: 6px;
                    }
                    .meta-grid div strong {
                        color: #64748b;
                    }
                    table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 14px;
                        font-size: 13px;
                    }
                    th {
                        background-color: #f1f5f9;
                        padding: 8px 10px;
                        text-align: left;
                        font-size: 12px;
                        color: #475569;
                        border-bottom: 2px solid #cbd5e1;
                    }
                    td {
                        padding: 8px 10px;
                        border-bottom: 1px solid #e2e8f0;
                    }
                    th:last-child, td:last-child {
                        text-align: right;
                    }
                    .total-section {
                        margin-top: 16px;
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        padding: 12px 14px;
                        background: #fefce8;
                        border: 1px solid #fef08a;
                        border-radius: 6px;
                    }
                    .total-section strong {
                        font-size: 16px;
                        color: #713f12;
                    }
                    .total-section .amount {
                        font-size: 20px;
                        font-weight: 800;
                        color: #b45309;
                    }
                    .payment-info {
                        margin-top: 12px;
                        font-size: 12px;
                        display: flex;
                        justify-content: space-between;
                        color: #475569;
                    }
                    .footer {
                        text-align: center;
                        margin-top: 20px;
                        font-size: 11px;
                        color: #64748b;
                    }
                    @media print {
                        body { padding: 0; }
                        .invoice { border: none; padding: 0; }
                    }
                </style>
            </head>
            <body>
                <div class="invoice">
                    <div class="header">
                        <img src="${logoSrc}" alt="Logo" onerror="this.style.display='none'">
                        <h1>${escapeHtml(restaurantName.toUpperCase())}</h1>
                        <div class="tagline">${escapeHtml(tagline)}</div>
                        <div class="contact-meta">
                            ${escapeHtml(address)}<br>
                            Tel: ${escapeHtml(phone)} | Email: ${escapeHtml(email)}
                        </div>
                        <div class="tax-badges">
                            ${gstin ? `<span>${gstin}</span>` : ''}
                            ${fssai ? `<span>${fssai}</span>` : ''}
                        </div>
                    </div>

                    <hr>

                    <div class="meta-grid">
                        <div><strong>Invoice No:</strong> ${escapeHtml(billNo)}</div>
                        <div><strong>Order Ref:</strong> #${escapeHtml(orderId)}</div>
                        <div><strong>Date:</strong> ${escapeHtml(dateTimeText(billDate))}</div>
                        <div><strong>Pay Status:</strong> <span style="color:#16a34a; font-weight:bold;">${escapeHtml(paymentStatus)}</span></div>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Item / Charge Details</th>
                                <th>Amount (${escapeHtml(currencySymbol)})</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Item Subtotal</td>
                                <td>${money(subtotal)}</td>
                            </tr>
                            <tr>
                                <td>GST (${gstRate}%)</td>
                                <td>${money(gst)}</td>
                            </tr>
                            <tr>
                                <td>Discount Applied</td>
                                <td>- ${money(discount)}</td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="total-section">
                        <strong>Grand Total</strong>
                        <span class="amount">${money(grandTotal)}</span>
                    </div>

                    <div class="payment-info">
                        <span>Payment Method: <strong>${escapeHtml(paymentMethod)}</strong></span>
                        <span>Authorized Official Receipt</span>
                    </div>

                    <div class="footer">
                        <hr>
                        <p style="margin: 4px 0; font-weight: 500;">${escapeHtml(footerNote)}</p>
                        <small>&copy; ${escapeHtml(restaurantName)} • Powered by Sankalp RMS</small>
                    </div>
                </div>
            </body>
            </html>
            `
        );

        printWindow.document.close();
        printWindow.focus();

        setTimeout(() => {
            printWindow.print();
        }, 500);
    }


    /* =====================================================
       CLEAR FORM
    ===================================================== */

    function clearForm() {

        state.selectedOrder =
            null;


        state.selectedDetails =
            [];


        state.discount =
            0;


        const orderSelect =
            $("orderSelect");


        if (orderSelect) {

            orderSelect.value =
                "";
        }


        if ($("billCustomer")) {

            $("billCustomer").textContent =
                "—";
        }


        if ($("billOrderDate")) {

            $("billOrderDate").textContent =
                "—";
        }


        if ($("billTable")) {

            $("billTable").textContent =
                "—";
        }


        if ($("billOrderStatus")) {

            $("billOrderStatus").textContent =
                "NEW";


            $("billOrderStatus").className =
                "status-badge new";
        }


        if ($("orderItemsBody")) {

            $("orderItemsBody").innerHTML =
                `
                <tr>

                    <td
                        colspan="4"
                        class="empty-items">

                        Select an order

                    </td>

                </tr>
                `;
        }


        if ($("subtotal")) {

            $("subtotal").textContent =
                "₹ 0.00";
        }


        if ($("gst")) {

            $("gst").textContent =
                "₹ 0.00";
        }


        if ($("discount")) {

            $("discount").textContent =
                "₹ 0.00";
        }


        if ($("grandTotal")) {

            $("grandTotal").textContent =
                "₹ 0.00";
        }


        if ($("receivedAmount")) {

            $("receivedAmount").value =
                "";
        }


        if ($("billNotes")) {

            $("billNotes").value =
                "";
        }
    }


    /* =====================================================
       CLOSE GENERATOR
    ===================================================== */

    function toggleGenerator() {

        const generator =
            $("billGenerator");


        const content =
            $("generatorContent");


        if (
            !generator ||
            !content
        ) {

            return;
        }


        generator.classList.toggle(
            "collapsed"
        );


        const collapsed =
            generator.classList.contains(
                "collapsed"
            );


        const button =
            $("closeGenerator");


        if (button) {

            button.innerHTML =
                collapsed
                    ? `<i class="fa-solid fa-plus"></i>`
                    : `<i class="fa-solid fa-minus"></i>`;
        }
    }


    /* =====================================================
       LOGOUT
    ===================================================== */

    function logout() {

        localStorage.removeItem(
            "token"
        );


        localStorage.removeItem(
            "jwtToken"
        );


        localStorage.removeItem(
            "role"
        );


        localStorage.removeItem(
            "chefToken"
        );


        localStorage.removeItem(
            "chefRole"
        );


        window.location.href =
            `${CTX}/login.jsp`;
    }


    /* =====================================================
       MODAL EVENT LISTENERS
    ===================================================== */

    function setupBillModal() {

        /* -----------------------------------------------
           CLOSE X
        ----------------------------------------------- */

        $("closeBillModal")
            ?.addEventListener(
                "click",
                closeBillModal
            );


        /* -----------------------------------------------
           CLOSE BUTTON
        ----------------------------------------------- */

        $("modalCloseBtn")
            ?.addEventListener(
                "click",
                closeBillModal
            );


        /* -----------------------------------------------
           PRINT BUTTON
        ----------------------------------------------- */

        $("modalPrintBtn")
            ?.addEventListener(
                "click",
                () => {

                    if (
                        state.lastCreatedBill
                    ) {

                        printBill(
                            state.lastCreatedBill
                        );

                    } else {

                        toast(
                            "Bill information is not available"
                        );
                    }
                }
            );


        /* -----------------------------------------------
           CLICK OUTSIDE MODAL
        ----------------------------------------------- */

        $("billDetailsModal")
            ?.addEventListener(
                "click",
                event => {

                    if (
                        event.target.id ===
                        "billDetailsModal"
                    ) {

                        closeBillModal();
                    }
                }
            );
    }


    /* =====================================================
       ESCAPE KEY
    ===================================================== */

    function setupKeyboardShortcuts() {

        document.addEventListener(
            "keydown",
            event => {

                if (
                    event.key ===
                    "Escape"
                ) {

                    closeBillModal();
                }
            }
        );
    }


    /* =====================================================
       INITIALIZE
    ===================================================== */

    async function initialize() {

        console.log(
            "Billing dashboard initialized"
        );


        /* -------------------------------------------------
           TODAY DATE
        ------------------------------------------------- */

        const now =
            new Date();


        if ($("todayDate")) {

            $("todayDate").textContent =
                now.toLocaleDateString(
                    "en-IN",
                    {
                        month: "long",
                        day: "numeric",
                        year: "numeric"
                    }
                );
        }


        if ($("todayDay")) {

            $("todayDay").textContent =
                now.toLocaleDateString(
                    "en-IN",
                    {
                        weekday: "long"
                    }
                );
        }


        /* -------------------------------------------------
           MENU
        ------------------------------------------------- */

        $("menuBtn")
            ?.addEventListener(
                "click",
                () => {

                    $("sidebar")
                        ?.classList.toggle(
                            "open"
                        );
                }
            );


        /* -------------------------------------------------
           BILL SEARCH
        ------------------------------------------------- */

        $("billSearch")
            ?.addEventListener(
                "input",
                applyFilters
            );


        /* -------------------------------------------------
           STATUS FILTER
        ------------------------------------------------- */

        $("statusFilter")
            ?.addEventListener(
                "change",
                applyFilters
            );


        /* -------------------------------------------------
           DATE FILTER
        ------------------------------------------------- */

        $("dateFilter")
            ?.addEventListener(
                "change",
                applyFilters
            );


        /* -------------------------------------------------
           ORDER SELECT
        ------------------------------------------------- */

        $("orderSelect")
            ?.addEventListener(
                "change",
                loadSelectedOrder
            );


        /* -------------------------------------------------
           LOAD ORDER
        ------------------------------------------------- */

        $("loadOrderBtn")
            ?.addEventListener(
                "click",
                handleLoadOrder
            );


        /* -------------------------------------------------
           PAYMENT METHOD
        ------------------------------------------------- */

        setupPaymentMethods();


        /* -------------------------------------------------
           PAYMENT STATUS
        ------------------------------------------------- */

        setupPaymentStatus();


        /* -------------------------------------------------
           BILL MODAL
        ------------------------------------------------- */

        setupBillModal();


        /* -------------------------------------------------
           ESCAPE KEY
        ------------------------------------------------- */

        setupKeyboardShortcuts();


        /* -------------------------------------------------
           GENERATE
        ------------------------------------------------- */

        $("generateBillBtn")
            ?.addEventListener(
                "click",
                generateBill
            );


        /* -------------------------------------------------
           CANCEL
        ------------------------------------------------- */

        $("cancelBillBtn")
            ?.addEventListener(
                "click",
                clearForm
            );


        /* -------------------------------------------------
           PRINT CURRENT BILL
        ------------------------------------------------- */

        $("printBillBtn")
            ?.addEventListener(
                "click",
                () => {

                    if (
                        state.lastCreatedBill
                    ) {

                        printBill(
                            state.lastCreatedBill
                        );

                    } else {

                        toast(
                            "Generate or select a bill first"
                        );
                    }
                }
            );


        /* -------------------------------------------------
           CLOSE GENERATOR
        ------------------------------------------------- */

        $("closeGenerator")
            ?.addEventListener(
                "click",
                toggleGenerator
            );


        /* -------------------------------------------------
           FIRST PAGE
        ------------------------------------------------- */

        $("billFirstPage")
            ?.addEventListener(
                "click",
                () => {

                    goToPage(0);
                }
            );


        /* -------------------------------------------------
           PREVIOUS PAGE
        ------------------------------------------------- */

        $("billPrevPage")
            ?.addEventListener(
                "click",
                () => {

                    goToPage(
                        state.page - 1
                    );
                }
            );


        /* -------------------------------------------------
           CURRENT PAGE
        ------------------------------------------------- */

        $("billCurrentPage")
            ?.addEventListener(
                "click",
                () => {

                    goToPage(
                        state.page
                    );
                }
            );


        /* -------------------------------------------------
           NEXT PAGE
        ------------------------------------------------- */

        $("billNextPage")
            ?.addEventListener(
                "click",
                () => {

                    goToPage(
                        state.page + 1
                    );
                }
            );


        /* -------------------------------------------------
           LAST PAGE
        ------------------------------------------------- */

        $("billLastPage")
            ?.addEventListener(
                "click",
                () => {

                    const totalPages =
                        Math.max(
                            1,
                            Math.ceil(
                                state.filteredBills.length /
                                state.size
                            )
                        );


                    goToPage(
                        totalPages - 1
                    );
                }
            );


        /* -------------------------------------------------
           LOGOUT
        ------------------------------------------------- */

        $("logoutBtn")
            ?.addEventListener(
                "click",
                logout
            );


        /* -------------------------------------------------
           LOAD DATA
        ------------------------------------------------- */

        await Promise.all([
            loadBills(),
            loadOrders()
        ]);


        console.log(
            "Billing module ready"
        );
    }


    /* =====================================================
       START
    ===================================================== */

    if (
        document.readyState ===
        "loading"
    ) {

        document.addEventListener(
            "DOMContentLoaded",
            initialize
        );

    } else {

        initialize();
    }

    window.triggerThermalPrintFromModal = async function() {
        if (!state.selectedBill) {
            alert("Please select a bill first.");
            return;
        }
        const billId = state.selectedBill.billId;
        if (window.ThermalPrinter) {
            await window.ThermalPrinter.printBill(billId, "");
        } else {
            alert("Thermal Printer module not loaded.");
        }
    };

    window.triggerTableQRFromModal = async function() {
        if (!state.selectedBill) {
            alert("Please select a bill first.");
            return;
        }
        const billId = state.selectedBill.billId;
        try {
            const res = await fetch(`/api/payment/qr-code/${billId}`);
            const data = await res.json();
            if (data && data.qrCodeBase64) {
                document.getElementById("qrModalImg").src = data.qrCodeBase64;
                document.getElementById("qrModalBillNo").textContent = data.billNo || ("#" + billId);
                document.getElementById("qrModalAmount").textContent = "₹" + (data.grandTotal ? data.grandTotal.toFixed(2) : "0.00");
                document.getElementById("tableQrModal").style.display = "flex";
            } else {
                alert("Failed to generate UPI QR Code.");
            }
        } catch (e) {
            console.error("QR Code Error:", e);
            alert("Error fetching QR Code: " + e.message);
        }
    };

})();