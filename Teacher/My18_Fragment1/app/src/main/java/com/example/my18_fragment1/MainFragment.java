package com.example.my18_fragment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    MainActivity activity;

    // 프래그먼트 화면 붙이기 : 반드시 onCreateView를 오버라이드 시켜야한다
    @Nullable
    @Override    //                        화면 붙이는 객체
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.mainfragment,
                                container, false);

        activity = (MainActivity) getActivity();

        // 메인프래그먼트 버튼이 눌렸을때
        rootView.findViewById(R.id.btnMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(1);
            }
        });



        return rootView;
    }
}
