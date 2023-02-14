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

public class Fragment2 extends Fragment {
    MainActivity activity;
    String sendData, receiveData;
    TextView tv2;
    Person person2;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        sendData = "프래그먼트2에서 보내는 데이터입니다";
        // 프래그먼트3에서 받을 문자열변수 초기화
        person2 = new Person("kim",25);
        receiveData = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment2,
                container, false);
        tv2 = viewGroup.findViewById(R.id.textView2);
        if (activity.mBundle != null) {
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            Person person1 = (Person) bundle.getSerializable("person1");
            tv2.setText(receiveData + "\n");
            tv2.append("name : " + person1.getName()
                    + "\nage : " + person1.getAge());
            activity.mBundle = null;
        }
        viewGroup.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("sendData", sendData);
                bundle.putInt("index",1);
                bundle.putSerializable("person2",person2);
                activity.fragBtnClicked(bundle);
                TabLayout.Tab tab = activity.tabs.getTabAt(2);
                tab.select();

            }
        });
        return viewGroup;
    }
}
