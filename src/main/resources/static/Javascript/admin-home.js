function showSection(sectionId) {
    document.querySelectorAll('.panel').forEach(panel => {
        panel.classList.remove('active');
    });
    document.getElementById(sectionId).classList.add('active');
}

// Logout action
document.querySelector('.logout-btn').addEventListener('click', () => {
    if (confirm("Are you sure you want to logout?")) {
        window.location.href = "/admin/logout"; // Redirect to login page
    }
});

document.querySelectorAll(".remove-btn").forEach(btn => {
  btn.addEventListener("click", (e) => {
    e.preventDefault(); // stop immediate submission
    if (confirm("Are you sure you want to remove?")) {
      e.currentTarget.closest("form").submit(); 
    }
  });
});