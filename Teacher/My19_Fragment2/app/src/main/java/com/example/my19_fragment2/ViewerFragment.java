package com.example.my19_fragment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewerFragment extends Fragment {

    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_viewer,
                container, false);

        imageView = rootView.findViewById(R.id.imageView);

        return rootView;
    }

    // 메인에서 이미지를 바꿀수 있도록 접근하는 매소드
    public void imageChange(int resId){  // R.drawable.dream01, dream02
        imageView.setImageResource(resId);
    }

}
