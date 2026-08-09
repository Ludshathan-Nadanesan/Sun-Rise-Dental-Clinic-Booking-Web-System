package com.sunrise.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import com.sunrise.dao.DentistDAO;
import com.sunrise.dao.DentistTreatmentDAO;
import com.sunrise.model.Dentist;
import com.sunrise.model.DentistTreatment;



@WebServlet("/admin/dentists/assign-treatment-list")
public class AssignedDentistList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    private DentistTreatmentDAO dentistTreatmentDAO;
    
    @Override
    public void init() throws ServletException {
    	dentistTreatmentDAO = new DentistTreatmentDAO();
    	
    }
    
    
    
    // open assigned treatments page
    
    @Override
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
    	int dentistId = Integer.parseInt(request.getParameter("id"));
    	
    	
    	String dentistName = request.getParameter("name");
    	
    	List<DentistTreatment> assignedTreatments = dentistTreatmentDAO.getAllDentistTreatmentsByDentistId(dentistId);
    	
    	request.setAttribute(
    			"assignedTreatments",
    			assignedTreatments);
    	
    	request.setAttribute(
    			"dentistId",
    			dentistId
    			);
    	
    	request.setAttribute(
    			"dentistName",
    			dentistName
    			);
    	
    	
    	request.getRequestDispatcher(
                "/admin/dentists/assign-treatment-list.jsp"
        )
        .forward(request, response);
    	
	}

}
