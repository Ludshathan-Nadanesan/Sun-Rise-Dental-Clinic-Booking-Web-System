package com.sunrise.model;


public class DentistTreatment {


    // dentist_treatments table
    private int id;

    private int dentistId;

    private int treatmentId;

    private double dentCommissionPerc;

    
    // calculate value
    private double commissionAmount;
    
    
    // treatment table
    private String treatmentName;
    
    private int estimatedDuration;
    
    private double defaultFee;
    

    // dentist table


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
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


    public double getDentCommissionPerc() {
        return dentCommissionPerc;
    }


    public void setDentCommissionPerc(double dentCommissionPerc) {
        this.dentCommissionPerc = dentCommissionPerc;
    }



    public double getCommissionAmount() {
        return commissionAmount;
    }


    public void setCommissionAmount(double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    
	public void setTreatmentName(String name) {
		this.treatmentName = name;
	}
	
	public String getTreatmentName() {
		return treatmentName;
	}
	
	
	public void setEstimatedDuration(int dur) {
		this.estimatedDuration = dur;
	}
	
	public int getEstimatedDuration() {
		return estimatedDuration;
	}
	
	
	public void setDefaultFee(double fee) {
		this.defaultFee = fee;
	}
    
	public double getDefaultFee() {
		return defaultFee;
	}
	
	
}