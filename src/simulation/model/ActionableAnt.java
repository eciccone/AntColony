package simulation.model;

import java.util.LinkedList;

public interface ActionableAnt {
	public void performAction(ColonyNode[][] grid, LinkedList<Ant> ants);
}
