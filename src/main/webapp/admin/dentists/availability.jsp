<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>

<%@ page import="com.sunrise.model.Dentist" %>

<%@ page import="com.sunrise.model.DentistAvailability" %>



<%

Dentist dentist =
(Dentist) request.getAttribute("dentist");


List<DentistAvailability> availabilityList =
(List<DentistAvailability>)
request.getAttribute("availabilityList");


SimpleDateFormat timeFormat =
        new SimpleDateFormat("HH:mm");
%>



<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>
Availability - <%= dentist.getFullName() %>
</title>


<style type="text/tailwindcss">

@custom-variant dark (&:where(.dark, .dark *));

</style>


<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css" integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA==" crossorigin="anonymous" referrerpolicy="no-referrer" />


</head>


<body class="bg-gray-50 dark:bg-gray-900">


<%@ include file="../../../utils/theme.jsp" %>

<%@ include file="../includes/admin-header.jsp" %>

<%@ include file="../includes/message.jsp" %>


<main class="p-8">


<div class="max-w-7xl mx-auto">


<!-- ========================================= -->
<!-- HEADER -->
<!-- ========================================= -->


<div class="flex justify-between items-center mb-8">


<div>

<h1 class="
text-3xl
font-bold
text-gray-800
dark:text-white
">

🗓️ Dentist Availability

</h1>


<p class="
text-gray-500
dark:text-gray-400
mt-2
">

This is the weekly availability schedule for 

<span class="font-semibold">

<%= dentist.getFullName() %>'s

</span>

working hours by day.

</p>

</div>



<a

href="${pageContext.request.contextPath}/admin/dentists"

class="
px-5
py-3
rounded-xl
bg-gray-200
dark:bg-gray-700
text-gray-700
dark:text-gray-200
hover:bg-gray-300
dark:hover:bg-gray-600
transition
">

← Back

</a>


</div>




<!-- ========================================= -->
<!-- DENTIST INFORMATION -->
<!-- ========================================= -->


<div class="
bg-white
dark:bg-gray-800
rounded-2xl
shadow-lg
border
border-gray-100
dark:border-gray-700
p-6
mb-8
">


<div class="flex items-center gap-4">


<div class="
w-14
h-14
rounded-2xl
bg-emerald-100
dark:bg-emerald-900/40
flex
items-center
justify-center
text-2xl
">

👨‍⚕️

</div>


<div>

<h2 class="
text-xl
font-bold
text-gray-800
dark:text-white
">

<%= dentist.getFullName() %>

</h2>


<p class="
text-sm
text-gray-500
dark:text-gray-400
">

<%= dentist.getEmail() %>

</p>


<p class="
text-sm
text-gray-500
dark:text-gray-400
">

+94 <%= dentist.getPhone() %>

</p>

</div>


<span class="

ml-auto

px-3
py-1

rounded-full

text-xs
font-medium

<%= dentist.getStatus().equals("active")
?
"bg-green-100 text-green-700"
:
"bg-red-100 text-red-700"
%>

">

<%= dentist.getStatus() %>

</span>


</div>


</div>




<!-- ========================================= -->
<!-- WEEKLY TABLE -->
<!-- ========================================= -->


<div class="
bg-white
dark:bg-gray-800
rounded-2xl
shadow-lg
border
border-gray-100
dark:border-gray-700
p-6
">

<div class="overflow-x-auto">


<table class="w-full min-w-[1100px]">


<thead>


<tr class="
bg-gray-100
dark:bg-gray-700
">

<th class="
px-5
py-4
text-center
text-sm
font-semibold
text-gray-700
dark:text-gray-200
">

Monday

</th>


<th class="
px-5
py-4
text-center
text-sm
font-semibold
text-gray-700
dark:text-gray-200
">

Tuesday

</th>


<th class="
px-5
py-4
text-center
text-sm
font-semibold
text-gray-700
dark:text-gray-200
">

Wednesday

</th>


<th class="
px-5
py-4
text-center
text-sm
font-semibold
text-gray-700
dark:text-gray-200
">

Thursday

</th>


<th class="
px-5
py-4
text-center
text-sm
font-semibold
text-gray-700
dark:text-gray-200
">

Friday

</th>


<th class="
px-5
py-4
text-center
text-sm
font-semibold
text-gray-700
dark:text-gray-200
">

Saturday

</th>


<th class="
px-5
py-4
text-center
text-sm
font-semibold
text-gray-700
dark:text-gray-200
">

Sunday

</th>


</tr>


</thead>



<tbody>


<tr class="
border-b
border-gray-200
dark:border-gray-700
">


<!-- Dentist -->

<%

String[] days = {

"Monday",
"Tuesday",
"Wednesday",
"Thursday",
"Friday",
"Saturday",
"Sunday"

};


