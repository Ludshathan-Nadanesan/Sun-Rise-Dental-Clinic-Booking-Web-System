package com.sunrise.servlet;


import java.io.IOException;


import com.sunrise.dao.DentistDAO;
import com.sunrise.model.Dentist;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/admin/dentists/edit")
public class EditDentistServlet extends HttpServlet {



    private static final long serialVersionUID = 1L;


    private DentistDAO dentistDAO;





    @Override
    public void init() throws ServletException {


        dentistDAO = new DentistDAO();

    }








    // Open edit page

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {



        int dentistId =
        Integer.parseInt(
                request.getParameter("id")
        );



        Dentist dentist =
                dentistDAO.getDentistById(dentistId);




        request.setAttribute(
                "dentist",
                dentist
        );



        request.getRequestDispatcher(
                "/admin/dentists/edit.jsp"
        )
        .forward(request, response);



    }









    // Update dentist

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {



        int dentistId =
        Integer.parseInt(
                request.getParameter("dentistId")
        );



        String fullName =
                request.getParameter("fullName");



        String email =
                request.getParameter("email");



        String phoneValue =
                request.getParameter("phone");



        String status =
                request.getParameter("status");






        // Name validation

        if(fullName == null ||
           fullName.trim().length() < 3){


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
           "^[A-Za-z0-9+_.-]+@(.+)$")){


            setMessage(
                    request,
                    "Enter a valid email address.",
                    "error"
            );


            redirectBack(request,response);

            return;

        }







        // Phone validation

        if(phoneValue == null ||
           !phoneValue.matches("\\d{9}")){


            setMessage(
                    request,
                    "Phone number must contain exactly 9 digits.",
                    "error"
            );


            redirectBack(request,response);

            return;


        }






        int phone =
                Integer.parseInt(phoneValue);









        // Duplicate email check

        if(dentistDAO.emailExistsExceptCurrent(
                email,
                dentistId)){



            setMessage(
                    request,
                    "Email already exists.",
                    "error"
            );


            redirectBack(request,response);

            return;

        }









        // Duplicate phone check


        if(dentistDAO.phoneExistsExceptCurrent(
                phone,
                dentistId)){



            setMessage(
                    request,
                    "Phone number already exists.",
                    "error"
            );


            redirectBack(request,response);

            return;

        }









        Dentist dentist = new Dentist();


        dentist.setDentistId(dentistId);


        dentist.setFullName(fullName);


        dentist.setEmail(email);


        dentist.setPhone(phone);


        dentist.setStatus(status);







        boolean updated =
                dentistDAO.updateDentist(dentist);








        if(updated){


            setMessage(
                    request,
                    "Dentist updated successfully.",
                    "success"
            );


        }
        else{


            setMessage(
                    request,
                    "Failed to update dentist.",
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