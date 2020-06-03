package com.aim.project.pwp.test;

import com.aim.project.pwp.hyperheuristics.InitialisationTest_HH;
import com.aim.project.pwp.runners.HH_Runner_Visual;

import AbstractClasses.HyperHeuristic;

/**
 * @author Warren G. Jackson
 *
 *         Runs a hyper-heuristic which just initialises a solution 
 *         and displays that initial solution.
 */
public class InitialisationTest_Runner extends HH_Runner_Visual {

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {

		return new InitialisationTest_HH(seed);
	}

	public static void main(String[] args) {

		HH_Runner_Visual runner = new InitialisationTest_Runner();
		runner.run();
	}

}
