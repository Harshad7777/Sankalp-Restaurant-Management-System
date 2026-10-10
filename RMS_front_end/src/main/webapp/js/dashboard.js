/* =====================================================
   SANKALP DASHBOARD JS
===================================================== */

console.log("=================================");
console.log("Sankalp Admin Dashboard Loaded");
console.log("=================================");


/* =====================================================
   API
===================================================== */

const BACKEND_BASE_URL = window.location.origin.includes(":8081") 
    ? "http://localhost:8080" 
    : (window.location.origin + "/RMS_backend");

const DASHBOARD_API = "/api/dashboard/summary";


/* =====================================================
   TOKEN
===================================================== */

const token =
    localStorage.getItem("token");


/* =====================================================
   LOGIN CHECK
===================================================== */

if (!token) {

    window.location.href =
        "login.jsp";

}


/* =====================================================
   DOM READY
===================================================== */

document.addEventListener(
    "DOMContentLoaded",
    function () {

        loadDashboard();

        setupRefresh();

    }
);


/* =====================================================
   LOAD DASHBOARD
===================================================== */

async function loadDashboard() {

    console.log(
        "Loading dashboard..."
    );


    try {

        const response =
            await fetch(
                DASHBOARD_API,
                {

                    method: "GET",

                    headers: {

                        "Authorization":
                            "Bearer " + token,

                        "Content-Type":
                            "application/json"

                    }

                }
            );


        console.log(
            "Dashboard HTTP status:",
            response.status
        );


        if (
            response.status === 401 ||
            response.status === 403
        ) {

            logoutUser();

            return;

        }


        if (!response.ok) {

            const text =
                await response.text();

            console.error(
                "Dashboard API response:",
                text
            );

            throw new Error(
                "Unable to load dashboard"
            );

        }


        const data =
            await response.json();


        console.log(
            "Dashboard data:",
            data
        );


        updateStatistics(
            data
        );


        const orders =
            Array.isArray(
                data.recentOrders
            )
                ? data.recentOrders
                : [];


        updateRecentOrders(
            orders
        );


        updateOrderStatus(
            orders
        );


        createRevenueChart(
            data
        );


        createOrderStatusChart(
            orders
        );

        updateTopSellingItems(
            data.topSellingItems || []
        );

        loadDashboardStockAlerts();

    }
    catch (error) {

        console.error(
            "Dashboard Error:",
            error
        );

    }

}


/* =====================================================
   UPDATE STATISTICS
===================================================== */

function updateStatistics(data) {

    setText(
        "todayOrders",
        data.todayOrders ?? 0
    );

    setText(
        "totalOrders",
        data.totalOrders ?? 0
    );

    setText(
        "totalCustomers",
        data.totalCustomers ?? 0
    );

    setText(
        "todaySales",
        formatNumber(
            data.todaySales ?? 0
        )
    );

    setText(
        "totalRevenue",
        formatNumber(
            data.totalRevenue ?? 0
        )
    );

    setText(
        "pendingOrders",
        data.todayOrders ?? 0
    );

    setText(
        "sidebarOrderCount",
        data.todayOrders ?? 0
    );

}


/* =====================================================
   SAFE TEXT
===================================================== */

function setText(
    id,
    value
) {

    const element =
        document.getElementById(id);


    if (element) {

        element.textContent =
            value;

    }

}


/* =====================================================
   NUMBER FORMAT
===================================================== */

function formatNumber(
    value
) {

    const number =
        Number(value) || 0;


    return number.toLocaleString(
        "en-IN",
        {
            maximumFractionDigits: 2
        }
    );

}


/* =====================================================
   RECENT ORDERS
===================================================== */

function updateRecentOrders(
    orders
) {

    const tbody =
        document.getElementById(
            "recentOrders"
        );


    if (!tbody) {

        return;

    }


    if (
        !orders ||
        orders.length === 0
    ) {

        tbody.innerHTML = `

            <tr>

                <td
                    colspan="5"
                    class="empty-row">

                    No recent orders found

                </td>

            </tr>

        `;

        return;

    }


    tbody.innerHTML = "";


    orders
        .slice(0, 5)
        .forEach(
            function (order) {

                const row =
                    document.createElement(
                        "tr"
                    );


                const status =
                    order.status ||
                    "PENDING";


                const statusClass =
                    getStatusClass(
                        status
                    );


                row.innerHTML = `

                    <td>
                        #ORD${order.orderId}
                    </td>

                    <td>
                        ${escapeHtml(
                            order.customerName ||
                            "Walk-in Customer"
                        )}
                    </td>

                    <td>
                        ₹${formatNumber(
                            order.totalAmount || 0
                        )}
                    </td>

                    <td>

                        <span
                            class="order-status ${statusClass}">

                            ${escapeHtml(
                                status
                            )}

                        </span>

                    </td>

                    <td>
                        ${formatDate(
                            order.orderDate
                        )}
                    </td>

                `;


                tbody.appendChild(
                    row
                );

            }
        );

}


