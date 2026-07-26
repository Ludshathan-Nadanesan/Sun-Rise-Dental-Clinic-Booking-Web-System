package com.sunrise.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public class SessionUtil {


    /*
     * Get Current Session
     * 
     * Returns existing session.
     * Does not create a new session.
     */
    public static HttpSession getSession(HttpServletRequest request) {

        return request.getSession(false);

    }



    /*
     * Check whether user is logged in
     */
    public static boolean isLoggedIn(HttpServletRequest request) {


        HttpSession session = getSession(request);


        return session != null
                && session.getAttribute("userId") != null;

    }



    /*
     * Get Logged User ID
     */
    public static Integer getUserId(HttpServletRequest request) {


        HttpSession session = getSession(request);


        if(session != null) {

            return (Integer) session.getAttribute("userId");

        }


        return null;

    }



    /*
     * Get Logged User Full Name
     */
    public static String getFullName(HttpServletRequest request) {


        HttpSession session = getSession(request);


        if(session != null) {

            return (String) session.getAttribute("full_name");

        }


        return null;

    }



    /*
     * Get Logged User Email
     */
    public static String getEmail(HttpServletRequest request) {


        HttpSession session = getSession(request);


        if(session != null) {

            return (String) session.getAttribute("email");

        }


        return null;

    }



    /*
     * Get Logged User Role
     */
    public static String getRole(HttpServletRequest request) {


        HttpSession session = getSession(request);


        if(session != null) {

            return (String) session.getAttribute("role");

        }


        return null;

    }



    /*
     * Logout User
     */
    public static void logout(HttpServletRequest request) {


        HttpSession session = getSession(request);


        if(session != null) {

            session.invalidate();

        }

    }


}