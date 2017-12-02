package com.gurbx.ld40.utils.particles;

public enum ParticleEffectType {
	CRYSTAL("img/crystalEffect.p", 20, true),
	CRYSTAL_PICKUP("img/crystalPickup.p", 20, false),
	BLOOD_GROUND("img/bloodGround.p", 50, true),
	HIT("img/hit.p", 40, false),
	HIT_SMALL("img/smallHit.p", 40, false),
	SHOOT("img/shoot.p", 10, false);
	
	
	private String path;
	private int poolSIze;
	private boolean behind;


	private ParticleEffectType(String path, int poolSIze, boolean behind) {
		this.path = path;
		this.poolSIze = poolSIze;
		this.behind = behind;
	}
	
	public String getPath() {
		return path;
	}


	public int getPoolSIze() {
		return poolSIze;
	}

	public boolean getBehind() {
		return behind;
	}
}
