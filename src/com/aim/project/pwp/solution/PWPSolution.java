package com.aim.project.pwp.solution;

import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

public class PWPSolution implements PWPSolutionInterface {

	private SolutionRepresentationInterface oRepresentation;
	
	private double dObjectiveFunctionValue;
	
	public PWPSolution(SolutionRepresentationInterface representation, double objectiveFunctionValue) {
		
		this.oRepresentation = representation;
		this.dObjectiveFunctionValue = objectiveFunctionValue;
	}

	@Override
	public double getObjectiveFunctionValue() {

		return dObjectiveFunctionValue;
	}

	@Override
	public void setObjectiveFunctionValue(double objectiveFunctionValue) {
		
		this.dObjectiveFunctionValue = objectiveFunctionValue;
	}

	@Override
	public SolutionRepresentationInterface getSolutionRepresentation() {
		
		return this.oRepresentation;
	}
	
	@Override
	public PWPSolutionInterface clone() {
		
		SolutionRepresentationInterface rep = oRepresentation.clone();
		return new PWPSolution(rep, dObjectiveFunctionValue);
	}

	@Override
	public int getNumberOfLocations() {
		
		return oRepresentation.getNumberOfLocations();
	}
}
