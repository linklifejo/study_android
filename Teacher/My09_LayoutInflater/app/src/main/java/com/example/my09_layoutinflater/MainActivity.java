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

        linear = findViewById(R.id.linear);
        relative = findViewById(R.id.relative);
        btnInflate = findViewById(R.id.btnInflate);

        // 인플레이트 하기
        btnInflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.layout_linear, linear, true);
                inflater.inflate(R.layout.layout_relative, relative, true);

                Button btnLinear = linear.findViewById(R.id.btnLinear);
                btnLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "리니어버튼 눌림!!", Toast.LENGTH_SHORT).show();
                    }
                });

                relative.findViewById(R.id.btnRelative).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "랠리티브버튼 눌림!!!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}