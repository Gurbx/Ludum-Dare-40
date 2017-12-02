package com.gurbx.ld40.player;

public class PlayerStateHandler {
	private final int MAX_SPEED = 100;
	private final int MIN_SPEED = 50;
	private float speed;
	
	public PlayerStateHandler() {
		speed = MAX_SPEED;
	}
	
	public float getSpeed() {
		return speed;
	}

}
