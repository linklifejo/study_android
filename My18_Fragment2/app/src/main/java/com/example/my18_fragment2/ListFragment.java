package com.example.my18_fragment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.listfragment,
                container, false);

        mainActivity =(MainActivity) getActivity();
        // 이미지1
        viewGroup.findViewById(R.id.btnImg1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mainActivity.onImageSelect(R.drawable.dream01);
            }
        });
        // 이미지2
        viewGroup.findViewById(R.id.btnImg2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onImageSelect(R.drawable.dream02);

            }
        });

        return viewGroup;
    }
}
