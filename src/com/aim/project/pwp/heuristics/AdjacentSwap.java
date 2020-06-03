package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;
	
	private int iNumberOfLocation;
	
	public AdjacentSwap(Random oRandom) {
		this.oRandom = oRandom;
	}

	@Override
	// Randomly select an index and swap it with the Location behind it
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		int iIterationTime = 1 << (int) (dIntensityOfMutation * 5);
		
		for(int timeCounter = 0; timeCounter < iIterationTime; timeCounter++) {
			int targetIndex = this.oRandom.nextInt(this.iNumberOfLocation);
			adjacentSwap(oSolution, targetIndex);
		}
		
		return oSolution.getObjectiveFunctionValue();
	}
	
	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {
		this.oObjectiveFunction = f;
		this.iNumberOfLocation = this.oObjectiveFunction.getNumberOfLocation() - 2;
	}

	@Override
	public boolean isCrossover() {
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return false;
	}

}

