package com.aim.project.pwp;


import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.aim.project.pwp.heuristics.*;
import com.aim.project.pwp.instance.InitialisationMode;
import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.reader.PWPInstanceReader;
import com.aim.project.pwp.interfaces.*;
import com.aim.project.pwp.solution.PWPSolution;

import AbstractClasses.ProblemDomain;

public class AIM_PWP extends ProblemDomain implements Visualisable {

	private String[] instanceFiles = {
		"square", "libraries-15", "carparks-40", "tramstops-85", "trafficsignals-446", "streetlights-35714"
	};
	
	private PWPSolutionInterface[] aoMemoryOfSolutions;
	
	public PWPSolutionInterface oBestSolution;
	
	public PWPInstanceInterface oInstance;
	
	private HeuristicInterface[] aoHeuristics;
	
	private ObjectiveFunctionInterface oObjectiveFunction;
	
	private long seed;
		
	public AIM_PWP(long seed) {
		super(seed);
		this.seed = seed;
		
		// TODO - set default memory size and create the array of low-level heuristics
		
		aoHeuristics = new HeuristicInterface[getNumberOfHeuristics()];
		aoHeuristics[0] = new AdjacentSwap(this.rng);
		aoHeuristics[1] = new InversionMutation(this.rng);
		aoHeuristics[2] = new Reinsertion(this.rng);
		aoHeuristics[3] = new NextDescent(this.rng);
		aoHeuristics[4] = new DavissHillClimbing(this.rng);
		aoHeuristics[5] = new OX(this.rng);
		aoHeuristics[6] = new CX(this.rng);
	}
	
	public PWPSolutionInterface getSolution(int index) {
		return aoMemoryOfSolutions[index];
	}
	
	public PWPSolutionInterface[] getSolutionSet() {
		return aoMemoryOfSolutions;
	}
	
	public PWPSolutionInterface getBestSolution() {
		return oBestSolution;
	}
	
	public int getNumberOfLocation() {
		return this.oObjectiveFunction.getNumberOfLocation();
	}

	@Override
	// Apply heuristic and return the objective value of the candidate solution
	// and keep track/update the best solution
	public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {		
		if(candidateIndex != currentIndex)
			copySolution(currentIndex, candidateIndex);
		
		double dObjectiveValue = aoHeuristics[hIndex].apply(
				this.aoMemoryOfSolutions[candidateIndex], 
				depthOfSearch, 
				intensityOfMutation);
		
		updateBestSolution(candidateIndex);
		
		return dObjectiveValue;
	}

	@Override		
	// Apply heuristic and return the objective value of the candidate solution
	// and keep track/update the best solution
	public double applyHeuristic(int hIndex, 
			int parent1Index, int parent2Index, int candidateIndex) {
		
		double dObjectiveValue = ((XOHeuristicInterface)aoHeuristics[hIndex]).apply(
				this.aoMemoryOfSolutions[parent1Index], 
				this.aoMemoryOfSolutions[parent2Index], 
				this.aoMemoryOfSolutions[candidateIndex], 
				depthOfSearch, 
				intensityOfMutation);

		updateBestSolution(candidateIndex);
		
		return dObjectiveValue;
	}

	@Override
	// Return the location IDs of the best solution including DEPOT and HOME locations
	// e.g. "DEPOT -> 0 -> 2 -> 1 -> HOME"
	public String bestSolutionToString() {
		String solution = "DEPOT -> ";
		for (int i : 
			this.oBestSolution.getSolutionRepresentation().getSolutionRepresentation())
			solution = solution + i + " -> ";
		solution += "HOME";
//		String solution = this.oInstance.getPostalDepot().toString() + " -> ";
//		for (int i : 
//			this.oBestSolution.getSolutionRepresentation().getSolutionRepresentation())
//			solution = solution + this.oInstance.getLocationForDelivery(i).toString() + " -> ";
//		solution += this.oInstance.getHomeAddress().toString();
		
		return solution;
	}

	@Override
	// Return true if the objective values of the two solutions are the same, else false
	public boolean compareSolutions(int iIndexA, int iIndexB) {
		double dValueA = aoMemoryOfSolutions[iIndexA].getObjectiveFunctionValue();
		double dValueB = aoMemoryOfSolutions[iIndexB].getObjectiveFunctionValue();
		
		return dValueA == dValueB;
	}

	@Override
	// BEWARE this should copy the solution, not the reference to it!
	//			That is, that if we apply a heuristic to the solution in index 'b',
	//			then it does not modify the solution in index 'a' or vice-versa.
	public void copySolution(int iIndexA, int iIndexB) {
		this.aoMemoryOfSolutions[iIndexB].setObjectiveFunctionValue(
				this.aoMemoryOfSolutions[iIndexA].getObjectiveFunctionValue());
		System.arraycopy(this.aoMemoryOfSolutions[iIndexA].getSolutionRepresentation().getSolutionRepresentation(), 0, 
				this.aoMemoryOfSolutions[iIndexB].getSolutionRepresentation().getSolutionRepresentation(), 0, 
				this.oInstance.getNumberOfLocations() - 2);
	}
	
	public void copyBestSolution(int iIndex) {
		this.aoMemoryOfSolutions[iIndex] = this.oBestSolution.clone();
	}

	@Override
	public double getBestSolutionValue() {
		return oBestSolution.getObjectiveFunctionValue();
	}
	
	@Override
	public double getFunctionValue(int index) {
		return aoMemoryOfSolutions[index].getObjectiveFunctionValue();
	}

	@Override
	// TODO Return an array of heuristic IDs based on the heuristic's type.
	public int[] getHeuristicsOfType(HeuristicType type) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		int[] aiIndexList;
		
