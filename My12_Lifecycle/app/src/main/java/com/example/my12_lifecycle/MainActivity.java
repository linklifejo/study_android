package com.example.my12_lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private static final String TAG ="main:MainActivity";
Button btnMain;
String id;
int pw;
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: 호출됨");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 호출됨");
        savePersonal();
        Log.d(TAG, "loadPersonal: 저장됨 " + id +", "+ pw);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 호출됨");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 호출됨");
        savePersonal();
        Log.d(TAG, "loadPersonal: 저장됨 " + id +", "+ pw);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 호출됨");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 호출됨");

        loadPersonal();
        Log.d(TAG, "loadPersonal:가져오기 " + id +", "+ pw);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: 호출됨");
        btnMain = findViewById(R.id.btnMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                startActivity(intent);
            }
        });


    }

    // 핸드폰 내부 저장공간에 데이터 저장하기
   private void savePersonal(){
       SharedPreferences pref = getSharedPreferences("personal", Activity.MODE_PRIVATE);
       SharedPreferences.Editor editor = pref.edit();
       id = "eyedial";
       pw = 1234;
       editor.putString("id", id);
       editor.putInt("pw",pw);
       editor.commit();

   }

   // 핸드폰 내부 저장공간에서 데이터 가졍기
    private void loadPersonal() {
        SharedPreferences pref = getSharedPreferences(
                "personal",Activity.MODE_PRIVATE
        );
        if(pref != null) {
             id = pref.getString("id", "CSS");
             pw = pref.getInt("pw", 9999);
        }
    }

}