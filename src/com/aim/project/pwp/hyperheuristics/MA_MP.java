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

public class MA_MP extends HyperHeuristic {
	
	int iPopulationSize = 9;
	
	int iGenerationSize = 6;
	
	int iTournamentSize = 1;
	
	int iUpperBound = 100;
	
	int iLowerBound = 1;
	
	int iDefaultScore = 50;
	
	int iCandidateIndex;
	
	double dIOMUnit, dIOMUpperBound, dIOMLowerBound;
	
	public MA_MP(long lSeed) {
		super(lSeed);
	}

	@Override
	protected void solve(ProblemDomain oPDProblem) {
		
		AIM_PWP oProblem = (AIM_PWP) oPDProblem;
		
//		long iteration = 0;
		double dMAIntensityOfMutation = 0.2, dMADepthOfSearch = 0.2;
		this.iCandidateIndex = this.iPopulationSize;
//		int iNonimproveCounter = 0;

		oProblem.setIntensityOfMutation(dMAIntensityOfMutation);
		oProblem.setDepthOfSearch(dMADepthOfSearch);
		
		oProblem.setMemorySize(this.iPopulationSize + 1);
		for(int index = 0; index < this.iPopulationSize + 1; index++)
			oProblem.initialiseSolution(index);
		double dOldBestValue = oProblem.getBestSolutionValue();
		double dCurrentBestValue, dCandidateValue;
		
		ReproductionMethod oReproductionMethod = new ReproductionMethod(oProblem, iPopulationSize, iGenerationSize, rng);
		oReproductionMethod.setiTournamentSize(iTournamentSize);
		CoolingSchedule oCoolingSchedule = new LundyAndMees(dOldBestValue);
		SimulateAnnealing oMoveAcceptance = new SimulateAnnealing(oCoolingSchedule, rng);
		ReplacementStrategy oReplacementStrategy = new ReplacementStrategy(oProblem, iPopulationSize, iGenerationSize);

		dIOMUnit = 0.05 * Math.floor((Math.log(oProblem.getNumberOfLocation())/Math.log(10)));
		dIOMUpperBound = 0.8;
		dIOMLowerBound = 0.2;
		
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
		
		System.out.print(toString() + ": ");
//		System.out.println("Iteration\tf(s)\tf(s')\tAccept");
		
		while(!hasTimeExpired()) {

			oReproductionMethod.runTournamentReproduction();
			
			for(int iSolutionCounter = 0; iSolutionCounter < this.iPopulationSize; iSolutionCounter++) {
				dCandidateValue = oProblem.applyHeuristic(hPair.getMutation(), iSolutionCounter, iCandidateIndex);
				dCandidateValue = oProblem.applyHeuristic(hPair.getLocalSearch(), iCandidateIndex, iCandidateIndex);
				
				if(oMoveAcceptance.accept( oProblem.getFunctionValue(iSolutionCounter), dCandidateValue)) {
					oProblem.copySolution(iCandidateIndex, iSolutionCounter);
				}
			}
			
			dCurrentBestValue = oProblem.getBestSolutionValue();
			if(dOldBestValue - dCurrentBestValue > 0.0000001) {
				dOldBestValue = dCurrentBestValue;
				selector.incrementScore(hPair);
				if(dMAIntensityOfMutation < dIOMUpperBound) {
					dMAIntensityOfMutation += dIOMUnit;	
				}
//				System.out.println("IOM: " + dMAIntensityOfMutation + " -- DOS: " + dMADepthOfSearch);
			}else {
				selector.decrementScore(hPair);
				if(dMAIntensityOfMutation > dIOMLowerBound) {
					dMAIntensityOfMutation -= dIOMUnit;
				}
			}

			oProblem.setIntensityOfMutation(dMAIntensityOfMutation);
			
			hPair = selector.performRouletteWheelSelection();
			oCoolingSchedule.advanceTemperature();
			
			oReplacementStrategy.runRelacementStrategy();
			
//			iteration++;
		}
		PWPSolutionInterface bestSolution = oProblem.getBestSolution();
		SolutionPrinter solutionPrinter = new SolutionPrinter("out.csv");
		solutionPrinter.printSolution(
				oProblem.getLoadedInstance().getSolutionAsListOfLocations(bestSolution)
		);
//		System.out.println("Total iterations = " + iteration);
//		System.out.println(oProblem.bestSolutionToString());
	}
	
	@Override
	public String toString() {
		return "Memetic Algorithm";
	}

}
