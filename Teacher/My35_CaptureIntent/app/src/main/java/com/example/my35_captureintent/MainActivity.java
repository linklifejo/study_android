package com.example.my35_captureintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    File imgFile = null;
    String imgFilePath = null;
    Bitmap bitmap;
    public int reqPicCode = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        imageView = findViewById(R.id.imageView);

        // 사진찍기
        findViewById(R.id.btnPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 암묵적인텐트 : 사진찍기(카메라를 불러옴)
                Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 일단 이 인텐트가 사용가능한지 체크
                if(picIntent.resolveActivity(getPackageManager()) != null){
                    imgFile = null;
                    // creatFile 매소드를 이용하여 임시파일을 만듬
                    imgFile = creatFile();

                    if(imgFile != null){
                        // API24 이상부터는 FileProvider를 제공해야함
                        Uri imgUri = FileProvider.getUriForFile(getApplicationContext(),
                                getApplicationContext().getPackageName()+".fileprovider",
                                imgFile);
                        // 만약에 API24 이상이면
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){ // API24
                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        }else {
                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
                        }

                        startActivityForResult(picIntent, reqPicCode);
                    }

                }
            }
        });



    }

    private File creatFile() {
        // 파일 이름을 만들기 위해 시간값을 생성함
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "My" + timestamp;
        // 사진파일을 저장하기 위한 경로
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File curFile = null;
        try {
            // 임시파일을 생성함(전체경로),  2번째 suffix 확장자:파일확장자(jpg)
            curFile = File.createTempFile(imageFileName, ".jpg"
                    , storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 스트링타입으로 임시파일이 있는 곳의 절대경로를 저장함
        imgFilePath = curFile.getAbsolutePath();

        return curFile;
    }


    // 사진찍은 후 데이터를 받는곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == reqPicCode && resultCode == RESULT_OK){
            // 저장처리를 함
            Toast.makeText(MainActivity.this, "사진이 잘 찍힘", Toast.LENGTH_SHORT).show();

            setPic();
        }

    }

    // 사진을 저장처리 하는 곳
    private void setPic() {
        // 이미지뷰의 크기 알아오기
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // 사진의 크기 가져오기
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int photoW = options.outWidth;
        int photoH = options.outHeight;

        // 이미지 크기를 맟출비율을 결정
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // 이미지뷰의 크기에 맞게 이미지크기를 조절
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        options.inPurgeable = true;

        // 비트맵 이미지를 생성
        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);
        // 이미지를 갤러리에 저장하기
        gelleryAddPic(bitmap);
        imageView.setImageBitmap(bitmap);

        /*BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);
        imageView.setImageBitmap(bitmap);*/

    }

    // 이미지를 갤러리에 저장하기
    private void gelleryAddPic(Bitmap bitmap) {
        FileOutputStream fos;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){ // API29
            ContentResolver resolver = getContentResolver();

            // 맵구조를 가진 ContentValues : 파일정보를 저장함
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,
                    "Image_" + "jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,
                    "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + "TestFolder");

            Uri imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues );
            try {
                fos = (FileOutputStream) resolver
                        .openOutputStream(Objects.requireNonNull(imageUri));
                Toast.makeText(MainActivity.this,
                        "fos 작업됨", Toast.LENGTH_SHORT).show();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

            }catch (Exception e){

            }

        }else {
            // 이미지 파일을 스캔해서 갤러리에 저장하기 위한 인텐트
            Intent msIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            // 처음에 CreateFile에서 생성해둔 이미지경로(imgFilePath)를 이용하여 파일객체를 만든다
            File f = new File(imgFilePath);
            Uri contentUri = Uri.fromFile(f);
            msIntent.setData(contentUri);
            // sendBroadcast를 이용하여 저장
            this.sendBroadcast(msIntent);
        }

    }

    // 위험권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_MEDIA_LOCATION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}