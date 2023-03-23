package com.example.audiocontrol;

import androidx.appcompat.app.AppCompatActivity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    SeekBar streamLengthControl;
    SeekBar volumeControl;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer = MediaPlayer.create(this, R.raw.audio);
        time = mediaPlayer.getDuration();
        // Activate the buttons
        // Play
        Button btnPlay = findViewById(R.id.buttonPlay);
        btnPlay.setOnClickListener((View view) -> {
            play();
        });
        // Pause
        Button btnPause = findViewById(R.id.buttonPause);
        btnPause.setOnClickListener((View view) -> {
            pause();
        });
        // Stop
        Button btnStop = findViewById(R.id.buttonStop);
        btnStop.setOnClickListener((View view) -> {
            stop();
        });


        volumeControl = findViewById(R.id.volumeSeekBar);
        volumeControl.setMax(maxVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seekbar Change", Integer.toString(progress));
                // TODO: What will you do when the volume seek bar position is changed?
                float volume = progress/ (float) maxVolume;
                mediaPlayer.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        streamLengthControl = findViewById(R.id.scrubSeekBar);
        // Set max length
        streamLengthControl.setMax(time);
        streamLengthControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Progress Bar Change", Integer.toString(i));
                // TODO: what will happen if the seekbar position is changed? set the
                //  streamLengthControl position to the new position
                if(b){
                    mediaPlayer.seekTo(i);
                    streamLengthControl.setProgress(i);
                }
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // TODO: continuously change the position of the progress bar by setting the streamLengthControl

                streamLengthControl.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 100);
        streamLengthControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void play() {
        Log.i("Media Player", "isPlaying " + mediaPlayer.isPlaying());
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.start();

        }
    }

    public void pause() {
        Log.i("Media Player", "isPlaying " + mediaPlayer.isPlaying());
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        Log.i("Media Player", "isPlaying " + mediaPlayer.isPlaying());
        if(mediaPlayer.isPlaying()) {
            streamLengthControl.setProgress(0);
            mediaPlayer.stop();
        }
    }
}