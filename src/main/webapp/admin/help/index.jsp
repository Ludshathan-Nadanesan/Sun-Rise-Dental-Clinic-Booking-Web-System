<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sunrise.model.HelpTopic" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Help Guide - Admin Dashboard</title>
    <style type="text/tailwindcss">
        @custom-variant dark (&:where(.dark, .dark *));
    </style>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css" />
    <style>
        .accordion-content {
            transition: max-height 0.4s ease-in-out, opacity 0.4s ease-in-out;
            max-height: 0;
            opacity: 0;
            overflow: hidden;
        }
        .accordion-content.expanded {
            max-height: 10000px; /* High enough to fit images */
            opacity: 1;
        }
    </style>
</head>
<body class="bg-gray-50 dark:bg-gray-900 transition-colors duration-300 min-h-screen">

    <%@ include file="../../utils/theme.jsp" %>
    <%@ include file="../includes/admin-header.jsp" %>

    <main class="p-8 max-w-7xl mx-auto">
        <!-- Header & Search -->
        <div class="flex flex-col md:flex-row items-start md:items-center justify-between gap-6 mb-8">
            <div>
                <h2 class="text-3xl font-bold text-gray-800 dark:text-white">Admin Help Guide</h2>
                <p class="text-gray-500 dark:text-gray-400 mt-2">Find step-by-step instructions on how to use the dashboard.</p>
            </div>
            
            <div class="w-full md:w-96 relative">
                <input type="text" id="searchInput" onkeyup="filterTopics()" placeholder="Search help topics (e.g. 'tax')..."
                    class="w-full pl-12 pr-4 py-3 rounded-xl border border-gray-200 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-800 dark:text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all shadow-sm">
                <i class="fa-solid fa-search absolute left-4 top-1/2 -translate-y-1/2 text-gray-400"></i>
            </div>
        </div>

        <!-- Help Topics Accordion -->
        <div class="space-y-4" id="topicsContainer">
            <% 
                List<HelpTopic> topics = (List<HelpTopic>) request.getAttribute("helpTopics");
                if (topics != null && !topics.isEmpty()) {
                    for (HelpTopic topic : topics) {
            %>
                        <div class="topic-item bg-white dark:bg-gray-800 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 overflow-hidden" data-title="<%= topic.getTitle().toLowerCase() %>">
                            <!-- Accordion Header -->
                            <button onclick="toggleAccordion('<%= topic.getId() %>', this)" class="w-full flex items-center justify-between p-6 hover:bg-gray-50 dark:hover:bg-gray-700/50 transition cursor-pointer text-left focus:outline-none">
                                <div>
                                    <h3 class="text-lg font-bold text-gray-800 dark:text-white flex items-center gap-3">
                                        <i class="fa-solid fa-circle-info text-emerald-500"></i> <%= topic.getTitle() %>
                                    </h3>
                                </div>
                                <i class="fa-solid fa-chevron-down text-gray-400 transform transition-transform duration-300"></i>
                            </button>
                            
                            <!-- Accordion Content -->
                            <div id="<%= topic.getId() %>" class="accordion-content bg-gray-50 dark:bg-gray-900/50 border-t border-gray-100 dark:border-gray-700">
                                <div class="p-6">
                                    <p class="text-gray-600 dark:text-gray-300 mb-6 font-medium text-lg border-l-4 border-emerald-500 pl-4 py-1">
                                        <%= topic.getDescription() %>
                                    </p>
                                    
                                    <div class="space-y-12">
                                        <% 
                                            List<String> imageUrls = topic.getImageUrls();
                                            if (imageUrls != null && !imageUrls.isEmpty()) {
                                                for (int i = 0; i < imageUrls.size(); i++) {
                                                    String imgUrl = imageUrls.get(i);
                                        %>
                                            <div class="flex flex-col items-center bg-white dark:bg-gray-800 p-4 rounded-xl shadow-sm border border-gray-100 dark:border-gray-700">
                                                <div class="w-full flex justify-between items-center mb-4 pb-2 border-b border-gray-100 dark:border-gray-700">
                                                    <span class="px-3 py-1 bg-emerald-100 text-emerald-700 dark:bg-emerald-900/30 dark:text-emerald-400 font-bold rounded-lg text-sm">
                                                        Step <%= (i + 1) %>
                                                    </span>
                                                </div>
                                                <img src="${pageContext.request.contextPath}/<%= imgUrl %>" alt="Step <%= (i + 1) %>" class="w-full h-auto rounded-lg shadow-sm border border-gray-100 dark:border-gray-600">
                                            </div>
                                        <% 
                                                }
                                            } else {
                                        %>
                                        <p class="text-gray-500 dark:text-gray-400 italic">No screenshots found for this topic.</p>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        </div>
            <% 
                    }
                } else { 
            %>
                    <div class="p-8 text-center bg-white dark:bg-gray-800 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700">
                        <i class="fa-solid fa-folder-open text-4xl text-gray-300 dark:text-gray-600 mb-4"></i>
                        <p class="text-gray-500 dark:text-gray-400">No help topics found. Please ensure the "How to use" folder contains valid screenshots.</p>
                    </div>
            <% } %>
        </div>
        
        <div id="noResults" class="hidden p-8 text-center bg-white dark:bg-gray-800 rounded-2xl shadow-sm border border-gray-100 dark:border-gray-700 mt-4">
            <i class="fa-solid fa-search text-4xl text-gray-300 dark:text-gray-600 mb-4"></i>
            <p class="text-gray-500 dark:text-gray-400">No matching help topics found.</p>
        </div>

    </main>

    <script>
        function toggleAccordion(id, btnElement) {
            const content = document.getElementById(id);
            const icon = btnElement.querySelector('.fa-chevron-down');
            
            // Toggle current
            if (content.classList.contains('expanded')) {
                content.classList.remove('expanded');
                icon.style.transform = 'rotate(0deg)';
            } else {
                content.classList.add('expanded');
                icon.style.transform = 'rotate(180deg)';
            }
        }

        function filterTopics() {
            const query = document.getElementById('searchInput').value.toLowerCase();
            const items = document.querySelectorAll('.topic-item');
            let visibleCount = 0;

            items.forEach(item => {
                const title = item.getAttribute('data-title');
                if (title.includes(query)) {
                    item.style.display = 'block';
                    visibleCount++;
                } else {
                    item.style.display = 'none';
                }
            });

            const noResults = document.getElementById('noResults');
            if (visibleCount === 0 && query !== '') {
                noResults.classList.remove('hidden');
            } else {
                noResults.classList.add('hidden');
            }
        }
    </script>
</body>
</html>
