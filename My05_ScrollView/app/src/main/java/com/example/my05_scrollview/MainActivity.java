package com.example.my05_scrollview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // 1. xml에 id를 준 위젯을 찾아 변수에 넣는다
    // 2. 필요하다면 변수에 클릭이벤트를 단다
    Button btnChange;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 여기 디자인이 되어있는 xml의 이름은 R.layout.activity_main에 있다
        setContentView(R.layout.activity_main);

        btnChange = findViewById(R.id.btnChange);
        imageView = findViewById(R.id.imageView);

        // 버튼을 클릭하면 이미지를 바꾼다
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.image02);
            }
        });

        /*findViewById(R.id.btnChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.image02);
            }
        });*/


    }



}