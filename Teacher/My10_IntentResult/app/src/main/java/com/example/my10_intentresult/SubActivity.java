package com.example.my10_intentresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    TextView tvSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        tvSub = findViewById(R.id.tvSub);

        // 2. 메인에서 넘겨준 데이터 받기 : 넘겨준 인텐트가 있으면 null이 아님
        Intent intent = getIntent();
        if(intent != null){
            String id = intent.getStringExtra("id");
            int pw = intent.getIntExtra("pw", 9999);
            PersonDTO personDTO1 =
                    (PersonDTO) intent.getSerializableExtra("personDTO1");

            // 기존에 써져 있는 데이터를 모두 지운다
            tvSub.setText("id : " + id + "\npw : " + pw);
            // 기존 데이터 끝에 추가한다
            tvSub.append("\nid : " + personDTO1.getId() +
                    "\npw : " + personDTO1.getPw());
        }

        // 메인으로 돌아가기
        findViewById(R.id.btnSub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. 메인에게 인텐트 만들어서 데이터 보내기
                Intent reIntent = new Intent();
                reIntent.putExtra("key", tvSub.getText().toString() + "ㅜㅜ");
                // 정상적으로 종료가 됐음을 알려줌
                setResult(RESULT_OK, reIntent);

                finish();
            }
        });


    }
}