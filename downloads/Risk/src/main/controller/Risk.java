package main.controller;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.audio.Mp3.Music;

import main.controller.gameState.GameStateManager;

/**
 *
 * @author Ben Owen
 *
 */
public class Risk implements ApplicationListener {

	private GameStateManager gameStateManager;

	/*
	 * Note: apparently music is heavy on resources, so we need to be carful with it
	 * https://github.com/libgdx/libgdx/wiki/Streaming-music
	 */
	private Music music;

	@Override
	public void create() {
		gameStateManager = new GameStateManager();

		//imports mp3 file of music
		music = (Music) Gdx.audio.newMusic(Gdx.files.internal("Assets/ussr_national_anthem.mp3"));

		//make sure the song keeps on playing
		music.setLooping(true);

		// guessing...but think that is 30%
		music.setVolume(0.5f);

		// starts song
		music.play();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {	
		
		/*
		 * TODO the update and render methods need to be handle better i.e.
		 * render loops at the fps (frames per second) limit...probably 30 - 60fps.
		 * The update could just run as fast as we want...maybe.
		 * Libgdx render method can pass a delta time (what ever that is) that can be used to do the above
		 */
		gameStateManager.update();
		gameStateManager.Render();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
