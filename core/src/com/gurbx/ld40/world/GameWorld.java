package com.gurbx.ld40.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.enemies.EnemyHandler;
import com.gurbx.ld40.enemies.EnemyType;
import com.gurbx.ld40.inventory.Inventory;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.GameObject;

import box2dLight.RayHandler;

public class GameWorld implements GameObject {
	private final int WIDTH = 2000;
	private final int HEIGHT = 2000;
	
	private TextureAtlas atlas;
	private Tiles tiles;
	
	private CrystalHandler crystalHandler;
	private Storage storage;
	private EnemyHandler enemies;
	private EnemySpawner enemySpawner;
	private RayHandler rayHandler;
	
	public GameWorld(TextureAtlas atlas, Inventory inventory, Player player, EnemyHandler enemies, RayHandler rayHandler) {
		this.atlas = atlas;
		this.rayHandler = rayHandler;
		crystalHandler = new CrystalHandler(atlas, player, rayHandler);
		inventory.addObserver(crystalHandler);
		storage = new Storage(50, 50, atlas, player, inventory, this);
		this.enemies = enemies;
		
		enemySpawner = new EnemySpawner(enemies, this, player);
		inventory.addObserver(enemySpawner);
		
		tiles = new Tiles(atlas, this);
		
//		enemies.addEnemy(EnemyType.STANDARD, new Vector2(100, 400));
//		enemies.addEnemy(EnemyType.STANDARD, new Vector2(200, 300));
//		enemies.addEnemy(EnemyType.STANDARD, new Vector2(300, 200));
//		enemies.addEnemy(EnemyType.STANDARD, new Vector2(400, 100));
		
	}

	@Override
	public void update(float delta) {
		tiles.update(delta);
		crystalHandler.update(delta);
		storage.update(delta);
		enemySpawner.update(delta);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		tiles.render(batch);
		storage.render(batch);
		crystalHandler.render(batch);
		enemySpawner.render(batch);
		
	}

	@Override
	public void dispose() {
		crystalHandler.dispose();
		storage.dispose();
		tiles.dispose();
		enemySpawner.dispose();
	}

	public CrystalHandler getCrystalHandler() {
		return crystalHandler;
	}

	public int getHeight() {
		return HEIGHT;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public Storage getStorage() {
		return storage;
	}

}
