package com.example.my14_myreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmsDisActivity extends AppCompatActivity {

    Button button1;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_dis);

        button1 = findViewById(R.id.button1);
        textView = findViewById(R.id.textView);

        // MyReceiver에서 보낸 인텐트를 받는다.
        Intent disIntent = getIntent();
        // 인텐트를 처리하는 매소드
        processIntent(disIntent);

        // 닫기
        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void processIntent(Intent disIntent) {
        String sender = disIntent.getStringExtra("sender");
        String receivedDate = disIntent.getStringExtra("receivedDate");
        String contents = disIntent.getStringExtra("contents");

        if(sender != null){
            button1.setText(sender + " 에서 문자 수신");
            textView.setText("[" + receivedDate + "]\n" + contents);
        }

    }

    // 기존 화면을 사용하고자 할때 onCreate()를 사용하지 못하니
    // onNewIntent() 매소드를 오버라이드하여 새로운 데이터(인텐트)를 받아
    // 화면에 데이터를 갱신한다.
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        processIntent(intent);
    }
}



