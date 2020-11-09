package simulation.model;

public class QueenAnt extends Ant {

	public QueenAnt(int id, Location location) {
		super(id, location);
	}
	
	public Ant hatchAnt(int antCount) {
		int randomNumber = Ant.random().nextInt(100) + 1;
		
		if(randomNumber <= 40)
			return new ForagerAnt(antCount, new Location(13, 13));
		else if(randomNumber <= 50)
			return new ScoutAnt(antCount, new Location(13, 13));
		else
			return new SoldierAnt(antCount, new Location(13, 13));
	}
	
	public boolean eat(ColonyNode colonyNode) {
		if(colonyNode.getFoodAmount() == 0)
			return false;	
		int foodAmountAfterEating = colonyNode.getFoodAmount() - 1;
		colonyNode.setFoodAmount(foodAmountAfterEating);
		return true;
	}
	
}
