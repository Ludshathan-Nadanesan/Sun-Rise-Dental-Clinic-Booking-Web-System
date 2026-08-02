package com.sunrise.servlet;

import java.io.IOException;

import com.sunrise.dao.TreatmentDAO;
import com.sunrise.model.Treatments;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/treatments/add")
public class AddTreatmentServlet extends HttpServlet{
	
    private static final long serialVersionUID = 1L;
    private TreatmentDAO treatmentDAO;
	
	@Override
	public void init() throws ServletException {
		
		treatmentDAO = new TreatmentDAO();
	}
	
	// open treatment add page
	@Override
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getRequestDispatcher("/admin/treatments/add.jsp")
		.forward(request, response);
		
	}
	
	// save treatment
	@Override
	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {
		
		String treatmentName = 
				request.getParameter("treatmentName");
		
		String description = 
				request.getParameter("description");
		
		String estimatedDuration = 
				request.getParameter("estimatedDuration");
		
		String defaultFee = 
				request.getParameter("defaultFee");
		
		Double fee;
		int duration;
		
		
		// validation
		
		if(treatmentName == null ||
		   treatmentName.trim().length() < 3) {
			
			setMessage(
					request,
					"Treatment name must contain minimum 3 characters.",
					"error"
			);
			
			redirectBack(request, response);
			
			return;
		}
		
		if (treatmentDAO.treatmentExist(treatmentName.trim())) {
			setMessage(
                    request,
                    "The treatment name already in treatments.",
                    "error"
            );

            redirectBack(request,response);

            return;
		}
		
		if(description == null ||
				description.trim().length() < 10) {
					
					setMessage(
							request,
							"Treatment description must contain minimum 10 characters.",
							"error"
					);
					
					redirectBack(request, response);
					
					return;
		}
		
		if (estimatedDuration == null ||
			estimatedDuration.trim().isEmpty()) {
			
			setMessage(
                    request,
                    "Estimated duration must contain minimum 1 digits.",
                    "error"
            );


            redirectBack(request,response);

            return;
		}
		
		try {
			duration = Integer.parseInt(estimatedDuration.trim());
			
			// max 24hrs = 1440 minutes
			if (duration <= 0 || duration > 1440) {

				setMessage(
	                    request,
	                    "Estimated duration must be between 1 and 1440.",
	                    "error"
	            );


	            redirectBack(request,response);
	            
	            return;
	            
			}
			
		} catch (NumberFormatException e) {
			setMessage(
                    request,
                    "Estimated duration must be a valid integer number.",
                    "error"
            );


            redirectBack(request,response);
            
            return;
		}
		
		if(defaultFee == null ||
		   defaultFee.trim().isEmpty()) {

			setMessage(
                    request,
                    "Default fee must contain minimum 1 digits.",
                    "error"
            );

            redirectBack(request,response);

            return;			
		}
		
		try {
            fee = Double.parseDouble(defaultFee.trim());
            
            if(fee < 0) {
    			setMessage(
                        request,
                        "Default fee must be a positve number.",
                        "error"
                );

                redirectBack(request,response);

                return;            	
            }
            
		} catch (NumberFormatException e) {
			setMessage(
                    request,
                    "Default fee is invalid format.",
                    "error"
            );

            redirectBack(request,response);
            
            return;
		}
		
		
		// Create treatment object
		Treatments treatment = new Treatments();
		
		treatment.setTreatmentName(treatmentName);
		
		treatment.setDescription(description);
		
		treatment.setEstimatedDuration(duration);
		
		treatment.setDefaultFee(fee);
		
		
		boolean result = treatmentDAO.addTreatment(treatment);
		
		if(result){
            setMessage(
                    request,
                    "Treatment added successfully.",
                    "success"
            );
        }
        else{
            setMessage(
                    request,
                    "Failed to add treatment.",
                    "error"
            );
        }

        redirectBack(request,response);
		
	}
	
	private void redirectBack(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {



        response.sendRedirect(
                request.getContextPath()
                +
                "/admin/treatments"
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
