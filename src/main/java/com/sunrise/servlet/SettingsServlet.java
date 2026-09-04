package com.sunrise.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.sunrise.dao.UserDAO;
import com.sunrise.model.User;
import com.sunrise.util.PasswordUtil;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SettingsServlet")
public class SettingsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String action = request.getParameter("action");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if ("getUserProfile".equals(action)) {
            int userId = (int) session.getAttribute("userId");
            User user = userDAO.getUserById(userId);
            if (user != null) {
                JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("full_name", user.getFullname())
                    .add("email", user.getEmail() != null ? user.getEmail() : "")
                    .add("phone", user.getPhone())
                    .add("status", user.getStatus())
                    .add("role", user.getRole())
                    .add("created_at", user.getCreatedAt().toString())
                    .add("updated_at", user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : user.getCreatedAt().toString())
                    .build();
                out.print(jsonResponse.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(Json.createObjectBuilder().add("error", "User not found").build().toString());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(Json.createObjectBuilder().add("success", false).add("error", "Unauthorized").build().toString());
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try (BufferedReader reader = request.getReader();
             JsonReader jsonReader = Json.createReader(reader)) {
             
            JsonObject jsonObject = jsonReader.readObject();
            String action = jsonObject.getString("action");

            if ("updateProfile".equals(action)) {
                String newName = jsonObject.getString("full_name");
                if (newName == null || newName.trim().isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(Json.createObjectBuilder().add("success", false).add("error", "Name cannot be empty").build().toString());
                    return;
                }

                boolean updated = userDAO.updateName(userId, newName.trim());
                if (updated) {
                    session.setAttribute("full_name", newName.trim()); // update session immediately
                    out.print(Json.createObjectBuilder().add("success", true).build().toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(Json.createObjectBuilder().add("success", false).add("error", "Failed to update profile").build().toString());
                }

            } else if ("changePassword".equals(action)) {
                String currentPassword = jsonObject.getString("current_password");
                String newPassword = jsonObject.getString("new_password");

                if (currentPassword == null || newPassword == null || currentPassword.isEmpty() || newPassword.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(Json.createObjectBuilder().add("success", false).add("error", "Passwords cannot be empty").build().toString());
                    return;
                }

                // Verify current password
                String currentHash = userDAO.getPasswordHashById(userId);
                if (currentHash == null || !PasswordUtil.checkPassword(currentPassword, currentHash)) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    // Specific error requested by user
                    out.print(Json.createObjectBuilder().add("success", false).add("error", "Wrong password").build().toString());
                    return;
                }

                // Update to new password
                String newHash = PasswordUtil.hashPassword(newPassword);
                boolean updated = userDAO.updatePassword(userId, newHash);

                if (updated) {
                    out.print(Json.createObjectBuilder().add("success", true).build().toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(Json.createObjectBuilder().add("success", false).add("error", "Failed to change password").build().toString());
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(Json.createObjectBuilder().add("success", false).add("error", "Unknown action").build().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(Json.createObjectBuilder().add("success", false).add("error", "Server error").build().toString());
        }
    }
}
