package com.example.badmetronome;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	
	SeekBar speedBar, accuracyBar;
	Button playPause;
	int speed, accuracy;
	boolean playing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		speedBar = (SeekBar) this.findViewById(R.id.speedBar);
		accuracyBar = (SeekBar) this.findViewById(R.id.accuracyBar);
		playPause = (Button) this.findViewById(R.id.button1);
		
		speed = speedBar.getProgress();
		accuracy = accuracyBar.getProgress();
		playing = false;
		
		playPause.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
