package com.jpapps.badmetronome;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.media.AudioTrack;
import android.util.Log;

public class Metronome {
	
	public static final int DEFAULT_SPEED = 100;
	public static final int MAX_ACCURACY = 100;
	public static final int MAX_LAG_BEATS = 4;
	
	private int bpm, accuracy;
	private boolean playing;
	private byte[] sound;
	private AudioTrack audioTrack;
	private Runnable playbackRunnable;
	private Thread playbackThread;
	
	public Metronome(byte[] sound, AudioTrack audioTrack) {
		this(DEFAULT_SPEED, sound, audioTrack);
	}
	
	public Metronome(int speed, byte[] sound, AudioTrack audioTrack) {
		this(speed, MAX_ACCURACY, sound, audioTrack);
	}
	
	public Metronome(int speed, int accuracy, byte[] sound, AudioTrack audioTrack) {
		playing = false;
		this.setAccuracy(accuracy);
		this.setSound(sound);
		this.setBPM(speed);
		this.setAudioTrack(audioTrack);
	}

	public int getBPM() {
		return bpm;
	}

	public void setBPM(int speed) {
		this.bpm = speed;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	
	private byte[] buildSpace(int beatLength, int soundLength) {
		int error = 0;
		//Log.w("BadMetronome", "Sample Rate: " + audioTrack.getSampleRate() + "  Beat Length: " + beatLength + "  Sound Length: " + sound.length);
		if(this.accuracy < MAX_ACCURACY) {
			int errorRandom = new Random().nextInt(MAX_ACCURACY-1) + 1; //Generate a random number in the range 1 to MAX_ACCURACY
			if(errorRandom > this.accuracy) {
				//A mistake is going to occur
				double errorRangeFactor = (double)(errorRandom - this.accuracy)/(double)MAX_ACCURACY;
				double flip = new Random().nextDouble();
				// 50/50 chance that the mistake is playing early or late
				if(flip < 0.5 && beatLength > soundLength) {
					//Too little space
					error = (int) (-1 * Math.round(errorRangeFactor * (beatLength - soundLength)));
					//Log.w("BadMetronome", "Playing early by " + error);
				} else {
					//Too much space
					error = (int)(Math.round(errorRangeFactor * soundLength * MAX_LAG_BEATS));
					//Log.w("BadMetronome", "Playing late by " + error);
				}
			}
		}
		int spaceLength = beatLength - soundLength + error;
		byte[] space = new byte[spaceLength];
		return space;
	}
	
	public void start() {
		audioTrack.play();
		playing = true;
		
		playbackRunnable = new Runnable() {
			@Override
			public void run() {		
				while (playing) {
					int beatLength = (int) Math.round((60.0/bpm)*audioTrack.getSampleRate());
					int soundLength = sound.length;
					if(soundLength > beatLength)
						soundLength = beatLength; //with higher BPMs, the full sound is too long
					audioTrack.write(sound, 0, soundLength);
					byte[] space = buildSpace(beatLength, soundLength);
					audioTrack.write(space, 0, space.length);
		        }
			}
		};

		playbackThread = new Thread(playbackRunnable);
		playbackThread.start();
	}
	
	public void stop() {
		audioTrack.pause();
		audioTrack.flush();
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

	public AudioTrack getAudioTrack() {
		return audioTrack;
	}

	public void setAudioTrack(AudioTrack audioTrack) {
		this.audioTrack = audioTrack;
	}
	
}