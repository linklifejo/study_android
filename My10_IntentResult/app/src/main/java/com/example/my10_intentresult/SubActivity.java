package com.example.my10_intentresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    Button btnSub;
    TextView tvSub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        btnSub = findViewById(R.id.btnSub);
        tvSub = findViewById(R.id.tvSub);
        Intent intent = getIntent();
        if(intent != null){
            String id = intent.getStringExtra("id");
            int  pw = intent.getIntExtra("pw",9999);
            Student hong = (Student) intent.getSerializableExtra("hong");
            tvSub.setText("id" + id + "\npw : " + pw);
            tvSub.append("\n이름 : " +hong.getName() + "\n나이 : " + hong.getAge());
        }

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent reIntent = new Intent();
            reIntent.putExtra("key",tvSub.getText().toString() + "\nㅋㅋㅋ");
            setResult(RESULT_OK,reIntent);
            finish();
            }
        });
    }
}