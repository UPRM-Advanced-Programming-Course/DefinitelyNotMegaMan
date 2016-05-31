package rbadia.voidspace.model;
import java.awt.Rectangle;

/**
 * Represents a bullet fired by a ship.
 */
public class BigBullet extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private int speed = 12;
	private int bigBulletWidth = 16;
	private int bigBulletHeight = 16;
	
	/**
	 * Creates a new bullet above the ship, centered on it
	 * @param ship
	 */
	public BigBullet(MegaMan megaMan) {
		this.setLocation(megaMan.x + megaMan.width - bigBulletWidth/2,
				megaMan.y + megaMan.width/2 - bigBulletHeight +4);
		this.setSize(bigBulletWidth, bigBulletHeight);
	}
	


	/**
	 * Return the bullet's speed.
	 * @return the bullet's speed.
	 */
	public int getBigSpeed() {
		return speed;
	}

	/**
	 * Set the bullet's speed
	 * @param speed the speed to set
	 */
	public void setBigSpeed(int speed) {
		this.speed = speed;
	}
}
