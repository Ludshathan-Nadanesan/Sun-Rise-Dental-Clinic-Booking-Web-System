package com.sunrise.servlet;

import java.io.IOException;
import java.util.List;

import com.sunrise.model.HelpTopic;
import com.sunrise.service.HelpContentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/receptionist/help")
public class ReceptionistHelpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"receptionist".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String realPath = request.getServletContext().getRealPath("/");
        List<HelpTopic> topics = HelpContentService.getHelpTopics(realPath, "Receptionist");
        
        request.setAttribute("helpTopics", topics);
        request.getRequestDispatcher("/receptionist/help/index.jsp").forward(request, response);
    }
}
