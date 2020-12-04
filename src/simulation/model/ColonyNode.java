package simulation.model;

import simulation.view.ColonyNodeView;

/**
 * Colony node model.
 * 
 * @author eddie
 *
 */
public class ColonyNode {

	private String id;
	private boolean discovered;
	private boolean queenPresent;
	private int foragerCount;
	private int scoutCount;
	private int soldierCount;
	private int balaCount;
	private int foodAmount;
	private int pheromoneLevel;

	private ColonyNodeView colonyNodeView;

	public ColonyNode(String id) {
		colonyNodeView = new ColonyNodeView();
		setId(id);
		reset();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		colonyNodeView.setID(this.id);
	}

	public boolean isDiscovered() {
		return discovered;
	}

	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;

		if (this.discovered) {
			colonyNodeView.showNode();
		} else {
			colonyNodeView.hideNode();
		}
	}

	public boolean isQueenPresent() {
		return queenPresent;
	}

	public void setQueenPresent(boolean queenPresent) {
		this.queenPresent = queenPresent;
		colonyNodeView.setQueen(this.queenPresent);
	}

	public int getForagerCount() {
		return foragerCount;
	}

	public void setForagerCount(int foragerCount) {
		this.foragerCount = foragerCount;
		colonyNodeView.setForagerCount(this.foragerCount);
	}

	public int getScoutCount() {
		return scoutCount;
	}

	public void setScoutCount(int scoutCount) {
		this.scoutCount = scoutCount;
		colonyNodeView.setScoutCount(this.scoutCount);
	}

	public int getSoldierCount() {
		return soldierCount;
	}

	public void setSoldierCount(int soldierCount) {
		this.soldierCount = soldierCount;
		colonyNodeView.setSoldierCount(this.soldierCount);
	}

	public int getBalaCount() {
		return balaCount;
	}

	public void setBalaCount(int balaCount) {
		this.balaCount = balaCount;
		colonyNodeView.setBalaCount(this.balaCount);
	}

	public int getFoodAmount() {
		return foodAmount;
	}

	public void setFoodAmount(int foodAmount) {
		this.foodAmount = foodAmount;
		colonyNodeView.setFoodAmount(this.foodAmount);
	}

	public int getPheromoneLevel() {
		return pheromoneLevel;
	}

	public void setPheromoneLevel(int pheromoneLevel) {
		this.pheromoneLevel = pheromoneLevel;
		colonyNodeView.setPheromoneLevel(this.pheromoneLevel);
	}

	public ColonyNodeView getColonyNodeView() {
		return colonyNodeView;
	}

	public void addAnt(Ant ant) {
		if (ant instanceof QueenAnt) {
			setQueenPresent(true);
		} else if (ant instanceof ForagerAnt) {
			setForagerCount(foragerCount + 1);
		} else if (ant instanceof ScoutAnt) {
			setScoutCount(scoutCount + 1);
		} else if (ant instanceof SoldierAnt) {
			setSoldierCount(soldierCount + 1);
		} else if (ant instanceof BalaAnt) {
			setBalaCount(balaCount + 1);
		}

		updateIcons();
	}

	public void removeAnt(Ant ant) {
		if (ant instanceof ForagerAnt) {
			setForagerCount(foragerCount - 1);
		} else if (ant instanceof ScoutAnt) {
			setScoutCount(scoutCount - 1);
		} else if (ant instanceof SoldierAnt) {
			setSoldierCount(soldierCount - 1);
		} else if (ant instanceof BalaAnt) {
			setBalaCount(balaCount - 1);
		}

		updateIcons();
	}

	private void updateIcons() {
		if (queenPresent) {
			colonyNodeView.showQueenIcon();
		} else {
			colonyNodeView.hideQueenIcon();
		}

		if (foragerCount > 0) {
			colonyNodeView.showForagerIcon();
		} else {
			colonyNodeView.hideForagerIcon();
		}

		if (scoutCount > 0) {
			colonyNodeView.showScoutIcon();
		} else {
			colonyNodeView.hideScoutIcon();
		}

		if (soldierCount > 0) {
			colonyNodeView.showSoldierIcon();
		} else {
			colonyNodeView.hideSoldierIcon();
		}

		if (balaCount > 0) {
			colonyNodeView.showBalaIcon();
		} else {
			colonyNodeView.hideBalaIcon();
		}
	}

	public void reset() {
		setDiscovered(false);
		setQueenPresent(false);
		setForagerCount(0);
		setScoutCount(0);
		setSoldierCount(0);
		setBalaCount(0);
		setFoodAmount(0);
		setPheromoneLevel(0);
		updateIcons();
	}

}
