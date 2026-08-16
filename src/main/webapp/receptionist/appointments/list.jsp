<%@page import="com.sunrise.model.Appointment" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ page import="java.util.List" %>
            <%@ page import="java.text.SimpleDateFormat" %>
            <%
    // Date format (e.g., Aug 15, 2026)
    java.text.SimpleDateFormat dateSdf = new java.text.SimpleDateFormat("MMM dd, yyyy");
    
    // Time format (e.g., 11:15 AM)
    java.text.SimpleDateFormat timeSdf = new java.text.SimpleDateFormat("hh:mm a");
%>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Manage Appointments</title>
                    <style type="text/tailwindcss">
                        @custom-variant dark (&:where(.dark, .dark *));
    </style>
                    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
                    <link rel="stylesheet"
                        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css"
                        integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA=="
                        crossorigin="anonymous" referrerpolicy="no-referrer" />
                </head>

                <body class="bg-gray-50 dark:bg-gray-900 transition-colors duration-300">

                    <!-- Header Include -->
                    <%@ include file="../../utils/theme.jsp" %>
                        <%@ include file="../includes/receptionist-header.jsp" %>
                            <%@ include file="../includes/message.jsp" %>

                                <main class="p-8">
                                    <!-- Page Header -->
                                    <div
                                        class="flex flex-col md:flex-row md:items-center md:justify-between gap-5 mb-10">
                                        <div>
                                            <h2 class="text-3xl font-bold text-gray-800 dark:text-white">
                                                Appointments Management
                                            </h2>
                                            <p class="text-gray-500 dark:text-gray-400 mt-2">
                                                Manage patient appointments and bookings.
                                            </p>
                                        </div>

                                        <!-- Add Button -->
                                        <a href="${pageContext.request.contextPath}/receptionist/appointments?action=add"
                                            class="bg-emerald-500 hover:bg-emerald-600 text-white px-6 py-3 rounded-xl shadow-md hover:shadow-lg transition font-medium">
                                            + Book Appointment
                                        </a>
                                    </div>

                                    <!-- Search + Filter + Sort -->
                                    <div
                                        class="bg-white dark:bg-gray-800 rounded-2xl shadow-md border border-gray-100 dark:border-gray-700 p-6 mb-8">
                                        <form method="get"
                                            action="${pageContext.request.contextPath}/receptionist/appointments"
                                            class="flex flex-col lg:flex-row gap-4 items-end">

                                            <!-- Search -->
                                            <div class="flex-1 w-full">
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">Search</label>
                                                <input type="text" name="search" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>"
                                                placeholder="Search ID, Dentist, Patient, Email, or Phone..."
                                                class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300
                                                dark:border-gray-600 text-gray-900 dark:text-white rounded-xl px-4 py-3
                                                focus:ring-2 focus:ring-emerald-400 outline-none">
                                            </div>

                                            <!-- Date Range (Start) -->
                                            <div class="w-full lg:w-48">
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">Start
                                                    Date</label>
                                                <input type="date" name="startDate" value="<%= request.getParameter("startDate") != null ? request.getParameter("startDate") : "" %>"
                                                class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300
                                                dark:border-gray-600 text-gray-900 dark:text-white rounded-xl px-4 py-3
                                                focus:ring-2 focus:ring-emerald-400 outline-none">
                                            </div>

                                            <!-- Date Range (End) -->
                                            <div class="w-full lg:w-48">
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">End
                                                    Date</label>
                                                <input type="date" name="endDate" value="<%= request.getParameter("endDate") != null ? request.getParameter("endDate") : "" %>"
                                                class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300
                                                dark:border-gray-600 text-gray-900 dark:text-white rounded-xl px-4 py-3
                                                focus:ring-2 focus:ring-emerald-400 outline-none">
                                            </div>

                                            <!-- Sort -->
                                            <div class="w-full lg:w-48">
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">Sort
                                                    & Filter By</label>
                                                <select name="sort"
                                                    class="w-full bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white border border-gray-300 dark:border-gray-600 rounded-xl px-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none">
                                                    <% String currentSort=request.getParameter("sort"); %>
                                                        <option value="newest" <%= "newest".equals(currentSort) || currentSort == null ? "selected" : "" %>>Newest First</option>
                                                        <option value="oldest" <%= "oldest".equals(currentSort) ? "selected" : "" %>>Oldest First</option>
                                                        <option value="scheduled" <%= "scheduled".equals(currentSort) ? "selected" : "" %>>Show Scheduled</option>
                                                        <option value="completed" <%= "completed".equals(currentSort) ? "selected" : "" %>>Show Completed</option>
                                                        <option value="cancelled" <%= "cancelled".equals(currentSort) ? "selected" : "" %>>Show Cancelled</option>
                                                </select>
                                            </div>

                                            <!-- Search Button -->
                                            <button type="submit"
                                                class="w-full lg:w-auto px-8 py-3 rounded-xl bg-gray-800 dark:bg-gray-600 hover:bg-gray-900 dark:hover:bg-gray-700 text-white font-medium transition">
                                                Apply
                                            </button>
                                        </form>
                                    </div>

                                    <!-- Appointments Table -->
                                    <div
                                        class="bg-white dark:bg-gray-800 rounded-2xl shadow-md border border-gray-100 dark:border-gray-700 overflow-hidden">
                                        <div class="overflow-y-auto max-h-[600px]">
                                            <table class="w-full text-left">
                                                <thead class="bg-gray-100 dark:bg-gray-700 sticky top-0">
                                                    <tr>
                                                        <th
                                                            class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                                                            ID</th>
                                                        <th
                                                            class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                                                            Patient</th>
                                                        <th
                                                            class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                                                            Dentist</th>
                                                        <th
                                                            class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                                                            Treatment</th>
                                                        <th
                                                            class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                                                            Date & Time</th>
                                                        <th
                                                            class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                                                            Paid?</th>
                                                        <th
                                                            class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                                                            Performed At</th>
                                                        <th
                                                            class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                                                            Status</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% List<Appointment> appointments = (List<Appointment>)
                                                            request.getAttribute("appointments");
                                                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy - hh:mm a");

                                                            if(appointments != null && !appointments.isEmpty()){
                                                            for(Appointment appointment : appointments){
                                                            %>
                                                            <tr
                                                                class="border-b border-gray-100 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-700/50 transition">
                                                                <td class="px-6 py-4 text-gray-700 dark:text-gray-300">#
                                                                    <%= appointment.getAppointmentId() %>
                                                                </td>
                                                                <td class="px-6 py-4 text-gray-700 dark:text-gray-300">
                                                                    <%= appointment.getPatientName() %>
                                                                </td>
                                                                <td class="px-6 py-4 text-gray-700 dark:text-gray-300">
                                                                    <%= appointment.getDentistName() %>
                                                                </td>
                                                                <td class="px-6 py-4 text-gray-700 dark:text-gray-300">
                                                                    <%= appointment.getTreatmentName() != null ? appointment.getTreatmentName() : "N/A" %>
                                                                </td>
                                                                <td class="px-6 py-4 whitespace-nowrap">
																    <div class="flex flex-col gap-1">
																        <!-- Date Display -->
																        <div class="text-sm font-semibold text-gray-800 dark:text-gray-200 flex items-center gap-1.5">
																            <i class="fa-regular fa-calendar-alt text-xs text-emerald-500"></i>
																            <%= appointment.getAppointmentStartDateTime() != null ? 
																                dateSdf.format(appointment.getAppointmentStartDateTime()) : "N/A" %>
																        </div>
																
																        <!-- Time Range Badge -->
																        <div class="inline-flex items-center gap-1 text-xs font-medium text-emerald-700 dark:text-emerald-400 bg-emerald-50 dark:bg-emerald-950/50 border border-emerald-200 dark:border-emerald-800/50 rounded-md px-2 py-0.5 w-fit">
																            <i class="fa-regular fa-clock text-[10px]"></i>
																            <span>
																                <%= appointment.getAppointmentStartDateTime() != null ? 
																                    timeSdf.format(appointment.getAppointmentStartDateTime()) : "" %>
																                -
																                <%= appointment.getAppointmentEndDateTime() != null ? 
																                    timeSdf.format(appointment.getAppointmentEndDateTime()) : "" %>
																            </span>
																        </div>
																    </div>
																</td>
                                                                <td class="px-6 py-4 text-gray-700 dark:text-gray-300">
                                                                    <%= "1".equals(appointment.getIsPaid()) || "yes".equalsIgnoreCase(appointment.getIsPaid()) || "true".equalsIgnoreCase(appointment.getIsPaid()) ? "Yes" : "No" %>
                                                                </td>
                                                                <td class="px-6 py-4 text-gray-700 dark:text-gray-300">
                                                                    <%= appointment.getPerfomedAt() != null ? sdf.format(appointment.getPerfomedAt()) : "Not Performed" %>
                                                                </td>
                                                                <td class="px-6 py-4">
                                                                    <% if
                                                                        ("scheduled".equalsIgnoreCase(appointment.getStatus()))
                                                                        { %>
                                                                        <span
                                                                            class="px-3 py-1 bg-blue-100 text-blue-700 dark:bg-blue-900/30 dark:text-blue-400 rounded-full text-xs font-medium">Scheduled</span>
                                                                        <% } else if
                                                                            ("checked-in".equalsIgnoreCase(appointment.getStatus()))
                                                                            { %>
                                                                            <span
                                                                                class="px-3 py-1 bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400 rounded-full text-xs font-medium">Checked-In</span>
                                                                            <% } else if
                                                                                ("completed".equalsIgnoreCase(appointment.getStatus()))
                                                                                { %>
                                                                                <span
                                                                                    class="px-3 py-1 bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400 rounded-full text-xs font-medium">Completed</span>
                                                                                <% } else if
                                                                                    ("cancelled".equalsIgnoreCase(appointment.getStatus()))
                                                                                    { %>
                                                                                    <span
                                                                                        class="px-3 py-1 bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400 rounded-full text-xs font-medium">Cancelled</span>
                                                                                    <% } else { %>
                                                                                        <span
                                                                                            class="px-3 py-1 bg-gray-100 text-gray-700 dark:bg-gray-700 dark:text-gray-300 rounded-full text-xs font-medium">
                                                                                            <%= appointment.getStatus()
                                                                                                %>
                                                                                        </span>
                                                                                        <% } %>
                                                                </td>
                                                            </tr>
                                                            <% } } else { %>
                                                                <tr>
                                                                    <td colspan="8"
                                                                        class="text-center py-10 text-gray-500 dark:text-gray-400">
                                                                        No appointments found.
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

