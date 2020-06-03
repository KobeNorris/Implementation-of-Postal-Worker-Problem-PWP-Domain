package com.aim.project.pwp.hyperheuristics.heuristicSelection;

import java.util.LinkedHashMap;
import java.util.Random;
import java.util.stream.Collectors;

import com.aim.project.pwp.hyperheuristics.HeuristicPair;

public class RouletteWheelHeuristicSelection {
	public HeuristicPair[] aoHeuristicPairs;

	public LinkedHashMap<HeuristicPair, Integer> oHeuristicScores;

	public final int iUpperBound;

	public final int iLowerBound;

	public final int iDefaultScore;

	public final Random rng;
	
	private int iTotalScore;
	
	/**
	 * Constructs a Roulette Wheel Selection method using a LinkedHashMap.
	 * 
	 * @param heuristic_ids The set of heuristic IDs.
	 * @param default_score The default score to give each heuristic.
	 * @param lower_bound   The lower bound on the heuristic scores.
	 * @param upper_bound   The upper bound on the heuristic scores.
	 * @param rng           The random number generator.
	 */
	public RouletteWheelHeuristicSelection(
			HeuristicPair[] hs, 
			int default_score, 
			int lower_bound, 
			int upper_bound, 
			Random rng) {

		this(new LinkedHashMap<HeuristicPair, Integer>(), 
				hs, default_score, lower_bound, upper_bound, rng);
	}
	
	/**
	 * Constructs a Roulette Wheel Selection method using the supplied Map.
	 * 
	 * @param heuristic_scores An empty Map for mapping heuristic IDs to heuristic
	 *                         scores.
	 * @param hs               The set of heuristic IDs for the (mutation,
	 *                         local_search) operators.
	 * @param default_score    The default score to give each heuristic.
	 * @param lower_bound      The lower bound on the heuristic scores.
	 * @param upper_bound      The upper bound on the heuristic scores.
	 * @param rng              The random number generator.
	 */
	public RouletteWheelHeuristicSelection(
			LinkedHashMap<HeuristicPair, Integer> heuristic_scores, 
			HeuristicPair[] hs,
			int default_score, 
			int lower_bound, 
			int upper_bound, 
			Random rng) {

		this.aoHeuristicPairs = hs;
		this.oHeuristicScores = heuristic_scores;
		this.iUpperBound = upper_bound;
		this.iLowerBound = lower_bound;
		this.iDefaultScore = default_score;
		this.rng = rng;

		// Initialize scores to the default_score
		for (HeuristicPair h : hs) {
			this.oHeuristicScores.put(h, default_score);
		}
		
		this.iTotalScore = default_score * hs.length;
	}
	
	/**
	 * 
	 * @param h The HeuristicPair to get the score of.
	 * @return The score for the aforementioned heuristic. If the HeuristicScore
	 *         does not exist, then you must return 0.
	 */
	public int getScore(HeuristicPair h) {
		if(oHeuristicScores.get(h) == null)
			return 0;
		else
			return oHeuristicScores.get(h);
	}

	/**
	 * Increments the score (by 1) of the specified heuristic whilst respecting the
	 * upper and lower bounds.
	 * 
	 * @param h The HeuristicPair whose score should be incremented.
	 */
	public void incrementScore(HeuristicPair h) {
		int score = getScore(h);

		if(score < iUpperBound) {
			score++;
			this.iTotalScore++;
		}
		oHeuristicScores.put(h, score);
	}

	/**
	 * Decrements the score (by 1) of the specified heuristic respecting the upper
	 * and lower bounds.
	 * 
	 * @param h The HeuristicPair whose score should be decremented.
	 */
	public void decrementScore(HeuristicPair h) {
		int score = getScore(h);
		
		if(score > iLowerBound) {
			score--;
			this.iTotalScore--;
		}
		oHeuristicScores.put(h, score);
	}

	/**
	 * 
	 * @return A heuristic based on the RWS method.
	 */
	public HeuristicPair performRouletteWheelSelection() {
		int randomValue = rng.nextInt(this.iTotalScore);
		int cumulativeScore = 0;
		HeuristicPair chosenHeuristic = null;
		
		for(HeuristicPair h : oHeuristicScores.keySet()){
			chosenHeuristic = h; 
			cumulativeScore += oHeuristicScores.get(h);
			if(cumulativeScore >= randomValue)
				break;
		}
		
		return chosenHeuristic;
	}

	/****************************************
	 * Utility methods useful for debugging
	 ****************************************/

	/**
	 * Prints the heuristic IDs into the console.
	 */
	public void printHeuristicIds() {

		String ids = "["
				+ oHeuristicScores.entrySet().stream().map(e -> e.getKey().toString()).collect(Collectors.joining(", "))
				+ "]";
		System.out.println("IDs    = " + ids);
	}

	/**
	 * Prints the heuristic scores into the console.
	 */
	public void printHeuristicScores() {

		String scores = "[" + oHeuristicScores.entrySet().stream().map(e -> e.getValue().toString())
				.collect(Collectors.joining(", ")) + "]";
		System.out.println("Scores = " + scores);
	}
}
