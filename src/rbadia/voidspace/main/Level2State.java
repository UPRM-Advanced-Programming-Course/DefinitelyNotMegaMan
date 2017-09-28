package rbadia.voidspace.main;
import rbadia.voidspace.model.Platform;

/**
 * Level very similar to LevelState1.  Platforms arranged in triangular form.
 */
public class Level2State extends Level1State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2094575762243216079L;

	// Constructors
	public Level2State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler) {
		super(level, frame, status, gameLogic, inputHandler);
	}

	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
	}

	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(this, i);
			if(i<4)	platforms[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);
			if(i==4) platforms[i].setLocation(50 +i*50, getHeight()/2 + 140 - 3*40);
			if(i>4){	
				int k=4;
				platforms[i].setLocation(50 + i*50, getHeight()/2 + 20 + (i-k)*40 );
				k=k+2;
			}
		}
		return platforms;
	}
}
