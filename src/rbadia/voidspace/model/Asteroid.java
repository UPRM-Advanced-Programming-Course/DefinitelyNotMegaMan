package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameState;
import rbadia.voidspace.main.Level1State;

public class Asteroid extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 4;
	
	private int asteroidWidth = 32;
	private int asteroidHeight = 32;
	private int speed = DEFAULT_SPEED;

	private Random rand = new Random();
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Asteroid(GameState screen){
		this.setLocation(
				screen.getWidth() - asteroidWidth,
        		rand.nextInt(screen.getHeight() - asteroidHeight - 32)
        		);
		this.setSize(asteroidWidth, asteroidHeight);
	}
	
	public int getAsteroidWidth() {
		return asteroidWidth;
	}
	public int getAsteroidHeight() {
		return asteroidHeight;
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
