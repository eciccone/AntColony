package simulation.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import simulation.util.Moves;

public class ForagerAnt extends MoveableAnt implements ActionableAnt {

	private boolean carryingFood;
	private boolean escapingLoop;
	private Stack<Location> history;
	private Location prevLocation;
	
	public ForagerAnt(int id, Location location) {
		super(id, location);
		carryingFood = false;
		escapingLoop = false;
		history = new Stack<Location>();
		prevLocation = null;
	}
	
	public boolean isCarryingFood() {
		return carryingFood;
	}

	@Override
	public void performAction(ColonyNode[][] grid, LinkedList<Ant> ants) {
		if(carryingFood) {
			returnToNest(grid);
		} else {
			forage(grid);
		}
	}
	
	public void returnToNest(ColonyNode[][] grid) {
		ColonyNode currentNode = grid[getLocation().getX()][getLocation().getY()];
		
		if(currentNode.getPheromoneLevel() < 1000 && !inNest(getLocation()))
			currentNode.setPheromoneLevel(currentNode.getPheromoneLevel() + 10);

		prevLocation = getLocation();
		
		move(grid, history.pop());

		if(inNest(getLocation()) && history.isEmpty()) {
			grid[13][13].setFoodAmount(grid[13][13].getFoodAmount() + 1);
			carryingFood = false;
			escapingLoop = false;
		}
	}
	
	private void forage(ColonyNode[][] grid) {
		if(!escapingLoop) {
			moveToHighestPheromone(grid);
			checkForLoops();
		} else {
			prevLocation = getLocation();
			history.push(getLocation());
			move(grid, Moves.getRestrictedMoves(grid, getLocation()));
		}
		
		ColonyNode node = grid[getLocation().getX()][getLocation().getY()];
		
		if(node.getFoodAmount() > 0 && !inNest(getLocation())) {
			node.setFoodAmount(node.getFoodAmount() - 1);
			carryingFood = true;
		}
	}
	
	private void moveToHighestPheromone(ColonyNode[][] grid) {
		ArrayList<Location> adjacentLocations = Moves.getRestrictedMoves(grid, getLocation());
		ArrayList<Location> possibleLocations = new ArrayList<Location>();
		
		int highestPLevel = 0;
		
		for(Location loc : adjacentLocations) {
			ColonyNode node = grid[loc.getX()][loc.getY()];
			if(!lastLoc(loc) && !inNest(loc)) {
			
				if(node.getPheromoneLevel() == highestPLevel) {
					possibleLocations.add(loc);
				}
				
				if(node.getPheromoneLevel() > highestPLevel) {
					possibleLocations.clear();
					highestPLevel = node.getPheromoneLevel();
					possibleLocations.add(loc);
				}
			}
		}
		
		prevLocation = getLocation();
		history.push(getLocation());
		
		if(possibleLocations.size() > 0) {
			move(grid, possibleLocations);
		} else {
			move(grid, history.peek());
		}
	}
	
	private void checkForLoops() {
		if(history.size() > 15) {
			Stack<Location> historyClone = (Stack<Location>) history.clone();
			
			int locationCounter = 0;
			
			while(!historyClone.isEmpty()) {
				Location loc = historyClone.pop();
				
				if(loc.getX() == getLocation().getX() && loc.getY() == getLocation().getY())
					locationCounter++;
			}
			
			if(locationCounter >= 5)
				escapingLoop = true;
		}
	}

	private boolean inNest(Location loc) {
		return loc.getX() == 13 && loc.getY() == 13;
	}
	
	private boolean lastLoc(Location loc) {
		if(prevLocation == null)
			return false;
		
		return prevLocation.getX() == loc.getX() && prevLocation.getY() == loc.getY();
	}
	

}
