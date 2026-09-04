package com.sunrise.servlet;

import java.io.IOException;
import java.util.List;

import com.sunrise.dao.AppointmentDAO;
import com.sunrise.model.Appointment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/appointments")
public class AdminAppointmentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String keyword = request.getParameter("search");
        if (keyword == null) {
            keyword = "";
        }

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        String sortBy = request.getParameter("sort");
        if (sortBy == null) {
            sortBy = "latest";
        }

        List<Appointment> appointments;

        if (keyword.isEmpty() && (startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty()) && sortBy.equals("latest")) {
            appointments = appointmentDAO.getAllAppointments();
        } else {
            appointments = appointmentDAO.searchAppointments(keyword, startDate, endDate, sortBy);
        }

        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/admin/appointments/list.jsp").forward(request, response);
    }
}
