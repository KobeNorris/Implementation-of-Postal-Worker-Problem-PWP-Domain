package com.aim.project.pwp.hyperheuristics;

import java.util.Arrays;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.SolutionPrinter;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

/**
 * Reinforce Learning - Iterative Local Search - Improving Move  Hyper Heuristic
 * @author Kejia WU
 */
public class RLILS_IM_HH extends HyperHeuristic {
	
	private static final int CURRENT = 0;
	private static final int CANDIDATE = 1;
	private static final int MEMORY_SIZE = 2;
	private static final int SCORE_LOWWER_BOUND = 1;
	private static final int SCORE_UPPER_BOUND = 20;
	private static final int SCORE_DEFAULT = 10;
	
	public RLILS_IM_HH(long seed) {
		
		super(seed);
	}
	
	@Override
	protected void solve(ProblemDomain problem) {
		int iCountAccept = 0;
		int iteration = 0;
		
		AIM_PWP pwpProblem = (AIM_PWP) problem;
		
		int[] heuristics = HyFlexUtilities.getHeuristicSetOfTypes(problem, HeuristicType.LOCAL_SEARCH, HeuristicType.MUTATION);
		int heuristicCount = heuristics.length;
		int[] heuristicScores = new int[heuristicCount];
		Arrays.fill(heuristicScores, SCORE_DEFAULT);
		int heuristicScoreSum = SCORE_DEFAULT * heuristicCount;
		
		pwpProblem.setMemorySize(MEMORY_SIZE);
		pwpProblem.initialiseSolution(CURRENT);
		pwpProblem.initialiseSolution(CANDIDATE);
		
		double currentCost = pwpProblem.getFunctionValue(CURRENT);
		
		System.out.println(toString() + ": ");
		System.out.println("Iteration\tf(s)\tf(s')\tAccept");
		
		while(!this.hasTimeExpired()) {
			int selectedHeuristicIndex = rouletteWheelSelection(heuristicScores, heuristicScoreSum);
			int selectedHeuristic = heuristics[selectedHeuristicIndex];
			double candidateCost = pwpProblem.applyHeuristic(selectedHeuristic, CURRENT, CANDIDATE);
			
			if (currentCost - candidateCost > 0.000001) {
				iCountAccept++;
				pwpProblem.copySolution(CANDIDATE, CURRENT);
				currentCost = candidateCost;
				if (heuristicScores[selectedHeuristicIndex] < SCORE_UPPER_BOUND) {
					++heuristicScores[selectedHeuristicIndex];
					++heuristicScoreSum;
				}
			}
			else {
				if (heuristicScores[selectedHeuristicIndex] > SCORE_LOWWER_BOUND) {
					--heuristicScores[selectedHeuristicIndex];
					--heuristicScoreSum;
				}
			}
			++iteration;
		}
		
		PWPSolutionInterface bestSolution = pwpProblem.getBestSolution();
		SolutionPrinter solutionPrinter = new SolutionPrinter("out.csv");
		solutionPrinter.printSolution(
				pwpProblem.getLoadedInstance().getSolutionAsListOfLocations(bestSolution)
		);
		System.out.println(Arrays.toString(heuristicScores));
		System.out.println("Total iterations = " + iteration);
		System.out.println("Total acceptance = " + iCountAccept);
	}
	
	private int rouletteWheelSelection(int[] scores, int scoreSum) {
		int random = this.rng.nextInt(scoreSum);
		int currentSum = 0;
		int finalIndex = scores.length - 1;
		int i;
		for (i = 0; i < finalIndex; ++i) {
			currentSum += scores[i];
			if (currentSum > random)
				break;
		}
		return i;
	}
	
	@Override
	public String toString() {
		return "RLILS_IM_HH";
	}
}
