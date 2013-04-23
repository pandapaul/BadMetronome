package com.example.badmetronome;

public class Metronome {
	private int speed, accuracy;
	
	public static final int DEFAULT_SPEED = 50;
	public static final int MAX_ACCURACY = 100;
	
	public Metronome() {
		this(DEFAULT_SPEED, MAX_ACCURACY);
	}
	
	public Metronome(int speed) {
		this(speed, MAX_ACCURACY);
	}
	
	public Metronome(int speed, int accuracy) {
		this.setSpeed(speed);
		this.setAccuracy(accuracy);
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
}
