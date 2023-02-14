package com.example.my25_navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
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
        // implement Listener 할때는 반드시 아래와 같이 정의한다
        nav_view.setNavigationItemSelectedListener(this);

        // toolbar 적용 : res->theme->NoActionBar로 변경
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // drawerLayout을 찾아서 토클 리스너를 붙인다
        draw_layout = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, draw_layout, toolbar,
                R.string.draw_open, R.string.draw_close );
        draw_layout.addDrawerListener(toggle);
        toggle.syncState();
        // 프래그먼트를  객체를 생성하고 프래임레이아웃에 초기화를 시킨다.
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, fragment1).commit();
        // 헤드 드로어에 접근해서 로그인 정보 표시하기
//        int UserLevel = 1; // 0: 관리자, 1: 일반유저 _> 디비에 있음
//        String loginID = "BTS"; // 관리자ID: BlackPink, 일반유저: BTS
        int UserLevel = 0; // 0: 관리자, 1: 일반유저 _> 디비에 있음
        String loginID = "BlackPink"; // 관리자ID: BlackPink, 일반유저: BTS
        View headerView = nav_view.getHeaderView(0);
        // 헤드 드로우에 있는 TextView 2개
        TextView navLoginID = headerView.findViewById(R.id.loginID);
        navLoginID.setText("반갑습니다 " + loginID + "님");

        // 이미지뷰에 접근
        ImageView loginImage = headerView.findViewById(R.id.loginImage);
        Glide
                .with(this)
              //  .load("https://image.jtbcplus.kr/data/contents/jam_photo/202212/19/ea593606-6855-4963-80b4-25323a0ec8d4.jpg")
              //  .load(R.drawable.bts)
                  .load(R.drawable.blackpink)

                .centerCrop()
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(loginImage);
        //loginImage.setImageResource(R.drawable.bts);
        // userLevel이 0 이면 관리자라서 아래메뉴까지 보여주고
        // userLevel이 1 이면 일반유저라서 아래메뉴를 보여주지 않는다
        if(UserLevel == 0){
            nav_view.getMenu().findItem(R.id.communi).setVisible(true);
        }else if (UserLevel == 1){
            nav_view.getMenu().findItem(R.id.communi).setVisible(false);
        }
        // 플로팅 버튼이 클릭되었을때
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Replace with your own code",
                        Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 클릭한 아이템의 id를 얻는다
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                onFragmentSelected("첫번째화면",fragment1);
                break;
            case R.id.nav_gallery:
                onFragmentSelected("두번째화면",fragment2);
                break;
            case R.id.nav_slideshow:
                onFragmentSelected("첫번째화면",fragment3);
                break;
            case R.id.nav_tools:
                onFragmentSelected("세번째화면",fragment1);
                break;
            case R.id.nav_share:
                onFragmentSelected("네번째화면",fragment2);
                break;
            case R.id.nav_send:
                onFragmentSelected("다섯번째화면",fragment3);
                break;
        }
        // 메뉴 선택후 드로어가 사라지게 한다
        draw_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onFragmentSelected(String screen, Fragment selFragment){
        toolbar.setTitle(screen);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, selFragment).commit();
    }
    // 뒤로가기를 눌렀을때 만약 드로어 창이 열려있으면 드로어 창을 닫고
    // 아니면 그냥 뒤로가기 원래 작업을 한다(여기서는 앱 종료)


    @Override
    public void onBackPressed() {
        if(draw_layout.isDrawerOpen(GravityCompat.START)){
            draw_layout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }
}