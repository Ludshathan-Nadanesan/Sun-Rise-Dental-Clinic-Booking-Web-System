package com.sunrise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.sunrise.dao.ReportDAO;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/api/reports")
public class AdminReportsDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReportDAO reportDAO;

    @Override
    public void init() throws ServletException {
        reportDAO = new ReportDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // 1. Financial KPIs
            Map<String, Double> kpis = reportDAO.getFinancialKPIs(fromDate, toDate);
            
            // 2. Dentist Payouts
            List<Map<String, Object>> payouts = reportDAO.getDentistPayouts(fromDate, toDate);
            JsonArrayBuilder payoutsArray = Json.createArrayBuilder();
            for (Map<String, Object> p : payouts) {
                payoutsArray.add(Json.createObjectBuilder()
                    .add("dentistName", (String) p.get("dentistName"))
                    .add("totalCommission", (Double) p.get("totalCommission"))
                    .add("treatmentsCount", (Integer) p.get("treatmentsCount"))
                );
            }

            // 3. Popular Treatments
            List<Map<String, Object>> treatments = reportDAO.getPopularTreatments(fromDate, toDate);
            JsonArrayBuilder treatmentsArray = Json.createArrayBuilder();
            for (Map<String, Object> t : treatments) {
                treatmentsArray.add(Json.createObjectBuilder()
                    .add("label", (String) t.get("label"))
                    .add("value", (Integer) t.get("value"))
                );
            }

            // 4. Dentist Productivity
            List<Map<String, Object>> productivity = reportDAO.getDentistProductivity(fromDate, toDate);
            JsonArrayBuilder productivityArray = Json.createArrayBuilder();
            for (Map<String, Object> p : productivity) {
                productivityArray.add(Json.createObjectBuilder()
                    .add("dentistName", (String) p.get("dentistName"))
                    .add("completedCount", (Integer) p.get("completedCount"))
                );
            }

            // 5. Conversion Rate
            Map<String, Integer> conversion = reportDAO.getAppointmentConversion(fromDate, toDate);

            // 6. Registration Trend
            List<Map<String, Object>> trend = reportDAO.getPatientRegistrationTrend(fromDate, toDate);
            JsonArrayBuilder trendArray = Json.createArrayBuilder();
            for (Map<String, Object> t : trend) {
                trendArray.add(Json.createObjectBuilder()
                    .add("date", (String) t.get("date"))
                    .add("count", (Integer) t.get("count"))
                );
            }

            JsonObjectBuilder responseJson = Json.createObjectBuilder()
                .add("kpis", Json.createObjectBuilder()
                    .add("totalRevenue", kpis.get("totalRevenue"))
                    .add("totalOutstanding", kpis.get("totalOutstanding"))
                    .add("totalTax", kpis.get("totalTax")))
                .add("payouts", payoutsArray)
                .add("popularTreatments", treatmentsArray)
                .add("productivity", productivityArray)
                .add("conversion", Json.createObjectBuilder()
                    .add("scheduled", conversion.get("scheduled"))
                    .add("completed", conversion.get("completed"))
                    .add("cancelled", conversion.get("cancelled")))
                .add("registrationTrend", trendArray);

            out.print(responseJson.build().toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(Json.createObjectBuilder().add("error", "Failed to fetch report data").build().toString());
        }
    }
}
