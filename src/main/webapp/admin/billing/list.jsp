<%@ page import="com.sunrise.model.Bill" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Billing Management - Sunrise Dental Clinic</title>
    <style type="text/tailwindcss">
        @custom-variant dark (&:where(.dark, .dark *));
    </style>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css" integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>

<body class="bg-gray-50 dark:bg-gray-900 transition-colors duration-300">

    <%@ include file="../../utils/theme.jsp" %>
    <%@ include file="../includes/admin-header.jsp" %>
    <%@ include file="../includes/message.jsp" %>

    <main class="p-8">
        <!-- Page Header -->
        <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-5 mb-10">
            <div>
                <h2 class="text-3xl font-bold text-gray-800 dark:text-white">Billing Management</h2>
                <p class="text-gray-500 dark:text-gray-400 mt-2">View all clinic bills and payment history.</p>
            </div>
        </div>

        <!-- Search + Sort -->
        <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-md border border-gray-100 dark:border-gray-700 p-6 mb-8">
            <form method="get" action="${pageContext.request.contextPath}/admin/bills" class="flex flex-col md:flex-row gap-4">
                
                <!-- Search -->
                <div class="flex-1">
                    <input type="text" name="search" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>" placeholder="Search by Patient Name or Bill ID..." class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl px-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none">
                </div>

                <!-- Sort -->
                <div>
                    <select name="sort" class="bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white border border-gray-300 dark:border-gray-600 rounded-xl px-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none">
                        <option value="newest" <%= "newest".equals(request.getParameter("sort")) ? "selected" : "" %>>Newest First</option>
                        <option value="oldest" <%= "oldest".equals(request.getParameter("sort")) ? "selected" : "" %>>Oldest First</option>
                    </select>
                </div>

                <!-- Submit Button -->
                <button type="submit" class="bg-emerald-500 hover:bg-emerald-600 text-white px-6 py-3 rounded-xl shadow-md transition font-medium">
                    Filter
                </button>
            </form>
        </div>

        <!-- Table -->
        <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-md border border-gray-100 dark:border-gray-700 overflow-hidden">
            <div class="overflow-x-auto">
                <table class="w-full text-left text-sm text-gray-600 dark:text-gray-400">
                    <thead class="bg-gray-50 dark:bg-gray-700/50 text-gray-700 dark:text-gray-300 font-semibold border-b border-gray-200 dark:border-gray-700">
                        <tr>
                            <th scope="col" class="px-6 py-4 rounded-l-xl">Bill ID</th>
                            <th scope="col" class="px-6 py-4">Patient Name</th>
                            <th scope="col" class="px-6 py-4">Total Amount (LKR)</th>
                            <th scope="col" class="px-6 py-4">Paid Amount (LKR)</th>
                            <th scope="col" class="px-6 py-4">Balance (LKR)</th>
                            <th scope="col" class="px-6 py-4">Status</th>
                            <th scope="col" class="px-6 py-4 rounded-r-xl">Date</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-100 dark:divide-gray-700/50">
                        <% 
                            List<Bill> bills = (List<Bill>) request.getAttribute("bills");
                            if(bills != null && !bills.isEmpty()){
                                for(Bill b : bills) {
                        %>
                        <tr class="hover:bg-gray-50 dark:hover:bg-gray-800/50 transition">
                            <td class="px-6 py-4 font-medium text-gray-800 dark:text-gray-200">
                                #<%= b.getBillId() %>
                            </td>
                            <td class="px-6 py-4">
                                <%= b.getPatientName() != null ? b.getPatientName() : "Unknown Patient" %>
                            </td>
                            <td class="px-6 py-4 font-semibold text-gray-800 dark:text-gray-200">
                                <%= b.getTotalAmmount() %>
                            </td>
                            <td class="px-6 py-4 text-emerald-600 dark:text-emerald-400 font-medium">
                                <%= b.getPaidAmmount() %>
                            </td>
                            <td class="px-6 py-4 text-red-500 font-medium">
                                <%= b.getBalanceAmmount() %>
                            </td>
                            <td class="px-6 py-4">
                                <% if("paid".equalsIgnoreCase(b.getPaymentStatus())){ %>
                                    <span class="px-3 py-1 text-xs font-medium bg-emerald-100 text-emerald-800 dark:bg-emerald-900/30 dark:text-emerald-400 rounded-full border border-emerald-200 dark:border-emerald-800">Paid</span>
                                <% } else { %>
                                    <span class="px-3 py-1 text-xs font-medium bg-amber-100 text-amber-800 dark:bg-amber-900/30 dark:text-amber-400 rounded-full border border-amber-200 dark:border-amber-800">Pending</span>
                                <% } %>
                            </td>
                            <td class="px-6 py-4 text-gray-500 dark:text-gray-400 text-xs">
                                <%= b.getCreatedAt() %>
                            </td>
                        </tr>
                        <% 
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="7" class="text-center py-10 text-gray-500 dark:text-gray-400">
                                No bills found.
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</body>
</html>