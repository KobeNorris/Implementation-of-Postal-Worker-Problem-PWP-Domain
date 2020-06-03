package com.aim.project.pwp.hyperheuristics;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.SolutionPrinter;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

// Implementation of Greedy Hyper-heuristic
public class GR_IM_HH extends HyperHeuristic {
	
	int iGenerationSize = 5;
	
	AIM_PWP oProblem;
	
	public GR_IM_HH(long lSeed) {
		super(lSeed);
	}

	@Override
	protected void solve(ProblemDomain oPDProblem) {

		AIM_PWP oProblem = (AIM_PWP) oPDProblem;
		
		long iteration = 0;
		
		oProblem = (AIM_PWP) oProblem;
		oProblem.setIntensityOfMutation(0.2);
		oProblem.setDepthOfSearch(0.4);
		
		oProblem.setMemorySize(this.iGenerationSize);
		for(int index = 0; index < this.iGenerationSize; index++)
			oProblem.initialiseSolution(index);
		
		System.out.println(toString() + ": ");
		System.out.println("Iteration\tf(s)\tf(s')\tAccept");
		
		while(!hasTimeExpired()) {
			for(int i = 0; i < this.iGenerationSize; i++) {
				oProblem.copyBestSolution(i);
			}

			oProblem.applyHeuristic(0, 0, 0);
			oProblem.applyHeuristic(1, 1, 1);
			oProblem.applyHeuristic(2, 2, 2);
			oProblem.applyHeuristic(3, 3, 3);
			oProblem.applyHeuristic(4, 4, 4);
			
			iteration++;
		}
		PWPSolutionInterface bestSolution = oProblem.getBestSolution();
		SolutionPrinter solutionPrinter = new SolutionPrinter("out.csv");
		solutionPrinter.printSolution(
				oProblem.getLoadedInstance().getSolutionAsListOfLocations(bestSolution)
		);
		System.out.println("Total iterations = " + iteration);
		System.out.println(oProblem.bestSolutionToString());
	}

	@Override
	public String toString() {
		return "Greedy Hyper Heuristic";
	}

}