/* =====================================================
   STATUS CLASS
===================================================== */

function getStatusClass(
    status
) {

    const value =
        String(status)
            .toUpperCase();


    if (
        value === "COMPLETED" ||
        value === "SERVED" ||
        value === "READY"
    ) {

        return "completed";

    }


    if (
        value === "PREPARING" ||
        value === "ACCEPTED"
    ) {

        return "preparing";

    }


    if (
        value === "CANCELLED" ||
        value === "CANCELED"
    ) {

        return "cancelled";

    }


    return "pending";

}


/* =====================================================
   ORDER STATUS
===================================================== */

function updateOrderStatus(
    orders
) {

    let completed = 0;

    let preparing = 0;

    let pending = 0;

    let cancelled = 0;


    orders.forEach(
        function (order) {

            const status =
                String(
                    order.status || ""
                ).toUpperCase();


            if (
                status === "COMPLETED" ||
                status === "SERVED" ||
                status === "READY"
            ) {

                completed++;

            }
            else if (
                status === "PREPARING" ||
                status === "ACCEPTED"
            ) {

                preparing++;

            }
            else if (
                status === "CANCELLED" ||
                status === "CANCELED"
            ) {

                cancelled++;

            }
            else {

                pending++;

            }

        }
    );


    setText(
        "completedCount",
        completed
    );


    setText(
        "preparingCount",
        preparing
    );


    setText(
        "pendingStatusCount",
        pending
    );


    setText(
        "cancelledCount",
        cancelled
    );


    setText(
        "statusTotal",
        orders.length
    );

}


/* =====================================================
   REVENUE CHART
===================================================== */

function createRevenueChart(data) {
    const canvas = document.getElementById("revenueChart");
    if (!canvas) return;

    window.lastDashboardData = data;

    const periodSelect = document.getElementById("chartPeriod");
    if (periodSelect && !periodSelect.dataset.listenerAttached) {
        periodSelect.dataset.listenerAttached = "true";
        periodSelect.addEventListener("change", function() {
            createRevenueChart(window.lastDashboardData || {});
        });
    }

    if (window.revenueChartInstance) {
        window.revenueChartInstance.destroy();
    }

    let labels = [];
    let values = [];

    const isMonth = periodSelect && periodSelect.value === "month";

    if (Array.isArray(data.revenueOverview) && data.revenueOverview.length > 0) {
        labels = data.revenueOverview.map(item => item.day || item.date);
        values = data.revenueOverview.map(item => Number(item.amount || 0));
    } else {
        const days = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
        labels = days;
        values = days.map(() => 0);
    }

    const ctx = canvas.getContext("2d");
    const gradient = ctx.createLinearGradient(0, 0, 0, 300);
    gradient.addColorStop(0, "rgba(217, 148, 0, 0.25)");
    gradient.addColorStop(1, "rgba(217, 148, 0, 0.0)");

    window.revenueChartInstance = new Chart(canvas, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: "Revenue (₹)",
                data: values,
                borderColor: "#d99400",
                backgroundColor: gradient,
                borderWidth: 2.5,
                fill: true,
                tension: 0.35,
                pointRadius: 4,
                pointHoverRadius: 6,
                pointBackgroundColor: "#d99400",
                pointBorderColor: "#ffffff",
                pointBorderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            interaction: {
                intersect: false,
                mode: "index"
            },
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return " Revenue: ₹" + Number(context.parsed.y || 0).toLocaleString("en-IN", {
                                minimumFractionDigits: 2,
                                maximumFractionDigits: 2
                            });
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: "#f0f0f0"
                    },
                    ticks: {
                        font: {
                            size: 11
                        },
                        callback: function(value) {
                            if (value >= 100000) return "₹" + (value / 100000).toFixed(1) + "L";
                            if (value >= 1000) return "₹" + (value / 1000).toFixed(0) + "K";
                            return "₹" + value;
                        }
                    }
                },
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        font: {
                            size: 11
                        }
                    }
                }
            }
        }
    });
}


