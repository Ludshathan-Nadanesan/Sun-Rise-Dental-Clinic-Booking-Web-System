package com.sunrise.servlet;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

import com.sunrise.dao.DentistAvailabilityDAO;
import com.sunrise.dao.DentistDAO;
import com.sunrise.model.Dentist;
import com.sunrise.model.DentistAvailability;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/dentists/availability")
public class DentistAvailabilityServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    private DentistAvailabilityDAO availabilityDAO;

    private DentistDAO dentistDAO;


    @Override
    public void init() throws ServletException {

        availabilityDAO =
                new DentistAvailabilityDAO();

        dentistDAO =
                new DentistDAO();

    }



    // =====================================================
    // GET
    // =====================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String dentistIdValue =
                request.getParameter("dentistId");


        if (dentistIdValue == null ||
            dentistIdValue.trim().isEmpty()) {


            setMessage(
                    request,
                    "Dentist ID is required.",
                    "error"
            );


            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/dentists"
            );


            return;
        }


        int dentistId;


        try {

            dentistId =
                    Integer.parseInt(
                            dentistIdValue
                    );

        }
        catch (NumberFormatException e) {


            setMessage(
                    request,
                    "Invalid dentist ID.",
                    "error"
            );


            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/dentists"
            );


            return;
        }



        // =================================================
        // DELETE
        // =================================================

        String action =
                request.getParameter("action");


        if ("delete".equals(action)) {


            String availabilityIdValue =
                    request.getParameter(
                            "availabilityId"
                    );


            if (availabilityIdValue == null) {


                setMessage(
                        request,
                        "Invalid availability.",
                        "error"
                );


                redirectToAvailability(
                        request,
                        response,
                        dentistId
                );


                return;
            }


            int availabilityId;


            try {

                availabilityId =
                        Integer.parseInt(
                                availabilityIdValue
                        );

            }
            catch (NumberFormatException e) {


                setMessage(
                        request,
                        "Invalid availability ID.",
                        "error"
                );


                redirectToAvailability(
                        request,
                        response,
                        dentistId
                );


                return;
            }


            boolean deleted =
                    availabilityDAO
                    .deleteAvailability(
                            availabilityId
                    );


            if (deleted) {

                setMessage(
                        request,
                        "Availability removed successfully.",
                        "success"
                );

            }
            else {

                setMessage(
                        request,
                        "Failed to remove availability.",
                        "error"
                );

            }


            redirectToAvailability(
                    request,
                    response,
                    dentistId
            );


            return;
        }



        // =================================================
        // GET PAGE
        // =================================================


        Dentist dentist =
                dentistDAO.getDentistById(
                        dentistId
                );


        if (dentist == null) {


            setMessage(
                    request,
                    "Dentist not found.",
                    "error"
            );


            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/dentists"
            );


            return;
        }



        List<DentistAvailability> availabilityList =
                availabilityDAO
                .getAvailabilityByDentistId(
                        dentistId
                );


        request.setAttribute(
                "dentist",
                dentist
        );


        request.setAttribute(
                "availabilityList",
                availabilityList
        );


        request.getRequestDispatcher(
                "/admin/dentists/availability.jsp"
        ).forward(
                request,
                response
        );

    }



    // =====================================================
    // POST
    // =====================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        String dentistIdValue =
                request.getParameter("dentistId");


        String dayOfWeek =
                request.getParameter("dayOfWeek");


        String startTimeValue =
                request.getParameter("startTime");


        String endTimeValue =
                request.getParameter("endTime");



        // =================================================
        // Dentist ID validation
        // =================================================

        int dentistId;


        try {

            dentistId =
                    Integer.parseInt(
                            dentistIdValue
                    );

        }
        catch (Exception e) {

            setMessage(
                    request,
                    "Invalid dentist ID.",
                    "error"
            );


            redirectToDentists(
                    request,
                    response
            );


            return;
        }



        // =================================================
        // Day validation
        // =================================================

        if (dayOfWeek == null ||
            dayOfWeek.trim().isEmpty()) {


            setMessage(
                    request,
                    "Please select a day.",
                    "error"
            );


            redirectToAvailability(
                    request,
                    response,
                    dentistId
            );


            return;
        }



        // =================================================
        // Time validation
        // =================================================

        if (startTimeValue == null ||
            startTimeValue.trim().isEmpty() ||
            endTimeValue == null ||
            endTimeValue.trim().isEmpty()) {


            setMessage(
                    request,
                    "Start time and end time are required.",
                    "error"
            );


            redirectToAvailability(
                    request,
                    response,
                    dentistId
            );


            return;
        }



        Time startTime;

        Time endTime;


        try {
        	
            startTime =
                    Time.valueOf(
                            startTimeValue + ":00"
                    );


            endTime =
                    Time.valueOf(
                            endTimeValue + ":00"
                    );

        }
        catch (IllegalArgumentException e) {


            setMessage(
                    request,
                    "Invalid time format.",
                    "error"
            );


            redirectToAvailability(
                    request,
                    response,
                    dentistId
            );


            return;
        }



        // =================================================
        // Start time must be before end time
        // =================================================

        if (!startTime.before(endTime)) {


            setMessage(
                    request,
                    "Start time must be earlier than end time.",
                    "error"
            );


            redirectToAvailability(
                    request,
                    response,
                    dentistId
            );


            return;
        }



        // =================================================
        // Check duplicate day
        // =================================================

        if (availabilityDAO.availabilityExists(
                dentistId,
                dayOfWeek)) {


            setMessage(
                    request,
                    "Availability already exists for "
                    + dayOfWeek
                    + ".",
                    "error"
            );


            redirectToAvailability(
                    request,
                    response,
                    dentistId
            );


            return;
        }



        // =================================================
        // Create object
        // =================================================

        DentistAvailability availability =
                new DentistAvailability();


        availability.setDentistId(
                dentistId
        );


        availability.setDayOfWeek(
                dayOfWeek
        );


        availability.setStartTime(
                startTime
        );


        availability.setEndTime(
                endTime
        );



        // =================================================
        // Save
        // =================================================

        boolean saved =
                availabilityDAO
                .addAvailability(
                        availability
                );


        if (saved) {


            setMessage(
                    request,
                    "Availability added successfully.",
                    "success"
            );

        }
        else {


            setMessage(
                    request,
                    "Failed to add availability.",
                    "error"
            );

        }



        redirectToAvailability(
                request,
                response,
                dentistId
        );

    }



    // =====================================================
    // Redirect to availability
    // =====================================================

    private void redirectToAvailability(
            HttpServletRequest request,
            HttpServletResponse response,
            int dentistId)
            throws IOException {


        response.sendRedirect(
                request.getContextPath()
                +
                "/admin/dentists/availability?dentistId="
                +
                dentistId
        );

    }



    // =====================================================
    // Redirect dentists
    // =====================================================

    private void redirectToDentists(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        response.sendRedirect(
                request.getContextPath()
                +
                "/admin/dentists"
        );

    }



    // =====================================================
    // Message
    // =====================================================

    private void setMessage(
            HttpServletRequest request,
            String message,
            String type) {


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