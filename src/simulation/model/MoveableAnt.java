package simulation.model;

import java.util.ArrayList;

/**
 * Moveable ant model.
 * 
 * @author eddie
 *
 */
public class MoveableAnt extends Ant {

	public MoveableAnt(int id, Location location) {
		super(id, location);
	}

	/**
	 * Moves the ant to a new location in the colony.
	 * 
	 * @param grid        The colony grid
	 * @param newLocation The new location of the ant
	 */
	public void move(ColonyNode[][] grid, Location newLocation) {
		grid[getLocation().getX()][getLocation().getY()].removeAnt(this);
		setLocation(newLocation);
		grid[getLocation().getX()][getLocation().getY()].addAnt(this);
	}

	/**
	 * Randomly chooses a new location for the ant and then moves the ant to that
	 * location.
	 * 
	 * @param grid              The colony grid
	 * @param possibleLocations The list of possible locations to move to
	 */
	public void move(ColonyNode[][] grid, ArrayList<Location> possibleLocations) {
		Location newLocation = possibleLocations.get(Ant.random().nextInt(possibleLocations.size()));
		move(grid, newLocation);
	}
}
