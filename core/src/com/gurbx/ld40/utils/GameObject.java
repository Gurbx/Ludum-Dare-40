package com.gurbx.ld40.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameObject {
	public void update(float delta);
	public void render(SpriteBatch batch);
	public void dispose();

}
