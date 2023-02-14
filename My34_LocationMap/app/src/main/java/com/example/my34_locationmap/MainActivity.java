package com.example.my34_locationmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    SupportMapFragment mapFragment;
    GoogleMap map;
    EditText etAddress;

    MarkerOptions myMarker;
    Location myLoc, markerLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        etAddress = findViewById(R.id.etAddress);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // map이 준비가 되면 onMapReady를 콜백한다
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Log.d(TAG, "onMapReady: Google Map is Ready...");

                map = googleMap;

                try {
                    // 내 위치를 볼수 있게 해준다
                    map.setMyLocationEnabled(true);
                }catch (SecurityException e){
                    e.getMessage();
                }
            // 하나의 윈도우 정보창 설정하기 - 마커 클릭시 하나의 창뜬다
                map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Nullable
                    @Override
                    public View getInfoContents(@NonNull Marker marker) {
                        LinearLayout info = new LinearLayout(getApplicationContext());
                        info.setOrientation(LinearLayout.VERTICAL);
                        //Marker Title
                        TextView title = new TextView(getApplicationContext());
                        title.setTextColor(Color.BLUE);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());
                        // Marker snippet
                        TextView snippet = new TextView(getApplicationContext());
                        snippet.setTextColor(Color.RED);
                        snippet.setGravity(Gravity.LEFT);
                        snippet.setText(marker.getSnippet());
                        info.addView(title);
                        info.addView(snippet);
                        return info;

                    }

                    @Nullable
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        return null;
                    }
                });

            }
        }); // getMapAsync()

        // 구글맵 초기화
        MapsInitializer.initialize(this);

        // 내 위치 찾기
        findViewById(R.id.btnLoc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMyLocation();
            }
        });
        // 한글주소를 위도와 경도로 변환하여 위치 찾기
        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etAddress.getText().toString().length() > 0){
                    Location location = getLocationFromAddress
                            (getApplicationContext(),etAddress.getText().toString());
                    // 한글주소에서 location으로 변환한것을 지도에 보여준다
                    showCurrentLocation(location);
                }
                
                
            }
        });

    }
    // 한글 주소를 받아서 Location 형태로 변경시켜서 보내주는 메소드
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");

        try {                                       //한글주소,최대반환주소갯수
            addresses = geocoder.getFromLocationName(address,5);
            if(addresses == null || (addresses.size() == 0)){
                return null;
            }
            // 념겨받은 주소리스트에서 가장 주소에 가까운 0번째 값을 사용한다
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
                (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            // 위치를 지도에 표시
                            showCurrentLocation(location);
                        }
                    }
            );
            // 만약 터널 같은곳에 들어가면 신호를 받지 못하므로
            // 마지막 수신된곳을 알려주게 한다
            Location lastLocation =
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); //기지국

            if(lastLocation != null){
                // 마지막에 수신된 장소를 지도에 표시한다
                showCurrentLocation(lastLocation);
            }
        }catch (SecurityException e){
            e.getMessage();
        }
 
    }

    private void showCurrentLocation(Location location) {
        // 현재 내 위치 전역변수에 넣음
        myLoc =location;
        // 지도에 위치를 찍을때는 LatLng타입을 사용함
        // Location => LatLng 타입으로 변환시켜줌
        LatLng curPoint = new LatLng(location.getLatitude(),location.getLongitude());
        String msg = "Latitude : " + curPoint.latitude
                + "\nLongitude : " + curPoint.longitude;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,18));
        // 마커찍기 : Location 생성 : 나중에는 DB난 API에서 가져옴
        Location location1 = new Location("");
        location1.setLatitude(35.153817);
        location1.setLongitude(126.8889);
        String name = "똥강아지";
        String phone = "010-111-1111";
        showMyLocationMarker(location1,name,phone);
        Location location2 = new Location("");
        location2.setLatitude(35.153825);
        location2.setLongitude(126.8885);
        String name1 = "똥강아지1";
        String phone1 = "010-111-2222";
        showMyLocationMarker(location2,name1,phone1);
    }

    private  void showMyLocationMarker(Location location, String name, String phone){
        //마커위치를 전역변수에 담음
        markerLoc = location;
        // 마커와 내 위치까지의 거리를 구한다
        int distance = getDistance(myLoc,markerLoc);
        if(myMarker == null){
            myMarker = new MarkerOptions();
            myMarker.position(new LatLng(location.getLatitude(),location.getLongitude()));
            myMarker.title("♤ " + name);
            myMarker.snippet(phone + "\n거리 => " + distance + " M");
            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myMarker);
            myMarker = null;

        }

    }

    private int getDistance(Location myLoc, Location markerLoc) {
        double distance = 0;
        // 거리를 구할때는 Location 타입을 사용합니다
        distance = myLoc.distanceTo(markerLoc);
        return (int)distance;
    }

    // 위험권한 : 실행시 허용여부를 다시 물어봄
    private void checkDangerousPermissions() {
        String[] permissions = {
                // 위험권한 내용 : 메니페스트에 권한을 여기에 적음
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