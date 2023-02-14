package com.example.my18_fragment2;

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
     MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.viewerfragment,
                container, false);
        mainActivity = (MainActivity)getActivity();
        imageView = viewGroup.findViewById(R.id.imageView);
        //imageView.setImageResource();
        return viewGroup;
    }
    // 메인에서 이미지를 바꾸수 있도록 접근하는 메소드
    public void onimageChange(int resId){
        imageView.setImageResource(resId);
    }
}
