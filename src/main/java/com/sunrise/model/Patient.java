package com.sunrise.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Patient {

    private int patientId;
    private String fullName;
    private String email;
    private int phone;
    private String gender;
    private Date dob;
    private String address;
    private Timestamp registeredAt;


    // =========================
    // Default Constructor
    // =========================

    public Patient() {

    }


    // =========================
    // Parameterized Constructor
    // =========================

    public Patient(
            int patientId,
            String fullName,
            String email,
            int phone,
            String gender,
            Date dob,
            String address,
            Timestamp registeredAt) {

        this.patientId = patientId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.registeredAt = registeredAt;
    }


    // =========================
    // Getters & Setters
    // =========================

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

}