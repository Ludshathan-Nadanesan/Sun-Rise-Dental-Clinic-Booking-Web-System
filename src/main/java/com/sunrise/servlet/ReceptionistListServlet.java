package com.sunrise.servlet;


import java.io.IOException;
import java.util.List;


import com.sunrise.dao.ReceptionistDAO;
import com.sunrise.model.User;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/admin/receptionists")
public class ReceptionistListServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    private ReceptionistDAO receptionistDAO;



    @Override
    public void init() throws ServletException {


        receptionistDAO = new ReceptionistDAO();

    }





    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {



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





        // Get receptionist list

        List<User> receptionists =
                receptionistDAO.searchReceptionists(
                        keyword,
                        sortBy
                );





        // Send data to JSP

        request.setAttribute(
                "receptionists",
                receptionists
        );





        request.getRequestDispatcher(
                "/admin/receptionists/list.jsp"
        )
        .forward(request, response);



    }


}