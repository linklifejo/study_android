package com.example.my09_layoutinflater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnInflate;
    LinearLayout linear;
    RelativeLayout relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInflate = findViewById(R.id.btnInflate);
        linear = findViewById(R.id.linear);
        relative = findViewById(R.id.relative);

        // 화면 인플레이트(붙이기) 하기
        btnInflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 화면을 붙이기 위해서 시스템에서 LAYOUT_INFLATER_SERVICE를 가져와서
                // 인스턴스를 만들어 넣는다
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.layout_linear, linear, true);
                inflater.inflate(R.layout.layout_relative, relative, true);

                Button btnLinear = linear.findViewById(R.id.btnLinear);
                btnLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,
                                "리니어 버튼이 눌렸습니다", Toast.LENGTH_SHORT).show();
                    }
                });

                Button btnRelative = relative.findViewById(R.id.btnRelative);
                btnRelative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,
                                "랠리티브 버튼이 눌렸습니다", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}