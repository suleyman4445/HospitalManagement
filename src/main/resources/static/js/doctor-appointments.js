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

document.addEventListener("DOMContentLoaded", function() {
    const statusEl = document.getElementById("status");
    const status = statusEl.dataset.status;
    const actionsContainer = document.querySelector(".actions");

    actionsContainer.innerHTML="";

    const createButton = (text, onClick, className) => {
        const btn = document.createElement("button");
        btn.textContent=text;
        btn.addEventListener("click",onClick);
        btn.classList.add("action-btn");
        if(className) {
            btn.classList.add(className);
        }
        actionsContainer.appendChild(btn);
    };

    switch (status) {
        case "PENDING":
            createButton("Payment Info", () => alert("Payment Info"), "btn-info");
            createButton("Cancel", () => alert("Cancelled!"), "btn-cancel");
            break;
        case "CONFIRMED":
            createButton("Reschedule", () => alert("Rescheduled!"), "btn-reschedule");
            createButton("Cancel", () => alert("Cancelled!"), "btn-cancel");
            break;
        case "COMPLETED":
            createButton("Prescription", () => alert("Prescription"), "btn-report");
            createButton("Reports", () => alert("Reports"), "btn-report");
            break;
        case "CANCELLED":
        case "MISSED":
            createButton("Reschedule", () => alert("Rescheduled!"), "btn-reschedule");
            break;
        default:
            createButton("View Details", () => alert("Viewing details..."), "btn-details");
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const filterDropdown = document.getElementById("statusFilter");
    const appointmentItems = document.querySelectorAll(".queue-item");

    filterDropdown.addEventListener("change", () => {
        const filter = filterDropdown.value;

        appointmentItems.forEach(item => {
            const status = item.querySelector("#status").getAttribute("data-status");
            if (filter === "ALL" || status === filter) {
                item.style.display = "";
            } else {
                item.style.display = "none";
            }
        });
    });
});

