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
	private TextureRegion tex, tex2;
	
	public Tiles(TextureAtlas atlas, GameWorld world) {
		this.world = world;
		tex = atlas.findRegion("tile");
		tex2 = atlas.findRegion("tile2");
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for (int i = -15; i < 115; i++) {
			for (int j = -15; j < 115; j++) {
				if (i < 5 || j < 5 || j > 95 || i > 95) {
					batch.draw(tex2, i*32, j*32);
				} else {
					batch.draw(tex, i*32, j*32);
				}
			}
		}
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
