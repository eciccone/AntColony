package simulation.model;

import simulation.view.ColonyView;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * The ant colony.
 * 
 * @author eddie
 *
 */
public class Colony {

	private int antCount;
	private LinkedList<Ant> ants;
	private QueenAnt queenAnt;
	private ColonyNode[][] grid;
	private ColonyView colonyView;

	private static final int QUEEN_MAX_AGE = 73000;
	private static final int ANT_MAX_AGE = 3650;

	public Colony() {
		antCount = 0;
		ants = new LinkedList<Ant>();
		queenAnt = null;
		grid = new ColonyNode[27][27];
		colonyView = new ColonyView(27, 27);

		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				String id = x + ", " + y;
				grid[x][y] = new ColonyNode(id);
				colonyView.addColonyNodeView(grid[x][y].getColonyNodeView(), x, y);
			}
		}
	}

	/**
	 * Gets the queen ant of the colony.
	 * 
	 * @return The queen ant
	 */
	public QueenAnt getQueenAnt() {
		return queenAnt;
	}

	/**
	 * Gets the colony view.
	 * 
	 * @return The colony view
	 */
	public ColonyView getColonyView() {
		return colonyView;
	}

	/**
	 * Sets up the colony under normal conditions.
	 * 
	 */
	public void normalSetup() {
		// Make starting nodes visible
		for (int x = 12; x < 15; x++) {
			for (int y = 12; y < 15; y++)
				grid[x][y].setDiscovered(true);
		}

		// Center node must start with 1000 units of food
		grid[13][13].setFoodAmount(1000);

		// Center node must contain the queen
		queenAnt = new QueenAnt(antCount, new Location(13, 13));
		addAnt(queenAnt);

		// Center node must start with 50 forager ants
		for (int i = 0; i < 50; i++)
			addAnt(new ForagerAnt(antCount, new Location(13, 13)));

		// Center node must start with 4 scout ants
		for (int i = 0; i < 4; i++)
			addAnt(new ScoutAnt(antCount, new Location(13, 13)));

		// Center node must start with 10 soldier ants
		for (int i = 0; i < 10; i++)
			addAnt(new SoldierAnt(antCount, new Location(13, 13)));
	}

	/**
	 * Adds a ant to the colony and updates the total ant count.
	 * 
	 * @param ant The ant to add to the colony
	 */
	private void addAnt(Ant ant) {
		grid[ant.getLocation().getX()][ant.getLocation().getY()].addAnt(ant);
		ants.add(ant);
		antCount++;
	}

	/**
	 * Resets all the colony nodes to starting conditions, clears all the ants, and
	 * resets the queen ant and ant count.
	 */
	public void reset() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++)
				grid[x][y].reset();
		}

		ants.clear();
		queenAnt = null;
		antCount = 0;
	}

	/**
	 * Decreases each colony nodes pheromone level by half if the pheromone level is
	 * greater than 0.
	 */
	private void decreasePheromone() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y].getPheromoneLevel() > 0) {
					int pheromoneLevel = grid[x][y].getPheromoneLevel() / 2;
					grid[x][y].setPheromoneLevel(pheromoneLevel);
				}
			}
		}
	}

	/**
	 * Updates and performs the action of each ant.
	 * 
	 * @param turns The turn count
	 */
	public void update(int turns) {
		queenAnt.setAge(turns);

		// Check if queen died of old age
		if (queenAnt.getAge() >= QUEEN_MAX_AGE) {
			queenAnt.setAlive(false);
			System.out.println("Queen died of old age.");
			return;
		}

		// Check if its a new day
		if (turns % 10 == 0) {
			Ant hatchedAnt = queenAnt.hatchAnt(antCount);
			addAnt(hatchedAnt);
			System.out.println("ANT " + hatchedAnt.getId() + " --- hatched.");
			decreasePheromone();
		}

		// Check if queen eats
		if (!queenAnt.eat(grid[13][13])) {
			queenAnt.setAlive(false);
			System.out.println("Queen died of starvation.");
			return;
		}

		// 3% chance of BalaAnt spawning
		if (Ant.random().nextInt(100) + 1 <= 3) {
			addAnt(new BalaAnt(antCount, new Location(0, 0)));
		}

		// Iterate through all ants and perform their actions
		Iterator<Ant> itr = ants.iterator();
		itr.next(); // First ant is always queen

		while (itr.hasNext()) {
			Ant ant = itr.next();

			if (ant.getAge() > ANT_MAX_AGE) {
				ant.setAlive(false);
			}

			if (!ant.isAlive()) {
				grid[ant.getLocation().getX()][ant.getLocation().getY()].removeAnt(ant);
				itr.remove();
				System.out.println("ANT " + ant.getId() + " --- died.");
				continue;
			}

			((ActionableAnt) ant).performAction(grid, ants);

			ant.setAge(ant.getAge() + 1);
		}
	}

}
