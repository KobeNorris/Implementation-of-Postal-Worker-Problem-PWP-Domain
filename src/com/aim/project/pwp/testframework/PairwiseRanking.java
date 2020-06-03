package com.aim.project.pwp.testframework;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.hyperheuristics.RL_RWSA_HH;
import com.aim.project.pwp.hyperheuristics.SR_IE_HH;

import AbstractClasses.HyperHeuristic;

public class PairwiseRanking extends HyFlexTestFrame{
	
	private final int[] aiInstanceIndex = {3};
	
	private double[] adMYHeuristicResults, adSRHeuristicResults;
	
	public PairwiseRanking() {
		super();
		this.adMYHeuristicResults = new double[getTotalRuns()];
		this.adSRHeuristicResults = new double[getTotalRuns()];
	}
	
	protected HyperHeuristic getSRHyperHeuristic(long seed) {
		return new SR_IE_HH(seed);
	}
	
	protected HyperHeuristic getMYHyperHeuristic(long seed) {
		return new RL_RWSA_HH(seed);
	}
	
	public void run() {
		double iMYScore = 0, iSRScore = 0, dTemp;
		
		for(int iInstanceIndex = 0; iInstanceIndex < this.aiInstanceIndex.length; iInstanceIndex++) {
			runInstanceCompare(iInstanceIndex);
			for(int iIterationCounter = 0; iIterationCounter < getTotalRuns(); iIterationCounter++) {
				dTemp = adMYHeuristicResults[iIterationCounter] - adSRHeuristicResults[iIterationCounter];
				if(dTemp > 0.000000001 || dTemp < -0.000000001) {
					if(dTemp > 0) {
						iMYScore += 1;
						System.out.printf("SR_IE_HH: 0.0 -- YOUR_HH: 1.0\n");
					}
					else {
						iSRScore += 1;
						System.out.printf("SR_IE_HH: 1.0 -- YOUR_HH: 0.0\n");
					}
				}
				else {
					iMYScore += 0.5;
					iSRScore += 0.5;
					System.out.print("SR_IE_HH: 0.5 -- YOUR_HH: 0.5\n");
				}
			}
		}
		
		System.out.print("SR_IE_HH Aver Rank: " + iSRScore / (getTotalRuns() * this.aiInstanceIndex.length));
		System.out.print("    YOUR_HH Aver Rank: " + iMYScore / (getTotalRuns() * this.aiInstanceIndex.length));
		System.out.println();
	}
	
	public void runInstanceCompare(int iInstanceIndex) {
		AIM_PWP oProblem;
		HyperHeuristic oHyperHeuristic;
		double dAverageResult = 0;
		double dBestResult = Double.MAX_VALUE;
		
		System.out.println("Problem Instance ID: " + this.aiInstanceIndex[iInstanceIndex]);
		System.out.println("Number of Trials: " + getTotalRuns() + "\n\n");
		
		for(int iIterationCounter = 0; iIterationCounter < getTotalRuns(); iIterationCounter++) {
			oProblem = new AIM_PWP(SEEDS[iIterationCounter]);
			oProblem.loadInstance(this.aiInstanceIndex[iInstanceIndex]);
			
			oHyperHeuristic = getMYHyperHeuristic(SEEDS[iIterationCounter]);
			oHyperHeuristic.setTimeLimit(MILLISECONDS_IN_TEN_MINUTES);
			oHyperHeuristic.loadProblemDomain(oProblem);
			oHyperHeuristic.run();
			
			adMYHeuristicResults[iIterationCounter] = oHyperHeuristic.getBestSolutionValue();
			System.out.println(iIterationCounter + " Trial Result: " + adMYHeuristicResults[iIterationCounter]);
			
			dAverageResult += adMYHeuristicResults[iIterationCounter];
			if(dBestResult > adMYHeuristicResults[iIterationCounter])
				dBestResult = adMYHeuristicResults[iIterationCounter];
		}
		System.out.println();
		System.out.println("Memetic Algorithm Aver Value = " + dAverageResult / getTotalRuns());
		System.out.println("Memetic Algorithm Best Value = " + dBestResult);
		System.out.println("\n");
		
		dAverageResult = 0;
		dBestResult = Double.MAX_VALUE;
		
		for(int iIterationCounter = 0; iIterationCounter < getTotalRuns(); iIterationCounter++) {
			oProblem = new AIM_PWP(SEEDS[iIterationCounter]);
			oProblem.loadInstance(this.aiInstanceIndex[iInstanceIndex]);
			
			oHyperHeuristic = getSRHyperHeuristic(SEEDS[iIterationCounter]);
			oHyperHeuristic.setTimeLimit(MILLISECONDS_IN_TEN_MINUTES);
			oHyperHeuristic.loadProblemDomain(oProblem);
			oHyperHeuristic.run();
			
			adSRHeuristicResults[iIterationCounter] = oHyperHeuristic.getBestSolutionValue();
			System.out.println(iIterationCounter + " Trial Result: " + adSRHeuristicResults[iIterationCounter]);
			
			dAverageResult += adSRHeuristicResults[iIterationCounter];
			if(dBestResult > adSRHeuristicResults[iIterationCounter])
				dBestResult = adSRHeuristicResults[iIterationCounter];
		}
		System.out.println();
		System.out.println("SR_IE_HH Aver Value = " + dAverageResult / getTotalRuns());
		System.out.println("SR_IE_HH Best Value = " + dBestResult);
		System.out.println("--------------------------------------------------------------");
	}

	public static void main(String[] args) {
		PairwiseRanking runner = new PairwiseRanking();
		runner.run();
	}
}