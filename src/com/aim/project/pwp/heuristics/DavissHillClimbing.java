package com.aim.project.pwp.heuristics;

import java.util.Random;
import java.util.stream.IntStream;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.util.ArrayMethods;


/**
 * 
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 *
 */
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	private int iNumberOfLocation;
	
	private double dBestObjectiveValue, dCurrentObjectiveValue;
	
	private int[] perm;
	
	public DavissHillClimbing(Random oRandom) {
		this.oRandom = oRandom;
	}

	@Override
	// Calculate the value of implementation times and call applyDavissHillClimbing()
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		int iIterationTime = (int) (dDepthOfSearch * 5 + 1);
		
		for(int timeCounter = 0; timeCounter < iIterationTime; timeCounter++) {
			this.dBestObjectiveValue = oSolution.getObjectiveFunctionValue();
			ArrayMethods.shuffle(perm, this.oRandom);
			for(int targetIndex : perm) {
				this.dCurrentObjectiveValue = adjacentSwap(oSolution, targetIndex);
				
				if(this.dBestObjectiveValue < this.dCurrentObjectiveValue) {
					// Swap route back to the original order
					adjacentSwap(oSolution, targetIndex);
				}else {
					//Update the best objective value
					this.dBestObjectiveValue = oSolution.getObjectiveFunctionValue();
				}
			}
		}
		
		return oSolution.getObjectiveFunctionValue();
	}
	
	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {
		this.oObjectiveFunction = f;
		this.iNumberOfLocation = this.oObjectiveFunction.getNumberOfLocation() - 2;
		perm = IntStream.rangeClosed(0, iNumberOfLocation - 1).toArray();
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
