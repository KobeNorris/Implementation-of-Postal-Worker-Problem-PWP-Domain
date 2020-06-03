package com.aim.project.pwp.hyperheuristics;

public class HeuristicPair {
    private final int iMutationIndex, iLocalSearchIndex, iCrossoverIndex;
	
	public HeuristicPair(int iMutationIndex, int iLocalSearchIndex) {
		this.iMutationIndex = iMutationIndex;
		this.iLocalSearchIndex = iLocalSearchIndex;
		this.iCrossoverIndex = 5;
	}
	
	public HeuristicPair(int iMutationIndex, int iLocalSearchIndex, int iCrossoverIndex) {
		this.iMutationIndex = iMutationIndex;
		this.iLocalSearchIndex = iLocalSearchIndex;
		this.iCrossoverIndex = iCrossoverIndex;
	}
	
	/**
	 * Returns the first heuristic in the pair.
	 * @return The first heuristic in the pair
	 */
	public int getMutation() {
		
		return this.iMutationIndex;
	}
	
	/**
	 * Returns the last heuristic in the pair.
	 * @return The last heuristic in the pair
	 */
	public int getLocalSearch() {
		
		return this.iLocalSearchIndex;
	}
	
	/**
	 * Returns the last heuristic in the pair.
	 * @return The last heuristic in the pair
	 */
	public int getCrossover() {
		
		return this.iCrossoverIndex;
	}
	
	public String getString() {
		return this.iMutationIndex + ", " + this.iLocalSearchIndex;
	}
}
