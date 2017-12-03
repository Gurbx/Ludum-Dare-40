package com.gurbx.ld40.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.gurbx.ld40.inventory.InventoryObserver;
import com.gurbx.ld40.player.Player;

import box2dLight.BlendFunc;
import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class LightHandler implements GameObject, InventoryObserver  {
	private RayHandler rayHandler;
	private Light playerLight, playerBigLight;
	private ConeLight coneLight;
	private World box2dWorld;
	private Player player;
	private float lr, lg, lb;
	private boolean outOfBoundaries;
	
	private int crystals;
	private final float LIGHT_LENGHT = 400f;
	
	public LightHandler(World box2dWorld, Player player) {
		this.box2dWorld = box2dWorld;
		rayHandler = new RayHandler(box2dWorld);
//		rayHandler.isDiffuse = false;
		RayHandler.useDiffuseLight(true);
		rayHandler.setShadows(true);
		lr = 1f;
		lg = 1f;
		lb = 1f;
		this.player = player;
//		rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 0.5f);
		
//		playerBigLight = new PointLight(rayHandler, 20, new Color(lr, lg, lb, 1f), 250/Constants.PPM, 0/Constants.PPM, 0/Constants.PPM);
//		playerBigLight.setPosition(player.getPosition());
		
		playerLight = new PointLight(rayHandler, 20, new Color(1 , 1, 1f, 1f), LIGHT_LENGHT/Constants.PPM, 0/Constants.PPM, 0/Constants.PPM);
		playerLight.setPosition(player.getPosition());
		playerLight.setStaticLight(true);
		playerLight.setSoftnessLength(0);
		
		coneLight = new ConeLight(rayHandler, 10,  new Color(1 , 1, 1f, 1f), LIGHT_LENGHT*2, player.getPosition().x, player.getPosition().y, 
				player.getTurretRotation(), 15f);
		coneLight.setStaticLight(true);
		player.setRayHandler(rayHandler);
		
	}

	@Override
	public void update(float delta) {
		playerLight.setPosition(player.getPosition());
		coneLight.setPosition(player.getPosition());
		coneLight.setDirection(player.getTurretRotation());
		coneLight.update();
		rayHandler.update();
		
		if (outOfBoundaries) {
			if (coneLight.getDistance() >= 0) {
				coneLight.setDistance(coneLight.getDistance()-250*delta);
			}
			if (playerLight.getDistance() >= 0) {
				playerLight.setDistance(playerLight.getDistance()-250*delta);
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		rayHandler.render();
	}

	@Override
	public void dispose() {
		rayHandler.dispose();
		playerLight.dispose();
		coneLight.dispose();
	}

	public RayHandler getRayHandler() {
		return rayHandler;
	}

	@Override
	public void inventoryStatus(int playerCrystals, int storageCrystals) {
		crystals = playerCrystals;
		if (outOfBoundaries) return;
		updateLights();
	}

	private void updateLights() {
		if (crystals >= 1) {
			float length = LIGHT_LENGHT - crystals * 40;
			if (length <= 50)length = 50; 
			playerLight.setDistance(length);
			coneLight.setDistance(length*2 + crystals*40);
		} else {
			playerLight.setDistance(LIGHT_LENGHT);
			coneLight.setDistance(LIGHT_LENGHT*2);
		}
		
	}

	public void isOutOfBoundaries(boolean b) {
		if (outOfBoundaries == false && b == true) {
		}
		if (b == false && outOfBoundaries == true) {
			updateLights();
		}
		outOfBoundaries = b;
		
	}

}
