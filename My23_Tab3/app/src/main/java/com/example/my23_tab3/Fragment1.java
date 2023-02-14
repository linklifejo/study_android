package com.example.my23_tab3;

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

public class Fragment1 extends Fragment {
    MainActivity activity;
    String sendData, receiveData;
    TextView tv1;
    Person person1;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        sendData = "프래그먼트1에서 보내는 데이터입니다";
        person1 = new Person("kim",25);
        // 프래그먼트3에서 받을 문자열변수 초기화
        receiveData = "";

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment1,
                container, false);

        tv1 = viewGroup.findViewById(R.id.textView1);
        if (activity.mBundle != null) {
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            Person person3 = (Person) bundle.getSerializable("person3");
            tv1.setText(receiveData + "\n");
            tv1.append("name : " + person3.getName()
                    + "\nage : " + person3.getAge());
            activity.mBundle = null;
        }

        viewGroup.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("sendData", sendData);
                    bundle.putInt("index",0);
                    bundle.putSerializable("person1",person1);
                    activity.fragBtnClicked(bundle);
                    TabLayout.Tab tab = activity.tabs.getTabAt(1);
                    tab.select();
            }
        });

        // 프래그먼트3에서 보낸 데이터보기

        return viewGroup;
    }
}
