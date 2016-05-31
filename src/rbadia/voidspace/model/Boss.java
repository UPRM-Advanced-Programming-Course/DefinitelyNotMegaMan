package rbadia.voidspace.model;

import java.awt.Rectangle;

import rbadia.voidspace.main.GameScreen;

/**
 * Represents a ship/space craft.
 *
 */
public class Boss extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED3 = 4;
	private static final int Y_OFFSET = 325; // initial y distance of the ship from the bottom of the screen 
	
	private int bossWidth = 75;
	private int bossHeight = 83;
	private int bossWidth2 = 110;
	private int bossHeight2 = 100;
	private int speed3 = DEFAULT_SPEED3;
	
	/**
	 * Creates a new ship at the default initial location. 
	 * @param screen the game screen
	 */
	public Boss(GameScreen screen){
		this.setLocation((screen.getWidth() - bossWidth)/2,
				screen.getHeight() - bossHeight - Y_OFFSET);
		this.setSize(bossWidth, bossHeight);
	}
	
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getBossWidth() {
		return bossWidth;
	}
	
	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getBossHeight2() {
		return bossHeight2;
	}
	
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getBossWidth2() {
		return bossWidth2;
	}
	
	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getBossHeight() {
		return bossHeight;
	}
	
	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getSpeed3() {
		return speed3;
	}
	
	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed3 = speed;
	}
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED3;
	}
	
}
