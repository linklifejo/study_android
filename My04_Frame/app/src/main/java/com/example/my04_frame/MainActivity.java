package com.example.my04_frame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button btnChgImage;
    ImageView ivImage1,ivImage2,ivImage3;
    int selImage = 2;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChgImage = findViewById(R.id.btnChgImage);
        ivImage1 = findViewById(R.id.ivImage1);
        ivImage2 = findViewById(R.id.ivImage2);
        ivImage3 = findViewById(R.id.ivImage3);

        btnChgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(selImage == 1){
                ivImage1.setVisibility(View.VISIBLE);
                ivImage2.setVisibility(View.GONE);
                ivImage3.setVisibility(View.GONE);
                selImage =2;
            }
            else if (selImage== 2){

                ivImage1.setVisibility(View.GONE);
                ivImage2.setVisibility(View.VISIBLE);
                ivImage3.setVisibility(View.GONE);
                selImage =3;

            }else{

                ivImage1.setVisibility(View.GONE);
                ivImage2.setVisibility(View.GONE);
                ivImage3.setVisibility(View.VISIBLE);
                selImage =1;

            }
            }
        });

    }
}