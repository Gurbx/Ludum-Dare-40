package com.gurbx.ld40;

import java.awt.Cursor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gurbx.ld40.screens.LoadingScreen;
import com.gurbx.ld40.screens.MenuScreen;
import com.gurbx.ld40.screens.PlayScreen;
import com.gurbx.ld40.utils.Constants;
import com.gurbx.ld40.utils.ScreenShaker;
import com.gurbx.ld40.utils.sound.SoundHandler;

public class Application extends Game {
	public final static boolean SOUND_ON = true;
	public static final boolean MUSIC_ON = false;
	public final static boolean SHADOWS = true;
	public AssetManager assets;
	public SpriteBatch batch;
	public Viewport viewport, uiViewport;
	public static OrthographicCamera camera, uiCamera;
	public ShapeRenderer shapeRenderer;
	public static ScreenShaker screenShake;
	public SoundHandler soundHandler;
	
	public BitmapFont font1, font2, font3;
	
	private Texture mouseTex, mtex2;
	private float mouseWidth, mouseHeight;
	private float mouseWidth2, mouseHeight2;
	private Sprite mouseTarget, mouseTargetActivated;
	
	//Screens
	public LoadingScreen loadingScreen;
	public PlayScreen playScreen;
	public MenuScreen menuScreen;
	
	@Override
	public void create () {
		initGeneral();
		initFonts();
		initScreens();
	}
	
	private void initGeneral() {
		assets = new AssetManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		uiCamera = new OrthographicCamera();
		viewport = new StretchViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
		uiViewport = new StretchViewport(Constants.UI_VIRTUAL_WIDTH, Constants.UI_VIRTUAL_HEIGHT, uiCamera);
		viewport.apply();
		uiViewport.apply();
		camera.position.set(Constants.VIRTUAL_WIDTH/2, Constants.VIRTUAL_HEIGHT/2, 0);
		uiCamera.position.set(Constants.UI_VIRTUAL_WIDTH/2, Constants.UI_VIRTUAL_HEIGHT/2, 0);
		camera.update();
		uiCamera.update();
		shapeRenderer = new ShapeRenderer();
		
		screenShake = new ScreenShaker();
		soundHandler = new SoundHandler();
		
		mouseTex = new Texture(Gdx.files.internal("cursorImage.png"));
		mtex2 = new Texture(Gdx.files.internal("cursorImageClicked.png"));
		mouseHeight = mouseTex.getHeight();
		mouseWidth = mouseTex.getWidth();
		mouseHeight2 = mtex2.getHeight();
		mouseWidth2 = mtex2.getWidth();
		mouseTarget = new Sprite(mouseTex);
		mouseTargetActivated = new Sprite(mtex2);

//		Pixmap pm = new Pixmap(Gdx.files.internal("transparent.png"));
//		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
//		pm.dispose();
		
		Pixmap pm = new Pixmap(Gdx.files.internal("cursor.png"));
		Gdx.input.setCursorImage(pm, 0, 0);
		pm.dispose();
	}
	
	private void initFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/notomono-regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 14;
		parameter.borderWidth = 1f;
		parameter.borderColor = Color.DARK_GRAY;
		font1 = generator.generateFont(parameter); 
		parameter.size = 11;
		font2 = generator.generateFont(parameter);
		parameter.size = 7;
		font3 = generator.generateFont(parameter);
		generator.dispose(); 
	}
	
	private void initScreens() {
		loadingScreen = new LoadingScreen(this);
		playScreen = new PlayScreen(this);
		menuScreen = new MenuScreen(this);
		setScreen(loadingScreen);
	}
	
	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		super.render();
	}
	
	public void renderMousePointer(float delta) {
		float mouseX = (float) Gdx.input.getX()/Gdx.graphics.getWidth() * (float) Constants.UI_VIRTUAL_WIDTH;
		float mouseY = (float) Gdx.input.getY()/Gdx.graphics.getHeight() * (float) Constants.UI_VIRTUAL_HEIGHT;
		mouseTarget.rotate(delta*225f);
		mouseTargetActivated.rotate(delta*225f);
		if (Gdx.input.isTouched()) {
			mouseTargetActivated.setPosition(mouseX-mouseWidth2*0.5f, Constants.UI_VIRTUAL_HEIGHT-mouseY-mouseHeight2*0.5f);
			mouseTargetActivated.draw(batch);
		} else {
			mouseTarget.setPosition(mouseX-mouseWidth*0.5f, Constants.UI_VIRTUAL_HEIGHT-mouseY-mouseHeight*0.5f);
			mouseTarget.draw(batch);
		}
	}
	
	private void update(float delta) {
		soundHandler.update(delta);
		screenShake.update(camera);
	}
	
	public static void shakeScreen(int nShakes, float f, boolean priority) {
		screenShake.shakeScreen(nShakes, camera.position, f, priority);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
		uiViewport.update(width, height);
		camera.update();
		uiCamera.update();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
		batch.dispose();
		loadingScreen.dispose();
		playScreen.dispose();
		shapeRenderer.dispose();
		font1.dispose();
		font2.dispose();
		font3.dispose();
		soundHandler.dispose();
		mouseTex.dispose();
		mtex2.dispose();
	}
}
