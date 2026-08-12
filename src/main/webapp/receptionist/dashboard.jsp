<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title><%= session.getAttribute("full_name") %> - Receptionist Dashboard</title>


<style type="text/tailwindcss">

@custom-variant dark (&:where(.dark, .dark *));

</style>


<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css" integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA==" crossorigin="anonymous" referrerpolicy="no-referrer" />


</head>



<body class="bg-gray-50 dark:bg-gray-900 transition-colors duration-300">



<!-- Theme + Header -->

<%@ include file="../../utils/theme.jsp" %>

<%@ include file="includes/receptionist-header.jsp" %>




<main class="p-8">





<!-- Page Header -->


<div class="mb-10">


<h2 class="
text-3xl
font-bold

text-gray-800
dark:text-white
">

Dashboard Overview

</h2>



<p class="
mt-2

text-gray-500
dark:text-gray-400
">

Monitor appoinments and patients.

</p>


</div>







<!-- Dashboard Cards -->


<div class="
grid
grid-cols-1
sm:grid-cols-2
xl:grid-cols-5

gap-6
">







<!-- Patients -->


<div class="
group

bg-white
dark:bg-gray-800

rounded-2xl

p-6

shadow-md

border
border-gray-100
dark:border-gray-700

hover:shadow-xl

transition

">


<div class="
flex
items-center
justify-between
">


<div>


<p class="
text-sm
font-medium

text-gray-500
dark:text-gray-400
">

Total Patients

</p>



<h3 class="
mt-3

text-3xl
font-bold

text-gray-800
dark:text-white
">

${stats.totalPatients}

</h3>


</div>



<div class="
w-12
h-12

rounded-xl

bg-emerald-100
dark:bg-emerald-900/40

flex
items-center
justify-center

text-2xl
">

🧑‍⚕️

</div>


</div>


</div>









<!-- Appointments -->


<div class="
bg-white
dark:bg-gray-800

rounded-2xl

p-6

shadow-md

border
border-gray-100
dark:border-gray-700

hover:shadow-xl

transition
">


<div class="flex items-center justify-between">


<div>


<p class="
text-sm
font-medium

text-gray-500
dark:text-gray-400
">

Today's Appointments

</p>



<h3 class="
mt-3

text-3xl
font-bold

text-gray-800
dark:text-white
">

${stats.todayAppointments}

</h3>


</div>



<div class="
w-12
h-12
rounded-xl

bg-blue-100
dark:bg-blue-900/40

flex
items-center
justify-center

text-2xl
">

📅

</div>


</div>


</div>









<!-- Dentists -->


<div class="
bg-white
dark:bg-gray-800

rounded-2xl

p-6

shadow-md

border
border-gray-100
dark:border-gray-700

hover:shadow-xl

transition
">


<div class="flex items-center justify-between">


<div>


<p class="
text-sm
font-medium

text-gray-500
dark:text-gray-400
">

Active Dentists

</p>


<h3 class="
mt-3

text-3xl
font-bold

text-gray-800
dark:text-white
">

${stats.activeDentists}

</h3>


</div>


<div class="
w-12
h-12

rounded-xl

bg-purple-100
dark:bg-purple-900/40

flex
items-center
justify-center

text-2xl
">

🦷

</div>


</div>


</div>









<!-- Treatments -->


<div class="
bg-white
dark:bg-gray-800

rounded-2xl

p-6

shadow-md

border
border-gray-100
dark:border-gray-700

hover:shadow-xl

transition
">


<div class="flex items-center justify-between">


<div>


<p class="
text-sm
font-medium

text-gray-500
dark:text-gray-400
">

Treatment Types

</p>



<h3 class="
mt-3

text-3xl
font-bold

text-gray-800
dark:text-white
">

${stats.totalTreatments}

</h3>


</div>



<div class="
w-12
h-12

rounded-xl

bg-orange-100
dark:bg-orange-900/40

flex
items-center
justify-center

text-2xl
">

💉

</div>


</div>


</div>









<!-- Users -->


<div class="
bg-white
dark:bg-gray-800

rounded-2xl

p-6

shadow-md

border
border-gray-100
dark:border-gray-700

hover:shadow-xl

transition
">


<div class="flex items-center justify-between">


<div>


<p class="
text-sm
font-medium

text-gray-500
dark:text-gray-400
">

Active Users

</p>



<h3 class="
mt-3

text-3xl
font-bold

text-gray-800
dark:text-white
">

${stats.activeUsers}

</h3>


</div>


<div class="
w-12
h-12

rounded-xl

bg-pink-100
dark:bg-pink-900/40

flex
items-center
justify-center

text-2xl
">

👥

</div>


</div>


</div>







</div>





<!--  Qucik Actions -->


<h2 class="
text-xl
font-bold
my-5
text-gray-800
dark:text-white
">

Quick Actions

</h2>

<div class="grid
grid-cols-1
sm:grid-cols-2
xl:grid-cols-5
gap-6">


