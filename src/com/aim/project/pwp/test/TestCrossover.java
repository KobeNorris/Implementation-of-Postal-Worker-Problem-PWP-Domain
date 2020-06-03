package com.aim.project.pwp.test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.solution.PWPSolution;
import com.aim.project.pwp.PWPObjectiveFunction;
//import com.aim.project.pwp.heuristics.CX;
import com.aim.project.pwp.heuristics.OX;
import com.aim.project.pwp.instance.InitialisationMode;
import com.aim.project.pwp.instance.reader.PWPInstanceReader;

public class TestCrossover {
	
	public static void main(String[] args) {
		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "pwp" + SEP + "libraries-15" + ".pwp";
		Path path = Paths.get(instanceName);
		
		Random random = new Random(311290110261L);
		PWPInstanceReader oPwpReader = new PWPInstanceReader();
		PWPInstanceInterface oInstance = oPwpReader.readPWPInstance(path, random);
		

		PWPObjectiveFunction objectiveFunction = new PWPObjectiveFunction(oInstance);
		
		//Create and show parent1
		PWPSolution oParentSolution1 = (PWPSolution) oInstance.createSolution(InitialisationMode.RANDOM);
		oParentSolution1.setObjectiveFunctionValue(objectiveFunction.getObjectiveFunctionValue(oParentSolution1.getSolutionRepresentation()));
		System.out.print("Parent1 route:  ");
		for(int i : oParentSolution1.getSolutionRepresentation().getSolutionRepresentation())
//			System.out.println(oInstance.getLocationForDelivery(i).toString());
			System.out.print(i + " ");
		System.out.print("\n");
		
		System.out.println("Parent1 value:  " + oParentSolution1.getObjectiveFunctionValue());
		
		//Create and show parent2
		PWPSolution oParentSolution2 = (PWPSolution) oInstance.createSolution(InitialisationMode.RANDOM);
		oParentSolution2.setObjectiveFunctionValue(objectiveFunction.getObjectiveFunctionValue(oParentSolution2.getSolutionRepresentation()));
		System.out.print("Parent2 route:  ");
		for(int i : oParentSolution2.getSolutionRepresentation().getSolutionRepresentation())
//			System.out.println(oInstance.getLocationForDelivery(i).toString());
			System.out.print(i + " ");
		System.out.print("\n");
		
		System.out.println("Parent2 value:  " + oParentSolution2.getObjectiveFunctionValue());

		//Create and show child
		PWPSolution oChildSolution = (PWPSolution) oInstance.createSolution(InitialisationMode.RANDOM);
		oChildSolution .setObjectiveFunctionValue(objectiveFunction.getObjectiveFunctionValue(oParentSolution2.getSolutionRepresentation()));
//		System.out.print("Child route:    ");
//		for(int i : oChildSolution.getSolutionRepresentation().getSolutionRepresentation())
////			System.out.println(oInstance.getLocationForDelivery(i).toString());
//			System.out.print(i + " ");
//		System.out.print("\n");
//		
//		System.out.println("Child value:  " + oChildSolution.getObjectiveFunctionValue());
		
		OX a = new OX(random);
		a.setObjectiveFunction(objectiveFunction);
		a.apply(oParentSolution1, oParentSolution2, oChildSolution, 0.1, 0.1);
		
		System.out.print("New child route: ");
		for(int i : oChildSolution.getSolutionRepresentation().getSolutionRepresentation())
//			System.out.println(oInstance.getLocationForDelivery(i).toString());
			System.out.print(i + " ");
		System.out.print("\n");
	}
}
