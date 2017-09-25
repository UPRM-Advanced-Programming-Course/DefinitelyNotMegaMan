package rbadia.voidspace.main;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.BulletBoss;
import rbadia.voidspace.model.BulletBoss2;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class Level1State extends GameState {

	private static final long serialVersionUID = 1L;
	
	private GraphicsManager graphicsMan;

	protected BufferedImage backBuffer;
	protected Graphics2D g2d;

	private MegaMan megaMan;
	private Boss boss;
	private Boss boss2;
	private Asteroid asteroid;
	private Asteroid asteroid2;
	private BigAsteroid bigAsteroid;
	private List<Bullet> bullets;
	private List<BulletBoss> bulletsBoss;
	private List<BulletBoss2> bulletsBoss2;
	private List<BigBullet> bigBullets;
	private Platform[] numPlatforms;
	private Floor[] floor;	// END Moved from GameLogic class
	protected Platform[] platforms;

	protected int damage=0;
	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;

	private long lastAsteroidTime;
	protected long lastShipTime;
		
	private Rectangle asteroidExplosion;

	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private int boom=0;
	
	// Constructors
	public Level1State() {
		super();
		
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);

		// initialize random number generator
		rand = new Random();
		
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
		
		this.setStatus(new GameStatus());
		this.setSoundManager(new SoundManager());

		// init some variables
		bullets = new ArrayList<Bullet>();
		bulletsBoss = new ArrayList<BulletBoss>();
		bulletsBoss2 = new ArrayList<BulletBoss2>();
		bigBullets = new ArrayList<BigBullet>();
	}
	
	// Getters

	public int getBoom(){
		return boom;
	}

	public MegaMan getMegaMan() {
		return megaMan;
	}

	public Floor[] getFloor(){
		return floor;	
	}

	public Platform[] getNumPlatforms(){
		return numPlatforms;
	}

	public Boss getBoss() {
		return boss;
	}

	public Boss getBoss2() {
		return boss2;
	}

	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public Asteroid getAsteroid() {
		return asteroid;
	}

	public Asteroid getAsteroid2() {
		return asteroid2;
	}

	public BigAsteroid getBigAsteroid() {
		return bigAsteroid;
	}

	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}

	/**
	 * Returns the list of the boss's bullets.
	 * @return the list of the boss's bullets
	 */
	public List<BulletBoss> getBulletBoss() {
		return bulletsBoss;
	}

	/**
	 * Returns the list of the second boss's bullets.
	 * @return the list of the second boss's bullets
	 */
	public List<BulletBoss2> getBulletBoss2() {
		return bulletsBoss2;
	}

	/**
	 * Returns the list of "Power Shot" bullets.
	 * @return the list of "Power Shot" bullets
	 */
	public List<BigBullet> getBigBullets(){
		return bigBullets;
	}
	
	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		MegaMan megaMan = this.getMegaMan();
		Floor[] floor = this.getFloor();
		Platform[] numPlatforms = this.getNumPlatforms();
		List<Bullet> bullets = this.getBullets();
		Asteroid asteroid = this.getAsteroid();
		List<BigBullet> bigBullets = this.getBigBullets();
		//		Asteroid asteroid2 = gameLogic.getAsteroid2();
		//		BigAsteroid bigAsteroid = gameLogic.getBigAsteroid();
		//		List<BulletBoss> bulletsBoss = gameLogic.getBulletBoss();
		//		List<BulletBoss2> bulletsBoss2 = gameLogic.getBulletBoss2();		
		//		Boss boss = gameLogic.getBoss();
		//		Boss boss2 = gameLogic.getBoss2();

		GameStatus status = this.getStatus();
		// set orignal font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 50 random stars
		drawStars(50);

		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}

		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			//			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
			//				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			//			}
			return;
		}

		//if the game is won, draw the "You Win!!!" message
		if(status.isGameWon()){
			// draw the message
			drawYouWin();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			return;
		}

		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			return;
		}

		//draw Floor
		for(int i=0; i<9; i++){
			graphicsMan.drawFloor(floor[i], g2d, this, i);	
		}


		//		if(level==1){
		//draw Platform LV. 1
		for(int i=0; i<8; i++){
			graphicsMan.drawPlatform(numPlatforms[i], g2d, this, i);
			//			}
		}
		//		//draw Platform LV. 2
		//		else if(level==2){
		//			for(int i=0; i<8; i++){
		//			
		//				graphicsMan.drawPlatform2(numPlatforms[i], g2d, this, i);
		//			}	
		//		}

		//draw MegaMan
		if(!status.isNewMegaMan()){
			if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))){
				graphicsMan.drawMegaFallR(megaMan, g2d, this);
			}
		}

		if((Fire() == true || Fire2()== true) && (Gravity()==false)){
			graphicsMan.drawMegaFireR(megaMan, g2d, this);
		}

		if((Gravity()==false) && (Fire()==false) && (Fire2()==false)){
			graphicsMan.drawMegaMan(megaMan, g2d, this);
		}

		// draw first asteroid
		if(!status.isNewAsteroid() && boom <= 2){
			// draw the asteroid until it reaches the bottom of the screen

			//LEVEL 1
			if((asteroid.getX() + asteroid.getAsteroidWidth() >  0) && (boom <= 5 || boom == 15)){
				asteroid.translate(-asteroid.getSpeed(), 0);
				graphicsMan.drawAsteroid(asteroid, g2d, this);	
			}
			else if (boom <= 5){
				asteroid.setLocation(this.getWidth() - asteroid.getAsteroidWidth(),
						rand.nextInt(this.getHeight() - asteroid.getAsteroidHeight() - 32));
			}	
		}

		else if(!status.isNewAsteroid() && boom > 2){
			// draw the asteroid until it reaches the bottom of the screen
			//LEVEL 2
			if((asteroid.getX() + asteroid.getAsteroidWidth() >  0)){
				asteroid.translate(-asteroid.getSpeed(), asteroid.getSpeed()/2);
				graphicsMan.drawAsteroid(asteroid, g2d, this);	
			}
			else if (boom <= 5){
				asteroid.setLocation(this.getWidth() - asteroid.getAsteroidWidth(),
						rand.nextInt(this.getHeight() - asteroid.getAsteroidHeight() - 32));
			}	
		}

		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				asteroid.setLocation(this.getWidth() - asteroid.getAsteroidWidth(),
						rand.nextInt(this.getHeight() - asteroid.getAsteroidHeight() - 32));
			}

			else{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}

		// draw bullets   
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove =   this.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
		}

		// draw big bullets
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			graphicsMan.drawBigBullet(bigBullet, g2d, this);

			boolean remove = this.moveBigBullet(bigBullet);
			if(remove){
				bigBullets.remove(i);
				i--;
			}
		}

		// check bullet-asteroid collisions
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(asteroid.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);

				removeAsteroid(asteroid);



				if(boom != 5 && boom != 15){
					boom=boom + 1;
				}
				damage=0;
				// remove bullet
				bullets.remove(i);
				break;
			}
		}

		// check big bullet-asteroid collisions
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(asteroid.intersects(bigBullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);

				removeAsteroid(asteroid);



				if(boom != 5 && boom != 15){
					boom=boom + 1;
				}
				damage=0;
			}
		}

		//MM-Asteroid collision
		if(asteroid.intersects(megaMan)){
			status.setShipsLeft(status.getShipsLeft() - 1);
			removeAsteroid(asteroid);
		}

		//Asteroid-Floor collision
		for(int i=0; i<9; i++){
			if(asteroid.intersects(floor[i])){
				removeAsteroid(asteroid);

			}
		}
		//

		if(boom == 2)
			restructure();

		status.getAsteroidsDestroyed();
		status.getShipsLeft();
		status.getLevel();

		// update asteroids destroyed label  
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));

		// update ships left label
		getMainFrame().getDestroyedValueLabel().setText(Integer.toString(status.getShipsLeft()));

		//update level label

		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getLevel()));
	}

	/**
	 * Draws the "Game Over" message.
	 */
	protected void drawGameOver() {
		String gameOverStr = "GAME OVER";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);

		boomReset();
		healthReset();
		delayReset();
	}

	protected void drawYouWin() {
		String youWinStr = "You Pass";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(youWinStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(youWinStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(youWinStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Next level starting soon";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(newGameStr, strX, strY);

		boom=3;	//Change value in order for the next level to start

		//		boomReset();
		//		healthReset();
		//		delayReset();
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	protected void drawGetReady() {
		String readyStr = "Get Ready"; 
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	protected void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}

	/**
	 * Display initial game title screen.
	 */
	protected void initialMessage() {
		String gameTitleStr = "Definitely Not MegaMan";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String itemGameStr = "Press <I> for Item Menu.";
		strWidth = fm.stringWidth(itemGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(itemGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String shopGameStr = "Press <S> for Shop Menu.";
		strWidth = fm.stringWidth(shopGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(shopGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		getMainFrame().getDestroyedValueLabel().setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame() {	
		
		// BEGIN Moved from GameLogic
		// init game variables
		bullets = new ArrayList<Bullet>();
		bulletsBoss = new ArrayList<BulletBoss>();
		bulletsBoss2 = new ArrayList<BulletBoss2>();
		bigBullets = new ArrayList<BigBullet>();
		//numPlatforms = new Platform[5];
		
		GameStatus status = this.getStatus();

		status.setGameStarting(true);
		status.setShipsLeft(3);
		status.setLevel(1);
		status.setGameOver(false);
		status.setAsteroidsDestroyed(0);
		status.setNewAsteroid(false);
		status.setNewAsteroid2(false);
		status.setNewBigAsteroid(false);
		//status.setNewFloor(false);

		// init the ship and the asteroid
		newMegaMan(this);
		newFloor(this, 9);

		newNumPlatforms(this, 8);

		//        newPlatform(gameScreen/*, 1*/);
		//        newPlatform1(gameScreen);
		newBoss(this);
		newBoss2(this);
		newAsteroid(this);
		newAsteroid2(this);
		newBigAsteroid(this);
		// END Moved from GameLogic
		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		//lastBigAsteroidTime = -NEW_BIG_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		getMainFrame().getDestroyedValueLabel().setForeground(Color.BLACK);
		getMainFrame().getDestroyedValueLabel().setText(Integer.toString(status.getShipsLeft()));
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getLevel()));
		
		
	}

	public int boomReset(){
		boom= 0;
		return boom;
	}
	public long healthReset(){
		boom= 0;
		return boom;
	}
	public long delayReset(){
		boom= 0;
		return boom;
	}

	protected boolean Gravity(){
		MegaMan megaMan = this.getMegaMan();
		Floor[] floor = this.getFloor();

		for(int i=0; i<9; i++){
			if((megaMan.getY() + megaMan.getMegaManHeight() -17 < this.getHeight() - floor[i].getFloorHeight()/2) 
					&& Fall() == true){

				megaMan.translate(0 , 2);
				return true;

			}
		}
		return false;
	}
	
	//Bullet fire pose
	protected boolean Fire(){
		MegaMan megaMan = this.getMegaMan();
		List<Bullet> bullets = this.getBullets();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if((bullet.getX() > megaMan.getX() + megaMan.getMegaManWidth()) && 
					(bullet.getX() <= megaMan.getX() + megaMan.getMegaManWidth() + 60)){
				return true;
			}
		}
		return false;
	}

	//BigBullet fire pose
	protected boolean Fire2(){
		MegaMan megaMan = this.getMegaMan();
		List<BigBullet> bigBullets = this.getBigBullets();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if((bigBullet.getX() > megaMan.getX() + megaMan.getMegaManWidth()) && 
					(bigBullet.getX() <= megaMan.getX() + megaMan.getMegaManWidth() + 60)){
				return true;
			}
		}
		return false;
	}

	//Platform Gravity
	public boolean Fall(){
		MegaMan megaMan = this.getMegaMan(); 
		Platform[] platform = this.getNumPlatforms();
		for(int i=0; i<8; i++){
			if((((platform[i].getX() < megaMan.getX()) && (megaMan.getX()< platform[i].getX() + platform[i].getPlatformWidth()))
					|| ((platform[i].getX() < megaMan.getX() + megaMan.getMegaManWidth()) 
							&& (megaMan.getX() + megaMan.getMegaManWidth()< platform[i].getX() + platform[i].getPlatformWidth())))
					&& megaMan.getY() + megaMan.getMegaManHeight() == platform[i].getY()
					){
				return false;
			}
		}
		return true;
	}

	public void restructure(){
		Platform[] platform = this.getNumPlatforms();
		for(int i=0; i<8; i++){
			if(i<4)	platform[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);
			if(i==4) platform[i].setLocation(50 +i*50, getHeight()/2 + 140 - 3*40);
			if(i>4){	
				int n=4;
				platform[i].setLocation(50 + i*50, getHeight()/2 + 20 + (i-n)*40 );
				n=n+2;
			}
		}
		this.getStatus().setLevel(this.getStatus().getLevel() + 1);
	}

	public void removeAsteroid(Asteroid asteroid){
		// "remove" asteroid
		asteroidExplosion = new Rectangle(
				asteroid.x,
				asteroid.y,
				asteroid.width,
				asteroid.height);
		asteroid.setLocation(-asteroid.width, -asteroid.height);
		this.getStatus().setNewAsteroid(true);
		lastAsteroidTime = System.currentTimeMillis();

		// play asteroid explosion sound
		this.getSoundManager().playAsteroidExplosionSound();
	}
	
	// BEGIN Moved from GameLogic
	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(megaMan);
		bullets.add(bullet);
		this.getSoundManager().playBulletSound();
	}

	/**
	 * Fire the "Power Shot" bullet
	 */
	public void fireBigBullet(){
		BigBullet bigBullet = new BigBullet(megaMan);
		bigBullets.add(bigBullet);
		this.getSoundManager().playBulletSound();
	}

	/**
	 * Move a bullet once fired from the ship.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(bullet.getSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	}

	/**
	 * Move a bullet once fired from the boss.
	 * @param bulletBoss the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBulletBoss(BulletBoss bulletBoss){
		if(bulletBoss.getY() - bulletBoss.getSpeed() >= 0){
			bulletBoss.translate(0, bulletBoss.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}

	/** Move a bullet once fired from the second boss.
	 * @param bulletBoss2 the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBulletBoss2(BulletBoss2 bulletBoss2){
		if(bulletBoss2.getY() - bulletBoss2.getSpeed() >= 0){
			bulletBoss2.translate(0, bulletBoss2.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}

	/** Move a "Power Shot" bullet once fired from the ship.
	 * @param bulletBoss2 the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBigBullet(BigBullet bigBullet){
		if(bigBullet.getY() - bigBullet.getBigSpeed() >= 0){
			bigBullet.translate(bigBullet.getBigSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	}

	/**
	 * Create a new ship (and replace current one).
	 */
	public MegaMan newMegaMan(Level1State screen){
		this.megaMan = new MegaMan(screen);
		return megaMan;
	}

	public Floor[] newFloor(Level1State screen, int n){
		floor = new Floor[n];
		for(int i=0; i<n; i++){
			this.floor[i] = new Floor(screen, i);
		}

		return floor;
	}

	public Platform[] newNumPlatforms(Level1State screen, int n){
		numPlatforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.numPlatforms[i] = new Platform(screen, i);
		}
		return numPlatforms;

	}


	/**
	 * Create the first boss.
	 */
	public Boss newBoss(Level1State screen){
		this.boss = new Boss(screen);
		return boss;
	}

	/**
	 * Create the second boss.
	 */
	public Boss newBoss2(Level1State screen){
		this.boss2 = new Boss(screen);
		return boss2;
	}

	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(Level1State screen){
		this.asteroid = new Asteroid(screen);
		return asteroid;
	}

	/**
	 * Create a second asteroid.
	 */
	public Asteroid newAsteroid2(Level1State screen){
		this.asteroid2 = new Asteroid(screen);
		return asteroid2;
	}

	/**
	 * Create a new big asteroid.
	 */
	public BigAsteroid newBigAsteroid(Level1State screen){
		this.bigAsteroid = new BigAsteroid(screen);
		return bigAsteroid;
	}


	
	/**
	 * Move the megaMan up
	 * @param megaMan the megaMan
	 */
	public void moveMegaManUp(MegaMan megaMan){
		if(megaMan.getY() - megaMan.getSpeed() >= 0){
			megaMan.translate(0, -megaMan.getSpeed()*2);
		}
	}

	/**
	 * Move the megaMan down
	 * @param megaMan the megaMan
	 */
	public void moveMegaManDown(MegaMan megaMan, int screenHeight, Floor[] floor){
		for(int i=0; i<9; i++){
			if(megaMan.getY() + megaMan.getSpeed() + megaMan.height < screenHeight - floor[i].getFloorHeight()/2){
				megaMan.translate(0, 2);
			}
		}
	}

	/**
	 * Move the megaMan left
	 * @param megaMan the megaMan
	 */
	public void moveMegaManLeft(MegaMan megaMan){
		if(megaMan.getX() - megaMan.getSpeed() >= 0){
			megaMan.translate(-megaMan.getSpeed(), 0);
		}
	}

	/**
	 * Move the megaMan right
	 * @param megaMan the megaMan
	 */
	public void moveMegaManRight(MegaMan megaMan, int screenWidth){
		if(megaMan.getX() + megaMan.getSpeed() + megaMan.width < screenWidth){
			megaMan.translate(megaMan.getSpeed(), 0);
		}
	}
	// END Moved from GameLogic
}
