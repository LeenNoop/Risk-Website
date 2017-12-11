package main.controller.gameState;

/**
 *
 * @author Ben Owen
 *
 */
public class GameStateManager {

	//this is public so you can set the state any class that has an instance of "this" class
	public enum State {PLAYSTATE, SPLASHSCREENSTATE, MENUSTATE};
	private State state;

	private GameState gameState;

	public GameStateManager(){
		this(State.SPLASHSCREENSTATE);
	}

	public GameStateManager(State splashscreenstate) {
		gameState = new SplashScreen(this);
		setGameState(splashscreenstate);
	}

	public void update() {
		gameState.update();
	}

	public void Render() {
		gameState.render();
	}

	public void setGameState(State state) {
		switch (state) {
			case PLAYSTATE:
				gameState = new PlayState(this);
				break;
			case SPLASHSCREENSTATE:
				gameState = new SplashScreen(this);
				break;
			case MENUSTATE:
				gameState = new MenuState(this);
				break;
		}
		gameState.setup();
	}




}
