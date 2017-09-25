package rbadia.voidspace.main;

import javax.swing.JPanel;
import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.sounds.SoundManager;

public abstract class GameState extends JPanel {
	
	protected static final long serialVersionUID = 1L;
	
	private GraphicsManager graphicsManager;
	private GameLogic gameLogic;
	private InputHandler inputHandler;
	private MainFrame mainFrame;
	private GameStatus status;
	private SoundManager soundManager;

	
	// Getters
	public GraphicsManager getGraphicsManager() { return graphicsManager; }
	public GameLogic getGameLogic() { return gameLogic; }
	public InputHandler getInputHandler() { return inputHandler; }
	public MainFrame getMainFrame() { return mainFrame; }
	public GameStatus getStatus() { return status; }
	public SoundManager getSoundManager() { return soundManager; }
	
	public abstract int getBoom();
	public abstract MegaMan getMegaMan();
	public abstract Floor[] getFloor();
	
	// Setters
	protected void setGraphicsManager(GraphicsManager graphicsManager) { this.graphicsManager = graphicsManager; }
	protected void setGameLogic(GameLogic gameLogic) { this.gameLogic = gameLogic; }
	protected void setInputHandler(InputHandler inputHandler) { this.inputHandler = inputHandler; }
	public void setMainFrame ( MainFrame mainFrame) { this.mainFrame = mainFrame; }
	public void setStatus(GameStatus status) { this.status = status; }
	public void setSoundManager(SoundManager soundManager) { this.soundManager = soundManager; }
		
	// Game evolution methods
	protected abstract void updateScreen();	
	protected abstract void drawGameOver();
	protected abstract void drawYouWin();
	protected abstract void drawGetReady();
	protected abstract void drawStars(int numberOfStars);
	protected abstract void initialMessage();
	protected abstract void doGameOver();
	protected abstract void doNewGame();
	
	// User actions and character reactions
	protected abstract boolean Gravity();
	protected abstract boolean Fire();
	protected abstract boolean Fire2();
	protected abstract boolean Fall();	
	public abstract void fireBullet();
	public abstract void fireBigBullet();
	public abstract void moveMegaManUp(MegaMan megaMan);
	public abstract void moveMegaManDown(MegaMan megaMan, int screenHeight, Floor[] floor);
	public abstract void moveMegaManLeft(MegaMan megaMan);
	public abstract void moveMegaManRight(MegaMan megaMan, int screenWidth);
}
