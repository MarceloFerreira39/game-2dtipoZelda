package com.mvjstudios.main;

import java.applet.Applet;
import java.applet.AudioClip;



@SuppressWarnings("deprecation")
public class Sound {

	private AudioClip clip;

	public static final Sound MUSIC_BACKGROUND = new Sound("/sound01.wav");
	public static final Sound HURTEFFECT = new Sound("/Hit_Hurt3.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {}
			
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {}
	}

	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		
		} catch (Throwable e) {}
		
	}
}
