package simulation.model;

import java.util.LinkedList;

import simulation.util.Moves;

public class ScoutAnt extends MoveableAnt implements ActionableAnt {

	public ScoutAnt(int id, Location location) {
		super(id, location);
	}

	@Override
	public void performAction(ColonyNode[][] grid, LinkedList<Ant> ants) {
		move(grid, Moves.getUnrestrictedMoves(getLocation()));
		
		ColonyNode node = grid[getLocation().getX()][getLocation().getY()];
		
		if(!node.isDiscovered()) {
			node.setDiscovered(true);
			randomlyGenerateFood(node);
		}
	}

	private void randomlyGenerateFood(ColonyNode node) {
		if(Ant.random().nextInt(100) + 1 <= 25)
			node.setFoodAmount(Ant.random().nextInt(1000 - 500 + 1) + 500);
	}

}
