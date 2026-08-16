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

@WebServlet("/admin/taxes/add")
public class AddTaxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaxesDAO taxesDAO;

    @Override
    public void init() throws ServletException {
        taxesDAO = new TaxesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/taxes/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String taxName = request.getParameter("tax_name");
        String percentageStr = request.getParameter("tax_percantage");

        if (taxName == null || taxName.trim().isEmpty() || percentageStr == null || percentageStr.trim().isEmpty()) {
            session.setAttribute("error", "All fields are required.");
            response.sendRedirect(request.getContextPath() + "/admin/taxes/add");
            return;
        }

        try {
            double percentage = Double.parseDouble(percentageStr);
            if (percentage < 0 || percentage > 100) {
                session.setAttribute("error", "Percentage must be between 0 and 100.");
                response.sendRedirect(request.getContextPath() + "/admin/taxes/add");
                return;
            }

            Tax tax = new Tax();
            tax.setTaxName(taxName.trim());
            tax.setTaxPercentage(percentage);

            boolean isAdded = taxesDAO.addTax(tax);

            if (isAdded) {
                session.setAttribute("success", "Tax added successfully.");
                response.sendRedirect(request.getContextPath() + "/admin/taxes");
            } else {
                session.setAttribute("error", "Failed to add tax. Please try again.");
                response.sendRedirect(request.getContextPath() + "/admin/taxes/add");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid percentage format.");
            response.sendRedirect(request.getContextPath() + "/admin/taxes/add");
        }
    }
}
