package simulation.model;

import java.util.LinkedList;

/**
 * Interface for ants who perform a action.
 * 
 * @author eddie
 *
 */
public interface ActionableAnt {
	
	/**
	 * Performs the ant action.
	 * 
	 * @param grid The colony grid
	 * @param ants The ants in the colony
	 */
	public void performAction(ColonyNode[][] grid, LinkedList<Ant> ants);
}
