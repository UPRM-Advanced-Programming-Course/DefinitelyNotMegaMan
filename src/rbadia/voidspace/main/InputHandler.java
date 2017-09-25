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

//import rbadia.voidspace.model.Floor;
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



	private LevelState levelState;
	//private GameScreen gScreen;
	
	public LevelState getLevelState() { return levelState; }

	/**
	 * Create a new input handler
	 * @param gameLogic the game logic handler
	 */
	public InputHandler(LevelState levelState){
		this.levelState = levelState;
	}

	public boolean isLeftPressed() {
		return leftIsPressed;
	}

	public boolean isRightPressed() {
		return rightIsPressed;
	}

	public boolean isDownPressed() {
		return downIsPressed;
	}

	public boolean isUpPressed() {
		return upIsPressed;
	}

	public boolean isSpacePressed() {
		return spaceIsPressed;
	}

	public boolean isShiftPressed() {
		return shiftIsPressed;
	}

	public boolean isEPressed() {
		return eIsPressed;
	}

	public boolean isQPressed() {
		return qIsPressed;
	}

	public boolean isMPressed() {
		return mIsPressed;
	}


	/**
	 * Handle a key input event.
	 */
	public void keyPressed(KeyEvent e) {
		GameStatus status = levelState.getStatus();
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
				//lastBulletTime = System.currentTimeMillis();
				leftIsPressed = false;
				rightIsPressed = false;
				downIsPressed = false;
				upIsPressed = false;
				spaceIsPressed = false;
				levelState.getGameLogic().newGame();

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
						"Item:                Price\r\n"+
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
								"                           (Press E to buy, limit of one life per second.)\r\n" +
								"Power Shot:  Activates the Power Shot which kills the asteroid in one hit\r\n"+
						"                           (Press Q to buy, afterwards press Q to fire.)\r\n");

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
			MegaMan megaMan = this.getLevelState().getMegaMan(); 
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

}
