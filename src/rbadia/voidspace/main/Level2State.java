package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;

/**
 * Level very similar to LevelState1.  Platforms arranged in triangular form.
 */
public class Level2State extends Level1State {
	
	protected int boom=0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2094575762243216079L;

	// Constructors
	public Level2State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler) {
		super(level, frame, status, gameLogic, inputHandler);
	}

	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
	}
	
	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		MegaMan megaMan = this.getMegaMan();
		//Floor[] floor = this.getFloor();
		Platform[] numPlatforms = this.getPlatforms();
		List<Bullet> bullets = this.getBullets();
		Asteroid asteroid = this.getAsteroid();
		List<BigBullet> bigBullets = this.getBigBullets();
		Graphics2D g2d = getGraphics2D();

		GameStatus status = this.getGameStatus();
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


		//draw Floor
		for(int i=0; i<9; i++){
			graphicsMan.drawFloor(floor[i], g2d, this, i);	
		}


		//		if(level==1){
		//draw Platform LV. 1
		for(int i=0; i<getNumPlatforms(); i++){
			graphicsMan.drawPlatform(numPlatforms[i], g2d, this, i);
			//			}
		}

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

				levelAsteroidsDestroyed++;

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
			status.setLivesLeft(status.getLivesLeft() - 1);
			removeAsteroid(asteroid);
		}

		//Asteroid-Floor collision
		for(int i=0; i<9; i++){
			if(asteroid.intersects(floor[i])){
				removeAsteroid(asteroid);

			}
		}

		// update asteroids destroyed label  
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));

		// update lives left label
		getMainFrame().getShipsValueLabel().setText(Integer.toString(status.getLivesLeft()));

		//update level label
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
	}

	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(this, i);
			if(i<4)	platforms[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);
			if(i==4) platforms[i].setLocation(50 +i*50, getHeight()/2 + 140 - 3*40);
			if(i>4){	
				int k=4;
				platforms[i].setLocation(50 + i*50, getHeight()/2 + 20 + (i-k)*40 );
				k=k+2;
			}
		}
		return platforms;
	}
}
