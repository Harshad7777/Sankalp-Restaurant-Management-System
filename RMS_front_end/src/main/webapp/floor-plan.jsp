<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
%>

<%@ include file="includes/header.jsp" %>
<%@ include file="includes/sidebar.jsp" %>

<div class="main-content">

    <main class="table-page">

        <!-- =================================================
             PAGE HEADER
        ================================================== -->
        <div class="table-page-header">

            <div class="table-title-area">

                <h1>
                    Floor Plan Visualizer
                </h1>

                <div class="table-breadcrumb">

                    <span>
                        Dashboard
                    </span>

                    <i class="fa-solid fa-chevron-right"></i>

                    <span class="active">
                        Floor Plan
                    </span>

                </div>

            </div>

            <div class="d-flex gap-2">

                <button
                    type="button"
                    class="btn btn-outline-secondary px-3 py-2 fw-semibold"
                    onclick="loadTables()">

                    <i class="fa-solid fa-rotate-right me-1"></i>

                    Refresh

                </button>

                <button
                    type="button"
                    class="btn btn-success px-4 py-2 fw-bold text-white shadow-sm"
                    onclick="saveFloorLayout()">

                    <i class="fa-solid fa-floppy-disk me-1"></i>

                    Save Layout

                </button>

            </div>

        </div>


        <!-- =================================================
             LIVE TABLE STATS BAR
        ================================================== -->
        <div class="table-stats mb-4">

            <div class="table-stat-card">
                <div class="stat-icon available">
                    <i class="fa-solid fa-chair"></i>
                </div>
                <div class="stat-info">
                    <span class="stat-value" id="statAvailable">0</span>
                    <span class="stat-label">Available Tables</span>
                </div>
            </div>

            <div class="table-stat-card">
                <div class="stat-icon occupied" style="background: #fee2e2; color: #ef4444;">
                    <i class="fa-solid fa-user-group"></i>
                </div>
                <div class="stat-info">
                    <span class="stat-value" id="statOccupied">0</span>
                    <span class="stat-label">Occupied Tables</span>
                </div>
            </div>

            <div class="table-stat-card">
                <div class="stat-icon reserved" style="background: #fef3c7; color: #f59e0b;">
                    <i class="fa-solid fa-bookmark"></i>
                </div>
                <div class="stat-info">
                    <span class="stat-value" id="statReserved">0</span>
                    <span class="stat-label">Reserved Tables</span>
                </div>
            </div>

            <div class="table-stat-card">
                <div class="stat-icon billed" style="background: #dbeafe; color: #3b82f6;">
                    <i class="fa-solid fa-file-invoice-dollar"></i>
                </div>
                <div class="stat-info">
                    <span class="stat-value" id="statBilled">0</span>
                    <span class="stat-label">Billed Tables</span>
                </div>
            </div>

        </div>


        <!-- =================================================
             GRAPHICAL FLOOR CANVAS CONTAINER
        ================================================== -->
        <div class="card shadow-sm border-0 rounded-3 mb-4">

            <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center border-bottom">

                <h5 class="m-0 fw-bold text-dark">
                    <i class="fa-solid fa-map-location-dot text-primary me-2"></i>
                    Interactive Restaurant Floor Canvas
                </h5>

                <span class="badge bg-light text-secondary border px-3 py-2 fs-6 fw-normal">
                    <i class="fa-solid fa-hand-pointer me-1 text-primary"></i>
                    Drag tables to re-arrange seating layout
                </span>

            </div>

            <div class="card-body p-3">

                <div id="floorCanvas" style="width: 100%; height: 620px; background-color: #f8fafc; background-image: radial-gradient(#cbd5e1 1.5px, transparent 1.5px); background-size: 24px 24px; border: 2px dashed #cbd5e1; border-radius: 12px; position: relative; overflow: hidden;">
                    <!-- Tables rendered dynamically -->
                </div>

            </div>

        </div>

    </main>

</div>

