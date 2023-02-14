package com.example.my40_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 진동으로 울리기
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator)
                        getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >= 26) { //Build.VERSION_CODES.O){ // API26
                    // 진동시간, 진동세기(1-255 까지 가능)
                    vibrator.vibrate(VibrationEffect.createOneShot(2000, 10));
                }else {
                    vibrator.vibrate(2000);
                }
            }
        });

        // 소리로 울리기
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager
                        .getRingtone(getApplicationContext(), uri);
                ringtone.play();
            }
        });

        // 파일 소리로 울리기
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = MediaPlayer
                        .create(getApplicationContext(), R.raw.m01);
                player.start();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        if(player != null){
            player.stop();
            player.release();
            player = null;
        }
    }
}