package rbadia.voidspace.main;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public abstract class BaseScreen extends JPanel {
	protected static final long serialVersionUID = 1L;
	
	protected abstract void initialize();
	
	protected abstract void updateScreen();	
	
	protected abstract void drawGameOver();
	
	protected abstract void drawYouWin();
	
	protected abstract void drawGetReady();
	
	protected abstract void drawStars(int numberOfStars);
	
	protected abstract void initialMessage();
	
	protected abstract void doGameOver();
	
	protected abstract void doNewGame();
	
	protected abstract void setGraphicsMan(GraphicsManager graphicsMan);
	
	protected abstract void setGameLogic(GameLogic gameLogic);
	
	protected abstract void setDestroyedValueLabel(JLabel destroyedValueLabel);
	
	protected abstract void setShipsValueLabel(JLabel shipsValueLabel);
	
	protected abstract void setLevelValueLabel(JLabel levelValueLabel);
	
	protected abstract boolean Gravity();
	
	protected abstract boolean Fire();
	
	protected abstract boolean Fire2();
	
	protected abstract boolean Fall();	

}
