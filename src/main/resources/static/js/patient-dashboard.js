document.addEventListener("DOMContentLoaded", () => {
  // Mobile menu toggle
  const hamburger = document.querySelector(".hamburger");
  const navLinks = document.querySelector(".nav-links");
  if (hamburger) {
    hamburger.addEventListener("click", () => {
      navLinks.classList.toggle("show");
    });
  }

  // Panel collapse/expand
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

  // Remind me button animation + toast
  const reminderBtn = document.getElementById("reminderBtn");
  const toast = document.getElementById("toast");
  if (reminderBtn && toast) {
    reminderBtn.addEventListener("click", () => {
      // small pulse animation
      reminderBtn.animate([
        { transform: "scale(1)" },
        { transform: "scale(1.08)" },
        { transform: "scale(1)" }
      ], { duration: 350, easing: "ease-out" });

      // show toast
      toast.textContent = "Reminder set — we'll notify you 1 hour before the appointment.";
      toast.style.opacity = "1";
      toast.style.transform = "translateY(0)";
      setTimeout(() => {
        toast.style.opacity = "0";
        toast.style.transform = "translateY(20px)";
      }, 3200);
    });
  }

  // Cards clickable: anchor cards already navigate; for the non-anchor cards, enable click navigation if data-href present
  document.querySelectorAll(".card").forEach(card => {
    // use th:href result if server rendered as href, else check data-href
    const link = card.getAttribute("href") || card.getAttribute("data-href");
    if (!link) {
      card.addEventListener("click", () => {
        // for example messages card -> open messages route (if configured)
        const id = card.id;
        if (id === "messagesCard") {
          // redirect (Thymeleaf will resolve if server-side set a value into data-href)
          const dh = card.getAttribute("data-href");
          if (dh) window.location.href = dh;
        }
      });
    }
  });

  // small entrance animation for cards
  // const cards = document.querySelectorAll(".cards-grid .card");
  // cards.forEach((c, i) => {
  //   c.style.opacity = 0;
  //   c.style.transform = "translateY(12px)";
  //   setTimeout(() => {
  //     c.style.transition = "all 420ms cubic-bezier(.16,.84,.32,1)";
  //     c.style.opacity = 1;
  //     c.style.transform = "translateY(0)";
  //   }, 100 + i * 80);
  // });

  // Optional: enable keyboard focus styling for accessibility
  document.addEventListener("keydown", (e) => {
    if (e.key === "Tab") document.documentElement.classList.add("user-is-tabbing");
  });
});
