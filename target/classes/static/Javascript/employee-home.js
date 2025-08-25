function showSection(sectionId) {
	document.querySelectorAll('.panel').forEach(panel => {
		panel.classList.remove('active');
	});
	document.getElementById(sectionId).classList.add('active');
}

// Logout action
document.querySelector('.logout-btn').addEventListener('click', () => {
	if (confirm("Are you sure you want to logout?")) {
		window.location.href = "/employee/logout"; // Redirect to login page
	}
});
