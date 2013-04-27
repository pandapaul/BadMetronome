package com.jpapps.badmetronome;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.media.AudioTrack;

public class Metronome {
	
	public static final int DEFAULT_SPEED = 100;
	public static final int MAX_ACCURACY = 100;
	
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
	
	private byte[] buildSilence() {
		int error = 0;
		if(this.accuracy < MAX_ACCURACY) {
			double rando = new Random().nextDouble();
			if(rando*100 > this.accuracy) {
				//The drummer dun goofed
				error = (int)((MAX_ACCURACY/100-(rando))*sound.length);
			}
		}
		int silenceLength = (int) ((60.0/bpm)*audioTrack.getSampleRate()) + error;
		byte[] silence = new byte[silenceLength];
		return silence;
	}
	
	public void start() {
		audioTrack.play();
		
		playbackRunnable = new Runnable() {
			@Override
			public void run() {		
				while (playing) {
					audioTrack.write(sound, 0, sound.length);
					byte[] silence = buildSilence();
					audioTrack.write(silence, 0, silence.length);
		        }
			}
		};

		playbackThread = new Thread(playbackRunnable);
		playbackThread.start();
		
		playing = true;
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