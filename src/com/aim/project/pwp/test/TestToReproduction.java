//package com.aim.project.pwp.test;
//
//import java.nio.file.FileSystems;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Random;
//
//import com.aim.project.pwp.PWPObjectiveFunction;
////import com.aim.project.pwp.heuristics.CX;
//import com.aim.project.pwp.hyperheuristics.replacement.ReplacementStrategy;
//import com.aim.project.pwp.hyperheuristics.reproduction.ReproductionMethod;
//import com.aim.project.pwp.instance.InitialisationMode;
//import com.aim.project.pwp.instance.reader.PWPInstanceReader;
//import com.aim.project.pwp.interfaces.PWPInstanceInterface;
//import com.aim.project.pwp.solution.PWPSolution;
//
//public class TestToReproduction {
//	public static void main(String[] args) {
//		String SEP = FileSystems.getDefault().getSeparator();
//		String instanceName = "instances" + SEP + "pwp" + SEP + "libraries-15" + ".pwp";
//		Path path = Paths.get(instanceName);
//		
//		Random random = new Random(311200261L);
//		PWPInstanceReader oPwpReader = new PWPInstanceReader();
//		PWPInstanceInterface oInstance = oPwpReader.readPWPInstance(path, random);
//		PWPObjectiveFunction objectiveFunction = new PWPObjectiveFunction(oInstance);
//		
//		PWPSolution[] aoParentSolution = new PWPSolution[16];
//		for(int iParentIndex = 0; iParentIndex < 16; iParentIndex++) {
//			aoParentSolution[iParentIndex] = (PWPSolution) oInstance.createSolution(InitialisationMode.RANDOM);
//			aoParentSolution[iParentIndex].setObjectiveFunctionValue(objectiveFunction.getObjectiveFunctionValue(aoParentSolution[iParentIndex].getSolutionRepresentation()));
//			System.out.print("Parent"+ iParentIndex +" route:  ");
//			for(int i : aoParentSolution[iParentIndex].getSolutionRepresentation().getSolutionRepresentation())
////				System.out.println(oInstance.getLocationForDelivery(i).toString());
//				System.out.print(i + " ");
//			System.out.print(" Objective: " + aoParentSolution[iParentIndex].getObjectiveFunctionValue() + "\n");
//		}
//		
//		ReproductionMethod a = new ReproductionMethod(aoParentSolution, 8, 16, random);
//		a.setiTournamentSize(5);
////		int[][] b = a.runTournamentSelection();
//		
//		ReplacementStrategy c = new ReplacementStrategy(aoParentSolution);
//		c.runRelacementStrategy(8);
//		
//		for(int iParentIndex = 0; iParentIndex < 8; iParentIndex++) {
//			System.out.print("Parent"+ iParentIndex +" route:  ");
//			for(int i : aoParentSolution[iParentIndex].getSolutionRepresentation().getSolutionRepresentation())
////				System.out.println(oInstance.getLocationForDelivery(i).toString());
//				System.out.print(i + " ");
//			System.out.print(" Objective: " + aoParentSolution[iParentIndex].getObjectiveFunctionValue() + "\n");
//		}
//		
////		System.out.println("Parents Pairs: ");
////		for(int[] i : b)
////			System.out.println(i[0] + " " + i[1]);
////		System.out.println();
//	}
//}
