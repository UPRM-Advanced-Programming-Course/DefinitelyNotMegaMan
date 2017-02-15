package rbadia.voidspace.main;

import java.awt.Color;

import javax.swing.JLabel;

import rbadia.voidspace.graphics.GraphicsManager;

public class GameScreen2 extends BaseScreen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameScreen2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void drawGameOver() {
		// TODO Auto-generated method stub
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}

	@Override
	protected void drawYouWin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void drawGetReady() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void drawStars(int numberOfStars) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doGameOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doNewGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setGraphicsMan(GraphicsManager graphicsMan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setGameLogic(GameLogic gameLogic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setShipsValueLabel(JLabel shipsValueLabel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setLevelValueLabel(JLabel levelValueLabel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean Gravity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean Fire() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean Fire2() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean Fall() {
		// TODO Auto-generated method stub
		return false;
	}

}
