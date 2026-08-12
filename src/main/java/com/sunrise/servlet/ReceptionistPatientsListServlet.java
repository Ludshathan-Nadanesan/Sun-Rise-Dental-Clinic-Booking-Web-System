package com.sunrise.servlet;


import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.sunrise.dao.PatientDAO;
import com.sunrise.model.Patient;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/receptionist/patients")
public class ReceptionistPatientsListServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    private PatientDAO patientDAO;



    @Override
    public void init() throws ServletException {


    	patientDAO = new PatientDAO();

    }



    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


    	
        String action =
                request.getParameter("action");

        
        // ==========================================
        // DELETE
        // ==========================================

        if ("delete".equals(action)) {


            String idValue =
                    request.getParameter("patientId");


            if (idValue == null ||
                idValue.trim().isEmpty()) {

                setMessage(
                        request,
                        "Invalid unavailability ID.",
                        "error"
                );

                redirectBack(request, response);

                return;

            }


            try {

                int id =
                        Integer.parseInt(idValue);


                boolean deleted =
                        patientDAO.deletePatientById(id);


                if (deleted) {

                    setMessage(
                            request,
                            "Patient removed successfully.",
                            "success"
                    );

                }
                else {

                    setMessage(
                            request,
                            "Failed to remove patient.",
                            "error"
                    );

                }


            }
            catch (NumberFormatException e) {

                setMessage(
                        request,
                        "Invalid unavailability ID.",
                        "error"
                );

            }


            redirectBack(request, response);

            return; 

        }
        
        
        
        // ==========================================
        // Edit (Show Form)
        // ==========================================

        if ("edit".equals(action)) {

            String idValue = request.getParameter("patientId");

            if (idValue != null && !idValue.trim().isEmpty()) {
                try {
                    int patientId = Integer.parseInt(idValue);
                    Patient patient = patientDAO.getPatientById(patientId);
                    
                    if (patient != null) {
                        request.setAttribute("patient", patient);
                        request.getRequestDispatcher("/receptionist/patients/edit.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    // Ignore and let it fall through to the error message below
                }
            }

            setMessage(request, "Invalid patient ID or patient not found.", "error");
            redirectBack(request, response);
            return;
        }
        
        
        
        // ==========================================
        // Add (Show Form)
        // ==========================================

        if ("add".equals(action)) {
            request.getRequestDispatcher("/receptionist/patients/register.jsp").forward(request, response);
            return;
        }
        
        
        
        // ==========================================
        // Default
        // ==========================================

        
        // Search keyword

        String keyword =
                request.getParameter("search");



        if(keyword == null){

            keyword = "";

        }





        // Sorting option

        String sortBy =
                request.getParameter("sort");



        if(sortBy == null){

            sortBy = "newest";

        }


        // Get patients list

        List<Patient> patients =
                patientDAO.searchPatients(keyword, sortBy);


        // Send data to JSP

        request.setAttribute(
                "patients",
                patients
        );


        request.getRequestDispatcher(
                "/receptionist/patients/list.jsp"
        )
        .forward(request, response);



    }
    
    
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {

            try {
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String phoneStr = request.getParameter("phone");
                String gender = request.getParameter("gender");
                String dobStr = request.getParameter("dob");
                String address = request.getParameter("address");

                // Validation
                if (fullName == null || fullName.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    phoneStr == null || phoneStr.trim().isEmpty() ||
                    gender == null || gender.trim().isEmpty() ||
                    dobStr == null || dobStr.trim().isEmpty() ||
                    address == null || address.trim().isEmpty()) {
                    
                    setMessage(request, "All fields are required.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=edit&patientId=" + patientId);
                    return;
                }

                if (!phoneStr.matches("^[1-9]\\d{8}$")) {
                    setMessage(request, "Phone number must be exactly 9 digits and cannot start with 0.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=edit&patientId=" + patientId);
                    return;
                }

                int phone = Integer.parseInt(phoneStr);
                Date dob = Date.valueOf(dobStr);

                if (dob.after(new java.sql.Date(System.currentTimeMillis()))) {
                    setMessage(request, "Date of Birth cannot be in the future.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=edit&patientId=" + patientId);
                    return;
                }

                // Check duplicates (excluding current patient)
                if (patientDAO.emailExistsExceptCurrent(email, patientId)) {
                    setMessage(request, "Email already exists for another patient.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=edit&patientId=" + patientId);
                    return;
                }

                if (patientDAO.phoneExistsExceptCurrent(phone, patientId)) {
                    setMessage(request, "Phone number already exists for another patient.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=edit&patientId=" + patientId);
                    return;
                }

                // Retrieve existing patient to preserve registeredAt
                Patient existingPatient = patientDAO.getPatientById(patientId);
                if (existingPatient == null) {
                    setMessage(request, "Patient not found.", "error");
                    redirectBack(request, response);
                    return;
                }

                Patient updatedPatient = new Patient(
                        patientId,
                        fullName,
                        email,
                        phone,
                        gender,
                        dob,
                        address,
                        existingPatient.getRegisteredAt()
                );

                boolean success = patientDAO.updatePatient(updatedPatient);

                if (success) {
                    setMessage(request, "Patient details updated successfully.", "success");
                    redirectBack(request, response);
                } else {
                    setMessage(request, "Failed to update patient details. Please try again.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=edit&patientId=" + patientId);
                }

            } catch (Exception e) {
                e.printStackTrace();
                setMessage(request, "An unexpected error occurred. Please verify inputs.", "error");
                redirectBack(request, response);
            }
            
            return;
        }
        
        
        // ==========================================
        // Add (Process Form)
        // ==========================================

        if ("add".equals(action)) {

            try {
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String phoneStr = request.getParameter("phone");
                String gender = request.getParameter("gender");
                String dobStr = request.getParameter("dob");
                String address = request.getParameter("address");

                // Validation
                if (fullName == null || fullName.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    phoneStr == null || phoneStr.trim().isEmpty() ||
                    gender == null || gender.trim().isEmpty() ||
                    dobStr == null || dobStr.trim().isEmpty() ||
                    address == null || address.trim().isEmpty()) {
                    
                    setMessage(request, "All fields are required.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=add");
                    return;
                }

                if (!phoneStr.matches("^[1-9]\\d{8}$")) {
                    setMessage(request, "Phone number must be exactly 9 digits and cannot start with 0.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=add");
                    return;
                }

                int phone = Integer.parseInt(phoneStr);
                Date dob = Date.valueOf(dobStr);

                if (dob.after(new java.sql.Date(System.currentTimeMillis()))) {
                    setMessage(request, "Date of Birth cannot be in the future.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=add");
                    return;
                }

                // Check duplicates 
                if (patientDAO.emailExists(email)) {
                    setMessage(request, "Email already exists.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=add");
                    return;
                }

                if (patientDAO.phoneExists(phone)) {
                    setMessage(request, "Phone number already exists.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=add");
                    return;
                }

                Patient newPatient = new Patient(
                        0,
                        fullName,
                        email,
                        phone,
                        gender,
                        dob,
                        address,
                        null
                );

                boolean success = patientDAO.addPatient(newPatient);

                if (success) {
                    setMessage(request, "Patient registered successfully.", "success");
                    redirectBack(request, response);
                } else {
                    setMessage(request, "Failed to register patient. Please try again.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=add");
                }

            } catch (Exception e) {
                e.printStackTrace();
                setMessage(request, "An unexpected error occurred. Please verify inputs.", "error");
                response.sendRedirect(request.getContextPath() + "/receptionist/patients?action=add");
            }
            
            return;
        }

        redirectBack(request, response);
    }
    
    
    

    // =====================================================
    // Redirect helpers
    // =====================================================

    private void redirectBack(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        response.sendRedirect(
                request.getContextPath()
                +
                "/receptionist/patients"
        );

    }

    private void setMessage(
            HttpServletRequest request,
            String message,
            String type) {


        request.getSession().setAttribute(
                "message",
                message
        );


        request.getSession().setAttribute(
                "messageType",
                type
        );

    }


}