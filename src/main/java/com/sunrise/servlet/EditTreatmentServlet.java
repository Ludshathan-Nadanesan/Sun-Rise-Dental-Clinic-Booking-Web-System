package com.sunrise.servlet;


import java.io.IOException;

import com.sunrise.dao.TreatmentDAO;
import com.sunrise.model.Treatments;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/admin/treatments/edit")
public class EditTreatmentServlet extends HttpServlet {


private static final long serialVersionUID = 1L;


private TreatmentDAO treatmentDAO;



@Override
public void init() throws ServletException {

    treatmentDAO = new TreatmentDAO();

}





// LOAD EDIT PAGE

@Override
protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {



int id =
Integer.parseInt(
request.getParameter("id")
);



Treatments treatment =
treatmentDAO.getTreatmentById(id);



if(treatment == null){

    response.sendRedirect(
    request.getContextPath()
    +"/admin/treatments"
    );

    return;

}



request.setAttribute(
"treatment",
treatment
);



request.getRequestDispatcher(
"/admin/treatments/edit.jsp"
)
.forward(request,response);



}






// UPDATE DATA

@Override
protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {



int id =
Integer.parseInt(
request.getParameter("treatmentId")
);



String name =
request.getParameter("treatmentName").trim();



String description =
request.getParameter("description").trim();



int duration =
Integer.parseInt(
request.getParameter("estimatedDuration")
);



double fee =
Double.parseDouble(
request.getParameter("defaultFee")
);




Treatments treatment =
new Treatments();


treatment.setTreatmentID(id);

treatment.setTreatmentName(name);

treatment.setDescription(description);

treatment.setEstimatedDuration(duration);

treatment.setDefaultFee(fee);




boolean updated =
treatmentDAO.updateTreatment(treatment);



if(updated){

	setMessage(
            request,
            "Treatment updated successfully.",
            "success"
    );


}
else{

	 setMessage(
             request,
             "Failed to update Treatment.",
             "error"
     );

}




response.sendRedirect(
request.getContextPath()
+"/admin/treatments"
);



}


private void setMessage(
        HttpServletRequest request,
        String message,
        String type){


    request.getSession()
           .setAttribute(
                   "message",
                   message
           );



    request.getSession()
           .setAttribute(
                   "messageType",
                   type
           );

}


}