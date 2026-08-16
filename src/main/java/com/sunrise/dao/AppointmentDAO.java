package com.sunrise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sunrise.config.DBConnection;
import com.sunrise.model.Appointment;

public class AppointmentDAO {

    // =====================================================
    // Get All Appointments (with Patient and Dentist names)
    // =====================================================

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        String sql = 
            "SELECT a.appointment_id, a.treatment_id, a.patient_id, a.dentist_id, a.start_date_time, a.end_date_time, a.status, a.is_paid, a.created_at, a.updated_at, a.perfomed_at, " + 
            "p.full_name AS patient_name, d.full_name AS dentist_name, tr.treatment_name AS treatment_name " +
            "FROM appointments a " +
            "JOIN patients p ON a.patient_id = p.patient_id " +
            "JOIN dentists d ON a.dentist_id = d.dentist_id "+
            "JOIN treatments tr ON a.treatment_id = tr.treatment_id " +
            "ORDER BY a.appointment_date_time DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDentistId(rs.getInt("dentist_id"));
                appointment.setTreatmentId(rs.getInt("treatment_id"));
                appointment.setAppointmentStartDateTime(rs.getTimestamp("start_date_time"));
                appointment.setAppointmentEndDateTime(rs.getTimestamp("end_date_time"));
                appointment.setStatus(rs.getString("status"));
                appointment.setIsPaid(rs.getString("is_paid"));
                appointment.setCreatedAt(rs.getTimestamp("created_at"));
                appointment.setUpdatedAt(rs.getTimestamp("updated_at"));
                appointment.setPerfomedAt(rs.getTimestamp("perfomed_at"));
                appointment.setPatientName(rs.getString("patient_name"));
                appointment.setDentistName(rs.getString("dentist_name"));
                appointment.setTreatmentName(rs.getString("treatment_name"));
                
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    // =====================================================
    // Add Appointment
    // =====================================================

