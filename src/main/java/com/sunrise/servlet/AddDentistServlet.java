package com.sunrise.servlet;


import java.io.IOException;


import com.sunrise.dao.DentistDAO;
import com.sunrise.model.Dentist;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/admin/dentists/add")
public class AddDentistServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    private DentistDAO dentistDAO;



    @Override
    public void init() throws ServletException {


        dentistDAO = new DentistDAO();

    }






    // Open Add Dentist Page

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {



        request.getRequestDispatcher(
                "/admin/dentists/add.jsp"
        )
        .forward(request, response);


    }









    // Save Dentist

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {



        String fullName =
                request.getParameter("fullName");



        String email =
                request.getParameter("email");



        String phoneString =
                request.getParameter("phone");






        // Full name validation

        if(fullName == null ||
           fullName.trim().length() < 3) {



            setMessage(
                    request,
                    "Dentist name must contain minimum 3 characters.",
                    "error"
            );


            redirectBack(request,response);

            return;

        }







        // Email validation

        if(email == null ||
           !email.matches(
           "^[A-Za-z0-9+_.-]+@(.+)$")) {



            setMessage(
                    request,
                    "Enter a valid email address.",
                    "error"
            );


            redirectBack(request,response);

            return;

        }







        // Phone validation

        if(phoneString == null ||
           !phoneString.matches("\\d{9}")) {



            setMessage(
                    request,
                    "Phone number must contain exactly 9 digits.",
                    "error"
            );


            redirectBack(request,response);

            return;

        }






        int phone =
                Integer.parseInt(phoneString);







        // Duplicate email check

        if(dentistDAO.emailExists(email)){



            setMessage(
                    request,
                    "Email already exists.",
                    "error"
            );


            redirectBack(request,response);

            return;

        }







        // Duplicate phone check

        if(dentistDAO.phoneExists(phone)){



            setMessage(
                    request,
                    "Phone number already exists.",
                    "error"
            );


            redirectBack(request,response);

            return;

        }








        // Create Dentist Object


        Dentist dentist = new Dentist();


        dentist.setFullName(fullName);


        dentist.setEmail(email);


        dentist.setPhone(phone);


        dentist.setStatus("active");








        boolean result =
                dentistDAO.addDentist(dentist);






        if(result){


            setMessage(
                    request,
                    "Dentist added successfully.",
                    "success"
            );


        }
        else{


            setMessage(
                    request,
                    "Failed to add dentist.",
                    "error"
            );


        }






        redirectBack(request,response);


    }









    private void redirectBack(
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
            String type){



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