package com.aim.project.pwp.heuristics;

//import java.math.BigDecimal;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.util.ArrayMethods;

public class HeuristicOperators {

	public ObjectiveFunctionInterface oObjectiveFunction;

	public HeuristicOperators() {
	}

	public void setObjectiveFunction(ObjectiveFunctionInterface f) {
		this.oObjectiveFunction = f;
	}

	/**
	 * TODO implement any common functionality here so that your
	 * 			heuristics can reuse them!
	 * E.g.  you may want to implement the swapping of two delivery locations here!
	 */
	public double adjacentSwap(PWPSolutionInterface oSolution ,int iTargetIndex) {
		double dObjectiveValue = oSolution.getObjectiveFunctionValue();
		
		int[] aiRoute = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		ArrayMethods.swapAdjacent(aiRoute, iTargetIndex);

		if(aiRoute.length < 3) {
			// If the length of the route is less than 3
			dObjectiveValue = this.oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
		}else if(iTargetIndex == 0) {
			// When swapped indexes include the head
			dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[1], aiRoute[2]);
			dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[0]);
			dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[0], aiRoute[2]);
			dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[1]);
		}else if (iTargetIndex == aiRoute.length - 2) {
			// When swapped indexes include the tail
			dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iTargetIndex - 1], aiRoute[iTargetIndex]);
			dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iTargetIndex + 1]);
			dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iTargetIndex - 1], aiRoute[iTargetIndex + 1]);
			dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iTargetIndex]);
		}else if (iTargetIndex == aiRoute.length - 1) {
			// When swapped indexes include the head and tail
			dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[0], aiRoute[1]);
			dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iTargetIndex - 1], aiRoute[iTargetIndex]);
			dObjectiveValue += this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[0]);
			dObjectiveValue += this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[iTargetIndex]);
			dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iTargetIndex], aiRoute[1]);
			dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iTargetIndex - 1], aiRoute[0]);
			dObjectiveValue -= this.oObjectiveFunction.getCostBetweenDepotAnd(aiRoute[iTargetIndex]);
			dObjectiveValue -= this.oObjectiveFunction.getCostBetweenHomeAnd(aiRoute[0]);
		}else {
			// When swapped indexes is within the aiRoute
			dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iTargetIndex - 1], aiRoute[iTargetIndex]);
			dObjectiveValue += this.oObjectiveFunction.getCost(aiRoute[iTargetIndex + 1], aiRoute[iTargetIndex + 2]);
			dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iTargetIndex - 1], aiRoute[iTargetIndex + 1]);
			dObjectiveValue -= this.oObjectiveFunction.getCost(aiRoute[iTargetIndex], aiRoute[iTargetIndex + 2]);
		}
		
		oSolution.setObjectiveFunctionValue(dObjectiveValue);

		return dObjectiveValue;
	}
}
