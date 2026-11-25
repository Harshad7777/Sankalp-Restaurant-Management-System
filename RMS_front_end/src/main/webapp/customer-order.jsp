<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sankalp RMS - Digital Table Menu & Self Ordering</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root { --primary-color: #e65100; --accent-color: #ff9800; }
        body { background-color: #f8f9fa; font-family: 'Segoe UI', Roboto, sans-serif; padding-bottom: 90px; }
        .hero-banner { background: linear-gradient(135deg, #e65100, #ff9800); color: white; padding: 25px 15px; border-radius: 0 0 25px 25px; }
        .menu-card { border: none; border-radius: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.06); transition: transform 0.2s; margin-bottom: 15px; }
        .menu-card:active { transform: scale(0.98); }
        .cart-bar { position: fixed; bottom: 0; left: 0; right: 0; background: white; padding: 15px 20px; border-top: 1px solid #eee; box-shadow: 0 -5px 15px rgba(0,0,0,0.1); z-index: 1000; }
        .badge-veg { border: 1px solid #2e7d32; color: #2e7d32; padding: 2px 6px; border-radius: 4px; font-size: 0.75rem; }
        .badge-nonveg { border: 1px solid #c62828; color: #c62828; padding: 2px 6px; border-radius: 4px; font-size: 0.75rem; }
    </style>
</head>
<body>

    <div class="hero-banner text-center shadow-sm">
        <h3 class="fw-bold mb-1"><i class="fas fa-utensils me-2"></i>Sankalp Restaurant</h3>
        <p class="mb-0 fs-6 opacity-90" id="tableHeader"><i class="fas fa-qrcode me-1"></i> Table Self-Ordering PWA</p>
    </div>

    <div class="container mt-4">
        <!-- Guest Registration Input -->
        <div class="card p-3 mb-4 border-0 shadow-sm rounded-4">
            <div class="row g-2">
                <div class="col-6">
                    <input type="text" id="guestName" class="form-control form-control-sm rounded-3" placeholder="Your Name">
                </div>
                <div class="col-6">
                    <input type="tel" id="guestMobile" class="form-control form-control-sm rounded-3" placeholder="Mobile Number">
                </div>
            </div>
        </div>

        <!-- Menu Categories Nav -->
        <div class="d-flex overflow-auto mb-3 pb-2" id="categoryNav">
            <button class="btn btn-primary rounded-pill me-2 btn-sm active px-3">All Items</button>
        </div>

        <!-- Menu Item Cards -->
        <div class="row" id="menuItemsContainer">
            <div class="text-center py-5 text-muted">
                <div class="spinner-border text-warning mb-2" role="status"></div>
                <p>Loading digital menu items...</p>
            </div>
        </div>
    </div>

    <!-- Floating Mobile Cart Bar -->
    <div class="cart-bar d-flex justify-content-between align-items-center">
        <div>
            <span class="text-muted d-block small" id="cartItemCount">0 items selected</span>
            <strong class="fs-5 text-dark" id="cartTotal">₹0.00</strong>
        </div>
        <button class="btn btn-warning px-4 py-2 rounded-pill fw-bold text-white shadow-sm" onclick="submitCustomerOrder()">
            Place Order <i class="fas fa-paper-plane ms-1"></i>
        </button>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/config.js?v=1.0.0"></script>
    <script>
        const urlParams = new URLSearchParams(window.location.search);
        const tableId = urlParams.get('tableId') || 1;
        document.getElementById('tableHeader').innerHTML = '<i class="fas fa-chair me-1"></i> Table #' + tableId + ' Self-Ordering PWA';

        let cart = [];

        async function loadMenu() {
            try {
                const res = await fetch(window.getApiUrl('/menu'));
                const items = await res.json();
                let html = '';
                items.forEach(item => {
                    const isVegBadge = item.isVeg ? '<span class="badge-veg mb-1 d-inline-block"><i class="fas fa-circle me-1"></i>VEG</span>' : '<span class="badge-nonveg mb-1 d-inline-block"><i class="fas fa-circle me-1"></i>NON-VEG</span>';
                    const desc = item.description || 'Delicious freshly prepared dish.';
                    html += '<div class="col-12 col-md-6">' +
                        '<div class="card menu-card p-3">' +
                        '<div class="d-flex justify-content-between align-items-start">' +
                        '<div>' +
                        isVegBadge +
                        '<h6 class="fw-bold mb-1">' + item.itemName + '</h6>' +
                        '<p class="text-muted small mb-2">' + desc + '</p>' +
                        '<span class="fw-bold text-success fs-6">₹' + item.price + '</span>' +
                        '</div>' +
                        '<button class="btn btn-outline-warning btn-sm rounded-pill px-3" onclick="addToCart(' + item.itemId + ', \'' + item.itemName.replace(/'/g, "\\'") + '\', ' + item.price + ')">' +
                        '+ Add' +
                        '</button>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                });
                document.getElementById('menuItemsContainer').innerHTML = html;
            } catch (err) {
                console.error(err);
                document.getElementById('menuItemsContainer').innerHTML = '<div class="alert alert-danger">Failed to load menu items.</div>';
            }
        }

        function addToCart(id, name, price) {
            const existing = cart.find(i => i.itemId === id);
            if (existing) {
                existing.quantity++;
            } else {
                cart.push({ itemId: id, itemName: name, price: price, quantity: 1 });
            }
            updateCartUI();
        }

        function updateCartUI() {
            let total = 0;
            let count = 0;
            cart.forEach(i => {
                total += i.price * i.quantity;
                count += i.quantity;
            });
            document.getElementById('cartItemCount').innerText = count + ' item(s) selected';
            document.getElementById('cartTotal').innerText = '₹' + total.toFixed(2);
        }

        async function submitCustomerOrder() {
            if (cart.length === 0) {
                alert("Please add items to your cart first.");
                return;
            }
            const name = document.getElementById('guestName').value || 'Guest';
            const mobile = document.getElementById('guestMobile').value || '9999999999';

            const payload = {
                tableId: parseInt(tableId),
                customerId: 1,
                staffId: 1,
                items: cart.map(i => ({ itemId: i.itemId, quantity: i.quantity, price: i.price }))
            };

            try {
                const res = await fetch(window.getApiUrl('/orders'), {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                if (res.ok) {
                    alert('🎉 Order placed successfully from Table #' + tableId + '! Your kitchen ticket has been sent.');
                    cart = [];
                    updateCartUI();
                    
                    fetch(window.getApiUrl('/notifications/whatsapp/send'), {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ phone: mobile, message: 'Thank you ' + name + '! Your order from Table #' + tableId + ' was sent to kitchen.' })
                    });
                } else {
                    alert("Failed to submit order. Please check connection.");
                }
            } catch (err) {
                alert("Error placing order: " + err.message);
            }
        }

        loadMenu();
    </script>
</body>
</html>