    public boolean addAppointment(Appointment appointment) {
        boolean result = false;

        String sql = "INSERT INTO appointments (patient_id, dentist_id, treatment_id, start_date_time, end_date_time, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDentistId());
            ps.setInt(3, appointment.getTreatmentId());
            ps.setTimestamp(4, appointment.getAppointmentStartDateTime());
            ps.setTimestamp(5, appointment.getAppointmentEndDateTime());
            ps.setString(6, appointment.getStatus() != null ? appointment.getStatus() : "scheduled");

            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // =====================================================
    // Update Appointment Status
    // =====================================================

    public boolean updateAppointmentStatus(int appointmentId, String status, String isPaid) {
        boolean result = false;

        String sql = "UPDATE appointments SET status=?, is_paid=? WHERE appointment_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, isPaid);
            ps.setInt(3, appointmentId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // =====================================================
    // Get Appointment By ID
    // =====================================================

    public Appointment getAppointmentById(int appointmentId) {
        Appointment appointment = null;

        String sql = 
            "SELECT a.appointment_id, a.treatment_id, a.patient_id, a.dentist_id, a.start_date_time, a.end_date_time, a.status, a.is_paid, a.created_at, a.updated_at, a.perfomed_at, " +
            "p.full_name AS patient_name, d.full_name AS dentist_name, tr.treatment_name AS treatment_name " +
            "FROM appointments a " +
            "JOIN patients p ON a.patient_id = p.patient_id " +
            "JOIN dentists d ON a.dentist_id = d.dentist_id "+
            "JOIN treatments tr ON a.treatment_id = tr.treatment_id " +
            "WHERE a.appointment_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setTreatmentId(rs.getInt("treatment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDentistId(rs.getInt("dentist_id"));
                appointment.setAppointmentStartDateTime(rs.getTimestamp("start_date_time"));
                appointment.setAppointmentEndDateTime(rs.getTimestamp("end_date_time"));
                appointment.setStatus(rs.getString("status"));
                appointment.setIsPaid(rs.getString("is_paid"));
                appointment.setCreatedAt(rs.getTimestamp("created_at"));
                appointment.setUpdatedAt(rs.getTimestamp("updated_at"));
                appointment.setPerfomedAt(rs.getTimestamp("perfomed_at"));
                appointment.setPatientName(rs.getString("patient_name"));
                appointment.setDentistName(rs.getString("dentist_name"));
                appointment.setTreatmentName(rs.getString("treatment_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

    // =====================================================
    // Search, Filter and Sort Appointments
    // =====================================================

    public List<Appointment> searchAppointments(String keyword, String startDate, String endDate, String sortBy) {
        List<Appointment> appointments = new ArrayList<>();

        String orderBy = "a.start_date_time DESC";
        String statusFilter = null;

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "oldest":
                    orderBy = "a.start_date_time ASC";
                    break;
                case "newest":
                    orderBy = "a.start_date_time DESC";
                    break;
                case "scheduled":
                case "completed":
                case "cancelled":
                    statusFilter = sortBy;
                    break;
                case "status":
                    orderBy = "a.status ASC, a.start_date_time DESC";
                    break;
                default:
                    orderBy = "a.start_date_time DESC";
                    break;
            }
        }

        StringBuilder sql = new StringBuilder(
            "SELECT a.appointment_id, a.treatment_id, a.patient_id, a.dentist_id, a.start_date_time, a.end_date_time, a.status, a.is_paid, a.created_at, a.updated_at, a.perfomed_at, " +
            "p.full_name AS patient_name, p.email AS patient_email, p.phone AS patient_phone, d.full_name AS dentist_name, tr.treatment_name AS treatment_name " +
            "FROM appointments a " +
            "JOIN patients p ON a.patient_id = p.patient_id " +
            "JOIN dentists d ON a.dentist_id = d.dentist_id "+
            "JOIN treatments tr ON a.treatment_id = tr.treatment_id " +
            "WHERE 1=1 "
        );

        // Search Condition
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (a.appointment_id LIKE ? OR p.full_name LIKE ? OR p.email LIKE ? OR CAST(p.phone AS CHAR) LIKE ? OR d.full_name LIKE ?) ");
        }

        // Filter Condition
        if (startDate != null && !startDate.trim().isEmpty() && endDate != null && !endDate.trim().isEmpty()) {
            sql.append("AND (DATE(a.start_date_time) BETWEEN ? AND ?) ");
        }

        // Status Filter
        if (statusFilter != null) {
            sql.append("AND a.status = ? ");
        }

        sql.append("ORDER BY ").append(orderBy);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String search = "%" + keyword + "%";
                ps.setString(paramIndex++, search);
                ps.setString(paramIndex++, search);
                ps.setString(paramIndex++, search);
                ps.setString(paramIndex++, search);
                ps.setString(paramIndex++, search);
            }

            if (startDate != null && !startDate.trim().isEmpty() && endDate != null && !endDate.trim().isEmpty()) {
                ps.setString(paramIndex++, startDate);
                ps.setString(paramIndex++, endDate);
            }

            if (statusFilter != null) {
                ps.setString(paramIndex++, statusFilter);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setTreatmentId(rs.getInt("treatment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDentistId(rs.getInt("dentist_id"));
                appointment.setAppointmentStartDateTime(rs.getTimestamp("start_date_time"));
                appointment.setAppointmentEndDateTime(rs.getTimestamp("end_date_time"));
                appointment.setStatus(rs.getString("status"));
                appointment.setIsPaid(rs.getString("is_paid"));
                appointment.setCreatedAt(rs.getTimestamp("created_at"));
                appointment.setUpdatedAt(rs.getTimestamp("updated_at"));
                appointment.setPerfomedAt(rs.getTimestamp("perfomed_at"));
                appointment.setPatientName(rs.getString("patient_name"));
                appointment.setDentistName(rs.getString("dentist_name"));
                appointment.setTreatmentName(rs.getString("treatment_name"));
                
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    public List<Appointment> getAppointmentsByDentistAndDate(int dentistId, String date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.appointment_id, a.patient_id, a.treatment_id, a.dentist_id, a.start_date_time, a.end_date_time, a.status, a.is_paid, a.created_at, a.updated_at, a.perfomed_at FROM appointments a WHERE a.dentist_id = ? AND DATE(a.start_date_time) = ? AND a.status != 'cancelled' ORDER BY a.start_date_time ASC";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dentistId);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setTreatmentId(rs.getInt("treatment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDentistId(rs.getInt("dentist_id"));
                appointment.setAppointmentStartDateTime(rs.getTimestamp("start_date_time"));
                appointment.setAppointmentEndDateTime(rs.getTimestamp("end_date_time"));
                appointment.setStatus(rs.getString("status"));
                appointment.setIsPaid(rs.getString("is_paid"));
                appointment.setCreatedAt(rs.getTimestamp("created_at"));
                appointment.setPerfomedAt(rs.getTimestamp("perfomed_at"));
                appointment.setUpdatedAt(rs.getTimestamp("updated_at"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
}




