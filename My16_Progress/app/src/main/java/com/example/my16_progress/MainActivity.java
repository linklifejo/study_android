package com.example.my16_progress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText etInput;
    ProgressDialog dialog;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        etInput = findViewById(R.id.etInput);
        seekBar = findViewById(R.id.seekBar);
        // progressBar 설정
        progressBar.setIndeterminate(false); // 정해진 값을 사용할것인지
        progressBar.setMax(100);  // 최대값 설정
        progressBar.setProgress(50);  // 최초 셋팅값

        findViewById(R.id.btnInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int data = Integer.parseInt(etInput.getText().toString());
                if(data >= 0){
                    if(data < 0 ){
                        progressBar.setProgress(0);
                    }else if(data >= 100 ){
                        progressBar.setProgress(100);
                    }else{
                        progressBar.setProgress(data);
                    }
                }

            }
        });
        // 보여주기
        findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("데이터를 확인하는 중입니다....");
                // 아무데나 눌럳 사라지지 않게 하기
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
        // 닫기
        findViewById(R.id.btnDismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override  // 내가 변경한값, 유저가 변경했는가?
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                etInput.setText("" + progress);
               // Log.d(TAG, "onProgressChanged: " + fromUser);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}