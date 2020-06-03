package com.aim.project.pwp;

import java.lang.Math;
//import java.math.BigDecimal;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

public class PWPObjectiveFunction implements ObjectiveFunctionInterface {
	
	private final PWPInstanceInterface oInstance;
	
	public PWPObjectiveFunction(PWPInstanceInterface oInstance) {
		
		this.oInstance = oInstance;
	}

	// TODO Check the jump to Delta evaluation
	@Override
	public double getObjectiveFunctionValue(SolutionRepresentationInterface oSolution) {
		double ObjectiveFunctionValue = 0.0;
		int[] locationIndexList = oSolution.getSolutionRepresentation();
		
//		ObjectiveFunctionValue = DAdd(ObjectiveFunctionValue, getCostBetweenHomeAnd(locationIndexList[0]));
		ObjectiveFunctionValue += getCostBetweenDepotAnd(locationIndexList[0]);
//		System.out.println("Now: " + ObjectiveFunctionValue);
		
		for (int index = 0; index < locationIndexList.length - 1; index++) {
//			ObjectiveFunctionValue = DAdd(ObjectiveFunctionValue, getCost(locationIndexList[index], locationIndexList[index + 1]));
			ObjectiveFunctionValue += getCost(locationIndexList[index], locationIndexList[index + 1]);
//			System.out.println("Now: " + ObjectiveFunctionValue);
		}
//		ObjectiveFunctionValue = DAdd(ObjectiveFunctionValue, getCostBetweenDepotAnd(locationIndexList[locationIndexList.length - 1]));
		ObjectiveFunctionValue += getCostBetweenHomeAnd(locationIndexList[locationIndexList.length - 1]);
//		System.out.println("Now: " + ObjectiveFunctionValue);
		
		return ObjectiveFunctionValue;
	}
	
	@Override
	// Get cost between two locations with target location indexes
	public double getCost(int iLocationA, int iLocationB) {
		Location oLocationA = oInstance.getLocationForDelivery(iLocationA);
		Location oLocationB = oInstance.getLocationForDelivery(iLocationB);
		
		double cost = 0;
//		cost = DAdd(cost, Math.pow((oLocationA.getX() - oLocationB.getX()), 2));
//		cost = DAdd(cost, Math.pow((oLocationA.getY() - oLocationB.getY()), 2));
		cost += Math.pow((oLocationA.getX() - oLocationB.getX()), 2);
		cost += Math.pow((oLocationA.getY() - oLocationB.getY()), 2);
		cost = Math.sqrt(cost);
//		showPath(oLocationA, oLocationB, cost);
		
		return cost;
	}

	@Override
	// Get cost between depot and location with target location index
	public double getCostBetweenDepotAnd(int iLocation) {
		Location oLocationA = oInstance.getLocationForDelivery(iLocation);
		Location oLocationB = oInstance.getPostalDepot();
		
		double cost = 0.0;
//		cost = DAdd(cost, Math.pow((oLocationA.getX() - oLocationB.getX()), 2));
//		cost = DAdd(cost, Math.pow((oLocationA.getY() - oLocationB.getY()), 2));
		cost += Math.pow((oLocationA.getX() - oLocationB.getX()), 2);
		cost += Math.pow((oLocationA.getY() - oLocationB.getY()), 2);
		cost = Math.sqrt(cost);
//		showPath(oLocationA, oLocationB, cost);
		
		return cost;
	}

	@Override
	// Get cost between home and location with target location index
	public double getCostBetweenHomeAnd(int iLocation) {
		Location oLocationA = oInstance.getHomeAddress();
		Location oLocationB = oInstance.getLocationForDelivery(iLocation);
		
		double cost = 0.0;
//		cost = DAdd(cost, Math.pow((oLocationA.getX() - oLocationB.getX()), 2));
//		cost = DAdd(cost, Math.pow((oLocationA.getY() - oLocationB.getY()), 2));
		cost += Math.pow((oLocationA.getX() - oLocationB.getX()), 2);
		cost += Math.pow((oLocationA.getY() - oLocationB.getY()), 2);
		cost = Math.sqrt(cost);
//		showPath(oLocationA, oLocationB, cost);
		
		return cost;
	}
	
	public void showPath(Location oLocationA, Location oLocationB, double cost) {
		System.out.println(oLocationA.toString() + " -> " + oLocationB.toString() + 
				" Cost: " + cost);
	}
	
//	public double DAdd(double value1, double value2) {
//		BigDecimal a = new BigDecimal(Double.toString(value1));
//		BigDecimal b = new BigDecimal(Double.toString(value2));
//		
//		return a.add(b).doubleValue();
//	}
	
	public int getNumberOfLocation() {
		return this.oInstance.getNumberOfLocations();
	}
}
