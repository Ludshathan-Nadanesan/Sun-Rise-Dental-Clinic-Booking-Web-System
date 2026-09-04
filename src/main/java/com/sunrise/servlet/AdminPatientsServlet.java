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
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/patients")
public class AdminPatientsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        String action = request.getParameter("action");

        // Edit (Show Form)
        if ("edit".equals(action)) {
            String idValue = request.getParameter("patientId");
            if (idValue != null && !idValue.trim().isEmpty()) {
                try {
                    int patientId = Integer.parseInt(idValue);
                    Patient patient = patientDAO.getPatientById(patientId);
                    
                    if (patient != null) {
                        request.setAttribute("patient", patient);
                        request.getRequestDispatcher("/admin/patients/edit.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {}
            }
            setMessage(request, "Invalid patient ID or patient not found.", "error");
            redirectBack(request, response);
            return;
        }
        
        // List Default
        String keyword = request.getParameter("search");
        if(keyword == null){ keyword = ""; }

        String sortBy = request.getParameter("sort");
        if(sortBy == null){ sortBy = "newest"; }

        List<Patient> patients = patientDAO.searchPatients(keyword, sortBy);
        request.setAttribute("patients", patients);
        request.getRequestDispatcher("/admin/patients/list.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

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

                if (fullName == null || fullName.trim().isEmpty() || email == null || email.trim().isEmpty() ||
                    phoneStr == null || phoneStr.trim().isEmpty() || gender == null || gender.trim().isEmpty() ||
                    dobStr == null || dobStr.trim().isEmpty() || address == null || address.trim().isEmpty()) {
                    setMessage(request, "All fields are required.", "error");
                    response.sendRedirect(request.getContextPath() + "/admin/patients?action=edit&patientId=" + patientId);
                    return;
                }

                if (!phoneStr.matches("^[1-9]\\d{8}$")) {
                    setMessage(request, "Phone number must be exactly 9 digits and cannot start with 0.", "error");
                    response.sendRedirect(request.getContextPath() + "/admin/patients?action=edit&patientId=" + patientId);
                    return;
                }

                int phone = Integer.parseInt(phoneStr);
                Date dob = Date.valueOf(dobStr);

                if (dob.after(new java.sql.Date(System.currentTimeMillis()))) {
                    setMessage(request, "Date of Birth cannot be in the future.", "error");
                    response.sendRedirect(request.getContextPath() + "/admin/patients?action=edit&patientId=" + patientId);
                    return;
                }

                if (patientDAO.emailExistsExceptCurrent(email, patientId)) {
                    setMessage(request, "Email already exists for another patient.", "error");
                    response.sendRedirect(request.getContextPath() + "/admin/patients?action=edit&patientId=" + patientId);
                    return;
                }

                if (patientDAO.phoneExistsExceptCurrent(phone, patientId)) {
                    setMessage(request, "Phone number already exists for another patient.", "error");
                    response.sendRedirect(request.getContextPath() + "/admin/patients?action=edit&patientId=" + patientId);
                    return;
                }

                Patient existingPatient = patientDAO.getPatientById(patientId);
                if (existingPatient == null) {
                    setMessage(request, "Patient not found.", "error");
                    redirectBack(request, response);
                    return;
                }

                Patient updatedPatient = new Patient(
                        patientId, fullName, email, phone, gender, dob, address, existingPatient.getRegisteredAt()
                );

                boolean success = patientDAO.updatePatient(updatedPatient);

                if (success) {
                    setMessage(request, "Patient details updated successfully.", "success");
                    redirectBack(request, response);
                } else {
                    setMessage(request, "Failed to update patient details. Please try again.", "error");
                    response.sendRedirect(request.getContextPath() + "/admin/patients?action=edit&patientId=" + patientId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                setMessage(request, "An unexpected error occurred. Please verify inputs.", "error");
                redirectBack(request, response);
            }
            return;
        }

        redirectBack(request, response);
    }
    
    private void redirectBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/admin/patients");
    }

    private void setMessage(HttpServletRequest request, String message, String type) {
        request.getSession().setAttribute("message", message);
        request.getSession().setAttribute("messageType", type);
    }
}
