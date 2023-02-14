package com.example.my24_bottomnavi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    BottomNavigationView bNaviView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        // 처음 화면 초기화 : Fragment1로 초기화
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, fragment1).commit();
        // 네비게이션뷰를 찾아서 클릭이벤트를 달아준다
        bNaviView = findViewById(R.id.bottom_navi);
        bNaviView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contain, fragment1).commit();
                        break;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contain, fragment2).commit();
                        break;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contain, fragment3).commit();
                        break;
                }
                return true;
            }
        });
    }
}