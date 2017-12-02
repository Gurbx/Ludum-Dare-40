package com.gurbx.ld40.utils.porjectiles;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.enemies.Enemy;
import com.gurbx.ld40.utils.Constants;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.utils.particles.ParticleEffectHandler;
import com.gurbx.ld40.utils.particles.ParticleEffectType;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Projectile implements GameObject {
	private Vector2 position;
	private float width, height;
	private float radians;
	private float dx, dy;
	private float targetX, targetY;
	private float speed;
	private float lifeTime;
	private boolean shouldRemove;
	private Sprite sprite;
	private int damage;
	
	PointLight light;
	
	
	public Projectile(float x, float y, float targetX, float targetY, TextureRegion texture, float speed, int damage, RayHandler rayHandler, boolean shootInvert) {
		sprite = new Sprite(texture);
		this.width = texture.getRegionWidth();
		this.height = texture.getRegionHeight();
		this.position = new Vector2(x,y);
		this.speed = speed;
		shouldRemove = false;
		sprite.setPosition(x-width*0.5f, y-height-0.5f);
		this.damage = damage;
		lifeTime = 500f/speed + 1;
		
		light = new PointLight(rayHandler, 20, new Color(1 , 0.2f, 0.2f, 1f), 30, 0, 0);
		light.setStaticLight(true);
		light.setPosition(position);
		
		radians = (float) Math.atan2(targetY - y, targetX - x);
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		
		if (shootInvert) {
			dx = -dx;
			dy = -dy;
		}
		
	}
	
	@Override
	public void update(float delta) {
		lifeTime-=delta;
		if (lifeTime <= 0) {
			lifeTimeExpired();
		}
		
		position.x += dx * delta;
		position.y += dy * delta;
		light.setPosition(position);
		
		sprite.setRotation((float) Math.toDegrees(radians));
		sprite.setPosition(position.x-width*0.5f, position.y-height-0.5f);
		
	}

	private void lifeTimeExpired() {
		shouldRemove = true;
	}
	
	public boolean hitsTarget(float targetX, float targetY, float targetRangeX, float targetRangeY) {
		if (targetX - targetRangeX*0.5f < position.x && position.x < targetX + targetRangeX*0.5f &&
				targetY - targetRangeY*0.5 < position.y && position.y < targetY + targetRangeY*0.5f) {
			return true;
		}
		return false;
	}
	
	public void hit() {
		shouldRemove = true;
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.HIT, position.x, position.y);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	@Override
	public void dispose() {
		if (light != null) {
			light.remove();
		}
		
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
