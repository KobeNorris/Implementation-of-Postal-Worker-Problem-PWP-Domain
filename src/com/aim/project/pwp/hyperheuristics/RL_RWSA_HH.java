package com.aim.project.pwp.hyperheuristics;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.SolutionPrinter;
import com.aim.project.pwp.hyperheuristics.heuristicSelection.RouletteWheelHeuristicSelection;
import com.aim.project.pwp.hyperheuristics.moveAcceptance.LundyAndMees;
import com.aim.project.pwp.hyperheuristics.moveAcceptance.SimulateAnnealing;
import com.aim.project.pwp.interfaces.CoolingSchedule;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class RL_RWSA_HH extends HyperHeuristic {
	
	private int iPopulationSize = 1;
	
	private int iUpperBound = 200;
	
	private int iLowerBound = 1;
	
	private int iDefaultScore = 100;
	
	public RL_RWSA_HH(long lSeed) {
		super(lSeed);
	}

	@Override
	protected void solve(ProblemDomain oPDProblem) {
		AIM_PWP oProblem = (AIM_PWP) oPDProblem;
		oProblem.setMemorySize(this.iPopulationSize + 1);
		for(int index = 0; index < this.iPopulationSize + 1; index++)
			oProblem.initialiseSolution(index);
		int iCurrentIndex = 0, iCandidateIndex = 1;
		double dOldBestValue = oProblem.getBestSolutionValue();
		double dCurrentBestValue, dCandidateValue;
		
		double dMAIntensityOfMutation = 0.4, dMADepthOfSearch = 0.2;
		double dIOMUpperBound = 0.8, dIOMLowerBound = 0.2;
		double dIOMUnit = 0.05 * Math.floor((Math.log(oProblem.getNumberOfLocation())/Math.log(10)));
		
		oProblem.setIntensityOfMutation(dMAIntensityOfMutation);
		oProblem.setDepthOfSearch(dMADepthOfSearch);
		
		CoolingSchedule oCoolingSchedule = new LundyAndMees(dOldBestValue);
		SimulateAnnealing oMoveAcceptance = new SimulateAnnealing(oCoolingSchedule, rng);
		
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
		
		while(!hasTimeExpired()) {
			dCandidateValue = oProblem.applyHeuristic(hPair.getMutation(), iCurrentIndex, iCandidateIndex);
			dCandidateValue = oProblem.applyHeuristic(hPair.getLocalSearch(), iCandidateIndex, iCandidateIndex);
			
			if(oMoveAcceptance.accept( oProblem.getFunctionValue(iCurrentIndex), dCandidateValue)) {
				oProblem.copySolution(iCandidateIndex, iCurrentIndex);
			}
			
			dCurrentBestValue = oProblem.getBestSolutionValue();
			if(dOldBestValue - dCurrentBestValue > 0.0000001) {
				dOldBestValue = dCurrentBestValue;
				selector.incrementScore(hPair);
				if(dMAIntensityOfMutation < dIOMUpperBound) {
					dMAIntensityOfMutation += dIOMUnit;	
				}
			}else {
				selector.decrementScore(hPair);
				if(dMAIntensityOfMutation > dIOMLowerBound) {
					dMAIntensityOfMutation -= dIOMUnit;
				}
			}
			oProblem.setIntensityOfMutation(dMAIntensityOfMutation);
			hPair = selector.performRouletteWheelSelection();
			oCoolingSchedule.advanceTemperature();
		}
		
		PWPSolutionInterface bestSolution = oProblem.getBestSolution();
		SolutionPrinter solutionPrinter = new SolutionPrinter("out.csv");
		solutionPrinter.printSolution(
				oProblem.getLoadedInstance().getSolutionAsListOfLocations(bestSolution)
		);
	}
	
	@Override
	public String toString() {
		return "RL_RWSA_HH";
	}

}
