package com.gurbx.ld40.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gurbx.ld40.utils.GameObject;

public class StorageSwirl implements GameObject {
	private final float ROTATION_SPEED = 100f;
	private float x, y;
	private Sprite outer, middle, inner;
	private Sprite outerSmoke, innerSmoke;
	private float width, height;
	
	public StorageSwirl(float x, float y, TextureAtlas atlas) {
		this.x = x;
		this.y = y;
		TextureRegion tex = atlas.findRegion("outerRing");
		width = tex.getRegionWidth();
		height = tex.getRegionHeight();
		outer = new Sprite(tex);
		middle = new Sprite(atlas.findRegion("middleRing"));
		inner = new Sprite(atlas.findRegion("innerRing"));
		innerSmoke = new Sprite(atlas.findRegion("innerSmokeRing"));
		outerSmoke = new Sprite(atlas.findRegion("outerSmokeRing"));
		setPosition(x, y);
	}

	public void setPosition(float x2, float y2) {
		outer.setPosition(x2 - width*0.5f, y2 - height*0.5f);
		inner.setPosition(x2 - width*0.5f, y2 - height*0.5f);
		middle.setPosition(x2 - width*0.5f, y2 - height*0.5f);
		outerSmoke.setPosition(x2 - width*0.5f, y2 - height*0.5f);
		innerSmoke.setPosition(x2 - width*0.5f, y2 - height*0.5f);
	}

	@Override
	public void update(float delta) {
		outerSmoke.rotate(ROTATION_SPEED* delta);
		innerSmoke.rotate(ROTATION_SPEED*delta * 1.5f);
		outer.rotate(ROTATION_SPEED*delta*2);
		middle.rotate(ROTATION_SPEED*delta*2.5f);
		inner.rotate(ROTATION_SPEED*delta*3);
	}

	@Override
	public void render(SpriteBatch batch) {
		inner.draw(batch);
		middle.draw(batch);
		outer.draw(batch);
		innerSmoke.draw(batch);
		outerSmoke.draw(batch);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
