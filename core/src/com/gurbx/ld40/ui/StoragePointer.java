package com.gurbx.ld40.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.utils.Constants;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.world.Storage;

public class StoragePointer implements GameObject {
	private Sprite sprite;
	private Storage storage;
	private Vector2 playerPosition;
	private float radians, distance;
	private float dx, dy;
	
	public StoragePointer(TextureAtlas atlas, Storage storage, Vector2 cameraPosition) {
		sprite = new Sprite(atlas.findRegion("arrow"));
		this.storage = storage;
		this.playerPosition = cameraPosition;
		sprite.setPosition(cameraPosition.x-16, cameraPosition.y-8);
	}

	@Override
	public void update(float delta) {
		radians = (float) Math.atan2(storage.getY() - playerPosition.y, storage.getX() - playerPosition.x);
		sprite.setRotation((float) Math.toDegrees(radians) - 90);
		
		distance = (float) Math.hypot(storage.getX() - playerPosition.x, storage.getY() - playerPosition.y);
		if (distance < Constants.UI_VIRTUAL_WIDTH*0.5f) {
			sprite.setAlpha(0);
		} else {
			sprite.setAlpha(1);
		}
		
		dx = MathUtils.cos(radians) * 100;
		dy = MathUtils.sin(radians) * 100;
		
		sprite.setPosition(playerPosition.x-16 + dx, playerPosition.y-8 + dy);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
