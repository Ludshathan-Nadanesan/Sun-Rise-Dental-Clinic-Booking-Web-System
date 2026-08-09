package com.sunrise.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.sunrise.dao.DentistDAO;
import com.sunrise.dao.DentistUnavailabilityDAO;
import com.sunrise.model.Dentist;
import com.sunrise.model.DentistUnavailability;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/dentists/unavailability")
public class DentistUnavailabilityServlet
        extends HttpServlet {


    private static final long serialVersionUID = 1L;


    private DentistUnavailabilityDAO dao;
    private DentistDAO dentistDAO;


    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd'T'HH:mm"
            );


    @Override
    public void init() throws ServletException {

        dao = new DentistUnavailabilityDAO();
        dentistDAO = new DentistDAO();

    }



    // =====================================================
    // GET
    // =====================================================

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
                    request.getParameter("id");


            if (idValue == null ||
                idValue.trim().isEmpty()) {

                setMessage(
                        request,
                        "Invalid unavailability ID.",
                        "error"
                );

                redirectToList(request, response);

                return;

            }


            try {

                int id =
                        Integer.parseInt(idValue);


                boolean deleted =
                        dao.deleteUnavailability(id);


                if (deleted) {

                    setMessage(
                            request,
                            "Unavailability removed successfully.",
                            "success"
                    );

                }
                else {

                    setMessage(
                            request,
                            "Failed to remove unavailability.",
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


            redirectToList(request, response);

            return;

        }



//        // ==========================================
//        // EDIT
//        // ==========================================
//
//        if ("edit".equals(action)) {
//
//
//            String idValue =
//                    request.getParameter("id");
//
//
//            try {
//
//                int id =
//                        Integer.parseInt(idValue);
//
//
//                DentistUnavailability du =
//                        dao.getById(id);
//
//
//                if (du == null) {
//
//                    setMessage(
//                            request,
//                            "Unavailability record not found.",
//                            "error"
//                    );
//
//                    redirectToList(request, response);
//
//                    return;
//
//                }
//
//
//                request.setAttribute(
//                        "unavailability",
//                        du
//                );
//
//
//                request.getRequestDispatcher(
//                        "/admin/dentists/edit-unavailability.jsp"
//                ).forward(request, response);
//
//
//            }
//            catch (Exception e) {
//
//                e.printStackTrace();
//
//                setMessage(
//                        request,
//                        "Unable to load record.",
//                        "error"
//                );
//
//                redirectToList(request, response);
//
//            }
//
//
//            return;
//
//        }



        // ==========================================
        // DEFAULT
        // ==========================================

        String dentistIdValue =
                request.getParameter("dentistId");


        if (dentistIdValue == null ||
            dentistIdValue.trim().isEmpty()) {


            setMessage(
                    request,
                    "Dentist ID is required.",
                    "error"
            );


            redirectToDentists(request, response);

            return;

        }


        try {

            int dentistId =
                    Integer.parseInt(dentistIdValue);
            
            Dentist dentist = dentistDAO.getDentistById(dentistId);

            request.setAttribute(
                "dentistName",
                dentist.getFullName()
            );


            request.setAttribute(
                    "dentistId",
                    dentistId
            );


            request.setAttribute(
                    "unavailabilityList",
                    dao.getByDentistId(dentistId)
            );


            request.getRequestDispatcher(
                    "/admin/dentists/unavailability.jsp"
            ).forward(request, response);


        }
        catch (NumberFormatException e) {

            setMessage(
                    request,
                    "Invalid dentist ID.",
                    "error"
            );


            redirectToDentists(request, response);

        }

    }



    // =====================================================
    // POST
    // =====================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        request.setCharacterEncoding("UTF-8");


        String action =
                request.getParameter("action");


        String dentistIdValue =
                request.getParameter("dentistId");


        String startValue =
                request.getParameter("startDatetime");


        String endValue =
                request.getParameter("endDatetime");


        String reason =
                request.getParameter("reason");


        // ==========================================
        // Dentist ID validation
        // ==========================================

        int dentistId;


        try {

            dentistId =
                    Integer.parseInt(dentistIdValue);

        }
        catch (Exception e) {

            setMessage(
                    request,
                    "Invalid dentist ID.",
                    "error"
            );

            redirectToDentists(request, response);

            return;

        }



        // ==========================================
        // Start / End required
        // ==========================================

        if (startValue == null ||
            startValue.trim().isEmpty()) {


            setMessage(
                    request,
                    "Start date and time is required.",
                    "error"
            );


            redirectBack(request, response, dentistId);

            return;

        }


        if (endValue == null ||
            endValue.trim().isEmpty()) {


            setMessage(
                    request,
                    "End date and time is required.",
                    "error"
            );


            redirectBack(request, response, dentistId);

            return;

        }



        // ==========================================
        // Parse datetime
        // ==========================================

        LocalDateTime startDatetime;
        LocalDateTime endDatetime;


        try {

            startDatetime =
                    LocalDateTime.parse(
                            startValue,
                            formatter
                    );


            endDatetime =
                    LocalDateTime.parse(
                            endValue,
                            formatter
                    );

        }
        catch (DateTimeParseException e) {

            setMessage(
                    request,
                    "Invalid date and time format.",
                    "error"
            );


            redirectBack(
                    request,
                    response,
                    dentistId
            );

            return;

        }



        // ==========================================
        // Start < End validation
        // ==========================================

        if (!endDatetime.isAfter(startDatetime)) {


            setMessage(
                    request,
                    "End date and time must be after start date and time.",
                    "error"
            );


            redirectBack(
                    request,
                    response,
                    dentistId
            );

            return;

        }



        // ==========================================
        // Reason validation
        // ==========================================

        if (reason == null ||
            reason.trim().isEmpty()) {


            setMessage(
                    request,
                    "Please enter a reason.",
                    "error"
            );


            redirectBack(
                    request,
                    response,
                    dentistId
            );

            return;

        }


        reason = reason.trim();


        if (reason.length() < 3) {


            setMessage(
                    request,
                    "Reason must contain at least 3 characters.",
                    "error"
            );


            redirectBack(
                    request,
                    response,
                    dentistId
            );

            return;

        }


        if (reason.length() > 500) {


            setMessage(
                    request,
                    "Reason cannot exceed 500 characters.",
                    "error"
            );


            redirectBack(
                    request,
                    response,
                    dentistId
            );

            return;

        }



        // ==========================================
        // UPDATE
        // ==========================================

        if ("update".equals(action)) {


            String idValue =
                    request.getParameter("id");


            int id;


            try {

                id =
                        Integer.parseInt(idValue);

            }
            catch (Exception e) {

                setMessage(
                        request,
                        "Invalid unavailability ID.",
                        "error"
                );


                redirectBack(
                        request,
                        response,
                        dentistId
                );

                return;

            }


            DentistUnavailability du =
                    new DentistUnavailability();


            du.setUnavailabilityId(id);

            du.setDentistId(dentistId);

            du.setStartDatetime(startDatetime);

            du.setEndDatetime(endDatetime);

            du.setReason(reason);


            boolean updated =
                    dao.updateUnavailability(du);


            if (updated) {

                setMessage(
                        request,
                        "Unavailability updated successfully.",
                        "success"
                );

            }
            else {

                setMessage(
                        request,
                        "Failed to update unavailability.",
                        "error"
                );

            }


            redirectBack(
                    request,
                    response,
                    dentistId
            );

            return;

        }



        // ==========================================
        // ADD
        // ==========================================

        DentistUnavailability du =
                new DentistUnavailability();


        du.setDentistId(dentistId);

        du.setStartDatetime(startDatetime);

        du.setEndDatetime(endDatetime);

        du.setReason(reason);


        boolean added =
                dao.addUnavailability(du);


        if (added) {

            setMessage(
                    request,
                    "Dentist unavailability added successfully.",
                    "success"
            );

        }
        else {

            setMessage(
                    request,
                    "Failed to add unavailability.",
                    "error"
            );

        }


        redirectBack(
                request,
                response,
                dentistId
        );

    }



    // =====================================================
    // Redirect helpers
    // =====================================================

    private void redirectBack(
            HttpServletRequest request,
            HttpServletResponse response,
            int dentistId)
            throws IOException {


        response.sendRedirect(
                request.getContextPath()
                +
                "/admin/dentists/unavailability?dentistId="
                +
                dentistId
        );

    }



    private void redirectToList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        String dentistId =
                request.getParameter("dentistId");


        if (dentistId == null) {

            redirectToDentists(request, response);

            return;

        }


        response.sendRedirect(
                request.getContextPath()
                +
                "/admin/dentists/unavailability?dentistId="
                +
                dentistId
        );

    }



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