package com.gurbx.ld40.world;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.GameObject;

public class Storage implements GameObject {
	private final float RANGE = 64;
	private float x, y;
	private Sprite sprite;
	private Player player;
	private Inventory inventory;
	
	private int worldWidth;
	private int worldHeight;
	private final int MIN_TELEPORT_DISTANCE = 200;
	private Random random;
	
	public Storage(float x, float y, TextureAtlas atlas, Player player, Inventory inventory, GameWorld world) {
		this.player = player;
		this.worldHeight = world.getHeight();
		this.worldWidth = world.getWidth();
		this.inventory = inventory;
		this.x = x;
		this.y = y;
		sprite = new Sprite(atlas.findRegion("storage"));
		sprite.setPosition(x-32, y-32);
		random = new Random();
	}

	@Override
	public void update(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.Q) && inRange(player.getPosition()) && inventory.canDropCrystal()) {
			inventory.transferToStorage();
			moveStorage();
		}
	}
	
	private void moveStorage() {
		float x = random.nextInt(worldWidth);
		float y = random.nextInt(worldHeight);
		this.x = x;
		this.y = y;
		sprite.setPosition(x-32, y-32);
	}

	public boolean inRange(Vector2 position) {
		if (position.x - RANGE*0.5f < x && x < position.x + RANGE*0.5f &&
				position.y - RANGE*0.5 < y && y < position.y + RANGE*0.5f) {
			return true;
		}
		return false;
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}

}
