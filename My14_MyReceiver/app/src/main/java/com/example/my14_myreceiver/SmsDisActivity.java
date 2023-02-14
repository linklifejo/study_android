package com.example.my14_myreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmsDisActivity extends AppCompatActivity {

    Button btnSender, btnClose;
    TextView tvContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_dis);

        btnSender = findViewById(R.id.btnSender);
        btnClose = findViewById(R.id.btnClose);
        tvContents = findViewById(R.id.tvContents);

        // MyReceiver에서 보낸 인텐트를 받는다
        Intent disIntent = getIntent();
        // 인텐트를 처리하는 매소드
        processIntent(disIntent);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void processIntent(Intent disIntent) {
        String sender = disIntent.getStringExtra("sender");
        String receivedDate =
                disIntent.getStringExtra("receivedDate");
        String contents = disIntent.getStringExtra("contents");

        if(sender != null){
            btnSender.setText(sender + " 에서 문자수신");
            tvContents.setText("[" + receivedDate + "]\n" + contents);
        }

    }

    // 기존 화면을 사용하고자 할때 onCreate()를 사용하지 못하니
    // onNewIntent() 매소드를 오버라이드 하여 새로운 데이터(인텐트)를
    // 받아 화면에 데이터를 갱신한다
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        processIntent(intent);
    }
}