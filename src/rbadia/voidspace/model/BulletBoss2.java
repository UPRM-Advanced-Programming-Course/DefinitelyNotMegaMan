package rbadia.voidspace.model;
import java.awt.Rectangle;

/**
 * Represents a bullet fired by a ship.
 */
public class BulletBoss2 extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private int bulletBossWidth = 8;
	private int bulletBossHeight = 8;
	private int speed = 12;

	
	/**
	 * Creates a new bullet above the ship, centered on it
	 * @param ship
	 */	
	public BulletBoss2(Boss boss2){
		this.setLocation(boss2.x + boss2.width/2 + 15,
				boss2.y + boss2.height);
		this.setSize(bulletBossWidth, bulletBossHeight);
	}
	

	/**
	 * Return the bullet's speed.
	 * @return the bullet's speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the bullet's speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
