package com.sunrise.servlet;


import java.io.IOException;



import com.sunrise.dao.ReceptionistDAO;
import com.sunrise.model.User;
import com.sunrise.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/admin/receptionists/add")
public class AddReceptionistServlet extends HttpServlet {


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


        request.getRequestDispatcher(
                "/admin/receptionists/add.jsp"
        )
        .forward(request, response);


    }









    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {



        String fullname =
                request.getParameter("fullname");



        String email =
                request.getParameter("email");



        String phoneString =
                request.getParameter("phone");



        String password =
                request.getParameter("password");






        try {


            int phone =
                    Integer.parseInt(phoneString);




            // Phone validation

            if(phoneString == null || 
    		   !phoneString.matches("\\d{9}")) {


    		    setMessage(
    		        request,
    		        "Phone number must contain exactly 9 digits.",
    		        "error"
    		    );


    		    response.sendRedirect(
    		        request.getContextPath()
    		        + "/admin/receptionists/add"
    		    );


    		    return;

    		}






            // Email duplicate check

            if(receptionistDAO.emailExists(email)) {


                setMessage(
                        request,
                        "Email already registered.",
                        "error"
                );


                response.sendRedirect(
                    request.getContextPath()
                    + "/admin/receptionists/add"
                );


                return;


            }






            // Phone duplicate check

            if(receptionistDAO.phoneExists(phone)) {


                setMessage(
                        request,
                        "Phone number already registered.",
                        "error"
                );


                response.sendRedirect(
                    request.getContextPath()
                    + "/admin/receptionists/add"
                );


                return;


            }








            // Create User Object

            User user = new User();



            user.setFullname(fullname);


            user.setEmail(email);


            user.setPhone(phone);



            user.setRole(
                    "receptionist"
            );



            user.setStatus(
                    "active"
            );

            
            
            if(password == null ||
    		   password.contains(" ") ||
    		   password.length() < 8 ||
    		   !password.matches(".*[!@#$%^&*].*")){


    		    setMessage(
    		        request,
    		        "Password must contain minimum 8 characters and one special character.",
    		        "error"
    		    );


    		    response.sendRedirect(
    		        request.getContextPath()
    		        + "/admin/receptionists/add"
    		    );


    		    return;

    		}





            // BCrypt Password Hash

            String hashedPassword = PasswordUtil.hashPassword(password);
            user.setPassword(
                    hashedPassword
            );









            boolean added =
                    receptionistDAO.addReceptionist(user);







            if(added) {


                setMessage(
                        request,
                        "Receptionist added successfully.",
                        "success"
                );


            }
            else {


                setMessage(
                        request,
                        "Failed to add receptionist.",
                        "error"
                );


            }







        }
        catch(NumberFormatException e) {


            setMessage(
                    request,
                    "Invalid phone number.",
                    "error"
            );


        }







        response.sendRedirect(
                request.getContextPath()
                + "/admin/receptionists"
        );



    }









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