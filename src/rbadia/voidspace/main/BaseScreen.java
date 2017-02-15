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

	protected BufferedImage backBuffer;
	protected Graphics2D g2d;

	protected static final int NEW_SHIP_DELAY = 500;
	protected static final int NEW_ASTEROID_DELAY = 500;
	//	protected static final int NEW_ASTEROID_2_DELAY = 500;
	//	protected static final int NEW_BIG_ASTEROID_DELAY = 500;

	protected long lastShipTime;
	protected long lastAsteroidTime;
	//	protected long lastAsteroid2Time;
	//	protected long lastBigAsteroidTime;

	protected Rectangle asteroidExplosion;
	//	protected Rectangle bigAsteroidExplosion;
	//	protected Rectangle shipExplosion;
	//	protected Rectangle bossExplosion;

	protected JLabel shipsValueLabel;
	protected JLabel destroyedValueLabel;
	protected JLabel levelValueLabel;

	protected Random rand;

	protected Font originalFont;
	protected Font bigFont;
	protected Font biggestFont;

	protected GameStatus status;
	protected SoundManager soundMan;
	protected GraphicsManager graphicsMan;
	protected GameLogic gameLogic;
	protected InputHandler input;
	protected Platform[] platforms;

	protected int boom=0;
	protected int damage=0;
	//	protected int scroll=0;
	//	protected int bossHealth=0;
	//	protected int delay=0;
	
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
