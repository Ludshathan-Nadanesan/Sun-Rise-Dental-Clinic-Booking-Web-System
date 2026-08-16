package com.sunrise.servlet;

import java.io.IOException;

import com.sunrise.dao.TaxesDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/taxes/delete")
public class DeleteTaxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaxesDAO taxesDAO;

    @Override
    public void init() throws ServletException {
        taxesDAO = new TaxesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String taxIdStr = request.getParameter("id");

        if (taxIdStr != null && !taxIdStr.trim().isEmpty()) {
            try {
                int taxId = Integer.parseInt(taxIdStr);
                boolean isDeleted = taxesDAO.deleteTaxById(taxId);

                if (isDeleted) {
                    session.setAttribute("success", "Tax deleted successfully.");
                } else {
                    session.setAttribute("error", "Failed to delete tax.");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("error", "Invalid tax ID.");
            }
        } else {
            session.setAttribute("error", "Tax ID is missing.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/taxes");
    }
}
