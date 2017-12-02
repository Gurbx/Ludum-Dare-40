package com.gurbx.ld40.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gurbx.ld40.enemies.EnemyHandler;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.utils.porjectiles.Projectile;
import com.gurbx.ld40.world.Crystal;
import com.gurbx.ld40.world.CrystalHandler;

public class Player implements GameObject {
	private Vector2 position;
	private Sprite sprite;
	private float width, height;
	
	private PlayerStateHandler playerState;
	private CrystalHandler crystalHandler;
	private PlayerProjectileHandler projectileHandler;
	private Inventory inventory;
	private float rotation;
	
	private float mouseX, mouseY;
	
	private boolean shootingLeft;
	private boolean shootingRight;
//	private TextureRegion bulletTexture;
	private Gun gun;
	
	public Player(Vector2 position, TextureAtlas atlas, Inventory inventory, EnemyHandler enemies) {
		playerState = new PlayerStateHandler();
		this.inventory = inventory;
		inventory.addObserver(playerState);
		this.position = position;
		this.sprite = new Sprite(atlas.findRegion("player"));
		width = 32;
		height = 32;
		sprite.setPosition(position.x - 16, position.y - 16);
		
		projectileHandler = new PlayerProjectileHandler(enemies);
		gun = new Gun(0.1f, 4, 750, projectileHandler, position, atlas.findRegion("bullet"), 200);
		inventory.addObserver(gun);
	}
	
	public void setCrystalHandler(CrystalHandler handler) {
		this.crystalHandler = handler;
	}
	
	
	private void handleMovement(float delta) {
		float speed = playerState.getSpeed();
		if (Gdx.input.isKeyPressed(Keys.A)) position.x -= speed * delta;
		if (Gdx.input.isKeyPressed(Keys.D)) position.x += speed * delta;
		if (Gdx.input.isKeyPressed(Keys.W)) position.y += speed * delta;
		if (Gdx.input.isKeyPressed(Keys.S)) position.y -= speed * delta;
		
		sprite.setPosition(position.x - width*0.5f, position.y - height*0.5f);
	}
	
	private void handleCrystals() {
		if (inventory.canDropCrystal() && Gdx.input.isKeyJustPressed(Keys.Q)) {
			inventory.removeCrystalFromPlayer();
			crystalHandler.addCrystal(position.x, position.y);
		}
	}


	@Override
	public void update(float delta) {
		handleMovement(delta);
		handleCrystals();
		handleShooting(delta);
		projectileHandler.update(delta);
	}
	
	private void handleShooting(float delta) {
		gun.update(delta);
		if (shootingLeft) {
			gun.shoot(mouseX, mouseY);
		}
		
	}

	public void shootLeft(float targetX, float targetY) {
//		projectileHandler.addProjectile(new Projectile(position.x, position.y, targetX, targetY, bulletTexture, 750));
		gun.shoot(targetX, targetY);
	}
	
	public void canCollectCrystal(Crystal crystal) {
		if (Gdx.input.isKeyJustPressed(Keys.E)) {
			crystal.collect();
			inventory.addCrystalToPlayer();
		}
	}



	@Override
	public void render(SpriteBatch batch) {
		projectileHandler.render(batch);
		sprite.draw(batch);
		
	}

	@Override
	public void dispose() {
		projectileHandler.dispose();
		
	}
	
	public void setRotation(float degrees) {
		sprite.setRotation(degrees);
	}


	public Vector2 getPosition() {
		return position;
	}


	
	public void setShootingLeft(boolean b) {
		shootingLeft = b;
	}
	
	public void setShootingRight(boolean b) {
		shootingRight = b;
	}
	
	public boolean isShootingLeft() {
		return shootingLeft;
	}
	
	public boolean isShootingRight() {
		return shootingRight;
	}
	
	public void setMousePosition(Vector3 mousePosInGameWorld) {
		mouseX = mousePosInGameWorld.x;
		mouseY = mousePosInGameWorld.y;
		
	}
}
