package rbadia.voidspace.model;

import java.awt.Rectangle;

import rbadia.voidspace.main.GameScreen;

public class Platform extends Rectangle {
	private static final long serialVersionUID = 1L;

	//	public static final int DEFAULT_SPEED = 4;
	private int platformWidth = 44;
	private int platformHeight = 4;
//	private int platformX=0;
//	private int platformY=0;
	//	private int speed = DEFAULT_SPEED;

	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Platform(GameScreen screen, int n){

		this.setLocation( 0 , screen.getHeight()/2 + 140 - n*40);
		this.setSize(platformWidth, platformHeight);
		
	}

	public int getPlatformWidth() {
		return platformWidth;
	}
	public int getPlatformHeight() {
		return platformHeight;
	}
}
