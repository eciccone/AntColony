package simulation.model;

import java.util.ArrayList;
import java.util.LinkedList;

import simulation.util.Moves;

/**
 * Soldier ant model.
 * 
 * @author eddie
 *
 */
public class SoldierAnt extends MoveableAnt implements ActionableAnt {

	public SoldierAnt(int id, Location location) {
		super(id, location);
	}

	@Override
	public void performAction(ColonyNode[][] grid, LinkedList<Ant> ants) {
		ColonyNode node = grid[getLocation().getX()][getLocation().getY()];

		if (node.getBalaCount() > 0) {
			attackEnemy(grid, ants);
		} else {
			moveTowardsBala(grid);
		}
	}

	/**
	 * Attacks a BalaAnt that is in the same colony node. There is a chance that the
	 * BalaAnt is already dead, if so the SoldierAnt will move towards the next
	 * closest BalaAnt.
	 * 
	 * @param grid The colony grid
	 * @param ants The ants in the colony
	 */
	private void attackEnemy(ColonyNode[][] grid, LinkedList<Ant> ants) {
		LinkedList<Ant> possibleTargets = new LinkedList<Ant>();

		for (Ant ant : ants) {
			if (isAttackableEnemy(ant) && ant.isAlive())
				possibleTargets.add(ant);
		}

		if (possibleTargets.size() > 0) {
			Ant antToAttack = possibleTargets.get(Ant.random().nextInt(possibleTargets.size()));

			if (Ant.random().nextBoolean())
				antToAttack.setAlive(false);
		} else {
			moveTowardsBala(grid);
		}
	}

	/**
	 * Moves towards a node that contains BalaAnts, if none are present the
	 * SoldierAnt will move randomly.
	 * 
	 * @param grid The colony grid
	 */
	private void moveTowardsBala(ColonyNode[][] grid) {
		ArrayList<Location> adjacentLocations = Moves.getRestrictedMoves(grid, getLocation());
		ArrayList<Location> possibleLocations = new ArrayList<Location>();

		for (Location loc : adjacentLocations) {
			ColonyNode node = grid[loc.getX()][loc.getY()];

			if (node.getBalaCount() > 0)
				possibleLocations.add(loc);
		}

		if (possibleLocations.size() > 0) {
			move(grid, possibleLocations);
		} else {
			move(grid, Moves.getRestrictedMoves(grid, getLocation()));
		}

	}

	/**
	 * Determines if a given ant is allowed to be attacked.
	 * 
	 * @param ant The Ant to check if it can be attacked
	 * @return true if the ant is attackable, false otherwise
	 */
	private boolean isAttackableEnemy(Ant ant) {
		return ant.getLocation().getX() == getLocation().getX() && ant.getLocation().getY() == getLocation().getY()
				&& ant instanceof BalaAnt;
	}
}
