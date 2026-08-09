package com.sunrise.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sunrise.dao.DentistTreatmentDAO;
import com.sunrise.model.Dentist;

@WebServlet("/admin/dentists/delete-assigned-treatment")
public class DeleteAssignedTreatmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DentistTreatmentDAO dtDAO;
	
	@Override
	public void init() throws ServletException {
		dtDAO = new DentistTreatmentDAO();
	}
	
	@Override
	protected void doGet (
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		int dentistId = Integer.parseInt(request.getParameter("dentistId"));
		
		String dentistName = request.getParameter("dentistName");
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		boolean res = dtDAO.deleteDentistTreatment(id);
		
		
		System.out.println("Dentist Name == " + dentistName);
		
		if (res) {
			request.getSession()
            .setAttribute(
            "message",
            "Assigned treatment deleted successfully."
            );


            request.getSession()
            .setAttribute(
            "messageType",
            "success"
            );
		}
		else{


            request.getSession()
            .setAttribute(
            "message",
            "Failed to delete assigned treatment."
            );


            request.getSession()
            .setAttribute(
            "messageType",
            "error"
            );
        }
		
		response.sendRedirect(
		        request.getContextPath()
		        +
		        "/admin/dentists/assign-treatment-list?"
		        + "id=" + dentistId
		        + "&"
		        + "name=" + dentistName
		        );
	}
	
	
	
}
