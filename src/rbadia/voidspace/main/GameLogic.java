package rbadia.voidspace.main;

import java.awt.event.ActionEvent;



import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.BulletBoss;
import rbadia.voidspace.model.BulletBoss2;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.sounds.SoundManager;


/**
 * Handles general game logic and status.
 */
public class GameLogic {
	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;

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
	private Floor[] floor;
	
	private long lastBulletTime;
	private long lastExchangeTime;
	private long lastBigBulletTime;
	private int stack= 0;
	private int mute = 0;


	/**
	 * Create a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;

		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();

		// init some variables
		bullets = new ArrayList<Bullet>();
		bulletsBoss = new ArrayList<BulletBoss>();
		bulletsBoss2 = new ArrayList<BulletBoss2>();
		bigBullets = new ArrayList<BigBullet>();
	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}

	public SoundManager getSoundMan() {
		return soundMan;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}
	
	public int getMute(){
		return mute;
	}


	/**
	 * Prepare for a new game.
	 */
	public void newGame(){
		status.setGameStarting(true);

		// init game variables
		bullets = new ArrayList<Bullet>();
		bulletsBoss = new ArrayList<BulletBoss>();
		bulletsBoss2 = new ArrayList<BulletBoss2>();
		bigBullets = new ArrayList<BigBullet>();
		//numPlatforms = new Platform[5];

		status.setShipsLeft(3);
		status.setLevel(1);
		status.setGameOver(false);
		status.setAsteroidsDestroyed(0);
		status.setNewAsteroid(false);
		status.setNewAsteroid2(false);
		status.setNewBigAsteroid(false);
		//status.setNewFloor(false);

		// init the ship and the asteroid
		newMegaMan(gameScreen);
		newFloor(gameScreen, 9);

		newNumPlatforms(gameScreen, 8);

		//        newPlatform(gameScreen/*, 1*/);
		//        newPlatform1(gameScreen);
		newBoss(gameScreen);
		newBoss2(gameScreen);
		newAsteroid(gameScreen);
		newAsteroid2(gameScreen);
		newBigAsteroid(gameScreen);

		// prepare game screen
		gameScreen.doNewGame();

		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0){
				gameOver();
			}
		}
		if(!status.isGameWon()){
			if(gameScreen.getBoom() == 2)
				gameWon();
		}
	}

	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(5000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();

		//Change music back to menu screen music
		VoidSpaceMain.audioClip.close();
		VoidSpaceMain.audioFile = new File("audio/menuScreen.wav");
		try {
			VoidSpaceMain.audioStream = AudioSystem.getAudioInputStream(VoidSpaceMain.audioFile);
			VoidSpaceMain.audioClip.open(VoidSpaceMain.audioStream);
			VoidSpaceMain.audioClip.start();
			VoidSpaceMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Actions to take if game is won.
	 */

	//GAME LOOPS ON THE FIRST GAMESCREEN AND RESETS ALL VARIABLE COUNTERS
	public void gameWon(){
		//status.setGameStarted(false);  //SENDS TO MAIN SCREEN/ IF COMMENTED OUT LOOPS THE GAME
		status.setGameWon(true);
		gameScreen.doGameOver();

		// delay to display "Game Won" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameWon(false);
			}
		});
		timer.setRepeats(false);
		timer.start();

		//Change music back to menu screen music
		VoidSpaceMain.audioClip.close();
		VoidSpaceMain.audioFile = new File("audio/menuScreen.wav");
		try {
			VoidSpaceMain.audioStream = AudioSystem.getAudioInputStream(VoidSpaceMain.audioFile);
			VoidSpaceMain.audioClip.open(VoidSpaceMain.audioStream);
			VoidSpaceMain.audioClip.start();
			VoidSpaceMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(megaMan);
		bullets.add(bullet);
		soundMan.playBulletSound();
	}

	/**
	 * Fire the "Power Shot" bullet
	 */
	public void fireBigBullet(){
		BigBullet bigBullet = new BigBullet(megaMan);
		bigBullets.add(bigBullet);
		soundMan.playBulletSound();
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
	public MegaMan newMegaMan(GameScreen screen){
		this.megaMan = new MegaMan(screen);
		return megaMan;
	}

	public Floor[] newFloor(GameScreen screen, int n){
		floor = new Floor[n];
		for(int i=0; i<n; i++){
			this.floor[i] = new Floor(screen, i);
		}

		return floor;
	}

	public Platform[] newNumPlatforms(GameScreen screen, int n){
		numPlatforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.numPlatforms[i] = new Platform(screen, i);
		}
		return numPlatforms;

	}


	/**
	 * Create the first boss.
	 */
	public Boss newBoss(GameScreen screen){
		this.boss = new Boss(screen);
		return boss;
	}

	/**
	 * Create the second boss.
	 */
	public Boss newBoss2(GameScreen screen){
		this.boss2 = new Boss(screen);
		return boss2;
	}

	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(GameScreen screen){
		this.asteroid = new Asteroid(screen);
		return asteroid;
	}

	/**
	 * Create a second asteroid.
	 */
	public Asteroid newAsteroid2(GameScreen screen){
		this.asteroid2 = new Asteroid(screen);
		return asteroid2;
	}

	/**
	 * Create a new big asteroid.
	 */
	public BigAsteroid newBigAsteroid(GameScreen screen){
		this.bigAsteroid = new BigAsteroid(screen);
		return bigAsteroid;
	}

	/**
	 * Returns the ship.
	 * @return the ship
	 */
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
	 * Handle user input after screen update.
	 * @param gameScreen he game screen
	 */
	public void handleInput(InputHandler ih, GameScreen gameScreen){
		
		GameStatus status = this.getStatus();

		if(!status.isGameOver() && !status.isNewMegaMan() && !status.isGameStarting() && !status.isGameWon()){
			// fire bullet if space is pressed
			if(ih.isSpacePressed()){
				// fire only up to 5 bullets per second
				stack=0;
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBulletTime) > 1000/5){
					lastBulletTime = currentTime;
					this.fireBullet();
				}
			}

			if(ih.isEPressed()){
				if(status.getAsteroidsDestroyed()>= 1500){
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastExchangeTime > 1000)){
						lastExchangeTime = currentTime;
						status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() - 1500);
						status.setShipsLeft(status.getShipsLeft() + 1);
					}
				}
			}

			if(ih.isQPressed()){
				if(!status.isGameOver() && !status.isNewMegaMan() && !status.isGameStarting() && !status.isGameWon()){
					if(stack==0 && status.getAsteroidsDestroyed()>= 0){
						stack++;
						status.setAsteroidsDestroyed(status.getAsteroidsDestroyed()-0);
					}
					else if(stack>= 1){
						long currentTime = System.currentTimeMillis();
						if((currentTime - lastBigBulletTime) > 1000){
							lastBigBulletTime = currentTime;
							this.fireBigBullet();
						}

					}
				}
				else{

				}
			}

			MegaMan megaMan = this.getMegaMan();
			Floor[] floor = this.getFloor();

			if(ih.isShiftPressed()){
				megaMan.setSpeed(megaMan.getDefaultSpeed() * 2 +1);
			}

			if(ih.isUpPressed()){
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBigBulletTime) > 570){ //if i<10 (700)
					lastBigBulletTime = currentTime;
					for(int i=0; i<6; i++){
						moveMegaManUp(megaMan);
					}
				}
			}

			if(ih.isDownPressed()){
				moveMegaManDown(megaMan, gameScreen.getHeight(), floor);
			}

			if(ih.isLeftPressed()){
				moveMegaManLeft(megaMan);
			}

			if(ih.isRightPressed()){
				moveMegaManRight(megaMan, gameScreen.getWidth());
			}
		}
	}




	/**
	 * Move the megaMan up
	 * @param megaMan the megaMan
	 */
	private void moveMegaManUp(MegaMan megaMan){
		if(megaMan.getY() - megaMan.getSpeed() >= 0){
			megaMan.translate(0, -megaMan.getSpeed()*2);
		}
	}

	/**
	 * Move the megaMan down
	 * @param megaMan the megaMan
	 */
	private void moveMegaManDown(MegaMan megaMan, int screenHeight, Floor[] floor){
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
	private void moveMegaManLeft(MegaMan megaMan){
		if(megaMan.getX() - megaMan.getSpeed() >= 0){
			megaMan.translate(-megaMan.getSpeed(), 0);
		}
	}

	/**
	 * Move the megaMan right
	 * @param megaMan the megaMan
	 */
	private void moveMegaManRight(MegaMan megaMan, int screenWidth){
		if(megaMan.getX() + megaMan.getSpeed() + megaMan.width < screenWidth){
			megaMan.translate(megaMan.getSpeed(), 0);
		}
	}
}
