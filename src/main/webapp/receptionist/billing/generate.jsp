<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Generate Bill - Sunrise Dental Clinic</title>
        <style type="text/tailwindcss">
            @custom-variant dark (&:where(.dark, .dark *));
    </style>
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css"
            integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA=="
            crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>

    <body class="bg-gray-50 dark:bg-gray-900 transition-colors duration-300 min-h-screen pb-12">

        <%@ include file="../../utils/theme.jsp" %>
            <%@ include file="../includes/receptionist-header.jsp" %>

                <main class="p-8">
                    <div class="mb-8 flex items-center justify-between">
                        <div>
                            <a href="list.jsp"
                                class="text-emerald-600 dark:text-emerald-400 hover:underline mb-2 inline-block"><i
                                    class="fa-solid fa-arrow-left mr-2"></i>Back to Billing History</a>
                            <h2 class="text-3xl font-bold text-gray-800 dark:text-white">Generate New Bill</h2>
                        </div>
                    </div>

                    <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <!-- Left Column: Search & Patient Details -->
                        <div class="lg:col-span-1 space-y-6">
                            <!-- Search Box -->
                            <div
                                class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-md border border-gray-100 dark:border-gray-700">
                                <h3 class="text-lg font-bold text-gray-800 dark:text-white mb-4">Find Patient</h3>
                                <div class="relative">
                                    <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                        <i class="fa-solid fa-search text-gray-400"></i>
                                    </div>
                                    <input type="text" id="patientSearch"
                                        class="block w-full pl-10 pr-3 py-2 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-900 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition-all outline-none"
                                        placeholder="Search by name, phone...">
                                </div>
                                <div id="searchResults" class="mt-3 space-y-2 hidden max-h-64 overflow-y-auto">
                                    <!-- Results dynamically populated -->
                                </div>
                            </div>

                            <!-- Patient Summary Card -->
                            <div id="patientSummaryCard"
                                class="hidden relative overflow-hidden bg-gradient-to-br from-emerald-500 to-teal-600 rounded-2xl p-6 shadow-lg text-white">
                                <div
                                    class="absolute -right-6 -top-6 w-24 h-24 bg-white opacity-10 rounded-full blur-2xl">
                                </div>
                                <div
                                    class="absolute -left-6 -bottom-6 w-32 h-32 bg-teal-900 opacity-20 rounded-full blur-2xl">
                                </div>

                                <div class="relative z-10 flex items-start gap-4">
                                    <div
                                        class="w-16 h-16 rounded-full bg-white/20 flex items-center justify-center text-3xl shrink-0 border border-white/30 backdrop-blur-sm shadow-inner">
                                        <i id="summaryIcon" class="fa-solid fa-user"></i>
                                    </div>
                                    <div class="w-full">
                                        <h3
                                            class="text-xs uppercase tracking-wider font-semibold text-emerald-100 mb-0.5">
                                            Selected Patient</h3>
                                        <h2 id="summaryName"
                                            class="text-xl font-bold mb-3 border-b border-white/20 pb-2">--</h2>

                                        <div class="grid grid-cols-1 gap-2 text-sm text-emerald-50">
                                            <p class="flex items-center gap-x-2"><i
                                                    class="fa-solid fa-phone w-5 text-emerald-200"></i> <span
                                                    id="summaryPhone">--</span></p>
                                            <p class="flex items-center gap-x-2"><i
                                                    class="fa-solid fa-envelope w-5 text-emerald-200"></i> <span
                                                    id="summaryEmail">--</span></p>
                                            <p class="flex items-center gap-x-2"><i
                                                    class="fa-solid fa-cake-candles w-5 text-emerald-200"></i> <span
                                                    id="summaryDob">--</span></p>
                                            <p class="flex items-center gap-x-2"><i
                                                    class="fa-solid fa-location-dot w-5 text-emerald-200"></i> <span
                                                    id="summaryAddress" class="truncate" title="">--</span></p>
                                            <p class="flex items-center text-xs mt-1 text-emerald-200/80 gap-x-2"><i
                                                    class="fa-solid fa-clock w-4"></i> Registered: <span
                                                    id="summaryRegistered" class="ml-1">--</span></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Right Column: Appointments & Calculation -->
                        <div class="lg:col-span-2 space-y-6">
                            <!-- Unbilled Appointments -->
                            <div
                                class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-md border border-gray-100 dark:border-gray-700">
                                <h3
                                    class="text-lg font-bold text-gray-800 dark:text-white mb-4 flex items-center gap-2">
                                    <i class="fa-solid fa-stethoscope text-emerald-500"></i> Pending Appointments
                                </h3>

                                <div id="appointmentsContainer" class="space-y-3">
                                    <p class="text-gray-500 dark:text-gray-400 text-sm italic">Please select a patient
                                        first.</p>
                                </div>
                            </div>

                            <!-- Billing Calculation Form -->
                            <div id="billingFormSection"
                                class="hidden bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-md border border-gray-100 dark:border-gray-700">
                                <h3
                                    class="text-lg font-bold text-gray-800 dark:text-white mb-6 border-b border-gray-200 dark:border-gray-700 pb-3">
                                    Bill Summary</h3>

                                <div class="space-y-4">
                                    <div class="flex justify-between items-center text-gray-600 dark:text-gray-300">
                                        <span>Sub Total:</span>
                                        <span class="font-medium" id="subTotalDisplay">LKR 0.00</span>
                                    </div>

                                    <div class="flex justify-between items-center text-gray-600 dark:text-gray-300">
                                        <label for="taxSelect" class="block">Tax:</label>
                                        <div class="flex items-center gap-3">
                                            <select id="taxSelect"
                                                class="rounded-lg border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-700 dark:text-gray-200 py-1 px-3 outline-none focus:ring-2 focus:ring-emerald-500">
                                                <option value="0">No Tax (0%)</option>
                                                <!-- Populated dynamically -->
                                            </select>
                                            <span class="font-medium w-32 text-right" id="taxPayDisplay">LKR 0.00</span>
                                        </div>
                                    </div>

                                    <div
                                        class="pt-4 border-t border-gray-200 dark:border-gray-700 flex justify-between items-center">
                                        <span class="text-xl font-bold text-gray-800 dark:text-white">Total
                                            Amount:</span>
                                        <span class="text-2xl font-bold text-emerald-600 dark:text-emerald-400"
                                            id="totalAmountDisplay">LKR 0.00</span>
                                    </div>

                                    <div class="pt-4 grid grid-cols-2 gap-4">
                                        <div>
                                            <label
                                                class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">Paid
                                                Amount (LKR)</label>
                                            <input type="number" id="paidAmountInput" value="0.00" min="0" step="0.01"
                                                class="block w-full px-3 py-2 rounded-xl border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 outline-none">
                                        </div>
                                        <div>
                                            <label
                                                class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">Balance
                                                (LKR)</label>
                                            <input type="text" id="balanceAmountDisplay" value="0.00" readonly
                                                class="block w-full px-3 py-2 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-100 dark:bg-gray-800 text-gray-500 font-medium">
                                        </div>
                                    </div>

                                    <button id="generateBillBtn" disabled
                                        class="mt-6 w-full bg-emerald-600 hover:bg-emerald-700 disabled:opacity-50 disabled:cursor-not-allowed text-white font-bold py-3 px-4 rounded-xl shadow-md transition duration-300 flex items-center justify-center gap-2">
                                        <i class="fa-solid fa-file-invoice"></i> Generate Bill
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>

                <!-- Success Modal -->
                <div id="successModal"
                    class="hidden fixed inset-0 bg-gray-900/50 backdrop-blur-sm z-50 flex items-center justify-center">
                    <div
                        class="bg-white dark:bg-gray-800 rounded-2xl p-8 max-w-sm w-full mx-4 shadow-2xl text-center transform scale-95 transition-all">
                        <div
                            class="w-16 h-16 bg-green-100 dark:bg-green-900/30 text-green-500 rounded-full flex items-center justify-center text-3xl mx-auto mb-4">
                            <i class="fa-solid fa-check"></i>
                        </div>
                        <h3 class="text-2xl font-bold text-gray-800 dark:text-white mb-2">Success!</h3>
                        <p class="text-gray-600 dark:text-gray-400 mb-6">Bill has been generated successfully.</p>
                        <div class="space-y-3">
                            <a id="downloadBillBtn" href="#" target="_blank"
                                class="block w-full bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-2.5 rounded-xl transition flex items-center justify-center gap-2">
                                <i class="fa-solid fa-print"></i> Print / Download Bill
                            </a>
                            <a href="list.jsp"
                                class="block w-full bg-gray-100 hover:bg-gray-200 dark:bg-gray-700 dark:hover:bg-gray-600 text-gray-800 dark:text-white font-bold py-2.5 rounded-xl transition">
                                View Billing History
                            </a>
                        </div>
                    </div>
                </div>

                <script>
                    // State
                    const CONTEXT_PATH = '<%= request.getContextPath() %>';

                    function formatCustomDate(dateString) {
                        if (!dateString) return 'N/A';
                        const d = new Date(dateString);
                        if (isNaN(d.getTime())) return 'Invalid Date';
                        const day = String(d.getDate()).padStart(2, '0');
                        const month = String(d.getMonth() + 1).padStart(2, '0');
                        const year = d.getFullYear();
                        return day + '-' + month + '-' + year;
                    }

                    let currentPatientId = null;
                    let unbilledAppointments = [];
                    let selectedAppointments = new Set();

                    let subTotal = 0;
                    let taxPercentage = 0;
                    let taxPay = 0;
                    let totalAmount = 0;
                    let paidAmount = 0;
                    let balanceAmount = 0;

                    // Elements
                    const searchInput = document.getElementById('patientSearch');
                    const searchResults = document.getElementById('searchResults');

                    const patientSummaryCard = document.getElementById('patientSummaryCard');
                    const summaryName = document.getElementById('summaryName');
                    const summaryPhone = document.getElementById('summaryPhone');
                    const summaryEmail = document.getElementById('summaryEmail');

                    const appointmentsContainer = document.getElementById('appointmentsContainer');
                    const billingFormSection = document.getElementById('billingFormSection');
                    const taxSelect = document.getElementById('taxSelect');
                    const paidAmountInput = document.getElementById('paidAmountInput');
                    const generateBillBtn = document.getElementById('generateBillBtn');

                    // Load Taxes on init
                    document.addEventListener('DOMContentLoaded', () => {
                        fetch(CONTEXT_PATH + '/BillServlet?action=getTaxes')
                            .then(res => res.json())
                            .then(data => {
                                data.forEach(tax => {
                                    const opt = document.createElement('option');
                                    opt.value = tax.tax_percantage;
                                    opt.textContent = tax.tax_name + ' (' + tax.tax_percantage + '%)';
                                    taxSelect.appendChild(opt);
                                });
                            })
                            .catch(err => console.error("Error loading taxes:", err));
                    });

                    // Search Patient
                    let searchTimeout;
                    searchInput.addEventListener('input', (e) => {
                        clearTimeout(searchTimeout);
                        const query = e.target.value.trim();
                        if (query.length < 2) {
                            searchResults.classList.add('hidden');
                            return;
                        }
                        searchTimeout = setTimeout(() => {
                            fetch(CONTEXT_PATH + '/BillServlet?action=searchPatients&query=' + encodeURIComponent(query))
                                .then(res => res.json())
                                .then(data => {
                                    searchResults.innerHTML = '';
                                    if (data.length > 0) {
                                        searchResults.classList.remove('hidden');
                                        data.forEach(patient => {
                                            const el = document.createElement('div');
                                            el.className = 'cursor-pointer p-3 bg-gray-50 dark:bg-gray-700/50 hover:bg-emerald-50 dark:hover:bg-emerald-900/20 border border-gray-200 dark:border-gray-600 rounded-lg transition-colors';
                                            el.innerHTML = '<div class="font-semibold text-gray-800 dark:text-gray-200">' + patient.full_name + '</div>' +
                                                '<div class="text-xs text-gray-500">' + patient.phone + '</div>';
                                            el.onclick = () => selectPatient(patient);
                                            searchResults.appendChild(el);
                                        });
                                    } else {
                                        searchResults.classList.remove('hidden');
                                        searchResults.innerHTML = '<div class="p-3 text-sm text-gray-500 text-center">No patients found.</div>';
                                    }
                                });
                        }, 300);
                    });

                    function selectPatient(patient) {
                        searchInput.value = '';
                        searchResults.classList.add('hidden');

                        currentPatientId = patient.patient_id;
                        summaryName.textContent = patient.full_name;
                        summaryPhone.textContent = patient.phone;
                        summaryEmail.textContent = patient.email || 'N/A';
                        document.getElementById('summaryDob').textContent = patient.dob || 'N/A';
                        document.getElementById('summaryAddress').textContent = patient.address || 'N/A';
                        document.getElementById('summaryAddress').title = patient.address || '';
                        document.getElementById('summaryRegistered').textContent = patient.registered_at ? formatCustomDate(patient.registered_at) : 'N/A';

                        const icon = document.getElementById('summaryIcon');
                        if (patient.gender === 'Female' || patient.gender === 'female') icon.className = 'fa-solid fa-person-dress';
                        else if (patient.gender === 'Male' || patient.gender === 'male') icon.className = 'fa-solid fa-person';
                        else icon.className = 'fa-solid fa-user';

                        patientSummaryCard.classList.remove('hidden');

                        // Reset state
                        selectedAppointments.clear();
                        updateCalculations();

                        // Load Appointments
                        appointmentsContainer.innerHTML = '<div class="text-center py-4"><i class="fa-solid fa-spinner fa-spin text-emerald-500 text-2xl"></i></div>';

                        fetch(CONTEXT_PATH + '/BillServlet?action=getPendingAppointments&patientId=' + patient.patient_id)
                            .then(res => res.json())
                            .then(data => {
                                unbilledAppointments = data;
                                appointmentsContainer.innerHTML = '';
                                if (data.length > 0) {
                                    billingFormSection.classList.remove('hidden');
                                    data.forEach(app => {
                                        const date = formatCustomDate(app.perfomed_at);
                                        const div = document.createElement('label');
                                        div.className = 'flex items-center justify-between p-4 bg-gray-50 dark:bg-gray-700/50 border border-gray-200 dark:border-gray-600 rounded-xl cursor-pointer hover:border-emerald-400 transition-colors';

                                        div.innerHTML =
                                            '<div class="flex items-center gap-4">' +
                                            '<input type="checkbox" value="' + app.appointment_id + '" data-fee="' + app.default_fee + '" class="app-checkbox w-5 h-5 text-emerald-600 rounded focus:ring-emerald-500 bg-white dark:bg-gray-800 border-gray-300 dark:border-gray-600">' +
                                            '<div>' +
                                            '<h4 class="font-bold text-gray-800 dark:text-white">' + app.treatment_name + '</h4>' +
                                            '<p class="text-xs text-gray-500 dark:text-gray-400">Date: ' + date + '</p>' +
                                            '</div>' +
                                            '</div>' +
                                            '<div class="font-semibold text-gray-700 dark:text-gray-300">LKR ' +
                                            app.default_fee.toFixed(2) +
                                            '</div>';

                                        const checkbox = div.querySelector('input');
                                        checkbox.addEventListener('change', (e) => {
                                            if (e.target.checked) {
                                                selectedAppointments.add(app.appointment_id);
                                            } else {
                                                selectedAppointments.delete(app.appointment_id);
                                            }
                                            updateCalculations();
                                        });

                                        appointmentsContainer.appendChild(div);
                                    });
                                } else {
                                    appointmentsContainer.innerHTML = '<div class="p-4 bg-yellow-50 dark:bg-yellow-900/20 text-yellow-700 dark:text-yellow-400 rounded-xl border border-yellow-200 dark:border-yellow-800/50">No pending unbilled appointments for this patient.</div>';
                                    billingFormSection.classList.add('hidden');
                                }
                            });
                    }

                    function updateCalculations() {
                        subTotal = 0;
                        document.querySelectorAll('.app-checkbox:checked').forEach(cb => {
                            subTotal += parseFloat(cb.getAttribute('data-fee'));
                        });

                        taxPercentage = parseFloat(taxSelect.value);
                        taxPay = (subTotal * taxPercentage) / 100;
                        totalAmount = subTotal + taxPay;

                        // Validate input strictly
                        if (paidAmountInput.validity.badInput) {
                            generateBillBtn.disabled = true;
                            paidAmountInput.classList.add('border-red-500', 'focus:ring-red-500');
                            return;
                        }
                        
                        const rawValue = paidAmountInput.value.trim();
                        if (rawValue !== '' && !/^\d+(\.\d{1,2})?$/.test(rawValue)) {
                            generateBillBtn.disabled = true;
                            paidAmountInput.classList.add('border-red-500', 'focus:ring-red-500');
                            return;
                        } else {
                            paidAmountInput.classList.remove('border-red-500', 'focus:ring-red-500');
                        }

                        paidAmount = parseFloat(rawValue) || 0;
                        if (paidAmount > totalAmount) {
                            paidAmount = totalAmount;
                            // Only update value if we are constraining it, don't overwrite user typing otherwise
                            paidAmountInput.value = paidAmount.toFixed(2);
                        }
                        balanceAmount = totalAmount - paidAmount;

                        // Update UI
                        document.getElementById('subTotalDisplay').textContent = 'LKR ' + subTotal.toFixed(2);
                        document.getElementById('taxPayDisplay').textContent = 'LKR ' + taxPay.toFixed(2);
                        document.getElementById('totalAmountDisplay').textContent = 'LKR ' + totalAmount.toFixed(2);
                        document.getElementById('balanceAmountDisplay').value = balanceAmount.toFixed(2);

                        generateBillBtn.disabled = selectedAppointments.size === 0;
                    }

                    taxSelect.addEventListener('change', updateCalculations);
                    paidAmountInput.addEventListener('input', updateCalculations);

                    generateBillBtn.addEventListener('click', () => {
                        if (!currentPatientId || selectedAppointments.size === 0) return;

                        const payload = {
                            patientId: currentPatientId,
                            subTotal: subTotal,
                            taxPay: taxPay,
                            totalAmount: totalAmount,
                            paidAmount: paidAmount,
                            balanceAmount: balanceAmount,
                            appointmentIds: Array.from(selectedAppointments)
                        };

                        generateBillBtn.disabled = true;
                        generateBillBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Processing...';

                        fetch(CONTEXT_PATH + '/BillServlet', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(payload)
                        })
                            .then(res => res.json())
                            .then(data => {
                                if (data.success) {
                                    document.getElementById('downloadBillBtn').href = 'print.jsp?billId=' + data.bill_id;
                                    document.getElementById('successModal').classList.remove('hidden');
                                } else {
                                    alert('Error generating bill: ' + data.error);
                                    generateBillBtn.disabled = false;
                                    generateBillBtn.innerHTML = '<i class="fa-solid fa-file-invoice"></i> Generate Bill';
                                }
                            })
                            .catch(err => {
                                console.error(err);
                                alert('An error occurred.');
                                generateBillBtn.disabled = false;
                                generateBillBtn.innerHTML = '<i class="fa-solid fa-file-invoice"></i> Generate Bill';
                            });
                    });
                </script>
    </body>

    </html>