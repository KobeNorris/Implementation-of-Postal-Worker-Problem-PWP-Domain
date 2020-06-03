package com.aim.project.pwp.runners;

import java.awt.Color;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.hyperheuristics.MA_SP;
import com.aim.project.pwp.visualiser.PWPView;

import AbstractClasses.HyperHeuristic;

public class MA_SP_VirtualRunner extends HH_Runner_Visual{
	
	private int iNumberOfTrials = 11;
	
	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {
		
		return new MA_SP(seed);
	}
	
	public static void main(String[] args) {
		
		HH_Runner_Visual runner = new MA_SP_VirtualRunner();
		runner.run();
	}
	
	@Override
	public void run() {
		long seed, timeLimit = 60_000L;
		
		AIM_PWP oProblem;
		HyperHeuristic oHyperHeuristic;
		double dAverageResult = 0;
		double dBestResult = Double.MAX_VALUE;
		double dCurrentResult;
		
		for(int iIterationCounter = 0; iIterationCounter < iNumberOfTrials; iIterationCounter++) {
			seed = System.currentTimeMillis();
			timeLimit = 60_000L;
			oProblem = new AIM_PWP(seed);
			oProblem.loadInstance(4);
			
			oHyperHeuristic = getHyperHeuristic(seed);
			oHyperHeuristic.setTimeLimit(timeLimit);
			oHyperHeuristic.loadProblemDomain(oProblem);
			oHyperHeuristic.run();
			
			dCurrentResult = oHyperHeuristic.getBestSolutionValue();
			System.out.println("f(s_best) = " + dCurrentResult);
			new PWPView(oProblem.oInstance, oProblem, Color.RED, Color.GREEN);
			
			dAverageResult += dCurrentResult;
			if(dBestResult > dCurrentResult)
				dBestResult = dCurrentResult;
		}
		System.out.println("Aver Value = " + dAverageResult / iNumberOfTrials);
		System.out.println("Best Value = " + dBestResult);
	}
	
}
