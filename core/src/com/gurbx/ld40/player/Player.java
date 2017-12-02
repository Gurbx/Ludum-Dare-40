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
import com.badlogic.gdx.physics.box2d.Body;
import com.gurbx.ld40.enemies.EnemyHandler;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.utils.Input;
import com.gurbx.ld40.utils.porjectiles.Projectile;
import com.gurbx.ld40.world.Crystal;
import com.gurbx.ld40.world.CrystalHandler;

import box2dLight.RayHandler;

public class Player implements GameObject {
	private float speed;
	private int MAX_SPEED = 200;
	
	private Vector2 baseDirection;
	private Vector2 velocity;
	
	private boolean shootInvert;
	private Vector2 position;
	private Sprite tower;
	private float offsetX, offsetY;
	private Sprite base;
	private float width, height;
	
	private PlayerStateHandler playerState;
	private CrystalHandler crystalHandler;
	private PlayerProjectileHandler projectileHandler;
	private Inventory inventory;
	private float towerRotation;
	private float baseRotation;
	
	private float mouseX, mouseY;
	
	private boolean shootingLeft;
	private boolean shootingRight;
//	private TextureRegion bulletTexture;
	private Gun gun;
	private RayHandler rayHandler;
	private Input input;
	
	public Player(Vector2 position, TextureAtlas atlas, Inventory inventory, EnemyHandler enemies) {
		playerState = new PlayerStateHandler();
		this.inventory = inventory;
		inventory.addObserver(playerState);
		this.position = position;
		base = new Sprite(atlas.findRegion("player"));
		this.tower = new Sprite(atlas.findRegion("turret"));
		width = 64;
		height = 64;
		tower.setPosition(position.x - width*0.5f, position.y - height*0.5f);
		base.setPosition(position.x - width*0.5f, position.y - height*0.5f);
		
		projectileHandler = new PlayerProjectileHandler(enemies);
		gun = new Gun(0.1f, 4, 750, projectileHandler, position, atlas.findRegion("bullet"), 200);
		inventory.addObserver(gun);
		
		baseDirection = new Vector2();
		velocity = new Vector2();
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
		
		tower.setPosition(position.x - width*0.5f, position.y - height*0.5f);
		base.setPosition(position.x - width*0.5f, position.y - height*0.5f);
	}
	
	private void handleCrystals() {
		if (inventory.canDropCrystal() && Gdx.input.isKeyJustPressed(Keys.E)) {
			inventory.removeCrystalFromPlayer();
			crystalHandler.addCrystal(position.x, position.y);
		}
	}


	@Override
	public void update(float delta) {
//		handleMovement(delta);
		handleBaseMovement(delta);
		handleCrystals();
		handleShooting(delta);
		projectileHandler.update(delta);
	}
	
	private void handleBaseMovement(float delta) {
		//Rotate
		if (Gdx.input.isKeyPressed(Keys.A)) {
			baseRotation += 100*delta;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			baseRotation -= 100*delta;
		}
		//Keep rotation in 360
		if (baseRotation > 360) baseRotation = 0;
		if (baseRotation < 0) baseRotation = 360;
		
		//get direction
		baseDirection.x = (float) Math.cos(Math.toRadians(baseRotation));
		baseDirection.y = (float) Math.sin(Math.toRadians(baseRotation));
		
		velocity.x = baseDirection.x * speed;
		velocity.y = baseDirection.y * speed;
		
		position.x += velocity.x * delta;
		position.y += velocity.y * delta;
		
		//Forward
		if (Gdx.input.isKeyPressed(Keys.W) && (speed < MAX_SPEED)) {
			speed += 150*delta;
		} else if (speed > 0){
			speed -= 150*delta;
		}
		//Backward
		if (Gdx.input.isKeyPressed(Keys.S) && (speed > -1*MAX_SPEED/2)) {
			speed -= 150*delta;
		} else if (speed < 0){
			speed += 150*delta;
		}
		
		base.setRotation(baseRotation);
		
//		position.x = base.getX() - width*0.5f;
//		position.y = base.getY() - height*0.5f;
		
		tower.setPosition(position.x - width*0.5f, position.y - height*0.5f);
		base.setPosition(position.x - width*0.5f, position.y - height*0.5f);
	}

	private void handleShooting(float delta) {
		gun.update(delta);
		if (shootingLeft) {
			input.refrensShootingProperties(0);
			gun.shoot(mouseX, mouseY, offsetX, offsetY, shootInvert);
		}
		
	}

	public void shootLeft(float targetX, float targetY) {
//		projectileHandler.addProjectile(new Projectile(position.x, position.y, targetX, targetY, bulletTexture, 750));
//		gun.shoot(targetX, targetY, offsetX, offsetY);
	}
	
	public void canCollectCrystal(Crystal crystal) {
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			crystal.collect();
			inventory.addCrystalToPlayer();
		}
	}



	@Override
	public void render(SpriteBatch batch) {
		projectileHandler.render(batch);
		base.draw(batch);
		tower.draw(batch);
		
	}

	@Override
	public void dispose() {
		projectileHandler.dispose();
		
	}
	
	public void setTowerRotation(float degrees) {
		towerRotation = degrees;
		tower.setRotation(degrees);
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

	public void setRayHandler(RayHandler rayHandler2) {
		this.rayHandler = rayHandler2;
		gun.setRayHandler(rayHandler2);
		
	}

	public float getTurretRotation() {
		return towerRotation;
	}

	public void setShootingInformation(float x, float y, float dx, float dy, boolean invert) {
		offsetX = dx;
		offsetY = dy;
		shootInvert = invert;
	
	}

	public void setInput(Input input2) {
		this.input = input2;
		
	}

}
