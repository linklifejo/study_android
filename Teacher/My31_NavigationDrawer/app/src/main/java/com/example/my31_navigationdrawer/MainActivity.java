package com.example.my31_navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    FloatingActionButton fab;
    NavigationView nav_view;
    DrawerLayout draw_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav_view = findViewById(R.id.nav_view);
        // implement Listener 할때는 반드시 아래와 같이 정의한다.
        nav_view.setNavigationItemSelectedListener(this);

        // toolbar 적용 : theme에 NoActionBar로 변경
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        draw_layout = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, draw_layout, toolbar,
                 R.string.drawer_open, R.string.drawer_close );
        draw_layout.addDrawerListener(toggle);
        toggle.syncState();

        // 프래그먼트를 생성해서 프래임레이아웃에 초기화를 시킨다
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, fragment1).commit();

        // 헤드 드로어에 접근해서 로그인 정보 표시하기
        int userLevel = 0; // 관리자:0, 일반유저:1 => DB
        String loginID = "BLACK PINK"; // 관리자ID:BlackPink, 일반유저ID:BTS
        View headerView = nav_view.getHeaderView(0);

        // textView
        TextView navLoginID = headerView.findViewById(R.id.loginID);
        navLoginID.setText("반갑습니다" + loginID + "님!!!");
        // ImageView
        ImageView loginImage = headerView.findViewById(R.id.loginImage);
        Glide.with(this)
                .load(R.drawable.blackpink)
                //.load("https://upload3.inven.co.kr/upload/2020/06/21/bbs/i015955522648.gif")
                .circleCrop()
                .into(loginImage);
        //loginImage.setImageResource(R.drawable.bts);

        // userLevel이 0이면 관리자라서 아래메뉴까지 보여주고
        // userLevel이 1이면 일반유저라서 아래메뉴를 보여주지 않는다.
        if(userLevel == 0){
            nav_view.getMenu().findItem(R.id.communi).setVisible(true);
        }else if(userLevel == 1){
            nav_view.getMenu().findItem(R.id.communi).setVisible(false);
        }

        // 플로팅 버튼이 클릭되었을때
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action"
                                , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    // 메뉴에 있는 아이템이 선택되었을때
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 클릭한 아이템의 id를 얻는다
        int id = item.getItemId();

        if(id == R.id.nav_home){
            onFragmentSelected(0);
        }else if(id == R.id.nav_gallery){
            onFragmentSelected(1);
        }else if(id == R.id.nav_slideshow){
            onFragmentSelected(2);
        }else if(id == R.id.nav_tools){
            onFragmentSelected(3);
        }else if(id == R.id.nav_share){
            onFragmentSelected(4);
        }else if(id == R.id.nav_send){
            onFragmentSelected(5);
        }

        // 메뉴 선택 후 드로어가 사라지게 한다
        draw_layout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void onFragmentSelected(int position) {
        Fragment curFragment = null;

        if(position == 0){
            curFragment = fragment1;
            toolbar.setTitle("첫번째 화면");
        }else if(position == 1){
            curFragment = fragment2;
            toolbar.setTitle("두번째 화면");
        }else if(position == 2){
            curFragment = fragment3;
            toolbar.setTitle("세번째 화면");
        }else if(position == 3){
            curFragment = fragment1;
            toolbar.setTitle("네번째 화면");
        }else if(position == 4){
            curFragment = fragment2;
            toolbar.setTitle("다섯번째 화면");
        }else if(position == 5){
            curFragment = fragment3;
            toolbar.setTitle("여섯번째 화면");
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, curFragment).commit();

    }

    // 뒤로가기를 눌렀을때 만약 드로어 창이 열려있으면 드로어 창을 닫고
    // 아니면 그냥 뒤로가기 원래 작업을 한다(여기서는 앱종료)
    @Override
    public void onBackPressed() {
        if(draw_layout.isDrawerOpen(GravityCompat.START)){
            draw_layout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

}