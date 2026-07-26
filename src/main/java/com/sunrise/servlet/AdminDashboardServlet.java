package com.sunrise.servlet;


import java.io.IOException;


import com.sunrise.dao.AdminDashboardDAO;
import com.sunrise.model.DashboardStats;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/admin")
public class AdminDashboardServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;



    private AdminDashboardDAO dashboardDAO;



    @Override
    public void init() throws ServletException {


        dashboardDAO = new AdminDashboardDAO();


    }




    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        DashboardStats stats =
                dashboardDAO.getDashboardStats();

        

        request.setAttribute(
                "stats",
                stats
        );



        request.getRequestDispatcher(
                "/admin/dashboard.jsp"
        )
        .forward(request, response);

//        System.out.println(stats.getActiveUsers());


    }


}