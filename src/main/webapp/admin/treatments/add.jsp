<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Add Treatment</title>

<style type="text/tailwindcss">
@custom-variant dark (&:where(.dark, .dark *));
</style>

<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>

</head>

<body class="bg-gray-50 dark:bg-gray-900">

<%@ include file="../../utils/theme.jsp" %>
<%@ include file="../includes/admin-header.jsp" %>
<%@ include file="../includes/message.jsp" %>

<main class="p-8">

<div class="

max-w-3xl
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

<div class="flex items-center gap-4 mb-8">

<div class="

w-14
h-14

rounded-2xl

bg-emerald-100
dark:bg-emerald-900/40

flex
items-center
justify-center

text-3xl

">

🦷

</div>

<div>

<h1 class="

text-2xl
font-bold

text-gray-800
dark:text-white

">

Add New Treatment

</h1>

<p class="

text-sm

text-gray-500
dark:text-gray-400

mt-1

">

Create a treatment available in the clinic.

</p>

</div>

</div>

<form

method="post"

action="${pageContext.request.contextPath}/admin/treatments/add"

onsubmit="return validateForm();"

class="space-y-6"

>

<!-- Treatment Name -->

<div>

<label class="block mb-2 text-sm font-medium text-gray-700 dark:text-gray-200">

Treatment Name

</label>

<input

type="text"

name="treatmentName"

id="treatmentName"

maxlength="100"

placeholder="Root Canal Treatment"

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

required>

<p id="nameError" class="text-red-500 text-sm mt-1"></p>

</div>

<!-- Description -->

<div>

<label class="block mb-2 text-sm font-medium text-gray-700 dark:text-gray-200">

Description

</label>

<textarea

name="description"

id="description"

rows="4"

maxlength="500"

placeholder="Enter treatment description..."

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

resize-none

focus:ring-2
focus:ring-emerald-400

"

required></textarea>

<p id="descriptionError" class="text-red-500 text-sm mt-1"></p>

</div>

<!-- Duration -->

<div>

<label class="block mb-2 text-sm font-medium text-gray-700 dark:text-gray-200">

Estimated Duration (Minutes)

</label>

<input

type="number"

name="estimatedDuration"

id="estimatedDuration"

placeholder="60"

min="1"

max="600"

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

required>

<p id="durationError" class="text-red-500 text-sm mt-1"></p>

</div>

<!-- Default Fee -->

<div>

<label class="block mb-2 text-sm font-medium text-gray-700 dark:text-gray-200">

Default Fee (LKR)

</label>

<input

type="number"

name="defaultFee"

id="defaultFee"

placeholder="5000.00"

step="0.01"

min="0.01"

max="99999999.99"

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

required>

<p id="feeError" class="text-red-500 text-sm mt-1"></p>

</div>

<!-- Buttons -->

<div class="flex justify-end gap-4 pt-5">

<a

href="${pageContext.request.contextPath}/admin/treatments"

class="

px-6
py-3

rounded-xl

bg-gray-200
dark:bg-gray-700

text-gray-700
dark:text-gray-200

hover:bg-gray-300
dark:hover:bg-gray-600

transition

"

>

Cancel

</a>

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

hover:shadow-lg

transition

"

>

Add Treatment

</button>

</div>

</form>

</div>

</main>

<script>

function validateForm(){

let valid = true;

const name =
document.getElementById("treatmentName").value.trim();

const description =
document.getElementById("description").value.trim();

const duration =
document.getElementById("estimatedDuration").value.trim();

const fee =
document.getElementById("defaultFee").value.trim();

document.getElementById("nameError").innerHTML="";
document.getElementById("descriptionError").innerHTML="";
document.getElementById("durationError").innerHTML="";
document.getElementById("feeError").innerHTML="";

if(name.length < 3){

document.getElementById("nameError").innerHTML =
"Treatment name must contain at least 3 characters.";

valid=false;

}

if(description.length < 10){

document.getElementById("descriptionError").innerHTML =
"Description must contain at least 10 characters.";

valid=false;

}

if(duration==="" || isNaN(duration)){

document.getElementById("durationError").innerHTML =
"Estimated duration is required.";

valid=false;

}
else if(parseInt(duration)<1 || parseInt(duration)>600){

document.getElementById("durationError").innerHTML =
"Duration must be between 1 and 600 minutes.";

valid=false;

}

const feePattern = /^\d+(\.\d{1,2})?$/;

if(!feePattern.test(fee)){

document.getElementById("feeError").innerHTML =
"Enter a valid amount (up to 2 decimal places).";

valid=false;

}
else if(parseFloat(fee)<=0){

document.getElementById("feeError").innerHTML =
"Fee must be greater than 0.";

valid=false;

}

return valid;

}

</script>

</body>
</html>