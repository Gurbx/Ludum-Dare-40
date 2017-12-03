package com.gurbx.ld40.utils.sound;

public enum Sounds {
	LASER ("sound/Laser2.wav", 0.5f),
	HURT ("sound/Hurt.wav", 0.5f),
	HURT2 ("sound/Hurt2.wav", 2f),
	EXPLOSION ("sound/Explosion1.wav", 0.5f),
	PICKUP ("sound/Powerup1.wav", 1f),
	PICKUP2 ("sound/Powerup110.wav", 0.5f),
	PICKUP3 ("sound/Powerup117.wav", 0.5f),
	SELECT ("sound/select.wav", 0.5f),
	HIT ("sound/Hit1.wav", 1f);
	
	private String path;
	private float volume;

	private Sounds(String path, float volume) {
		this.path = path;
		this.volume = volume;
	}

	public String getPath() {
		return path;
	}
	
	public float getVolume() {
		return volume;
	}
}
