<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reports Dashboard - Sunrise Dental Clinic</title>
    <style type="text/tailwindcss">
        @custom-variant dark (&:where(.dark, .dark *));
    </style>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-gray-50 dark:bg-gray-900 transition-colors duration-300 min-h-screen">

    <%@ include file="../../utils/theme.jsp" %>
    <%@ include file="../includes/admin-header.jsp" %>

    <main class="p-8">
        <!-- Header & Filters -->
        <div class="flex flex-col xl:flex-row xl:items-center justify-between gap-6 mb-8">
            <div>
                <h2 class="text-3xl font-bold text-gray-800 dark:text-white">Reports & Analytics</h2>
                <p class="text-gray-500 dark:text-gray-400 mt-2">View financial and operational performance.</p>
            </div>
            
            <div class="flex flex-col md:flex-row gap-4 items-center bg-white dark:bg-gray-800 p-4 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700">
                <div class="flex items-center gap-3">
                    <label class="text-sm font-medium text-gray-700 dark:text-gray-300">From:</label>
                    <input type="date" id="fromDate" class="bg-gray-50 dark:bg-gray-700 border border-gray-200 dark:border-gray-600 rounded-xl px-3 py-2 text-sm text-gray-800 dark:text-gray-200 focus:ring-2 focus:ring-emerald-500 outline-none">
                </div>
                <div class="flex items-center gap-3">
                    <label class="text-sm font-medium text-gray-700 dark:text-gray-300">To:</label>
                    <input type="date" id="toDate" class="bg-gray-50 dark:bg-gray-700 border border-gray-200 dark:border-gray-600 rounded-xl px-3 py-2 text-sm text-gray-800 dark:text-gray-200 focus:ring-2 focus:ring-emerald-500 outline-none">
                </div>
                <button onclick="loadReportData()" class="bg-emerald-600 hover:bg-emerald-700 text-white px-5 py-2.5 rounded-xl text-sm font-medium transition shadow-md">
                    Filter
                </button>
                <div class="h-8 w-px bg-gray-200 dark:bg-gray-700 hidden md:block mx-2"></div>
                <button onclick="downloadCSV()" class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2.5 rounded-xl text-sm font-medium transition flex items-center gap-2">
                    <i class="fa-solid fa-file-csv"></i> Export CSV
                </button>
                <button onclick="window.print()" class="bg-gray-800 hover:bg-gray-900 dark:bg-gray-700 dark:hover:bg-gray-600 text-white px-4 py-2.5 rounded-xl text-sm font-medium transition flex items-center gap-2">
                    <i class="fa-solid fa-file-pdf"></i> Save PDF
                </button>
            </div>
        </div>

        <!-- KPI Cards -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-sm border border-gray-100 dark:border-gray-700 flex items-center gap-5">
                <div class="w-14 h-14 rounded-full bg-emerald-100 text-emerald-600 dark:bg-emerald-900/30 dark:text-emerald-400 flex items-center justify-center text-2xl">
                    <i class="fa-solid fa-chart-line"></i>
                </div>
                <div>
                    <p class="text-sm font-medium text-gray-500 dark:text-gray-400">Total Net Revenue (LKR)</p>
                    <h3 class="text-2xl font-bold text-gray-800 dark:text-white mt-1" id="kpiRevenue">0.00</h3>
                </div>
            </div>
            <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-sm border border-gray-100 dark:border-gray-700 flex items-center gap-5">
                <div class="w-14 h-14 rounded-full bg-amber-100 text-amber-600 dark:bg-amber-900/30 dark:text-amber-400 flex items-center justify-center text-2xl">
                    <i class="fa-solid fa-clock-rotate-left"></i>
                </div>
                <div>
                    <p class="text-sm font-medium text-gray-500 dark:text-gray-400">Total Outstanding (LKR)</p>
                    <h3 class="text-2xl font-bold text-gray-800 dark:text-white mt-1" id="kpiOutstanding">0.00</h3>
                </div>
            </div>
            <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-sm border border-gray-100 dark:border-gray-700 flex items-center gap-5">
                <div class="w-14 h-14 rounded-full bg-blue-100 text-blue-600 dark:bg-blue-900/30 dark:text-blue-400 flex items-center justify-center text-2xl">
                    <i class="fa-solid fa-building-columns"></i>
                </div>
                <div>
                    <p class="text-sm font-medium text-gray-500 dark:text-gray-400">Total Tax Collected (LKR)</p>
                    <h3 class="text-2xl font-bold text-gray-800 dark:text-white mt-1" id="kpiTax">0.00</h3>
                </div>
            </div>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 gap-8 mb-8">
            <!-- Popular Treatments Chart -->
            <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-sm border border-gray-100 dark:border-gray-700">
                <h3 class="text-lg font-bold text-gray-800 dark:text-white mb-6">Popular Treatments</h3>
                <div class="relative h-64">
                    <canvas id="treatmentsChart"></canvas>
                </div>
            </div>
            
            <!-- Appointment Conversion Chart -->
            <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-sm border border-gray-100 dark:border-gray-700">
                <h3 class="text-lg font-bold text-gray-800 dark:text-white mb-6">Appointment Conversion</h3>
                <div class="relative h-64">
                    <canvas id="conversionChart"></canvas>
                </div>
            </div>

            <!-- Patient Registration Trend -->
            <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-sm border border-gray-100 dark:border-gray-700 xl:col-span-1 lg:col-span-2">
                <h3 class="text-lg font-bold text-gray-800 dark:text-white mb-6">New Patients Trend</h3>
                <div class="relative h-64">
                    <canvas id="trendChart"></canvas>
                </div>
            </div>
        </div>

        <div class="grid grid-cols-1 xl:grid-cols-2 gap-8">
            <!-- Dentist Payouts Table -->
            <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 overflow-hidden">
                <div class="p-6 border-b border-gray-100 dark:border-gray-700">
                    <h3 class="text-lg font-bold text-gray-800 dark:text-white">Dentist Payouts</h3>
                </div>
                <div class="overflow-x-auto max-h-96">
                    <table class="w-full text-left text-sm text-gray-600 dark:text-gray-400">
                        <thead class="bg-gray-50 dark:bg-gray-700/50 sticky top-0 text-gray-700 dark:text-gray-300 font-semibold border-b border-gray-200 dark:border-gray-700">
                            <tr>
                                <th class="px-6 py-4">Dentist Name</th>
                                <th class="px-6 py-4">Treatments Performed</th>
                                <th class="px-6 py-4 text-right">Total Commission (LKR)</th>
                            </tr>
                        </thead>
                        <tbody id="payoutsTableBody" class="divide-y divide-gray-100 dark:divide-gray-700/50">
                            <!-- Populated dynamically -->
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Dentist Productivity Table -->
            <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 overflow-hidden">
                <div class="p-6 border-b border-gray-100 dark:border-gray-700">
                    <h3 class="text-lg font-bold text-gray-800 dark:text-white">Dentist Productivity</h3>
                </div>
                <div class="overflow-x-auto max-h-96">
                    <table class="w-full text-left text-sm text-gray-600 dark:text-gray-400">
                        <thead class="bg-gray-50 dark:bg-gray-700/50 sticky top-0 text-gray-700 dark:text-gray-300 font-semibold border-b border-gray-200 dark:border-gray-700">
                            <tr>
                                <th class="px-6 py-4">Dentist Name</th>
                                <th class="px-6 py-4 text-right">Completed Appointments</th>
                            </tr>
                        </thead>
                        <tbody id="productivityTableBody" class="divide-y divide-gray-100 dark:divide-gray-700/50">
                            <!-- Populated dynamically -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>

    <script>
        const CONTEXT_PATH = '${pageContext.request.contextPath}';
        let charts = {};
        
        // Setup initial default dates (Current month)
        const today = new Date();
        const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
        document.getElementById('fromDate').value = firstDay.toISOString().split('T')[0];
        document.getElementById('toDate').value = today.toISOString().split('T')[0];

        // Format currency
        const formatCurrency = (amount) => {
            return new Intl.NumberFormat('en-LK', { minimumFractionDigits: 2 }).format(amount || 0);
        };

        // Download CSV function
        function downloadCSV() {
            const fromDate = document.getElementById('fromDate').value;
            const toDate = document.getElementById('toDate').value;
            fetch(CONTEXT_PATH + `/admin/api/reports?fromDate=` + fromDate + `&toDate=` + toDate)
                .then(res => res.json())
                .then(data => {
                    let csvContent = "data:text/csv;charset=utf-8,";
                    csvContent += "=== FINANCIAL KPIs ===\n";
                    csvContent += `Total Revenue,\${data.kpis.totalRevenue}\n`;
                    csvContent += `Total Outstanding,\${data.kpis.totalOutstanding}\n`;
                    csvContent += `Total Tax,\${data.kpis.totalTax}\n\n`;
                    
                    csvContent += "=== DENTIST PAYOUTS ===\n";
                    csvContent += "Dentist Name,Treatments Count,Total Commission\n";
                    data.payouts.forEach(p => csvContent += `\${p.dentistName},\${p.treatmentsCount},\${p.totalCommission}\n`);
                    csvContent += "\n";

                    csvContent += "=== DENTIST PRODUCTIVITY ===\n";
                    csvContent += "Dentist Name,Completed Appointments\n";
                    data.productivity.forEach(p => csvContent += `\${p.dentistName},\${p.completedCount}\n`);
                    
                    const encodedUri = encodeURI(csvContent);
                    const link = document.createElement("a");
                    link.setAttribute("href", encodedUri);
                    link.setAttribute("download", `admin_report_\${fromDate}_to_\${toDate}.csv`);
                    document.body.appendChild(link);
                    link.click();
                    link.remove();
                });
        }

        function loadReportData() {
            const fromDate = document.getElementById('fromDate').value;
            const toDate = document.getElementById('toDate').value;
            
            fetch(CONTEXT_PATH + `/admin/api/reports?fromDate=` + fromDate + `&toDate=` + toDate)
                .then(res => res.json())
                .then(data => {
                    // Update KPIs
                    document.getElementById('kpiRevenue').textContent = formatCurrency(data.kpis.totalRevenue);
                    document.getElementById('kpiOutstanding').textContent = formatCurrency(data.kpis.totalOutstanding);
                    document.getElementById('kpiTax').textContent = formatCurrency(data.kpis.totalTax);

                    // Update Payouts Table
                    const payoutsBody = document.getElementById('payoutsTableBody');
                    payoutsBody.innerHTML = '';
                    if(data.payouts.length === 0) payoutsBody.innerHTML = `<tr><td colspan="3" class="px-6 py-4 text-center">No data found</td></tr>`;
                    data.payouts.forEach(p => {
                        payoutsBody.innerHTML += `
                            <tr class="hover:bg-gray-50 dark:hover:bg-gray-800/50">
                                <td class="px-6 py-4 font-medium text-gray-800 dark:text-gray-200">\${p.dentistName}</td>
                                <td class="px-6 py-4">\${p.treatmentsCount}</td>
                                <td class="px-6 py-4 text-right font-semibold text-emerald-600 dark:text-emerald-400">\${formatCurrency(p.totalCommission)}</td>
                            </tr>
                        `;
                    });

                    // Update Productivity Table
                    const prodBody = document.getElementById('productivityTableBody');
                    prodBody.innerHTML = '';
                    if(data.productivity.length === 0) prodBody.innerHTML = `<tr><td colspan="2" class="px-6 py-4 text-center">No data found</td></tr>`;
                    data.productivity.forEach(p => {
                        prodBody.innerHTML += `
                            <tr class="hover:bg-gray-50 dark:hover:bg-gray-800/50">
                                <td class="px-6 py-4 font-medium text-gray-800 dark:text-gray-200">\${p.dentistName}</td>
                                <td class="px-6 py-4 text-right">\${p.completedCount}</td>
                            </tr>
                        `;
                    });

                    // Update Charts
                    updateCharts(data);
                });
        }

        function updateCharts(data) {
            // Chart.js default text color for dark mode support
            const isDark = document.documentElement.classList.contains('dark');
            Chart.defaults.color = isDark ? '#9ca3af' : '#4b5563';

            // Destroy existing charts to redraw
            if(charts.treatments) charts.treatments.destroy();
            if(charts.conversion) charts.conversion.destroy();
            if(charts.trend) charts.trend.destroy();

            // 1. Popular Treatments (Pie)
            charts.treatments = new Chart(document.getElementById('treatmentsChart'), {
                type: 'pie',
                data: {
                    labels: data.popularTreatments.map(t => t.label),
                    datasets: [{
                        data: data.popularTreatments.map(t => t.value),
                        backgroundColor: ['#10b981', '#3b82f6', '#f59e0b', '#8b5cf6', '#ec4899', '#06b6d4'],
                        borderWidth: 0
                    }]
                },
                options: { responsive: true, maintainAspectRatio: false, plugins: { legend: { position: 'right' } } }
            });

            // 2. Conversion (Doughnut)
            charts.conversion = new Chart(document.getElementById('conversionChart'), {
                type: 'doughnut',
                data: {
                    labels: ['Scheduled', 'Completed', 'Cancelled'],
                    datasets: [{
                        data: [data.conversion.scheduled || 0, data.conversion.completed || 0, data.conversion.cancelled || 0],
                        backgroundColor: ['#f59e0b', '#10b981', '#ef4444'],
                        borderWidth: 0
                    }]
                },
                options: { responsive: true, maintainAspectRatio: false, cutout: '70%', plugins: { legend: { position: 'bottom' } } }
            });

            // 3. Trend (Line)
            charts.trend = new Chart(document.getElementById('trendChart'), {
                type: 'line',
                data: {
                    labels: data.registrationTrend.map(t => t.date),
                    datasets: [{
                        label: 'New Patients',
                        data: data.registrationTrend.map(t => t.count),
                        borderColor: '#3b82f6',
                        backgroundColor: 'rgba(59, 130, 246, 0.1)',
                        tension: 0.4,
                        fill: true
                    }]
                },
                options: { 
                    responsive: true, 
                    maintainAspectRatio: false,
                    scales: { y: { beginAtZero: true, ticks: { stepSize: 1 } } }
                }
            });
        }

        // Handle Theme Change explicitly for charts
        const observer = new MutationObserver(() => {
            loadReportData(); // Reload charts with new theme colors
        });
        observer.observe(document.documentElement, { attributes: true, attributeFilter: ['class'] });

        // Load data on page load
        document.addEventListener('DOMContentLoaded', loadReportData);
    </script>
</body>
</html>
