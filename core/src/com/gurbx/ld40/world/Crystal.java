package com.gurbx.ld40.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.utils.particles.ParticleEffectHandler;
import com.gurbx.ld40.utils.particles.ParticleEffectType;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Crystal implements GameObject {
	private float x, y;
	private Sprite sprite;
	private final float RANGE = 100;
	private boolean shouldRemove;
	private ParticleEffect effect;
	private PointLight light;
	
	public Crystal(float x, float y, TextureRegion texture, RayHandler rayHandler) {
		this.x = x;
		this.y = y;
		this.sprite = new Sprite(texture);
		this.sprite.setPosition(x-8, y-8);
		shouldRemove = false;
		effect = ParticleEffectHandler.returnAddedParticleEffect(ParticleEffectType.CRYSTAL, x, y, 100);
		light = new PointLight(rayHandler, 20, new Color(0 , 0.5f, 1f, 1f), 20, 0, 0);
		light.setStaticLight(true);
		light.setPosition(new Vector2(x,y));
	}

	@Override
	public void update(float delta) {
		effect.setDuration(100);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	@Override
	public void dispose() {
		
	}

	public boolean inRange(Vector2 position) {
		if (position.x - RANGE*0.5f < x && x < position.x + RANGE*0.5f &&
				position.y - RANGE*0.5 < y && y < position.y + RANGE*0.5f) {
			return true;
		}
		return false;
	}
	
	public void collect() {
		shouldRemove = true;
		light.remove();
		effect.setDuration(0);
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.CRYSTAL_PICKUP, x, y);
	}
	
	public boolean shouldRemove() {
		return shouldRemove;
	}

}
