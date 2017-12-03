package com.gurbx.ld40.utils.sound;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.gurbx.ld40.Application;

public class SoundHandler {
	private static HashMap<Sounds, Sound> sounds;
//	private static ArrayList<TimedSound> timedSounds;
	
//	private static Music music;
	
	public SoundHandler() {
		//Sounds
		sounds = new HashMap<Sounds, Sound>();
//		timedSounds = new ArrayList<TimedSound>();
	}
	
	public static void initSounds(Application app) {
		for (Sounds soundType : Sounds.values()) {
			Sound sound = app.assets.get(soundType.getPath(), Sound.class);
			sounds.put(soundType, sound);
		}
//		music = app.assets.get("sound/music/music1.ogg", Music.class);
	}
	
	public static void playSound(Sounds sound) {
		if (!Application.SOUND_ON) return;
		sounds.get(sound).play(sound.getVolume());
	}
	
	public static void playSound(Sounds sound, float duration)  {
		if (!Application.SOUND_ON) return;
//		timedSounds.add(new TimedSound(sounds.get(sound), duration, sound.getVolume()));
	}
	
	public static void playMusic() {
//		if (!Application.MUSIC_ON) return;
//		music.stop();
//		if (music.isPlaying() == false) {
//			music.setVolume(0.25f);
//			music.play();
//			music.setLooping(true);
//		}
	}
	
	public void update(float delta) {
//		for (int i = 0; i < timedSounds.size(); i++) {
//			timedSounds.get(i).update(delta);
//			if (timedSounds.get(i).finishedPlaying()) {
//				timedSounds.remove(i);
//			}
// 		}
	}
	
	public static void muteSounds() {
	}
	
	
	public void dispose() {
		for (Sounds key : sounds.keySet()) {
			sounds.get(key).dispose();
		}
//		music.dispose();
	}
}
