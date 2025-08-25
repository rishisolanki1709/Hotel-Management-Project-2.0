document.getElementById("checkoutForm").addEventListener("submit", function(event) {
	let pendingAmount = parseInt(document.getElementById("pendingAmount").innerText);

	if (pendingAmount > 0) {
		event.preventDefault();
		alert("Checkout not allowed! Pending amount must be cleared before checkout.");
	}
});

