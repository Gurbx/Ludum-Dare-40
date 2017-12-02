package com.gurbx.ld40.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.Application;
import com.gurbx.ld40.inventory.InventoryObserver;
import com.gurbx.ld40.utils.porjectiles.Projectile;

public class Gun implements InventoryObserver {
	private PlayerProjectileHandler projectileHandler;
	private float standardCooldown;
	private float cooldown;
	private float timer;
	private int standardDamage;
	private int damage;
	private float speed;
	private float standardSpeed;
	private float minSpeed;
	
	private Vector2 playerPosition;
	private TextureRegion bulletTexture;
	
	private boolean canShoot;
	
	public Gun(float cooldown, int damage, float speed, PlayerProjectileHandler projectileHandler, Vector2 playerPosition, TextureRegion bulletTexture,
			float minSpeed) {
		this.bulletTexture = bulletTexture;
		this.projectileHandler = projectileHandler;
		this.cooldown = cooldown;
		this.standardCooldown = cooldown;
		this.playerPosition = playerPosition;
		this.timer = 0;
		this.damage = damage;
		this.standardDamage = damage;
		this.speed = speed;
		this.standardSpeed = speed;
		this.minSpeed = minSpeed;
		canShoot = false;
	}
	
	
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) {
			canShoot = true;
		}
	}
	
	public void shoot(float targetX, float targetY) {
		if (canShoot) {
			timer = cooldown;
			projectileHandler.addProjectile(new Projectile(playerPosition.x, playerPosition.y, targetX, targetY, bulletTexture, speed, damage));
			canShoot = false;
			Application.shakeScreen(4, 2, false);
		}
	}


	@Override
	public void inventoryStatus(int playerCrystals, int storageCrystals) {
		if (playerCrystals >= 1) {
			cooldown = standardCooldown + playerCrystals*0.02f;
			damage = standardDamage - playerCrystals;
//			speed = standardSpeed * (1-(0.1f * playerCrystals));
			
			if (damage < 1) damage = 1;
			if (speed < minSpeed) speed = minSpeed;
		} else {
			cooldown = standardCooldown;
			damage = standardDamage;
			speed = standardSpeed;
		}
	}
}
