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
		
		// get game screen
        GameState gameState = new Level1State();

        // init main frame
		MainFrame frame = new MainFrame(gameState);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        gameState.setMainFrame(frame);
        		
		// init game logic handler
		GameLogic gameLogic = new GameLogic(gameState);
		gameState.setGameLogic(gameLogic);
		
       	frame.setGameState(gameState);
		       
		// init input handler
        InputHandler inputHandler = new InputHandler(gameState);
        frame.addKeyListener(inputHandler);
        gameState.setInputHandler(inputHandler);
        
        // show main frame
		frame.setVisible(true);
		
		// init main game loop
		new Thread(new GameLoop(gameState)).start();
	}


}
