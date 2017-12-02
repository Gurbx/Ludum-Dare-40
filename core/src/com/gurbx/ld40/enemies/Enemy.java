package com.gurbx.ld40.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gurbx.ld40.Application;
import com.gurbx.ld40.player.Player;
import com.gurbx.ld40.utils.GameObject;
import com.gurbx.ld40.utils.ScreenShaker;
import com.gurbx.ld40.utils.particles.ParticleEffectHandler;
import com.gurbx.ld40.utils.particles.ParticleEffectType;

public class Enemy implements GameObject {
	private Vector2 position;
	private float width, height;
	private EnemyType type;
	private Sprite sprite;
	private boolean shouldRemove;
	private float elapsedTime;
	
	private int health;
	private float speed;
	private float dx, dy;
	private float radians;
	private Animation animation;
	
	private float attackTimer;
	
	private Player player;
	
	public Enemy(Vector2 position, EnemyType type, TextureAtlas atlas, Player player) {
		TextureRegion tex = atlas.findRegion(type.getPath());
		this.width = tex.getRegionWidth();
		this.height = tex.getRegionHeight();
		sprite = new Sprite(tex);
		this.type = type;
		this.position = position;
		sprite.setPosition(position.x - width*0.5f, position.y - height*0.5f);
		shouldRemove = false;
		this.health = type.getHealth();
		this.player = player;
		this.speed = type.getSpeed();
		initAnimation(atlas, type);
		elapsedTime = 0;
	}
	
	private void initAnimation(TextureAtlas atlas, EnemyType type) {
		   TextureRegion[] moveFrames = new TextureRegion[4];
	       for (int i = 0; i < moveFrames.length; i++) {
	    	   moveFrames[i] = atlas.findRegion(type.getPath() + (i+1));
	        }
		   this.width = moveFrames[0].getRegionWidth();
		   this.height =moveFrames[0].getRegionHeight();
	       animation = new Animation(1/12f, moveFrames);  
	}

	@Override
	public void update(float delta) {
		elapsedTime += delta;
		handleMovement(delta);
//		sprite.setPosition(position.x - width*0.5f, position.y - height*0.5f);
//		sprite.setRotation((float) Math.toDegrees(radians));
		
	}

	private void handleMovement(float delta) {
		chasePlayer(delta);
	}

	private void chasePlayer(float delta) {
		radians = (float) Math.atan2(player.getPosition().y - position.y, player.getPosition().x - position.x);
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		position.x += dx * delta;
		position.y += dy * delta;
		
		
		//Handle attack
		attackTimer-=delta;
		if (attackTimer <= 0) {
			//Check if close enough to attack
			if (overlaps(player.getPosition().x, player.getPosition().y, width + 10)) {
				attackTimer = type.getAttackCooldown();
//				SoundHandler.playSound(Sounds.ATTACK);
				player.damage(type.getDamage());
			}
		}

	}
	
	public boolean overlaps(float x, float y, float range) {
		if (x < position.x + width && x + range > position.x &&
				y < position.y + height && y + range > position.y) {
			return true;
		}
		return false;
	}

	@Override
	public void render(SpriteBatch batch) {
//		sprite.draw(batch);
		batch.draw(animation.getKeyFrame(elapsedTime, true), position.x - width*0.5f, position.y-height*0.5f, width/2, height/2, width, height, 1f, 1f, (float) Math.toDegrees(radians));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean shouldRemove() {
		return shouldRemove;
	}

	public void damaga(int damage) {
		this.health -= damage;
		if (health <= 0) {
			shouldRemove = true;
			Application.shakeScreen(10, 10, true);
			ParticleEffectHandler.addParticleEffect(ParticleEffectType.HIT, position.x, position.y);
			ParticleEffectHandler.addParticleEffect(ParticleEffectType.BLOOD_GROUND, position.x, position.y);
		} else {
			Application.shakeScreen(5, 4, false);
		}
		
		System.out.println(health);
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}

}
