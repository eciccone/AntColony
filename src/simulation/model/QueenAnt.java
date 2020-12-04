package simulation.model;

/**
 * Queen ant model.
 * 
 * @author eddie
 *
 */
public class QueenAnt extends Ant {

	public QueenAnt(int id, Location location) {
		super(id, location);
	}
	
	/**
	 * Randomly hatches a ant.
	 * 	40% chance of ForagerAnt
	 * 	5% chance of ScoutAnt
	 * 	55% chance of SoldierAnt
	 * 
	 * @param antCount The total ant count used as a unique ID for ant
	 * @return The newly hatched ant
	 */
	public Ant hatchAnt(int antCount) {
		int randomNumber = Ant.random().nextInt(100) + 1;
		
		if(randomNumber <= 40)
			return new ForagerAnt(antCount, new Location(13, 13));
		else if(randomNumber <= 45)
			return new ScoutAnt(antCount, new Location(13, 13));
		else
			return new SoldierAnt(antCount, new Location(13, 13));
	}
	
	/**
	 * Makes the queen ant eat 1 unit of food.
	 * 
	 * @param colonyNode The nest node
	 * @return true if the nest node has food, otherwise false
	 */
	public boolean eat(ColonyNode colonyNode) {
		if(colonyNode.getFoodAmount() == 0)
			return false;	
		int foodAmountAfterEating = colonyNode.getFoodAmount() - 1;
		colonyNode.setFoodAmount(foodAmountAfterEating);
		return true;
	}
	
}
