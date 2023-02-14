package com.example.my32_audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String AUDIO_URL =
            //"https://actions.google.com/sounds/v1/alarms/alarms_clock.ogg";
            "https://sites.google.com/site/ubiaccessmobile/sample_audio.mp3";

    MediaPlayer mediaPlayer;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 재생
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playAudio(AUDIO_URL);
                playAudio1(R.raw.m01);

                Toast.makeText(MainActivity.this,
                        "음악재생버튼 눌림", Toast.LENGTH_SHORT).show();
            }
        });

        // 중지
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    mediaPlayer.stop();

                    Toast.makeText(MainActivity.this,
                            "음악재생 중지", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 일시정지
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    position = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();

                    Toast.makeText(MainActivity.this,
                            "음악재생 일시정지", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 재시작
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null && !mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mediaPlayer.seekTo(position);

                    Toast.makeText(MainActivity.this,
                            "음악재생 재시작", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void playAudio(String audio_url) {
        killMediaPlayer();

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audio_url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playAudio1(int resId) {
        killMediaPlayer();

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), resId);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void killMediaPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.release();
        }
    }

}