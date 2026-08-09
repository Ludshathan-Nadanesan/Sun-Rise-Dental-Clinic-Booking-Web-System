<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="com.sunrise.model.Dentist" %>



<%

List<Dentist> dentists =
(List<Dentist>) request.getAttribute("dentists");

%>



<%@ include file="../../includes/message.jsp" %>






<div class="
bg-white
dark:bg-gray-800

rounded-2xl

shadow-md

border
border-gray-100
dark:border-gray-700

p-6
">







<!-- Search + Add Dentist -->


<form method="get"

action="${pageContext.request.contextPath}/admin/dentists"


class="
flex

flex-col

lg:flex-row

gap-4

mb-6
">






<!-- Search -->


<div class="flex-1">


<input


type="text"


name="search"


value="<%= request.getParameter("search") != null 
? request.getParameter("search") : "" %>"


placeholder="Search dentist name, email or phone..."


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








<!-- Sort -->


<div>


<select

name="sort"


class="
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

"


>


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


<button

type="submit"


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









<!-- Add Dentist -->


<a

href="${pageContext.request.contextPath}/admin/dentists/add"


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


text-center

">


+ Add Dentist


</a>






</form>









<!-- Table -->


<div class="

rounded-xl

overflow-hidden


border

border-gray-200

dark:border-gray-700

">





<div class="
max-h-[550px]

overflow-y-auto

">





<table class="w-full text-left">







<thead class="

bg-gray-100

dark:bg-gray-700

sticky top-0 z-10

">



<tr>




<th class="
px-6
py-4

text-sm

font-semibold

text-gray-700

dark:text-gray-200
">

Name

</th>





<th class="
px-6
py-4

text-sm

font-semibold

text-gray-700

dark:text-gray-200
">

Email

</th>





<th class="
px-6
py-4

text-sm

font-semibold

text-gray-700

dark:text-gray-200
">

Phone

</th>





<th class="
px-6
py-4

text-sm

font-semibold

text-gray-700

dark:text-gray-200
">

Status

</th>





<th class="
px-6
py-4

text-sm

font-semibold

text-gray-700

dark:text-gray-200
">

Created At

</th>


<th class="
px-6
py-4

text-sm

font-semibold

text-gray-700

dark:text-gray-200
">

Assigned Treatments

</th>



<th class="
px-6
py-4

text-sm

font-semibold

text-gray-700

dark:text-gray-200
">

Availability

</th>



<th class="
px-6
py-4

text-sm

font-semibold

text-gray-700

dark:text-gray-200
">

Action

</th>



</tr>



</thead>









<tbody class="divide-y dark:divide-gray-700">






<%

if(dentists != null && !dentists.isEmpty()){


for(Dentist dentist : dentists){


%>






<tr class="

hover:bg-gray-50

dark:hover:bg-gray-700/50

transition

">








<td class="
px-6
py-4

font-medium

text-gray-800

dark:text-gray-200
">


<%= dentist.getFullName() %>


</td>









<td class="
px-6
py-4

text-gray-600

dark:text-gray-300
">


<%= dentist.getEmail() %>


</td>









<td class="
px-6
py-4

text-gray-600

dark:text-gray-300
">


+94<%= String.format("%09d", dentist.getPhone()) %>


</td>









<td class="px-6 py-4">





<%

if(dentist.getStatus().equals("active")){


%>



<span class="

px-3

py-1

rounded-full

text-sm

font-medium


bg-green-100

dark:bg-green-900/40


text-green-700

dark:text-green-400

">

Active

</span>




<%

}

else{


%>





<span class="

px-3

py-1

rounded-full

text-sm

font-medium


bg-red-100

dark:bg-red-900/40


text-red-700

dark:text-red-400

">

Inactive

</span>





<%

}

%>



</td>









<td class="
px-6
py-4

text-gray-600

dark:text-gray-300
">


<%= dentist.getCreatedAt() %>


</td>



<td class="
px-6
py-4

text-gray-600

dark:text-gray-300
">

<a

href="${pageContext.request.contextPath}/admin/dentists/assign-treatment-list?id=<%=dentist.getDentistId()%>&name=<%=dentist.getFullName()%>"


class="

px-4 py-2
								
								rounded-xl
								
								bg-gray-100
								dark:bg-gray-700
								
								text-gray-700
								dark:text-gray-200
								
								hover:bg-gray-500
								hover:text-white
								
								transition
								
								text-sm
								
								font-medium

">

<%= dentist.getAssignedTreatments() %>

</a>

</td>


<td class="px-6 py-4">

    <div class="flex items-center gap-2">

        <!-- Available -->
        <a href="${pageContext.request.contextPath}/admin/dentists/availability?dentistId=<%=dentist.getDentistId()%>"
           class="
           inline-flex items-center gap-1.5
           px-3 py-2
           rounded-lg
           text-sm font-medium

           bg-emerald-50
           text-emerald-700

           dark:bg-emerald-900/10
           dark:text-emerald-400

           border border-emerald-200
           dark:border-emerald-800

           hover:bg-emerald-100
           dark:hover:bg-emerald-900/50

           transition duration-200
           ">

            <span class="w-2 h-2 rounded-full bg-emerald-500"></span>

            Available

        </a>


        <!-- Unavailable -->
        <a href="${pageContext.request.contextPath}/admin/dentists/unavailability?dentistId=<%=dentist.getDentistId()%>"
           class="
           inline-flex items-center gap-1.5
           px-3 py-2
           rounded-lg
           text-sm font-medium

           bg-red-50
           text-red-700

           dark:bg-red-900/10
           dark:text-red-400

           border border-red-200
           dark:border-red-800

           hover:bg-red-100
           dark:hover:bg-red-900/50

           transition duration-200
           ">

            <span class="w-2 h-2 rounded-full bg-red-500"></span>

            Unavailable

        </a>

    </div>

</td>


<td class="px-6 py-4">





<a

href="${pageContext.request.contextPath}/admin/dentists/edit?id=<%=dentist.getDentistId()%>"


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
								
								font-medium

">


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


class="

text-center

py-10


text-gray-500

dark:text-gray-400

">


No dentists found.


</td>


</tr>






<%

}

%>








</tbody>





</table>





</div>






</div>






</div>