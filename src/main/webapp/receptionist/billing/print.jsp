<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Print Bill - Sunrise Dental Clinic</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @media print {
            body { -webkit-print-color-adjust: exact; print-color-adjust: exact; }
            .no-print { display: none !important; }
            @page { margin: 0.5cm; }
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css" integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body class="bg-gray-100 min-h-screen p-8 text-gray-800">
    <div class="max-w-3xl mx-auto bg-white p-10 shadow-lg rounded-sm relative">
        <!-- Print Controls -->
        <div class="absolute top-4 right-4 no-print flex gap-2">
            <button onclick="window.close()" class="px-4 py-2 bg-gray-200 text-gray-700 rounded hover:bg-gray-300 transition text-sm font-medium">Close</button>
            <button onclick="window.print()" class="px-4 py-2 bg-emerald-600 text-white rounded hover:bg-emerald-700 transition text-sm font-medium flex items-center gap-2">
                <i class="fa-solid fa-print"></i> Print Bill
            </button>
        </div>

        <div id="billContent" class="hidden">
            <!-- Header Section -->
            <div class="flex justify-between items-start border-b-2 border-emerald-500 pb-6 mb-6">
                <div>
                    <h1 class="text-4xl font-bold text-emerald-600 tracking-tight flex items-center gap-3">
                        <img src="<%= request.getContextPath() %>/logo/logo-light.png" alt="Sunrise Dental" class="h-10 w-auto"> 
                        Sunrise Dental
                    </h1>
                    <p class="text-gray-500 mt-2 font-medium">Professional Dental Care</p>
                    <div class="text-sm text-gray-500 mt-1">
                        <p>123 Clinic Avenue, Cityville</p>
                        <p>Tel: +94 11 234 5678 | Email: care@sunrisedental.com</p>
                    </div>
                </div>
                <div class="text-right">
                    <h2 class="text-3xl font-black text-gray-200 uppercase tracking-widest mb-2">INVOICE</h2>
                    <p class="font-semibold text-gray-700 text-lg">Bill #<span id="billIdDisplay"></span></p>
                    <p class="text-sm text-gray-500 mt-1">Date: <span id="billDateDisplay"></span></p>
                    <div class="mt-2 inline-block px-3 py-1 rounded-full text-xs font-bold uppercase tracking-wider" id="paymentStatusBadge">
                        <!-- Status Badge -->
                    </div>
                </div>
            </div>

            <!-- Patient Details -->
            <div class="bg-gray-50 p-4 rounded-lg border border-gray-100 mb-8 flex flex-col">
                <h3 class="text-xs uppercase font-bold text-gray-400 mb-2">Bill To:</h3>
                <p class="text-xl font-bold text-gray-800" id="patientNameDisplay"></p>
                <div class="mt-2 space-y-1">
                    <p class="text-sm text-gray-600"><i class="fa-solid fa-phone w-5 text-gray-400 ml-2"></i><span id="patientPhoneDisplay"></span></p>
                    <p class="text-sm text-gray-600"><i class="fa-solid fa-envelope w-5 text-gray-400 ml-2"></i><span id="patientEmailDisplay"></span></p>
                    <p class="text-sm text-gray-600 mt-1 flex items-start"><i class="fa-solid fa-location-dot w-5 mt-0.5 text-gray-400 ml-2"></i><span id="patientAddressDisplay"></span></p>
                </div>
            </div>

            <!-- Bill Items Table -->
            <table class="w-full text-left mb-8">
                <thead>
                    <tr class="border-b-2 border-gray-200 text-gray-600">
                        <th class="py-3 px-2 font-bold uppercase text-xs">Description / Treatment</th>
                        <th class="py-3 px-2 font-bold uppercase text-xs">Date Performed</th>
                        <th class="py-3 px-2 font-bold uppercase text-xs text-right">Amount (LKR)</th>
                    </tr>
                </thead>
                <tbody id="billItemsBody" class="text-sm">
                    <!-- Items injected here -->
                </tbody>
            </table>

            <!-- Totals Section -->
            <div class="flex justify-end">
                <div class="w-1/2 space-y-3">
                    <div class="flex justify-between text-gray-600 border-b border-gray-100 pb-2">
                        <span>Sub Total:</span>
                        <span class="font-medium" id="subTotalDisplay"></span>
                    </div>
                    <div class="flex justify-between text-gray-600 border-b border-gray-100 pb-2">
                        <span>Tax:</span>
                        <span class="font-medium" id="taxDisplay"></span>
                    </div>
                    <div class="flex justify-between text-lg font-bold text-gray-800 border-b-2 border-emerald-500 pb-2">
                        <span>Total Amount:</span>
                        <span id="totalDisplay"></span>
                    </div>
                    <div class="flex justify-between text-green-600 font-medium">
                        <span>Amount Paid:</span>
                        <span id="paidDisplay"></span>
                    </div>
                    <div class="flex justify-between text-red-500 font-bold bg-red-50 p-2 rounded">
                        <span>Balance Due:</span>
                        <span id="balanceDisplay"></span>
                    </div>
                </div>
            </div>

            <!-- Footer -->
            <div class="mt-16 pt-8 border-t border-gray-200 text-center text-sm text-gray-500">
                <p>Thank you for choosing Sunrise Dental Clinic.</p>
                <p class="mt-1 text-xs">Wishing you a healthy and bright smile!</p>
            </div>
        </div>

        <div id="loading" class="text-center py-20 text-gray-500">
            <i class="fa-solid fa-circle-notch fa-spin text-4xl text-emerald-500 mb-4"></i>
            <p>Loading bill details...</p>
        </div>
        
        <div id="errorMsg" class="hidden text-center py-20 text-red-500">
            <i class="fa-solid fa-triangle-exclamation text-4xl mb-4"></i>
            <p class="font-bold text-lg">Bill not found or an error occurred.</p>
        </div>
    </div>

    <script>
        const urlParams = new URLSearchParams(window.location.search);
        const billId = urlParams.get('billId');
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

        if(billId) {
            fetch(CONTEXT_PATH + '/BillServlet?action=getBillDetails&billId=' + billId)
                .then(res => {
                    if(!res.ok) throw new Error('Not found');
                    return res.json();
                })
                .then(data => {
                    document.getElementById('loading').classList.add('hidden');
                    document.getElementById('billContent').classList.remove('hidden');

                    document.getElementById('billIdDisplay').textContent = data.bill_id;
                    document.getElementById('billDateDisplay').textContent = formatCustomDate(data.created_at);
                    
                    document.getElementById('patientNameDisplay').textContent = data.patient_name;
                    document.getElementById('patientPhoneDisplay').textContent = data.patient_phone || 'N/A';
                    document.getElementById('patientEmailDisplay').textContent = data.patient_email || 'N/A';
                    document.getElementById('patientAddressDisplay').textContent = data.patient_address || 'N/A';

                    const statusBadge = document.getElementById('paymentStatusBadge');
                    if(data.payment_status === 'paid' || data.payment_status === 'Paid') {
                        statusBadge.className = 'mt-2 inline-block px-3 py-1 rounded-full text-xs font-bold uppercase tracking-wider bg-green-100 text-green-700';
                        statusBadge.textContent = 'PAID';
                    } else {
                        statusBadge.className = 'mt-2 inline-block px-3 py-1 rounded-full text-xs font-bold uppercase tracking-wider bg-red-100 text-red-700';
                        statusBadge.textContent = 'PENDING';
                    }

                    document.getElementById('subTotalDisplay').textContent = 'LKR ' + data.sub_total.toFixed(2);
                    document.getElementById('taxDisplay').textContent = 'LKR ' + data.tax_pay.toFixed(2);
                    document.getElementById('totalDisplay').textContent = 'LKR ' + data.total_amount.toFixed(2);
                    document.getElementById('paidDisplay').textContent = 'LKR ' + data.paid_amount.toFixed(2);
                    document.getElementById('balanceDisplay').textContent = 'LKR ' + data.balance_amount.toFixed(2);

                    const tbody = document.getElementById('billItemsBody');
                    data.items.forEach(item => {
                        const tr = document.createElement('tr');
                        tr.className = 'border-b border-gray-100';
                        tr.innerHTML = 
                            '<td class="py-4 px-2 font-medium text-gray-800">' + item.treatment_name + '</td>' +
                            '<td class="py-4 px-2 text-gray-500">' + formatCustomDate(item.perfomed_at) + '</td>' +
                            '<td class="py-4 px-2 text-right font-medium text-gray-700">LKR ' + item.charged_amount.toFixed(2) + '</td>';
                        tbody.appendChild(tr);
                    });
                    
                    // Auto print
                    setTimeout(() => window.print(), 500);
                })
                .catch(err => {
                    document.getElementById('loading').classList.add('hidden');
                    document.getElementById('errorMsg').classList.remove('hidden');
                });
        } else {
            document.getElementById('loading').classList.add('hidden');
            document.getElementById('errorMsg').classList.remove('hidden');
        }
    </script>
</body>
</html>
