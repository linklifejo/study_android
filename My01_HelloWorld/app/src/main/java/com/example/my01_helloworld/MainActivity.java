package com.example.my01_helloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnNaver, btnCall, btnNew;
    EditText etPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // R:res-> layout -> activity_main.xml파일을 디자인으로 하겠다
        // 화면(디자인) 연결
        setContentView(R.layout.activity_main);

        // 에디트 텍스트를 찾는다
        etPhoneNum = findViewById(R.id.etPhoneNum);

        // xml에 선언된 id로 찾아서 똑같은 타입으로 변수를 생성하여 넣는다
        btnNaver = findViewById(R.id.btnNaver);
        // 변수에 이벤트 리스너를 추가시킨다 : 클릭이벤트
        btnNaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 암묵적 인텐트를 사용
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://m.naver.com"));
                startActivity(intent);

            }
        });

        btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext에 있는 전화번호를 가져온다
                String phone = "tel:" + etPhoneNum.getText().toString();

                // 암묵적 인텐트를 사용
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse(phone));
                startActivity(intent);

            }
        });

        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 명시적 인텐트를 사용
                Intent intent = new
                        Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
            }
        });

    }

    // xml파일에서 정의
    public void btn1Clicked(View view){
        Toast.makeText(this, "버튼1을 클릭했네요!!!",
                Toast.LENGTH_SHORT).show();
    }

}