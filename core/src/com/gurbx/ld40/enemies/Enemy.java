package com.gurbx.ld40.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.Application;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.utils.ScreenShaker;

public class Enemy implements GameObject {
	private Vector2 position;
	private float width, height;
	private EnemyType type;
	private Sprite sprite;
	private boolean shouldRemove;
	
	private int health;
	private float speed;
	private float dx, dy;
	private float radians;
	
	private Player player;
	
	public Enemy(Vector2 position, EnemyType type, TextureAtlas atlas, Player player) {
		TextureRegion tex = atlas.findRegion(type.getPath());
		this.width = tex.getRegionWidth();
		this.height = tex.getRegionHeight();
		sprite = new Sprite(tex);
		this.position = position;
		sprite.setPosition(position.x - width*0.5f, position.y - height*0.5f);
		shouldRemove = false;
		this.health = type.getHealth();
		this.player = player;
		this.speed = type.getSpeed();
	}

	@Override
	public void update(float delta) {
		handleMovement(delta);
		sprite.setPosition(position.x - width*0.5f, position.y - height*0.5f);
		sprite.setRotation((float) Math.toDegrees(radians));
		
	}

	private void handleMovement(float delta) {
		chasePlayer(delta);
	}

	private void chasePlayer(float delta) {
		radians = (float) Math.atan2(player.getPosition().y - position.y, player.getPosition().x - position.x);
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		position.x += dx * delta;
		position.y += dy * delta;
		
		

	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean shouldRemove() {
		return shouldRemove;
	}

	public void damaga(int damage) {
		this.health -= damage;
		if (health <= 0) {
			shouldRemove = true;
			Application.shakeScreen(10, 10, true);
		} else {
			Application.shakeScreen(4, 3, false);
		}
		
		System.out.println(health);
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}

}
