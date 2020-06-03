package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.util.ArrayMethods;


public class InversionMutation extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	private int iNumberOfLocation;
	
	public InversionMutation(Random oRandom) {
		this.oRandom = oRandom;
	}

	@Override
	//
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		// Calculate times of applying mutation
		int iIterationTime = (int) (dIntensityOfMutation * 5 + 1);
		int iStartIndex, iEndIndex, temp;
		int[] aiRoute = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		double dObjectiveValue = oSolution.getObjectiveFunctionValue();
		
		for(int timeCounter = 0; timeCounter < iIterationTime; timeCounter++) {
			iStartIndex = this.oRandom.nextInt(this.iNumberOfLocation);
			iEndIndex = this.oRandom.nextInt(this.iNumberOfLocation);
			while(iStartIndex == iEndIndex)
				iEndIndex = this.oRandom.nextInt(this.iNumberOfLocation);
			
			temp = iStartIndex;
			if(iStartIndex > iEndIndex) {
				iStartIndex = iEndIndex;
				iEndIndex = temp;
			}
			ArrayMethods.reverse(aiRoute, iStartIndex, iEndIndex);
			
			//Recalculate the objective value
			if(this.iNumberOfLocation < 3) {
				/*
				 * Standard evaluation might be more efficient.
				 * Directly applied the standard method.
				 */
				dObjectiveValue = this.oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
			}else if(iStartIndex > 0 && iEndIndex < this.iNumberOfLocation - 1) {
				/*
				 * Reversing without route head and route tail.
				 * Add new links between break points and subtract the original cost.
				 */
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iStartIndex - 1], aiRoute[iStartIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iEndIndex], aiRoute[iEndIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iStartIndex - 1], aiRoute[iEndIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iStartIndex], aiRoute[iEndIndex + 1]);
			}else if(iStartIndex == 0 && iEndIndex == this.iNumberOfLocation - 1) {
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[0]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iEndIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iEndIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[0]);
			}else if(iStartIndex == 0) {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iEndIndex], aiRoute[iEndIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[0]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[0], aiRoute[iEndIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iEndIndex]);
			}else {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iStartIndex - 1], aiRoute[iStartIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iEndIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iStartIndex - 1], aiRoute[iEndIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iStartIndex]);
			}
		}
		oSolution.setObjectiveFunctionValue(dObjectiveValue);
		
		return dObjectiveValue;
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
