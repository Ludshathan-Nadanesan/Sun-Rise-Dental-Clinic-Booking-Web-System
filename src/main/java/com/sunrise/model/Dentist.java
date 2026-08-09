package com.sunrise.model;

import java.sql.Timestamp;

public class Dentist {

    private int dentistId;
    private String fullName;
    private String email;
    private int phone;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    private int assignedTreatments;

    public Dentist() {

    }

    public Dentist(int dentistId,
                   String fullName,
                   String email,
                   int phone,
                   String status,
                   Timestamp createdAt,
                   Timestamp updatedAt) {

        this.dentistId = dentistId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getDentistId() {
        return dentistId;
    }

    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    
    public int getAssignedTreatments() {

        return assignedTreatments;

    }
    
    public void setAssignedTreatments(int assignedTreatments) {

        this.assignedTreatments = assignedTreatments;

    }
}