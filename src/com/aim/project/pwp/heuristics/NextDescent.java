package com.aim.project.pwp.heuristics;

import java.util.Random;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

/**
 * 
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 *
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	private int iNumberOfLocation;
	
	public NextDescent(Random oRandom) {
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		int iAcceptTime = (int) (dDepthOfSearch * 5 + 1);
		int targetIndex, iLocationCounter;
		double currentObjectiveValue, bestObjectiveValue;
		
		targetIndex = this.oRandom.nextInt(iNumberOfLocation);
		
		for(iLocationCounter = 0; iLocationCounter < this.iNumberOfLocation 
				&& iAcceptTime > 0; iLocationCounter++) {	
			bestObjectiveValue = oSolution.getObjectiveFunctionValue();
			currentObjectiveValue = adjacentSwap(oSolution, targetIndex);
			
			//TODO Add move acceptance method
			if(bestObjectiveValue > currentObjectiveValue) {
				iAcceptTime--;
				oSolution.setObjectiveFunctionValue(currentObjectiveValue);
				bestObjectiveValue = currentObjectiveValue;
			}else
				adjacentSwap(oSolution, targetIndex);
			
			targetIndex = ++targetIndex % (iNumberOfLocation);
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
		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return true;
	}
}
