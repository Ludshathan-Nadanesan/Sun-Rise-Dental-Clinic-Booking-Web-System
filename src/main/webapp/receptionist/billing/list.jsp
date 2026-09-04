<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Billing History - Sunrise Dental Clinic</title>
        <style type="text/tailwindcss">
            @custom-variant dark (&:where(.dark, .dark *));
    </style>
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css"
            integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA=="
            crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>

    <body class="bg-gray-50 dark:bg-gray-900 transition-colors duration-300 min-h-screen">

        <%@ include file="../../utils/theme.jsp" %>
            <%@ include file="../includes/receptionist-header.jsp" %>

                <main class="p-8">
                    <div class="flex items-center justify-between mb-8">
                        <div>
                            <h2 class="text-3xl font-bold text-gray-800 dark:text-white">Billing Management</h2>
                            <p class="mt-2 text-gray-500 dark:text-gray-400">Search patients and view their billing
                                history.</p>
                        </div>
                        <a href="generate.jsp"
                            class="px-5 py-2.5 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-medium shadow-md transition flex items-center gap-2">
                            <i class="fa-solid fa-plus"></i> Generate New Bill
                        </a>
                    </div>

                    <!-- Search Section -->
                    <div
                        class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-md border border-gray-100 dark:border-gray-700 mb-8">
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 relative">
                            <!-- Patient Search -->
                            <div>
                                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Search
                                    Patient</label>
                                <div class="relative">
                                    <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                                        <i class="fa-solid fa-search text-gray-400"></i>
                                    </div>
                                    <input type="text" id="patientSearch"
                                        class="block w-full pl-11 pr-4 py-3 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-900 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition-all outline-none"
                                        placeholder="Name, phone, or email...">
                                </div>
                                <!-- Absolute positioned results dropdown -->
                                <div id="searchResults"
                                    class="mt-2 absolute z-10 w-full max-w-md bg-white dark:bg-gray-800 shadow-xl rounded-xl border border-gray-100 dark:border-gray-700 max-h-80 overflow-y-auto hidden flex flex-col gap-2 p-2">
                                    <!-- Results dynamically populated -->
                                </div>
                            </div>

                            <!-- Bill ID Search -->
                            <div>
                                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Search by
                                    Bill ID</label>
                                <div class="relative flex items-center">
                                    <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                                        <i class="fa-solid fa-file-invoice text-gray-400"></i>
                                    </div>
                                    <input type="number" id="billIdSearch"
                                        class="block w-full pl-11 pr-24 py-3 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-900 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition-all outline-none"
                                        placeholder="Enter Bill #...">
                                    <button id="searchBillBtn"
                                        class="absolute right-1.5 px-4 py-2 rounded-lg bg-emerald-600 hover:bg-emerald-700 text-white font-medium transition shadow-sm flex items-center gap-2 text-sm">
                                        Search
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Billing History Table -->
                    <div id="billingHistorySection"
                        class="hidden bg-white dark:bg-gray-800 rounded-2xl shadow-md border border-gray-100 dark:border-gray-700 overflow-hidden">
                        <div class="p-6 border-b border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800/50">
                            <h3 class="text-xl font-bold text-gray-800 dark:text-white flex items-center gap-3">
                                <i class="fa-solid fa-file-invoice-dollar text-emerald-500"></i>
                                Billing History for <span id="selectedPatientName"
                                    class="text-emerald-600 dark:text-emerald-400"></span>
                            </h3>
                        </div>
                        <div class="overflow-x-auto">
                            <table class="w-full text-left text-sm text-gray-600 dark:text-gray-400">
                                <thead
                                    class="bg-gray-50 dark:bg-gray-700/50 text-gray-700 dark:text-gray-300 font-semibold border-b border-gray-200 dark:border-gray-700">
                                    <tr>
                                        <th class="px-6 py-4">Bill ID</th>
                                        <th class="px-6 py-4">Date</th>
                                        <th class="px-6 py-4">Sub Total (LKR)</th>
                                        <th class="px-6 py-4">Tax (LKR)</th>
                                        <th class="px-6 py-4">Total Amount (LKR)</th>
                                        <th class="px-6 py-4">Paid (LKR)</th>
                                        <th class="px-6 py-4">Balance (LKR)</th>
                                        <th class="px-6 py-4">Status</th>
                                        <th class="px-6 py-4 text-center">Action</th>
                                    </tr>
                                </thead>
                                <tbody id="billingTableBody" class="divide-y divide-gray-100 dark:divide-gray-700">
                                    <!-- Rows dynamically populated -->
                                </tbody>
                            </table>
                        </div>
                        <div id="noBillsMessage" class="hidden p-8 text-center text-gray-500 dark:text-gray-400">
                            No billing history found for this patient.
                        </div>
                    </div>

                    <!-- Settle Balance Modal -->
                    <div id="settleModal"
                        class="hidden fixed inset-0 bg-gray-900/50 backdrop-blur-sm z-50 flex items-center justify-center">
                        <div class="bg-white dark:bg-gray-800 rounded-2xl p-8 max-w-sm w-full mx-4 shadow-2xl">
                            <h3 class="text-xl font-bold text-gray-800 dark:text-white mb-4">Settle Balance</h3>
                            <p class="text-sm text-gray-500 mb-4">Enter payment amount for Bill #<span
                                    id="settleBillIdDisplay" class="font-bold"></span></p>

                            <div class="mb-4">
                                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">Payment
                                    Amount (LKR)</label>
                                <input type="number" id="settleAmountInput" min="0" step="0.01"
                                    class="block w-full px-3 py-2 rounded-xl border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 outline-none">
                            </div>

                            <div class="flex gap-3 mt-6">
                                <button onclick="closeSettleModal()"
                                    class="w-1/2 px-4 py-2.5 bg-gray-200 hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600 text-gray-800 dark:text-white rounded-xl transition font-bold">Cancel</button>
                                <button id="confirmSettleBtn"
                                    class="w-1/2 px-4 py-2.5 bg-emerald-600 hover:bg-emerald-700 text-white rounded-xl transition font-bold shadow-md">Confirm</button>
                            </div>
                        </div>
                    </div>

                    <!-- Delete Modal -->
                    <div id="deleteModal" class="hidden fixed inset-0 bg-gray-900/50 backdrop-blur-sm z-50 flex items-center justify-center">
                        <div class="bg-white dark:bg-gray-800 rounded-2xl p-8 max-w-sm w-full mx-4 shadow-2xl text-center">
                            <div class="w-16 h-16 bg-red-100 dark:bg-red-900/30 text-red-600 rounded-full flex items-center justify-center text-3xl mx-auto mb-4">
                                <i class="fa-solid fa-trash-can"></i>
                            </div>
                            <h3 class="text-xl font-bold text-gray-800 dark:text-white mb-2">Delete Bill?</h3>
                            <p class="text-sm text-gray-500 dark:text-gray-400 mb-6">Are you sure you want to delete Bill #<span id="deleteBillIdDisplay" class="font-bold"></span>? This action cannot be undone.</p>
                            
                            <div class="flex gap-3">
                                <button onclick="closeDeleteModal()" class="w-1/2 px-4 py-2.5 bg-gray-200 hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600 text-gray-800 dark:text-white rounded-xl transition font-bold">Cancel</button>
                                <button id="confirmDeleteBtn" class="w-1/2 px-4 py-2.5 bg-red-600 hover:bg-red-700 text-white rounded-xl transition font-bold shadow-md">Delete</button>
                            </div>
                        </div>
                    </div>

                    <!-- Error Modal -->
                    <div id="errorModal"
                        class="hidden fixed inset-0 bg-gray-900/50 backdrop-blur-sm z-50 flex items-center justify-center">
                        <div
                            class="bg-white dark:bg-gray-800 rounded-2xl p-8 max-w-sm w-full mx-4 shadow-2xl text-center transform scale-95 transition-all">
                            <div
                                class="w-16 h-16 bg-red-100 dark:bg-red-900/30 text-red-500 rounded-full flex items-center justify-center text-3xl mx-auto mb-4">
                                <i class="fa-solid fa-triangle-exclamation"></i>
                            </div>
                            <h3 class="text-2xl font-bold text-gray-800 dark:text-white mb-2">Oops!</h3>
                            <p id="errorModalMsg" class="text-gray-600 dark:text-gray-400 mb-6">Something went wrong.
                            </p>
                            <button onclick="document.getElementById('errorModal').classList.add('hidden')"
                                class="block w-full bg-red-600 hover:bg-red-700 text-white font-bold py-2.5 rounded-xl transition">
                                Close
                            </button>
                        </div>
                    </div>
                </main>

                <script>
                    const searchInput = document.getElementById('patientSearch');
                    const searchResults = document.getElementById('searchResults');
                    const billingHistorySection = document.getElementById('billingHistorySection');
                    const billingTableBody = document.getElementById('billingTableBody');
                    const selectedPatientName = document.getElementById('selectedPatientName');
                    const noBillsMessage = document.getElementById('noBillsMessage');

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

                    let searchTimeout;
                    let currentPatientId = null;

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
                                            const card = document.createElement('div');
                                            card.className = 'cursor-pointer bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-4 hover:border-emerald-500 dark:hover:border-emerald-400 hover:shadow-md transition-all';
                                            card.innerHTML =
                                                '<h4 class="font-bold text-gray-800 dark:text-white">' + patient.full_name + '</h4>' +
                                                '<p class="text-sm text-gray-500 dark:text-gray-400 mt-1"><i class="fa-solid fa-phone text-xs mr-2"></i>' + patient.phone + '</p>' +
                                                '<p class="text-sm text-gray-500 dark:text-gray-400 mt-1"><i class="fa-solid fa-envelope text-xs mr-2"></i>' + (patient.email || 'N/A') + '</p>';
                                            card.onclick = () => selectPatient(patient.patient_id, patient.full_name);
                                            searchResults.appendChild(card);
                                        });
                                    } else {
                                        searchResults.classList.remove('hidden');
                                        searchResults.innerHTML = '<div class="col-span-full p-4 text-center text-gray-500">No patients found.</div>';
                                    }
                                })
                                .catch(err => console.error("Error searching patients:", err));
                        }, 300);
                    });

                    function selectPatient(patientId, patientName) {
                        currentPatientId = patientId;
                        searchInput.value = '';
                        searchResults.classList.add('hidden');
                        selectedPatientName.textContent = patientName;
                        billingHistorySection.classList.remove('hidden');

                        fetch(CONTEXT_PATH + '/BillServlet?action=getBills&patientId=' + patientId)
                            .then(res => res.json())
                            .then(data => {
                                billingTableBody.innerHTML = '';
                                if (data.length > 0) {
                                    noBillsMessage.classList.add('hidden');
                                    data.forEach(bill => {
                                        const tr = document.createElement('tr');
                                        tr.className = 'hover:bg-gray-50 dark:hover:bg-gray-700/30 transition-colors';

                                        const statusBadge = bill.payment_status === 'paid'
                                            ? '<span class="px-2.5 py-1 rounded-full text-xs font-medium bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400">Paid</span>'
                                            : '<span class="px-2.5 py-1 rounded-full text-xs font-medium bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400">Pending</span>';

                                        const date = formatCustomDate(bill.created_at);

                                        tr.innerHTML =
                                            '<td class="px-6 py-4 font-medium text-gray-900 dark:text-white">#' + bill.bill_id + '</td>' +
                                            '<td class="px-6 py-4">' + date + '</td>' +
                                            '<td class="px-6 py-4">' + bill.sub_total.toFixed(2) + '</td>' +
                                            '<td class="px-6 py-4">' + bill.tax_pay.toFixed(2) + '</td>' +
                                            '<td class="px-6 py-4 font-bold text-gray-800 dark:text-gray-200">' + bill.total_ammount.toFixed(2) + '</td>' +
                                            '<td class="px-6 py-4 text-green-600 dark:text-green-400">' + bill.paid_ammount.toFixed(2) + '</td>' +
                                            '<td class="px-6 py-4 text-red-500 dark:text-red-400">' + bill.balance_ammount.toFixed(2) + '</td>' +
                                            '<td class="px-6 py-4">' + statusBadge + '</td>' +
                                            '<td class="px-6 py-4 text-center">' +
                                            (bill.payment_status.toLowerCase() === 'pending'
                                                ? '<button onclick="openSettleModal(' + bill.bill_id + ', ' + bill.balance_ammount + ')" class="mr-3 text-emerald-600 hover:text-emerald-800 dark:text-emerald-400 dark:hover:text-emerald-300" title="Settle Balance"><i class="fa-solid fa-hand-holding-dollar"></i></button>'
                                                : '') +
                                            '<a href="print.jsp?billId=' + bill.bill_id + '" target="_blank" class="mr-3 text-gray-500 hover:text-gray-800 dark:text-gray-400 dark:hover:text-gray-200" title="Print Bill">' +
                                            '<i class="fa-solid fa-print"></i>' +
                                            '</a>' +
                                            '<button onclick="openDeleteModal(' + bill.bill_id + ')" class="text-red-500 hover:text-red-700 dark:text-red-400 dark:hover:text-red-300" title="Delete Bill">' +
                                            '<i class="fa-solid fa-trash-can"></i>' +
                                            '</button>' +
                                            '</td>';
                                        billingTableBody.appendChild(tr);
                                    });
                                } else {
                                    noBillsMessage.classList.add('hidden');
                                }
                            })
                            .catch(err => console.error("Error fetching bills:", err));
                    }

                    let currentSettleBillId = null;

                    function openSettleModal(billId, maxAmount) {
                        currentSettleBillId = billId;
                        document.getElementById('settleBillIdDisplay').textContent = billId;
                        const input = document.getElementById('settleAmountInput');
                        input.value = maxAmount.toFixed(2);
                        input.max = maxAmount;

                        input.classList.remove('border-red-500', 'focus:ring-red-500');

                        document.getElementById('settleModal').classList.remove('hidden');
                    }

                    function closeSettleModal() {
                        document.getElementById('settleModal').classList.add('hidden');
                        currentSettleBillId = null;
                    }

                    document.getElementById('confirmSettleBtn').addEventListener('click', () => {
                        const input = document.getElementById('settleAmountInput');

                        if (input.validity.badInput || input.value.trim() === '' || parseFloat(input.value) <= 0) {
                            input.classList.add('border-red-500', 'focus:ring-red-500');
                            return;
                        }

                        let amount = parseFloat(input.value);
                        const maxAmount = parseFloat(input.max);
                        if (amount > maxAmount) amount = maxAmount; // cap it gracefully

                        const payload = {
                            action: 'settleBalance',
                            billId: currentSettleBillId,
                            paymentAmount: amount
                        };

                        const btn = document.getElementById('confirmSettleBtn');
                        btn.disabled = true;
                        btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin mr-2"></i>Processing...';

                        fetch(CONTEXT_PATH + '/BillServlet', {
                            method: 'POST',
                            headers: { 'Content-Type': 'application/json' },
                            body: JSON.stringify(payload)
                        })
                            .then(res => res.json())
                            .then(data => {
                                btn.disabled = false;
                                btn.textContent = 'Confirm';
                                if (data.success) {
                                    closeSettleModal();
                                    if (currentPatientId) {
                                        selectPatient(currentPatientId, selectedPatientName.textContent);
                                    }
                                } else {
                                    document.getElementById('errorModalMsg').textContent = 'Error settling balance: ' + (data.error || 'Unknown error');
                                    document.getElementById('errorModal').classList.remove('hidden');
                                }
                            })
                            .catch(err => {
                                console.error(err);
                                btn.disabled = false;
                                btn.textContent = 'Confirm';
                                document.getElementById('errorModalMsg').textContent = 'An error occurred while settling the balance.';
                                document.getElementById('errorModal').classList.remove('hidden');
                            });
                    });

                    // Delete Bill Logic
                    let currentDeleteBillId = null;

                    function openDeleteModal(billId) {
                        currentDeleteBillId = billId;
                        document.getElementById('deleteBillIdDisplay').textContent = billId;
                        document.getElementById('deleteModal').classList.remove('hidden');
                    }

                    function closeDeleteModal() {
                        document.getElementById('deleteModal').classList.add('hidden');
                        currentDeleteBillId = null;
                    }

                    document.getElementById('confirmDeleteBtn').addEventListener('click', () => {
                        const btn = document.getElementById('confirmDeleteBtn');
                        btn.disabled = true;
                        btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin mr-2"></i>Deleting...';
                        
                        fetch(CONTEXT_PATH + '/BillServlet', {
                            method: 'POST',
                            headers: { 'Content-Type': 'application/json' },
                            body: JSON.stringify({ action: 'deleteBill', billId: currentDeleteBillId })
                        })
                        .then(res => res.json())
                        .then(data => {
                            btn.disabled = false;
                            btn.textContent = 'Delete';
                            if (data.success) {
                                closeDeleteModal();
                                if (currentPatientId) {
                                    selectPatient(currentPatientId, selectedPatientName.textContent);
                                } else {
                                    billingHistorySection.classList.add('hidden');
                                }
                            } else {
                                document.getElementById('errorModalMsg').textContent = 'Error deleting bill: ' + (data.error || 'Unknown error');
                                document.getElementById('errorModal').classList.remove('hidden');
                            }
                        })
                        .catch(err => {
                            console.error(err);
                            btn.disabled = false;
                            btn.textContent = 'Delete';
                            document.getElementById('errorModalMsg').textContent = 'An error occurred while deleting the bill.';
                            document.getElementById('errorModal').classList.remove('hidden');
                        });
                    });

                    // Search By Bill ID Logic
                    document.getElementById('searchBillBtn').addEventListener('click', () => {
                        const billId = document.getElementById('billIdSearch').value.trim();
                        if (!billId) return;

                        const btn = document.getElementById('searchBillBtn');
                        btn.disabled = true;
                        btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i>';

                        fetch(CONTEXT_PATH + '/BillServlet?action=getBillDetails&billId=' + billId)
                            .then(res => {
                                if (!res.ok) throw new Error('Not found');
                                return res.json();
                            })
                            .then(bill => {
                                btn.disabled = false;
                                btn.innerHTML = '<i class="fa-solid fa-search"></i> Search';

                                billingHistorySection.classList.remove('hidden');
                                noBillsMessage.classList.add('hidden');
                                selectedPatientName.textContent = bill.patient_name;
                                billingTableBody.innerHTML = '';

                                const tr = document.createElement('tr');
                                tr.className = 'bg-emerald-50 dark:bg-emerald-900/20 hover:bg-emerald-100 dark:hover:bg-emerald-900/40 transition-colors';

                                const statusBadge = bill.payment_status.toLowerCase() === 'paid'
                                    ? '<span class="px-2.5 py-1 rounded-full text-xs font-medium bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400">Paid</span>'
                                    : '<span class="px-2.5 py-1 rounded-full text-xs font-medium bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400">Pending</span>';

                                const date = formatCustomDate(bill.created_at);

                                tr.innerHTML =
                                    '<td class="px-6 py-4 font-medium text-gray-900 dark:text-white">#' + bill.bill_id + '</td>' +
                                    '<td class="px-6 py-4">' + date + '</td>' +
                                    '<td class="px-6 py-4">' + bill.sub_total.toFixed(2) + '</td>' +
                                    '<td class="px-6 py-4">' + bill.tax_pay.toFixed(2) + '</td>' +
                                    '<td class="px-6 py-4 font-bold text-gray-800 dark:text-gray-200">' + bill.total_amount.toFixed(2) + '</td>' +
                                    '<td class="px-6 py-4 text-green-600 dark:text-green-400">' + bill.paid_amount.toFixed(2) + '</td>' +
                                    '<td class="px-6 py-4 text-red-500 dark:text-red-400">' + bill.balance_amount.toFixed(2) + '</td>' +
                                    '<td class="px-6 py-4">' + statusBadge + '</td>' +
                                    '<td class="px-6 py-4 text-center">' +
                                    (bill.payment_status.toLowerCase() === 'pending'
                                        ? '<button onclick="openSettleModal(' + bill.bill_id + ', ' + bill.balance_amount + ')" class="mr-3 text-emerald-600 hover:text-emerald-800 dark:text-emerald-400 dark:hover:text-emerald-300 cursor-pointer" title="Settle Balance"><i class="fa-solid fa-hand-holding-dollar"></i></button>'
                                        : '') +
                                    '<a href="print.jsp?billId=' + bill.bill_id + '" target="_blank" class="mr-3 cursor-pointer text-gray-500 hover:text-gray-800 dark:text-gray-400 dark:hover:text-gray-200" title="Print Bill">' +
                                        '<i class="fa-solid fa-print"></i>' +
                                    '</a>' +
                                    '<button onclick="openDeleteModal(' + bill.bill_id + ')" class="text-red-500 hover:text-red-700 dark:text-red-400 dark:hover:text-red-300 cursor-pointer" title="Delete Bill">' +
                                        '<i class="fa-solid fa-trash-can"></i>' +
                                    '</button>' +
                                    '</td>';
                                billingTableBody.appendChild(tr);

                                // Set currentPatientId so settling the balance refreshes this patient's full list
                                currentPatientId = bill.patient_id;
                            })
                            .catch(err => {
                                btn.disabled = false;
                                btn.innerHTML = 'Search';
                                document.getElementById('errorModalMsg').textContent = 'Bill ID #' + billId + ' was not found.';
                                document.getElementById('errorModal').classList.remove('hidden');
                            });
                    });
                </script>
    </body>

    </html>