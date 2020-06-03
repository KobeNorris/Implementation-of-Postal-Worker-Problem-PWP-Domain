package com.aim.project.pwp.interfaces;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public interface XOHeuristicInterface extends HeuristicInterface {

	/**
	 * 
	 * @param oParent1            Parent solution 1
	 * @param oParent2            Parent solution 2
	 * @param dDepthOfSearch       current DOS setting
	 * @param dIntensityOfMutation currentIOM setting
	 */
	public double apply(PWPSolutionInterface oParent1, PWPSolutionInterface oParent2, PWPSolutionInterface oChild,
			double dDepthOfSearch, double dIntensityOfMutation);

}
