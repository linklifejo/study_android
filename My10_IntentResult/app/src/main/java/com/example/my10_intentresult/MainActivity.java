package com.example.my10_intentresult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnMain;
    TextView tvMain;
    int reqCode = 1004;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMain = findViewById(R.id.btnMain);
        tvMain = findViewById(R.id.tvMain);

        btnMain.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
                Student hong = new Student("kim",24);
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                intent.putExtra("id","KIM");
                intent.putExtra("pw",1234);
                intent.putExtra("hong",hong);
                startActivityForResult(intent,reqCode);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == reqCode){
            if(data != null){
                String key = data.getStringExtra("key");
                tvMain.setText(key);
            }

        }
        else if(requestCode == 1005){

        }

    }
}