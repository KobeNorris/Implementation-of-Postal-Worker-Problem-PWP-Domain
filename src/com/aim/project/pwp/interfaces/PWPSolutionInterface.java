package com.aim.project.pwp.interfaces;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public interface PWPSolutionInterface extends Cloneable {
	/**
	 * 
	 * @return The objective value of the solution.
	 */
	public double getObjectiveFunctionValue();
	
	/**
	 * 
	 * Updates the objective value of the solution.
	 * @param objectiveFunctionValue The new objective function value.
	 */
	public void setObjectiveFunctionValue(double objectiveFunctionValue);
	
	/**
	 * 
	 * @return The solution's representation.
	 */
	public SolutionRepresentationInterface getSolutionRepresentation();
	
	/**
	 * 
	 * @return The total number of locations in this solution (includes the DEPOT and HOME).
	 */
	public int getNumberOfLocations();

	/**
	 * 
	 * @return A deep clone of the solution.
	 */
	public PWPSolutionInterface clone();
}
