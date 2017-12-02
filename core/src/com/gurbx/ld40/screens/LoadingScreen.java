package com.gurbx.ld40.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.gurbx.ld40.Application;
import com.gurbx.ld40.utils.GameScreen;

public class LoadingScreen extends GameScreen {

	public LoadingScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		loadGeneralAssets();
//		loadSounds();
		
	}
	
	private void loadGeneralAssets() {
		app.assets.load("img/generalPack.atlas", TextureAtlas.class);
		
	}

	private void update(float delta) {
		app.assets.update();
		if (app.assets.getProgress() >= 1) {
			app.setScreen(app.playScreen);
		}
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		app.batch.setProjectionMatrix(app.uiCamera.combined);
		app.batch.begin();
//		app.font2.draw(app.batch, "Loading...", 5, 15);
		app.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
