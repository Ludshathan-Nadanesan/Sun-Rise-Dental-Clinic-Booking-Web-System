<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.sunrise.model.User" %>


<%

User receptionist =
(User)request.getAttribute("receptionist");

%>



<!DOCTYPE html>
<html>

<head>

<title>Edit Receptionist</title>


<style type="text/tailwindcss">

@custom-variant dark (&:where(.dark, .dark *));

</style>


<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>


</head>




<body class="
bg-gray-50
dark:bg-gray-900

transition-colors duration-300
">





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

shadow-xl

border
border-gray-100
dark:border-gray-700

p-8

">








<!-- Header -->


<div class="mb-8">


<div class="flex items-center gap-4">


<div class="
w-14
h-14

rounded-xl

bg-emerald-100
dark:bg-emerald-900/40

flex
items-center
justify-center

text-3xl
">

👤

</div>



<div>


<h2 class="
text-2xl
font-bold

text-gray-800
dark:text-white
">

Edit Receptionist

</h2>



<p class="
text-sm

text-gray-500
dark:text-gray-400

mt-1
">

Update receptionist account details and access status.

</p>


</div>



</div>


</div>









<form

method="post"
action="${pageContext.request.contextPath}/admin/receptionists/edit"
class="space-y-6"

>







<input 

type="hidden"

name="userId"

value="<%=receptionist.getUserId()%>"

>









<!-- Full Name -->


<div>


<label class="
block

mb-2

text-sm
font-medium

text-gray-700
dark:text-gray-200
">

Full Name

</label>



<input

type="text"

name="fullname"

value="<%=receptionist.getFullname()%>"

required


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

transition
"

>


</div>



<!-- Password -->


<div>


<label class="
block

mb-2

text-sm
font-medium

text-gray-700
dark:text-gray-200
">

New Password

</label>


<input

type="password"

name="password"

placeholder="Leave empty to keep current password"


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

transition
"

>




<p class="
text-xs

mt-2

text-gray-500
dark:text-gray-400
">

Leave blank if you don't want to change password.

</p>



</div>









<!-- Status -->


<div>


<label class="
block

mb-2

text-sm
font-medium

text-gray-700
dark:text-gray-200
">

Account Status

</label>



<select

name="status"


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

transition
"



>


<option value="active"

<%= receptionist.getStatus().equals("active")?"selected":"" %>

>

Active

</option>



<option value="in_active"

<%= receptionist.getStatus().equals("in_active")?"selected":"" %>

>

Inactive

</option>


</select>



</div>









<!-- Buttons -->


<div class="
flex

justify-end

gap-4

pt-6

border-t

border-gray-200
dark:border-gray-700

">





<a

href="${pageContext.request.contextPath}/admin/receptionists"


class="
px-6
py-3

rounded-xl

bg-gray-100

dark:bg-gray-700

text-gray-700

dark:text-gray-200

hover:bg-gray-200

dark:hover:bg-gray-600

transition

font-medium
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




</body>

</html>