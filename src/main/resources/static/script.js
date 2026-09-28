const API_BASE = "http://localhost:8080/api";

let workers = [];
let worksites = [];
let attendanceList = [];
let records = [];


/* =====================================================
   NAVIGATION
===================================================== */

function showSection(sectionName, button) {

    document.querySelectorAll(".section").forEach(section => {
        section.classList.remove("active");
    });

    document.querySelectorAll(".nav-item").forEach(item => {
        item.classList.remove("active");
    });

    document.getElementById(sectionName).classList.add("active");

    if (button) {
        button.classList.add("active");

        document.getElementById("pageTitle").textContent =
            button.textContent.trim();
    }
}


function showSectionByName(sectionName) {

    document.querySelectorAll(".section").forEach(section => {
        section.classList.remove("active");
    });

    document.querySelectorAll(".nav-item").forEach(item => {
        item.classList.remove("active");
    });

    document.getElementById(sectionName).classList.add("active");

    const button = document.querySelector(
        `.nav-item[onclick*="'${sectionName}'"]`
    );

    if (button) {
        button.classList.add("active");
        document.getElementById("pageTitle").textContent =
            button.textContent.trim();
    }

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}


/* =====================================================
   COMMON API FUNCTION
===================================================== */

async function apiRequest(url, options = {}) {

    try {

        const response = await fetch(url, {

            ...options,

            headers: {
                "Content-Type": "application/json",
                ...(options.headers || {})
            }

        });


        const text = await response.text();

        let data = null;

        if (text) {

            try {
                data = JSON.parse(text);
            }

            catch {
                data = text;
            }

        }


        if (!response.ok) {

            throw new Error(
                typeof data === "string"
                    ? data
                    : `Request failed (${response.status})`
            );

        }


        return data;

    }

    catch (error) {

        console.error(error);

        throw error;

    }

}


/* =====================================================
   MESSAGE
===================================================== */

function showMessage(message, type = "success") {

    const box = document.getElementById("message");

    const div = document.createElement("div");

    div.className = `message ${type}`;

    div.textContent = message;

    box.appendChild(div);


    setTimeout(() => {

        div.remove();

    }, 3000);

}


/* =====================================================
   API STATUS
===================================================== */

async function checkApi() {

    const status =
        document.getElementById("apiStatus");

    try {

        await fetch(`${API_BASE}/workers`);

        status.textContent =
            "Backend Connected";

    }

    catch {

        status.textContent =
            "Backend Offline";

    }

}


/* =====================================================
   WORKERS
===================================================== */

async function loadWorkers() {

    try {

        workers =
            await apiRequest(`${API_BASE}/workers`);

        renderWorkers();

        updateDashboard();

    }

    catch (error) {

        showMessage(
            "Unable to load workers",
            "error"
        );

    }

}


function renderWorkers() {

    const table =
        document.getElementById("workerTable");


    if (!workers.length) {

        table.innerHTML = `
            <tr>
                <td colspan="5" class="empty">
                    No workers found
                </td>
            </tr>
        `;

        return;

    }


    table.innerHTML =
        workers.map(worker => `

        <tr>

            <td>
                <strong>#${worker.id}</strong>
            </td>

            <td>
                ${escapeHtml(worker.name)}
            </td>

            <td>
                ₹${Number(worker.dailyWage).toFixed(2)}
            </td>

            <td>
                ${escapeHtml(worker.phone)}
            </td>

            <td>

                <div class="action-buttons">

                    <button
                        class="edit-btn"
                        onclick="editWorker(${worker.id})">
                        Edit
                    </button>

                    <button
                        class="delete-btn"
                        onclick="deleteWorker(${worker.id})">
                        Delete
                    </button>

                </div>

            </td>

        </tr>

    `).join("");

}


document
    .getElementById("workerForm")
    .addEventListener("submit", async function(event) {

        event.preventDefault();


        const id =
            document.getElementById("workerId").value;


        const worker = {

            name:
            document.getElementById("workerName").value,

            dailyWage:
                Number(
                    document.getElementById("dailyWage").value
                ),

            phone:
            document.getElementById("workerPhone").value

        };


        try {

            if (id) {

                await apiRequest(
                    `${API_BASE}/workers/${id}`,
                    {
                        method: "PUT",
                        body: JSON.stringify(worker)
                    }
                );

                showMessage(
                    "Worker updated successfully"
                );

            }

            else {

                await apiRequest(
                    `${API_BASE}/workers`,
                    {
                        method: "POST",
                        body: JSON.stringify(worker)
                    }
                );

                showMessage(
                    "Worker added successfully"
                );

            }


            resetWorkerForm();

            await loadWorkers();

        }

        catch (error) {

            showMessage(
                error.message,
                "error"
            );

        }

    });


