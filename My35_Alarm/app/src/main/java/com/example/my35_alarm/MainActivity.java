package com.example.my35_alarm;

import androidx.appcompat.app.AppCompatActivity;

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
    protected void onStop() {
        super.onStop();
        if(player != null){
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 진동으로 울리기
        findViewById(R.id.btnVib).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >= 26){ // Build.VERSION_CODES.0
                    vibrator.vibrate(VibrationEffect.createOneShot(2000,10));
                }else{
                    vibrator.vibrate(2000);
                }
            }
        });
        // 소리로 울리기
        findViewById(R.id.btnSound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
                ringtone.play();
            }
        });
        // 파일로 소리 울리기
        findViewById(R.id.btnFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player = MediaPlayer.create(getApplicationContext(),R.raw.m01);
            }
        });
    }
}