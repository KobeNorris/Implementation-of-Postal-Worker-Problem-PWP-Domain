package com.aim.project.pwp.runners;


import com.aim.project.pwp.hyperheuristics.SR_IE_HH;

import AbstractClasses.HyperHeuristic;

/**
 * @author Warren G. Jackson
 * Runs a simple random IE hyper-heuristic then displays the best solution found
 * 
 * scykw1
 * 13638408
 */
public class SR_IE_VisualRunner extends HH_Runner_Visual {

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {
		
		return new SR_IE_HH(seed);
	}
	
	public static void main(String [] args) {
		HH_Runner_Visual runner = new SR_IE_VisualRunner();
		
		for(int i = 0; i < 11; i++)
			runner.run();
	}

}