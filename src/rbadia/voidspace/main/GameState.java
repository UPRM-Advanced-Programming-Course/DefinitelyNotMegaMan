package rbadia.voidspace.main;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public abstract class GameState extends JPanel {
	
	protected static final long serialVersionUID = 1L;
	
	// Getters
	public abstract GameStatus getStatus();
	public abstract int getBoom();
	public abstract MegaMan getMegaMan();
	public abstract Floor[] getFloor();
	public abstract GameLogic getGameLogic();
	public abstract InputHandler getInputHandler();
	
	// Setters
	protected abstract void setGraphicsMan(GraphicsManager graphicsMan);
	protected abstract void setGameLogic(GameLogic gameLogic);
	protected abstract void setInputHandler(InputHandler inputHandler);
	public abstract void setMainFrame ( MainFrame mainFrame);
		
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
