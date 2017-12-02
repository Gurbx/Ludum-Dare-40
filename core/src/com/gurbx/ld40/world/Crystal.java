package com.gurbx.ld40.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.utils.GameObject;

public class Crystal implements GameObject {
	private float x, y;
	private Sprite sprite;
	private final float RANGE = 42;
	private boolean shouldRemove;
	
	public Crystal(float x, float y, TextureRegion texture) {
		this.x = x;
		this.y = y;
		this.sprite = new Sprite(texture);
		this.sprite.setPosition(x-8, y-8);
		shouldRemove = false;
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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
	}
	
	public boolean shouldRemove() {
		return shouldRemove;
	}

}
