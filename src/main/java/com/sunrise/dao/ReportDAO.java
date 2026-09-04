package com.sunrise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunrise.config.DBConnection;

public class ReportDAO {

    // Helper for date filtering
    private String getDateFilter(String fromDate, String toDate, String columnPrefix) {
        if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            return " AND DATE(" + columnPrefix + "created_at) BETWEEN ? AND ? ";
        }
        return "";
    }
    
    private void setDateParams(PreparedStatement pstmt, int startIndex, String fromDate, String toDate) throws SQLException {
        if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            pstmt.setString(startIndex, fromDate);
            pstmt.setString(startIndex + 1, toDate);
        }
    }

    // 1. Financial KPIs
    public Map<String, Double> getFinancialKPIs(String fromDate, String toDate) {
        Map<String, Double> kpis = new HashMap<>();
        kpis.put("totalRevenue", 0.0);
        kpis.put("totalOutstanding", 0.0);
        kpis.put("totalTax", 0.0);

        String sql = "SELECT " +
                     "SUM(b.tax_pay) as total_tax, " +
                     "SUM(CASE WHEN b.payment_status = 'pending' OR b.balance_ammount > 0 THEN b.balance_ammount ELSE 0 END) as total_outstanding, " +
                     "(SELECT SUM(bi.clinical_pay) FROM bill_items bi JOIN bills b2 ON bi.bill_id = b2.bill_id WHERE 1=1 " + 
                     getDateFilter(fromDate, toDate, "b2.") + ") as total_revenue " +
                     "FROM bills b WHERE 1=1 " + getDateFilter(fromDate, toDate, "b.");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             int index = 1;
             if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                 pstmt.setString(index++, fromDate);
                 pstmt.setString(index++, toDate);
                 pstmt.setString(index++, fromDate);
                 pstmt.setString(index++, toDate);
             }

             ResultSet rs = pstmt.executeQuery();
             if (rs.next()) {
                 kpis.put("totalRevenue", rs.getDouble("total_revenue"));
                 kpis.put("totalOutstanding", rs.getDouble("total_outstanding"));
                 kpis.put("totalTax", rs.getDouble("total_tax"));
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kpis;
    }

    // 2. Dentist Payout Report
    public List<Map<String, Object>> getDentistPayouts(String fromDate, String toDate) {
        List<Map<String, Object>> payouts = new ArrayList<>();
        
        String sql = "SELECT d.full_name as dentist_name, SUM(bi.dentist_pay) as total_commission, COUNT(bi.bill_item_id) as treatments_count " +
                     "FROM bill_items bi " +
                     "JOIN appointments a ON bi.appointment_id = a.appointment_id " +
                     "JOIN dentists d ON a.dentist_id = d.dentist_id " +
                     "JOIN bills b ON bi.bill_id = b.bill_id " +
                     "WHERE 1=1 " + getDateFilter(fromDate, toDate, "b.") +
                     "GROUP BY d.dentist_id, d.full_name " +
                     "ORDER BY total_commission DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             setDateParams(pstmt, 1, fromDate, toDate);
             ResultSet rs = pstmt.executeQuery();
             
             while (rs.next()) {
                 Map<String, Object> map = new HashMap<>();
                 map.put("dentistName", rs.getString("dentist_name"));
                 map.put("totalCommission", rs.getDouble("total_commission"));
                 map.put("treatmentsCount", rs.getInt("treatments_count"));
                 payouts.add(map);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payouts;
    }

    // 3. Most Popular Treatments (Pie Chart)
    public List<Map<String, Object>> getPopularTreatments(String fromDate, String toDate) {
        List<Map<String, Object>> treatments = new ArrayList<>();
        
        String sql = "SELECT t.treatment_name, COUNT(a.appointment_id) as count " +
                     "FROM appointments a " +
                     "JOIN treatments t ON a.treatment_id = t.treatment_id " +
                     "WHERE 1=1 " + getDateFilter(fromDate, toDate, "a.") +
                     "GROUP BY t.treatment_id, t.treatment_name " +
                     "ORDER BY count DESC LIMIT 10";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             setDateParams(pstmt, 1, fromDate, toDate);
             ResultSet rs = pstmt.executeQuery();
             
             while (rs.next()) {
                 Map<String, Object> map = new HashMap<>();
                 map.put("label", rs.getString("treatment_name"));
                 map.put("value", rs.getInt("count"));
                 treatments.add(map);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatments;
    }

    // 4. Dentist Productivity (Bar/List)
    public List<Map<String, Object>> getDentistProductivity(String fromDate, String toDate) {
        List<Map<String, Object>> productivity = new ArrayList<>();
        
        String sql = "SELECT d.full_name as dentist_name, COUNT(a.appointment_id) as completed_appointments " +
                     "FROM appointments a " +
                     "JOIN dentists d ON a.dentist_id = d.dentist_id " +
                     "WHERE a.status = 'completed' " + getDateFilter(fromDate, toDate, "a.") +
                     "GROUP BY d.dentist_id, d.full_name " +
                     "ORDER BY completed_appointments DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             setDateParams(pstmt, 1, fromDate, toDate);
             ResultSet rs = pstmt.executeQuery();
             
             while (rs.next()) {
                 Map<String, Object> map = new HashMap<>();
                 map.put("dentistName", rs.getString("dentist_name"));
                 map.put("completedCount", rs.getInt("completed_appointments"));
                 productivity.add(map);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productivity;
    }

    // 5. Appointment Conversion (Doughnut Chart)
    public Map<String, Integer> getAppointmentConversion(String fromDate, String toDate) {
        Map<String, Integer> conversion = new HashMap<>();
        conversion.put("scheduled", 0);
        conversion.put("completed", 0);
        conversion.put("cancelled", 0);

        String sql = "SELECT status, COUNT(*) as count FROM appointments WHERE 1=1 " + 
                     getDateFilter(fromDate, toDate, "") +
                     "GROUP BY status";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             setDateParams(pstmt, 1, fromDate, toDate);
             ResultSet rs = pstmt.executeQuery();
             
             while (rs.next()) {
                 conversion.put(rs.getString("status").toLowerCase(), rs.getInt("count"));
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversion;
    }

    // 6. Patient Registration Trend (Line Chart)
    public List<Map<String, Object>> getPatientRegistrationTrend(String fromDate, String toDate) {
        List<Map<String, Object>> trend = new ArrayList<>();
        
        String sql = "SELECT DATE(registered_at) as reg_date, COUNT(*) as count " +
                     "FROM patients " +
                     "WHERE 1=1 ";
                     
        if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            sql += " AND DATE(registered_at) BETWEEN ? AND ? ";
        }
        
        sql += "GROUP BY DATE(registered_at) ORDER BY DATE(registered_at) ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
             if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                 pstmt.setString(1, fromDate);
                 pstmt.setString(2, toDate);
             }
             
             ResultSet rs = pstmt.executeQuery();
             while (rs.next()) {
                 Map<String, Object> map = new HashMap<>();
                 map.put("date", rs.getString("reg_date"));
                 map.put("count", rs.getInt("count"));
                 trend.add(map);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trend;
    }
}
