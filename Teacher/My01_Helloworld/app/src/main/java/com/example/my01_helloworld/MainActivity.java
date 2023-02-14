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

    // 버튼 전역변수로 만든다 => 선언만 해준다.
    Button btnCall;
    EditText editText;

    // 오버라이드 : 부모액티비티에 이미 선언된 매소드를 재정의 하는것
    @Override
    // 화면을 생성해주는 매소드
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 이 액티비티는 R:res, layout:res아래 layout, activity_main:화면이름
        // 여기서 화면 지정
        setContentView(R.layout.activity_main);

        // 화면을 지정했으니 위 화면에서 정의한 위젯(버튼, 텍스트뷰 등)을 찾을수 있다.
        // 찾는타입 변수명 = findViewById(R.id.내가준 아이디);
        // 변수명은 xml에서 주었던 id와 같은 이름을 주었으면 좋겠다(달라도 상관없음)
        // 1. id를 이용하여 찾아서 변수에 넣어준다.
        // 전화번호는 숫자만 쓰므로 xml에 inputType에 number로 체크
        editText = findViewById(R.id.editText); // 010-1111-2222
        btnCall = findViewById(R.id.btnCall);
        // 2. 변수명에 클릭리스너를 달아준다.
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editText에 있는 전화번호를 가져와서 변수에 저장.
                String phoneNum = "tel:" + editText.getText().toString();

                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse(phoneNum));
                startActivity(intent);
            }
        });

        // 새창 띄우기 : 명시적 인텐트
        findViewById(R.id.btnNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
            }
        });


    } // onCreate

    // 한번 클릭해보기 : 매소드 이용 => xml에서 지정해줌
    public void onBtnClicked(View view){
        Toast.makeText(this, "클릭한번 해 봤어!!!",
                Toast.LENGTH_SHORT).show();

    }

    // 네이버 연결하기 : 암묵적 인텐트를 사용
    public void onNaverClicked(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://m.naver.com"));
        startActivity(intent);
    }

}