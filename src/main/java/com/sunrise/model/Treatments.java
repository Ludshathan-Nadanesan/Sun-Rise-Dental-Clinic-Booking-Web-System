package com.sunrise.model;

public class Treatments {
	private int treatmentID;
	private String treatmentName;
	private String description;
	private int estimatedDuration;
	private double defaultFee;
	
	public Treatments() {
		
	}
	
	public Treatments(
			int treatmentID,
			String treatmentName,
			String description,
			int estimatedDuration,
			double defaultFee
			) {
		this.treatmentID = treatmentID;
		this.treatmentName = treatmentName;
		this.description = description;
		this.estimatedDuration = estimatedDuration;
		this.defaultFee = defaultFee;
	}
	
	public int getTreatmentID() {
		return treatmentID;
	}
	
	public void setTreatmentID(int treatmentID) {
		this.treatmentID = treatmentID; 
	}
	
	public String getTreatmentName() {
		return treatmentName;
	}
	
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName; 
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description; 
	}
	
	public int getEstimatedDuration() {
		return estimatedDuration;
	}
	
	public void setEstimatedDuration(int estimatedDuration) {
		this.estimatedDuration = estimatedDuration; 
	}
	
	public double getDefaultFee() {
		return defaultFee;
	}
	
	public void setDefaultFee(double defaultFee) {
		this.defaultFee = defaultFee; 
	}
	
	
	
}
