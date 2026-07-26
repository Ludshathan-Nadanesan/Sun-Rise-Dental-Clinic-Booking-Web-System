<%@page import="com.sunrise.util.SessionUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%

// Prevent browser caching

response.setHeader(
    "Cache-Control",
    "no-cache, no-store, must-revalidate"
);

response.setHeader(
    "Pragma",
    "no-cache"
);

response.setDateHeader(
    "Expires",
    0
);


// Check existing session

if(SessionUtil.isLoggedIn(request)){


    String role = session.getAttribute("role").toString();



    if(role.equalsIgnoreCase("admin")){


        response.sendRedirect(
            "admin"
        );


        return;


    }
    else if(role.equalsIgnoreCase("receptionist")){


        response.sendRedirect(
            "receptionist"
        );


        return;


    }

}

%>
    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>

</head>


<body class="bg-gray-50 dark:bg-gray-900 h-screen relative transition-colors duration-300">

<%@ include file="/utils/theme.jsp" %>


<!-- Theme Toggle Top Left -->

<button 
onclick="toggleTheme()"
class="
fixed top-5 left-5 z-50
w-11 h-11
rounded-full
flex items-center justify-center
cursor-pointer
bg-white dark:bg-gray-800
shadow-lg
border border-gray-200 dark:border-gray-700
hover:scale-110
transition duration-300
">


<!-- Sun Icon -->

<svg 
class="hidden dark:block w-5 h-5 text-yellow-400"
xmlns="http://www.w3.org/2000/svg"
fill="none"
viewBox="0 0 24 24"
stroke="currentColor">

<path 
stroke-linecap="round"
stroke-linejoin="round"
stroke-width="2"
d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364-6.364-.707.707M6.343 17.657l-.707.707m12.728 0-.707-.707M6.343 6.343l-.707-.707M12 8a4 4 0 100 8 4 4 0 000-8z"/>

</svg>



<!-- Moon Icon -->

<svg 
class="block dark:hidden w-5 h-5 text-gray-700"
xmlns="http://www.w3.org/2000/svg"
fill="none"
viewBox="0 0 24 24"
stroke="currentColor">

<path 
stroke-linecap="round"
stroke-linejoin="round"
stroke-width="2"
d="M21 12.79A9 9 0 1111.21 3 7 7 0 0021 12.79z"/>

</svg>


</button>




<section>

<div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">


<a href="#" 
class="
flex items-center mb-6
text-3xl font-bold
text-gray-900 dark:text-white
tracking-wide
">

🦷 SunRise Dental Clinic

</a>



<div 
class="
w-full
sm:max-w-md
rounded-2xl
shadow-xl
border
border-gray-100
dark:border-gray-700
bg-white
dark:bg-gray-800
transition-all duration-300
">


<div class="p-8 space-y-6">



<h1 
class="
text-2xl
font-bold
text-gray-900
dark:text-white
">

Login to your account

</h1>




<form class="space-y-5" action="login" method="post">


<div>

<label 
class="
block mb-2
text-sm font-medium
text-gray-900
dark:text-white">

Your email

</label>


<input 
type="email"
name="email"
id="email"
placeholder="name@email.com"

class="
bg-gray-50
dark:bg-gray-700

border
border-gray-300
dark:border-gray-600

text-gray-900
dark:text-white

rounded-xl

focus:ring-2
focus:ring-emerald-400

block w-full

p-3

transition

"

required>


</div>





<div>

<label 
class="
block mb-2
text-sm font-medium
text-gray-900
dark:text-white">

Password

</label>



<input 

type="password"

name="password"

id="password"

placeholder="••••••••"


class="
bg-gray-50
dark:bg-gray-700

border
border-gray-300
dark:border-gray-600

text-gray-900
dark:text-white

rounded-xl

focus:ring-2
focus:ring-emerald-400

block w-full

p-3

transition

"

required>


</div>






<button 

type="submit"

class="
w-full

rounded-xl

py-3

font-semibold

text-white

bg-emerald-400
dark:bg-emerald-700

hover:bg-emerald-500
dark:hover:bg-emerald-600

transition

duration-300

shadow-md

hover:shadow-lg

cursor-pointer

">

Login

</button>



</form>


<!-- Error message keep your existing JSP block here -->
<%
String error = (String) request.getAttribute("error");
if (error != null) {
%>

<div class="mt-4 rounded-lg border border-red-300 bg-red-50 px-4 py-3 text-sm text-red-700 dark:border-red-600 dark:bg-red-900/30 dark:text-red-300">
    <%= error %>
</div>

<%
}
%>


</div>

</div>


</div>

</section>



</body>
</html>