package rbadia.voidspace.main;

/**
 * Implements the main game loop, i.e. what actions should be taken on each frame update.
 */
public class GameLoop implements Runnable{
	private LevelState levelState;
	private GameLogic gameLogic;
	private InputHandler inputHandler;

	/**
	 * Creates a new game loop.
	 * @param levelState the game screen
	 * @param gameLogic the game logic handler
	 * @param inputHandler the user input handler
	 */
	public GameLoop(LevelState levelState){
		this.levelState = levelState;
		this.gameLogic = levelState.getGameLogic();
		this.inputHandler = levelState.getInputHandler();
	}

	/**
	 * Implements the run interface method. Should be called by the running thread.
	 */
	public void run() {
		while(!levelState.getStatus().isGameOver() && !levelState.isLevelWon()) {
			// main game loop
			try{
				// sleep/wait for 1/60th of a second,
				// for a resulting refresh rate of 60 frames per second (fps) 
				Thread.sleep(1000/60);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			// check game or level ending conditions
			gameLogic.checkConditions();
			
			gameLogic.handleInput(inputHandler, levelState);
			
			// update the game graphics
			levelState.updateScreen();
			
			// handle input
			gameLogic.handleInput(inputHandler, levelState);
			
			// repaint the graphics unto screen
			levelState.repaint();
		}
	}

}