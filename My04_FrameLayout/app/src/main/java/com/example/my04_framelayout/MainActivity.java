package com.example.my04_framelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnChgImage;
    ImageView ivImage1, ivImage2, ivImage3;
    int selImage = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChgImage = findViewById(R.id.btnChgImage);
        ivImage1 = findViewById(R.id.image1);
        ivImage2 = findViewById(R.id.image2);
        ivImage3 = findViewById(R.id.image3);

        btnChgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // selImage가 1이면 첫번째 이미지 2이면 두번째 이미지
                // 3이면 세번째 이미지를 보여주겠다
                if(selImage == 1){
                    ivImage1.setVisibility(View.VISIBLE);
                    ivImage2.setVisibility(View.GONE);
                    ivImage3.setVisibility(View.GONE);
                    selImage = 2;
                }else if(selImage == 2){
                    ivImage1.setVisibility(View.GONE);
                    ivImage2.setVisibility(View.VISIBLE);
                    ivImage3.setVisibility(View.GONE);
                    selImage = 3;
                }else if(selImage == 3){
                    ivImage1.setVisibility(View.GONE);
                    ivImage2.setVisibility(View.GONE);
                    ivImage3.setVisibility(View.VISIBLE);
                    selImage = 1;
                }

            }
        });


    }
}