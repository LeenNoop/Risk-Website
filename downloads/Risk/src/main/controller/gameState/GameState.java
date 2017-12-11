package main.controller.gameState;

/**
 *
 * @author Ben Owen
 *
 */
public abstract class GameState {

	public GameState(GameStateManager gsm) {

	}

	public abstract void setup();

	public abstract void update();

	public abstract void render();

	public abstract void proccessInput();

	public abstract void dispose();
}
