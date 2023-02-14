package com.example.my33_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ScrollingView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkDangerousPermissions();
        textView = findViewById(R.id.textView);
        scrollView = findViewById(R.id.scrollView);
        // 내 위치 확인하기
        findViewById(R.id.btnLoc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationService();
            }
        });
    }

    private void startLocationService() {
        // 위치관리자 객체 참조
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 윈치정보리스너를 클래스로 만드는 방법이 있고
        // 직접 여기에서 위치정보리스너를 만드는 방법이 있다
        long minTime = 10000; //10초마다 갱신
        float minDistance = 0; // 위치변화가 없더라도 갱신하겠다 단위는 미터

        try{
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {


                            Double latitude = location.getLatitude(); // 윈도
                            Double longitude = location.getLongitude(); // 경도

                            String msg = "Latitude : " + latitude
                                    + "\nLongitude : " + longitude;
                            textView.append("\n내위치 => \n" + msg);
                            scrollView.fullScroll(View.FOCUS_DOWN);

                        }
                    });
            // 만약 터널 같은곳에 들어가면 신호를 받지 못하므로
            // 마지막 수신된곳을 알려주게 한다
            Location lastLocation =
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                   //manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); //기지국

            if(lastLocation != null){
                Double latitude = lastLocation.getLatitude(); // 위도
                Double longitude = lastLocation.getLongitude(); // 경도

                String msg = "Latitude : " + latitude
                        + "\nLongitude : " + longitude;
                textView.append("\n내위치 => \n" + msg);
                scrollView.fullScroll(View.FOCUS_DOWN);
            }

        }catch (SecurityException e){

        }

    }

    // 위험권한 : 실행시 허용여부를 다시 물어봄
    private void checkDangerousPermissions() {
        String[] permissions = {
                // 위험권한 내용 : 메니페스트에 권한을 여기에 적음
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION


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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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