package com.gurbx.ld40.world;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.enemies.EnemyHandler;
import com.gurbx.ld40.enemies.EnemyType;
import com.gurbx.ld40.inventory.InventoryObserver;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.GameObject;

public class EnemySpawner implements GameObject, InventoryObserver {
	private final int BASE_SPAWN_TIME = 3;
	private final float MIN_SPAWN_TIME = 0.1f;
	private final int SPAWN_RANGE = 520;
	private float timer;
	private float spawnTime;
	private Random random;
	private GameWorld world;
	private Player player;
	private EnemyHandler enemies;
	
	public EnemySpawner(EnemyHandler enemyHandler, GameWorld world, Player player) {
		this.enemies = enemyHandler;
		random = new Random();
		spawnTime = BASE_SPAWN_TIME;
		timer = BASE_SPAWN_TIME;
		this.world = world;
		this.player = player;
	}

	@Override
	public void inventoryStatus(int playerCrystals, int storageCrystals) {
		if (playerCrystals >= 1) {
			spawnTime = (float) (BASE_SPAWN_TIME - 0.2*playerCrystals);
			if (spawnTime <= MIN_SPAWN_TIME) spawnTime = MIN_SPAWN_TIME;
		} else {
			spawnTime = BASE_SPAWN_TIME;
		}
	}
	
	@Override
	public void update(float delta) {
		timer -= delta;
		if (timer <= 0) { 
			timer = spawnTime;
			spawnEnemy();
		}
	}

	private void spawnEnemy() {
		float x = player.getPosition().x - SPAWN_RANGE*0.5f + random.nextInt(SPAWN_RANGE);
		float y = player.getPosition().y - SPAWN_RANGE*0.5f + random.nextInt(SPAWN_RANGE);
		
		float radians = (float) Math.atan2(player.getPosition().y - y, player.getPosition().x - x);
		float dx = MathUtils.cos(radians) * SPAWN_RANGE;
		float dy = MathUtils.sin(radians) * SPAWN_RANGE;
		
		
		Vector2 position = new Vector2(player.getPosition().x + dx, player.getPosition().y + dy);
		enemies.addEnemy(EnemyType.STANDARD, position);
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
