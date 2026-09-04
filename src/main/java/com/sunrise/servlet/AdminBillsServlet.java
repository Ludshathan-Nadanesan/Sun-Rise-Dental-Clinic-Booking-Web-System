package com.sunrise.servlet;

import java.io.IOException;
import java.util.List;

import com.sunrise.dao.BillDAO;
import com.sunrise.model.Bill;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/bills")
public class AdminBillsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BillDAO billDAO;

    @Override
    public void init() throws ServletException {
        billDAO = new BillDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String keyword = request.getParameter("search");
        if(keyword == null) { keyword = ""; }

        String sortBy = request.getParameter("sort");
        if(sortBy == null) { sortBy = "newest"; }

        try {
            List<Bill> bills = billDAO.searchBills(keyword, sortBy);
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("/admin/billing/list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error loading bills.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("/admin/billing/list.jsp").forward(request, response);
        }
    }
}
