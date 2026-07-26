package com.sunrise.servlet;


import java.io.IOException;

import com.sunrise.dao.UserDAO;
import com.sunrise.model.User;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet("/login")
public class LoginServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    private UserDAO userDAO;



    @Override
    public void init() throws ServletException {

        userDAO = new UserDAO();

    }



    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {



        // Get login form values

        String email =
                request.getParameter("email");


        String password =
                request.getParameter("password");



        // Basic validation

        if(email == null || email.trim().isEmpty()
                ||
           password == null || password.trim().isEmpty()) {



            request.setAttribute(
                    "error",
                    "Email and password are required."
            );


            request.getRequestDispatcher(
                    "index.jsp"
            ).forward(request, response);


            return;

        }



        try {


            // Authenticate user

            User user =
                    userDAO.authenticate(
                            email,
                            password
                    );



            // Login failed

            if(user == null) {


                request.setAttribute(
                        "error",
                        "Invalid email or password."
                );


                request.getRequestDispatcher(
                        "index.jsp"
                ).forward(request, response);


                return;

            }




            // Login success
            
            HttpSession oldSession = request.getSession(false);

            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession session = request.getSession(true);


            session.setAttribute(
                    "userId",
                    user.getUserId()
            );


            session.setAttribute(
                    "full_name",
                    user.getFullname()
            );


            session.setAttribute(
                    "email",
                    user.getEmail()
            );


            session.setAttribute(
                    "role",
                    user.getRole()
            );


            // Session timeout
            // 30 minutes

            session.setMaxInactiveInterval(
                    30 * 60
            );




            // Role based redirect


            if(user.getRole().equalsIgnoreCase("admin")) {

            	response.sendRedirect(
            		    request.getContextPath() +
            		    "/admin"
            		);

//                response.sendRedirect(
//                        "admin/dashboard.jsp"
//                );

            }

            else if(
                    user.getRole()
                    .equalsIgnoreCase("receptionist")
            ) {
            	
            	response.sendRedirect(
            		    request.getContextPath() +
            		    "/receptionist"
            		);

//                response.sendRedirect(
//                        "receptionist/dashboard.jsp"
//                );

            }

            else {


                session.invalidate();


                request.setAttribute(
                        "error",
                        "Invalid user role."
                );


                request.getRequestDispatcher(
                        "index.jsp"
                ).forward(request, response);

                
            }



        }

        catch(Exception e) {


            e.printStackTrace();


            request.setAttribute(
                    "error",
                    "Something went wrong. Please try again later."
            );


            request.getRequestDispatcher(
                    "index.jsp"
            ).forward(request, response);


        }


    }


}