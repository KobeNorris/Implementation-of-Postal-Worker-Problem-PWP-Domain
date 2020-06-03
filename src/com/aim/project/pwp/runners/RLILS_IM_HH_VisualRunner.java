package com.aim.project.pwp.runners;

import com.aim.project.pwp.hyperheuristics.RLILS_IM_HH;
import AbstractClasses.HyperHeuristic;

public class RLILS_IM_HH_VisualRunner extends HH_Runner_Visual{

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {
		
		return new RLILS_IM_HH(seed);
	}
	
	public static void main(String[] args) {
		
		HH_Runner_Visual runner = new RLILS_IM_HH_VisualRunner();
		runner.run();
	}
	
}
