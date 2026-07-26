<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="com.sunrise.model.User" %>


<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Manage Receptionists</title>

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
			Receptionist Management
			</h2>
			
			
			<p class="
			text-gray-500
			dark:text-gray-400
			mt-2
			">
			Manage receptionist accounts and access.
			</p>

        </div>



        <!-- Add Button -->        
        <a href="${pageContext.request.contextPath}/admin/receptionists/add"
		   class="
			bg-emerald-500
			hover:bg-emerald-600
			
			text-white
			
			px-6 py-3
			
			rounded-xl
			
			shadow-md
			
			hover:shadow-lg
			
			transition
			
			font-medium
			">
		+ Add Receptionist
		</a>


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
              action="${pageContext.request.contextPath}/admin/receptionists"
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


                    <option value="status">
                        Status
                    </option>


                </select>


            </div>




            <!-- Search Button -->


            <button type="submit"
            class="
				bg-emerald-500
				hover:bg-emerald-600
				
				text-white
				
				px-7 py-3
				
				rounded-xl
				
				font-medium
				
				transition
				">


                Search


            </button>




        </form>


    </div>







    <!-- Receptionist Table -->


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
                            Status
                        </th>


                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            Created At
                        </th>


                        <th class="px-6 py-4 text-sm font-semibold text-gray-700 dark:text-gray-200">
                            Action
                        </th>


                    </tr>

                </thead>





                <tbody>


<%

List<User> receptionists =
(List<User>) request.getAttribute("receptionists");



if(receptionists != null && !receptionists.isEmpty()){



    for(User user : receptionists){


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

                            <%= user.getFullname() %>

                        </td>





                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">

                            <%= user.getEmail() %>

                        </td>






                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">


                            <%= String.format(
                                    "0%09d",
                                    user.getPhone()
                                ) %>


                        </td>





                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">


<%

if(user.getStatus().equalsIgnoreCase("active")){

%>


                            <span class="bg-green-100
								dark:bg-green-900/40
								
								text-green-700
								dark:text-green-400">

                                Active

                            </span>


<%

}
else{

%>



                            <span class="bg-red-100
								dark:bg-red-900/40
								
								text-red-700
								dark:text-red-400">

                                Inactive

                            </span>



<%

}

%>


                        </td>






                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">


                            <%= user.getCreatedAt() %>


                        </td>





                        <td class="px-6 py-4 text-gray-700 dark:text-gray-300">


                            <a
                            href="${pageContext.request.contextPath}/admin/receptionists/edit?id=<%=user.getUserId()%>"
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

                                Manage


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

                            No receptionist records found.

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