package rbadia.voidspace.main;

import java.awt.event.KeyEvent;


import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.JOptionPane;

import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;

/**
 * Handles user input events.
 */
public class InputHandler implements KeyListener{
	private boolean leftIsPressed;
	private boolean rightIsPressed;
	private boolean downIsPressed;
	private boolean upIsPressed;
	private boolean spaceIsPressed = false;
	private boolean shiftIsPressed;
	private boolean eIsPressed;
	private boolean qIsPressed;
	private boolean mIsPressed;

	private long lastBulletTime;
	private long lastExchangeTime;
	private long lastBigBulletTime;
	private int stack= 0;
	private int mute = 0;


	private GameLogic gameLogic;



	/**
	 * Create a new input handler
	 * @param gameLogic the game logic handler
	 */
	public InputHandler(GameLogic gameLogic){
		this.gameLogic = gameLogic;
	}

	/**
	 * Handle user input after screen update.
	 * @param gameScreen he game screen
	 */
	public void handleInput(GameScreen gameScreen){
		GameStatus status = gameLogic.getStatus();

		if(!status.isGameOver() && !status.isNewMegaMan() && !status.isGameStarting() && !status.isGameWon()){
			// fire bullet if space is pressed
			if(spaceIsPressed){
				// fire only up to 5 bullets per second
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBulletTime) > 1000/5){
					lastBulletTime = currentTime;
					gameLogic.fireBullet();
				}
			}

			if(eIsPressed){
				if(status.getAsteroidsDestroyed()>= 1500){
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastExchangeTime > 1000)){
						lastExchangeTime = currentTime;
						status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() - 1500);
						status.setShipsLeft(status.getShipsLeft() + 1);
					}
				}
			}

			if(qIsPressed){
				if(!status.isGameOver() && !status.isNewMegaMan() && !status.isGameStarting() && !status.isGameWon()){
					if(stack==0 && status.getAsteroidsDestroyed()>= 0){
						stack++;
						status.setAsteroidsDestroyed(status.getAsteroidsDestroyed()-0);
					}
					else if(stack>= 1){
						long currentTime = System.currentTimeMillis();
						if((currentTime - lastBigBulletTime) > 1000){
							lastBigBulletTime = currentTime;
							gameLogic.fireBigBullet();
						}

					}
				}
				else{

				}
			}

			//WIP
//			if(mIsPressed){
//				mute=1;
//			}

			MegaMan megaMan = gameLogic.getMegaMan();
			Floor floor = gameLogic.getFloor();

			if(shiftIsPressed){
				megaMan.setSpeed(megaMan.getDefaultSpeed() * 2);
			}

			if(upIsPressed){
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBigBulletTime) > 500){ //if i<10 (700)
					lastBigBulletTime = currentTime;
					for(int i=0; i<6; i++){
						moveMegaManUp(megaMan);
					}
				}
			}

			if(downIsPressed){
				moveMegaManDown(megaMan, gameScreen.getHeight(), floor);
			}

			if(leftIsPressed){
				moveMegaManLeft(megaMan);
			}

			if(rightIsPressed){
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
	private void moveMegaManDown(MegaMan megaMan, int screenHeight, Floor floor){
		if(megaMan.getY() + megaMan.getSpeed() + megaMan.height < screenHeight - floor.getFloorHeight()/2){
			megaMan.translate(0, megaMan.getSpeed());
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
	/**
	 * Handle a key input event.
	 */
	public void keyPressed(KeyEvent e) {
		GameStatus status = gameLogic.getStatus();
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting() && !status.isGameWon()){
			}
			else{
				this.upIsPressed = true;
			}
			break;
		case KeyEvent.VK_DOWN:
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting() && !status.isGameWon()){
			}
			else{
				this.downIsPressed = true;
			}
			break;
		case KeyEvent.VK_LEFT:
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting() && !status.isGameWon()){
			}
			else{
				this.leftIsPressed = true;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting() && !status.isGameWon()){
			}
			else{
				this.rightIsPressed = true;
			}
			break;
		case KeyEvent.VK_SPACE:
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting() && !status.isGameWon()){
				// new game
				lastBulletTime = System.currentTimeMillis();
				leftIsPressed = false;
				rightIsPressed = false;
				downIsPressed = false;
				upIsPressed = false;
				spaceIsPressed = false;
				stack=0;
				gameLogic.newGame();
				
				//WIP
//				if(mute==0){
					
				//Music
				//changes music from "menu music" to "ingame music"
					VoidSpaceMain.audioClip.close();
					VoidSpaceMain.audioFile = new File("audio/mainGame.wav");
					try {
						VoidSpaceMain.audioStream = AudioSystem.getAudioInputStream(VoidSpaceMain.audioFile);
						VoidSpaceMain.audioClip.open(VoidSpaceMain.audioStream);
						VoidSpaceMain.audioClip.start();
						VoidSpaceMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
//				}
			}
			else{
				this.spaceIsPressed = true;

			}
			break;
		case KeyEvent.VK_SHIFT:
			this.shiftIsPressed = true;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(1);
			break;
		case KeyEvent.VK_E:
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting() && !status.isGameWon()){
			}
			else if(status.getAsteroidsDestroyed() < 1500){
			}
			else{
				this.eIsPressed = true;
			}
			break;
		case KeyEvent.VK_S:
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting() && !status.isGameWon()){
				JOptionPane.showMessageDialog( null, 
						"Item:     Price\r\n"+
								"\r\n"+
								"Extra Life:      1500\r\n"+ 
								"Power Shot:  1000\r\n"+
						"\r\n");

			}
			else{
			}
			break;
		case KeyEvent.VK_I:
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting() && !status.isGameWon()){
				JOptionPane.showMessageDialog( null, 
						"Power Up:     Explanation\r\n"+
								"\r\n"+
								"Extra Life:      Gives an extra life (One Extra Life per second)\r\n"+ 

								"Power Shot:  Activates the Power Shot which kills all asteroid in one hit\r\n"+
						"                           (Press Q to buy, afterwards press or hold Q to fire.)\r\n");

			}
			else{
			}
			break;

		case KeyEvent.VK_Q:
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting() && !status.isGameWon()){		
			}
			else{
				this.qIsPressed= true;
			}
			break;

		case KeyEvent.VK_M:
			this.mIsPressed= true;
			break;
		}


		e.consume();
	}

	/**
	 * Handle a key release event.
	 */
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			this.upIsPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = false;
			break;
		case KeyEvent.VK_SPACE:
			this.spaceIsPressed = false;
			break;
		case KeyEvent.VK_SHIFT:
			this.shiftIsPressed = false;
			MegaMan megaMan = gameLogic.getMegaMan(); 
			megaMan.setSpeed(megaMan.getDefaultSpeed());
			break;
		case KeyEvent.VK_E:
			this.eIsPressed = false;
			break;
		case KeyEvent.VK_Q:
			this.qIsPressed = false;
			break;

		case KeyEvent.VK_M:
			this.mIsPressed = false;
			break;
		}
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
		// not used
	}

	public boolean getSpace(){
		return spaceIsPressed;
	}

	public int getMute(){
		return mute;
	}
}
