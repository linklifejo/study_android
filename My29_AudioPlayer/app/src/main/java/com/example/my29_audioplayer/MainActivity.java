package com.example.my29_audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    String AUDIO_URL =
            "https://sites.google.com/site/ubiaccessmobile/sample_audio.mp3";
    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // 재생
        findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // playAudio(AUDIO_URL);
                playAudio1(R.raw.m01); // 내부적 파일재생 일경우
                Toast.makeText(MainActivity.this, "재생버튼 눌림", Toast.LENGTH_SHORT).show();
            }
        });
        //정지
        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                killMediaPlayer();
                Toast.makeText(MainActivity.this, "정지버튼 눌림", Toast.LENGTH_SHORT).show();

            }
        });
        //일시정지 : 정지한 곳의 위치가 필요
        findViewById(R.id.btnPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer != null) {
                    position = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                    Toast.makeText(MainActivity.this, "일시정지 버튼눌림", Toast.LENGTH_SHORT).show();

                }
            }
        });
        //재시작
        findViewById(R.id.btnRestart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mediaPlayer가 null이 아니면서 재생중이 아니여야 한다
                if(mediaPlayer != null && !mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mediaPlayer.seekTo(position);
                    Toast.makeText(MainActivity.this, "재시작버튼 눌림", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void playAudio(String audio_url) {
        // 만약 재생중이라면기존에 재생중인것을 kill한다
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
            // 만약 재생중이라면기존에 재생중인것을 kill한다
            killMediaPlayer();
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer = MediaPlayer.create(MainActivity.this,resId);
                  mediaPlayer.start();
            }catch(Exception e){
                e.printStackTrace();
            }

    }

    private void killMediaPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}