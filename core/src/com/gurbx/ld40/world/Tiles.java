package com.gurbx.ld40.world;

import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gurbx.ld40.utils.GameObject;

public class Tiles implements GameObject {
	private GameWorld world;
	private TextureAtlas atlas;
	private TextureRegion tex;
	
	public Tiles(TextureAtlas atlas, GameWorld world) {
		this.world = world;
		tex = atlas.findRegion("tile");
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				batch.draw(tex, i*32, j*32);
			}
		}
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
