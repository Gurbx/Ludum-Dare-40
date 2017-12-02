package com.gurbx.ld40.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.GameObject;

public class World implements GameObject {
	private final int WIDTH = 2000;
	private final int HEIGHT = 2000;
	
	private TextureAtlas atlas;
	
	private CrystalHandler crystalHandler;
	private Storage storage;
	
	public World(TextureAtlas atlas, Inventory inventory, Player player) {
		this.atlas = atlas;
		crystalHandler = new CrystalHandler(atlas, player);
		inventory.addObserver(crystalHandler);
		storage = new Storage(50, 50, atlas, player, inventory, this);
	}

	@Override
	public void update(float delta) {
		crystalHandler.update(delta);
		storage.update(delta);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		storage.render(batch);
		crystalHandler.render(batch);
		
	}

	@Override
	public void dispose() {
		crystalHandler.dispose();
		storage.dispose();
	}

	public CrystalHandler getCrystalHandler() {
		return crystalHandler;
	}

	public int getHeight() {
		return HEIGHT;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public Storage getStorage() {
		return storage;
	}

}
