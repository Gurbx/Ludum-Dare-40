package com.gurbx.ld40.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.world.Crystal;
import com.gurbx.ld40.world.CrystalHandler;

public class Player implements GameObject {
	private Vector2 position;
	private Sprite sprite;
	
	private PlayerStateHandler playerState;
	private CrystalHandler crystalHandler;
	private Inventory inventory;
	private float rotation;
	
	public Player(Vector2 position, TextureAtlas atlas, Inventory inventory) {
		playerState = new PlayerStateHandler();
		this.inventory = inventory;
		inventory.addObserver(playerState);
		this.position = position;
		this.sprite = new Sprite(atlas.findRegion("player"));
		sprite.setPosition(position.x - 16, position.y - 16);
	}
	
	public void setCrystalHandler(CrystalHandler handler) {
		this.crystalHandler = handler;
	}
	
	
	private void handleMovement(float delta) {
		float speed = playerState.getSpeed();
		if (Gdx.input.isKeyPressed(Keys.A)) position.x -= speed * delta;
		if (Gdx.input.isKeyPressed(Keys.D)) position.x += speed * delta;
		if (Gdx.input.isKeyPressed(Keys.W)) position.y += speed * delta;
		if (Gdx.input.isKeyPressed(Keys.S)) position.y -= speed * delta;
		
		sprite.setPosition(position.x - 16, position.y - 16);
	}
	
	private void handleCrystals() {
		if (inventory.canDropCrystal() && Gdx.input.isKeyJustPressed(Keys.Q)) {
			inventory.removeCrystalFromPlayer();
			crystalHandler.addCrystal(position.x, position.y);
		}
	}


	@Override
	public void update(float delta) {
		handleMovement(delta);
		handleCrystals();
		
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void setRotation(float degrees) {
		sprite.setRotation(degrees);
	}


	public Vector2 getPosition() {
		return position;
	}


	public void canCollectCrystal(Crystal crystal) {
		if (Gdx.input.isKeyJustPressed(Keys.E)) {
			crystal.collect();
			inventory.addCrystalToPlayer();
		}
	}
}
