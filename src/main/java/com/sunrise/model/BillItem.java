package com.sunrise.model;

public class BillItem {
    private int billItemId;
    private int billId;
    private int appointmentId;
    private double clinicalPay;
    private double dentistPay;
    private double chargedAmount;

    public BillItem() {}

    public BillItem(int billItemId, int billId, int appointmentId, double clinicalPay, 
                    double dentistPay, double chargedAmount) {
        this.billItemId = billItemId;
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.clinicalPay = clinicalPay;
        this.dentistPay = dentistPay;
        this.chargedAmount = chargedAmount;
    }

    public int getBillItemId() { return billItemId; }
    public void setBillItemId(int billItemId) { this.billItemId = billItemId; }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public double getClinicalPay() { return clinicalPay; }
    public void setClinicalPay(double clinicalPay) { this.clinicalPay = clinicalPay; }

    public double getDentistPay() { return dentistPay; }
    public void setDentistPay(double dentistPay) { this.dentistPay = dentistPay; }

    public double getChargedAmount() { return chargedAmount; }
    public void setChargedAmount(double chargedAmount) { this.chargedAmount = chargedAmount; }
}
