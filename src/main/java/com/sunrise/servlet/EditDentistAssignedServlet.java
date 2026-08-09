package com.sunrise.servlet;

import java.io.IOException;

import com.sunrise.dao.DentistDAO;
import com.sunrise.dao.DentistTreatmentDAO;
import com.sunrise.model.Dentist;
import com.sunrise.model.DentistTreatment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/dentists/edit-assigned-treatment")
public class EditDentistAssignedServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DentistTreatmentDAO dtDao;
    private DentistDAO dDao;

    @Override
    public void init() throws ServletException {

        dtDao = new DentistTreatmentDAO();
        dDao = new DentistDAO();

    }

    // =====================================================
    // OPEN EDIT PAGE
    // =====================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idValue = request.getParameter("id");
        String dIdValue = request.getParameter("dId");

        // Validate ID parameters
        if (idValue == null || dIdValue == null) {

            setMessage(
                    request,
                    "Invalid dentist treatment information.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }

        int dtId;
        int dentistId;

        try {

            dtId = Integer.parseInt(idValue);
            dentistId = Integer.parseInt(dIdValue);

        }
        catch (NumberFormatException e) {

            setMessage(
                    request,
                    "Invalid ID format.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }

        // Get assigned treatment
        DentistTreatment dt =
                dtDao.getDentistAssignedTreatmentById(dtId);

        if (dt == null) {

            setMessage(
                    request,
                    "Assigned treatment not found.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }

        // Get dentist
        Dentist dentist =
                dDao.getDentistById(dentistId);

        if (dentist == null) {

            setMessage(
                    request,
                    "Dentist not found.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }

        // Make sure the treatment actually belongs
        // to the requested dentist
        if (dt.getDentistId() != dentistId) {

            setMessage(
                    request,
                    "Invalid dentist treatment assignment.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }

        request.setAttribute(
                "dentistTreatment",
                dt
        );

        request.setAttribute(
                "dentist",
                dentist
        );

        request.getRequestDispatcher(
                "/admin/dentists/edit-assigned-treatment.jsp"
        ).forward(request, response);

    }

    // =====================================================
    // UPDATE COMMISSION
    // =====================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idValue =
                request.getParameter("id");

        String dentistIdValue =
                request.getParameter("dentistId");

        String commissionValue =
                request.getParameter("commission");
        

        // =================================================
        // ID VALIDATION
        // =================================================

        if (idValue == null || dentistIdValue == null) {

            setMessage(
                    request,
                    "Invalid treatment assignment.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }


        int id;
        int dentistId;


        try {

            id =
                    Integer.parseInt(idValue);

            dentistId =
                    Integer.parseInt(dentistIdValue);

        }
        catch (NumberFormatException e) {

            setMessage(
                    request,
                    "Invalid ID format.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }


        // =================================================
        // BASIC ID VALIDATION
        // =================================================

        if (id <= 0 || dentistId <= 0) {

            setMessage(
                    request,
                    "Invalid treatment assignment.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }


        // =================================================
        // COMMISSION VALIDATION
        // =================================================

        if (commissionValue == null ||
            commissionValue.trim().isEmpty()) {

            setMessage(
                    request,
                    "Commission percentage is required.",
                    "error"
            );

            redirectToEditPage(
                    request,
                    response,
                    id,
                    dentistId
            );

            return;
        }


        double commission;


        try {

            commission =
                    Double.parseDouble(
                            commissionValue.trim()
                    );

        }
        catch (NumberFormatException e) {

            setMessage(
                    request,
                    "Enter a valid commission percentage.",
                    "error"
            );

            redirectToEditPage(
                    request,
                    response,
                    id,
                    dentistId
            );

            return;
        }


        // =================================================
        // PREVENT NaN / INFINITY
        // =================================================

        if (Double.isNaN(commission) ||
            Double.isInfinite(commission)) {

            setMessage(
                    request,
                    "Enter a valid commission percentage.",
                    "error"
            );

            redirectToEditPage(
                    request,
                    response,
                    id,
                    dentistId
            );

            return;
        }


        // =================================================
        // RANGE VALIDATION
        // 0.00 - 100.00
        // =================================================

        if (commission < 0 || commission > 100) {

            setMessage(
                    request,
                    "Commission must be between 0.00% and 100.00%.",
                    "error"
            );

            redirectToEditPage(
                    request,
                    response,
                    id,
                    dentistId
            );

            return;
        }


        // =================================================
        // OPTIONAL DECIMAL PRECISION VALIDATION
        // DECIMAL(5,2)
        // =================================================

        String commissionText =
                commissionValue.trim();

        int decimalIndex =
                commissionText.indexOf('.');

        if (decimalIndex >= 0) {

            int decimalPlaces =
                    commissionText.length()
                    - decimalIndex
                    - 1;

            if (decimalPlaces > 2) {

                setMessage(
                        request,
                        "Commission can contain maximum 2 decimal places.",
                        "error"
                );

                redirectToEditPage(
                        request,
                        response,
                        id,
                        dentistId
                );

                return;
            }
        }


        // =================================================
        // VERIFY ASSIGNMENT EXISTS
        // =================================================

        DentistTreatment existing =
                dtDao.getDentistAssignedTreatmentById(id);


        if (existing == null) {

            setMessage(
                    request,
                    "Assigned treatment not found.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }


        // =================================================
        // VERIFY DENTIST MATCH
        // =================================================

        if (existing.getDentistId() != dentistId) {

            setMessage(
                    request,
                    "Invalid dentist treatment assignment.",
                    "error"
            );

            redirectToDentists(request, response);

            return;
        }


        // =================================================
        // UPDATE
        // =================================================

        boolean updated =
                dtDao.updateCommission(
                        id,
                        commission
                );


        if (updated) {

            setMessage(
                    request,
                    "Commission updated successfully.",
                    "success"
            );

        }
        else {

            setMessage(
                    request,
                    "Failed to update commission.",
                    "error"
            );

        }


        // =================================================
        // REDIRECT
        // =================================================

        redirectToAssignedTreatments(
                request,
                response,
                dentistId,
                dDao.getDentistById(dentistId).getFullName()
        );

    }


    // =====================================================
    // REDIRECT TO ASSIGNED TREATMENTS
    // =====================================================

    private void redirectToAssignedTreatments(
            HttpServletRequest request,
            HttpServletResponse response,
            int dentistId, String dname)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                +
                "/admin/dentists/assign-treatment-list?id="
                +
                dentistId
                +
                "&name="
                + 
                dname
                
        );

    }


    // =====================================================
    // REDIRECT TO DENTISTS
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
    // REDIRECT BACK TO EDIT PAGE
    // =====================================================

    private void redirectToEditPage(
            HttpServletRequest request,
            HttpServletResponse response,
            int id,
            int dentistId)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                +
                "/admin/dentists/edit-assigned-treatment"
                +
                "?id="
                + id
                +
                "&dId="
                + dentistId
        );

    }


    // =====================================================
    // SESSION MESSAGE
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