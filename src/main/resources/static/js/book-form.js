function checkSlots() {
    const doctorId = document.getElementById('doctorSelect').value;
    const date = document.getElementById('date').value;

    if (!doctorId || !date) return;

    fetch('/patient/book', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `doctorId=${doctorId}&date=${date}`
    })
    .then(response => response.text())
    .then(html => {
        const temp = document.createElement('div');
        temp.innerHTML = html;
        const slots = temp.querySelector(`#slots-${doctorId}`);
        if (slots) {
            document.getElementById('slotContainer').innerHTML = slots.innerHTML;
            document.getElementById('doctorId').value = doctorId;
        }
    });
}

function selectSlot(time, element) {
    document.getElementById('timeInput').value = time;

    document.querySelectorAll('.slot').forEach(btn => {
        btn.style.backgroundColor = '#ccc';
    });

    element.style.backgroundColor = 'green';
}

function clearSlots() {
    document.getElementById('slotContainer').innerHTML = '';
    document.getElementById('timeInput').value = '';
}
