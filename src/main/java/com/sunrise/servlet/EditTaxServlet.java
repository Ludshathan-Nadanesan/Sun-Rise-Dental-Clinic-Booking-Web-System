package com.sunrise.servlet;

import java.io.IOException;

import com.sunrise.dao.TaxesDAO;
import com.sunrise.model.Tax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/taxes/edit")
public class EditTaxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaxesDAO taxesDAO;

    @Override
    public void init() throws ServletException {
        taxesDAO = new TaxesDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String taxIdStr = request.getParameter("tax_id");
        String taxName = request.getParameter("tax_name");
        String percentageStr = request.getParameter("tax_percantage");

        if (taxIdStr == null || taxName == null || taxName.trim().isEmpty() || percentageStr == null || percentageStr.trim().isEmpty()) {
            session.setAttribute("error", "All fields are required.");
            response.sendRedirect(request.getContextPath() + "/admin/taxes");
            return;
        }

        try {
            int taxId = Integer.parseInt(taxIdStr);
            double percentage = Double.parseDouble(percentageStr);
            
            if (percentage < 0 || percentage > 100) {
                session.setAttribute("error", "Percentage must be between 0 and 100.");
                response.sendRedirect(request.getContextPath() + "/admin/taxes");
                return;
            }

            Tax tax = new Tax();
            tax.setTaxId(taxId);
            tax.setTaxName(taxName.trim());
            tax.setTaxPercentage(percentage);

            boolean isUpdated = taxesDAO.updateTax(tax);

            if (isUpdated) {
                session.setAttribute("success", "Tax updated successfully.");
            } else {
                session.setAttribute("error", "Failed to update tax. Please try again.");
            }
            
            response.sendRedirect(request.getContextPath() + "/admin/taxes");

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid data format.");
            response.sendRedirect(request.getContextPath() + "/admin/taxes");
        }
    }
}
