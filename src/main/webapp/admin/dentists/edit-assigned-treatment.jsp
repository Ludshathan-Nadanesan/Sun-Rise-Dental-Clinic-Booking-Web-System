<%@page import="com.sunrise.model.Dentist"%>
<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.sunrise.model.DentistTreatment"%>


<%

DentistTreatment treatment =
(DentistTreatment) request.getAttribute(
    "dentistTreatment"
);

Dentist dentist = (Dentist) request.getAttribute("dentist");


%>


<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>
Edit Treatment Assignment
</title>


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

max-w-2xl

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

Edit Treatment Assignment

</h1>


<p class="

text-sm

text-gray-500
dark:text-gray-400

mt-1

">

Update the dentist's commission percentage.

</p>

</div>

</div>


<!-- Dentist Information -->

<div class="

bg-gray-50
dark:bg-gray-700

rounded-xl

p-5

mb-6

">

<div class="grid grid-cols-1 md:grid-cols-2 gap-4">


<div>

<p class="text-xs text-gray-500 dark:text-gray-400">

Dentist Name

</p>

<p class="

font-medium

text-gray-800
dark:text-white

">

<%= dentist.getFullName() %>

</p>

</div>


<div>

<p class="text-xs text-gray-500 dark:text-gray-400">

Status

</p>

<p class="

font-medium

text-gray-800
dark:text-white

">

<%= dentist.getStatus() %>

</p>

</div>


<div>

<p class="text-xs text-gray-500 dark:text-gray-400">

Email

</p>

<p class="

font-medium

text-gray-800
dark:text-white

">

<%= dentist.getEmail() %>

</p>

</div>


<div>

<p class="text-xs text-gray-500 dark:text-gray-400">

Phone

</p>

<p class="

font-medium

text-gray-800
dark:text-white

">

+94 <%= dentist.getPhone() %>

</p>

</div>


</div>

</div>


<!-- Treatment Information -->

<div class="

bg-gray-50
dark:bg-gray-700

rounded-xl

p-5

mb-6

">


<h2 class="

font-semibold

text-gray-800
dark:text-white

mb-4

">

🦷 Treatment Information

</h2>


<div class="grid grid-cols-1 md:grid-cols-2 gap-4">


<div>

<p class="text-xs text-gray-500 dark:text-gray-400">

Treatment

</p>

<p class="

font-medium

text-gray-800
dark:text-white

">

<%= treatment.getTreatmentName() %>

</p>

</div>


<div>

<p class="text-xs text-gray-500 dark:text-gray-400">

Duration

</p>

<p class="

font-medium

text-gray-800
dark:text-white

">

<%= treatment.getEstimatedDuration() %> minutes

</p>

</div>


<div>

<p class="text-xs text-gray-500 dark:text-gray-400">

Default Clinic Fee

</p>

<p class="

font-semibold

text-gray-800
dark:text-white

">

LKR <%= String.format("%.2f",
        treatment.getDefaultFee()) %>

</p>

</div>


</div>

</div>


<!-- Form -->

<form

method="post"

action="${pageContext.request.contextPath}/admin/dentists/edit-assigned-treatment"

onsubmit="return validateForm()"


>


<input

type="hidden"

name="id"

value="<%= treatment.getId() %>"
>


<input

type="hidden"

name="dentistId"

value="<%= treatment.getDentistId() %>"
>


<!-- Commission -->

<div class="mb-6">


<label class="

block

mb-2

text-sm

font-medium

text-gray-700
dark:text-gray-200

">

Commission Percentage (%)

</label>


<input

type="number"

name="commission"

id="commission"

step="0.01"

min="0"

max="100"

value="<%= treatment.getDentCommissionPerc() %>"

oninput="validateCommission(); calculateCommission();"

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


<p

id="commissionError"

class="

text-red-500

text-sm

mt-1

">

</p>


<p class="

text-xs

text-gray-500
dark:text-gray-400

mt-2

">

Commission must be between 0% and 100%.

</p>

</div>


<!-- Calculation -->

<div class="

bg-emerald-50
dark:bg-emerald-900/30

rounded-xl

p-5

mb-8

">


<div class="flex justify-between items-center">


<div>

<p class="

text-sm

text-gray-500
dark:text-gray-400

">

Dentist Commission

</p>

<p

id="commissionDisplay"

class="

text-sm

font-medium

text-emerald-600
dark:text-emerald-400

">

<%= String.format("%.2f",
        treatment.getDentCommissionPerc()) %>%

</p>

</div>


<div class="text-right">


<p class="

text-sm

text-gray-500
dark:text-gray-400

">

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

LKR <%= String.format("%.2f",
        treatment.getCommissionAmount()) %>

</h2>


</div>

</div>


</div>


<!-- Buttons -->

<div class="

flex

justify-end

gap-4

pt-3

">


<a

href="${pageContext.request.contextPath}/admin/dentists/assign-treatment-list?id=<%= treatment.getDentistId() %>&name=<%= java.net.URLEncoder.encode(dentist.getFullName(), "UTF-8") %>"

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

hover:shadow-lg

transition

">

Save Changes

</button>


</div>


</form>


</div>


</main>


<script>


const defaultFee =
<%= treatment.getDefaultFee() %>;



function calculateCommission() {

    const commissionInput =
        document.getElementById("commission");

    const earning =
        document.getElementById("earning");

    const display =
        document.getElementById("commissionDisplay");


    let commission =
        parseFloat(commissionInput.value);


    /*
     * Invalid value
     */

    if (
        isNaN(commission) ||
        commission < 0 ||
        commission > 100
    ) {

        earning.innerHTML = "LKR 0.00";

        display.innerHTML = "0.00%";

        return;
    }


    /*
     * Calculate dentist earning
     */

    const amount =
        defaultFee * commission / 100;


    earning.innerHTML =
        "LKR " + amount.toFixed(2);


    display.innerHTML =
        commission.toFixed(2) + "%";

}



function validateCommission() {

    const input =
        document.getElementById("commission");

    const error =
        document.getElementById("commissionError");


    error.innerHTML = "";


    if(input.value === "") {

        return;
    }


    let value =
        parseFloat(input.value);


    if(isNaN(value)) {

        input.value = "";

        error.innerHTML =
            "Enter a valid commission percentage.";

        return;
    }


    /*
     * Prevent negative values
     */

    if(value < 0) {

        input.value = 0;

        error.innerHTML =
            "Commission cannot be negative.";

        return;
    }


    /*
     * Prevent values above 100
     */

    if(value > 100) {

        input.value = 100;

        error.innerHTML =
            "Commission cannot exceed 100%.";

        return;
    }

}



function validateForm() {

    const input =
        document.getElementById("commission");

    const error =
        document.getElementById("commissionError");


    error.innerHTML = "";


    if(input.value === "") {

        error.innerHTML =
            "Commission percentage is required.";

        return false;
    }


    const commission =
        parseFloat(input.value);


    if(isNaN(commission)) {

        error.innerHTML =
            "Enter a valid commission percentage.";

        return false;
    }


    if(commission < 0) {

        error.innerHTML =
            "Commission cannot be negative.";

        return false;
    }


    if(commission > 100) {

        error.innerHTML =
            "Commission cannot exceed 100%.";

        return false;
    }


    return true;

}


/*
 * Show initial calculation
 */

calculateCommission();

</script>


</body>

</html>