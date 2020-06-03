package com.aim.project.pwp.hyperheuristics.moveAcceptance;

public interface CoolingSchedule {

	/**
	 * Gets the current temperature.
	 * @return The current temperature.
	 */
	public double getCurrentTemperature();
	
	/**
	 * Advances the temperature in accordance with the cooling schedule.
	 */
	public void advanceTemperature();
	
	public void reheatTemperature();
	
	public String toString();
}
