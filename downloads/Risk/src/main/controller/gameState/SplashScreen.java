package main.controller.gameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import main.controller.gameState.GameStateManager.State;

/**
 *
 * @author Ben Owen
 *
 */
public class SplashScreen extends GameState{

	private SpriteBatch batch;
	private Sprite splashScreenSprite;
	private Sprite thugLifeGlassesSprite;
	private Texture splashScreen;
	private Texture thugLifeGlasses;
	private GameStateManager gsm;

	private float height = 1080;
	private float width = 1920;
	private float startHeight = height/100;
	private float startWidth = width/100;
	private float currentHeight = startHeight;
	private float currentWidth = startWidth;

	private float rotationSpeed = 6;
	private float numberOfRotations = 2;

	private float splashScreenDisplayTime = 0;

	private long startTime;

	public SplashScreen(GameStateManager gsm) {
		super(gsm);
		this.gsm = gsm;
	}

	@Override
	public void setup() {
		startTime = System.currentTimeMillis();
		batch = new SpriteBatch();

		splashScreen = new Texture(Gdx.files.internal("Assets/panda.jpg"));
		thugLifeGlasses = new Texture(Gdx.files.internal("Assets/thugLifeGlasses.png"));

		splashScreenSprite = new Sprite(splashScreen);
		splashScreenSprite.setSize(startWidth, startHeight);

		thugLifeGlassesSprite = new Sprite(thugLifeGlasses);
		thugLifeGlassesSprite.setSize(400, 300);
		thugLifeGlassesSprite.setPosition(450 - 2000, 560);
		//thugLifeGlassesSprite.setPosition(100, 100);

	}

	@Override
	public void update() {
		//after some time has elapsed (set above) the next state is set
		if(System.currentTimeMillis() - startTime > splashScreenDisplayTime) {
			gsm.setGameState(State.PLAYSTATE);
		}
		splashScreenSprite.setOrigin(currentWidth/2, currentHeight/2);
		if(currentHeight <= height && currentWidth <= width) {
			currentWidth+=startWidth;
			currentHeight+=startHeight;
			splashScreenSprite.setSize(currentWidth, currentHeight);
		}

		splashScreenSprite.rotate(rotationSpeed);

		if(splashScreenSprite.getRotation() >= 360 * numberOfRotations) {
			rotationSpeed = 0;
		}

		if(thugLifeGlassesSprite.getX() < 450) {
			thugLifeGlassesSprite.translate(10, 0);
		}

	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		{
			splashScreenSprite.draw(batch);
			thugLifeGlassesSprite.draw(batch);
		}
		batch.end();
	}

	@Override
	public void proccessInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
