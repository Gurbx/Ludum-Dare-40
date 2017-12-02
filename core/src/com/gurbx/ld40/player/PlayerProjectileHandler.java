package com.gurbx.ld40.player;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gurbx.ld40.enemies.EnemyHandler;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.utils.porjectiles.Projectile;

public class PlayerProjectileHandler implements GameObject {
	private LinkedList<Projectile> projectiles;
	private EnemyHandler enemies;
	
	public PlayerProjectileHandler(EnemyHandler enemies) {
		projectiles = new LinkedList<Projectile>();
		this.enemies = enemies;
	}
	
	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update(delta);
			if (projectiles.get(i).shouldRemove()) {
				projectiles.get(i).dispose();
				projectiles.remove(i);
			} else {
				projectiles.get(i).handleEnemyCollision(enemies.getEnemies());
			}
		}
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(batch);
		}
		
	}

	@Override
	public void dispose() {
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).dispose();
		}
		
	}

}
