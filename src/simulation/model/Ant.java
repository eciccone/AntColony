package simulation.model;

import java.util.Random;

public class Ant {

    private int id;
    private int age;
    private boolean alive;
    private Location location;
    
    private static Random random = new Random(5151994);

    public Ant(int id, Location location) {
        setId(id);
        setAge(0);
        setAlive(true);
        setLocation(location);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
	
	public static Random random() {
		return random;
	}
}
