<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%

String message =
(String) session.getAttribute("message");


String type =
(String) session.getAttribute("messageType");



if(message != null){


boolean success =
        "success".equalsIgnoreCase(type);


%>




<div id="toast"

class="
fixed

bottom-6
right-6

z-50

w-[350px]

rounded-2xl

shadow-xl

border

backdrop-blur-md

animate-slide-in

<%= success 
? 
"bg-green-50 dark:bg-green-900/40 border-green-200 dark:border-green-800"
:
"bg-red-50 dark:bg-red-900/40 border-red-200 dark:border-red-800"
%>

">






<div class="
flex
items-start
gap-4

p-5
">





<!-- Icon -->

<div

class="
flex-shrink-0

w-10
h-10

rounded-full

flex
items-center
justify-center

text-xl

<%= success

?

"bg-green-100 dark:bg-green-800 text-green-600 dark:text-green-300"

:

"bg-red-100 dark:bg-red-800 text-red-600 dark:text-red-300"

%>

">


<%= success ? "✓" : "✕" %>


</div>







<!-- Message -->

<div class="flex-1">



<h3

class="
font-semibold

<%= success

?

"text-green-800 dark:text-green-300"

:

"text-red-800 dark:text-red-300"

%>

">


<%= success ? "Success" : "Error" %>


</h3>





<p

class="
text-sm

mt-1

<%= success

?

"text-green-700 dark:text-green-200"

:

"text-red-700 dark:text-red-200"

%>

">

<%= message %>


</p>



</div>






<!-- Close Button -->


<button

onclick="closeToast()"

class="
text-gray-400

hover:text-gray-700

dark:hover:text-white

transition

text-lg

">

×


</button>




</div>





</div>







<style>


@keyframes slideIn {


from{

transform:translateX(120%);

opacity:0;

}


to{

transform:translateX(0);

opacity:1;

}


}



.animate-slide-in{

animation:slideIn .4s ease-out;

}


</style>







<script>


function closeToast(){

    const toast =
    document.getElementById("toast");


    if(toast){

        toast.style.opacity="0";

        toast.style.transform="translateX(120%)";


        setTimeout(()=>{

            toast.remove();

        },300);

    }

}





setTimeout(()=>{


    closeToast();


},4000);



</script>







<%

session.removeAttribute("message");

session.removeAttribute("messageType");


}


%>