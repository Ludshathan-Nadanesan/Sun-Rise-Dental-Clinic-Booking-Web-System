package com.sunrise.servlet;


import java.io.IOException;
import java.util.List;


import com.sunrise.dao.ReceptionistDAO;
import com.sunrise.dao.TreatmentDAO;
import com.sunrise.model.Treatments;
import com.sunrise.model.User;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/admin/treatments")
public class TreatmentListServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    private TreatmentDAO treatmentDAO;


    @Override
    public void init() throws ServletException {

        treatmentDAO = new TreatmentDAO();
        
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
        List<Treatments> treatments =
                treatmentDAO.searchTreatments(
                        keyword,
                        sortBy
                );



        // Send data to JSP

        request.setAttribute(
                "treatments",
                treatments
        );


        request.getRequestDispatcher(
                "/admin/treatments/list.jsp"
        )
        .forward(request, response);



    }


}