		switch (type.name()) {
		case "MUTATION":
			arrayList.add(0);
			arrayList.add(1);
			arrayList.add(2);
			break;
			
		case "CROSSOVER":
			for(int index = 0; index < aoHeuristics.length; index++) {
				if(aoHeuristics[index].isCrossover())
					arrayList.add(index);
			}
			break;
			
		case "RUIN_RECREATE":
			break;
			
		case "LOCAL_SEARCH":
			arrayList.add(3);
			arrayList.add(4);
			break;
			
		case "OTHER":
			break;

		default:
			break;
		}
		
		aiIndexList = new int[arrayList.size()];
		for(int index = 0; index < arrayList.size(); index++)
			aiIndexList[index] = arrayList.get(index);
		
		return aiIndexList;
	}

	@Override		
	// Return the array of heuristic IDs that use depth of search.
	public int[] getHeuristicsThatUseDepthOfSearch() {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		int[] aiIndexList;
		
		for(int index = 0; index < aoHeuristics.length; index++)
			if(aoHeuristics[index].usesDepthOfSearch())
				arrayList.add(index);
		aiIndexList = new int[arrayList.size()];
		for(int index = 0; index < arrayList.size(); index++)
			aiIndexList[index] = arrayList.get(index);
		
		return aiIndexList;
	}

	@Override
	// Return the array of heuristic IDs that use intensity of mutation.
	public int[] getHeuristicsThatUseIntensityOfMutation() {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		int[] aiIndexList;
		
		for(int index = 0; index < aoHeuristics.length; index++)
			if(aoHeuristics[index].usesIntensityOfMutation())
				arrayList.add(index);
		aiIndexList = new int[arrayList.size()];
		for(int index = 0; index < arrayList.size(); index++)
			aiIndexList[index] = arrayList.get(index);
		
		return aiIndexList;
	}

	@Override
	// Has to be hard-coded due to the design of the HyFlex framework...
	public int getNumberOfHeuristics() {
		return 7;
	}

	@Override
	// Return the number of available instances
	public int getNumberOfInstances() {
		return instanceFiles.length;
	}

	@Override
	public void initialiseSolution(int index) {
		
		// Initialize a solution in index 'index' 
		// making sure that you also update the best solution!
		this.aoMemoryOfSolutions[index] = oInstance.createSolution(InitialisationMode.RANDOM);
		
		updateBestSolution(index);
	}
	
	public void initialiseSolution() {
		
		// Initialize all solutions
		// and update the best solution!
		for(int index = 0; index < this.aoMemoryOfSolutions.length; index++)
			initialiseSolution(index);
	}

	// TODO implement the instance reader that this method uses
	//		to correctly read in the PWP instance, and set up the objective function.
	@Override
	public void loadInstance(int instanceId) {

		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "pwp" + SEP + instanceFiles[instanceId] + ".pwp";

		Path path = Paths.get(instanceName);
		Random random = new Random(seed);
		PWPInstanceReader oPwpReader = new PWPInstanceReader();
		oInstance = oPwpReader.readPWPInstance(path, random);

		oObjectiveFunction = oInstance.getPWPObjectiveFunction();
		
		for(HeuristicInterface h : aoHeuristics) {
			h.setObjectiveFunction(oObjectiveFunction);
		}
	}

	@Override
	public void setMemorySize(int size) {
		// TODO sets a new memory size
		// IF the memory size is INCREASED, then
		//		the existing solutions should be copied to the new memory at the same indices.
		// IF the memory size is DECREASED, then
		//		the first 'size' solutions are copied to the new memory.
		int length = 0;
		PWPSolutionInterface[] aoNewMemoryOfSolutions = new PWPSolution[size];
		if(this.aoMemoryOfSolutions != null)
			length = this.aoMemoryOfSolutions.length;
		for (int index = 0; index < size; index++) {
			if(index < length)
				if(aoNewMemoryOfSolutions[index] != null) {
					aoNewMemoryOfSolutions[index] = 
					this.aoMemoryOfSolutions[index];
				}else {
					if(this.oInstance != null)
						aoNewMemoryOfSolutions[index] = 
						this.oInstance.createSolution(InitialisationMode.RANDOM);
				}
		}
		
		this.aoMemoryOfSolutions = aoNewMemoryOfSolutions;
	}

	@Override
	public String solutionToString(int index) {
		String solution = "DEPOT -> ";
		for (int i : this.aoMemoryOfSolutions[index].getSolutionRepresentation().getSolutionRepresentation()) {
			solution = solution + i + " -> ";
		}
		solution += "HOME";
		
		return solution;
	}
	
	//Compare solution on input index with previous best one 
	//then update the best
	private void updateBestSolution(int index) {
		if(oBestSolution == null) {
			this.oBestSolution = aoMemoryOfSolutions[index].clone();
		}else if(oBestSolution.getObjectiveFunctionValue() > 
		aoMemoryOfSolutions[index].getObjectiveFunctionValue()) {
			this.oBestSolution.getSolutionRepresentation().setSolutionRepresentation(
					aoMemoryOfSolutions[index].getSolutionRepresentation().getSolutionRepresentation().clone());
			this.oBestSolution.setObjectiveFunctionValue(
					aoMemoryOfSolutions[index].getObjectiveFunctionValue());
		}
	}
	
	@Override
	public PWPInstanceInterface getLoadedInstance() {
		return this.oInstance;
	}

	@Override
	public Location[] getRouteOrderedByLocations() {

		int[] city_ids = getBestSolution().getSolutionRepresentation().getSolutionRepresentation();
		Location[] route = Arrays.stream(city_ids).boxed().map(getLoadedInstance()::getLocationForDelivery).toArray(Location[]::new);
		return route;
	}
	
	@Override
	// Print whose course work it is
	public String toString() {
		return "scykw1's G52AIM PWP";
	}
}
