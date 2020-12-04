package simulation.model;

import java.util.LinkedList;

import simulation.util.Moves;

/**
 * Bala ant model.
 * 
 * @author eddie
 *
 */
public class BalaAnt extends MoveableAnt implements ActionableAnt {

	public BalaAnt(int id, Location location) {
		super(id, location);
	}

	@Override
	public void performAction(ColonyNode[][] grid, LinkedList<Ant> ants) {
		ColonyNode node = grid[getLocation().getX()][getLocation().getY()];

		if (enemiesInRange(node)) {
			attackEnemy(grid, ants);
		} else {
			move(grid, Moves.getUnrestrictedMoves(getLocation()));
		}
	}

	/**
	 * Attacks a enemy ant that is in the same colony node. There is a chance that
	 * the enemy ant is already dead, if so the BalaAnt will move randomly.
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

			// 50% chance of successfully killing enemy.
			if (Ant.random().nextBoolean()) {
				antToAttack.setAlive(false);

				// Handle if ForagerAnt was carrying food
				if (antToAttack instanceof ForagerAnt) {
					if (((ForagerAnt) antToAttack).isCarryingFood()) {
						ColonyNode node = grid[antToAttack.getLocation().getX()][antToAttack.getLocation().getY()];
						node.setFoodAmount(node.getFoodAmount() + 1);
					}
				}
			}
		} else {
			move(grid, Moves.getUnrestrictedMoves(getLocation()));
		}
	}

	/**
	 * Determines if any enemies of this ant are in the given node.
	 * 
	 * @param node The ColonyNode to check if there are any enemies
	 * @return true if any enemy ants are present, otherwise false
	 */
	private boolean enemiesInRange(ColonyNode node) {
		return node.getScoutCount() > 0 || node.getSoldierCount() > 0 || node.getForagerCount() > 0
				|| node.isQueenPresent();
	}

	/**
	 * Determines if a given ant is allowed to be attacked.
	 * 
	 * @param ant The Ant to check if it can be attacked
	 * @return true if the ant is attackable, false otherwise
	 */
	private boolean isAttackableEnemy(Ant ant) {
		return ant.getLocation().getX() == getLocation().getX() && ant.getLocation().getY() == getLocation().getY()
				&& !(ant instanceof BalaAnt);
	}
}