<style>
    .draggable-table {
        position: absolute;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        font-weight: 700;
        color: white;
        cursor: grab;
        user-select: none;
        box-shadow: 0 6px 15px rgba(0,0,0,0.18);
        transition: transform 0.15s ease, box-shadow 0.15s ease;
        border: 2px solid rgba(255,255,255,0.85);
    }
    .draggable-table:active {
        cursor: grabbing;
        transform: scale(1.05);
        box-shadow: 0 12px 25px rgba(0,0,0,0.3);
        z-index: 1000;
    }
    .draggable-table:hover {
        box-shadow: 0 10px 22px rgba(0,0,0,0.25);
    }
    .status-AVAILABLE { background: linear-gradient(135deg, #22c55e, #16a34a); }
    .status-OCCUPIED { background: linear-gradient(135deg, #ef4444, #dc2626); }
    .status-RESERVED { background: linear-gradient(135deg, #f59e0b, #d97706); }
    .status-BILLED { background: linear-gradient(135deg, #3b82f6, #2563eb); }
    .shape-ROUND { border-radius: 50%; }
    .shape-RECTANGLE { border-radius: 12px; }
</style>

<script>
    window.CONTEXT_PATH = "<%= contextPath %>";

    let tableData = [];
    let activeDrag = null;
    let offsetX = 0, offsetY = 0;

    async function loadTables() {
        try {
            const res = await fetch('/api/tables');
            if (!res.ok) {
                throw new Error("HTTP error " + res.status);
            }
            tableData = await res.json();
            renderFloorPlan();
        } catch (e) {
            console.error("Error loading floor plan tables:", e);
            tableData = [
                { tableId: 1, tableNumber: "T-01", capacity: 2, tableStatus: "AVAILABLE", xPos: 50, yPos: 50, shape: "RECTANGLE", width: 110, height: 110 },
                { tableId: 2, tableNumber: "T-02", capacity: 4, tableStatus: "AVAILABLE", xPos: 200, yPos: 50, shape: "RECTANGLE", width: 110, height: 110 },
                { tableId: 3, tableNumber: "T-03", capacity: 4, tableStatus: "AVAILABLE", xPos: 350, yPos: 50, shape: "RECTANGLE", width: 110, height: 110 },
                { tableId: 4, tableNumber: "T-04", capacity: 6, tableStatus: "AVAILABLE", xPos: 500, yPos: 50, shape: "RECTANGLE", width: 110, height: 110 },
                { tableId: 5, tableNumber: "T-05", capacity: 8, tableStatus: "OCCUPIED", xPos: 50, yPos: 200, shape: "ROUND", width: 120, height: 120 },
                { tableId: 6, tableNumber: "T-06", capacity: 4, tableStatus: "RESERVED", xPos: 200, yPos: 200, shape: "RECTANGLE", width: 110, height: 110 }
            ];
            renderFloorPlan();
        }
    }

    function updateStats() {
        let avail = 0, occ = 0, res = 0, bill = 0;
        tableData.forEach(t => {
            const s = (t.tableStatus || 'AVAILABLE').toUpperCase();
            if (s === 'AVAILABLE') avail++;
            else if (s === 'OCCUPIED') occ++;
            else if (s === 'RESERVED') res++;
            else if (s === 'BILLED') bill++;
        });
        document.getElementById('statAvailable').innerText = avail;
        document.getElementById('statOccupied').innerText = occ;
        document.getElementById('statReserved').innerText = res;
        document.getElementById('statBilled').innerText = bill;
    }

    function renderFloorPlan() {
        updateStats();
        const canvas = document.getElementById('floorCanvas');
        if (!canvas) return;
        canvas.innerHTML = '';
        tableData.forEach((t, idx) => {
            const status = t.tableStatus || 'AVAILABLE';
            const shape = t.shape || 'RECTANGLE';
            const tableNo = t.tableNumber || ('T-' + String(idx + 1).padStart(2, '0'));
            const capacity = t.capacity || 4;
            const posX = (t.xPos !== undefined && t.xPos !== null && t.xPos > 0) ? t.xPos : (idx % 5 * 150 + 40);
            const posY = (t.yPos !== undefined && t.yPos !== null && t.yPos > 0) ? t.yPos : (Math.floor(idx / 5) * 140 + 40);
            const width = t.width || 110;
            const height = t.height || 110;

            const div = document.createElement('div');
            div.className = 'draggable-table status-' + status + ' shape-' + shape;
            div.style.left = posX + 'px';
            div.style.top = posY + 'px';
            div.style.width = width + 'px';
            div.style.height = height + 'px';
            div.dataset.id = t.tableId;

            div.innerHTML = '<i class="fa-solid fa-chair mb-1 fs-5"></i>' +
                '<span>' + tableNo + '</span>' +
                '<small style="font-size:0.75rem;" class="opacity-90">' + capacity + ' Seats</small>';

            div.onmousedown = (e) => startDrag(e, t, div);
            canvas.appendChild(div);
        });
    }

    function startDrag(e, tableObj, element) {
        activeDrag = { obj: tableObj, el: element };
        const rect = element.getBoundingClientRect();
        offsetX = e.clientX - rect.left;
        offsetY = e.clientY - rect.top;

        document.onmousemove = doDrag;
        document.onmouseup = stopDrag;
    }

    function doDrag(e) {
        if (!activeDrag) return;
        const canvasRect = document.getElementById('floorCanvas').getBoundingClientRect();
        let newX = e.clientX - canvasRect.left - offsetX;
        let newY = e.clientY - canvasRect.top - offsetY;

        if (newX < 0) newX = 0;
        if (newY < 0) newY = 0;
        if (newX > canvasRect.width - 110) newX = canvasRect.width - 110;
        if (newY > canvasRect.height - 110) newY = canvasRect.height - 110;

        activeDrag.el.style.left = newX + 'px';
        activeDrag.el.style.top = newY + 'px';

        activeDrag.obj.xPos = Math.round(newX);
        activeDrag.obj.yPos = Math.round(newY);
    }

    function stopDrag() {
        activeDrag = null;
        document.onmousemove = null;
        document.onmouseup = null;
    }

    async function saveFloorLayout() {
        try {
            const res = await fetch('/api/tables/floor-plan/layout', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(tableData)
            });
            if (res.ok) {
                if (typeof showToast === 'function') {
                    showToast('Layout Saved', 'Floor plan layout has been saved successfully!');
                } else {
                    alert('✅ Restaurant floor plan layout saved successfully!');
                }
            } else {
                alert('Failed to save floor layout.');
            }
        } catch (err) {
            alert('Error saving layout: ' + err.message);
        }
    }

    document.addEventListener('DOMContentLoaded', loadTables);
    loadTables();
</script>

<%@ include file="includes/footer.jsp" %>
