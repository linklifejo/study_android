package com.example.my10_intentresult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvMain;
    int ReqCode = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMain = findViewById(R.id.tvMain);
        // 새창 띄우기
        findViewById(R.id.btnMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDTO personDTO1 = new PersonDTO("CHO", 1111);

                // 1. 서브창을 띄우고 데이터를 넘긴다. : 인텐트 조공
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                intent.putExtra("id", "KIM");
                intent.putExtra("pw", 1234);
                intent.putExtra("personDTO1", personDTO1);
                startActivityForResult(intent, ReqCode);
            }
        });

    }// onCreate

    // 4. 서브에서 보낸 데이터 받는곳 : 정해져 있음, 반드시 오버라이드 시켜야함 별5개
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //                      1004            -1     서브에서 받은 인텐트
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ReqCode){ // 서브화면에서 받은것
            if(data != null){
                String key = data.getStringExtra("key");
                tvMain.setText(key);
            }
        }else if(requestCode == 1005){  // 다른화면에서 받은것

        }

    }

}


