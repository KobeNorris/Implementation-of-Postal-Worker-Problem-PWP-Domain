package com.aim.project.pwp.hyperheuristics;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.SolutionPrinter;
import com.aim.project.pwp.hyperheuristics.heuristicSelection.RouletteWheelHeuristicSelection;
import com.aim.project.pwp.hyperheuristics.moveAcceptance.CoolingSchedule;
import com.aim.project.pwp.hyperheuristics.moveAcceptance.LundyAndMees;
import com.aim.project.pwp.hyperheuristics.moveAcceptance.SimulateAnnealing;
import com.aim.project.pwp.hyperheuristics.replacement.ReplacementStrategy;
import com.aim.project.pwp.hyperheuristics.reproduction.ReproductionMethod;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class MA_SP extends HyperHeuristic {
	
	int iGenerationSize = 6;
	
	int iPopulationSize = 4;
	
	int iTournamentSize = 3;
	
	int iUpperBound = 20;
	
	int iLowerBound = 1;
	
	int iDefaultScore = 10;
	
	int iCandidateIndex;
	
	public MA_SP(long lSeed) {
		super(lSeed);
	}

	@Override
	protected void solve(ProblemDomain oPDProblem) {
		
		AIM_PWP oProblem = (AIM_PWP) oPDProblem;
		
		long iteration = 0;
		int acceptCounter = 0;
		double dMAIntensityOfMutation = 0.2, dMADepthOfSearch = 0.2;
		this.iCandidateIndex = this.iGenerationSize;

		oProblem.setIntensityOfMutation(dMAIntensityOfMutation);
		oProblem.setDepthOfSearch(dMADepthOfSearch);
		
		oProblem.setMemorySize(this.iGenerationSize + 1);
		for(int index = 0; index < this.iGenerationSize + 1; index++)
			oProblem.initialiseSolution(index);
		double dOldBestValue = oProblem.getBestSolutionValue();
		double dCurrentBestValue, dCandidateValue;
		
		ReproductionMethod oReproductionMethod = new ReproductionMethod(oProblem, iGenerationSize, iPopulationSize, rng);
		oReproductionMethod.setiTournamentSize(iTournamentSize);
		CoolingSchedule oCoolingSchedule = new LundyAndMees(dOldBestValue);
		SimulateAnnealing oMoveAcceptance = new SimulateAnnealing(oCoolingSchedule, rng);
		ReplacementStrategy oReplacementStrategy = new ReplacementStrategy(oProblem, iGenerationSize, iPopulationSize);
		
		HeuristicPair[] hs = new HeuristicPair[6];
		hs[0] = new HeuristicPair(0,3);
		hs[1] = new HeuristicPair(0,4);
		hs[2] = new HeuristicPair(1,3);
		hs[3] = new HeuristicPair(1,4);
		hs[4] = new HeuristicPair(2,3);
		hs[5] = new HeuristicPair(2,4);
		
		RouletteWheelHeuristicSelection selector = 
				new RouletteWheelHeuristicSelection(hs, iDefaultScore, iLowerBound, iUpperBound, this.rng);
		HeuristicPair hPair = selector.performRouletteWheelSelection();
		
		System.out.println(toString() + ": ");
		System.out.println("Iteration\tf(s)\tf(s')\tAccept");
		
		while(!hasTimeExpired()) {

			oReproductionMethod.runTournamentReproduction();
			
			for(int iSolutionCounter = 0; iSolutionCounter < this.iGenerationSize; iSolutionCounter++) {
				dCandidateValue = oProblem.applyHeuristic(hPair.getMutation(), iSolutionCounter, iCandidateIndex);
				dCandidateValue = oProblem.applyHeuristic(hPair.getLocalSearch(), iCandidateIndex, iCandidateIndex);
				
				if(oMoveAcceptance.accept( oProblem.getFunctionValue(iSolutionCounter), dCandidateValue)) {
					oProblem.copySolution(iCandidateIndex, iSolutionCounter);
				}
			}
			
			dCurrentBestValue = oProblem.getBestSolutionValue();
			if(dOldBestValue - dCurrentBestValue > 0.00001) {
				acceptCounter++;
				dOldBestValue = dCurrentBestValue;
				selector.incrementScore(hPair);
			}else {
				selector.decrementScore(hPair);
			}
			
			hPair = selector.performRouletteWheelSelection();
			oCoolingSchedule.advanceTemperature();
			
			oReplacementStrategy.runRelacementStrategy();
			
			
			iteration++;
		}
		PWPSolutionInterface bestSolution = oProblem.getBestSolution();
		SolutionPrinter solutionPrinter = new SolutionPrinter("out.csv");
		solutionPrinter.printSolution(
				oProblem.getLoadedInstance().getSolutionAsListOfLocations(bestSolution)
		);
		System.out.println("Total iterations = " + iteration);
		System.out.println("Total acceptance = " + acceptCounter);
		System.out.println(oProblem.bestSolutionToString());
	}

	@Override
	public String toString() {
		return "Generic Algorithm";
	}

}
