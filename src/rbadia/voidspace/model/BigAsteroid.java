package rbadia.voidspace.model;

public class BigAsteroid extends GameObject {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 2;
	
	private static final  int WIDTH = 64;
	private static final int HEIGHT = 64;

	public BigAsteroid(int xPos, int yPos) {
		super(xPos, yPos, BigAsteroid.WIDTH, BigAsteroid.HEIGHT);
		this.setSpeed(DEFAULT_SPEED);
	}

	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
