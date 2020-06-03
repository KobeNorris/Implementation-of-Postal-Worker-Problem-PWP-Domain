package com.aim.project.pwp.hyperheuristics.replacement;

import com.aim.project.pwp.AIM_PWP;

public class ReplacementStrategy {
	
	private AIM_PWP oProblem;
	
	private int iGenerationSize, iPopulationSize;
	
	public ReplacementStrategy(AIM_PWP oProblem, int iGenerationSize, int iPopulationSize) {
		this.oProblem = oProblem;
		this.iGenerationSize = iGenerationSize;
		this.iPopulationSize = iPopulationSize;
	}
	
	public void runRelacementStrategy() {
		generationalReplacement();
	}
	
	private void generationalReplacement() {
		int iParentIndex, iTargetIndex;
		double dCandidateObjectiveValue, dCurrentObjectiveValue;
		
		for(int iCandidateIndex = iPopulationSize; 
				iCandidateIndex < iGenerationSize; 
				iCandidateIndex++) {
			iTargetIndex = -1;
			dCandidateObjectiveValue = 
					this.oProblem.getSolution(iCandidateIndex).getObjectiveFunctionValue();
			
			for(iParentIndex = 0; iParentIndex < this.iPopulationSize; iParentIndex++) {
				dCurrentObjectiveValue = this.oProblem.getSolution(iParentIndex).getObjectiveFunctionValue();
				if(dCurrentObjectiveValue > dCandidateObjectiveValue) {
					iTargetIndex = iParentIndex;
					dCandidateObjectiveValue = dCurrentObjectiveValue;
				}
			}
			if(iTargetIndex != -1) {
				this.oProblem.copySolution(iCandidateIndex, iTargetIndex);
			}
		}
	}
	
//	private void steadyStateReplacement() {
//		for(int iCandidateIndex = iPopulationSize; 
//				iCandidateIndex < iGenerationSize; 
//				iCandidateIndex++) {
//			
//		}
//	}
}
