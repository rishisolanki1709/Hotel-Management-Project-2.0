// Sticky header shadow + mobile menu
const header = document.getElementById('header');
const nav = document.getElementById('nav');
const hamburger = document.getElementById('hamburger');
window.addEventListener('scroll', () => {
	header.style.boxShadow = window.scrollY > 10 ? '0 8px 20px rgba(0,0,0,.3)' : 'none';
});
hamburger.addEventListener('click', () => nav.classList.toggle('open'));

// Year
document.getElementById('year').textContent = new Date().getFullYear();

// Parallax hero
const hero = document.querySelector('[data-parallax] .hero-media');
window.addEventListener('scroll', () => {
	const y = Math.min(window.scrollY * 0.2, 80);
	hero.style.transform = `translate3d(0, ${y}px, 0) scale(1.03)`;
});

// Reveal on scroll
const revealEls = document.querySelectorAll('.reveal, .amenity, .contact-card');
const io = new IntersectionObserver((entries) => {
	entries.forEach(e => {
		if (e.isIntersecting) { e.target.classList.add('visible'); io.unobserve(e.target); }
	});
}, { threshold: .12 });
revealEls.forEach(el => io.observe(el));

// Booking calc (real-time)
const checkIn = document.getElementById('checkIn');
const checkOut = document.getElementById('checkOut');
const nightsEl = document.getElementById('nights');
const totalDisplay = document.getElementById('totalDisplay');
const totalHidden = document.getElementById('total');
const roomType = document.getElementById('roomType');

const todayStr = new Date().toISOString().split('T')[0];
if (checkIn) checkIn.min = todayStr;
if (checkOut) checkOut.min = todayStr;

function priceFromRoom() {
	const opt = roomType.selectedOptions[0];
	return Number(opt.dataset.price || 0);
}
function calc() {
	if (!checkIn.value || !checkOut.value) {
		nightsEl.textContent = '0';
		setTotal(0); return;
	}
	const inD = new Date(checkIn.value), outD = new Date(checkOut.value);
	if (outD <= inD) { nightsEl.textContent = '0'; setTotal(0); return; }
	const nights = Math.round((outD - inD) / (1000 * 60 * 60 * 24));
	nightsEl.textContent = String(nights);
	setTotal(nights * priceFromRoom());
}
function setTotal(val) {
	totalDisplay.textContent = `$${val}`;
	totalHidden.value = val;
}
['change', 'input'].forEach(ev => {
	[checkIn, checkOut, roomType].forEach(el => el && el.addEventListener(ev, calc));
});
calc();
/*
document.getElementById('bookingForm')?.addEventListener('submit', (e) => {
	e.preventDefault();
	// cute confirmation
	e.target.querySelector('.btn').textContent = 'Checking...';
	setTimeout(() => {
		window.location.href = "/customer/Register_New_Book";
	}, 600);
});*/

// Gallery Lightbox
const lightbox = document.getElementById('lightbox');
const lightboxImg = document.getElementById('lightboxImg');
const lightboxClose = document.getElementById('lightboxClose');
document.querySelectorAll('.lightboxable').forEach(img => {
	img.addEventListener('click', () => {
		lightboxImg.src = img.src;
		lightbox.classList.add('open');
		document.body.style.overflow = 'hidden';
	});
});
function closeLightbox() {
	lightbox.classList.remove('open');
	document.body.style.overflow = '';
}
lightboxClose.addEventListener('click', closeLightbox);
lightbox.addEventListener('click', (e) => { if (e.target === lightbox) closeLightbox(); });
window.addEventListener('keydown', (e) => e.key === 'Escape' && closeLightbox());

// Reviews carousel
const track = document.querySelector('.c-track');
const prev = document.querySelector('.c-btn.prev');
const next = document.querySelector('.c-btn.next');
let index = 0;
function slide(to) {
	const items = document.querySelectorAll('.c-item').length;
	index = (to + items) % items;
	track.style.transform = `translateX(-${index * 100}%)`;
}
prev?.addEventListener('click', () => slide(index - 1));
next?.addEventListener('click', () => slide(index + 1));
setInterval(() => slide(index + 1), 6000);


