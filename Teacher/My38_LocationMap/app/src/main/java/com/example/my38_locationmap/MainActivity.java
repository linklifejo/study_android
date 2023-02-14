package com.example.my38_locationmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    SupportMapFragment mapFragment;
    GoogleMap map;
    EditText editText;

    MarkerOptions myMarker;

    Location myLoc, markerLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        editText = findViewById(R.id.editText);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Log.d(TAG, "onMapReady: Google Map is Ready!!!");

                map = googleMap;
                try {
                    // 내 위치를 볼수 있게 해준다
                    map.setMyLocationEnabled(true);
                }catch (SecurityException e){

                }

                // 하나의 윈도우인포창 설정하기 - 마커클릭시 하나의 창뜬다.
                map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        LinearLayout info = new LinearLayout(getApplicationContext());
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(getApplicationContext());
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(getApplicationContext());
                        snippet.setTextColor(Color.GRAY);
                        snippet.setGravity(Gravity.LEFT);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });

            }
        });

        // 구글맵 초기화
        MapsInitializer.initialize(this);

        // 내위치 찾기
        findViewById(R.id.btnLoc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMyLocation();

            }
        });

        // 한글주소를 위도와 경도를 받아 위치 찾기
        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().length() > 0){
                    Location location = getLocationFromAddress
                            (getApplicationContext(), editText.getText().toString());

                    // 지도에서 그 위치로 이동해서 보여준다
                    showCurrentLocation(location);
                }
            }
        });


    }

    // 한글 주소를 받아서 Location 형태로 변경시켜서 보내주는 매소드
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");

        try {                                   //   한글주소,   최대 5개
            addresses = geocoder.getFromLocationName(address, 5);
            if((addresses == null) || (addresses.size() == 0) ){
                return null;
            }

            // 넘겨받은 주소리스트에서 가장 주소에 가까운 0번째 값을 사용한다
            Address addressLoc = addresses.get(0);
            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resLocation;
    }

    private void requestMyLocation() {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);
                        }
                    }
            );

            /*manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);
                        }
                    }
            );*/

            // 터널같은곳에 들어가면 신호를 받지 못하므로
            // 마지막 수신이 된곳을 알려주게 한다
            Location lastLocation =
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation != null){
                Double latitude = lastLocation.getLatitude();  // 위도
                Double longitude = lastLocation.getLongitude();  // 경도

                String msg = "Latitude : " + latitude
                        + "\nLongitude : " + longitude;
                Log.d(TAG, "requestMyLocation: " + msg);
            }

        }catch (SecurityException e){

        }

    }

    private void showCurrentLocation(Location location) {
        // 지도에 위치를 찍을때는 LatLng 타입을 사용함 => 변환시켜줌
        LatLng curPoint =
                new LatLng(location.getLatitude(), location.getLongitude());
        // 현재 내위치 전역변수에 넣음
        myLoc = location;

        String msg = "Latitude : " + curPoint.latitude
                + "\nLongitude : " + curPoint.longitude;
        Log.d(TAG, "showCurrentLocation1: " + msg);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 18));

        // 마커찍기 : Location 생성 : 나중에는 DB나 API에서 가져옴
        Location location1 = new Location("");
        location1.setLatitude(35.153817);
        location1.setLongitude(126.8889);
        String name = "한강병원";
        String phone = "010-1111-1111";
        showMyLocationMarker(location1, name, phone);

        Location location2 = new Location("");
        location2.setLatitude(35.153825);
        location2.setLongitude(126.8885);
        name = "한울병원";
        phone = "010-1111-2222";
        showMyLocationMarker(location2, name, phone);
    }

    // location 받아서 마커 생성하여 지도에 표시하기
    private void showMyLocationMarker(Location location,
                                      String name, String phone) {
        markerLoc = location;
        // 위에서 넣은 내위치와 마커까지의 위치까지 거리를 구한다
        int distance = getDistance(myLoc, markerLoc);

        if(myMarker == null){
            myMarker = new MarkerOptions();
            myMarker.position(
                    new LatLng(location.getLatitude(), location.getLongitude()));
            myMarker.title("◎ " + name);
            myMarker.snippet(phone + "\n거리 => " + distance + " m");
            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myMarker);
            myMarker = null;
        }

    }

    private int getDistance(Location myLoc, Location markerLoc) {
        double distance = 0;
        // 거리를 구할때는 Location 타입사용
        distance = myLoc.distanceTo(markerLoc);

        return (int)distance;
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
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