<!--  New Appointments  -->
<a 
href="#"
class="
relative
rounded-2xl
p-[1px]

bg-transparent

hover:bg-gradient-to-r
hover:from-emerald-400
hover:via-teal-400
hover:to-emerald-500

transition-all
duration-500

group
cursor-pointer
">

    <div class="
    bg-white
    dark:bg-gray-800

    rounded-2xl

    p-6

    shadow-md

    border
    border-gray-100
    dark:border-gray-700

    group-hover:border-transparent

    group-hover:shadow-xl

    transition-all
    duration-500
    
    text-center
    ">

        <p class="
        text-gray-500
        dark:text-gray-400
        ">
            New Appointment
        </p>

        <h3 class="
        text-3xl
        font-bold
        text-gray-800
        dark:text-white
        mt-2
        ">
            +
            
        </h3>

    </div>

</a>




<!--  Register Patient  -->
<a 
href="#"
class="
relative
rounded-2xl
p-[1px]

bg-transparent

hover:bg-gradient-to-r
hover:from-emerald-400
hover:via-teal-400
hover:to-emerald-500

transition-all
duration-500

group
cursor-pointer
">

    <div class="
    bg-white
    dark:bg-gray-800

    rounded-2xl

    p-6

    shadow-md

    border
    border-gray-100
    dark:border-gray-700

    group-hover:border-transparent

    group-hover:shadow-xl

    transition-all
    duration-500
    
    text-center
    ">

        <p class="
        text-gray-500
        dark:text-gray-400
        ">
            Register Patient
        </p>

        <h3 class="
        text-3xl
        font-bold
        text-gray-800
        dark:text-white
        mt-2
        ">
            <i class="fa-solid fa-clipboard-user"></i>
            
        </h3>

    </div>

</a>



<!--  Find Patient  -->
<div class="
relative
rounded-2xl
p-[1px]

bg-transparent

hover:bg-gradient-to-r
hover:from-emerald-400
hover:via-teal-400
hover:to-emerald-500

transition-all
duration-500

group
cursor-pointer
">

    <div class="
    bg-white
    dark:bg-gray-800

    rounded-2xl

    p-6

    shadow-md

    border
    border-gray-100
    dark:border-gray-700

    group-hover:border-transparent

    group-hover:shadow-xl

    transition-all
    duration-500
    
    text-center
    ">

        <p class="
        text-gray-500
        dark:text-gray-400
        ">
            Find Patient
        </p>

        <h3 class="
        text-3xl
        font-bold
        text-gray-800
        dark:text-white
        mt-2
        ">
            <i class="fa-solid fa-magnifying-glass"></i>
            
        </h3>

    </div>

</div>



<!--  View  Appointments -->
<div class="
relative
rounded-2xl
p-[1px]

bg-transparent

hover:bg-gradient-to-r
hover:from-emerald-400
hover:via-teal-400
hover:to-emerald-500

transition-all
duration-500

group
cursor-pointer
">

    <div class="
    bg-white
    dark:bg-gray-800

    rounded-2xl

    p-6

    shadow-md

    border
    border-gray-100
    dark:border-gray-700

    group-hover:border-transparent

    group-hover:shadow-xl

    transition-all
    duration-500
    
    text-center
    ">

        <p class="
        text-gray-500
        dark:text-gray-400
        ">
            View Appointment
        </p>

        <h3 class="
        text-3xl
        font-bold
        text-gray-800
        dark:text-white
        mt-2
        ">
            <i class="fa-solid fa-calendar-days"></i>
            
        </h3>

    </div>

</div>



<!--  View  Appointments -->
<div class="
relative
rounded-2xl
p-[1px]

bg-transparent

hover:bg-gradient-to-r
hover:from-emerald-400
hover:via-teal-400
hover:to-emerald-500

transition-all
duration-500

group
cursor-pointer
">

    <div class="
    bg-white
    dark:bg-gray-800

    rounded-2xl

    p-6

    shadow-md

    border
    border-gray-100
    dark:border-gray-700

    group-hover:border-transparent

    group-hover:shadow-xl

    transition-all
    duration-500
    
    text-center
    ">

        <p class="
        text-gray-500
        dark:text-gray-400
        ">
            View Bills
        </p>

        <h3 class="
        text-3xl
        font-bold
        text-gray-800
        dark:text-white
        mt-2
        ">
            
            <i class="fa-solid fa-file-invoice-dollar"></i>
            
        </h3>

    </div>

</div>



</div>



<!-- Future Activity Section -->


<div class="
mt-10

bg-white
dark:bg-gray-800

rounded-2xl

shadow-md

border
border-gray-100
dark:border-gray-700

p-6
">


<h3 class="
text-xl
font-bold

text-gray-800
dark:text-white
">

Recent Clinic Activity

</h3>


<p class="
mt-2

text-gray-500
dark:text-gray-400
">

Upcoming appointments, treatments and system activities will appear here.

</p>



</div>




</main>



</body>

</html>