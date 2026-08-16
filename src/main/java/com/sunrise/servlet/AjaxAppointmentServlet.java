package com.sunrise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.sunrise.dao.AppointmentDAO;
import com.sunrise.dao.DentistAvailabilityDAO;
import com.sunrise.dao.DentistTreatmentDAO;
import com.sunrise.dao.DentistUnavailabilityDAO;
import com.sunrise.dao.TreatmentDAO;
import com.sunrise.model.Appointment;
import com.sunrise.model.DentistAvailability;
import com.sunrise.model.DentistTreatment;
import com.sunrise.model.DentistUnavailability;
import com.sunrise.model.Treatments;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/appointments/booking-data")
public class AjaxAppointmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DentistTreatmentDAO dentistTreatmentDAO;
    private DentistAvailabilityDAO dentistAvailabilityDAO;
    private DentistUnavailabilityDAO dentistUnavailabilityDAO;
    private AppointmentDAO appointmentDAO;
    private TreatmentDAO treatmentDAO;

    @Override
    public void init() throws ServletException {
        dentistTreatmentDAO = new DentistTreatmentDAO();
        dentistAvailabilityDAO = new DentistAvailabilityDAO();
        dentistUnavailabilityDAO = new DentistUnavailabilityDAO();
        appointmentDAO = new AppointmentDAO();
        treatmentDAO = new TreatmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
        	//  populate assigned treatments by dentist id
        	
            if ("treatments".equals(action)) {
                int dentistId = Integer.parseInt(request.getParameter("dentistId"));
                List<DentistTreatment> treatments = dentistTreatmentDAO.getAllDentistTreatmentsByDentistId(dentistId);
                
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                for (DentistTreatment dt : treatments) {
                    arrayBuilder.add(Json.createObjectBuilder()
                        .add("treatmentId", dt.getTreatmentId())
                        .add("treatmentName", dt.getTreatmentName())
                        .add("defaultFee", dt.getDefaultFee())
                        .add("estDur", dt.getEstimatedDuration())
                    );
                }
                out.print(arrayBuilder.build().toString());
                
            } 
            
            // validate & populate slots by dentist id
            
            else if ("slots".equals(action)) {
                int dentistId = Integer.parseInt(request.getParameter("dentistId"));
                int treatmentId = Integer.parseInt(request.getParameter("treatmentId")); // 1. Added Treatment ID
                String dateStr = request.getParameter("date");
                LocalDate selectedDate = LocalDate.parse(dateStr);
                
                // 2. Fetch Treatment Duration from DB/DAO
                Treatments treatment = treatmentDAO.getTreatmentById(treatmentId);
                int estimatedDuration = treatment.getEstimatedDuration(); // e.g., 40 minutes
                
                DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();
                String dayName = dayOfWeek.name().substring(0, 1).toUpperCase() + dayOfWeek.name().substring(1).toLowerCase();
                
                List<DentistAvailability> allAvails = dentistAvailabilityDAO.getAvailabilityByDentistId(dentistId);
                DentistAvailability todayAvail = null;
                for (DentistAvailability da : allAvails) {
                    if (da.getDayOfWeek().equalsIgnoreCase(dayName)) {
                        todayAvail = da;
                        break;
                    }
                }
                
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                
                if (todayAvail == null) {
                    out.print(arrayBuilder.build().toString());
                    return;
                }
                
                LocalTime startTime = todayAvail.getStartTime().toLocalTime();
                LocalTime endTime = todayAvail.getEndTime().toLocalTime();
                
                List<LocalTime> possibleSlots = new ArrayList<>();
                LocalTime current = startTime;
                // Slots are still generated at 15-minute intervals
                while (current.plusMinutes(15).isBefore(endTime) || current.plusMinutes(15).equals(endTime)) {
                    possibleSlots.add(current);
                    current = current.plusMinutes(15);
                }
                
                List<DentistUnavailability> unavailabilities = dentistUnavailabilityDAO.getByDentistId(dentistId);
                List<Appointment> existingApps = appointmentDAO.getAppointmentsByDentistAndDate(dentistId, dateStr);
                
                for (LocalTime slot : possibleSlots) {
                    LocalDateTime slotStart = LocalDateTime.of(selectedDate, slot);
                    
                    // 3. Dynamic End Time based on Treatment Duration!
                    LocalDateTime slotEnd = slotStart.plusMinutes(estimatedDuration);
                    
                                        String status = "available";
                    
                    LocalDateTime workEndTime = LocalDateTime.of(selectedDate, endTime);
                    if (slotEnd.isAfter(workEndTime)) {
                        status = "duration_overflow";
                    }
                    
                    for (DentistUnavailability du : unavailabilities) {
                        LocalDateTime breakStart = du.getStartDatetime();
                        LocalDateTime breakEnd = du.getEndDatetime();
                        
                        if (slotStart.isBefore(breakEnd) && slotEnd.isAfter(breakStart)) {
                            if (!slotStart.isBefore(breakStart)) {
                                status = "unavailable";
                            } else {
                                if (!status.equals("unavailable") && !status.equals("booked")) {
                                    status = "duration_overflow";
                                }
                            }
                        }
                    }
                    
                    for (Appointment app : existingApps) {
                        if ("cancelled".equalsIgnoreCase(app.getStatus())) continue;
                        
                        LocalDateTime appStart = app.getAppointmentStartDateTime().toLocalDateTime();
                        LocalDateTime appEnd = app.getAppointmentEndDateTime().toLocalDateTime();
                        
                        if (slotStart.isBefore(appEnd) && slotEnd.isAfter(appStart)) {
                            if (!slotStart.isBefore(appStart)) {
                                status = "booked";
                            } else {
                                if (!status.equals("unavailable") && !status.equals("booked")) {
                                    status = "duration_overflow";
                                }
                            }
                        }
                    }
                    
                    if (slotStart.isBefore(LocalDateTime.now())) {
                        status = "past";
                    }
                    
                    arrayBuilder.add(Json.createObjectBuilder()
                        .add("time", slot.format(DateTimeFormatter.ofPattern("HH:mm")))
                        .add("status", status)
                    );
                }
                
                out.print(arrayBuilder.build().toString());
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(Json.createObjectBuilder().add("error", e.getMessage()).build().toString());
        } finally {
            out.flush();
        }
    }
}


