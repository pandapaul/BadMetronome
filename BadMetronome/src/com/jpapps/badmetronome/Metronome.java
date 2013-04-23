package com.jpapps.badmetronome;

import android.media.MediaPlayer;

public class Metronome {
	
	public static final int DEFAULT_SPEED = 50;
	public static final int MAX_ACCURACY = 100;
	
	private int speed, accuracy;
	private MediaPlayer player;
	
	protected boolean playing;
	
	public Metronome(MediaPlayer player) {
		this(DEFAULT_SPEED, player);
	}
	
	public Metronome(int speed, MediaPlayer player) {
		this(speed, MAX_ACCURACY, player);
	}
	
	public Metronome(int speed, int accuracy, MediaPlayer player) {
		this.setSpeed(speed);
		this.setAccuracy(accuracy);
		this.setPlayer(player);
		playing = false;
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
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
	
	public void togglePlayback() {
		if(playing) {
			this.stop();
		}
		else {
			this.start();
		}
	}

	public MediaPlayer getPlayer() {
		return player;
	}

	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}
	
}