package rbadia.voidspace.model;

import java.awt.Rectangle;

import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class BigAsteroid extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED2 = 2;
	
	private int bigAsteroidWidth = 64;
	private int bigAsteroidHeight = 64;
	private int speed2 = DEFAULT_SPEED2;

	private Random rand = new Random();
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public BigAsteroid(GameScreen screen){
		this.setLocation(rand.nextInt(screen.getWidth() - bigAsteroidWidth), 0);
		this.setSize(bigAsteroidWidth, bigAsteroidHeight);
	}
	
	public int getBigAsteroidWidth() {
		return bigAsteroidWidth;
	}
	public int getBigAsteroidHeight() {
		return bigAsteroidHeight;
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed2() {
		return speed2;
	}
	
	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed2(int speed2) {
		this.speed2 = speed2;
	}
	
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed2(){
		return DEFAULT_SPEED2;
	}
}
