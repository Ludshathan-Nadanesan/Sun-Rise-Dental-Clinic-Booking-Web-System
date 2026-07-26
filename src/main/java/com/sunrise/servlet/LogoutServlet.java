package com.sunrise.servlet;

import java.io.IOException;

import com.sunrise.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

    	SessionUtil.logout(request);
    	
	    response.sendRedirect(
	        request.getContextPath()+"/"
	    );
    	
//        HttpSession session = request.getSession(false);
//
//        if (session != null) {
//            session.invalidate();
//        }
//
//        response.sendRedirect(
//                request.getContextPath() + "/index.jsp"
//        );

    }

}