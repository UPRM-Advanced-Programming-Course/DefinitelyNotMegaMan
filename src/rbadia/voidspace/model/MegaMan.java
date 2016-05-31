package rbadia.voidspace.model;

import java.awt.Rectangle;


import rbadia.voidspace.main.GameScreen;

/**
 * Represents a ship/space craft.
 *
 */
public class MegaMan extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 5;
	private static final int Y_OFFSET = 5; // initial y distance of the ship from the bottom of the screen 
	
	private int megaManWidth = 42;
	private int megaManHeight = 41;
	private int speed = DEFAULT_SPEED;
	
	/**
	 * Creates a new ship at the default initial location. 
	 * @param screen the game screen
	 */
	public MegaMan(GameScreen screen){
		this.setLocation((screen.getWidth() - megaManWidth)/2,
				(screen.getHeight() - megaManHeight - Y_OFFSET) /2);
		this.setSize(megaManWidth, megaManHeight);
	}
	
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getMegaManWidth() {
		return megaManWidth;
	}
	
	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getMegaManHeight() {
		return megaManHeight;
	}
	
	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	
}
