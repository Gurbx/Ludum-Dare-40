package com.gurbx.ld40.player;

import com.gurbx.ld40.inventory.InventoryObserver;

public class PlayerStateHandler implements InventoryObserver {
	private final int MAX_SPEED = 200;
	private final int MIN_SPEED = 50;
	private final float speedDecreasePerCrystal = 0.075f;
	private float speed;
	
	public PlayerStateHandler() {
		speed = MAX_SPEED;
	}
	
	public float getSpeed() {
		return speed;
	}

	@Override
	public void inventoryStatus(int playerCrystals, int storageCrystals) {
		if (playerCrystals > 0) {
			speed = MAX_SPEED * (1-(speedDecreasePerCrystal * playerCrystals));
			if (speed < MIN_SPEED) speed = MIN_SPEED;
		} else {
			speed = MAX_SPEED;
		}
		System.out.println(speed);
		
	}

}
