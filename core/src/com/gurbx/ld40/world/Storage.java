package com.gurbx.ld40.world;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.Constants;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.utils.particles.ParticleEffectHandler;
import com.gurbx.ld40.utils.particles.ParticleEffectType;
import com.gurbx.ld40.utils.sound.SoundHandler;
import com.gurbx.ld40.utils.sound.Sounds;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Storage implements GameObject {
	private final float RANGE = 200;
	private float x, y;
	private Sprite sprite;
	private Player player;
	private Inventory inventory;
	
	private int worldWidth;
	private int worldHeight;
	private final int MIN_TELEPORT_DISTANCE = 200;
	private Random random;
	private PointLight light;
	
	private StorageSwirl swirl;
	
	public Storage(float x, float y, TextureAtlas atlas, Player player, Inventory inventory, GameWorld world, RayHandler rayHandler) {
		this.player = player;
		this.worldHeight = world.getHeight();
		this.worldWidth = world.getWidth();
		this.inventory = inventory;
		sprite = new Sprite(atlas.findRegion("storage"));
		random = new Random();
		
		this.x = random.nextInt(worldWidth);
		this.y = random.nextInt(worldHeight);
		sprite.setPosition(this.x-64, this.y-64);
		
		light = new PointLight(rayHandler, 20, new Color(0.5f , 0.8f, 1f, 1f), 200/Constants.PPM, 0/Constants.PPM, 0/Constants.PPM);
		light.setPosition(new Vector2(this.x, this.y));
		light.setStaticLight(true);
		
		swirl = new StorageSwirl(this.x, this.y, atlas);
				
	}

	@Override
	public void update(float delta) {
		swirl.update(delta);
		if (Gdx.input.isKeyJustPressed(Keys.E) && inRange(player.getPosition()) && inventory.canDropCrystal()) {
			inventory.transferToStorage();
			SoundHandler.playSound(Sounds.PICKUP2);
			SoundHandler.playSound(Sounds.PICKUP3);
			moveStorage();
		}
	}
	
	private void moveStorage() {
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.CRYSTAL_PICKUP, x, y);
		x = random.nextInt(worldWidth);
		y = random.nextInt(worldHeight);
		sprite.setPosition(x-64, y-64);
		ParticleEffectHandler.addParticleEffect(ParticleEffectType.CRYSTAL_PICKUP, x, y);
		light.setPosition(new Vector2(x,y));
		swirl.setPosition(x, y);
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
		swirl.render(batch);
		
	}

	@Override
	public void dispose() {
		light.dispose();
		
	}

	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}

}
