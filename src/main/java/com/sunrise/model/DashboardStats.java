package com.sunrise.model;


public class DashboardStats {


    private int totalPatients;

    private int todayAppointments;

    private int activeDentists;
    
    private int activeUsers;

    private int totalTreatments;



    public DashboardStats() {

    }



    public int getTotalPatients() {

        return totalPatients;

    }



    public void setTotalPatients(int totalPatients) {

        this.totalPatients = totalPatients;

    }



    public int getTodayAppointments() {

        return todayAppointments;

    }



    public void setTodayAppointments(int todayAppointments) {

        this.todayAppointments = todayAppointments;

    }



    public int getActiveDentists() {

        return activeDentists;

    }



    public void setActiveDentists(int activeDentists) {

        this.activeDentists = activeDentists;

    }

    
    
    public int getActiveUsers() {

        return activeUsers;

    }



    public void setActiveUsers(int activeUsers) {

        this.activeUsers = activeUsers;

    }


    public int getTotalTreatments() {

        return totalTreatments;

    }



    public void setTotalTreatments(int totalTreatments) {

        this.totalTreatments = totalTreatments;

    }


}