package com.sunrise.model;

import java.sql.Timestamp;

public class Tax {
    private int taxId;
    private String taxName;
    private double taxPercentage;
    private Timestamp lastUpdatedAt;

    public Tax() {
    }

    public Tax(int taxId, String taxName, double taxPercentage, Timestamp lastUpdatedAt) {
        this.taxId = taxId;
        this.taxName = taxName;
        this.taxPercentage = taxPercentage;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
