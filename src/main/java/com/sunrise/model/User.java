package com.sunrise.model;

import java.sql.Timestamp;

public class User {

    private int userID;

    private String fullname;

    private String email;

    private int phone;

    private String role;

    private String password;

    private String status;

    private Timestamp createdAt;

    private Timestamp updatedAt;


    // Empty Constructor

    public User() {

    }


    // Parameterized Constructor

    public User(
            int userId,
            String fullname,
            String email,
            int phone,
            String password,
            String role,
            String status,
            Timestamp createdAt,
            Timestamp updatedAt
    ) {

        this.userID = userId;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }



    public int getUserId() {

        return userID;

    }


    public void setUserId(int userId) {

        this.userID = userId;

    }



    public String getFullname() {

        return fullname;

    }


    public void setFullname(String fullname) {

        this.fullname = fullname;

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



    public String getPassword() {

        return password;

    }


    public void setPassword(String password) {

        this.password = password;

    }



    public String getRole() {

        return role;

    }


    public void setRole(String role) {

        this.role = role;

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

}