/* =====================================================
   ORDER STATUS CHART
===================================================== */

function createOrderStatusChart(
    orders
) {

    const canvas =
        document.getElementById(
            "orderStatusChart"
        );


    if (!canvas) {

        return;

    }


    if (
        window.statusChartInstance
    ) {

        window.statusChartInstance.destroy();

    }


    let completed = 0;

    let preparing = 0;

    let pending = 0;

    let cancelled = 0;


    orders.forEach(
        function (order) {

            const status =
                String(
                    order.status || ""
                ).toUpperCase();


            if (
                status === "COMPLETED" ||
                status === "SERVED" ||
                status === "READY"
            ) {

                completed++;

            }
            else if (
                status === "PREPARING" ||
                status === "ACCEPTED"
            ) {

                preparing++;

            }
            else if (
                status === "CANCELLED" ||
                status === "CANCELED"
            ) {

                cancelled++;

            }
            else {

                pending++;

            }

        }
    );


    window.statusChartInstance =
        new Chart(
            canvas,
            {

                type: "doughnut",

                data: {

                    labels: [

                        "Completed",
                        "Preparing",
                        "Pending",
                        "Cancelled"

                    ],

                    datasets: [

                        {

                            data: [

                                completed,
                                preparing,
                                pending,
                                cancelled

                            ],

                            backgroundColor: [

                                "#55a958",
                                "#ff8b00",
                                "#f0b400",
                                "#e63c2d"

                            ],

                            borderWidth: 0,

                            hoverOffset: 4

                        }

                    ]

                },

                options: {

                    responsive: true,

                    maintainAspectRatio: false,

                    cutout: "68%",

                    plugins: {

                        legend: {

                            display: false

                        }

                    }

                }

            }
        );

}


/* =====================================================
   REFRESH
===================================================== */

function setupRefresh() {
    const button = document.getElementById("refreshDashboard");
    if (!button) return;

    button.addEventListener("click", async function () {
        const icon = button.querySelector("i");
        if (icon) icon.classList.add("fa-spin");
        await loadDashboard();
        setTimeout(function () {
            if (icon) icon.classList.remove("fa-spin");
        }, 500);
    });
}


/* =====================================================
   LOGOUT
===================================================== */

function logoutUser() {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
    localStorage.removeItem("userId");
    window.location.href = "login.jsp";
}


/* =====================================================
   DATE FORMAT
===================================================== */

function formatDate(value) {
    if (!value) return "-";
    try {
        const date = new Date(value);
        if (Number.isNaN(date.getTime())) return value;
        return date.toLocaleDateString("en-IN", {
            day: "2-digit",
            month: "short",
            year: "numeric"
        });
    } catch (error) {
        return value;
    }
}


/* =====================================================
   ESCAPE HTML
===================================================== */

function escapeHtml(value) {
    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}


/* =====================================================
   LIVE INVENTORY & KITCHEN STOCK ALERTS
===================================================== */

