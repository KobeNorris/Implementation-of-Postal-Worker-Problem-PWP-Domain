package com.aim.project.pwp.test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.solution.PWPSolution;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.heuristics.AdjacentSwap;
import com.aim.project.pwp.heuristics.HeuristicOperators;
//import com.aim.project.pwp.heuristics.CX;
//import com.aim.project.pwp.heuristics.DavissHillClimbing;
//import com.aim.project.pwp.heuristics.InversionMutation;
//import com.aim.project.pwp.heuristics.NextDescent;
//import com.aim.project.pwp.heuristics.OX;
//import com.aim.project.pwp.heuristics.Reinsertion;
import com.aim.project.pwp.instance.InitialisationMode;
import com.aim.project.pwp.instance.reader.PWPInstanceReader;

public class TestMutationAndHillClimbing {
	
	public static void main(String[] args) {
		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "pwp" + SEP + "tramstops-85" + ".pwp";
		Path path = Paths.get(instanceName);
		
		Random random = new Random(3090193150261L);
		PWPInstanceReader oPwpReader = new PWPInstanceReader();
		PWPInstanceInterface oInstance = oPwpReader.readPWPInstance(path, random);
		
//		System.out.println(oInstance.getHomeAddress().getX() + "-" + oInstance.getHomeAddress().getY() + "\n");
//		System.out.println(oInstance.getPostalDepot().getX() + "-" + oInstance.getPostalDepot().getY() + "\n");
//		for(int index = 0; index < oInstance.getNumberOfLocations(); index++)
//			System.out.println(oInstance.getLocationForDelivery(index).getX() + "-" + oInstance.getLocationForDelivery(index).getY());

//		int[] perm = IntStream.rangeClosed(0, 4).toArray();
//		perm = ArrayMethods.shuffle(perm, random);
//		
//		for (int i : perm) {
//			System.out.println(i);
//		}
		
//		int[] a = {1,2,3,4,5};
//		int[] b = {5,4,3,2,1};
//		
//		SolutionRepresentation s1 = new SolutionRepresentation(a);
//		SolutionRepresentation s2 = (SolutionRepresentation) s1.clone();
//		s2.setSolutionRepresentation(b);
//		
//		int[] c = s2.getSolutionRepresentation();
//		c[0] = 6;
//		
//		System.out.println(s1.getSolutionRepresentation()[0] + " " + s2.getSolutionRepresentation()[0] + " " + c[0]);
	
		HeuristicOperators operator = new HeuristicOperators();
		PWPObjectiveFunction objectiveFunction = new PWPObjectiveFunction(oInstance);
		operator.setObjectiveFunction(objectiveFunction);
		
		PWPSolution oOriSolution1 = (PWPSolution) oInstance.createSolution(InitialisationMode.RANDOM);
		PWPSolution oOriSolution2 = (PWPSolution) oInstance.createSolution(InitialisationMode.RANDOM);
		oOriSolution1.setObjectiveFunctionValue(objectiveFunction.getObjectiveFunctionValue(oOriSolution1.getSolutionRepresentation()));
		oOriSolution2.setObjectiveFunctionValue(objectiveFunction.getObjectiveFunctionValue(oOriSolution2.getSolutionRepresentation()));
		
		System.out.print("Original route1:  \n");
		for(int i : oOriSolution1.getSolutionRepresentation().getSolutionRepresentation())
//			System.out.println(oInstance.getLocationForDelivery(i).toString());
			System.out.print(i + " ");
		System.out.print("\n");
		
		System.out.print("Original route2:  \n");
		for(int i : oOriSolution2.getSolutionRepresentation().getSolutionRepresentation())
//			System.out.println(oInstance.getLocationForDelivery(i).toString());
			System.out.print(i + " ");
		System.out.print("\n");
		
		System.out.println("Original value1:  " + oOriSolution1.getObjectiveFunctionValue());
		System.out.println("Original value2:  " + oOriSolution2.getObjectiveFunctionValue());

		PWPSolution oNewSolution1 = (PWPSolution)oOriSolution1.clone();
//		PWPSolution oNewSolution2 = (PWPSolution)oOriSolution2.clone();
		AdjacentSwap a = new AdjacentSwap(random);
		a.setObjectiveFunction(objectiveFunction);

		a.apply(oNewSolution1, 1.0, 1.0);
//		a.apply(oNewSolution1, oNewSolution2, oNewSolution1, 1.0, 1.0);
		
//		for(int i : oOriSolution.getSolutionRepresentation().getSolutionRepresentation())
//			System.out.print(i);
//		System.out.println();
//		
//		for(int i : oNewSolution.getSolutionRepresentation().getSolutionRepresentation())
//			System.out.print(i);
//		System.out.println();
		
		System.out.print("New route:       \n");
		for(int i : oNewSolution1.getSolutionRepresentation().getSolutionRepresentation())
//			System.out.println(oInstance.getLocationForDelivery(i).toString());
			System.out.print(i + " ");
		System.out.print("\n");
		
		System.out.println("Original value:  " + oOriSolution1.getObjectiveFunctionValue());
		System.out.println("Delta result:    " + oNewSolution1.getObjectiveFunctionValue());
		System.out.println("Standard result: " + objectiveFunction.getObjectiveFunctionValue(oNewSolution1.getSolutionRepresentation()));
	}
}
