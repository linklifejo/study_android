package com.example.my28_gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    /*1. DB에 있는 테이블을 기본으로 하여 DTO를 만든다
        가정 : 1. DB에 singerDTO라는 테이블이 있다
        2. singerDTO라는 테이블에 name, mobile, age, resId(이미지 경로)의 칼럼이 있다

    2. 1에서 만든 DTO에서 내가 보여주고 싶은 데이터를 골라 xml파일을 만든다
    3. xml 파일에서 사용된 모든 변수를 adapter에서 클래스로 선언한다
    4. 선언한 클래스와 xml 파일을 이용하여 화면을 adapter에서 생성한다
    5. 만든 adapter를 listView에 붙여준다*/

    GridView gridView;

    SingerAdapter adapter;
    ArrayList<SingerDTO> dtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 디바이스 사이즈 구하기
        Point size = getDeviceSize();

        // 중요 : dtos 넘겨줄때 반드시 생성해서 넘겨준다
        dtos = new ArrayList<>();

        gridView = findViewById(R.id.gridView);

        // 어댑터 객체를 생성한다
        adapter = new SingerAdapter(MainActivity.this, dtos);
        // 어댑터에 생성한 매소드 addDto를 이용하여 dtos에 데이터를 추가한다
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
        // 5. 만든 어댑터를 리스트뷰에 붙인다
        gridView.setAdapter(adapter);
        // 리스트뷰는 아이템 클릭 이벤트를 제공해준다
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingerDTO dto = (SingerDTO) adapter.getItem(position);
                Toast.makeText(MainActivity.this, "" +
                        "선택 : " + position + "\n이름 : " + dto.getName()
                        + "\n전화번호 : " + dto.getMobile() + "\n나이 : " + dto.getAge()
                        + "\n이미지 : " + dto.getResId(), Toast.LENGTH_SHORT).show();
            }
        });

        // 추가
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addDto(new SingerDTO("김연자", "010-1111-7777",
                        35, R.drawable.dream03));
                // 추가후 리스트 갱신
                adapter.notifyDataSetChanged();
            }
        });


    }

    // 디바이스 가로 세로 사이즈 구하기
    // getRealSize()는 status bar 등 system insets을
    // 포함한 스크린 사이즈를 가져오는 방법이고,
    // getSize()는 status bar 등 system insets를
    // 제외한 스크린 사이즈를 가져오는 함수 입니다.
    // 단위는 픽셀입니다
    private Point getDeviceSize() {
        Display display = getWindowManager().getDefaultDisplay(); // in Activity
        // getActivity.getWindowManager().getDefaultDisplay();  // in Fragment
        Point size = new Point();
        display.getRealSize(size);
        int width = size.x; // 디바이스 넓이
        int height = size.y; // 디바이스 높이
        Log.d(TAG, "getDeviceSize: 넓이 => " + width + ", 높이 => " + height);
        
        return size;
    }
}