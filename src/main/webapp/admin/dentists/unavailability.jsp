<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="com.sunrise.model.DentistUnavailability"%>

<%

Integer dentistIdObj =
        (Integer) request.getAttribute("dentistId");

int dentistId =
        dentistIdObj != null ? dentistIdObj : 0;


List<DentistUnavailability> list =
        (List<DentistUnavailability>)
        request.getAttribute("unavailabilityList");


String dentistName =
        (String) request.getAttribute("dentistName");


DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

%>


<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Dentist Unavailability</title>


<style type="text/tailwindcss">

@custom-variant dark (&:where(.dark, .dark *));

</style>


<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>



<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css" integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA==" crossorigin="anonymous" referrerpolicy="no-referrer" />



</head>


<body class="bg-gray-50 dark:bg-gray-900 transition-colors">


<%@ include file="../../../utils/theme.jsp" %>

<%@ include file="../includes/admin-header.jsp" %>

<%@ include file="../includes/message.jsp" %>


<main class="p-8">


<div class="max-w-6xl mx-auto">


<!-- Header -->

<div class="flex justify-between items-center mb-8">

<div>

<h1 class="text-3xl font-bold text-gray-800 dark:text-white">

⏳ Dentist Unavailability

</h1>

<p class="text-gray-500 dark:text-gray-400 mt-2">

Manage unavailable dates and times for 

<span class="font-semibold">
    <%= dentistName %>.
</span>

</p>

</div>


<a

href="${pageContext.request.contextPath}/admin/dentists"

class="px-5 py-3 rounded-xl bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-200 hover:bg-gray-300 dark:hover:bg-gray-600 transition">

← Back

</a>

</div>



<!-- Add Unavailability -->

<div class="bg-white dark:bg-gray-800 rounded-2xl shadow-lg border border-gray-100 dark:border-gray-700 p-8 mb-8">


<div class="flex items-center gap-4 mb-6">

<div class="w-12 h-12 rounded-xl bg-red-100 dark:bg-red-900/30 flex items-center justify-center text-2xl">

🚫

</div>


<div>

<h2 class="text-xl font-bold text-gray-800 dark:text-white">

Add Unavailability

</h2>

<p class="text-sm text-gray-500 dark:text-gray-400">

Set a period when the dentist will not be available.

</p>

</div>

</div>



<form method="post"

action="${pageContext.request.contextPath}/admin/dentists/unavailability"

onsubmit="return validateForm()">


<input type="hidden"

name="dentistId"

value="<%= dentistId %>">


<input type="hidden"

name="action"

value="add">



<div class="grid grid-cols-1 md:grid-cols-2 gap-6">


<!-- Start -->

<div>

<label class="block mb-2 text-sm font-medium text-gray-700 dark:text-gray-200">

Start Date & Time

</label>


<input

type="datetime-local"

id="startDatetime"

name="startDatetime"

class="w-full px-4 py-3 rounded-xl bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white outline-none focus:ring-2 focus:ring-red-400"

required>


<p id="startError"

class="text-red-500 text-sm mt-1">

</p>

</div>



<!-- End -->

<div>

<label class="block mb-2 text-sm font-medium text-gray-700 dark:text-gray-200">

End Date & Time

</label>


<input

type="datetime-local"

id="endDatetime"

name="endDatetime"

class="w-full px-4 py-3 rounded-xl bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white outline-none focus:ring-2 focus:ring-red-400"

required>


<p id="endError"

class="text-red-500 text-sm mt-1">

</p>

</div>

</div>



<!-- Reason -->

<div class="mt-6">

<label class="block mb-2 text-sm font-medium text-gray-700 dark:text-gray-200">

Reason

</label>


<textarea

id="reason"

name="reason"

rows="3"

maxlength="500"

placeholder="Example: Personal leave, medical appointment, vacation..."

class="w-full px-4 py-3 rounded-xl bg-gray-50 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white outline-none focus:ring-2 focus:ring-red-400"

required></textarea>


<div class="flex justify-between">

<p id="reasonError"

class="text-red-500 text-sm mt-1">

</p>

<span id="counter"

class="text-xs text-gray-400 mt-1">

0 / 500

</span>

</div>

</div>



<div class="flex justify-end mt-6">


<button

type="submit"

class="px-6 py-3 rounded-xl bg-red-500 hover:bg-red-600 text-white font-medium shadow-md transition">

Add Unavailability

</button>


</div>


</form>


</div>



<!-- Existing Records -->

<div class="bg-white dark:bg-gray-800 rounded-2xl shadow-lg border border-gray-100 dark:border-gray-700 p-8">


<h2 class="text-xl font-bold text-gray-800 dark:text-white mb-6">

Existing Unavailability

</h2>


<%

