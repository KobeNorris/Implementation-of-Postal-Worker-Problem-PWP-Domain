package com.aim.project.pwp.hyperheuristics;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.SolutionPrinter;
import com.aim.project.pwp.hyperheuristics.heuristicSelection.RouletteWheelHeuristicSelection;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

// Implementation of Reinforcement Learning Hyper-heuristic in heuristic Pairs
public class RL_RW_HH extends HyperHeuristic{
	int iGenerationSize = 2;
	
	AIM_PWP oProblem;
	
	public RL_RW_HH(long lSeed) {
		super(lSeed);
	}
	
	@Override
	protected void solve(ProblemDomain oProblem) {
		int acceptCounter = 0;
		int current = 0;
		int candidate = 1;
		long iteration = 0;
		
		this.oProblem = (AIM_PWP) oProblem;
		this.oProblem.setIntensityOfMutation(0.2);
		this.oProblem.setDepthOfSearch(0.2);
		
		this.oProblem.setMemorySize(this.iGenerationSize);
		for(int index = 0; index < this.iGenerationSize; index++)
			this.oProblem.initialiseSolution(index);
		
		HeuristicPair[] hs = new HeuristicPair[6];
		hs[0] = new HeuristicPair(0,3);
		hs[1] = new HeuristicPair(0,4);
		hs[2] = new HeuristicPair(1,3);
		hs[3] = new HeuristicPair(1,4);
		hs[4] = new HeuristicPair(2,3);
		hs[5] = new HeuristicPair(2,4);
		
		RouletteWheelHeuristicSelection selector = 
				new RouletteWheelHeuristicSelection(hs, 10, 1, 20, this.rng);
		
		HeuristicPair hPair = selector.performRouletteWheelSelection(); 

		System.out.println(toString() + ": ");
		System.out.println("Iteration\tf(s)\tf(s')\tAccept");

		oProblem.copySolution(current, candidate);
		
		while(!hasTimeExpired()) {
			double currentObjectiveValue = oProblem.getFunctionValue(current);
			double candidateObjectiveValue = oProblem.applyHeuristic(hPair.getMutation(), current, candidate);
			candidateObjectiveValue = oProblem.applyHeuristic(hPair.getLocalSearch(), candidate, candidate);
			
			if(currentObjectiveValue - candidateObjectiveValue > 0.00001) {
				acceptCounter++;
				oProblem.copySolution(candidate, current);
				selector.incrementScore(hPair);
			}else {
				selector.decrementScore(hPair);
			}
			//RLILS_IM_HH
			hPair = selector.performRouletteWheelSelection();
			
			iteration++;
		}
		
		PWPSolutionInterface oSolution = ((AIM_PWP) oProblem).getBestSolution();
		SolutionPrinter oSP = new SolutionPrinter("out.csv");
		oSP.printSolution( ((AIM_PWP) oProblem).oInstance.getSolutionAsListOfLocations(oSolution));
		System.out.println(String.format("Total iterations = %d", iteration));
		System.out.println(String.format("Total acceptance = %d", acceptCounter));
		System.out.println(((AIM_PWP) oProblem).bestSolutionToString());
	}
	
	@Override
	public String toString() {

		return "RL_RW_HH";
	}
}
