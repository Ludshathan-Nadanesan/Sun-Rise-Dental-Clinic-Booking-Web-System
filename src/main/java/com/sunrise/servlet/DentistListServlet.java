package com.sunrise.servlet;

import java.io.IOException;
import java.util.List;

import com.sunrise.dao.DentistDAO;
import com.sunrise.model.Dentist;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/admin/dentists")
public class DentistListServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DentistDAO dentistDAO;

    @Override
    public void init() {

        dentistDAO = new DentistDAO();

    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("search");

        if(keyword == null){

            keyword = "";

        }

        String sort = request.getParameter("sort");

        if(sort == null){

            sort = "newest";

        }

        List<Dentist> dentists =
                dentistDAO.searchDentists(keyword, sort);

        request.setAttribute("dentists", dentists);

        request.getRequestDispatcher(
                "/admin/dentists/index.jsp")
                .forward(request, response);

    }

}