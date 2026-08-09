package com.sunrise.model;

import java.sql.Time;

public class DentistAvailability {

    private int availabilityId;
    private int dentistId;

    private String dayOfWeek;

    private Time startTime;
    private Time endTime;


    public DentistAvailability() {

    }


    public DentistAvailability(
            int availabilityId,
            int dentistId,
            String dayOfWeek,
            Time startTime,
            Time endTime) {

        this.availabilityId = availabilityId;
        this.dentistId = dentistId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public int getAvailabilityId() {
        return availabilityId;
    }


    public void setAvailabilityId(int availabilityId) {
        this.availabilityId = availabilityId;
    }


    public int getDentistId() {
        return dentistId;
    }


    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }


    public String getDayOfWeek() {
        return dayOfWeek;
    }


    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


    public Time getStartTime() {
        return startTime;
    }


    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }


    public Time getEndTime() {
        return endTime;
    }


    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

}