function editWorker(id) {

    const worker =
        workers.find(item => item.id === id);

    if (!worker) return;


    document.getElementById("workerId").value =
        worker.id;

    document.getElementById("workerName").value =
        worker.name;

    document.getElementById("dailyWage").value =
        worker.dailyWage;

    document.getElementById("workerPhone").value =
        worker.phone;


    showSectionByName("workers");

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });

}


async function deleteWorker(id) {

    if (!confirm(
        `Are you sure you want to delete Worker #${id}?`
    )) {
        return;
    }


    try {

        await apiRequest(
            `${API_BASE}/workers/${id}`,
            {
                method: "DELETE"
            }
        );


        showMessage(
            "Worker deleted successfully"
        );


        await loadWorkers();

    }

    catch (error) {

        showMessage(
            "Unable to delete worker. Check the backend/database.",
            "error"
        );

        console.error(error);

    }

}


function resetWorkerForm() {

    document
        .getElementById("workerForm")
        .reset();

    document
        .getElementById("workerId")
        .value = "";

}


/* =====================================================
   WORKSITES
===================================================== */

async function loadWorksites() {

    try {

        worksites =
            await apiRequest(`${API_BASE}/worksites`);

        renderWorksites();

        updateDashboard();

    }

    catch (error) {

        showMessage(
            "Unable to load worksites",
            "error"
        );

    }

}


function renderWorksites() {

    const table =
        document.getElementById("worksiteTable");


    if (!worksites.length) {

        table.innerHTML = `
            <tr>
                <td colspan="4" class="empty">
                    No worksites found
                </td>
            </tr>
        `;

        return;

    }


    table.innerHTML =
        worksites.map(worksite => `

        <tr>

            <td>
                <strong>#${worksite.id}</strong>
            </td>

            <td>
                ${escapeHtml(worksite.name)}
            </td>

            <td>
                ${escapeHtml(worksite.location)}
            </td>

            <td>

                <div class="action-buttons">

                    <button
                        class="edit-btn"
                        onclick="editWorksite(${worksite.id})">
                        Edit
                    </button>

                    <button
                        class="delete-btn"
                        onclick="deleteWorksite(${worksite.id})">
                        Delete
                    </button>

                </div>

            </td>

        </tr>

    `).join("");

}


document
    .getElementById("worksiteForm")
    .addEventListener("submit", async function(event) {

        event.preventDefault();


        const id =
            document.getElementById("worksiteId").value;


        const worksite = {

            name:
            document.getElementById("worksiteName").value,

            location:
            document.getElementById("worksiteLocation").value

        };


        try {

            if (id) {

                await apiRequest(
                    `${API_BASE}/worksites/${id}`,
                    {
                        method: "PUT",
                        body: JSON.stringify(worksite)
                    }
                );

                showMessage(
                    "Worksite updated successfully"
                );

            }

            else {

                await apiRequest(
                    `${API_BASE}/worksites`,
                    {
                        method: "POST",
                        body: JSON.stringify(worksite)
                    }
                );

                showMessage(
                    "Worksite added successfully"
                );

            }


            resetWorksiteForm();

            await loadWorksites();

        }

        catch (error) {

            showMessage(
                error.message,
                "error"
            );

        }

    });


function editWorksite(id) {

    const worksite =
        worksites.find(item => item.id === id);

    if (!worksite) return;


    document.getElementById("worksiteId").value =
        worksite.id;

    document.getElementById("worksiteName").value =
        worksite.name;

    document.getElementById("worksiteLocation").value =
        worksite.location;


    showSectionByName("worksites");

}


async function deleteWorksite(id) {

    if (!confirm(
        `Are you sure you want to delete Worksite #${id}?`
    )) {
        return;
    }


    try {

        await apiRequest(
            `${API_BASE}/worksites/${id}`,
            {
                method: "DELETE"
            }
        );


        showMessage(
            "Worksite deleted successfully"
        );


        await loadWorksites();

    }

    catch (error) {

        showMessage(
            error.message,
            "error"
        );

    }

}


function resetWorksiteForm() {

    document
        .getElementById("worksiteForm")
        .reset();

    document
        .getElementById("worksiteId")
        .value = "";

}


/* =====================================================
   ATTENDANCE
===================================================== */

async function loadAttendance() {

    try {

        attendanceList =
            await apiRequest(
                `${API_BASE}/attendance`
            );

        renderAttendance();

        updateDashboard();

    }

    catch (error) {

        showMessage(
            "Unable to load attendance",
            "error"
        );

    }

}


