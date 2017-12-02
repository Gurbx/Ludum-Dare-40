package com.gurbx.ld40.world;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.gurbx.ld40.inventory.InventoryObserver;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.GameObject;

import box2dLight.RayHandler;

public class CrystalHandler implements GameObject, InventoryObserver {
	private final int SPAWN_RANGE = 800;
	private LinkedList<Crystal> crystals;
	private TextureAtlas atlas;
	private TextureRegion crystalTexture;
	private Player player;
	private RayHandler rayHandler;
	private Random random;
	private GameWorld world;
	
	public CrystalHandler(TextureAtlas atlas, Player player, RayHandler rayHandler, GameWorld world) {
		this.rayHandler = rayHandler;
		this.atlas = atlas;
		this.player = player;
		crystalTexture = atlas.findRegion("crystal");
		crystals = new LinkedList<Crystal>();
		this.world = world;
		random = new Random();
		
		for (int i = 0; i < 80; i++) {
			spawnCrystal();
		}
		
//		crystals.add(new Crystal(100, 100, crystalTexture, rayHandler));
//		crystals.add(new Crystal(400, 400, crystalTexture, rayHandler));
//		crystals.add(new Crystal(600, 200, crystalTexture, rayHandler));
//		crystals.add(new Crystal(-100, 400, crystalTexture, rayHandler));
//		crystals.add(new Crystal(200, 100, crystalTexture, rayHandler));
//		crystals.add(new Crystal(200, 400, crystalTexture, rayHandler));
//		crystals.add(new Crystal(-300, 200, crystalTexture, rayHandler));
//		crystals.add(new Crystal(-100, 100, crystalTexture, rayHandler));
	}
	
	private void spawnCrystal() {
		float x = random.nextInt(world.getWidth());
		float y = random.nextInt(world.getHeight());
		
		crystals.add(new Crystal(x, y, crystalTexture, rayHandler));
	}
	
	public void addCrystal(float x, float y) {
		crystals.add(new Crystal(x, y, crystalTexture, rayHandler));
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < crystals.size(); i++) {
			crystals.get(i).update(delta);
			if (crystals.get(i).shouldRemove()) {
				crystals.get(i).dispose();
				crystals.remove(i);
				
			} else if (crystals.get(i).inRange(player.getPosition())) {
				player.canCollectCrystal(crystals.get(i));
			}
		}
		
		if (crystals.size() < 80) {
			spawnCrystal();
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		for (int i = 0; i < crystals.size(); i++) {
			crystals.get(i).render(batch);
		}
		
	}
	
	

	@Override
	public void dispose() {
		for (int i = 0; i < crystals.size(); i++) {
			crystals.get(i).dispose();
		}
	}

	@Override
	public void inventoryStatus(int playerCrystals, int storageCrystals) {
		// TODO Auto-generated method stub
		
	}

}
