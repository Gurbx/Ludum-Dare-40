package com.gurbx.ld40.enemies;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.GameObject;

public class EnemyHandler implements GameObject {
	private LinkedList<Enemy> enemies;
	private TextureAtlas atlas;
	private Player player;
	
	public EnemyHandler(TextureAtlas atlas) {
		this.atlas = atlas;
		enemies = new LinkedList<Enemy>();
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void addEnemy(EnemyType type, Vector2 position) {
		enemies.add(new Enemy(position, type, atlas, player));
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update(delta);
			if (enemies.get(i).shouldRemove()) {
				enemies.get(i).dispose();
				enemies.remove(i);
			}
		}
		
	}

	@Override
	public void render(SpriteBatch batch) {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(batch);
		}
	}

	@Override
	public void dispose() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).dispose();
		}
		
	}
	
	public LinkedList<Enemy> getEnemies() {
		return enemies;
	}


}
