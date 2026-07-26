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



@WebServlet("/admin/receptionists/edit")
public class EditReceptionistServlet extends HttpServlet {



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



        int id =
        Integer.parseInt(
            request.getParameter("id")
        );



        User receptionist =
        receptionistDAO.getReceptionistById(id);



        request.setAttribute(
                "receptionist",
                receptionist
        );



        request.getRequestDispatcher(
        "/admin/receptionists/edit.jsp"
        )
        .forward(request,response);



    }








    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {



        int id =
        Integer.parseInt(
        request.getParameter("userId"));



        String fullname =
        request.getParameter("fullname");
        

        String password =
        request.getParameter("password");



        String status =
        request.getParameter("status");





        User user = new User();


        user.setUserId(id);

        user.setFullname(fullname);

        user.setStatus(status);

        user.setRole("receptionist");


        if(password != null &&
        		   !password.trim().isEmpty()){


        		    if(password.contains(" ")
        		       ||
        		       password.length()<8
        		       ||
        		       !password.matches(".*[!@#$%^&*].*")){


        		        setMessage(
        		            request,
        		            "Password must contain minimum 8 characters and one special character without spaces.",
        		            "error"
        		        );


        		        response.sendRedirect(
        		            request.getContextPath()
        		            +
        		            "/admin/receptionists/edit?id="
        		            +
        		            id
        		        );


        		        return;


        		    }
        		    
        		 // BCrypt Password Hash

                    String hashedPassword = PasswordUtil.hashPassword(password);
                    user.setPassword(
                            hashedPassword
                    );


        		}







        boolean result =
        receptionistDAO.updateReceptionist(user);






        if(result){


            request.getSession()
            .setAttribute(
            "message",
            "Receptionist updated successfully."
            );


            request.getSession()
            .setAttribute(
            "messageType",
            "success"
            );


        }
        else{


            request.getSession()
            .setAttribute(
            "message",
            "Update failed."
            );


            request.getSession()
            .setAttribute(
            "messageType",
            "error"
            );

        }






        response.sendRedirect(
        request.getContextPath()
        +
        "/admin/receptionists"
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