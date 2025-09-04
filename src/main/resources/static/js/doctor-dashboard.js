document.addEventListener("DOMContentLoaded", () => {
    const cards = document.querySelectorAll(".dashboard-card, .action-card");

    cards.forEach(card => {
        card.addEventListener("mouseover", () => {
            card.style.cursor = "pointer";
        });
    });
});

document.querySelectorAll(".square-btn").forEach((btn) => {
	btn.addEventListener("mousemove", (e) => {
		const rect = btn.getBoundingClientRect();
		const x = e.clientX - rect.left;
		const y = e.clientY - rect.top;
		btn.style.setProperty("--mouse-x", `${x}px`);
		btn.style.setProperty("--mouse-y", `${y}px`);
	});
});

document.querySelectorAll(".panel-toggle").forEach((toggle) => {
	toggle.addEventListener("click", () => {
		const expanded = toggle.getAttribute("aria-expanded") === "true";
		toggle.setAttribute("aria-expanded", !expanded);

		const patientInfo = toggle.querySelector(".patient-info");
		const content = toggle.nextElementSibling;

		if (!expanded) {
			// Opening
			patientInfo.style.display = "none";
			content.classList.add("open");
		} else {
			// Closing
			patientInfo.style.display = "";
			content.classList.remove("open");
		}
	});
});
