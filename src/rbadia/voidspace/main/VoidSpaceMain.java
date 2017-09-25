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
import javax.swing.JOptionPane;

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

		MainFrame frame = new MainFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int playAgain = 2;
		while(playAgain != 1) {

			GameState level1State = new Level1State(1);
			GameState level2State = new Level1State(2);
			GameState levels[] = { level1State, level2State };

			for (GameState nextLevel : levels) {

				System.out.println("Next Level Started");
				frame.setLevelState(nextLevel);
				nextLevel.setMainFrame(frame);
				GameLogic gameLogic = new GameLogic(nextLevel);
				nextLevel.setGameLogic(gameLogic);
				InputHandler inputHandler = new InputHandler(nextLevel);
				frame.addKeyListener(inputHandler);
				nextLevel.setInputHandler(inputHandler);
				frame.setVisible(true);

				// init main game loop
				Thread nextLevelThread = new Thread(new GameLoop(nextLevel));
				nextLevelThread.start();
				nextLevelThread.join();

				if (nextLevel.getStatus().isGameOver()) {
					break;
				}

			}
			playAgain = JOptionPane.showConfirmDialog(null, "Play Again?", "GAME OVER", JOptionPane.YES_NO_OPTION);
		}
		System.exit(0);
	}
}
