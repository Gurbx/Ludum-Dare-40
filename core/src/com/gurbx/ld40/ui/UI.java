package com.gurbx.ld40.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gurbx.ld40.Application;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.Constants;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.world.Storage;
import com.gurbx.ld40.world.GameWorld;

public class UI implements GameObject {
	private Application app;
	private Inventory inventory;
	private GameWorld world;
	private Storage storage;
	private StoragePointer storagePointer;
	private Player player;
	
	public UI(Application app, Inventory inventory, GameWorld world, TextureAtlas atlas, Player player) {
		this.player = player;
		this.app = app;
		this.inventory = inventory;
		this.world = world;
		storage = world.getStorage();
		storagePointer = new StoragePointer(atlas, storage, player.getPosition());
	}

	@Override
	public void update(float delta) {
		storagePointer.update(delta);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		app.font1.draw(app.batch, "Crystals: " + inventory.getPlayerCrystals(), 6, Constants.UI_VIRTUAL_HEIGHT-10);
		app.font1.draw(app.batch, "Storage: " + inventory.getStorageCrystals(), 6, Constants.UI_VIRTUAL_HEIGHT-30);
		
	}
	
	public void renderWithGameCamera(SpriteBatch batch) {
		storagePointer.render(batch);
	}

	@Override
	public void dispose() {
		storagePointer.dispose();
		
	}

}
