package com.aim.project.pwp.interfaces;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public interface SolutionRepresentationInterface extends Cloneable {
	
	/**
	 * 
	 * @return The current solution representation
	 */
	public int[] getSolutionRepresentation();
	
	/**
	 * Sets the representation of the solution to the new representation.
	 * @param aiRepresentation The new representation
	 */
	public void setSolutionRepresentation(int[] aiRepresentation);
	
	/**
	 * 
	 * @return The total number of locations in this instance (includes DEPOT and HOME).
	 */
	public int getNumberOfLocations();

	/**
	 * 
	 * @return A deep clone of the solution representation.
	 */
	public SolutionRepresentationInterface clone();
}
