package com.aim.project.pwp.hyperheuristics.moveAcceptance;

public class LundyAndMees implements CoolingSchedule{
	
	private double dCurrentTemperature;
	
	private final double dBeta;
	
	public LundyAndMees(double initTemperature) {
		double c = 1.0d;
		this.dCurrentTemperature = c * initTemperature;
		this.dBeta = 0.001d;
	}

	@Override
	public double getCurrentTemperature() {
		return dCurrentTemperature;
	}

	@Override
	//Update current temperature
	public void advanceTemperature() {
		dCurrentTemperature = dCurrentTemperature / (1 + dCurrentTemperature * dBeta);
	}
	
	public void reheatTemperature() {
		dCurrentTemperature = dCurrentTemperature / (1 - dCurrentTemperature * dBeta);
	}

	@Override
	public String toString() {
		return "Lundy and Mees";
	}
}
