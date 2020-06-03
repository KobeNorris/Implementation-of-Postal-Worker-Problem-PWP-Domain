package com.aim.project.pwp.runners;

import java.awt.Color;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.hyperheuristics.GR_IM_HH;
import com.aim.project.pwp.visualiser.PWPView;

import AbstractClasses.HyperHeuristic;

public class GR_IM_VisualRunner extends HH_Runner_Visual{

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {
		return new GR_IM_HH(seed);
	}
	
	@Override
	public void run() {
		long seed = System.currentTimeMillis();
		long timeLimit = 60_000l;
		AIM_PWP problem = new AIM_PWP(seed);
		problem.loadInstance(3);
		HyperHeuristic hh = getHyperHeuristic(seed);
		hh.setTimeLimit(timeLimit);
		hh.loadProblemDomain(problem);
		hh.run();
		
		System.out.println("f(s_best) = " + hh.getBestSolutionValue());
		new PWPView(problem.oInstance, problem, Color.RED, Color.GREEN);
	}
	
	public static void main(String [] args) {
		
		HH_Runner_Visual runner = new GR_IM_VisualRunner();
		runner.run();
	}
	
}
