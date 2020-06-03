package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;


public class OX implements XOHeuristicInterface {
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction;
	
	private int iNumberOfLocation;
	
	private boolean[][] aabIsSwapped;
	
	private int[] aiParentRoute1, aiParentRoute2, aiChildRoute1, aiChildRoute2;

	public OX(Random oRandom) {
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		return oSolution.getObjectiveFunctionValue();
	}

	@Override
	public double apply(PWPSolutionInterface oParent1, PWPSolutionInterface oParent2,
			PWPSolutionInterface oChild, double depthOfSearch, double dIntensityOfMutation) {
		int iIterationTime = (int) (dIntensityOfMutation);
		int  iCutPoint1, iCutPoint2, temp, iChildIndex1, iChildIndex2;
		
		System.arraycopy(oParent1.getSolutionRepresentation().getSolutionRepresentation()
				, 0, this.aiChildRoute1, 0, this.iNumberOfLocation);
		System.arraycopy(oParent2.getSolutionRepresentation().getSolutionRepresentation()
				, 0, this.aiChildRoute2, 0, this.iNumberOfLocation);
		
		for(int timeCounter = 0; timeCounter < iIterationTime; timeCounter++) {
			// Select two indexes 
			iChildIndex1 = 0;
			iChildIndex2 = 0;
			iCutPoint1 = this.oRandom.nextInt(this.iNumberOfLocation);
			iCutPoint2 = this.oRandom.nextInt(this.iNumberOfLocation);
			while(iCutPoint1 == iCutPoint2 || 
					(iCutPoint1 * iCutPoint2 == 0 && 
					iCutPoint1 + iCutPoint2 == this.iNumberOfLocation - 1))
				iCutPoint2 = this.oRandom.nextInt(this.iNumberOfLocation);
			
			if(iCutPoint2 < iCutPoint1) {
				temp = iCutPoint1;
				iCutPoint1 = iCutPoint2;
				iCutPoint2 = temp;
			}

//			System.out.println("Picked " + iCutPoint1 + " " + iCutPoint2);
			
			for(int index = 0; index < this.iNumberOfLocation; index++) {
				this.aiParentRoute1[index] = this.aiChildRoute1[index];
				this.aiParentRoute2[index] = this.aiChildRoute2[index];
				if(index >= iCutPoint1 && index <= iCutPoint2) {
					this.aabIsSwapped[0][aiParentRoute1[index]] = true;
					this.aabIsSwapped[1][aiParentRoute2[index]] = true;
				}else {
					this.aabIsSwapped[0][aiParentRoute1[index]] = false;
					this.aabIsSwapped[1][aiParentRoute2[index]] = false;
				}
			}
			
//			for(int tempIndex = 0; tempIndex < this.iNumberOfLocation; tempIndex++)
//				System.out.println(this.aabIsSwapped[0][tempIndex] + " " + this.aabIsSwapped[1][tempIndex]);
			for(int index = 0; index < this.iNumberOfLocation; index++) {
				if(index >= iCutPoint1 && index <= iCutPoint2)
					swapElement(index);
				else {
					while(this.aabIsSwapped[1][aiParentRoute1[iChildIndex1]]) {
						iChildIndex1++;	
					}
					aiChildRoute1[index] = aiParentRoute1[iChildIndex1];
					while(this.aabIsSwapped[0][aiParentRoute2[iChildIndex2]]) {
						iChildIndex2++;
					}
					aiChildRoute2[index] = aiParentRoute2[iChildIndex2];
					
//					System.out.println("1: " + iChildIndex1 + " -- 2: " + iChildIndex2);
					
					iChildIndex1++;
					iChildIndex2++;
				}
			}
		}
		
//		System.out.print("Now route1: ");
//		for(int i : aiChildRoute1)
//			System.out.print(i + " ");
//		System.out.println();
//		
//		System.out.print("Now route2: ");
//		for(int i : aiChildRoute2)
//			System.out.print(i + " ");
//		System.out.println();
		
		if(this.oRandom.nextDouble() > 0.5) {
			System.arraycopy(this.aiChildRoute1, 0,
					oChild.getSolutionRepresentation().getSolutionRepresentation(), 
					0, this.iNumberOfLocation);
		}
		else {
			System.arraycopy(this.aiChildRoute2, 0,
					oChild.getSolutionRepresentation().getSolutionRepresentation(), 
					0, this.iNumberOfLocation);
		}	
		oChild.setObjectiveFunctionValue(this.oObjectiveFunction.
				getObjectiveFunctionValue(oChild.getSolutionRepresentation()));
		
		return oChild.getObjectiveFunctionValue();
	}
	
	private void swapElement(int index) {
		int temp = this.aiChildRoute1[index];
		this.aiChildRoute1[index] = this.aiChildRoute2[index];
		this.aiChildRoute2[index] = temp;
	}
	
	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {
		this.oObjectiveFunction = f;
		this.iNumberOfLocation = this.oObjectiveFunction.getNumberOfLocation() - 2;
		this.aabIsSwapped = new boolean[2][this.iNumberOfLocation];
		this.aiParentRoute1 = new int[this.iNumberOfLocation];
		this.aiParentRoute2 = new int[this.iNumberOfLocation];
		this.aiChildRoute1 = new int[this.iNumberOfLocation];
		this.aiChildRoute2 = new int[this.iNumberOfLocation];
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
