package com.aim.project.pwp.hyperheuristics.reproduction;

import java.util.Random;
import java.util.stream.IntStream;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.util.ArrayMethods;

public class ReproductionMethod {
	
	private AIM_PWP oProblem;
	
	private int iGenerationSize = 6;
	
	private int iPopulationSize = 4;
	
	private Random rng;
	
	private int iTournamentSize;
	
	private int[] perm;
	
	public ReproductionMethod(AIM_PWP oProblem,
			int iGenerationSize, int iPopulationSize, Random rng) {
		this.oProblem = oProblem;
		this.iGenerationSize = iGenerationSize;
		this.iPopulationSize = iPopulationSize;
		this.rng = rng;
		perm = IntStream.rangeClosed(0,this.iPopulationSize - 1).toArray();
	}
	
	public void setiTournamentSize(int iTournamentSize) {
		this.iTournamentSize = iTournamentSize;
	}
	
	public void runTournamentReproduction() {
		int iParent1Index = -1, iParent2Index = -1, iChildIndex;
		double dBestValue, dCandidateValue, dCrossover = this.rng.nextDouble();
		boolean bRepeated = false;
		
		for(iChildIndex = this.iPopulationSize; iChildIndex < this.iGenerationSize; iChildIndex++) {
			dBestValue = Double.MAX_VALUE;
			ArrayMethods.shuffle(perm, this.rng);
			for(int iSizeCounter = 0; iSizeCounter < this.iTournamentSize; iSizeCounter++) {
				dCandidateValue = this.oProblem.getSolution(perm[iSizeCounter]).getObjectiveFunctionValue();
				if(dCandidateValue < dBestValue) {
					iParent1Index = perm[iSizeCounter];
					dBestValue = dCandidateValue;
				}
			}
			
			dBestValue = Double.MAX_VALUE;
			ArrayMethods.shuffle(perm, this.rng);
			for(int iSizeCounter = 0; iSizeCounter < this.iTournamentSize; iSizeCounter++) {
				// Ensure no repeat
				if(perm[iSizeCounter] == iParent1Index) {
					this.iTournamentSize++;
					iSizeCounter++;
					bRepeated = true;
				}
				
				dCandidateValue = this.oProblem.getSolution(perm[iSizeCounter]).getObjectiveFunctionValue();
				if(dCandidateValue < dBestValue) {
					iParent2Index = perm[iSizeCounter];
					dBestValue = dCandidateValue;
				}
			}
			
			if(bRepeated) {
				this.iTournamentSize--;
				bRepeated = false;
			}
			
			if(dCrossover > 0.5)
				this.oProblem.applyHeuristic(6, iParent1Index, iParent2Index, iChildIndex);
			else {
				this.oProblem.applyHeuristic(5, iParent1Index, iParent2Index, iChildIndex);
			}
		}
	}
	
	public void runTournamentReproduction(int iCrossoverIndex) {
		int iParent1Index = -1, iParent2Index = -1, iChildIndex;
		double dBestValue, dCandidateValue;
		boolean bRepeated = false;
		
		for(iChildIndex = this.iPopulationSize; iChildIndex < this.iGenerationSize; iChildIndex++) {
			dBestValue = Double.MAX_VALUE;
			ArrayMethods.shuffle(perm, this.rng);
			for(int iSizeCounter = 0; iSizeCounter < this.iTournamentSize; iSizeCounter++) {
				dCandidateValue = this.oProblem.getSolution(perm[iSizeCounter]).getObjectiveFunctionValue();
				if(dCandidateValue < dBestValue) {
					iParent1Index = perm[iSizeCounter];
					dBestValue = dCandidateValue;
				}
			}
			
			dBestValue = Double.MAX_VALUE;
			ArrayMethods.shuffle(perm, this.rng);
			for(int iSizeCounter = 0; iSizeCounter < this.iTournamentSize; iSizeCounter++) {
				// Ensure no repeat
				if(perm[iSizeCounter] == iParent1Index) {
					this.iTournamentSize++;
					iSizeCounter++;
					bRepeated = true;
				}
				
				dCandidateValue = this.oProblem.getSolution(perm[iSizeCounter]).getObjectiveFunctionValue();
				if(dCandidateValue < dBestValue) {
					iParent2Index = perm[iSizeCounter];
					dBestValue = dCandidateValue;
				}
			}
			
			if(bRepeated) {
				this.iTournamentSize--;
				bRepeated = false;
			}
			
			this.oProblem.applyHeuristic(iCrossoverIndex, iParent1Index, iParent2Index, iChildIndex);
		}
	}
}
