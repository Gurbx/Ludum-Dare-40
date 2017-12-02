package com.gurbx.ld40.enemies;

public enum EnemyType {
	STANDARD(10, 1, 100, 1, "bug");
	
	private int health;
	private int damage;
	private int speed;
	private float attackCooldown;
	private String path;
	
	private EnemyType(int health, int damage, int speed, float attackCooldowm, String path) {
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.attackCooldown = attackCooldowm;
		this.path = path;
	}

	public int getHealth() {
		return health;
	}

	public int getDamage() {
		return damage;
	}

	public int getSpeed() {
		return speed;
	}
	
	public String getPath() {
		return path;
	}

	public float getAttackCooldown() {
		return attackCooldown;
	}
	
	
	

}
