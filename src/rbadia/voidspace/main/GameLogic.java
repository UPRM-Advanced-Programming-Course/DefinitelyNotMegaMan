package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;


/**
 * Handles general game logic and status.
 */
public class GameLogic {

	private long lastBulletTime;
	private long lastExchangeTime;
	private long lastBigBulletTime;
	private int stack= 0;
	private int mute = 0;

	private GameState gameState;

	/**
	 * Create a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameState gameState){
		this.gameState = gameState;
	}

	public GameState getGameState() {
		return gameState;
	}

	public int getMute(){
		return mute;
	}


	/**
	 * Prepare for a new game.
	 */
	public void newGame(){

		// prepare game screen
		gameState.doNewGame();

		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getGameState().getStatus().setGameStarting(false);
				getGameState().getStatus().setGameStarted(true);
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
		if(getGameState().getStatus().isGameStarted()) {
			if (!getGameState().getStatus().isGameOver() && getGameState().getStatus().getShipsLeft() == 0) {
				gameOver();
			}
			if(!getGameState().getStatus().isGameWon() && getGameState().getBoom() == 2) {
				gameWon();
			}
		}
	}

	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		getGameState().getStatus().setGameStarted(false);
		getGameState().getStatus().setGameOver(true);
		gameState.doGameOver();

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(5000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getGameState().getStatus().setGameOver(false);
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
		getGameState().getStatus().setGameWon(true);
		gameState.doGameOver();

		// delay to display "Game Won" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getGameState().getStatus().setGameWon(false);
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
	 * Handle user input after screen update.
	 * @param gameState he game screen
	 */
	public void handleInput(InputHandler ih, GameState gameState){

		GameStatus status = getGameState().getStatus();

		if(!status.isGameOver() && !status.isNewMegaMan() && !status.isGameStarting() && !status.isGameWon()){
			// fire bullet if space is pressed
			if(ih.isSpacePressed()){
				// fire only up to 5 bullets per second
				stack=0;
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBulletTime) > 1000/5){
					lastBulletTime = currentTime;
					getGameState().fireBullet();
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
							getGameState().fireBigBullet();
						}

					}
				}
				else{

				}
			}

			MegaMan megaMan = getGameState().getMegaMan();
			Floor[] floor = getGameState().getFloor();

			if(ih.isShiftPressed()){
				megaMan.setSpeed(megaMan.getDefaultSpeed() * 2 +1);
			}

			if(ih.isUpPressed()){
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBigBulletTime) > 570){ //if i<10 (700)
					lastBigBulletTime = currentTime;
					for(int i=0; i<6; i++){
						getGameState().moveMegaManUp(megaMan);
					}
				}
			}

			if(ih.isDownPressed()){
				getGameState().moveMegaManDown(megaMan, gameState.getHeight(), floor);
			}

			if(ih.isLeftPressed()){
				getGameState().moveMegaManLeft(megaMan);
			}

			if(ih.isRightPressed()){
				getGameState().moveMegaManRight(megaMan, gameState.getWidth());
			}
		}
	}
}
