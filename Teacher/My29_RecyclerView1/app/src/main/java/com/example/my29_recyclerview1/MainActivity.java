package com.example.my29_recyclerview1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*1. DB에 있는 테이블을 기본으로 하여 DTO를 만든다
        가정 : 1. DB에 singerDTO라는 테이블이 있다
        2. singerDTO라는 테이블에 name, mobile, age, resId(이미지 경로)의 칼럼이 있다

    2. 1에서 만든 DTO에서 내가 보여주고 싶은 데이터를 골라 xml파일을 만든다
    3. xml 파일에서 사용된 모든 변수를 adapter에서 클래스로 선언한다
    4. 선언한 클래스와 xml 파일을 이용하여 화면을 adapter에서 생성한다
    5. 만든 adapter를 RecyclerView에 붙여준다*/

    RecyclerView recyclerView;
    ArrayList<SingerDTO> dtos;

    SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        // recyclerView에서 반드시 초기화를 아래와 같이 해줘야함
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 어댑터 객체 생성
        adapter = new SingerAdapter(MainActivity.this, dtos);

        // 어댑터에 있는 ArrayList(dtos)에 dto 추가
        adapter.addDto(new SingerDTO("블랙핑크", "010-1111-1111",
                25, R.drawable.singer1));
        adapter.addDto(new SingerDTO("걸스데이", "010-1111-2222",
                26, R.drawable.singer2));
        adapter.addDto(new SingerDTO("방탄소년단", "010-1111-3333",
                23, R.drawable.singer3));
        adapter.addDto(new SingerDTO("마마무", "010-1111-4444",
                29, R.drawable.singer4));
        adapter.addDto(new SingerDTO("소녀시대", "010-1111-5555",
                28, R.drawable.singer5));
        // 만든 어댑터를 리싸이클러뷰에 붙인다
        recyclerView.setAdapter(adapter);



    }
}