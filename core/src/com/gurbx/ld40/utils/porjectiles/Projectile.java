package com.gurbx.ld40.utils.porjectiles;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.gurbx.ld40.enemies.Enemy;
import com.gurbx.ld40.utils.GameObject;

public class Projectile implements GameObject {
	private float x, y;
	private float width, height;
	private float radians;
	private float dx, dy;
	private float targetX, targetY;
	private float speed;
	private float lifeTime;
	private boolean shouldRemove;
	private Sprite sprite;
	private int damage;
	
	
	public Projectile(float x, float y, float targetX, float targetY, TextureRegion texture, float speed, int damage) {
		sprite = new Sprite(texture);
		this.width = texture.getRegionWidth();
		this.height = texture.getRegionHeight();
		this.x = x;
		this.y = y;
		this.speed = speed;
		shouldRemove = false;
		sprite.setPosition(x-width*0.5f, y-height-0.5f);
		this.damage = damage;
		lifeTime = 500f/speed;
		
		radians = (float) Math.atan2(targetY - y, targetX - x);
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		
	}
	
	@Override
	public void update(float delta) {
		lifeTime-=delta;
		if (lifeTime <= 0) {
			lifeTimeExpired();
		}
		
		x += dx * delta;
		y += dy * delta;
		
		sprite.setRotation((float) Math.toDegrees(radians));
		sprite.setPosition(x-width*0.5f, y-height-0.5f);
		
	}

	private void lifeTimeExpired() {
		shouldRemove = true;
	}
	
	public boolean hitsTarget(float targetX, float targetY, float targetRangeX, float targetRangeY) {
		if (targetX - targetRangeX*0.5f < x && x < targetX + targetRangeX*0.5f &&
				targetY - targetRangeY*0.5 < y && y < targetY + targetRangeY*0.5f) {
			return true;
		}
		return false;
	}
	
	public void hit() {
		shouldRemove = true;
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

	public void handleEnemyCollision(LinkedList<Enemy> enemies) {
		for (int i = 0; i < enemies.size(); i++) {
			if (hitsTarget(enemies.get(i).getX(), enemies.get(i).getY(), enemies.get(i).getWidth(), enemies.get(i).getHeight())) {
				enemies.get(i).damaga(damage);
				hit();
			}
		}
	}

}
