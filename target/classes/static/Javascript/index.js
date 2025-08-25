function toggleRole(header) {
	const card = header.parentElement;

	// Close others
	document.querySelectorAll('.role-card').forEach(c => {
		if (c !== card) c.classList.remove('active');
	});

	// Toggle clicked
	card.classList.toggle('active');
}