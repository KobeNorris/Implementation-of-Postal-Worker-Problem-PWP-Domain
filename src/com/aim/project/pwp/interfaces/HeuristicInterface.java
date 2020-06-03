package com.aim.project.pwp.interfaces;

public interface HeuristicInterface {

	/**
	 * Applies this heuristic to the solution <code>oSolution</code>
	 * and updates the objective value of the solution.
	 * @param oSolution The solution to apply the heuristic to.
	 * @param dDepthOfSearch The current depth of search setting.
	 * @param dIntensityOfMutation The current intensity of mutation setting.
	 */
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation);
	
	public boolean isCrossover();
	
	public boolean usesIntensityOfMutation();
	
	public boolean usesDepthOfSearch();
	
	public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction);
}
