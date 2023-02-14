package com.example.my21_optionmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 반드시 androidx 로 임포트
        actionBar = getSupportActionBar();
        // 액션바 숨기기 2가지 방법
        // 1. res->values->themes.xml => parent:NoActionBar로 수정
        // 2. actionBar.hide();

        // 액션바의 타이틀부분 로고 바꾸기
        actionBar.setLogo(R.drawable.back); // 뒤로가기
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);

        // 타이틀로고 클릭이벤트 만들기 : 디폴트 true
        // 그리고 onOptionsItemSelected()에 추가
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // 옵션바에 옵션메뉴 붙이기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);

        return true;
    }

    // 옵션메뉴에 클릭이벤트 달아주기
    @Override  //                            내가 선택한 옵션메뉴
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 그 메뉴 아이템에서 내가 부여한 아이디를 가져올수 있다
        int curId = item.getItemId();

        switch (curId){
            case R.id.menu_refresh:
                Toast.makeText(this, "새로고침메뉴가 클릭됨!!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_search:
                Toast.makeText(this, "검색메뉴가 클릭됨!!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_settings:
                Toast.makeText(this, "설정메뉴가 클릭됨!!!", Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home:
                Toast.makeText(this, "홈 메뉴가 눌림...", Toast.LENGTH_SHORT).show();
                finish();  // 여기서는 앱 종료
                break;
        }

        return true;
    }
}



