package com.sunrise.model;

import java.sql.Timestamp;

public class Appointment {

    private int appointmentId;
    private int patientId;
    private int dentistId;
    private int treatmentId;
    
    private Timestamp appointmentStartDateTime;
    private Timestamp appointmentEndDateTime;
    
    private String status;
    private String isPaid;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp perfomedAt;

    
    
    // Transient fields for displaying in UI
    private String patientName;
    private String dentistName;
    private String treatmentName;
    

    // =========================
    // Default Constructor
    // =========================

    public Appointment() {
    }

    // =========================
    // Parameterized Constructor
    // =========================

    public Appointment(int appointmentId, int patientId, int dentistId, Timestamp appointmentStartDateTime, Timestamp appointmentEndDateTime, String status, Timestamp createdAt, Timestamp updatedAt) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.dentistId = dentistId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.appointmentStartDateTime = appointmentStartDateTime;
        this.appointmentEndDateTime = appointmentEndDateTime;
    }

    // =========================
    // Getters & Setters
    // =========================

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDentistId() {
        return dentistId;
    }

    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }
    
    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }
    
    
    
    public Timestamp getAppointmentStartDateTime() {
        return appointmentStartDateTime;
    }

    public void setAppointmentStartDateTime(Timestamp appointmentStartDateTime) {
        this.appointmentStartDateTime = appointmentStartDateTime;
    }
    
    
    public Timestamp getAppointmentEndDateTime() {
        return appointmentEndDateTime;
    }

    public void setAppointmentEndDateTime(Timestamp appointmentEndDateTime) {
        this.appointmentEndDateTime = appointmentEndDateTime;
    }
    
    
    
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }
    

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getPerfomedAt() {
        return perfomedAt;
    }

    public void setPerfomedAt(Timestamp perfomedAt) {
        this.perfomedAt = perfomedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }
}
