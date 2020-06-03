package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;
	
	private int iIterationTime, iNumberOfLocation;
	
	public Reinsertion(Random oRandom) {
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		double dObjectiveValue = oSolution.getObjectiveFunctionValue();
		this.iIterationTime = (int) (dIntensityOfMutation * 5 + 1);
		int iDrawIndex, iInsertIndex, temp;
		int[] aiRoute = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		
		for(int timeCounter = 0; timeCounter < this.iIterationTime; timeCounter++) {
			iDrawIndex = this.oRandom.nextInt(this.iNumberOfLocation);
			iInsertIndex = this.oRandom.nextInt(this.iNumberOfLocation);
			while(iDrawIndex == iInsertIndex)
				iInsertIndex = this.oRandom.nextInt(this.iNumberOfLocation);
			
			if(iDrawIndex > iInsertIndex) {
				temp = aiRoute[iDrawIndex];
				for(int currentIndex = iDrawIndex; currentIndex > iInsertIndex; currentIndex--)
					aiRoute[currentIndex] = aiRoute[currentIndex - 1];
				aiRoute[iInsertIndex] = temp;
				// Check the length of the route
				if(this.iNumberOfLocation < 3)
					dObjectiveValue = this.oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
				else
					dObjectiveValue += applyForwardsMove(aiRoute, iDrawIndex, iInsertIndex);
			}else{
				temp = aiRoute[iDrawIndex];
				for(int currentIndex = iDrawIndex; currentIndex < iInsertIndex; currentIndex++)
					aiRoute[currentIndex] = aiRoute[currentIndex + 1];
				aiRoute[iInsertIndex] = temp;
				// Check the length of the route
				if(this.iNumberOfLocation < 3)
					dObjectiveValue = this.oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
				else
					dObjectiveValue += applyBackwardsMove(aiRoute, iDrawIndex, iInsertIndex);
			}
		}
		oSolution.setObjectiveFunctionValue(dObjectiveValue);
		
		return dObjectiveValue;
	}
	
	// When iDrawIndex is bigger than iInsertIndex, target location is moved forwards
	private double applyForwardsMove(int[] aiRoute, int iDrawIndex, int iInsertIndex){
		double dObjectiveValue = 0;
		//Recalculate the objective value
		if(iInsertIndex == 0 && iDrawIndex == this.iNumberOfLocation - 1) {
			dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iInsertIndex + 1]);
			dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iInsertIndex]);
			dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iDrawIndex]);
			
			dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iInsertIndex]);
			dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iInsertIndex + 1]);
			dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iInsertIndex]);
		}else if(iInsertIndex == 0) {
			if(iDrawIndex - iInsertIndex > 1) {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iDrawIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iInsertIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iDrawIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iInsertIndex + 1]);
			}else {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iDrawIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iDrawIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iInsertIndex + 1]);
			}
		}else if(iDrawIndex == this.iNumberOfLocation - 1) {
			if(iDrawIndex - iInsertIndex > 1) {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iInsertIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iDrawIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iInsertIndex]);
			}else {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iDrawIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iInsertIndex]);
			}
		}else {
			if(iDrawIndex - iInsertIndex > 1) {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iInsertIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iDrawIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iDrawIndex + 1]);
			}else {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iDrawIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iDrawIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iDrawIndex + 1]);
			}
		}
		
		return dObjectiveValue;
	}
	
	// When iDrawIndex is smaller than iInsertIndex, target location is moved backwards
	private double applyBackwardsMove(int[] aiRoute, int iDrawIndex, int iInsertIndex){
		double dObjectiveValue = 0;
		//Recalculate the objective value
		if(iDrawIndex == 0 && iInsertIndex == this.iNumberOfLocation - 1) {
			dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex]);
			dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iDrawIndex]);
			dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iInsertIndex]);
			
			dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iDrawIndex]);
			dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iInsertIndex]);
			dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iInsertIndex - 1]);
		}else if(iDrawIndex == 0) {
			if(iInsertIndex - iDrawIndex > 1) {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iInsertIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iDrawIndex]);
				
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iDrawIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iInsertIndex]);
			}else {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iInsertIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iDrawIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iInsertIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iDrawIndex + 1]);
			}
		}else if(iInsertIndex == this.iNumberOfLocation - 1) {
			if(iInsertIndex - iDrawIndex > 1) {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iDrawIndex - 1], aiRoute[iDrawIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iInsertIndex]);
				
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iDrawIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iInsertIndex - 1]);
			}else {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iDrawIndex - 1], aiRoute[iDrawIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iDrawIndex]);
			}
		}else {
			if(iInsertIndex - iDrawIndex > 1) {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iDrawIndex - 1], aiRoute[iDrawIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iInsertIndex + 1]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iDrawIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iInsertIndex - 1], aiRoute[iInsertIndex + 1]);
			}else {
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iDrawIndex - 1], aiRoute[iDrawIndex]);
				dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iInsertIndex], aiRoute[iInsertIndex + 1]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex - 1], aiRoute[iInsertIndex]);
				dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iDrawIndex], aiRoute[iInsertIndex + 1]);
			}
		}
		
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
