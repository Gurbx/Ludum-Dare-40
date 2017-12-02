package com.gurbx.ld40.world;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gurbx.ld40.inventory.InventoryObserver;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.GameObject;

public class CrystalHandler implements GameObject, InventoryObserver {
	private LinkedList<Crystal> crystals;
	private TextureAtlas atlas;
	private TextureRegion crystalTexture;
	private Player player;
	
	public CrystalHandler(TextureAtlas atlas, Player player) {
		this.atlas = atlas;
		this.player = player;
		crystalTexture = atlas.findRegion("crystal");
		crystals = new LinkedList<Crystal>();
		
		crystals.add(new Crystal(100, 100, crystalTexture));
		crystals.add(new Crystal(400, 400, crystalTexture));
		crystals.add(new Crystal(600, 200, crystalTexture));
		crystals.add(new Crystal(-100, 400, crystalTexture));
		crystals.add(new Crystal(200, 100, crystalTexture));
		crystals.add(new Crystal(200, 400, crystalTexture));
		crystals.add(new Crystal(-300, 200, crystalTexture));
		crystals.add(new Crystal(-100, 100, crystalTexture));
	}
	
	public void addCrystal(float x, float y) {
		crystals.add(new Crystal(x, y, crystalTexture));
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
