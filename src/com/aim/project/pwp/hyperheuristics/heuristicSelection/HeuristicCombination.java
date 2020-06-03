package com.aim.project.pwp.hyperheuristics.heuristicSelection;

public class HeuristicCombination {
	private final int 	iHillClimbingIndex, 
						iMutationIndex,
						iCrossoverIndex;
	
	public HeuristicCombination(int iHillClimbingIndex, int iMutationIndex, int iCrossoverIndex) {
		this.iHillClimbingIndex = iHillClimbingIndex;
		this.iMutationIndex = iMutationIndex;
		this.iCrossoverIndex = iCrossoverIndex;
	}
	
	/**
	 * Returns the HillClimbing heuristic index in the pair.
	 * @return The HillClimbing heuristic index in the pair
	 */
	public int getHillClimbingIndex() {
		
		return this.iHillClimbingIndex;
	}
	
	/**
	 * Returns the Mutation heuristic index in the pair.
	 * @return The Mutation heuristic index in the pair
	 */
	public int getMutationIndex() {
		
		return this.iMutationIndex;
	}
	
	/**
	 * Returns the Crossover heuristic index in the pair.
	 * @return The Crossover heuristic index in the pair
	 */
	public int getCrossoverIndex() {
		
		return this.iCrossoverIndex;
	}
}
