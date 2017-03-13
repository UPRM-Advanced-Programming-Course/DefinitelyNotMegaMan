package rbadia.voidspace.model;

import java.awt.Rectangle;

import rbadia.voidspace.main.GameScreen;

public class Floor extends Rectangle {
	private static final long serialVersionUID = 1L;

	//	public static final int DEFAULT_SPEED = 4;

	private int floorWidth = 64;
	private int floorHeight = 64;
//	private int floorX = 0;
//	private int floorY =0;
	//	private int speed = DEFAULT_SPEED;

	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Floor(GameScreen screen, int n){
			this.setLocation(0 + n*floorWidth , screen.getHeight()- floorHeight/2);
			this.setSize(floorWidth, floorHeight);
	}

	public int getFloorWidth() {
		return floorWidth;
	}
	public int getFloorHeight() {
		return floorHeight;
	}
}
