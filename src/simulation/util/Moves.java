package simulation.util;

import java.util.ArrayList;

import simulation.model.ColonyNode;
import simulation.model.Location;

/**
 * A utility class that uses static methods to calculate acceptable locations
 * throughout the ant colony.
 * 
 * @author eddie
 *
 */
public class Moves {

	private static final int LOWER_BOUND = 0;

	private static final int UPPER_BOUND = 26;

	/**
	 * Gets and returns a list of all the adjacent locations.
	 * 
	 * @param location The starting location
	 * @return List of adjacent locations
	 */
	public static ArrayList<Location> getUnrestrictedMoves(Location location) {
		ArrayList<Location> moves = new ArrayList<Location>();

		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int yOffset = -1; yOffset <= 1; yOffset++) {
				int x = location.getX() + xOffset;
				int y = location.getY() + yOffset;

				if (inBounds(x, y) && acceptableOffset(xOffset, yOffset))
					moves.add(new Location(x, y));
			}
		}

		return moves;
	}

	/**
	 * Gets and returns a list of all the adjacent locations in which the ColonyNode
	 * has been discovered.
	 * 
	 * @param grid     The two-dimensional array that makes up the colony
	 * @param location The starting location
	 * @return List of discovered locations
	 */
	public static ArrayList<Location> getRestrictedMoves(ColonyNode[][] grid, Location location) {
		ArrayList<Location> moves = new ArrayList<Location>();

		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int yOffset = -1; yOffset <= 1; yOffset++) {
				int x = location.getX() + xOffset;
				int y = location.getY() + yOffset;

				if (inBounds(x, y) && acceptableOffset(xOffset, yOffset)) {
					ColonyNode node = grid[x][y];

					if (node.isDiscovered())
						moves.add(new Location(x, y));
				}
			}
		}

		return moves;
	}

	/**
	 * Determines if a offset for a "move" is acceptable.
	 * 
	 * A "move" must result in a different location. For example, a offset of (0, 0)
	 * added to location (2, 2) results in a move to the same location; this does
	 * not constitute a move. But a offset of (0, 1) added to location (2, 2) would
	 * result in (2, 3); this does constitute a move.
	 * 
	 * @param x The x offset
	 * @param y The y offset
	 * @return true if both x and y do not equal 0, otherwise false (both x and y
	 *         equal 0)
	 */
	private static boolean acceptableOffset(int xOffset, int yOffset) {
		return !(xOffset == 0 && yOffset == 0);
	}

	/**
	 * Determines if both x and y coordinates are within bounds.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return true if within bounds, false otherwise
	 */
	private static boolean inBounds(int x, int y) {
		return x >= LOWER_BOUND && x <= UPPER_BOUND && y >= LOWER_BOUND && y <= UPPER_BOUND;
	}

}