function renderAttendance() {

    const table =
        document.getElementById("attendanceTable");


    if (!attendanceList.length) {

        table.innerHTML = `
            <tr>
                <td colspan="6" class="empty">
                    No attendance records found
                </td>
            </tr>
        `;

        return;

    }


    table.innerHTML =
        attendanceList.map(item => {

            let badgeClass = "badge-present";

            if (item.status === "ABSENT") {
                badgeClass = "badge-absent";
            }

            else if (item.status === "HALF DAY") {
                badgeClass = "badge-half";
            }


            return `

            <tr>

                <td>
                    <strong>#${item.id}</strong>
                </td>

                <td>
                    ${item.workerId}
                </td>

                <td>
                    ${item.worksiteId}
                </td>

                <td>
                    ${item.date}
                </td>

                <td>

                    <span class="badge ${badgeClass}">
                        ${escapeHtml(item.status)}
                    </span>

                </td>

                <td>

                    <div class="action-buttons">

                        <button
                            class="edit-btn"
                            onclick="editAttendance(${item.id})">
                            Edit
                        </button>

                        <button
                            class="delete-btn"
                            onclick="deleteAttendance(${item.id})">
                            Delete
                        </button>

                    </div>

                </td>

            </tr>

        `;

        }).join("");

}


document
    .getElementById("attendanceForm")
    .addEventListener("submit", async function(event) {

        event.preventDefault();


        const id =
            document.getElementById("attendanceId").value;


        const attendance = {

            workerId:
                Number(
                    document.getElementById(
                        "attendanceWorkerId"
                    ).value
                ),

            worksiteId:
                Number(
                    document.getElementById(
                        "attendanceWorksiteId"
                    ).value
                ),

            date:
            document.getElementById(
                "attendanceDate"
            ).value,

            status:
            document.getElementById(
                "attendanceStatus"
            ).value

        };


        try {

            if (id) {

                await apiRequest(
                    `${API_BASE}/attendance/${id}`,
                    {
                        method: "PUT",
                        body: JSON.stringify(attendance)
                    }
                );

                showMessage(
                    "Attendance updated successfully"
                );

            }

            else {

                await apiRequest(
                    `${API_BASE}/attendance`,
                    {
                        method: "POST",
                        body: JSON.stringify(attendance)
                    }
                );

                showMessage(
                    "Attendance added successfully"
                );

            }


            resetAttendanceForm();

            await loadAttendance();

        }

        catch (error) {

            showMessage(
                error.message,
                "error"
            );

        }

    });


function editAttendance(id) {

    const item =
        attendanceList.find(
            record => record.id === id
        );

    if (!item) return;


    document.getElementById(
        "attendanceId"
    ).value = item.id;


    document.getElementById(
        "attendanceWorkerId"
    ).value = item.workerId;


    document.getElementById(
        "attendanceWorksiteId"
    ).value = item.worksiteId;


    document.getElementById(
        "attendanceDate"
    ).value = item.date;


    document.getElementById(
        "attendanceStatus"
    ).value = item.status;


    showSectionByName("attendance");

}


async function deleteAttendance(id) {

    if (!confirm(
        `Delete attendance record #${id}?`
    )) {
        return;
    }


    try {

        await apiRequest(
            `${API_BASE}/attendance/${id}`,
            {
                method: "DELETE"
            }
        );


        showMessage(
            "Attendance deleted successfully"
        );


        await loadAttendance();

    }

    catch (error) {

        showMessage(
            error.message,
            "error"
        );

    }

}


function resetAttendanceForm() {

    document
        .getElementById("attendanceForm")
        .reset();

    document
        .getElementById("attendanceId")
        .value = "";


    document
        .getElementById("attendanceDate")
        .value =
        new Date()
            .toISOString()
            .split("T")[0];

}


/* =====================================================
   PAYMENT RECORDS
===================================================== */

async function loadRecords() {

    try {

        records =
            await apiRequest(
                `${API_BASE}/records`
            );

        renderRecords();

        updateDashboard();

    }

    catch (error) {

        showMessage(
            "Unable to load payment records",
            "error"
        );

    }

}


function renderRecords() {

    const table =
        document.getElementById("recordTable");


    if (!records.length) {

        table.innerHTML = `
            <tr>
                <td colspan="9" class="empty">
                    No payment records found
                </td>
            </tr>
        `;

        return;

    }


    table.innerHTML =
        records.map(record => {

            const statusClass =
                record.paymentStatus === "PAID"
                    ? "badge-paid"
                    : "badge-unpaid";


            return `

            <tr>

                <td>
                    <strong>#${record.id}</strong>
                </td>

                <td>
                    ${record.workerId}
                </td>

                <td>
                    ${record.date}
                </td>

                <td>
                    ₹${Number(
                record.regularAmount
            ).toFixed(2)}
                </td>

                <td>
                    ${Number(
                record.overtimeHours
            ).toFixed(2)}
                </td>

                <td>
                    ₹${Number(
                record.overtimeAmount
            ).toFixed(2)}
                </td>

                <td>
                    <strong>
                        ₹${Number(
                record.totalAmount
            ).toFixed(2)}
                    </strong>
                </td>

                <td>

                    <span class="badge ${statusClass}">
                        ${escapeHtml(
                record.paymentStatus
            )}
                    </span>

                </td>

                <td>

                    <div class="action-buttons">

                        <button
                            class="edit-btn"
                            onclick="editRecord(${record.id})">
                            Edit
                        </button>

                        <button
                            class="delete-btn"
                            onclick="deleteRecord(${record.id})">
                            Delete
                        </button>

                    </div>

                </td>

            </tr>

        `;

        }).join("");

}


