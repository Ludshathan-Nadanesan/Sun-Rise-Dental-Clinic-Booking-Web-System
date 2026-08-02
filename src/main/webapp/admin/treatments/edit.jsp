<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.sunrise.model.Treatments" %>

<%

Treatments treatment =
(Treatments)request.getAttribute("treatment");

%>


<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Edit Treatment</title>


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

💉

</div>


<div>

<h1 class="
text-2xl
font-bold

text-gray-800
dark:text-white
">

Edit Treatment

</h1>


<p class="
text-sm
text-gray-500
dark:text-gray-400
mt-1
">

Update treatment details.

</p>

</div>


</div>





<form

method="post"

action="${pageContext.request.contextPath}/admin/treatments/edit"

onsubmit="return validateForm();"

class="space-y-6">


<input

type="hidden"

name="treatmentId"

value="<%= treatment.getTreatmentID() %>"

>



<!-- Treatment Name -->


<div>

<label class="
block
mb-2

text-sm
font-medium

text-gray-700
dark:text-gray-200
">

Treatment Name

</label>


<input

type="text"

id="treatmentName"

name="treatmentName"

value="<%= treatment.getTreatmentName() %>"

maxlength="100"

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


<p id="nameError"
class="text-red-500 text-sm mt-1"></p>


</div>





<!-- Description -->


<div>


<label class="
block
mb-2

text-sm
font-medium

text-gray-700
dark:text-gray-200
">

Description

</label>



<textarea

id="description"

name="description"

rows="4"

maxlength="500"

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

><%= treatment.getDescription() %></textarea>


<p id="descriptionError"
class="text-red-500 text-sm mt-1"></p>


</div>





<!-- Duration -->


<div>


<label class="
block
mb-2

text-sm
font-medium

text-gray-700
dark:text-gray-200
">

Estimated Duration (Minutes)

</label>


<input

type="number"

id="duration"

name="estimatedDuration"

value="<%= treatment.getEstimatedDuration() %>"

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


<p id="durationError"
class="text-red-500 text-sm mt-1"></p>


</div>







<!-- Fee -->


<div>


<label class="
block
mb-2

text-sm
font-medium

text-gray-700
dark:text-gray-200
">

Default Fee (LKR)

</label>


<input

type="number"

id="fee"

name="defaultFee"

value="<%= treatment.getDefaultFee() %>"

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



<p id="feeError"
class="text-red-500 text-sm mt-1"></p>



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

transition

">

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

">

Save Changes

</button>



</div>



</form>


</div>


</main>






<script>


function validateForm(){


let valid=true;


let name =
document.getElementById("treatmentName").value.trim();


let description =
document.getElementById("description").value.trim();


let duration =
document.getElementById("duration").value;


let fee =
document.getElementById("fee").value;



document.getElementById("nameError").innerHTML="";
document.getElementById("descriptionError").innerHTML="";
document.getElementById("durationError").innerHTML="";
document.getElementById("feeError").innerHTML="";



if(name.length < 3){

document.getElementById("nameError").innerHTML =
"Treatment name must contain minimum 3 characters.";

valid=false;

}



if(description.length < 10){

document.getElementById("descriptionError").innerHTML =
"Description must contain minimum 10 characters.";

valid=false;

}




if(duration < 1 || duration > 600){

document.getElementById("durationError").innerHTML =
"Duration must be between 1 and 600 minutes.";

valid=false;

}



if(!/^\d+(\.\d{1,2})?$/.test(fee)
|| fee <=0){

document.getElementById("feeError").innerHTML =
"Enter a valid fee.";

valid=false;

}



return valid;


}


</script>



</body>

</html>