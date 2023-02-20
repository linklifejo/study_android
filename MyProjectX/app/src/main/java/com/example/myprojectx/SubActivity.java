package com.example.myprojectx;

import static com.example.myprojectx.COMMON.CommonMethod.ipConfig;
import static com.example.myprojectx.COMMON.CommonMethod.loginDto;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprojectx.COMMON.CommonMethod;
import com.example.myprojectx.DTO.MemberDTO;
import com.example.myprojectx.adapter.MemberAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubActivity extends AppCompatActivity {
    private static final String TAG = "main:SubActivity";
    RecyclerView recyclerView;
    MemberAdapter adapter;
    ArrayList<MemberDTO> dtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // 반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        // recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(
                this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 서버에 member들 데이터를 요청
        CommonMethod commonMethod = new CommonMethod();
      //  commonMethod.setParams("id",loginDto.getId());
        commonMethod.getData("selectMembers", new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

               if(response.isSuccessful()){
                  Log.d(TAG, "onResponse: " + response.body());
                    //서버에서 넘어온 데이터를 받는다
 //                 dtos = new ArrayList<>(Arrays.asList(new Gson().fromJson(response.body(), MemberDTO[].class)));

                   Gson gson = new Gson();
                    dtos =  gson.fromJson(response.body(), new TypeToken<ArrayList<MemberDTO>>(){}.getType());


                    for(MemberDTO dto: dtos){
                        dto.setProfile(ipConfig + "resources/" + dto.getProfile());
                    }

                    adapter = new MemberAdapter(SubActivity.this,dtos);

                   recyclerView.setAdapter(adapter);
                //   adapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(SubActivity.this,
                            "넘어온 데이타가 없습니다;;",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        // 어댑터 객체 생성
        adapter = new MemberAdapter(SubActivity.this,dtos);
        recyclerView.setAdapter(adapter);
    }
}