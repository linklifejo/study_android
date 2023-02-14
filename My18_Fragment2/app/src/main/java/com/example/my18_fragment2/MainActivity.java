package com.example.my18_fragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ListFragment listFragment;
    ViewerFragment viewerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        listFragment = (ListFragment) manager.findFragmentById(R.id.listfragment);
        viewerFragment = (ViewerFragment) manager.findFragmentById(R.id.viewrfragment);


    }
    // 프래그먼트에서 접근할수 있도록 만든 메소드
    public void onImageSelect(int resId) {
        // 만약 1이 넘어오면 이미지1, 2가 넘어오면 이미지2를 뛰운다
        viewerFragment.onimageChange(resId);
    }
}