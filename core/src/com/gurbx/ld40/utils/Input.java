package com.gurbx.ld40.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.gurbx.ld40.Application;
import com.gurbx.ld40.player.Player;

public class Input implements InputProcessor {
	private Player player;
	private Application app;
	private float playerRadians;
	private float distanceBetweenPlayerAndMouse;
	
	public Input(Player player, Application app) {
		this.player = player;
		this.app = app;
	}
	
	public void update(float delta) {
		playerRadians = (float) Math.atan2(getMousePosInGameWorld().y - player.getPosition().y, getMousePosInGameWorld().x - player.getPosition().x);
		player.setRotation((float) Math.toDegrees(playerRadians));
		distanceBetweenPlayerAndMouse = (float) Math.sqrt((player.getPosition().x-getMousePosInGameWorld().x)*(player.getPosition().x-getMousePosInGameWorld().x)
				+ (player.getPosition().y-getMousePosInGameWorld().y)*(player.getPosition().y-getMousePosInGameWorld().y));
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Vector3 getMousePosInGameWorld() {
		 return app.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}
	
	//Called by camera hander
	public float getMouseDirectionX() {
		return MathUtils.cos(playerRadians) * distanceBetweenPlayerAndMouse*0.25f;
	}
	public float getMouseDirectionY() {
		return MathUtils.sin(playerRadians) * distanceBetweenPlayerAndMouse*0.25f;
	}

}
