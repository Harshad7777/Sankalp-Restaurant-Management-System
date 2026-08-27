"use strict";

document.addEventListener("DOMContentLoaded", function () {
    const registerForm = document.getElementById("registerForm");
    const messageBox = document.getElementById("message");

    function showMessage(msg, isSuccess = false) {
        if (!messageBox) {
            alert(msg);
            return;
        }
        const span = messageBox.querySelector("span");
        if (span) span.textContent = msg;
        messageBox.style.display = "flex";
        if (isSuccess) {
            messageBox.style.background = "rgba(34, 197, 94, 0.15)";
            messageBox.style.borderColor = "rgba(34, 197, 94, 0.4)";
            messageBox.style.color = "#4ade80";
        } else {
            messageBox.style.background = "rgba(239, 68, 68, 0.15)";
            messageBox.style.borderColor = "rgba(239, 68, 68, 0.4)";
            messageBox.style.color = "#f87171";
        }
    }

    if (registerForm) {
        registerForm.addEventListener("submit", async function (e) {
            e.preventDefault();

            const fullName = document.getElementById("fullName").value.trim();
            const username = document.getElementById("username").value.trim();
            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value;
            const role = document.getElementById("role").value;

            if (!fullName || !username || !email || !password) {
                showMessage("All fields are required.");
                return;
            }

            if (password.length < 6) {
                showMessage("Password must be at least 6 characters.");
                return;
            }

            const user = {
                fullName: fullName,
                username: username,
                email: email,
                password: password,
                role: role
            };

            try {
                const backendBase = window.location.origin.includes(":8081") 
                    ? "http://localhost:8080" 
                    : (window.location.origin + "/RMS_backend");
                const response = await fetch("/api/users/register", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    },
                    body: JSON.stringify(user)
                });

                const text = await response.text();
                let result;
                try {
                    result = JSON.parse(text);
                } catch {
                    result = { message: text };
                }

                if (response.ok) {
                    showMessage(result.message || "Registration Successful! Redirecting to login...", true);
                    setTimeout(() => {
                        window.location.href = "login.jsp";
                    }, 1500);
                } else {
                    showMessage(result.message || "Registration failed. Please try again.");
                    console.error("Registration error:", result);
                }

            } catch (error) {
                console.error("Network Error:", error);
                showMessage("Cannot connect to server. Please ensure backend is running.");
            }
        });
    }
});