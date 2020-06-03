package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;

/**
 * Cycle Crossover
 * [Generated from AIM Lecture No.6]
 * 
 * @author Kejia WU
 * @version 1.2
 */
public class CX implements XOHeuristicInterface {
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction;
	
	private int iNumberOfLocation;
	
	private int[] aiParentRoute1, aiParentRoute2, aiLocationMap;

	public CX(Random oRandom) {
		this.oRandom = oRandom;
	}

	@Override
	/**
	 * Apply circle crossover to single solution generates the solution itself
	 */
	public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
		return solution.getObjectiveFunctionValue();
	}

	@Override
	/** 
	 * Apply circular crossover to parent1 and parent2 and store child on child index
	 */
	public double apply(PWPSolutionInterface oParent1, PWPSolutionInterface oParent2,
			PWPSolutionInterface oChild, double depthOfSearch, double dIntensityOfMutation) {
		int targetIndex, startValue, targetValue, nextIndex;
		
		// Calculate the iteration time of circular crossover process
		int iIterationTime = (int) (dIntensityOfMutation * 5 + 1);
		
		// Initialize parent routes
		System.arraycopy(oParent1.getSolutionRepresentation().getSolutionRepresentation()
				, 0, this.aiParentRoute1, 0, this.iNumberOfLocation);
		System.arraycopy(oParent2.getSolutionRepresentation().getSolutionRepresentation()
				, 0, this.aiParentRoute2, 0, this.iNumberOfLocation);
		// Initialize location map for parent route 1
		for(int index = 0; index < this.iNumberOfLocation; index++) {
			this.aiLocationMap[aiParentRoute1[index]] = index;
		}

		for(int timeCounter = 0; timeCounter < iIterationTime; timeCounter++) {
			targetIndex = this.oRandom.nextInt(this.iNumberOfLocation);
			startValue = aiParentRoute1[targetIndex];
//			System.out.println("Picked " + startValue);
			targetValue = aiParentRoute2[targetIndex];
			nextIndex = this.aiLocationMap[targetValue];
//			System.out.println("nextIndex: " + nextIndex);
			swapElement(targetIndex);
//			System.out.println("Swap 1:" + startValue + " - 2:" + targetValue);
			
			while(targetValue != startValue) {
				this.aiLocationMap[targetValue] = targetIndex;
				targetIndex = nextIndex;
				targetValue = aiParentRoute2[targetIndex];
				nextIndex = this.aiLocationMap[targetValue];
//				System.out.println("nextIndex: " + nextIndex);
//				System.out.println("Swap 1:" + aiParentRoute1[targetIndex] + " - 2:" + aiParentRoute2[targetIndex]);
				swapElement(targetIndex);
			}
			this.aiLocationMap[targetValue] = targetIndex;
		}
		
		if(this.oRandom.nextDouble() > 0.5) {
			System.arraycopy(this.aiParentRoute1, 0, 
					oChild.getSolutionRepresentation().getSolutionRepresentation(), 
					0, this.iNumberOfLocation);	
		}
		else {
			System.arraycopy(this.aiParentRoute2, 0, 
					oChild.getSolutionRepresentation().getSolutionRepresentation(), 
					0, this.iNumberOfLocation);
		}
		oChild.setObjectiveFunctionValue(this.oObjectiveFunction.
				getObjectiveFunctionValue(oChild.getSolutionRepresentation()));
		
		return oChild.getObjectiveFunctionValue();
	}
	
	// Swap element of aiParentRoute1 and aiParentRoute2 at index
	private void swapElement(int index) {
		int temp = this.aiParentRoute1[index];
		this.aiParentRoute1[index] = this.aiParentRoute2[index];
		this.aiParentRoute2[index] = temp;
	}

	// Set the objective function for current instance and reset elements' containers
	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {
		this.oObjectiveFunction = oObjectiveFunction;
		this.iNumberOfLocation = this.oObjectiveFunction.getNumberOfLocation() - 2;
		this.aiLocationMap = new int[this.iNumberOfLocation];
		this.aiParentRoute1 = new int[this.iNumberOfLocation];
		this.aiParentRoute2 = new int[this.iNumberOfLocation];
	}
	
	@Override
	public boolean isCrossover() {
		return true;
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