if(list != null && !list.isEmpty()){

%>


<div class="overflow-x-auto">


<table class="min-w-full">


<thead>

<tr class="bg-gray-100 dark:bg-gray-700 text-left">

<th class="px-5 py-4 text-sm font-semibold dark:text-gray-200">

Start

</th>

<th class="px-5 py-4 text-sm font-semibold dark:text-gray-200">

End

</th>

<th class="px-5 py-4 text-sm font-semibold dark:text-gray-200">

Reason

</th>

<th class="px-5 py-4 text-center text-sm font-semibold dark:text-gray-200">

Actions

</th>

</tr>

</thead>


<tbody>


<%

for(DentistUnavailability du : list){

%>


<tr class="border-b border-gray-200 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-700">


<td class="px-5 py-4 dark:text-gray-200">

<%= du.getStartDatetime().format(formatter) %>

</td>


<td class="px-5 py-4 dark:text-gray-200">

<%= du.getEndDatetime().format(formatter) %>

</td>


<td class="px-5 py-4 text-gray-600 dark:text-gray-300">

<%= du.getReason() %>

</td>


<td class="px-5 py-4 text-center">


<a

href="#"

onclick="confirmDelete(<%= du.getUnavailabilityId() %>, <%= du.getDentistId() %>); return false;"

class="
px-1 py-1

rounded-full

bg-red-50
text-red-700

dark:bg-red-900/30
dark:text-red-400

border border-red-200
dark:border-red-800

hover:bg-red-100
dark:hover:bg-red-900/50

transition duration-200

text-xs

font-medium
">

<i class="fa-solid fa-trash"></i>

</a>

</td>

</tr>


<%

}

%>


</tbody>

</table>

</div>


<%

}else{

%>


<div class="text-center py-12">


<div class="text-5xl mb-4">

📅

</div>


<h3 class="text-lg font-semibold text-gray-700 dark:text-white">

No Unavailability Records

</h3>


<p class="text-sm text-gray-500 dark:text-gray-400 mt-2">

This dentist currently has no unavailable periods.

</p>


</div>


<%

}

%>


</div>


</div>


</main>





<!-- Delete Confirmation Modal -->

<div

id="deleteModal"

class="

fixed
inset-0

bg-black/50

hidden

items-center
justify-center

z-50

">

<div

class="

bg-white
dark:bg-gray-800

rounded-2xl

shadow-xl

w-full
max-w-md

p-6

mx-4

">

<div class="flex items-center gap-3 mb-4">

<div class="

w-12
h-12

rounded-full

bg-red-100
dark:bg-red-900/30

flex
items-center
justify-center

text-red-600

text-xl

">

<i class="fa-solid fa-trash"></i>

</div>

<h2 class="

text-xl

font-bold

text-gray-800
dark:text-white

">

Delete Unavailability

</h2>

</div>

<p class="

text-gray-600
dark:text-gray-300

mb-6

">

Are you sure you want to remove this unavailability?

This action cannot be undone.

</p>

<div class="flex justify-end gap-3">

<button

type="button"

onclick="closeDeleteModal()"

class="

px-5
py-2

rounded-xl

bg-gray-200
dark:bg-gray-700

text-gray-700
dark:text-gray-200

hover:bg-gray-300
dark:hover:bg-gray-600

transition

">

Cancel

</button>

<a

id="deleteBtn"

href="#"

class="

px-5
py-2

rounded-xl

bg-red-600

text-white

hover:bg-red-700

transition

">

Yes, Delete

</a>

</div>

</div>

</div>





<script>

function confirmDelete(duid, did){

const modal =
document.getElementById("deleteModal");

const deleteBtn =
document.getElementById("deleteBtn");


deleteBtn.href = 
	"${pageContext.request.contextPath}/admin/dentists/unavailability?" + 
	"action=delete&" + 
	"id=" + duid + "&" +
	"dentistId=" + did;

	
modal.classList.remove("hidden");
modal.classList.add("flex");

}

function closeDeleteModal(){

const modal =
document.getElementById("deleteModal");

modal.classList.remove("flex");
modal.classList.add("hidden");

}

// Close modal when clicking outside

document.getElementById("deleteModal").addEventListener("click", function(e){

if(e.target === this){

closeDeleteModal();

}

});




const reason =
document.getElementById("reason");

const counter =
document.getElementById("counter");


reason.addEventListener("input", function(){

counter.innerText =
this.value.length + " / 500";

});



function validateForm(){


let valid = true;


const start =
document.getElementById("startDatetime").value;

const end =
document.getElementById("endDatetime").value;

const reasonValue =
document.getElementById("reason").value.trim();


document.getElementById("startError").innerHTML = "";

document.getElementById("endError").innerHTML = "";

document.getElementById("reasonError").innerHTML = "";



if(start === ""){

document.getElementById("startError").innerHTML =
"Start date and time is required.";

valid = false;

}


if(end === ""){

document.getElementById("endError").innerHTML =
"End date and time is required.";

valid = false;

}



if(start !== "" && end !== ""){


const startDate =
new Date(start);

const endDate =
new Date(end);


if(endDate <= startDate){

document.getElementById("endError").innerHTML =
"End date and time must be after start date and time.";

valid = false;

}

}



if(reasonValue.length < 3){

document.getElementById("reasonError").innerHTML =
"Reason must contain at least 3 characters.";

valid = false;

}


if(reasonValue.length > 500){

document.getElementById("reasonError").innerHTML =
"Reason cannot exceed 500 characters.";

valid = false;

}


return valid;

}

</script>


</body>

</html>