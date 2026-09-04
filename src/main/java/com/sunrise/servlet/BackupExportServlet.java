package com.sunrise.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sunrise.config.DBConfig;
import com.sunrise.dao.AppointmentDAO;
import com.sunrise.dao.BillDAO;
import com.sunrise.dao.PatientDAO;
import com.sunrise.model.Appointment;
import com.sunrise.model.Bill;
import com.sunrise.model.Patient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/export")
public class BackupExportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String type = request.getParameter("type");
        if (type == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Export type required");
            return;
        }

        String dateStr = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        try {
            switch (type) {
                case "sql":
                    exportSQL(response, dateStr);
                    break;
                case "patients":
                    exportPatientsCSV(response, dateStr);
                    break;
                case "bills":
                    exportBillsCSV(response, dateStr);
                    break;
                case "appointments":
                    exportAppointmentsCSV(response, dateStr);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid export type");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Export failed");
        }
    }

    private void exportSQL(HttpServletResponse response, String dateStr) throws IOException {
        String dbName = "sunrisedentalclinicdb";
        String dbUser = DBConfig.get("db.username");
        String dbPass = DBConfig.get("db.password");
        
        // Command to run mysqldump. This requires mysqldump to be in the PATH.
        String command = "mysqldump -u " + dbUser + (dbPass != null && !dbPass.isEmpty() ? " -p" + dbPass : "") + " " + dbName;
        
        response.setContentType("application/sql");
        response.setHeader("Content-Disposition", "attachment; filename=\"backup_" + dateStr + ".sql\"");
        
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStream is = process.getInputStream();
            OutputStream os = response.getOutputStream();
            
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            
            os.flush();
            is.close();
            
            // Wait for process to finish
            int processComplete = process.waitFor();
            if (processComplete != 0) {
                // If it fails, we write an error comment into the SQL file
                os.write("\n-- BACKUP FAILED. Ensure mysqldump is in your system PATH.\n".getBytes());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void exportPatientsCSV(HttpServletResponse response, String dateStr) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"patients_" + dateStr + ".csv\"");
        
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.searchPatients("", "newest");
        
        PrintWriter writer = response.getWriter();
        writer.println("Patient ID,Full Name,Email,Phone,Gender,DOB,Address,Registered At");
        
        for (Patient p : patients) {
            writer.printf("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                p.getPatientId(), p.getFullName(), p.getEmail(), p.getPhone(), p.getGender(), 
                p.getDob(), p.getAddress(), p.getRegisteredAt());
        }
    }

    private void exportBillsCSV(HttpServletResponse response, String dateStr) throws IOException, SQLException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"bills_" + dateStr + ".csv\"");
        
        BillDAO billDAO = new BillDAO();
        List<Bill> bills = billDAO.searchBills("", "newest");
        
        PrintWriter writer = response.getWriter();
        writer.println("Bill ID,Patient Name,Sub Total,Tax,Total Amount,Paid Amount,Balance Amount,Status,Created At");
        
        for (Bill b : bills) {
            writer.printf("%d,\"%s\",%.2f,%.2f,%.2f,%.2f,%.2f,\"%s\",\"%s\"\n",
                b.getBillId(), b.getPatientName(), b.getSubTotal(), b.getTaxPay(), b.getTotalAmmount(), 
                b.getPaidAmmount(), b.getBalanceAmmount(), b.getPaymentStatus(), b.getCreatedAt());
        }
    }

    private void exportAppointmentsCSV(HttpServletResponse response, String dateStr) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"appointments_" + dateStr + ".csv\"");
        
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        
        PrintWriter writer = response.getWriter();
        writer.println("Appointment ID,Patient Name,Dentist Name,Treatment,Status,Start Time,End Time");
        
        for (Appointment a : appointments) {
            writer.printf("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                a.getAppointmentId(), a.getPatientName(), a.getDentistName(), a.getTreatmentName(), 
                a.getStatus(), a.getAppointmentStartDateTime(), a.getAppointmentEndDateTime());
        }
    }
}
