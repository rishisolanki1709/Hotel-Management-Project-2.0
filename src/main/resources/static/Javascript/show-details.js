// === Elements ===
const checkIn = document.getElementById('checkIn');
const checkOut = document.getElementById('checkOut');
const nightsEl = document.getElementById('nights');
const totalEl = document.getElementById('totalDisplay');
const totalHidden = document.getElementById('total');
const bookingMode = document.getElementById("bookingMode");
const offlineBox = document.getElementById("offlineBox");
const paidInput = document.getElementById("paidAmount");
const pendingText = document.getElementById("pendingDisplay");
const pendingHidden = document.getElementById("pendingAmount");

// === Prefill values from backend ===
function prefillFromServer() {
    // Already filled by Thymeleaf with th:value/text
    // Just ensure JS logic starts with correct values
    updatePending();
}

// === Date & Nights Calculation ===
function calc() {
    if (!checkIn.value || !checkOut.value) {
        nightsEl.textContent = '0';
        totalEl.textContent = '0';
        totalHidden.value = 0;
        return;
    }

    const inDate = new Date(checkIn.value);
    const outDate = new Date(checkOut.value);

    if (outDate <= inDate) {
        nightsEl.textContent = '0';
        totalEl.textContent = '0';
        totalHidden.value = 0;
        return;
    }

    const ms = outDate - inDate;
    const nights = Math.round(ms / (1000 * 60 * 60 * 24));
    nightsEl.textContent = String(nights);

    const perNight = Number(document.getElementById('roomPriceData')?.dataset.price || 0);
    const total = nights * perNight;

    totalEl.textContent = String(total);
    totalHidden.value = total;

    if (bookingMode.value === "OFFLINE") {
        updatePending();
    }
}

// === Pending Calculation ===
function updatePending() {
    const total = Number(totalEl.textContent) || 0;
    const paid = Number(paidInput.value) || 0;
    const pending = Math.max(total - paid, 0);

    pendingText.textContent = String(pending);
    pendingHidden.value = pending;
}

// === Booking Mode Handling ===
bookingMode.addEventListener("change", function() {
    if (this.value === "OFFLINE") {
        offlineBox.style.display = "block";
        updatePending();
    } else {
        offlineBox.style.display = "none";
        pendingText.textContent = "0";
        pendingHidden.value = "0";
    }
});

paidInput.addEventListener("input", updatePending);
checkIn.addEventListener('change', (e) => { checkOut.min = e.target.value; calc(); });
checkOut.addEventListener('change', calc);

// === Prevent checkout if pending > 0 ===
/*
document.querySelector("form").addEventListener("submit", function(e) {
    if (Number(pendingHidden.value) > 0) {
        e.preventDefault();
        alert("Pending amount must be cleared before checkout!");
    }
});
*/
// === Run on page load ===
window.addEventListener("DOMContentLoaded", prefillFromServer);
