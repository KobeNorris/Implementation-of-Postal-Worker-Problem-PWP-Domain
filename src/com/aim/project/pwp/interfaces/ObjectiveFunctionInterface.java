package com.aim.project.pwp.interfaces;

/**
 * 
 * @author Warren G. Jackson
 * @since 1.0.0
 * 
 * Interface for an objective function for PWP.
 */
public interface ObjectiveFunctionInterface {

	/**
	 * 
	 * @param solutionRepresentation The representation of the current solution
	 * @return The objective function of the current solution represented by <code>solutionRepresentation</code>
	 */
	public double getObjectiveFunctionValue(SolutionRepresentationInterface solutionRepresentation);
	
	/**
	 * 
	 * @param iLocationA ID of the starting delivery location.
	 * @param iLocationB ID of the destination delivery location.
	 * @return The distance between cities <code>location_a</code> and <code>location_b</code>.
	 */
	public double getCost(int iLocationA, int iLocationB);
	
	/**
	 * 
	 * @param iLocation ID of the delivery location.
	 * @return The cost of going from the DEPOT to the location with ID iLocation.
	 */
	public double getCostBetweenDepotAnd(int iLocation);

	/**
	 * 
	 * @param iLocation ID of the delivery location.
	 * @return The cost of going from the HOME to the location with ID iLocation.
	 */
	public double getCostBetweenHomeAnd(int iLocation);
	
	public int getNumberOfLocation();
}
