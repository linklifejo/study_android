package com.example.my24_tab3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class Fragment3 extends Fragment {

    MainActivity activity;
    String sendData, receiveData;
    Person person3;

    Button btnThird;
    TextView tvThird;

    // 화면이 붙을때 작동하는 매소드 오버라이드 : 초기화
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // 프래그먼트가 속한 액티비티 가져옴
        activity = (MainActivity) getActivity();
        // 프래그먼트2에서 보낼 문자열과 객체(DTO)
        sendData = "프래그먼트3에서 보낸 데이터입니다.";
        person3 = new Person("LEE", 28);

        // 프래그먼트2에서 받을 문자열 변수 초기화
        receiveData = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3,
                container, false);
        tvThird = rootView.findViewById(R.id.tvThird);
        btnThird = rootView.findViewById(R.id.btnthird);

        // 프래그먼트2에서 보낸 데이터 받기 : 버튼이 눌렸을때 null이 아님
        if(activity.mBundle != null){
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            Person person2 = (Person) bundle.getSerializable("person2");
            String name = person2.getName();
            int age = person2.getAge();

            tvThird.setText(receiveData + "\n");
            tvThird.append("name : " + name + "\nage : " + age);

            activity.mBundle = null;
        }

        // 프래그먼트1로 데이터 보내기
        btnThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sendData", sendData);
                bundle.putInt("index", 2);
                bundle.putSerializable("person3", person3);

                // 메인 액티비티에 번들 만들어서 보내기
                activity.fragBtnClicked(bundle);

                // 메인 액티비티에 프래그먼트2로 화면전환을 요청
                TabLayout.Tab tab = activity.tabs.getTabAt(0);
                tab.select();

            }
        });


        return rootView;
    }
}
