package com.gurbx.ld40.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gurbx.ld40.Application;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.ui.UI;
import com.gurbx.ld40.utils.GameScreen;
import com.gurbx.ld40.utils.Input;
import com.gurbx.ld40.world.World;

public class PlayScreen extends GameScreen {
	private TextureAtlas generalAtlas;
	private Input input;
	private Player player;
	private World world;
	private Inventory inventory;
	private UI ui;

	public PlayScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		generalAtlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
		inventory = new Inventory();
		player = new Player(new Vector2(100,100), generalAtlas, inventory);
		world = new World(generalAtlas, inventory, player);
		player.setCrystalHandler(world.getCrystalHandler());
		ui = new UI(app, inventory, world, generalAtlas, player);
		
		input = new Input(player, app);
		Gdx.input.setInputProcessor(input);
	}
	
	private void update(float delta) {
		input.update(delta);
		world.update(delta);
		player.update(delta);
		ui.update(delta);
		handleCamera(delta);
		
	}
	

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0.5f, 0.6f, 0.65f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		app.batch.setProjectionMatrix(app.camera.combined);
		app.batch.begin();
		world.render(app.batch);
		player.render(app.batch);
		ui.renderWithGameCamera(app.batch);
		app.batch.end();
		
		app.batch.setProjectionMatrix(app.uiCamera.combined);
		app.batch.begin();
		ui.render(app.batch);
		app.batch.end();
	}
	
	private void handleCamera(float delta) {
		float lerp = 10f;
		Vector3 position = app.camera.position;
		position.x += (player.getPosition().x+input.getMouseDirectionX() - position.x) * lerp * delta;
		position.y += (player.getPosition().y+input.getMouseDirectionY()  - position.y) * lerp * delta;
		
		app.camera.update();
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
		generalAtlas.dispose();
		world.dispose();
		player.dispose();
		ui.dispose();
		
	}

}
