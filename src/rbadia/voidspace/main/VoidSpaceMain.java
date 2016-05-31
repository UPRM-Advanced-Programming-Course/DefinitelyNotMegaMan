package rbadia.voidspace.main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 * Main game class. Starts the game.
 */
public class VoidSpaceMain {
	
	//Starts playing menu music as soon as the game frame is created
	
	public static AudioInputStream audioStream;
	public static Clip audioClip;
	public static File audioFile;	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException, IOException  {
		
		//Music
		//allows music to be played while playing
		audioFile = new File("audio/menuScreen.wav");
		try {
			audioStream = AudioSystem.getAudioInputStream(audioFile);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		
		AudioFormat format = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		
		try {
			audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.start();
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// init main frame
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// get game screen
        GameScreen gameScreen = frame.getGameScreen();
		
		// init game logic handler
		GameLogic gameLogic = new GameLogic(gameScreen);
		
		// pass some variables to game screen
        gameScreen.setGameLogic(gameLogic);
        
		// init input handler
        InputHandler inputHandler = new InputHandler(gameLogic);
        frame.addKeyListener(inputHandler);
        
        // show main frame
		frame.setVisible(true);
		
		// init main game loop
		new Thread(new GameLoop(gameScreen, gameLogic, inputHandler)).start();
	}


}