document
    .getElementById("recordForm")
    .addEventListener("submit", async function(event) {

        event.preventDefault();


        const id =
            document.getElementById("recordId").value;


        const record = {

            workerId:
                Number(
                    document.getElementById(
                        "recordWorkerId"
                    ).value
                ),

            date:
            document.getElementById(
                "recordDate"
            ).value,

            regularAmount:
                Number(
                    document.getElementById(
                        "regularAmount"
                    ).value
                ),

            overtimeHours:
                Number(
                    document.getElementById(
                        "overtimeHours"
                    ).value
                ),

            overtimeAmount:
                Number(
                    document.getElementById(
                        "overtimeAmount"
                    ).value
                ),

            totalAmount:
                Number(
                    document.getElementById(
                        "totalAmount"
                    ).value
                ),

            paymentStatus:
            document.getElementById(
                "paymentStatus"
            ).value

        };


        try {

            if (id) {

                await apiRequest(
                    `${API_BASE}/records/${id}`,
                    {
                        method: "PUT",
                        body: JSON.stringify(record)
                    }
                );

                showMessage(
                    "Payment record updated successfully"
                );

            }

            else {

                await apiRequest(
                    `${API_BASE}/records`,
                    {
                        method: "POST",
                        body: JSON.stringify(record)
                    }
                );

                showMessage(
                    "Payment record added successfully"
                );

            }


            resetRecordForm();

            await loadRecords();

        }

        catch (error) {

            showMessage(
                error.message,
                "error"
            );

        }

    });


function editRecord(id) {

    const record =
        records.find(
            item => item.id === id
        );

    if (!record) return;


    document.getElementById(
        "recordId"
    ).value = record.id;


    document.getElementById(
        "recordWorkerId"
    ).value = record.workerId;


    document.getElementById(
        "recordDate"
    ).value = record.date;


    document.getElementById(
        "regularAmount"
    ).value = record.regularAmount;


    document.getElementById(
        "overtimeHours"
    ).value = record.overtimeHours;


    document.getElementById(
        "overtimeAmount"
    ).value = record.overtimeAmount;


    document.getElementById(
        "totalAmount"
    ).value = record.totalAmount;


    document.getElementById(
        "paymentStatus"
    ).value = record.paymentStatus;


    showSectionByName("records");

}


async function deleteRecord(id) {

    if (!confirm(
        `Delete payment record #${id}?`
    )) {
        return;
    }


    try {

        await apiRequest(
            `${API_BASE}/records/${id}`,
            {
                method: "DELETE"
            }
        );


        showMessage(
            "Payment record deleted successfully"
        );


        await loadRecords();

    }

    catch (error) {

        showMessage(
            error.message,
            "error"
        );

    }

}


function resetRecordForm() {

    document
        .getElementById("recordForm")
        .reset();

    document
        .getElementById("recordId")
        .value = "";


    document
        .getElementById("recordDate")
        .value =
        new Date()
            .toISOString()
            .split("T")[0];

}


/* =====================================================
   DASHBOARD
===================================================== */

function updateDashboard() {

    document.getElementById(
        "workerCount"
    ).textContent = workers.length;


    document.getElementById(
        "worksiteCount"
    ).textContent = worksites.length;


    document.getElementById(
        "attendanceCount"
    ).textContent =
        attendanceList.length;


    document.getElementById(
        "recordCount"
    ).textContent =
        records.length;

}


/* =====================================================
   SECURITY / HTML ESCAPE
===================================================== */

function escapeHtml(value) {

    if (
        value === null ||
        value === undefined
    ) {
        return "";
    }


    return String(value)

        .replaceAll("&", "&amp;")

        .replaceAll("<", "&lt;")

        .replaceAll(">", "&gt;")

        .replaceAll('"', "&quot;")

        .replaceAll("'", "&#039;");

}


/* =====================================================
   START APPLICATION
===================================================== */

async function initializeApp() {

    const today =
        new Date()
            .toISOString()
            .split("T")[0];


    document.getElementById(
        "attendanceDate"
    ).value = today;


    document.getElementById(
        "recordDate"
    ).value = today;


    await checkApi();


    await Promise.all([

        loadWorkers(),

        loadWorksites(),

        loadAttendance(),

        loadRecords()

    ]);

}


initializeApp();