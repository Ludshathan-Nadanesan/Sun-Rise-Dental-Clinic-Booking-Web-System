<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String uri = request.getRequestURI();

String activeClass =
"text-emerald-600 dark:text-emerald-400 font-semibold border-b-2 border-emerald-600 dark:border-emerald-400 pb-2";

String normalClass =
"text-gray-600 dark:text-gray-300 hover:text-emerald-600 dark:hover:text-emerald-400 transition";
%>
<header class="
bg-white dark:bg-gray-900
shadow-lg
border-b
border-gray-200 dark:border-gray-700
transition-colors duration-300
">


<div class="
flex items-center justify-between
px-8 py-5
">



<!-- Logo -->

<div class="flex items-center gap-4">


<div class="
w-15 h-15
rounded-xl
flex items-center justify-center
overflow-hidden
">

    <img
        src="${pageContext.request.contextPath}/logo/logo-light.png"
        alt="Sunrise Dental Clinic Logo"
        class="w-full h-full object-contain"
    >

</div>



<div>

<h1 class="
text-2xl
font-bold
text-emerald-600
dark:text-emerald-400
">

Sunrise Dental Clinic

</h1>


<p class="
text-sm
text-gray-500
dark:text-gray-400
">

Admin Dashboard

</p>


</div>


</div>





<!-- Right Section -->


<div class="flex items-center gap-5">



<!-- Theme Toggle -->


<button 
onclick="toggleTheme()"

class="
w-10 h-10
rounded-full

flex items-center justify-center

bg-gray-100
dark:bg-gray-800

border
border-gray-200
dark:border-gray-700

hover:scale-110

transition

cursor-pointer
">


<!-- Moon -->

<svg 
class="
dark:hidden
w-5 h-5
text-gray-700
"
fill="none"
viewBox="0 0 24 24"
stroke="currentColor">


<path 
stroke-linecap="round"
stroke-linejoin="round"
stroke-width="2"
d="M21 12.79A9 9 0 1111.21 3 7 7 0 0021 12.79z"/>


</svg>



<!-- Sun -->


<svg 
class="
hidden dark:block
w-5 h-5
text-yellow-400
"
fill="none"
viewBox="0 0 24 24"
stroke="currentColor">


<path 
stroke-linecap="round"
stroke-linejoin="round"
stroke-width="2"
d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364-6.364-.707.707M6.343 17.657l-.707.707m12.728 0-.707-.707M6.343 6.343l-.707-.707"/>


</svg>


</button>






<!-- Admin Info -->


<div class="text-right hidden sm:block">


<p class="
font-semibold
text-gray-800
dark:text-white
">

Welcome,
<%= session.getAttribute("full_name") %>

</p>


<p class="
text-sm
text-gray-500
dark:text-gray-400
capitalize
">

<%= session.getAttribute("role") %>

</p>


</div>






<!-- Logout -->


<a href="${pageContext.request.contextPath}/logout"

class="
px-5 py-2.5

rounded-xl

bg-red-500

hover:bg-red-600

text-white

font-medium

shadow-md

transition

">

Logout

</a>



</div>


</div>





<!-- Navigation -->


<nav class="
border-t

border-gray-200
dark:border-gray-700

px-8 py-3

overflow-x-auto

">


<div class="
flex gap-7
text-sm
font-medium
whitespace-nowrap
">


<a href="${pageContext.request.contextPath}/admin"
class="<%= uri.contains("/admin/dashboard.jsp") ? activeClass : normalClass %>">

Dashboard

</a>




<a href="${pageContext.request.contextPath}/admin/receptionists"

class="<%= uri.contains("/admin/receptionists/list.jsp") ? activeClass : normalClass %>">

Receptionists

</a>





<a href="${pageContext.request.contextPath}/admin/dentists"

class="<%= uri.contains("/admin/dentists") ? activeClass : normalClass %>">

Dentists

</a>





<a href="${pageContext.request.contextPath}/admin/treatments"

class="<%= uri.contains("/admin/treatments") ? activeClass : normalClass %>">

Treatments

</a>





<a href="#"

class="<%= uri.contains("/admin/appointments") ? activeClass : normalClass %>">

Appointments

</a>





<a href="#"

class="<%= uri.contains("/admin/patients") ? activeClass : normalClass %>">

Patients

</a>





<a href="#"

class="<%= uri.contains("/admin/reports") ? activeClass : normalClass %>">

Reports

</a>





<a href="#"

class="<%= uri.contains("/admin/settings") ? activeClass : normalClass %>">

Settings

</a>


</div>


</nav>


</header>