package com.aim.project.pwp.hyperheuristics;


import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.SolutionPrinter;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

/**
 * @author Warren G. Jackson
 *
 * Creates an initial solution and finishes.
 * Can be used for testing your initialisation method.
 */
public class InitialisationTest_HH extends HyperHeuristic {
	
	public InitialisationTest_HH(long seed) {
		
		super(seed);
	}

	@Override
	protected void solve(ProblemDomain problem) {
		AIM_PWP oProblem = (AIM_PWP) problem;

		System.out.println(toString() + ": ");
		System.out.println("Iteration\tf(s)\tf(s')\tAccept");

		oProblem.initialiseSolution(0);
		hasTimeExpired();

		PWPSolutionInterface oSolution = ((AIM_PWP) oProblem).getBestSolution();
		SolutionPrinter oSP = new SolutionPrinter("out.csv");
		oSP.printSolution( ((AIM_PWP) oProblem).oInstance.getSolutionAsListOfLocations(oSolution));
		System.out.println(((AIM_PWP) oProblem).bestSolutionToString());
	}

	@Override
	public String toString() {

		return "InitialisationTest_HH";
	}
}
