package com.aim.project.pwp.hyperheuristics.moveAcceptance;

import java.util.Random;

public class SimulateAnnealing {
	private CoolingSchedule oCoolingSchedule;
	
	private Random random;
	
	private boolean accept;
	
//	private int iNonimproveCounter;
	
	public SimulateAnnealing(CoolingSchedule schedule, Random random) {
		this.random = random;
		this.oCoolingSchedule = schedule;
//		iNonimproveCounter = 0;
	}

	public boolean accept(double dOriObjectValue, double dNewObjectValue) {	
		double dDelta = dOriObjectValue - dNewObjectValue;
		
		// r <- random \in [0,1];
		double r = this.random.nextDouble();
		
		// Decide whether to accept this solution or not
		if(dDelta > 0 || r < Math.exp(dDelta/oCoolingSchedule.getCurrentTemperature()) ) {
			//Accept and pass clean non-improve
			this.accept = true;
//			iNonimproveCounter = 0;
		}
		else {
			//Reject and block count non-improve
			this.accept = false;
//			if(iNonimproveCounter == 120) {
//				iNonimproveCounter = 0;
//				this.oCoolingSchedule.reheatTemperature();
//				System.out.println("Reheat Temperture");
//			}
//			else {
//				iNonimproveCounter++;	
//			}
		}
		
		return this.accept;
	}
	
	public void advance() {
		// Update the cooling schedule
		oCoolingSchedule.advanceTemperature();
	}
	
	public void reheat() {
		// Update the cooling schedule
		oCoolingSchedule.reheatTemperature();
	}
		
	public String toString() {
		return "Simulated Annealing with " + oCoolingSchedule.toString();
	}
}
