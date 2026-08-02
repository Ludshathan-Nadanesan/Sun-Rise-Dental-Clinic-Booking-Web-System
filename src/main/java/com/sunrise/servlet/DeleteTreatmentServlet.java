package com.sunrise.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sunrise.dao.TreatmentDAO;

@WebServlet("/admin/treatments/delete")
public class DeleteTreatmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TreatmentDAO treatmentDAO;
	
	@Override
	public void init() throws ServletException {
		
		treatmentDAO = new TreatmentDAO();
	}
	
	
	@Override
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		boolean res = treatmentDAO.deleteTreatmentById(id);
		
		if (res) {
			request.getSession()
            .setAttribute(
            "message",
            "Treatment deleted successfully."
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
            "Treatment delete failed."
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
		        "/admin/treatments"
		        );

		
	}

}
