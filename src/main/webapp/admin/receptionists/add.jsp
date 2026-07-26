<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Add Receptionist</title>


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


<div class="
flex
items-center
gap-4
">


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

👤

</div>



<div>


<h2 class="
text-2xl
font-bold

text-gray-800
dark:text-white
">

Add New Receptionist

</h2>


<p class="
text-sm

text-gray-500
dark:text-gray-400
mt-1
">

Create receptionist account and manage clinic access.

</p>


</div>



</div>


</div>








<form 

action="${pageContext.request.contextPath}/admin/receptionists/add"

method="post"

onsubmit="return validateForm();"

class="space-y-6"

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

placeholder="Ex: Vel Murugan"

required

pattern="[A-Za-z]+(?: [A-Za-z]+)+"

title="Spaces are not permitted in this field."

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

Email

</label>



<input

type="email"

name="email"

placeholder="name@email.com"

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



<input

type="text"

name="phone"

id="phone"

maxlength="9"

placeholder="771234567"

required

oninput="validatePhone()"


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



<p id="phoneError"

class="
text-red-500
dark:text-red-400

text-sm

mt-2

hidden
">

</p>



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

Password

</label>




<input

type="password"

name="password"

id="password"

required

placeholder="••••••••"

oninput="validatePassword()"


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




<p id="passwordError"

class="
text-red-500
dark:text-red-400

text-sm

mt-2

hidden
">

</p>



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

Save Receptionist

</button>




</div>




</form>



</div>




</main>


<!--  JS Validation for phone num and password  -->
<script>


function validatePhone(){


    let phone =
        document.getElementById("phone").value;



    let error =
        document.getElementById("phoneError");



    // allow only numbers

    if(!/^[0-9]*$/.test(phone)){


        error.innerHTML =
        "Only numbers are allowed.";


        error.classList.remove("hidden");


        return false;


    }





    if(phone.length !== 9){


        error.innerHTML =
        "Phone number must contain exactly 9 digits.";


        error.classList.remove("hidden");


        return false;


    }





    error.classList.add("hidden");


    return true;


}









function validatePassword(){


    let password =
        document.getElementById("password").value;



    let error =
        document.getElementById("passwordError");




    // no spaces

    if(password.includes(" ")){


        error.innerHTML =
        "Password cannot contain spaces.";


        error.classList.remove("hidden");


        return false;


    }





    // minimum 8 chars + special character

    let pattern =
    /^(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,}$/;





    if(!pattern.test(password)){


        error.innerHTML =
        "Password must contain minimum 8 characters and one special character.";


        error.classList.remove("hidden");


        return false;


    }





    error.classList.add("hidden");


    return true;


}


function validateForm(){


    let phoneValid =
        validatePhone();



    let passwordValid =
        validatePassword();



    return phoneValid && passwordValid;


}






</script>


</body>

</html>