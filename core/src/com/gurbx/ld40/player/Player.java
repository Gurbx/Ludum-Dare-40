package com.gurbx.ld40.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.utils.GameObject;

public class Player implements GameObject {
	private Vector2 position;
	private Sprite sprite;
	
	private PlayerStateHandler playerState;
	private float rotation;
	
	public Player(Vector2 position, TextureAtlas atlas) {
		playerState = new PlayerStateHandler();
		this.position = position;
		this.sprite = new Sprite(atlas.findRegion("player"));
		sprite.setPosition(position.x, position.y);
	}
	
	
	private void handleMovement(float delta) {
		float speed = playerState.getSpeed();
		if (Gdx.input.isKeyPressed(Keys.A)) position.x -= speed * delta;
		if (Gdx.input.isKeyPressed(Keys.D)) position.x += speed * delta;
		if (Gdx.input.isKeyPressed(Keys.W)) position.y += speed * delta;
		if (Gdx.input.isKeyPressed(Keys.S)) position.y -= speed * delta;
		
		sprite.setPosition(position.x, position.y);
		
	}

	@Override
	public void update(float delta) {
		handleMovement(delta);
		
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

}
