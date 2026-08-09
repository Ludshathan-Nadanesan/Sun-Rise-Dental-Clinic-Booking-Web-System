package com.sunrise.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.sun.net.httpserver.Request;
import com.sunrise.dao.DentistDAO;
import com.sunrise.dao.DentistTreatmentDAO;
import com.sunrise.model.Dentist;
import com.sunrise.model.DentistTreatment;
import com.sunrise.model.Treatments;


@WebServlet("/admin/dentists/assign-treatment")
public class AssignTreatmentSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DentistTreatmentDAO dtDAO;
	private DentistDAO dDao;
	
	@Override
	public void init() throws ServletException {
		dtDAO = new DentistTreatmentDAO();
		dDao = new DentistDAO();
		
	}
	
	// open assign dentist to a treatment page
	@Override
	protected void doGet(
			HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException{
		
		int dentistId = Integer.parseInt(
				req.getParameter("id")
				);
		
		Dentist dentist =
                dDao.getDentistById(dentistId);

        req.setAttribute(
                "dentist",
                dentist
        );
        
        List<Treatments> availableTreatments = dtDAO.getAvailableTreatmentsForDentist(dentistId);
        
        req.setAttribute(
                "availableTreatments",
                availableTreatments
        );
		
		req.getRequestDispatcher("/admin/dentists/assign-treatment.jsp")
		.forward(req, res);
	}
	
	// save assigned dentist treatment
	
	@Override
	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse res
			) throws IOException {
		
		String dentistIdStr = req.getParameter("dentistId");
		String treatmentIdStr = req.getParameter("treatmentId");
		String dentComStr = req.getParameter("commission");
		String dentName = req.getParameter("dentistName");
		
		int dentId, treatId;
		double dentComPerc;
		
		// validation
		if (dentistIdStr.trim().isEmpty() && 
			treatmentIdStr.trim().isEmpty() && 
			dentComStr.trim().isEmpty()) {
			
			setMessage(
					req,
					"Fields are empty!.",
					"error"
			);
			
			redirectBack(req, res);
			
			return;
		}
		
		try {
			dentId = Integer.parseInt(dentistIdStr.trim());
			treatId = Integer.parseInt(treatmentIdStr.trim());
			dentComPerc = Double.parseDouble(dentComStr.trim());
		} catch (NumberFormatException e) {

			setMessage(
					req,
					"Fields are empty!.",
					"error"
			);
			
			redirectBack(req, res);
			
			return;
		}
		
		// create dentist treatment object
		DentistTreatment dt = new DentistTreatment();
		
		dt.setDentistId(dentId);
		dt.setTreatmentId(treatId);
		dt.setDentCommissionPerc(dentComPerc);
		
		boolean result = dtDAO.assignTreatment(dt);
		
		if (result) {
			setMessage(req, 
					"Treatment assigned successfully.", 
					"success");
			redirectBack(req, res, dentId, dentName);
			
			return;
		} else {
			setMessage(req, 
					"Failed to assign treatment.", 
					"error");
			redirectBack(req, res, dentId, dentName);
		}
	}
	
	
	private void redirectBack(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {



        response.sendRedirect(
                request.getContextPath()
                +
                "/admin/dentists"
        );


    }
	
	private void redirectBack(
            HttpServletRequest request,
            HttpServletResponse response,
            int dentId,
            String name)
            throws IOException {



        response.sendRedirect(
                request.getContextPath()
                +
                "/admin/dentists/assign-treatment-list?"
                + "id=" + dentId
                + "&name=" + name
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
