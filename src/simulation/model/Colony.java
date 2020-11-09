package simulation.model;

import simulation.view.ColonyView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

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

        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[x].length; y++) {
                String id = x + ", " + y;
                grid[x][y] = new ColonyNode(id);
                colonyView.addColonyNodeView(grid[x][y].getColonyNodeView(), x, y);
            }
        }
    }
    
    public QueenAnt getQueenAnt() {
    	return queenAnt;
    }

    public ColonyView getColonyView() {
        return colonyView;
    }

	public void normalSetup() {
		// Make starting nodes visible
		for(int x = 12; x < 15; x++) {
			for(int y = 12; y < 15; y++)
				grid[x][y].setDiscovered(true);
		}
		
		// Center node must start with 1000 units of food
		grid[13][13].setFoodAmount(1000);
		
		// Center node must contain the queen
		queenAnt = new QueenAnt(antCount, new Location(13, 13));
		addAnt(queenAnt);
		
		// Center node must start with 50 forager ants
		for(int i = 0; i < 50; i++)
			addAnt(new ForagerAnt(antCount, new Location(13, 13)));
		
		// Center node must start with 4 scout ants
		for(int i = 0; i < 4; i++)
			addAnt(new ScoutAnt(antCount, new Location(13, 13)));
		
		// Center node must start with 10 soldier ants
		for(int i = 0; i < 10; i++)
			addAnt(new SoldierAnt(antCount, new Location(13, 13)));
	}
	
	private void addAnt(Ant ant) {
		grid[ant.getLocation().getX()][ant.getLocation().getY()].addAnt(ant);
		ants.add(ant);
		antCount++;
	}
	
    public void reset() {
    	for(int x = 0; x < grid.length; x++) {
    		for(int y = 0; y < grid[x].length; y++)
    			grid[x][y].reset();
    	}
    	
    	ants.clear();
    	antCount = 0;
    }
    
    public void update(int turns) {

    }

}