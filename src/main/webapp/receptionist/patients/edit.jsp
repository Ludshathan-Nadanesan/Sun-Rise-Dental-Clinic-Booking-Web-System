<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ page import="com.sunrise.model.Patient" %>


        <!DOCTYPE html>
        <html>

        <head>

            <meta charset="UTF-8">

            <title>Edit Patient</title>

            <style type="text/tailwindcss">

                @custom-variant dark (&:where(.dark, .dark *));

</style>

            <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>

            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css"
                integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA=="
                crossorigin="anonymous" referrerpolicy="no-referrer" />

        </head>


        <body class="
bg-gray-50
dark:bg-gray-900
transition-colors duration-300
">


            <!-- Header Include -->

            <%@ include file="../../utils/theme.jsp" %>
                <%@ include file="../includes/receptionist-header.jsp" %>
                    <%@ include file="../includes/message.jsp" %>


                        <main class="p-8 max-w-4xl mx-auto">


                            <!-- Page Header -->

                            <div class="mb-10 flex items-center gap-4">

                                <a href="${pageContext.request.contextPath}/receptionist/patients" class="
           w-10 h-10 
           rounded-full 
           bg-white dark:bg-gray-800 
           flex items-center justify-center 
           text-gray-500 dark:text-gray-400 
           hover:text-emerald-500 dark:hover:text-emerald-400 
           hover:shadow-md 
           transition
           ">
                                    <i class="fa-solid fa-arrow-left"></i>
                                </a>

                                <div>
                                    <h2 class="text-3xl font-bold text-gray-800 dark:text-white">
                                        Edit Patient
                                    </h2>
                                    <p class="text-gray-500 dark:text-gray-400 mt-1">
                                        Update the patient's information below.
                                    </p>
                                </div>

                            </div>


                            <% Patient patient=(Patient) request.getAttribute("patient"); if (patient !=null) { %>

                                <!-- Edit Form -->
                                <div class="
        bg-white dark:bg-gray-800 
        rounded-2xl 
        shadow-md 
        border border-gray-100 dark:border-gray-700 
        p-8
    ">

                                    <form action="${pageContext.request.contextPath}/receptionist/patients"
                                        method="post">

                                        <input type="hidden" name="action" value="edit">
                                        <input type="hidden" name="patientId" value="<%= patient.getPatientId() %>">

                                        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">

                                            <!-- Full Name -->
                                            <div class="col-span-1 md:col-span-2">
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                                                    Full Name <span class="text-red-500">*</span>
                                                </label>
                                                <div class="relative">
                                                    <div
                                                        class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
                                                        <i class="fa-regular fa-user"></i>
                                                    </div>
                                                    <input type="text" name="fullName" required
                                                        value="<%= patient.getFullName() %>"
                                                        class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl pl-11 pr-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none transition">
                                                </div>
                                            </div>

                                            <!-- Email -->
                                            <div>
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                                                    Email Address <span class="text-red-500">*</span>
                                                </label>
                                                <div class="relative">
                                                    <div
                                                        class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
                                                        <i class="fa-regular fa-envelope"></i>
                                                    </div>
                                                    <input type="email" name="email" required
                                                        value="<%= patient.getEmail() %>"
                                                        class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl pl-11 pr-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none transition">
                                                </div>
                                            </div>

                                            <!-- Phone -->
                                            <div>
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                                                    Phone Number <span class="text-red-500">*</span>
                                                </label>
                                                <div class="relative">
                                                    <div
                                                        class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
                                                        <i class="fa-solid fa-phone"></i>
                                                    </div>
                                                    <input type="tel" name="phone" required
                                                        pattern="^[1-9]\d{8}$"
                                                        title="Phone number must be exactly 9 digits and cannot start with 0"
                                                        value="<%= patient.getPhone() %>"
                                                        class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl pl-11 pr-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none transition">
                                                </div>
                                            </div>

                                            <!-- Gender -->
                                            <div>
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                                                    Gender <span class="text-red-500">*</span>
                                                </label>
                                                <div class="relative">
                                                    <div
                                                        class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
                                                        <i class="fa-solid fa-venus-mars"></i>
                                                    </div>
                                                    <select name="gender" required
                                                        class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl pl-11 pr-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none transition appearance-none">
                                                        <option value="Male" <%="Male" .equals(patient.getGender())
                                                            ? "selected" : "" %>>Male</option>
                                                        <option value="Female" <%="Female" .equals(patient.getGender())
                                                            ? "selected" : "" %>>Female</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- Date of Birth -->
                                            <div>
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                                                    Date of Birth <span class="text-red-500">*</span>
                                                </label>
                                                <div class="relative">
                                                    <div
                                                        class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
                                                        <i class="fa-regular fa-calendar-alt"></i>
                                                    </div>
                                                    <input type="date" name="dob" required
                                                        max="<%= java.time.LocalDate.now() %>"
                                                        value="<%= patient.getDob() %>"
                                                        class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl pl-11 pr-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none transition">
                                                </div>
                                            </div>

                                            <!-- Address -->
                                            <div class="col-span-1 md:col-span-2">
                                                <label
                                                    class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                                                    Address <span class="text-red-500">*</span>
                                                </label>
                                                <textarea name="address" required rows="3"
                                                    class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl p-4 focus:ring-2 focus:ring-emerald-400 outline-none transition"><%= patient.getAddress() %></textarea>
                                            </div>

                                        </div>

                                        <!-- Submit Button -->
                                        <div class="mt-8 flex justify-end">
                                            <button type="submit" class="
                    bg-emerald-500 hover:bg-emerald-600 
                    text-white font-semibold 
                    px-8 py-3 rounded-xl 
                    shadow-md hover:shadow-lg transition
                    flex items-center gap-2
                    ">
                                                <i class="fa-regular fa-floppy-disk"></i>
                                                Update Patient
                                            </button>
                                        </div>

                                    </form>

                                </div>

                                <% } else { %>
                                    <div
                                        class="bg-red-50 dark:bg-red-900/30 text-red-700 dark:text-red-400 p-6 rounded-2xl border border-red-200 dark:border-red-800 text-center font-medium">
                                        <i class="fa-solid fa-triangle-exclamation text-2xl mb-2 block"></i>
                                        Patient data not found. Please go back and try again.
                                    </div>
                                    <% } %>

                        </main>

        </body>

        </html>