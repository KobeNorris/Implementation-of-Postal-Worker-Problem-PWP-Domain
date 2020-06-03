package com.aim.project.pwp.instance;


import java.util.ArrayList;
//import java.util.HashSet;
import java.util.Random;
import java.util.stream.IntStream;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.solution.SolutionRepresentation;
import com.aim.project.pwp.util.ArrayMethods;
import com.aim.project.pwp.solution.PWPSolution;


public class PWPInstance implements PWPInstanceInterface {
	
	private final Location[] aoLocations;
	
	private final Location oPostalDepotLocation;
	
	private final Location oHomeAddressLocation;
	
	private final int iNumberOfLocations;
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction = null;
	
	/**
	 * 
	 * @param numberOfLocations The TOTAL number of locations (including DEPOT and HOME).
	 * @param aoLocations The delivery locations.
	 * @param oPostalDepotLocation The DEPOT location.
	 * @param oHomeAddressLocation The HOME location.
	 * @param random The random number generator to use.
	 */
	public PWPInstance(int numberOfLocations, Location[] aoLocations, Location oPostalDepotLocation, Location oHomeAddressLocation, Random random) {
		
		this.iNumberOfLocations = numberOfLocations;
		this.oRandom = random;
		this.aoLocations = aoLocations;
		this.oPostalDepotLocation = oPostalDepotLocation;
		this.oHomeAddressLocation = oHomeAddressLocation;
	}

	@Override
	public PWPSolution createSolution(InitialisationMode mode) {
		PWPSolution solution = null;
		
		// TODO construct a new 'PWPSolution' using RANDOM initialisation
		switch (mode) {
		case RANDOM:
			solution = randomInitialization();
			break;

		default:
			break;
		}
		
		return solution;
	}
	
	public PWPSolution randomInitialization() {
		int[] aiSolutionRepresentation = IntStream.rangeClosed(0, getNumberOfLocations() - 3).toArray();
		ArrayMethods.shuffle(aiSolutionRepresentation, this.oRandom);
		
		SolutionRepresentation solutionRepresentation = new SolutionRepresentation(aiSolutionRepresentation);
		double dObjectiveValue = getPWPObjectiveFunction().getObjectiveFunctionValue(solutionRepresentation);
		PWPSolution solution = new PWPSolution(solutionRepresentation, dObjectiveValue);
		
		return solution;
	} 
	
	@Override
	public ObjectiveFunctionInterface getPWPObjectiveFunction() {
		
		if(oObjectiveFunction == null) {
			this.oObjectiveFunction = new PWPObjectiveFunction(this);
		}

		return oObjectiveFunction;
	}

	@Override
	public int getNumberOfLocations() {

		return iNumberOfLocations;
	}

	@Override
	public Location getLocationForDelivery(int deliveryId) {

		return aoLocations[deliveryId];
	}

	@Override
	public Location getPostalDepot() {
		
		return this.oPostalDepotLocation;
	}

	@Override
	public Location getHomeAddress() {
		
		return this.oHomeAddressLocation;
	}
	
	@Override
	public ArrayList<Location> getSolutionAsListOfLocations(PWPSolutionInterface oSolution) {
		
		// TODO return an 'ArrayList' of ALL LOCATIONS in the solution.
		ArrayList<Location> listOfLocations = new ArrayList<Location>();
		
		int[] arrayOfLocations = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		
//		listOfLocations.add(oHomeAddressLocation);
		for (int i : arrayOfLocations) {
			listOfLocations.add(getLocationForDelivery(i));
		}
//		listOfLocations.add(oPostalDepotLocation);
		
		return listOfLocations;
	}

}
