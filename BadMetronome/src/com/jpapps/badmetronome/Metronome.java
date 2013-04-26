package com.jpapps.badmetronome;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

public class Metronome {
	
	public static final int DEFAULT_SPEED = 50;
	public static final int MAX_ACCURACY = 100;
	
	private int speed, accuracy;
	private boolean playing;
	private byte[] sound;
	
	public Metronome(byte[] sound) {
		this(DEFAULT_SPEED, sound);
	}
	
	public Metronome(int speed, byte[] sound) {
		this(speed, MAX_ACCURACY, sound);
	}
	
	public Metronome(int speed, int accuracy, byte[] sound) {
		playing = false;
		this.setAccuracy(accuracy);
		this.setSound(sound);
		this.setSpeed(speed);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	
	private int calculateDelay() {
		int delay = 0;
		int duration = 0;
		double beatLength = 60000.0 / speed;
		delay = (int)Math.round(beatLength) - duration;
		
		return Math.max(delay, 0);
	}
	
	public void start() {
		
		playing = true;
	}
	
	public void stop() {
		playing = false;
	}
	
	public void togglePlayback() {
		if(playing) {
			this.stop();
		}
		else {
			this.start();
		}
	}
	
	public boolean isPlaying() {
		return playing;
	}

	public byte[] getSound() {
		return sound;
	}

	public void setSound(byte[] sound) {
		this.sound = sound;
	}
	
}