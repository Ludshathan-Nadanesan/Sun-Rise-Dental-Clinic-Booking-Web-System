<%@page import="com.sunrise.model.Patient" %>
<%@page import="com.sunrise.model.Dentist" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Book Appointment</title>
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

    <main class="p-8 max-w-4xl mx-auto">
        <!-- Page Header -->
        <div class="mb-10 flex items-center gap-4">
            <a href="${pageContext.request.contextPath}/receptionist/appointments"
                class="w-10 h-10 rounded-full bg-white dark:bg-gray-800 flex items-center justify-center text-gray-500 dark:text-gray-400 hover:text-emerald-500 dark:hover:text-emerald-400 hover:shadow-md transition">
                <i class="fa-solid fa-arrow-left"></i>
            </a>
            <div>
                <h2 class="text-3xl font-bold text-gray-800 dark:text-white">
                    Book Appointment
                </h2>
                <p class="text-gray-500 dark:text-gray-400 mt-1">
                    Select a patient, dentist, treatment, and date to schedule.
                </p>
            </div>
        </div>

        <!-- Book Form -->
        <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-md border border-gray-100 dark:border-gray-700 p-8">
            <form action="${pageContext.request.contextPath}/receptionist/appointments" method="post" id="bookingForm">
                <input type="hidden" name="action" value="add">
                
                <!-- Hidden input for final combined DateTime -->
                <input type="hidden" name="appointmentStartDateTime" id="appointmentDateTime" required>

                <div class="grid grid-cols-1 gap-6">

                    <!-- Select Patient -->
                    <div>
                        <label class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                            Select Patient <span class="text-red-500">*</span>
                        </label>
                        <div class="relative">
                            <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
                                <i class="fa-regular fa-user"></i>
                            </div>
                            <select name="patientId" required class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl pl-11 pr-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none transition appearance-none">
                                <option value="" disabled selected>-- Select a Patient --</option>
                                <% List<Patient> patients = (List<Patient>) request.getAttribute("patients");
                                    if(patients != null) {
                                        for(Patient p : patients) {
                                %>
                                <option value="<%= p.getPatientId() %>">
                                    <%= p.getFullName() %> (<%= p.getPhone() %>)
                                </option>
                                <% } } %>
                            </select>
                        </div>
                    </div>

                    <!-- Select Dentist -->
                    <div>
                        <label class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                            Select Dentist <span class="text-red-500">*</span>
                        </label>
                        <div class="relative">
                            <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
                                <i class="fa-solid fa-user-md"></i>
                            </div>
                            <select name="dentistId" id="dentistId" required class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl pl-11 pr-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none transition appearance-none">
                                <option value="" disabled selected>-- Select a Dentist --</option>
                                <% List<Dentist> dentists = (List<Dentist>) request.getAttribute("dentists");
                                    if(dentists != null) {
                                        for(Dentist d : dentists) {
                                %>
                                <option value="<%= d.getDentistId() %>">
                                    <%= d.getFullName() %>
                                </option>
                                <% } } %>
                            </select>
                        </div>
                    </div>

                    <!-- Select Treatment -->
                    <div>
                        <label class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                            Select Treatment <span class="text-red-500">*</span>
                        </label>
                        <div class="relative">
                            <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
                                <i class="fa-solid fa-tooth"></i>
                            </div>
                            <select name="treatmentId" id="treatmentId" required disabled class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl pl-11 pr-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none transition appearance-none disabled:opacity-50">
                                <option value="" disabled selected>-- Select Dentist First --</option>
                            </select>
                        </div>
                    </div>

                    <!-- Appointment Date -->
                    <div>
                        <label class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                            Appointment Date <span class="text-red-500">*</span>
                        </label>
                        <div class="relative">
                            <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
                                <i class="fa-regular fa-calendar-alt"></i>
                            </div>
                            <!-- Just a date picker -->
                            <input type="date" id="appointmentDate" required disabled
                                class="w-full bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white rounded-xl pl-11 pr-4 py-3 focus:ring-2 focus:ring-emerald-400 outline-none transition disabled:opacity-50">
                        </div>
                    </div>

                    <!-- 15-Minute Slots Display -->
                    <div>
                        
                        <div class="flex items-center justify-between mb-5">
					        <label class="block text-sm font-semibold text-gray-700 dark:text-gray-300">
					            Available Time Slots
					        </label>
					        <span class="text-xs text-gray-500 dark:text-gray-400 bg-gray-100 dark:bg-gray-700 px-2.5 py-1 rounded-full border border-gray-200 dark:border-gray-600">
					            <i class="fa-regular fa-clock mr-1"></i> Each slot: 15 Mins
					        </span>
					    </div>
                        
                        
                        <div id="slotsContainer" class="grid grid-cols-4 sm:grid-cols-6 md:grid-cols-8 gap-3 mb-5 pb-4 border-b border-gray-400 dark:border-gray-600">
                            <!-- Slots will be rendered here via AJAX -->
                            <div class="col-span-full text-gray-500 dark:text-gray-400 italic text-sm">
                                Please select a dentist, treatment, and date to view available time slots.
                            </div>
                        </div>
                        <div class="flex flex-row justify-center items-center gap-3">
                        	<div class="flex flex-col items-center justify-center gap-2">
                        		<div class="w-4 h-4 border border-emerald-400 bg-emerald-50 dark:bg-emerald-900/30 rounded-sm"></div>
                        		<span class="text-gray-500 dark:text-gray-400 text-xs">Available</span>
                        	</div>
                        	<div class="flex flex-col items-center justify-center gap-2">
                        		<div class="w-4 h-4 border border-yellow-300 bg-yellow-100 dark:bg-yellow-900/30 rounded-sm"></div>
                        		<span class="text-gray-500 dark:text-gray-400 text-xs">Booked</span>
                        	</div>
                        	<div class="flex flex-col items-center justify-center gap-2">
                        		<div class="w-4 h-4 border border-red-300 bg-red-100 dark:bg-red-900/30 rounded-sm"></div>
                        		<span class="text-gray-500 dark:text-gray-400 text-xs">Unavailable</span>
                        	</div>
                        	<div class="flex flex-col items-center justify-center gap-2">
                        		<div class="w-4 h-4 border border-purple-300 bg-purple-100 dark:bg-purple-900/30 rounded-sm"></div>
                        		<span class="text-gray-500 dark:text-gray-400 text-xs">Less Dur</span>
                        	</div>
                        	<div class="flex flex-col items-center justify-center gap-2">
                        		<div class="w-4 h-4 border border-gray-300 bg-gray-100 dark:bg-gray-600 rounded-sm"></div>
                        		<span class="text-gray-500 dark:text-gray-400 text-xs">Past</span>
                        	</div>
                        	
                        </div>
                    </div>

                </div>

                <!-- Submit Button -->
                <div class="mt-8 flex justify-end">
                    <button type="button" id="submitBtn" disabled
                        class="bg-emerald-500 hover:bg-emerald-600 text-white font-semibold px-8 py-3 rounded-xl shadow-md hover:shadow-lg transition flex items-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed">
                        <i class="fa-solid fa-calendar-check"></i>
                        Confirm Booking
                    </button>
                </div>

            </form>
        </div>
    </main>

    <script>
        const contextPath = '${pageContext.request.contextPath}';
        const dentistSelect = document.getElementById('dentistId');
        const treatmentSelect = document.getElementById('treatmentId');
        const dateInput = document.getElementById('appointmentDate');
        const slotsContainer = document.getElementById('slotsContainer');
        
        // Set minimum date to today
        const today = new Date().toISOString().split('T')[0];
        dateInput.min = today;
        const hiddenDateTimeInput = document.getElementById('appointmentDateTime');
        const submitBtn = document.getElementById('submitBtn');

        let selectedTime = null;

        // Fetch treatments when dentist changes
        dentistSelect.addEventListener('change', async function() {
            const dentistId = this.value;
            treatmentSelect.innerHTML = '<option value="" disabled selected>Loading treatments...</option>';
            treatmentSelect.disabled = true;
            dateInput.disabled = true;
            dateInput.value = '';
            slotsContainer.innerHTML = '<div class="col-span-full text-gray-500 dark:text-gray-400 italic text-sm">Please select a treatment and date.</div>';
            disableSubmit();
            
            try {
                const response = await fetch(contextPath + '/api/appointments/booking-data?action=treatments&dentistId=' + dentistId);
                const treatments = await response.json();
                
                treatmentSelect.innerHTML = '<option value="" disabled selected>-- Select a Treatment --</option>';
                if(treatments.length > 0) {
                    treatments.forEach(t => {
                        const option = document.createElement('option');
                        option.value = t.treatmentId;
                        option.textContent = t.treatmentName + ' (Rs. ' + t.defaultFee + ' /= | Dur: ' + t.estDur + 'mins )';
                        treatmentSelect.appendChild(option);
                    });
                    treatmentSelect.disabled = false;
                } else {
                    treatmentSelect.innerHTML = '<option value="" disabled selected>No treatments assigned to this dentist.</option>';
                }
            } catch (error) {
                console.error("Error fetching treatments:", error);
                treatmentSelect.innerHTML = '<option value="" disabled selected>Error loading treatments</option>';
            }
        });

        // Enable date picker when treatment is selected
        treatmentSelect.addEventListener('change', function() {
            if(this.value) {
                dateInput.disabled = false;
                if(dateInput.value) {
                    fetchSlots();
                }
            }
        });

        // Fetch slots when date changes
        dateInput.addEventListener('change', fetchSlots);

        async function fetchSlots() {
            const dentistId = dentistSelect.value;
            const treatmentId = treatmentSelect.value; // 1. Get treatmentId
            const dateStr = dateInput.value;
            
            // Check if all 3 are selected
            if(!dentistId || !treatmentId || !dateStr) return;

            slotsContainer.innerHTML = '<div class="col-span-full text-gray-500 dark:text-gray-400 italic text-sm"><i class="fa-solid fa-spinner fa-spin mr-2"></i>Loading slots...</div>';
            disableSubmit();

            try {
                // 2. Add treatmentId parameter to the URL
                const url = contextPath + '/api/appointments/booking-data?action=slots&dentistId=' + dentistId + '&treatmentId=' + treatmentId + '&date=' + dateStr;
                
                const response = await fetch(url);
                const slots = await response.json();
                
                slotsContainer.innerHTML = '';
                
                if(slots.length > 0) {
                    slots.forEach(slotObj => {
                        const time = slotObj.time;
                        const status = slotObj.status;
                        
                        const btn = document.createElement('button');
                        btn.type = 'button';
                        btn.textContent = time;
                        
                                                if (status === 'available') {
                            btn.className = 'slot-btn py-2 px-3 border border-emerald-400 text-emerald-600 dark:text-emerald-400 rounded-lg hover:bg-emerald-50 dark:hover:bg-emerald-900 transition font-medium text-sm text-center';
                            btn.onclick = function() {
                                selectSlot(btn, time);
                            };
                        } else if (status === 'past') {
                            btn.className = 'py-2 px-3 border border-gray-300 text-gray-400 dark:border-gray-600 dark:text-gray-500 rounded-lg font-medium text-sm text-center cursor-not-allowed bg-transparent opacity-60';
                            btn.disabled = true;
                            btn.title = 'Time has already passed';
                        } else if (status === 'unavailable') {
                            btn.className = 'py-2 px-3 border border-red-300 bg-red-100 text-red-600 dark:border-red-800 dark:bg-red-900/30 dark:text-red-400 rounded-lg font-medium text-sm text-center cursor-not-allowed opacity-75';
                            btn.disabled = true;
                            btn.title = 'Dentist is on break/leave';
                        } else if (status === 'booked') {
                            btn.className = 'py-2 px-3 border border-yellow-300 bg-yellow-100 text-yellow-700 dark:border-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-500 rounded-lg font-medium text-sm text-center cursor-not-allowed opacity-75';
                            btn.disabled = true;
                            btn.title = 'Slot already booked';
                        } else if (status === 'duration_overflow') {
                            btn.className = 'py-2 px-3 border border-purple-300 bg-purple-50 text-purple-600 dark:border-purple-800 dark:bg-purple-900/20 dark:text-purple-400 rounded-lg font-medium text-sm text-center cursor-not-allowed opacity-75';
                            btn.disabled = true;
                            btn.title = 'Not enough time for this treatment';
                        }
                        
                        slotsContainer.appendChild(btn);
                    });
                } else {
                    slotsContainer.innerHTML = '<div class="col-span-full text-red-500 font-medium text-sm">No working hours for the selected date.</div>';
                }
            } catch (error) {
                console.error("Error fetching slots:", error);
                slotsContainer.innerHTML = '<div class="col-span-full text-red-500 font-medium text-sm">Error loading slots. Please try again.</div>';
            }
        }

        function selectSlot(buttonElement, time) {
            // Remove active class from all buttons
            const allBtns = document.querySelectorAll('.slot-btn');
            allBtns.forEach(btn => {
                btn.classList.remove('bg-emerald-500', 'text-white', 'dark:text-white', 'shadow-md');
                btn.classList.add('text-emerald-600', 'dark:text-emerald-400');
            });
            
            // Add active class to selected
            buttonElement.classList.add('bg-emerald-500', 'text-white', 'dark:text-white', 'shadow-md');
            buttonElement.classList.remove('text-emerald-600', 'dark:text-emerald-400');
            
            selectedTime = time;
            
            // Combine date and time for hidden input
            // The format ReceptionistAppointmentsServlet expects is yyyy-MM-dd'T'HH:mm
            const dateStr = dateInput.value;
            hiddenDateTimeInput.value = dateStr + 'T' + selectedTime;
            
            // Enable submit button
            submitBtn.disabled = false;
        }

        function disableSubmit() {
            submitBtn.disabled = true;
            hiddenDateTimeInput.value = '';
            selectedTime = null;
        }

        submitBtn.addEventListener('click', function() {
            if(hiddenDateTimeInput.value) {
                document.getElementById('bookingForm').submit();
            } else {
                alert("Please select an available time slot.");
            }
        });

    </script>
</body>
</html>