for(String day : days){

DentistAvailability found = null;


if(availabilityList != null){

for(DentistAvailability a :
availabilityList){

if(a.getDayOfWeek()
.equalsIgnoreCase(day)){

found = a;

break;

}

}

}

%>


<td class="px-4 py-6 text-center">


<%

if(found != null){

%>


<div class="
inline-flex
flex-row
items-center
gap-3
bg-emerald-50
dark:bg-emerald-900/20
border
border-emerald-200
dark:border-emerald-800
rounded-xl
px-4
py-3
">


<span class="
text-sm
font-semibold
text-emerald-700
dark:text-emerald-400
">

<%= timeFormat.format(found.getStartTime()) %>

-

<%= timeFormat.format(found.getEndTime()) %>

</span>



<a

href="#"

onclick="confirmDelete(<%= dentist.getDentistId() %>, <%= found.getAvailabilityId() %>); return false;"

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

<i class="fa-solid fa-xmark"></i>

</a>

</div>


<%

}
else{

%>


<span class="
inline-block
px-3
py-2
rounded-lg
bg-gray-100
dark:bg-gray-700
text-gray-400
dark:text-gray-500
text-xs
">

Not Set

</span>


<%

}

%>


</td>


<%

}

%>


</tr>


</tbody>


</table>


</div>


</div>




<!-- ========================================= -->
<!-- ADD AVAILABILITY -->
<!-- ========================================= -->


<div class="
bg-white
dark:bg-gray-800
rounded-2xl
shadow-lg
border
border-gray-100
dark:border-gray-700
p-6
mt-8
">


<h2 class="
text-xl
font-bold
text-gray-800
dark:text-white
mb-6
">

+ Add Availability

</h2>



<form

method="post"

action="${pageContext.request.contextPath}/admin/dentists/availability"

onsubmit="return validateAvailability()"
>


<input

type="hidden"

name="dentistId"

value="<%= dentist.getDentistId() %>"
>



<div class="grid grid-cols-1 md:grid-cols-3 gap-5">


<!-- Day -->


<div>


<label class="
block
mb-2
text-sm
font-medium
text-gray-700
dark:text-gray-200
">

Day

</label>


<select

name="dayOfWeek"

id="dayOfWeek"

class="
w-full
px-4
py-3
rounded-xl
bg-gray-50
dark:bg-gray-700
border
border-gray-300
dark:border-gray-600
text-gray-900
dark:text-white
outline-none
focus:ring-2
focus:ring-emerald-400
"

required
>


<option value="">

Select Day

</option>


<option value="Monday">Monday</option>

<option value="Tuesday">Tuesday</option>

<option value="Wednesday">Wednesday</option>

<option value="Thursday">Thursday</option>

<option value="Friday">Friday</option>

<option value="Saturday">Saturday</option>

<option value="Sunday">Sunday</option>


</select>


<p id="dayError"
class="text-red-500 text-sm mt-1">
</p>


</div>



<!-- Start -->


<div>


<label class="
block
mb-2
text-sm
font-medium
text-gray-700
dark:text-gray-200
">

Start Time

</label>


<input

type="time"

name="startTime"

id="startTime"

class="
w-full
px-4
py-3
rounded-xl
bg-gray-50
dark:bg-gray-700
border
border-gray-300
dark:border-gray-600
text-gray-900
dark:text-white
"

required
>


<p id="startError"
class="text-red-500 text-sm mt-1">
</p>


</div>



<!-- End -->


<div>


<label class="
block
mb-2
text-sm
font-medium
text-gray-700
dark:text-gray-200
">

End Time

</label>


<input

type="time"

name="endTime"

id="endTime"

class="
w-full
px-4
py-3
rounded-xl
bg-gray-50
dark:bg-gray-700
border
border-gray-300
dark:border-gray-600
text-gray-900
dark:text-white
"

required
>


<p id="endError"
class="text-red-500 text-sm mt-1">
</p>


</div>


</div>



<div class="flex justify-end mt-6">


<button

type="submit"

class="
px-6
py-3
rounded-xl
bg-emerald-500
hover:bg-emerald-600
text-white
font-medium
shadow-md
transition
">

Add Availability

</button>


</div>


</form>


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

Delete Availability

</h2>

</div>

<p class="

text-gray-600
dark:text-gray-300

mb-6

">

Are you sure you want to remove this availability?

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

function confirmDelete(did, aid){

const modal =
document.getElementById("deleteModal");

const deleteBtn =
document.getElementById("deleteBtn");


deleteBtn.href = 
	"${pageContext.request.contextPath}/admin/dentists/availability?" + 
	"dentistId=" + did + 
	"&action=delete&availabilityId=" + aid;

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



function validateAvailability(){


let valid = true;


const day =
document.getElementById("dayOfWeek").value;


const start =
document.getElementById("startTime").value;


const end =
document.getElementById("endTime").value;


document.getElementById("dayError").innerHTML = "";

document.getElementById("startError").innerHTML = "";

document.getElementById("endError").innerHTML = "";



if(day === ""){

document.getElementById("dayError")
.innerHTML =
"Please select a day.";

valid = false;

}



if(start === ""){

document.getElementById("startError")
.innerHTML =
"Please select start time.";

valid = false;

}



if(end === ""){

document.getElementById("endError")
.innerHTML =
"Please select end time.";

valid = false;

}



if(start !== "" &&
end !== "" &&
start >= end){

document.getElementById("endError")
.innerHTML =
"End time must be later than start time.";

valid = false;

}



return valid;

}

</script>


</body>

</html>