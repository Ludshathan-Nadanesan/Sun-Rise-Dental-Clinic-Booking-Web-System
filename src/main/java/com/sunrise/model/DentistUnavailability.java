package com.sunrise.model;

import java.time.LocalDateTime;

public class DentistUnavailability {

    private int unavailabilityId;
    private int dentistId;

    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;

    private String reason;

    // Optional dentist details
    private String dentistName;


    public DentistUnavailability() {
    }


    public int getUnavailabilityId() {
        return unavailabilityId;
    }

    public void setUnavailabilityId(int unavailabilityId) {
        this.unavailabilityId = unavailabilityId;
    }


    public int getDentistId() {
        return dentistId;
    }

    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }


    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }


    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

}