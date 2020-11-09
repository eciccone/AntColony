package simulation.model;

import java.util.ArrayList;

public class MoveableAnt extends Ant {

	public MoveableAnt(int id, Location location) {
		super(id, location);
	}
	
	public void move(ColonyNode[][] grid, Location newLocation) {
		grid[getLocation().getX()][getLocation().getY()].removeAnt(this);
		setLocation(newLocation);
		grid[getLocation().getX()][getLocation().getY()].addAnt(this);
	}
	
	public void move(ColonyNode[][] grid, ArrayList<Location> possibleLocations) {
		Location newLocation = possibleLocations.get(Ant.random().nextInt(possibleLocations.size()));
		move(grid, newLocation);
	}
}
