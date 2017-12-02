package com.gurbx.ld40.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	private Sprite hearth;
	private float behindPlayerBarHealth;
	
	public UI(Application app, Inventory inventory, GameWorld world, TextureAtlas atlas, Player player) {
		this.player = player;
		this.app = app;
		this.inventory = inventory;
		this.world = world;
		storage = world.getStorage();
		storagePointer = new StoragePointer(atlas, storage, player.getPosition());
		hearth = new Sprite(atlas.findRegion("hearth"));
		hearth.setPosition(8, Constants.UI_VIRTUAL_HEIGHT - 32);
		behindPlayerBarHealth = player.getHealth();
	}

	@Override
	public void update(float delta) {
		storagePointer.update(delta);
		handleHealthBar(delta);
		
	}
	

	private void handleHealthBar(float delta) {
		if (player.getHealth() < behindPlayerBarHealth) {
			behindPlayerBarHealth -= (behindPlayerBarHealth-player.getHealth())*2*delta;
		} else if (player.getHealth() > behindPlayerBarHealth) {
			behindPlayerBarHealth = player.getHealth();
		}
		
	}

	@Override
	public void render(SpriteBatch batch) {
		app.font1.draw(app.batch, "Crystals: " + inventory.getPlayerCrystals(), Constants.UI_VIRTUAL_WIDTH - 130, Constants.UI_VIRTUAL_HEIGHT-10);
		app.font1.draw(app.batch, "Storage: " + inventory.getStorageCrystals(), Constants.UI_VIRTUAL_WIDTH - 130, Constants.UI_VIRTUAL_HEIGHT-30);
		hearth.draw(batch);
	}
	
	public void renderBars() {
		app.shapeRenderer.begin(ShapeType.Filled);
		//Player Time Power
		app.shapeRenderer.setColor(1f, 1f, 0, 1f);
		app.shapeRenderer.rect(40, Constants.UI_VIRTUAL_HEIGHT-26, 200*behindPlayerBarHealth/player.getMaxHealth(), 11);
		app.shapeRenderer.setColor(1f, 0.05f, 0.05f, 1f);
		app.shapeRenderer.rect(40, Constants.UI_VIRTUAL_HEIGHT-26, 200*player.getHealth()/player.getMaxHealth(), 11);
		app.shapeRenderer.end();
		
	}
	
	public void renderWithGameCamera(SpriteBatch batch) {
		storagePointer.render(batch);
	}

	@Override
	public void dispose() {
		storagePointer.dispose();
		
	}

}
