package com.gurbx.ld40.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.gurbx.ld40.Application;
import com.gurbx.ld40.enemies.EnemyHandler;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.ui.UI;
import com.gurbx.ld40.utils.Constants;
import com.gurbx.ld40.utils.GameScreen;
import com.gurbx.ld40.utils.Input;
import com.gurbx.ld40.utils.LightHandler;
import com.gurbx.ld40.world.GameWorld;

public class PlayScreen extends GameScreen {
	private TextureAtlas generalAtlas;
	private Input input;
	private Player player;
	private EnemyHandler enemies;
	private GameWorld world;
	private Inventory inventory;
	private UI ui;
	private World box2dWorld;
	private LightHandler lights;
	
	public PlayScreen(Application app) {
		super(app);
	}

	@Override
	public void show() {
		generalAtlas = app.assets.get("img/generalPack.atlas", TextureAtlas.class);
		inventory = new Inventory();
		enemies = new EnemyHandler(generalAtlas);
		player = new Player(new Vector2(100,100), generalAtlas, inventory, enemies);
		enemies.setPlayer(player);
		world = new GameWorld(generalAtlas, inventory, player, enemies);
		player.setCrystalHandler(world.getCrystalHandler());
		ui = new UI(app, inventory, world, generalAtlas, player);
		box2dWorld = new World(new Vector2(), false);
		lights = new LightHandler(box2dWorld, player);
		
		input = new Input(player, app);
		Gdx.input.setInputProcessor(input);
	}
	
	private void update(float delta) {
		box2dWorld.step(1/60f, 6, 2);
		input.update(delta);
		world.update(delta);
		player.update(delta);
		enemies.update(delta);
		lights.update(delta);
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
		enemies.render(app.batch);
		player.render(app.batch);
		ui.renderWithGameCamera(app.batch);
		app.batch.end();
		
		lights.getRayHandler().setCombinedMatrix(app.camera.combined.scl(Constants.PPM));
		lights.render(app.batch);
		
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
		enemies.dispose();
		
	}

}
