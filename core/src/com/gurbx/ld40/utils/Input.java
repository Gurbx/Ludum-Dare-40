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
		player.setTowerRotation((float) Math.toDegrees(playerRadians));
		distanceBetweenPlayerAndMouse = (float) Math.sqrt((player.getPosition().x-getMousePosInGameWorld().x)*(player.getPosition().x-getMousePosInGameWorld().x)
				+ (player.getPosition().y-getMousePosInGameWorld().y)*(player.getPosition().y-getMousePosInGameWorld().y));
		
		player.setMousePosition(getMousePosInGameWorld());
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
		if (button == 0 && !player.isShootingRight()) {
			player.setShootingLeft(true);
		}
		if (button == 1 && !player.isShootingLeft()) {
			player.setShootingRight(true);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			player.setShootingLeft(false);
		}
		if (button == 1) {
			player.setShootingRight(false);
		}
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
	
	public void refrensShootingProperties(float spread) {
		float dx;
		float dy;
		if (spread != 0) {
			float newRadians = (float) (playerRadians + Math.toRadians(-0.5f*spread + Math.random()*spread));
			dx = MathUtils.cos(newRadians) * 30;
			dy = MathUtils.sin(newRadians) * 30;
		} else {
			dx = MathUtils.cos(playerRadians) * 35;
			dy = MathUtils.sin(playerRadians) * 35;
		}
		
		//Invert the dirction if distance is less than 25 so player doesn't shoot backwards
		boolean invert = (distanceBetweenPlayerAndMouse < 35);
		
		player.setShootingInformation(getMousePosInGameWorld().x, getMousePosInGameWorld().y, dx, dy, invert);
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
