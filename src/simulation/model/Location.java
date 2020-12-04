package simulation.model;

/**
 * Location model.
 * 
 * @author eddie
 *
 */
public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
