package com.sunrise.model;

import java.sql.Timestamp;

public class Bill {
    private int billId;
    private int patientId;
    private String patientName;
    private double subTotal;
    private double taxPay;
    private double totalAmmount;
    private double paidAmmount;
    private double balanceAmmount;
    private String paymentStatus;
    private Timestamp createdAt;
    private Timestamp paidAt;

    public Bill() {}

    public Bill(int billId, int patientId, double subTotal, double taxPay, double totalAmmount, 
                double paidAmmount, double balanceAmmount, String paymentStatus, 
                Timestamp createdAt, Timestamp paidAt) {
        this.billId = billId;
        this.patientId = patientId;
        this.subTotal = subTotal;
        this.taxPay = taxPay;
        this.totalAmmount = totalAmmount;
        this.paidAmmount = paidAmmount;
        this.balanceAmmount = balanceAmmount;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
    }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }

    public double getTaxPay() { return taxPay; }
    public void setTaxPay(double taxPay) { this.taxPay = taxPay; }

    public double getTotalAmmount() { return totalAmmount; }
    public void setTotalAmmount(double totalAmmount) { this.totalAmmount = totalAmmount; }

    public double getPaidAmmount() { return paidAmmount; }
    public void setPaidAmmount(double paidAmmount) { this.paidAmmount = paidAmmount; }

    public double getBalanceAmmount() { return balanceAmmount; }
    public void setBalanceAmmount(double balanceAmmount) { this.balanceAmmount = balanceAmmount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getPaidAt() { return paidAt; }
    public void setPaidAt(Timestamp paidAt) { this.paidAt = paidAt; }
}
