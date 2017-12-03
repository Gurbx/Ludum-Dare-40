package com.gurbx.ld40.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gurbx.ld40.utils.GameObject;

public class ScreenText implements GameObject {
	private final float ALPHA_INCREASE = 2f;
	private Sprite text;
	private boolean visible;
	private float alpha;
	private float visibleTimer;
	
	public ScreenText(float x, float y, TextureRegion tex, boolean visible) {
		text = new Sprite(tex);
		text.setPosition(x - tex.getRegionWidth()*0.5f, y-tex.getRegionHeight()*0.5f);
		this.visible = visible;
		if (!visible) {
			alpha = 0;
		} else {
			alpha = 1;
		}
		text.setAlpha(alpha);
	}

	@Override
	public void update(float delta) {
		visibleTimer -= delta;
		if (visibleTimer <= 0) visible = false;
		
		
		if (visible && alpha < 1) {
			alpha+=ALPHA_INCREASE*delta;
			if (alpha >= 1) alpha = 1;
		}
		if (!visible && alpha > 0) {
			alpha -= ALPHA_INCREASE*delta;
			if (alpha <= 0) alpha = 0;
		}
		text.setAlpha(alpha);
	}
	
	public void setVisible(float duration) {
		visibleTimer = duration;
		visible = true;
	}

	@Override
	public void render(SpriteBatch batch) {
		text.draw(batch);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	

}