async function loadDashboardStockAlerts() {
    const tbody = document.getElementById("dashboardStockAlertsBody");
    if (!tbody) return;

    try {
        const response = await fetch("/api/inventory", {
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) return;

        const items = await response.json();
        const lowStockItems = items.filter(i => (Number(i.quantity) <= Number(i.minThreshold)) || (i.status === 'LOW_STOCK') || (i.status === 'OUT_OF_STOCK'));

        if (lowStockItems.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="6" style="text-align: center; color: #059669; padding: 22px; font-weight: 600; background: #f0fdf4;">
                        <i class="fa-solid fa-circle-check" style="margin-right: 6px; font-size: 16px;"></i> All kitchen raw materials and ingredients are healthy and adequately stocked.
                    </td>
                </tr>
            `;
            return;
        }

        let html = "";
        lowStockItems.forEach(item => {
            const qty = Number(item.quantity || 0);
            const isOut = qty <= 0;
            const badgeBg = isOut ? "#fef2f2" : "#fffbeb";
            const badgeColor = isOut ? "#dc2626" : "#d97706";
            const badgeBorder = isOut ? "#fecaca" : "#fde68a";
            const badgeText = isOut ? "Out of Stock" : "Low Stock";

            html += `
                <tr>
                    <td><strong style="color: #1e293b; font-size: 13px;">${escapeHtml(item.itemName)}</strong></td>
                    <td><span style="background: #f1f5f9; padding: 2px 8px; border-radius: 4px; font-size: 11px; font-weight: 600; color: #475569;">${escapeHtml(item.category || 'General')}</span></td>
                    <td><strong style="color: ${badgeColor}; font-size: 13px;">${qty} ${escapeHtml(item.unit || 'unit')}</strong></td>
                    <td style="color: #64748b; font-size: 13px;">${item.minThreshold} ${escapeHtml(item.unit || 'unit')}</td>
                    <td>
                        <span style="display: inline-flex; align-items: center; gap: 4px; padding: 3px 10px; border-radius: 9999px; font-size: 11px; font-weight: 700; background: ${badgeBg}; color: ${badgeColor}; border: 1px solid ${badgeBorder};">
                            <i class="fa-solid fa-circle" style="font-size: 6px;"></i> ${badgeText}
                        </span>
                    </td>
                    <td style="text-align: right; padding-right: 20px;">
                        <a href="inventory.jsp" style="display: inline-flex; align-items: center; gap: 4px; padding: 4px 10px; background: #fffdf7; border: 1px solid #d99400; color: #b45309; border-radius: 6px; font-size: 11px; font-weight: 700; text-decoration: none; transition: all 0.15s ease;">
                            <i class="fa-solid fa-arrow-right-arrow-left"></i> Restock
                        </a>
                    </td>
                </tr>
            `;
        });

        tbody.innerHTML = html;

    } catch (e) {
        console.error("Dashboard Stock Alerts error:", e);
    }
}


/* =====================================================
   UPDATE TOP SELLING ITEMS BY RESTAURANT
===================================================== */

function updateTopSellingItems(topItems) {
    const container = document.getElementById("topSellingItemsContainer") || document.querySelector(".top-items");
    if (!container) return;

    if (!Array.isArray(topItems) || topItems.length === 0) {
        container.innerHTML = `<div class="empty-state text-center py-4 text-muted" style="grid-column: 1 / -1;">No top selling items recorded yet for this restaurant.</div>`;
        return;
    }

    const icons = ["fa-bowl-food", "fa-bowl-rice", "fa-plate-wheat", "fa-utensils", "fa-cookie"];
    let html = "";

    topItems.forEach((item, index) => {
        const rank = index + 1;
        const itemName = item.itemName || "Menu Item";
        const orders = item.orders || 0;
        const soldQty = item.soldQuantity || 0;
        const percent = item.percentage ?? 100;
        const iconClass = icons[index % icons.length];

        let imgUrl = "";
        if (item.image && typeof item.image === "string") {
            const raw = item.image.trim();
            if (raw.startsWith("http://") || raw.startsWith("https://") || raw.startsWith("data:")) {
                imgUrl = raw;
            } else {
                let clean = raw.replace(/^\/+/, "");
                if (clean.startsWith("api/")) clean = clean.substring(4);
                if (clean.startsWith("uploads/")) {
                    imgUrl = "/api/" + clean;
                } else if (/\.(jpg|jpeg|png|webp|gif)$/i.test(clean)) {
                    imgUrl = "/api/uploads/" + clean;
                } else {
                    imgUrl = "/api/" + clean;
                }
            }
        }

        const subtitleText = orders > 0 
            ? `${orders} Orders (${soldQty} Sold)` 
            : `Popular Item`;

        const imageHtml = imgUrl 
            ? `<div class="food-image" style="position: relative; overflow: hidden;">
                <img src="${imgUrl}" alt="${escapeHtml(itemName)}" style="width: 100%; height: 100%; object-fit: cover; border-radius: 7px;" onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                <i class="fa-solid ${iconClass}" style="display: none; width: 100%; height: 100%; align-items: center; justify-content: center;"></i>
               </div>`
            : `<div class="food-image">
                <i class="fa-solid ${iconClass}"></i>
               </div>`;

        html += `
            <div class="top-item">
                <div class="rank">${rank}</div>
                ${imageHtml}
                <div class="food-info">
                    <strong title="${escapeHtml(itemName)}">${escapeHtml(itemName)}</strong>
                    <span>${subtitleText}</span>
                </div>
                <div class="food-percent">
                    <strong>${percent}%</strong>
                    <div class="progress">
                        <div style="width: ${percent}%;"></div>
                    </div>
                </div>
            </div>
        `;
    });

    container.innerHTML = html;
}
