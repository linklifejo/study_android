package com.example.myprojectx;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.myprojectx.ASyncTask.ApiClient;
import com.example.myprojectx.ASyncTask.ApiInterface;
import com.example.myprojectx.COMMON.CommonMethod;
import com.example.myprojectx.DTO.MemberDTO;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "main:JoinActivity";

    // 서버에 보내야 할 내용들
    EditText etIDMem, etPWMem, etName, etPhone, etAddress;

    // 사진찍기 -------------
    ImageView ivProfile;
    File imgFile = null;
    String imgFilePath = null;
    int reqPicCode = 1004;
    // ----------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        etIDMem = findViewById(R.id.etIDMem);
        etPWMem = findViewById(R.id.etPWMem);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);

        ivProfile = findViewById(R.id.ivProfile);
        // 이미지뷰를 클릭하면 사진을 찍을수 있게 한다
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 암묵적 인텐트 : 사진찍기(카메라창을 불러옴)
                Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 일단 이 인텐트가 사용가능한지 체크
                if(picIntent.resolveActivity(getPackageManager()) != null){
                    imgFile = null;
                    // creatFile 매소드를 이용하여 임시파일을 만듬
                    imgFile = creatFile();

                    if(imgFile != null){
                        // API24 이상부터는 FileProvider를 제공해야함
                        Uri imgUri = FileProvider.getUriForFile(
                                getApplicationContext(),
                                getApplicationContext().getPackageName()+".fileprovider",
                                imgFile
                        );
                        // 만약에 API24 이상이면
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){ // API24
                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        }else{  // 만약에 24 미만이면
                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(imgFile));
                        }

                        startActivityForResult(picIntent, reqPicCode);
                    }

                }
            }
        });

        // 가입하기 : 회원의 정보(내용)를 가져와서 서버에 보낸다
        findViewById(R.id.btnJoinMem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id = etIDMem.getText().toString();
                String pw = etPWMem.getText().toString();
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();

                // 보낼 파일 MultipartBody.Part 생성

               CommonMethod commonMethod = new CommonMethod();
                MemberDTO dto = new MemberDTO() ;

                dto.setId(etIDMem.getText().toString());
                dto.setPw(etPWMem.getText().toString());
                dto.setName( etName.getText().toString());
                dto.setPhone(etPhone.getText().toString());
                dto.setAddress(etAddress.getText().toString());
                dto.setProfile(etAddress.getText().toString());

                commonMethod.setParams("param", dto);
                // 보낼 파일 MultipartBody.Part 생성
                // List<MultipartBody.Part> imgs = new ArrayList<>();
                RequestBody fileBody =null;
                MultipartBody.Part filePart =null;
                if(imgFile != null) {
                     fileBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(imgFilePath));
                     filePart = MultipartBody.Part.createFormData("file", "test.jpg", fileBody);
                }
                // 데이터를 파라메터로 보낸다
                //commonMethod.setParams("param", dto);
                // 서버로 보내는 부분
                commonMethod.sendFile("anJoin", filePart, new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(TAG, "onResponse: 보내기 성공 " );
                        finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "onResponse: 보내기 실패 => " + t.getMessage() );
                    }
                });



//                //commonMethod.setParams("profileimg", imgFilePath);
//                commonMethod.sendFile("anJoin", imgFilePath).enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//
//                    }
//                });


            }
        });

        // 취소
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // 사진을 찍은 후 데이터 받는곳 :정해져 있음
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == reqPicCode && resultCode == RESULT_OK){
            // 저장처리를 함
            Toast.makeText(this, "사진이 잘 찍힘", Toast.LENGTH_SHORT).show();

            setPic();
        }

    }

    // 사진 저장처리를 하는 곳
    private void setPic() {
        // 사진의 크기 가져오기
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 사진의 해상도를 1/8로 지정
        options.inSampleSize = 8;
        // 비트맵 이미지를 생성
        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);
        // 이미지를 갤러리에 저장
        gelleryAddPic(bitmap);
        // 이미지를 이미지뷰에 세팅
        Glide   .with(this)
                .load(imgFilePath)
                .centerCrop()
                .circleCrop()  // 사진을 둥글게
                .placeholder(R.mipmap.ic_launcher_round)
                .into(ivProfile);


        //ivProfile.setImageBitmap(bitmap);

    }

    // 이미지를 갤러리에 저장
    private void gelleryAddPic(Bitmap bitmap) {
        FileOutputStream fos;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){ // API29
            // 쓰기 객체
            ContentResolver resolver = getContentResolver();
            // 맵구조를 가진 ContentValues : 파일정보를 저장함
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,
                    "Image_jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,
                    "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + "MyFolder");

            Uri imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
            );

            try {
                fos = (FileOutputStream) resolver
                        .openOutputStream(Objects.requireNonNull(imageUri));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

                Toast.makeText(this, "fos 작업됨", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

    }

    private File creatFile() {
        // 파일 이름을 만들기 위해 시간값을 생성함
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "My" + timestamp;
        // 사진파일을 저장하기 위한 경로
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File curFile = null;
        try{
            // 임시파일을 생성함      1:파일이름,2:확장자이름,3:경로
            curFile = File.createTempFile(imageFileName,
                    ".jpg", storageDir);
        }catch (Exception e){
            e.getMessage();
        }
        // 스트링타입으로 임시파일이 있는 곳의 절대경로를 저장함
        imgFilePath = curFile.getAbsolutePath();
        Log.d(TAG, "creatFile: " + imgFilePath);

        return curFile;
    }


}