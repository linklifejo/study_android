package com.example.my42_asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    ProgressBar progressBar;
    BackgroundTask backgroundTask;
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        // 실행
        findViewById(R.id.btnExe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundTask = new BackgroundTask(progressBar, value);
                try {
                    String result = backgroundTask.execute().get();
                    Log.d(TAG, "onClick: " + result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 취소
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(backgroundTask != null){
                    backgroundTask.cancel(true);
                }
            }
        });



    }
}