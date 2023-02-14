package com.example.my17_fragment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    SubFragment subFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 이미 xml에 id를 주고 선언한 경우
        /*mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainFragment);*/
        mainFragment = new MainFragment();
        subFragment = new SubFragment();

        // 초기 메인화면에 프래그먼트 초기화 시키기 : 동적으로
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, mainFragment).commit();

    }

    // 프래그먼트에서 화면 전환을 요청받는 매소드
    // MainFragment:1, SubFragment:2
    public void onChangeFrag(int fragNum){
        if(fragNum == 1) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contain, subFragment).commit();
        } else if(fragNum == 2) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contain, mainFragment).commit();
        }
    }

}