<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>


<head>

<meta charset="UTF-8">

<title>Dentist Management</title>


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





<!-- Theme + Header -->

<%@ include file="../../utils/theme.jsp" %>

<%@ include file="../includes/admin-header.jsp" %>








<main class="p-8">


<!-- Page Header -->
<div class="mb-10">


<div class="
flex
items-center
gap-4
">

<div>


<h1 class="
text-3xl

font-bold

text-gray-800
dark:text-white
">

Dentist Management

</h1>




<p class="
text-gray-500
dark:text-gray-400

mt-2
">

Manage dentists, treatments and clinical schedules.

</p>



</div>




</div>


</div>




<%@ include file="tabs/records.jsp" %>


</main>


</body>

</html>