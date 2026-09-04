<%@page import="com.sunrise.model.Patient"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="com.sunrise.model.User" %>


<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Manage Patients</title>

<style type="text/tailwindcss">

@custom-variant dark (&:where(.dark, .dark *));

</style>

<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.0/css/all.min.css" integrity="sha512-ApSLB1Pd3/bZN8fWB/RG9YhN/7bd9Hkf3AGaE2mPfebjrxagjuBtx2GcgdqIlJkUzwylBo61r9Xa9NmgBI0swA==" crossorigin="anonymous" referrerpolicy="no-referrer" />


</head>


<body class="
bg-gray-50
dark:bg-gray-900
transition-colors duration-300
">

<!-- Header Include -->

<%@ include file="../../utils/theme.jsp" %>
<%@ include file="../includes/admin-header.jsp" %>
<%@ include file="../includes/message.jsp" %>





<main class="p-8">


    <!-- Page Header -->

    <div class="
		flex 
		flex-col
		md:flex-row
		md:items-center
		md:justify-between
		gap-5
		mb-10
		">

        <div>

            <h2 class="
			text-3xl 
			font-bold
			text-gray-800
			dark:text-white
			">
			Patients Management
			</h2>
			
			
			<p class="
			text-gray-500
			dark:text-gray-400
			mt-2
			">
			Manage patients details.
			</p>

        </div>



        </div>


    </div>





    <!-- Search + Sort -->
	<div class="
		bg-white
		dark:bg-gray-800
		
		rounded-2xl
		
		shadow-md
		
		border
		border-gray-100
		dark:border-gray-700
		
		p-6
		
		mb-8
		
		">


        <form method="get"
              action="${pageContext.request.contextPath}/admin/patients"
              class="flex flex-col md:flex-row gap-4">



            <!-- Search -->

            <div class="flex-1">

                <input 
                    type="text"
                    name="search"
                    value="<%= request.getParameter("search") != null 
                            ? request.getParameter("search") : "" %>"
                    placeholder="Search name, email or phone..."
                    class="
						w-full
						
						bg-gray-50
						dark:bg-gray-700
						
						border
						border-gray-300
						dark:border-gray-600
						
						text-gray-900
						dark:text-white
						
						rounded-xl
						
						px-4 py-3
						
						focus:ring-2
						focus:ring-emerald-400
						
						outline-none
						">


            </div>



            <!-- Sort -->

            <div>


                <select name="sort"
                class="
					bg-gray-50
					dark:bg-gray-700
					
					text-gray-900
					dark:text-white
					
					border
					border-gray-300
					dark:border-gray-600
					
					rounded-xl
					
					px-4 py-3
					">


                    <option value="newest">
                        Newest
                    </option>


                    <option value="oldest">
                        Oldest
                    </option>


                    <option value="name_asc">
                        Name A-Z
                    </option>


                    <option value="name_desc">
                        Name Z-A
                    </option>

                </select>


            </div>

		

            <!-- Search Button -->


            <button type="submit"
            class="
				px-6
py-3


rounded-xl


bg-gray-800

dark:bg-gray-600


hover:bg-gray-900

dark:hover:bg-gray-700


text-white


font-medium


transition
				">


                Search


            </button>




        </form>


    </div>







    <!-- Patients Table -->


	<div class="
	bg-white
	dark:bg-gray-800
	
	rounded-2xl
	
	shadow-md
	
	border
	border-gray-100
	dark:border-gray-700
	
	overflow-hidden
	">


        <div class="overflow-y-auto max-h-[600px]">



            <table class="w-full text-left">


				<thead class="
				bg-gray-100
				dark:bg-gray-700
				
				sticky top-0
				">

                    <tr>


                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            Name
                        </th>


                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            Email
                        </th>


                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            Phone
                        </th>


                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            Gender
                        </th>


                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            DoB
                        </th>
                        
                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            Address
                        </th>
                        
                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            Registered at
                        </th>

                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            Action
                        </th>


                    </tr>

                </thead>





                <tbody>


<%

List<Patient> patients =
(List<Patient>) request.getAttribute("patients");



if(patients != null && !patients.isEmpty()){



    for(Patient patient : patients){

 
%>




                    <tr class="
						border-b
						
						border-gray-100
						dark:border-gray-700
						
						hover:bg-gray-50
						dark:hover:bg-gray-700/50
						
						transition
						">



                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">

                            <%= patient.getFullName() %>

                        </td>





                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">

                            <%= patient.getEmail() %>

                        </td>






                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">

                            <%= String.format(
                                    "0%09d",
                                    patient.getPhone()
                                ) %>


                        </td>





                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">
                        
                        <%= patient.getGender() %>

                        </td>


                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">

                        <%= patient.getDob() %>

                        </td>


                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">

                        <%= patient.getAddress() %>

                        </td>


                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">

                        <%= patient.getRegisteredAt() %>

                        </td>



                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300 flex justify-start items-center gap-3">


                            <a
                            href = "
                            ${pageContext.request.contextPath}/admin/patients?action=edit&patientId=<%= patient.getPatientId() %>"
                            class="
								px-4 py-2
								
								rounded-xl
								
								bg-gray-100
								dark:bg-gray-700
								
								text-gray-700
								dark:text-gray-200
								
								hover:bg-emerald-500
								hover:text-white
								
								transition
								
								text-sm
								
								font-medium">

                                Edit
                            </a>
                            
                            
                        </td>



                    </tr>



<%

    }

}
else{

%>



                    <tr>

                        <td colspan="6"
                            class="text-center
								py-10
								
								text-gray-500
								dark:text-gray-400">

                            No patients records found.

                        </td>

                    </tr>



<%

}

%>



                </tbody>


            </table>


        </div>


    </div>




</main>




</body>

</html>
