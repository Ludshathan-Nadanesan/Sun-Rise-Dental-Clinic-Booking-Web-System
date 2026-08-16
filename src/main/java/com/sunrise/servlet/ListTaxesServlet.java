package com.sunrise.servlet;

import java.io.IOException;
import java.util.List;

import com.sunrise.dao.TaxesDAO;
import com.sunrise.model.Tax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/taxes")
public class ListTaxesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaxesDAO taxesDAO;

    @Override
    public void init() throws ServletException {
        taxesDAO = new TaxesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("search");
        if(keyword == null){
            keyword = "";
        }

        String sortBy = request.getParameter("sort");
        if(sortBy == null){
            sortBy = "newest";
        }

        List<Tax> taxes = taxesDAO.searchTaxes(keyword, sortBy);

        request.setAttribute("taxes", taxes);
        request.getRequestDispatcher("/admin/taxes/list.jsp").forward(request, response);
    }
}
