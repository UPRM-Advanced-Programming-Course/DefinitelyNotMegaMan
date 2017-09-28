package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.Timer;



/**
 * Handles general game logic and status.
 */
public class LevelLogic {

	private long lastBulletTime;
	private long lastExchangeTime;
	private long lastBigBulletTime;
	private int stack= 0;
	private int mute = 0;

	private LevelState levelState;

	protected Font originalFont;
	protected Font bigFont;
	protected Font biggestFont;

	/**
	 * Create a new game logic handler
	 * @param gameScreen the game screen
	 */
	public LevelLogic(){
	}

	public LevelState getLevelState() {
		return levelState;
	}

	public void setLevelState(LevelState levelState) {
		this.levelState = levelState;
	}

	public int getMute(){
		return mute;
	}


	/**
	 * Prepare for a new game.
	 */
	public void newGame(){

		// prepare game screen
		//levelState.doLevelStart();

		// delay to display "Get Ready" message for 1.5 seconds
	}

	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(getLevelState().getGameStatus().isGameStarted()) {
			if (!getLevelState().getGameStatus().isGameOver() && getLevelState().getGameStatus().getShipsLeft() == 0) {
				gameOver();
			}
			if(!getLevelState().getGameStatus().isGameWon() && getLevelState().getBoom() == 2) {
				gameWon();
			}
		}
	}

	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		getLevelState().getGameStatus().setGameStarted(false);
		getLevelState().getGameStatus().setGameOver(true);

		levelState.doGameOverScreen();

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(5000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getLevelState().getGameStatus().setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();

		//Change music back to menu screen music
		MegaManMain.audioClip.close();
		MegaManMain.audioFile = new File("audio/menuScreen.wav");
		try {
			MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
			MegaManMain.audioClip.open(MegaManMain.audioStream);
			MegaManMain.audioClip.start();
			MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
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
		getLevelState().getGameStatus().setGameWon(true);
		levelState.doLevelWon();

		// delay to display "Game Won" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getLevelState().getGameStatus().setGameWon(false);
			}
		});
		timer.setRepeats(false);
		timer.start();

		//Change music back to menu screen music
		MegaManMain.audioClip.close();
		MegaManMain.audioFile = new File("audio/menuScreen.wav");
		try {
			MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
			MegaManMain.audioClip.open(MegaManMain.audioStream);
			MegaManMain.audioClip.start();
			MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Draws the "Game Over" message.
	 */
	protected void drawGameOver() {

		LevelState levelState = getLevelState();
		Graphics2D g2d = levelState.getGraphics2D();

		// set original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String gameOverStr = "Game Over";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > levelState.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (levelState.getWidth() - strWidth)/2;
		int strY = (levelState.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.RED);
		g2d.drawString(gameOverStr, strX, strY);
	}

	protected void drawYouWin() {

		LevelState levelState = getLevelState();
		Graphics2D g2d = levelState.getGraphics2D();

		// set original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String youWinStr = "Level " + levelState.getLevel() + " Completed";

		//Font currentFont = biggestFont == null? bigFont : biggestFont;
		Font currentFont = originalFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 5).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(youWinStr);
		if(strWidth > levelState.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(youWinStr);
		}
		int ascent = fm.getAscent();
		int strX = (levelState.getWidth() - strWidth)/2;
		int strY = (levelState.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(youWinStr, strX, strY);

//		g2d.setFont(originalFont);
//		fm = g2d.getFontMetrics();
//		String newGameStr = "Next level starting soon";
//		strWidth = fm.stringWidth(newGameStr);
//		strX = (levelState.getWidth() - strWidth)/2;
//		strY = (levelState.getHeight() + fm.getAscent())/2 + ascent + 16;
//		g2d.setPaint(Color.YELLOW);
//		g2d.drawString(newGameStr, strX, strY);

		//boom=3;	//Change value in order for the next level to start

		//		boomReset();
		//		healthReset();
		//		delayReset();
	}

	/**
	 * Display initial game title screen.
	 */
	protected void drawInitialMessage() {

		LevelState levelState = getLevelState();
		Graphics2D g2d = levelState.getGraphics2D();

		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String gameTitleStr = "MegaMAN !!!";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > levelState.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (levelState.getWidth() - strWidth)/2;
		int strY = (levelState.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start the Level";
		strWidth = fm.stringWidth(newGameStr);
		strX = (levelState.getWidth() - strWidth)/2;
		strY = (levelState.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String itemGameStr = "Press <I> for Item Menu.";
		strWidth = fm.stringWidth(itemGameStr);
		strX = (levelState.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(itemGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String shopGameStr = "Press <S> for Shop Menu.";
		strWidth = fm.stringWidth(shopGameStr);
		strX = (levelState.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(shopGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (levelState.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	protected void drawGetReady() {
		LevelState levelState = getLevelState();
		Graphics2D g2d = levelState.getGraphics2D();

		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String readyStr = "Get Ready for Level " + levelState.getLevel(); 
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (levelState.getWidth() - strWidth)/2;
		int strY = (levelState.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}



	public void stateTransition(InputHandler ih, LevelState levelState){
		GameStatus status = getLevelState().getGameStatus();
		switch (levelState.getCurrentState()) {
		case LevelState.START_STATE:
			levelState.setCurrentState(LevelState.INITIAL_SCREEN);
			break;

		case LevelState.INITIAL_SCREEN:
			levelState.doInitialScreen();
			handleKeysDuringInitialScreen(ih, levelState);
			break;

		case LevelState.GETTING_READY:
			levelState.doGettingReady();
			levelState.setCurrentState(LevelState.PLAYING);
			break;

		case LevelState.PLAYING:
			levelState.doPlaying();
			handleKeysDuringPlay(ih, levelState);
			if(status.getShipsLeft() == 0) {
				levelState.setCurrentState(LevelState.GAME_OVER_SCREEN);
			}
			if(levelState.isLevelWon()) {
				levelState.setCurrentState(LevelState.LEVEL_WON);
			}
			break;

		case LevelState.NEW_MEGAMAN:
			// TODO Verify that this state is activated when MegaMan dies
			break;

		case LevelState.GAME_OVER_SCREEN:
			levelState.doGameOverScreen();
			levelState.setCurrentState(LevelState.GAME_OVER);
			break;

		case LevelState.GAME_OVER:
			levelState.doGameOver();
			break;

		case LevelState.LEVEL_WON:
			levelState.doLevelWon();
			break;
		}
	}


	public void handleKeysDuringInitialScreen(InputHandler ih, LevelState levelState) {
		if(ih.isSpacePressed()) {
			ih.reset();
			levelState.setCurrentState(LevelState.GETTING_READY);	
			return;
		}
		if(ih.isSPressed()) {
			JOptionPane.showMessageDialog( null, 
					"Item:                Price\r\n"+
							"\r\n"+
							"Extra Life:      1500\r\n"+ 
							"Power Shot:  1000\r\n"+
					"\r\n");
			return;
		}
		if(ih.isIPressed()) {
			JOptionPane.showMessageDialog( null, 
					"Power Up:     Explanation\r\n"+
							"\r\n"+
							"Extra Life:      Gives an extra life (One Extra Life per second)\r\n"+ 
							"                           (Press E to buy, limit of one life per second.)\r\n" +
							"Power Shot:  Activates the Power Shot which kills the asteroid in one hit\r\n"+
					"                           (Press Q to buy, afterwards press Q to fire.)\r\n");
			return;
		}
	}

	public void handleKeysDuringPlay(InputHandler ih, LevelState levelState) {

		GameStatus status = getLevelState().getGameStatus();

		// fire bullet if space is pressed
		if(ih.isSpacePressed()){
			// fire only up to 5 bullets per second
			stack=0;
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBulletTime) > 1000/5){
				lastBulletTime = currentTime;
				getLevelState().fireBullet();
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
						getLevelState().fireBigBullet();
					}

				}
			}
			else{

			}
		}

		if(ih.isShiftPressed()){
			getLevelState().speedUpMegaMan();
		}

		if(ih.isUpPressed()){
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBigBulletTime) > 570){
				lastBigBulletTime = currentTime;
				for(int i=0; i<6; i++){
					getLevelState().moveMegaManUp();
				}
			}
		}

		if(ih.isDownPressed()){
			getLevelState().moveMegaManDown();
		}

		if(ih.isLeftPressed()){
			getLevelState().moveMegaManLeft();
		}

		if(ih.isRightPressed()){
			getLevelState().moveMegaManRight();
		}
	}

	/**
	 * Handle user input after screen update.
	 * @param levelState he game screen
	 */
	public void handleInput(InputHandler ih, LevelState levelState){

		GameStatus status = getLevelState().getGameStatus();

		if(status.isGameNotStarted() && ih.isSpacePressed()) {
			// new game
			//lastBulletTime = System.currentTimeMillis();
			ih.reset();
			this.drawGetReady();
			// delay to display "Get Ready" message for 1.5 seconds
			Timer timer = new Timer(1500, new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					getLevelState().getGameStatus().setGameStarting(false);
					getLevelState().getGameStatus().setGameStarted(true);
				}
			});
			timer.setRepeats(false);
			timer.start();

			//status.setGameStarted(true);

			//Music
			//changes music from "menu music" to "ingame music"
			MegaManMain.audioClip.close();
			MegaManMain.audioFile = new File("audio/mainGame.wav");
			try {
				MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
				MegaManMain.audioClip.open(MegaManMain.audioStream);
				MegaManMain.audioClip.start();
				MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
			return;
		}
		if(status.isGameNotStarted() && ih.isSPressed()) {
			JOptionPane.showMessageDialog( null, 
					"Item:                Price\r\n"+
							"\r\n"+
							"Extra Life:      1500\r\n"+ 
							"Power Shot:  1000\r\n"+
					"\r\n");
			return;
		}
		if(status.isGameNotStarted() && ih.isIPressed()) {
			JOptionPane.showMessageDialog( null, 
					"Power Up:     Explanation\r\n"+
							"\r\n"+
							"Extra Life:      Gives an extra life (One Extra Life per second)\r\n"+ 
							"                           (Press E to buy, limit of one life per second.)\r\n" +
							"Power Shot:  Activates the Power Shot which kills the asteroid in one hit\r\n"+
					"                           (Press Q to buy, afterwards press Q to fire.)\r\n");
			return;
		}

		// the game has not started yet and no key is pressed
		if(!status.isGameStarted()){
			// draw game title screen
			drawInitialMessage();
			return;
		}

		//		// if the game is starting, draw "Get Ready" message
		//		if(status.isGameStarting()){
		//			drawGetReady();
		//			return;
		//		}

		if(status.isGameStarted()){
			// fire bullet if space is pressed
			if(ih.isSpacePressed()){
				// fire only up to 5 bullets per second
				stack=0;
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBulletTime) > 1000/5){
					lastBulletTime = currentTime;
					getLevelState().fireBullet();
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
							getLevelState().fireBigBullet();
						}

					}
				}
				else{

				}
			}

			//MegaMan megaMan = getLevelState().getMegaMan();

			if(ih.isShiftPressed()){
				getLevelState().speedUpMegaMan();
			}

			if(ih.isUpPressed()){
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBigBulletTime) > 570){ //if i<10 (700)
					lastBigBulletTime = currentTime;
					for(int i=0; i<6; i++){
						getLevelState().moveMegaManUp();
					}
				}
			}

			if(ih.isDownPressed()){
				getLevelState().moveMegaManDown();
			}

			if(ih.isLeftPressed()){
				getLevelState().moveMegaManLeft();
			}

			if(ih.isRightPressed()){
				getLevelState().moveMegaManRight();
			}
		}

		// if the game is over, draw the "Game Over" message
		if(status.isGameStarted()) {
			if (!status.isGameOver() && status.getShipsLeft() == 0) {
				gameOver();
			}
			if(!status.isGameWon() && getLevelState().getBoom() == 2) {
				gameWon();
			}
		}

		if(status.isGameOver()){
			// draw the message
			drawGameOver();
			//
			//			long currentTime = System.currentTimeMillis();
			//			// draw the explosions until their time passes
			//			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
			//				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			//			}
			//			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
			//				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			//			}
			return;
		}

		//if the game is won, draw the "You Win!!!" message
		if(status.isGameWon()){
			// draw the message
			drawYouWin();

			//			long currentTime = System.currentTimeMillis();
			//			// draw the explosions until their time passes
			//			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
			//				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			//			}
			return;
		}
	}
	
	public static void delay(long millis) {
		try{
			// sleep/wait for 1/60th of a second,
			// for a resulting refresh rate of 60 frames per second (fps) 
			Thread.sleep(millis);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
