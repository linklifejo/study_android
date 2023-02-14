package com.example.my23_tab3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class Fragment3 extends Fragment {
    MainActivity activity;
    String sendData, receiveData;
    TextView tv3;
    Person person3;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        sendData = "프래그먼트3에서 보내는 데이터입니다";
        person3 = new Person("kim",25);
        // 프래그먼트3에서 받을 문자열변수 초기화
        receiveData = "";
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment3,
                container, false);
        tv3 = viewGroup.findViewById(R.id.textView3);
        if (activity.mBundle != null) {
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            Person person2 = (Person) bundle.getSerializable("person2");
            tv3.setText(receiveData + "\n");
            tv3.append("name : " + person2.getName()
                    + "\nage : " + person2.getAge());
            activity.mBundle = null;
        }
        viewGroup.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("sendData", sendData);
                bundle.putInt("index",2);
                bundle.putSerializable("person3",person3);
                activity.fragBtnClicked(bundle);
                TabLayout.Tab tab = activity.tabs.getTabAt(0);
                tab.select();

            }
        });
        return viewGroup;
    }
}
