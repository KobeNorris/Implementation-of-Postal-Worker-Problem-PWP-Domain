package com.aim.project.pwp.interfaces;

import java.util.ArrayList;

import com.aim.project.pwp.instance.InitialisationMode;
import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.solution.PWPSolution;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public interface PWPInstanceInterface {

	/**
	 * 
	 * @param mode The initialisation mode to use. You only need to implement RANDOM initialisation!
	 * @return
	 */
	public PWPSolution createSolution(InitialisationMode mode);
	
	/**
	 * 
	 * @return The objective function used to evaluate the current problem instance.
	 */
	public ObjectiveFunctionInterface getPWPObjectiveFunction();
	
	/**
	 * 
	 * @return
	 */
	public int getNumberOfLocations();
	
	public Location getLocationForDelivery(int iDeliveryId);
	
	public Location getPostalDepot();
	
	public Location getHomeAddress();
	
	public ArrayList<Location> getSolutionAsListOfLocations(PWPSolutionInterface oSolution);
}
