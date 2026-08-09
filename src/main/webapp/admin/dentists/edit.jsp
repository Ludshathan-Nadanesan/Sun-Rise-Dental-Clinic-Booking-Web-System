<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="com.sunrise.model.Dentist" %>



<%

Dentist dentist =
(Dentist)request.getAttribute("dentist");

%>





<!DOCTYPE html>
<html>


<head>


<meta charset="UTF-8">


<% 
    String dname = dentist.getFullName(); 
%>

<title><%= dname %> - Edit Dentist</title>


<style type="text/tailwindcss">

@custom-variant dark (&:where(.dark, .dark *));

</style>


<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>


</head>





<body class="bg-gray-50 dark:bg-gray-900">





<%@ include file="../../utils/theme.jsp" %>

<%@ include file="../includes/admin-header.jsp" %>







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


Edit Dentist


</h1>




<p class="

text-sm

text-gray-500

dark:text-gray-400

mt-1

">


Update dentist profile and account status.


</p>


</div>



</div>









<form method="post"


action="${pageContext.request.contextPath}/admin/dentists/edit"


onsubmit="return validateForm()"


class="space-y-6">







<input type="hidden"

name="dentistId"

value="<%=dentist.getDentistId()%>">









<!-- Name -->


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


id="fullName"


name="fullName"


value="<%=dentist.getFullName()%>"


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

class="text-red-500 text-sm mt-1">

</p>



</div>









<!-- Email -->


<div>


<label class="

block

mb-2

text-sm

font-medium


text-gray-700

dark:text-gray-200

">


Email Address


</label>





<input


type="email"


id="email"


name="email"


value="<%=dentist.getEmail()%>"


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





<p id="emailError"

class="text-red-500 text-sm mt-1">

</p>



</div>









<!-- Phone -->


<div>


<label class="

block

mb-2

text-sm

font-medium


text-gray-700

dark:text-gray-200

">


Phone Number


</label>






<div class="flex">



<span class="

px-5


flex

items-center


rounded-l-xl


bg-gray-100

dark:bg-gray-700


border

border-gray-300

dark:border-gray-600


text-gray-600

dark:text-gray-300

">


+94


</span>







<input


type="text"


id="phone"


name="phone"


maxlength="9"


value="<%=dentist.getPhone()%>"


oninput="this.value=this.value.replace(/[^0-9]/g,'')"


class="

flex-1

px-4

py-3


rounded-r-xl


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




</div>







<p id="phoneError"

class="text-red-500 text-sm mt-1">

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

"

>




<option value="active"

<%= dentist.getStatus().equals("active")?"selected":"" %>>

Active

</option>





<option value="in_active"

<%= dentist.getStatus().equals("in_active")?"selected":"" %>>

Inactive

</option>





</select>




</div>









<!-- Buttons -->


<div class="

flex

justify-end

gap-4

pt-5

">






<a href="${pageContext.request.contextPath}/admin/dentists"


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


function validateForm(){



let valid=true;



let name =
document.getElementById("fullName").value.trim();



let phone =
document.getElementById("phone").value.trim();






document.getElementById("nameError").innerHTML="";


document.getElementById("phoneError").innerHTML="";









if(name.length < 3){


document.getElementById("nameError").innerHTML =
"Minimum 3 characters required.";


valid=false;


}









if(!/^[0-9]{9}$/.test(phone)){


document.getElementById("phoneError").innerHTML =
"Phone number must contain exactly 9 digits.";


valid=false;


}








return valid;


}




</script>







</body>


</html>