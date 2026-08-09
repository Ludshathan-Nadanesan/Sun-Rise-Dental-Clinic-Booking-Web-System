<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="com.sunrise.model.DentistTreatment"%>
<%@ page import="com.sunrise.model.Dentist" %>

<%

List<DentistTreatment> dentistTreatments = 
(List<DentistTreatment>) request.getAttribute("assignedTreatments");

String dname = 
(String) request.getAttribute("dentistName");

int dentistId = (int) request.getAttribute("dentistId");

%>   
    
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Assigned Treatment List - <%= dname %></title>



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

<div class="

max-w-5xl

mx-auto

bg-white
dark:bg-gray-800

rounded-2xl

shadow-lg

border
border-gray-100
dark:border-gray-700

p-8

">

<!-- Header -->

<div class="flex justify-between items-center mb-8">

<div>

<h1 class="

text-3xl

font-bold

text-gray-800
dark:text-white

">

🦷 Assigned Treatments

</h1>

<p class="

text-gray-500
dark:text-gray-400

mt-2

">

Dentist :
<span class="font-semibold"><%= dname %></span>

</p>

</div>



<div class="flex gap-3">

<a

href="${pageContext.request.contextPath}/admin/dentists/assign-treatment?id=<%= dentistId %>"

class="

px-5
py-3

rounded-xl

bg-emerald-500

hover:bg-emerald-600

text-white

font-medium

transition

">

+ Assign Treatment

</a>



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

</div>





<%

if(dentistTreatments != null && !dentistTreatments.isEmpty()){

%>

<div class="overflow-x-auto">

<table class="min-w-full">

<thead>

<tr class="

bg-gray-100
dark:bg-gray-700

text-left

">

<th class="px-5 py-4">Treatment</th>

<th class="px-5 py-4">Default Fee</th>

<th class="px-5 py-4">Commission %</th>

<th class="px-5 py-4">Dentist Earn</th>

<th class="px-5 py-4 text-center">Actions</th>

</tr>

</thead>

<tbody>

<%

for(DentistTreatment dt : dentistTreatments){
	
%>

<tr class="

border-b

border-gray-200
dark:border-gray-700

hover:bg-gray-50
dark:hover:bg-gray-700

">

<td class="px-5 py-4 font-medium dark:text-white">

<%= dt.getTreatmentName() %>

</td>

<td class="px-5 py-4 dark:text-gray-300">

LKR <%= String.format("%.2f", dt.getDefaultFee()) %>

</td>

<td class="px-5 py-4">

<span class="

px-3

py-1

rounded-full

bg-emerald-100

text-emerald-700

text-sm

">

<%= dt.getDentCommissionPerc() %> %

</span>

</td>

<td class="px-5 py-4 font-semibold text-blue-600">

LKR <%= String.format("%.2f", dt.getCommissionAmount()) %>

</td>

<td class="px-5 py-4">

<div class="flex justify-center gap-2">

<a

href="${pageContext.request.contextPath}/admin/dentists/edit-assigned-treatment?id=<%= dt.getId() %>&dId=<%= dentistId %>"

class="

px-3

py-2

rounded-lg

bg-yellow-500

hover:bg-yellow-600

text-white

text-sm

">

Edit

</a>

<a
href="#"

onclick="confirmDelete(<%= dt.getId() %>, <%= dentistId %>, '<%= URLEncoder.encode(dname, "UTF-8") %>' ); return false;"

class="
px-2 py-2

rounded-full

bg-red-600
dark:bg-red-700

text-white

hover:bg-red-500

transition

text-sm

font-medium
">

<i class="fa-solid fa-trash"></i>

</a>

</div>

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

<div class="

text-center

py-16

">

<div class="text-6xl mb-4">

🦷

</div>

<h2 class="

text-xl

font-semibold

text-gray-700
dark:text-white

">

No Treatments Assigned

</h2>

<p class="

text-gray-500
dark:text-gray-400

mt-2

">

This dentist has not been assigned any treatments yet.

</p>

</div>

<%

}

%>

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

Delete Assigned Treatment

</h2>

</div>

<p class="

text-gray-600
dark:text-gray-300

mb-6

">

Are you sure you want to delete this assigned treatment?

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

function confirmDelete(id, dId, dName){

const modal =
document.getElementById("deleteModal");

const deleteBtn =
document.getElementById("deleteBtn");

deleteBtn.href =
"${pageContext.request.contextPath}/admin/dentists/delete-assigned-treatment?id=" 
		+ id + "&dentistId=" + dId + "&dentistName=" + dName;

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

</script>




</body>
</html>