<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>

<%@ page import="com.sunrise.model.Dentist" %>

<%@ page import="com.sunrise.model.Treatments" %>




<%

Dentist dentist =

(Dentist)
request.getAttribute("dentist");



List<Treatments> availableTreatments =

(List<Treatments>)
request.getAttribute("availableTreatments");


%>





<!DOCTYPE html>

<html>


<head>

<meta charset="UTF-8">

<title>Assign Treatment</title>



<style type="text/tailwindcss">

@custom-variant dark (&:where(.dark, .dark *));

</style>


<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>


</head>






<body class="bg-gray-50 dark:bg-gray-900">





<%@ include file="../../../utils/theme.jsp" %>

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

dark:text-white

">

Assign Treatment

</h1>


<p class="text-sm text-gray-500">

Assign a treatment to dentist

</p>


</div>


</div>








<!-- Dentist Info -->


<div class="

bg-gray-50

dark:bg-gray-700

rounded-xl

p-5

mb-8

">



<h2 class="

font-semibold

dark:text-white

mb-3

">

👨‍⚕️ Dentist Information

</h2>




<div class="grid grid-cols-2 gap-4">



<div>

<p class="text-xs text-gray-500">

Name

</p>


<p class="font-medium dark:text-white">

<%=dentist.getFullName()%>

</p>

</div>





<div>

<p class="text-xs text-gray-500">

Status

</p>


<p class="font-medium dark:text-white">

<%=dentist.getStatus()%>

</p>

</div>






<div>

<p class="text-xs text-gray-500">

Email

</p>


<p class="font-medium dark:text-white">

<%=dentist.getEmail()%>

</p>

</div>






<div>

<p class="text-xs text-gray-500">

Phone

</p>


<p class="font-medium dark:text-white">

+94 <%=dentist.getPhone()%>

</p>

</div>



</div>


</div>









<form method="post"

action="${pageContext.request.contextPath}/admin/dentists/assign-treatment"

onsubmit="return validateForm()">





<input type="hidden"

name="dentistId"

value="<%=dentist.getDentistId()%>">


<input type="hidden"

name="dentistName"

value="<%=dentist.getFullName()%>">







<!-- Treatment -->


<div class="mb-6">



<label class="

block

mb-2

font-medium

dark:text-white

">

Select Treatment

</label>





<select

name="treatmentId"

id="treatment"

onchange="calculateCommission()"

class="

w-full

px-4

py-3

rounded-xl

bg-gray-50

dark:bg-gray-700

border

dark:border-gray-600

dark:text-white

"

required>



<option value="">

-- Select Treatment --

</option>



<%

for(Treatments t : availableTreatments){

%>


<option

value="<%=t.getTreatmentID()%>"

data-fee="<%=t.getDefaultFee()%>"

>


<%=t.getTreatmentName()%>

-
LKR <%=t.getDefaultFee()%>


</option>


<%

}

%>


</select>


<p id="treatmentError"

class="text-red-500 text-sm mt-1">

</p>


</div>









<!-- Commission -->


<div class="mb-6">


<label class="

block

mb-2

font-medium

dark:text-white

">

Commission Percentage (%)

</label>


<input

type="number"

step="0.01"

min="0"

max="100"

name="commission"

id="commission"

placeholder="20.00"

oninput="validateCommission(); calculateCommission();"

class="

w-full

px-4

py-3

rounded-xl

bg-gray-50

dark:bg-gray-700

border

dark:border-gray-600

dark:text-white

"

required>

<p id="commissionError"
class="text-red-500 text-sm mt-1"></p>


</div>










<!-- Calculation -->


<div class="

bg-emerald-50

dark:bg-emerald-900/30

rounded-xl

p-5

mb-6

">



<p class="text-sm text-gray-500">

Dentist Earnings

</p>


<h2

id="earning"

class="

text-2xl

font-bold

text-emerald-600

dark:text-emerald-400

">

LKR 0.00

</h2>



</div>









<!-- Buttons -->


<div class="flex justify-end gap-4">



<a href="${pageContext.request.contextPath}/admin/dentists/assign-treatment-list?id=<%=dentist.getDentistId()%>&name=<%=dentist.getFullName()%>"

class="

px-6

py-3

rounded-xl

bg-gray-200

dark:bg-gray-700

dark:text-white

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

">

Assign Treatment

</button>




</div>





</form>






</div>





</main>








<script>


function calculateCommission() {

    let treatment =
    document.getElementById("treatment");

    let earning =
    document.getElementById("earning");

    if (treatment.selectedIndex <= 0) {

        earning.innerHTML = "LKR 0.00";
        return;

    }

    let fee = parseFloat(
        treatment.options[treatment.selectedIndex]
        .getAttribute("data-fee")
    );

    let commission = parseFloat(
        document.getElementById("commission").value
    );

    if (
        isNaN(fee) ||
        isNaN(commission) ||
        commission < 0 ||
        commission > 100
    ) {

        earning.innerHTML = "LKR 0.00";
        return;

    }

    let amount = fee * commission / 100;

    earning.innerHTML =
    "LKR " + amount.toFixed(2);

}


function validateCommission(){

    let input =
    document.getElementById("commission");

    let error =
    document.getElementById("commissionError");

    let value = input.value;

    error.innerHTML = "";

    if(value === ""){
        return;
    }

    value = parseFloat(value);

    if(isNaN(value)){

        input.value = "";
        error.innerHTML = "Enter a valid commission.";

        return;
    }

    if(value < 0){

        input.value = 0;

        error.innerHTML =
        "Commission cannot be negative.";

        return;
    }

    if(value > 100){

        input.value = 100;

        error.innerHTML =
        "Maximum commission is 100%.";

    }

}




function validateForm(){

    let valid = true;

    let treatment =
    document.getElementById("treatment").value;

    let commission =
    parseFloat(
        document.getElementById("commission").value
    );

    document.getElementById("treatmentError").innerHTML = "";
    document.getElementById("commissionError").innerHTML = "";

    if(treatment == ""){

        document.getElementById("treatmentError").innerHTML =
        "Please select a treatment.";

        valid = false;
    }

    if(isNaN(commission)){

        document.getElementById("commissionError").innerHTML =
        "Commission is required.";

        valid = false;
    }
    else if(commission < 0){

        document.getElementById("commissionError").innerHTML =
        "Commission cannot be negative.";

        valid = false;
    }
    else if(commission > 100){

        document.getElementById("commissionError").innerHTML =
        "Commission cannot exceed 100%.";

        valid = false;
    }

    return valid;

}


</script>






</body>

</html>