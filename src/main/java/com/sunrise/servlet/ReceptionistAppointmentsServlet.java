package com.sunrise.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.sunrise.dao.AppointmentDAO;
import com.sunrise.dao.DentistDAO;
import com.sunrise.dao.PatientDAO;
import com.sunrise.dao.TreatmentDAO;
import com.sunrise.model.Appointment;
import com.sunrise.model.Dentist;
import com.sunrise.model.Patient;
import com.sunrise.model.Treatments;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/receptionist/appointments")
public class ReceptionistAppointmentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    private DentistDAO dentistDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
        patientDAO = new PatientDAO();
        dentistDAO = new DentistDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // ==========================================
        // Add (Show Form)
        // ==========================================
        if ("add".equals(action)) {
            // Get all patients and dentists for the dropdowns
            List<Patient> patients = patientDAO.searchPatients("", null);
            List<Dentist> dentists = dentistDAO.searchDentists("", null);
            
            request.setAttribute("patients", patients);
            request.setAttribute("dentists", dentists);
            
            request.getRequestDispatcher("/receptionist/appointments/book.jsp").forward(request, response);
            return;
        }

        // ==========================================
        // Default (List Appointments)
        // ==========================================
        
        String keyword = request.getParameter("search");
        if (keyword == null) { keyword = ""; }
        
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        String sortBy = request.getParameter("sort");
        if (sortBy == null) { sortBy = "newest"; }
        
        List<Appointment> appointments = appointmentDAO.searchAppointments(keyword, startDate, endDate, sortBy);
        
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/receptionist/appointments/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // ==========================================
        // Add (Process Form)
        // ==========================================
        if ("add".equals(action)) {
            try {
            	String treatmeIdStr = request.getParameter("treatmentId");
                String patientIdStr = request.getParameter("patientId");
                String dentistIdStr = request.getParameter("dentistId");
                String appointmentStartDateTimeStr = request.getParameter("appointmentStartDateTime");

                // Validation
                if (
                	treatmeIdStr == null || treatmeIdStr.trim().isEmpty() ||
                	patientIdStr == null || patientIdStr.trim().isEmpty() ||
                    dentistIdStr == null || dentistIdStr.trim().isEmpty() ||
                    appointmentStartDateTimeStr == null || appointmentStartDateTimeStr.trim().isEmpty()) {
                    
                    setMessage(request, "All fields are required.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/appointments?action=add");
                    return;
                }

                int patientId = Integer.parseInt(patientIdStr);
                int dentistId = Integer.parseInt(dentistIdStr);
                int treatmentId = Integer.parseInt(treatmeIdStr);

                // Parase Start DateTime
                // Assuming datetime format from HTML5 input type="datetime-local" is yyyy-MM-dd'T'HH:mm
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                java.util.Date parsedDate = sdf.parse(appointmentStartDateTimeStr);
                Timestamp startDateTime = new Timestamp(parsedDate.getTime());
                
                // Fetch treatment duration using treatment Dao
                TreatmentDAO treatmentDAO = new TreatmentDAO();
                Treatments treatment = treatmentDAO.getTreatmentById(treatmentId);
                int estimatedDurationMinutes = treatment.getEstimatedDuration(); // e.g., 40 mins

                // Calculate End DateTime (Start Time + Duration)
                long durationInMillis = estimatedDurationMinutes * 60 * 1000L;
                Timestamp endDateTime = new Timestamp(startDateTime.getTime() + durationInMillis);
                
                // Create Appointment Object with BOTH start_date_time and end_date_time
                Appointment appointment = new Appointment(0, patientId, dentistId, startDateTime, endDateTime, "scheduled", null, null);
                appointment.setTreatmentId(treatmentId);
      

                boolean success = appointmentDAO.addAppointment(appointment);

                if (success) {
                    setMessage(request, "Appointment booked successfully.", "success");
                    redirectBack(request, response);
                } else {
                    setMessage(request, "Failed to book appointment. Please try again.", "error");
                    response.sendRedirect(request.getContextPath() + "/receptionist/appointments?action=add");
                }

            } catch (ParseException e) {
                e.printStackTrace();
                setMessage(request, "Invalid date/time format.", "error");
                response.sendRedirect(request.getContextPath() + "/receptionist/appointments?action=add");
            } catch (Exception e) {
                e.printStackTrace();
                setMessage(request, "An unexpected error occurred. Please verify inputs.", "error");
                response.sendRedirect(request.getContextPath() + "/receptionist/appointments?action=add");
            }
            return;
        }

        redirectBack(request, response);
    }

    // =====================================================
    // Redirect helpers
    // =====================================================

    private void redirectBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/receptionist/appointments");
    }

    private void setMessage(HttpServletRequest request, String message, String type) {
        request.getSession().setAttribute("message", message);
        request.getSession().setAttribute("messageType", type);
    }
}


