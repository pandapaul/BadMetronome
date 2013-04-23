package com.jpapps.badmetronome;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

public class Metronome {
	
	public static final int DEFAULT_SPEED = 50;
	public static final int MAX_ACCURACY = 100;
	
	private int speed, accuracy;
	private MediaPlayer player;
	
	protected int delay;
	protected boolean playing;
	protected Handler playbackHandler;
	protected Runnable playbackRunnable;
	
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
		delay = calculateDelay();
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
		if(player!=null)
			 duration = player.getDuration();
		double beatLength = 60000.0 / speed;
		delay = (int)Math.round(beatLength) - duration;
		return delay;
	}
	
	public void start() {
		playbackHandler = new Handler();
		playbackRunnable = new Runnable() {
			@Override
			public void run() {
				if(player.isPlaying()) {
					player.seekTo(0);
				} else {
					player.start();
				}
				playbackHandler.postDelayed(this, delay);
			}
		};
		playbackHandler.post(playbackRunnable);
		playing = true;
	}
	
	public void stop() {
		if(player.isPlaying()){
			player.pause();
		}
		if(playbackHandler != null)
			playbackHandler.removeCallbacks(playbackRunnable);
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

	public MediaPlayer getPlayer() {
		return player;
	}

	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}
	
}