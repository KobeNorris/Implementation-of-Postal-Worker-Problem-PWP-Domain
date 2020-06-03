package com.aim.project.pwp.runners;

import com.aim.project.pwp.hyperheuristics.RL_RW_HH;

import AbstractClasses.HyperHeuristic;

public class RL_RW_HH_VisualRunner extends HH_Runner_Visual{
	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {
		
		return new RL_RW_HH(seed);
	}
	
	public static void main(String[] args) {
		
		HH_Runner_Visual runner = new RL_RW_HH_VisualRunner();
		runner.run();
	}
}
