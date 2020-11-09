package simulation.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import simulation.util.Moves;

public class SoldierAnt extends MoveableAnt implements ActionableAnt {

	public SoldierAnt(int id, Location location) {
		super(id, location);
	}

	@Override
	public void performAction(ColonyNode[][] grid, LinkedList<Ant> ants) {
		ColonyNode node = grid[getLocation().getX()][getLocation().getY()];
		
		if(enemiesInRange(node)) {
			attackEnemy(grid, ants);
		} else {
			moveTowardsBala(grid);
		}
	}

	private void attackEnemy(ColonyNode[][] grid, LinkedList<Ant> ants) {
		LinkedList<Ant> possibleTargets = new LinkedList<Ant>();
		
		for(Ant ant : ants) {
			if(isAttackableEnemy(ant) && ant.isAlive())
				possibleTargets.add(ant);
		}
		
		if(possibleTargets.size() > 0) {
			Ant antToAttack = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
			
			if(new Random().nextBoolean())
				antToAttack.setAlive(false);
		}
	}
	
	private void moveTowardsBala(ColonyNode[][] grid) {
		ArrayList<Location> adjacentLocations = Moves.getRestrictedMoves(grid, getLocation());
		ArrayList<Location> possibleLocations = new ArrayList<Location>();
		
		for(Location loc : adjacentLocations) {
			ColonyNode node = grid[loc.getX()][loc.getY()];
			
			if(node.getBalaCount() > 0)
				possibleLocations.add(loc);
		}
		
		if(possibleLocations.size() > 0) {
			move(grid, possibleLocations);
		} else {
			move(grid, Moves.getRestrictedMoves(grid, getLocation()));
		}
		
	}

	private boolean enemiesInRange(ColonyNode node) {
		return node.getBalaCount() > 0;
	}
	
	private boolean isAttackableEnemy(Ant ant) {
		return ant.getLocation().getX() == getLocation().getX()
				&& ant.getLocation().getY() == getLocation().getY()
				&& ant instanceof BalaAnt;
	}
}
