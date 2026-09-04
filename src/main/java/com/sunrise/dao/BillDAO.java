package com.sunrise.dao;

import com.sunrise.config.DBConnection;
import com.sunrise.model.Bill;
import com.sunrise.model.BillItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillDAO {

    public int createBill(Bill bill, List<Integer> appointmentIds) throws SQLException {
        Connection conn = null;
        int generatedBillId = -1;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Insert into bills table
            String insertBillSQL = "INSERT INTO bills (patient_id, sub_total, tax_pay, total_ammount, paid_ammount, balance_ammount, payment_status, created_at) " +
                                   "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            
            PreparedStatement pstmtBill = conn.prepareStatement(insertBillSQL, Statement.RETURN_GENERATED_KEYS);
            pstmtBill.setInt(1, bill.getPatientId());
            pstmtBill.setDouble(2, bill.getSubTotal());
            pstmtBill.setDouble(3, bill.getTaxPay());
            pstmtBill.setDouble(4, bill.getTotalAmmount());
            pstmtBill.setDouble(5, bill.getPaidAmmount());
            pstmtBill.setDouble(6, bill.getBalanceAmmount());
            pstmtBill.setString(7, bill.getPaymentStatus());
            
            int affectedRows = pstmtBill.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmtBill.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedBillId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }

            // 2. Fetch necessary calculations and insert into bill_items
            String fetchCalculationDataSQL = 
                "SELECT a.appointment_id, t.default_fee, dt.dent_commision_perc " +
                "FROM appointments a " +
                "JOIN treatments t ON a.treatment_id = t.treatment_id " +
                "JOIN dentist_treatments dt ON a.dentist_id = dt.dentist_id AND a.treatment_id = dt.treatment_id " +
                "WHERE a.appointment_id = ?";

            String insertBillItemSQL = 
                "INSERT INTO bill_items (bill_id, appointment_id, clinical_pay, dentist_pay, charged_amount) " +
                "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmtFetch = conn.prepareStatement(fetchCalculationDataSQL);
            PreparedStatement pstmtItem = conn.prepareStatement(insertBillItemSQL);

            for (Integer appId : appointmentIds) {
                pstmtFetch.setInt(1, appId);
                ResultSet rsData = pstmtFetch.executeQuery();
                
                if (rsData.next()) {
                    double chargedAmount = rsData.getDouble("default_fee");
                    double commissionPerc = rsData.getDouble("dent_commision_perc");
                    
                    double dentistPay = (chargedAmount * commissionPerc) / 100.0;
                    double clinicalPay = chargedAmount - dentistPay;

                    pstmtItem.setInt(1, generatedBillId);
                    pstmtItem.setInt(2, appId);
                    pstmtItem.setDouble(3, clinicalPay);
                    pstmtItem.setDouble(4, dentistPay);
                    pstmtItem.setDouble(5, chargedAmount);
                    
                    pstmtItem.addBatch();
                } else {
                    throw new SQLException("Required data not found for appointment_id: " + appId);
                }
            }

            pstmtItem.executeBatch();
            
            conn.commit(); // Commit transaction

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return generatedBillId;
    }

    public List<Map<String, Object>> getPendingAppointments(int patientId) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT a.appointment_id, a.status, a.perfomed_at, t.treatment_id, t.treatment_name, t.default_fee " +
                     "FROM appointments a " +
                     "JOIN treatments t ON a.treatment_id = t.treatment_id " +
                     "WHERE a.patient_id = ? AND a.status = 'completed' " +
                     "AND a.appointment_id NOT IN (SELECT appointment_id FROM bill_items)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("appointment_id", rs.getInt("appointment_id"));
                map.put("treatment_name", rs.getString("treatment_name"));
                map.put("default_fee", rs.getDouble("default_fee"));
                map.put("perfomed_at", rs.getString("perfomed_at")); // or Date/Timestamp
                list.add(map);
            }
        }
        return list;
    }

    public List<Map<String, Object>> searchPatients(String query) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        boolean isNumeric = query != null && query.matches("\\d+");
        
        String sql;
        if (isNumeric) {
            sql = "SELECT DISTINCT p.patient_id, p.full_name, p.phone, p.email, p.gender, p.dob, p.address, p.registered_at " +
                  "FROM patients p " +
                  "LEFT JOIN bills b ON p.patient_id = b.patient_id " +
                  "LEFT JOIN bill_items bi ON b.bill_id = bi.bill_id " +
                  "WHERE p.full_name LIKE ? OR p.phone LIKE ? OR p.email LIKE ? " +
                  "OR b.bill_id = ? OR bi.appointment_id = ? LIMIT 10";
        } else {
            sql = "SELECT patient_id, full_name, phone, email, gender, dob, address, registered_at FROM patients " +
                  "WHERE full_name LIKE ? OR phone LIKE ? OR email LIKE ? LIMIT 10";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            String likeQuery = "%" + query + "%";
            pstmt.setString(1, likeQuery);
            pstmt.setString(2, likeQuery);
            pstmt.setString(3, likeQuery);
            
            if (isNumeric) {
                int numericVal = Integer.parseInt(query);
                pstmt.setInt(4, numericVal);
                pstmt.setInt(5, numericVal);
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("patient_id", rs.getInt("patient_id"));
                map.put("full_name", rs.getString("full_name"));
                map.put("phone", rs.getString("phone"));
                map.put("email", rs.getString("email"));
                map.put("gender", rs.getString("gender"));
                map.put("dob", rs.getDate("dob"));
                map.put("address", rs.getString("address"));
                map.put("registered_at", rs.getTimestamp("registered_at"));
                list.add(map);
            }
        }
        return list;
    }

    public Map<String, Object> getBillDetails(int billId) throws SQLException {
        Map<String, Object> billDetails = new HashMap<>();
        String billSql = "SELECT b.*, p.full_name, p.phone, p.email, p.address " +
                         "FROM bills b JOIN patients p ON b.patient_id = p.patient_id " +
                         "WHERE b.bill_id = ?";
                         
        String itemsSql = "SELECT bi.*, a.perfomed_at, t.treatment_name " +
                          "FROM bill_items bi " +
                          "JOIN appointments a ON bi.appointment_id = a.appointment_id " +
                          "JOIN treatments t ON a.treatment_id = t.treatment_id " +
                          "WHERE bi.bill_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(billSql)) {
                pstmt.setInt(1, billId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    billDetails.put("bill_id", rs.getInt("bill_id"));
                    billDetails.put("patient_id", rs.getInt("patient_id"));
                    billDetails.put("patient_name", rs.getString("full_name"));
                    billDetails.put("patient_phone", rs.getString("phone"));
                    billDetails.put("patient_email", rs.getString("email"));
                    billDetails.put("patient_address", rs.getString("address"));
                    billDetails.put("sub_total", rs.getDouble("sub_total"));
                    billDetails.put("tax_pay", rs.getDouble("tax_pay"));
                    billDetails.put("total_amount", rs.getDouble("total_ammount")); // Keeping original DB spelling
                    billDetails.put("paid_amount", rs.getDouble("paid_ammount"));
                    billDetails.put("balance_amount", rs.getDouble("balance_ammount"));
                    billDetails.put("payment_status", rs.getString("payment_status"));
                    billDetails.put("created_at", rs.getTimestamp("created_at"));
                }
            }
            
            List<Map<String, Object>> items = new ArrayList<>();
            try (PreparedStatement pstmt = conn.prepareStatement(itemsSql)) {
                pstmt.setInt(1, billId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("treatment_name", rs.getString("treatment_name"));
                    item.put("charged_amount", rs.getDouble("charged_amount"));
                    item.put("perfomed_at", rs.getTimestamp("perfomed_at"));
                    items.add(item);
                }
            }
            billDetails.put("items", items);
        }
        return billDetails;
    }

    public boolean settleBillBalance(int billId, double newPaymentAmount) throws SQLException {
        String selectSql = "SELECT total_ammount, paid_ammount, balance_ammount FROM bills WHERE bill_id = ?";
        String updateSql = "UPDATE bills SET paid_ammount = ?, balance_ammount = ?, payment_status = ? WHERE bill_id = ?";
        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                double totalAmount = 0;
                double paidAmount = 0;
                
                try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                    selectStmt.setInt(1, billId);
                    ResultSet rs = selectStmt.executeQuery();
                    if (rs.next()) {
                        totalAmount = rs.getDouble("total_ammount");
                        paidAmount = rs.getDouble("paid_ammount");
                    } else {
                        return false; // bill not found
                    }
                }
                
                double newPaidAmount = paidAmount + newPaymentAmount;
                if (newPaidAmount > totalAmount) {
                    newPaidAmount = totalAmount; // Cap at total to avoid negative balance
                }
                double newBalanceAmount = totalAmount - newPaidAmount;
                String status = newBalanceAmount <= 0 ? "paid" : "pending";
                
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setDouble(1, newPaidAmount);
                    updateStmt.setDouble(2, newBalanceAmount);
                    updateStmt.setString(3, status);
                    updateStmt.setInt(4, billId);
                    updateStmt.executeUpdate();
                }
                
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<Bill> getBillsByPatient(int patientId) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE patient_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Bill b = new Bill();
                b.setBillId(rs.getInt("bill_id"));
                b.setPatientId(rs.getInt("patient_id"));
                b.setSubTotal(rs.getDouble("sub_total"));
                b.setTaxPay(rs.getDouble("tax_pay"));
                b.setTotalAmmount(rs.getDouble("total_ammount"));
                b.setPaidAmmount(rs.getDouble("paid_ammount"));
                b.setBalanceAmmount(rs.getDouble("balance_ammount"));
                b.setPaymentStatus(rs.getString("payment_status"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                b.setPaidAt(rs.getTimestamp("paid_at"));
                bills.add(b);
            }
        }
        return bills;
    }

    public List<Map<String, Object>> getTaxes() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT tax_id, tax_name, tax_percantage FROM taxes";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("tax_id", rs.getInt("tax_id"));
                map.put("tax_name", rs.getString("tax_name"));
                map.put("tax_percantage", rs.getDouble("tax_percantage"));
                list.add(map);
            }
        }
        return list;
    }

    public boolean deleteBill(int billId) throws SQLException {
        String deleteItemsSql = "DELETE FROM bill_items WHERE bill_id = ?";
        String deleteBillSql = "DELETE FROM bills WHERE bill_id = ?";
        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement pstmt1 = conn.prepareStatement(deleteItemsSql)) {
                    pstmt1.setInt(1, billId);
                    pstmt1.executeUpdate();
                }
                
                try (PreparedStatement pstmt2 = conn.prepareStatement(deleteBillSql)) {
                    pstmt2.setInt(1, billId);
                    int affected = pstmt2.executeUpdate();
                    if (affected == 0) {
                        conn.rollback();
                        return false;
                    }
                }
                
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
