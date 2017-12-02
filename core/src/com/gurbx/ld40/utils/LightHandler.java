package com.gurbx.ld40.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.gurbx.ld40.player.Player;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class LightHandler implements GameObject  {
	private RayHandler rayHandler;
	private Light playerLight, playerBigLight;
	private World box2dWorld;
	private Player player;
	private float lr, lg, lb;
	
	public LightHandler(World box2dWorld, Player player) {
		this.box2dWorld = box2dWorld;
		rayHandler = new RayHandler(box2dWorld);
		rayHandler.setShadows(true);
		lr = 0.8f;
		lg = 0.4f;
		lg = 0.4f;
		playerLight = new PointLight(rayHandler, 10, new Color(lr , lg, lb, 0.5f), 200/Constants.PPM, 0/Constants.PPM, 0/Constants.PPM);
		playerLight.setPosition(player.getPosition());
		this.player = player;
		
	}

	@Override
	public void update(float delta) {
		playerLight.setPosition(player.getPosition());
		
	}

	@Override
	public void render(SpriteBatch batch) {
		rayHandler.render();
	}

	@Override
	public void dispose() {
		rayHandler.dispose();
		
	}

	public RayHandler getRayHandler() {
		return rayHandler;
	}

}
