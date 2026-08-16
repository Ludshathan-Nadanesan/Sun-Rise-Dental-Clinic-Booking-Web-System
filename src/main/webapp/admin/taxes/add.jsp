<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Tax - Sunrise Dental Clinic</title>

<style type="text/tailwindcss">
@custom-variant dark (&:where(.dark, .dark *));
</style>
<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css" integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA==" crossorigin="anonymous" referrerpolicy="no-referrer" />

</head>

<body class="bg-gray-50 dark:bg-gray-900 transition-colors duration-300">
    
    <!-- Header Include -->
    <%@ include file="../../utils/theme.jsp" %>
    <%@ include file="../includes/admin-header.jsp" %>
    <%@ include file="../includes/message.jsp" %>
    
    <main class="p-8 max-w-4xl mx-auto">
        <!-- Page Header -->
        <div class="mb-10 flex items-center gap-4">
            <a href="${pageContext.request.contextPath}/admin/taxes"
               class="w-10 h-10 rounded-xl bg-white dark:bg-gray-800 flex items-center justify-center text-gray-500 dark:text-gray-400 hover:text-emerald-500 dark:hover:text-emerald-400 shadow-sm border border-gray-100 dark:border-gray-700 transition">
                <i class="fa-solid fa-arrow-left"></i>
            </a>
            
            <div>
                <h2 class="text-3xl font-bold text-gray-800 dark:text-white">Add New Tax</h2>
                <p class="text-gray-500 dark:text-gray-400 mt-2">Fill in the form to add a new tax to the system.</p>
            </div>
        </div>
        
        <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-md border border-gray-100 dark:border-gray-700 p-8">
            <form action="${pageContext.request.contextPath}/admin/taxes/add" method="post" class="space-y-6">
                <!-- Tax Name -->
                <div>
                    <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Tax Name *</label>
                    <input type="text" name="tax_name" required placeholder="e.g., VAT"
                        class="w-full px-4 py-3 rounded-xl bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white focus:ring-2 focus:ring-emerald-400 outline-none transition">
                </div>
                
                <!-- Tax Percentage -->
                <div>
                    <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Tax Percentage (%) *</label>
                    <input type="number" name="tax_percantage" required min="0" max="100" step="0.01" placeholder="e.g., 15.00"
                        class="w-full px-4 py-3 rounded-xl bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white focus:ring-2 focus:ring-emerald-400 outline-none transition">
                </div>
                
                <!-- Action Buttons -->
                <div class="flex items-center justify-end gap-4 pt-4 border-t border-gray-100 dark:border-gray-700">
                    <button type="reset"
                        class="px-6 py-3 rounded-xl bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-200 font-medium hover:bg-gray-300 dark:hover:bg-gray-600 transition">
                        Reset Form
                    </button>
                    
                    <button type="submit"
                        class="px-6 py-3 rounded-xl bg-emerald-500 hover:bg-emerald-600 text-white font-medium shadow-md hover:shadow-lg transition flex items-center gap-2">
                        <i class="fa-solid fa-save"></i>
                        Save Tax
                    </button>
                </div>
            </form>
        </div>
    </main>

</body>
</html>
